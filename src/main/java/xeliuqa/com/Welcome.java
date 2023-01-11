package xeliuqa.com;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity {

    Button signUP;
    Button signIN;
    private SessionHandler session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        //pin = user.getOTP();

        if(session.isLoggedIn()){
            loadDashboard();
            finish();
        }

        signUP = (Button) findViewById(R.id.singup);
        signIN = (Button) findViewById(R.id.singin);


        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Welcome.this, Signup.class);
                startActivity(i);

            }
        });

        signIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Welcome.this, Forgot.class);
                startActivity(i);

            }
        });

    }
    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), PIN_Verify.class);
        startActivity(i);
        finish();

    }
}