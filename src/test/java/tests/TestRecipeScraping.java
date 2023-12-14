package tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import base.BaseTest;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TestRecipeScraping extends BaseTest {
    @Test
    public void testScrapingEcommerce() throws IOException {
        //Navigate to tarladala website
        driver.get("https://tarladalal.com/");

        //maximize the browser window
        driver.manage().window().maximize();

        //wait for element to be visible and then click on it
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='ctl00_txtsearch']")));
        driver.findElement(By.xpath("//input[@id='ctl00_txtsearch']")).sendKeys("diabetes");

        //clicking search button
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        //wait for diabetes recipes element to be visible and then click on it
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),\"Diabetes and Healthy \")]")));
        driver.findElement(By.xpath("//a[contains(text(),\"Diabetes and Healthy \")]")).click();

        //wait for all recipes to load on the screen
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='recipelist']")));

        //fetch and store the reference to all recipes parent web element
        WebElement recipeList = driver.findElement(By.xpath("//*[@class='recipelist']"));
        List < WebElement > recipesLinks = recipeList.findElements(By.xpath("//article//div/span/a"));

        // arraylist to store all the links in string form 
        ArrayList < String > links = new ArrayList < > (24);

        //traverse the list of web elements to scrap the required data
        for (WebElement link: recipesLinks) {
            String href = link.getAttribute("href");
            System.out.println(link.getAttribute("href"));
            links.add(href);
            break;

        }

        for (String link: links) {
            Document doc = Jsoup.connect(link).get();
            Elements prepTimeElement = doc.selectXpath("//time[1]");
            // WebElement prepTimeElement = driver.findElement(By.xpath("//time[1]"));
            String prepTime = prepTimeElement.text();
            System.out.println("Prep time is: " + prepTime);
            Elements cookingTimeElement = doc.selectXpath("//time[2]");
            String cookTime = cookingTimeElement.text();
            System.out.println("Cook time is: " + cookTime);
            Elements preparationMethod = doc.selectXpath("//div[@id='recipe_small_steps']");
            String prepMethod = preparationMethod.text();
            System.out.println(prepMethod);
            Elements ingredientList = doc.selectXpath("//div[@id='rcpinglist']");
            String ingredients = ingredientList.text();
            System.out.println(ingredients);
            Elements reciepe = doc.selectXpath("//*[@id='ctl00_cntrightpanel_imgRecipe']");
            String reciepeName = reciepe.attr("alt");
            System.out.println(reciepeName);
            Elements nutrients = doc.select("table#rcpnutrients");
            System.out.println(nutrients.text());

            break;

        }

    }
}