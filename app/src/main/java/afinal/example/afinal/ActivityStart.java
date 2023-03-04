package afinal.example.afinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityStart extends AppCompatActivity {

    private Button startButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {//onCreate is used to start an activity
        //super is used to call the parent class constructor//setContentView is used to set the xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(ActivityStart.this, LoginActivity.class);

                startActivity(i);
                finish();
            }
        }, 3000);


    }
}

