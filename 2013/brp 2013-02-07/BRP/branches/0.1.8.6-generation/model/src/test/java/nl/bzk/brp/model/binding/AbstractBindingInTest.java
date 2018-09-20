/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.binding;

import java.io.ByteArrayInputStream;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;


/**
 * Binding test class voor het testen van mappings.
 */
public abstract class AbstractBindingInTest<T> extends AbstractBindingTest<T> {

    @Override
    protected String getBindingFactoryName() {
        return "binding-test-bericht";
    }

    /**
     * Voert de unmarshalling uit om van een XML bericht (middels de JiBX databinding) de juiste Java object(en) te
     * genereren.
     *
     * @param xml het xml bericht dat geunmarshalled dient te worden.
     * @return het object dat is gegenereerd op basis van de unmarshalling.
     * @throws org.jibx.runtime.JiBXException in geval van problemen tijdens de unmarshalling (mogelijk binding
     *             configuratie problemen).
     */
    protected T unmarshalObject(final String xml) throws JiBXException {
        IBindingFactory bfact = BindingDirectory.getFactory(getBindingFactoryName(), getBindingClass());
        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();

        @SuppressWarnings("unchecked")
        T unmarshalDocument = (T) uctx.unmarshalDocument(new ByteArrayInputStream(xml.getBytes()), "UTF-8");

        return unmarshalDocument;
    }
}
