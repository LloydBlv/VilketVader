package com.example.data

import app.cash.turbine.test
import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import assertk.assertions.startsWith
import com.example.data.datasource.WeatherApiClientDefault
import com.example.data.di.NetModule
import com.example.data.repositories.WeatherRepositoryDefault
import com.example.domain.usecases.GetWeatherUseCase
import com.example.testing.FakeLocalDataSource
import com.example.testing.MainDispatcherRule
import com.example.testing.TestData
import com.example.testing.assertTestWeather
import com.example.testing.createFakeWeatherStore
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.reflect.instanceOf
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class GetWeatherUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private fun createdEngine(
        response: MockRequestHandleScope.(HttpRequestData) -> HttpResponseData,
    ): MockEngine {
        return MockEngine {
            assertThat(it.url.toString())
                .startsWith("https://api.openweathermap.org/data/2.5/weather?q=stockholm")
            response.invoke(this, it)
        }
    }

    @Test
    fun `UseCase returns success when upstream returns data`() = runTest {
        val repository = createWeatherRepository(
            testDispatcher = mainDispatcherRule.testDispatcher,
            response = {
                respond(
                    content = TestData.getRawJsonFromResources(),
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString(),
                    ),
                )
            },
        )
        val usecase = GetWeatherUseCase(repository)
        usecase.flow.test {
            expectNoEvents()
            usecase.invoke(GetWeatherUseCase.Params(TestData.STOCKHOLM, language = "en"))
            assertThat(awaitItem()).all {
                transform { it.getOrNull() }
                    .isNotNull()
                    .all { assertTestWeather() }
            }
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `UseCase returns failure when upstream fails`() = runTest {
        val repository = createWeatherRepository(
            testDispatcher = mainDispatcherRule.testDispatcher,
            response = {
                respond(
                    content = "",
                    status = HttpStatusCode.NotFound,
                )
            },
        )
        val usecase = GetWeatherUseCase(repository)
        usecase.flow.test {
            expectNoEvents()
            usecase.invoke(GetWeatherUseCase.Params(TestData.STOCKHOLM, language = "en"))
            assertThat(awaitItem()).all {
                transform { it.getOrNull() }.isNull()
                transform { it.exceptionOrNull() }.run {
                    instanceOf(ClientRequestException::class)
                    transform { it as ClientRequestException }
                        .transform { it.response.status }.isEqualTo(HttpStatusCode.NotFound)
                }
            }
        }
    }

    private fun createWeatherRepository(
        testDispatcher: CoroutineDispatcher,
        response: MockRequestHandleScope.(HttpRequestData) -> HttpResponseData,
    ): WeatherRepositoryDefault {
        val engine = createdEngine { response.invoke(this, it) }

        val repository = WeatherRepositoryDefault(
            client = WeatherApiClientDefault(
                ktorfit = dagger.Lazy { NetModule.provideKtorfit(createMockedClient(engine)) },
            ),
            localDataSource = FakeLocalDataSource(),
            weatherStore = createFakeWeatherStore(testDispatcher = testDispatcher),
            ioDispatcher = testDispatcher,

        )
        return repository
    }

    private fun createMockedClient(engine: MockEngine): HttpClient {
        return HttpClient(engine) {
            expectSuccess = true
            install(ContentNegotiation) {
                json(NetModule.provideJson())
            }
            install(HttpTimeout) {
                val timeout = 30000L
                connectTimeoutMillis = timeout
                requestTimeoutMillis = timeout
                socketTimeoutMillis = timeout
            }
        }
    }
}
