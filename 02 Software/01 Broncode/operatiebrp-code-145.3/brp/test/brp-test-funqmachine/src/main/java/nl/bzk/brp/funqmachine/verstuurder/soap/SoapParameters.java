/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.verstuurder.soap;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Wrapper voor de parameters die nodig zijn voor een soap request.
 */
public final class SoapParameters {
    private String wsdlUrl;
    private String namespace;
    private String serviceName;
    private String portName;

    /**
     * Constructor die de gegevens voor deze parameters mee krijgt.
     *
     * @param endpoint endopont met placeholder voor url. Deze wordt uit de environment gehaald en ingevuld
     * @param namespace namespace voor soap
     *
     */
    public SoapParameters(final String endpoint, final String namespace) {
        this.namespace = namespace;
        this.wsdlUrl = endpoint + "?wsdl";

        final String[] parts = endpoint.split("/");
        final int partsLenght = parts.length;
        this.serviceName = parts[partsLenght - 2];
        this.portName = parts[partsLenght - 1];
    }

    /**
     * Geeft de URL terug waar de WSDL te vinden is.
     *
     * @return een {@link URL} naar de WSDL
     * @throws MalformedURLException als het maken van het {@link URL} niet kan omdat de input geen valide url is.
     */
    public URL getWsdlURL() throws MalformedURLException {
        return new URL(wsdlUrl);
    }

    /**
     * Geeft de service terug.
     * @return de service als {@link QName}
     */
    QName getServiceQName() {
        return new QName(namespace, serviceName);
    }

    /**
     * Geeft de portname terug.
     * @return de portname als {@link QName}
     */
    public QName getPortQName() {
        return new QName(namespace, portName);
    }

    /**
     * Geeft de namespace terug.
     * @return de namespace
     */
    public String getNamespace() {
        return namespace;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof SoapParameters) {
            final SoapParameters otherSoapParameters = (SoapParameters) obj;
            return new EqualsBuilder().append(this.wsdlUrl, otherSoapParameters.wsdlUrl)
                                      .append(this.namespace, otherSoapParameters.namespace)
                                      .append(this.serviceName, otherSoapParameters.serviceName)
                                      .append(this.portName, otherSoapParameters.portName)
                                      .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(wsdlUrl).append(namespace).append(serviceName).append(portName).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("wsdlUrl", wsdlUrl)
                                        .append("namespace", namespace)
                                        .append("serviceName", serviceName)
                                        .append("portName", portName)
                                        .toString();
    }
}
