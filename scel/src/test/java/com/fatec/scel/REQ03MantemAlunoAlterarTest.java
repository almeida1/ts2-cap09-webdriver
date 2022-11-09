package com.fatec.scel;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

class REQ03MantemAlunoAlterarTest {

	private WebDriver driver;
	  private Map<String, Object> vars;
	  JavascriptExecutor js;
	  @BeforeEach
	  public void setUp() {
		  System.setProperty("webdriver.chrome.driver", "browserDriver/chromedriver.exe");
	    driver = new ChromeDriver();
	    driver.get("https://scel.herokuapp.com/login");
	    js = (JavascriptExecutor) driver;
	    vars = new HashMap<String, Object>();
	  }
	  @AfterEach
	  public void tearDown() {
	    driver.quit();
	  }
	  public String waitForWindow(int timeout) {
	    try {
	      Thread.sleep(timeout);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	    Set<String> whNow = driver.getWindowHandles();
	    Set<String> whThen = (Set<String>) vars.get("window_handles");
	    if (whNow.size() > whThen.size()) {
	      whNow.removeAll(whThen);
	    }
	    return whNow.iterator().next();
	  }
	  @Test
	  public void ct03atualizacepcomsucesso() {
	  
	    driver.findElement(By.name("username")).click();
	    driver.findElement(By.name("username")).sendKeys("jose");
	    driver.findElement(By.name("password")).sendKeys("123");
	    driver.findElement(By.cssSelector("button")).click();
	    driver.findElement(By.linkText("Alunos")).click();
	    vars.put("window_handles", driver.getWindowHandles());
	    driver.findElement(By.cssSelector(".btn:nth-child(2)")).click();
	    vars.put("win4157", waitForWindow(2000));
	    driver.switchTo().window(vars.get("win4157").toString());
	    driver.findElement(By.linkText("Editar")).click();
	    driver.findElement(By.id("cep")).click();
	    driver.findElement(By.id("cep")).clear();
	    driver.findElement(By.id("cep")).sendKeys("03694000");
	    //driver.findElement(By.cssSelector(".row:nth-child(4)")).click();
	    driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
	    assertTrue(driver.getPageSource().contains("Avenida Águia de Haia"));
	  }

}
