package nl.bzk.brp.funqmachine.verstuurder.soap

import nl.bzk.brp.funqmachine.configuratie.Environment
import org.junit.Test

class SoapParametersTest {

    @Test
    void correcteParams() {
        String url = 'http://localhost/nl.sandersmee.testtool.ontvanger/port'
        String ns = 'http://namespace'

        SoapParameters parameters = Environment.instance().getSoapParameters(url, ns)

        assert parameters.namespace == ns
        assert parameters.portName == 'port'
        assert parameters.serviceName == 'nl.sandersmee.testtool.ontvanger'
        assert parameters.wsdlURL.toString() == "${url}?wsdl"
    }
}
