package kr.hkit.iot_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button diviceButton = findViewById(R.id.diviceButton);
        diviceButton.setOnClickListener(onDiviceClickListener);

        Button smartButton = findViewById(R.id.smartButton);
        smartButton.setOnClickListener(onSmartClickListener);

        ImageButton exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(onExitClickListener);

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_setting) {
            Intent intent = new Intent(getBaseContext(),SettingActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private View.OnClickListener onDiviceClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getBaseContext(), DiviceActivity.class);
            startActivity(intent);
        }
    };


    private View.OnClickListener onSmartClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getBaseContext(), SmartActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener onExitClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finishAffinity();
            System.runFinalization();
            System.exit(0);
            // http://g-y-e-o-m.tistory.com/96 - 앱 종료 참고했음
        }
    };



}
