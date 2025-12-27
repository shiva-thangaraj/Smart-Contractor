package com.smartcontractor.model.mappermodel;

import com.smartcontractor.model.Employee;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.List;

public class CompanyMap {

    @Id
    private String companyId;
    private String companyName;
    private String companyDisc;
    private String userId; // Foreign key to User

    public String getCompanyDisc() {
        return companyDisc;
    }

    public void setCompanyDisc(String companyDisc) {
        this.companyDisc = companyDisc;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
