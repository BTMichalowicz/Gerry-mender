package com.example.Gerrymender.web;


import com.example.Gerrymender.Abstractions.*;
import com.example.Gerrymender.exception.ResourceNotFoundException;
import com.example.Gerrymender.model.*;
import com.example.Gerrymender.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple4;
import reactor.util.function.Tuple5;
import reactor.util.function.Tuples;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


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

    MyAlgorithm alg;
    Map<String, BaseState> baseStateMap = new HashMap<>();
    @RequestMapping("/")
    public String welcome(){
        return "Homepage";
    }

    @RequestMapping(value="/getSelectArea",method = RequestMethod.POST)
    public @ResponseBody String updateSmallInfoWindow(String stateName, String id, String mapLevel, String year, String electionType) {
        ObjectMapper obj = new ObjectMapper();
        List res = new ArrayList();
        switch(mapLevel){
            case "district":
                District founddis = districtRepository.findByDistrictidAndStatename(id, stateName);
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
            return r;
        } catch (IOException e) {
            return "";
        }


    }

    @ResponseBody
    @RequestMapping("/Test")
    public String stateList(){
        ObjectMapper obj = new ObjectMapper();
        try {
            String ret = obj.writeValueAsString(alg.getBaseState());
            return ret;
        } catch (IOException e) {
            return "";
        }
    }


    @RequestMapping(value="/phase0",method = RequestMethod.POST)
    public @ResponseBody String phase0(String electionYear, String electionType, double popThreshold, double voteThreshold) {
        alg.lock.lock();
        List<VotingBlocInfo> r = alg.phase0(popThreshold / 100.0, voteThreshold / 100.0, electionYear + electionType);
        alg.lock.unlock();
        ObjectMapper obj = new ObjectMapper();
        try {
            String ret = obj.writeValueAsString(r);
            return ret;
        } catch (IOException e) {
            return "";
        }
    }

    @RequestMapping(value = "/phase1", method = RequestMethod.POST)
    public @ResponseBody String phase1(String[] whichRaces, double minPopPerc, double maxPopPerc, int numDistricts) {
        alg.lock.lock();
        String ret = "";
        ObjectMapper obj = new ObjectMapper();
        if(alg.isRunning()) {
            try {
                alg.getPhase1Semaphore().acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<Tuple2<String, String>> r = null;
            if(!alg.getPhase1Queue().isEmpty()) {
                r = alg.getPhase1Queue().remove();
                if(r == null) {
                    alg.setIsRunning(false);
                }
            }
            try {
                    ret = obj.writeValueAsString(ret);
                alg.lock.unlock();
                return ret;
            } catch (IOException e) {
                alg.lock.unlock();
                return "error";
            }
        }
        else{
            Race[] races = new Race[whichRaces.length];
            for(int i = 0; i < whichRaces.length; i++) {
                    races[i] = (Race.valueOf(whichRaces[i]));
            }
            Runnable run = () -> {
                alg.phase1(races, minPopPerc / 100.0, maxPopPerc / 100.0, numDistricts);
            };
            new Thread(run).start();
        }
        try {
            ret = obj.writeValueAsString(ret);
            alg.lock.unlock();
            return ret;
        } catch (IOException e) {
            alg.lock.unlock();
            return "error writing to string";
        }
    }

    @RequestMapping(value = "/phase2", method = RequestMethod.POST)
    public @ResponseBody String phase2(double[] weights, int numIters)  {
        Map<Measure, Double> measureMap = new HashMap<>();
        for(int i = 0; i < weights.length; i++) {
            measureMap.put(Measure.values()[i], weights[i]);
        }
        ObjectMapper obj = new ObjectMapper();
        String ret = "";
        alg.lock.lock();
        if(alg.isRunning()) {
            try {
                alg.getPhase2Semaphore().acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Tuple2<String, String> r = null;
            if(!alg.getPhase2Queue().isEmpty()) {
                r = alg.getPhase2Queue().remove();
            }
            try {
                if(r != null) {
                    ret = obj.writeValueAsString(r);
                }
                else {
                    ret = obj.writeValueAsString(ret);
                }
                alg.lock.unlock();
                System.out.println(ret);
                return ret;
            } catch (IOException e) {
                alg.lock.unlock();
                return "";
            }
        }
        else {
            alg.setDistrictScoreFunction(DefaultMeasures.defaultMeasuresWithWeights(measureMap));
            Runnable run = () -> { alg.phase2(numIters);};
            new Thread(run).start();
        }
        return null;
    }

    @RequestMapping(value = "/resetAlg", method = RequestMethod.GET)
    public @ResponseBody void resetAlg() {
        BaseState s = alg.getBaseState();
        alg = new MyAlgorithm();
        alg.setBaseState(s);
    }

    @RequestMapping(value = "/getCluster", method = RequestMethod.POST)
    public @ResponseBody String getCluster(String id, String electionId, String whichRaces[]) {
        BaseState s = alg.getBaseState();
        BaseCluster c = s.getClusters().get(id);
        Votes v = c.getVotes().get(electionId);
        int max = 0;
        Pol_part maxParty = Pol_part.DEMOCRAT;
        for(int i = 0; i < v.getVotes().length; i++) {
            if(v.getVotes()[i] > max) {
                max = (int)v.getVotes()[i];
                maxParty = Pol_part.values()[i];
            }
        }
        int maxRacePop = 0;
        Race maxRace = Race.WHITE;
        int races[] = c.getRacePops();
        for(int i = 0; i < whichRaces.length; i++) {
            if(races[Race.valueOf(whichRaces[i]).ordinal()] > maxRacePop) {
                maxRacePop = races[Race.valueOf(whichRaces[i]).ordinal()];
                maxRace = Race.valueOf(whichRaces[i]);
            }
        }
        boolean majMin = false;
        if((double)maxRacePop / (double)c.getPopulation() > 0.5 && maxRace != Race.WHITE) {
            majMin = true;
        }
        Tuple4<String, Integer, String, Boolean> r = Tuples.of(id, c.getPopulation(), maxParty.name(), majMin);
        ObjectMapper obj = new ObjectMapper();
        try {
            String ret = obj.writeValueAsString(r);
            return ret;
        } catch (IOException e) {
            return "";
        }
    }

    @RequestMapping(value="/updateState", method=RequestMethod.POST)
    public @ResponseBody void updateState(String id) {
        if(baseStateMap.containsKey(id)) {
            alg.lock.lock();
            alg.setBaseState(baseStateMap.get(id));
            alg.lock.unlock();
            return;
        }
        if(alg == null) {
            alg = new MyAlgorithm();
        }
        alg.lock.lock();
        State s = stateRepository.findById(id).orElse(null);
        List<Precinct> precincts = precinctRepository.findByStatename(s.getNameID());
        Map<String, BasePrecinct> basePrecincts = new ConcurrentHashMap<>();
        for(Precinct p : precincts) {
            Map<String, Votes> votes = new HashMap<>();
            votes.put("2016PRESIDENT", new Votes(voteRepository.findByVoteid(new VoteId(p.getNameID(), "2016", "PRESIDENT"))));
            votes.put("2016CONGRESSIONAL", new Votes(voteRepository.findByVoteid(new VoteId(p.getNameID(), "2016", "CONGRESSIONAL"))));
            votes.put("2018CONGRESSIONAL", new Votes(voteRepository.findByVoteid(new VoteId(p.getNameID(), "2018", "CONGRESSIONAL"))));
            basePrecincts.put(p.getNameID(), new BasePrecinct(p, votes));
        }
        for(String key : basePrecincts.keySet()) {
            BasePrecinct basePrecinct = basePrecincts.get(key);
            for(String neighborId : basePrecinct.getNeighborIDs()) {
                if(basePrecincts.get(neighborId) != null) {
                    basePrecinct.getEdges().add(basePrecincts.get(neighborId));
                }
            }
        }
        BaseState baseState = new BaseState(s);
        baseState.setPrecincts(basePrecincts);
        alg.setBaseState(baseState);
        baseStateMap.put(id, baseState);
        alg.lock.unlock();
        System.out.println("State " + id + " has been updated.");
    }
}
