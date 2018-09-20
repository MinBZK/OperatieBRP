package nl.bzk.brp.soapui

import nl.bzk.brp.soapui.excel.TestStatus
import org.junit.Test

import static nl.bzk.brp.soapui.excel.OutputKolommen.*

class ResultatenTest {
    Resultaten resultaten = new Resultaten()

    @Test
    void success() {
        resultaten << new Properties()
        resultaten.voegToe(new Properties())

        assert resultaten.size() == 2
    }

    @Test
    void resultaatToevoegen() {
        Properties properties = new Properties()

        properties.put(TestGeval.naam, 'VP.0001')
        properties.put(Volgnummer.naam, '01')
        properties.put(Status.naam, 'FAILED')

        resultaten << properties

        assert !resultaten.isSuccess()
        assert resultaten.failed() == 1
    }

    @Test
    void errorsCount() {
        Properties properties = new Properties()

        properties.put(TestGeval.naam, 'Foo01')
        properties.put(Volgnummer.naam, '1')
        properties.put(Status.naam, TestStatus.Handmatig_Testen.waarde)
        properties.put(Debug_Log.naam, 'some log')

        resultaten << properties

        assert resultaten.errors() == 1
        assert resultaten.failed() == 0
    }

    @Test
    void skippedCount() {
        Properties properties = new Properties()

        properties.put(TestGeval.naam, 'Foo01')
        properties.put(Volgnummer.naam, '1')
        properties.put(Status.naam, TestStatus.In_Quarantaine.waarde)
        properties.put(Debug_Log.naam, 'some log')
        properties.put(Assertion.naam, TestStatus.In_Quarantaine.waarde)

        resultaten << properties

        assert resultaten.skipped() == 1
        assert resultaten.failed() == 0
    }

    @Test
    void failedQuarantaine() {
        Properties properties = new Properties()

        properties.put(TestGeval.naam, 'Foo01')
        properties.put(Volgnummer.naam, '1')
        properties.put(Status.naam, TestStatus.Gefaald.waarde)
        properties.put(Debug_Log.naam, 'some log')
        properties.put(Assertion.naam, TestStatus.In_Quarantaine.waarde)

        resultaten << properties

        assert resultaten.skipped() == 0
        assert resultaten.failed() == 1

    }
}
