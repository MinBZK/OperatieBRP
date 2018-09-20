/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.dal.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;
import nl.bzk.brp.pocmotor.dal.AbstractRepositoryTest;
import nl.bzk.brp.pocmotor.dal.logisch.PersoonAdresLGMRepository;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.His_PersoonAdres;
import org.junit.Test;

public class JpaPersoonAdresDAOTest extends AbstractRepositoryTest {

    @Inject
    private PersoonAdresLGMRepository persoonAdresLGMRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testOphalenPersoonAdresCLaag() {
        His_PersoonAdres hisAdres = em.find(His_PersoonAdres.class, 1L);
        Assert.assertNotNull(hisAdres);
    }
}
