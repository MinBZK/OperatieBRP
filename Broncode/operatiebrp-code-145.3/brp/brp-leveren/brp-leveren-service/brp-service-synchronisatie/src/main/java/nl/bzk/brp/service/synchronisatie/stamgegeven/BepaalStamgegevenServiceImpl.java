/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.stamgegeven;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.StamtabelGegevens;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieParams;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieValidatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.algemeen.stamgegevens.StamTabelService;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Component
final class BepaalStamgegevenServiceImpl implements SynchroniseerStamgegeven.BepaalStamgegevenService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private PartijService partijService;
    @Inject
    private LeveringsautorisatieValidatieService leveringsautorisatieService;
    @Inject
    private StamTabelService stamTabelService;

    private BepaalStamgegevenServiceImpl() {

    }

    /**
     * Geeft resultaat van stamgegeven bepaling.
     * NB: gebruikt rw transactiemanager, een r transactiemanager volstaat natuurlijk ook.
     * @param verzoek het verzoek
     * @return het resultaat
     */
    @Transactional(transactionManager = "masterTransactionManager")
    @Override
    public BepaalStamgegevenResultaat maakResultaat(final SynchronisatieVerzoek verzoek) {
        final BepaalStamgegevenResultaat resultaat = new BepaalStamgegevenResultaat(verzoek);
        try {
            final String zendendePartijCode = verzoek.getStuurgegevens().getZendendePartijCode();
            final int leveringautorisatieId = Integer.parseInt(verzoek.getParameters().getLeveringsAutorisatieId());
            final AutorisatieParams autorisatieParams = AutorisatieParams.maakBuilder()
                    .metZendendePartijCode(zendendePartijCode)
                    .metLeveringautorisatieId(leveringautorisatieId)
                    .metOIN(verzoek.getOin())
                    .metVerzoekViaKoppelvlak(verzoek.isBrpKoppelvlakVerzoek())
                    .metSoortDienst(verzoek.getSoortDienst()).build();
            resultaat.setBrpPartij(partijService.geefBrpPartij());
            final Autorisatiebundel autorisatiebundel = this.leveringsautorisatieService.controleerAutorisatie(autorisatieParams);
            resultaat.setAutorisatiebundel(autorisatiebundel);
            //haal stamgegevens op
            final StamtabelGegevens stamgegevens = stamTabelService.geefStamgegevens(verzoek.getParameters().getStamgegeven());
            if (stamgegevens == null) {
                throw new StapMeldingException(new Melding(Regel.R1331));
            }
            resultaat.setStamgegevens(stamgegevens);
        } catch (final StapMeldingException e) {
            LOGGER.warn("Functionele fout bij synchroniseren stamgegeven: " + e);
            resultaat.getMeldingList().addAll(e.getMeldingen());
        } catch (final StapException e) {
            LOGGER.error("Algemene fout bij synchroniseren stamgegeven: ", e);
            resultaat.getMeldingList().add(e.maakFoutMelding());
        } catch (final AutorisatieException e) {
            LOGGER.info("Autorisatiefout bij synchroniseren stamgegeven: " + e);
            resultaat.getMeldingList().add(new Melding(Regel.R2343));
        }
        return resultaat;
    }
}
