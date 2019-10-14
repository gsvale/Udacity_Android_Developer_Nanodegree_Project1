package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        // Pass Sandwich object as argument
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    // Populate UI with parameter Sandwich object
    private void populateUI(Sandwich sandwich) {

        // Set Known names in TextView
        TextView alsoKnownTv = findViewById(R.id.also_known_tv);
        List<String> alsoKnownNames = sandwich.getAlsoKnownAs();

        if (alsoKnownNames != null && !alsoKnownNames.isEmpty()) {
            alsoKnownTv.append(alsoKnownNames.get(0));
            for (int i = 1; i < alsoKnownNames.size(); i++) {
                alsoKnownTv.append("\n" + alsoKnownNames.get(i));
            }
        }

        // Set place of origin in TextView
        TextView originTv = findViewById(R.id.origin_tv);
        originTv.setText(sandwich.getPlaceOfOrigin());

        // Set description in TextView
        TextView descriptionTv = findViewById(R.id.description_tv);
        descriptionTv.setText(sandwich.getDescription());

        // Set ingredients in TextView
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        List<String> ingredients = sandwich.getIngredients();

        if (ingredients != null && !ingredients.isEmpty()) {
            ingredientsTv.append(ingredients.get(0));
            for (int i = 1; i < ingredients.size(); i++) {
                ingredientsTv.append("\n" + ingredients.get(i));
            }
        }

    }
}
