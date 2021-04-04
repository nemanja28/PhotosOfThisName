package com.ns.photosofthisname

import android.app.Instrumentation
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.ns.photosofthisname.di.appModules
import com.ns.photosofthisname.feature.photosearch.coordinator.PhotoSearchActivity
import com.ns.photosofthisname.feature.photosearch.view.PhotoViewHolder
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin


@RunWith(AndroidJUnit4ClassRunner::class)
class PhotoSearchActivityTest {

    private val intent = Intent(ApplicationProvider.getApplicationContext(), PhotoSearchActivity::class.java)
    private var activityRule : ActivityScenario<PhotoSearchActivity>? = null
    private lateinit var device: UiDevice

    @Before
    fun start() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        Intents.init()
        loadKoinModules(appModules(true))
    }

    @Test
    fun test_recyclerview_elements_for_valid_response() {
        activityRule = ActivityScenario.launch(intent)
        onView(withId(R.id.nameEditText)).perform(typeText(SEARCH_TERM))
        onView(withId(R.id.searchButton)).perform(click())
        onView(withId(R.id.photoList)).check(matches(atPosition(0, 1, withText(PHOTO_TITLE))))
        onView(withId(R.id.photoList)).check(matches(atPosition(0, 2, withText(PHOTO_OWNER))))
        onView(withId(R.id.photoList)).perform(RecyclerViewActions.actionOnItemAtPosition<PhotoViewHolder>(0, click()))

        val expected = allOf(IntentMatchers.hasAction(Intent.ACTION_VIEW))
        Intents.intending(expected).respondWith(Instrumentation.ActivityResult(0, null))
        Intents.intended(expected)
        device.pressBack()
    }

    @After
    fun tearDown() {
        Intents.release()
        stopKoin()
    }

    companion object{
        private const val SEARCH_TERM = "nemanja"
        private const val PHOTO_TITLE = "1232058833"
        private const val PHOTO_OWNER = "82909129@N08"

    }
}