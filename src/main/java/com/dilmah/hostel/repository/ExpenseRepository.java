package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dilmah.hostel.model.Expense;

@RepositoryRestResource(exported = false)
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}
