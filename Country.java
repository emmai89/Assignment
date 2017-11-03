/*****************************************************************************
 * FILE: Country.java
 * AUTHOR: Emmanuel Iloh
 * USERNAME: 18817599
 * UNIT: Data Structures and Algorithms (COMP1002)
 * PURPOSE: Provides a containment class to store and manage data about
 *          different Countries
 * REFERENCE: None.
 * COMMENTS: The algorithm uses simple random number generation,
 * to generate the numbers.
 * REQUIRES: Makes use of LottoSet.java which contains a class
 * used to contain of set of six lotto numbers
 * Last Mod: 16/10/2017
 ******************************************************************************/
public class Country extends Place
{
   // Constants
   private static final long serialVersionUID = 42L;

   // private class feilds
   String lang;

   /**************************************************************************
   * Default Constructor:
   * Import: none
   * Export: none
   * ASSERTION: sets the values to default valid values
   * ************************************************************************/
   public Country()
   {
      super();
      lang = "N/A";
   }

   /**************************************************************************
   * Alternate Constructor:
   * Import: name, stName, area, pop, popRef, lang
   * Export: none
   * ASSERTION: initilizes the values of a Country to those imported
   * ************************************************************************/
   public Country(String name,
                  String stName,
                  int area,
                  int pop,
                  String popRef,
                  String lang)
   {
      super(name, stName, area, pop, popRef);
      this.lang = lang;
   }

   // Accesors
}
