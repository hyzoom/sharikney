package com.eshraq.sharikney;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by hazem on 12/14/15.
 */
public class SingleNotification extends Activity {
    TextView msg;
    EditText reply;
    Button send;
    private ProgressDialog pDialog;
    AsyncTask<Void, Void, Void> mRegisterTask;
    String sentRegId, sentMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_notification);

        msg = (TextView) findViewById(R.id.msg);
        String complete_msg = getIntent().getStringExtra("complete_msg");
        if (complete_msg.contains(getString(R.string.concate))) {
            sentMessage = complete_msg.substring(0, complete_msg.indexOf(getString(R.string.concate)));
            sentRegId = complete_msg.substring(complete_msg.indexOf(getString(R.string.concate)) + 6, complete_msg.length());
        } else {
            sentMessage = complete_msg;
            sentRegId = "0";
        }
        msg.setText(sentMessage);
    }

    public void sendMsg(View v) {
        mRegisterTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(SingleNotification.this);
                pDialog.setMessage("Sending message. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                // Register on our server
                // On server creates a new user
                ServerUtilities.sendSingle(getApplicationContext(), sentRegId, "second");
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                pDialog.dismiss();
                mRegisterTask = null;
            }
        };
        mRegisterTask.execute(null, null, null);
    }
}
