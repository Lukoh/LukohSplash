package com.goforer.lukohsplash.presentation.vm.photo

import com.goforer.lukohsplash.data.source.model.cache.entity.PhotoInfoTest
import com.goforer.lukohsplash.presentation.vm.Params
import com.goforer.lukohsplash.presentation.vm.Query
import com.goforer.lukohsplash.presentation.vm.TriggerViewModelTest
import kotlinx.coroutines.DelicateCoroutinesApi
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetPhotoInfoViewModelTest : TriggerViewModelTest() {
    @Before
    @DelicateCoroutinesApi
    override fun setup() {
        super.setup()

        viewModel = GetPhotoInfoViewModel(getBaseUseCase(PhotoInfoTest().photo0))
    }

    @After
    override fun tearDown() {
        super.tearDown()
    }

    @Test
    fun pullTriggerTest() {
        viewModel.pullTrigger(Params(Query().apply {
            firstParam = "Jvw3pxgeiZw"
            secondParam = -1
        })) {
            Assert.assertEquals(it, getResponseResult(PhotoInfoTest().photo0))
        }
    }
}