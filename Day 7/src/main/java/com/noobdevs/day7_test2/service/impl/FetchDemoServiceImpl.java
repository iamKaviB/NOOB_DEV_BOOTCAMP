package com.noobdevs.day7_test2.service.impl;

import com.noobdevs.day7_test2.dto.FetchDemoResponseDTO;
import com.noobdevs.day7_test2.model.Order;
import com.noobdevs.day7_test2.model.User;
import com.noobdevs.day7_test2.repository.UserRepository;
import com.noobdevs.day7_test2.service.FetchDemoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FetchDemoServiceImpl implements FetchDemoService {

    private final UserRepository userRepository;
    private final TransactionTemplate transactionTemplate;

    public FetchDemoServiceImpl(UserRepository userRepository,
                                PlatformTransactionManager transactionManager) {
        this.userRepository = userRepository;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DEMO 1 — LAZY with @Transactional
    //
    // Hibernate fires TWO separate SQL statements:
    //   SQL #1  SELECT * FROM users WHERE id = ?
    //   SQL #2  SELECT * FROM orders WHERE user_id = ?   ← triggered by getOrders()
    //
    // @Transactional keeps the session open so getOrders() can execute SQL #2.
    // Without @Transactional the session closes after findById() returns,
    // and accessing getOrders() would throw LazyInitializationException.
    //
    // LIVE SWITCH TIP:
    //   Change FetchType.LAZY → FetchType.EAGER in User.java, restart, call
    //   this endpoint again — you will see 1 SQL with a LEFT JOIN in the console.
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public FetchDemoResponseDTO demonstrateLazy(Long userId) {
        // SQL #1 fires here — loads only the users row
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // SQL #2 fires here — Hibernate sees the LAZY proxy and queries orders
        List<String> tracking = extractTrackingNumbers(user);

        FetchDemoResponseDTO dto = new FetchDemoResponseDTO();
        dto.setFetchMode("LAZY  (FetchType.LAZY + @Transactional)");
        dto.setQueriesGenerated(2);
        dto.setExplanation(
                "User loaded with SQL #1. Accessing user.getOrders() triggered SQL #2 " +
                "because the collection is LAZY. @Transactional kept the session alive so " +
                "the second query could execute. With 100 users this becomes 101 queries — " +
                "the classic N+1 problem.");
        dto.setWatchConsole("You should see 2 SELECT statements in the Spring console.");
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setOrderCount(tracking.size());
        dto.setOrderTrackingNumbers(tracking);
        return dto;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DEMO 2 — EAGER via JOIN FETCH (@Query)
    //
    // Hibernate fires ONE SQL with a LEFT OUTER JOIN:
    //   SELECT u.*, o.* FROM users u
    //   LEFT OUTER JOIN orders o ON o.user_id = u.id
    //   WHERE u.id = ?
    //
    // No @Transactional needed — both User and Orders are already in memory
    // before this method returns. Works regardless of the entity's FetchType.
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    public FetchDemoResponseDTO demonstrateEager(Long userId) {
        // Single SQL with LEFT JOIN FETCH — User + Orders loaded together
        User user = userRepository.findByIdWithOrdersEager(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        List<String> tracking = extractTrackingNumbers(user);

        FetchDemoResponseDTO dto = new FetchDemoResponseDTO();
        dto.setFetchMode("EAGER  (JOIN FETCH via @Query)");
        dto.setQueriesGenerated(1);
        dto.setExplanation(
                "User and orders loaded together in ONE SQL using LEFT JOIN FETCH. " +
                "No matter how many users you load this way, it is always a single query. " +
                "No @Transactional needed because the data is already in memory. " +
                "Trade-off: orders are always loaded even if you do not need them.");
        dto.setWatchConsole("You should see exactly 1 SELECT with a LEFT OUTER JOIN in the Spring console.");
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setOrderCount(tracking.size());
        dto.setOrderTrackingNumbers(tracking);
        return dto;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DEMO 3 — LAZY without a transaction (intentional failure)
    //
    // TransactionTemplate runs findById() inside its OWN transaction and
    // closes it as soon as execute() returns. The User entity is now DETACHED
    // — its orders proxy is disconnected from any Hibernate session.
    //
    // Calling user.getOrders().size() after the transaction closes throws:
    //   org.hibernate.LazyInitializationException:
    //   failed to lazily initialize a collection of role: ...orders
    //
    // GlobalExceptionHandler catches this and returns HTTP 500 with:
    //   { "error": "LazyInitializationException", "fix_1": ..., "fix_2": ..., "fix_3": ... }
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    public FetchDemoResponseDTO demonstrateLazyFail(Long userId) {
        // Load User inside a transaction that closes immediately after execute() returns
        User detachedUser = transactionTemplate.execute(status ->
                userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found with id: " + userId))
        );

        // Session is CLOSED here — the orders collection is an uninitialised proxy
        // This line intentionally throws LazyInitializationException
        detachedUser.getOrders().size();

        // Unreachable — GlobalExceptionHandler returns the 500 response above
        return null;
    }

    private List<String> extractTrackingNumbers(User user) {
        if (user.getOrders() == null) return Collections.emptyList();
        return user.getOrders().stream()
                .map(Order::getTrackingNumber)
                .collect(Collectors.toList());
    }
}
