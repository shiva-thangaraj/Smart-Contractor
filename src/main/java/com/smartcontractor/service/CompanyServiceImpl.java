package com.smartcontractor.service;

import com.smartcontractor.model.Company;
import com.smartcontractor.repository.CompanyRepository;
import com.smartcontractor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Company createCompany(String userId, Company company) {
        // Validation: Check if user exists
        if (!userRepository.existsById(userId)) {
             throw new RuntimeException("User not found");
        }

        // Generate Company ID if not present
        if (company.getCompanyId() == null || company.getCompanyId().isEmpty()) {
            company.setCompanyId(generateCompanyId());
        }

        // Set User ID mapping
        company.setUserId(userId);

        return companyRepository.save(company);
    }

    @Override
    public List<Company> getAllCompanys(String userId) {

        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        return companyRepository.findAll();
    }

    private String generateCompanyId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        java.security.SecureRandom random = new java.security.SecureRandom();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
