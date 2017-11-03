/*****************************************************************************
 * FILE: FileIO.java
 * AUTHOR: Emmanuel Iloh
 * USERNAME: 18817599
 * UNIT: Data Structures and Algorithms (COMP1002)
 * PURPOSE: it handles all interactions with files from disk storage
 * REFERENCE: none
 * COMMENTS: none
 * REQUIRES: It makes use of DSALinkedList to store all the differnt types of
 *           inputs, Country, State and Location, and their repective container
 *           classes. It also uses the DSAGraph class for reading in travel data
 * Last Mod: 16/10/2017
 ******************************************************************************/

import java.io.*;
import java.util.*;

public class FileIO
{
   /***********************************************************************
    * SUBMODULE: readLocationFile
    * IMPORTS: the file name
    * EXPORTS: a linked list of locations
    * ASSERTION: redaing the file and places it's contents in a linked list
    * ********************************************************************/
   public static DSALinkedList<Location> readLocationFile(String fileName)
   {
      FileInputStream fileStrm = null;
      InputStreamReader rdr;
      BufferedReader bufRdr;
      String line;
      DSALinkedList<Country> countries = new DSALinkedList<>();
      DSALinkedList<State> states = new DSALinkedList<>();
      DSALinkedList<Location> locations = new DSALinkedList<>();

      try
      {
         fileStrm = new FileInputStream(fileName);
         rdr = new InputStreamReader(fileStrm);
         bufRdr = new BufferedReader(rdr);

         line = bufRdr.readLine();
         while(line != null)
         {
            if(line.charAt(0) == 'C') // the format of the country, state and Location
            {                         // are not the same, so they need to be seperated
               countries.insertLast(processCountryLine(line));
            }
            else if(line.charAt(0) == 'S')
            {
               states.insertLast(processStateLine(line, countries));
            }
            else if(line.charAt(0) == 'L')
            {
               locations.insertLast(processLocationLine(line, countries, states));
            }
            line = bufRdr.readLine();
         }
         fileStrm.close();
      }
      catch (IOException e)
      {
         if (fileStrm != null)
         {
            try
            {
               fileStrm.close();
            }
            catch (IOException ex2)
            {
            }
         }
         System.out.println("Error in file processing: " + e.getMessage());
         locations = null;
      }
      return locations; // we only really need the locations
   }

   /***********************************************************************
    * SUBMODULE: processCountryLine
    * IMPORTS: a line from the file
    * EXPORTS: a Country object
    * ASSERTION: it processes the line and stores its contents
    * ********************************************************************/
   private static Country processCountryLine(String line)
   {
      String delims = ":|=";
      String[] tokens = line.split(delims);
      int tokenCount = tokens.length;

      String name = tokens[2];
      String stName = tokens[4];
      int area = Integer.parseInt(tokens[8]);
      int pop = Integer.parseInt(tokens[10]);
      String popRef = tokens[12];
      String lang = tokens[6];

      return new Country(name, stName, area, pop, popRef, lang);
   }

   /***********************************************************************
    * SUBMODULE: processStateLine
    * IMPORTS: a line from the file, a list of countries
    * EXPORTS: a State object
    * ASSERTION: it processes the line and stores its contents
    * ********************************************************************/
   private static State processStateLine(String line, DSALinkedList<Country> countries)
   {
      String delims = ":|=";
      String[] tokens = line.split(delims);
      int tokenCount = tokens.length;

      String name = tokens[2];
      String stName = tokens[6];
      int area = Integer.parseInt(tokens[8]);
      int pop = Integer.parseInt(tokens[12]);
      String popRef = tokens[14];
      Country country = findCountry(countries, tokens[4]); // finds the country in the list of countries

      return new State(name, stName, area, pop, popRef, country);
   }

   /***********************************************************************
    * SUBMODULE: processLocationLine
    * IMPORTS: a line from the file, list of countries and states
    * EXPORTS: a Location object
    * ASSERTION: it processes the line and stores its contents
    * ********************************************************************/
   private static Location processLocationLine(String line,
                                               DSALinkedList<Country> countries,
                                               DSALinkedList<State> states)
   {
      String delims = ":|=|,";
      String[] tokens = line.split(delims);
      int tokenCount = tokens.length;

      String name = tokens[2];
      State state = findState(states, tokens[4]);       // state and contry are found froma list
      Country country = findCountry(countries, tokens[6]);
      double coorX, coorY;
      String discription;
      if(!(tokens[8].equals("")))
      {
         coorX = Double.parseDouble(tokens[8]);
         coorY = Double.parseDouble(tokens[9]);
         discription = tokens[11];
      }
      else
      {
         coorX = 0.0; // this is for those entries with no coordinates
         coorY = 0.0;
         discription = tokens[9];
      }

      return new Location(name, country, state, coorX, coorY, discription);
   }

   /***********************************************************************
    * SUBMODULE: findCountry
    * IMPORTS: the file name, a list of countries
    * EXPORTS: a country Object
    * ASSERTION: finds if the name is one of the countries in the list
    * ********************************************************************/
   private static Country findCountry(DSALinkedList<Country> countries, String name)
   {
      Iterator<Country> iter = countries.iterator();
      boolean found = false;
      Country country = new Country();

      while(iter.hasNext() && !found)
      {
         country = iter.next();
         if(name.equals(country.getName()) || name.equals(country.getStName()))
         {
            found = true;
         }
      }
      if(!found) // this is if it doesn't yet eist for some reason
      {
         country.setName(name);
         countries.insertLast(country);
         System.out.println("nope: " +name); // TODO: sort this out
      }
      return country;
   }

   /***********************************************************************
    * SUBMODULE: findState
    * IMPORTS: the file name, a list of states
    * EXPORTS: a country Object
    * ASSERTION: finds if the name is one of the states in the list
    * ********************************************************************/
   private static State findState(DSALinkedList<State> states, String name)
   {
      Iterator<State> iter = states.iterator();
      boolean found = false;
      State state = new State();

      while(iter.hasNext() && !found)
      {
         state = iter.next();
         if(name.equals(state.getName()) || name.equals(state.getStName()))
         {
            found = true;
         }
      }
      if(!found) // this is if it doesn't yet eist for some reason
      {
         state.setName(name);
         states.insertLast(state);
         System.out.println("nope: " +name);// TODO: sort this out
      }
      return state;
   }

   /***********************************************************************
    * SUBMODULE: readDistanceFile
    * IMPORTS: the file name, list of locations
    * EXPORTS: a graph
    * ASSERTION: redaing the file to build the graph
    * ********************************************************************/
   public static DSAGraph<Location> readDistanceFile(String fileName,
                                                     DSALinkedList<Location> locations)
   {
      FileInputStream fileStrm = null;
      InputStreamReader rdr;
      BufferedReader bufRdr;
      String line;
      DSAGraph<Location> map = new DSAGraph<>();

      try
      {
         fileStrm = new FileInputStream(fileName);
         rdr = new InputStreamReader(fileStrm);
         bufRdr = new BufferedReader(rdr);

         line = bufRdr.readLine();
         while(line != null)
         {
            processDistance(line, map);
            line = bufRdr.readLine();
         }
         fileStrm.close();
      }
      catch (IOException e)
      {
         if (fileStrm != null)
         {
            try
            {
               fileStrm.close();
            }
            catch (IOException ex2)
            {
            }
         }
         System.out.println("Error in file processing: " + e.getMessage());
         map = null;
      }
      return map;
   }

   /***********************************************************************
    * SUBMODULE: processDistance
    * IMPORTS: the file name
    * EXPORTS: a linked list of locations
    * ASSERTION: redaing the file and places it's contents in a linked list
    * ********************************************************************/
   private static void processDistance(String line, DSAGraph<Location> map)
   {
      String[] keys = new String[2];
      double weight;
      int timeWeight, pkTime;
      String mode;
      String delims = ",|:|=";

      String[] tokens = line.split(delims);

      keys[0] = tokens[0];
      keys[1] = tokens[3];
      mode = tokens[6];
      timeWeight = (Integer.parseInt(tokens[8])*60) + (Integer.parseInt(tokens[9])); // time is converted to just
      if(!tokens[7].equals("")) // this is is there is mo weight                                                     // minutes for storage
      {
         weight = Double.parseDouble(tokens[7]);
      }
      else
      {
         weight = Double.MAX_VALUE;
      }

      if(tokens.length < 11) // this is if there is no peak time
      {
         pkTime = 0;
      }
      else
      {
         pkTime = (Integer.parseInt(tokens[11])*60) + (Integer.parseInt(tokens[12]));
      }

      DSAGraphNode<Location> nodes1 = new DSAGraphNode<>(keys[0]); // this is where all that
      DSAGraphNode<Location> nodes2 = new DSAGraphNode<>(keys[1]); // information gets inserted
                                                                   // into the graph
      map.addVertex(nodes1);
      map.addVertex(nodes2);
      map.addEdge(nodes1, nodes2, weight, timeWeight, mode, pkTime);
      map.addEdge(nodes2, nodes1, weight, timeWeight, mode, pkTime);
   }

   /***********************************************************************
    * SUBMODULE: writeToSerialMap
    * IMPORTS: the file name and a graph
    * EXPORTS: void
    * ASSERTION: Serializes the map
    * ********************************************************************/
   public static void writeToSerialMap(String fileName, DSAGraph<Location> map)
   {
      FileOutputStream fileStrm;
      ObjectOutputStream objStrm;

      try
      {
         fileStrm = new FileOutputStream(fileName);
         objStrm = new ObjectOutputStream(fileStrm);
         objStrm.writeObject(map);

         objStrm.close();
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw new IllegalArgumentException("Unable to save object to file");
      }
   }

   /***********************************************************************
    * SUBMODULE: readSerialMap
    * IMPORTS: the file name
    * EXPORTS: a graph
    * ASSERTION: read a serilised graph
    * ********************************************************************/
   @SuppressWarnings("unchecked")
   public static DSAGraph<Location> readSerialMap(String filename)
   throws IllegalArgumentException
   {
      FileInputStream fileStrm;
      ObjectInputStream objStrm;
      DSAGraph<Location> map;

      try
      {
         fileStrm = new FileInputStream(filename);
         objStrm = new ObjectInputStream(fileStrm);
         map = (DSAGraph<Location>)objStrm.readObject();
         objStrm.close();
      }
      catch (ClassNotFoundException e)
      {
         e.printStackTrace();
         System.out.println("Name Class not found" + e.getMessage());
         map = null;
         throw new IllegalArgumentException("no class");
      }
      catch (Exception e)
      {
         e.printStackTrace();
         map = null;
         throw new IllegalArgumentException("Unable to load object from file");
      }
      return map;
   }

   /***********************************************************************
    * SUBMODULE: writeToSerialLocations
    * IMPORTS: the file name and a list of locations
    * EXPORTS: void
    * ASSERTION: Serializes the a list of locations
    * ********************************************************************/
   public static void writeToSerialLocations(String fileName,
                                             DSALinkedList<Location> locations)
   {
      FileOutputStream fileStrm;
      ObjectOutputStream objStrm;

      try
      {
         fileStrm = new FileOutputStream(fileName);
         objStrm = new ObjectOutputStream(fileStrm);
         objStrm.writeObject(locations);

         objStrm.close();
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw new IllegalArgumentException("Unable to save object to file");
      }
   }

   /***********************************************************************
    * SUBMODULE: readSerialLocations
    * IMPORTS: the file name
    * EXPORTS: a list of locations
    * ASSERTION: read a serilised linked list
    * ********************************************************************/
   @SuppressWarnings("unchecked")
   public static DSALinkedList<Location> readSerialLocations(String filename)
   throws IllegalArgumentException
   {
      FileInputStream fileStrm;
      ObjectInputStream objStrm;
      DSALinkedList<Location> locations;

      System.out.println("=======================" +filename +"==================");

      try
      {
         fileStrm = new FileInputStream(filename);
         objStrm = new ObjectInputStream(fileStrm);
         locations = (DSALinkedList<Location>)objStrm.readObject();
         objStrm.close();
      }
      catch (ClassNotFoundException e)
      {
         e.printStackTrace();
         System.out.println("Name Class not found" + e.getMessage());
         locations = null;
         throw new IllegalArgumentException("no class");
      }
      catch (Exception e)
      {
         e.printStackTrace();
         locations = null;
         throw new IllegalArgumentException("Unable to load object from file");
      }
      return locations;
   }

}
