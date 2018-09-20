/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.selectie.RelatieSelectieFilter;
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.gedeeld.SoortPersoon;
import nl.bzk.brp.model.gedeeld.SoortRelatie;
import org.junit.Test;

public class RelatieRepositoryTest extends AbstractRepositoryTestCase {

//    @Inject
//    private PersoonRepository persoonRepository;
//
//    @Inject
//    private ReferentieDataRepository referentieDataRepository;

    @Inject
    private RelatieRepository relatieRepository;

//    @Inject
//    private PersoonNationaliteitHistorieRepository persoonNationaliteitHistorieRepository;
//

    @Test
    public void testZoekPartnersNull() throws Exception {
        List<Long> personen = relatieRepository.haalopPartners(null);
        Assert.assertNotNull(personen);
        Assert.assertTrue(personen.isEmpty());
    }

    @Test
    public void testZoekPartners() throws Exception {
        List<Long> personen = relatieRepository.haalopPartners(8731137L);
        Assert.assertNotNull(personen);
        Assert.assertEquals(2, personen.size());
    }

    @Test
    public void testZoekEchtgenoten() throws Exception {
        List<Long> personen = relatieRepository.haalopEchtgenoten(8731137L);
        Assert.assertNotNull(personen);
        Assert.assertEquals(2, personen.size());
    }

    @Test
    public void testZoekEchtgenotenOp20121231() throws Exception {
        List<Long> personen = relatieRepository.haalopEchtgenoten(8731137L, 20121231);
        Assert.assertNotNull(personen);
        Assert.assertEquals(2, personen.size());
    }

    @Test
    public void testZoekEchtgenotenOp20151231() throws Exception {
        List<Long> personen = relatieRepository.haalopEchtgenoten(8731137L, 20151231);
        Assert.assertNotNull(personen);
        Assert.assertEquals(0, personen.size());
    }

    @Test
    public void testZoekEchtgenotenOp19631231() throws Exception {
        List<Long> personen = relatieRepository.haalopEchtgenoten(8731137L, 19631231);
        Assert.assertNotNull(personen);
        Assert.assertEquals(0, personen.size());
    }

    @Test
    public void testZoekKinderen() throws Exception {
        List<Long> personen = relatieRepository.haalopKinderen(8731137L);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
    }

    @Test
    public void testZoekOuders() throws Exception {
        List<Long> personen = relatieRepository.haalopOuders(3L);
        Assert.assertNotNull(personen);
        Assert.assertEquals(2, personen.size());
    }

    @Test
    public void testZoekfamilie() throws Exception {
        List<Long> personen = relatieRepository.haalopFamilie(3L);
        Assert.assertNotNull(personen);
        Assert.assertEquals(3, personen.size());
        personen = relatieRepository.haalopFamilie(8731137L);
        Assert.assertEquals(3, personen.size());
    }

    @Test
    public void testZoekEchtgenotenFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.HUWELIJK);
        List<Long> personen = relatieRepository.haalopRelatiesVanPersoon(8731137L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(2, personen.size());
    }

    @Test
    public void testZoekEchtgenotenFilterMannelijk() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.HUWELIJK);
        filter.setUitGeslachtsAanduidingen(GeslachtsAanduiding.MAN);
        List<Long> personen = relatieRepository.haalopRelatiesVanPersoon(8731137L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(new Long(8731137), personen.get(0));
    }

    @Test
    public void testZoekKinderenFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.KIND);
        List<Long> personen = relatieRepository.haalopRelatiesVanPersoon(8731137L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(new Long(3), personen.get(0));
    }

    @Test
    public void testZoekKinderenDochterFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.KIND);
        filter.setUitGeslachtsAanduidingen(GeslachtsAanduiding.VROUW);
        List<Long> personen = relatieRepository.haalopRelatiesVanPersoon(8731137L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(0, personen.size());
    }

    @Test
    public void testZoekKinderenVaderFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.OUDER);
        filter.setUitGeslachtsAanduidingen(GeslachtsAanduiding.MAN);
        List<Long> personen = relatieRepository.haalopRelatiesVanPersoon(3L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(new Long(8731137), personen.get(0));
    }
    @Test
    public void testZoekKinderenMoederFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.OUDER);
        filter.setUitGeslachtsAanduidingen(GeslachtsAanduiding.VROUW);
        List<Long> personen = relatieRepository.haalopRelatiesVanPersoon(3L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(new Long(2), personen.get(0));
    }

    @Test
    public void testZoekFamilieFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.OUDER, SoortBetrokkenheid.KIND);
        List<Long> personen = relatieRepository.haalopRelatiesVanPersoon(3L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(3, personen.size());
    }

    @Test
    public void testZoekFamilieIngezetenenFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.OUDER, SoortBetrokkenheid.KIND);
        filter.setUitPersoonTypen(SoortPersoon.INGESCHREVENE);
        List<Long> personen = relatieRepository.haalopRelatiesVanPersoon(3L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(3, personen.size());
    }

    @Test
    public void testZoekAlleRelatieFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, SoortRelatie.HUWELIJK);
        filter.setSoortBetrokkenheden(SoortBetrokkenheid.KIND, SoortBetrokkenheid.OUDER, SoortBetrokkenheid.PARTNER);
        List<Long> personen = relatieRepository.haalopRelatiesVanPersoon(3L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(3, personen.size());
    }
}
