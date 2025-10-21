package com.example;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Category {

    private static final Map<String, Category> CACHE = new ConcurrentHashMap<>();
    private final String name;

    private Category(String name){
        this.name = name;
    }
    public static Category of(String name){
        if (name == null){
            throw new IllegalArgumentException("Category name can't be null");
        }
        if (name.isBlank()){
            throw new IllegalArgumentException("Category name can't be blank");
        }

        String normalizedName =
                name.substring(0, 1).toUpperCase(Locale.ROOT) +
                        name.substring(1).toLowerCase(Locale.ROOT);

        return CACHE.computeIfAbsent(normalizedName, Category::new);
    }

    public String name(){
        return name;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return Objects.equals(name, category.name);
    }
    @Override
    public int hashCode(){
        return Objects.hash(name);
    }

    @Override
    public String toString(){
        return name;
    }
}
