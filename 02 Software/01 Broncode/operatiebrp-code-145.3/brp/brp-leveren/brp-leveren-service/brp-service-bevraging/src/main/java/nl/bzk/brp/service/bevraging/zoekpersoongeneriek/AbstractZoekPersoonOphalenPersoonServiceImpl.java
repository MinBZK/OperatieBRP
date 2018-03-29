/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekbereik;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.ZoekCriterium;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.request.DatumService;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import nl.bzk.brp.service.dalapi.QueryCancelledException;
import nl.bzk.brp.service.dalapi.QueryNietUitgevoerdException;
import nl.bzk.brp.service.dalapi.QueryTeDuurException;
import nl.bzk.brp.service.dalapi.ZoekPersoonDataOphalerService;
import org.springframework.beans.factory.annotation.Value;


/**
 * Functionaliteit voor het ophalen van een personen a.h.v. zoekcriteria.
 * @param <T> verzoek
 */
@Bedrijfsregel(Regel.R2285)
@Bedrijfsregel(Regel.R2289)
public abstract class AbstractZoekPersoonOphalenPersoonServiceImpl<T extends ZoekPersoonGeneriekVerzoek> implements ZoekPersoon.OphalenPersoonService<T> {

    /**
     * max results default.
     */
    public static final int MAX_RESULTS_DEFAULT = 10;

    private static final AtomicInteger CONCURRENT_REQUESTS = new AtomicInteger();

    @Value("${brp.bevraging.zoekpersoon.max.tussenresultaat:250}")
    private int maxResulatenTussenResultaat;
    @Value("${brp.bevraging.zoekpersoon.max.conc.request:10}")
    private int maxConcurrentRequest;

    private PersoonslijstService persoonslijstService;
    private GevondenZoekPersoonFilterService gevondenZoekPersoonFilterService;
    private ZoekPersoonDataOphalerService zoekPersoonDataOphalerService;
    private ConverteerVerzoekZoekCriteriaService zoekCriteriaConverteerService;
    protected DatumService datumService;

    @Override
    public final List<Persoonslijst> voerStapUit(final T bevragingVerzoek, final Autorisatiebundel autorisatiebundel)
            throws StapException {
        try {
            controleerAantalRequestsBinnenLimiet();
            final Set<ZoekCriterium> zoekCriteria = zoekCriteriaConverteerService.maakZoekCriteria(bevragingVerzoek.getZoekCriteriaPersoon());
            //zoek personen
            final ZoekPersoonGeneriekVerzoek.ZoekBereikParameters zoekBereikParameters = bevragingVerzoek.getParameters().getZoekBereikParameters();
            final List<Long> persoonIds = bepaalPersoonIds(zoekCriteria, maxResulatenTussenResultaat, zoekBereikParameters);
            if (persoonIds.isEmpty()) {
                return Collections.emptyList();
            }
            if (persoonIds.size() > maxResulatenTussenResultaat) {
                throw new StapMeldingException(Regel.R2285);
            }

            final Set<Long> persoonIdsSet = new HashSet<>(persoonIds);
            final List<Persoonslijst> persoonslijstLijst = persoonslijstService.getByIdsVoorZoeken(persoonIdsSet);
            final List<Persoonslijst> gefilterdePersoonslijstSet = gevondenZoekPersoonFilterService.filterPersoonslijst(autorisatiebundel,
                    persoonslijstLijst);
            valideerAantalZoekResultaten(gefilterdePersoonslijstSet, autorisatiebundel, zoekBereikParameters);
            return gefilterdePersoonslijstSet;
        } catch (QueryTeDuurException | QueryCancelledException e) {
            throw new StapMeldingException(Regel.R2284, e);
        } catch (QueryNietUitgevoerdException algemeneQueryFout) {
            throw new StapException(algemeneQueryFout);
        } finally {
            CONCURRENT_REQUESTS.decrementAndGet();
        }
    }

    private void controleerAantalRequestsBinnenLimiet() throws StapMeldingException {
        final int nieuwAantalRequest = CONCURRENT_REQUESTS.incrementAndGet();
        if (nieuwAantalRequest > maxConcurrentRequest) {
            throw new StapMeldingException(Regel.R2340);
        }
    }

    /**
     * @param gefilterdePersoonsgegevens gefilterdePersoonsgegevens
     * @param autorisatiebundel autorisatiebundel
     * @param zoekBereikParameters zoekbereik parameters
     * @throws StapMeldingException fout tijdens valideren
     */
    protected abstract void valideerAantalZoekResultaten(final List<Persoonslijst> gefilterdePersoonsgegevens,
                                                         final Autorisatiebundel autorisatiebundel,
                                                         final ZoekPersoonGeneriekVerzoek.ZoekBereikParameters zoekBereikParameters)
            throws StapMeldingException;

    @Bedrijfsregel(Regel.R2402)
    private List<Long> bepaalPersoonIds(final Set<ZoekCriterium> zoekCriteria, final Integer maxAantalZoekResultaten,
                                        final ZoekPersoonGeneriekVerzoek.ZoekBereikParameters zoekBereikParameters)
            throws StapMeldingException, QueryNietUitgevoerdException {
        final List<Long> persoonIds;
        final boolean historisch = zoekBereikParameters != null && (zoekBereikParameters.getPeilmomentMaterieel() != null || Zoekbereik.MATERIELE_PERIODE
                .equals(zoekBereikParameters.getZoekBereik()));
        if (historisch) {
            Integer peilmomentMaterieel = null;
            if (zoekBereikParameters.getPeilmomentMaterieel() != null) {
                peilmomentMaterieel = Integer.parseInt(
                        datumService.parseDate(zoekBereikParameters.getPeilmomentMaterieel()).format(DateTimeFormatter.BASIC_ISO_DATE));
            }
            persoonIds = zoekPersoonDataOphalerService
                    .zoekPersonenHistorisch(zoekCriteria, peilmomentMaterieel,
                            Zoekbereik.MATERIELE_PERIODE.equals(zoekBereikParameters.getZoekBereik()), maxAantalZoekResultaten);
        } else {
            persoonIds = zoekPersoonDataOphalerService.zoekPersonenActueel(zoekCriteria, maxAantalZoekResultaten);
        }
        return persoonIds;
    }

    @Inject
    public void setPersoonslijstService(final PersoonslijstService persoonslijstService) {
        this.persoonslijstService = persoonslijstService;
    }

    @Inject
    public void setGevondenZoekPersoonFilterService(final GevondenZoekPersoonFilterService gevondenZoekPersoonFilterService) {
        this.gevondenZoekPersoonFilterService = gevondenZoekPersoonFilterService;
    }

    @Inject
    public void setZoekPersoonDataOphalerService(final ZoekPersoonDataOphalerService zoekPersoonDataOphalerService) {
        this.zoekPersoonDataOphalerService = zoekPersoonDataOphalerService;
    }

    @Inject
    public void setZoekCriteriaConverteerService(final ConverteerVerzoekZoekCriteriaService zoekCriteriaConverteerService) {
        this.zoekCriteriaConverteerService = zoekCriteriaConverteerService;
    }

    @Inject
    public void setDatumService(final DatumService datumService) {
        this.datumService = datumService;
    }
}
