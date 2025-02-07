package com.autotest.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import com.autotest.pages.HomePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UiTests {
    private WebDriver driver;
    private HomePage homePage;

    @BeforeAll
    void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-extensions");

        driver = new org.openqa.selenium.chrome.ChromeDriver(options);
        homePage = new HomePage(driver);
    }

    @BeforeEach
    void openHomePage() {
        homePage.open();
    }

    @AfterAll
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Проверка правильного отображения главной страницы")
    void testClientLinkVisible() {
        assertTrue(homePage.isClientLinkVisible(), "Главная страница неправильно отображается");
    }

    @Test
    @DisplayName("Проверка перехода на страницу 'Интернет-банк ФЛ'")
    void testInternetBankNavigation()  {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String originalWindow = driver.getWindowHandle();

        homePage.clickLoginButton();
        homePage.openInternetBankPage();
        homePage.switchToNewTab(originalWindow);
        homePage.checkCurrentUrl("i.bspb.ru");

        homePage.closeNewTabAndReturn(originalWindow);
    }

    @Test
    @DisplayName("Проверка формы входа в 'Интернет-банк ФЛ'")
    void testInternetBankLogin() {
        HomePage homePage = new HomePage(driver);

        homePage.clickLoginButton();
        homePage.openInternetBankPage();
        String originalWindow = driver.getWindowHandle();
        homePage.switchToNewTab(originalWindow);
        homePage.loginToInternetBank("79009006620", "SecurePassword123");

        assertTrue(homePage.isLoginSuccessful(), "Авторизация прошла успешно!");

        homePage.closeNewTabAndReturn(originalWindow);
    }

    @Test
    @DisplayName("Проверка перехода на страницу 'Кабинет заемщика'")
    void testBorrowerCabinetNavigation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String originalWindow = driver.getWindowHandle();
        HomePage homePage = new HomePage(driver);

        homePage.clickLoginButton();
        homePage.openBorrowerCabinetPage();
        homePage.switchToNewTab(originalWindow);
        wait.until(ExpectedConditions.urlContains("online-bspb.ru"));

        System.out.println("Текущий URL: " + driver.getCurrentUrl());

        homePage.closeNewTabAndReturn(originalWindow);
    }

    @Test
    @DisplayName("Проверка перехода на страницу 'Интернет-банк i2B'")
    void testInternetBankI2bNavigation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String originalWindow = driver.getWindowHandle();
        HomePage homePage = new HomePage(driver);

        homePage.clickLoginButton();
        homePage.openInternetBankI2bPage();
        homePage.switchToNewTab(originalWindow);

        wait.until(ExpectedConditions.urlContains("i.bspb.ru"));

        System.out.println("Текущий URL: " + driver.getCurrentUrl());

        homePage.closeNewTabAndReturn(originalWindow);
    }

    @Test
    @DisplayName("Проверка перехода на страницу 'Расчётный счёт'")
    void currentAccountNavigation(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String originalWindow = driver.getWindowHandle();
        HomePage homePage = new HomePage(driver);

        homePage.clickLoginButton();
        homePage.openCurrentAccountPage();
        homePage.switchToNewTab(originalWindow);

        wait.until(ExpectedConditions.urlContains("lkul.bspb.ru"));

        System.out.println("Текущий URL: " + driver.getCurrentUrl());

        homePage.closeNewTabAndReturn(originalWindow);
    }

    @Test
    @DisplayName("5. Проверка работы кнопки 'Посмотреть на карте' (офисы)")
    void testViewOnMapButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        HomePage homePage = new HomePage(driver);

        homePage.clickViewOnMapButton();

        wait.until(ExpectedConditions.urlContains("bspb.ru"));

        System.out.println("Тест успешно пройден, кнопка 'Посмотреть на карте' работает.");
    }

    @Test
    @DisplayName("Проверка работы кнопки 'Купить валюту'")
    void testBuyCurrencyButton() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        HomePage homePage = new HomePage(driver);

        homePage.clickBuyCurrencyButton();

        wait.until(ExpectedConditions.urlContains("bspb.ru"));

        System.out.println("Тест успешно пройден, кнопка 'Купить валюту' работает.");
    }


    @Test
    @DisplayName("Проверка работы кнопки 'Написать в чат'")
    void testChatButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);

        WebElement chatButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"app-wrapper\"]/main/div/div[9]/div/div/div[1]/a")));

        actions.moveToElement(chatButton).perform();

        wait.until(ExpectedConditions.elementToBeClickable(chatButton));
        chatButton.click();

        wait.until(ExpectedConditions.urlContains("bspb.ru")); // Подставь часть URL, который открывается

        System.out.println("Тест успешно пройден, кнопка 'Написать в чат' работает.");
    }

    @Test
    @DisplayName("Проверка оформления ипотеки через главную страницу")
    void testMortgageApplicationButton() {
        homePage.openMortgageTab();
        assertTrue(homePage.isFamilyMortgageHeaderVisible(), "Страница ипотеки не загрузилась!");

        homePage.clickApplyMortgageButton();
        assertTrue(homePage.isMortgageApplicationPageOpened(), "Окно оформления ипотеки не открылось!");

        System.out.println("Тест успешно пройден: вкладка 'Ипотека' найдена, кнопка 'Оформить' работает.");
    }

    @Test
    @DisplayName("Проверка оформления кредита через главную страницу")
    void testCreditButton() {
        homePage.openCreditTab();
        assertTrue(homePage.isCreditHeaderVisible(), "Страница кредита не загрузилась!");

        homePage.clickCreditApplyButton();
        assertTrue(homePage.isCreditApplicationPageOpened(), "Окно оформления кредита не открылось!");

        System.out.println("Тест успешно пройден: вкладка 'Кредит' найдена, кнопка 'Оформить' работает.");
    }

    @AfterAll
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
