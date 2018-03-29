/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.dal.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusOnderzoek;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.test.dal.DBUnit;
import nl.bzk.algemeenbrp.test.dal.DBUnitBrpUtil;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.bijhouding.dal.AbstractRepositoryTest;
import nl.bzk.brp.bijhouding.dal.OnderzoekRepository;
import org.dbunit.DatabaseUnitException;
import org.junit.Before;
import org.junit.Test;

/**
 * Testen voor {@link OnderzoekRepositoryImpl}.
 */
public class OnderzoekRepositoryImplTest extends AbstractRepositoryTest {

    @Inject
    private OnderzoekRepository onderzoekRepository;

    @Inject
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;

    @Inject
    private DBUnitBrpUtil dbUnitBrpUtil;

    @Before
    public void setup() throws DatabaseUnitException, SQLException {
        dbUnitBrpUtil.resetDB(this.getClass(), LoggerFactory.getLogger(), false);
        doInsertBefore();
    }

    @DBUnit.InsertBefore("OnderzoekTestData.xml")
    @Test
    public void testFindById() {
        final Onderzoek onderzoek = onderzoekRepository.findById(1L);
        assertNotNull(onderzoek);
        assertEquals(StatusOnderzoek.IN_UITVOERING, onderzoek.getStatusOnderzoek());
        assertNull(onderzoekRepository.findById(0L));
        assertEquals(1, onderzoek.getGegevenInOnderzoekSet().size());
        assertNotNull(onderzoek.getGegevenInOnderzoekSet().iterator().next().getEntiteitOfVoorkomen());
    }
}
