public class testFileIO
{
   public static void main(String[] args)
   {
      DSALinkedList<Location> locations;
      DSAGraph<Location> map;

      locations = FileIO.readLocationFile("locationFull.csv");
      map = FileIO.readDistanceFile("distancesFull.csv", locations);

      map.display();
      System.out.println("*************************************************");
      //  map.moveToFront("Nadi Airport");
      map.display();
      System.out.println("*************************************************");
      map.display();
   }

}
