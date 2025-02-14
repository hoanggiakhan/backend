package com.example.backend.config;

public class Endpoint {
    public static String[] getPublic = {
        "/api/users/*",
        "/api/categories/*",
        "/api/categories/budget/*",
            "/api/debts/*",
            "/api/transactions/*",
             "/api/reports/*",
              "/api/budgets/*"
    };

    public static String[] postPublic = {
            "/api/users/*",
            "/api/categories/create/*",
            "/api/debts/create/*",
             "/api/budgets/create/*",
             "/api/reports/*",
             "/api/transactions/*",
    };

    public static String[] deletePublic = {
            "/api/categories/*",
            "/api/debts/*",
            "/api/transactions/*",
    };

    public static String[] putPublic = {
            "/api/categories/*",
            "/api/debts/*"
    };
}
