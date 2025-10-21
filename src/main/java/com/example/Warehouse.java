package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Warehouse {

    private static final Map<String, Warehouse> INSTANCES = new ConcurrentHashMap<>();

    private final String name;
    private final Map<UUID, Product> products = new HashMap<>();
    private final Set<Product> changedProducts = new HashSet<>();

    private Warehouse(String name){
        this.name = name;
    }

    public static Warehouse getInstance(String name){
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Warehouse name cannot be null or blank.");
        }
        return INSTANCES.computeIfAbsent(name, Warehouse::new);
    }

    public void clearProducts(){
        products.clear();
        changedProducts.clear();
    }

    public void addProduct(Product product){
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        products.put(product.uuid(), product);
    }

    public List<Product> getProducts(){
        return Collections.unmodifiableList(new ArrayList<>(products.values()));
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    public void updateProductPrice(UUID id, BigDecimal newPrice){
        Product product = getProductById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id: " + id));

        product.price(newPrice);

        changedProducts.add(product);
    }

    public  Set<Product> getChangedProducts() {
        return  Collections.unmodifiableSet(new HashSet<>(changedProducts));
    }

    public List<Perishable> expiredProducts(){
        LocalDate today = LocalDate.now();

        return  products.values().stream()
                .filter(p -> p instanceof Perishable)
                .map(p -> (Perishable) p)
                .filter(Perishable::isExpired)
                .collect(Collectors.toList());
    }

    public List<Shippable> shippableProducts(){
        return products.values().stream()
                .filter(p -> p instanceof Shippable)
                .map(p -> (Shippable) p)
                .collect(Collectors.toList());
    }

    public void remove(UUID id) {
        Product removed = products.remove(id);

        if (removed != null)  {
            changedProducts.remove(removed);
        }
    }

}
