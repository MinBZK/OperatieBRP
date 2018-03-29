/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.bevraging;

import com.google.common.collect.Sets;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.delivery.bevraging.BevragingCallbackImpl;
import nl.bzk.brp.delivery.bevraging.BevragingWebService;
import nl.bzk.brp.domain.algemeen.DatumFormatterUtil;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoekVerwerker;
import nl.bzk.brp.service.bevraging.detailspersoon.GeefDetailsPersoonVerzoek;
import nl.bzk.brp.test.common.dsl.DatumConstanten;
import nl.bzk.brp.tooling.apitest.dto.XmlVerzoek;
import nl.bzk.brp.tooling.apitest.service.basis.VerzoekService;
import nl.bzk.brp.tooling.apitest.service.dataaccess.AutorisatieStubService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Implementatie van {@link BevragingApiService.GeefDetailsPersoonApiService}.
 */
@Component
final class GeefDetailsPersoonApiServiceImpl implements BevragingApiService.GeefDetailsPersoonApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private VerzoekService verzoekService;
    @Inject
    @Named("maakLeveringsautorisatieRepository")
    private AutorisatieStubService autorisatieService;
    @Inject
    @Named("geefDetailsPersoonVerwerker")
    private BevragingVerzoekVerwerker<GeefDetailsPersoonVerzoek> geefDetailsPersoonVerwerker;
    @Inject
    private BevragingWebService webserviceProvider;

    @Override
    public void verzoek(final Map<String, String> map) {
        LOGGER.info("Verzoek: Geef Details Persoon: " + map);

        final Set<String> keyset = Sets.newHashSet(map.keySet());
        keyset.removeAll(KEYS_GEEF_DETAILS);
        BevragingApiService.assertGeenOnbekendeKeys(keyset);

        verzoekService.resetStubState();
        final GeefDetailsPersoonVerzoek verzoek = new GeefDetailsPersoonVerzoek();
        verzoek.setSoortDienst(SoortDienst.GEEF_DETAILS_PERSOON);
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
                    if (dienst.getSoortDienst() == SoortDienst.GEEF_DETAILS_PERSOON) {
                        dienstId = dienst.getId().toString();
                        break dienstbundelLoop;
                    }
                }
            }
        }
        if (map.containsKey(BevragingKeys.HISTORIEVORM)) {
            verzoek.getParameters().setHistorieVorm(HistorieVorm.getByNaam(map.get(BevragingKeys.HISTORIEVORM)));
        }
        if (map.containsKey(BevragingKeys.GEEF_DETAILS_PEILMOMENT_MATERIEEL_RESULTAAT)) {
            final String datumwaarde = map.get(BevragingKeys.GEEF_DETAILS_PEILMOMENT_MATERIEEL_RESULTAAT);
            final LocalDate localDate = DatumConstanten.getPredefinedLocalDateOrNull(datumwaarde);
            String peilmoment = datumwaarde;
            if (localDate != null) {
                peilmoment = localDate.format(DateTimeFormatter.ISO_DATE);
            }
            verzoek.getParameters().setPeilMomentMaterieelResultaat(peilmoment);
        }
        if (map.containsKey(BevragingKeys.GEEF_DETAILS_PEILMOMENT_FORMEEL_RESULTAAT)) {
            final String datumwaarde = map.get(BevragingKeys.GEEF_DETAILS_PEILMOMENT_FORMEEL_RESULTAAT);
            String peilmoment = datumwaarde;
            try {
                final ZonedDateTime zonedDateTime = DatumConstanten.getDateTime(datumwaarde);
                if (zonedDateTime != null) {
                    peilmoment = DatumFormatterUtil.vanZonedDateTimeNaarXsdDateTime(zonedDateTime);
                }
            } catch (DateTimeException dte) {
                //kan voorkomen bij invalide datum bv 30 feb > gaat de parser op stuk, maar willen we wel als parameter mee kunnen geven//TODO ROOD-1942 kan
                // hele
                // formatter stap hier net weg en direct datumWaarde+tijdzone bepaling gedaan worden ?

                peilmoment = datumwaarde + "Z";
            }
            verzoek.getParameters().setPeilMomentFormeelResultaat(peilmoment);
        }
        if (map.containsKey(BevragingKeys.SCOPING_ELEMENTEN)) {
            final String[] elementen = StringUtils.split(map.get(BevragingKeys.SCOPING_ELEMENTEN), ",");
            for (final String element : elementen) {
                verzoek.getScopingElementen().getElementen().add(element);
            }
        }
        if (map.containsKey(BevragingKeys.VERANTWOORDING)) {
            final String verantwoording = map.get(BevragingKeys.VERANTWOORDING);
            Assert.isTrue("Geen".equals(verantwoording), "jekuntalleengeenopgevenhier");
            verzoek.getParameters().setVerantwoording(verantwoording);
        }

        BevragingApiService.assertDienstGevuld(dienstId);
        verzoek.getParameters().setDienstIdentificatie(dienstId);

        if (map.containsKey(BevragingKeys.BSN)) {
            verzoek.getIdentificatiecriteria().setBsn(map.get(BevragingKeys.BSN));
        }

        if (map.containsKey(BevragingKeys.ANR)) {
            verzoek.getIdentificatiecriteria().setAnr(map.get(BevragingKeys.ANR));
        }

        if (map.containsKey(BevragingKeys.OBJECT_SLEUTEL)) {
            verzoek.getIdentificatiecriteria().setObjectSleutel(map.get(BevragingKeys.OBJECT_SLEUTEL));
        }
        final BevragingCallbackImpl bevragingCallback = new BevragingCallbackImpl();
        geefDetailsPersoonVerwerker.verwerk(verzoek, bevragingCallback);
        verzoekService.bewaarVerzoekAntwoord(verzoek, bevragingCallback.getResultaat());
    }

    @Override
    public void verzoekMetXml(final XmlVerzoek xmlVerzoek) {
        verzoekService.xmlVerzoek(xmlVerzoek, webserviceProvider, SoortDienst.GEEF_DETAILS_PERSOON);
    }

}
