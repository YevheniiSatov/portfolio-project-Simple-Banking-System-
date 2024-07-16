package com.example.bank.repository;

import com.example.bank.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for managing Purchase entities.
 */
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    /**
     * Finds all purchases made between the specified start and end dates.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return a list of purchases
     */
    List<Purchase> findAllByTransactionDateBetween(LocalDate startDate, LocalDate endDate);
}
