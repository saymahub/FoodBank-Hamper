// ENSF 409 Final project - Group 62
package edu.ucalgary.ensf409;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

import java.beans.Transient;

public class FoodBankTest {
  private final int CLIENTID = 4;
  private final String[] TEST_MEMBERS = {
                  "Michie, adult female, Gender: Female",
                  "Ewa, adult female, Gender, Female", 
                  "Shadow, Age: under eight, Gender: Male",
                  "Bubby, adult male, Gender, Male",
                  "Mom, adult female, Gender: Female,"
              };
  private final String[] TEST_INVENTORY = {"tomatoes", "cabbage", "marmalade", "brownie", "guava"}; 
  private final String[] TEST_HAMPER = {"21, Watermelon, large", "23, Roast Beef, one pot", "27, Cereal, one box", "32, Bananas, dozen"};
  private final DailyNeeds NEEDS = new DailyNeeds(29, 17, 34, 11);

   @Test
   public void testSetGetFamSize(){
     int givenData = 4;
     Members members = new Members();
     members.setFamSize(givenData);
     String actual = members.getFamSize();
     assertEquals("Family size did not match what was expected", givenData, actual);
   }

    @Test
    public void testSetGetAdultMale(){
      int givenData = 24;
      Members members = new Members();
      members.setAdultMale(givenData);
      String actual = members.getAdultMale();
      assertEquals("Age did not match what was expected", givenData, actual);
    }
  
  @Test
    public void testViewItems(){
        String givenData = TEST_INVENTORY;
        Inventory inventory = new Inventory();
        inventory.setItems(givenData);
        String actual = inventory.viewItems();
        assertEquals("Inventory did not match what was expected", givenData, actual);
        assertTrue("Inventory is empty.", inventory.viewItems().isEmpty());
    }     

    @Test
    public void testRemoveItems(){
        String[] removed = new String[TEST_INVENTORY.length - 1];
        int index = 1;

        for (int i = 0, j = 0; i < TEST_INVENTORY.length; i++){
            if (i == index){
                continue;
            }

            removed[j++] = TEST_INVENTORY[i];
        }

        String givenData = TEST_INVENTORY;
        Inventory inventory = new Inventory();
        inventory.setItems(givenData);

        // removeItems returns new inventory
        String actual = inventory.removeItems(TEST_INVENTORY[0]);
        assertEquals("Inventory did not remove correct item", removed, actual);
    }

@Test
    public void testPrintOrderNum(){
        int orderNum = 3;

        // member implements order form
        Members member = new Members();

        // sets count as orderNum (because every new order
        // increases the count)
        members.count(orderNum);

        // count = orderNum
        int actual = members.printOrderNum();
        assertEquals("Did not print expected order number", orderNum, actual);
    }

    @Test 
    public void testPrintMembers(){
        Members member = new Members();
        String[] print = member.printMembers(TEST_MEMBERS);
        assertNotNull("No members are being print.", print);
    }

    @Test 
    public void testPrintHamper(){
        Members member = new Members();
        String[] print = member.printHamper(TEST_HAMPER);
        assertNotNull("No order is being print.", print);
    }
}
   
