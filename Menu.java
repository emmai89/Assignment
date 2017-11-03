/*****************************************************************************
 * FILE: Menu.java
 * AUTHOR: Emmanuel Iloh
 * USERNAME: 18817599
 * UNIT: Data Structures and Algorithms (COMP1002)
 * PURPOSE: this is the main class that handle all the functionallity of the
 *          project
 * REFERENCE: none
 * COMMENTS: none
 * REQUIRES: It makes use of the DSALinkedList to store all the locations
 *           avaiable and a DSAGraph to create a map of all the locations
 * Last Mod: 16/10/2017
 ******************************************************************************/

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Menu
{
   /***********************************************************************
    * SUBMODULE: main
    * IMPORTS: args
    * EXPORTS: none
    * ASSERTION: the menu interface that controls teh whole program
    * ********************************************************************/
   public static void main(String args[])
   {
      DSALinkedList<Location> locations = null;
      DSAGraph<Location> map = null;

      int sec;
      Scanner sc = new Scanner(System.in);

      do
      {
         try
         {
            System.out.println("\n(1) - Travel search\n"
                               +"(2) - Location Search\n"
                               +"(3) - Nearby Search\n"
                               +"(4) - Update Data\n"
                               +"(5) - Load Data\n"
                               +"(6) - Save Data\n"
                               +"(0) - Quit\n");
            sec = sc.nextInt();

            switch (sec)
            {
            case 1:
               if (map == null || locations == null) // mothing can be done before loading
               {
                  System.out.println("please enter a map file and/or a locations file");
               }
               else
               {
                  System.out.println("Travel search");
                  travelSearch(locations, map);
               }
               break;

            case 2:
               if (map == null || locations == null)
               {
                  System.out.println("please enter a map file and/or a locations file");
               }
               else
               {
                  System.out.println("Location search");
                  locationSearch(locations);
               }
               break;

            case 3:
               if (map == null || locations == null)
               {
                  System.out.println("please enter a map file and/or a locations file");
               }
               else
               {
                  System.out.println("Nearby search\n");
                  nearby(locations, map);
               }
               break;

            case 4:
               if (map == null || locations == null)
               {
                  System.out.println("please enter a map file and/or a locations file");
               }
               else
               {
                  System.out.println("Update data chosen");
                  UpdateData(locations, map);
               }
               break;

            case 5:
               System.out.println("1) text\n2) serial\n");
               int plz = sc.nextInt();
               switch(plz)
               {
               case 1:

                  System.out.println("what is the name of the Location file?");
                  sc.nextLine();
                  String fileName1 = sc.nextLine();
                  locations = FileIO.readLocationFile(fileName1);

                  System.out.println("what is the name of the distances file?");
                  String fileName2 = sc.nextLine();
                  map = FileIO.readDistanceFile(fileName2, locations);
                  break;

               case 2:
                  System.out.println("what is the name of the Location file?");
                  sc.nextLine();
                  String fileName3 = sc.nextLine();
                  locations = FileIO.readSerialLocations(fileName3);

                  System.out.println("please enter the corresponding distances file?");
                  String fileName4 = sc.nextLine();
                  map = FileIO.readSerialMap(fileName4);

                  break;

               default:
                  System.out.println("invalid!!!!");

               }
               break;

            case 6:
               if (map == null || locations == null)
               {
                  System.out.println("please enter a map file and/or a locations file");
               }
               else
               {
                  System.out.println("what is the name of the Location file?");
                  sc.nextLine();
                  String fileName = sc.nextLine();
                  FileIO.writeToSerialLocations(fileName, locations);

                  System.out.println("please enter the corresponding distances file?");
                  String fileName1 = sc.nextLine();
                  FileIO.writeToSerialMap(fileName1, map);
               }
               break;

            case 0:
               System.out.println("BYE!");
               break;

            default:
               System.out.println("Invalid Input");
            }
         }
         catch(Exception e)
         {
            e.printStackTrace();
            System.out.println("please enter an int");
            sec = 4;
            sc.nextLine();
         }
      } while(sec != 0);
   }

   /***********************************************************************
    * SUBMODULE: locationSearch
    * IMPORTS: a list of location
    * EXPORTS: a location
    * ASSERTION: finds the location specified in the list
    * ********************************************************************/
   private static Location locationSearch(DSALinkedList<Location> locations)
   {
      Scanner sc = new Scanner(System.in);
      Location chosen = null;
      boolean active = false;
      int count = 0;

      do
      {
         count = 0;
         String text = sc.nextLine();
         System.out.println("\n==========Search Reasults For " +text +
                            "============\n");
         boolean found = false;
         active = false;
         Location[] matches = new Location[locations.getCount()];

         for (Location place : locations)
         {
            Pattern pattern = Pattern.compile("^" +text, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(place.getName());
            if(matcher.find())
            {
               matches[count] = place;
               System.out.println(count+1 +": " +place.getName());
               found = true;
               count++;
            }
         }

         if(!found)
         {
            System.out.println("it doesn't exit\n");
         }
         System.out.println("\n=================================================");

         System.out.println("Have you found your location?");
         String ch = sc.nextLine();
         if(ch.equals("y") || ch.equals("yes") || ch.equals("yep") || ch.equals("Y"))
         {
            System.out.println("which number is it?");
            int index = sc.nextInt();
            chosen = matches[index-1];
         }
         else
         {
            System.out.println("do you want to search again?");
            ch = sc.nextLine();
            if(ch.equals("y") || ch.equals("yes") || ch.equals("yep") ||
               ch.equals("Y"))
            {
               active = true;
               System.out.println("Location search");
            }
         }

      } while(active);

      if(chosen != null)
      {
         System.out.println("\n**************************************************");
         System.out.println("you have chosen: " +chosen.getName());
         System.out.println("**************************************************\n");
      }
      else
      {
         chosen = null;
      }

      return chosen;
   }

   /***********************************************************************
    * SUBMODULE: nearby
    * IMPORTS: a list of location and a graph
    * EXPORTS: none
    * ASSERTION: displays all location in a given range of one location
    * ********************************************************************/
   private static void nearby(DSALinkedList<Location> locations,
                              DSAGraph<Location> map)
   {
      Scanner sc = new Scanner(System.in);
      String mode;

      System.out.println("What location do you want to your search from?\n");
      Location location = locationSearch(locations);

      if(location == null)
      {
         System.out.println("no location chosen!!\n");
      }
      else
      {
         DSAGraphNode<Location> localNode = map.getNode(location.getName());

         System.out.println("What is the radius of your search in kms?\n");
         double range = sc.nextDouble();

         System.out.println("===========Reasults Within " +range +
                            " kms of " +location.getName() +"===========\n");

         DSALinkedList<DSAGraphNode<Location> > shortP;
         shortP = map.shortestPath(location.getName(),
                                   locations.peekFirst().getName(), true, "any"); // Dijkstra's is classed here
                                                                                  // with the nearby flag
         for (DSAGraphNode<Location> option : map.getNodes())
         {

            double dis = option.getTentDistance();
            if(dis <= range && !(location.getName().equals(option.getLabel()))) // if the location is within the
            {                                                                   // range, it's printed
               System.out.println(option.getLabel() +": " +dis +" kms");
            }
         }
         System.out.println("\n===============================================\n");

      }

   }

   /***********************************************************************
    * SUBMODULE: travelSearch
    * IMPORTS: a list of location and a graph
    * EXPORTS: none
    * ASSERTION: displays most efficent path between 2 nodes given a few factors
    * ********************************************************************/
   private static void travelSearch(DSALinkedList<Location> locations,
                                    DSAGraph<Location> map)
   {
      Scanner sc = new Scanner(System.in);
      System.out.println("What location do you want start?\n");
      Location from = locationSearch(locations);

      System.out.println("Where do you want to go to??\n");
      Location to = locationSearch(locations);

      if(to == null || from == null)
      {
         System.out.println("one or more locations not chosen!!\n");
      }
      else
      {

         System.out.println("do you want to serch by distance or time?\n"
                            +"(1) distance\n"
                            +"(2) time\n"
                            +"(0) EXIT");
         int ch = sc.nextInt();

         String mode;

         DSALinkedList<DSAGraphNode<Location> > shortP = null;
         DSAGraphNode<Location> node1 = null;
         Iterator<DSAGraphNode<Location> > iter = null;

         switch (ch)
         {
         case 1:
            mode = modeSelect();

            if(mode.equals("any"))
            {
               System.out.println("shortest distance from " +from.getName() +" to " +
                                  to.getName() +"\n");
               shortP = map.shortestPath(from.getName(), to.getName(), false, mode);

               node1 = null;
               iter = shortP.iterator();

               while(iter.hasNext())
               {
                  DSAGraphNode<Location> node2 = iter.next();
                  if(node1 != null)
                  {
                     DSAGraphEdge<Location> edge = node1.getEdge(node2.getLabel()); // this is how the path is displayed
                     int[] time = edge.getShortestLength().getConvertedTime();  // time has to be convered back to hrs:mins
                     System.out.println(edge.getForm().getLabel()
                                        +" -----> "      // the direction from one node to another is shown
                                        +edge.getTo().getLabel());
                     System.out.print(edge.getShortestLength().getMode() +" "
                                      +edge.getShortestLength().getLength() +" kms in " // distane and time are shown
                                      +time[0] +":" +time[1] +"\n\n");
                  }
                  node1 = node2; // next node selected
               }
               System.out.println(shortP.removeLast().getTentDistance() +
                                  "kms total distance"); // total distance is shown
            }
            else        // the rest of this method is slight modification of the above code
            {
               System.out.println("shortest distance from " +from.getName() +" to " +
                                  to.getName() +" by " +mode +"\n");
               shortP = map.shortestPath(from.getName(), to.getName(), false, mode);

               node1 = null;
               iter = shortP.iterator();

               while(iter.hasNext())
               {
                  DSAGraphNode<Location> node2 = iter.next();
                  if(node1 != null)
                  {
                     DSAGraphEdge<Location> edge = node1.getEdge(node2.getLabel());
                     int[] time = edge.findWeight(mode).getConvertedTime();
                     System.out.println(edge.getForm().getLabel()
                                        +" -----> "
                                        +edge.getTo().getLabel());
                     System.out.print(mode +" "
                                      +edge.findWeight(mode).getLength() +" kms in "
                                      +time[0] +":" +time[1] +"\n\n");
                  }
                  node1 = node2;
               }
               double dis = shortP.removeLast().getTentDistance();
               if(Math.abs(dis - Double.MAX_VALUE) < 0.001)
               {
                  System.out.println("path unavailble by " +mode);
               }
               else
               {
                  System.out.println(dis +
                                     "kms total distance");
               }
            }
            break;

         case 2:
            mode = modeSelect();
            System.out.println("is it peak time? \ny or n");
            sc.nextLine();
            char chPk  = sc.nextLine().charAt(0);
            boolean peak = false;

            if(chPk == 'y')
            {
               peak = true;
               System.out.println("peak chosen");
            }
            System.out.println("fastest time from " +from.getName() +" to " +
                               to.getName() +" by time" +"\n");
            try
            {
               shortP = map.shortestTimePath(from.getName(), to.getName(), peak, mode);

               iter = shortP.iterator();

               while(iter.hasNext())
               {
                  DSAGraphNode<Location> node2 = iter.next();
                  if(node1 != null)
                  {
                     DSAGraphEdge<Location> edge = node1.getEdge(node2.getLabel());
                     int[] time = new int[2];
                     if(peak)
                     {
                        if(mode.equals("any"))
                        {
                           time = edge.getShortestTime(peak).getConvertedPkTime();
                        }
                        else
                        {
                           time = edge.findWeight(mode).getConvertedPkTime();
                        }
                        System.out.println(edge.getForm().getLabel()
                                           +" -----> "
                                           +edge.getTo().getLabel());
                     }
                     else
                     {
                        if(mode.equals("any"))
                        {
                           time = edge.getShortestTime(peak).getConvertedTime();
                        }
                        else
                        {
                           time = edge.findWeight(mode).getConvertedPkTime();
                        }
                        System.out.println(edge.getForm().getLabel()
                                           +" -----> "
                                           +edge.getTo().getLabel());
                     }
                     if(mode.equals("any"))
                     {
                        System.out.print(edge.getShortestTime(peak).getMode() +" in "
                                         +time[0] +":" +time[1] +"\n\n");
                     }
                     else
                     {
                        System.out.print(mode +" in "
                                         +time[0] +":" +time[1] +"\n\n");
                     }
                  }
                  node1 = node2;
               }
               int fullTime[] = shortP.removeLast().getTentConvertedTime();
               System.out.println("the total time is: " +fullTime[0] +":" +
                                  fullTime[1]);
            }
            catch(NullPointerException e)
            {
               System.out.println("is not possible by " +mode); // some places can't be accessed by a mode
            }
            catch (Exception e)
            {
               e.printStackTrace(); // just in case
            }
            break;

         default:
            System.out.println("it's not an option");
         }
      }
   }

   /***********************************************************************
    * SUBMODULE: modeSelect
    * IMPORTS: none
    * EXPORTS: a selected mode
    * ASSERTION: prompts the user to select a mode
    * ********************************************************************/
   private static String modeSelect()
   {
      Scanner sc = new Scanner(System.in);
      String mode;
      System.out.println("what mode of transport?\n"
                         +"1 -> car\n"
                         +"2 -> cycle\n"
                         +"3 -> walk\n"
                         +"4 -> public tansport\n"
                         +"5 -> plane\n"
                         +"6 -> any\n");

      int ch = sc.nextInt();
      switch (ch)
      {
      case 1:
         mode = "car";
         break;

      case 2:
         mode = "cycle";
         break;

      case 3:
         mode = "walk";
         break;

      case 4:
         mode = "pubtrans";
         break;

      case 5:
         mode = "plane";
         break;

      case 6:
         System.out.println("any mode was chosen");
         mode = "any";
         break;

      default:
         throw new IllegalArgumentException("not a valid entry");
      }

      return mode;
   }

   /***********************************************************************
    * SUBMODULE: UpdateData
    * IMPORTS: a list of location and a graph
    * EXPORTS: none
    * ASSERTION: updats the imparement of an edge
    * ********************************************************************/
   private static void UpdateData(DSALinkedList<Location> locations,
                                  DSAGraph<Location> map)
   {
      Scanner sc = new Scanner(System.in);
      Location place1;
      String place2, mode;

      System.out.println("what is the starting location?");
      place1 = locationSearch(locations);

      if(place1 == null)
      {
         System.out.println("no location chosen!!");
      }
      else
      {
         DSALinkedList<Location> options = new DSALinkedList<>();
         String[] matches = new String[locations.getCount()];
         int count = 0;
         for (DSAGraphNode<Location> n : map.getNode(place1.getName()).getAdjacent())
         {

            matches[count] = n.getLabel();
            System.out.println(count+1 +": " +n.getLabel()); // all adjacent nodes are displayed
            count++;
         }

         System.out.println("what is the secound location?");
         System.out.println("which number is it?");
         int index = sc.nextInt();
         place2 = matches[index-1];

         DSAGraphEdge<Location> edge = map.getEdge(place1.getName() + place2);

         mode = modeSelect();
         int[] time = edge.findWeight(mode).getConvertedTime();
         System.out.println("\nthe old time from " +place1.getName() +" to " +place2
                            +" is " +time[0] +":" +time[1] +"\n"); // shows the time bore
         System.out.println("what is the imparement, from 0 - 100"); // TODO: add try catch?
         double impare = sc.nextDouble()/100;

         edge.findWeight(mode).setImpare(impare);

         time = edge.findWeight(mode).getConvertedTime();
         System.out.println("\nthe new time from " +place1.getName() +" to " +place2
                            +" is " +time[0] +":" +time[1] +"\n"); // and the time after
      }
   }

}
