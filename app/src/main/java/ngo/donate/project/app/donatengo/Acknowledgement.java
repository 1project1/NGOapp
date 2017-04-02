package ngo.donate.project.app.donatengo;

import android.support.v7.app.AppCompatActivity;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

public class Acknowledgement extends AppCompatActivity {

    private final String our_username = "jeevanmru@gmail.com";
    private final String our_password = "bahubali";
    private  String mail_subject;
    private  String mail_body;


    public void sendConfirmationMail(String name,String ngo_name, String user_email,String donated_item_name,String donated_item_quantity) {

        mail_subject = "Donation Successfull - Your Donation with " + ngo_name + " has " +
                "been successfull.";

        mail_body = "Hi " + name + ",\n\nWe are pleased to inform you that your items " +
                "have successfully been donated to the needy people via " + ngo_name + ". This " +
                "successfully completes your donation. Thank you for choosing our service.\n\n"
                + "The summary of items donated is mentioned below.\n\n" + donated_item_name +
                "\n" + donated_item_quantity;

        BackgroundMail.newBuilder(this)
                .withUsername(our_username)
                .withPassword(our_password)
                .withMailto(user_email)
                .withType(BackgroundMail.TYPE_PLAIN)
                .withSubject(mail_subject)
                .withBody(mail_body)
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        //do some magic
                       // Toast.makeText(Acknowledgement.this, "You will soon get an email about the confirmation", Toast.LENGTH_SHORT).show();
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                        //do some magic
                        //Toast.makeText(Acknowledgement.this, "Failed... Try Again", Toast.LENGTH_SHORT).show();
                    }
                })
                .send();
    }
}