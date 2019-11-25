package com.example.Gerrymender.web;


import com.example.Gerrymender.Abstractions.Algorithm;
import com.example.Gerrymender.Abstractions.BaseState;
import com.example.Gerrymender.Abstractions.VotingBloc;
import com.example.Gerrymender.Abstractions.VotingBlocInfo;
import com.example.Gerrymender.exception.ResourceNotFoundException;
import com.example.Gerrymender.model.*;
import com.example.Gerrymender.repository.DistrictRepository;
import com.example.Gerrymender.repository.PrecinctRepository;
import com.example.Gerrymender.repository.StateRepository;
import com.example.Gerrymender.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Controller
public class MainController {
    @Autowired
    StateRepository stateRepository;
    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    PrecinctRepository precinctRepository;
    @Autowired
    VoteRepository voteRepository;

    Algorithm alg;

    @RequestMapping("/")
    public String welcome(){
        return "Homepage";
    }

    @RequestMapping(value="/getSelectArea",method = RequestMethod.POST)
    public @ResponseBody List<String> updateSmallInfoWindow(HttpServletRequest request, String id, String mapLevel, int year, String electionType) {
        List<String> res = new ArrayList<>();
        res.add("3");

//        switch(mapLevel){
//            case "district":
////                District founddis = districtRepository.findById(id)
////                        .orElseThrow(() -> new ResourceNotFoundException("district", "id", id));
//                District founddis = new District();
//
//                founddis.setTotalPop(100);
//                founddis.setParty("OTHER");
//                founddis.setAfricanAmerican_pop(100);
//                founddis.setAsian_pop(100);
//                founddis.setHispanic_pop(100);
//                founddis.setWhite_pop(100);
//                founddis.setNameID("DISTRICT" + 12);
//                founddis.setNativeAmerican_pop(100);
//                setDistrictInfo(res, founddis);
//                break;
//            default:
////                Precinct foundpre = precinctRepository.findById(id)
////                        .orElseThrow(() -> new ResourceNotFoundException("district", "id", id));
//                Precinct foundpre = new Precinct();
//                foundpre.setTotalPop(100);
//                foundpre.setParty("OTHER");
//                foundpre.setAfricanAmerican_pop(100);
//                foundpre.setAsian_pop(100);
//                foundpre.setHispanic_pop(100);
//                foundpre.setWhite_pop(100);
//                foundpre.setNameID("PRECINCT" + 10);
//                foundpre.setNativeAmerican_pop(100);
//                setPrecinctInfo(res, foundpre);
//        }
//        String voteid = id + String.valueOf(year) + electionType;
//        Vote v = voteRepository.findById(voteid)
//                .orElseThrow(() -> new ResourceNotFoundException("vote", "record", id));
//        res.add(String.valueOf(v.getTotalvote()));
//        res.add(String.valueOf(v.getDemvote()));
//        res.add(String.valueOf(v.getRepvote()));
//        res.add(v.getWinner());
        return res;
    }

    public void setDistrictInfo(List<String> res, District d){
        res.add(d.getNameID());
        res.add(String.valueOf(d.getTotalPop()));
        res.add(d.getParty());
        res.add(String.valueOf(d.getWhite_pop()));
        res.add(String.valueOf(d.getAfricanAmerican_pop()));
        res.add(String.valueOf(d.getHispanic_pop()));
        res.add(String.valueOf(d.getAsian_pop()));
        res.add(String.valueOf(d.getNativeAmerican_pop()));

    }

    public void setPrecinctInfo(List<String> res, Precinct p){
        res.add(p.getNameID());
        res.add(String.valueOf(p.getTotalPop()));
        res.add(p.getParty());
        res.add(String.valueOf(p.getWhite_pop()));
        res.add(String.valueOf(p.getAfricanAmerican_pop()));
        res.add(String.valueOf(p.getHispanic_pop()));
        res.add(String.valueOf(p.getAsian_pop()));
        res.add(String.valueOf(p.getNativeAmerican_pop()));

    }

    @ResponseBody
    @RequestMapping("/Test")
    public List<State> stateList(){ return stateRepository.findAll(); }

    @ResponseBody
    @RequestMapping(value="/phase0",method = RequestMethod.POST)
    public List<VotingBlocInfo> loadAlg(HttpServletRequest request, String id, double popThreshold, double voteThreshold) {
        return alg.phase0(popThreshold, voteThreshold);
    }

    @ResponseBody
    @RequestMapping(value="/updateState", method=RequestMethod.GET)
    public void updateState(HttpServletRequest req, String id) {
        State s = stateRepository.findById(id).orElse(null);
        alg = new Algorithm(new BaseState(s));
    }


}
