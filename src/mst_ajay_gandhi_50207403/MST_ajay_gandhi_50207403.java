/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mst_ajay_gandhi_50207403;

/**
 *
 * @author Ajay-Pc
 */


import java.io.*;
import java.util.*;
import mst_ajay_gandhi_50207403.MST_ajay_gandhi_50207403.EDGE;

public class MST_ajay_gandhi_50207403
{ 

    static class EDGE
    {
        int weight;                                                             //Declaration of variables of object of class Edge
        int start_vertex;
        int end_vertex;
        
        
        EDGE(int start, int end, int wt)                                        //Parameterised Constructor
        {
            weight = wt;
            start_vertex = start;
            end_vertex = end;
            
        }
    }
    
     public static ArrayList<EDGE> PrimsAlgo(ArrayList<ArrayList<EDGE>> Graph)
     {
        if(Graph.isEmpty())throw new NullPointerException("Sorry the Graph is Empty...");           //Error exception
        
        Create_Heap heap_new = new Create_Heap();                               //create a variable for heap
        ArrayList<EDGE> Tree = new ArrayList<>();                               //create a variable for the minimum spanning tree
        boolean[] vertex_visited = new boolean[Graph.size()];                   //create a variable to track number of vertices visited
        
        for(EDGE e:Graph.get(1))                                                
        {    
            heap_new.insertIntoHeap(new EDGE(e.start_vertex,e.end_vertex,e.weight));    
        } 
        vertex_visited[0] = true;
        while(!heap_new.Heap_Empty()){
            EDGE e = heap_new.top();                                            //returns the top edge of the heap
            
            heap_new.remove_min();                                              //extracts the minimum edge from the heap
            if(vertex_visited[e.start_vertex] && vertex_visited[e.end_vertex])
            {
                continue;
            }
            vertex_visited[e.start_vertex] = true;                              //set true to vertex visited
            for(EDGE edge:Graph.get(e.end_vertex))
            {
                if(!vertex_visited[edge.end_vertex])                            //if previously not visited
                {
                    heap_new.insertIntoHeap(edge);                                      //insert new edge into the heap
                }
            }
            vertex_visited[e.end_vertex] = true;                                
            Tree.add(e);                                                        //add the edge to the minimum spanning tree
            
        }
        return Tree;                                                            //return the calculated tree
    }
    
    public static ArrayList<ArrayList<EDGE>> createAGraph(EDGE[] Edge_recv)
    {
        int len = Edge_recv.length*2;                                           //create a variable to store length
        ArrayList<ArrayList<EDGE>> Graph = new ArrayList<>();                   //create a variable for Graph
        
        for(int i=0;i<len;++i)
        {
            Graph.add(new ArrayList<>());                                       //Add Arraylists equal to length
        }
        
        for(EDGE ed:Edge_recv)
        {
            Graph.get(ed.start_vertex).add(ed);                                 //Add edge 'A'-'B' to the graph
            EDGE opposite = new EDGE(ed.start_vertex, ed.end_vertex, ed.weight);//Create an opposite edge i.e. 'B'-'A'   
            Graph.get(ed.end_vertex).add(opposite);                             //Add edge 'B'-'A' to the graph
       
        }
        
        return Graph;                                                           //return the created Graph
    }
 
    
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, IOException
    {
        
        Scanner in = new Scanner(new File("input.txt"));                        //to read the specified input file
        String outputfilename = "output.txt";                                   //to create the specified output file
        BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputfilename), "UTF-8"));    //to create a buffered writer to write to the output file
        int numberOfVertices;                                                   //variable to count number of vertices
        int numberOfEdges;                                                      //variable to count number of edges
        numberOfVertices = in.nextInt();                                        //read number of vertices from first line
        numberOfEdges = in.nextInt();                                           //read number of edges from first line
        //vertices = new ArrayList<ArrayList<int[]>>();

        EDGE[] edge_array = new EDGE[numberOfEdges];                            //creates an array of edges

        for (int i = 0; i < numberOfEdges; i++) 
        {
                int start = in.nextInt();
                int end = in.nextInt();
                int cost = in.nextInt();
                edge_array[i] = new EDGE(start, end, cost);                     //to read values in a line and assign them to an edge object
        }

        ArrayList<ArrayList<EDGE>> graph = createAGraph(edge_array);            //call the function to create a graph
        ArrayList<EDGE> minimum_tree = PrimsAlgo(graph);                        //call the Prims Algorithm Function to create a minimum spanning tree for the graph
       int minimum_tree_size = minimum_tree.size();                             //Calculate the size of the Minimum-Spanning-Tree
       int minimum_tree_cost = 0;                                               //Create a variable to find the cost of traversing the minimum spanning tree
       for(int s=0;s<minimum_tree_size;s++)
        {
            minimum_tree_cost += minimum_tree.get(s).weight;                    //Calculate the cost
            
        }
        System.out.println(minimum_tree_cost);                                  //print the total cost of the traversal among all nodes
        String str = Integer.toString(minimum_tree_cost);                                
        bufferedwriter.write(str);                                              //Write the cost to output file
        bufferedwriter.newLine();
       for(int s=0;s<minimum_tree_size;s++)
        {
            System.out.println(minimum_tree.get(s).start_vertex+" "+minimum_tree.get(s).end_vertex+" "+minimum_tree.get(s).weight);
            bufferedwriter.write(minimum_tree.get(s).start_vertex+" "+minimum_tree.get(s).end_vertex+" "+minimum_tree.get(s).weight);
            bufferedwriter.newLine();
        }
    bufferedwriter.close();                                                     //close the writer to the output file
    }
}

  class Create_Heap                                                             //class to create a heap
  {
     

	private ArrayList<EDGE> heap_array;

	public Create_Heap()                                                    //constructor
        {
		heap_array = new ArrayList<EDGE>();
                
	}
        
	private void HeapifyUp()
        {
		int n = heap_array.size() - 1;
		while (n > 0)
                {
			int par = (n-1)/2;
                        EDGE parent = heap_array.get(par);
			EDGE item = heap_array.get(n);
			if ( parent.weight > item.weight)
                        {
                            heap_array.set(par, item);
			    heap_array.set(n, parent);
                            n = par;
			}
                        else 
                        {
                            break;
			}
		}
	}
	
	public void insertIntoHeap(EDGE item)
        {
		heap_array.add(item);                                           //add the edge into the heap
		HeapifyUp();                                                    //re perform heapifyup to re-balance the heap
	}
	
	private void HeapifyDown()
        {
		int par = 0;
		int left = 2*par+1;
		while (left < heap_array.size())
                {
			int maximum=left;
                        int right=maximum+1;
			
                        if (right < heap_array.size())
                        {
                            if (heap_array.get(right).weight < heap_array.get(left).weight)
                            {
                                maximum++;
                            }
			}
			if (heap_array.get(par).weight >heap_array.get(maximum).weight)
                        {
                            EDGE temp = heap_array.get(par);
                            heap_array.set(par, heap_array.get(maximum));
                            heap_array.set(maximum, temp);
                            par = maximum;
                            left = 2*par+1;
			}
                        else
                        {
                            break;
			}
		}
	}
	
        public EDGE top()
        {
        return heap_array.get(0);                                               //returns the top element of the heap
        }
     
	public EDGE remove_min() throws NoSuchElementException 
        {
		if (heap_array.isEmpty())                                       //check for empty heap
                {
			throw new NoSuchElementException();
		}
		if (heap_array.size() == 1)                                     //remove the edge (no need to reheapify)
                {
			return heap_array.remove(0);
		}
		EDGE removed_edge = heap_array.get(0);                          //get the top most edge in the heap
		heap_array.set(0, heap_array.remove(heap_array.size()-1));      //remove that edge
		HeapifyDown();                                                  //re-perform heapifydown operation because edge was removed
		return removed_edge;                                            //return the removed edge from theheap
	}
        
	public boolean Heap_Empty()
        {
		return heap_array.isEmpty();                                    //returns true if heap is empty

	}
        
}