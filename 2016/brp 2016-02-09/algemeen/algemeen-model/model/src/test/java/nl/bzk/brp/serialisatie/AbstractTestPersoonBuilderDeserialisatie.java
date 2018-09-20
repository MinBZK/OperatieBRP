/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Random;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieConversieGBABericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.serialisatie.persoon.PersoonHisVolledigStringSerializer;
import nl.bzk.brp.util.StamgegevenBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.datumedge.hamcrest.json.SameJSONAs;


public abstract class AbstractTestPersoonBuilderDeserialisatie {
    protected final PersoonHisVolledigImpl persoon;

    private static final Logger LOGGER = LoggerFactory.getLogger();

    protected final JacksonJsonSerializer<PersoonHisVolledigImpl> serializer = new PersoonHisVolledigStringSerializer();

    public AbstractTestPersoonBuilderDeserialisatie() {
        this.persoon = maakPersoon();
    }

    @Test
    public void roundtripLevertDezelfdeJson() throws IOException {
        // given
        final byte[] heenJson = serializer.serialiseer(persoon);
        String heenJsonString = new String(heenJson);
        LOGGER.info("{}", heenJsonString);

        // when
        final PersoonHisVolledigImpl terugPersoon = serializer.deserialiseer(heenJson);
        final byte[] terugJson = serializer.serialiseer(terugPersoon);
        String terugJsonString = new String(terugJson);
        LOGGER.info("{}", terugJsonString);

        // then
        assertThat(persoon.getID() + " doet JSON roundtrip", heenJsonString, SameJSONAs.sameJSONAs(terugJsonString).allowingAnyArrayOrdering());
    }

    @Test
    public void roundtripLevertDezelfdeObjecten() throws IOException {
        // when
        final byte[] json = serializer.serialiseer(persoon);
        LOGGER.info("{}", new String(json));

        final PersoonHisVolledigImpl terugPersoon = serializer.deserialiseer(json);

        // then
        valideerObjecten(persoon, terugPersoon);
    }

    /**
     * Test moet een persoon aanmaken.
     *
     * @return een persoon his volledig
     */
    protected abstract PersoonHisVolledigImpl maakPersoon();

    protected void valideerObjecten(PersoonHisVolledigImpl persoon, PersoonHisVolledigImpl terugPersoon) {
        final RelatieHisVolledig relatie = terugPersoon.getBetrokkenheden().iterator().next().getRelatie();
        assertThat("relatie -> betrokkenheid heeft backreference", relatie.getBetrokkenheden().iterator().next()
            .getRelatie(), is(relatie));

        for (BetrokkenheidHisVolledig betrokkenheid : relatie.getBetrokkenheden()) {
            assertThat("relatie -> persoon referentie bestaat",
                betrokkenheid.getPersoon(), notNullValue());
        }

        // TODO RNE waarom zou hier geen terugreferentie mogen zijn?
//        assertThat("persoon -> betrokkenheid heeft GEEN backreference",
//            terugPersoon.getBetrokkenheden().iterator().next().getPersoon(), allOf(notNullValue(), is(terugPersoon)));
    }

    protected PersoonHisVolledigImpl maakBasisPersoon(final int persoonId) {
        final PersoonHisVolledigImpl persoon =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                        .nieuwGeboorteRecord(maakActie())
                        .datumGeboorte(20110101)
                        .landGebiedGeboorte((short) 6030)
                        .eindeRecord()
                        .nieuwNaamgebruikRecord(maakActie())
                        .adellijkeTitelNaamgebruik("Koning")
                        .eindeRecord()
                        .voegPersoonAdresToe(
                                new PersoonAdresHisVolledigImplBuilder().nieuwStandaardRecord(maakActie())
                                        .aangeverAdreshouding("P").afgekorteNaamOpenbareRuimte("kort")
                                        .buitenlandsAdresRegel3("buitenlandje")
                                        .locatieTenOpzichteVanAdres(LocatieTenOpzichteVanAdres.TO).eindeRecord()
                                        .build())
                        .voegPersoonNationaliteitToe(
                                new PersoonNationaliteitHisVolledigImplBuilder(
                                        StamgegevenBuilder.bouwDynamischStamgegeven(
                                                Nationaliteit.class, 6030)).nieuwStandaardRecord(maakActie())
                                        .redenVerkrijging((short) 321).redenVerlies((short) 4763).eindeRecord().build())
                        .voegPersoonIndicatieDerdeHeeftGezagToe(
                                new PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder()
                                        .nieuwStandaardRecord(maakActie()).waarde(Ja.J).eindeRecord().build())
                        .voegPersoonIndicatieVolledigeVerstrekkingsbeperkingToe(
                                new PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder()
                                        .nieuwStandaardRecord(maakActie()).waarde(Ja.J).eindeRecord().build()).build();
        ReflectionTestUtils.setField(persoon, "iD", persoonId);
        return persoon;
    }

    protected ActieModel maakActie() {
        final ActieBericht actieBericht = new ActieConversieGBABericht();
        actieBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20110101));
        actieBericht.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20110101));
        actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(20110101).toDate()));
        final ActieModel actieModel = new ActieModel(actieBericht, null);
        ReflectionTestUtils.setField(actieModel, "iD", new Random().nextLong());
        return actieModel;
    }

    protected Dienst maakDienst() {
        return TestDienstBuilder.maker().metSoortDienst(SoortDienst.PLAATSEN_AFNEMERINDICATIE).maak();
    }
}
