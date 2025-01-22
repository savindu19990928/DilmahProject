package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dilmah.hostel.model.Instruction;

public interface InstructionRepository extends JpaRepository<Instruction, Integer> {

}
