/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.writer;

import java.util.List;

/**
 * Deze writer kan lijsten binnen het bijhoudingsmodel wegschrijven naar XML.
 *
 * @param <T> het type dat deze writer kan wegscrhijven naar XML
 */
public final class ListWriter<T> extends AbstractWriter<List<T>> {

    private final String elementNaam;

    /**
     * Maakt een nieuw ListWriter object.
     *
     * @param elementNaam de naam van het lijst element
     */
    public ListWriter(final String elementNaam) {
        this.elementNaam = elementNaam;
    }

    @Override
    protected boolean heeftChildElementen(final List<T> element) {
        return !element.isEmpty();
    }

    @Override
    protected void writeElementInhoud(final List<T> lijstElement, final WriterContext context) throws WriteException {
        for (final T child : lijstElement) {
            new ObjectWriter(child.getClass()).write(child, context);
        }
    }

    @Override
    protected String getElementNaam(final List<T> element) {
        return elementNaam;
    }
}
