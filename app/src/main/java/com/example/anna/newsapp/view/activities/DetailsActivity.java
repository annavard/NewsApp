package com.example.anna.newsapp.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.anna.newsapp.R;
import com.example.anna.newsapp.model.ArticleDataHolder;
import com.example.anna.newsapp.model.models.Result;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
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

    private Result mResult;
    private boolean mIsPinned;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);


        mResult = Parcels.unwrap(getIntent().getParcelableExtra(MainActivity.ARTICLE_KEY));
        if (mResult != null) {
            sectionText.setText(mResult.getSectionName());
            titleText.setText(mResult.getWebTitle());

            Picasso.get().load(mResult.getFields().getThumbnail())
                    .placeholder(R.drawable.placeholder)
                    .into(mainImage);
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
    public void onPinClicked(){
        Log.d(TAG, "onPinClicked");
        if(mIsPinned){
//            pinImage.setImageResource(R.drawable.ic_checked);
            pinImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_checked));
            ArticleDataHolder.getInstance().getArticles().add(mResult);
        }else {
//            pinImage.setImageResource(R.drawable.icon_pin);
            pinImage.setImageDrawable(getResources().getDrawable(R.drawable.icon_pin));
            ArticleDataHolder.getInstance().getArticles().remove(mResult);
        }



    }

}
