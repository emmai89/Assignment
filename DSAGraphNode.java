/*****************************************************************************
 * FILE: DSAGraphNode.java
 * AUTHOR: Emmanuel Iloh
 * USERNAME: 18817599
 * UNIT: Data Structures and Algorithms (COMP1002)
 * PURPOSE: none
 * REFERENCE: DSA lecture notes
 *            Dijkstra’s  algorithm: https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
 * COMMENTS: none
 * REQUIRES: It makes use of the DSALinkedList to store a list of all the
 *           adjacent nodes, which are also DSAGraphNode, as well as a list of
 *           of all it's edges, DSAGraphEdge.
 * Last Mod: 16/10/2017
 ******************************************************************************/

import java.util.*;
import java.io.*;

public class DSAGraphNode<E> implements Iterable<E>, Serializable
{
   // Constants
   private static final long serialVersionUID = 42L;

   // private class feilds
   private String label;
   private double tentDistance;
   private int tentTime;
   private DSALinkedList<DSAGraphNode<E> > links;
   private boolean visited;
   private DSAGraphNode<E> prev;
   private DSALinkedList<DSAGraphEdge<E> > edges;

   /**************************************************************************
   * Default Constructor:
   * Import: none
   * Export: none
   * ASSERTION: sets the tent distance and time to infinity for Dijkstra algorithm
   * ************************************************************************/
   public DSAGraphNode()
   {
      tentDistance = Double.MAX_VALUE;// this values are been set to infinity
      tentTime = Integer.MAX_VALUE;   // to meet Dijkstra's algorithm elsewhere
   }                                  // in the program

   /**************************************************************************
   * Alternate Constructor:
   * Import: label
   * Export: none
   * ASSERTION: initilizes a node in the graph
   * ************************************************************************/
   public DSAGraphNode(String label)
   {
      this.label = label;
      visited = false;
      links = new DSALinkedList<DSAGraphNode<E> >();
      edges = new DSALinkedList<DSAGraphEdge<E> >();
   }

   // Accesors

   /***********************************************************************
    * SUBMODULE: getPrev
    * IMPORTS: none
    * EXPORTS: prev
    * ASSERTION:
    * ********************************************************************/
   public DSAGraphNode<E> getPrev()
   {
      return prev;
   }

   /***********************************************************************
    * SUBMODULE: getLabel
    * IMPORTS: none
    * EXPORTS: label
    * ASSERTION:
    * ********************************************************************/
   public String getLabel()
   {
      return label;
   }

   /***********************************************************************
    * SUBMODULE: getTentDistance
    * IMPORTS: none
    * EXPORTS: tentDistance
    * ASSERTION:
    * ********************************************************************/
   public double getTentDistance()
   {
      return tentDistance;
   }

   /***********************************************************************
    * SUBMODULE: getTentTime
    * IMPORTS: none
    * EXPORTS: tentTime
    * ASSERTION:
    * ********************************************************************/
   public int getTentTime()
   {
      return tentTime;
   }

   /***********************************************************************
    * SUBMODULE: getTentConvertedTime
    * IMPORTS: none
    * EXPORTS: array of time, hours and minutes
    * ASSERTION: converts teh time from minutes to hours and minutes
    * ********************************************************************/
   public int[] getTentConvertedTime()
   {
      int time[] = new int[2];
      time[0] = this.tentTime/60;
      time[1] = this.tentTime - (time[0]*60);

      return time;
   }

   /***********************************************************************
    * SUBMODULE: getAdjacent
    * IMPORTS: none
    * EXPORTS: links
    * ASSERTION:
    * ********************************************************************/
   public DSALinkedList<DSAGraphNode<E> > getAdjacent()
   {
      return links;
   }

   /***********************************************************************
    * SUBMODULE: getVisited
    * IMPORTS: none
    * EXPORTS: visited
    * ASSERTION:
    * ********************************************************************/
   public boolean getVisited()
   {
      return visited;
   }

   /***********************************************************************
    * SUBMODULE: getEdge
    * IMPORTS: label
    * EXPORTS: edge
    * ASSERTION:
    * ********************************************************************/
   public DSAGraphEdge<E> getEdge(String label)
   {
      Iterator<DSAGraphEdge<E> > iter = edges.iterator();
      DSAGraphEdge<E> newNd = null;
      String name = this.label+label;
      while(iter.hasNext())
      {
         DSAGraphEdge<E> edge = iter.next();
         if(name.equals(edge.getLabel())) // iterates thought the list until the
         {                                // desired edge is found
            newNd = edge;
         }
      }
      return newNd;
   }

   /***********************************************************************
    * SUBMODULE: display
    * IMPORTS: none
    * EXPORTS: none
    * ASSERTION: displays the the adjacent links to the terminal
    * ********************************************************************/
   public void display()
   {
      Iterator<DSAGraphNode<E> > iter = links.iterator();

      while(iter.hasNext())
      {
         DSAGraphNode<E> link = iter.next();
         System.out.print(link.getLabel());
         if(iter.hasNext())
         {
            System.out.print(", "); // add a comma after each label in the list
         }
      }
      System.out.println("\n\n");
   }

   // Mutators

   public void addEdge(DSAGraphNode<E> node, DSAGraphEdge<E> edge)
   {
      if(links.find(node) == null)
      {
         links.insertLast(node);
      }
      edges.insertLast(edge);
   }

   /**************************************************************************
    * SUBMODULE: setPrev
    * IMPORTS: node
    * EXPORTS: none
    * ASSERTION: sets prev to the imported node
    * ***********************************************************************/
   public void setPrev(DSAGraphNode<E> node)
   {
      this.prev = node;
   }

   /**************************************************************************
    * SUBMODULE: setVisited
    * IMPORTS: none
    * EXPORTS: none
    * ASSERTION: sets the current node to visited
    * ***********************************************************************/
   public void setVisited()
   {
      visited = true;
   }

   /**************************************************************************
    * SUBMODULE: setTentDistance
    * IMPORTS: tentDistance
    * EXPORTS: none
    * ASSERTION:
    * ***********************************************************************/
   public void setTentDistance(double tentDistance)
   {
      this.tentDistance = tentDistance;
   }

   /**************************************************************************
    * SUBMODULE: setTentTime
    * IMPORTS: tentTime
    * EXPORTS: none
    * ASSERTION:
    * ***********************************************************************/
   public void setTentTime(int tentTime)
   {
      this.tentTime = tentTime;
   }

   /**************************************************************************
    * SUBMODULE: setName
    * IMPORTS: none
    * EXPORTS: none
    * ASSERTION: sets current node as unvisited
    * ***********************************************************************/
   public void clearVisited()
   {
      visited = false;
   }

   public Iterator<E> iterator()
   {
      return this.iterator();
   }

}
