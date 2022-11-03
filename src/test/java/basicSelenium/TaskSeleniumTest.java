package basicSelenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Date;

public class TaskSeleniumTest {

    WebDriver driver;

    @BeforeEach
    public void setup(){
        System.setProperty("webdriver.chrome.driver","src/test/resources/driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://todo.ly/");
    }

    @AfterEach
    public void cleanup(){
        driver.quit();
    }

    @Test
    public void verifyCRUDProject() throws InterruptedException {

        // LOGIN
        driver.findElement(By.xpath("//img[contains(@src,'pagelogin')]")).click();
        driver.findElement(By.id("ctl00_MainContent_LoginControl1_TextBoxEmail")).sendKeys("jperez.t3st@gmail.com");
        driver.findElement(By.id("ctl00_MainContent_LoginControl1_TextBoxPassword")).sendKeys("123456");
        driver.findElement(By.id("ctl00_MainContent_LoginControl1_ButtonLogin")).click();
        Thread.sleep(1000);
        Assertions.assertTrue(driver.findElement(By.id("ctl00_HeaderTopControl1_LinkButtonLogout")).isDisplayed()
                ,"ERROR login was incorrect");

        // CREATE PROJECT
        String nameProject="MaribelProject"+new Date().getTime();
        driver.findElement(By.xpath("//td[text()='Add New Project']")).click();
        driver.findElement(By.id("NewProjNameInput")).sendKeys(nameProject);
        driver.findElement(By.id("NewProjNameButton")).click();
        Thread.sleep(1000);
        int actualResult=driver.findElements(By.xpath(" //td[text()='"+nameProject+"'] ")).size();
        Assertions.assertTrue(actualResult >= 1
                ,"ERROR The project was not created");

        // CREATE TASK
        String nameTask="Task Maribel";
        driver.findElement(By.id("NewItemContentInput")).sendKeys(nameTask);
        driver.findElement(By.id("NewItemAddButton")).click();
        Thread.sleep(1000);
        actualResult=driver.findElements(By.xpath("//div[text()='"+nameTask+"']")).size();
        Assertions.assertTrue(actualResult >= 1
                ,"ERROR The TASK was not created");

        // UPDATE TASK
        driver.findElement(By.xpath("//td[contains(@class,'ItemContent')]//div[text()='"+nameTask+"']")).click();
        nameTask=nameTask + new Date().getTime();
        driver.findElement(By.xpath("//td//div/textarea[@id='ItemEditTextbox']")).clear();
        driver.findElement(By.xpath("//td//div/textarea[@id='ItemEditTextbox']")).sendKeys(nameTask);
        Thread.sleep(2000);
        actualResult=driver.findElements(By.xpath("//td[contains(@class,'ItemContent')]//div[text()='"+nameTask+"']")).size();
        Assertions.assertTrue(actualResult >= 1
                ,"ERROR The TASK was not updated");

    }
}
