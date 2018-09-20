/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.repository.historie.GroepFormeleHistorieRepository;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.groep.bericht.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.util.ReflectionTestUtils;


public class AlgemeenAbstractGroepFormeleHistorieRepositoryTest extends AbstractRepositoryTestCase {

    private static final String QUERY_ACTUELE_RECORDS   =
        "select * from kern.his_persgeboorte where tsverval is null and pers = :persId order by tsreg desc";
    private static final String QUERY_VERVALLEN_RECORDS =
        "select * from kern.his_persgeboorte where tsverval is not null and pers = :persId order by tsreg desc";

    @Inject
    private GroepFormeleHistorieRepository<PersoonModel> persoonGeboorteHistorieRepository;

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager em;

    @Test
    public void testNieuwHistorie() {
        Integer persoonId = 2;
        persoonGeboorteHistorieRepository.persisteerHistorie(bouwPersoon(persoonId, 19800101, 2), bouwActie());

        em.flush();

        // Verwacht nu 1 bestaande record uit testdata.xml en 1 nieuw toegevoegd.
        Assert.assertEquals(2, countRowsInTable("kern.his_persgeboorte"));
        // Test records voor huidige persoon met id 2
        testVerwachteActueleRecords(persoonId, new GeboorteInfoTuple("2012-12-12 12:12:12", null, 2, 19800101));
    }

    @Test
    public void testCorrectie() {
        Integer persoonId = 1;

        persoonGeboorteHistorieRepository.persisteerHistorie(bouwPersoon(persoonId, 19800101, 1), bouwActie());

        em.flush();

        // Verwacht nu 1 record uit testdata.xml vervallen en 1 nieuw toegevoegd.
        Assert.assertEquals(2, countRowsInTable("kern.his_persgeboorte"));
        // Test records voor huidige persoon met id 1
        testVerwachteActueleRecords(persoonId, new GeboorteInfoTuple("2012-12-12 12:12:12", null, 1, 19800101));

        testVerwachteVervallenRecords(persoonId, new GeboorteInfoTuple("2012-02-02 13:13:13.5", "2012-12-12 12:12:12",
            2, 20120101));
    }

    /**
     * Test of de verwachte tuples gelijk zijn aan de gevonden actuele records.
     *
     * @param persoonId technisch id van persoon
     * @param verwachteGeboorteElementen de verwachte geboorte elementen
     */
    private void testVerwachteActueleRecords(final Integer persoonId,
        final GeboorteInfoTuple... verwachteGeboorteElementen)
    {
        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("persId", persoonId);

        List<GeboorteInfoTuple> gevondenElementen =
            this.simpleJdbcTemplate.query(QUERY_ACTUELE_RECORDS, new GeboorteRowMapper(), param);
        testVerwachteRecords(gevondenElementen, verwachteGeboorteElementen);
    }

    /**
     * Test of de verwachte tuples gelijk zijn aan de gevonden vervallen records.
     *
     * @param persoonId technische id van persoon
     * @param verwachteGeboorteElementen de verwachte geboorte elementen
     */
    private void testVerwachteVervallenRecords(final Integer persoonId,
        final GeboorteInfoTuple... verwachteGeboorteElementen)
    {
        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("persId", persoonId);

        List<GeboorteInfoTuple> gevondenElementen =
            this.simpleJdbcTemplate.query(QUERY_VERVALLEN_RECORDS, new GeboorteRowMapper(), param);
        testVerwachteRecords(gevondenElementen, verwachteGeboorteElementen);
    }

    /**
     * Test of de verwachte geboorte tuples gelijk zijn aan de gevonden records.
     *
     * @param verwachteGeboorteElementen de verwachte geboorte elementen
     */
    private void testVerwachteRecords(final List<GeboorteInfoTuple> gevondenGeboorteElementen,
        final GeboorteInfoTuple... verwachteGeboorteElementen)
    {
        Assert.assertEquals(verwachteGeboorteElementen.length, gevondenGeboorteElementen.size());
        int i = 0;
        for (GeboorteInfoTuple tuple : verwachteGeboorteElementen) {
            Assert.assertEquals("Fout in geboorte record " + i, tuple.datumGeboorte,
                gevondenGeboorteElementen.get(i).datumGeboorte);
            Assert.assertEquals("Fout in datum aanvang record " + i, tuple.landGeboorte,
                gevondenGeboorteElementen.get(i).landGeboorte);
            Assert.assertEquals("Fout in tijdstip registratie record " + i, tuple.tijdstipRegistratie,
                gevondenGeboorteElementen.get(i).tijdstipRegistratie);
            Assert.assertEquals("Fout in tijdstip vervallen record " + i, tuple.tijdstipVervallen,
                gevondenGeboorteElementen.get(i).tijdstipVervallen);
            i++;
        }
    }

    /**
     * Instantieert en retourneert een {@link PersoonModel} instantie met de opgegeven id en een geboorte met opgegeven
     * geboorte land en geboorte datum.
     *
     * @param persoonId persoonId
     * @param datumGeboorte de geboortedatum
     * @param landId technisch id van land
     * @return een nieuwe persoon met opgegeven geboorte
     */
    private PersoonModel bouwPersoon(final Integer persoonId, final Integer datumGeboorte, final Integer landId) {
        Land land = new Land();
        ReflectionTestUtils.setField(land, "id", landId);

        PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();
        geboorte.setDatumGeboorte(new Datum(datumGeboorte));

        geboorte.setLandGeboorte(land);

        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setGeboorte(geboorte);

        PersoonModel persoon = new PersoonModel(persoonBericht);
        ReflectionTestUtils.setField(persoon, "id", persoonId);

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

    /** Tuple van 4 elementen die een geboorte record uit de database representeren. */
    private final class GeboorteInfoTuple {

        private String  tijdstipRegistratie;
        private String  tijdstipVervallen;
        private Integer landGeboorte;
        private Integer datumGeboorte;

        /** Standaard constructor die alle velden zet. */
        private GeboorteInfoTuple(final String tijdstipRegistratie, final String tijdstipVervallen,
            final Integer landGeboorte, final Integer datumGeboorte)
        {
            this.tijdstipRegistratie = tijdstipRegistratie;
            this.tijdstipVervallen = tijdstipVervallen;
            this.landGeboorte = landGeboorte;
            this.datumGeboorte = datumGeboorte;
        }
    }

    /**
     * Implementatie van de {@link RowMapper} interface die
     * {@link nl.bzk.brp.dataaccess.repository.AlgemeenAbstractGroepFormeleHistorieRepositoryTest.GeboorteInfoTuple}
     * instanties
     * uitleest uit een SQL Query.
     */
    private class GeboorteRowMapper implements RowMapper<GeboorteInfoTuple> {

        @Override
        public GeboorteInfoTuple mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            int index = rowNum;
            while (index < rowNum) {
                rs.next();
            }
            return new GeboorteInfoTuple(rs.getString("tsreg"), rs.getString("tsverval"), rs.getInt("landgeboorte"),
                rs.getInt("datgeboorte"));
        }
    }

}
