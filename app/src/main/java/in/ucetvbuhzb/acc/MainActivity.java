package in.ucetvbuhzb.acc;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;





public class MainActivity extends AppCompatActivity {
    private EditText id,password;
    private Button loginb;
    private Button studentloginbutton;
    private RequestQueue requestQueue;


     private static final String URL = "http://172.29.144.1/acc/user_control.php";
    /**

  private static final String URL = "http://ucet.esy.es/accounts/user_control.php";
     */


    private StringRequest request;
    public String transfer_user;
    Check_internet ci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = (EditText) findViewById(R.id.username);

        password = (EditText) findViewById(R.id.password);
        loginb = (Button) findViewById(R.id.loginbutton);
        studentloginbutton = (Button) findViewById(R.id.student_login_button);

        studentloginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),studentActivity.class));
            }
        });

        requestQueue = Volley.newRequestQueue(this);

        ci = new Check_internet(this);

        loginb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(ci.isConnected()){




                    request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.names().get(0).equals("success")) {
                                    transfer_user = jsonObject.getString("success");


                                    Toast.makeText(getApplicationContext(), "SUCCESS : " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), wall2.class));
                                    finish();

                                } else {
                                    Toast.makeText(getApplicationContext(), "Error :" + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("id", id.getText().toString());
                            hashMap.put("password", password.getText().toString());
                            return hashMap;
                        }

                    };

                    requestQueue.add(request);


                } else {

                    Toast.makeText(MainActivity.this, "Internet NOT Connected!", Toast.LENGTH_LONG).show();
                }


            }


        });

    }
}

