package com.dilmah.hostel.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.dilmah.hostel.model.FoodMenu;

@RepositoryRestResource(path = "foodmenu")
@PreAuthorize("hasRole('WARDEN')")
public interface FoodMenuRepository extends JpaRepository<FoodMenu, Long> {

    @Override
    @RestResource(exported = true)
    <S extends FoodMenu> S save(S entity);

    @Override
    @RestResource(exported = true)
    Optional<FoodMenu> findById(Long id);

    @Override
    @RestResource(exported = true)
    List<FoodMenu> findAll();

    @Override
    @RestResource(exported = false)
    void deleteById(Long id);

    @Override
    @RestResource(exported = false)
    void delete(FoodMenu entity);
    
    @RestResource(exported = true)
    FoodMenu findTopByMenuDateLessThanEqualOrderByMenuDateDesc(LocalDate menuDate);
}

