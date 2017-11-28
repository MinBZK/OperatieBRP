/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.bericht;

import java.util.List;
import nl.bzk.brp.domain.algemeen.Melding;

/**
 * BerichtMeldingWriter.
 */
public final class BerichtMeldingWriter {

    private BerichtMeldingWriter() {
    }

    /**
     * @param meldingen meldingen
     * @param writer de writer
     */
    public static void write(final List<Melding> meldingen, final BerichtWriter writer) {
        if (meldingen != null && !meldingen.isEmpty()) {
            writer.startElement("meldingen");
            for (final Melding melding : meldingen) {
                writer.startElement("melding");
                writer.attribute(BerichtConstants.OBJECTTYPE, "Melding");
                writer.attribute("referentieID", melding.getReferentieID() != null ? melding.getReferentieID() : "0");
                writer.element("regelCode", melding.getRegel().getCode());
                writer.element("soortNaam", melding.getSoort().getNaam());
                writer.element("melding", melding.getMeldingTekst());
                writer.endElement();
            }
            writer.endElement();
        }
    }
}
