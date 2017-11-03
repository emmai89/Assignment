/******************************************************************************
 * FILE: DSAGraph.java
 * AUTHOR: Emmanuel Iloh
 * USERNAME: 18817599
 * UNIT: Data Structures and Algorithms (COMP1002)
 * PURPOSE: TODO: what is the purpose
 * REFERENCE: DSA lecture notes
 *            Dijkstra’s  algorithm: https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
 *            Emmanuel Iloh's DSA practical submissions
 * COMMENTS: none
 * REQUIRES: It makes use of the DSALinkedList class to store the nodes and
 *           edges, as well as the DSAGraphNode and DSAGraphEdge to complete
 *           functionallity of this class
 * Last Mod: 16/10/2017
 *****************************************************************************/

import java.util.*;
import java.io.*;

public class DSAGraph<E> implements Iterable<E>, Serializable
{
   // Constants
   private static final long serialVersionUID = 42L;
   private final double tol = 0.0001; // for checking for real equality

   // private class feilds
   private DSALinkedList<DSAGraphNode<E> > nodes;
   private DSALinkedList<DSAGraphEdge<E> > edges;

   /**************************************************************************
   * Default Constructor:
   * Import: none
   * Export: none
   * ASSERTION: creates a list of nodes and edges
   * ************************************************************************/
   public DSAGraph()
   {
      nodes = new DSALinkedList<>();
      edges = new DSALinkedList<>();
   }

   public void addVertex(DSAGraphNode<E> node)
   {
      if(labFind(node.getLabel()) == null)
      {
         nodes.insertLast(node);
      }
   }

   /***********************************************************************
    * SUBMODULE: addEdge
    * IMPORTS: ver1, ver2, length, time, mode, pkTime
    * EXPORTS: none
    * ASSERTION: adds and edge form the 2 imported modes with teh given weight
    * ********************************************************************/
   public void addEdge(DSAGraphNode<E> ver1,
                       DSAGraphNode<E> ver2,
                       double length,
                       int time,
                       String mode,
                       int pkTime)
   {
      ver1 = labFind(ver1.getLabel()); // finds out if the nodes exist
      ver2 = labFind(ver2.getLabel());
      DSAGraphEdge<E> edge = ver1.getEdge(ver2.getLabel()); // checks if the edge already exist

      if(edge == null) // if it doesn't make a new edge between the 2 nodes
      {
         edge = new DSAGraphEdge<E>(ver1, ver2);
      }

      if(nodes.find(ver1) != null && nodes.find(ver2) != null) // if both nodes exist
      {                                                        // they are made adjacnet and
         ver1.addEdge(ver2, edge);                             // edge weight are added
         ver2.addEdge(ver1, edge);
         edge.addWeight(mode, time, length, pkTime);
         edges.insertLast(edge);
      }
      else
      {
         System.out.println("those don't exist"); // the node has to already be in the graph
      }
   }

   /***********************************************************************
    * SUBMODULE: getNodeCount
    * IMPORTS: none
    * EXPORTS: number of nodes
    * ASSERTION:
    * ********************************************************************/
   public int getNodeCount()
   {
      return nodes.getCount();
   }

   /***********************************************************************
    * SUBMODULE: getEdgeCount
    * IMPORTS: none
    * EXPORTS: number of edges
    * ASSERTION:
    * ********************************************************************/
   public int getEdgeCount()
   {
      return edges.getCount();
   }

   /***********************************************************************
    * SUBMODULE: getNode
    * IMPORTS: none
    * EXPORTS: node
    * ASSERTION:
    * ********************************************************************/
   public DSAGraphNode<E> getNode(String label)
   {
      return labFind(label);
   }

   /***********************************************************************
    * SUBMODULE: getEdge
    * IMPORTS: none
    * EXPORTS: edge
    * ASSERTION:
    * ********************************************************************/
   public DSAGraphEdge<E> getEdge(String label)
   {
      return eFind(label);
   }

   /***********************************************************************
    * SUBMODULE: getNodes
    * IMPORTS: none
    * EXPORTS:  nodes
    * ASSERTION:
    * ********************************************************************/
   public DSALinkedList<DSAGraphNode<E> > getNodes()
   {
      return nodes;
   }

   /***********************************************************************
    * SUBMODULE: getEdges
    * IMPORTS: none
    * EXPORTS: edges
    * ASSERTION:
    * ********************************************************************/
   public DSALinkedList<DSAGraphEdge<E> > getEdges()
   {
      return edges;
   }

   /***********************************************************************
    * SUBMODULE: isAdjacent
    * IMPORTS: node1, node2
    * EXPORTS: isAdjacent boolean
    * ASSERTION: checks if 2 node are adjacent of each other
    * ********************************************************************/
   public boolean isAdjacent(DSAGraphNode<E> node1, DSAGraphNode<E> node2)
   {
      boolean value = false;

      Iterator<DSAGraphNode<E> > iter = node1.getAdjacent().iterator();
      while(iter.hasNext())
      {
         DSAGraphNode<E> node = iter.next();
         if(node.getLabel() == node2.getLabel())
         {
            value = true;
         }
      }

      return value;
   }

   /***********************************************************************
    * SUBMODULE: display
    * IMPORTS: none
    * EXPORTS: none
    * ASSERTION: displays the list of nodes
    * ********************************************************************/
   public void display()
   {
      adjacencyList();
   }

   /***********************************************************************
    * SUBMODULE: shortestPath
    * IMPORTS: to, from, nearby, mode
    * EXPORTS: a linked list of DSAGraphNodes
    * ASSERTION: it finds the shortest path from one node to another given
    *             a few options
    * ********************************************************************/
   public DSALinkedList<DSAGraphNode<E> > shortestPath(String from,
                                                       String to,
                                                       boolean nearby,
                                                       String mode)
   {
      DSALinkedList<DSAGraphNode<E> > Q = new DSALinkedList<>();
      Iterator<DSAGraphNode<E> > iter = nodes.iterator();

      for (DSAGraphNode<E> n : nodes)
      {
         n.setTentDistance(Double.MAX_VALUE); // the tentative values are set to
         n.setPrev(null);                     // infinity
         Q.insertLast(n);     // all the nodes are put into a queue
      }

      DSAGraphNode<E> start = labFind(from);
      start.setTentDistance(0.0); // the starting node's tentative value is set to 0

      while(!Q.isEmpty())
      {
         DSAGraphNode<E> p  = new DSAGraphNode<>();
         for (DSAGraphNode<E> n : Q)
         {
            if(p.getTentDistance() >= n.getTentDistance()) // this is what I used
            {                                             // min priority heap
               p = n;
            }
         }
         Q.removeAt(p); // smallest node in the queue is then removed
         p.setVisited();

         for (DSAGraphNode<E> n : p.getAdjacent()) // it finds the all it's adjacent node
         {
            try
            {
               if(!n.getVisited()) // but only those not visited
               {

                  DSAGraphEdge<E> edge = eFind("" +p.getLabel() +n.getLabel()); // the edge is found using it's label

                  if(mode.equals("any")) // this is for when the user doesn not care which mode they take
                  {
                     double temp = p.getTentDistance() +
                                   edge.getShortestLength().getLength(); // this here ensures the tentative
                     if(temp < n.getTentDistance())                  // value is always the mininal
                     {
                        n.setTentDistance(temp);
                        n.setPrev(p); // this is so I can retrace the shortest path
                     }
                  }
                  else
                  {
                     double temp = p.getTentDistance() +
                                   edge.findWeight(mode).getLength(); // same as about, but
                     if(temp < n.getTentDistance())               // using only a specific mode
                     {
                        n.setTentDistance(temp);
                        n.setPrev(p);
                     }
                  }
               }
            }
            catch (NullPointerException e)
            {
               // This is for when there is not edge of a given node, it simply skips that node
               // hence not accessable
            }
            catch (Exception e)
            {
               e.printStackTrace(); // this is so I can find any other unknown errors
            }

         }
      }

      for (DSAGraphNode<E> n : nodes)
      {
         n.clearVisited(); // this is to reset all the values to unvisited for the next search
      }

      DSALinkedList<DSAGraphNode<E> > S = new DSALinkedList<>(); // this is to put the shortest
      if(!nearby)                                               // path from source to dec in a list
      {
         DSAGraphNode<E> pos = labFind(to); // this is the traget node;
         S.insertFirst(pos);

         while(pos.getPrev() != null)
         {
            pos = pos.getPrev(); // this is where tr=he prev pointer comes into play
            S.insertFirst(pos); // it recalls the shortest prev adjancent node from the creent
         }
      }
      return S;
   }

   /***********************************************************************
    * SUBMODULE: shortestPath
    * IMPORTS: to, from, peak, mode
    * EXPORTS: a linked list of DSAGraphNodes
    * ASSERTION: it finds the shortest path from one node to another given
    *             a few options
    * ********************************************************************/
   public DSALinkedList<DSAGraphNode<E> > shortestTimePath(String from,
                                                           String to,
                                                           boolean peak,
                                                           String mode)
   {
      DSALinkedList<DSAGraphNode<E> > Q = new DSALinkedList<>();
      Iterator<DSAGraphNode<E> > iter = nodes.iterator();

      for (DSAGraphNode<E> n : nodes)
      {
         n.setTentTime(Integer.MAX_VALUE); // the tentative values of all the nodes
         n.setPrev(null);                 // are set to infinity and put into
         Q.insertLast(n);                 // a queue
      }

      DSAGraphNode<E> start = labFind(from);
      start.setTentTime(0); // the start tentative value is set to 0

      while(!Q.isEmpty())
      {
         DSAGraphNode<E> p  = new DSAGraphNode<>();
         for (DSAGraphNode<E> n : Q)
         {
            if(p.getTentTime() >= n.getTentTime()) // used in place of a heap
            {                                     // or min priority queue
               p = n;
            }
         }
         Q.removeAt(p);  // the node with the smallest tentative value is removed
         p.setVisited();  // and set to visited

         for (DSAGraphNode<E> n : p.getAdjacent())
         {
            try
            {
               if(!n.getVisited())
               {
                  DSAGraphEdge<E> edge = eFind("" +p.getLabel() +n.getLabel());
                  int temp;                // each unvisited adjancent node has
                                           // its edge examined
                  if(mode.equals("any"))       // this is for when any mode of transport
                  {                           // is chosen
                     if(peak) // if it's peak time or not
                     {
                        temp = p.getTentTime() +edge.getShortestTime(peak).getPkTime();

                     }
                     else
                     {
                        temp = p.getTentTime() +edge.getShortestTime(peak).getTime();
                     }
                  }
                  else
                  {
                     if(peak) // asame as above but with none peak
                     {
                        temp = p.getTentTime() +edge.findWeight(mode).getPkTime();

                     }
                     else
                     {
                        temp = p.getTentTime() +edge.findWeight(mode).getTime();
                     }
                  }
                  if(temp < n.getTentTime()) // if applicable the tentative value is updated
                  {                         // and the prev is set
                     n.setTentTime(temp);
                     n.setPrev(p);
                  }
               }
            }
            catch (NullPointerException e)
            {
               // This is for when there is not edge of a given node, it simply skips that node
               // hence not accessable
            }
            catch (Exception e)
            {
               e.printStackTrace();// this is so I can find any other unknown errors
            }
         }
      }

      for (DSAGraphNode<E> n : nodes)
      {
         n.clearVisited();// this is to reset all the values to unvisited for the next search
      }

      DSALinkedList<DSAGraphNode<E> > S = new DSALinkedList<>();// this is to put the shortest
      DSAGraphNode<E> pos = labFind(to);                       // path from source to dec in a list
      S.insertFirst(pos);

      while(pos.getPrev() != null)
      {
         pos = pos.getPrev(); // this is where tr=he prev pointer comes into play
         S.insertFirst(pos);            // it recalls the shortest prev adjancent node from the creent
      }
      return S;
   }

   public Iterator<E> iterator()
   {
      return this.iterator();
   }

// Private submodules

/***********************************************************************
 * SUBMODULE: adjacencyList
 * IMPORTS: none
 * EXPORTS: none
 * ASSERTION: displayes all the adjacent nodes
 * ********************************************************************/
   private void adjacencyList()
   {
      Iterator<DSAGraphNode<E> > iter = nodes.iterator();

      System.out.println("\n");
      while(iter.hasNext())
      {
         DSAGraphNode<E> node = iter.next();
         System.out.print(node.getLabel() +": ");
         node.display();
      }
   }

   /***********************************************************************
    * SUBMODULE: labFind
    * IMPORTS: label
    * EXPORTS: DSAGraphNode
    * ASSERTION: returns a node matching the label
    * ********************************************************************/
   private DSAGraphNode<E> labFind(String label)
   {
      Iterator<DSAGraphNode<E> > iter = nodes.iterator();
      DSAGraphNode<E> newNd = null;

      while(iter.hasNext())
      {
         DSAGraphNode<E> node = iter.next();
         if(label.equals(node.getLabel()))
         {
            newNd = node;
         }
      }
      return newNd;
   }

   /***********************************************************************
    * SUBMODULE: clearVisited
    * IMPORTS: none
    * EXPORTS: none
    * ASSERTION: it finds the shortest path from one node to another given
    *             a few options
    * ********************************************************************/
   private void clearVisited()
   {
      Iterator<DSAGraphNode<E> > iter = nodes.iterator();
      DSAGraphNode<E> n = iter.next();

      for ( DSAGraphNode<E> x: nodes)
      {
         x.clearVisited();
         for (DSAGraphNode<E> w : x.getAdjacent())
         {
            w.clearVisited();
         }
      }
   }

   /***********************************************************************
    * SUBMODULE: shortestPath
    * IMPORTS: to, from, nearby, mode
    * EXPORTS: a linked list of DSAGraphNodes
    * ASSERTION: sets all node in the list to unvisited
    * ********************************************************************/
   private DSAGraphEdge<E> eFind(String label)
   {
      Iterator<DSAGraphEdge<E> > iter = edges.iterator();
      DSAGraphEdge<E> newNd = null;

      while(iter.hasNext())
      {
         DSAGraphEdge<E> edge = iter.next();
         if(label.equals(edge.getLabel()))
         {
            newNd = edge;
         }
      }
      if(newNd == null)
      {
         throw new IllegalArgumentException("this edge doesn't exist: " +label);
      }
      return newNd;
   }

}
