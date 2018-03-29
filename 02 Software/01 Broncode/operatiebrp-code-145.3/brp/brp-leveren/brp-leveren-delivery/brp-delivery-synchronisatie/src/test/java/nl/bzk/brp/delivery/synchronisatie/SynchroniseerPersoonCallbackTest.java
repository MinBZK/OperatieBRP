/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.synchronisatie;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.SynchroniseerPersoonAntwoordBericht;
import org.junit.Test;

/**
 * Unit test voor {@link SynchroniseerPersoonCallbackImpl}.
 */
public class SynchroniseerPersoonCallbackTest {

    @Test
    public void verwerkResultaat() throws Exception {
        final SynchroniseerPersoonCallbackImpl callback = new SynchroniseerPersoonCallbackImpl();

        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder().metPartijCode
                ("000001").metStuurgegevens().metCrossReferentienummer("referentienummer").metReferentienummer("referentienummer.antwoord")
                .metTijdstipVerzending(ZonedDateTime
                        .of(2016, 10, 3, 16, 12, 59, 0, ZoneId.of("UTC"))).metZendendePartij(
                        TestPartijBuilder.maakBuilder().metCode("000123").build())
                .eindeStuurgegevens().metMeldingen(Collections.singletonList(new Melding(Regel.ALG0001)))
                .metResultaat(
                        BerichtVerwerkingsResultaat.builder().metHoogsteMeldingsniveau(SoortMelding.FOUT.getNaam()).metVerwerking(
                                VerwerkingsResultaat
                                        .FOUTIEF).build())
                .build();
        SynchroniseerPersoonAntwoordBericht bericht = new SynchroniseerPersoonAntwoordBericht(basisBerichtGegevens);
        callback.verwerkResultaat(bericht);
        System.out.println(callback.getResultaat());
        assertThat(callback.getResultaat(), is("<?xml version='1.0' encoding='UTF-8'?><brp:lvg_synGeefSynchronisatiePersoon_R xmlns:brp=\"http://www.bzk"
                + ".nl/brp/brp0200\"><brp:stuurgegevens><brp:zendendePartij>000123</brp:zendendePartij><brp:zendendeSysteem>BRP</brp:zendendeSysteem><brp"
                + ":referentienummer>referentienummer.antwoord</brp:referentienummer><brp:crossReferentienummer>referentienummer</brp:crossReferentienummer"
                + "><brp:tijdstipVerzending>2016-10-03T16:12:59.000Z</brp:tijdstipVerzending></brp:stuurgegevens><brp:resultaat><brp:verwerking>Foutief</brp"
                + ":verwerking><brp:hoogsteMeldingsniveau>Fout</brp:hoogsteMeldingsniveau></brp:resultaat><brp:meldingen><brp:melding "
                + "brp:objecttype=\"Melding\" brp:referentieID=\"0\"><brp:regelCode>ALG0001</brp:regelCode><brp:soortNaam>Fout</brp:soortNaam><brp:melding"
                + ">Algemene fout opgetreden.</brp:melding></brp:melding></brp:meldingen></brp:lvg_synGeefSynchronisatiePersoon_R>"));
    }

}
