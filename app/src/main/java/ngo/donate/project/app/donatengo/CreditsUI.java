package ngo.donate.project.app.donatengo;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import ngo.donate.project.app.donatengo.controllers.MyPagerAdapter;
import ngo.donate.project.app.donatengo.controllers.VerticalViewPager;

public class CreditsUI extends AppCompatActivity {

    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits_activity_main);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.credits_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/


        VerticalViewPager vpPager = (VerticalViewPager) findViewById(R.id.pager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
    }

}
