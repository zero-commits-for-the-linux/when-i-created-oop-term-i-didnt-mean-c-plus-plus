package com.example.trpp.NavigationActivity.ui.queue;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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


public class QueueFragment extends Fragment {

    private FragmentQueueBinding binding;
    WebView webView;
    public QueueFragment() {
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentQueueBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button to_queue = binding.toQueue;
        Button out_queue = binding.outQueue;

        to_queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "This is to queue btn", Toast.LENGTH_LONG).show();

            }
        });

        out_queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "This is out queue btn", Toast.LENGTH_LONG).show();
            }
        });

        webView = binding.webView;

        webView.setWebViewClient(new QueueFragment.MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

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