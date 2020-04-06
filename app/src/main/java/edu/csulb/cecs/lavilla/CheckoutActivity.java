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
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // TODO add more UI

        loadPage();
    }

    private void loadPage() {
        // Clear the card widget
        CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
        cardInputWidget.clear();

        // For added security, our sample app gets the publishable key from the server
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

        // Hook up the pay button to the card widget and stripe instance
        Button payButton = findViewById(R.id.payButton);
        payButton.setOnClickListener((View view) -> pay());
    }

    private void pay() {
        CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
        PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();

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
                    + "\"items\":["
                    + "399,799"
                    + "]"
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
