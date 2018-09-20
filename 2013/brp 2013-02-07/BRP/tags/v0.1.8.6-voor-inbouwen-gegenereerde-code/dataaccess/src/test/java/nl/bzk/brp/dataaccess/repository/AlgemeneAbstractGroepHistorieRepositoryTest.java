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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.repository.jpa.historie.GroepHistorieRepository;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.groep.bericht.PersoonAanschrijvingGroepBericht;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAanschrijvingHisModel;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import org.junit.Test;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Algemene unit test voor het testen van de functionaliteit in de
 * {@link nl.bzk.brp.dataaccess.repository.jpa.historie.AbstractGroepHistorieRepository} class. Dit wordt gedaan
 * middels de {@link nl.bzk.brp.dataaccess.repository.jpa.historie.PersoonAanschrijvingHistorieRepository}, wat een
 * implementatie is van eerder genoemde abstracte class.
 * <p/>
 * De test data voor deze test bestaat uit een 5 tal records in de his_persaanschr tabel, te weten 3 actuele records
 * en 2 vervallen records. Daarnaast is er ook nog een extra actie beschikbaar met id '104'.
 * <p/>
 * -------------------------------------------------------------------------------------------------------
 * | id  | pers | dataanvgel | dateindegel | geslnaamaanschr | tsreg               | tsverval            |
 * -------------------------------------------------------------------------------------------------------
 * | 101 | 2    | 2010-01-01 |             | Test1           | 2010-01-01 10:00:00 | 2011-01-02 12:12:12 |
 * | 102 | 2    | 2010-01-01 | 2011-01-01  | Test1           | 2011-01-02 12:12:12 |                     |
 * | 103 | 2    | 2011-01-01 |             | Test2           | 2011-01-02 12:12:12 | 2011-11-12 13:13:13 |
 * | 104 | 2    | 2011-01-01 | 2011-11-11  | Test2           | 2011-11-12 13:13:13 |                     |
 * | 105 | 2    | 2011-11-11 |             | Test3           | 2011-11-12 13:13:13 |                     |
 * -------------------------------------------------------------------------------------------------------
 */
public class AlgemeneAbstractGroepHistorieRepositoryTest extends AbstractRepositoryTestCase {

    private static final String QUERY_ACTUELE_RECORDS =
        "select * from kern.his_persaanschr where tsverval is null order by dataanvgel, tsreg";
    private static final String QUERY_VERVALLEN_RECORDS =
        "select * from kern.his_persaanschr where tsverval is not null order by dataanvgel, tsreg";

    @Inject
    private GroepHistorieRepository<PersoonModel, PersoonAanschrijvingHisModel> persoonAanschrijvingHistorieRepository;
    @PersistenceContext
    private EntityManager em;

    @Test
    public void testNieuwRecordZonderEindeEnEenDeelsOverlap() {
        persoonAanschrijvingHistorieRepository.persisteerHistorie(bouwPersoon("Tester"), bouwActie(),
            new Datum(20120101), null);
        em.flush();

        // Verwacht nu 7 records (4 actueel, 3 vervallen)
        Assert.assertEquals(7, countRowsInTable("kern.his_persaanschr"));
        testVerwachteActueleRecords(
            new AanschrijvingInfoTuple("20100101", "20110101", "2011-01-02 12:12:12", null, "Test1"),
            new AanschrijvingInfoTuple("20110101", "20111111", "2011-11-12 13:13:13", null, "Test2"),
            new AanschrijvingInfoTuple("20111111", "20120101", "2012-12-12 12:12:12", null, "Test3"),
            new AanschrijvingInfoTuple("20120101", null, "2012-12-12 12:12:12", null, "Tester"));
        testVerwachteVervallenRecords(
            new AanschrijvingInfoTuple("20100101", null, "2010-01-01 10:00:00", "2011-01-02 12:12:12", "Test1"),
            new AanschrijvingInfoTuple("20110101", null, "2011-01-02 12:12:12", "2011-11-12 13:13:13", "Test2"),
            new AanschrijvingInfoTuple("20111111", null, "2011-11-12 13:13:13", "2012-12-12 12:12:12", "Test3"));
    }


    @Test
    public void testNieuwRecordZonderEindeEnEenDeelsEnGeheelOverlap() {
        persoonAanschrijvingHistorieRepository.persisteerHistorie(bouwPersoon("Tester"), bouwActie(),
            new Datum(20110909), null);
        em.flush();

        // Verwacht nu 7 records (3 actueel, 4 vervallen)
        Assert.assertEquals(7, countRowsInTable("kern.his_persaanschr"));
        testVerwachteActueleRecords(
            new AanschrijvingInfoTuple("20100101", "20110101", "2011-01-02 12:12:12", null, "Test1"),
            new AanschrijvingInfoTuple("20110101", "20110909", "2012-12-12 12:12:12", null, "Test2"),
            new AanschrijvingInfoTuple("20110909", null, "2012-12-12 12:12:12", null, "Tester"));
        testVerwachteVervallenRecords(
            new AanschrijvingInfoTuple("20100101", null, "2010-01-01 10:00:00", "2011-01-02 12:12:12", "Test1"),
            new AanschrijvingInfoTuple("20110101", null, "2011-01-02 12:12:12", "2011-11-12 13:13:13", "Test2"),
            new AanschrijvingInfoTuple("20110101", "20111111", "2011-11-12 13:13:13", "2012-12-12 12:12:12", "Test2"),
            new AanschrijvingInfoTuple("20111111", null, "2011-11-12 13:13:13", "2012-12-12 12:12:12", "Test3"));
    }

    @Test
    public void testNieuwRecordMetEindeEnOverlaptTweeBestaande() {
        persoonAanschrijvingHistorieRepository.persisteerHistorie(bouwPersoon("Tester"), bouwActie(),
            new Datum(20110909), new Datum(20120101));
        em.flush();

        // Verwacht nu 8 records (4 actueel, 4 vervallen)
        Assert.assertEquals(8, countRowsInTable("kern.his_persaanschr"));
        testVerwachteActueleRecords(
            new AanschrijvingInfoTuple("20100101", "20110101", "2011-01-02 12:12:12", null, "Test1"),
            new AanschrijvingInfoTuple("20110101", "20110909", "2012-12-12 12:12:12", null, "Test2"),
            new AanschrijvingInfoTuple("20110909", "20120101", "2012-12-12 12:12:12", null, "Tester"),
            new AanschrijvingInfoTuple("20120101", null, "2012-12-12 12:12:12", null, "Test3"));
        testVerwachteVervallenRecords(
            new AanschrijvingInfoTuple("20100101", null, "2010-01-01 10:00:00", "2011-01-02 12:12:12", "Test1"),
            new AanschrijvingInfoTuple("20110101", null, "2011-01-02 12:12:12", "2011-11-12 13:13:13", "Test2"),
            new AanschrijvingInfoTuple("20110101", "20111111", "2011-11-12 13:13:13", "2012-12-12 12:12:12", "Test2"),
            new AanschrijvingInfoTuple("20111111", null, "2011-11-12 13:13:13", "2012-12-12 12:12:12", "Test3"));
    }

    /**
     * Test of de verwachte aanschrijving tuples gelijk zijn aan de gevonden actuele records. Hierbij wordt uitgegaan
     * van een volgorde gebaseerd op datum aanvang geldigheid.
     *
     * @param verwachteAanschrijvingElementen de verwachte aanschrijving elementen
     */
    private void testVerwachteActueleRecords(final AanschrijvingInfoTuple... verwachteAanschrijvingElementen) {
        List<AanschrijvingInfoTuple> gevondenAanschrijvingElementen =
            simpleJdbcTemplate.query(QUERY_ACTUELE_RECORDS, new AanschrijvingRowMapper());
        testVerwachteRecords(gevondenAanschrijvingElementen, verwachteAanschrijvingElementen);
    }

    /**
     * Test of de verwachte aanschrijving tuples gelijk zijn aan de gevonden vervallen records. Hierbij wordt uitgegaan
     * van een volgorde gebaseerd op datum aanvang geldigheid.
     *
     * @param verwachteAanschrijvingElementen de verwachte aanschrijving elementen
     */
    private void testVerwachteVervallenRecords(final AanschrijvingInfoTuple... verwachteAanschrijvingElementen) {
        List<AanschrijvingInfoTuple> gevondenAanschrijvingElementen =
            simpleJdbcTemplate.query(QUERY_VERVALLEN_RECORDS, new AanschrijvingRowMapper());
        testVerwachteRecords(gevondenAanschrijvingElementen, verwachteAanschrijvingElementen);
    }

    /**
     * Test of de verwachte aanschrijving tuples gelijk zijn aan de gevonden records. Hierbij wordt uitgegaan
     * van een volgorde gebaseerd op datum aanvang geldigheid.
     *
     * @param verwachteAanschrijvingElementen de verwachte aanschrijving elementen
     */
    private void testVerwachteRecords(final List<AanschrijvingInfoTuple> gevondenAanschrijvingElementen,
        final AanschrijvingInfoTuple... verwachteAanschrijvingElementen)
    {
        Assert.assertEquals(verwachteAanschrijvingElementen.length, gevondenAanschrijvingElementen.size());
        int i = 0;
        for (AanschrijvingInfoTuple tuple : verwachteAanschrijvingElementen) {
            Assert.assertEquals("Fout in aanschrijving record " + i, tuple.aanschrijving,
                gevondenAanschrijvingElementen.get(i).aanschrijving);
            Assert.assertEquals("Fout in datum aanvang record " + i, tuple.datumAanvang,
                gevondenAanschrijvingElementen.get(i).datumAanvang);
            Assert.assertEquals("Fout in datum einde record " + i, tuple.datumEinde,
                gevondenAanschrijvingElementen.get(i).datumEinde);
            Assert.assertEquals("Fout in tijdstip registratie record " + i, tuple.tijdstipRegistratie,
                gevondenAanschrijvingElementen.get(i).tijdstipRegistratie);
            Assert.assertEquals("Fout in tijdstip vervallen record " + i, tuple.tijdstipVervallen,
                gevondenAanschrijvingElementen.get(i).tijdstipVervallen);
            i++;
        }
    }

    /**
     * Instantieert en retourneert een {@link PersoonModel} instantie met de id '2' en een aanschrijving met opgegeven
     * naam aanschrijving.
     *
     * @param naamAanschrijving de aanschrijving
     * @return een nieuwe persoon met opgegeven aanschrijving
     */
    private PersoonModel bouwPersoon(final String naamAanschrijving) {
        PersoonAanschrijvingGroepBericht aanschrijvingGroep = new PersoonAanschrijvingGroepBericht();
        aanschrijvingGroep.setIndAanschrijvingAlgorthmischAfgeleid(JaNee.Ja);
        aanschrijvingGroep.setGeslachtsnaam(new Geslachtsnaamcomponent(naamAanschrijving));

        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setAanschrijving(aanschrijvingGroep);

        PersoonModel persoon = new PersoonModel(persoonBericht);
        ReflectionTestUtils.setField(persoon, "id", 2);
        return persoon;
    }

    /**
     * Instantieert en retourneert een {@link ActieModel} instantie met de id '104'.
     *
     * @return een nieuwe actie model met id 104
     */
    private ActieModel bouwActie() {
        ActieBericht actieBericht = new ActieBericht();
        Calendar tijdstipRegistratie = Calendar.getInstance();
        tijdstipRegistratie.set(Calendar.YEAR, 2012);
        tijdstipRegistratie.set(Calendar.MONTH, Calendar.DECEMBER);
        tijdstipRegistratie.set(Calendar.DAY_OF_MONTH, 12);
        tijdstipRegistratie.set(Calendar.HOUR_OF_DAY, 12);
        tijdstipRegistratie.set(Calendar.MINUTE, 12);
        tijdstipRegistratie.set(Calendar.SECOND, 12);
        tijdstipRegistratie.set(Calendar.MILLISECOND, 0);
        actieBericht.setTijdstipRegistratie(new DatumTijd(tijdstipRegistratie.getTime()));

        ActieModel actie = new ActieModel(actieBericht);
        ReflectionTestUtils.setField(actie, "id", 104L);

        return actie;
    }


    /** Tuple van 5 elementen die een aanschrijving record uit de database representeren. */
    private final class AanschrijvingInfoTuple {

        private final String datumAanvang;
        private final String datumEinde;
        private final String tijdstipRegistratie;
        private final String tijdstipVervallen;
        private final String aanschrijving;

        /** Standaard constructor die alle velden zet. */
        private AanschrijvingInfoTuple(final String datumAanvang, final String datumEinde,
            final String tijdstipRegistratie, final String tijdstipVervallen, final String aanschrijving)
        {
            this.aanschrijving = aanschrijving;
            this.datumAanvang = datumAanvang;
            this.datumEinde = datumEinde;
            this.tijdstipRegistratie = tijdstipRegistratie;
            this.tijdstipVervallen = tijdstipVervallen;
        }

    }

    /**
     * Implementatie van de {@link RowMapper} interface die
     * {@link nl.bzk.brp.dataaccess.repository.AlgemeneAbstractGroepHistorieRepositoryTest.AanschrijvingInfoTuple}
     * instanties uitleest uit een SQL Query.
     */
    private class AanschrijvingRowMapper implements RowMapper<AanschrijvingInfoTuple> {
        @Override
        public AanschrijvingInfoTuple mapRow(final ResultSet rs, final int rowNum)
            throws SQLException
        {
            int index = rowNum;
            while (index < rowNum) {
                rs.next();
            }
            return new AanschrijvingInfoTuple(rs.getString("dataanvgel"), rs.getString("dateindegel"),
                rs.getString("tsreg"), rs.getString("tsverval"), rs.getString("geslnaamaanschr"));
        }
    }
}
