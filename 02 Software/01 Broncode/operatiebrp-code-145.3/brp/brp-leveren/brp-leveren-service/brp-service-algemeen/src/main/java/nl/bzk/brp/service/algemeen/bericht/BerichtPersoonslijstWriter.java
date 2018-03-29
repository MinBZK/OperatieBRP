/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.bericht;

import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.berichtmodel.BerichtElementAttribuut;

/**
 * BerichtPersoonslijstWriter.
 */
public final class BerichtPersoonslijstWriter {

    private BerichtPersoonslijstWriter() {
    }

    /**
     * @param berichtElement berichtElement
     * @param writer writer
     */
    public static void write(final BerichtElement berichtElement, final BerichtWriter writer) {
        writer.startElement(berichtElement.getNaam());
        for (BerichtElementAttribuut berichtElementAttribuut : berichtElement.getAttributen()) {
            writer.attribute(berichtElementAttribuut.getNaam(), berichtElementAttribuut.getWaarde());
        }
        if (berichtElement.getWaarde() != null) {
            writer.writeCharacters(berichtElement.getWaarde());
        }
        for (BerichtElement berichtElementKind : berichtElement.getElementen()) {
            write(berichtElementKind, writer);
        }

        writer.endElement();
    }
}
