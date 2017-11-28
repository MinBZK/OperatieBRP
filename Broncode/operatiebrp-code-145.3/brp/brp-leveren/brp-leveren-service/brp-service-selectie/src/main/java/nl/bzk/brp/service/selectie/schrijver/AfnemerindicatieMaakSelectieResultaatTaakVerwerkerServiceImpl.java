/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.schrijver;

import java.util.UUID;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.SelectieKenmerken;
import nl.bzk.brp.domain.berichtmodel.SelectieResultaatBericht;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;
import nl.bzk.brp.domain.internbericht.selectie.SelectieTaakResultaat;
import nl.bzk.brp.domain.internbericht.selectie.TypeResultaat;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.selectie.publicatie.SelectieTaakResultaatPublicatieService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Implementatie van {@link MaakSelectieResultaatTaakVerwerkerService} specifiek voor afnemerindicaties.
 */
@Service
@Qualifier("afnemerIndicatieMaakSelectieResultaatTaakVerwerkerServiceImpl")
final class AfnemerindicatieMaakSelectieResultaatTaakVerwerkerServiceImpl implements MaakSelectieResultaatTaakVerwerkerService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private PartijService partijService;
    @Inject
    private LeveringsautorisatieService leveringsautorisatieService;
    @Inject
    private SelectieResultaatWriterFactory selectieResultaatWriterFactory;
    @Inject
    private SelectieTaakResultaatPublicatieService selectieTaakResultaatPublicatieService;

    private AfnemerindicatieMaakSelectieResultaatTaakVerwerkerServiceImpl() {
    }

    @Override
    public void verwerk(final MaakSelectieResultaatTaak maakSelectieResultaatTaak) {
        final ToegangLeveringsAutorisatie
                toegangLeveringsAutorisatie =
                leveringsautorisatieService.geefToegangLeveringsAutorisatie(maakSelectieResultaatTaak.getToegangLeveringsAutorisatieId());
        final Partij ontvangendePartij = toegangLeveringsAutorisatie.getGeautoriseerde().getPartij();
        final Dienst dienst = AutAutUtil.zoekDienst(toegangLeveringsAutorisatie.getLeveringsautorisatie(), maakSelectieResultaatTaak.getDienstId());
        try {
            final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                    .metStuurgegevens()
                    .metReferentienummer(UUID.randomUUID().toString())
                    .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                    .metZendendePartij(partijService.geefBrpPartij())
                    .metZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM)
                    .metOntvangendePartij(ontvangendePartij)
                    .eindeStuurgegevens().build();

            final int volgnummer = 1;
            final SelectieKenmerken.Builder builder = SelectieKenmerken.builder()
                    .metDienst(dienst)
                    .metPeilmomentFormeelResultaat(maakSelectieResultaatTaak.getPeilmomentFormeelResultaat())
                    .metPeilmomentMaterieelResultaat(maakSelectieResultaatTaak.getPeilmomentMaterieelResultaat())
                    .metLeveringsautorisatie(toegangLeveringsAutorisatie.getLeveringsautorisatie())
                    .metSelectietaakId(maakSelectieResultaatTaak.getSelectietaakId())
                    .metDatumUitvoer(maakSelectieResultaatTaak.getDatumUitvoer())
                    .metSoortSelectie(maakSelectieResultaatTaak.getSoortSelectie())
                    .metSoortSelectieresultaatVolgnummer(volgnummer);

            final SelectieResultaatSchrijfInfo info = new SelectieResultaatSchrijfInfo();
            info.setBerichtId(volgnummer);
            info.setSelectieRunId(maakSelectieResultaatTaak.getSelectieRunId());
            info.setDienstId(maakSelectieResultaatTaak.getDienstId());
            info.setToegangLeveringsAutorisatieId(maakSelectieResultaatTaak.getToegangLeveringsAutorisatieId());
            final SelectieKenmerken selectieKenmerken = builder.metSoortSelectieresultaatSet("Resultaatset totalen").build();
            final SelectieResultaatBericht totalenBericht = new SelectieResultaatBericht(basisBerichtGegevens, selectieKenmerken);
            final SelectieResultaatWriterFactory.TotalenBestandWriter totalenWriter;
            if (toegangLeveringsAutorisatie.getLeveringsautorisatie().getStelsel() == Stelsel.BRP) {
                totalenWriter =
                        selectieResultaatWriterFactory.totalenWriterBrp(info, totalenBericht);
            } else {
                totalenWriter =
                        selectieResultaatWriterFactory.totalenWriterGba(info, totalenBericht);
            }
            totalenWriter.schrijfTotalen(maakSelectieResultaatTaak.getAantalPersonen(), 1);
            totalenWriter.eindeBericht();
            publiceerResultaat();
        } catch (SelectieResultaatVerwerkException e) {
            LOGGER.error("Fout bij het schrijven van totalen voor toegang leveringsautorisatie {}, dienst {} en selectietaak {}.",
                    toegangLeveringsAutorisatie.getId(), dienst.getId(), maakSelectieResultaatTaak.getSelectietaakId(), e);
            selectieTaakResultaatPublicatieService.publiceerFout();
        }
    }

    private void publiceerResultaat() {
        SelectieTaakResultaat selectieResultaat = new SelectieTaakResultaat();
        selectieResultaat.setType(TypeResultaat.SELECTIE_RESULTAAT_SCHRIJF);
        selectieTaakResultaatPublicatieService.publiceerSelectieTaakResultaat(selectieResultaat);
    }
}
