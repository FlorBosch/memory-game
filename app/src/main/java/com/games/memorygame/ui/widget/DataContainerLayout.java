package com.themobilecompany.memorygame.ui.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.themobilecompany.memorygame.R;
import com.themobilecompany.memorygame.util.ViewUtil;

public class DataContainerLayout extends RelativeLayout {

    private NetworkErrorListener listener;

    public DataContainerLayout(Context context) {
        super(context);
    }

    public DataContainerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DataContainerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setUp(NetworkErrorListener listener) {
        this.listener = listener;
        addNetworkErrorView();
        addLoadingView();
        setVisibility(false, true);
    }

    public void stopLoading() {
        setVisibility(false, false);
    }

    public void displayError() {
        setVisibility(true, false);
    }

    private void addNetworkErrorView() {
        View view = inflate(getContext(), R.layout.network_error_layout, null);
        Button button = view.findViewById(R.id.retry_button);
        button.setOnClickListener(v -> {
            listener.retry();
            setVisibility(false, true);
        });
        addCenteredView(view);
    }

    private void addLoadingView() {
        addCenteredView(new ProgressBar(getContext()));
    }

    private void setVisibility(boolean networkError, boolean inProgress) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getId() == R.id.network_error_container) {
                ViewUtil.setVisibility(networkError, child);
                continue;
            }
            if (child instanceof ProgressBar) {
                ViewUtil.setVisibility(inProgress, child);
                continue;
            }
            boolean visible = !networkError && !inProgress;
            ViewUtil.setVisibility(visible, child);
        }
    }

    private void addCenteredView(View view) {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(view, params);
    }

    public interface NetworkErrorListener {
        void retry();
    }
}
