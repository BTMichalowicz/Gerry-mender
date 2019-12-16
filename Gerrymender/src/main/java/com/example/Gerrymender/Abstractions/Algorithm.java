package com.example.Gerrymender.Abstractions;


import com.example.Gerrymender.Abstractions.AbstrInterface.DistrictInterface;
import com.example.Gerrymender.Abstractions.AbstrInterface.MeasureFunction;
import com.example.Gerrymender.model.Pol_part;
import com.example.Gerrymender.model.Precinct;
import com.example.Gerrymender.model.Race;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.apache.catalina.Cluster;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;
import java.util.Comparator;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Algorithm {
    public int objectiveValue;
    public int numIterations;
    public ReentrantLock lock;
    private boolean isRunning;
    private Queue<List<Tuple2<String, String>>> phase1Queue;
    private Semaphore phase1Semaphore;
    private Queue<Tuple2<String, String>> phase2Queue;
    private Semaphore phase2Semaphore;

    public Algorithm(BaseState s) {
        BaseState = s;
        lock = new ReentrantLock();
        isRunning = false;
        phase1Semaphore = new Semaphore(0);
        phase2Semaphore = new Semaphore(0);
    }

    public Algorithm() {
        lock = new ReentrantLock();
        isRunning = false;
        phase1Semaphore = new Semaphore(0);
        phase2Semaphore = new Semaphore(0);
    }

    public void setBaseState(BaseState s) {
        this.BaseState = s;
    }

    public BaseState getBaseState() {
        return BaseState;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public Queue<List<Tuple2<String, String>>> getPhase1Queue() {
        return phase1Queue;
    }

    public Semaphore getPhase1Semaphore() {
        return phase1Semaphore;
    }

    public Queue<Tuple2<String, String>> getPhase2Queue() {
        return phase2Queue;
    }

    public Semaphore getPhase2Semaphore() {
        return phase2Semaphore;
    }

    private void combine(BaseCluster c1, BaseCluster c2) {
        c1.combine(c2);
        Object edges[] = c2.getEdges().toArray();
        for (int i = 0; i < edges.length; i++) {
            BaseCluster c = (BaseCluster) edges[i];
            Set<BaseCluster> edge = c.getEdges();
            edge.remove(c2);
            edge.add(c1);
        }
        if (!BaseState.getClusters().containsKey(c2.getID())) {
            c1.getEdges().remove(c2.getID());
            return;
        }
        BaseState.getClusters().remove(c2.getID());
    }

    /*
        Should be called before calling phase1 to ensure the clusters have been created
     */
    public void initializeClusters() {
        HashMap<String, BaseCluster> clusters = new HashMap<>();
        int count = 0;
        for (BasePrecinct p : BaseState.getPrecincts().values()) {
            BaseCluster c = new BaseCluster("" + count, BaseState, p.getPopulation(), p.getRacePops(), p.getCountyName(), p.getVotes());
            p.setClusterId(count);
            c.addPrecinct(p);
            clusters.put("" + count, c);
            count++;
        }
        for (Map.Entry<String, BaseCluster> clust : clusters.entrySet()) {
            BaseCluster c = clust.getValue();
            Map<String, BasePrecinct> prec = c.getPrecincts();
            for (Map.Entry<String, BasePrecinct> p : prec.entrySet()) {
                for (BasePrecinct BasePrecinct : p.getValue().getEdges()) {
                    c.addEdge(clusters.get("" + BasePrecinct.getClusterId()));
                }
            }
        }
        BaseState.setClusters(clusters);
    }

    public void phase2(int numIterations, Race[] races, double minPopPerc, double maxPopPerc) {
        Map<String, BaseDistrict> baseDistricts = BaseState.getDistricts();
        lock.lock();
        phase2Queue = new LinkedList<>();
        isRunning = true;
        lock.unlock();
        int numDistricts = baseDistricts.size();
        for (int i = 0; i < numIterations; i++) {
            BaseDistrict district = baseDistricts.get("" + (i % numDistricts + 1));
            Move m = getMoveFromDistrict(district, races, minPopPerc, maxPopPerc);
            if (m != null) {
                phase2Queue.add(Tuples.of(m.getPrecinct().getID(), m.getTo().getID()));
                phase2Semaphore.release();
            }
        }
        phase2Queue.add(Tuples.of("END", "END"));
        phase2Semaphore.release();
        lock.lock();
        isRunning = false;
        lock.unlock();
    }

    public void phase1(Race[] races, double minPopPerc, double maxPopPerc, int numDistricts) {
        lock.lock();
        isRunning = true;
        phase1Queue = new LinkedList<>();
        lock.unlock();
        initializeClusters();

        int avgPop = BaseState.getPopulation() / numDistricts;
        double avgPopEpsilon = avgPop * .35;
        boolean lastIter = false;
        int i = 0;
        while (BaseState.getClusters().size() > numDistricts) {
            Map<String, BaseCluster> clusters = BaseState.getClusters();
            if (clusters.size() <= 2 * numDistricts) {
                lastIter = true;
            }

            String key = (String) clusters.keySet().toArray()[i % clusters.size()];
            BaseCluster bestNeighbor = null;
            Tuple2<Double, Double> bestJoinability = Tuples.of(0.0, 0.0);
            BaseCluster c = clusters.get(key);
            for (BaseCluster neighbor : c.getEdges()) {
                Tuple2<Double, Double> join = c.joinability(neighbor, minPopPerc, maxPopPerc, avgPop, avgPopEpsilon, races, lastIter, BaseState);
                if (BaseCluster.maxJoinability(bestJoinability, join, lastIter)) {
                    bestJoinability = join;
                    bestNeighbor = neighbor;
                }
            }
            if (bestNeighbor == null) {
                i++;
                continue;
            }

            List<Tuple2<String, String>> changes = new ArrayList<>();

            for (BasePrecinct p : bestNeighbor.getPrecincts().values()) {
                changes.add(Tuples.of(p.getID(), c.getID()));
            }
            phase1Queue.add(changes);
            phase1Semaphore.release();
            combine(clusters.get(key), bestNeighbor);
            i++;
        }
        Map<String, BaseDistrict> baseDistricts = new HashMap<>();
        int id = 1;
        Map<String, BaseCluster> clusters = BaseState.getClusters();
        for (String clusterKey : clusters.keySet()) {
            BaseDistrict district = new BaseDistrict("" + id, BaseState);
            for (String precintKey : clusters.get(clusterKey).getPrecincts().keySet()) {
                district.addPrecinct(clusters.get(clusterKey).getPrecincts().get(precintKey));
            }
            baseDistricts.put("" + id, district);
            id++;
        }

        System.out.println("Sent END!");
        phase1Queue.add(null);
        BaseState.setDistricts(baseDistricts);
        phase1Semaphore.release();
    }

    public List<VotingBlocInfo> phase0(double popThreshold, double voteThreshold, String election) {
        if (BaseState == null) {
            return null;
        }

        List<VotingBlocInfo> ret = new ArrayList<>();
        for (String key : BaseState.getPrecincts().keySet()) {
            BasePrecinct p = BaseState.getPrecinct(key);
            Race r = p.getMajorityRace();
            double perc = (double) p.getMajorityRacePop() / (double) p.getPopulation();
            if (perc >= popThreshold) {
                Votes v = p.getVotes().get(election);
                double votePerc = (double) v.getVotes()[v.getParty().ordinal()] / (double) v.getTotalVotes();
                if (votePerc >= voteThreshold) {
                    p.setBloc(new VotingBloc(r, (int) v.getVotes()[v.getParty().ordinal()], v.getParty()));
                    ret.add(new VotingBlocInfo(p.getID(), v.getParty(), (int) v.getTotalVotes(), (int) v.getVotes()[v.getParty().ordinal()], p.getMajorityRace()));
                }
            }
        }
        return ret;
    }


    private BaseState BaseState;
    private HashMap<String, String> precinctDistrictMap; //precinctID --> districtID

    //calculates an aggregate measure score (double) for a given BaseDistrict
    private MeasureFunction<BasePrecinct, BaseDistrict> districtScoreFunction;
    private HashMap<BaseDistrict, Double> currentScores;
    private BaseDistrict currentDistrict = null;


    public MeasureFunction<BasePrecinct, BaseDistrict> getDistrictScoreFunction() {
        return districtScoreFunction;
    }

    public void setDistrictScoreFunction(MeasureFunction<BasePrecinct, BaseDistrict> districtScoreFunction) {
        this.districtScoreFunction = districtScoreFunction;
    }

    public Algorithm(BaseState BaseState,
                     MeasureFunction<BasePrecinct, BaseDistrict> districtScoreFunction) {
        this.BaseState = BaseState;
        this.precinctDistrictMap = new HashMap<String, String>();
        this.districtScoreFunction = districtScoreFunction;
        allocatePrecincts(BaseState);
        updateScores();
    }

    // Determine the initial districts of precincts using a bfs
    // Very fast, but not ideal for compactness
    // Experimental - use a modified breadth first search
    public void allocatePrecinctsExp(BaseState BaseState) {
        HashSet<BasePrecinct> unallocatedPrecincts = new HashSet<BasePrecinct>();
        // Populate unallocated precincts
        for (BaseDistrict d : BaseState.getDistricts().values()) {
            for (BasePrecinct p : d.getPrecincts()) {
                unallocatedPrecincts.add(p);
            }
        }
        // Hash districts to numbers
        HashMap<Integer, DistrictInterface> myDistricts = new HashMap<>();
        int num_districts = 0;
        for (BaseDistrict d : BaseState.getDistricts().values()) {
            myDistricts.put(num_districts, d);
            num_districts += 1;
        }
        // Strip out all precincts from each BaseDistrict
        for (BaseDistrict d : BaseState.getDistricts().values()) {
            for (BasePrecinct p : d.getPrecincts()) {
                d.removePrecinct(p);
            }
        }
        // Go through precincts in a BFS and just add them to the BaseDistrict corresponding to
        // (total_population / current_population) * num_districts
        // Whenever we run out, just grab a new BaseDistrict from the set.
        ArrayList<BasePrecinct> ptp = new ArrayList<BasePrecinct>();
        int curPop = 0;
        DistrictInterface prevDist = myDistricts.get(0);
        while (!unallocatedPrecincts.isEmpty()) {
            BasePrecinct nextPrec = unallocatedPrecincts.iterator().next();
            unallocatedPrecincts.remove(nextPrec);
            ptp.add(nextPrec);
            while (!ptp.isEmpty()) {
                BasePrecinct curPrec = ptp.get(0);
                curPop += curPrec.getPopulation();
                DistrictInterface curDist =
                        myDistricts.get((int) Math.ceil((curPop * 1.0) / BaseState.getPopulation() * num_districts) - 1);
                // Add the current BasePrecinct to the current BaseDistrict
                System.out.println(ptp.size() + " " + unallocatedPrecincts.size());
                precinctDistrictMap.put(curPrec.getID(), curDist.getID());

                ptp.remove(0);
                unallocatedPrecincts.remove(curPrec);
                curDist.addPrecinct(curPrec);
                // If we've moved on to a new BaseDistrict, discard our current queue
                if (prevDist != curDist) {
                    ptp = new ArrayList<BasePrecinct>();
                    prevDist = curDist;
                }
                // Add any new connections
                for (String s : curPrec.getNeighborIDs()) {
                    BasePrecinct n = BaseState.getPrecinct(s);
                    if (unallocatedPrecincts.contains(n) && !ptp.contains(n)) {
                        ptp.add(n);
                    }
                }
            }
        }
    }

    // Determine the initial districts of precincts
    public void allocatePrecincts(BaseState BaseState) {
        HashSet<BasePrecinct> unallocatedPrecincts = new HashSet<BasePrecinct>();
        // Populate unallocated precincts
        for (BaseDistrict d : BaseState.getDistricts().values()) {
            unallocatedPrecincts.addAll(d.getPrecincts());
        }
        // For each BaseDistrict, select a random BasePrecinct
        HashSet<BasePrecinct> seedPrecincts = new HashSet<BasePrecinct>();
        for (BaseDistrict d : BaseState.getDistricts().values()) {
            int selectedIndex = (int) Math.floor(Math.random() * unallocatedPrecincts.size());
            BasePrecinct selectedPrecinct = null;
            int i = 0;
            for (BasePrecinct p : unallocatedPrecincts) {
                if (i == selectedIndex) {
                    seedPrecincts.add(p);
                    selectedPrecinct = p;
                    for (BaseDistrict dp : BaseState.getDistricts().values()) {
                        if (dp.getPrecincts().contains(p)) {
                            dp.removePrecinct(p);
                        }
                    }
                    d.addPrecinct(p);
                    precinctDistrictMap.put(p.getID(), d.getID());
                }
                i++;
            }
            unallocatedPrecincts.remove(selectedPrecinct);
        }
        // Strip out all but one BasePrecinct from each BaseDistrict
        for (BaseDistrict d : BaseState.getDistricts().values()) {
            for (BasePrecinct p : d.getPrecincts()) {
                if (!seedPrecincts.contains(p)) {
                    d.removePrecinct(p);
                }
            }
        }
        // For each BaseDistrict, add new precincts until a population threshold is met
        System.out.println(unallocatedPrecincts.size() + " before initial alloc.");
        int idealPop = BaseState.getPopulation() / BaseState.getDistricts().size();
        boolean outOfMoves = false;
        int highestPop = -1;
        while (outOfMoves == false) {
            outOfMoves = true;
            for (BaseDistrict d : BaseState.getDistricts().values()) {
                // Add precincts until population > highestPop
                for (BasePrecinct pd : d.getPrecincts()) {
                    for (String s : pd.getNeighborIDs()) {
                        BasePrecinct p = BaseState.getPrecinct(s);
                        if (unallocatedPrecincts.contains(p)) {
                            unallocatedPrecincts.remove(p);
                            d.addPrecinct(p);
                            precinctDistrictMap.put(p.getID(), d.getID());
                            outOfMoves = false;
                            if (d.getPopulation() >= highestPop) {
                                highestPop = d.getPopulation();
                                break;
                            }
                        }
                        if (d.getPopulation() >= highestPop) {
                            highestPop = d.getPopulation();
                            break;
                        }
                    }

                }
            }
        }
        // Allocate remaining unallocated precincts
        System.out.println("Adding remaining precincts.");
        int loopCheck = -1;
        while (!unallocatedPrecincts.isEmpty()) {
            if (loopCheck != unallocatedPrecincts.size()) {
                loopCheck = unallocatedPrecincts.size();
            } else {
                // Set to default if all else fails.
                for (BasePrecinct p : unallocatedPrecincts) {
                    precinctDistrictMap.put(p.getID(), p.getOriginalDistrictID());
                    BaseState.getDistrict(p.getOriginalDistrictID()).addPrecinct(p);
                }
                unallocatedPrecincts.clear();
            }
            System.out.println(unallocatedPrecincts.size() + " left.");
            HashSet<BasePrecinct> newlyAdded = new HashSet<BasePrecinct>();
            for (BasePrecinct p : unallocatedPrecincts) {
                String myDistrict = null;
                for (String s : p.getNeighborIDs()) {
                    if (precinctDistrictMap.get(s) != null) {
                        myDistrict = precinctDistrictMap.get(s);
                    }
                }
                if (myDistrict != null) {
                    precinctDistrictMap.put(p.getID(), myDistrict);
                    BaseState.getDistrict(myDistrict).addPrecinct(p);
                    newlyAdded.add(p);
                }
            }
            unallocatedPrecincts.removeAll(newlyAdded);
        }
    }

    public String getPrecinctDistrictID(BasePrecinct p) {
        return precinctDistrictMap.get(p.getID());
    }

//    public Move<BasePrecinct, BaseDistrict> makeMove() {
//        if (currentDistrict == null) {
//            currentDistrict = getWorstDistrict();
//        }
//        BaseDistrict startDistrict = currentDistrict;
//        Move m = getMoveFromDistrict(startDistrict);
//        if (m == null) {
//            return makeMove_secondary();
//        }
//        return m;
//    }

    public Move getMoveFromDistrict(BaseDistrict startDistrict, Race[] races, double minPopPerc, double maxPopPerc) {
        Set<BasePrecinct> precincts = startDistrict.getBorderPrecincts();
        //Set<BasePrecinct> precincts = startDistrict.getPrecincts();
        for (BasePrecinct p : precincts) {
            Set<String> neighborIDs = p.getNeighborIDs();
            for (String n : neighborIDs) {
                if (startDistrict.getPrecinct(n) == null) {
                    BaseDistrict neighborDistrict = BaseState.getDistrict(precinctDistrictMap.get(n));
                    //System.out.println("Start BaseDistrict: " + startDistrict.getID() + ", Neighbor BaseDistrict: " + neighborDistrict.getID() + ", BasePrecinct: " + p.getID());
                    Move move = testMove(neighborDistrict, startDistrict, p, races, minPopPerc, maxPopPerc);
                    if (move != null) {
                        System.out.println("Moving p to neighborDistrict(neighborID = " + n + ")");
                        currentDistrict = startDistrict;
                        return move;
                    }
                    move = testMove(startDistrict, neighborDistrict, neighborDistrict.getPrecinct(n), races, minPopPerc, maxPopPerc);
                    if (move != null) {
                        System.out.println("Moving n to Start BaseDistrict: " + startDistrict.getID());
                        currentDistrict = startDistrict;
                        return move;
                    }
                }
            }
        }
        System.out.println("Move not found for BaseDistrict " + startDistrict.getID());
        return null;
    }

    // Returns the confirmed move if successful, otherwise returns null.
    private Move testMove(BaseDistrict to, BaseDistrict from, BasePrecinct p, Race[] races, double minPopPerc, double maxPopPerc) {
        Move m = new Move<>(to, from, p);
        double initial_score = currentScores.get(to) + currentScores.get(from);
        boolean toMajMin1 = false;
        for (Race r : races) {
            double racePerc = (double) (to.getRacePops()[r.ordinal()]) / (double) (to.getPopulation());
            if (racePerc >= minPopPerc && racePerc <= maxPopPerc) {
                toMajMin1 = true;
            }
        }
        boolean fromMajMin1 = false;
        for (Race r : races) {
            double racePerc = (double) (from.getRacePops()[r.ordinal()]) / (double) (from.getPopulation());
            if (racePerc >= minPopPerc && racePerc <= maxPopPerc) {
                fromMajMin1 = true;
            }
        }
        m.execute();
        if (!checkContiguity(p, from)) {
            m.undo();
            return null;
        }
        boolean toMajMin2 = false;
        for (Race r : races) {
            double racePerc = (double) (to.getRacePops()[r.ordinal()]) / (double) (to.getPopulation());
            if (racePerc >= minPopPerc && racePerc <= maxPopPerc) {
                toMajMin2 = true;
            }
        }
        boolean fromMajMin2 = false;
        for (Race r : races) {
            double racePerc = (double) (from.getRacePops()[r.ordinal()]) / (double) (from.getPopulation());
            if (racePerc >= minPopPerc && racePerc <= maxPopPerc) {
                fromMajMin2 = true;
            }
        }
        if (toMajMin1 && !toMajMin2) { // to degrades
            if (fromMajMin1 && !fromMajMin2) { // from also degrades
                m.undo();
                return null;
            }
            if (fromMajMin1 == fromMajMin2) { // from doesn't improves
                m.undo();
                return null;
            }
        }
        if (fromMajMin1 && !fromMajMin2) { // from degrades
            if (toMajMin1 && !toMajMin2) { // to also degrades
                m.undo();
                return null;
            }
            if (toMajMin1 == toMajMin2) { // to doesn't improves
                m.undo();
                return null;
            }
        }
        double to_score = rateDistrict(to);
        double from_score = rateDistrict(from);
        double final_score = (to_score + from_score);
        double change = final_score - initial_score;
        if (change <= 0) {
            m.undo();
            return null;
        }
        currentScores.put(to, to_score);
        currentScores.put(from, from_score);
        precinctDistrictMap.put(p.getID(), to.getID());
        return m;
    }

    // Manually force a move. Return true if both parameters are valid.
    public Move<BasePrecinct, BaseDistrict> manualMove(String BasePrecinct, String BaseDistrict) {
        BaseDistrict from = BaseState.getDistrict(precinctDistrictMap.get(BasePrecinct));
        if (from == null) {
            return null;
        }
        BasePrecinct p = from.getPrecinct(BasePrecinct);
        BaseDistrict to = BaseState.getDistrict(BaseDistrict);
        if (to == null) {
            return null;
        }
        if (p == null) {
            return null;
        }
        Move<BasePrecinct, BaseDistrict> m = new Move<>(to, from, p);
        double initial_score = currentScores.get(to) + currentScores.get(from);
        m.execute();
        double to_score = rateDistrict(to);
        double from_score = rateDistrict(from);
        currentScores.put(to, to_score);
        currentScores.put(from, from_score);
        precinctDistrictMap.put(p.getID(), to.getID());
        return m;
    }

    public BaseDistrict getWorstDistrict() {
        BaseDistrict worstDistrict = null;
        double minScore = Double.POSITIVE_INFINITY;
        for (BaseDistrict d : BaseState.getDistricts().values()) {
            double score = currentScores.get(d);
            if (score < minScore) {
                worstDistrict = d;
                minScore = score;
            }
        }
        return worstDistrict;
    }

//    public Move<BasePrecinct, BaseDistrict> makeMove_secondary() {
//        List<BaseDistrict> districts = getWorstDistricts();
//        //districts.remove(0);
//        while (districts.size() > 0) {
//            BaseDistrict startDistrict = districts.get(0);
//            Move m = getMoveFromDistrict(startDistrict);
//            if (m != null) {
//                return m;
//            }
//            districts.remove(0);
//        }
//        return null;
//    }

    // Returns a list of districts sorted from worst to best
    public List<BaseDistrict> getWorstDistricts() {
        List<Map.Entry<BaseDistrict, Double>> list = new LinkedList<>(currentScores.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<BaseDistrict, Double>>() {
            @Override
            public int compare(Entry<BaseDistrict, Double> o1, Entry<BaseDistrict, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        List<BaseDistrict> to_return = new ArrayList<BaseDistrict>();
        for (Entry<BaseDistrict, Double> entry : list)
            to_return.add(entry.getKey());
        return to_return;
    }

    public double calculateObjectiveFunction() {
        double score = 0;
        for (BaseDistrict d : BaseState.getDistricts().values()) {
            score += currentScores.get(d);
        }
        return score;
    }

    public double rateDistrict(BaseDistrict d) {
        return districtScoreFunction.calculateMeasure(d);
    }

    public void updateScores() {
        currentScores = new HashMap<BaseDistrict, Double>();
        for (BaseDistrict d : BaseState.getDistricts().values()) {
            // TODO = resovlve districtRating
            currentScores.put(d, rateDistrict(d));
        }
    }

    /**
     * Check contiguity for moving BasePrecinct p out of BaseDistrict d
     *
     * @param p BasePrecinct to move
     * @param d BaseDistrict to move p out of
     * @return true if contiguous
     */
    private boolean checkContiguity(BasePrecinct p, BaseDistrict d) {
        HashSet<BasePrecinct> precinctsToCheck = new HashSet<BasePrecinct>();
        HashSet<BasePrecinct> neighborsToExplore = new HashSet<BasePrecinct>(); // Potential sources of exploration
        HashSet<BasePrecinct> exploredNeighbors = new HashSet<BasePrecinct>(); // Neighbors already explored
        exploredNeighbors.add(p); // Add the BasePrecinct being moved, to ensure it won't be used.
        // If a neighbor is in the BaseDistrict that's losing a BasePrecinct, we need to make sure they're still contiguous
        for (String p_id : p.getNeighborIDs()) {
            // If neighbor is in the BaseDistrict we're moving p out of
            if ((precinctDistrictMap.get(p_id) != null) &&
                    (precinctDistrictMap.get(p_id)).equals(d.getID())) {
                BasePrecinct neighbor = d.getPrecinct(p_id);
                precinctsToCheck.add(neighbor);
            }
        }
        // If there are no same - BaseDistrict neighbors for the node, return false.
        if (precinctsToCheck.size() == 0) {
            return false;
        }
        // Add an arbitrary same - BaseDistrict neighbor to the sources of exploration
        BasePrecinct initialPrecinctToCheck = precinctsToCheck.iterator().next();
        neighborsToExplore.add(initialPrecinctToCheck);
        //we can remove the initial BasePrecinct to check since we're assuming it to be in our search tree
        precinctsToCheck.remove(initialPrecinctToCheck);
        // While we still need IDs and still have neighbors to explore
        while (neighborsToExplore.size() != 0) {
            // Take an arbitrary BasePrecinct from the sources of exploration
            BasePrecinct precinctToExplore = neighborsToExplore.iterator().next();
            for (String p_id : precinctToExplore.getNeighborIDs()) {
                // We only care about neighbors in our BaseDistrict, d.
                if (precinctDistrictMap.get(p_id).equals(d.getID())) {
                    BasePrecinct neighbor = d.getPrecinct(p_id);
                    // If we've hit one of our needed precincts, check it off.
                    if (precinctsToCheck.contains(neighbor)) {
                        precinctsToCheck.remove(neighbor);
                        if (precinctsToCheck.size() == 0) {
                            return true;
                        }
                    }
                    // Add any neighbors in same BaseDistrict to neighborsToExplore if not in exploredNeighbors
                    if (!exploredNeighbors.contains(neighbor)) {
                        //if its a border BasePrecinct we need to check it otherwise we DONT
                        //EXCEPT -- we didn't actually execute the move at this point
                        if (d.getBorderPrecincts().contains(neighbor)) {
                            neighborsToExplore.add(neighbor);
                        } else {
                            exploredNeighbors.add(neighbor);
                        }
                    }
                }
            }
            // Check this BasePrecinct off
            exploredNeighbors.add(precinctToExplore);
            neighborsToExplore.remove(precinctToExplore);
        }
        return (precinctsToCheck.size() == 0);
    }


}
