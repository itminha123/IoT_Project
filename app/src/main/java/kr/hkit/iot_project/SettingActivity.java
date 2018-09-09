package kr.hkit.iot_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import kr.hkit.iot_project.preference.AddressPreference;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        AddressPreference ap = new AddressPreference(getBaseContext());

        String ip = ap.getIp();
        EditText ipEditText = findViewById(R.id.idEditText);
        ipEditText.setText(ip);

        int port = ap.getPort();
        EditText portEditText = findViewById(R.id.portEditText);
        portEditText.setText(String.valueOf(port));

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(onSaveClickListener);

    }

    private View.OnClickListener onSaveClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            EditText ipEditText = findViewById(R.id.idEditText);
            String ip = ipEditText.getText().toString();

            EditText portEditText = findViewById(R.id.portEditText);
            String portText = portEditText.getText().toString();
            int port = Integer.valueOf(portText);

            AddressPreference ap = new AddressPreference(getBaseContext());

            ap.putIp(ip);
            ap.putPort(port);

            Toast.makeText(getBaseContext(),ip + "저장 했다 임뫄!!!", Toast.LENGTH_SHORT).show();
            finish();

        }
    };

}