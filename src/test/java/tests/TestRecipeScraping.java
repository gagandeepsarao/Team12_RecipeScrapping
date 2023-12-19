package tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static utils.Constants.MORBIDITIES;
import static utils.Constants.ALLERGICINGREDIENTS;
import static utils.Constants.PCOSRECEIPES;
import static utils.Constants.DIABETESRECEIPES;
import static utils.Constants.HYPERTHYROIDISMRECEIPES;
import static utils.Constants.HYPERTENSIONRECEIPES;
import static utils.Constants.ATOZRECEIPES;
import static utils.Constants.VEGRECEIPES;
import static utils.Constants.RECEIPEBYEQUIPMENT;
import static utils.Constants.RECEIPEBYCUISINE;
import static utils.Constants.BASICCOOKINGRECEIPES;



import org.testng.annotations.Test;
import utils.IngredientandMorbidityOperations;
import utils.RecipeWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestRecipeScraping {
    @Test
    public void testScrapingEcommerce() throws IOException {

        IngredientandMorbidityOperations ingredientData = new IngredientandMorbidityOperations();
        ingredientData.readData();
        RecipeWriter writer = new RecipeWriter();

        

        //Max page index for the morbidities for pagination
        int maxPageIndexPcos = 6;
        int maxPageIndexDiabetes = 24;
        int maxPageIndexThyroidism = 3;
        int maxPageIndexHyperTension = 5;

        //Array list to save the urls for each morbidity
        ArrayList < String > pcosReceipesLinks = new ArrayList < > ();
        ArrayList < String > diabetesReceipesLinks = new ArrayList < > ();
        ArrayList < String > hyperThyroidismReceipesLinks = new ArrayList < > ();
        ArrayList < String > hyperTensionReceipesLinks = new ArrayList < > ();
        ArrayList < String > otherReceipesLinks = new ArrayList < > ();

        //Initialize Jsoup doc
        Document doc = Jsoup.connect("https://www.tarladalal.com").timeout(100 * 1000).get();

        //Load list of all recipes for pcos and get their links and save to arrayList 
        for (int i = 1; i <= maxPageIndexPcos; i++) {
            String finalUrl = PCOSRECEIPES + i;
            doc = Jsoup.connect(finalUrl).timeout(100 * 1000).get();
            Elements receipeList = doc.selectXpath("//article//div/span/a");
            for (Element elem: receipeList) {
                pcosReceipesLinks.add(elem.attr("abs:href"));
            }
        }

        System.out.println("pcosReceipesLinks: " + pcosReceipesLinks.size());

        //Load list of all recipes for diabetes and get their links and save to arrayList 
        for (int i = 1; i <= maxPageIndexDiabetes; i++) {
            String finalUrl = DIABETESRECEIPES + i;
            doc = Jsoup.connect(finalUrl).timeout(100 * 1000).get();
            Elements receipeList = doc.selectXpath("//article//div/span/a");
            for (Element elem: receipeList) {
                diabetesReceipesLinks.add(elem.attr("abs:href"));
            }
        }

        System.out.println("diabetesReceipesLinks: " + diabetesReceipesLinks.size());

        //Load list of all recipes for hyperthyroidism and get their links and save to arrayList 
        for (int i = 1; i <= maxPageIndexThyroidism; i++) {
            String finalUrl = HYPERTHYROIDISMRECEIPES + i;
            doc = Jsoup.connect(finalUrl).timeout(100 * 1000).get();
            Elements receipeList = doc.selectXpath("//article//div/span/a");
            for (Element elem: receipeList) {
                hyperThyroidismReceipesLinks.add(elem.attr("abs:href"));
            }
        }

        System.out.println("hyperThyroidismReceipesLinks: " + hyperThyroidismReceipesLinks.size());

        ////Load list of all recipes for hyperTension and get their links and save to arrayList 
        for (int i = 1; i <= maxPageIndexHyperTension; i++) {
            String finalUrl = HYPERTENSIONRECEIPES + i;
            doc = Jsoup.connect(finalUrl).timeout(100 * 1000).get();
            Elements receipeList = doc.selectXpath("//article//div/span/a");
            for (Element elem: receipeList) {
                hyperTensionReceipesLinks.add(elem.attr("abs:href"));
            }
        }

        System.out.println("hyperTensionReceipesLinks: " + hyperTensionReceipesLinks.size());

        //Load list of all A-Z recipes and get their links and save to arrayList 
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            String finalUrlWithAlphabet = ATOZRECEIPES + ch;
            doc = Jsoup.connect(finalUrlWithAlphabet).timeout(100 * 1000).get();
            Elements el = doc.selectXpath("//*[@class='respglink']");
            String numbersString = el.text();
            if (!numbersString.isBlank()) {

                List < String > numbersArray = Arrays.asList(numbersString.split(" "));
                int maxOtherPageIndex = numbersArray.stream()
                    .mapToInt(Integer::parseInt)
                    .max()
                    .orElse(-1);

                for (int i = 1; i <= maxOtherPageIndex; i++) {

                    String finalUrl = finalUrlWithAlphabet + "&pageindex=" + i;
                    System.out.println(finalUrl);
                    doc = Jsoup.connect(finalUrl).timeout(100 * 1000).get();
                    Elements receipeList = doc.selectXpath("//*[@class='rcc_recipename']/a");
                    for (Element elem: receipeList) {
                        otherReceipesLinks.add(elem.attr("abs:href"));
                    }
                }
            }

        }

        ////Load list of all recipes for veg courses and get their links and save to arrayList 
        for (int i = 1; i <= 601; i++) {
            String finalUrl = VEGRECEIPES + i;
            System.out.println(finalUrl);
            doc = Jsoup.connect(finalUrl).timeout(100 * 1000).get();
            Elements receipeList = doc.selectXpath("//article//div/span/a");
            for (Element elem: receipeList) {
                otherReceipesLinks.add(elem.attr("abs:href"));
            }
        }

        //Load list of all recipes by equipment to be used and get their links and save to arrayList 
        for (int i = 1; i <= 282; i++) {
            String finalUrl = RECEIPEBYEQUIPMENT + i;
            System.out.println(finalUrl);
            doc = Jsoup.connect(finalUrl).timeout(100 * 1000).get();
            Elements receipeList = doc.selectXpath("//article//div/span/a");
            for (Element elem: receipeList) {
                otherReceipesLinks.add(elem.attr("abs:href"));
            }
        }

        //Load list of all recipes by cuisine and get their links and save to arrayList 
        for (int i = 1; i <= 287; i++) {
            String finalUrl = RECEIPEBYCUISINE + i;
            System.out.println(finalUrl);
            doc = Jsoup.connect(finalUrl).timeout(100 * 1000).get();
            Elements receipeList = doc.selectXpath("//article//div/span/a");
            for (Element elem: receipeList) {
                otherReceipesLinks.add(elem.attr("abs:href"));
            }
        }

        ////Load list of all recipes for basic cooking and get their links and save to arrayList 
        for (int i = 1; i <= 164; i++) {
            String finalUrl = BASICCOOKINGRECEIPES + i;
            System.out.println(finalUrl);
            doc = Jsoup.connect(finalUrl).timeout(100 * 1000).get();
            Elements receipeList = doc.selectXpath("//article//div/span/a");
            for (Element elem: receipeList) {
                otherReceipesLinks.add(elem.attr("abs:href"));
            }
        }

        //removing duplicate urls from the lists to make the arraylist unique
        List < String > uniquePcosReceipesLinks = pcosReceipesLinks.stream()
            .distinct()
            .collect(Collectors.toList());

        List < String > uniqueDiabetesReceipesLinks = diabetesReceipesLinks.stream()
            .distinct()
            .collect(Collectors.toList());

        List < String > uniqueHyperThyroidismReceipesLinks = hyperThyroidismReceipesLinks.stream()
            .distinct()
            .collect(Collectors.toList());

        List < String > uniqueHyperTensionReceipesLinks = hyperTensionReceipesLinks.stream()
            .distinct()
            .collect(Collectors.toList());

        List < String > uniqueOtherReceipesLinks = otherReceipesLinks.stream()
            .distinct()
            .collect(Collectors.toList());

        //Creating hashmap with morbidity as key and url list of that morbidity as value
        HashMap < String, ArrayList < String >> links = new HashMap();

        links.put("PCOS", (ArrayList < String > ) uniquePcosReceipesLinks);
        links.put("Diabetes", (ArrayList < String > ) uniqueDiabetesReceipesLinks);
        links.put("HyperThyroidism", (ArrayList < String > ) uniqueHyperThyroidismReceipesLinks);
        links.put("HyperTension", (ArrayList < String > ) uniqueHyperTensionReceipesLinks);
        links.put("Others", (ArrayList < String > ) uniqueOtherReceipesLinks);


        Iterator it = links.entrySet().iterator();
        int total = 0;
        ArrayList < String > writtenRecipeId = new ArrayList < > ();
        ArrayList < String > morbidities = new ArrayList < > (MORBIDITIES);


        ArrayList < String > allergicIngredientsArrayList = new ArrayList < > (ALLERGICINGREDIENTS);

        //iterate through hashmap of morbidity and links
        while (it.hasNext()) {
            @SuppressWarnings("rawtypes")
            Map.Entry pair = (Map.Entry) it.next();
            String morbidity = pair.getKey().toString();

            //loop through all the links for particular morbidity 
            for (String link: (ArrayList < String > ) pair.getValue()) {
                boolean linkProcessed = false;
                int retryCount = 0;
                //try load link 3 times else skip that link
                while (!linkProcessed && retryCount < 3) {
                    try {

                        String receipeIdwithSuffix = link.substring(link.lastIndexOf('-') + 1);
                        String receipeId = receipeIdwithSuffix.replaceAll("[^0-9]", "");
                        System.out.println("receipeId is: " + receipeId);
                        
                        // skip receipe if already written
                        if (!writtenRecipeId.contains(receipeId)) {
                            writtenRecipeId.add(receipeId);
                            doc = Jsoup.connect(link).timeout(100 * 1000).get();

                            Elements prepTimeElement = doc.selectXpath("//time[1]");
                            String prepTime = prepTimeElement.text();

                            Elements cookingTimeElement = doc.selectXpath("//time[2]");
                            String cookTime = cookingTimeElement.text();

                            Elements preparationMethod = doc.selectXpath("//div[@id='recipe_small_steps']");
                            String prepMethod = preparationMethod.text();

                            Elements ingredientList = doc.selectXpath("//div[@id='rcpinglist']//a/span");
                            List < String > listIngredients = ingredientList.eachText();
                            ArrayList < String > listOfIngredients = new ArrayList < > (listIngredients);
                            ArrayList < String > allergicIngredient = new ArrayList < > ();

                            //Get allergy element from ingredient list
                            for (String ingredient: listOfIngredients) {

                                if (allergicIngredientsArrayList.stream().anyMatch(s -> s.toUpperCase().contains(ingredient.toUpperCase())) && !ingredient.isBlank()) {
                                    allergicIngredient.add(ingredient);
                                }
                            }

                            List < String > uniqueAllergicIngredient = allergicIngredient.stream()
                                .distinct()
                                .collect(Collectors.toList());

                            Elements reciepe = doc.selectXpath("//span[@id='ctl00_cntrightpanel_lblRecipeName']");
                            String reciepeName = reciepe.text();

                            Elements nutrients = doc.select("table#rcpnutrients");

                            Elements tags = doc.selectXpath("//*[@id='recipe_tags']");
                            List < String > tagText = tags.eachText();

                            String foodCategory = ingredientData.foodCategory((ArrayList < String > ) tagText, listOfIngredients);
                            String recipeCategory = ingredientData.recipeCategory((ArrayList < String > ) tagText);
                            String haveToAdd = "No";

                            //Categorize other list of recipes according to morbidity
                            if (morbidity.equals("Others")) {
                                for (String morb: morbidities) {
                                    if (!ingredientData.ingredientsInEliminateList(morb, listOfIngredients) && !ingredientData.ingredientInTitleOrTag(morb, reciepeName)) {

                                        boolean containsToAddElement = ingredientData.ingredientsInToAddList(morb, listOfIngredients);

                                        if (containsToAddElement) {
                                            haveToAdd = "Yes";
                                        }

                                        //write recipe to excel 
                                        writer.writeRecipes(receipeId, reciepeName, foodCategory, recipeCategory, listOfIngredients, prepTime, cookTime, prepMethod, nutrients.text(), morb, link, haveToAdd, String.join(",", uniqueAllergicIngredient).toString());
                                        total += 1;
                                    }
                                }
                            } else {

                                boolean containsEliminatedElement = ingredientData.ingredientsInEliminateList(morbidity, listOfIngredients);

                                boolean containsToAddElement = ingredientData.ingredientsInToAddList(morbidity, listOfIngredients);

                                if (containsToAddElement) {
                                    haveToAdd = "Yes";
                                }

                                if (!containsEliminatedElement) {

                                    //write recipe to excel
                                    writer.writeRecipes(receipeId, reciepeName, foodCategory, recipeCategory, listOfIngredients, prepTime, cookTime, prepMethod, nutrients.text(), morbidity, link, haveToAdd, String.join(",", uniqueAllergicIngredient).toString());
                                    total += 1;

                                } else {
                                    System.out.println("Skipping ---------- " + link);
                                }

                            }
                        }

                        linkProcessed = true;

                    } catch (Exception e) {

                        e.printStackTrace();
                        retryCount += 1;
                        System.out.println("Exception. Retrying link" + link + ". Retry count " + retryCount);

                    }

                }

            }
            it.remove();
        }

        System.out.println("Total unique hyperTension Receipes :" + uniqueHyperTensionReceipesLinks.size());
        System.out.println("Total hyperThyroidism Receipes :" + uniqueHyperThyroidismReceipesLinks.size());
        System.out.println("Total PCOS Receipes :" + uniquePcosReceipesLinks.size());
        System.out.println("Total diabetes Receipes :" + uniqueDiabetesReceipesLinks.size());
        System.out.println("Total other Receipes :" + uniqueOtherReceipesLinks.size());
        writer.closeFile();

    }
}