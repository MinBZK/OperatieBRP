/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.afnemerindicatie;

import com.google.common.collect.Sets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.delivery.afnemerindicatie.OnderhoudAfnemerindicatiesWebServiceImpl;
import nl.bzk.brp.delivery.afnemerindicatie.RegistreerAfnemerindicatieCallbackImpl;
import nl.bzk.brp.service.afnemerindicatie.Afnemerindicatie;
import nl.bzk.brp.service.afnemerindicatie.AfnemerindicatieVerzoek;
import nl.bzk.brp.service.afnemerindicatie.OnderhoudAfnemerindicatieService;
import nl.bzk.brp.test.common.dsl.DatumConstanten;
import nl.bzk.brp.tooling.apitest.autorisatie.Partijen;
import nl.bzk.brp.tooling.apitest.dto.XmlVerzoek;
import nl.bzk.brp.tooling.apitest.service.basis.VerzoekService;
import nl.bzk.brp.tooling.apitest.service.dataaccess.AutorisatieStubService;
import org.springframework.util.Assert;

/**
 * OnderhoudAfnemerindicatieApiService implementatie.
 */
final class OnderhoudAfnemerindicatieApiServiceImpl implements OnderhoudAfnemerindicatieApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private VerzoekService verzoekService;
    @Inject
    private OnderhoudAfnemerindicatieService onderhoudAfnemerindicatieService;
    @Inject
    @Named("maakLeveringsautorisatieRepository")
    private AutorisatieStubService autorisatieService;
    @Inject
    private OnderhoudAfnemerindicatiesWebServiceImpl webserviceProvider;

    @Override
    public void plaatsAfnemerindicatie(final Map<String, String> map) {
        LOGGER.info("Verzoek: Plaats Afnemerindicatie: " + map);
        verzoekService.resetStubState();

        final Set<String> keyset = Sets.newHashSet(map.keySet());
        keyset.removeAll(KEYS);
        Assert.isTrue(keyset.isEmpty(), "Ongeldige keys: " + keyset);

        final AfnemerindicatieVerzoek verzoek = maakOnderhoudAfnemerindicatieVerzoek(map);
        verzoek.setSoortDienst(SoortDienst.PLAATSING_AFNEMERINDICATIE);

        final Afnemerindicatie afnemerindicatie = new Afnemerindicatie();
        afnemerindicatie.setBsn(map.get(BSN));
        afnemerindicatie.setPartijCode(verzoek.getStuurgegevens().getZendendePartijCode());

        // maakt het mogelijk in de test een indicatie te plaatsen voor een andere partij dan de eigen partij.
        if (map.containsKey(AFNEMERINDICATIE_ANDERE_PARTIJ)) {
            afnemerindicatie.setPartijCode(String.valueOf(Partijen.getPartij(map.get(AFNEMERINDICATIE_ANDERE_PARTIJ)).getCode()));
        }
        if (map.containsKey(DATUM_EINDE_VOLGEN)) {
            final String datumwaarde = map.get(DATUM_EINDE_VOLGEN);
            final LocalDate localDate = DatumConstanten.getPredefinedLocalDateOrNull(datumwaarde);
            String peilmoment = datumwaarde;
            if (localDate != null) {
                peilmoment = localDate.format(DateTimeFormatter.ISO_DATE);
            }
            afnemerindicatie.setDatumEindeVolgen(peilmoment);
        }
        if (map.containsKey(DATUM_AANVANG_MATERIELE_PERIODE)) {
            final String datumwaarde = map.get(DATUM_AANVANG_MATERIELE_PERIODE);
            final LocalDate localDate = DatumConstanten.getPredefinedLocalDateOrNull(datumwaarde);
            String peilmoment = datumwaarde;
            if (localDate != null) {
                peilmoment = localDate.format(DateTimeFormatter.ISO_DATE);
            }
            afnemerindicatie.setDatumAanvangMaterielePeriode(peilmoment);
        }
        verzoek.setAfnemerindicatie(afnemerindicatie);
        verzoek.setDummyAfnemerCode(afnemerindicatie.getPartijCode());
        final RegistreerAfnemerindicatieCallbackImpl registreerAfnemerindicatieCallback = new RegistreerAfnemerindicatieCallbackImpl();
        onderhoudAfnemerindicatieService.onderhoudAfnemerindicatie(verzoek, registreerAfnemerindicatieCallback);
        verzoekService.bewaarVerzoekAntwoord(verzoek, registreerAfnemerindicatieCallback.getResultaat());
    }

    @Override
    public void verwijderAfnemerindicatie(final Map<String, String> map) {
        LOGGER.info("Verzoek: Verwijder Afnemerindicatie: " + map);
        final Set<String> keyset = Sets.newHashSet(map.keySet());
        keyset.removeAll(KEYS);
        keyset.removeAll(VerzoekService.GENERIEKE_KEYS);
        Assert.isTrue(keyset.isEmpty(), "Ongeldige keys: " + keyset);

        verzoekService.resetStubState();
        final AfnemerindicatieVerzoek verzoek = maakOnderhoudAfnemerindicatieVerzoek(map);
        verzoek.setSoortDienst(SoortDienst.VERWIJDERING_AFNEMERINDICATIE);
        final Afnemerindicatie afnemerindicatie = new Afnemerindicatie();
        afnemerindicatie.setBsn(map.get(BSN));
        afnemerindicatie.setPartijCode(verzoek.getStuurgegevens().getZendendePartijCode());
        verzoek.setAfnemerindicatie(afnemerindicatie);
        verzoek.setDummyAfnemerCode(verzoek.getStuurgegevens().getZendendePartijCode());

        final RegistreerAfnemerindicatieCallbackImpl registreerAfnemerindicatieCallback = new RegistreerAfnemerindicatieCallbackImpl();
        onderhoudAfnemerindicatieService.onderhoudAfnemerindicatie(verzoek, registreerAfnemerindicatieCallback);
        verzoekService.bewaarVerzoekAntwoord(verzoek, registreerAfnemerindicatieCallback.getResultaat());
    }

    @Override
    public void plaatsAfnemerindicatieMetXml(final XmlVerzoek xmlVerzoek) {
        verzoekService.xmlVerzoek(xmlVerzoek, webserviceProvider, SoortDienst.PLAATSING_AFNEMERINDICATIE);
    }

    @Override
    public void verwijderAfnemerindicatieMetXml(final XmlVerzoek xmlVerzoek) {
        verzoekService.xmlVerzoek(xmlVerzoek, webserviceProvider, SoortDienst.VERWIJDERING_AFNEMERINDICATIE);
    }

    private AfnemerindicatieVerzoek maakOnderhoudAfnemerindicatieVerzoek(final Map<String, String> map) {
        VerzoekService.assertMinimaleParametersAanwezig(map);
        final AfnemerindicatieVerzoek verzoek = new AfnemerindicatieVerzoek();
        VerzoekService.vulGeneriek(verzoek, map);
        final Integer leveringsautorisatieNaam = autorisatieService.getLeveringsautorisatie(map.get(VerzoekService.LEVERING_AUTORISATIE_NAAM)).getId();
        verzoek.getParameters().setLeveringsAutorisatieId(leveringsautorisatieNaam.toString());
        return verzoek;
    }
}
