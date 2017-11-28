/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.stuf;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieParams;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieValidatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import org.springframework.stereotype.Service;

/**
 * StufBerichtServiceImpl.
 */
@Service
final class StufBerichtServiceImpl implements StufBerichtService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private PartijService partijService;
    @Inject
    private LeveringsautorisatieValidatieService leveringsautorisatieValidatieService;
    @Inject
    private StufBerichtBerichtFactory stufBerichtBerichtFactory;
    @Inject
    private StufBerichtInhoudControleService stufBerichtInhoudControleService;
    @Inject
    private StufVertaalService stufVertaalService;

    private StufBerichtServiceImpl() {
    }

    @Override
    public StufBerichtResultaat verwerkVerzoek(final StufBerichtVerzoek verzoek) {
        final StufBerichtResultaat resultaat = new StufBerichtResultaat(verzoek);
        try {
            resultaat.setBrpPartij(partijService.geefBrpPartij());
            final AutorisatieParams autorisatieParams = maakAutorisatieParams(resultaat);
            resultaat.setAutorisatiebundel(leveringsautorisatieValidatieService.controleerAutorisatie(autorisatieParams));
            stufBerichtInhoudControleService.controleerInhoud(resultaat.getVerzoek());
            final StufTransformatieResultaat stufTransformatieResultaat = stufVertaalService.vertaal(resultaat.getVerzoek());
            resultaat.getMeldingen().addAll(stufTransformatieResultaat.getMeldingen());
            resultaat.setStufVertalingen(stufTransformatieResultaat.getVertalingen());
        } catch (final AutorisatieException e) {
            LOGGER.info("Autorisatiefout opgetreden bij verwerken stuf bericht: " + e);
            resultaat.getMeldingen().add(new Melding(Regel.R2343));
        } catch (final StapMeldingException e) {
            LOGGER.warn("Functionele fout bij verwerken stuf bericht: " + e);
            resultaat.getMeldingen().addAll(e.getMeldingen());
        }
        return resultaat;
    }

    private AutorisatieParams maakAutorisatieParams(StufBerichtResultaat resultaat) {
        final StufBerichtVerzoek verzoek = resultaat.getVerzoek();
        final String zendendePartijCode = verzoek.getStuurgegevens().getZendendePartijCode();
        final int leveringautorisatieId = Integer.parseInt(verzoek.getParameters().getLeveringsAutorisatieId());
        return AutorisatieParams.maakBuilder()
                .metZendendePartijCode(zendendePartijCode)
                .metLeveringautorisatieId(leveringautorisatieId)
                .metOIN(verzoek.getOin())
                .metSoortDienst(verzoek.getSoortDienst())
                .build();
    }
}
