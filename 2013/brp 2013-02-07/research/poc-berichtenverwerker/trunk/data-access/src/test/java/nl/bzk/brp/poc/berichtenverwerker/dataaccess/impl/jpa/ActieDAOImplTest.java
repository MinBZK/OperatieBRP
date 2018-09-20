/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.berichtenverwerker.dataaccess.impl.jpa;

import nl.bzk.brp.poc.berichtenverwerker.dataaccess.AbstractActieDAOTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;


/**
 * JUnit test voor de JPA specific {@link ActieDAOImpl} class.
 */
@ContextConfiguration({ "JPAGenericDAOImplTest-context.xml", "ActieDAOImplTest-context.xml" })
public final class ActieDAOImplTest extends AbstractActieDAOTest {

    // Actual tests are in the abstract Test Case

    @Test
    public void vindActieOpBasisVanId() {
        super.testVindActieOpBasisVanId();
    }

    @Test
    public void voegToeActie() {
        super.testVoegToeActie();
    }

}
