package com.smartcontractor.mapper;

import com.smartcontractor.model.Company;
import com.smartcontractor.model.User;
import com.smartcontractor.model.mappermodel.CompanyMap;
import com.smartcontractor.model.mappermodel.UserMap;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyMapper {

    public CompanyMapper() {
    }

    public static CompanyMap toCompanyMap(Company company) {
        if (company == null) return null;

        CompanyMap res = new CompanyMap();
        res.setCompanyId(company.getCompanyId());
        res.setUserId(company.getUserId());
        res.setCompanyName(company.getCompanyName());
        return res;
    }


    public static List<CompanyMap> toCompanyToList(List<Company> users) {

        if (users == null) return List.of();

        return users.stream()
                .map(CompanyMapper::toCompanyMap)
                .collect(Collectors.toList());
    }

}
