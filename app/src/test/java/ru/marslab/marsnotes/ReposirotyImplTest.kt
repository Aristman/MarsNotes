package ru.marslab.marsnotes

import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import ru.marslab.marsnotes.data.RepositoryImpl
import ru.marslab.marsnotes.domain.Callback
import ru.marslab.marsnotes.domain.model.Note

class ReposirotyImplTest {

    private var repositoryImpl: RepositoryImpl = RepositoryImpl()

    private val testNotes = listOf(
        Note(id = "111"),
        Note(id = "222"),
        Note(id = "333"),
        Note(id = "444"),
        Note(id = "555")
    )

    @Before
    fun clearNotes() {
        repositoryImpl.clear()
    }

    @Test
    fun addNote_isSuccessful() {
        val newNote = Note(id = "111")
        repositoryImpl.addNote(
            newNote,
            TestCallback(
                onSuccessful = { assertTrue(it >= 0) }
            )
        )
    }

    @Test
    fun addNotes_isSuccessful() {
        var result = true
        testNotes.forEach {
            repositoryImpl.addNote(
                it,
                TestCallback(
                    onSuccessful = { result = result || true },
                    onError = { result = result || false }
                )
            )
        }
        assertTrue(result)
    }

    private class TestCallback<D>(
        private val onSuccessful: (data: D) -> Unit,
        private val onError: (() -> Unit)? = null
    ) : Callback<D> {
        override fun onSuccess(result: D) {
            onSuccessful(result)
        }

        override fun onError() {
            onError
        }
    }
}
