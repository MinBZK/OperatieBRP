/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.synchronisatie;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.delivery.synchronisatie.SynchronisatieWebServiceImpl;
import nl.bzk.brp.delivery.synchronisatie.SynchroniseerPersoonCallbackImpl;
import nl.bzk.brp.delivery.synchronisatie.SynchroniseerStamgegevenCallbackImpl;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;
import nl.bzk.brp.service.synchronisatie.persoon.SynchroniseerPersoonService;
import nl.bzk.brp.service.synchronisatie.stamgegeven.SynchroniseerStamgegevenService;
import nl.bzk.brp.tooling.apitest.dto.XmlVerzoek;
import nl.bzk.brp.tooling.apitest.service.basis.VerzoekService;
import nl.bzk.brp.tooling.apitest.service.dataaccess.AutorisatieStubService;
import org.springframework.util.Assert;

/**
 * SynchronisatieApiService implementatie.
 */
final class SynchronisatieApiServiceImpl implements SynchronisatieApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private VerzoekService verzoekService;
    @Inject
    @Named("maakLeveringsautorisatieRepository")
    private AutorisatieStubService autorisatieService;
    @Inject
    private SynchroniseerPersoonService     synchroniseerPersoonService;
    @Inject
    private SynchroniseerStamgegevenService synchroniseerStamgegevenService;
    @Inject
    private SynchronisatieWebServiceImpl webserviceProvider;

    @Override
    public void synchroniseerPersoon(final Map<String, String> map) throws IOException {
        LOGGER.info("Verzoek: Synchroniseer Persoon: " + map);
        final Set<String> keyset = Sets.newHashSet(map.keySet());
        keyset.removeAll(KEYS_SYNCHRONISEER_PERSOON);
        Assert.isTrue(keyset.isEmpty(), "Ongeldige synchroniseer persoon keys: " + keyset);
        verzoekService.resetStubState();
        final SynchronisatieVerzoek verzoek = maakSynchronisatieVerzoek(map);
        verzoek.setSoortDienst(SoortDienst.SYNCHRONISATIE_PERSOON);
        verzoek.getZoekCriteriaPersoon().setCommunicatieId("comid.zoekCriteria");
        verzoek.getZoekCriteriaPersoon().setBsn(map.get(BSN));
        final SynchroniseerPersoonCallbackImpl synchronisatieCallback = new SynchroniseerPersoonCallbackImpl();
        synchroniseerPersoonService.synchroniseer(verzoek, synchronisatieCallback);
        verzoekService.bewaarVerzoekAntwoord(verzoek, synchronisatieCallback.getResultaat());
    }

    @Override
    public void synchroniseerStamgegeven(final Map<String, String> map) {
        LOGGER.info("Verzoek: Synchroniseer Stamgegeven: " + map);
        final Set<String> keyset = Sets.newHashSet(map.keySet());
        keyset.removeAll(KEYS_SYNCHRONISEER_STAMGEGEVEN);
        Assert.isTrue(keyset.isEmpty(), "Ongeldige synchroniseer stamgegeven keys: " + keyset);
        Assert.notNull(map.get(STAMGEGEVEN), "parameter 'stamgegeven' ontbreekt");
        final SynchronisatieVerzoek verzoek = maakSynchronisatieVerzoek(map);
        verzoek.setSoortDienst(SoortDienst.SYNCHRONISATIE_STAMGEGEVEN);
        verzoek.getParameters().setStamgegeven(map.get(STAMGEGEVEN));
        final SynchroniseerStamgegevenCallbackImpl synchroniseerStamgegevenCallback = new SynchroniseerStamgegevenCallbackImpl();
        synchroniseerStamgegevenService.maakResponse(verzoek, synchroniseerStamgegevenCallback);
        verzoekService.bewaarVerzoekAntwoord(verzoek, synchroniseerStamgegevenCallback.getResultaat());
    }

    @Override
    public void synchroniseerPersoonMetXml(final XmlVerzoek xmlVerzoek) {
        verzoekService.xmlVerzoek(xmlVerzoek, webserviceProvider, SoortDienst.SYNCHRONISATIE_PERSOON);
    }

    @Override
    public void synchroniseerStamgegevenMetXml(final XmlVerzoek xmlVerzoek) {
        verzoekService.xmlVerzoek(xmlVerzoek, webserviceProvider, SoortDienst.SYNCHRONISATIE_STAMGEGEVEN);
    }

    private SynchronisatieVerzoek maakSynchronisatieVerzoek(final Map<String, String> map) {
        VerzoekService.assertMinimaleParametersAanwezig(map);
        final SynchronisatieVerzoek verzoek = new SynchronisatieVerzoek();
        VerzoekService.vulGeneriek(verzoek, map);

        final String leveringsautorisatieId;
        if (map.containsKey(VerzoekService.LEVERING_AUTORISATIE_ID)) {
            //nuttig voor het opgeven van een foutieve leveringautorisatie
            leveringsautorisatieId = map.get(VerzoekService.LEVERING_AUTORISATIE_ID);
        } else {
            leveringsautorisatieId = autorisatieService.getLeveringsautorisatie(map.get(VerzoekService.LEVERING_AUTORISATIE_NAAM)).getId().toString();
        }
        verzoek.getParameters().setLeveringsAutorisatieId(leveringsautorisatieId);
        return verzoek;
    }

}
