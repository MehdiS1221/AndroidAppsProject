package algonquin.cst2335.finalproject.hussein.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import algonquin.cst2335.finalproject.R;

public class ImageViewerActivity extends AppCompatActivity {

    ImageView ivClose;
    ImageView imageView;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        imageView = findViewById(R.id.iv_image);
        ivClose = findViewById(R.id.iv_close);

        //getting image
        Intent intent = getIntent();
        try {
            image = intent.getStringExtra("IMAGE");
            //finish if image is empty
            if (TextUtils.isEmpty(image)) {
                finish();
            }
        } catch (Exception e) {
            finish();
            e.printStackTrace();
        }


        Glide.with(ImageViewerActivity.this)
                .load(image)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(imageView);

        ivClose.setOnClickListener(v -> finish());
    }
}