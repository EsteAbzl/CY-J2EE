package com.util;

import com.model.Employee;

public class ValidationUtil {
    public static String validateEmployee(Employee e) {
        if (e.getFirstName() == null || e.getFirstName().isEmpty()) return "Prénom requis";
        if (e.getLastName() == null || e.getLastName().isEmpty()) return "Nom requis";
        if (e.getEmail() == null || !e.getEmail().contains("@")) return "Email invalide";
        if (e.getBaseSalary() <= 0) return "Salaire de base invalide";
        return null;
    }
}
