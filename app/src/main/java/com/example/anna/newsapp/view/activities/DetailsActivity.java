package com.example.anna.newsapp.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anna.newsapp.R;
import com.example.anna.newsapp.model.db.Article;
import com.example.anna.newsapp.model.repository.ArticleRepository;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends AppCompatActivity {

    public static String TAG = "DetailsActivity";

    @Nullable
    @BindView(R.id.image)
    ImageView mainImage;

    @Nullable
    @BindView(R.id.image_pin)
    ImageView pinImage;


    @BindView(R.id.text_section)
    TextView sectionText;


    @BindView(R.id.text_title)
    TextView titleText;

    private Article mArticle;

    private boolean isPinned;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);


        mArticle = Parcels.unwrap(getIntent().getParcelableExtra(MainActivity.ARTICLE_KEY));
        if (mArticle != null) {
            sectionText.setText(mArticle.getSectionName());
            titleText.setText(mArticle.getWebTitle());

            Picasso.get().load(mArticle.getThumbnail())
                    .placeholder(R.drawable.ic_placeholder)
                    .into(mainImage);
            if (mArticle.getPinned()) {
                pinImage.setImageResource(R.drawable.ic_checked);
            } else {
                pinImage.setImageResource(R.drawable.icon_pin);
            }

            isPinned = mArticle.getPinned();

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @OnClick(R.id.image_pin)
    public void onPinClicked() {
        Log.d(TAG, "onPinClicked");
        pinImage.setImageResource(R.drawable.ic_checked);
        if (!mArticle.getPinned()) {
            pinImage.setImageResource(R.drawable.ic_checked);
            mArticle.setPinned(true);
            return;
        }
        mArticle.setPinned(false);
        pinImage.setImageResource(R.drawable.icon_pin);
    }


    private void sendToActivity() {
        Log.d(TAG, "sendToActivity");
        Intent intent = new Intent();
        Parcelable extra = Parcels.wrap(mArticle);
        intent.putExtra(MainActivity.ARTICLE_KEY, extra);
        setResult(Activity.RESULT_OK, intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isStateChanged()) {
            sendToActivity();
        }
    }

    private boolean isStateChanged() {
        return isPinned != mArticle.getPinned();
    }
}
