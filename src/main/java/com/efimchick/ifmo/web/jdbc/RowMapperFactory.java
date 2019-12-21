package com.efimchick.ifmo.web.jdbc;

import com.efimchick.ifmo.web.jdbc.domain.Employee;
import com.efimchick.ifmo.web.jdbc.domain.FullName;
import com.efimchick.ifmo.web.jdbc.domain.Position;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDate;

public class RowMapperFactory {

    public RowMapper<Employee> employeeRowMapper() {

        return resultSet -> {
            try {
                return new Employee(new BigInteger(resultSet.getString("ID")), new FullName(resultSet.getString("FIRSTNAME"), resultSet.getString("LASTNAME"), resultSet.getString("MIDDLENAME")), Position.valueOf(resultSet.getString("POSITION")), LocalDate.parse(resultSet.getString("HIREDATE")), new BigDecimal(resultSet.getString("SALARY")));
            } catch (SQLException e) {
                return null;
            }
        };
    }
}
