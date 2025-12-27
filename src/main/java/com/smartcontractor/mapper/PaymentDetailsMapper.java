package com.smartcontractor.mapper;

import com.smartcontractor.model.PaymentDetails;
import com.smartcontractor.model.mappermodel.PaymentDetailsMap;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentDetailsMapper {

    public PaymentDetailsMapper() {
    }

    public static PaymentDetailsMap toPaymentDetailsMap(PaymentDetails paymentDetails) {
        if (paymentDetails == null) return null;

        PaymentDetailsMap res = new PaymentDetailsMap();
        res.setSalary(paymentDetails.getSalary());
        res.setCurrency(paymentDetails.getCurrency());
        res.setPaymentCycle(paymentDetails.getPaymentCycle());
        res.setBankAccount(paymentDetails.getBankAccount());
        res.setHalfDaySalary(paymentDetails.getHalfDaySalary());

        return res;
    }

    public static List<PaymentDetailsMap> toPaymentDetailsList(List<PaymentDetails> paymentDetailsList) {

        if (paymentDetailsList == null) return List.of();

        return paymentDetailsList.stream()
                .map(PaymentDetailsMapper::toPaymentDetailsMap)
                .collect(Collectors.toList());
    }

}
