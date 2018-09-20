/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de methodes in de (@link AbstractBerichtEntiteitGroep} klasse.
 */
public class GeneriekeAbstractBerichtEntiteitGroepTest {

    @Test
    public void testGettersEnSetters() {
        final BerichtEntiteitGroep groep = bouwStandaardBerichtEntiteitGroep();

        Assert.assertNull(groep.getVoorkomenId());

        groep.setVoorkomenId("Test");
        Assert.assertEquals("Test", groep.getVoorkomenId());
        groep.setVoorkomenId("Anders");
        Assert.assertEquals("Anders", groep.getVoorkomenId());
    }

    /**
     * Instantieert en retourneert een {@link AbstractBerichtEntiteitGroep} instantie.
     *
     * @return een {@link AbstractBerichtEntiteitGroep} instantie.
     */
    private AbstractBerichtEntiteitGroep bouwStandaardBerichtEntiteitGroep() {
        return new AbstractBerichtEntiteitGroep() {

            @Override
            public List<Attribuut> getAttributen() {
                return new ArrayList<>();
            }

            @Override
            public Integer getMetaId() {
                return null;
            }

            @Override
            public boolean bevatElementMetMetaId(final Integer id) {
                return false;
            }
        };
    }
}
