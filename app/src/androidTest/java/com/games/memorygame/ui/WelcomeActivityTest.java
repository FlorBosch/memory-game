package com.themobilecompany.memorygame.ui;


import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.themobilecompany.memorygame.MemoryGameApplication;
import com.themobilecompany.memorygame.R;
import com.themobilecompany.memorygame.injection.TestComponentRule;
import com.themobilecompany.memorygame.injection.component.TestComponent;
import com.themobilecompany.memorygame.model.game.BoardConfiguration;
import com.themobilecompany.memorygame.model.game.LevelFactory;
import com.themobilecompany.memorygame.model.game.LevelType;
import com.themobilecompany.memorygame.model.game.PlayerMode;
import com.themobilecompany.memorygame.ui.welcome.WelcomeActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.themobilecompany.memorygame.ui.util.CustomViewMatcher.matchesItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class WelcomeActivityTest {

    private final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());

    private final ActivityTestRule<WelcomeActivity> activityTestRule =
            new ActivityTestRule<>(WelcomeActivity.class);

    @Rule
    public TestRule chain = RuleChain.outerRule(component).around(activityTestRule);

    @Inject
    BoardConfiguration configuration;

    @Before
    public void setUp() throws Exception {
        TestComponent component = (TestComponent) MemoryGameApplication
                .get(InstrumentationRegistry.getTargetContext())
                .getComponent();
        component.inject(this);
    }

    @Test
    public void appActivityTest() {
        assertThat(configuration.getLevel().getPointsPerMatch(),
                is(LevelFactory.getLevel(LevelType.EASY).getPointsPerMatch()));
        assertThat(configuration.getPlayerMode(), is(PlayerMode.SINGLE_PLAYER));
        onView(withId(R.id.radio_normal)).perform(click());
        onView(withId(R.id.radio_multi_player_mode)).perform(click());

        assertThat(configuration.getLevel().getPointsPerMatch(),
                is(LevelFactory.getLevel(LevelType.NORMAL).getPointsPerMatch()));
        assertThat(configuration.getPlayerMode(), is(PlayerMode.MULTI_PLAYER));

        onView(withId(R.id.btn_scores)).perform(click());
        ViewInteraction list = onView(allOf(isDisplayed(), withId(R.id.score_list)));
        list.check(matches(matchesItemCount(11)));
        pressBack();

        onView(withId(R.id.btn_start)).perform(click());
        onView(withId(R.id.first_player_score)).check(matches(isDisplayed()));
        onView(withId(R.id.second_player_score)).check(matches(isDisplayed()));
        onView(withId(R.id.timer)).check(matches(not(isDisplayed())));
        pressBack();
        onView(withText(R.string.cancel)).perform(click());

        onView(withId(R.id.radio_single_player_mode)).perform(click());
        onView(withId(R.id.btn_start)).perform(click());
        onView(withId(R.id.first_player_score)).check(matches(isDisplayed()));
        onView(withId(R.id.second_player_score)).check(matches(not(isDisplayed())));
        onView(withId(R.id.timer)).check(matches(isDisplayed()));
    }

}
