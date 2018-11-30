package id.alif.footbalmatchschedule.api

import com.google.gson.Gson
import id.alif.footbalmatchschedule.main.DetailLastMatch
import id.alif.footbalmatchschedule.main.LastMatchView
import id.alif.footbalmatchschedule.model.*
import id.alif.footbalmatchschedule.presenter.DetailMatchPresenter
import id.alif.footbalmatchschedule.presenter.LastMatchPresenter
import id.alif.footbalmatchschedule.presenter.NextMatchPresenter
import id.alif.footbalmatchschedule.util.TestContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class ApiRepositoryTest {

    @Mock
    private
    lateinit var viewDetail: DetailLastMatch

    @Mock
    private
    lateinit var viewMatch: LastMatchView


    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenterDetail: DetailMatchPresenter
    private lateinit var presenterLast: LastMatchPresenter
    private lateinit var presenterNext: NextMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenterDetail = DetailMatchPresenter(viewDetail, apiRepository, gson, TestContextProvider())
        presenterLast = LastMatchPresenter(viewMatch, apiRepository, gson, TestContextProvider())
        presenterNext = NextMatchPresenter(viewMatch, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun doRequestDetail() {
        val teams: MutableList<DetailMatch> = mutableListOf()
        val response = ResponseDetail(teams)
        val match = "576585"
        val home = "133613"
        val away = "133615"
        val apiRepository = mock(ApiRepository::class.java)
        var detailM: List<DetailMatch> = mutableListOf()
        var homeBadge: List<HomeBadge> = mutableListOf()
        var awayBadge: List<AwayBadge> = mutableListOf()

        GlobalScope.launch {
            `when`(
              gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getDetail(match)).await(),ResponseDetail::class.java)
            ).thenReturn(response)
            `when`(
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getHomeLogo(home)).await(),ResponseDetail::class.java)
            ).thenReturn(response)
            `when`(
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getAwayLogo(away)).await(),ResponseDetail::class.java)
            ).thenReturn(response)

            presenterDetail.getDetailMatch(match,home,away)

            Mockito.verify(viewDetail).bindItem(detailM,homeBadge,awayBadge)

        }

    }

    @Test
    fun doRequestLastMatch() {

        val teams: MutableList<LastMatchTeam> = mutableListOf()
        val response = ResponseApi(teams)
        val league = "4328"
        val apiRepository = mock(ApiRepository::class.java)

        GlobalScope.launch {
            `when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getLastMatch(league)).await(),
                    ResponseApi::class.java
                )
            ).thenReturn(response)

            Mockito.verify(viewMatch).showLoading()
            Mockito.verify(viewMatch).showTeamList(teams)
            Mockito.verify(viewMatch).hideLoading()
        }
    }

        @Test
        fun doRequestNextMatch() {

            val teams: MutableList<LastMatchTeam> = mutableListOf()
            val response = ResponseApi(teams)
            val league = "4328"
            val apiRepository = mock(ApiRepository::class.java)

            GlobalScope.launch {
                `when`(
                    gson.fromJson(
                        apiRepository
                            .doRequest(TheSportDBApi.getNextMatch(league)).await(),
                        ResponseApi::class.java
                    )
                ).thenReturn(response)

                Mockito.verify(viewMatch).showLoading()
                Mockito.verify(viewMatch).showTeamList(teams)
                Mockito.verify(viewMatch).hideLoading()

            }
        }

}