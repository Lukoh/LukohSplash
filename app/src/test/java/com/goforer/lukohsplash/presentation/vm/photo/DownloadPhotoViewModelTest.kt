package com.goforer.lukohsplash.presentation.vm.photo

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import com.goforer.lukohsplash.domain.processor.photo.DownloadPhotosUseCase
import com.goforer.lukohsplash.presentation.vm.Params
import com.goforer.lukohsplash.presentation.vm.Query
import com.goforer.lukohsplash.presentation.vm.TriggerViewModelTest
import kotlinx.coroutines.DelicateCoroutinesApi
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mockStatic
import java.io.File

class DownloadPhotoViewModelTest : TriggerViewModelTest() {
    private lateinit var file: File
    private lateinit var url: String
    private lateinit var environment: Environment

    @Before
    @DelicateCoroutinesApi
    override fun setup() {
        super.setup()

        viewModel = DownloadPhotoViewModel(getProcessorUseCase())
    }

    @Before
    fun setFile() {
        mockStatic(Environment::class.java)
        file = Mockito.mock(File::class.java)
        Mockito.`when`(file.path).thenReturn(Environment.DIRECTORY_PICTURES)
        url = "https://images.unsplash.com/photo-1625582709381-8350d178fdc6?ixid=MnwyNDc1MTN8MHwxfGFsbHwzMXx8fHx8fDJ8fDE2MjcyNjE1NjU\\u0026ixlib=rb-1.2.1"
    }

    @After
    override fun tearDown() {
        super.tearDown()
    }

    @Test
    fun pullTriggerTest() {
        val context: Context = Mockito.mock(Context::class.java)
        val manager: DownloadManager? = Mockito.mock(DownloadManager::class.java)

        Mockito.`when`(context.getSystemService(Context.DOWNLOAD_SERVICE)).thenReturn(manager)

        viewModel.pullTrigger(Params(Query().apply {
            firstParam = manager!!
            secondParam = url
            thirdParam = file
        })) {
            when (it) {
                DownloadManager.STATUS_FAILED -> {
                    Assert.assertEquals(it, DownloadManager.STATUS_FAILED)
                }

                DownloadManager.STATUS_PAUSED -> {
                    Assert.assertEquals(it, DownloadManager.STATUS_PAUSED)
                }

                DownloadManager.STATUS_PENDING -> {
                    Assert.assertEquals(it, DownloadManager.STATUS_PENDING)
                }

                DownloadManager.STATUS_RUNNING -> {
                    Assert.assertEquals(it, DownloadManager.STATUS_RUNNING)
                }

                DownloadManager.STATUS_SUCCESSFUL -> {
                    Assert.assertEquals(it, DownloadManager.STATUS_SUCCESSFUL)
                }

                DownloadPhotosUseCase.FILE_EXISTED -> {
                    Assert.assertEquals(it, DownloadPhotosUseCase.FILE_EXISTED)
                }
            }
        }
    }
}