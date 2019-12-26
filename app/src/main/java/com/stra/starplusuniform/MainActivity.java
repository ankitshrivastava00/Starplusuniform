package com.stra.starplusuniform;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.displayWebview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setBuiltInZoomControls(true);



        webView.loadUrl("https://starplusuniforms.com/");



        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                //Toast.makeText(MainActivity.this, "Old Method",Toast.LENGTH_SHORT).show();

                if(url.startsWith("http")){
                  //  Toast.makeText(MainActivity.this,"Page link",Toast.LENGTH_SHORT).show();
                    // Return false means, web view will handle the link
                    return false;
                }else if(url.startsWith("tel:")){
                    // Handle the tel: link
                    handleTelLink(url);

                    // Return true means, leave the current web view and handle the url itself
                    return true;
                }

                return false;
            }
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
               // Toast.makeText(MainActivity.this, "New Method",Toast.LENGTH_SHORT).show();

                // Get the tel: url
                String url = request.getUrl().toString();

                if(url.startsWith("http")){
                  //  Toast.makeText(MainActivity.this,"Page link",Toast.LENGTH_SHORT).show();
                    // Return false means, web view will handle the link
                    return false;
                }else if(url.startsWith("tel:")){
                    // Handle the tel: link
                    handleTelLink(url);

                    // Return true means, leave the current web view and handle the url itself
                    return true;
                }

                return false;

            }
            @Override
            public void onReceivedSslError(final WebView view, final SslErrorHandler handler, SslError error) {
                Log.d("CHECK", "onReceivedSslError");
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                AlertDialog alertDialog = builder.create();
                String message = "Certificate error.";
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        message = "The certificate authority is not trusted.";
                        break;
                    case SslError.SSL_EXPIRED:
                        message = "The certificate has expired.";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        message = "The certificate Hostname mismatch.";
                        break;
                    case SslError.SSL_NOTYETVALID:
                        message = "The certificate is not yet valid.";
                        break;
                }
                message += " Do you want to continue anyway?";
                alertDialog.setTitle("SSL Certificate Error");
                alertDialog.setMessage(message);
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("CHECK", "Button ok pressed");
                        // Ignore SSL certificate errors
                        handler.proceed();
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("CHECK", "Button cancel pressed");
                        handler.cancel();
                    }
                });
                alertDialog.show();
            }
        });
        webView.loadUrl("https://starplusuniforms.com/");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    protected void handleTelLink(String url){
        // Initialize an intent to open dialer app with specified phone number
        // It open the dialer app and allow user to call the number manually
        Intent intent = new Intent(Intent.ACTION_DIAL);

        // Send phone number to intent as data
        intent.setData(Uri.parse(url));

        // Start the dialer app activity with number
        startActivity(intent);

    }
}
