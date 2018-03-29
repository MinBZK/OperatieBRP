/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.exception;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Element stack (om positie binnen XML verwerking te bewaren).
 */
public final class ElementStack {

    private final Deque<String> stack = new ArrayDeque<>();

    /**
     * Push een element op de stack.
     *
     * @param element element naam
     */
    public void pushElement(final String element) {
        stack.push(element);
    }

    /**
     * Pop een element van de stack.
     */
    public void popElement() {
        stack.pop();
    }

    /**
     * Geeft de huidige stack terug als array.
     * @return de huidige stack als array
     */
    public String[] toArray() {
        return stack.toArray(new String [stack.size()]);
    }

    /**
     * Geef de element stack.
     *
     * @return element stack als string
     */
    String asString() {
        final StringBuilder sb = new StringBuilder();
        for (final String element : stack) {
            if (sb.length() != 0) {
                sb.insert(0, ".");
            }
            sb.insert(0, element);
        }
        return sb.toString();
    }

}
