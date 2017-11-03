/*****************************************************************************
 * FILE: State.java
 * AUTHOR: Emmanuel Iloh
 * USERNAME: 18817599
 * UNIT: Data Structures and Algorithms (COMP1002)
 * PURPOSE: a container class for storing and managing information about a state
 * REFERENCE: none
 * COMMENTS: it a child class of Place.java
 * REQUIRES: It requires Place.java to run as it inherits from it
 * Last Mod: 16/10/2017
 ******************************************************************************/

public class State extends Place
{
   // Constants
   private static final long serialVersionUID = 42L;

   // private class feilds
   private Country country;

   /**************************************************************************
   * Default Constructor:
   * Import: none
   * Export: none
   * ASSERTION: sets the values to default valid values
   * ************************************************************************/
   public State()
   {
      super();
      country = new Country();
   }

   /**************************************************************************
   * Alternate Constructor:
   * Import: name, stName, area, pop, popRef, country
   * Export: none
   * ASSERTION: initilizes the values of a State to those imported
   * ************************************************************************/
   public State(String name,
                String stName,
                int area,
                int pop,
                String popRef,
                Country country)
   {
      super(name, stName, area, pop, popRef);
      this.country = country;
   }

}
