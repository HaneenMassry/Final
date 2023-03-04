package afinal.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private TextView textView5;
    private ImageView imageView6;
    private LinearLayout scrollView4;
    private EditText editTextpassword4;
    private EditText editTextpassword2;
    private EditText editTextpassword3;
    private EditText editTextpassword;
    private Button cirRegisterButton;
    private ImageView imageFase;
    private ImageView imageGmail;
    private ImageView imageView7;
    private TextView textUseother;
    private Object view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         cirRegisterButton = findViewById(R.id.cirRegisterButton);
        changeStatusBarColor();

        cirRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, CourseDetails.class);
                startActivity(i);
            }
        });


    }

    private void changeStatusBarColor() {
    }
/*
    public void onClick(View v) {
        Intent i = new Intent(RegisterActivity.this, ActivityShow.class);
        startActivity(i);


        View btnNewUser;
        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

 */

/*
    public void onLoginClick(View view);
    {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

    }

*/


    }

