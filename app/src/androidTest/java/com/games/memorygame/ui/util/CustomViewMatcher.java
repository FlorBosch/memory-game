package com.themobilecompany.memorygame.ui.util;


import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public final class CustomViewMatcher {

    private CustomViewMatcher() {
    }

    public static Matcher<View> atPosition(final int position, final Matcher<View> itemMatcher,
                                           final int targetViewId) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("at position: " + position);
                itemMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(final RecyclerView recyclerView) {
                ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                return viewHolder != null &&
                        itemMatcher.matches(viewHolder.itemView.findViewById(targetViewId));
            }
        };
    }

    public static Matcher<View> matchesItemCount(final int size) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("list size is: " + size);
            }

            @Override
            public boolean matchesSafely(final RecyclerView recyclerView) {
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                return adapter != null && adapter.getItemCount() == size;
            }
        };
    }

}

