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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String url = "https://learn.operatoroverload.com/rental/";

//    private RecyclerView mList;
//
//    private LinearLayoutManager linearLayoutManager;
//    private DividerItemDecoration dividerItemDecoration;
//    private List<Property> propertyList;
//    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mList = findViewById(R.id.main_list);

//        propertyList = new ArrayList<>();
//        adapter = new PropertyAdapter(getApplicationContext(),propertyList);
//
//        linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());
//
//        mList.setHasFixedSize(true);
//        mList.setLayoutManager(linearLayoutManager);
//        mList.addItemDecoration(dividerItemDecoration);
//        mList.setAdapter(adapter);

//        Button fab = findViewById(R.id.buttonGet);
////        fab.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                getData();
////            }
////        });

        Spinner spinner = (Spinner) findViewById(R.id.optionSpinner);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_options, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        String item = parent.getItemAtPosition(pos).toString().toLowerCase();
//        Log.d("itemSelected", item);
        getData(item);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        final TextView textContent = findViewById(R.id.textContent);
        textContent.setText("");
    }

    private void getData(final String resource) {

        if (resource != "") {

            final TextView textContent = findViewById(R.id.textContent);

    //        final EditText editText = findViewById(R.id.editText);

    //        final String resource = editText.getText().toString();

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

                            //propertyList.add(property);
                        } catch (JSONException e) {
                            textContent.setText("Error");
                            Log.d("Test", "Calling FAB");
                            e.printStackTrace();
                            break;
                        }
                    }
//                    adapter.notifyDataSetChanged();
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
}

