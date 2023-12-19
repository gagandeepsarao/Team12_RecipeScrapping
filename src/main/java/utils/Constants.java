package utils;

import java.util.Arrays;
import java.util.List;

public final class Constants {
	private Constants() {
}
	
	public static final String DIABETES = "Diabetes";
	public static final String PCOS = "PCOS";
	public static final String HYPERTHYRODISM = "HyperThyroidism";
	public static final String HYPERTENSION = "HyperTension";
	public static final List<String> MORBIDITIES = (List<String>) Arrays.asList(DIABETES, PCOS, HYPERTHYRODISM, HYPERTENSION);
	public static final List < String > ALLERGICINGREDIENTS = Arrays.asList(
            "Milk", "Soy", "Egg", "Eggs", "Sesame", "Peanuts", "Peanut", "Walnut", "Walnuts",
            "Almond", "Almonds", "Hazelnut", "Pecan", "Pecans", "Cashews", "Cashew", "Pistachio", "Pistachios",
            "Shellfish", "Seafood"
        );

	public static final String PCOSRECEIPES = "https://www.tarladalal.com/recipes-for-pcos-1040?pageindex=";
	public static final String DIABETESRECEIPES = "https://www.tarladalal.com/recipes-for-indian-diabetic-recipes-370?pageindex=";
	public static final String HYPERTHYROIDISMRECEIPES = "https://www.tarladalal.com/recipes-for-hyperthyroidism-diet-veg--850?pageindex=";
	public static final String HYPERTENSIONRECEIPES = "https://www.tarladalal.com/recipes-for-high-blood-pressure-644?pageindex=";
	public static final String ATOZRECEIPES = "https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith=";
	public static final String VEGRECEIPES = "https://www.tarladalal.com/recipes-for-course-veg-course-140?pageindex=";
	public static final String RECEIPEBYEQUIPMENT = "https://www.tarladalal.com/recipes-for-equipment-309?pageindex=";
	public static final String RECEIPEBYCUISINE  ="https://www.tarladalal.com/recipes-for-cuisine-1?pageindex=";
	public static final String BASICCOOKINGRECEIPES  ="https://www.tarladalal.com/recipes-for-cooking-basics-271?pageindex=";


}
