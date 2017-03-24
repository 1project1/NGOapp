package ngo.donate.project.app.donatengo.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import ngo.donate.project.app.donatengo.LogIn;
import ngo.donate.project.app.donatengo.R;

/**
 * Created by AmanPC on 19-03-2017.
 */

public class Splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startAnimation();


        //textanimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splash.this, LogIn.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                finish();
            }
        },2000);
    }
    private void startAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        animation.reset();
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.clearAnimation();
        imageView.startAnimation(animation);
    }
}
