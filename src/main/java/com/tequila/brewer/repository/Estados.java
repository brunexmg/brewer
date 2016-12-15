package com.tequila.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tequila.brewer.model.Estado;

@Repository
public interface Estados extends JpaRepository<Estado, Long> {

}
