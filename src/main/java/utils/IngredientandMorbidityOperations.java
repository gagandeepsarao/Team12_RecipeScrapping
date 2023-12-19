package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.Constants.DIABETES;
import static utils.Constants.PCOS;
import static utils.Constants.HYPERTHYRODISM;
import static utils.Constants.HYPERTENSION;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class IngredientandMorbidityOperations {
    public MorbidityIngredients diebeticIngredients = new MorbidityIngredients();
    public MorbidityIngredients hyperThyrodismIngredients = new MorbidityIngredients();
    public MorbidityIngredients hyperTensionIngredients = new MorbidityIngredients();
    public MorbidityIngredients pcosIngredients = new MorbidityIngredients();

    //Reading eliminate and to add from the excel
    public void readData() throws IOException {

        String excelPath = "./src/test/resources/IngredientsAndComorbidities-ScrapperHackathon.xlsx";

        FileInputStream inputStream = new FileInputStream(excelPath);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        int rows = sheet.getLastRowNum();

        for (int r = 2; r <= rows; r++) {
            Row row = sheet.getRow(r);

            //get eliminate list for diabetes
            Cell cell = row.getCell(0);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                String value = cell.getStringCellValue();
                String[] splitArray = value.split(",");
                for (String substring: splitArray) {
                    diebeticIngredients.eliminate.add(substring.trim().toUpperCase());
                }

            }

            //get "to add" data from diabetes
            cell = row.getCell(1);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                String value = cell.getStringCellValue();
                String[] splitArray = value.split(",");
                for (String substring: splitArray) {
                    diebeticIngredients.toAdd.add(substring.trim().toUpperCase());
                }

            }

            //get eliminate list for hyper thyroidism
            cell = row.getCell(2);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                String value = cell.getStringCellValue();
                String[] splitArray = value.split(",");
                for (String substring: splitArray) {
                    hyperThyrodismIngredients.eliminate.add(substring.trim().toUpperCase());
                }

            }

            //get "to add" data from hyperthyrodism
            cell = row.getCell(3);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                String value = cell.getStringCellValue();
                String[] splitArray = value.split(",");
                for (String substring: splitArray) {
                    hyperThyrodismIngredients.toAdd.add(substring.trim().toUpperCase());
                }

            }

            //get eliminate list for hyperTension
            cell = row.getCell(4);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                String value = cell.getStringCellValue();
                String[] splitArray = value.split(",");
                for (String substring: splitArray) {
                    hyperTensionIngredients.eliminate.add(substring.trim().toUpperCase());
                }

            }

            //get "to add" data from hypertension
            cell = row.getCell(5);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                String value = cell.getStringCellValue();
                String[] splitArray = value.split(",");
                for (String substring: splitArray) {
                    hyperTensionIngredients.toAdd.add(substring.trim().toUpperCase());
                }

            }

            //get eliminate list for pcos
            cell = row.getCell(6);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                String value = cell.getStringCellValue();

                String[] splitArray = value.split(",");
                for (String substring: splitArray) {
                    pcosIngredients.eliminate.add(substring.trim().toUpperCase());
                }

            }

            //get "to add" data from pcos
            cell = row.getCell(7);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                String value = cell.getStringCellValue();
                String[] splitArray = value.split(",");
                for (String substring: splitArray) {
                    pcosIngredients.toAdd.add(substring.trim().toUpperCase());
                }

            }

        }
        
        workbook.close();
        inputStream.close();
    }

    //Check if recipe ingredient in elimination list
    public boolean ingredientsInEliminateList(String morbidity, ArrayList < String > recipeIngredients) {
        ArrayList < String > eliminateList = new ArrayList < > ();
        switch (morbidity) {
        case DIABETES:
            eliminateList = diebeticIngredients.eliminate;
            break;
        case HYPERTHYRODISM:
            eliminateList = hyperThyrodismIngredients.eliminate;
            break;
        case HYPERTENSION:
            eliminateList = hyperTensionIngredients.eliminate;
            break;
        case PCOS:
            eliminateList = pcosIngredients.eliminate;
            break;

        }
        for (String ingredient: eliminateList) {
            if (recipeIngredients.stream().anyMatch(s -> s.toUpperCase().contains(ingredient))) {
                return true;
            }

        }

        return false;
    }

    //Check if recipe ingredient in "To add" list
    public boolean ingredientsInToAddList(String morbidity, ArrayList < String > recipeIngredients) {
        ArrayList < String > toAddList = new ArrayList < > ();
        switch (morbidity) {
        case DIABETES:
            toAddList = diebeticIngredients.toAdd;
            break;
        case HYPERTHYRODISM:
            toAddList = hyperThyrodismIngredients.toAdd;
            break;
        case HYPERTENSION:
            toAddList = hyperTensionIngredients.toAdd;
            break;
        case PCOS:
            toAddList = pcosIngredients.toAdd;
            break;

        }
        for (String ingredient: toAddList) {
            if (recipeIngredients.stream().anyMatch(s -> s.toUpperCase().contains(ingredient))) {
                return true;
            }

        }

        return false;
    }

    //Categorize food based on tags and ingredients from recipe
    public String foodCategory(ArrayList < String > tags, ArrayList < String > recipeIngredients) {
        String category = "";

        if (tags.stream().anyMatch(s -> s.toUpperCase().contains("JAIN"))) {
            category = "Jain";
        } else if (tags.stream().anyMatch(s -> s.toUpperCase().contains("VEGAN"))) {
            category = "Vegan";
        } else if (tags.stream().anyMatch(s -> s.toUpperCase().contains("VEGETARIAN")) || tags.stream().anyMatch(s -> s.toUpperCase().contains("VEG"))) {
            category = "Vegetarian";
        } else if ((recipeIngredients.stream().anyMatch(s -> s.toUpperCase().contains("EGG")) || recipeIngredients.stream().anyMatch(s -> s.toUpperCase().contains("EGGS"))) && !recipeIngredients.stream().anyMatch(s -> s.toUpperCase().contains("EGGPLANT"))) {
            category = "Eggitarian";
        }

        return category;
    }

    //Categorize recipes based on tags 
    public String recipeCategory(ArrayList < String > tags) {
        String category = "";

        if (tags.stream().anyMatch(s -> s.toUpperCase().contains("LUNCH"))) {
            category = "Lunch";
        } else if (tags.stream().anyMatch(s -> s.toUpperCase().contains("BREAKFAST"))) {
            category = "Breakfast";
        } else if (tags.stream().anyMatch(s -> s.toUpperCase().contains("DINNER"))) {
            category = "Dinner";
        }

        return category;
    }

    //Check eliminate ingredients in the title/Recipe name
    public boolean ingredientInTitleOrTag(String morbidity, String reciepeName) {

        ArrayList < String > eliminateList = new ArrayList < > ();
        switch (morbidity) {
        case DIABETES:
            eliminateList = diebeticIngredients.eliminate;
            break;
        case HYPERTHYRODISM:
            eliminateList = hyperThyrodismIngredients.eliminate;
            break;
        case HYPERTENSION:
            eliminateList = hyperTensionIngredients.eliminate;
            break;
        case PCOS:
            eliminateList = pcosIngredients.eliminate;
            break;

        }

        for (String ingredient: eliminateList) {
            String regex = "\\b" + Pattern.quote(ingredient.toUpperCase()) + "\\b";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(reciepeName.toUpperCase());
            if (matcher.find()) {
                return true;
            }

        }

        return false;
    }
}