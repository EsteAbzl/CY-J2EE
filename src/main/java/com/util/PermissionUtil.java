package com.util;

import com.model.Employee;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class PermissionUtil {
    public enum ValidationEnum {
        ALLOWED, NOT_LOGGED, DENIED
    }

    public static ValidationEnum isConnexionAllowed (HttpServletRequest req, Integer[] allowedDepartmentIds){
        return isConnexionAllowed(req, allowedDepartmentIds, new Integer[]{});
    }
    public static ValidationEnum isConnexionAllowed (HttpServletRequest req, Integer[] allowedDepartmentIds, Integer[] allowedEmployeeIds){
        ArrayList<Integer> DepartmentIds = new ArrayList<>(Arrays.asList(allowedDepartmentIds));
        ArrayList<Integer> EmployeeIds = new ArrayList<>(Arrays.asList(allowedEmployeeIds));
        System.out.println("department allowed :"+DepartmentIds+"\nEmployee allowed :"+EmployeeIds);

        ValidationEnum res = ValidationEnum.DENIED;

        HttpSession session = req.getSession();
        Employee employee = (Employee) session.getAttribute("SESSION_employee");

        if(employee != null){
            if(DepartmentIds.contains(employee.getDepartmentId())){
                res = ValidationEnum.ALLOWED;
            }

            if(EmployeeIds.contains(employee.getId())){
                res = ValidationEnum.ALLOWED;
            }
        }
        else {
            res = ValidationEnum.NOT_LOGGED;
        }

        System.out.println("is allowed ? : "+res);
        return res;
    }


    public static void manageConnexionPermission (HttpServletRequest req, HttpServletResponse resp, ValidationEnum v) throws IOException, ServletException {
        manageConnexionPermission(req, resp, v, "permissionDenied.jsp", "error.jsp");
    }

    public static void manageConnexionPermission (HttpServletRequest req, HttpServletResponse resp, ValidationEnum v, String pageDenied, String pageNotLogged) throws IOException, ServletException {
        switch (v){
            case DENIED :
                req.getRequestDispatcher(pageDenied).forward(req, resp);
                break;
            case NOT_LOGGED:
                req.getRequestDispatcher(pageNotLogged).forward(req, resp);
                break;
        }
    }
}
