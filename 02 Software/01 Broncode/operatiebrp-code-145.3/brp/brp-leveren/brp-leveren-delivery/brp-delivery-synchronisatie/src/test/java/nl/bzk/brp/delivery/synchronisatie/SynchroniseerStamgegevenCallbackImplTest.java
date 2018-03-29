/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.synchronisatie;

import static java.util.Arrays.asList;

import com.google.common.collect.ImmutableMap;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.StamgegevenTabel;
import nl.bzk.brp.domain.algemeen.StamtabelGegevens;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtStamgegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.SynchroniseerStamgegevenBericht;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.service.synchronisatie.stamgegeven.SynchroniseerStamgegevenCallback;
import org.junit.Assert;
import org.junit.Test;

public class SynchroniseerStamgegevenCallbackImplTest {

    @Test
    public void verwerkResultaat() throws Exception {
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens =
            BasisBerichtGegevens.builder()
                .metStuurgegevens()
                .metCrossReferentienummer("crossReferentienummer")
                .metReferentienummer("referentienummer")
                .metTijdstipVerzending(ZonedDateTime.of(2016, 10, 5, 6, 42, 0, 0, ZoneId.of("UTC")))
                .metZendendePartij(TestPartijBuilder.maakBuilder().metCode("000123").build())
                .eindeStuurgegevens()
                .metAdministratieveHandelingId(1L)
                .metResultaat(BerichtVerwerkingsResultaat.builder()
                    .metVerwerking(VerwerkingsResultaat.GESLAAGD)
                    .metHoogsteMeldingsniveau("Waarschuwing").build())
                .metMeldingen(Collections.singletonList(new Melding(Regel.R1321)))
                .metPartijCode("000123")
                .metTijdstipRegistratie(ZonedDateTime.of(2016, 10, 5, 6, 42, 0, 0, ZoneId.of("UTC")))
                .build();

        final BerichtStamgegevens berichtStamgegevens = new BerichtStamgegevens(
            new StamtabelGegevens(new StamgegevenTabel(
                ElementHelper.getObjectElement(Element.PERSOON_ADRES),
                Collections.singletonList(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE)),
                Collections.singletonList(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE))
            ), asList(
                ImmutableMap.<String, Object>builder().put("gem", "Amsterdam").build(),
                ImmutableMap.<String, Object>builder().put("gem", "Rotterdam").build(),
                ImmutableMap.<String, Object>builder().put("gem", "Utrecht").build()
            ))
        );

        final SynchroniseerStamgegevenBericht bericht = new SynchroniseerStamgegevenBericht(basisBerichtGegevens, berichtStamgegevens);
        //@formatter:on

        final SynchroniseerStamgegevenCallback<String> synchroniseerStamgegevenCallback = new SynchroniseerStamgegevenCallbackImpl();
        synchroniseerStamgegevenCallback.verwerkResultaat(bericht);
        final String xml = synchroniseerStamgegevenCallback.getResultaat();
        System.out.println(xml);
        String expected = "<?xml version='1.0' encoding='UTF-8'?><brp:lvg_synGeefSynchronisatieStamgegeven_R xmlns:brp=\"http://www.bzk"
                + ".nl/brp/brp0200\"><brp:stuurgegevens><brp:zendendePartij>000123</brp:zendendePartij><brp:zendendeSysteem>BRP</brp:zendendeSysteem>"
                + "<brp:referentienummer>referentienummer</brp:referentienummer><brp:crossReferentienummer>crossReferentienummer</brp:crossReferentienummer>"
                + "<brp:tijdstipVerzending>2016-10-05T06:42:00.000Z</brp:tijdstipVerzending></brp:stuurgegevens><brp:resultaat>"
                + "<brp:verwerking>Geslaagd</brp:verwerking><brp:hoogsteMeldingsniveau>Waarschuwing</brp:hoogsteMeldingsniveau></brp:resultaat><brp:meldingen>"
                + "<brp:melding brp:objecttype=\"Melding\" brp:referentieID=\"0\"><brp:regelCode>R1321</brp:regelCode>"
                + "<brp:soortNaam>Waarschuwing</brp:soortNaam><brp:melding>De persoon heeft een openstaande terugmelding</brp:melding></brp:melding>"
                + "</brp:meldingen><brp:stamgegevens><brp:persoonAdresTabel><brp:persoonAdres brp:objecttype=\"PersoonAdres\">"
                + "<brp:gemeenteCode>Amsterdam</brp:gemeenteCode></brp:persoonAdres><brp:persoonAdres brp:objecttype=\"PersoonAdres\">"
                + "<brp:gemeenteCode>Rotterdam</brp:gemeenteCode></brp:persoonAdres><brp:persoonAdres "
                + "brp:objecttype=\"PersoonAdres\"><brp:gemeenteCode>Utrecht</brp:gemeenteCode></brp:persoonAdres></brp:persoonAdresTabel></brp:stamgegevens"
                + "></brp:lvg_synGeefSynchronisatieStamgegeven_R>";

        Assert.assertEquals(expected, xml);
    }

}
