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
 * betreffende abonnement) gerechtigd is de opgevraagde gegevens in te zien. De gegevenselementen waartoe de
 * afnemer (op basis van het abonnement) niet gerechtigd is, worden in deze stap uit het bericht gefilterd, zodat
 * uitsluitend die gegevenselementen van de persoon aan de afnemer worden geleverd waarvoor een corresponderend
 * en geldig gegevenselement aanwezig is voor het abonnement waarvoor opgevraagd of geleverd wordt.
 */
public class GegevensElementenAutorisatieAchterafStap extends AbstractBerichtVerwerkingsStap {

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean voerVerwerkingsStapUitVoorBericht(final BrpBerichtCommand bericht) {
        if (bericht instanceof OpvragenPersoonBerichtCommand) {
            verstrekkingsbeperkingToepassen((OpvragenPersoonBerichtCommand) bericht);
        }
        return DOORGAAN_MET_VERWERKING;
    }

    /**
     * Verwijder alle personen met een verstrekkingsbeperking.
     * @param bericht het bericht waarop de verstrekkingsbeperking moet worden toegepast
     * @brp.bedrijfsregel BRAU0018
     */
    private void verstrekkingsbeperkingToepassen(final OpvragenPersoonBerichtCommand bericht) {
        Collection<Persoon> personenMetBeperking = new ArrayList<Persoon>();
        for (Persoon persoon : bericht.getAntwoord().getPersonen()) {
            if (persoon.verstrekkingsBeperking() == Boolean.TRUE) {
                personenMetBeperking.add(persoon);
            }
        }
        bericht.getAntwoord().getPersonen().removeAll(personenMetBeperking);
    }

}
