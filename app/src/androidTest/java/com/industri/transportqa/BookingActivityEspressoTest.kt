package com.industri.transportqa

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookingActivityEspressoTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun positiveBookingFlow_validNikAndName_showsSuccessStatus() {
        onView(withId(R.id.etNik))
            .perform(typeText("3209123456780001"), closeSoftKeyboard())

        onView(withId(R.id.etName))
            .perform(typeText("Ahmad Dahlan"), closeSoftKeyboard())

        onView(withId(R.id.btnSubmitBooking))
            .perform(click())

        onView(withId(R.id.tvBookingStatus))
            .check(matches(isDisplayed()))
            .check(matches(withText("Pemesanan Tiket Berhasil Diverifikasi")))
    }

    @Test
    fun negativeBookingFlow_emptyNik_showsNikRequiredError() {
        onView(withId(R.id.etName))
            .perform(typeText("Ahmad Dahlan"), closeSoftKeyboard())

        onView(withId(R.id.btnSubmitBooking))
            .perform(click())

        onView(withId(R.id.tvBookingStatus))
            .check(matches(isDisplayed()))
            .check(matches(withText("NIK tidak boleh kosong")))
    }
}
