/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.persoon;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.VerzoekAsynchroonBerichtQueueService;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.algemeen.persoonselectie.SelecteerPersoonService;
import nl.bzk.brp.service.algemeen.util.BsnValidator;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;
import org.springframework.stereotype.Component;

/**
 */
@Component
final class MaakSynchronisatieBerichtServiceImpl implements SynchroniseerPersoon.MaakSynchronisatieBerichtService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private PartijService partijService;
    @Inject
    private SynchroniseerPersoonAutorisatieService synchroniseerPersoonAutorisatieService;
    @Inject
    private SelecteerPersoonService selecteerPersoonService;
    @Inject
    private SynchronisatieBerichtFactory synchronisatieBerichtFactory;
    @Inject
    private VerzoekAsynchroonBerichtQueueService zetPersoonberichtOpQueue;

    private MaakSynchronisatieBerichtServiceImpl() {

    }

    @Override
    public MaakSynchronisatieBerichtResultaat verwerkVerzoek(final SynchronisatieVerzoek verzoek) {
        final MaakSynchronisatieBerichtResultaat resultaat = new MaakSynchronisatieBerichtResultaat(verzoek);
        try {
            getMaakSynchronisatieBerichtServiceResultaat(resultaat);
        } catch (final StapMeldingException e) {
            LOGGER.warn("Functionele fout bij synchroniseren persoon: " + e);
            resultaat.getMeldingList().addAll(e.getMeldingen());
        } catch (final StapException e) {
            LOGGER.error("Algemene fout bij synchroniseren persoon: ", e);
            resultaat.getMeldingList().add(e.maakFoutMelding());
        } catch (AutorisatieException e) {
            LOGGER.info("Autorisatiefout bij synchroniseren persoon: " + e);
            resultaat.getMeldingList().add(new Melding(Regel.R2343));
        }
        return resultaat;
    }


    private void getMaakSynchronisatieBerichtServiceResultaat(final MaakSynchronisatieBerichtResultaat resultaat)
            throws StapException, AutorisatieException {

        final SynchronisatieVerzoek verzoek = resultaat.getVerzoek();

        final boolean valideBsn = BsnValidator.isGeldigeBsn(verzoek.getZoekCriteriaPersoon().getBsn());
        if (!valideBsn) {
            throw new StapMeldingException(Regel.R1587);
        }

        //controleer autorisatie (generiek en dienst-specifiek)
        final Autorisatiebundel autorisatiebundel = synchroniseerPersoonAutorisatieService.controleerAutorisatie(verzoek);
        resultaat.setAutorisatiebundel(autorisatiebundel);

        //haal zendende partij op
        final String zendendePartijCode = verzoek.getStuurgegevens().getZendendePartijCode();
        final Partij zendendePartij = this.partijService.vindPartijOpCode(zendendePartijCode);
        resultaat.setZendendePartij(zendendePartij);

        //haal persoon op
        final Persoonslijst persoonslijst = selecteerPersoonService.selecteerPersoonMetBsn(
                verzoek.getZoekCriteriaPersoon().getBsn(), autorisatiebundel);
        resultaat.setPersoonslijst(persoonslijst);

        //maak asynchroon bericht
        final VerwerkPersoonBericht leverBericht = synchronisatieBerichtFactory.apply(persoonslijst, autorisatiebundel);
        if (leverBericht.isLeeg()) {
            LOGGER.warn(String.format("Geen leeg bericht verzonden na synchroniseer persoon voor toegang [%d] en dienst [%d]",
                    autorisatiebundel.getToegangLeveringsautorisatie().getId(), autorisatiebundel.getDienst().getId()));
        } else {
            //plaats asynchroon bericht op queue
            zetPersoonberichtOpQueue.plaatsQueueberichtVoorVerzoek(leverBericht, autorisatiebundel, null);
        }
    }
}
