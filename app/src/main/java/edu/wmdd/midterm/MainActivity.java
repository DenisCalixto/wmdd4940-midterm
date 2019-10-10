package edu.wmdd.midterm;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
//    private String url = "http://159.65.44.135/api/jobs.json";

    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Property> propertyList;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = findViewById(R.id.main_list);

        propertyList = new ArrayList<>();
        adapter = new PropertyAdapter(getApplicationContext(),propertyList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        Button fab = findViewById(R.id.buttonGet);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

    }

    private void getData() {

        final TextView textContent = findViewById(R.id.textContent);

        final EditText editText = findViewById(R.id.editText);

        final String resource = editText.getText().toString();

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
                adapter.notifyDataSetChanged();
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

