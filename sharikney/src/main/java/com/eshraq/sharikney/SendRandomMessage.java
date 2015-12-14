package com.eshraq.sharikney;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gcm.GCMRegistrar;

/**
 * Created by hazem on 12/10/15.
 */
public class SendRandomMessage extends Activity {
    AsyncTask<Void, Void, Void> mRegisterTask;
    Button sendMsg;
    private ProgressDialog pDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_random_msg);
        sendMsg = (Button) findViewById(R.id.sendMsg);
        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMsg();
            }
        });
    }

    public void sendMsg() {
        mRegisterTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(SendRandomMessage.this);
                pDialog.setMessage("Sending message. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                // Register on our server
                // On server creates a new user
                String regId = GCMRegistrar.getRegistrationId(getApplicationContext());
                ServerUtilities.sendRandom(getApplicationContext(), regId, 5, "test" + getString(R.string.concate) + regId);
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
