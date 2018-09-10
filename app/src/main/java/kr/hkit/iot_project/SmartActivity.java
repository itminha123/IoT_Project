package kr.hkit.iot_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import kr.hkit.iot_project.preference.AddressPreference;

public class SmartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart);

        Button smartButton = findViewById(R.id.smartButton);
        smartButton.setOnClickListener(onSmartClickListener);
    }

    View.OnClickListener onSmartClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            requestGet("send_arduino", "command=smart_mode", responseArduinoSmartListener);
        }
    };

    Response.Listener<String> responseArduinoSmartListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Toast.makeText(getBaseContext(), response, Toast.LENGTH_SHORT).show();
        }
    } ;

    void requestGet(String urlPath, String sendData, Response.Listener<String> responseListener) {
        AddressPreference ap = new AddressPreference(getBaseContext());
        String ip = ap.getIp();
        int port = ap.getPort();

        String url = "http://" + ip + ":" + String.valueOf(port) + "/" + urlPath + "?" + sendData;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, null);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}
