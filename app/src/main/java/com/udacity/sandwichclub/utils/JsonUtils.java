package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME_TAG = "name";
    private static final String MAIN_NAME_TAG = "mainName";
    private static final String ALSO_KNOWN_AS_TAG = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN_TAG = "placeOfOrigin";
    private static final String DESCRIPTION_TAG = "description";
    private static final String IMAGE_TAG = "image";
    private static final String INGREDIENTS_TAG = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        // If Json string is empty or null, return null
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        // Create new Sandwich object
        Sandwich sandwich = new Sandwich();

        try{

            // Create Root Json Object
            JSONObject rootJsonResponse = new JSONObject(json);

            // Create Response Json Object
            JSONObject sandwichJsonResponse = rootJsonResponse.getJSONObject(NAME_TAG);

            String mainName = "";
            List<String> alsoKnownAs = new ArrayList<>();
            String placeOfOrigin = "";
            String description = "";
            String image = "";
            List<String> ingredients = new ArrayList<>();

            // Get values to fill Sandwich object

            if(!TextUtils.isEmpty(sandwichJsonResponse.getString(MAIN_NAME_TAG))) {
                mainName = sandwichJsonResponse.getString(MAIN_NAME_TAG);
            }

            JSONArray alsoKnownAsJsonArray = sandwichJsonResponse.getJSONArray(ALSO_KNOWN_AS_TAG);
            int alsoKnownAsJsonArraySize = alsoKnownAsJsonArray.length();

            for(int i = 0 ; i < alsoKnownAsJsonArraySize; i++){
                String alsoKnownAsString = alsoKnownAsJsonArray.getString(i);
                if(!TextUtils.isEmpty(alsoKnownAsString)) {
                    alsoKnownAs.add(alsoKnownAsString);
                }
            }

            if(!TextUtils.isEmpty(rootJsonResponse.getString(PLACE_OF_ORIGIN_TAG))) {
                placeOfOrigin = rootJsonResponse.getString(PLACE_OF_ORIGIN_TAG);
            }

            if(!TextUtils.isEmpty(rootJsonResponse.getString(DESCRIPTION_TAG))) {
                description = rootJsonResponse.getString(DESCRIPTION_TAG);
            }

            if(!TextUtils.isEmpty(rootJsonResponse.getString(IMAGE_TAG))) {
                image = rootJsonResponse.getString(IMAGE_TAG);
            }

            JSONArray ingredientsJsonArray = rootJsonResponse.getJSONArray(INGREDIENTS_TAG);
            int ingredientsJsonArraySize = ingredientsJsonArray.length();

            for(int i = 0 ; i < ingredientsJsonArraySize; i++){
                String ingredientString = ingredientsJsonArray.getString(i);
                if(!TextUtils.isEmpty(ingredientString)) {
                    ingredients.add(ingredientString);
                }
            }

            sandwich.setMainName(mainName);
            sandwich.setAlsoKnownAs(alsoKnownAs);
            sandwich.setPlaceOfOrigin(placeOfOrigin);
            sandwich.setDescription(description);
            sandwich.setImage(image);
            sandwich.setIngredients(ingredients);

        }
        catch(JSONException e){
            e.printStackTrace();
        }


        return sandwich;
    }
}
