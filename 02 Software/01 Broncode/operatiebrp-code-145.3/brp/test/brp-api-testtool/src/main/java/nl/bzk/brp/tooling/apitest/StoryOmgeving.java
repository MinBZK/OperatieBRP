/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest;

import com.google.common.collect.Lists;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.cache.CacheController;
import nl.bzk.brp.service.selectie.algemeen.Configuratie;
import nl.bzk.brp.service.selectie.verwerker.cache.VerwerkerCache;
import nl.bzk.brp.tooling.apitest.autorisatie.AutorisatieData;
import nl.bzk.brp.tooling.apitest.autorisatie.Autorisatielader;
import nl.bzk.brp.tooling.apitest.service.basis.ApiService;
import nl.bzk.brp.tooling.apitest.service.basis.BerichtControleService;
import nl.bzk.brp.tooling.apitest.service.basis.BlobMutatieService;
import nl.bzk.brp.tooling.apitest.service.basis.StoryService;
import nl.bzk.brp.tooling.apitest.service.basis.VerzoekService;
import nl.bzk.brp.tooling.apitest.service.basis.impl.BasisConfiguratie;
import nl.bzk.brp.tooling.apitest.service.beheer.BeheerSelectieAPIService;
import nl.bzk.brp.tooling.apitest.service.dataaccess.AfnemerindicatieStubService;
import nl.bzk.brp.tooling.apitest.service.dataaccess.AutorisatieStubService;
import nl.bzk.brp.tooling.apitest.service.dataaccess.PersoonDataStubService;
import nl.bzk.brp.tooling.apitest.service.dataaccess.StamtabelStub;
import nl.bzk.brp.tooling.apitest.service.leveringalgemeen.ArchiveringControleService;
import nl.bzk.brp.tooling.apitest.service.leveringalgemeen.LeverberichtStubService;
import nl.bzk.brp.tooling.apitest.service.leveringalgemeen.LogControleService;
import nl.bzk.brp.tooling.apitest.service.leveringalgemeen.ProtocolleringControleService;
import nl.bzk.brp.tooling.apitest.service.selectie.SelectieAPIService;
import nl.bzk.brp.tooling.apitest.service.vrijbericht.VrijBerichtControleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

/**
 * Een storyomgeving managed de state voor het uitvoeren van een story. Een storyomgeving is gebonden aan één thread. Na uitvoeren van de story kan de
 * omgeving hergebruikt worden voor een volgende story.
 */
public final class StoryOmgeving {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private PersoonDataStubService persoonDataStubService;
    @Inject
    private AfnemerindicatieStubService afnemerindicatieStubService;
    @Inject
    private BlobMutatieService blobMutatieService;
    @Inject
    @Named("maakLeveringsautorisatieRepository")
    private AutorisatieStubService autorisatieService;
    @Inject
    @Named("beheerAutorisatieStub")
    private AutorisatieStubService beheerAutorisatieService;
    @Inject
    private LeverberichtStubService leverberichtStubService;
    @Inject
    private VerzoekService verzoekService;
    @Inject
    private ApiService apiService;
    @Inject
    private BerichtControleService berichtControleService;
    @Inject
    private StoryService storyService;
    @Inject
    private ArchiveringControleService archiveringControleService;
    @Inject
    private ProtocolleringControleService protocolleringControleService;
    @Inject
    private CacheController cacheController;
    @Inject
    private LogControleService logControleService;
    @Inject
    private VrijBerichtControleService vrijBerichtControleService;
    @Inject
    private StamtabelStub stamtabelStub;
    @Inject
    private VerwerkerCache selectieAutorisatieCache;
    @Inject
    private SelectieAPIService.BulkMode bulkMode;
    @Inject
    private Configuratie configuratie;
    @Inject
    private BeheerSelectieAPIService beheerSelectieAPIService;

    private String laatstGeladenAutorisatieKey;

    private final ApplicationContext basisContext;

    /**
     * Constructor.
     */
    public StoryOmgeving() {
        basisContext = new AnnotationConfigApplicationContext(BasisConfiguratie.class);
        basisContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

    public PersoonDataStubService getPersoonDataStubService() {
        return persoonDataStubService;
    }

    public AfnemerindicatieStubService getAfnemerindicatieStubService() {
        return afnemerindicatieStubService;
    }

    public BlobMutatieService getBlobMutatieService() {
        return blobMutatieService;
    }

    public LeverberichtStubService getLeverberichtStubService() {
        return leverberichtStubService;
    }

    public VerzoekService getVerzoekService() {
        return verzoekService;
    }

    public AutorisatieStubService getAutorisatieService() {
        return autorisatieService;
    }

    public StoryService getStoryService() {
        return storyService;
    }

    public BerichtControleService getBerichtControleService() {
        return berichtControleService;
    }

    public ArchiveringControleService getArchiveringControleService() {
        return archiveringControleService;
    }

    public ProtocolleringControleService getProtocolleringControleService() {
        return protocolleringControleService;
    }

    public ApiService getApiService() {
        return apiService;
    }

    public LogControleService getLogControleService() {
        return logControleService;
    }

    public VrijBerichtControleService getVrijBerichtControleService() {
        return vrijBerichtControleService;
    }

    public StamtabelStub getStamtabelStub() {
        return stamtabelStub;
    }

    /**
     * Geeft een bean uit de basis applicatie context.
     * @param t de class van de bean
     * @param <T> het type class
     * @return de bean
     */
    public <T> T getBasisContextBean(final Class<T> t) {
        return basisContext.getBean(t);
    }

    /**
     * Laad de autorisaties.
     * @param autorisatieBestanden lijst met autorisatiebestanden
     */
    public void laadAutorisaties(final List<String> autorisatieBestanden) {
        LOGGER.info("Laad Autorisaties: " + autorisatieBestanden);

        final List<Resource> resources = Lists.newArrayListWithCapacity(autorisatieBestanden.size());
        for (final String bestand : autorisatieBestanden) {
            resources.add(getStoryService().resolvePath(bestand));
        }
        laadAutorisatieResources(resources);
    }

    /**
     * Laad de autorisaties.
     * @param autorisatieBestanden lijst met autorisatiebestanden
     */
    public void laadAutorisatieResources(final List<Resource> autorisatieBestanden) {
        LOGGER.info("Laad Autorisaties: " + autorisatieBestanden);
        final AutorisatieData autorisatieData = Autorisatielader.laadAutorisatie(autorisatieBestanden);
        final String newAutorisatieKey = autorisatieBestanden.toString();
        if (newAutorisatieKey.equals(laatstGeladenAutorisatieKey)) {
            LOGGER.info("Autorisatie is gelijk aan eerder geladen autorisatie en wordt niet opnieuw ingeladen: " + newAutorisatieKey);
            return;
        }
        autorisatieService.setData(autorisatieData);
        beheerAutorisatieService.setData(autorisatieData);
        cacheController.herlaadCaches();
        laatstGeladenAutorisatieKey = newAutorisatieKey;
    }

    /**
     * @param autorisatieData autorisatieData
     * @param newAutorisatieKey newAutorisatieKey
     */
    public void laadAutorisaties(final AutorisatieData autorisatieData, final String newAutorisatieKey) {
        autorisatieService.setData(autorisatieData);
        beheerAutorisatieService.setData(autorisatieData);
        cacheController.herlaadCaches();
        laatstGeladenAutorisatieKey = newAutorisatieKey;
    }


    /**
     * Reset de omgeving.
     */
    public void reset() {
        berichtControleService.reset();
        verzoekService.reset();
        archiveringControleService.reset();
        protocolleringControleService.reset();
        logControleService.reset();
        leverberichtStubService.reset();
        vrijBerichtControleService.reset();
        selectieAutorisatieCache.clear();
        beheerSelectieAPIService.reset();
        bulkMode.reset();
        configuratie.setSelectiebestandFolder(null);
    }
}
