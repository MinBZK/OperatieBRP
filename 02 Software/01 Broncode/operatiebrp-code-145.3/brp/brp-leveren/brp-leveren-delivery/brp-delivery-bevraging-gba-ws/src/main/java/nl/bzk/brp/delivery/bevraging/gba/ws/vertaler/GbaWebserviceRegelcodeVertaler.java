/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.vertaler;

import java.util.List;
import java.util.Optional;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.levering.lo3.conversie.regels.RegelcodeVertaler;
import org.springframework.stereotype.Component;

/**
 * Vertaalt BRP regelcodes naar Lo3 foutcodes.
 */
@Component
public class GbaWebserviceRegelcodeVertaler implements RegelcodeVertaler<AntwoordBerichtResultaat> {

    /**
     * Bepaal de foutcode uit een lijst van meldingen.
     * @param meldingen lijst van meldingen
     * @return geeft een foutcode character terug
     */
    @Override
    public Optional<AntwoordBerichtResultaat> bepaalFoutcode(final List<Melding> meldingen) {
        return Optional.of(
                meldingen.stream()
                        .map(Melding::getRegel)
                        .map(this::vertaalRegelcodeNaarFoutcode)
                        .findFirst()
                        .orElse(AntwoordBerichtResultaat.OK));
    }

    private AntwoordBerichtResultaat vertaalRegelcodeNaarFoutcode(final Regel regel) {
        return AntwoordBerichtResultaat.of(regel).orElseThrow(() -> new IllegalArgumentException(
                String.format("Ongeldige regel %s of meerdere resultaten mappen op de regel %s: %s", regel, regel, regel.getMelding())));
    }
}
