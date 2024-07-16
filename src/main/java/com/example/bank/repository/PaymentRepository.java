package com.example.bank.repository;

import com.example.bank.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Repository interface for managing Payment entities.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    /**
     * Finds all payments made between the specified start and end dates.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return a list of payments
     */
    List<Payment> findAllByTransactionDateBetween(Date startDate, Date endDate);
}
