/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingAdoptieIngezeteneBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test klasse ten behoeve van de {@link ActieRepository} klasse. In deze unit test wordt de werkelijke
 * opslag getest met een fysieke database.
 */
public class AdministratieveHandelingRepositoryIntegratieTest extends AbstractRepositoryTestCase {

    public static final String SELECT_PARTIJ_FROM_KERN_ADMHND_WHERE_ID = "SELECT partij FROM Kern.admhnd WHERE id=?";
    @Inject
    private AdministratieveHandelingRepository administratieveHandelingRepository;

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    @Test
    public void testOpslaanNieuwAdministratieveHandeling() {
        final AdministratieveHandelingBericht ahb = new HandelingAdoptieIngezeteneBericht();
        ahb.setPartij(new PartijAttribuut(em.find(Partij.class, (short) 3)));
        final AdministratieveHandelingModel opgeslagenAh =
            administratieveHandelingRepository.opslaanNieuwAdministratieveHandeling(ahb);

        Assert.assertNotNull(opgeslagenAh.getID());
        Assert.assertEquals(SoortAdministratieveHandeling.ADOPTIE_INGEZETENE, opgeslagenAh.getSoort().getWaarde());

        final TypedQuery<AdministratieveHandelingModel> query =
            em.createQuery("SELECT ahd FROM AdministratieveHandelingModel ahd WHERE id=:id",
                AdministratieveHandelingModel.class);
        query.setParameter("id", opgeslagenAh.getID());

        final AdministratieveHandelingModel ahdDb = query.getSingleResult();
        Assert.assertNotNull(ahdDb);
        Assert.assertEquals(SoortAdministratieveHandeling.ADOPTIE_INGEZETENE, ahdDb.getSoort().getWaarde());
    }

    @Test
    public void testHaalAdministratieveHandeling() {
        final AdministratieveHandelingModel ah =
            administratieveHandelingRepository.haalAdministratieveHandeling(1000L);

        Assert.assertNotNull(ah);
        Assert.assertEquals(SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND, ah.getSoort().getWaarde());
    }

    @Test
    public void testBijwerkenBestaandeAdministratieveHandeling() {
        AdministratieveHandelingModel ahbModel;
        AdministratieveHandelingModel opgeslagenAh;

        final AdministratieveHandelingBericht ahb = new HandelingAdoptieIngezeteneBericht();
        ahb.setPartij(new PartijAttribuut(em.find(Partij.class, (short) 3)));
        ahbModel = new AdministratieveHandelingModel(ahb);

        // Eerst opslaan van een nog niet in EntityManager aanwezige administratieve handeling
        opgeslagenAh = administratieveHandelingRepository.opslaanAdministratieveHandeling(ahbModel);
        em.flush();

        final int partijId = this.jdbcTemplate.query(SELECT_PARTIJ_FROM_KERN_ADMHND_WHERE_ID, new ResultSetExtractor<Integer>() {

            @Override
            public Integer extractData(final ResultSet resultSet) throws SQLException, DataAccessException {
                resultSet.next();
                return resultSet.getInt(1);
            }
        }, opgeslagenAh.getID());
        // Test of partij juist id heeft in adm hand.
        Assert.assertEquals(3, partijId);

        // Partij wijzigen op in EntityManager aanwezige adm hand
        ahbModel = em.find(AdministratieveHandelingModel.class, opgeslagenAh.getID());
        ReflectionTestUtils.setField(ahbModel, "partij", new PartijAttribuut(em.find(Partij.class, (short) 4)));

        // Opslaan gewijzigde in EntityManager aanwezige adm hand
        opgeslagenAh = administratieveHandelingRepository.opslaanAdministratieveHandeling(ahbModel);
        em.flush();

        // Test of partij juist (gewijzigde) id heeft in adm hand
        final int partijId2 = this.jdbcTemplate.query(SELECT_PARTIJ_FROM_KERN_ADMHND_WHERE_ID, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(final ResultSet resultSet) throws SQLException, DataAccessException {
                resultSet.next();
                return resultSet.getInt(1);
            }
        }, opgeslagenAh.getID());
        Assert.assertEquals(4, partijId2);
        Assert.assertEquals(ahbModel.getID(), opgeslagenAh.getID());
    }

}
