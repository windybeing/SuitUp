package hk.suitup.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hk.suitup.R;

public class ChangeInfoActivity extends ActionBarActivity {

    private EditText changeAddress;
    private EditText changeAge;
    private EditText changePhonenumber;
    private EditText changeEmail;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);

        //initial view groups
        changeAddress = (EditText) findViewById(R.id.address_change);
        changeAge = (EditText) findViewById(R.id.age_change);
        changePhonenumber = (EditText) findViewById(R.id.phone_change);
        changeEmail = (EditText) findViewById(R.id.email_change);

        button = (Button) findViewById(R.id.save_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check whether all of the inputs are empty or not
                if(changeAge.getText().toString().equals("")&&changeAddress.getText().toString().equals("")&&changePhonenumber.getText().toString().equals("")&&changeEmail.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"You can't leave all parameters empty !", Toast.LENGTH_LONG).show();
                }

                else {
                    //set the corresponding attributes
                    if (!changeAddress.getText().toString().equals("")) {
                        MainActivity.user.setAddress(changeAddress.getText().toString());
                    }

                    if (!changeEmail.getText().toString().equals("")) {
                        MainActivity.user.setEmail(changeEmail.getText().toString());
                    }

                    if (!changePhonenumber.getText().toString().equals("")) {
                        MainActivity.user.setPhonenumber(changePhonenumber.getText().toString());
                    }

                    if (!changeAge.getText().toString().equals("")) {
                        MainActivity.user.setAge(changeAge.getText().toString());
                    }

                    //display the information
                    Toast.makeText(getApplicationContext(),"Change successfully!", Toast.LENGTH_LONG).show();

                    //jump to the next activity
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",MainActivity.user);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_change_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
