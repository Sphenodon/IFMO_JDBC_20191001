package com.efimchick.ifmo.web.jdbc;

import com.efimchick.ifmo.web.jdbc.domain.Employee;
import com.efimchick.ifmo.web.jdbc.domain.FullName;
import com.efimchick.ifmo.web.jdbc.domain.Position;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class SetMapperFactory {

    public SetMapper<Set<Employee>> employeesSetMapper() {
        return new SetMapper<Set<Employee>>() {
            @Override
            public Set<Employee> mapSet(ResultSet resultSet) {
                Set<Employee> employees = new HashSet<>();
                try {
                    while (resultSet.next()) {
                        employees.add(managers(resultSet, resultSet.getRow()));
                    }
                    return employees;
                }
                catch (SQLException e) {
                    throw new UnsupportedOperationException();
                }
            }
            private Employee managers(ResultSet resultSet, int position) throws SQLException {
                Employee manager = null;
                int IdOfManager = resultSet.getInt("manager");
                if (resultSet.getString("manager") != null) {
                    resultSet.absolute(0);
                    while (resultSet.next()) {
                        if (Integer.parseInt(resultSet.getString("id")) == IdOfManager)
                            manager = managers(resultSet, resultSet.getRow());

                    }
                    resultSet.absolute(position);
                }
                return  new Employee(
                        (resultSet.getBigDecimal("id").toBigInteger()),
                        new FullName(
                                    resultSet.getString("firstname"),
                                    resultSet.getString("lastname"),
                                    resultSet.getString("middlename")
                        ),
                        Position.valueOf(resultSet.getString("position")), resultSet.getDate("hiredate").toLocalDate(), resultSet.getBigDecimal("salary"), manager
                );
            }
        };
    }
}