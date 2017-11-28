/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.writer;

import nl.bzk.brp.bijhouding.bericht.model.BijhoudingAntwoordBericht;

/**
 * Zet een BijhoudingAntwoordBericht om in XML.
 */
public final class BijhoudingAntwoordBerichtWriter extends AbstractWriter<BijhoudingAntwoordBericht> {

    private final ObjectWriter objectWriter;

    /**
     * Maak een nieuw BijhoudingAntwoordBerichtWriter object.
     */
    public BijhoudingAntwoordBerichtWriter() {
        objectWriter = new ObjectWriter(BijhoudingAntwoordBericht.class);
    }

    @Override
    protected boolean heeftChildElementen(final BijhoudingAntwoordBericht element) {
        return objectWriter.heeftChildElementen(element);
    }

    @Override
    protected void writeElementInhoud(final BijhoudingAntwoordBericht bericht, final WriterContext context) throws WriteException {
        objectWriter.writeElementInhoud(bericht, context);
    }

    @Override
    protected String getElementNaam(final BijhoudingAntwoordBericht bericht) {
        return objectWriter.getElementNaam(bericht);
    }
}
