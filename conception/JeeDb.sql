-- Schema: hrms
CREATE DATABASE IF NOT EXISTS JeeDb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE JeeDb;


DROP TABLE IF EXISTS absences;
DROP TABLE IF EXISTS payslips;
DROP TABLE IF EXISTS project_assignments;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS departments;
DROP TABLE IF EXISTS roles;


-- Roles
CREATE TABLE roles (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       `name` VARCHAR(50) NOT NULL UNIQUE -- ADMIN, RH, DEPT_HEAD, PROJECT_HEAD, EMPLOYEE
) ENGINE=InnoDB;

-- Departments
CREATE TABLE departments (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             `name` VARCHAR(150) NOT NULL UNIQUE,
                             `description` TEXT
) ENGINE=InnoDB;

-- Employees
CREATE TABLE employees (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           first_name VARCHAR(100) NOT NULL,
                           last_name VARCHAR(100) NOT NULL,
                           email VARCHAR(150) NOT NULL UNIQUE,
                           grade VARCHAR(50),
                           position_title VARCHAR(100),
                           base_salary DECIMAL(10,2) NOT NULL,
                           department_id INT,
                           `active` TINYINT(1) DEFAULT 1,
                           FOREIGN KEY (department_id) REFERENCES departments(id)
) ENGINE=InnoDB;

CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(100) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       full_name VARCHAR(150) NOT NULL,
                       must_change_password TINYINT(1) NOT NULL DEFAULT 0,
                       role_id INT NOT NULL,
                       `active` TINYINT(1) NOT NULL DEFAULT 1,
                       employee_id INT,
                       CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES roles(id),
                       CONSTRAINT fk_users_employee FOREIGN KEY (employee_id) REFERENCES employees(id)
                           ON UPDATE CASCADE
                           ON DELETE SET NULL
) ENGINE=InnoDB;

-- Projects
CREATE TABLE projects (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          `name` VARCHAR(150) NOT NULL UNIQUE,
                          `description` TEXT,
                          `status` ENUM('EN_COURS','TERMINE','ANNULE') DEFAULT 'EN_COURS',
                          start_date DATE,
                          end_date DATE,
                          department_id INT,
                          FOREIGN KEY (department_id) REFERENCES departments(id)
) ENGINE=InnoDB;

-- Employee assignments to projects (many-to-many)
CREATE TABLE project_assignments (
                                     project_id INT NOT NULL,
                                     employee_id INT NOT NULL,
                                     role_in_project VARCHAR(100),
                                     PRIMARY KEY (project_id, employee_id),
                                     FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
                                     FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
);

-- Payslips
CREATE TABLE payslips (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          employee_id INT NOT NULL,
                          period_year INT NOT NULL,
                          period_month INT NOT NULL, -- 1..12
                          base_salary DECIMAL(10,2) NOT NULL,
                          bonuses DECIMAL(10,2) DEFAULT 0.00,
                          deductions DECIMAL(10,2) DEFAULT 0.00,
                          net_pay DECIMAL(10,2) NOT NULL,
                          generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          UNIQUE KEY uniq_emp_period (employee_id, period_year, period_month),
                          FOREIGN KEY (employee_id) REFERENCES employees(id)
) ENGINE=InnoDB;

-- Absences (optional for deductions logic)
CREATE TABLE absences (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          employee_id INT NOT NULL,
                          `date` DATE NOT NULL,
                          `type` ENUM('CONGE','MALADIE','NON_PAYE') DEFAULT 'NON_PAYE',
                          hours INT DEFAULT 8,
                          FOREIGN KEY (employee_id) REFERENCES employees(id)
) ENGINE=InnoDB;

-- Seed roles
INSERT INTO roles (name) VALUES ('ADMIN'), ('DEPT_HEAD'), ('PROJECT_HEAD'), ('EMPLOYEE');

-- Example admin user (password: admin123 hashed with BCrypt placeholder; replace in real)
-- admin user (must_change_password = 0)
INSERT INTO users (username, password_hash, full_name, must_change_password, role_id)
VALUES ('admin', 'admin', 'Administrateur', 0, 1);

