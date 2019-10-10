package edu.wmdd.midterm;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String url = "https://learn.operatoroverload.com/rental/";

    TextView textContent;

//    private RecyclerView mList;
//
//    private LinearLayoutManager linearLayoutManager;
//    private DividerItemDecoration dividerItemDecoration;
    private List<JSONObject> propertyList;
    String mainOptionSelected;
//    private RecyclerView.Adapter adapter;

    Spinner contentSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        propertyList = new ArrayList<>();

        textContent = findViewById(R.id.textContent);

        Spinner spinner = (Spinner) findViewById(R.id.optionSpinner);
//        spinner.setOnItemSelectedListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String item = parent.getItemAtPosition(pos).toString().toLowerCase();
                mainOptionSelected = item;
                getFirstLevelData(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_options, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        contentSpinner = (Spinner) findViewById(R.id.contentSpinner);
        contentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String item = parent.getItemAtPosition(pos).toString().toLowerCase();
                //getSecondLevelData(mainOptionSelected, item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });

        ArrayAdapter<JSONObject> contentDataAdapter = new ArrayAdapter<JSONObject>(this, android.R.layout.simple_spinner_item, propertyList);
        contentDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contentSpinner.setAdapter(contentDataAdapter);
    }

//    public void onItemSelected(AdapterView<?> parent, View view,
//                               int pos, long id) {
//        // An item was selected. You can retrieve the selected item using
//        String item = parent.getItemAtPosition(pos).toString().toLowerCase();
////        Log.d("itemSelected", item);
//        getFirstLevelData(item);
//    }

//    public void onNothingSelected(AdapterView<?> parent) {
//        // Another interface callback
////        final TextView textContent = findViewById(R.id.textContent);
////        textContent.setText("");
//    }

    private void getFirstLevelData(final String resource) {

        if (resource != "") {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url + resource, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    textContent.setText(response.toString());
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                            Property property = new Property();
                            property.setName(jsonObject.getString(resource.substring(0, resource.length() - 1)));
    //                        property.setId(jsonObject.getInt("id"));
    //                        property.setImage_url(jsonObject.getString("image"));

                            propertyList.add(jsonObject);
                        } catch (JSONException e) {
                            textContent.setText("Error");
                            Log.d("Test", "Calling FAB");
                            e.printStackTrace();
                            break;
                        }
                    }
//                    adapter.notifyDataSetChanged();
                    loadContentSpinner(propertyList);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley", error.toString());
                    textContent.setText("Error");
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonArrayRequest);
        }
    }

    private void getSecondLevelData(final String resource, final String item_id) {

        if (resource != "") {

            Log.e("item_id", item_id);

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url + resource + "/" + item_id, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    textContent.setText(response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley", error.toString());
                    textContent.setText("Error");
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonArrayRequest);
        }
    }

    private void loadContentSpinner(List<JSONObject> propertyList) {
        ArrayAdapter<JSONObject> contentDataAdapter = new ArrayAdapter<JSONObject>(this, android.R.layout.simple_spinner_item, propertyList);
        contentDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contentSpinner.setAdapter(contentDataAdapter);

    }
}

