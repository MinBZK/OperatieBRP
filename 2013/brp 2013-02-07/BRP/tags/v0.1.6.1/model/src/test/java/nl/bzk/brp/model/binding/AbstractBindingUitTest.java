/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.binding;

import java.io.ByteArrayOutputStream;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;


/**
 * Deze klasse biedt standaard methodes om objecten naar xml te marshallen.
 *
 * @param <T> het object type die gemarshalled wordt.
 */
public abstract class AbstractBindingUitTest<T> extends AbstractBindingTest<T> {

    /**
     * Voert de marshalling uit om van een object middels de JiBX databinding de juiste XML te genereren.
     *
     * @param object het object dat gemarshalled dient te worden.
     * @return het naar XML gemarshallde object.
     * @throws org.jibx.runtime.JiBXException in geval van problemen tijdens de marshalling (mogelijk binding
     * configuratie problemen).
     */
    protected String marshalObject(final T object) throws JiBXException {
        IBindingFactory bfact = BindingDirectory.getFactory(getBindingClass());
        IMarshallingContext mctx = bfact.createMarshallingContext();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        mctx.marshalDocument(object, "UTF-8", true, os);

        return os.toString();
    }

    /**
     * Voert de marshalling uit om van een object middels de JiBX databinding de juiste XML te genereren.
     *
     * @param object het object dat gemarshalled dient te worden.
     * @return het naar XML gemarshallde object.
     * @throws org.jibx.runtime.JiBXException in geval van problemen tijdens de marshalling (mogelijk binding
     * configuratie problemen).
     */
    protected String marshalObjectTest(final T object) throws JiBXException {
        return marshalObject(object, "binding-model-test");
    }

    /**
     * Voert de marshalling uit om van een object middels de JiBX databinding de juiste XML te genereren.
     *
     * @param object het object dat gemarshalled dient te worden.
     * @param factoryName Naam van de te gebruiken factory.
     * @return het naar XML gemarshallde object.
     * @throws org.jibx.runtime.JiBXException in geval van problemen tijdens de marshalling (mogelijk binding
     * configuratie problemen).
     */
    protected String marshalObject(final T object, final String factoryName) throws JiBXException {
        IBindingFactory bfact = BindingDirectory.getFactory(factoryName, getBindingClass());
        IMarshallingContext mctx = bfact.createMarshallingContext();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        mctx.marshalDocument(object, "UTF-8", true, os);

        return os.toString();
    }

}
