package nl.bzk.brp.funqmachine.verstuurder.soap

import javax.xml.namespace.QName
import nl.bzk.brp.funqmachine.configuratie.Environment

/**
 * Wrapper voor de parameters die nodig zijn voor een soap request.
 */
class SoapParameters {
    private String wsdlUrl
    private String namespace
    private String serviceName
    private String portName

    /**
     * Constructor die de gegevens voor deze parameters mee krijgt.
     *
     * @param endpoint endopont met placeholder voor url.
     *      Deze wordt uit de environment gehaald en ingevuld
     * @param namespace namespace voor soap
     *
     * @see Environment#parse(java.lang.String)
     */
    SoapParameters(final String endpoint, final String namespace) {
        this.namespace = namespace
        this.wsdlUrl = "$endpoint?wsdl"

        def parts = endpoint.split('/')
        this.serviceName = parts[-2]
        this.portName = parts[-1]
    }

    URL getWsdlURL() {
        return new URL(wsdlUrl)
    }

    QName getServiceQName() {
        return new QName(namespace, serviceName)
    }

    QName getPortQName() {
        return new QName(namespace, portName)
    }

    boolean equals(final o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        final SoapParameters that = (SoapParameters) o

        if (namespace != that.namespace) return false
        if (portName != that.portName) return false
        if (serviceName != that.serviceName) return false
        if (wsdlUrl != that.wsdlUrl) return false

        return true
    }

    int hashCode() {
        int result
        result = wsdlUrl.hashCode()
        result = 31 * result + namespace.hashCode()
        result = 31 * result + serviceName.hashCode()
        result = 31 * result + portName.hashCode()
        return result
    }


    @Override
    public String toString() {
        return "SoapParameters{" +
            "wsdlUrl='" + wsdlUrl + '\'' +
            ", namespace='" + namespace + '\'' +
            ", serviceName='" + serviceName + '\'' +
            ", portName='" + portName + '\'' +
            '}';
    }
}
