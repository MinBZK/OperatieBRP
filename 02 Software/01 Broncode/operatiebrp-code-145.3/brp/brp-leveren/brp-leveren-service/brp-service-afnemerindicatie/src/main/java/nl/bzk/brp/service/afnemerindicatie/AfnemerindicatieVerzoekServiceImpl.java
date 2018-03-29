/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.afnemerindicatie;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieParams;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieValidatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.algemeen.persoonselectie.SelecteerPersoonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 */
@Component
final class AfnemerindicatieVerzoekServiceImpl implements OnderhoudAfnemerindicatie.AfnemerindicatieVerzoekService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private SelecteerPersoonService selecteerPersoonService;
    @Inject
    private LeveringsautorisatieValidatieService leveringsautorisatieService;
    @Inject
    private OnderhoudAfnemerindicatie.PlaatsAfnemerIndicatieService plaatsService;
    @Inject
    private OnderhoudAfnemerindicatie.VerwijderAfnemerIndicatieService verwijderService;
    @Inject
    private PartijService partijService;

    private AfnemerindicatieVerzoekServiceImpl() {
    }

    @Override
    public OnderhoudResultaat verwerkVerzoek(final AfnemerindicatieVerzoek verzoek) {
        final OnderhoudResultaat resultaat = new OnderhoudResultaat(verzoek);
        try {
            resultaat.setBrpPartij(partijService.geefBrpPartij());
            //controleer autorisatie en verrijk resultaat met autorisatiegegevens
            vulAanMetAutorisatieEnControleer(resultaat);
            vulAanMetPersoonsgegevens(resultaat);
            valideerOnderhoudEigenPartij(verzoek);
            switch (verzoek.getSoortDienst()) {
                case PLAATSING_AFNEMERINDICATIE:
                    plaatsService.plaatsAfnemerindicatie(resultaat);
                    break;
                case VERWIJDERING_AFNEMERINDICATIE:
                    verwijderService.verwijderAfnemerindicatie(resultaat);
                    break;
                default:
                    throw new IllegalStateException("Onbekende dienst: " + verzoek.getSoortDienst());
            }
        } catch (final StapMeldingException e) {
            LOGGER.warn("Functionele fout bij onderhouden afnemerindicatie: " + e);
            resultaat.getMeldingList().addAll(e.getMeldingen());
        } catch (final StapException e) {
            LOGGER.error("Algemene fout bij onderhouden afnemerindicatie", e);
            resultaat.getMeldingList().add(e.maakFoutMelding());
        } catch (AutorisatieException e) {
            LOGGER.info("Autorisatiefout opgetreden bij onderhouden afnemerindicatie: " + e);
            resultaat.getMeldingList().add(new Melding(Regel.R2343));
        }
        return resultaat;
    }


    private void vulAanMetAutorisatieEnControleer(final OnderhoudResultaat resultaat) throws AutorisatieException {
        final AfnemerindicatieVerzoek verzoek = resultaat.getVerzoek();
        final String zendendePartijCode = verzoek.getStuurgegevens().getZendendePartijCode();
        final int leveringautorisatieId = Integer.parseInt(verzoek.getParameters().getLeveringsAutorisatieId());
        final AutorisatieParams autorisatieParams = AutorisatieParams.maakBuilder()
                .metZendendePartijCode(zendendePartijCode)
                .metLeveringautorisatieId(leveringautorisatieId)
                .metOIN(verzoek.getOin())
                .metVerzoekViaKoppelvlak(verzoek.isBrpKoppelvlakVerzoek())
                .metSoortDienst(verzoek.getSoortDienst()).build();

        resultaat.setAutorisatiebundel(this.leveringsautorisatieService.controleerAutorisatie(autorisatieParams));
    }

    private void vulAanMetPersoonsgegevens(final OnderhoudResultaat resultaat) throws StapException {
        final Persoonslijst persoonslijst = selecteerPersoonService.selecteerPersoonMetBsn(resultaat.getVerzoek()
                .getAfnemerindicatie().getBsn(), resultaat.getAutorisatiebundel());
        resultaat.setPersoonslijst(persoonslijst);
    }


    /**
     * Specifieke regel validatie voor het BRP bericht. Dit past niet niet in de 'generieke' valideer regels stap welke ook voor de GBA routes gebruikt
     * wordt. <br> LET OP! dit is een beetje suffe controle omdat het volgens koppelvlak meerdere partijen meegegeven kunnen worden terwijl ze eigenlijk
     * allemaal hetzelfde moeten zijn....
     */
    @Bedrijfsregel(value = Regel.R2061)
    private void valideerOnderhoudEigenPartij(final AfnemerindicatieVerzoek verzoek) throws StapException {
        final String zendendePartijCode = verzoek.getStuurgegevens().getZendendePartijCode();
        final String partijWaarvoorIndicatieGeplaatstWordt = verzoek.getAfnemerindicatie().getPartijCode();
        final String partijWaarvoorIndicatieGeplaatstWordt2 = StringUtils
                .defaultIfEmpty(verzoek.getDummyAfnemerCode(), zendendePartijCode);
        if (!StringUtils.equals(zendendePartijCode, partijWaarvoorIndicatieGeplaatstWordt)
                || !StringUtils.equals(zendendePartijCode, partijWaarvoorIndicatieGeplaatstWordt2)) {
            throw new StapMeldingException(Regel.R2061);
        }
    }

}
