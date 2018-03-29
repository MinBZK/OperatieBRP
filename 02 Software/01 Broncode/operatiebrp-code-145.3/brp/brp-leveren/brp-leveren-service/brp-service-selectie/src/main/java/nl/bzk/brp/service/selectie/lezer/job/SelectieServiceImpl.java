/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer.job;

import com.google.common.collect.Lists;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectierun;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SelectietaakHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.LeverwijzeSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.dalapi.SelectieRepository;
import nl.bzk.brp.service.selectie.algemeen.Selectie;
import nl.bzk.brp.service.selectie.algemeen.SelectietaakAutorisatie;
import nl.bzk.brp.service.selectie.algemeen.SelectietaakStatusHistorieUtil;
import nl.bzk.brp.service.selectie.lezer.status.SelectieJobRunStatusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SelectieServiceImpl.
 */
@Service
@Bedrijfsregel(Regel.R2666)
@Bedrijfsregel(Regel.R2667)
@Bedrijfsregel(Regel.R2673)
@Bedrijfsregel(Regel.R2678)
@Bedrijfsregel(Regel.R2692)
final class SelectieServiceImpl implements SelectieService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private SelectieAutorisatieService selectieAutorisatieService;
    @Inject
    private SelectieJobRunStatusService selectieJobRunStatusService;

    @Inject
    private SelectieRepository selectieRepository;

    private SelectieServiceImpl() {
    }

    @Override
    @Transactional(transactionManager = "masterTransactionManager")
    public Selectie bepaalSelectie() {
        LOGGER.info("Start bepaal Selectie");
        final List<SelectietaakAutorisatie> selectietaakAutorisaties = bepaalSelectietaakAutorisaties();
        final Timestamp nu = DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(DatumUtil.nuAlsZonedDateTime());
        LOGGER.info("Aantal selectie autorisaties: {}", selectietaakAutorisaties.size());
        final Selectierun selectierun = new Selectierun(nu);
        final Selectie selectie = new Selectie(selectierun, selectietaakAutorisaties);
        Set<Selectietaak> taken = selectietaakAutorisaties.stream()
                .map(SelectietaakAutorisatie::getSelectietaak).collect(Collectors.toSet());
        for (Selectietaak taak : taken) {
            //koppel selectietaken aan selectierun
            selectierun.getSelectieTaken().add(taak);
            //zoek niet vervallen his en verval
            final SelectietaakHistorie hisTaakVerval = vervalSelectietaak(nu, taak.getSelectietaakHistorieSet());
            SelectietaakHistorie hisTaakNieuw = new SelectietaakHistorie(hisTaakVerval);
            hisTaakNieuw.setDatumTijdRegistratie(nu);
            taak.addSelectietaakHistorieSet(hisTaakNieuw);
            //historie selectietaakstatus
            final SelectietaakStatus statusNieuw = SelectietaakStatus.IN_UITVOERING;
            SelectietaakStatusHistorieUtil.updateTaakStatus(taak, nu, statusNieuw, null);
            //update ook de a-laag selectietaak
            taak.setUitgevoerdIn(selectierun);
            taak.setStatus((short) statusNieuw.getId());
            //R2678 bijwerken peilmomenten indien historiefilter is toegepast en peilmoment is leeg
            if (taak.getDienst().getHistorievormSelectie() != null || taak.getPeilmomentMaterieelResultaat() != null ||
                    taak.getPeilmomentFormeelResultaat() != null) {
                if (taak.getPeilmomentMaterieelResultaat() == null) {
                    taak.setPeilmomentMaterieelResultaat(BrpNu.get().alsIntegerDatumNederland());
                }
                if (taak.getPeilmomentFormeelResultaat() == null) {
                    taak.setPeilmomentFormeelResultaat(DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(BrpNu.get().getDatum()));
                }
            }
        }
        selectieRepository.slaSelectieOp(selectierun);
        LOGGER.info("Selectie bepaald selectieRunId = {}", selectierun.getId());
        return selectie;
    }


    @Override
    @Transactional(transactionManager = "masterTransactionManager")
    public void eindeSelectie(final Selectie selectie) {
        final Timestamp nuTijd = DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(DatumUtil.nuAlsZonedDateTime());
        final Integer nuDatum = DatumUtil.vandaag();
        final Selectierun selectierun = selectie.getSelectierun();
        selectierun.setTijdstipGereed(DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(DatumUtil.nuAlsZonedDateTime()));
        for (Selectietaak selectietaak : selectierun.getSelectieTaken()) {
            final SelectietaakStatus statusNieuw = updateSelectietaakStatus(selectietaak, nuTijd);
            //historie selectietaak
            final SelectietaakHistorie selectietaakHistorieVervallen = vervalSelectietaak(nuTijd, selectietaak.getSelectietaakHistorieSet());
            final SelectietaakHistorie selectietaakHistorieNieuw = new SelectietaakHistorie(selectietaakHistorieVervallen);
            selectietaakHistorieNieuw.setDatumUitvoer(nuDatum);
            selectietaakHistorieNieuw.setDatumTijdRegistratie(nuTijd);
            selectietaak.addSelectietaakHistorieSet(selectietaakHistorieNieuw);
            //update ook de a-laag selectietaak
            selectietaak.setDatumUitvoer(nuDatum);
            selectietaak.setStatus((short) statusNieuw.getId());
        }
        selectieRepository.werkSelectieBij(selectierun);
    }

    private SelectietaakStatus updateSelectietaakStatus(Selectietaak selectietaak, Timestamp nuTijd) {
        final SelectietaakStatus selectietaakStatus;
        String statusToelichting = null;
        if (selectieJobRunStatusService.getStatus().isError()) {
            selectietaakStatus = SelectietaakStatus.UITVOERING_MISLUKT;
        } else if (selectieJobRunStatusService.getStatus().getOngeldigeSelectietaken().contains(selectietaak.getId())) {
            selectietaakStatus = SelectietaakStatus.UITVOERING_AFGEBROKEN;
            statusToelichting = "Opgegeven peildatum formeel resultaat ligt voor de overgang naar BRP systematiek";
        } else if (selectietaak.getDienst().getSoortSelectie() == SoortSelectie.STANDAARD_SELECTIE.getId() && (
                selectietaak.getDienst().getLeverwijzeSelectie() == null || LeverwijzeSelectie.STANDAARD.getId() == selectietaak.getDienst()
                        .getLeverwijzeSelectie())) {
            if (Boolean.TRUE.equals(selectietaak.getDienst().getIndicatieSelectieresultaatControleren())) {
                selectietaakStatus = SelectietaakStatus.CONTROLEREN;
            } else {
                selectietaakStatus = SelectietaakStatus.TE_LEVEREN;
            }
        } else {
            selectietaakStatus = SelectietaakStatus.SELECTIE_UITGEVOERD;
        }
        //historie selectietaakstatus
        SelectietaakStatusHistorieUtil.updateTaakStatus(selectietaak, nuTijd, selectietaakStatus, statusToelichting);
        return selectietaakStatus;
    }

    private SelectietaakHistorie vervalSelectietaak(Timestamp nu, Set<SelectietaakHistorie> selectietaakHistorieSet) {
        //zoek niet vervallen his en verval
        for (SelectietaakHistorie selectietaakHistorie : selectietaakHistorieSet) {
            if (selectietaakHistorie.getDatumTijdVerval() == null) {
                selectietaakHistorie.setDatumTijdVerval(nu);
                return selectietaakHistorie;
            }
        }
        throw new NullPointerException("er moet een actueel selectie taak his record zijn");
    }

    private List<SelectietaakAutorisatie> bepaalSelectietaakAutorisaties() {
        final LinkedList<SelectietaakAutorisatie> selectietaakAutorisaties = Lists.newLinkedList();
        final List<Selectietaak> selectietaakList = selectieRepository.getTakenGeplandVoorVandaag().stream()
                .filter(selectietaak -> selectietaak.getStatus() != null &&
                        selectietaak.getStatus() == SelectietaakStatus.UITVOERBAAR.getId())
                .filter(selectietaak -> selectietaak.getDatumPlanning() != null
                        && selectietaak.getDatumPlanning() == DatumUtil.vandaag()).collect(Collectors.toList());
        for (Selectietaak selectietaak : selectietaakList) {
            final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = selectietaak.getToegangLeveringsAutorisatie();

            if (selectieAutorisatieService.isAutorisatieGeldig(toegangLeveringsAutorisatie, selectietaak.getDienst())) {
                selectietaakAutorisaties.add(new SelectietaakAutorisatie(selectietaak, toegangLeveringsAutorisatie));
            } else {
                LOGGER.info(String.format("Ongeldige autorisatie uit te voeren selectie taak met id [%d]", selectietaak.getId()));
                final Timestamp nuTijd = DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(DatumUtil.nuAlsZonedDateTime());
                //historie selectietaakstatus
                SelectietaakStatusHistorieUtil.updateTaakStatus(selectietaak, nuTijd, SelectietaakStatus.AUTORISATIE_GEWIJZIGD, null);
            }
        }
        return selectietaakAutorisaties;
    }


}
