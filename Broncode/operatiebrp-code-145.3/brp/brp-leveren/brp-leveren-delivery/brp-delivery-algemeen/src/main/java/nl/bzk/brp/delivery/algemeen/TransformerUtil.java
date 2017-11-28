/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.algemeen;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import org.w3c.dom.Node;

/**
 * Utiliteitsklasse rond {@link javax.xml.transform.Transformer}.
 */
public final class TransformerUtil {

    private static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();

    private TransformerUtil() {

    }

    /**
     * Transformeer een {@link Source} naar een {@link Node}.
     * @param source de {@link Source}
     * @return de {@link Node}
     * @throws TransformerException bij een transformatiefout
     */
    public static Node initializeNode(final Source source) throws TransformerException {
        final Transformer t = TRANSFORMER_FACTORY.newTransformer();
        final DOMResult domResult = new DOMResult();
        t.transform(source, domResult);
        return domResult.getNode().getFirstChild();
    }
}
