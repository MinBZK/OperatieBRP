/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.pojo;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.historie.PersoonAdresHistorieRepository;
import nl.bzk.brp.dataaccess.repository.jpa.historie.PersoonGeboorteHistorieRepository;
import nl.bzk.brp.dataaccess.repository.jpa.historie.PersoonNationaliteitStandaardHistorieRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAdresHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.pojo.PersoonHisModel;
import nl.bzk.brp.model.objecttype.pojo.PersoonProxy;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Ignore;
import org.junit.Test;

public class PojoProxyTest extends AbstractRepositoryTestCase {

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager em;

    @Inject
    private PersoonAdresHistorieRepository persoonAdresHistorieRepository;

    @Inject
    private PersoonRepository persoonRepository;

    @Test
    public void testAdresHistorie() throws Exception {
        // given
        PersoonAdresModel adres = em.find(PersoonAdresModel.class, 1101);
        Assert.assertNotNull(adres);

        List<PersoonAdresHisModel> historie = persoonAdresHistorieRepository.haalopHistorie(adres, true);

        PersoonHisModel hisPersoon = new PersoonHisModel(-1);
        hisPersoon.setAdressen(historie);

        Assume.assumeTrue(hisPersoon.getAdressen().size() == 3);

        // when
        PersoonProxy actueel = new PersoonProxy(hisPersoon);
        PersoonProxy tweeduizendelf = new PersoonProxy(hisPersoon, new Datum(20110606));

        // then
        Collection<PersoonAdresModel> nuAdressen = actueel.getAdressen();
        Assert.assertEquals(2, nuAdressen.size());

        Collection<PersoonAdresModel> toenAdressen = tweeduizendelf.getAdressen();
        Assert.assertEquals(1, toenAdressen.size());
    }

}
