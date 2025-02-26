package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.dilmah.hostel.model.Expense;

@RepositoryRestResource(path = "expense")
@PreAuthorize("hasAnyRole('WARDEN', 'SUPERADMIN')")
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}
