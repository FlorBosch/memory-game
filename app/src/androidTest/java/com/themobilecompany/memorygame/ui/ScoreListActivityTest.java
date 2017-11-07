package com.themobilecompany.memorygame.ui;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.themobilecompany.memorygame.R;
import com.themobilecompany.memorygame.injection.TestComponentRule;
import com.themobilecompany.memorygame.ui.score.ScoreActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.themobilecompany.memorygame.ui.util.CustomViewMatcher.atPosition;
import static com.themobilecompany.memorygame.ui.util.CustomViewMatcher.matchesItemCount;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class ScoreListActivityTest {

    private final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());

    private final ActivityTestRule<ScoreActivity> activityTestRule =
            new ActivityTestRule<>(ScoreActivity.class);

    @Rule
    public TestRule chain = RuleChain.outerRule(component).around(activityTestRule);

    @Test
    public void testList() {
        ViewInteraction list = onView(allOf(isDisplayed(), withId(R.id.score_list)));
        list.check(matches(matchesItemCount(11)));
        for (int i = 0; i < 11; i++) {
            list.perform(scrollToPosition(i));
            list.check(matches(atPosition(i, withText("User " + i), R.id.user_name)));
            list.check(matches(atPosition(i, withText("27/08/2017"), R.id.date)));
            list.check(matches(atPosition(i, withText(String.valueOf(i)), R.id.score)));
        }
    }

}
