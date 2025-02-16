package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.dilmah.hostel.model.BorrowedItem;


@RepositoryRestResource(path = "borroweditem")
@PreAuthorize("hasRole('SUPERADMIN')")
public interface BorrowedItemRepository extends JpaRepository<BorrowedItem, Integer> {

}
