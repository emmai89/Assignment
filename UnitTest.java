import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UnitTest
{
   private DSALinkedList<Integer> list;
   private DSALinkedList<Location> locations;
   private DSAGraph<Location> map;

   @Before
   public void setUp()
   {
      list = new DSALinkedList<>();
      locations = new DSALinkedList<>();
      map = new DSAGraph<>();
   }

   @Test
   public void testLinkedList1()
   {
      int ii;
      for(ii = 0; ii < 20; ii++)
      {
         list.insertLast(ii);
      }
      assertEquals(list.getCount(), 20);

      ii = 0;
      while(!list.isEmpty())
      {
         int test = list.removeFirst();
         assertEquals(test, ii);
         ii++;
      }
      assertEquals(list.getCount(), 0);
   }

   @Test
   public void testLinkedList2()
   {
      int ii;
      for(ii = 0; ii < 20; ii++)
      {
         list.insertFirst(ii);
      }
      assertEquals(list.getCount(), 20);

      ii = 0;
      while(!list.isEmpty())
      {
         int test = list.removeLast();
         assertEquals(test, ii);
         ii++;
      }
      assertEquals(list.getCount(), 0);
   }

   @Test
   public void FileIOLocation()
   {
      locations = FileIO.readLocationFile("location.csv");

      assertEquals(locations.peekFirst().getName(), "Perth");

      map = FileIO.readDistanceFile("distances.csv", locations);

      assertEquals(locations.getCount(), 14);
      assertEquals(map.getNodeCount(), 11);
      assertEquals(map.getEdgeCount(), 62);

      double temp = map.getEdges().peekFirst().findWeight("walk").getLength();
      assertEquals(temp, 13.1, 0.0001);

      int temp1 = map.getEdges().peekFirst().findWeight("car").getPkTime();
      assertEquals(temp1, 24);

   }

}
