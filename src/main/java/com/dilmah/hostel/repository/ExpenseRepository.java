package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dilmah.hostel.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}
