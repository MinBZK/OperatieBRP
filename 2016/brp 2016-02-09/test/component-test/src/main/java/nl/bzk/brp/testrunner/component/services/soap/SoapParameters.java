/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component.services.soap;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;

/**
 * Wrapper voor de parameters die nodig zijn voor een soap request.
 */
public class SoapParameters {
    private String wsdlUrl;
    private String namespace;
    private String serviceName;
    private String portName;

    /**
     * Constructor die de gegevens voor deze parameters mee krijgt.
     *
     * @param endpoint endopont met placeholder voor url.
     *      Deze wordt uit de environment gehaald en ingevuld
     * @param namespace namespace voor soap
     *
     */
    public SoapParameters(final String endpoint, final String namespace) {
        this.namespace = namespace;
        this.wsdlUrl = endpoint + "?wsdl";

        String [] parts = endpoint.split("/");
        this.serviceName = parts[parts.length -2];
        this.portName = parts[parts.length -1];
    }

    URL getWsdlURL() throws MalformedURLException {
        return new URL(wsdlUrl);
    }

    QName getServiceQName() {
        return new QName(namespace, serviceName);
    }

    QName getPortQName() {
        return new QName(namespace, portName);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final SoapParameters that = (SoapParameters) o;

        if (wsdlUrl != null ? !wsdlUrl.equals(that.wsdlUrl) : that.wsdlUrl != null) {
            return false;
        }
        if (namespace != null ? !namespace.equals(that.namespace) : that.namespace != null) {
            return false;
        }
        if (serviceName != null ? !serviceName.equals(that.serviceName) : that.serviceName != null) {
            return false;
        }
        return !(portName != null ? !portName.equals(that.portName) : that.portName != null);

    }

    @Override
    public int hashCode() {
        int result = wsdlUrl != null ? wsdlUrl.hashCode() : 0;
        result = 31 * result + (namespace != null ? namespace.hashCode() : 0);
        result = 31 * result + (serviceName != null ? serviceName.hashCode() : 0);
        result = 31 * result + (portName != null ? portName.hashCode() : 0);
        return result;
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
