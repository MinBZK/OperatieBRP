/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.migratie.conversietabel;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import nl.bzk.algemeenbrp.test.dal.DBUnit.InsertBefore;

import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RedenOntbindingHuwelijkPartnerschapConversietabelTest extends AbstractDatabaseTest {

    @Inject
    private ConversietabelFactory conversietabelFactory;

    @Inject
    private EntityManagerFactory emf;

    private Conversietabel<Lo3RedenOntbindingHuwelijkOfGpCode, BrpRedenEindeRelatieCode> conversietabel;

    @Before
    public void setup() {
        // ehCacheCacheManager.getCacheManager().clearAll();
        emf.unwrap(HibernateEntityManagerFactory.class).getCache().evictAll();
        conversietabel = conversietabelFactory.createRedenEindeRelatieConversietabel();
    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml"})
    @Test
    public void testRedenOntbindingConversieTabel() {

        final Lo3RedenOntbindingHuwelijkOfGpCode lo3Input = new Lo3RedenOntbindingHuwelijkOfGpCode("O");
        final BrpRedenEindeRelatieCode brpRedenEindeRelatieCode = conversietabel.converteerNaarBrp(lo3Input);
        Assert.assertEquals(new BrpRedenEindeRelatieCode('O'), brpRedenEindeRelatieCode);

        final Lo3RedenOntbindingHuwelijkOfGpCode terug = conversietabel.converteerNaarLo3(brpRedenEindeRelatieCode);
        Assert.assertEquals(lo3Input, terug);
    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml"})
    @Test(expected = IllegalArgumentException.class)
    public void testWaardeNietGevondenLo3NaarBrp() {

        final BrpRedenEindeRelatieCode result = conversietabel.converteerNaarBrp(new Lo3RedenOntbindingHuwelijkOfGpCode("X"));
        Assert.fail("Exceptie verwacht: waarde was: " + result);
    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml"})
    @Test(expected = IllegalArgumentException.class)
    public void testWaardeNietGevondenBrpNaarLo3() {

        final Lo3RedenOntbindingHuwelijkOfGpCode result = conversietabel.converteerNaarLo3(new BrpRedenEindeRelatieCode('X'));
        Assert.fail("Exceptie verwacht: waarde was: " + result);
    }
}
