package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//this code creates a hash map and write data from it to excel file
public class RecipeWriter {

	public Workbook workbook;
	public Sheet sheet;
	public FileOutputStream fos;

	public RecipeWriter() throws IOException {

			fos = new FileOutputStream("./src/test/resources/ScrappedRecipes.xlsx");
		    workbook = new XSSFWorkbook();
		    sheet = workbook.createSheet("Recipes");

		    sheet.setDefaultColumnWidth(20);


			String[] headers = new String[]{ "Recipe Id", "Recipe Name", "Recipe Category", "Food Category",
					"Ingredients", "Prepration Time", "Cooking Time", "Preparation Method", "Nutrient Value",
					"Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)", "Recipe URL", "Have To Add Ingredients"};
			Row header = sheet.createRow(0);

			for(int i=0; i<headers.length; i++) {
			XSSFCell cell = (XSSFCell) header.createCell(i);
			cell.setCellValue(headers[i]);
			}

	//		fos = new FileOutputStream("./src/test/resources/ScrappedRecipes.xlsx");
	//		workbook.write(fos);

			System.out.println("written headers");

	}


	public void closeFile() throws IOException {
		workbook.write(fos);
		fos.flush();

		fos.close();
		workbook.close();
		System.out.println("closed");
	}

	public void writeRecipes(String recipeId, String recipeName, String recipeCategory,
			String foodCategory, ArrayList<String> ingredients, String preprationTime,
			String cookingTime, String preparationMethod, String nutrientValue, String morbidity, String recipeURL, String haveToAdd) throws IOException{

			int nextRow = sheet.getLastRowNum()+1;
			Row row = sheet.createRow(nextRow);

			row.createCell(0).setCellValue(recipeId);
			row.createCell(1).setCellValue(recipeName);
			row.createCell(2).setCellValue(recipeCategory);
			row.createCell(3).setCellValue(foodCategory);
			row.createCell(4).setCellValue(String.join(",", ingredients));
			row.createCell(5).setCellValue(preprationTime);
			row.createCell(6).setCellValue(cookingTime);
			row.createCell(7).setCellValue(preparationMethod);
			row.createCell(8).setCellValue(nutrientValue);
			row.createCell(9).setCellValue(morbidity);
			row.createCell(10).setCellValue(recipeURL);
			row.createCell(11).setCellValue(haveToAdd);
		//	fos = new FileOutputStream("./src/test/resources/ScrappedRecipes.xlsx");



			System.out.println("recipes written");



	}

}
