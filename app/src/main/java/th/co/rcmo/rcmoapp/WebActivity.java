package th.co.rcmo.rcmoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        final WebView webView = new WebView(this);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(webView, url);
                Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT).show();
               // finish();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub

                Log.d("URL => ", url);    // current URL

                if(url.contains("success")){
                    finish();
                }
                view.loadUrl(url);
                return true;
            }


        });
        webView.loadUrl(getIntent().getStringExtra("link"));

        setContentView(webView);


        /*
       if (webView.()){
           startActivity(new Intent(WebActivity.this, LoginActivity.class));
        }
        */


    }
}
