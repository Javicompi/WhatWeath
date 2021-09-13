package es.jnsoft.whatweath.presentation.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import es.jnsoft.domain.usecase.FindCurrentByLatLonUseCase
import es.jnsoft.domain.usecase.FindCurrentByNameUseCase
import es.jnsoft.domain.usecase.SetUnitsUseCase
import es.jnsoft.whatweath.presentation.ui.search.SearchViewModel
import es.jnsoft.whatweath.utils.FakeSettingsRepository
import es.jnsoft.whatweath.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var settings: FakeSettingsRepository

    @Mock
    private lateinit var findCurrentByLatLonUseCase: FindCurrentByLatLonUseCase

    @Mock
    private lateinit var findCurrentByNameUseCase: FindCurrentByNameUseCase

    private lateinit var setUnitsUseCase: SetUnitsUseCase

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        settings = FakeSettingsRepository()
        setUnitsUseCase
        viewModel = SearchViewModel(
            findCurrentByLatLonUseCase,
            findCurrentByNameUseCase,
            settings,
            setUnitsUseCase
        )
    }

    @After
    fun tearDown() {
    }


}