/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.dataaccess;

import java.util.List;

import nl.bzk.brp.metaregister.model.Laag;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.SetOfModel;
import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;


/**
 * Abstracte superclass voor repository (persistence) testcases.
 */
@ContextConfiguration
public abstract class AbstractRepositoryTestCase extends AbstractTransactionalJUnit4SpringContextTests {

    @Before
    public void init() {
        Laag.LOGISCH.set();
        SetOfModel.setWaardes(SetOfModel.MODEL, SetOfModel.BEIDE);
    }

    protected ObjectType getObjectTypeOpNaam(final List<ObjectType> objectTypes, final String name) {
        ObjectType resultaat = null;
        for (ObjectType objectType : objectTypes) {
            if (name.equals(objectType.getNaam())) {
                resultaat = objectType;
            }
        }
        return resultaat;
    }

}
