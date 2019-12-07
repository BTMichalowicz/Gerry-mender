package com.example.Gerrymender.web;


import com.example.Gerrymender.Abstractions.Algorithm;
import com.example.Gerrymender.Abstractions.BasePrecinct;
import com.example.Gerrymender.Abstractions.BaseState;
import com.example.Gerrymender.Abstractions.VotingBlocInfo;
import com.example.Gerrymender.exception.ResourceNotFoundException;
import com.example.Gerrymender.model.*;
import com.example.Gerrymender.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;


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
    @Autowired
    VoteDisRepository voteDisRepository;

    Algorithm alg;

    @RequestMapping("/")
    public String welcome(){
        return "Homepage";
    }

    @RequestMapping(value="/getSelectArea",method = RequestMethod.POST)
    public @ResponseBody String updateSmallInfoWindow(HttpServletRequest req, String id, String mapLevel, String year, String electionType) {
        ObjectMapper obj = new ObjectMapper();
        List res = new ArrayList();
        switch(mapLevel){
            case "district":
//                District founddis = districtRepository.findById(id)
//                        .orElseThrow(() -> new ResourceNotFoundException("district", "id", id));
                District founddis = new District();

                founddis.setTotalPop(100);
                founddis.setParty("OTHER");
                founddis.setAfricanAmerican_pop(100);
                founddis.setAsian_pop(100);
                founddis.setHispanic_pop(100);
                founddis.setWhite_pop(100);
                founddis.setNameID(id);
                founddis.setNativeAmerican_pop(100);
                founddis.setStateName("FLORIDA");
                res.add(founddis);
                VoteDis dv = voteDisRepository.findByVoteid(new VoteDisId(id, founddis.getStateName(), "" + year, electionType));
                res.add(dv);
                break;
            default:
                Precinct foundpre = precinctRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("precinct", "id", id));
                res.add(foundpre);
                Vote v = voteRepository.findByVoteid(new VoteId(foundpre.getNameID(), "" + year, electionType));
                res.add(v);
        }
        try {
            String r = obj.writeValueAsString(res);
            System.out.println(r);
            return r;
        } catch (IOException e) {
            return "";
        }


    }

    @ResponseBody
    @RequestMapping("/Test")
    public List<State> stateList(){ return stateRepository.findAll(); }

    @ResponseBody
    @RequestMapping(value="/phase0",method = RequestMethod.POST)
    public String loadAlg(HttpServletRequest request, String id, double popThreshold, double voteThreshold) {
        List<VotingBlocInfo> r = alg.phase0(popThreshold, voteThreshold);
        ObjectMapper obj = new ObjectMapper();
        try {
            String ret = obj.writeValueAsString(r);
            System.out.println(ret);
            return ret;
        } catch (IOException e) {
            return "";
        }
    }

    @ResponseBody
    @RequestMapping(value="/updateState", method=RequestMethod.GET)
    public void updateState(HttpServletRequest req, String id) {
        State s = stateRepository.findById(id).orElse(null);
        List<Precinct> precincts = precinctRepository.findByStatename(s.getNameID());
        Map<String, BasePrecinct> basePrecincts = new HashMap<>();
        for(Precinct p : precincts) {
            basePrecincts.put(p.getNameID(), new BasePrecinct(p));
        }
        for(String key : basePrecincts.keySet()) {
            BasePrecinct basePrecinct = basePrecincts.get(key);
            for(String neighborId : basePrecinct.getNeighborIDs()) {
                basePrecinct.getEdges().add(basePrecincts.get(neighborId));
            }
        }
        BaseState baseState = new BaseState(s);
        baseState.setPrecincts(basePrecincts);
        alg = new Algorithm(new BaseState(s));
    }


}
