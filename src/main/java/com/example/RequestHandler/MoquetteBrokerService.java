package com.example.RequestHandler;

import org.springframework.stereotype.Service;

import java.util.PriorityQueue;

@Service
public class MoquetteBrokerService {
    private PriorityQueue<MoquetteBroker> pq = new PriorityQueue<>();

    public void addBroker(MoquetteBroker mb){
        pq.add(mb);
    }

    public MoquetteBroker getBroker(){
        return pq.peek();
    }

    public boolean pqContains(MoquetteBroker mb){
        return pq.contains(mb);
    }

    public PriorityQueue<MoquetteBroker> getPq(){
        return this.pq;
    }
}
