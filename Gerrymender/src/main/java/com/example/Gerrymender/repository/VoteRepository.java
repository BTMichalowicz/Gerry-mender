package com.example.Gerrymender.repository;

import com.example.Gerrymender.model.District;
import com.example.Gerrymender.model.Vote;
import com.example.Gerrymender.model.VoteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface VoteRepository extends JpaRepository<Vote, String> {
    Vote findByVoteid(VoteId voteId);
}