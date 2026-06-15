package com.noobdevs.dayfour.controller;

import com.noobdevs.dayfour.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST Controller responsible for handling product-related HTTP requests.
 *
 * Base URL:
 * /api/v1/products
 *
 * This controller demonstrates basic CRUD operations using an in-memory list.
 * In a real-world application, the data would typically be stored and retrieved
 * from a database through a Service and Repository layer.
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    /**
     * Temporary in-memory data store.
     * Used only for demonstration purposes.
     */
    private List<Product> productList = new ArrayList<>();

    /**
     * Initializes sample product data when the controller is created.
     */
    public ProductController() {
        productList.add(new Product(1, "Mobile Phone", 85000));
        productList.add(new Product(2, "TV", 220000));
    }

    /**
     * Retrieves all available products.
     *
     * HTTP Method: GET
     * Endpoint: /api/v1/products
     *
     * @return List of products with HTTP 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<Product>> getProductList() {
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    /**
     * Retrieves a single product by its identifier.
     *
     * HTTP Method: GET
     * Endpoint: /api/v1/products/{id}
     *
     * @param id Product identifier received from the URL path.
     * @return Product with HTTP 200 (OK) if found,
     *         otherwise HTTP 404 (NOT FOUND).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {

        for (Product p : productList) {
            if (p.getId() == id) {
                return new ResponseEntity<>(p, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Creates a new product.
     *
     * HTTP Method: POST
     * Endpoint: /api/v1/products
     *
     * Request Body:
     * {
     *   "id": 3,
     *   "name": "Laptop",
     *   "price": 300000
     * }
     *
     * Basic validation is performed to ensure the product name is provided.
     *
     * @param newProduct Product information received in the request body.
     * @return Created product with HTTP 201 (CREATED),
     *         or HTTP 400 (BAD REQUEST) if validation fails.
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product newProduct) {

        if (newProduct.getName() == null || newProduct.getName().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        productList.add(newProduct);

        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    /**
     * Deletes a product using its identifier.
     *
     * HTTP Method: DELETE
     * Endpoint: /api/v1/products/{id}
     *
     * @param id Product identifier.
     * @return Deleted product ID with HTTP 200 (OK),
     *         or HTTP 404 (NOT FOUND) if the product does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable int id) {

        for (Product p : productList) {
            if (p.getId() == id) {
                productList.remove(p);
                return new ResponseEntity<>(id, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Updates an existing product.
     *
     * HTTP Method: PUT
     * Endpoint: /api/v1/products/{id}
     *
     * @param id Product identifier.
     * @param productWithNewData Updated product details received from the client.
     * @return Updated product with HTTP 200/201,
     *         or HTTP 404 (NOT FOUND) if the product does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(
            @PathVariable int id,
            @RequestBody Product productWithNewData) {

        for (Product p : productList) {

            if (p.getId() == id) {

                p.setName(productWithNewData.getName());
                p.setPrice(productWithNewData.getPrice());

                return new ResponseEntity<>(p, HttpStatus.CREATED);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}