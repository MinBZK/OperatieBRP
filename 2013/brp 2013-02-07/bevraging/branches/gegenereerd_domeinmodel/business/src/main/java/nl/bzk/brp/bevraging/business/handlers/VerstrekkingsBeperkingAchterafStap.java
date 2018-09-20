/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import java.util.ArrayList;
import java.util.Collection;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.bevraging.domein.kern.Persoon;


/**
 * De stap in de uitvoering van een bericht waarin (achteraf) wordt gecontroleerd of de afnemer (op basis van
 * betreffende abonnement) gerechtigd is de persoon/personen in het resultaat te zien. Personen in het resultaat
 * waartoe een afnemer niet gerechtigd is, worden in deze stap dan ook uit het antwoord gefilterd. Dit daar
 * uitsluitend personen aan de afnemer mogen worden geleverd of door de afnemer mogen worden opgevraagd waarvoor
 * geen verstrekkingsbeperking geldt.
 *
 * Eventueel kan de doelbinding van het abonnement bepalen dat de verstrekkingsbeperkingsvlag op een persoon niet
 * hoeft te worden gehonoreerd. In dat geval worden er dus geen personen uit het antwoord gefilterd.
 *
 * @brp.bedrijfsregel BRAU0018, FTLE0001
 */
public class VerstrekkingsBeperkingAchterafStap extends AbstractBerichtVerwerkingsStap {

    /**
     * {@inheritDoc}
     *
     * @brp.bedrijfsregel BRAU0018, FTLE0001
     */
    @Override
    public final <T extends BerichtAntwoord> boolean voerVerwerkingsStapUitVoorBericht(final BerichtVerzoek<T> verzoek,
            final BerichtContext context, final T antwoord)
    {
        if (antwoord != null && antwoord.getPersonen() != null
            && context.getAbonnement().getDoelBinding().isVerstrekkingsBeperkingHonoreren())
        {
            verstrekkingsbeperkingToepassen(antwoord.getPersonen());
        }
        return DOORGAAN_MET_VERWERKING;
    }

    /**
     * Verwijder alle personen met een verstrekkingsbeperking.
     *
     * @param personen de lijst waar de personen met verstrekkingsbeperking uitgehaald moeten worden
     * @brp.bedrijfsregel BRAU0018, FTLE0001
     */
    private void verstrekkingsbeperkingToepassen(final Collection<Persoon> personen) {
        Collection<Persoon> personenMetBeperking = new ArrayList<Persoon>();
        for (Persoon persoon : personen) {
            if (Boolean.TRUE.equals(persoon.verstrekkingsBeperking())) {
                personenMetBeperking.add(persoon);
            }
        }
        personen.removeAll(personenMetBeperking);
    }

}
