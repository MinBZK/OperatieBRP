/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.basis;

import com.google.common.collect.Sets;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Set;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.Provider;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.service.algemeen.request.OIN;
import nl.bzk.brp.service.algemeen.request.Verzoek;
import nl.bzk.brp.tooling.apitest.autorisatie.Partijen;
import nl.bzk.brp.tooling.apitest.dto.XmlVerzoek;
import org.springframework.util.Assert;

/**
 * Stub interface voor verzoek functionaliteit.
 */
public interface VerzoekService extends Stateful {

    /**
     * leveringsautorisatieId param.
     */
    String LEVERING_AUTORISATIE_ID = "leveringsautorisatieId";
    /**
     * zendendePartijNaam param.
     */
    String ZENDENDE_PARTIJ_NAAM = "zendendePartijNaam";
    /**
     * zendendePartijCode param.
     */
    String ZENDENDE_PARTIJ_CODE = "zendendePartijCode";
    /**
     * zendendeSysteem param.
     */
    String ZENDENDE_SYSTEEM = "zendendeSysteem";
    /**
     * leveringsautorisatieNaam param.
     */
    String LEVERING_AUTORISATIE_NAAM = "leveringsautorisatieNaam";
    /**
     * transporteur param.
     */
    String TRANSPORTEUR = "transporteur";
    /**
     * ondertekenaar param.
     */
    String ONDERTEKENAAR = "ondertekenaar";

    /**
     * Set generieke keys.
     */
    Set<String> GENERIEKE_KEYS = Sets
            .newHashSet(ZENDENDE_PARTIJ_NAAM, ZENDENDE_PARTIJ_CODE, LEVERING_AUTORISATIE_ID, LEVERING_AUTORISATIE_NAAM, TRANSPORTEUR, ONDERTEKENAAR);

    /**
     * Assert dat de minimale parameters aanwezig zijn in het technische verzoek.
     * @param map map met parameters.
     */
    static void assertMinimaleParametersAanwezig(Map<String, String> map) {
        Assert.notNull(map.get(LEVERING_AUTORISATIE_NAAM), "parameter 'leveringsautorisatieNaam' ontbreekt");
    }

    /**
     * Vult de generieke parameters van het verzoek.
     * @param verzoek het verzoek
     * @param map map met parametera
     */
    static void vulGeneriek(Verzoek verzoek, Map<String, String> map) {
        String oinOndertekenaar = null;
        if (map.containsKey(ONDERTEKENAAR)) {
            final Partij partij = Partijen.getPartij(map.get(ONDERTEKENAAR));
            oinOndertekenaar = partij.getOin();
        }
        String oinTransporteur = null;
        if (map.containsKey(TRANSPORTEUR)) {
            final Partij partij = Partijen.getPartij(map.get(TRANSPORTEUR));
            oinTransporteur = partij.getOin();
        }

        verzoek.setOin(new OIN(oinOndertekenaar, oinTransporteur));

        final String zendendePartijCode;
        if (map.containsKey(ZENDENDE_PARTIJ_CODE)) {
            //nuttig voor het opgeven van een foutieve zendende partij
            zendendePartijCode = map.get(ZENDENDE_PARTIJ_CODE);
        } else {
            Assert.notNull(map.get(ZENDENDE_PARTIJ_NAAM), "parameter 'zendendePartijNaam' ontbreekt");
            final Partij zendendePartij = Partijen.getPartij(map.get(ZENDENDE_PARTIJ_NAAM));
            zendendePartijCode = String.valueOf(zendendePartij.getCode());
        }
        verzoek.getStuurgegevens().setZendendePartijCode(zendendePartijCode);
        verzoek.getStuurgegevens().setCommunicatieId("comid.stuurgegevens");
        verzoek.getStuurgegevens().setZendendSysteem(map.get(ZENDENDE_SYSTEEM) == null ? "AFNEMERSYSTEEM" : map.get(ZENDENDE_SYSTEEM));
        verzoek.getStuurgegevens().setReferentieNummer("00000000-0000-0000-0000-000000001214");
        verzoek.getStuurgegevens().setTijdstipVerzending(ZonedDateTime.now());
    }

    /**
     * @return het antwoordbericht van het laatst geplaatste verzoek.
     */
    String getLaatsteAntwoordbericht();

    /**
     * @return het laatste verzoek
     */
    Verzoek getLaatsteVerzoek();

    /**
     * Bewaart het resultaat van het gegeven verzoek. Op dit resultaat kunnen vervolgens asserts gedaan worden.
     * @param verzoek het ingeschoten verzoek
     * @param antwoord het XML antwoordbericht op het verzoek.
     */
    void bewaarVerzoekAntwoord(Verzoek verzoek, String antwoord);

    /**
     * Reset de bewaarde state in de stubs (ontvangen berichten, laatst bekeken bericht etc).
     */
    void resetStubState();

    /**
     * @param verzoekFile het xml verzoek
     * @param webserviceProvider ws provider
     * @param soortDienst soort dienst
     */
    void xmlVerzoek(XmlVerzoek verzoekFile, Provider<DOMSource> webserviceProvider, SoortDienst soortDienst);
}
