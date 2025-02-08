package com.example.backend.config;

public class Endpoint {
    public static String[] getPublic = {
        "/api/categories/*",
            "/api/debts/*"
    };

    public static String[] postPublic = {
            "/api/users/*",
            "/api/categories/create/*",
            "/api/debts/create/*"
    };

    public static String[] deletePublic = {
            "/api/categories/*",
            "/api/debts/*"
    };

    public static String[] putPublic = {
            "/api/categories/*",
            "/api/debts/*"
    };
}
