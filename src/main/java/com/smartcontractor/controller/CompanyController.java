package com.smartcontractor.controller;

import com.smartcontractor.common.ApiResponse;
import com.smartcontractor.mapper.CompanyMapper;
import com.smartcontractor.model.Company;
import com.smartcontractor.model.User;
import com.smartcontractor.model.mappermodel.CompanyMap;
import com.smartcontractor.service.CompanyService;
import com.smartcontractor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
    @RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createCompany(@RequestParam String userId, @RequestBody Company company) {

        try {
            Company createdCompany = companyService.createCompany(userId, company);

            CompanyMap mapper = CompanyMapper.toCompanyMap(createdCompany);

            return new ResponseEntity<>(ApiResponse.success(HttpStatus.CREATED.value(), "Company created successfully", mapper), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            if ("User not found".equals(e.getMessage())) {
                return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Company creation failed", "User not found"), HttpStatus.NOT_FOUND);
            }
             return new ResponseEntity<>(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Company creation failed", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/update")
    public ResponseEntity<ApiResponse<?>> updateCompany(@RequestParam String userId, @RequestBody Company company) {

        try {
            Company createdCompany = companyService.createCompany(userId, company);

            CompanyMap mapper = CompanyMapper.toCompanyMap(createdCompany);

            return new ResponseEntity<>(ApiResponse.success(HttpStatus.CREATED.value(), "Company created successfully", mapper), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            if ("User not found".equals(e.getMessage())) {
                return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Company creation failed", "User not found"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Company creation failed", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/getAllCompanys")
    public ResponseEntity<ApiResponse<?>> getCompanyAll(@RequestParam String userId) {
        try {
            List<Company> getCompany = companyService.getAllCompanys(userId);
            List<Company> companies = new ArrayList<>();
            for(Company company : getCompany){
                if (company.getUserId().equals(userId)) {
                    companies.add(company);
                }
            }
            return new ResponseEntity<>(ApiResponse.success(HttpStatus.OK.value(), "Company found", companies), HttpStatus.OK);

        } catch (Exception e) {
            if ("User not found".equals(e.getMessage())) {
                return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Company Not Found", "User not found"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Company Not Found", e.getMessage()), HttpStatus.BAD_REQUEST);
        }


    }



    @GetMapping("/getCompany")
    public ResponseEntity<ApiResponse<?>> getCompany(@RequestParam String userId, @RequestParam String companyId) {
        try {
            List<Company> getCompany = companyService.getAllCompanys(userId);
            Company company = new Company();
            for(Company companyObj : getCompany){
                if (companyObj.getCompanyId().equals(companyId)) {
                    company =  companyObj;
                }
            }
            return new ResponseEntity<>(ApiResponse.success(HttpStatus.OK.value(), "Company found", company), HttpStatus.OK);

        } catch (Exception e) {
            if ("User not found".equals(e.getMessage())) {
                return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Company Not Found", e.getMessage()), HttpStatus.NOT_FOUND);
            }

        }

        return new ResponseEntity<>(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Company Not Found", null), HttpStatus.BAD_REQUEST);
    }


}
