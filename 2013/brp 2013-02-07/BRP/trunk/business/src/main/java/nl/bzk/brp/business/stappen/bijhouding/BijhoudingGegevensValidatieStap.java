/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bijhouding;

import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.business.stappen.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.stappen.AbstractBerichtgegevensValidatieStap;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;


/**
 * De stap in de uitvoering van een bericht waarin wordt gecontroleerd of het binnenkomende bericht valide is.
 * Hierbij wordt gecontroleerd of de benodigde parameters aanwezig zijn, er geen tegenstrijdigheden in zitten
 * en of er geen ongeldige waardes tussen zitten. Eventueel geconstateerde invalide waardes worden, inclusief
 * bericht melding en zwaarte, toegevoegd aan de lijst van fouten binnen het antwoord.
 */
public class BijhoudingGegevensValidatieStap
        extends AbstractBerichtgegevensValidatieStap<Actie, AbstractBijhoudingsBericht, BerichtVerwerkingsResultaat>
{

    /** {@inheritDoc} */
    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final AbstractBijhoudingsBericht bericht,
                                                     final BerichtContext context,
                                                     final BerichtVerwerkingsResultaat resultaat)
    {

        for (Actie actie : bericht.getBrpActies()) {
            if (null == actie.getRootObjecten() || actie.getRootObjecten().isEmpty()) {
                resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, MeldingCode.ALG0002,
                        String.format("%s: %s", MeldingCode.ALG0002.getOmschrijving(), "betrokkenen")));
            } else {
                valideer(actie, resultaat);
            }
        }

        return DOORGAAN_MET_VERWERKING;
    }

    @Override
    public boolean voerStapUit(final AbstractBijhoudingsBericht onderwerp, final BerichtContext context,
                               final BerichtVerwerkingsResultaat resultaat)
    {
        return voerVerwerkingsStapUitVoorBericht(onderwerp, context,resultaat);
    }



}
