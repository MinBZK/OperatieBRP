/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.berichtenverwerker.dataaccess;

import java.math.BigDecimal;

import nl.bzk.brp.poc.berichtenverwerker.model.Pers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * Abstracte (generieke) Unit test voor de {@link PersoonDAO} class en de in deze DAO gedefinieerde methodes.
 */
public abstract class AbstractPersoonDAOTest extends AbstractDAOTest {

    @Autowired
    private PersoonDAO persoonDao;

    /**
     * Unit test voor de {@link PersoonDAO#vindPersoonOpBasisVanId(long)} methode.
     */
    public final void testVindPersoonOpBasisVanId() {
        Pers result = persoonDao.vindPersoonOpBasisVanId(1L);

        assertNotNull(result);
        assertEquals("BSN is niet gelijk", new BigDecimal("1234567"), result.getBsn());
        assertEquals("ID is niet gelijk", 1L, result.getId());
        assertEquals("Naam is niet gelijk", "Tester", result.getGeslnaam());
    }

    /**
     * Unit test voor de {@link PersoonDAO#vindPersoonOpBasisVanBsn(BigDecimal)} methode.
     */
    public final void testVindPersoonOpBasisVanBsn() {
        BigDecimal bsn = new BigDecimal("1234567");
        Pers result = persoonDao.vindPersoonOpBasisVanBsn(bsn);

        assertNotNull(result);
        assertEquals("BSN is niet gelijk", bsn.intValue(), result.getBsn().intValue());
        assertEquals("ID is niet gelijk", 1L, result.getId());
        assertEquals("Naam is niet gelijk", "Tester", result.getGeslnaam());
    }

    @Override
    protected final String getXmlDataSetFileName() {
        return "PersoonDAOImpl.xml";
    }

}
