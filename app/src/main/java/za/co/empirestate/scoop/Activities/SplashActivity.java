package za.co.empirestate.scoop.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import za.co.empirestate.scoop.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            public void run() {
               SplashActivity.this.startActivity(new Intent(SplashActivity.this, NewsFeed.class));
                // overridePendingTransition(R.anim.from, R.anim.to);
                SplashActivity.this.finish();
            }
        }
                , 3000);
    }
}
