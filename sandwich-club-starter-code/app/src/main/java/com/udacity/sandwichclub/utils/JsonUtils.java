package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject json_req = new JSONObject(json);
            JSONObject name_json = json_req.getJSONObject("name");

            String mainName = name_json.getString("mainName");

            JSONArray alsoKnownAs = name_json.getJSONArray("alsoKnownAs");

            String placeOfOrigin = json_req.getString("placeOfOrigin");
            String description = json_req.getString("description");
            String image = json_req.getString("image");

            JSONArray ingredients = json_req.getJSONArray("ingredients");

            List<String> ingredientsList = new ArrayList<String>();
            List<String> alsoKnownAsList = new ArrayList<String>();

            for (int i = 0; i < alsoKnownAs.length(); i++) {
                alsoKnownAsList.add(alsoKnownAs.getString(i));
            }


            for (int i = 0; i < ingredients.length(); i++) {
                ingredientsList.add(ingredients.getString(i));
            }

            sandwich.setMainName(mainName);
            sandwich.setAlsoKnownAs(alsoKnownAsList);
            sandwich.setPlaceOfOrigin(placeOfOrigin);
            sandwich.setDescription(description);
            sandwich.setImage(image);
            sandwich.setIngredients(ingredientsList);

            return sandwich;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
