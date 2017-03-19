package ngo.donate.project.app.donatengo;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Aakash on 01-Mar-17.
 */

public class DialogActivity extends AppCompatActivity {

    public ProgressDialog mProcessDialog;

    void showProgressDialog() {
        if (mProcessDialog == null) {
            mProcessDialog = new ProgressDialog(this);
            mProcessDialog.setMessage(getString(R.string.loading));
            mProcessDialog.setIndeterminate(true);
            mProcessDialog.setCancelable(false);
        }

        mProcessDialog.show();
    }

    void hideProgressDialog() {
        if (mProcessDialog != null && mProcessDialog.isShowing()) {
            mProcessDialog.dismiss();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideProgressDialog();
    }
}
