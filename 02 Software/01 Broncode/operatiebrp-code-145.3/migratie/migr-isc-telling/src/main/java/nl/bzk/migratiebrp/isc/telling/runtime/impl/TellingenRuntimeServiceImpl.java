/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.runtime.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.telling.entiteit.Bericht;
import nl.bzk.migratiebrp.isc.telling.entiteit.BerichtTelling;
import nl.bzk.migratiebrp.isc.telling.entiteit.ProcesExtractie;
import nl.bzk.migratiebrp.isc.telling.entiteit.ProcesTelling;
import nl.bzk.migratiebrp.isc.telling.repository.BerichtRepository;
import nl.bzk.migratiebrp.isc.telling.repository.BerichtTellingRepository;
import nl.bzk.migratiebrp.isc.telling.repository.ProcesExtractieRepository;
import nl.bzk.migratiebrp.isc.telling.repository.ProcesTellingenRepository;
import nl.bzk.migratiebrp.isc.telling.repository.RuntimeRepository;
import nl.bzk.migratiebrp.isc.telling.runtime.TellingenRuntimeService;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatie van de tellingen runtime service.
 */
@Service("tellingenRuntimeService")
public final class TellingenRuntimeServiceImpl implements TellingenRuntimeService {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Integer MAXIMALE_BATCH_GROOTTE = 5000;
    private static final String MAP_SLEUTEL_SPLITTER = "_";
    private static final String MAP_SLEUTEL_DATUM_FORMAAT = "yyyy-MM-dd";
    private static final Integer PROCES_EXTRACTIE_SLEUTEL_INDEX_DATUM = 0;
    private static final Integer PROCES_EXTRACTIE_SLEUTEL_INDEX_PROCESNAAM = 1;
    private static final Integer PROCES_EXTRACTIE_SLEUTEL_INDEX_KANAAL = 2;
    private static final Integer PROCES_EXTRACTIE_SLEUTEL_INDEX_BERICHTTYPE = 3;
    private static final Integer BERICHT_SLEUTEL_INDEX_DATUM = 0;
    private static final Integer BERICHT_SLEUTEL_INDEX_BERICHTTYPE = 1;
    private static final Integer BERICHT_SLEUTEL_INDEX_KANAAL = 2;
    private static final String RUNTIME_NAAM = "tellingen";

    private final BerichtRepository berichtRepositoryService;

    private final ProcesExtractieRepository procesExtractieRepositoryService;

    private final BerichtTellingRepository berichtTellingenRepositoryService;

    private final ProcesTellingenRepository procesTellingenRepositoryService;

    private final RuntimeRepository runtimeRepositoryService;

    private Map<String, List<ProcesExtractie>> teVerwerkenGestarteProcesExtracties;
    private Map<String, List<ProcesExtractie>> teVerwerkenBeeindigdeProcesExtracties;
    private Map<String, List<Bericht>> teVerwerkenBerichten;
    private List<Bericht> opgehaaldeGestarteProcesExtractieBerichten;
    private List<ProcesExtractie> opgehaaldeGestarteProcesExtracties;
    private List<ProcesExtractie> opgehaaldeBeeindigdeProcesExtracties;

    /**
     * constructor.
     * @param berichtRepositoryService bericht repo
     * @param procesExtractieRepositoryService proces
     * @param berichtTellingenRepositoryService bericht telling
     * @param procesTellingenRepositoryService proces telling
     * @param runtimeRepositoryService runtime
     */
    @Inject
    public TellingenRuntimeServiceImpl(final BerichtRepository berichtRepositoryService, final ProcesExtractieRepository procesExtractieRepositoryService,
                                       final BerichtTellingRepository berichtTellingenRepositoryService,
                                       final ProcesTellingenRepository procesTellingenRepositoryService,
                                       final RuntimeRepository runtimeRepositoryService) {
        this.berichtRepositoryService = berichtRepositoryService;
        this.procesExtractieRepositoryService = procesExtractieRepositoryService;
        this.berichtTellingenRepositoryService = berichtTellingenRepositoryService;
        this.procesTellingenRepositoryService = procesTellingenRepositoryService;
        this.runtimeRepositoryService = runtimeRepositoryService;
    }

    @Override
    public void werkLopendeTellingenBij(final Timestamp procesDatum) {

        // Voeg runtime toe: Deze start in eigen transactie (die eventueel ook weer gerollbacked wordt)
        if (runtimeRepositoryService.voegRuntimeToe(RUNTIME_NAAM)) {

            try {
                boolean resultaat;

                final Timestamp selectieDatum = procesDatum;

                resultaat = werkLopendeTellingenBerichtenBij(selectieDatum);

                // Als er in de voorgaande stappen wat is misgegaan, dan willen we niet verder gaan met bijwerken.
                if (resultaat) {
                    resultaat = werkLopendeTellingenProcessenBij(selectieDatum);
                }

                LOG.info("Resultaat = " + (resultaat ? "Geslaagd" : "Gefaald"));

            } finally {
                runtimeRepositoryService.verwijderRuntime(RUNTIME_NAAM);
            }
        }

    }

    @Override
    public boolean werkLopendeTellingenBerichtenBij(final Timestamp selectieDatum) {

        boolean resultaat = true;

        LOG.info("Bepalen dataset te tellen berichten.");

        final Long aantalTeVerwerkenBerichten = berichtRepositoryService.telInTellingTeVerwerkenBerichten(selectieDatum);
        LOG.info("Berichten dataset bestaat uit " + aantalTeVerwerkenBerichten + " te tellen berichten.");

        final Integer limit = MAXIMALE_BATCH_GROOTTE;
        int resterendeBerichten = aantalTeVerwerkenBerichten.intValue();

        do {

            if (resterendeBerichten > limit) {
                bepaalTeVerwerkenBerichten(selectieDatum, limit);
            } else {
                bepaalTeVerwerkenBerichten(selectieDatum, resterendeBerichten);
            }

            resultaat = resultaat && werkAantallenLopendeTellingenBerichtenBij();

            // Als er in de voorgaande stappen wat is misgegaan, dan willen we niet markeren.
            if (resultaat) {
                resultaat = markeerBerichtenAlsGeteld();
            }

            if (teVerwerkenBerichten != null) {
                resterendeBerichten -= opgehaaldeGestarteProcesExtractieBerichten.size();
                LOG.info("Verwerkte berichten: " + opgehaaldeGestarteProcesExtractieBerichten.size());
                LOG.info("Resterende berichten: " + resterendeBerichten);
            }
        } while (resterendeBerichten > 0);

        return resultaat;
    }

    @Override
    public boolean werkLopendeTellingenProcessenBij(final Timestamp selectieDatum) {

        boolean resultaat;

        resultaat = werkLopendeTellingenGestarteProcessenBij(selectieDatum);

        // Als er in de voorgaande stap wat is misgegaan, dan willen we de tellingen niet bijwerken.
        if (resultaat) {

            resultaat = werkLopendeTellingenBeeindigdeProcessenBij(selectieDatum);

        }

        return resultaat;
    }

    private boolean werkLopendeTellingenBeeindigdeProcessenBij(final Timestamp selectieDatum) {
        LOG.info("Bepalen dataset beëindigde processen .");
        final Long aantalTeVerwerkenBeeindigdeProcessen = procesExtractieRepositoryService.telInTellingTeVerwerkenBeeindigdeProcessen(selectieDatum);
        LOG.info("Beëindigde processen dataset bestaat uit " + aantalTeVerwerkenBeeindigdeProcessen + " te tellen beëindigde processen.");

        final Integer limit = MAXIMALE_BATCH_GROOTTE;
        boolean resultaat = true;
        int resterendeBeeindigdeProcessen = aantalTeVerwerkenBeeindigdeProcessen.intValue();

        do {

            if (resterendeBeeindigdeProcessen > limit) {
                bepaalBeeindigdeProcesExtracties(selectieDatum, limit);
            } else {
                bepaalBeeindigdeProcesExtracties(selectieDatum, resterendeBeeindigdeProcessen);
            }

            resultaat = resultaat && werkAantallenLopendeTellingenBeeindigdeProcessenBij();

            // Als er in de voorgaande stappen wat is misgegaan, dan willen we niet markeren.
            if (resultaat) {
                resultaat = markeerBeeindigdeProcesExtractiesAlsGeteld();
            }

            if (opgehaaldeBeeindigdeProcesExtracties != null) {
                resterendeBeeindigdeProcessen -= opgehaaldeBeeindigdeProcesExtracties.size();
                LOG.info("Verwerkte beëindigde processen: " + opgehaaldeBeeindigdeProcesExtracties.size());
                LOG.info("Resterende beëindigde processen: " + resterendeBeeindigdeProcessen);
            }
        } while (resterendeBeeindigdeProcessen > 0);
        return resultaat;
    }

    private boolean werkLopendeTellingenGestarteProcessenBij(final Timestamp selectieDatum) {

        LOG.info("Bepalen dataset gestarte processen .");
        final Long aantalTeVerwerkenGestarteProcessen = procesExtractieRepositoryService.telInTellingTeVerwerkenGestarteProcessen(selectieDatum);
        LOG.info("Gestarte processen dataset bestaat uit " + aantalTeVerwerkenGestarteProcessen + " te tellen gestarte processen.");

        final Integer limit = MAXIMALE_BATCH_GROOTTE;
        boolean resultaat = true;
        int resterendeGestarteProcessen = aantalTeVerwerkenGestarteProcessen.intValue();

        do {

            if (resterendeGestarteProcessen > limit) {
                bepaalGestarteProcesExtracties(selectieDatum, limit);
            } else {
                bepaalGestarteProcesExtracties(selectieDatum, resterendeGestarteProcessen);
            }

            resultaat = resultaat && werkAantallenLopendeTellingenGestarteProcessenBij();

            // Als er in de voorgaande stappen wat is misgegaan, dan willen we niet markeren.
            if (resultaat) {
                resultaat = markeerGestarteProcesExtractiesAlsGeteld();
            }

            if (opgehaaldeGestarteProcesExtracties != null) {
                resterendeGestarteProcessen -= opgehaaldeGestarteProcesExtracties.size();
                LOG.info("Verwerkte gestarte processen: " + opgehaaldeGestarteProcesExtracties.size());
                LOG.info("Resterende gestarte processen: " + resterendeGestarteProcessen);
            }
        } while (resterendeGestarteProcessen > 0);
        return resultaat;
    }

    private void bepaalTeVerwerkenBerichten(final Timestamp datumTot, final Integer limit) {

        LOG.info("Bepalen berichten voor tellingen (limiet=" + limit + ").");
        opgehaaldeGestarteProcesExtractieBerichten = berichtRepositoryService.selecteerInTellingTeVerwerkenBerichten(datumTot, limit);

        teVerwerkenBerichten = Collections.synchronizedMap(new HashMap<String, List<Bericht>>());

        if (opgehaaldeGestarteProcesExtractieBerichten != null && !opgehaaldeGestarteProcesExtractieBerichten.isEmpty()) {
            LOG.info(opgehaaldeGestarteProcesExtractieBerichten.size() + " berichten gevonden voor tellingen.");

            // Maak een map waarin de opgehaalde gestarte berichten per berichttype worden gegroepeerd.
            for (final Bericht huidigBericht : opgehaaldeGestarteProcesExtractieBerichten) {

                final String mapSleutelHuidigBericht =
                        new Date(huidigBericht.getTijdstip().getTime())
                                + MAP_SLEUTEL_SPLITTER
                                + huidigBericht.getNaam()
                                + MAP_SLEUTEL_SPLITTER
                                + huidigBericht.getKanaal();

                if (!teVerwerkenBerichten.containsKey(mapSleutelHuidigBericht)) {
                    teVerwerkenBerichten.put(mapSleutelHuidigBericht, Collections.synchronizedList(new ArrayList<>(Arrays.asList(huidigBericht))));
                } else {
                    teVerwerkenBerichten.get(mapSleutelHuidigBericht).add(huidigBericht);
                }

            }
        }

    }

    private void bepaalGestarteProcesExtracties(final Timestamp datumTot, final Integer limit) {

        LOG.info("Bepalen processen voor 'gestart'.");
        opgehaaldeGestarteProcesExtracties = procesExtractieRepositoryService.selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, limit);

        teVerwerkenGestarteProcesExtracties = Collections.synchronizedMap(new HashMap<String, List<ProcesExtractie>>());

        if (opgehaaldeGestarteProcesExtracties != null && !opgehaaldeGestarteProcesExtracties.isEmpty()) {
            LOG.info(opgehaaldeGestarteProcesExtracties.size() + " processen gevonden voor 'gestart'.");

            // Maak een map waarin de opgehaalde gestarte procesextractie per proces worden gegroepeerd.
            for (final ProcesExtractie huidigeProcesExtractie : opgehaaldeGestarteProcesExtracties) {

                final String mapSleutelHuidigeProcesExtractie = new Date(huidigeProcesExtractie.getStartDatum().getTime())
                        + MAP_SLEUTEL_SPLITTER
                        + huidigeProcesExtractie.getProcesnaam()
                        + MAP_SLEUTEL_SPLITTER
                        + huidigeProcesExtractie.getKanaal()
                        + MAP_SLEUTEL_SPLITTER
                        + huidigeProcesExtractie.getBerichtType();

                if (!teVerwerkenGestarteProcesExtracties.containsKey(mapSleutelHuidigeProcesExtractie)) {
                    teVerwerkenGestarteProcesExtracties.put(
                            mapSleutelHuidigeProcesExtractie,
                            Collections.synchronizedList(new ArrayList<>(Arrays.asList(huidigeProcesExtractie))));
                } else {
                    teVerwerkenGestarteProcesExtracties.get(mapSleutelHuidigeProcesExtractie).add(huidigeProcesExtractie);
                }

            }
        }
    }

    private void bepaalBeeindigdeProcesExtracties(final Timestamp datumTot, final Integer limit) {

        LOG.info("Bepalen processen voor 'beëindigd'.");
        opgehaaldeBeeindigdeProcesExtracties = procesExtractieRepositoryService.selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(datumTot, limit);

        teVerwerkenBeeindigdeProcesExtracties = Collections.synchronizedMap(new HashMap<String, List<ProcesExtractie>>());

        if (opgehaaldeBeeindigdeProcesExtracties != null && !opgehaaldeBeeindigdeProcesExtracties.isEmpty()) {
            LOG.info(opgehaaldeBeeindigdeProcesExtracties.size() + " processen gevonden voor 'beëindigd'.");
            // Maak een map waarin de opgehaalde gestrarte procesextractie per proces worden gegroepeerd.
            for (final ProcesExtractie huidigeProcesExtractie : opgehaaldeBeeindigdeProcesExtracties) {

                final String mapSleutelHuidigeProcesExtractie = new Date(huidigeProcesExtractie.getStartDatum().getTime())
                        + MAP_SLEUTEL_SPLITTER
                        + huidigeProcesExtractie.getProcesnaam()
                        + MAP_SLEUTEL_SPLITTER
                        + huidigeProcesExtractie.getKanaal()
                        + MAP_SLEUTEL_SPLITTER
                        + huidigeProcesExtractie.getBerichtType();

                if (!teVerwerkenBeeindigdeProcesExtracties.containsKey(mapSleutelHuidigeProcesExtractie)) {
                    teVerwerkenBeeindigdeProcesExtracties.put(
                            mapSleutelHuidigeProcesExtractie,
                            Collections.synchronizedList(new ArrayList<>(Arrays.asList(huidigeProcesExtractie))));
                } else {
                    teVerwerkenBeeindigdeProcesExtracties.get(mapSleutelHuidigeProcesExtractie).add(huidigeProcesExtractie);
                }
            }
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean werkAantallenLopendeTellingenGestarteProcessenBij() {
        boolean resultaat = true;

        if (teVerwerkenGestarteProcesExtracties != null && teVerwerkenGestarteProcesExtracties.size() > 0) {
            LOG.info("Bijwerken tellingen processen voor 'gestart'.");
            for (Iterator<String> iterator = teVerwerkenGestarteProcesExtracties.keySet().iterator(); iterator.hasNext(); ) {
                final String mapSleutelHuidigeProcesExtractieLijst = iterator.next();

                final String[] mapSleutelGesplit = mapSleutelHuidigeProcesExtractieLijst.split(MAP_SLEUTEL_SPLITTER);

                final Timestamp datum;
                try {
                    datum =
                            new Timestamp(
                                    new SimpleDateFormat(MAP_SLEUTEL_DATUM_FORMAAT).parse(mapSleutelGesplit[PROCES_EXTRACTIE_SLEUTEL_INDEX_DATUM]).getTime());
                } catch (final ParseException e) {
                    resultaat = false;
                    break;
                }
                final String procesnaam = mapSleutelGesplit[PROCES_EXTRACTIE_SLEUTEL_INDEX_PROCESNAAM];
                final String kanaal = mapSleutelGesplit[PROCES_EXTRACTIE_SLEUTEL_INDEX_KANAAL];
                final String berichtType = mapSleutelGesplit[PROCES_EXTRACTIE_SLEUTEL_INDEX_BERICHTTYPE];

                final ProcesTelling procesTelling = haalProcesTellingOp(procesnaam, kanaal, berichtType, datum);

                final int aantalGestarteProcessen =
                        procesTelling.getAantalGestarteProcessen() + teVerwerkenGestarteProcesExtracties.get(mapSleutelHuidigeProcesExtractieLijst).size();

                procesTelling.setBerichtType(berichtType);
                procesTelling.setAantalGestarteProcessen(aantalGestarteProcessen);

                procesTellingenRepositoryService.save(procesTelling);
            }
        }

        return resultaat;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean werkAantallenLopendeTellingenBeeindigdeProcessenBij() {
        boolean resultaat = true;

        if (teVerwerkenBeeindigdeProcesExtracties != null && teVerwerkenBeeindigdeProcesExtracties.size() > 0) {
            LOG.info("Bijwerken tellingen processen voor 'beëindigd'.");
            for (Iterator<String> iterator = teVerwerkenBeeindigdeProcesExtracties.keySet().iterator(); iterator.hasNext(); ) {
                final String mapSleutelHuidigeProcesExtractieLijst = iterator.next();

                final String[] mapSleutelGesplit = mapSleutelHuidigeProcesExtractieLijst.split(MAP_SLEUTEL_SPLITTER);

                final Timestamp datum;
                try {
                    datum =
                            new Timestamp(
                                    new SimpleDateFormat(MAP_SLEUTEL_DATUM_FORMAAT).parse(mapSleutelGesplit[PROCES_EXTRACTIE_SLEUTEL_INDEX_DATUM]).getTime());
                } catch (final ParseException e) {
                    resultaat = false;
                    break;
                }
                final String procesnaam = mapSleutelGesplit[PROCES_EXTRACTIE_SLEUTEL_INDEX_PROCESNAAM];
                final String kanaal = mapSleutelGesplit[PROCES_EXTRACTIE_SLEUTEL_INDEX_KANAAL];
                final String berichtType = mapSleutelGesplit[PROCES_EXTRACTIE_SLEUTEL_INDEX_BERICHTTYPE];

                final ProcesTelling procesTelling = haalProcesTellingOp(procesnaam, kanaal, berichtType, datum);

                procesTelling.setBerichtType(berichtType);
                procesTelling.setKanaal(kanaal);
                procesTelling.setProcesnaam(procesnaam);
                procesTelling.setAantalBeeindigdeProcessen(
                        procesTelling.getAantalBeeindigdeProcessen()
                                + teVerwerkenBeeindigdeProcesExtracties.get(mapSleutelHuidigeProcesExtractieLijst).size());
                procesTellingenRepositoryService.save(procesTelling);
            }
        }
        return resultaat;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean werkAantallenLopendeTellingenBerichtenBij() {
        boolean resultaat = true;

        if (teVerwerkenBerichten != null && teVerwerkenBerichten.size() > 0) {
            LOG.info("Bijwerken tellingen berichten.");

            for (final String mapSleutelHuidigeBerichtLijst : teVerwerkenBerichten.keySet()) {

                final String[] mapSleutelHuidigeBerichtLijstGesplit = mapSleutelHuidigeBerichtLijst.split(MAP_SLEUTEL_SPLITTER);

                final Timestamp datum;
                try {
                    datum =
                            new Timestamp(
                                    new SimpleDateFormat(MAP_SLEUTEL_DATUM_FORMAAT).parse(mapSleutelHuidigeBerichtLijstGesplit[BERICHT_SLEUTEL_INDEX_DATUM])
                                            .getTime());
                } catch (final ParseException e) {
                    resultaat = false;
                    break;
                }
                final String berichtType = mapSleutelHuidigeBerichtLijstGesplit[BERICHT_SLEUTEL_INDEX_BERICHTTYPE];
                final String kanaal = mapSleutelHuidigeBerichtLijstGesplit[BERICHT_SLEUTEL_INDEX_KANAAL];

                werkBerichtTellingBij(berichtType, kanaal, datum, mapSleutelHuidigeBerichtLijst);
            }
        }
        return resultaat;
    }

    private void werkBerichtTellingBij(final String berichtType, final String kanaal, final Timestamp datum, final String mapSleutelHuidigeBerichtLijst) {

        final BerichtTelling berichtTelling = haalBerichtTellingOp(berichtType, kanaal, datum);
        int aantalIngaand = berichtTelling.getAantalIngaand();
        int aantalUitgaand = berichtTelling.getAantalUitgaand();

        final List<Bericht> berichtList = teVerwerkenBerichten.get(mapSleutelHuidigeBerichtLijst);
        for (final Iterator<Bericht> iterator = berichtList.iterator(); iterator.hasNext(); ) {
            final Bericht huidigBericht = iterator.next();
            final boolean genegeerd = "herhalingGenegeerd".equals(huidigBericht.getJbpmActie());
            if (!genegeerd) {
                final boolean inkomend = Bericht.Direction.INKOMEND.equals(Bericht.Direction.valueOfCode(huidigBericht.getRichting()));
                if (inkomend) {
                    aantalIngaand++;
                } else {
                    aantalUitgaand++;
                }

            } else {
                iterator.remove();
            }
        }

        berichtTelling.setAantalIngaand(aantalIngaand);
        berichtTelling.setAantalUitgaand(aantalUitgaand);

        berichtTellingenRepositoryService.save(berichtTelling);
    }

    private BerichtTelling haalBerichtTellingOp(final String berichtType, final String kanaal, final Timestamp datum) {
        BerichtTelling berichtTelling;

        berichtTelling = berichtTellingenRepositoryService.haalBerichtTellingOp(berichtType, kanaal, datum);

        if (berichtTelling == null) {
            LOG.info(
                    "Nog geen bericht tellingen gevonden (berichttype={}, kanaal={}, datum={}); "
                            + "nieuwe bericht telling aangemaakt.",
                    new Object[]{berichtType, kanaal, new SimpleDateFormat(MAP_SLEUTEL_DATUM_FORMAAT).format(datum),});
            berichtTelling = new BerichtTelling();
            berichtTelling.setBerichtType(berichtType);
            berichtTelling.setKanaal(kanaal);
            berichtTelling.setDatum(datum);
            berichtTelling.setAantalIngaand(0);
            berichtTelling.setAantalUitgaand(0);
        }

        return berichtTelling;
    }

    private ProcesTelling haalProcesTellingOp(final String procesnaam, final String kanaal, final String berichtType, final Timestamp datum) {
        ProcesTelling procesTelling;

        procesTelling = procesTellingenRepositoryService.haalProcesTellingOp(procesnaam, kanaal, berichtType, datum);

        if (procesTelling == null) {
            LOG.info(
                    "Nog geen proces tellingen gevonden(procesnaam={}, berichttype={}, kanaal={}, datum={}); "
                            + "nieuwe proces telling aangemaakt.",
                    new Object[]{procesnaam, berichtType, kanaal, new SimpleDateFormat(MAP_SLEUTEL_DATUM_FORMAAT).format(datum),});
            procesTelling = new ProcesTelling();
            procesTelling.setProcesnaam(procesnaam);
            procesTelling.setDatum(datum);
            procesTelling.setKanaal(kanaal);
            procesTelling.setBerichtType(berichtType);
            procesTelling.setAantalGestarteProcessen(0);
            procesTelling.setAantalBeeindigdeProcessen(0);
        }

        return procesTelling;
    }

    private boolean markeerGestarteProcesExtractiesAlsGeteld() {

        boolean resultaat = true;

        if (teVerwerkenGestarteProcesExtracties != null) {
            LOG.info("Markeren processen als geteld voor 'gestart'.");
            // Markeer gestarte proces extracties als geteld.
            for (final List<ProcesExtractie> huidigeProcesExtractieLijst : teVerwerkenGestarteProcesExtracties.values()) {
                resultaat = resultaat && markeerIndicatieGeteldProcesExtracties(true, huidigeProcesExtractieLijst);
            }
        }

        return resultaat;
    }

    private boolean markeerBeeindigdeProcesExtractiesAlsGeteld() {

        boolean resultaat = true;

        if (teVerwerkenBeeindigdeProcesExtracties != null) {
            LOG.info("Markeren processen als geteld voor 'beëindigd'.");
            // Markeer beëindigde proces extracties als geteld.
            for (final List<ProcesExtractie> huidigeProcesExtractieLijst : teVerwerkenBeeindigdeProcesExtracties.values()) {
                resultaat = resultaat && markeerIndicatieGeteldProcesExtracties(false, huidigeProcesExtractieLijst);
            }
        }

        return resultaat;
    }

    private boolean markeerBerichtenAlsGeteld() {

        boolean resultaat = true;

        if (teVerwerkenBerichten != null) {
            LOG.info("Markeren " + opgehaaldeGestarteProcesExtractieBerichten.size() + " berichten als geteld.");
            // Markeer berichten als gestart geteld.
            for (final List<Bericht> huidigeBerichtenLijst : teVerwerkenBerichten.values()) {
                resultaat = resultaat && markeerIndicatieGeteldBerichten(huidigeBerichtenLijst);
            }
        }

        return resultaat;
    }

    private boolean markeerIndicatieGeteldProcesExtracties(final boolean markeerGestartVerwerkt, final List<ProcesExtractie> teMarkerenProcesExtracties) {
        boolean resultaat = true;

        final List<Long> teUpdatenIds = new ArrayList<>();

        for (final ProcesExtractie huidigeProcesExtractie : teMarkerenProcesExtracties) {
            teUpdatenIds.add(huidigeProcesExtractie.getProcesInstantieId());
        }

        try {
            if (markeerGestartVerwerkt) {
                resultaat = procesExtractieRepositoryService.updateIndicatieGestartGeteldProcesExtracties(teUpdatenIds);
            } else {
                resultaat = procesExtractieRepositoryService.updateIndicatieBeeindigdGeteldProcesExtracties(teUpdatenIds);
            }

        } catch (final HibernateException exceptie) {
            LOG.error("Fout opgetreden bij het bijwerken van de indicatie van processen.",exceptie);
        }

        return resultaat;
    }

    private boolean markeerIndicatieGeteldBerichten(final List<Bericht> teMarkerenBerichten) {

        final List<Long> teUpdatenIds = new ArrayList<>();

        for (final Bericht huidigBericht : teMarkerenBerichten) {
            teUpdatenIds.add(huidigBericht.getId());
        }

        try {

            return berichtRepositoryService.updateIndicatieGeteldBerichten(teUpdatenIds);
        } catch (final HibernateException exceptie) {
            LOG.error("Fout opgetreden bij het bijwerken van geteld indicatie berichten.",exceptie);
            return false;
        }
    }

}
