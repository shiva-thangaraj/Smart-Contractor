package com.smartcontractor.model.mappermodel;

public class PaymentDetailsMap {

    private Double salary;
    private String currency;
    private String paymentCycle;
    private String bankAccount;
    private Double halfDaySalary;

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentCycle() {
        return paymentCycle;
    }

    public void setPaymentCycle(String paymentCycle) {
        this.paymentCycle = paymentCycle;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Double getHalfDaySalary() {
        return halfDaySalary;
    }

    public void setHalfDaySalary(Double halfDaySalary) {
        this.halfDaySalary = halfDaySalary;
    }
}
