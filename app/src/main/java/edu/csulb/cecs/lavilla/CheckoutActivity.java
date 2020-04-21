package edu.csulb.cecs.lavilla;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardMultilineWidget;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class CheckoutActivity extends AppCompatActivity {

    // 10.0.2.2 is the Android emulator's alias to localhost
    private static final String BACKEND_URL = "http://10.0.2.2:4242/";

    private OkHttpClient httpClient = new OkHttpClient();
    private Stripe stripe;

    CardMultilineWidget cardMultilineWidget;
    TextView checkout_billing_country, checkout_billing_address, checkout_billing_apt,
            checkout_billing_city, checkout_billing_state, checkout_billing_zipCode,
            checkout_billing_phone, checkout_billing_email;
    TextView checkout_shipping_country, checkout_shipping_address, checkout_shipping_apt,
            checkout_shipping_city, checkout_shipping_state, checkout_shipping_zipCode,
            checkout_shipping_phone, checkout_shipping_email;
    TextView checkout_shipping_address_title, checkout_order_subTotal, checkout_order_tax, checkout_order_shipping_cost, checkout_order_total;
    Button place_order_btn;
    CheckBox sameAddress;
    int total;

    String total_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // TODO add more UI
        cardMultilineWidget = findViewById(R.id.card_multiline_widget);

        checkout_shipping_address_title = findViewById(R.id.checkout_shipping_address_title);

        checkout_billing_country = findViewById(R.id.checkout_billing_country);
        checkout_billing_address = findViewById(R.id.checkout_billing_address);
        checkout_billing_apt = findViewById(R.id.checkout_billing_apt);
        checkout_billing_city = findViewById(R.id.checkout_billing_city);
        checkout_billing_state = findViewById(R.id.checkout_billing_state);
        checkout_billing_zipCode = findViewById(R.id.checkout_billing_zipCode);
        checkout_billing_phone = findViewById(R.id.checkout_billing_phone);
        checkout_billing_email = findViewById(R.id.checkout_billing_email);

        checkout_shipping_country = findViewById(R.id.checkout_shipping_country);
        checkout_shipping_address = findViewById(R.id.checkout_shipping_address);
        checkout_shipping_apt = findViewById(R.id.checkout_shipping_apt);
        checkout_shipping_city = findViewById(R.id.checkout_shipping_city);
        checkout_shipping_state = findViewById(R.id.checkout_shipping_state);
        checkout_shipping_zipCode = findViewById(R.id.checkout_shipping_zipCode);
        checkout_shipping_phone = findViewById(R.id.checkout_shipping_phone);
        checkout_shipping_email = findViewById(R.id.checkout_shipping_email);

        checkout_order_subTotal = findViewById(R.id.checkout_order_subTotal);
        checkout_order_tax = findViewById(R.id.checkout_order_tax);
        checkout_order_shipping_cost = findViewById(R.id.checkout_order_shipping_cost);
        checkout_order_total = findViewById(R.id.checkout_order_total);

        place_order_btn = findViewById(R.id.place_order_btn);
        sameAddress = findViewById(R.id.checkBox);

        Intent intent = getIntent();
//        total_string = intent.getStringExtra("total");
//        float total_float = (float) intent.getFloatExtra("total", 0);
        String orderType = intent.getStringExtra("orderType");
        int subTotal = (int) (intent.getIntExtra("total", 0));
        float subTotal_float = ((float) subTotal) / 100;
        String subTotal_string = "$" + String.format("%.2f", subTotal_float);
        checkout_order_subTotal.setText(subTotal_string);

        int tax = subTotal * 725 / 10000;
        float tax_float = ((float) tax) / 100;
        String tax_string = "$" + String.format("%.2f", tax_float);
        checkout_order_tax.setText(tax_string);

        int shippingCost;

        if(orderType.equals("DELIVERY")) {
            shippingCost = 599;
            checkout_order_shipping_cost.setText("$5.99");
            checkout_shipping_address_title.setVisibility(View.VISIBLE);
            sameAddress.setVisibility(View.VISIBLE);
            checkout_shipping_address.setVisibility(View.VISIBLE);
            checkout_shipping_apt.setVisibility(View.VISIBLE);
            checkout_shipping_city.setVisibility(View.VISIBLE);
            checkout_shipping_zipCode.setVisibility(View.VISIBLE);
            checkout_shipping_state.setVisibility(View.VISIBLE);
            checkout_shipping_email.setVisibility(View.VISIBLE);
            checkout_shipping_phone.setVisibility(View.VISIBLE);
            checkout_shipping_country.setVisibility(View.VISIBLE);
        }
        else {
            shippingCost = 0;
            checkout_order_shipping_cost.setText("$0.00");
            checkout_shipping_address_title.setVisibility(View.GONE);
            sameAddress.setVisibility(View.GONE);
            checkout_shipping_address.setVisibility(View.GONE);
            checkout_shipping_apt.setVisibility(View.GONE);
            checkout_shipping_city.setVisibility(View.GONE);
            checkout_shipping_zipCode.setVisibility(View.GONE);
            checkout_shipping_state.setVisibility(View.GONE);
            checkout_shipping_email.setVisibility(View.GONE);
            checkout_shipping_phone.setVisibility(View.GONE);
            checkout_shipping_country.setVisibility(View.GONE);
        }

        float shippingCost_float = ((float) shippingCost) / 100;
        String shippingCost_string = "$" + String.format("%.2f", shippingCost_float);
        checkout_order_shipping_cost.setText(shippingCost_string);

        total = subTotal + tax + shippingCost;
        float total_float = ((float) total) / 100;
        String total_string = "$" + String.format("%.2f", total_float);
        checkout_order_total.setText(total_string);

        Request request = new Request.Builder()
                .url(BACKEND_URL + "stripe-key")
                .get()
                .build();
        httpClient.newCall(request)
                .enqueue(new StripeKeyCallback(this));

        sameAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(sameAddress.isChecked()) {
                    checkout_shipping_address.setText(checkout_billing_address.getText());
                    checkout_shipping_apt.setText(checkout_billing_apt.getText());
                    checkout_shipping_city.setText(checkout_billing_city.getText());
                    checkout_shipping_zipCode.setText(checkout_billing_zipCode.getText());
                    checkout_shipping_state.setText(checkout_billing_state.getText());
                    checkout_shipping_email.setText(checkout_billing_email.getText());
                    checkout_shipping_phone.setText(checkout_billing_phone.getText());
                    checkout_shipping_country.setText(checkout_billing_country.getText());
                }
            }
        });
    }

    private static final class StripeKeyCallback implements Callback {
        @NonNull
        private final WeakReference<CheckoutActivity> activityRef;

        private StripeKeyCallback(@NonNull CheckoutActivity activity) {
            this.activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            final CheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            activity.runOnUiThread(() ->
                    Toast.makeText(activity, "Error: " + e.toString(), Toast.LENGTH_LONG)
                            .show()
            );
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull final Response response)
                throws IOException {
            final CheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            if (!response.isSuccessful()) {
                activity.runOnUiThread(() -> Toast.makeText(activity,
                        "Error: " + response.toString(), Toast.LENGTH_LONG).show());
            } else {
                Gson gson = new Gson();
                Type type = new TypeToken<Map<String, String>>(){}.getType();
                final ResponseBody responseBody = response.body();
                final Map<String, String> responseMap;
                if (responseBody != null) {
                    responseMap = gson.fromJson(responseBody.string(), type);
                } else {
                    responseMap = new HashMap<>();
                }

                final String stripePublishableKey = responseMap.get("publishableKey");
                if (stripePublishableKey != null) {
                    activity.runOnUiThread(() ->
                            activity.onRetrievedKey(stripePublishableKey));
                }
            }
        }
    }

    private void onRetrievedKey(@NonNull String stripePublishableKey) {
        // Configure the SDK with your Stripe publishable key so that it can make requests to the Stripe API
        final Context applicationContext = getApplicationContext();
        PaymentConfiguration.init(applicationContext, stripePublishableKey);
        stripe = new Stripe(applicationContext, stripePublishableKey);

//        // Hook up the pay button to the card widget and stripe instance
//        Button placeOrderBtn = findViewById(R.id.place_order_btn);
//        placeOrderBtn.setOnClickListener((View view) -> pay());
        place_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String billing_address = checkout_billing_address.getText().toString();
                String billing_apt = checkout_billing_apt.getText().toString();
                String billing_city = checkout_billing_city.getText().toString();
                String billing_zipCode = checkout_billing_zipCode.getText().toString();
                String billing_state = checkout_billing_state.getText().toString();
                String billing_country = checkout_billing_country.getText().toString();
                String billing_email = checkout_billing_email.getText().toString();
                String billing_phone = checkout_billing_phone.getText().toString();

                String shipping_address = checkout_shipping_address.getText().toString();
                String shipping_apt = checkout_shipping_apt.getText().toString();
                String shipping_city = checkout_shipping_city.getText().toString();
                String shipping_zipCode = checkout_shipping_zipCode.getText().toString();
                String shipping_state = checkout_shipping_state.getText().toString();
                String shipping_country = checkout_shipping_country.getText().toString();
                String shipping_email = checkout_shipping_email.getText().toString();
                String shipping_phone = checkout_shipping_phone.getText().toString();

                if(!cardMultilineWidget.validateAllFields()) {

                }
                else if(billing_address.isEmpty()) {
                    checkout_billing_address.setError("This field is required");
                    checkout_billing_address.requestFocus();
                }
                else if(billing_city.isEmpty()) {
                    checkout_billing_city.setError("This field is required");
                    checkout_billing_city.requestFocus();
                }
                else if(billing_state.isEmpty()) {
                    checkout_billing_state.setError("This field is required");
                    checkout_billing_state.requestFocus();
                }
                else if(billing_zipCode.isEmpty()) {
                    checkout_billing_zipCode.setError("This field is required");
                    checkout_billing_zipCode.requestFocus();
                }
                else if(billing_country.isEmpty()) {
                    checkout_billing_country.setError("This field is required");
                    checkout_billing_country.requestFocus();
                }
                else if(billing_email.isEmpty()) {
                    checkout_billing_email.setError("This field is required");
                    checkout_billing_email.requestFocus();
                }
                else if(shipping_address.isEmpty()) {
                    checkout_shipping_address.setError("This field is required");
                    checkout_shipping_address.requestFocus();
                }
                else if(shipping_city.isEmpty()) {
                    checkout_shipping_city.setError("This field is required");
                    checkout_shipping_city.requestFocus();
                }
                else if(shipping_state.isEmpty()) {
                    checkout_shipping_state.setError("This field is required");
                    checkout_shipping_state.requestFocus();
                }
                else if(shipping_zipCode.isEmpty()) {
                    checkout_shipping_zipCode.setError("This field is required");
                    checkout_shipping_zipCode.requestFocus();
                }
                else if(shipping_country.isEmpty()) {
                    checkout_shipping_country.setError("This field is required");
                    checkout_shipping_country.requestFocus();
                }
                else if(shipping_email.isEmpty()) {
                    checkout_shipping_email.setError("This field is required");
                    checkout_shipping_email.requestFocus();
                }
                else {
                    pay();
                }
//                Toast.makeText(CheckoutActivity.this, "Total: " + total, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pay() {
//        CardMultilineWidget cardMultilineWidget = findViewById(R.id.card_multiline_widget);
        PaymentMethodCreateParams params = cardMultilineWidget.getPaymentMethodCreateParams();

        if (params == null) {
            return;
        }
        stripe.createPaymentMethod(params, new ApiResultCallback<PaymentMethod>() {
            @Override
            public void onSuccess(@NonNull PaymentMethod result) {
                // Create and confirm the PaymentIntent by calling the sample server's /pay endpoint.
                pay(result.id, null);
            }

            @Override
            public void onError(@NonNull Exception e) {

            }
        });
    }

    private void pay(@Nullable String paymentMethodId, @Nullable String paymentIntentId) {
        final MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        final String json;
        // TODO include data to send to server
        if (paymentMethodId != null) {
            json = "{"
                    + "\"useStripeSdk\":true,"
                    + "\"paymentMethodId\":" + "\"" + paymentMethodId + "\","
                    + "\"currency\":\"usd\","
                    + "\"total\":" + total + ","
                    + "\"email\":\"" + checkout_billing_email + "\","
                    + "\"phone\":\"" + checkout_billing_phone + "\","
                    + "\"city\":\"" + checkout_billing_city + "\","
                    + "\"line1\":\"" + checkout_billing_address + "\","
                    + "\"line2\":\"" + checkout_billing_apt + "\","
                    + "\"postal_code\":\"" + checkout_billing_zipCode + "\","
                    + "\"state\":\"" + checkout_billing_state + "\","
                    + "\"country\":\"" + checkout_billing_country + "\","
                    + "\"isSavingCard\":true"
                    + "}";
        } else {
            json = "{"
                    + "\"paymentIntentId\":" +  "\"" + paymentIntentId + "\""
                    + "}";
        }
        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url(BACKEND_URL + "pay")
                .post(body)
                .build();
        httpClient
                .newCall(request)
                .enqueue(new PayCallback(this, stripe));
    }

    private static final class PayCallback implements Callback {
        @NonNull private final WeakReference<CheckoutActivity> activityRef;
        @NonNull private final Stripe stripe;

        private PayCallback(@NonNull CheckoutActivity activity, @NonNull Stripe stripe) {
            this.activityRef = new WeakReference<>(activity);
            this.stripe = stripe;
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            final CheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            activity.runOnUiThread(() ->
                    Toast.makeText(activity, "Error: " + e.toString(), Toast.LENGTH_LONG)
                            .show()
            );
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull final Response response)
                throws IOException {
            final CheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            if (!response.isSuccessful()) {
                activity.runOnUiThread(() ->
                        Toast.makeText(activity, "Error: " + response.toString(), Toast.LENGTH_LONG)
                                .show());
            } else {
                Gson gson = new Gson();
                Type type = new TypeToken<Map<String, String>>(){}.getType();
                final ResponseBody responseBody = response.body();
                final Map<String, String> responseMap;
                if (responseBody != null) {
                    responseMap = gson.fromJson(responseBody.string(), type);
                } else {
                    responseMap = new HashMap<>();
                }

                String error = responseMap.get("error");
                String paymentIntentClientSecret = responseMap.get("clientSecret");
                String requiresAction = responseMap.get("requiresAction");

                if (error != null) {
                    activity.displayAlert("Error", error, false);
                } else if (paymentIntentClientSecret != null) {
                    if ("true".equals(requiresAction)) {
                        activity.runOnUiThread(() ->
                                stripe.handleNextActionForPayment(activity, paymentIntentClientSecret));
                    } else {
                        activity.displayAlert("Payment succeeded",
                                paymentIntentClientSecret, true);
                    }
                }

            }
        }
    }

    private void displayAlert(@NonNull String title, @NonNull String message, boolean restartDemo) {
        runOnUiThread(() -> {
            final AlertDialog.Builder builder =
                    new AlertDialog.Builder(this)
                            .setTitle(title)
                            .setMessage(message);
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            builder.setPositiveButton("Ok", null);
            builder
                    .create()
                    .show();
        });
    }
}
