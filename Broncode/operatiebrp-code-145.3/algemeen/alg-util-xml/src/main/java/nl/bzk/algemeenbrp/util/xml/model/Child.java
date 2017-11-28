/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.model;

import java.io.Writer;
import nl.bzk.algemeenbrp.util.xml.context.Context;
import nl.bzk.algemeenbrp.util.xml.exception.XmlException;

/**
 * Kind van een {@link CompositeObject}.
 * @param <T> type van het kind
 */
public interface Child<T> {

    /**
     * Geef de toegang tot de waarde.
     * @return toegang
     */
    Accessor<T> getAccessor();

    /**
     * Geef de geconfigureerde naam.
     * @return naam
     */
    String getName();

    /**
     * Schrijf het kind.
     * @param context encodeer context
     * @param value waarde
     * @param writer writer om XML te schrijven
     * @throws XmlException bij configuratie fouten (annotaties in het kind); of bij encodeer fouten
     */
    void encode(final Context context, final T value, final Writer writer) throws XmlException;

    /**
     * Bepaal of het kind als attribuut geschreven moet worden of als element.
     * @return true, als dit kind als attribute geschreven moet worden, anders false
     */
    boolean isAttribute();

    /**
     * Verwerk het element (wordt enkel aangeroepen indien de 'voorgaande' aanroep van
     * {@linkplain #canDecode(org.w3c.dom.Element)} true heeft geretourneerd.
     * @param context decodeer context
     * @param node XML Dom Node
     * @param previousValue de vorige gelezen waarde (voor eenzelfde child, binnen dezelfde parent)
     * @return gelezen waarde
     * @throws XmlException bij configuratie fouten (annotaties in het kind); of bij decodeer fouten
     */
    T decode(final Context context, final org.w3c.dom.Node node, T previousValue) throws XmlException;

    /**
     * Bepaal of dit kind deze node kan verwerken.
     * @param node de te verwerken node
     * @return true, als dit kind deze node kan verwerken, anders false
     */
    boolean canDecode(final org.w3c.dom.Node node);
}
