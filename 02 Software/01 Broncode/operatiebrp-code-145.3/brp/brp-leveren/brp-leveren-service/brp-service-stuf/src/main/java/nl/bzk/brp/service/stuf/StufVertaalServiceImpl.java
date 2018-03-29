/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.stuf;

import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.brp.domain.algemeen.Melding;
import org.springframework.stereotype.Service;

/**
 * StufVertaalServiceImpl.
 */
@Service
@Bedrijfsregel(Regel.R2445)
@Bedrijfsregel(Regel.R2446)
final class StufVertaalServiceImpl implements StufVertaalService {

    private static final String VALIDE_STUF_ANTWOORD_LEEG = "<BG:npsLk01 xmlns:BG=\"http://www.egem.nl/StUF/sector/bg/0310\"\n"
            + "\t\t\t\txmlns:StUF=\"http://www.egem.nl/StUF/StUF0301\"\n"
            + "\t\t\t\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"></BG:npsLk01>";

    @Override
    public StufTransformatieResultaat vertaal(final StufBerichtVerzoek stufBerichtVerzoek) {
        final StufTransformatieResultaat stufTransformatieResultaat = new StufTransformatieResultaat();
        //bij gebrek aan echte component dummy code op partijen voor simuleren fout situatie
        String communicatieId = "01V";
        if ("039201".equals(stufBerichtVerzoek.getStuurgegevens().getZendendePartijCode())) {
            final Melding melding = new Melding(SoortMelding.WAARSCHUWING, Regel.R2445, "meldingen tekst 1 uit vertaalservice.", communicatieId);
            stufTransformatieResultaat.getMeldingen().add(melding);
            stufTransformatieResultaat.getVertalingen().add(new StufTransformatieVertaling(VALIDE_STUF_ANTWOORD_LEEG, communicatieId));
        } else if ("017401".equals(stufBerichtVerzoek.getStuurgegevens().getZendendePartijCode())) {
            final Melding melding = new Melding(SoortMelding.FOUT, Regel.R2446, "meldingen tekst 2 uit vertaalservice.");
            stufTransformatieResultaat.getMeldingen().add(melding);
        } else {
            stufTransformatieResultaat.getVertalingen().add(new StufTransformatieVertaling(VALIDE_STUF_ANTWOORD_LEEG));
        }
        return stufTransformatieResultaat;
    }
}
