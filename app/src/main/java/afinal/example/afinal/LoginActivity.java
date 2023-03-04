package afinal.example.afinal;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private Button btnNewUser;
    private Button btnForget;
    private TextView textView3;
    private ImageView imageView3;
    private EditText editTextpassword;
    private EditText editTextEmail;


    private Button cirLoginButton;
    private TextView textUseother;
    private ImageView imageGmail;
    private ImageView imageFase;
    private ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btnNewUser = findViewById(R.id.btnNewUser);
        btnForget = findViewById(R.id.btnForget);
        cirLoginButton = findViewById(R.id.cirLoginButton);
        textView3 = findViewById(R.id.textView5);
        imageView3 = findViewById(R.id.imageView3);
        editTextpassword = findViewById(R.id.editTextpassword);
        editTextEmail = findViewById(R.id.editTextEmail);

        cirLoginButton = findViewById(R.id.cirLoginButton);
        textUseother = findViewById(R.id.textUseother);
        imageGmail = findViewById(R.id.imageGmail);
        imageFase = findViewById(R.id.imageFase);
        imageView2 = findViewById(R.id.imageView2);

        //for changing status bar icon color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);


            }
        });

        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ActivityStart.class);
                startActivity(i);


            }
        });

        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
/////setOnClickListener המסייעת לנו לקשר מאזין עם תכונות מסוימות.
// setOnClickListener היא שיטה באנדרואיד המשמשת בעצם עם לחצנים, לחצני תמונה וכו

    };
    public void onLoginClick (View view )
    {
        startActivity(new Intent(this, CourseDetails.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }

    }


