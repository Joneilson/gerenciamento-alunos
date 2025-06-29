package br.com.gerenciamento.acceptance;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class LoginAcceptanceTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/Junior/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");
        driver = new ChromeDriver();
    }
    @Test
    public void deveIrParaPaginaDeCadastro() {
        driver.get("http://localhost:8080/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement linkCadastro = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Clique aqui para se cadastrar")));
        linkCadastro.click();

        wait.until(ExpectedConditions.urlContains("/cadastro"));
        assertTrue(driver.getCurrentUrl().contains("/cadastro"));
    }

    @Test
    public void deveCadastrarUsuarioComSucesso() {
        driver.get("http://localhost:8080/cadastro");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement inputEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        WebElement inputUsername = driver.findElement(By.name("user"));
        WebElement inputSenha = driver.findElement(By.name("senha"));
        WebElement botaoCadastrar = driver.findElement(By.cssSelector("button[type='submit']"));

        inputEmail.sendKeys("novo@email.com");
        inputUsername.sendKeys("novousuario");
        inputSenha.sendKeys("senha123");
        botaoCadastrar.click();

        // Espera redirecionamento para login ou dashboard
        wait.until(ExpectedConditions.urlContains("/salvarUsuario"));
        assertTrue(driver.getCurrentUrl().contains("/salvarUsuario"));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
