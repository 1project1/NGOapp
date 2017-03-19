package ngo.donate.project.app.donatengo.model;


import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ngo.donate.project.app.donatengo.R;

/*
*
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Group.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Group#newInstance} factory method to
 * create an instance of this fragment.
*/

public class Group extends Fragment {

    Button fb0, g0;
    TextView tv,tv2;
    public static Group newInstance() {
        Group fragmentFirst = new Group();
        return fragmentFirst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_group, container, false);

        fb0 = (Button) v.findViewById(R.id.groupButtonFacebook);
        g0 = (Button) v.findViewById(R.id.groupButtonGoogle);
        tv = (TextView) v.findViewById(R.id.groupTextView1);
        tv2 = (TextView) v.findViewById(R.id.groupTextView2);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/billabong.ttf");
        tv.setTypeface(typeface);
        tv2.setTypeface(typeface);

        fb0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent();
                String pageURL = "https://www.facebook.com/AakashJhakas/";
                String pageId = "AakashJhakas/";
                try {
                    int pInfo = getActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
                    boolean activated =  getActivity().getPackageManager().getApplicationInfo("com.facebook.katana", 0).enabled;
                    if(activated){
                        if ((pInfo >= 3002850)) {
                            intent.setData(Uri.parse("fb://facewebmodal/f?href=" + pageURL));
                        } else {
                            intent.setData(Uri.parse("fb://page/" + pageId));
                        }
                    }
                    startActivity(intent);
                } catch (Exception e) {
                    intent =  new Intent(Intent.ACTION_VIEW, Uri.parse(pageURL));
                    startActivity(Intent.createChooser(intent,""));
                }*/

            }
        });

        g0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                String pageURL = "https://plus.google.com/114349189622563690120";
                String pageId = "114349189622563690120";
                try {
                    intent.setClassName("com.google.android.apps.plus",
                            "com.google.android.apps.plus.phone.UrlGatewayActivity");
                    intent.putExtra("customAppUri", pageId);
                    startActivity(intent);
                } catch (Exception e) {
                    intent =  new Intent(Intent.ACTION_VIEW, Uri.parse(pageURL));
                    startActivity(Intent.createChooser(intent,""));
                }

            }
        });
        return v;

    }
}
