/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer;

import com.google.common.collect.Lists;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAutorisatieBericht;
import nl.bzk.brp.domain.internbericht.selectie.SelectiePersoonBericht;
import nl.bzk.brp.domain.internbericht.selectie.SelectieVerwerkTaakBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.selectie.algemeen.Configuratie;
import nl.bzk.brp.service.selectie.algemeen.ConfiguratieService;
import nl.bzk.brp.service.selectie.algemeen.Selectie;
import nl.bzk.brp.service.selectie.algemeen.SelectietaakAutorisatie;
import nl.bzk.brp.service.selectie.lezer.status.SelectieJobRunStatus;
import nl.bzk.brp.service.selectie.lezer.status.SelectieJobRunStatusService;

/**
 * PersoonslijstConsumer. 1 actief.
 */
final class PersoonCacheConsumer implements Callable<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private SelectieTaakPublicatieService selectieTaakPublicatieService;
    private SelectieJobRunStatusService selectieJobRunStatusService;
    private ConfiguratieService configuratieService;

    private BlockingQueue<List<PersoonCache>> queue;
    private Selectie selectie;

    @Override
    public Object call() throws Exception {
        LOGGER.info("startBericht consumer cache batches");
        BrpNu.set();
        final List<SelectieAutorisatieBericht> selectieAutorisatieBerichten = maakSelectieAutorisaties();

        while (true) {
            LOGGER.debug("startBericht consuming batch");
            final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
            final int aantalSelectieTaken = status.getSelectieTaakCount();
            final int aantalSelectieResulaatTaken = status.getVerwerkTakenKlaarCount();
            final int aantalSelectieSchrijfTaken = status.getAantalSchrijfTaken();
            final int aantalSelectieSchrijfResulaatTaken = status.getSchrijfTakenKlaarCount();

            LOGGER.debug("aantal selectie taken gepubliceerd: " + aantalSelectieTaken);
            LOGGER.debug("aantal selectie resultaat taken klaar: " + aantalSelectieResulaatTaken);
            LOGGER.debug("aantal selectie schrijf taken gepubliceerd: " + aantalSelectieSchrijfTaken);
            LOGGER.debug("aantal selectie schrijf resultaat taken klaar: " + aantalSelectieSchrijfResulaatTaken);

            final boolean teDruk =
                    bepaalTeDruk(aantalSelectieTaken, aantalSelectieResulaatTaken, aantalSelectieSchrijfTaken, aantalSelectieSchrijfResulaatTaken);
            if (!teDruk) {
                final List<PersoonCache> caches = queue.poll(Configuratie.QUEUE_POLLING_TIMEOUT_MS, TimeUnit.MILLISECONDS);
                if (caches == null) {
                    //geen blob batch werk beschikbaar
                    LOGGER.debug("geen blob batch werk beschikbaar na wachten");
                    continue;
                }
                //poison, klaar
                if (caches.isEmpty()) {
                    LOGGER.info("einde consumer cache batches");
                    return null;
                }
                if (!status.moetStoppen()) {
                    publiceerSelectieTaken(selectieAutorisatieBerichten, caches);
                }
            }
        }
    }

    private List<SelectieAutorisatieBericht> maakSelectieAutorisaties() {
        final List<SelectieAutorisatieBericht> berichten = new ArrayList<>(selectie.getSelectietaakAutorisatieList().size());
        for (SelectietaakAutorisatie autorisatie : selectie.getSelectietaakAutorisatieList()) {
            final SelectieAutorisatieBericht bericht = new SelectieAutorisatieBericht();
            bericht.setDienstId(autorisatie.getSelectietaak().getDienst().getId());
            bericht.setToegangLeveringsAutorisatieId(autorisatie.getToegangLeveringsAutorisatie().getId());
            bericht.setSelectietaakId(autorisatie.getSelectietaak().getId());
            bericht.setHistorieVorm(HistorieVorm.parseId(autorisatie.getSelectietaak().getDienst().getHistorievormSelectie()));
            bericht.setPeilmomentMaterieel(autorisatie.getSelectietaak().getPeilmomentMaterieelResultaat());
            bericht.setPeilmomentFormeel(
                    DatumUtil.vanTimestampNaarZonedDateTime(autorisatie.getSelectietaak().getPeilmomentFormeelResultaat()));
            bericht.setLijstGebruiken(autorisatie.getSelectietaak().isIndicatieSelectielijstGebruiken());
            berichten.add(bericht);
        }
        return berichten;
    }

    private boolean bepaalTeDruk(int aantalSelectieTaken, int aantalSelectieResulaatTaken, int aantalSelectieSchrijfTaken,
                                 int aantalSelectieSchrijfResulaatTaken) throws InterruptedException {
        final int slaapTijd = 10;
        final int werkSelectieTaakCount = aantalSelectieTaken - aantalSelectieResulaatTaken;
        if (werkSelectieTaakCount >= configuratieService.getMaxSelectieTaak()) {
            LOGGER.info("selectie produceer selectie taak slaap, te druk met selectietaken: " + werkSelectieTaakCount);
            TimeUnit.MILLISECONDS.sleep(slaapTijd);
            return true;
        }
        final int werkSelectieSchrijfTaakCount = aantalSelectieSchrijfTaken - aantalSelectieSchrijfResulaatTaken;
        if (werkSelectieSchrijfTaakCount >= configuratieService.getMaxSelectieSchrijfTaak()) {
            LOGGER.info("selectie produceer selectie taak slaap, te druk met selectieschrijftaken: " + werkSelectieSchrijfTaakCount);
            TimeUnit.MILLISECONDS.sleep(slaapTijd);
            return true;
        }
        return false;
    }

    private void publiceerSelectieTaken(List<SelectieAutorisatieBericht> selectieAutorisatieBerichten, List<PersoonCache> caches) {
        final List<SelectieVerwerkTaakBericht> selectieTaken = new ArrayList<>();
        //verdeel personen in config chunk size
        final List<List<PersoonCache>> persoonsLijstChunks = Lists.partition(caches, configuratieService.getBlobsPerSelectieTaak());
        //verdeel autorisaties in config chunk size
        final List<List<SelectieAutorisatieBericht>> autorisatieChunks =
                Lists.partition(selectieAutorisatieBerichten, configuratieService.getAutorisatiesPerSelectieTaak());
        //maak selectietaken
        for (List<SelectieAutorisatieBericht> autorisatieChunk : autorisatieChunks) {
            for (List<PersoonCache> bundelChunk : persoonsLijstChunks) {
                SelectieVerwerkTaakBericht selectieTaak = maakSelectieTaak(bundelChunk, autorisatieChunk);
                selectieTaken.add(selectieTaak);
                selectieJobRunStatusService.getStatus().incrementEnGetVerwerkTaken();
            }
        }
        selectieTaakPublicatieService.publiceerSelectieTaak(selectieTaken);
    }

    private SelectieVerwerkTaakBericht maakSelectieTaak(List<PersoonCache> caches, List<SelectieAutorisatieBericht> autorisatieChunk) {
        SelectieVerwerkTaakBericht selectieTaak = new SelectieVerwerkTaakBericht();
        final List<SelectiePersoonBericht> selectiePersonen = new ArrayList<>();
        for (PersoonCache persoonCache : caches) {
            final SelectiePersoonBericht selectiePersoonBericht = new SelectiePersoonBericht();
            if (persoonCache.getAfnemerindicatieGegevens().length > 0) {
                final String afnemerindicatieData =
                        new String(Base64.getEncoder().encode(persoonCache.getAfnemerindicatieGegevens()), StandardCharsets.UTF_8);
                selectiePersoonBericht.setAfnemerindicatieGegevens(afnemerindicatieData);
            }
            if (persoonCache.getPersoonHistorieVolledigGegevens().length > 0) {
                final String persoonData = new String(Base64.getEncoder().encode(persoonCache.getPersoonHistorieVolledigGegevens()), StandardCharsets.UTF_8);
                selectiePersoonBericht.setPersoonHistorieVolledigGegevens(persoonData);
            }
            selectiePersonen.add(selectiePersoonBericht);
        }
        selectieTaak.setPersonen(selectiePersonen);
        selectieTaak.setSelectieAutorisaties(autorisatieChunk);
        selectieTaak.setSelectieRunId(selectie.getSelectierun().getId());
        selectieTaak.setSelectieStartDatum(DatumUtil.vanDatumNaarInteger(selectie.getSelectierun().getTijdstipStart()));
        return selectieTaak;
    }

    /**
     * @param queue queue
     */
    public void setQueue(final BlockingQueue<List<PersoonCache>> queue) {
        this.queue = queue;
    }

    /**
     * @param selectie selectie
     */
    public void setSelectie(final Selectie selectie) {
        this.selectie = selectie;
    }

    /**
     * @param selectieTaakPublicatieService selectieTaakPublicatieService
     */
    public void setSelectieTaakPublicatieService(SelectieTaakPublicatieService selectieTaakPublicatieService) {
        this.selectieTaakPublicatieService = selectieTaakPublicatieService;
    }

    /**
     * @param selectieJobRunStatusService selectieJobRunStatusService
     */
    public void setSelectieJobRunStatusService(SelectieJobRunStatusService selectieJobRunStatusService) {
        this.selectieJobRunStatusService = selectieJobRunStatusService;
    }

    /**
     * @param configuratieService configuratieService
     */
    public void setConfiguratieService(ConfiguratieService configuratieService) {
        this.configuratieService = configuratieService;
    }
}
