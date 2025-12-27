package com.smartcontractor.mapper;

import com.smartcontractor.model.Employee;
import com.smartcontractor.model.mappermodel.EmployeeMap;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {

    public EmployeeMapper() {
    }

    public static EmployeeMap toEmployeeMap(Employee employee) {
        if (employee == null) return null;

        EmployeeMap res = new EmployeeMap();
        res.setEmployeeId(employee.getEmployeeId());
        res.setCompanyId(employee.getCompanyId());
        res.setName(employee.getName());
        res.setDesignation(employee.getDesignation());
        res.setDepartment(employee.getDepartment());
        res.setContactNumber(employee.getContactNumber());

        return res;
    }

    public static List<EmployeeMap> toEmployeeToList(List<Employee> employees) {

        if (employees == null) return List.of();

        return employees.stream()
                .map(EmployeeMapper::toEmployeeMap)
                .collect(Collectors.toList());
    }

}
