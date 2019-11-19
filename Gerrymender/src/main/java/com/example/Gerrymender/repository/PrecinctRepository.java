package com.example.Gerrymender.repository;

import com.example.Gerrymender.model.District;
import com.example.Gerrymender.model.Precinct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface PrecinctRepository extends JpaRepository<Precinct, String> {

}