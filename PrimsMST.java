import java.util.*;
import java.io.*;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.LinkedList;
public class PrimsMST
{
    static class Edge 
    {
        int sc;
        int dst;
        int wt;

        public Edge(int sc, int dst, int wt) 
        {
            this.sc = sc;
            this.dst = dst;
            this.wt = wt;
        }
    }

    static class HeapNode
    {
        int vertex;
        int key;
    }

    static class Result 
    {
        int parent;
        int wt;
    }

    static class Graph
     {
        int vertices;
        LinkedList<Edge>[] adjacencylist;

        Graph(int vertices) 
        {
            this.vertices = vertices;
            adjacencylist = new LinkedList[vertices];
            
            for (int i = 0; i <vertices ; i++) 
            {
                adjacencylist[i] = new LinkedList();
            }
        }

        public void addedge(int sc, int dst, int wt)
        {
            Edge edge = new Edge(sc, dst, wt);
            adjacencylist[sc].addFirst(edge);

            edge = new Edge(dst, sc, wt);
            adjacencylist[dst].addFirst(edge); 
        }

        public void primmst()
        {

            boolean[] inPriorityQueue = new boolean[vertices];
            Result[] resultset = new Result[vertices];
            int [] key = new int[vertices]; 

            HeapNode [] heapNodes = new HeapNode[vertices];
            for (int i = 0; i <vertices ; i++) 
            {
                heapNodes[i] = new HeapNode();
                heapNodes[i].vertex = i;
                heapNodes[i].key = Integer.MAX_VALUE;
                resultset[i] = new Result();
                resultset[i].parent = -1;
                inPriorityQueue[i] = true;
                key[i] = Integer.MAX_VALUE;
            }

            
            heapNodes[0].key = 0;

           
            PriorityQueue<HeapNode> pq = new PriorityQueue(vertices,
                    new Comparator<HeapNode>() 
                    {
                        @Override
                        public int compare(HeapNode o1, HeapNode o2) 
                        {
                            return o1.key -o2.key;
                        }
                    });
            
            for (int i = 0; i <vertices ; i++) 
            {
                pq.offer(heapNodes[i]);
            }

            while(!pq.isEmpty())
            {
               
                HeapNode extractedNode = pq.poll();

                
                int extractedVertex = extractedNode.vertex;
                inPriorityQueue[extractedVertex] = false;

               
                LinkedList<Edge> list = adjacencylist[extractedVertex];
                for (int i = 0; i <list.size() ; i++) 
                {
                    Edge edge = list.get(i);
                   
                    if(inPriorityQueue[edge.dst]) 
                    {
                        int dst = edge.dst;
                        int newKey = edge.wt;
                        
                        if(key[dst]>newKey)
                         {
                            decreaseKey(pq, newKey, dst);
                            
                            resultset[dst].parent = extractedVertex;
                            resultset[dst].wt = newKey;
                            key[dst] = newKey;
                        }
                    }
                }
            }
          
            Print(resultset);
        }

        public void decreaseKey(PriorityQueue<HeapNode> pq, int newKey, int vertex)
        {

           
            Iterator it = pq.iterator();

            while (it.hasNext()) 
            {
                HeapNode heapNode = (HeapNode) it.next();
                if(heapNode.vertex==vertex) 
                {
                    pq.remove(heapNode);
                    heapNode.key = newKey;
                    pq.offer(heapNode);
                    break;
                }
            }
        }

        public void Print(Result[] resultset)
        {
            int minwt = 0;
            System.out.println("minimum spanning Tree:");
            for (int i=1;i <vertices ; i++) 
            {
                System.out.println("edge from source " + i + "  and destination is " + resultset[i].parent + "  weight between the edges    " + resultset[i].wt);
                minwt += resultset[i].wt;
            }
            System.out.println("\n\nTotal weight using minimum spanning tree " + minwt);
        }
    }
    public static void main(String[] args) 
     {      
           
            Scanner sc= new Scanner(System.in);
            System.out.println("enter the number of vertices");
            int vert=sc.nextInt();
            Graph graph = new Graph(vert);
            System.out.println("enter number of edges");

          
            int ed= sc.nextInt();
             
            
            for(int i=0;i<ed;i++)
            {
              System.out.println("enter the edges source,destination and weight");
              int a=sc.nextInt();
              int b=sc.nextInt();
              int w=sc.nextInt();
              graph.addedge(a,b,w);

           }
            
            graph.primmst();
        }
}
