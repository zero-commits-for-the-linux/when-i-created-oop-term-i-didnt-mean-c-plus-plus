package com.example.trpp.NavigationActivity.ui.queue;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;


import com.example.trpp.NavigationActivity.ui.mail.MailFragment;
import com.example.trpp.databinding.FragmentQueueBinding;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.IOUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class QueueFragment extends Fragment {

    private FragmentQueueBinding binding;
    WebView webView;
    boolean flag = false;
    private String response;

    public QueueFragment() {
    }

    private class MyToQueueThread implements Runnable {

        @Override
        public void run() {
            Log.e("Отслеживаю потоки", "поток - " + Thread.currentThread());
            String register_url = "http://185.18.55.107:5000/joinqueue";
            JSONObject register = new JSONObject();
            try {
                register.put("fullname", getActivity().getIntent().getStringExtra("fio"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                URL url = new URL(register_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");

                OutputStream os = conn.getOutputStream();
                os.write(register.toString().getBytes(StandardCharsets.UTF_8));
                os.close();

                InputStream in = new BufferedInputStream(conn.getInputStream());
                response = IOUtils.toString(in, "UTF-8");
                System.out.println(response);
                in.close();

                conn.disconnect();
            } catch (Exception e) {
                System.out.println(e);
                Log.e("______________________________________", String.valueOf(e));
            }
        }
    }

    private class MyOutQueueThread implements Runnable {

        @Override
        public void run() {
            Log.e("Отслеживаю потоки", "поток - " + Thread.currentThread());
            String register_url = "http://185.18.55.107:5000/leavequeue";
            JSONObject register = new JSONObject();
            try {
                register.put("fullname", getActivity().getIntent().getStringExtra("fio"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                URL url = new URL(register_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");

                OutputStream os = conn.getOutputStream();
                os.write(register.toString().getBytes(StandardCharsets.UTF_8));
                os.close();

                InputStream in = new BufferedInputStream(conn.getInputStream());
                response = IOUtils.toString(in, "UTF-8");
                System.out.println(response);
                in.close();

                conn.disconnect();
            } catch (Exception e) {
                System.out.println(e);
                Log.e("______________________________________", String.valueOf(e));
            }
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentQueueBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button to_queue = binding.toQueue;
        Button out_queue = binding.outQueue;

        webView = binding.webView;

        webView.setWebViewClient(new QueueFragment.MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        to_queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = true;
                Thread myThread = new Thread(new MyToQueueThread());
                myThread.start();
                webView.loadUrl("185.18.55.107");
            }
        });
        webView.loadUrl("185.18.55.107");

        out_queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = false;
                Thread myThread = new Thread(new MyOutQueueThread());
                myThread.start();
                webView.loadUrl("185.18.55.107");
            }
        });
        webView.loadUrl("185.18.55.107");

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,String contentDisposition, String mimetype,long contentLength) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        webView.loadUrl("185.18.55.107");

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        webView = binding.webView;
        webView.destroy();
        binding = null;
    }

    private class MyWebViewClient extends WebViewClient {
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }
}