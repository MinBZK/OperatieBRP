/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import java.util.ArrayList;
import java.util.Collection;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;
import nl.bzk.brp.bevraging.business.berichtcmds.OpvragenPersoonBerichtCommand;
import nl.bzk.brp.bevraging.domein.Persoon;


/**
 * De stap in de uitvoering van een bericht waarin (achteraf) wordt gecontroleerd of de afnemer (op basis van
 * betreffende abonnement) gerechtigd is de persoon/personen in het resultaat te zien. Personen in het resultaat
 * waartoe een afnemer niet gerechtigd is, worden in deze stap dan ook uit het antwoord gefilterd. Dit daar
 * uitsluitend personen aan de afnemer mogen worden geleverd of door de afnemer mogen worden opgevraagd die
 * binnen de op dat moment geldige populatie vallen van het abonnement waarvoor opgevraagd of geleverd wordt.
 */
public class PopulatieAutorisatieAchterafStap extends AbstractBerichtVerwerkingsStap {

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean voerVerwerkingsStapUitVoorBericht(final BrpBerichtCommand<?, ?> bericht) {
        if (bericht instanceof OpvragenPersoonBerichtCommand) {
            verstrekkingsbeperkingToepassenIndienNodig((OpvragenPersoonBerichtCommand) bericht);
        }
        return DOORGAAN_MET_VERWERKING;
    }

    /**
     * Verwijder alle personen met een verstrekkingsbeperking.
     * @param bericht het bericht waarop de verstrekkingsbeperking moet worden toegepast
     * @brp.bedrijfsregel BRAU0018
     */
    private void verstrekkingsbeperkingToepassenIndienNodig(final OpvragenPersoonBerichtCommand bericht) {
        if (bericht.getAntwoord() != null && bericht.getAntwoord().getPersonen() != null
            && bericht.getContext().getAbonnement().getDoelBinding().isVerstrekkingsBeperkingHonoreren())
        {
            verstrekkingsbeperkingToepassen(bericht.getAntwoord().getPersonen());
        }
    }

    /**
     * Verwijder alle personen met een verstrekkingsbeperking.
     * @param personen de lijst waar de personen met verstrekkingsbeperking uitgehaald moeten worden
     * @brp.bedrijfsregel BRAU0018
     */
    private void verstrekkingsbeperkingToepassen(final Collection<Persoon> personen) {
        Collection<Persoon> personenMetBeperking = new ArrayList<Persoon>();
        for (Persoon persoon : personen) {
            if (persoon.verstrekkingsBeperking() == Boolean.TRUE) {
                personenMetBeperking.add(persoon);
            }
        }
        personen.removeAll(personenMetBeperking);
    }

}
