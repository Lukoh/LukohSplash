package com.goforer.lukohsplash.domain.processor.photo

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.goforer.lukohsplash.presentation.ui.HomeActivity
import com.goforer.lukohsplash.presentation.uitest.TestFragment
import com.goforer.lukohsplash.presentation.vm.Params
import com.goforer.lukohsplash.presentation.vm.Query
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@InternalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class CheckFileExistUseCaseInstrumentedTest {
    private lateinit var context: Context
    private lateinit var file: File
    private lateinit var url: String
    private lateinit var useCaseForCheckFileExist: CheckFileExistUseCase

    @get:Rule
    var activityRule: ActivityScenarioRule<HomeActivity> =
        ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setup() {
        file = File("Pictures")
        url =
            "https://images.unsplash.com/photo-1625582709381-8350d178fdc6?ixid=MnwyNDc1MTN8MHwxfGFsbHwzMXx8fHx8fDJ8fDE2MjcyNjE1NjU\\u0026ixlib=rb-1.2.1"
        context = InstrumentationRegistry.getInstrumentation().targetContext
        useCaseForCheckFileExist = CheckFileExistUseCase(context)
    }

    @Test
    fun executeTest() {
        val scenario = launchFragmentInContainer<TestFragment>()

        scenario.onFragment { fragment ->
            fragment.setupPermission(object : TestFragment.PermissionCallback {
                override fun onPermissionGranted() {
                    runBlocking {
                        useCaseForCheckFileExist.run(this, Params(Query().apply {
                            firstParam = url
                            secondParam = 1
                        })).collect {
                            assert(!it)
                        }
                    }
                }
            })
        }
    }
}
