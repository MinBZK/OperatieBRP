/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.writer;

import nl.bzk.brp.bijhouding.bericht.model.VerwerkBijhoudingsplanBericht;
import nl.bzk.brp.bijhouding.bericht.model.VerwerkBijhoudingsplanBerichtImpl;

/**
 * Zet een VerwerkBijhoudingsplanBericht om in XML.
 */
public final class VerwerkBijhoudingsplanBerichtWriter extends AbstractWriter<VerwerkBijhoudingsplanBericht> {

    private final ObjectWriter objectWriter;

    /**
     * Maak een nieuw BijhoudingVerzoekBerichtWriter object.
     */
    public VerwerkBijhoudingsplanBerichtWriter() {
        objectWriter = new ObjectWriter(VerwerkBijhoudingsplanBerichtImpl.class);
    }

    @Override
    protected boolean heeftChildElementen(final VerwerkBijhoudingsplanBericht element) {
        return objectWriter.heeftChildElementen(element);
    }

    @Override
    protected void writeElementInhoud(final VerwerkBijhoudingsplanBericht bericht, final WriterContext context) throws WriteException {
        objectWriter.writeElementInhoud(bericht, context);
    }

    @Override
    protected String getElementNaam(final VerwerkBijhoudingsplanBericht bericht) {
        return objectWriter.getElementNaam(bericht);
    }
}
