/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.business.handlers.AbstractBerichtgegevensValidatieStap;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;


/**
 * De stap in de uitvoering van een bericht waarin wordt gecontroleerd of het binnenkomende bericht valide is.
 * Hierbij wordt gecontroleerd of de benodigde parameters aanwezig zijn, er geen tegenstrijdigheden in zitten
 * en of er geen ongeldige waardes tussen zitten. Eventueel geconstateerde invalide waardes worden, inclusief
 * bericht melding en zwaarte, toegevoegd aan de lijst van fouten binnen het antwoord.
 */
public class BijhoudingGegevensValidatieStap
        extends AbstractBerichtgegevensValidatieStap<BRPActie, BijhoudingsBericht, BerichtResultaat>
{

    /** {@inheritDoc} */
    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final BijhoudingsBericht bericht, final BerichtContext context,
        final BerichtResultaat resultaat)
    {

        for (BRPActie actie : bericht.getBrpActies()) {
            if (null == actie.getRootObjecten() || actie.getRootObjecten().isEmpty()) {
                resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0002,
                        String.format("%s: %s", MeldingCode.ALG0002.getOmschrijving(), "betrokkenen")));
            } else {
                valideer(actie, resultaat);
            }
        }

        return DOORGAAN_MET_VERWERKING;
    }
}
