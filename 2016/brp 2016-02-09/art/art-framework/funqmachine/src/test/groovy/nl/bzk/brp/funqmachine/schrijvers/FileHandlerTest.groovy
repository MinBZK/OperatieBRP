package nl.bzk.brp.funqmachine.schrijvers

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class FileHandlerTest {
    FileHandler fileHandler
    File path

    @Rule
    public ExpectedException thrown = ExpectedException.none()

    @Before
    void setUp() {
        fileHandler = new FileHandler()
    }

    @Test
    void geeftOutputBestand() {
        File output = fileHandler.geefOutputFile('not-1-expected.xml')

        assert !output.exists()
    }

    @Test
    void schrijftFileObvReferentie() {
        fileHandler.schrijfFile('DATA/response/output.txt', 'some context')

        File verwacht = fileHandler.geefOutputFile('DATA/response/output.txt')

        assert verwacht.exists()
        assert verwacht.text == 'some context'
    }
}
