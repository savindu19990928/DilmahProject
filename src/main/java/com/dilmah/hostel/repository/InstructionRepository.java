package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.dilmah.hostel.model.Instruction;

@RepositoryRestResource(path = "instruction")
@PreAuthorize("hasAnyRole('WARDEN', 'HOSTELLER', 'SUPERADMIN')")
public interface InstructionRepository extends JpaRepository<Instruction, Integer> {

}
