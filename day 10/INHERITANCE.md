# JPA Table Inheritance in this project

`User` is the parent of `Admin` and `Client`. JPA has three ways to map that
class hierarchy onto tables. This project uses **SINGLE_TABLE**.

## The three strategies

### 1. SINGLE_TABLE (used here)

```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
public abstract class User { ... }

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User { private String department; }

@Entity
@DiscriminatorValue("CLIENT")
public class Client extends User { private String company; }
```

Every subclass shares **one physical table**. Columns from every subclass are
merged into it, and a discriminator column tells Hibernate which subclass a
row belongs to.

`users` table:

| id | user_type | name  | email             | password | department | company    |
|----|-----------|-------|-------------------|----------|------------|------------|
| 1  | ADMIN     | Asha  | asha@corp.com     | ...      | Platform   | NULL       |
| 2  | CLIENT    | Ravi  | ravi@client.com   | ...      | NULL       | Acme Corp  |

- Columns that belong to only one subclass (`department`, `company`) must be
  **nullable**, since a `CLIENT` row has no department and vice versa.
- `user_type` is populated automatically by Hibernate from `@DiscriminatorValue`.

**Pros:** one table, no joins, fastest reads/writes, simplest schema.
**Cons:** lots of nullable columns as subclasses grow; no NOT NULL constraint
possible on subclass-only fields at the DB level.

### 2. JOINED

```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User { ... }
```

Produces one table per class, linked by a shared primary key:

- `users(id, user_type, name, email, password)`
- `admin(id [FK -> users.id], department)`
- `client(id [FK -> users.id], company)`

Loading an `Admin` requires a `JOIN` between `users` and `admin`. Loading all
`User` rows requires an outer join across every subtype table.

**Pros:** normalized, subclass columns can be `NOT NULL`, no wasted columns.
**Cons:** joins on every read; slower than SINGLE_TABLE at scale.

### 3. TABLE_PER_CLASS

```java
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User { ... }
```

Each concrete subclass gets its own complete, self-contained table
(`admin(id, name, email, password, department)`,
`client(id, name, email, password, company)`) — no shared `users` table at
all. Polymorphic queries (`SELECT * FROM User`) are done with `UNION ALL`
across every subclass table.

**Pros:** each table is fully normalized and independent.
**Cons:** no shared table, `UNION`-based polymorphic queries are the slowest
and hardest to index; primary keys usually can't use simple `IDENTITY`
generation across subclasses.

## Why SINGLE_TABLE here

- Only two subclasses with a handful of extra columns each — the "wasted
  nullable column" downside is minor.
- `UserRepository.findAll()` can return a mixed list of `Admin` and `Client`
  objects with a single, fast `SELECT * FROM users` — no joins, no unions.
- `AdminRepository` / `ClientRepository` filter automatically on
  `user_type = 'ADMIN'` / `'CLIENT'` under the hood, so you still get typed
  access when you only care about one subtype.

## How the discriminator flows through the code

1. `AdminServiceImpl.createAdmin` builds an `Admin` entity and saves it via
   `AdminRepository` → Hibernate inserts a row into `users` with
   `user_type='ADMIN'`.
2. `UserServiceImpl.getAllUsers` calls `UserRepository.findAll()`, which
   returns both `Admin` and `Client` instances (Hibernate uses `user_type` to
   decide which Java class to instantiate for each row). The service maps
   each one to a `UserDTO` and stamps `userType` as `"ADMIN"` or `"CLIENT"`
   using an `instanceof` check.
3. `AdminRepository extends JpaRepository<Admin, Long>` only ever sees rows
   where `user_type = 'ADMIN'` — Hibernate adds that filter for you.

## Switching strategy later

If subclasses grow large and divergent (many admin-only or client-only
columns, or you need `NOT NULL` on subclass fields), switch to `JOINED` by
changing one annotation on `User`:

```java
@Inheritance(strategy = InheritanceType.JOINED)
```

No changes needed in repositories, services, or controllers — the mapping
strategy is transparent above the entity layer.
