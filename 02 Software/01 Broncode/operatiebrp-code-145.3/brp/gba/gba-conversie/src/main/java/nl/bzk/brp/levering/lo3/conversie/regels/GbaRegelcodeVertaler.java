/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.regels;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch.RegelConversietabel;
import org.springframework.stereotype.Component;

/**
 * Vertaalt BRP regelcodes naar Lo3 foutcodes.
 */
@Component
public class GbaRegelcodeVertaler implements RegelcodeVertaler<Character> {

    private static final RegelConversietabel REGEL_CONVERSIE = new RegelConversietabel();

    /**
     * Bepaal de foutcode uit een lijst van meldingen.
     * @param meldingen lijst van meldingen
     * @return geeft een foutcode character terug
     */
    @Override
    public Optional<Character> bepaalFoutcode(final List<Melding> meldingen) {
        return meldingen.stream()
                .map(melding -> melding.getRegel().getCode())
                .map(this::vertaalRegelcodeNaarFoutcode)
                .findFirst()
                .map(Supplier::get);
    }

    private Supplier<Character> vertaalRegelcodeNaarFoutcode(final String regelcode) {
        if (!REGEL_CONVERSIE.valideerBrp(regelcode)) {
            return () -> {
                throw new IllegalArgumentException(
                        "Regelcode heeft geen vertaling naar GBA foutcode (in conversie van regel brp naar foutcode lo3): " + regelcode);
            };
        }
        final Character foutcode = REGEL_CONVERSIE.converteerNaarLo3(regelcode);
        if (foutcode == null) {
            return () -> {
                throw new IllegalArgumentException(
                        "Regelcode niet verwacht als fout (in conversie van regel brp naar foutcode lo3): " + regelcode);
            };
        } else {
            return () -> foutcode;
        }
    }
}
