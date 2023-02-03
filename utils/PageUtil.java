package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PageUtil {
    public static WebElement getElement(final WebDriver driver, final By locator, long... timeout) {
        long t = timeout.length>0 ? timeout[0]:0;
        try {
            return new WebDriverWait(driver, t)
                    .until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (StaleElementReferenceException ex) {

            return getElementOvercomingStale(driver, locator, t);
        } catch (Exception e) {
            return null;
        }
    }

    public static WebElement getElementOvercomingStale(final WebDriver driver, final By locator, long timeout) {

        try {
            return new WebDriverWait(driver, timeout).until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(locator)));
        } catch (Exception ex) {
            return null;
        }
    }

    public static List<WebElement> getElements(final WebDriver driver, final By locator, long... timeout) {
        long t = timeout.length>0 ? timeout[0]:0;
        try {
            return new WebDriverWait(driver, t)
                    .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch (Exception ex) {
            return null;
        }
    }

    public void wait(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
    }

    public static void subLog(String text) {
        System.out.println("\t"+text);
    }
}
