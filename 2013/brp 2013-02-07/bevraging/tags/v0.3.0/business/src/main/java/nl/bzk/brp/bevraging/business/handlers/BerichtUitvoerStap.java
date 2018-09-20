/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.berichtcmds.BerichtCommand;
import nl.bzk.brp.bevraging.business.berichtcmds.OpvragenPersoonCommand;
import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;

import org.springframework.context.ApplicationContext;


/**
 * De stap in de uitvoering van een bericht waarin de werkelijke executie van het bericht wordt uitgevoerd. In deze
 * stap wordt de door het bericht opgegeven verzoek uitgevoerd en het antwoord/resultaat geformuleerd/aangevuld.
 * Hiervoor wordt dan ook een voor het {@link BerichtVerzoek} geldende {@link BerichtCommand} geinitialiseerd middels
 * de {@link BerichtUitvoerStap#getCommandFactory(BerichtVerzoek, BerichtContext)} methode, waarna de
 * {@link BerichtCommand#voerUit(BerichtAntwoord)} methode op het command wordt aangeroepen voor de uiteindelijk
 * uitvoer van het bericht.
 */
public class BerichtUitvoerStap extends AbstractBerichtVerwerkingsStap {

    @Inject
    private ApplicationContext applicationContext;

    /**
     * {@inheritDoc}
     */
    @Override
    public final <T extends BerichtAntwoord> boolean voerVerwerkingsStapUitVoorBericht(final BerichtVerzoek<T> verzoek,
            final BerichtContext context, final T antwoord)
    {
        BerichtCommand<? extends BerichtVerzoek<T>, T> command = getCommandFactory(verzoek, context);
        command.voerUit(antwoord);
        return DOORGAAN_MET_VERWERKING;
    }

    /**
     * Factory methode voor het creeeren van het juiste {@link BerichtCommand}, welke middels de Spring context wordt
     * opgehaald.
     *
     * @param verzoek het verzoek.
     * @param context de context.
     * @param <T> Type van het antwoord.
     * @return het voor het verzoek geldende command.
     */
    @SuppressWarnings("unchecked")
    private <T extends BerichtAntwoord> BerichtCommand<? extends BerichtVerzoek<T>, T> getCommandFactory(
            final BerichtVerzoek<T> verzoek, final BerichtContext context)
    {
        BerichtCommand<? extends BerichtVerzoek<T>, T> command = null;

        switch (verzoek.getSoortBericht()) {
            case OPVRAGENPERSOONVRAAG:
                command =
                    (BerichtCommand<? extends BerichtVerzoek<T>, T>) applicationContext.getBean(
                            OpvragenPersoonCommand.class.getSimpleName(), verzoek, context);
                break;
            default:

        }
        return command;
    }
}
