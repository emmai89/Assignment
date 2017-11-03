/*****************************************************************************
 * FILE: DSAGraphEdge.java
 * AUTHOR: Emmanuel Iloh
 * USERNAME: 18817599
 * UNIT: Data Structures and Algorithms (COMP1002)
 * PURPOSE: TODO: what is the purpose
 * REFERENCE: none
 * COMMENTS: none
 * REQUIRES: It makes use of the DSAGraphNode to mark the point it's from and
 *           the point it's starting at and going to
 * Last Mod: 20/10/2017
 ******************************************************************************/

import java.util.*;
import java.io.*;

public class DSAGraphEdge<E> implements Iterable<E>, Serializable
{
   // Constants
   private static final long serialVersionUID = 42L;
   public final String[] MODES = {"walk", "car", "pubtrans", "cycle", "plane"};

   // private class feilds
   private String label;
   private DSAGraphNode<E> from;
   private DSAGraphNode<E> to;
   private DSALinkedList<DSAEdgeWeight> weights;

   /**************************************************************************
   * Alternate Constructor:
   * Import: to and from, both DSAGraphNode's
   * Export: none
   * ASSERTION: initilizes an edge between the 2 nodes
   * ************************************************************************/
   public DSAGraphEdge(DSAGraphNode<E> from,
                       DSAGraphNode<E> to)
   {
      this.label = "" +from.getLabel() +to.getLabel();
      this.from = from;
      this.to = to;
      this.weights = new DSALinkedList<>();
   }

   /***********************************************************************
    * SUBMODULE: findWeight
    * IMPORTS: mode
    * EXPORTS: DSAEdgeWeight of the mode chosen
    * ASSERTION: sets the current value to the value
    * ********************************************************************/
   public DSAEdgeWeight findWeight(String mode)
   {
      DSAEdgeWeight weight = null;

      for (DSAEdgeWeight w : weights) // this loops through all the weaight in this
      {                               // edge to find the requested mode
         if(mode.equals(w.getMode()))
         {                            // A while loop could be implemented for a more
            weight = w;               // efficient runtime performance
         }
      }
      return weight;
   }

   /***********************************************************************
    * SUBMODULE: addWeight
    * IMPORTS: mode, time, length, pkTime
    * EXPORTS: none
    * ASSERTION: creates a new weight based on the inputs then adds it to the list
    * ********************************************************************/
   public void addWeight(String mode, int time, double length, int pkTime)
   {
      if(!isMode(mode))
      {
         throw new IllegalArgumentException("this mode is not recodnised: " +mode);
      }
      weights.insertLast(new DSAEdgeWeight(mode, time, length, pkTime));// adds a new weight to
                                                                        // the linked list
   }

   /***************************************************************************
   * SUBMODULE: isMode
   * IMPORTS: mode
   * EXPORTS: boolean
   * ASSERTION: checks if the imported mode is actually on of  the valid modes
   * *************************************************************************/
   private boolean isMode(String mode)
   {
      boolean is = false;

      for(int ii = 0; ii < MODES.length; ii++)
      {
         if(mode.equals(MODES[ii]))
         {
            is = true;
         }
      }

      return is;
   }

   /***********************************************************************
    * SUBMODULE: getShortestTime
    * IMPORTS: peak
    * EXPORTS: DSAEdgeWeight with the shortest time
    * ASSERTION: finds the lowest time based on peak or not
    * ********************************************************************/
   public DSAEdgeWeight getShortestTime(boolean peak)
   {
      int time = Integer.MAX_VALUE;
      DSAEdgeWeight edge = null;
      for (DSAEdgeWeight e : weights)
      {
         if(peak)
         {
            if(time > e.getPkTime())
            {
               time =  e.getPkTime();
               edge = e;
            }
         }
         else
         {
            if(time > e.getTime())
            {
               time =  e.getTime();
               edge = e;
            }
         }
      }
      return edge;
   }

   /***********************************************************************
    * SUBMODULE: getShortestLength
    * IMPORTS: none
    * EXPORTS: DSAEdgeWeight with the shortest length
    * ASSERTION: finds the shortest edge based on distance
    * ********************************************************************/
   public DSAEdgeWeight getShortestLength()
   {
      double length = Double.MAX_VALUE;
      DSAEdgeWeight edge = null;
      for (DSAEdgeWeight e : weights)
      {
         if(length > e.getLength())
         {
            length =  e.getLength();
            edge = e;
         }
      }
      return edge;
   }

   // Accesors
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
    * SUBMODULE: getForm
    * IMPORTS: none
    * EXPORTS: from
    * ASSERTION:
    * ********************************************************************/
   public DSAGraphNode<E> getForm()
   {
      return from;
   }

   /***********************************************************************
    * SUBMODULE: getTo
    * IMPORTS: none
    * EXPORTS: to
    * ASSERTION:
    * ********************************************************************/
   public DSAGraphNode<E> getTo()
   {
      return to;
   }

   public Iterator<E> iterator()
   {
      return this.iterator();
   }

}
