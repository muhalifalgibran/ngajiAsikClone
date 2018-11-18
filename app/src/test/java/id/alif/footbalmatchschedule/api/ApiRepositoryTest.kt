package id.alif.footbalmatchschedule.api

import id.alif.footbalmatchschedule.BuildConfig
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito
import org.mockito.Mockito.mock

class ApiRepositoryTest {

    @Test
    fun doRequestNextMatch() {
        val apiRepository = mock(ApiRepository::class.java)
        apiRepository.doRequest(TheSportDBApi.getNextMatch("4328"))
        Mockito.verify(apiRepository).doRequest(TheSportDBApi.getNextMatch("4328"))
    }

    @Test
    fun doRequestLastMatch() {
        val apiRepository = mock(ApiRepository::class.java)
        apiRepository.doRequest(TheSportDBApi.getLastMatch("4328"))
        Mockito.verify(apiRepository).doRequest(TheSportDBApi.getLastMatch("4328"))
    }

    @Test
    fun doRequestDetail() {
        val apiRepository = mock(ApiRepository::class.java)
        apiRepository.doRequest(TheSportDBApi.getDetail("576585"))
        Mockito.verify(apiRepository).doRequest(TheSportDBApi.getDetail("576585"))
    }

    @Test
    fun doRequestHomeLogo() {
        val apiRepository = mock(ApiRepository::class.java)
        apiRepository.doRequest(TheSportDBApi.getHomeLogo("133613"))
        Mockito.verify(apiRepository).doRequest(TheSportDBApi.getHomeLogo("133613"))
    }

    @Test
    fun doRequestAwayLogo(){
        val apiRepository = mock(ApiRepository::class.java)
        apiRepository.doRequest(TheSportDBApi.getAwayLogo("133615"))
        Mockito.verify(apiRepository).doRequest(TheSportDBApi.getAwayLogo("133615"))
    }


}