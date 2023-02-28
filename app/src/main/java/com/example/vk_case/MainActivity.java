package com.example.vk_case;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.ColorUtils;
import androidx.palette.graphics.Palette;

import com.example.vk_case.databinding.UserCardLayoutBinding;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    FlexboxLayout flexboxUsers;
    int flexColumns = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flexboxUsers = this.findViewById(R.id.flexbox_users);
        Button endCallBtn = this.findViewById(R.id.end_btn);
        endCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.finish();
            }
        });

        addUserView(this,"you",(int)R.drawable.rikc_roll);
        addUserView(this, "super long username for test and test and test again",(int)R.drawable.stripes);


//      FOR TESTING
//        flexboxUsers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addUserView(MainActivity.this,"new gg",R.drawable.man);
//            }
//        });

    }

    private void addUserView(Context context, String name, int imgId){
        Drawable drawable = AppCompatResources.getDrawable(context,imgId);
        int cardWidth = (int)((this.getResources().getConfiguration().screenWidthDp-10)*this.getResources().getDisplayMetrics().density)-6*(flexColumns);
        int col = (int)Math.sqrt(flexboxUsers.getFlexItemCount()+1);
        UserCardLayoutBinding userCard = UserCardLayoutBinding.inflate(getLayoutInflater());
        userCard.userCardImg.setImageDrawable(drawable);
        userCard.userCardImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        userCard.userCardImg.getLayoutParams().height = cardWidth/flexColumns/4;
        userCard.userCardImg.getLayoutParams().width = cardWidth/flexColumns/4;
        userCard.userCardName.setText(name);
        setGradientsForCard(BitmapFactory.decodeResource(context.getResources(),imgId),userCard.getRoot());
        CardView.LayoutParams card_params = new FrameLayout.LayoutParams(cardWidth/flexColumns, ViewGroup.LayoutParams.WRAP_CONTENT);
        card_params.setMargins(3,3,3,3);
        userCard.getRoot().setLayoutParams(card_params);
        flexboxUsers.addView(userCard.getRoot());
        if(flexColumns < col){
            flexColumns = col;
            cardWidth = (int)((this.getResources().getConfiguration().screenWidthDp-10)*this.getResources().getDisplayMetrics().density)-6*(flexColumns);
            resizeAllUserViews(cardWidth/flexColumns);
        }
    }
    private void resizeAllUserViews(int width){
        for (int i = 0; i < flexboxUsers.getFlexItemCount(); i++) {
            ViewGroup user = (ViewGroup) flexboxUsers.getChildAt(i);
            user.getLayoutParams().width=width;
            user.getChildAt(0).getLayoutParams().height = width/4;
            user.getChildAt(0).getLayoutParams().width = width/4;
        }
    }
    public void setGradientsForCard(Bitmap bitmap, View view) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette p) {
                List<Palette.Swatch> sws = p.getSwatches();
                Log.i("gg",String.valueOf(sws.isEmpty()));
                if(!sws.isEmpty()){
                    int col1 = ColorUtils.blendARGB(sws.get(0).getRgb(), Color.DKGRAY,0.2f);
                    int col2 = ColorUtils.blendARGB(sws.get(sws.size()-1).getRgb(), Color.DKGRAY,0.4f);
                    Log.i("gg",String.valueOf(col1)+" "+String.valueOf(col2));
                    view.setBackground(createGrad(col1, col2));
                }
            }
        });
    }

    private GradientDrawable createGrad(int color1, int color2){
        int[] colors = {color1,color2};
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT,colors);
        gd.setCornerRadius(6f);
        return gd;
    }

}