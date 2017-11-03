/*****************************************************************************
 * FILE: DSAEdgeWeight.java
 * AUTHOR: Emmanuel Iloh
 * USERNAME: 18817599
 * UNIT: Data Structures and Algorithms (COMP1002)
 * PURPOSE: this is the class that handle the information of the edge weights
 * REFERENCE: none
 * COMMENTS: none
 * REQUIRES: none
 * Last Mod: 20/10/2017
 ******************************************************************************/

import java.io.*;

public class DSAEdgeWeight implements Serializable
{
   // Constants
   private final double TOL = 0.0001;
   private static final long serialVersionUID = 42L;

   // private class feilds
   private double length;
   private int time;
   private int pkTime;
   private String mode;
   private double impare;

   /**************************************************************************
   * Alternate Constructor:
   * Import: mode of transport, time of trip, length of trip and pkTime(peak time)
   * Export: none
   * ASSERTION: initilizes a new instance of DSAEdgeWeight with the given imports
   * ************************************************************************/
   public DSAEdgeWeight(String mode, int time, double length, int pkTime)
   {
      if(!validImpare(impare))// checks if the imparment input is valid
      {
         throw new IllegalArgumentException(impare +" is out of range enter, 100 - 0");
      } // you may notice 00 the stored is not the same as that inputed, that is
      if(length < 0) // because of the input is altered in the method that calls this
      {              // Constructor
         throw new IllegalArgumentException("the distace have to be positive");
      }
      this.length = length;
      this.time = time;
      this.mode = mode;
      this.impare = 0.0;
      if(pkTime == 0)
      {
         this.pkTime = time;
      }
      else
      {
         this.pkTime = pkTime;
      }
   }

   /************************************************************************
   * SUBMODULE: getTime
   * IMPORTS: none
   * EXPORTS: time
   * ASSERTION: based on the impare value, it return an altered value of the
   *            time.
   * **********************************************************************/
   public int getTime()
   {
      int time;
      if (Math.abs(impare - 1) < TOL)
      {
         time = Integer.MAX_VALUE;
      }
      else
      {
         time = (int)((double)this.time / (1.0 - impare));
      }
      return time;
   }

   /************************************************************************
   * SUBMODULE: getPkTime
   * IMPORTS: none
   * EXPORTS: pkTime
   * ASSERTION: based on the impare value, it return an altered value of the
   *            peak time.
   * **********************************************************************/
   public int getPkTime()
   {
      int pkTime;
      if (Math.abs(impare - 1) < TOL)
      {
         pkTime = Integer.MAX_VALUE;
      }
      else
      {
         pkTime = (int)((double)this.pkTime / (1.0 - impare));
      }
      return pkTime;
   }

   /************************************************************************
   * SUBMODULE: getLength
   * IMPORTS: none
   * EXPORTS: length
   * ASSERTION: it returns the length
   * **********************************************************************/
   public double getLength()
   {
      return length;
   }

   /************************************************************************
   * SUBMODULE: getNode
   * IMPORTS: none
   * EXPORTS: mode
   * ASSERTION: baased on the impare value, it return an altered value of the
   * **********************************************************************/
   public String getMode()
   {
      return mode;
   }

   /************************************************************************
   * SUBMODULE: getConvertedTime
   * IMPORTS: none
   * EXPORTS: array of int (hours and minutes)
   * ASSERTION:
   * **********************************************************************/
   public int[] getConvertedTime()
   {
      int time[] = new int[2];
      time[0] = getTime()/60; // getTime is used to factor in the impared value
      time[1] = getTime() - (time[0]*60); // conversion from mins to hours:mins

      return time;
   }

   public int[] getConvertedPkTime()
   {
      int time[] = new int[2];
      time[0] = getPkTime()/60;// getTime is used to factor in the impared value
      time[1] = getPkTime() - (time[0]*60); // conversion from mins to hours:mins

      return time;
   }

   /***********************************************************************
    * SUBMODULE: setImpare
    * IMPORTS: impare
    * EXPORTS: none
    * ASSERTION: sets the current impare to the imported impare is it's valid
    * ********************************************************************/
   public void setImpare(Double impare)
   {
      if(!validImpare(impare))
      {
         throw new IllegalArgumentException(impare +" is out of range enter, 100 - 0");
      }
      this.impare = impare;
   }

   // private submodules

   /************************************************************************
   * SUBMODULE: validImpare
   * IMPORTS: impare
   * EXPORTS: valid, boolean
   * ASSERTION: it checks if the imported impare value is valid or not
   * **********************************************************************/
   private boolean validImpare(double impare)
   {
      boolean valid = false;
      if((impare <= 1.0) && (impare >= 0.0))
      {
         valid = true; // if the the imparment is in the range, it's valid
      }
      return valid;
   }

}// end of inner class
