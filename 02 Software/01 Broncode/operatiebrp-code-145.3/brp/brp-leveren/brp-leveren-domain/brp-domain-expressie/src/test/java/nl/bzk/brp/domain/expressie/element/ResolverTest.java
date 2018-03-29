/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.element;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.ModelIndex;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class ResolverTest {


    @Test
    public void testResolver() {
        Resolver resolver = (modelIndex, o) -> Collections.emptyList();
        Assert.assertTrue(resolver.postFilter());

        Assert.assertTrue(resolver.matchContext(TestBuilders.LEEG_PERSOON));
        Assert.assertFalse(resolver.matchContext(MetaObject.maakBuilder().metObjectElement(Element.KIND.getId()).eindeObject().build()));
        Assert.assertFalse(resolver.matchContext(
                Iterables.getOnlyElement(TestBuilders.LEEG_PERSOON.getGroepen())));

        Assert.assertEquals(Prioriteit.HOOG, resolver.prioriteit());
    }

    @Test
    public void testResolverPrio() {

        Resolver prioLaag = new Resolver() {
            @Override
            public Collection apply(final ModelIndex modelIndex, final Object o) {
                return null;
            }

            @Override
            public Prioriteit prioriteit() {
                return Prioriteit.LAAG;
            }
        };

        Resolver prioMiddel = new Resolver() {
            @Override
            public Collection apply(final ModelIndex modelIndex, final Object o) {
                return null;
            }

            @Override
            public Prioriteit prioriteit() {
                return Prioriteit.MIDDEL;
            }
        };

        Resolver prioHoog = new Resolver() {
            @Override
            public Collection apply(final ModelIndex modelIndex, final Object o) {
                return null;
            }

            @Override
            public Prioriteit prioriteit() {
                return Prioriteit.HOOG;
            }
        };

        final ArrayList<Resolver> resolvers = Lists.newArrayList(prioMiddel, prioHoog, prioLaag);
        resolvers.sort(Resolver.prioriteitComparator());

        Assert.assertArrayEquals(new Resolver[]{prioHoog, prioMiddel, prioLaag}, resolvers.toArray(new Resolver[3]));
    }
}
