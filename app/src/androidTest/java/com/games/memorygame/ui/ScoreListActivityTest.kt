package com.games.memorygame.ui

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.ViewInteraction
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4

import com.games.memorygame.R
import com.games.memorygame.injection.TestComponentRule
import com.games.memorygame.ui.score.ScoreActivity

import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.v7.widget.RecyclerView
import android.view.View
import com.games.memorygame.ui.util.CustomViewMatcher.atPosition
import com.games.memorygame.ui.util.CustomViewMatcher.matchesItemCount
import org.hamcrest.Matchers.allOf

@RunWith(AndroidJUnit4::class)
class ScoreListActivityTest {

    private val component = TestComponentRule(InstrumentationRegistry.getTargetContext())

    private val activityTestRule = ActivityTestRule(ScoreActivity::class.java)

    @Rule @JvmField var chain: TestRule = RuleChain.outerRule(component).around(activityTestRule)

    @Test
    fun testList() {
        val list = onView(allOf<View>(isDisplayed(), withId(R.id.score_list)))
        list.check(matches(matchesItemCount(11)))
        (0..10).forEach {
            list.perform(scrollToPosition<RecyclerView.ViewHolder>(it))
            list.check(matches(atPosition(it, withText("User " + it), R.id.user_name)))
            list.check(matches(atPosition(it, withText("27/08/2017"), R.id.date)))
            list.check(matches(atPosition(it, withText(it.toString()), R.id.score)))
        }
    }

}
