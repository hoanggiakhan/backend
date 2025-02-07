package com.example.backend.config;

public class Endpoint {
    public static String[] getPublic = {
        "/api/categories/*"
    };

    public static String[] postPublic = {
            "/api/users/*",
            "/api/categories/create/*"
    };

    public static String[] deletePublic = {
            "/api/categories/*"
    };

    public static String[] putPublic = {
            "/api/categories/*"
    };
}
