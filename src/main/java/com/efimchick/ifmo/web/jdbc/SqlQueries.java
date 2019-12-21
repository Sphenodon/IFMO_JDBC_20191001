package com.efimchick.ifmo.web.jdbc;

/**
 * Implement sql queries like described
 */
public class SqlQueries {
    //Select all employees sorted by last name in ascending order
    String select01 = "SELECT * FROM employee ORDER BY LASTNAME ASC";

    //Select employees having no more than 5 characters in last name sorted by last name in ascending order
    //language=HSQLDB
    String select02 = "SELECT * FROM employee WHERE LENGTH(LASTNAME) <= 5 ORDER BY LASTNAME ASC";

    //Select employees having salary no less than 2000 and no more than 3000
    //language=HSQLDB
    String select03 = "SELECT * FROM employee WHERE SALARY >= 2000 AND SALARY <= 3000";

    //Select employees having salary no more than 2000 or no less than 3000
    //language=HSQLDB
    String select04 = "SELECT * FROM employee WHERE SALARY <= 2000 OR SALARY >= 3000";

    //Select employees assigned to a department and corresponding department name
    //language=HSQLDB
    String select05 = "SELECT * FROM employee, department WHERE EMPLOYEE.department= department.ID ";

    //Select all employees and corresponding department name if there is one.
    //Name column containing name of the department "depname".
    //language=HSQLDB
    String select06 = "SELECT employee.ID, employee.FIRSTNAME, employee.LASTNAME, employee.SALARY, department.NAME AS depname FROM employee LEFT OUTER JOIN department ON employee.department = department.ID";

    //Select total salary pf all employees. Name it "total".
    //language=HSQLDB
    String select07 = "SELECT SUM(SALARY) AS total FROM employee";

    //Select all departments and amount of employees assigned per department
    //Name column containing name of the department "depname".
    //Name column containing employee amount "staff_size".
    //language=HSQLDB
    String select08 = "SELECT department.NAME AS depname, COUNT(employee.FIRSTNAME) AS staff_size FROM employee, department WHERE employee.department = department.ID GROUP BY department.NAME";

    //Select all departments and values of total and average salary per department
    //Name column containing name of the department "depname".
    //language=HSQLDB
    String select09 = "SELECT department.NAME AS depname, SUM(employee.SALARY) AS total, AVG(employee.SALARY) AS average FROM employee, department WHERE employee.department = department.ID GROUP BY department.NAME";

    //Select all employees and their managers if there is one.
    //Name column containing employee lastname "employee".
    //Name column containing manager lastname "manager".
    //language=HSQLDB
    String select10 = "SELECT employee.LASTNAME AS employee, MANAGER.LASTNAME AS manager FROM employee LEFT OUTER JOIN employee MANAGER ON employee.MANAGER = MANAGER.ID";
}
