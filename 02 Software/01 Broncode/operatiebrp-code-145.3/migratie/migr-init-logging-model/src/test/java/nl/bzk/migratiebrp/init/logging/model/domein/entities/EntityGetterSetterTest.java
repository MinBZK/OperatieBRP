/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.model.domein.entities;

import nl.bzk.migratiebrp.init.logging.model.testutils.GetterSetterTester;
import org.junit.Test;

public class EntityGetterSetterTest {

    @Test
    public void test() throws ReflectiveOperationException {
        new GetterSetterTester().testEntities("nl.bzk.migratiebrp.init.logging.model.domein.entities");

        new GetterSetterTester().test(new InitVullingAfnemersindicatieStapelPk());
    }

}
