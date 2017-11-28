/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.context;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.util.xml.exception.ElementStack;

/**
 * Context tijdens encoding of decoding.
 */
public final class Context {

    private final ElementStack elementStack = new ElementStack();

    private final Map<String, Object> data = new HashMap<>();

    /**
     * Push een element op de stack.
     *
     * @param element element naam
     */
    public void pushElement(final String element) {
        elementStack.pushElement(element);
    }

    /**
     * Pop een element van de stack.
     */
    public void popElement() {
        elementStack.popElement();
    }

    /**
     * Geef de element stack.
     *
     * @return element stack als string
     */
    public ElementStack getElementStack() {
        return elementStack;
    }

    /**
     * Geef data.
     *
     * @param name naam
     * @return data
     */
    public Object getData(final String name) {
        return data.get(name);
    }

    /**
     * Zet data.
     *
     * @param name naam
     * @param value data
     */
    public void setData(final String name, final Object value) {
        data.put(name, value);
    }
}
