/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.view;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import nl.bzk.brp.model.beheer.AbstractModelIntegratieTest;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonInschrijvingModel;
import org.junit.Assert;
import org.junit.Test;

public class ActieTest extends AbstractModelIntegratieTest {

    @PersistenceUnit(unitName = "lezenEntityManagerFactory")
    private EntityManagerFactory emf;

    @Test
    public void test() {
        final EntityManager em = emf.createEntityManager();

        final AdministratieveHandelingModel administratieveHandeling = em.find(AdministratieveHandelingModel.class, 1000L);
        Assert.assertNotNull(administratieveHandeling);
        final List<Actie> acties =
                em.createQuery("select actie from Actie actie where administratieveHandeling = :administratieveHandeling")
                  .setParameter("administratieveHandeling", administratieveHandeling)
                  .getResultList();
        Assert.assertEquals(4, acties.size());

        final Actie actie = em.find(Actie.class, 101L);
        Assert.assertNotNull(actie);
        final Historie<HisPersoonIdentificatienummersModel> hisPersIdnrs = actie.getHisPersoonIdentificatienummer();
        Assert.assertNotNull(hisPersIdnrs);
        Assert.assertNotNull(hisPersIdnrs.getInhoud());
        Assert.assertEquals(1, hisPersIdnrs.getInhoud().size());
        //Assert.assertNotNull(hisPersIdnrs.getGeldigheid());
        //Assert.assertEquals(0, hisPersIdnrs.getGeldigheid().size());
        Assert.assertNotNull(hisPersIdnrs.getVerval());
        Assert.assertEquals(1, hisPersIdnrs.getVerval().size());
        Assert.assertNotNull(hisPersIdnrs.getVervalTbvMutatie());
        Assert.assertEquals(0, hisPersIdnrs.getVervalTbvMutatie().size());

        final Historie<HisPersoonInschrijvingModel> hisPersInschrijvingen = actie.getHisPersoonInschrijving();
        Assert.assertNotNull(hisPersInschrijvingen);
        Assert.assertNotNull(hisPersInschrijvingen.getInhoud());
        Assert.assertEquals(1, hisPersInschrijvingen.getInhoud().size());
        //Assert.assertNotNull(hisPersInschrijvingen.getGeldigheid());
        //Assert.assertEquals(0, hisPersInschrijvingen.getGeldigheid().size());
        Assert.assertNotNull(hisPersInschrijvingen.getVerval());
        Assert.assertEquals(0, hisPersInschrijvingen.getVerval().size());
        Assert.assertNotNull(hisPersInschrijvingen.getVervalTbvMutatie());
        Assert.assertEquals(0, hisPersInschrijvingen.getVervalTbvMutatie().size());
    }
}
