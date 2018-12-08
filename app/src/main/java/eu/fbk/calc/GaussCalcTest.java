package eu.fbk.calc;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class GaussCalcTest {

    @Rule
    public ActivityTestRule<GaussCalc> mActivityTestRule = new ActivityTestRule<>(GaussCalc.class);

    @Test
    public void gaussCalcTest() {
        ViewInteraction button = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.buttonTwo), ViewMatchers.withText("2"),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.activity_main),
                                        childAtPosition(
                                                ViewMatchers.withId(android.R.id.content),
                                                0)),
                                9),
                        ViewMatchers.isDisplayed()));
        button.perform(ViewActions.click());

        ViewInteraction button2 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.buttonTwo), ViewMatchers.withText("2"),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.activity_main),
                                        childAtPosition(
                                                ViewMatchers.withId(android.R.id.content),
                                                0)),
                                9),
                        ViewMatchers.isDisplayed()));
        button2.perform(ViewActions.click());

        ViewInteraction button3 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.buttonAdd), ViewMatchers.withText("+"),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.activity_main),
                                        childAtPosition(
                                                ViewMatchers.withId(android.R.id.content),
                                                0)),
                                17),
                        ViewMatchers.isDisplayed()));
        button3.perform(ViewActions.click());

        ViewInteraction button4 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.buttonFour), ViewMatchers.withText("4"),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.activity_main),
                                        childAtPosition(
                                                ViewMatchers.withId(android.R.id.content),
                                                0)),
                                5),
                        ViewMatchers.isDisplayed()));
        button4.perform(ViewActions.click());

        ViewInteraction button5 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.buttonEqual), ViewMatchers.withText("="),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.activity_main),
                                        childAtPosition(
                                                ViewMatchers.withId(android.R.id.content),
                                                0)),
                                13),
                        ViewMatchers.isDisplayed()));
        button5.perform(ViewActions.click());

        ViewInteraction textView = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.infoTextView), ViewMatchers.withText("22+4 = 26"),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.activity_main),
                                        childAtPosition(
                                                ViewMatchers.withId(android.R.id.content),
                                                0)),
                                0),
                        ViewMatchers.isDisplayed()));
        textView.check(ViewAssertions.matches(ViewMatchers.withText("22+4 = 26")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
