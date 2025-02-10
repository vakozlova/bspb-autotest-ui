package com.autotest.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Время ожидания
        this.actions = new Actions(driver);
    }

    // Метод для открытия главной страницы
    public void open() {
        driver.get("https://bspb.ru/");
    }

    // Метод для проверки отображения ссылки клиента
    public boolean isClientLinkVisible() {
        WebElement clientLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[text()='Частным клиентам']")));
        return clientLink.isDisplayed();
    }

    // Метод для клика по кнопке "Войти"
    public void clickLoginButton() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Войти']\n")));
        loginButton.click();
    }

    // Метод для перехода на страницу "Интернет-банк ФЛ"
    public void openInternetBankPage() {
        // Ожидание появления и кликабельности ссылки "Интернет-банк ФЛ"
        WebElement internetBankLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[text()='Интернет-банк ФЛ']\n")));
        internetBankLink.click();
    }

    // Метод для переключения на новую вкладку
    public void switchToNewTab(String originalWindow) {
        // Ожидание открытия новой вкладки
        wait.until(driver -> driver.getWindowHandles().size() > 1);

        // Переключение на новую вкладку
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }

    // Метод для проверки URL страницы
    public void checkCurrentUrl(String expectedUrl) {
        wait.until(ExpectedConditions.urlContains(expectedUrl));
        System.out.println("Текущий URL: " + driver.getCurrentUrl());
    }

    // Метод для авторизации в "Интернет-банке ФЛ"
    public void loginToInternetBank(String username, String password) {
        WebElement usernameField = driver.findElement(By.xpath("//input[@name='phoneNumber']\n"));
        usernameField.sendKeys(username);

        WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']\n"));
        passwordField.sendKeys(password);

        WebElement continueButton = driver.findElement(By.xpath("//span[text()='Продолжить']"));
        continueButton.click();
    }

    // Метод для проверки успешной авторизации
    public boolean isLoginSuccessful() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[text()='Код из СМС']")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Метод для закрытия новой вкладки и возврата к исходной
    public void closeNewTabAndReturn(String originalWindow) {
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    // Метод для перехода на страницу "Кабинет заёмщика"
    public void openBorrowerCabinetPage() {
        WebElement borrowerCabinetLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(@class, 'css-1etso2o') and text()='Кабинет заемщика']")));
        borrowerCabinetLink.click();
    }

    // Метод для перехода на страницу "Интернет-банк i2B"
    public void openInternetBankI2bPage() {
        WebElement internetBankI2bLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[text()='Интернет-банк i2B']\n")));
        internetBankI2bLink.click();
    }

    // Метод для перехода на страницу "Расчётный счёт"
    public void openCurrentAccountPage() {
        WebElement currentAccountLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[text()='Расчётный счёт']")));
        currentAccountLink.click();
    }

    // Метод для клика по кнопке "Посмотреть на карте"
    public void clickViewOnMapButton() {
        WebElement viewOnMapButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[text()='Посмотреть на карте']\n")));
    }

    // Метод для клика по кнопке "Купить валюту"
    public void clickBuyCurrencyButton() {
        WebElement buyCurrencyButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[text()='Купить валюту']")));
    }

        // Метод для поиска вкладки "Ипотека"
    public void openMortgageTab() {
        WebElement mortgageTab = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(text(), 'Ипотека')]")));

        // Скроллим к элементу, чтобы он был в зоне видимости
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", mortgageTab);

        // Дополнительно ждем, пока элемент станет кликабельным
        wait.until(ExpectedConditions.elementToBeClickable(mortgageTab));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", mortgageTab);
    }

    // Метод для проверки наличия заголовка "Семейная ипотека"
    public boolean isFamilyMortgageHeaderVisible() {
        WebElement familyMortgageHeader = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h2[contains(text(), 'Семейная ипотека')]")));
        return familyMortgageHeader.isDisplayed();
    }

    // Метод для клика по кнопке "Оформить"
    public void clickApplyMortgageButton() {
        WebElement applyButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[text()='Оформить' and contains(@href, 'calculator/mortgage')]")));

        actions.moveToElement(applyButton).perform();
        wait.until(ExpectedConditions.elementToBeClickable(applyButton));
        applyButton.click();
    }

    // Метод для проверки, что открылось окно оформления ипотеки
    public boolean isMortgageApplicationPageOpened() {
        return wait.until(ExpectedConditions.urlContains("online-bspb.ru/calculator/mortgage"));
    }

    public void openCreditTab() {
        WebElement creditTab = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[text()='Кредит']\n"))); // XPath вкладки "Кредит"

        // Прокрутка страницы с помощью JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", creditTab);

        // Ждем, пока вкладка станет кликабельной
        wait.until(ExpectedConditions.elementToBeClickable(creditTab));

        // Кликаем по вкладке
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", creditTab);
    }

    // Метод для проверки наличия заголовка вкладки "Потребительский кредит"
    public boolean isCreditHeaderVisible() {
        WebElement creditHeader = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h2[text()='Потребительский кредит']\n"))); // XPath заголовка "Потребительский кредит"
        return creditHeader.isDisplayed();
    }

    // Метод для поиска кнопки "Оформить"
    public WebElement findCreditApplyButton() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(@href, 'calculator/credit')]"))); // XPath кнопки
    }

    // Метод для клика по кнопке "Оформить"
    public void clickCreditApplyButton() {
        WebElement applyButton = findCreditApplyButton();
        actions.moveToElement(applyButton).perform();  // Прокручиваем страницу до кнопки
        wait.until(ExpectedConditions.elementToBeClickable(applyButton));  // Ждем кликабельности
        applyButton.click();
    }

    // Метод для проверки, что открылось окно оформления кредита
    public boolean isCreditApplicationPageOpened() {
        return wait.until(ExpectedConditions.urlContains("online-bspb.ru/calculator/credit"));
    }
