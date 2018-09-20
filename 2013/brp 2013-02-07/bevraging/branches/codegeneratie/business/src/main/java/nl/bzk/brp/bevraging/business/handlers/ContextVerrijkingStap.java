/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutCode;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutZwaarte;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.bevraging.domein.repository.AbonnementRepository;
import nl.bzk.brp.bevraging.domein.repository.PartijRepository;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.lev.Abonnement;


/**
 * Een van de eerste stappen in de uitvoering van een bericht waarin de context verder wordt verrijkt op basis van de
 * reeds in de context aanwezige data en het bericht zelf. Hierbij gaat het met name op het verrijken van de context
 * met data uit de database die (minimaal) benodigd is voor de verder verwerking van het bericht, zoals bijvoorbeeld
 * de partij en de daar bij behorende autorisatie specifieke gegevens.
 */
public class ContextVerrijkingStap extends AbstractBerichtVerwerkingsStap {

    @Inject
    private PartijRepository     partijRepository;
    @Inject
    private AbonnementRepository abonnementRepository;

    /**
     * {@inheritDoc} <br/>
     * Haalt specifiek de partij en het abonnement op op basis van de partij id en het abonnement id in de context en
     * voegt deze beide objecten dan toe aan de context.
     */
    @Override
    public final <T extends BerichtAntwoord> boolean voerVerwerkingsStapUitVoorBericht(final BerichtVerzoek<T> verzoek,
            final BerichtContext context, final T antwoord)
    {
        boolean resultaat = verrijkPartij(context, antwoord);
        resultaat &= verrijkAbonnement(context, antwoord);

        return resultaat;
    }

    /**
     * Haalt het abonnement op op basis van het id van het abonnement en voegt deze toe aan
     * het bericht. Indien er geen abonnement wordt gevonden, wordt er een fout toegevoegd aan het bericht.
     *
     * @param context de context waarbinnen het bericht wordt uitgevoerd en die de partij en abonnement Id bevat.
     * @param antwoord het antwoord bericht.
     * @return indicatie of de verrijking is geslaagd (abonnement is gevonden en toegevoegd) of niet.
     * @brp.bedrijfsregel BRPE0001
     */
    private boolean verrijkAbonnement(final BerichtContext context, final BerichtAntwoord antwoord) {
        boolean resultaat;

        resultaat = STOP_VERWERKING;
        Abonnement abonnement = abonnementRepository.findOne(context.getAbonnementId());
        if (abonnement == null) {
            antwoord.voegFoutToe(new BerichtVerwerkingsFout(
                    BerichtVerwerkingsFoutCode.BRPE0001_04_ABONNEMENT_BESTAAT_NIET, BerichtVerwerkingsFoutZwaarte.FOUT));
        } else {
            if (!"A".equals(abonnement.getAbonnementStatusHis())) {
                antwoord.voegFoutToe(new BerichtVerwerkingsFout(
                        BerichtVerwerkingsFoutCode.BRPE0001_03_ABONNEMENT_ONGELDIG, BerichtVerwerkingsFoutZwaarte.FOUT));
            } else if (!abonnement.getDoelbinding().getGeautoriseerde().getID().equals(context.getPartijId())) {
                antwoord.voegFoutToe(new BerichtVerwerkingsFout(
                        BerichtVerwerkingsFoutCode.BRPE0001_02_ABONNEMENT_BEHOORT_NIET_BIJ_AFNEMER,
                        BerichtVerwerkingsFoutZwaarte.FOUT));
            } else {
                context.setAbonnement(abonnement);
                resultaat = DOORGAAN_MET_VERWERKING;
            }
        }
        return resultaat;
    }

    /**
     * Haalt de partij op op basis van het id en voegt deze toe aan het bericht. Indien er geen partij wordt gevonden,
     * wordt er een fout toegevoegd aan het bericht.
     *
     * @param context de context waarbinnen het bericht wordt uitgevoerd en die de partij Id bevat.
     * @param antwoord het antwoord bericht.
     * @return indicatie of de verrijking is geslaagd (partij is gevonden en toegevoegd) of niet.
     */
    private boolean verrijkPartij(final BerichtContext context, final BerichtAntwoord antwoord) {
        boolean resultaat;
        Partij partij = partijRepository.findOne(context.getPartijId());

        if (partij == null) {
            antwoord.voegFoutToe(new BerichtVerwerkingsFout(BerichtVerwerkingsFoutCode.PARTIJ_ONBEKEND,
                    BerichtVerwerkingsFoutZwaarte.FOUT));
            resultaat = STOP_VERWERKING;
        } else {
            context.setPartij(partij);
            resultaat = DOORGAAN_MET_VERWERKING;
        }
        return resultaat;
    }

}
