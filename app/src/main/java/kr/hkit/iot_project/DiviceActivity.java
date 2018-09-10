package kr.hkit.iot_project;

import android.app.Notification;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import kr.hkit.iot_project.preference.AddressPreference;

public class DiviceActivity extends AppCompatActivity {

    ImageView tvImageView;
    ImageView airconImageView;
    ImageView windowImageView;

    Button tvButton;
    Button airconButton;
    Button windowButton;
    Button ledButton;

    Notification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divice);

        tvImageView = findViewById(R.id.tvImageView);
        airconImageView = findViewById(R.id.airconImageView);
        windowImageView = findViewById(R.id.windowImageView);

        Button tvButton = findViewById(R.id.tvButton);
        tvButton.setOnClickListener(onTvClickListener);

        Button airconButton = findViewById(R.id.airconButton);
        airconButton.setOnClickListener(onAirconClickListener);


        Button windowButton = findViewById(R.id.windowButton);
        windowButton.setOnClickListener(onWindowClickListener);

        Button ledButton = findViewById(R.id.ledButton);
        ledButton.setOnClickListener(onLedClickListener);

        notification = new Notification(notificationListener);
        notification.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        pollingTask.cancel(true);
        notification.done();
    }



    View.OnClickListener onTvClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            requestGet("send_arduino", "command=TV", responseArduinoTvListener);
        }
    };

    View.OnClickListener onAirconClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            requestGet("send_arduino", "command=AIRCON", responseArduinoAirconListener);

        }
    };

    View.OnClickListener onWindowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            requestGet("send_arduino", "command=WINDOW", responseArduinoWindowListener);


        }
    };

    View.OnClickListener onLedClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            requestGet("send_arduino", "command=LED", responseArduinoLedListener);
        }

    };

    Response.Listener<String> responseArduinoTvListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Toast.makeText(getBaseContext(), response, Toast.LENGTH_SHORT).show();
        }
    } ;

    Response.Listener<String> responseArduinoAirconListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Toast.makeText(getBaseContext(), response, Toast.LENGTH_SHORT).show();
        }
    } ;

    Response.Listener<String> responseArduinoWindowListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Toast.makeText(getBaseContext(), response, Toast.LENGTH_SHORT).show();
        }
    } ;

    Response.Listener<String> responseArduinoLedListener = new Response.Listener<String>() {
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



    Response.Listener<String> responsePollingListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            if(response.equals("tv_on")) {
                tvImageView.setImageResource(R.drawable.tv_on);
            } else if(response.equals("tv_off")) {
                tvImageView.setImageResource(R.drawable.tv_off);
            }
            if(response.equals("aircon_on")) {
                airconImageView.setImageResource(R.drawable.aircon_on);
            } else if(response.equals("aircon_off")) {
                airconImageView.setImageResource(R.drawable.aircon_off);
            }
            if(response.equals("window_on")) {
                windowImageView.setImageResource(R.drawable.window_on);
            } else if(response.equals("window_off")) {
                windowImageView.setImageResource(R.drawable.window_off);
            }
        }
    } ;


}
