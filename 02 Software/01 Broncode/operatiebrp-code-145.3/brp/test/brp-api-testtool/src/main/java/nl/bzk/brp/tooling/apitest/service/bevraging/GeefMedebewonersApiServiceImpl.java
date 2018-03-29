/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.bevraging;

import com.google.common.collect.Sets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.delivery.bevraging.BevragingCallbackImpl;
import nl.bzk.brp.delivery.bevraging.BevragingWebService;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoekVerwerker;
import nl.bzk.brp.service.bevraging.geefmedebewoners.GeefMedebewonersVerzoek;
import nl.bzk.brp.test.common.dsl.DatumConstanten;
import nl.bzk.brp.tooling.apitest.dto.XmlVerzoek;
import nl.bzk.brp.tooling.apitest.service.basis.VerzoekService;
import nl.bzk.brp.tooling.apitest.service.dataaccess.AutorisatieStubService;
import org.springframework.stereotype.Component;

/**
 * Implementatie van {@link BevragingApiService.GeefMedebewonersApiService}.
 */
@Component
final class GeefMedebewonersApiServiceImpl implements BevragingApiService.GeefMedebewonersApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private VerzoekService verzoekService;
    @Inject
    @Named("maakLeveringsautorisatieRepository")
    private AutorisatieStubService autorisatieService;
    @Inject
    @Named("geefMedebewoners")
    private BevragingVerzoekVerwerker<GeefMedebewonersVerzoek> geefMedebewonersVerwerker;
    @Inject
    private BevragingWebService webserviceProvider;

    @Override
    public void verzoek(final Map<String, String> map) {
        LOGGER.info("Verzoek: Geef Medebewoners: " + map);

        final Set<String> keyset = Sets.newHashSet(map.keySet());
        keyset.removeAll(KEYS_GEEF_MEDEBEWONERS);
        BevragingApiService.assertGeenOnbekendeKeys(keyset);

        verzoekService.resetStubState();
        final GeefMedebewonersVerzoek verzoek = new GeefMedebewonersVerzoek();
        verzoek.setSoortDienst(SoortDienst.GEEF_MEDEBEWONERS_VAN_PERSOON);
        VerzoekService.vulGeneriek(verzoek, map);
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
            //default zoek naar de dienst in alle dienstbundels
            final Leveringsautorisatie leveringsautorisatie = autorisatieService
                .getLeveringsautorisatie(map.get(VerzoekService.LEVERING_AUTORISATIE_NAAM));
            dienstbundelLoop:
            for (final Dienstbundel dienstbundel : leveringsautorisatie.getDienstbundelSet()) {
                for (final Dienst dienst : dienstbundel.getDienstSet()) {
                    if (dienst.getSoortDienst() == SoortDienst.GEEF_MEDEBEWONERS_VAN_PERSOON) {
                        dienstId = dienst.getId().toString();
                        break dienstbundelLoop;
                    }
                }
            }
        }
        BevragingApiService.assertDienstGevuld(dienstId);
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


        final GeefMedebewonersVerzoek.Identificatiecriteria identificatiecriteria = verzoek.getIdentificatiecriteria();

        final String burgerservicenummer = map.get(BURGERSERVICENUMMER);
        if (burgerservicenummer != null) {
            identificatiecriteria.setBurgerservicenummer(burgerservicenummer);
        }
        final String administratienummer = map.get(ADMINISTRATIENUMMER);
        if (administratienummer != null) {
            identificatiecriteria.setAdministratienummer(administratienummer);
        }
        final String objectSleutel = map.get(OBJECT_SLEUTEL);
        if (objectSleutel != null) {
            identificatiecriteria.setObjectSleutel(objectSleutel);
        }
        final String gemeenteCode = map.get(GEMEENTE_CODE);
        if (gemeenteCode != null) {
            identificatiecriteria.setGemeenteCode(gemeenteCode);
        }
        final String afgekorteNaamOpenbareRuimte = map.get(AFGEKORTE_NAAM_OPENBARE_RUIMTE);
        if (afgekorteNaamOpenbareRuimte != null) {
            identificatiecriteria.setAfgekorteNaamOpenbareRuimte(afgekorteNaamOpenbareRuimte);
        }
        final String huisnummer = map.get(HUISNUMMER);
        if (huisnummer != null) {
            identificatiecriteria.setHuisnummer(huisnummer);
        }
        final String huisletter = map.get(HUISLETTER);
        if (huisletter != null) {
            identificatiecriteria.setHuisletter(huisletter);
        }
        final String huisnummertoevoeging = map.get(HUISNUMMERTOEVOEGING);
        if (huisnummertoevoeging != null) {
            identificatiecriteria.setHuisnummertoevoeging(huisnummertoevoeging);
        }
        final String locatieTenOpzichteVanAdres = map.get(LOCATIE_TEN_OPZICHTE_VAN_ADRES);
        if (locatieTenOpzichteVanAdres != null) {
            identificatiecriteria.setLocatieTenOpzichteVanAdres(locatieTenOpzichteVanAdres);
        }
        final String postcode = map.get(POSTCODE);
        if (postcode != null) {
            identificatiecriteria.setPostcode(postcode);
        }
        final String woonplaatsnaam = map.get(WOONPLAATSNAAM);
        if (woonplaatsnaam != null) {
            identificatiecriteria.setWoonplaatsnaam(woonplaatsnaam);
        }
        final String identificatiecodeNummeraanduiding = map.get(IDENTIFICATIECODE_NUMMERAANDUIDING);
        if (identificatiecodeNummeraanduiding != null) {
            identificatiecriteria.setIdentificatiecodeNummeraanduiding(identificatiecodeNummeraanduiding);
        }
        final String identificatiecodeAdresseerbaarObject = map.get(IDENTIFICATIECODE_ADRESSEERBAAR_OBJECT);
        if (identificatiecodeAdresseerbaarObject != null) {
            identificatiecriteria.setIdentificatiecodeAdresseerbaarObject(identificatiecodeAdresseerbaarObject);
        }

        final BevragingCallbackImpl bevragingCallback = new BevragingCallbackImpl();
        geefMedebewonersVerwerker.verwerk(verzoek, bevragingCallback);
        verzoekService.bewaarVerzoekAntwoord(verzoek, bevragingCallback.getResultaat());
    }

    @Override
    public void verzoekMetXml(final XmlVerzoek xmlVerzoek) {
        verzoekService.xmlVerzoek(xmlVerzoek, webserviceProvider, SoortDienst.GEEF_MEDEBEWONERS_VAN_PERSOON);
    }


}
