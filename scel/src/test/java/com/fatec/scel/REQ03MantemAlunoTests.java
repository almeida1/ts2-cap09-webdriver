package com.fatec.scel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

class REQ03MantemAlunoTests {
	private WebDriver driver;
	private Map<String, Object> vars;

	@BeforeEach
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "browserDriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://scel.herokuapp.com/login");
		driver.manage().window().maximize();
		vars = new HashMap<String, Object>();

	}

	@AfterEach
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void ct01_cadastrar_aluno_com_sucesso() {
		// ******************************************************************
		// dado que o usuario esta autenticado e autorizado
		// ******************************************************************
		driver.findElement(By.name("username")).click();
		driver.findElement(By.name("username")).sendKeys("jose");
		driver.findElement(By.name("password")).sendKeys("123");
		driver.findElement(By.cssSelector("button")).click();
		driver.findElement(By.linkText("Alunos")).click();

		// WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("css=h3:nth-child(2)")));

		// ******************************************************************
		// quando o usuario cadastra um aluno que não está cadastrado
		// ******************************************************************
		driver.findElement(By.id("ra")).click();
		driver.findElement(By.id("ra")).sendKeys("1111");
		driver.findElement(By.id("nome")).click();
		driver.findElement(By.id("nome")).sendKeys("jose");
		driver.findElement(By.id("email")).sendKeys("jose@gmail.com");
		driver.findElement(By.id("cep")).sendKeys("04280130");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();

		// ******************************************************************
		// entao o sistema apresenta as informações do aluno para consulta
		// ******************************************************************
		assertTrue(driver.getPageSource().contains("Rua Frei João"));
		// assertEquals("Rua Frei João",
		// driver.findElement(By.cssSelector("td:nth-child(6)")).getText());
		assertEquals("https://scel.herokuapp.com/sig/alunos", driver.getCurrentUrl());
		assertTrue(driver.getPageSource().contains("1111"));
		// *********************************************************************************
		// teardown - exclusao do registro
		// *********************************************************************************
		// driver.findElement(By.linkText("Excluir")).click();
		driver.findElement(By.cssSelector("tr:nth-child(2) .delete")).click();
	}

	@Test
	public void ct02_atualiza_aluno_com_sucesso() {
		// ***********************************************************************************
		// dado que o aluno esta cadastrado
		// ***********************************************************************************
		driver.findElement(By.name("username")).click();
		driver.findElement(By.name("username")).sendKeys("jose");
		driver.findElement(By.name("password")).sendKeys("123");
		driver.findElement(By.cssSelector("button")).click();
		driver.findElement(By.linkText("Alunos")).click();
		espera();
		driver.findElement(By.id("ra")).click();
		driver.findElement(By.id("ra")).sendKeys("2222");
		driver.findElement(By.id("nome")).sendKeys("jose");
		driver.findElement(By.id("email")).sendKeys("jose@gmail.com");
		driver.findElement(By.id("cep")).sendKeys("04280130");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		espera();
		assertTrue(driver.getPageSource().contains("Rua Frei João"));
		//assertEquals("Rua Frei João", driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(6)")).getText());
		// **********************************************************************************
		// quando o usuario altera o CEP do endereco
		// **********************************************************************************
		driver.findElement(By.linkText("Editar")).click();
		driver.findElement(By.cssSelector(".form-group:nth-child(2)")).click();
		driver.findElement(By.id("cep")).clear();
		driver.findElement(By.id("cep")).sendKeys("08545160");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		// entao o sistema apresenta as informações do aluno com o CEP alterado
		assertTrue(driver.getPageSource().contains("Rua João Soliman"));
		// ************************************************************************************
		// teardown - exclusao do registro
		// ***********************************************************************************
		//driver.findElement(By.linkText("Excluir")).click();
	}
	@Test
	public void ct03cadastraraluno_ja_cadastrado() {
			// ******************************************************************************
			// dado que o aluno esta cadastrado
			// ******************************************************************************
			driver.findElement(By.name("username")).click();
			driver.findElement(By.name("username")).sendKeys("jose");
			driver.findElement(By.name("password")).sendKeys("123");
			driver.findElement(By.cssSelector("button")).click();
			driver.findElement(By.linkText("Alunos")).click();
			espera();
			driver.findElement(By.id("ra")).click();
			driver.findElement(By.id("ra")).sendKeys("aaaa");
			driver.findElement(By.id("nome")).click();
			driver.findElement(By.id("nome")).sendKeys("Carlos");
			driver.findElement(By.id("email")).sendKeys("carlos@gmail.com");
			driver.findElement(By.id("cep")).sendKeys("04280130");
			driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
			// *******************************************************************************
			// entao retorna dados invalidos
			// *******************************************************************************
			espera();
			assertEquals("Dados invalidos", driver.findElement(By.cssSelector(".text-danger")).getText());
	}


	public void espera() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
