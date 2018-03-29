/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import nl.bzk.brp.service.selectie.algemeen.ConfiguratieService;
import nl.bzk.brp.service.selectie.algemeen.JobStopEvent;
import nl.bzk.brp.service.selectie.algemeen.Selectie;
import nl.bzk.brp.service.selectie.algemeen.SelectieException;
import nl.bzk.brp.service.selectie.algemeen.SelectietaakAutorisatie;
import nl.bzk.brp.service.selectie.lezer.SelectieLezerService;
import nl.bzk.brp.service.selectie.lezer.status.SelectieJobRunStatus;
import nl.bzk.brp.service.selectie.lezer.status.SelectieJobRunStatusService;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * SelectieRunJobServiceImpl.
 */
@Service
final class SelectieRunJobServiceImpl implements SelectieRunJobService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private ApplicationContext applicationContext;

    @Inject
    private SelectieService selectieService;

    @Inject
    private SelectieJobRunStatusService selectieJobRunStatusService;

    @Inject
    private MaakSelectieResultaatTaakPublicatieService maakSelectieResultaatTaakPublicatieService;

    @Inject
    private ConfiguratieService configuratieService;

    @Inject
    private SelectieLezerService selectieLezerService;

    private SelectieRunJobServiceImpl() {
    }

    @Override
    public boolean isRunning() {
        final boolean running = !selectieJobRunStatusService.getStatus().isStopped();
        LOGGER.info("selectie run service is running: " + running);
        return running;
    }

    @Override
    public void stop() {
        LOGGER.info("stop selectie run service");
        applicationContext.publishEvent(new JobStopEvent(this));
    }

    @Override
    @Async("selectiejob")
    public void start() {
        LOGGER.info("start selectie run service: ");
        BrpNu.set();
        Thread.currentThread().setName("Selectie Job Runner");
        final SelectieJobRunStatus status = selectieJobRunStatusService.newStatus();
        Selectie selectie = null;
        try {
            selectie = selectieService.bepaalSelectie();
            status.setStartDatum(new Date());
            status.setSelectieRunId(selectie.getSelectierun().getId());
            startSelectie(selectie);
            LOGGER.info("einde selectie run service: " + selectie.getSelectierun().getId());
        } finally {
            status.setEindeDatum(new Date());
            if (selectie != null) {
                selectieService.eindeSelectie(selectie);
            }
        }
    }

    private void startSelectie(final Selectie selectie) {
        if (!selectie.isUitvoerbaar()) {
            LOGGER.info("selectie niet uitvoerbaar, stop");
            return;
        }

        final ExecutorService wachtExecutor = Executors.newFixedThreadPool(1);
        try {
            selectieLezerService.startLezen(selectie);
            LOGGER.info("klaar met verwerken blob batchen en verwerktaken. Maak selectie resultaat file taken.");
            final boolean fragmentklaar = wachtFragmentTakenKlaar(wachtExecutor);
            LOGGER.info("alle fragment taken zijn klaar: " + fragmentklaar);
            LOGGER.info("start maak selectie resultaat taken");
            maakSelectieResultaatTaken(selectie);
            final boolean klaar = wachtSelectieResultaatTakenKlaar(wachtExecutor);
            LOGGER.info("alle taken zijn klaar: " + klaar);
        } catch (InterruptedException e) {
            LOGGER.error("Interrupt error running job", e);
            Thread.currentThread().interrupt();
            selectieJobRunStatusService.getStatus().setError(true);
            throw new BrpServiceRuntimeException(e);
        } catch (SelectieException e) {
            LOGGER.error("Error running job", e);
            selectieJobRunStatusService.getStatus().setError(true);
            throw new BrpServiceRuntimeException(e);
        } finally {
            wachtExecutor.shutdownNow();
        }
    }

    private void maakSelectieResultaatTaken(Selectie selectie) {
        final List<MaakSelectieResultaatTaak> taken = new ArrayList<>(selectie.getSelectietaakAutorisatieList().size());
        final List<MaakSelectieResultaatTaak> sv11Taken = new ArrayList<>();
        for (SelectietaakAutorisatie autorisatie : selectie.getSelectietaakAutorisatieList()) {
            //maak selectie resultaat taak
            final MaakSelectieResultaatTaak taak = new MaakSelectieResultaatTaak();
            //een taak kan ongeldig zijn. Dan zal in de resultaat service de resultaat folders/files worden leeggegooid.
            taak.setOngeldig(selectieJobRunStatusService.getStatus().getOngeldigeSelectietaken().contains(autorisatie.getSelectietaak().getId()));
            taak.setSelectieRunId(selectie.getSelectierun().getId());
            taak.setSelectietaakId(autorisatie.getSelectietaak().getId());
            taak.setToegangLeveringsAutorisatieId(autorisatie.getToegangLeveringsAutorisatie().getId());
            final Dienst dienst = autorisatie.getSelectietaak().getDienst();
            taak.setDienstId(dienst.getId());
            taak.setSoortSelectie(Optional.ofNullable(dienst.getSoortSelectie())
                    .map(SoortSelectie::parseId).orElseThrow(() -> new NullPointerException("soort selectie mag niet null zijn")));
            taak.setDatumUitvoer(DatumUtil.vanDatumNaarInteger(selectie.getSelectierun().getTijdstipStart()));

            //zet peilmomenten
            taak.setPeilmomentMaterieelResultaat(autorisatie.getSelectietaak().getPeilmomentMaterieelResultaat());
            taak.setPeilmomentFormeelResultaat(autorisatie.getSelectietaak().getPeilmomentFormeelResultaat());

            if (isAfnemerindicatieSelectieTaak(taak)) {
                taak.setAantalPersonen(selectieJobRunStatusService.getStatus().getVerwerktePersonenPerAfnemerindicatieTaak(taak.getSelectietaakId()));
            }
            if (Stelsel.BRP == autorisatie.getToegangLeveringsAutorisatie().getLeveringsautorisatie().getStelsel() || (
                    Stelsel.GBA == autorisatie.getToegangLeveringsAutorisatie().getLeveringsautorisatie().getStelsel()
                            && selectieJobRunStatusService.getStatus().getAantalVerwerktePersonenNetwerk() > 0)) {
                taken.add(taak);
            } else {
                sv11Taken.add(taak);
            }
        }
        selectieJobRunStatusService.getStatus().getSelectieResultaatSchrijfTaakIncrement(taken.size());
        maakSelectieResultaatTaakPublicatieService.publiceerMaakSelectieResultaatTaken(taken);
        if (!sv11Taken.isEmpty()) {
            maakSelectieResultaatTaakPublicatieService.publiceerMaakSelectieGeenResultaatNetwerkTaak(sv11Taken);
        }
    }


    private static boolean isAfnemerindicatieSelectieTaak(final MaakSelectieResultaatTaak taak) {
        return taak.getSoortSelectie() == SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE
                || taak.getSoortSelectie() == SoortSelectie.SELECTIE_MET_VERWIJDERING_AFNEMERINDICATIE;
    }

    private boolean wachtSelectieResultaatTakenKlaar(ExecutorService wachtExecutor) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final WachtCompleetTaak wachtTaak = new WachtCompleetTaak();
        wachtTaak.setSelectieJobRunStatusService(selectieJobRunStatusService);
        wachtTaak.setCountDownLatch(latch);
        wachtExecutor.submit(wachtTaak);
        final long wachttijd = configuratieService.getMaximaleWachttijdWachttaak();
        return latch.await(wachttijd, TimeUnit.MINUTES);
    }

    private boolean wachtFragmentTakenKlaar(ExecutorService wachtExecutor) throws InterruptedException {
        final CountDownLatch latchFragment = new CountDownLatch(1);
        final WachtSchrijvenCompleet wachtTaak = new WachtSchrijvenCompleet();
        wachtTaak.setSelectieJobRunStatusService(selectieJobRunStatusService);
        wachtTaak.setCountDownLatch(latchFragment);
        wachtExecutor.submit(wachtTaak);
        final long wachttijd = configuratieService.getMaximaleWachttijdWachttaak();
        return latchFragment.await(wachttijd, TimeUnit.MINUTES);
    }
}
