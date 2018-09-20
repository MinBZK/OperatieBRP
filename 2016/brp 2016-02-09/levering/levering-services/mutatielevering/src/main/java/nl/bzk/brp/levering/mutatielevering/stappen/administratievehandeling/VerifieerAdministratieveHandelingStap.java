/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.levering.algemeen.service.AdministratieveHandelingenOverslaanService;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.dataaccess.repository.lezenenschrijven.AdministratieveHandelingVerwerkerRepository;
import nl.bzk.brp.levering.mutatielevering.stappen.AbstractAdministratieveHandelingVerwerkingStap;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.validatie.Melding;

import org.perf4j.aop.Profiled;


/**
 * In deze stap wordt geverifieerd of de bijgehouden personen van de Administratieve Handeling momenteel niet verwerkt
 * worden. Als de bijgehouden niet verwerkt worden, dan gaat de verwerking verder, worden bijgehouden personen momenteel
 * wel verwerkt, dan stopt de verwerking van de Administratieve Handeling binnen deze stappen context.
 */
public class VerifieerAdministratieveHandelingStap extends AbstractAdministratieveHandelingVerwerkingStap {

    private static final Logger                         LOGGER = LoggerFactory.getLogger();

    @Inject
    private AdministratieveHandelingVerwerkerRepository administratieveHandelingVerwerkerRepository;

    @Inject
    private AdministratieveHandelingenOverslaanService  administratieveHandelingenOverslaanService;

    @Override
    @Profiled(tag = "VerifieerAdministratieveHandelingStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final AdministratieveHandelingMutatie onderwerp,
            final AdministratieveHandelingVerwerkingContext context,
            final AdministratieveHandelingVerwerkingResultaat resultaat)
    {
        if (bijgehoudenPersonenHebbenGeenOnverwerkteAdministratieveHandelingen(
                context.getHuidigeAdministratieveHandeling(), context.getBijgehoudenPersoonIds()))
        {
            return DOORGAAN;
        } else {
            LOGGER.info("Bijgehouden personen worden nog verwerkt in voorgaande handeling(en).");
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001,
                    "Bijgehouden personen van de administratieve handeling worden reeds verwerkt."));
            return STOPPEN;
        }
    }

    /**
     * Bepaalt of bijgehouden personen onverwerkte administratieve handelingen hebben.
     *
     *
     * @param administratieveHandelingModel the administratieve handeling model
     * @param bijgehoudenPersonenIds bijgehouden personen ids
     * @return true als bijgehouden personen geen onverwerkte administratieve handelingen hebben, wanneer er nog
     *         administratieve handelingen voor deze persoon staan die eerder zijn geregistreerd en nog niet verwerkt,
     *         dan
     *         wordt er false teruggegeven.
     */
    private boolean bijgehoudenPersonenHebbenGeenOnverwerkteAdministratieveHandelingen(
            final AdministratieveHandelingModel administratieveHandelingModel,
            final List<Integer> bijgehoudenPersonenIds)
    {
        final List<SoortAdministratieveHandeling> soortAdministratieveHandelingenDieOvergeslagenMoetenWorden =
            administratieveHandelingenOverslaanService
                    .geefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWorden();

        return administratieveHandelingVerwerkerRepository.magAdministratieveHandelingVerwerktWorden(
                administratieveHandelingModel, bijgehoudenPersonenIds,
                soortAdministratieveHandelingenDieOvergeslagenMoetenWorden);
    }

}
