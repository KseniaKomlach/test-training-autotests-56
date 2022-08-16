package Tests1;

import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass{
    @Test
    public void testGetClassString(){

        String class_string = getClassString();
        boolean result = (class_string.contains("hello")||class_string.contains("Hello"));

        if (result)
            testMessage("Test passed!");
        else
            Assert.fail("Test failed, the string does not contain 'hello' or 'Hello'!");
    }
    private void testMessage(String message){
        System.out.println(message);
    }
}
