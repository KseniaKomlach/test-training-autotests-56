package Tests1;

import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass{
    @Test
    public void testGetLocalNumber(){

        int number = getLocalNumber();
        boolean result = number==14;

        if (result)
            testMessage("Test passed, method getLocalNumber returns 14!");
        else
            Assert.fail("Test failed, method getLocalNumber returns wrong number!");
    }
    private void testMessage(String message){
        System.out.println(message);
    }
}
