/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.repository.historie.GroepMaterieleHistorieRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingErkenningOngeborenVruchtBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBijhoudingsaardGroepBericht;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingsaardGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonBijhoudingsaardGroepBasis;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingsaardModel;
import nl.bzk.brp.model.operationeel.kern.PersoonBijhoudingsaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.junit.Test;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Algemene unit test voor het testen van de functionaliteit in de
 * {@link nl.bzk.brp.dataaccess.repository.jpa.historie.AbstractGroepMaterieleHistorieRepository} class. Dit wordt
 * gedaan middels de {@link nl.bzk.brp.dataaccess.repository.jpa.historie.HistoriePersoonBijhoudingsaardJpaRepository},
 * wat een implementatie is van eerder genoemde abstracte class.
 * <p/>
 * De test data voor deze test bestaat uit een 5 tal records in de his_persbijhaard tabel, te weten 3 actuele records
 * en 2 vervallen records. Daarnaast is er ook nog een extra actie beschikbaar met id '104'.
 * LET OP: We maken hier in de historie ook gebruik van de niet bestaande bijhoudingsaard 4!
 * (dit, om een rijkere historie te hebben en het alleen voor direct testen op de nummers gebruikt wordt)
 * <p/>
 * Bijhoudingsaard.INGEZETENE = 1
 * Bijhoudingsaard.EMIGRATIE = 2
 * Bijhoudingsaard.REGISTER_NIET_INGEZETENE = 3
 * <p/>
 * ------------------------------------------------------------------------------------------------
 * | id  | pers | dataanvgel | dateindegel | bijhaard | tsreg               | tsverval            |
 * ------------------------------------------------------------------------------------------------
 * | 101 | 2    | 2010-01-01 |             | 4        | 2010-01-01 10:00:00 | 2011-01-02 12:12:12 |
 * | 102 | 2    | 2010-01-01 | 2011-01-01  | 4        | 2011-01-02 12:12:12 |                     |
 * | 103 | 2    | 2011-01-01 |             | 3        | 2011-01-02 12:12:12 | 2011-11-12 13:13:13 |
 * | 104 | 2    | 2011-01-01 | 2011-11-11  | 3        | 2011-11-12 13:13:13 |                     |
 * | 105 | 2    | 2011-11-11 |             | 2        | 2011-11-12 13:13:13 |                     |
 * ------------------------------------------------------------------------------------------------
 */
public class AlgemeneAbstractGroepHistorieRepositoryTest extends AbstractRepositoryTestCase {

    private static final String QUERY_ACTUELE_RECORDS   =
        "SELECT * FROM kern.His_PersBijhaard WHERE tsverval IS null ORDER BY dataanvgel, tsreg";
    private static final String QUERY_VERVALLEN_RECORDS =
        "SELECT * FROM kern.His_PersBijhaard WHERE tsverval IS NOT null ORDER BY dataanvgel, tsreg";

    @Named(value = "historiePersoonBijhoudingsaardRepository")
    @Inject
    private GroepMaterieleHistorieRepository<PersoonModel, PersoonBijhoudingsaardGroepBasis,
        HisPersoonBijhoudingsaardModel> repositoryUnderTest;

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager em;

    @Test
    public void testNieuwRecordZonderEindeEnEenDeelsOverlap() {
        Assert.assertEquals(5, countRowsInTable("kern.His_PersBijhaard"));
        repositoryUnderTest.persisteerHistorie(bouwPersoon(Bijhoudingsaard.INGEZETENE), bouwActie(),
            new Datum(20120101), null);
        em.flush();

        // Verwacht nu 7 records (4 actueel, 3 vervallen)
        Assert.assertEquals(7, countRowsInTable("kern.His_PersBijhaard"));
        testVerwachteActueleRecords(
            new BijhoudingsaardInfoTuple("20100101", "20110101", "2011-01-02 12:12:12", null, 4),
            new BijhoudingsaardInfoTuple("20110101", "20111111", "2011-11-12 13:13:13", null, 3),
            new BijhoudingsaardInfoTuple("20111111", "20120101", "2012-12-12 12:12:12", null, 2),
            new BijhoudingsaardInfoTuple("20120101", null, "2012-12-12 12:12:12", null, 1));
        testVerwachteVervallenRecords(
            new BijhoudingsaardInfoTuple("20100101", null, "2010-01-01 10:00:00", "2011-01-02 12:12:12", 4),
            new BijhoudingsaardInfoTuple("20110101", null, "2011-01-02 12:12:12", "2011-11-12 13:13:13", 3),
            new BijhoudingsaardInfoTuple("20111111", null, "2011-11-12 13:13:13", "2012-12-12 12:12:12", 2));
    }


    @Test
    public void testNieuwRecordZonderEindeEnEenDeelsEnGeheelOverlap() {
        repositoryUnderTest.persisteerHistorie(bouwPersoon(Bijhoudingsaard.INGEZETENE), bouwActie(),
            new Datum(20110909), null);
        em.flush();

        // Verwacht nu 7 records (3 actueel, 4 vervallen)
        Assert.assertEquals(7, countRowsInTable("kern.His_PersBijhaard"));
        testVerwachteActueleRecords(
            new BijhoudingsaardInfoTuple("20100101", "20110101", "2011-01-02 12:12:12", null, 4),
            new BijhoudingsaardInfoTuple("20110101", "20110909", "2012-12-12 12:12:12", null, 3),
            new BijhoudingsaardInfoTuple("20110909", null, "2012-12-12 12:12:12", null, 1));
        testVerwachteVervallenRecords(
            new BijhoudingsaardInfoTuple("20100101", null, "2010-01-01 10:00:00", "2011-01-02 12:12:12", 4),
            new BijhoudingsaardInfoTuple("20110101", null, "2011-01-02 12:12:12", "2011-11-12 13:13:13", 3),
            new BijhoudingsaardInfoTuple("20110101", "20111111", "2011-11-12 13:13:13", "2012-12-12 12:12:12", 3),
            new BijhoudingsaardInfoTuple("20111111", null, "2011-11-12 13:13:13", "2012-12-12 12:12:12", 2));
    }

    @Test
    public void testNieuwRecordMetEindeEnOverlaptTweeBestaande() {
        repositoryUnderTest.persisteerHistorie(bouwPersoon(Bijhoudingsaard.INGEZETENE), bouwActie(),
            new Datum(20110909), new Datum(20120101));
        em.flush();

        // Verwacht nu 8 records (4 actueel, 4 vervallen)
        Assert.assertEquals(8, countRowsInTable("kern.His_PersBijhaard"));
        testVerwachteActueleRecords(
            new BijhoudingsaardInfoTuple("20100101", "20110101", "2011-01-02 12:12:12", null, 4),
            new BijhoudingsaardInfoTuple("20110101", "20110909", "2012-12-12 12:12:12", null, 3),
            new BijhoudingsaardInfoTuple("20110909", "20120101", "2012-12-12 12:12:12", null, 1),
            new BijhoudingsaardInfoTuple("20120101", null, "2012-12-12 12:12:12", null, 2));
        testVerwachteVervallenRecords(
            new BijhoudingsaardInfoTuple("20100101", null, "2010-01-01 10:00:00", "2011-01-02 12:12:12", 4),
            new BijhoudingsaardInfoTuple("20110101", null, "2011-01-02 12:12:12", "2011-11-12 13:13:13", 3),
            new BijhoudingsaardInfoTuple("20110101", "20111111", "2011-11-12 13:13:13", "2012-12-12 12:12:12", 3),
            new BijhoudingsaardInfoTuple("20111111", null, "2011-11-12 13:13:13", "2012-12-12 12:12:12", 2));
    }

// Laten staan a.u.b. voor eventueel toekomstig debugging gebruik.
//    private void printHistorischeRecords() {
//        List<PersoonBijhoudingsaardHisModel> hisPersoonBijhoudingsaarden =
//                em.createQuery("select hpa FROM PersoonBijhoudingsaardHisModel hpa "
//                    + " order by hpa.historie.datumTijdVerval desc, "
//                    + " hpa.historie.datumAanvangGeldigheid desc, "
//                    + "hpa.historie.datumTijdRegistratie" ,
//                PersoonBijhoudingsaardHisModel.class).getResultList();
//        for (PersoonBijhoudingsaardHisModel his : hisPersoonBijhoudingsaarden) {
//            System.out.printf("%d= %d-%d, %d-%d %s\n",
//                    his.getId(), his.getHistorie().getDatumAanvangGeldigheid().getOmschrijving()
//                    , (null == his.getHistorie().getDatumEindeGeldigheid()) ?
//                            0 : his.getHistorie().getDatumEindeGeldigheid().getOmschrijving()
//                    , his.getHistorie().getDatumTijdRegistratie().getOmschrijving().getTime()
//                    , (null == his.getHistorie().getDatumTijdVerval()) ?
//                            0 : his.getHistorie().getDatumTijdVerval().getOmschrijving().getTime()
//                    , his.getGeslachtsnaam().getOmschrijving()
//            );
//        }
//    }

    @Test
    public void testTweePeriodesNietOverlappend() {
        // deze test simuleert meerdere acties binnen 1 bericht, met verschillende periode.
        // het probleem is dat de periode in het midden 2x wordt aangemaakt, waarbij de 1e record door de 2e actie
        // naar de D-Laag wordt gepushed, maar beide records hebben zelfde aanvangdatum en registratie tijdstip.
        // oplossing is dat alle record  met '0-geldigheidsperiode' uiteindelijk niet bewaard wordt.
        ActieModel actie = bouwActie();
        PersoonModel persoon = bouwPersoon(Bijhoudingsaard.INGEZETENE);
        PersoonBijhoudingsaardGroepBasis bijhoudingsaardGroep = persoon.getBijhoudingsaard();

        repositoryUnderTest.persisteerHistorie(persoon,
            bijhoudingsaardGroep, actie, new Datum(20110909), new Datum(20120101));

        repositoryUnderTest.persisteerHistorie(persoon,
            bijhoudingsaardGroep, actie, new Datum(20120515), new Datum(20120717));
        em.flush();
//        printHistorischeRecords();

        // Verwacht nu 10 records (6 actueel, 4 vervallen)
        Assert.assertEquals(10, countRowsInTable("kern.His_PersBijhaard"));
        testVerwachteActueleRecords(
            new BijhoudingsaardInfoTuple("20100101", "20110101", "2011-01-02 12:12:12", null, 4),
            new BijhoudingsaardInfoTuple("20110101", "20110909", "2012-12-12 12:12:12", null, 3),
            new BijhoudingsaardInfoTuple("20110909", "20120101", "2012-12-12 12:12:12", null, 1),
            new BijhoudingsaardInfoTuple("20120101", "20120515", "2012-12-12 12:12:12", null, 2),
            new BijhoudingsaardInfoTuple("20120515", "20120717", "2012-12-12 12:12:12", null, 1),
            new BijhoudingsaardInfoTuple("20120717", null, "2012-12-12 12:12:12", null, 2));
        testVerwachteVervallenRecords(
            new BijhoudingsaardInfoTuple("20100101", null, "2010-01-01 10:00:00", "2011-01-02 12:12:12", 4),
            new BijhoudingsaardInfoTuple("20110101", null, "2011-01-02 12:12:12", "2011-11-12 13:13:13", 3),
            new BijhoudingsaardInfoTuple("20110101", "20111111", "2011-11-12 13:13:13", "2012-12-12 12:12:12", 3),
            new BijhoudingsaardInfoTuple("20111111", null, "2011-11-12 13:13:13", "2012-12-12 12:12:12", 2));
    }

    @Test
    public void testTweePeriodesNietOverlappendAchtersteVoren() {
        // deze test toont aan dat de volgorde van acties niet toe doet.
        ActieModel actie = bouwActie();
        PersoonModel persoon = bouwPersoon(Bijhoudingsaard.INGEZETENE);
        PersoonBijhoudingsaardGroepBasis bijhoudingsaardGroep = persoon.getBijhoudingsaard();

        repositoryUnderTest.persisteerHistorie(persoon,
            bijhoudingsaardGroep, actie, new Datum(20120515), new Datum(20120717));

        repositoryUnderTest.persisteerHistorie(persoon,
            bijhoudingsaardGroep, actie, new Datum(20110909), new Datum(20120101));

        em.flush();

        // Verwacht nu 10 records (6 actueel, 4 vervallen)
        Assert.assertEquals(10, countRowsInTable("kern.His_PersBijhaard"));
        testVerwachteActueleRecords(
            new BijhoudingsaardInfoTuple("20100101", "20110101", "2011-01-02 12:12:12", null, 4),
            new BijhoudingsaardInfoTuple("20110101", "20110909", "2012-12-12 12:12:12", null, 3),
            new BijhoudingsaardInfoTuple("20110909", "20120101", "2012-12-12 12:12:12", null, 1),
            new BijhoudingsaardInfoTuple("20120101", "20120515", "2012-12-12 12:12:12", null, 2),
            new BijhoudingsaardInfoTuple("20120515", "20120717", "2012-12-12 12:12:12", null, 1),
            new BijhoudingsaardInfoTuple("20120717", null, "2012-12-12 12:12:12", null, 2));
        testVerwachteVervallenRecords(
            new BijhoudingsaardInfoTuple("20100101", null, "2010-01-01 10:00:00", "2011-01-02 12:12:12", 4),
            new BijhoudingsaardInfoTuple("20110101", null, "2011-01-02 12:12:12", "2011-11-12 13:13:13", 3),
            new BijhoudingsaardInfoTuple("20110101", "20111111", "2011-11-12 13:13:13", "2012-12-12 12:12:12", 3),
            new BijhoudingsaardInfoTuple("20111111", null, "2011-11-12 13:13:13", "2012-12-12 12:12:12", 2));
    }

    @Test
    public void testVierPeriodesNietOverlappend() {
        // meest complex.
        ActieModel actie = bouwActie();
        PersoonModel persoon = bouwPersoon(Bijhoudingsaard.INGEZETENE);
        PersoonBijhoudingsaardGroep bijhoudingsaardGroep = persoon.getBijhoudingsaard();
        PersoonBijhoudingsaardGroepBasis andereGroep = new PersoonBijhoudingsaardGroepModel(bijhoudingsaardGroep);
        ReflectionTestUtils.setField(andereGroep, "bijhoudingsaard", Bijhoudingsaard.EMIGRATIE);

        repositoryUnderTest.persisteerHistorie(persoon,
            bijhoudingsaardGroep, actie, new Datum(20110909), new Datum(20120101));
        repositoryUnderTest.persisteerHistorie(persoon,
            bijhoudingsaardGroep, actie, new Datum(20120313), new Datum(20120515));
        repositoryUnderTest.persisteerHistorie(persoon,
            bijhoudingsaardGroep, actie, new Datum(20120717), new Datum(20120919));
        repositoryUnderTest.persisteerHistorie(persoon,
            andereGroep, actie, new Datum(20120406), new Datum(20120816));
        em.flush();

        // Verwacht nu 12 records (6 actueel, 4 vervallen)
        Assert.assertEquals(12, countRowsInTable("kern.His_PersBijhaard"));
        testVerwachteActueleRecords(
            new BijhoudingsaardInfoTuple("20100101", "20110101", "2011-01-02 12:12:12", null, 4),
            new BijhoudingsaardInfoTuple("20110101", "20110909", "2012-12-12 12:12:12", null, 3),
            new BijhoudingsaardInfoTuple("20110909", "20120101", "2012-12-12 12:12:12", null, 1),
            new BijhoudingsaardInfoTuple("20120101", "20120313", "2012-12-12 12:12:12", null, 2),
            new BijhoudingsaardInfoTuple("20120313", "20120406", "2012-12-12 12:12:12", null, 1),
            new BijhoudingsaardInfoTuple("20120406", "20120816", "2012-12-12 12:12:12", null, 2),
            new BijhoudingsaardInfoTuple("20120816", "20120919", "2012-12-12 12:12:12", null, 1),
            new BijhoudingsaardInfoTuple("20120919", null, "2012-12-12 12:12:12", null, 2));
        testVerwachteVervallenRecords(
            new BijhoudingsaardInfoTuple("20100101", null, "2010-01-01 10:00:00", "2011-01-02 12:12:12", 4),
            new BijhoudingsaardInfoTuple("20110101", null, "2011-01-02 12:12:12", "2011-11-12 13:13:13", 3),
            new BijhoudingsaardInfoTuple("20110101", "20111111", "2011-11-12 13:13:13", "2012-12-12 12:12:12", 3),
            new BijhoudingsaardInfoTuple("20111111", null, "2011-11-12 13:13:13", "2012-12-12 12:12:12", 2));
    }


    @Test
    public void testTweePeriodesAaneensluitend() {
        // deze test simuleert meerdere acties binnen 1 bericht, met verschillende periode.
        // het probleem is dat in het proces een 0-periode record ook verwijderd moet worden.
        ActieModel actie = bouwActie();
        PersoonModel persoon = bouwPersoon(Bijhoudingsaard.INGEZETENE);
        PersoonBijhoudingsaardGroepBasis bijhoudingsaardGroep =
            persoon.getBijhoudingsaard();
        repositoryUnderTest.persisteerHistorie(persoon,
            bijhoudingsaardGroep, actie, new Datum(20110909), new Datum(20120515));
        repositoryUnderTest.persisteerHistorie(persoon,
            bijhoudingsaardGroep, actie, new Datum(20120515), new Datum(20120717));
        em.flush();

        // Verwacht nu 9 records (5 actueel, 4 vervallen)
        Assert.assertEquals(9, countRowsInTable("kern.His_PersBijhaard"));
        testVerwachteActueleRecords(
            new BijhoudingsaardInfoTuple("20100101", "20110101", "2011-01-02 12:12:12", null, 4),
            new BijhoudingsaardInfoTuple("20110101", "20110909", "2012-12-12 12:12:12", null, 3),
            new BijhoudingsaardInfoTuple("20110909", "20120515", "2012-12-12 12:12:12", null, 1),
            new BijhoudingsaardInfoTuple("20120515", "20120717", "2012-12-12 12:12:12", null, 1),
            new BijhoudingsaardInfoTuple("20120717", null, "2012-12-12 12:12:12", null, 2));
        testVerwachteVervallenRecords(
            new BijhoudingsaardInfoTuple("20100101", null, "2010-01-01 10:00:00", "2011-01-02 12:12:12", 4),
            new BijhoudingsaardInfoTuple("20110101", null, "2011-01-02 12:12:12", "2011-11-12 13:13:13", 3),
            new BijhoudingsaardInfoTuple("20110101", "20111111", "2011-11-12 13:13:13", "2012-12-12 12:12:12", 3),
            new BijhoudingsaardInfoTuple("20111111", null, "2011-11-12 13:13:13", "2012-12-12 12:12:12", 2));
    }

    @Test
    public void testTweePeriodesAaneensluitendAchtersteVoren() {
        // deze test toont aan dat de volgorde er niet toe doet voor TweePeriodesAaneensluitend
        ActieModel actie = bouwActie();
        PersoonModel persoon = bouwPersoon(Bijhoudingsaard.INGEZETENE);
        PersoonBijhoudingsaardGroepBasis bijhoudingsaardGroep =
            persoon.getBijhoudingsaard();
        repositoryUnderTest.persisteerHistorie(persoon,
            bijhoudingsaardGroep, actie, new Datum(20120515), new Datum(20120717));
        repositoryUnderTest.persisteerHistorie(persoon,
            bijhoudingsaardGroep, actie, new Datum(20110909), new Datum(20120515));
        em.flush();
        // Verwacht nu 9 records (5 actueel, 4 vervallen)
        Assert.assertEquals(9, countRowsInTable("kern.His_PersBijhaard"));
        testVerwachteActueleRecords(
            new BijhoudingsaardInfoTuple("20100101", "20110101", "2011-01-02 12:12:12", null, 4),
            new BijhoudingsaardInfoTuple("20110101", "20110909", "2012-12-12 12:12:12", null, 3),
            new BijhoudingsaardInfoTuple("20110909", "20120515", "2012-12-12 12:12:12", null, 1),
            new BijhoudingsaardInfoTuple("20120515", "20120717", "2012-12-12 12:12:12", null, 1),
            new BijhoudingsaardInfoTuple("20120717", null, "2012-12-12 12:12:12", null, 2));
        testVerwachteVervallenRecords(
            new BijhoudingsaardInfoTuple("20100101", null, "2010-01-01 10:00:00", "2011-01-02 12:12:12", 4),
            new BijhoudingsaardInfoTuple("20110101", null, "2011-01-02 12:12:12", "2011-11-12 13:13:13", 3),
            new BijhoudingsaardInfoTuple("20110101", "20111111", "2011-11-12 13:13:13", "2012-12-12 12:12:12", 3),
            new BijhoudingsaardInfoTuple("20111111", null, "2011-11-12 13:13:13", "2012-12-12 12:12:12", 2));
    }

    @Test
    public void testDriePeriodesAaneensluitend() {
        // deze test toont aan dat de volgorde er niet toe doet voor TweePeriodesAaneensluitend
        ActieModel actie = bouwActie();
        PersoonModel persoon = bouwPersoon(Bijhoudingsaard.INGEZETENE);
        PersoonBijhoudingsaardGroepBasis bijhoudingsaardGroep =
            persoon.getBijhoudingsaard();
        repositoryUnderTest.persisteerHistorie(persoon,
            bijhoudingsaardGroep, actie, new Datum(20110909), new Datum(20120515));
        repositoryUnderTest.persisteerHistorie(persoon,
            bijhoudingsaardGroep, actie, new Datum(20120515), new Datum(20120717));
        repositoryUnderTest.persisteerHistorie(persoon,
            bijhoudingsaardGroep, actie, new Datum(20120717), new Datum(20120919));
        em.flush();

//        printHistorischeRecords();

        // Verwacht nu 10 records (6 actueel, 4 vervallen)
        Assert.assertEquals(10, countRowsInTable("kern.His_PersBijhaard"));
        testVerwachteActueleRecords(
            new BijhoudingsaardInfoTuple("20100101", "20110101", "2011-01-02 12:12:12", null, 4),
            new BijhoudingsaardInfoTuple("20110101", "20110909", "2012-12-12 12:12:12", null, 3),
            new BijhoudingsaardInfoTuple("20110909", "20120515", "2012-12-12 12:12:12", null, 1),
            new BijhoudingsaardInfoTuple("20120515", "20120717", "2012-12-12 12:12:12", null, 1),
            new BijhoudingsaardInfoTuple("20120717", "20120919", "2012-12-12 12:12:12", null, 1),
            new BijhoudingsaardInfoTuple("20120919", null, "2012-12-12 12:12:12", null, 2));
        testVerwachteVervallenRecords(
            new BijhoudingsaardInfoTuple("20100101", null, "2010-01-01 10:00:00", "2011-01-02 12:12:12", 4),
            new BijhoudingsaardInfoTuple("20110101", null, "2011-01-02 12:12:12", "2011-11-12 13:13:13", 3),
            new BijhoudingsaardInfoTuple("20110101", "20111111", "2011-11-12 13:13:13", "2012-12-12 12:12:12", 3),
            new BijhoudingsaardInfoTuple("20111111", null, "2011-11-12 13:13:13", "2012-12-12 12:12:12", 2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMessedupDatums() throws Throwable {
        // Als de datums niet correct zijn, moet de methode een exception throwen. Dit om te voorkomen dat de
        // historie een complete garbage wordt. We hebben besloten om de aangegeven datums NIET te corrigeren.
        ActieModel actie = bouwActie();
        PersoonModel persoon = bouwPersoon(Bijhoudingsaard.INGEZETENE);
        PersoonBijhoudingsaardGroepBasis bijhoudingsaardGroep =
            persoon.getBijhoudingsaard();
        try {
            repositoryUnderTest.persisteerHistorie(persoon,
                bijhoudingsaardGroep, actie, new Datum(20120515), new Datum(20110909));
        } catch (InvalidDataAccessApiUsageException ei) {
            throw ei.getCause();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMessedupDatums2() throws Throwable {
        // Als de datums niet correct zijn, moet de methode een exception throwen. Dit om te voorkomen dat de
        // historie een complete garbage wordt. We hebben besloten om de aangegeven datums NIET te corrigeren.
        ActieModel actie = bouwActie();
        PersoonModel persoon = bouwPersoon(Bijhoudingsaard.INGEZETENE);
        PersoonBijhoudingsaardGroepBasis bijhoudingsaardGroep =
            persoon.getBijhoudingsaard();
        try {
            repositoryUnderTest.persisteerHistorie(persoon,
                bijhoudingsaardGroep, actie, new Datum(20120515), new Datum(20120515));
        } catch (InvalidDataAccessApiUsageException ei) {
            throw ei.getCause();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMessedupDatums3() throws Throwable {
        // Als de datums niet correct zijn, moet de methode een exception throwen. Dit om te voorkomen dat de
        // historie een complete garbage wordt. We hebben besloten om de aangegeven datums NIET te corrigeren.
        ActieModel actie = bouwActie();
        PersoonModel persoon = bouwPersoon(Bijhoudingsaard.INGEZETENE);
        PersoonBijhoudingsaardGroepBasis bijhoudingsaardGroep =
            persoon.getBijhoudingsaard();
        try {

            repositoryUnderTest.persisteerHistorie(persoon,
                bijhoudingsaardGroep, actie, null, new Datum(20120515));
        } catch (InvalidDataAccessApiUsageException ei) {
            throw ei.getCause();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMessedupDatums4() throws Throwable {
        // Als de datums niet correct zijn, moet de methode een exception throwen. Dit om te voorkomen dat de
        // historie een complete garbage wordt. We hebben besloten om de aangegeven datums NIET te corrigeren.
        ActieModel actie = bouwActie();
        PersoonModel persoon = bouwPersoon(Bijhoudingsaard.INGEZETENE);
        PersoonBijhoudingsaardGroepBasis bijhoudingsaardGroep =
            persoon.getBijhoudingsaard();
        try {
            repositoryUnderTest.persisteerHistorie(persoon,
                bijhoudingsaardGroep, actie, null, null);
        } catch (InvalidDataAccessApiUsageException ei) {
            throw ei.getCause();
        }
    }

    /**
     * Test of de verwachte bijhoudingsaard tuples gelijk zijn aan de gevonden actuele records. Hierbij wordt uitgegaan
     * van een volgorde gebaseerd op datum aanvang geldigheid.
     *
     * @param verwachteBijhoudingsaardElementen de verwachte bijhoudingsaard elementen
     */
    private void testVerwachteActueleRecords(final BijhoudingsaardInfoTuple... verwachteBijhoudingsaardElementen) {
        List<BijhoudingsaardInfoTuple> gevondenBijhoudingsaardElementen =
            simpleJdbcTemplate.query(QUERY_ACTUELE_RECORDS, new BijhoudingsaardRowMapper());
        testVerwachteRecords(gevondenBijhoudingsaardElementen, verwachteBijhoudingsaardElementen);
    }

    /**
     * Test of de verwachte bijhoudingsaard tuples gelijk zijn aan de gevonden vervallen records. Hierbij wordt uitgegaan
     * van een volgorde gebaseerd op datum aanvang geldigheid.
     *
     * @param verwachteBijhoudingsaardElementen de verwachte bijhoudingsaard elementen
     */
    private void testVerwachteVervallenRecords(final BijhoudingsaardInfoTuple... verwachteBijhoudingsaardElementen) {
        List<BijhoudingsaardInfoTuple> gevondenBijhoudingsaardElementen =
            simpleJdbcTemplate.query(QUERY_VERVALLEN_RECORDS, new BijhoudingsaardRowMapper());
        testVerwachteRecords(gevondenBijhoudingsaardElementen, verwachteBijhoudingsaardElementen);
    }

    /**
     * Test of de verwachte bijhoudingsaard tuples gelijk zijn aan de gevonden records. Hierbij wordt uitgegaan
     * van een volgorde gebaseerd op datum aanvang geldigheid.
     *
     * @param verwachteBijhoudingsaardElementen de verwachte bijhoudingsaard elementen
     */
    private void testVerwachteRecords(final List<BijhoudingsaardInfoTuple> gevondenBijhoudingsaardElementen,
        final BijhoudingsaardInfoTuple... verwachteBijhoudingsaardElementen)
    {
        Assert.assertEquals(verwachteBijhoudingsaardElementen.length, gevondenBijhoudingsaardElementen.size());
        int i = 0;
        for (BijhoudingsaardInfoTuple tuple : verwachteBijhoudingsaardElementen) {
            Assert.assertEquals("Fout in bijhoudingsaard record " + i, tuple.bijhoudingsaard,
                gevondenBijhoudingsaardElementen.get(i).bijhoudingsaard);
            Assert.assertEquals("Fout in datum aanvang record " + i, tuple.datumAanvang,
                gevondenBijhoudingsaardElementen.get(i).datumAanvang);
            Assert.assertEquals("Fout in datum einde record " + i, tuple.datumEinde,
                gevondenBijhoudingsaardElementen.get(i).datumEinde);
            Assert.assertEquals("Fout in tijdstip registratie record " + i, tuple.tijdstipRegistratie,
                gevondenBijhoudingsaardElementen.get(i).tijdstipRegistratie);
            Assert.assertEquals("Fout in tijdstip vervallen record " + i, tuple.tijdstipVervallen,
                gevondenBijhoudingsaardElementen.get(i).tijdstipVervallen);
            i++;
        }
    }

    /**
     * Instantieert en retourneert een {@link PersoonModel} instantie met de id '2'.
     *
     * @return een nieuwe persoon met opgegeven bijhoudingsaard
     */
    private PersoonModel bouwPersoon(final Bijhoudingsaard bijhoudingsaard) {
        PersoonBijhoudingsaardGroepBericht bijhoudingsaardGroep = new PersoonBijhoudingsaardGroepBericht();
        bijhoudingsaardGroep.setBijhoudingsaard(bijhoudingsaard);

        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setBijhoudingsaard(bijhoudingsaardGroep);

        PersoonModel persoon = new PersoonModel(persoonBericht);
        ReflectionTestUtils.setField(persoon, "iD", 2);
        return persoon;
    }

    /**
     * Instantieert en retourneert een {@link ActieModel} instantie met de id '104'.
     *
     * @return een nieuwe actie model met id 104
     */
    private ActieModel bouwActie() {
        ActieBericht actieBericht = new ActieRegistratieAdresBericht();
        Calendar tijdstipRegistratie = Calendar.getInstance();
        tijdstipRegistratie.set(Calendar.YEAR, 2012);
        tijdstipRegistratie.set(Calendar.MONTH, Calendar.DECEMBER);
        tijdstipRegistratie.set(Calendar.DAY_OF_MONTH, 12);
        tijdstipRegistratie.set(Calendar.HOUR_OF_DAY, 12);
        tijdstipRegistratie.set(Calendar.MINUTE, 12);
        tijdstipRegistratie.set(Calendar.SECOND, 12);
        tijdstipRegistratie.set(Calendar.MILLISECOND, 0);
        actieBericht.setTijdstipRegistratie(new DatumTijd(tijdstipRegistratie.getTime()));

        // Dummy administratieve handeling, geen inhoud nodig, alleen referentie vanuit actie.
        AdministratieveHandelingBericht administratieveHandeling = new HandelingErkenningOngeborenVruchtBericht();

        ActieModel actie = new ActieModel(actieBericht, new AdministratieveHandelingModel(administratieveHandeling));
        ReflectionTestUtils.setField(actie, "iD", 104L);

        return actie;
    }


    /** Tuple van 5 elementen die een bijhoudingsaard record uit de database representeren. */
    private final class BijhoudingsaardInfoTuple {

        private final String datumAanvang;
        private final String datumEinde;
        private final String tijdstipRegistratie;
        private final String tijdstipVervallen;
        private final int    bijhoudingsaard;

        /** Standaard constructor die alle velden zet. */
        private BijhoudingsaardInfoTuple(final String datumAanvang, final String datumEinde,
            final String tijdstipRegistratie, final String tijdstipVervallen, final int bijhoudingsaard)
        {
            this.datumAanvang = datumAanvang;
            this.datumEinde = datumEinde;
            this.tijdstipRegistratie = tijdstipRegistratie;
            this.tijdstipVervallen = tijdstipVervallen;
            this.bijhoudingsaard = bijhoudingsaard;
        }

    }

    /**
     * Implementatie van de {@link RowMapper} interface die
     * {@link AlgemeneAbstractGroepHistorieRepositoryTest.BijhoudingsaardInfoTuple}
     * instanties uitleest uit een SQL Query.
     */
    private class BijhoudingsaardRowMapper implements RowMapper<BijhoudingsaardInfoTuple> {
        @Override
        public BijhoudingsaardInfoTuple mapRow(final ResultSet rs, final int rowNum)
            throws SQLException
        {
            int index = rowNum;
            while (index < rowNum) {
                rs.next();
            }
            return new BijhoudingsaardInfoTuple(rs.getString("dataanvgel"), rs.getString("dateindegel"),
                rs.getString("tsreg"), rs.getString("tsverval"), rs.getInt("bijhaard"));
        }
    }
}
