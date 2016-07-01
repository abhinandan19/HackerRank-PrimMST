/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primmst;

import java.util.*;

/**
 *
 * @author Abhinandan
 */
public class PrimMST {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);       
        int N = keyboard.nextInt();
        int M = keyboard.nextInt();
        HashMap<Integer,HashMap<Integer,Integer>> edgeMap = new HashMap<Integer,HashMap<Integer,Integer>>();    
        
        for(int i=0;i<M;i++){
            int n1 = keyboard.nextInt();
            int n2 = keyboard.nextInt();
            int weight = keyboard.nextInt();
            
            if(edgeMap.containsKey(n1)){
                edgeMap.get(n1).put(n2, weight);
            }
            else{
                HashMap<Integer,Integer> edge = new HashMap<Integer,Integer>();
                edge.put(n2, weight);
                edgeMap.put(n1, edge);
            }
            
            if(edgeMap.containsKey(n2)){
                edgeMap.get(n2).put(n1, weight);
            }
            else{
                HashMap<Integer,Integer> edge = new HashMap<Integer,Integer>();
                edge.put(n1, weight);
                edgeMap.put(n2, edge);
            }
        }
        
        int S = keyboard.nextInt();
        
        PriorityQueue<CostNode> costQueue = new PriorityQueue<CostNode>(new Comparator<CostNode>(){
            public int compare(CostNode node1, CostNode node2){
                return (node1.cost - node2.cost);
            }
        });
        
        boolean[] visited = new boolean[N+1];
        int[] costArray = new int[N+1];
        
        for(int i=1;i<=N;i++){
            costArray[i] = Integer.MAX_VALUE;
        }
        
        int numVisited = 0;       
        costQueue.add(new CostNode(S, 0));
        costArray[S] = 0;
        
        while(numVisited < N){
            CostNode costNode = costQueue.poll();
            int minNode = costNode.node;
            
            if(!visited[minNode]){
                visited[minNode] = true;
                numVisited++;

                for(int neighbour: edgeMap.get(minNode).keySet()){
                    if(!visited[neighbour] && edgeMap.get(minNode).get(neighbour) < costArray[neighbour]){
                        costArray[neighbour] = edgeMap.get(minNode).get(neighbour);
                        costQueue.add(new CostNode(neighbour,costArray[neighbour]));
                    }
                }
            }
        }
        
        int totalCost = 0;
        
        for(int i=1;i<=N;i++){
            totalCost += costArray[i];
        }
        
        System.out.println(totalCost);
    }
}

class CostNode {
    int node;
    int cost;
    
    public CostNode(int node, int cost){
        this.node = node;
        this.cost = cost;
    }
}
