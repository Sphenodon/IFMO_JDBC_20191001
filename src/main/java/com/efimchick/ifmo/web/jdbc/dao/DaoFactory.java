package com.efimchick.ifmo.web.jdbc.dao;

import com.efimchick.ifmo.web.jdbc.ConnectionSource;
import com.efimchick.ifmo.web.jdbc.domain.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;


public class DaoFactory {

    public EmployeeDao employeeDAO() {
        List<Employee> employees = getEmployees();
        return new EmployeeDao() {
            @Override
            public List<Employee> getByDepartment(Department department) {
                List<Employee> emps = new ArrayList<>();
                for (Employee emp : employees) {
                    if (Objects.equals(emp.getDepartmentId(), department.getId())) {
                        emps.add(emp);
                    }
                }
                return emps;
            }

            @Override
            public List<Employee> getByManager(Employee employee) {
                List<Employee> managers = new ArrayList<>();
                for (Employee emp : employees) {
                    if (Objects.equals(emp.getManagerId(), employee.getId())) {
                        managers.add(emp);
                    }
                }

                return managers;
            }

            @Override
            public Optional<Employee> getById(BigInteger Id) {
                for (Employee emp : employees) {
                    if (Objects.equals(emp.getId(), Id)) {
                        return Optional.of(emp);
                    }
                }
                return Optional.empty();
            }

            @Override
            public List<Employee> getAll() {
                return employees;
            }

            @Override
            public Employee save(Employee employee) {
                if (employee != null) {
                    for (Employee emp : employees) {
                        if (Objects.equals(emp.getId(), employee.getId())) {
                            employees.remove(emp);
                        }
                    }
                    employees.add(employee);
                }
                return employee;
            }

            @Override
            public void delete(Employee employee) {
                if (employee != null) {
                    employees.remove(employee);
                }
            }
        };
    }

    private Employee employeeMapper(ResultSet rs) {
        try {
            return  new Employee(
                        new BigInteger(rs.getString("id")),
                        new FullName(rs.getString("firstname"), rs.getString("lastname"), rs.getString("middlename")),
                        Position.valueOf(rs.getString("position")),
                        LocalDate.parse(rs.getString("hiredate")),
                        new BigDecimal(rs.getString("salary")),
                        BigInteger.valueOf(rs.getInt("manager")),
                        BigInteger.valueOf(rs.getInt("department")));
        } catch (SQLException exception) {
            return null;
        }
    }

    private Department departmentMapper(ResultSet rs) {
        try {
            return new Department(
                    new BigInteger(rs.getString("id")),
                    rs.getString("name"),
                    rs.getString("location")
            );
        } catch (SQLException exception) {
            return null;
        }
    }

    private List<Employee> getEmployees() {
        try {
            List<Employee> employeesList = new ArrayList<>();
            ConnectionSource source = ConnectionSource.instance();
            Connection connection = source.createConnection();
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM employee");
            while (rs.next()) {
                employeesList.add(employeeMapper(rs));
            }
            return employeesList;
        }
        catch (SQLException exception) {
            return null;
        }
    }

    private List<Department> getDepartments() {
        try {
            List<Department> departmentsList = new ArrayList<>();
            ConnectionSource source = ConnectionSource.instance();
            Connection connection = source.createConnection();
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM department");
            while (rs.next()) {
                departmentsList.add(departmentMapper(rs));
            }
            return departmentsList;
        }
        catch (SQLException exception) {
            return null;
        }
    }



    public DepartmentDao departmentDAO() {
        List<Department> departments = getDepartments();
        return new DepartmentDao() {
            @Override
            public Optional<Department> getById(BigInteger Id) {
                for (Department dep : departments) {
                    if (Objects.equals(dep.getId(), Id)) {
                        return Optional.of(dep);
                    }
                }
                return Optional.empty();
            }

            @Override
            public List<Department> getAll() {
                return departments;
            }

            @Override
            public Department save(Department department) {
                if (department != null) {
                    for (Department dep : departments) {
                        if (Objects.equals(department.getId(), dep.getId())) {
                            departments.remove(dep);
                        }
                    }
                    departments.add(department);
                }
                return department;
            }

            @Override
            public void delete(Department department) {
                departments.remove(department);
            }
        };
    }
}
