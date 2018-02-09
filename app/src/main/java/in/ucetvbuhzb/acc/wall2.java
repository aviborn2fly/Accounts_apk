package in.ucetvbuhzb.acc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class wall2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText rollno;

    private Button searchbutton;
    private RequestQueue requestQueue;
    Check_internet ci;


     private static final String URL = "http://172.29.144.1/acc/search.php";
    private String imgURL = "http://172.29.144.1/acc/testImage.php";
    /**

    private static final String URL = "http://ucet.esy.es/accounts/search.php";
    private String imgURL = "http://ucet.esy.es/accounts/getImage.php";
     */
    private String imgURL2;
    private String errorimgURL = "http://ucet.esy.es/accounts/images/imagenotfound.png";
    private ImageView studentImage;


    private StringRequest request;

    private String intermediate;
    private Integer cat_payment = 0, paid = 0, varint = 0, feepaidtillint = 0, currentdate = 0;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    TextView name, roll1, fname, mname, branch, session, dobTextview, mob, category, email, address, feecategory, current_sem, totalpayment, totalfeepaid, totalfeedue, feepaidtill, date;

    NavigationView navigationView =null;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall2);

        MainFragment fragment = new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


/**
        studentImage = (ImageView) findViewById(R.id.studentImage);
*/

        rollno = (EditText) findViewById(R.id.searchroll);

        requestQueue = Volley.newRequestQueue(this);





        name = (TextView) findViewById(R.id.name);
        roll1 = (TextView) findViewById(R.id.roll);
        fname = (TextView) findViewById(R.id.fname);
        mname = (TextView) findViewById(R.id.mname);
        branch = (TextView) findViewById(R.id.branch);
        session = (TextView) findViewById(R.id.session);
        dobTextview = (TextView) findViewById(R.id.dobTextview);
        mob = (TextView) findViewById(R.id.mob);
        category = (TextView) findViewById(R.id.category);
        email = (TextView) findViewById(R.id.email);
        address = (TextView) findViewById(R.id.address);
        feecategory = (TextView) findViewById(R.id.feecategory);
        current_sem = (TextView) findViewById(R.id.current_sem);
        totalpayment = (TextView) findViewById(R.id.totalpayment);
        totalfeepaid = (TextView) findViewById(R.id.totalfeepaid);
        totalfeedue = (TextView) findViewById(R.id.totalfeedue);
        feepaidtill = (TextView) findViewById(R.id.feepayedtill);
        date = (TextView) findViewById(R.id.date);

        ci = new Check_internet(this);


        searchbutton = (Button) findViewById(R.id.searchbutton);
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ci.isConnected()){


/**
                    imgURL2 = imgURL + "?id=" + rollno.getText().toString();


                    ImageRequest imageRequest = new ImageRequest(imgURL2,

                            new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap response) {
                                    studentImage.setImageBitmap(response);
                                }
                            }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(wall2.this, "Student Image can not be loaded...", Toast.LENGTH_SHORT).show();


                                    error.printStackTrace();
                        }
                    });
                    MySingleton.getInstance(wall2.this).addToRequestQueue(imageRequest);

*/











                    request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {



                        @Override
                        public void onResponse(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);


                                if (jsonObject.names().get(0).equals("student") && jsonObject.names().get(1).equals("sum(amount)") && jsonObject.names().get(2).equals("payment_for_cat") ){


                                    JSONArray jsonArray = new JSONArray(jsonObject.getString("student"));
                                    JSONArray jsonArray1 = new JSONArray(jsonObject.getString("sum(amount)"));
                                    JSONArray jsonArray2 = new JSONArray(jsonObject.getString("payment_for_cat"));
                                    JSONArray jsonArray3 = new JSONArray(jsonObject.getString("date"));



                                    Toast.makeText(getApplicationContext(), "Student Record found...", Toast.LENGTH_LONG).show();

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                    JSONObject jsonObject2 = jsonArray1.getJSONObject(0);
                                    JSONObject jsonObject3 = jsonArray2.getJSONObject(0);
                                    JSONObject jsonObject4 = jsonArray3.getJSONObject(0);


                                    intermediate = "Name : " + jsonObject1.getString("name");
                                    name.setText(intermediate);




                                    intermediate = "Roll : " + jsonObject1.getString("roll");
                                    roll1.setText(intermediate);

                                    intermediate = "DOB : " + jsonObject1.getString("dob");
                                    dobTextview.setText(intermediate);

                                    intermediate = "Father's Name : " + jsonObject1.getString("f_name");
                                    fname.setText(intermediate);

                                    intermediate = "Mother's Name : " + jsonObject1.getString("m_name");
                                    mname.setText(intermediate);

                                    intermediate = "Branch : " + jsonObject1.getString("branch");
                                    branch.setText(intermediate);
                                    intermediate = "Session : " + jsonObject1.getString("session_start") + " - " + jsonObject1.getString("session_end");
                                    session.setText(intermediate);
                                    intermediate = "Mobile : " + jsonObject1.getString("mob");
                                    mob.setText(intermediate);
                                    intermediate = "Category : " + jsonObject1.getString("cat");
                                    category.setText(intermediate);
                                    intermediate = "Email : " + jsonObject1.getString("email");
                                    email.setText(intermediate);
                                    intermediate = "Address : " + jsonObject1.getString("address");
                                    address.setText(intermediate);
                                    intermediate = "Fee Category : " + jsonObject1.getString("categ_fee");
                                    feecategory.setText(intermediate);





                                    intermediate = "Total amount paid : " + jsonObject2.getString("sum(amount)");
                                    totalfeepaid.setText(intermediate);



                                    intermediate = "Today's Date : " +jsonObject4.getString("sysdate()") ;
                                    date.setText(intermediate);

                                    intermediate = jsonObject2.getString("sum(amount)");
                                    paid=0;

                                    if (intermediate != "null") {

                                        paid = Integer.parseInt(intermediate);
                                    }

                                    intermediate = jsonObject3.getString("payment_for_cat");
                                    totalpayment.setText("Total Fee :" +intermediate);

                                    feepaidtillint = 0;
                                    varint = 0;

                                    if (intermediate != "null") {

                                        cat_payment = Integer.parseInt(intermediate);

                                        varint = cat_payment - paid;




                                        totalfeedue.setText("Total Fee due : " + String.valueOf(varint));

                                        feepaidtillint = paid / (cat_payment / 8);

                                    }
                                    feepaidtill.setText("Fee paid till sem : " + String.valueOf(feepaidtillint));

                                    varint = Integer.parseInt(jsonObject4.getString("year(sysdate())"));

                                    varint = varint-Integer.parseInt(jsonObject1.getString("session_start"));
                                    if (varint==0) {
                                        current_sem.setText("Current Sem : 1 ");
                                    }
                                    else if (varint==1){
                                        current_sem.setText("Current Sem : 2 or 3");
                                    }
                                    else if (varint==2){
                                        current_sem.setText("Current Sem : 4 or 5");
                                    }
                                    else if (varint==3){
                                        current_sem.setText("Current Sem : 6 or 7");
                                    }
                                    else if(varint==4){
                                        current_sem.setText("Current Sem : 8");
                                    }
                                    else {
                                        current_sem.setText("Can't determine");
                                    }





                                } else {
                                    Toast.makeText(getApplicationContext(), "Error :" + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();

                                    intermediate = "Not Found!!!";
                                    name.setText("Name : "+intermediate);
                                    roll1.setText("Roll : "+intermediate);
                                    dobTextview.setText("DOB : "+intermediate);
                                    fname.setText("Father's Name : "+intermediate);
                                    mname.setText("Mother's Name : "+intermediate);
                                    branch.setText("Branch : "+intermediate);
                                    session.setText("Session : "+intermediate);
                                    mob.setText("Mobile : "+intermediate);
                                    category.setText("Category : "+intermediate);
                                    email.setText("Email : "+intermediate);
                                    address.setText("Address : "+intermediate);
                                    feecategory.setText("Fee Category : "+intermediate);
                                    totalfeepaid.setText("Total Fee Paid : "+intermediate);
                                    totalpayment.setText("Total Payment : "+intermediate);
                                    totalfeedue.setText("Total fee due : "+intermediate);
                                    feepaidtill.setText("Fee paid till : "+intermediate);
                                    current_sem.setText("Current Sem : "+intermediate);
                                }







                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {


                                }

                            })


                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("roll", rollno.getText().toString());


                            return hashMap;
                        }
                    };


                    requestQueue.add(request);



                } else {

                    Toast.makeText(wall2.this, "Internet NOT Connected!", Toast.LENGTH_LONG).show();
                }


            }



        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            // Handle the camera action
            MainFragment fragment = new MainFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(getApplicationContext(),AboutFullscreenActivity.class));

        }/** else if (id == R.id.nav_manage) {


        } */else if (id == R.id.nav_share) {

            String url = "http://www.ucetvbuhzb.in";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);

        } else if (id == R.id.nav_send) {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"aviborn2fly@yahoo.in"});
            intent.putExtra(Intent.EXTRA_SUBJECT,"Acc feedback");
            intent.putExtra(Intent.EXTRA_TEXT,"Acc Feedback: ");
            try {
                startActivity(Intent.createChooser(intent, "Send Email"));
            }catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(wall2.this, "Thete is no email clients installed.", Toast.LENGTH_SHORT).show();
            }


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
