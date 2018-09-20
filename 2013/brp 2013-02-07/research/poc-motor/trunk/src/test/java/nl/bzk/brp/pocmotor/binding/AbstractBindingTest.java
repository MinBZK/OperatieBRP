/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.binding;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.jibx.runtime.*;

/**
 * Abstracte class voor alle binding gerelateerde tests. Deze abstract class biedt methodes voor het marshallen en
 * unmarshallen van objecten.
 */
public abstract class AbstractBindingTest<T> {

    protected static final String BRP_NAMESPACE = "http://www.brp.nl/brp/0001";

    /**
     * Voert de marshalling uit om van een object middels de JiBX databinding de juiste XML te genereren.
     * @param object het object dat gemarshalled dient te worden.
     * @return het naar XML gemarshallde object.
     * @throws JiBXException in geval van problemen tijdens de marshalling (mogelijk binding configuratie problemen).
     */
    protected String marshalObject(final T object) throws JiBXException {
        IBindingFactory bfact = BindingDirectory.getFactory(getBindingClass());
        IMarshallingContext mctx = bfact.createMarshallingContext();

        OutputStream os = new ByteArrayOutputStream();
        mctx.marshalDocument(object, "UTF-8", true, os);
        return os.toString();
    }

    /**
     * Voert de unmarshalling uit om van een XML bericht (middels de JiBX databinding) de juiste Java object(en) te
     * genereren.
     *
     * @param xml het xml bericht dat geunmarshalled dient te worden.
     * @return het object dat is gegenereerd op basis van de unmarshalling.
     * @throws JiBXException in geval van problemen tijdens de unmarshalling (mogelijk binding configuratie problemen).
     */
    protected T unmarshalObject(final String xml) throws JiBXException {
        IBindingFactory bfact = BindingDirectory.getFactory(getBindingClass());
        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();

        return (T) uctx.unmarshalDocument(new ByteArrayInputStream(xml.getBytes()), null);
    }

    /**
     * Retourneert de class van het object dat gemarshalled en geunmarshalled moet worden.
     * @return de class van het object dat gemarshalled en geunmarshalled moet worden.
     */
    protected abstract Class<T> getBindingClass();

}
