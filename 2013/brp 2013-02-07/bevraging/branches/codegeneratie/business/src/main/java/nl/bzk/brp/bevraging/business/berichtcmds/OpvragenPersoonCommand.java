/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.berichtcmds;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.PersoonZoekCriteriaAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.PersoonZoekCriteria;
import nl.bzk.brp.bevraging.domein.repository.PersoonRepository;
import nl.bzk.brp.domein.kern.Persoon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Bericht command voor het ophalen van persoonsgegevens op basis van verschillende zoek criteria. (PUC500)
 *
 * @brp.bedrijfsregel FTPE0001
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Named("OpvragenPersoonCommand")
public class OpvragenPersoonCommand extends AbstractBerichtCommand<PersoonZoekCriteria, PersoonZoekCriteriaAntwoord> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpvragenPersoonCommand.class);

    @Inject
    private PersoonRepository   persoonRepository;

    /**
     * Standaard constructor waarbij het verzoek en de context van het bericht direct worden geinitialiseerd.
     *
     * @param verzoek het verzoek object van het bericht.
     * @param context de context van het bericht.
     */
    public OpvragenPersoonCommand(final PersoonZoekCriteria verzoek, final BerichtContext context) {
        super(verzoek, context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void voerUit(final PersoonZoekCriteriaAntwoord antwoord) {
        Collection<Persoon> personen = persoonRepository.findByBurgerservicenummer(getVerzoek().getBsn());
        for (Persoon persoon : personen) {
            /*
             * TODO: Eric Jan Malotaux(2012-02-23): om één of andere reden worden de persoonindicaties niet op tijd lazy
             * geladen, en is de JPA sessie gesloten op het moment dat de verstrekkingsbeperking wordt opgevraagd. Dat
             * is niet altijd zo geweest, en moet worden uitgezocht.
             */
            LOGGER.debug("persoon.verstrekkingsBeperking {}", persoon.getVerstrekkingsBeperking());
        }

        antwoord.getPersonen().clear();
        antwoord.getPersonen().addAll(personen);
    }

}
