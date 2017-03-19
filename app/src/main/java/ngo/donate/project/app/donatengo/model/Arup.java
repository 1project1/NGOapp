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
 * {@link Arup.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Arup#newInstance} factory method to
 * create an instance of this fragment.
*/
public class Arup extends Fragment {

    Button fb4,g4;
    TextView tv,tv2;
    public static Arup newInstance() {
        Arup fragmentFirst = new Arup();
        return fragmentFirst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_arup, container, false);

        fb4 = (Button) v.findViewById(R.id.arupButtonFacebook);
        g4 = (Button) v.findViewById(R.id.arupButtonGoogle);
        tv = (TextView) v.findViewById(R.id.arupTextView1);
        tv2 = (TextView) v.findViewById(R.id.arupTextView2);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/billabong.ttf");
        tv.setTypeface(typeface);
        tv2.setTypeface(typeface);

        fb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                String pageURL = "https://www.facebook.com/shibai.arup/";
                String pageId = "shibai.arup/";
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
                }

            }
        });

        g4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                String pageURL = "https://plus.google.com/111399302735653523384";
                String pageId = "111399302735653523384";
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
