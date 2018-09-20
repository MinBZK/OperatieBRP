/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.view;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import nl.bzk.brp.model.beheer.AbstractModelIntegratieTest;
import org.junit.Assert;
import org.junit.Test;

public class ActieViewTest extends AbstractModelIntegratieTest {

    @PersistenceUnit(unitName = "lezenEntityManagerFactory")
    private EntityManagerFactory emf;

    @Test
    public void test() {
        final EntityManager em = emf.createEntityManager();
        final Actie actie = em.find(Actie.class, 101L);
        final ActieView actieView = Acties.asActieView(actie);
        Assert.assertNotNull(actieView);
    }

}
