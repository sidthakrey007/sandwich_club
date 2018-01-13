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

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    @BindView(R.id.origin_tv) TextView placeOfOriginView;
    @BindView(R.id.ingredients_tv) TextView ingredientsView;
    @BindView(R.id.also_known_tv) TextView alsoKnownAsView;
    @BindView(R.id.description_tv) TextView descriptionView;
    @BindView(R.id.image_iv) ImageView ingredientsIv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        ingredientsIv = findViewById(R.id.image_iv);

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

    private void populateUI(Sandwich sandwich) {

        placeOfOriginView.setText(sandwich.getPlaceOfOrigin());
        descriptionView.setText(sandwich.getDescription());

        if (sandwich.getIngredients() != null) {
            String ingredients = sandwich.getIngredients().toString();
            ingredientsView.setText(ingredients.substring(1, ingredients.length()-1));
        }

        String alsoKnownAs = sandwich.getAlsoKnownAs().toString();
        alsoKnownAsView.setText(alsoKnownAs.substring(1, alsoKnownAs.length() - 1));

    }
}
