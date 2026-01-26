package com.smartcontractor.service;

import com.smartcontractor.model.Company;
import com.smartcontractor.model.mappermodel.CompanyMap;

import java.util.List;

public interface CompanyService {
    Company createCompany(String userId, Company company);

    List<Company> getAllCompanys(String userId);


    CompanyMap updateCompany(String userId, Company company);

    void deleteCompany(String userId, Company company);


}
