package com.example.payphone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    String code, phone, idcard;
    TextView price_shirt, price_pants, total;
    Button btn_add_shirt, btn_add_pants, btn_remove_shirt, btn_remove_pants, btn_buy;
    RequestQueue requestQueue;
    public JSONObject params = new JSONObject();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        code = getIntent().getStringExtra("code");
        phone = getIntent().getStringExtra("phone");
        idcard = getIntent().getStringExtra("idcard");

        price_shirt = findViewById(R.id.txtPriceOfShirt);
        price_pants = findViewById(R.id.txtPriceOfPants);
        total = findViewById(R.id.txtTotal);
        btn_add_shirt = findViewById(R.id.btnAddShirt);
        btn_add_pants = findViewById(R.id.btnAddPants);
        btn_remove_shirt = findViewById(R.id.btnRemoveShirt);
        btn_remove_pants = findViewById(R.id.btnRemovePants);
        btn_buy = findViewById(R.id.btnBuy);
        btn_remove_shirt.setVisibility(View.INVISIBLE);
        btn_remove_pants.setVisibility(View.INVISIBLE);

        btn_add_shirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = Integer.parseInt(price_shirt.getText().toString());
                int v_total = Integer.parseInt(total.getText().toString());
                int result = price + v_total;
                total.setText(String.valueOf(result));
                btn_remove_shirt.setVisibility(View.VISIBLE);
                btn_add_shirt.setEnabled(false);
            }
        });

        btn_add_pants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = Integer.parseInt(price_pants.getText().toString());
                int v_total = Integer.parseInt(total.getText().toString());
                int result = price + v_total;
                total.setText(String.valueOf(result));
                btn_remove_pants.setVisibility(View.VISIBLE);
                btn_add_pants.setEnabled(false);
            }
        });


        btn_remove_shirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = Integer.parseInt(price_shirt.getText().toString());
                int v_total = Integer.parseInt(total.getText().toString());
                int result = v_total - price;
                total.setText(String.valueOf(result));
                btn_remove_shirt.setVisibility(View.INVISIBLE);
                btn_add_shirt.setEnabled(true);
            }
        });

        btn_remove_pants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = Integer.parseInt(price_pants.getText().toString());
                int v_total = Integer.parseInt(total.getText().toString());
                int result = v_total - price;
                total.setText(String.valueOf(result));
                btn_remove_pants.setVisibility(View.INVISIBLE);
                btn_add_pants.setEnabled(true);
            }
        });

        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postRequest();
            }
        });


    }

    public void getParams() {
        int clientTransctID = (int) (100000 * Math.random());
        int v_total = Integer.parseInt(total.getText().toString())*100;
        int result_tax = 3;
        int amountWT = v_total - result_tax;
        try {
            params.put("phoneNumber", phone);
            params.put("countryCode", code);
            params.put("clientUserId", idcard);
            params.put("reference", "none");
            params.put("responseUrl", "http://paystoreCZ.com/confirm.php");
            params.put("amount", v_total);
            params.put("amountWithTax", amountWT);
            params.put("amountWithoutTax", 0);
            params.put("tax", result_tax);
            params.put("clientTransactionId", clientTransctID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void postRequest() {
        getParams();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://pay.payphonetodoesposible.com/api/Sale";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast toast = Toast.makeText(getApplicationContext(), (CharSequence) response, Toast.LENGTH_LONG);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer GmCAftJkEpsNdds74EkgL7eZBNpEws_GttZOAPzis2GI9-1nw0RcXsPJg3GGVuDQDJ3_IgjIv9sa2knhIstQanD-j3itPIrF6rz_PVMMY0yJxf4TdqfHW5XtXnsNl-ktUlEJATDpR6ulfjur-82VtEHnc0U9Ayi1MQ4VD7U1TVJjjPp6FY-8qKjMlkNA7Bmtje1hZA_3MFLxLMHj_8AMYtQOCgt4EllLbCGoQbUwQoUzLghjOyt-7Qbo5UlUj2iwz20Af346szHuEpY_GHTE8VgNq9WK3VYmJR7I6QVAuPb_yOaMXSeqUDNzJN0hgcmqaci3G1UILhfudgl-tmOoU-9l0f8");
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
}