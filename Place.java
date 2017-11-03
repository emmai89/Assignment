import java.io.*;

/*****************************************************************************
 * FILE: Place.java
 * AUTHOR: Emmanuel Iloh
 * USERNAME: 18817599
 * UNIT: Data Structures and Algorithms (COMP1002)
 * PURPOSE: it's an abstract container class that store and manages basic
 *          about a place;
 * REFERENCE: none
 * COMMENTS: in the context of this project, it store information about for the
 *           Country and State classes
 * REQUIRES: none
 * Last Mod: 16/10/2017
 ******************************************************************************/

public abstract class Place implements Serializable
{
// Constants
   private static final long serialVersionUID = 42L;

   // private class feilds
   private String name;
   private String stName;
   private int area;
   private int pop;
   private String popRef;

   /**************************************************************************
   * Default Constructor:
   * Import: none
   * Export: none
   * ASSERTION: sets the values to default valid values
   * ************************************************************************/
   public Place()
   {
      name = "NO NAME";
      stName = "N/A";
      area = 0;
      pop = 0;
      popRef = "N/A";
   }

   /**************************************************************************
   * Alternate Constructor:
   * Import: name, stName, area, pop, popRef
   * Export: none
   * ASSERTION: initilizes the values of a place to those imported
   * ************************************************************************/
   public Place(String name, String stName, int area, int pop, String popRef)
   {
      this.name = name;
      this.stName = stName;
      this.area = area;
      this.pop = pop;
      this.popRef = popRef;
   }

   // Accesors
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

   /***************************************************************************
   * SUBMODULE: getStName
   * IMPORTS: none
   * EXPORTS: stName
   * ASSERTION: returns the value of the short name
   * *************************************************************************/
   public String getStName()
   {
      return stName;
   }

   // Mutators
   /**************************************************************************
    * SUBMODULE: setName
    * IMPORTS: name
    * EXPORTS: none
    * ASSERTION: sets the current name to that imported
    * ***********************************************************************/
   public void setName(String name)
   {
      this.name = name;
   }

   /**************************************************************************
    * SUBMODULE: setStName
    * IMPORTS: stName
    * EXPORTS: none
    * ASSERTION: sets the current short name to that imported
    * ***********************************************************************/
   public void setStName(String stName)
   {
      this.stName = stName;
   }

}
