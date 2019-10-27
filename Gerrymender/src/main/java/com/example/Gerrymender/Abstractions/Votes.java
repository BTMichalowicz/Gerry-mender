package com.example.Gerrymender.Abstractions;


import com.example.Gerrymender.db_elements.Pol_part;

import java.util.List;

/**
 * The method of holding votes for any set of elections
 * 2016 Presidential
 * 2018 General/Congressional
 * 2016 General/Congressional
 */
public class Votes {
    private int year;

    private boolean presGen; //Boolean to determine if we are using
    public Pol_part party;
    public List<Votes> totalVotes;
}
