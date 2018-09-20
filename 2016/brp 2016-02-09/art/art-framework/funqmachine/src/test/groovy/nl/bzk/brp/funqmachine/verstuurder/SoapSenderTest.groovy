package nl.bzk.brp.funqmachine.verstuurder

import nl.bzk.brp.funqmachine.configuratie.Environment
import nl.bzk.brp.funqmachine.verstuurder.soap.SoapParameters
import org.junit.Test

class SoapSenderTest {

    @Test()
    void geeftResponseTerug() {
        SoapParameters parameters = Environment.instance().getSoapParameters('http://localhost/nl.sandersmee.testtool.ontvanger/port', '')
        SoapVerstuurder processor = new SoapVerstuurder()
    }
}
