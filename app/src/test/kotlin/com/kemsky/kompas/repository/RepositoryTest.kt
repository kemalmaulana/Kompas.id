package com.kemsky.kompas.repository

import com.kemsky.kompas.data.repository.GithubRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    private lateinit var repository: GithubRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = mock(GithubRepository::class.java)
    }

    @Test
    fun `test get all pokemon`() = runTest(UnconfinedTestDispatcher()) {
        repository.getAllPokemon(10, 0)
        verify(repository).getAllPokemon(10, 0)
    }

    @Test
    fun `test get detail pokemon`() = runTest(UnconfinedTestDispatcher()) {
        repository.getDetailPokemon(1)
        verify(repository).getDetailPokemon(1)
    }

    @Test
    fun `test get form pokemon`() = runTest(UnconfinedTestDispatcher()) {
        repository.getFormPokemon(1)
        verify(repository).getFormPokemon(1)
    }

    @Test
    fun `test get species pokemon`() = runTest(UnconfinedTestDispatcher()) {
        repository.getSpeciesPokemon(1)
        verify(repository).getSpeciesPokemon(1)
    }

}