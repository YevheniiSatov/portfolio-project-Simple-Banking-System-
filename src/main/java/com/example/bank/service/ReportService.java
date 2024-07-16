package com.example.bank.service;

import com.example.bank.model.Payment;
import com.example.bank.model.Purchase;
import com.example.bank.repository.PaymentRepository;
import com.example.bank.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ReportService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    private static final Logger logger = Logger.getLogger(ReportService.class.getName());

    public String generateReport(LocalDate startDate, LocalDate endDate) {
        StringBuilder report = new StringBuilder();

        // Извлечение платежей за указанный период
        logger.info("Fetching payments between " + startDate + " and " + endDate);
        List<Payment> payments = paymentRepository.findAllByTransactionDateBetween(java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate));
        logger.info("Payments fetched: " + payments.size());
        if (payments.isEmpty()) {
            report.append("No payments found for the specified period.\n");
        } else {
            report.append("Payments Report\n");
            for (Payment payment : payments) {
                report.append("Payment ID: ").append(payment.getId())
                        .append(", Account ID: ").append(payment.getAccount().getId())
                        .append(", Date: ").append(payment.getTransactionDate())
                        .append(", Amount: ").append(payment.getAmount())
                        .append(", Type: ").append(payment.getPaymentType())
                        .append("\n");
            }
        }

        // Извлечение покупок за указанный период
        logger.info("Fetching purchases between " + startDate + " and " + endDate);
        List<Purchase> purchases = purchaseRepository.findAllByTransactionDateBetween(startDate, endDate);
        logger.info("Purchases fetched: " + purchases.size());
        if (purchases.isEmpty()) {
            report.append("No purchases found for the specified period.\n");
        } else {
            report.append("\nPurchases Report\n");
            for (Purchase purchase : purchases) {
                report.append("Purchase ID: ").append(purchase.getId())
                        .append(", Account ID: ").append(purchase.getAccount().getId())
                        .append(", Date: ").append(purchase.getTransactionDate())
                        .append(", Amount: ").append(purchase.getAmount())
                        .append("\n");
            }
        }

        return report.toString();
    }
}
