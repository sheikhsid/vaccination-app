package com.example.vaccination.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.vaccination.entities.PtEntities;

@Repository
public interface PtDao extends JpaRepository<PtEntities, Long>{
}
