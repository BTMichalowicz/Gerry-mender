package com.example.Gerrymender.repository;

import com.example.Gerrymender.model.VoteDis;
import com.example.Gerrymender.model.VoteDisId;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
@EnableJpaRepositories
public interface VoteDisRepository extends JpaRepository<VoteDis, String>{
    VoteDis findByVoteid(VoteDisId voteid);
}
