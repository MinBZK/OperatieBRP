/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein;

import nl.bzk.migratiebrp.synchronisatie.dal.testutils.GetterSetterTester;
import org.junit.Test;

public class EntityGetterSetterTest {

    @Test
    public void blokkering() throws ReflectiveOperationException {
        new GetterSetterTester().testEntities("nl.bzk.migratiebrp.synchronisatie.dal.domein.blokkering.entity");
    }

    @Test
    public void brpAutaut() throws ReflectiveOperationException {
        new GetterSetterTester().testEntities("nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity");
    }

    @Test
    public void brpIst() throws ReflectiveOperationException {
        new GetterSetterTester().testEntities("nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.ist.entity");
    }

    @Test
    public void brpKern() throws ReflectiveOperationException {
        new GetterSetterTester().testEntities("nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity");
    }

    @Test
    public void brpLev() throws ReflectiveOperationException {
        new GetterSetterTester().testEntities("nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.lev.entity");
    }

    @Test
    public void conversietabel() throws ReflectiveOperationException {
        new GetterSetterTester().testEntities("nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity");
    }

    @Test
    public void logging() throws ReflectiveOperationException {
        new GetterSetterTester().testEntities("nl.bzk.migratiebrp.synchronisatie.dal.domein.logging.entity");
    }
}
