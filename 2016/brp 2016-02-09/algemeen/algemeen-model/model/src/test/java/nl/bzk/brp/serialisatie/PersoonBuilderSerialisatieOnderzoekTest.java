/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartijOnderzoek;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonOnderzoek;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusOnderzoek;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestElementBuilder;
import nl.bzk.brp.model.basis.IdComparator;
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.serialisatie.persoon.PersoonHisVolledigStringSerializer;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.GegevenInOnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PartijOnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonOnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonAntwoordPersoon;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * JSON serialisatie test met onderzoek in de BLOB.
 */
public class PersoonBuilderSerialisatieOnderzoekTest {

    private static final Logger LOGGER    = LoggerFactory.getLogger();
    private static final String ONDERZOEK = "onderzoek";
    private static final String JSON      = "json: \n{}";
    private static final String ID        = "iD";

    private final JacksonJsonSerializer<PersoonHisVolledigImpl> serializer = new PersoonHisVolledigStringSerializer();

    @BeforeClass
    public static void beforeClass() {
        XMLUnit.setIgnoreWhitespace(true);
    }

    @Test
    public void testSerialisatiePersoonMetOnderzoekBasaal() {
        final PersoonHisVolledigImpl piet = TestPersoonAntwoordPersoon.maakAntwoordPersoon();
        ReflectionTestUtils.setField(piet, ID, 333);

        final ActieModel actieOnderzoek =
            maakActieModel(3L, SoortActie.REGISTRATIE_ONDERZOEK, SoortAdministratieveHandeling.AANVANG_ONDERZOEK, new DatumAttribuut(20130901).toDate());

        final OnderzoekHisVolledigImpl onderzoek = voegOnderzoekToeAan(piet, actieOnderzoek);
        ReflectionTestUtils.setField(onderzoek, ID, 2348);

        final byte[] data = serializer.serialiseer(piet);
        final String json = new String(data);

        LOGGER.info(JSON, json);

        final PersoonHisVolledigImpl terugPersoon = serializer.deserialiseer(json.getBytes());

        final PersoonOnderzoekHisVolledigImpl heenOnderzoek = piet.getOnderzoeken().iterator().next();
        final PersoonOnderzoekHisVolledigImpl terugOnderzoek = terugPersoon.getOnderzoeken().iterator().next();

        Assert.assertEquals(heenOnderzoek.getOnderzoek().getID(), terugOnderzoek.getOnderzoek().getID());
//        Assert.assertEquals(heenOnderzoek.getOnderzoek().getPersonenInOnderzoek().size(), terugOnderzoek.getOnderzoek().getPersonenInOnderzoek().size());

        for (final PersoonOnderzoekHisVolledigImpl persoonOnderzoek : terugOnderzoek.getOnderzoek().getPersonenInOnderzoek()) {
            Assert.assertNotNull("Persoon leeg in " + persoonOnderzoek.getID(), persoonOnderzoek.getPersoon());
        }
    }

    //TODO!!
    @Test
    @Ignore
    public void testSerialisatiePersoonMetOnderzoek() {
        final PersoonHisVolledigImpl piet = TestPersoonAntwoordPersoon.maakAntwoordPersoon();
        ReflectionTestUtils.setField(piet, ID, 333);

        final ActieModel actieOnderzoek =
            maakActieModel(3L, SoortActie.REGISTRATIE_ONDERZOEK, SoortAdministratieveHandeling.AANVANG_ONDERZOEK, new DatumAttribuut(20130901).toDate());

//        assertTrue(false); //even opzettelijk laten falen...
        final OnderzoekHisVolledigImpl onderzoek = voegOnderzoekToeAan(piet, actieOnderzoek);
        ReflectionTestUtils.setField(onderzoek, ID, 2348);

        final byte[] data = serializer.serialiseer(piet);
        final String json = new String(data);

        LOGGER.info(JSON, json);

        final PersoonHisVolledigImpl terugPersoon = serializer.deserialiseer(data);

        final PersoonOnderzoekHisVolledigImpl heenOnderzoek = piet.getOnderzoeken().iterator().next();
        final PersoonOnderzoekHisVolledigImpl terugOnderzoek = terugPersoon.getOnderzoeken().iterator().next();

        Assert.assertEquals(heenOnderzoek.getOnderzoek().getID(), terugOnderzoek.getOnderzoek().getID());
        Assert.assertEquals(heenOnderzoek.getOnderzoek().getPersonenInOnderzoek().size(), terugOnderzoek.getOnderzoek().getPersonenInOnderzoek().size());

        for (final PersoonOnderzoekHisVolledigImpl persoonOnderzoek : terugOnderzoek.getOnderzoek().getPersonenInOnderzoek()) {
            Assert.assertNotNull("Persoon leeg in " + persoonOnderzoek.getID(), persoonOnderzoek.getPersoon());
        }
    }

    //TODO!!
    @Test
    @Ignore
    public void serialiseerJohnnyJordaanMetOnderzoek() {
        final PersoonHisVolledigImpl johnny = TestPersoonJohnnyJordaan.maak();

        final ActieModel actieOnderzoek =
            maakActieModel(2L, SoortActie.REGISTRATIE_ONDERZOEK, SoortAdministratieveHandeling.AANVANG_ONDERZOEK, new DatumAttribuut(20130101).toDate());

        final PersoonHisVolledigImpl anita = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        ReflectionTestUtils.setField(anita, ID, 4577);


//        assertTrue(false); //even opzettelijk laten falen...
        final PersoonOnderzoekHisVolledigImpl anitaOnderzoek = new PersoonOnderzoekHisVolledigImplBuilder(anita).nieuwStandaardRecord(
            actieOnderzoek).rol(SoortPersoonOnderzoek.INDIRECT).eindeRecord(123).build();
        ReflectionTestUtils.setField(anitaOnderzoek, ID, 1123);
        anita.getOnderzoeken().add(anitaOnderzoek);

        final PersoonOnderzoekHisVolledigImpl johnnyOnderzoek = new PersoonOnderzoekHisVolledigImplBuilder(johnny)
            .nieuwStandaardRecord(actieOnderzoek).rol(SoortPersoonOnderzoek.DIRECT).eindeRecord(345).build();
        ReflectionTestUtils.setField(johnnyOnderzoek, ID, 2234);
        johnny.getOnderzoeken().add(johnnyOnderzoek);

        final OnderzoekHisVolledigImpl onderzoekHisVolledig = new OnderzoekHisVolledigImplBuilder()
            .voegPersoonOnderzoekToe(anitaOnderzoek)
            .voegPersoonOnderzoekToe(johnnyOnderzoek)
            .nieuwStandaardRecord(
                actieOnderzoek).status(StatusOnderzoek.IN_UITVOERING).omschrijving("bla bla").eindeRecord(987).build();
        ReflectionTestUtils.setField(onderzoekHisVolledig, ID, 3345);

        for (final PersoonOnderzoekHisVolledigImpl persoonOnderzoekHisVolledig : onderzoekHisVolledig.getPersonenInOnderzoek()) {
            ReflectionTestUtils.setField(persoonOnderzoekHisVolledig, ONDERZOEK, onderzoekHisVolledig);
        }


        final byte[] data = serializer.serialiseer(johnny);
        final String json = new String(data);

        LOGGER.info(JSON, json);

        final PersoonHisVolledigImpl terugPersoon = serializer.deserialiseer(data);
        Assert.assertNotNull(terugPersoon);
    }

    private OnderzoekHisVolledigImpl voegOnderzoekToeAan(final PersoonHisVolledigImpl persoonInOnderzoek,
        final ActieModel actieOnderzoek)
    {
        final PersoonHisVolledigImpl marie = TestPersoonAntwoordPersoon.maakAntwoordPersoon();
        ReflectionTestUtils.setField(marie, ID, 444);


        final ActieModel actieUitbreidingOnderzoek =
            maakActieModel(4L, SoortActie.WIJZIGING_ONDERZOEK,
                SoortAdministratieveHandeling.WIJZIGING_GESLACHTSNAAM, new DatumAttribuut(20131012).toDate());

        final ActieModel actieEindeOnderzoek =
            maakActieModel(5L, SoortActie.WIJZIGING_ONDERZOEK, SoortAdministratieveHandeling.BEEINDIGING_ONDERZOEK,
                new DatumAttribuut(20131231).toDate());

        final PersoonOnderzoekHisVolledigImpl persoonOnderzoek =
            new PersoonOnderzoekHisVolledigImplBuilder(persoonInOnderzoek)
                .nieuwStandaardRecord(actieOnderzoek).rol(SoortPersoonOnderzoek.DIRECT)
                .eindeRecord(809).build();
        ReflectionTestUtils.setField(persoonOnderzoek, ID, 758354);
        persoonInOnderzoek.getOnderzoeken().add(persoonOnderzoek);

        final PersoonOnderzoekHisVolledigImpl marieOnderzoek = new PersoonOnderzoekHisVolledigImplBuilder(marie)
            .nieuwStandaardRecord(actieUitbreidingOnderzoek).rol(SoortPersoonOnderzoek.INDIRECT)
            .eindeRecord(543).build();
        ReflectionTestUtils.setField(marieOnderzoek, ID, 34563);
        marie.getOnderzoeken().add(marieOnderzoek);

        final OnderzoekHisVolledigImpl onderzoek = new OnderzoekHisVolledigImplBuilder()
            .voegPersoonOnderzoekToe(persoonOnderzoek)
            .voegGegevenInOnderzoekToe(
                new GegevenInOnderzoekHisVolledigImplBuilder(TestElementBuilder.maker().metId(12345).maak(),
                                                             new SleutelwaardeAttribuut(1201L), new SleutelwaardeAttribuut(1202L)).build())
                    .voegPartijOnderzoekToe(
                        new PartijOnderzoekHisVolledigImplBuilder(
                            StatischeObjecttypeBuilder.PARTIJ_MINISTERIE_BZK.getWaarde())
                            .nieuwStandaardRecord(actieOnderzoek).rol(SoortPartijOnderzoek.EIGENAAR)
                            .eindeRecord().build())
                    .nieuwAfgeleidAdministratiefRecord(actieOnderzoek).eindeRecord()
                    .nieuwStandaardRecord(actieOnderzoek).datumAanvang(20130901).eindeRecord(53432)
                    .voegPersoonOnderzoekToe(marieOnderzoek)
                    .voegPartijOnderzoekToe(
                        new PartijOnderzoekHisVolledigImplBuilder(
                            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_SGRAVENHAGE.getWaarde())
                            .nieuwStandaardRecord(actieUitbreidingOnderzoek).rol(SoortPartijOnderzoek.GEVOEGDE)
                            .eindeRecord().build())
                    .nieuwStandaardRecord(actieUitbreidingOnderzoek).status(StatusOnderzoek.IN_UITVOERING)
                    .omschrijving("Toevoeging partij").eindeRecord()
                    .nieuwStandaardRecord(actieEindeOnderzoek).status(StatusOnderzoek.GESTAAKT).datumEinde(20131231)
                    .eindeRecord(34534)
            .build();

        for (final PersoonOnderzoekHisVolledigImpl persoonOnderzoekHisVolledig : onderzoek.getPersonenInOnderzoek()) {
            ReflectionTestUtils.setField(persoonOnderzoekHisVolledig, ONDERZOEK, onderzoek);
        }

        return onderzoek;
    }

    private static ActieModel maakActieModel(final Long actieId, final SoortActie soortActie,
        final SoortAdministratieveHandeling soortAdministratieveHandeling,
        final Date datum)
    {
        final AdministratieveHandelingModel administratieveHandeling =
            new AdministratieveHandelingModel(
                new SoortAdministratieveHandelingAttribuut(soortAdministratieveHandeling), null, null,
                new DatumTijdAttribuut(datum));
        ReflectionTestUtils.setField(administratieveHandeling, ID, 2L);

        final ActieModel actieModel =
            new ActieModel(new SoortActieAttribuut(soortActie), administratieveHandeling, null,
                new DatumEvtDeelsOnbekendAttribuut(new DatumAttribuut(datum)),
                null, new DatumTijdAttribuut(datum), null);
        ReflectionTestUtils.setField(actieModel, ID, actieId);

        final SortedSet<ActieModel> acties = new TreeSet<>(new IdComparator());
        acties.add(actieModel);
        ReflectionTestUtils.setField(administratieveHandeling, "acties", acties);

        return actieModel;
    }
}
