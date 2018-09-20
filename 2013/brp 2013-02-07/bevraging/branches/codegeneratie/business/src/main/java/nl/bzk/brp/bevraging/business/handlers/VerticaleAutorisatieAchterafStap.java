/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.proxies.PersoonProxy;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.domein.kern.DatabaseObject;
import nl.bzk.brp.domein.kern.Persoon;
import nl.bzk.brp.domein.lev.Abonnement;
import nl.bzk.brp.domein.lev.AbonnementGegevenselement;


/**
 * De stap in de uitvoering van een bericht waarin (achteraf) wordt gecontroleerd of de afnemer (op basis van
 * betreffende abonnement) gerechtigd is de opgevraagde gegevens in te zien. De gegevenselementen waartoe de
 * afnemer (op basis van het abonnement) niet gerechtigd is, worden in deze stap uit het bericht gefilterd, zodat
 * uitsluitend die gegevenselementen van de persoon aan de afnemer worden geleverd waarvoor een corresponderend
 * en geldig gegevenselement aanwezig is voor het abonnement waarvoor opgevraagd of geleverd wordt.
 *
 * @brp.bedrijfsregel BRAU0045, FTAU0034
 */
public class VerticaleAutorisatieAchterafStap extends AbstractBerichtVerwerkingsStap {

    /**
     * {@inheritDoc}
     *
     * @brp.bedrijfsregel BRAU0018
     */
    @Override
    public final <T extends BerichtAntwoord> boolean voerVerwerkingsStapUitVoorBericht(final BerichtVerzoek<T> verzoek,
            final BerichtContext context, final T antwoord)
    {
        Collection<Persoon> persoonProxies = new HashSet<Persoon>();
        Set<String> toegestaneVelden = getToegestaneVelden(context.getAbonnement());
        for (Persoon persoon : antwoord.getPersonen()) {
            persoonProxies.add(new PersoonProxy(persoon, toegestaneVelden));
        }
        antwoord.getPersonen().clear();
        antwoord.getPersonen().addAll(persoonProxies);
        return DOORGAAN_MET_VERWERKING;
    }

    /**
     * Haalt de gegevenselementen op waartoe een abonnement recht geeft en stelt op basis daarvan een set samen met
     * daarin de in Java gebruikte veld namen van deze gegevenselementen.
     *
     * @param abonnement een abonnement met nul of meer gegevenselementen
     * @return de java namen van de velden die zijn toegestaan voor dit abonnement
     */
    private Set<String> getToegestaneVelden(final Abonnement abonnement) {
        Set<String> resultaat = new HashSet<String>();
        if (abonnement.getAbonnementGegevenselementen() != null) {
            for (AbonnementGegevenselement abonnementGegevensElement : abonnement.getAbonnementGegevenselementen()) {
                DatabaseObject element = abonnementGegevensElement.getGegevenselement();
                if (element.getOuder() != null) {
                    resultaat.add((element.getOuder().getJavaIdentifier() + "." + element.getJavaIdentifier()));
                }
            }
        }
        return resultaat;
    }

}
