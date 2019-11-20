package com.example.Gerrymender.repository;

import com.example.Gerrymender.model.State;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
@EnableJpaRepositories
public interface StateRepository extends JpaRepository<State, String> {}