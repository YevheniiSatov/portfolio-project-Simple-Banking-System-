package com.example.bank.repository;

import com.example.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Account entities.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
