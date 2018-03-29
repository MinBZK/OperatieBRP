/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.bevraging;

import static nl.bzk.brp.tooling.apitest.service.bevraging.BevragingApiService.assertDienstGevuld;
import static nl.bzk.brp.tooling.apitest.service.bevraging.BevragingApiService.assertGeenOnbekendeKeys;

import com.google.common.collect.Sets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekbereik;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.delivery.bevraging.BevragingCallbackImpl;
import nl.bzk.brp.delivery.bevraging.BevragingWebService;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoekVerwerker;
import nl.bzk.brp.service.bevraging.zoekpersoon.ZoekPersoonVerzoek;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.AbstractZoekPersoonVerzoek;
import nl.bzk.brp.service.bevraging.zoekpersoonopadres.ZoekPersoonOpAdresVerzoek;
import nl.bzk.brp.test.common.dsl.DatumConstanten;
import nl.bzk.brp.tooling.apitest.dto.XmlVerzoek;
import nl.bzk.brp.tooling.apitest.service.basis.VerzoekService;
import nl.bzk.brp.tooling.apitest.service.dataaccess.AutorisatieStubService;
import org.springframework.stereotype.Component;

/**
 * Implementatie van {@link BevragingApiService.ZoekPersoonApiService}.
 */
@Component
final class ZoekPersoonApiServiceImpl implements BevragingApiService.ZoekPersoonApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private VerzoekService                                       verzoekService;
    @Inject
    @Named("maakLeveringsautorisatieRepository")
    private AutorisatieStubService autorisatieService;
    @Inject
    @Named("zoekPersoon")
    private BevragingVerzoekVerwerker<ZoekPersoonVerzoek>        zoekPersoonVerwerker;
    @Inject
    @Named("zoekPersoonOpAdres")
    private BevragingVerzoekVerwerker<ZoekPersoonOpAdresVerzoek> zoekPersoonOpAdresVerwerker;
    @Inject
    private BevragingWebService webserviceProvider;

    @Override
    public void verzoekZoekPersoon(final Map<String, String> map, final LinkedList<AbstractZoekPersoonVerzoek.ZoekCriteria> zoekCriteriaList) {
        LOGGER.info("Verzoek: Zoek Persoon: " + map);
        final ZoekPersoonVerzoek verzoek = new ZoekPersoonVerzoek();
        verzoek.setSoortDienst(SoortDienst.ZOEK_PERSOON);

        vulVerzoek(map, zoekCriteriaList, verzoek);

        final BevragingCallbackImpl bevragingCallback = new BevragingCallbackImpl();
        zoekPersoonVerwerker.verwerk(verzoek, bevragingCallback);
        verzoekService.bewaarVerzoekAntwoord(verzoek, bevragingCallback.getResultaat());
    }

    @Override
    public void verzoekZoekPersoonOpAdres(final Map<String, String> map, final LinkedList<AbstractZoekPersoonVerzoek.ZoekCriteria> zoekCriteriaList) {
        LOGGER.info("Verzoek: Zoek Persoon Op Adres: " + map);
        final ZoekPersoonOpAdresVerzoek verzoek = new ZoekPersoonOpAdresVerzoek();
        final SoortDienst soortDienst = SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS;
        verzoek.setSoortDienst(soortDienst);

        vulVerzoek(map, zoekCriteriaList, verzoek);

        final BevragingCallbackImpl bevragingCallback = new BevragingCallbackImpl();
        zoekPersoonOpAdresVerwerker.verwerk(verzoek, bevragingCallback);
        verzoekService.bewaarVerzoekAntwoord(verzoek, bevragingCallback.getResultaat());
    }

    private void vulVerzoek(final Map<String, String> map, final LinkedList<AbstractZoekPersoonVerzoek.ZoekCriteria> zoekCriteriaList,
        final AbstractZoekPersoonVerzoek verzoek)
    {
        VerzoekService.vulGeneriek(verzoek, map);

        final Set<String> keyset = Sets.newHashSet(map.keySet());
        keyset.removeAll(KEYS_ZOEKEN);
        assertGeenOnbekendeKeys(keyset);

        verzoekService.resetStubState();

        final String leveringsautorisatieId;
        if (map.containsKey(VerzoekService.LEVERING_AUTORISATIE_ID)) {
            leveringsautorisatieId = map.get(VerzoekService.LEVERING_AUTORISATIE_ID);
        } else {
            leveringsautorisatieId = autorisatieService.getLeveringsautorisatie(map.get(VerzoekService.LEVERING_AUTORISATIE_NAAM)).getId().toString();
        }
        verzoek.getParameters().setLeveringsAutorisatieId(leveringsautorisatieId);

        String rolNaam = Rol.AFNEMER.getNaam();
        if (map.containsKey(BevragingKeys.ROL_NAAM)) {
            rolNaam = map.get(BevragingKeys.ROL_NAAM);
        }
        verzoek.getParameters().setRolNaam(rolNaam);

        String dienstId = null;
        if (map.containsKey(BevragingKeys.DIENST_ID)) {
            dienstId = map.get(BevragingKeys.DIENST_ID);
        } else {
            final Leveringsautorisatie leveringsautorisatie = autorisatieService
                .getLeveringsautorisatie(map.get(VerzoekService.LEVERING_AUTORISATIE_NAAM));
            dienstbundelLoop:
            for (final Dienstbundel dienstbundel : leveringsautorisatie.getDienstbundelSet()) {
                for (final Dienst dienst : dienstbundel.getDienstSet()) {
                    if (dienst.getSoortDienst() == verzoek.getSoortDienst()) {
                        dienstId = dienst.getId().toString();
                        break dienstbundelLoop;
                    }
                }
            }
        }
        assertDienstGevuld(dienstId);
        verzoek.getParameters().setDienstIdentificatie(dienstId);

        if (map.containsKey(BevragingKeys.ZOEK_PERSOON_PEILMOMENT_MATERIEEL)) {
            final String datumwaarde = map.get(BevragingKeys.ZOEK_PERSOON_PEILMOMENT_MATERIEEL);
            final LocalDate localDate = DatumConstanten.getPredefinedLocalDateOrNull(datumwaarde);
            String peilmoment = datumwaarde;
            if (localDate != null) {
                peilmoment = localDate.format(DateTimeFormatter.ISO_DATE);
            }
            verzoek.getParameters().setPeilmomentMaterieel(peilmoment);
        }

        if (map.containsKey(BevragingKeys.ZOEK_PERSOON_ZOEKBEREIK)) {
            verzoek.getParameters().setZoekBereik(Zoekbereik.getByNaam(map.get(BevragingKeys.ZOEK_PERSOON_ZOEKBEREIK)));
        }

        verzoek.getZoekCriteriaPersoon().addAll(zoekCriteriaList);
    }

    @Override
    public void zoekPersoonMetXml(final XmlVerzoek xmlVerzoek) {
        verzoekService.xmlVerzoek(xmlVerzoek, webserviceProvider, SoortDienst.ZOEK_PERSOON);
    }

    @Override
    public void zoekPersoonOpAdresMetXml(final XmlVerzoek xmlVerzoek) {
        verzoekService.xmlVerzoek(xmlVerzoek, webserviceProvider, SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS);
    }

}
