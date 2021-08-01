package com.goforer.lukohsplash.presentation.vm.home

import androidx.paging.PagingData
import com.goforer.lukohsplash.data.source.model.cache.entity.BaseTest
import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.lukohsplash.data.source.network.response.Resource
import com.goforer.lukohsplash.data.source.network.worker.NetworkBoundWorker
import com.goforer.lukohsplash.presentation.vm.Params
import com.goforer.lukohsplash.presentation.vm.Query
import com.goforer.lukohsplash.presentation.vm.TriggerViewModelTest
import com.goforer.test.kit.CoroutineTestRule
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.junit.*

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class GetPhotosViewModelTest : TriggerViewModelTest() {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @DelicateCoroutinesApi
    @Before
    override fun setup() {
        super.setup()

        viewModel = GetPhotosViewModel(getBaseUseCase(BaseTest().responseResult0))
    }

    @After
    override fun tearDown() {
        super.tearDown()
    }

    @Test
    fun pullTriggerTest() {
        viewModel.pullTrigger(Params(Query().apply {
            firstParam = 1
            secondParam = NetworkBoundWorker.NONE_ITEM_COUNT
            thirdParam = NetworkBoundWorker.LATEST
        })) {
            coroutineTestRule.managedCoroutineScope.launch {
                @Suppress("UNCHECKED_CAST")
                Assert.assertTrue(((it as Resource).getData() as? PagingData<Photo>) is PagingData<Photo>)
            }
        }
    }
}