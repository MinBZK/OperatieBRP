/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;
import nl.bzk.brp.bevraging.business.dto.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.BerichtVerwerkingsFoutCode;
import nl.bzk.brp.bevraging.business.dto.BerichtVerwerkingsFoutZwaarte;
import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.lev.Abonnement;
import nl.bzk.brp.bevraging.domein.repository.AbonnementRepository;
import nl.bzk.brp.bevraging.domein.repository.PartijRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


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
    @Transactional(propagation = Propagation.MANDATORY)
    public final boolean voerVerwerkingsStapUitVoorBericht(final BrpBerichtCommand brpBerichtCommand) {
        boolean resultaat = true;

        resultaat &= verrijkPartij(brpBerichtCommand.getContext().getPartijId(), brpBerichtCommand);
        resultaat &=
            verrijkAbonnement(brpBerichtCommand.getContext().getAbonnementId(), brpBerichtCommand.getContext()
                    .getPartijId(), brpBerichtCommand);

        return resultaat;
    }

    /**
     * Haalt het abonnement op op basis van het id van het abonnement en de id van de partij en voegt deze toe aan
     * het bericht. Indien er geen uniek abonnement wordt gevonden, wordt er een fout toegevoegd aan het bericht.
     *
     * @param abonnementId id van het abonnement.
     * @param partijId id van de partij.
     * @param brpBerichtCommand het bericht command dat wordt uitgevoerd.
     * @return indicatie of de verrijking is geslaagd (abonnement is gevonden en toegevoegd) of niet.
     */
    private boolean verrijkAbonnement(final Long abonnementId, final Long partijId,
            final BrpBerichtCommand brpBerichtCommand)
    {
        boolean resultaat;
        List<Abonnement> abonnementen =
            abonnementRepository.findByIdAndDoelBindingGeautoriseerdeId(abonnementId, partijId);

        if (abonnementen.size() == 1) {
            brpBerichtCommand.getContext().setAbonnement(abonnementen.get(0));
            resultaat = DOORGAAN_MET_VERWERKING;
        } else {
            brpBerichtCommand.voegFoutToe(new BerichtVerwerkingsFout(
                    BerichtVerwerkingsFoutCode.PARTIJ_ABONNEMENT_COMBI_ONBEKEND_OF_NIET_UNIEK,
                    BerichtVerwerkingsFoutZwaarte.FOUT));
            resultaat = STOP_VERWERKING;
        }
        return resultaat;
    }

    /**
     * Haalt de partij op op basis van het id en voegt deze toe aan het bericht. Indien er geen partij wordt gevonden,
     * wordt er een fout toegevoegd aan het bericht.
     *
     * @param partijId id van de partij.
     * @param brpBerichtCommand het bericht command dat wordt uitgevoerd.
     * @return indicatie of de verrijking is geslaagd (partij is gevonden en toegevoegd) of niet.
     */
    private boolean verrijkPartij(final Long partijId, final BrpBerichtCommand brpBerichtCommand) {
        boolean resultaat;
        Partij partij = partijRepository.findOne(partijId);

        if (partij == null) {
            brpBerichtCommand.voegFoutToe(new BerichtVerwerkingsFout(BerichtVerwerkingsFoutCode.PARTIJ_ONBEKEND,
                    BerichtVerwerkingsFoutZwaarte.FOUT));
            resultaat = STOP_VERWERKING;
        } else {
            brpBerichtCommand.getContext().setPartij(partij);
            resultaat = DOORGAAN_MET_VERWERKING;
        }
        return resultaat;
    }

}
