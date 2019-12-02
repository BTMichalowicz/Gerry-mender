package com.example.Gerrymender.repository;

import com.example.Gerrymender.model.Precinct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface PrecinctRepository extends JpaRepository<Precinct, String> {
    List<Precinct> findByStatename(String statename);
}