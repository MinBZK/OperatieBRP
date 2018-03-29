/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.afnemerindicatie;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.request.DatumService;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.VerzoekAsynchroonBerichtQueueService;
import nl.bzk.brp.service.algemeen.afnemerindicatie.GeneriekeOnderhoudAfnemerindicatieStappen;
import nl.bzk.brp.service.algemeen.afnemerindicatie.PlaatsAfnemerindicatieParams;
import org.springframework.stereotype.Service;

/**
 * Serviceimplementatie voor het plaatsen van een afnemerindicatie.
 */
@Service
final class PlaatsAfnemerIndicatieServiceImpl implements OnderhoudAfnemerindicatie.PlaatsAfnemerIndicatieService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private VerzoekAsynchroonBerichtQueueService zetBerichtOpQueueService;
    @Inject
    private OnderhoudAfnemerindicatiePersoonBerichtFactory onderhoudAfnemerindicatieBerichtFactory;
    @Inject
    private GeneriekeOnderhoudAfnemerindicatieStappen.PlaatsAfnemerindicatie plaatsAfnemerindicatieStap;
    @Inject
    private DatumService datumService;

    private PlaatsAfnemerIndicatieServiceImpl() {

    }

    @Override
    public void plaatsAfnemerindicatie(final OnderhoudResultaat verzoekResultaat)
            throws StapException {
        final AfnemerindicatieVerzoek verzoek = verzoekResultaat.getVerzoek();
        final Autorisatiebundel autorisatiebundel = verzoekResultaat.getAutorisatiebundel();
        final Integer datumEindeVolgenAttr = bepaalDatumEindeVolgen(verzoek);
        final Integer datumAanvangMaterielePeriode = bepaalDatumAanvangMaterielePeriode(verzoek);
        final Dienst dienst = AutAutUtil.zoekDienst(autorisatiebundel.getLeveringsautorisatie(), SoortDienst.PLAATSING_AFNEMERINDICATIE);

        final ZonedDateTime tijdstipRegistratie = BrpNu.get().getDatum();
        plaatsAfnemerindicatieStap.voerStapUit(new PlaatsAfnemerindicatieParams(autorisatiebundel
                .getToegangLeveringsautorisatie(), verzoekResultaat.getPersoonslijst(), datumAanvangMaterielePeriode,
                datumEindeVolgenAttr, tijdstipRegistratie, dienst.getId()));

        final VerwerkPersoonBericht bericht = onderhoudAfnemerindicatieBerichtFactory
                .maakBericht(verzoekResultaat.getPersoonslijst(), autorisatiebundel,
                        datumAanvangMaterielePeriode, tijdstipRegistratie,
                        SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE, verzoek.getStuurgegevens().getReferentieNummer());
        if (!bericht.isLeeg()) {
            zetBerichtOpQueueService.plaatsQueueberichtVoorVerzoek(bericht, autorisatiebundel, datumAanvangMaterielePeriode);
        } else {
            LOGGER.warn(String.format("Geen leeg bericht verzonden na plaatsen afnemerindicatie voor toegang [%d] en dienst [%d]",
                    autorisatiebundel.getToegangLeveringsautorisatie().getId(), dienst.getId()));
        }
        verzoekResultaat.setTijdstipRegistratie(tijdstipRegistratie);
        verzoekResultaat.setTijdstipVerzending(tijdstipRegistratie);
    }

    private Integer bepaalDatumEindeVolgen(final AfnemerindicatieVerzoek verzoek) throws StapMeldingException {
        final String datumEindeVolgenString = verzoek.getAfnemerindicatie().getDatumEindeVolgen();
        if (datumEindeVolgenString != null) {
            final LocalDate localDate = datumService.parseDate(datumEindeVolgenString);
            return Integer.valueOf(localDate.format(DateTimeFormatter.BASIC_ISO_DATE));
        }
        return null;
    }

    private Integer bepaalDatumAanvangMaterielePeriode(final AfnemerindicatieVerzoek verzoek) throws StapMeldingException {
        final String datumAanvangMaterielePeriode = verzoek.getAfnemerindicatie().getDatumAanvangMaterielePeriode();
        if (datumAanvangMaterielePeriode != null) {
            final LocalDate localDate = datumService.parseDate(datumAanvangMaterielePeriode);
            return Integer.valueOf(localDate.format(DateTimeFormatter.BASIC_ISO_DATE));
        }
        return null;
    }
}
