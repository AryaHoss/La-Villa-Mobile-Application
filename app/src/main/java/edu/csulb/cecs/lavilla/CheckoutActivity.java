package edu.csulb.cecs.lavilla;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;
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
    TextInputLayout checkout_billing_country_layout, checkout_billing_address_layout,
            checkout_billing_apt_layout, checkout_billing_city_layout, checkout_billing_state_layout,
            checkout_billing_zipCode_layout, checkout_billing_phone_layout, checkout_billing_email_layout;
    TextView checkout_billing_country, checkout_billing_address, checkout_billing_apt,
            checkout_billing_city, checkout_billing_state, checkout_billing_zipCode,
            checkout_billing_phone, checkout_billing_email;
    Button place_order_btn;
    int total;

    String total_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // TODO add more UI
        cardMultilineWidget = findViewById(R.id.card_multiline_widget);
        checkout_billing_country = findViewById(R.id.checkout_billing_country);
        checkout_billing_address = findViewById(R.id.checkout_billing_address);
        checkout_billing_apt = findViewById(R.id.checkout_billing_apt);
        checkout_billing_city = findViewById(R.id.checkout_billing_city);
        checkout_billing_state = findViewById(R.id.checkout_billing_state);
        checkout_billing_zipCode = findViewById(R.id.checkout_billing_zipCode);
        checkout_billing_phone = findViewById(R.id.checkout_billing_phone);
        checkout_billing_email = findViewById(R.id.checkout_billing_email);

        checkout_billing_country_layout = findViewById(R.id.checkout_billing_country_layout);
        checkout_billing_address_layout = findViewById(R.id.checkout_billing_address_layout);
        checkout_billing_apt_layout = findViewById(R.id.checkout_billing_apt_layout);
        checkout_billing_city_layout = findViewById(R.id.checkout_billing_city_layout);
        checkout_billing_state_layout = findViewById(R.id.checkout_billing_state_layout);
        checkout_billing_zipCode_layout = findViewById(R.id.checkout_billing_zipCode_layout);
        checkout_billing_phone_layout = findViewById(R.id.checkout_billing_phone_layout);
        checkout_billing_email_layout = findViewById(R.id.checkout_billing_email_layout);

        place_order_btn = findViewById(R.id.place_order_btn);

        Intent intent = getIntent();
//        total_string = intent.getStringExtra("total");
//        float total_float = (float) intent.getFloatExtra("total", 0);
        total = (int) (intent.getIntExtra("total", 0));

        Request request = new Request.Builder()
                .url(BACKEND_URL + "stripe-key")
                .get()
                .build();
        httpClient.newCall(request)
                .enqueue(new StripeKeyCallback(this));
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

                String address = checkout_billing_address.getText().toString();
                String apt = checkout_billing_apt.getText().toString();
                String city = checkout_billing_city.getText().toString();
                String zipCode = checkout_billing_zipCode.getText().toString();
                String state = checkout_billing_state.getText().toString();
                String country = checkout_billing_country.getText().toString();
                String email = checkout_billing_email.getText().toString();
                String phone = checkout_billing_phone.getText().toString();

                if(!cardMultilineWidget.validateAllFields()) {

                }
                else if(address.isEmpty()) {
                    checkout_billing_address.setError("Address is required");
                    checkout_billing_address.requestFocus();
//                    TextInputLayout checkout_billing_address_layout = (TextInputLayout) findViewById(R.id.checkout_billing_address_layout);
//                    checkout_billing_address_layout.setError("Address is required.");
                }
                else if(city.isEmpty()) {
                    checkout_billing_city.setError("City is required");
                    checkout_billing_city.requestFocus();
//                    TextInputLayout checkout_billing_city_layout = (TextInputLayout) findViewById(R.id.checkout_billing_city_layout);
//                    checkout_billing_city_layout.setError("City is required.");
                }
                else if(state.isEmpty()) {
                    checkout_billing_state.setError("State is required");
                    checkout_billing_state.requestFocus();
//                    TextInputLayout checkout_billing_state_layout = (TextInputLayout) findViewById(R.id.checkout_billing_state_layout);
//                    checkout_billing_state_layout.setError("State is required.");
                }
                else if(zipCode.isEmpty()) {
                    checkout_billing_zipCode.setError("ZIP Code is required");
                    checkout_billing_zipCode.requestFocus();
//                    TextInputLayout checkout_billing_zipCode_layout = (TextInputLayout) findViewById(R.id.checkout_billing_zipCode_layout);
//                    checkout_billing_zipCode_layout.setError("ZIP Code is required.");
                }
                else if(country.isEmpty()) {
                    checkout_billing_country.setError("Country is required");
                    checkout_billing_country.requestFocus();
//                    TextInputLayout checkout_billing_country_layout = (TextInputLayout) findViewById(R.id.checkout_billing_country_layout);
//                    checkout_billing_country_layout.setError("Country is required.");
                }
                else if(email.isEmpty()) {
                    checkout_billing_email.setError("Email is required");
                    checkout_billing_email.requestFocus();
//                    TextInputLayout checkout_billing_email_layout = (TextInputLayout) findViewById(R.id.checkout_billing_email_layout);
//                    checkout_billing_email_layout.setError("Email is required.");
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
