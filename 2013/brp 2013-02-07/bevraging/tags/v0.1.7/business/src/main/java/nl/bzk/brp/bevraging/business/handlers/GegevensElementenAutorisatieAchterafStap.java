/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import java.util.Collection;
import java.util.HashSet;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.decorators.PersoonProxy;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.bevraging.domein.kern.Persoon;


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
    public final <T extends BerichtAntwoord> boolean voerVerwerkingsStapUitVoorBericht(final BerichtVerzoek<T> verzoek,
            final BerichtContext context, final T antwoord)
    {
        Collection<Persoon> persoonProxies = new HashSet<Persoon>();
        for (Persoon persoon : antwoord.getPersonen()) {
            persoonProxies.add(new PersoonProxy(persoon.getPersoon()));
        }
        antwoord.getPersonen().clear();
        antwoord.getPersonen().addAll(persoonProxies);
        return DOORGAAN_MET_VERWERKING;
    }

}
