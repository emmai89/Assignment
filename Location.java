import java.io.*;

/*****************************************************************************
 * FILE: Location.java
 * AUTHOR: Emmanuel Iloh
 * USERNAME: 18817599
 * UNIT: Data Structures and Algorithms (COMP1002)
 * PURPOSE: it's a container class that manages data about a given location
 * REFERENCE: none
 * COMMENTS: none
 * REQUIRES: It makes use of the Country and State class, as the location will
 *           contain information about these things
 * Last Mod: 16/10/2017
 ******************************************************************************/

public class Location implements Serializable
{
   // Constants
   private static final long serialVersionUID = 42L;

   // private class feilds
   private String name;
   private Country country;
   private State state;
   private double coorX, coorY;
   private String discription;

   /**************************************************************************
   * Default Constructor:
   * Import: none
   * Export: none
   * ASSERTION: sets the values to default valid values
   * ************************************************************************/
   public Location()
   {
      name = "NO NAME";
      country = new Country();
      state = new State();
      coorX = 0.0;
      coorY = 0.0;
      discription = "N/A";
   }

   /**************************************************************************
   * Alternate Constructor:
   * Import: name, country, state, coorX, coorY, discription
   * Export: none
   * ASSERTION: initilizes the values of a State to those imported
   * ************************************************************************/
   public Location(String name,
                   Country country,
                   State state,
                   double coorX,
                   double coorY,
                   String discription)
   {
      this.name = name;
      this.country = country;
      this.state = state;
      this.coorX = coorX;
      this.coorY = coorY;
      this.discription = discription;
   }

   /***************************************************************************
   * SUBMODULE: getName
   * IMPORTS: none
   * EXPORTS: name
   * ASSERTION: returns the value of the name
   * *************************************************************************/
   public String getName()
   {
      return name;
   }

}
