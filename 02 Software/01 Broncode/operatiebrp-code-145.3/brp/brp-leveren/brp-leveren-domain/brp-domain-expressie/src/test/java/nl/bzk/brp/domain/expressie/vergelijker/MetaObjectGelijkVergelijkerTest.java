/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.vergelijker;

import static org.junit.Assert.assertEquals;

import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.MetaObjectLiteral;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import org.junit.Test;

public class MetaObjectGelijkVergelijkerTest {

    @Test
    public void test() {
        final MetaObjectGelijkVergelijker vergelijker = new MetaObjectGelijkVergelijker();
        final MetaObject metaObject1 = TestBuilders.LEEG_PERSOON;
        final MetaObject metaObject2 = TestBuilders.maakPersoonMetHandelingen(1).getMetaObject();
        final MetaObjectLiteral metaObjectLiteral1a = new MetaObjectLiteral(metaObject1, ExpressieType.BRP_METAOBJECT);
        final MetaObjectLiteral metaObjectLiteral1b = new MetaObjectLiteral(metaObject1, ExpressieType.BRP_METAOBJECT);
        final MetaObjectLiteral metaObjectLiteral = new MetaObjectLiteral(metaObject2, ExpressieType.BRP_METAOBJECT);

        assertEquals(BooleanLiteral.WAAR, vergelijker.apply(metaObjectLiteral1a, metaObjectLiteral1a));
        assertEquals(BooleanLiteral.WAAR, vergelijker.apply(metaObjectLiteral1a, metaObjectLiteral1b));
        assertEquals(BooleanLiteral.ONWAAR, vergelijker.apply(metaObjectLiteral1a, metaObjectLiteral));
    }
}
