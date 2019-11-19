package com.example.Gerrymender.repository;

import com.example.Gerrymender.model.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface ClusterRepository extends JpaRepository<Cluster, String> {

}