/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.afnemerindicatie;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Set;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Test;
import uk.co.datumedge.hamcrest.json.SameJSONAs;


/**
 *
 */
public class AfnemerIndicatieSerializerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private AfnemerIndicatieStringSerializer serializer = new AfnemerIndicatieStringSerializer();

    @Test
    public void roundtripLevertDezelfdeJson() {
        // arrange
        Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerindicaties = maakAfnemerIndicaties();

        // act
        byte[] data = serializer.serialiseer(afnemerindicaties);
        LOGGER.info("json: {}", new String(data));

        Set<PersoonAfnemerindicatieHisVolledigImpl> terugIndicaties = serializer.deserialiseer(data);
        byte[] terugData = serializer.serialiseer(terugIndicaties);

        // assert
        assertThat(new String(data), not(equalTo("{}")));
        assertThat(new String(data), SameJSONAs.sameJSONAs(new String(terugData)).allowingAnyArrayOrdering());
    }

    @Test
    public void roundtripLevertDezelfdeObjecten() {
        // arrange
        Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerindicaties = maakAfnemerIndicaties();

        // act
        byte[] data = serializer.serialiseer(afnemerindicaties);

        Set<PersoonAfnemerindicatieHisVolledigImpl> terugIndicaties = serializer.deserialiseer(data);

        // assert
        assertThat(afnemerindicaties.size(), equalTo(terugIndicaties.size()));

        PersoonAfnemerindicatieHisVolledigImpl afnemerindicatie = terugIndicaties.iterator().next();
        assertThat(afnemerindicatie.getAfnemer().getWaarde().getCode().getWaarde(), is(758));
        assertThat(afnemerindicatie.getAfnemer().getWaarde().getNaam(), is(nullValue()));
        assertThat(afnemerindicatie.getLeveringsautorisatie().getWaarde().getID(), is(notNullValue()));
        assertThat(afnemerindicatie.getPersoonAfnemerindicatieHistorie().getAantal(), is(1));
    }

    private Set<PersoonAfnemerindicatieHisVolledigImpl> maakAfnemerIndicaties() {
        final Partij partij = StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde();
        final Leveringsautorisatie abonnement = TestLeveringsautorisatieBuilder.maker().metNaam("Test Abo").metPopulatiebeperking("WAAR")
            .metProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN)
            .metDatumIngang(DatumAttribuut.gisteren())
            .maak();

        final PersoonHisVolledig persoon =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                        .voegPersoonAfnemerindicatieToe(
                                new PersoonAfnemerindicatieHisVolledigImplBuilder(partij, abonnement)
                                        .nieuwStandaardRecord(maakDienst()).datumAanvangMaterielePeriode(20100101)
                                        .eindeRecord(1L).build())
                        .voegPersoonAfnemerindicatieToe(
                                new PersoonAfnemerindicatieHisVolledigImplBuilder(partij, abonnement)
                                        .nieuwStandaardRecord(maakDienst()).datumAanvangMaterielePeriode(20130131)
                                        .datumEindeVolgen(DatumTijdAttribuut.bouwDatumTijd(2016, 1, 1).naarDatum())
                                        .eindeRecord(2L).build()).build();

        return (Set<PersoonAfnemerindicatieHisVolledigImpl>) persoon.getAfnemerindicaties();
    }

    private Dienst maakDienst() {
        return TestDienstBuilder.maker().metSoortDienst(SoortDienst.PLAATSEN_AFNEMERINDICATIE).maak();
    }
}
