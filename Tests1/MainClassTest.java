package Tests1;

import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass{
    @Test
    public void testGetClassNumber(){

        int number = getClassNumber();
        boolean result = number>45;

        if (result)
            testMessage("Test passed, method getClassNumber returns a number greater than 45 :) ");
        else
            Assert.fail("Test failed, method getClassNumber does not return a number greater than 45 :(");
    }
    private void testMessage(String message){
        System.out.println(message);
    }
}
