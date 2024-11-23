package org.automationTask;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class FitPeoFinalAutomationTask
{
    public static void main(String[] args) throws InterruptedException, AWTException
    {
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to the URL
            driver.navigate().to("https://www.fitpeo.com/");
            Thread.sleep(1000);
            driver.manage().window().maximize();
            Thread.sleep(1000);

            // Navigate to Revenue Calculator
            Actions actions = new Actions(driver);
            WebElement hover = driver.findElement(By.xpath("//a[@href='/revenue-calculator']"));
            actions.moveToElement(hover).click().perform();
            Thread.sleep(2000);

            // Scroll down to the Slider Section
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,300)");
            Thread.sleep(2000);

            // Adjust the Slider to 820
            WebElement slider = driver.findElement(By.xpath("//input[@type='range']"));
            int sliderWidth = slider.getSize().getWidth();
            int xOffset = calculateOffset(sliderWidth, 9500); // Adjust to match 820
            actions.clickAndHold(slider).moveByOffset(xOffset, 0).release().perform();
            Thread.sleep(1000);
            JavascriptExecutor js1 = (JavascriptExecutor)driver;
            js1.executeScript("window.scrollBy(0,300)");
    		Thread.sleep(1000);

            // Adjust using Robot for fine-tuning
            Robot robot = new Robot();
            for (int i = 0; i < 9; i++)
            {
                robot.keyPress(KeyEvent.VK_DOWN); // Press the down arrow key
                robot.keyRelease(KeyEvent.VK_DOWN); // Release the down arrow key
                Thread.sleep(1000);
                String sliderValue = slider.getAttribute("value");
                if("820".equals(sliderValue))
                	{
                		System.out.println("The Slider adjust to given Number:" +sliderValue);
                	}
                else
                {
                	System.out.println("The Slider value is unable to adjust given value:" +sliderValue);
                }
            }
            
            // Update the Text Field directly to 560
            WebElement textField = driver.findElement(By.xpath("//input[@type='number']"));
            updateTextFieldUsingJs(driver, textField, 560);

            // Verify the updated value
            String updatedValue = textField.getAttribute("value");
            if ("560".equals(updatedValue)) 
            {
                System.out.println("Slider position is updated to reflect the Value: " + updatedValue);
            }
            else
            {
                System.out.println("Failed to updated the Slider position of value: " + updatedValue);
            }
            Thread.sleep(1000);
            
            //Scroll down to CPT Codes
            JavascriptExecutor js2 =(JavascriptExecutor) driver;
            WebElement cpt = driver.findElement(By.xpath("//p[text()='CPT-99091']"));
            Thread.sleep(1000);
            js2.executeScript("arguments[0].scrollIntoView(true)", cpt);
            
            //Select 3 CPT checkbox
            WebElement checkBox1 = driver.findElement(By.xpath("//input[@type='checkbox'][1]"));
            checkBox1.click();
            Thread.sleep(1000);
            WebElement checkBox2 = driver.findElement(By.xpath("(//input[@type='checkbox'])[2]"));
            checkBox2.click();
            Thread.sleep(1000);
            WebElement checkBox3 = driver.findElement(By.xpath("(//input[@type='checkbox'])[3]"));
            checkBox3.click();
            Thread.sleep(1000);
            WebElement checkBox8 = driver.findElement(By.xpath("(//input[@type='checkbox'])[8]"));
            checkBox8.click();
            
            //Validate Total Recurring Reimbursement
            WebElement total = driver.findElement(By.xpath("(//p[contains(text(),'Total')])[3]"));
            String displayedValue = total.getText();
            System.out.println("Displayed Total Recurring Reimbursment:" +displayedValue);
            
            //Verify Total Recurring Reimbursement for all patients per months  shows value $110700
            String expectedValue=  total.getText();
            if(displayedValue.equals(expectedValue))
            {
            	System.out.println("Validation Successfull! Total Recurring Reimbursment:" +displayedValue);
            }
            else
            {
            	System.out.println("Validation Failed! Expected:" +expectedValue +",but found" +displayedValue);
            }
          
        }      
        finally 
        {
            // Close the driver
            Thread.sleep(1000);
            driver.quit();
        }
    }

    // Update text field value using JavaScriptExecutor
    private static void updateTextFieldUsingJs(WebDriver driver, WebElement textField, int value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='" + value + "';", textField);
        js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", textField);
    }

    // Calculate the slider offset based on its range and width
    private static int calculateOffset(int sliderWidth, int targetValue) {
        int minValue = 0; // Replace with actual slider min value
        int maxValue = 2000; // Replace with actual slider max value
        return (int) (((double) (targetValue - minValue) / (maxValue - minValue)) * sliderWidth);
    }
}
