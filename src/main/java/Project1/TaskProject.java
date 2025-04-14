package Project1;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.swing.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class TaskProject {
    WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://todomvc.com/examples/react/dist/");
    }

    @Test
    public void testAddTask() {
        WebElement inputField = driver.findElement(By.className("new-todo"));
        inputField.sendKeys("Buy Milk");
        inputField.sendKeys(Keys.ENTER);

        WebElement task = driver.findElement(By.xpath("//label[text()='Buy Milk']"));
        Assert.assertTrue(task.isDisplayed(), "Task 'Buy Milk' should be visible on the list.");
    }

    @AfterClass
    public void tearDown() {
       // driver.quit();
    }

    @Test
    public void markTask(){
        WebElement inputField = driver.findElement(By.className("new-todo"));
        inputField.sendKeys("Take out trash");
        inputField.sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//input[@class='toggle'][1]")).click();
        WebElement checkCompleted = driver.findElement(By.className("completed"));
        Assert.assertTrue(checkCompleted.isDisplayed());
    }

    @Test(dependsOnMethods = {"markTask"})
    public void deleteTask(){
        WebElement delete = driver.findElement(By.className("destroy"));
        delete.click();
        boolean isELementPresent = driver.findElements(By.xpath("//label[text()='Take out trash']")).isEmpty();
        Assert.assertTrue(isELementPresent);
    }

    @Test(dependsOnMethods = {"testAddTask"})
    public void editTask() throws InterruptedException {
        Actions act = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement task = driver.findElement(By.xpath("//label[text()='Buy Milk']"));
        act.doubleClick(task).click().sendKeys(Keys.DELETE).sendKeys("Buy Almond Milk").sendKeys(Keys.ENTER).perform();
    }

    @Test
    public void addMultiple(){
        List<String> tasks = new ArrayList<>();
        tasks.add("Gym");
        tasks.add("Groceries");
        tasks.add("Study");
        WebElement inputField = driver.findElement(By.className("new-todo"));

        for(int i =0; i<tasks.size(); i++){
            inputField.sendKeys(tasks.get(i));
            inputField.sendKeys(Keys.ENTER);
        }

        List<WebElement> things = driver.findElements(By.xpath("//ul[@class='todo-list']/li"));

        for(int i = 0; i< things.size(); i++){
            if(things.get(i).getText().equals("Groceries")){
                WebElement checkbox = things.get(i).findElement(By.cssSelector("input.toggle"));
                checkbox.click();
                break;
            }
        }

        WebElement completedTask = driver.findElement(By.xpath("//label[text()='Groceries']"));
        Assert.assertTrue(completedTask.isDisplayed(), "Groceries should be in the Completed list.");


        WebElement active = driver.findElement(By.linkText("Active"));
        active.click();

        WebElement completed = driver.findElement(By.linkText("Completed"));
        completed.click();

        WebElement all = driver.findElement(By.linkText("All"));
        all.click();

    }

    @Test(dependsOnMethods = {"addMultiple"})
    public void clearTask(){
        WebElement clearCompleted = driver.findElement(By.className("clear-completed"));
        clearCompleted.click();
        List<WebElement> deletedTask = driver.findElements(By.xpath("//label[text()='Groceries']"));
        Assert.assertTrue(deletedTask.isEmpty(), "'Groceries' should be deleted after clearing completed tasks");
    }







}







