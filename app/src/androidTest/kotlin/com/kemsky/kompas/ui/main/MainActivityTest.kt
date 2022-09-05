package com.kemsky.kompas.ui.main


import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.kemsky.kompas.R
import com.kemsky.kompas.ui.detail.DetailActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    lateinit var scenarioMain: ActivityScenario<MainActivity>
    lateinit var scenarioDetail: ActivityScenario<DetailActivity>

    @Test
    fun mainActivityTest() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        scenarioMain = ActivityScenario.launch(intent)
        scenarioMain.moveToState(Lifecycle.State.STARTED)
        scenarioMain.onActivity {
            val appCompatImageView = onView(
                allOf(
                    withClassName(`is`("androidx.appcompat.widget.AppCompatImageView")),
                    withContentDescription("Search"),
                    childAtPosition(
                        allOf(
                            withClassName(`is`("android.widget.LinearLayout")),
                            childAtPosition(
                                withId(R.id.searchView),
                                0
                            )
                        ),
                        1
                    ),
                    isDisplayed()
                )
            )
            appCompatImageView.perform(click())

            val searchAutoComplete = onView(
                allOf(
                    withClassName(`is`("android.widget.SearchView")),
                    childAtPosition(
                        allOf(
                            withClassName(`is`("android.widget.LinearLayout")),
                            childAtPosition(
                                withClassName(`is`("android.widget.LinearLayout")),
                                1
                            )
                        ),
                        0
                    ),
                    isDisplayed()
                )
            )
            searchAutoComplete.perform(replaceText("def"), closeSoftKeyboard())

            val searchAutoComplete2 = onView(
                allOf(
                    withClassName(`is`("android.widget.SearchView")),
                    withText("def"),
                    childAtPosition(
                        allOf(
                            withClassName(`is`("android.widget.LinearLayout")),
                            childAtPosition(
                                withClassName(`is`("android.widget.LinearLayout")),
                                1
                            )
                        ),
                        0
                    ),
                    isDisplayed()
                )
            )
            searchAutoComplete2.perform(pressImeActionButton())

//            val recyclerView = onView(
//                allOf(
//                    withId(R.id.rv_users),
//                    childAtPosition(
//                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
//                        1
//                    )
//                )
//            )
//            recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))
        }
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
