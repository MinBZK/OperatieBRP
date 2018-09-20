/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.groep.impl.usr.BetrokkenheidOuderlijkGezagGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.BetrokkenheidOuderschapGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.RelatieStandaardGroepMdl;
import nl.bzk.brp.model.objecttype.impl.usr.BetrokkenheidMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonMdl;
import nl.bzk.brp.model.objecttype.impl.usr.RelatieMdl;
import nl.bzk.brp.model.objecttype.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.statisch.SoortRelatie;
import nl.bzk.brp.model.objecttype.statisch.StatusHistorie;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;


public class RelatieNieuwRepositoryTest extends AbstractRepositoryTestCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(RelatieNieuwRepositoryTest.class);

    @PersistenceContext
    private EntityManager em;

    @Test
    public void haalRelatieOp() {
        RelatieMdl rel = em.find(RelatieMdl.class, 1L);
        Assert.assertNotNull(rel);
        BetrokkenheidMdl betr = em.find(BetrokkenheidMdl.class, 1L);
        Assert.assertNotNull(betr);
        Assert.assertEquals(SoortRelatie.HUWELIJK, rel.getSoort());
        Assert.assertNotNull(rel.getGegevens());
        Assert.assertEquals("blPlaatsAanv", rel.getGegevens().getBuitenlandsePlaatsAanvang().getWaarde());
        Assert.assertEquals("blPlaatsEinde", rel.getGegevens().getBuitenlandsePlaatsEinde().getWaarde());
        Assert.assertEquals("blRegioAanv", rel.getGegevens().getBuitenlandseRegioAanvang().getWaarde());
        Assert.assertEquals("blRegioEinde", rel.getGegevens().getBuitenlandseRegioEinde().getWaarde());
        Assert.assertEquals(new Integer(19681116), rel.getGegevens().getDatumAanvang().getWaarde());
        Assert.assertEquals(new Integer(20140519), rel.getGegevens().getDatumEinde().getWaarde());
        Assert.assertEquals("Almere", rel.getGegevens().getGemeenteAanvang().getNaam().getWaarde());
        Assert.assertEquals("Minister", rel.getGegevens().getGemeenteEinde().getNaam().getWaarde());
        Assert.assertEquals("Nederland", rel.getGegevens().getLandAanvang().getNaam().getWaarde());
        Assert.assertEquals("5010", rel.getGegevens().getLandEinde().getLandCode().getWaarde());
        Assert.assertEquals("omsLocAanv", rel.getGegevens().getOmschrijvingLocatieAanvang().getWaarde());
        Assert.assertEquals("omsLocEinde", rel.getGegevens().getOmschrijvingLocatieEinde().getWaarde());
        Assert.assertNull(rel.getGegevens().getRedenBeeindigingRelatie());
        Assert.assertNull(rel.getGegevens().getStatusHistorie());
        Assert.assertEquals("Almeres", rel.getGegevens().getWoonPlaatsAanvang().getNaam().getWaarde());
        Assert.assertEquals("Scheveningen", rel.getGegevens().getWoonPlaatsEinde().getNaam().getWaarde());

        Assert.assertEquals(2, rel.getBetrokkenheden().size());
        Iterator<? extends BetrokkenheidMdl> it = rel.getBetrokkenheden().iterator();
        betr = it.next();
        if (betr.getId().longValue() != 1L) {
            betr = it.next();
        }
        Assert.assertNotNull(betr);
        Assert.assertNotNull(betr.getBetrokkene());
        Assert.assertNotNull(betr.getRelatie());
        Assert.assertNull(betr.getBetrokkenheidOuderlijkGezag());
        Assert.assertNull(betr.getBetrokkenheidOuderschap());
        Assert.assertEquals(SoortBetrokkenheid.PARTNER, betr.getRol());

    }

    @Test
    public void slaRelatieOp() {
        PersoonMdl persoon = em.find(PersoonMdl.class, 1L);
        RelatieMdl rel = new RelatieMdl();
        RelatieStandaardGroepMdl relGrp = new RelatieStandaardGroepMdl();
        BetrokkenheidMdl betr = new BetrokkenheidMdl();
        BetrokkenheidOuderlijkGezagGroepMdl betrGrp1 = new BetrokkenheidOuderlijkGezagGroepMdl();
        BetrokkenheidOuderschapGroepMdl betrGrp2 = new BetrokkenheidOuderschapGroepMdl();
        Set<BetrokkenheidMdl> betrokkenheden = new HashSet<BetrokkenheidMdl>();
        betrokkenheden.add(betr);

        ReflectionTestUtils.setField(rel, "gegevens", relGrp);
        ReflectionTestUtils.setField(rel, "soort", SoortRelatie.HUWELIJK);
        ReflectionTestUtils.setField(rel, "gegevens", relGrp);
        ReflectionTestUtils.setField(rel, "betrokkenheden", betrokkenheden);
        ReflectionTestUtils.setField(relGrp, "statusHistorie", StatusHistorie.A);


        ReflectionTestUtils.setField(betr, "relatie", rel);
        ReflectionTestUtils.setField(betr, "betrokkene", persoon);
        ReflectionTestUtils.setField(betr, "betrokkenheidOuderlijkGezag", betrGrp1);
        ReflectionTestUtils.setField(betr, "betrokkenheidOuderschap", betrGrp2);

        ReflectionTestUtils.setField(betrGrp1, "statusHistorie", StatusHistorie.A);
        ReflectionTestUtils.setField(betrGrp2, "statusHistorie", StatusHistorie.A);

        em.persist(rel);
        Assert.assertNotNull(rel.getId());
        Assert.assertNotNull(rel.getBetrokkenheden().iterator().next().getId());
        Long relId = rel.getId();
        Long betrId = rel.getBetrokkenheden().iterator().next().getId();
        System.out.println("id rel: " + rel.getId());
        System.out.println("id betr: " + rel.getBetrokkenheden().iterator().next().getId());
        rel = em.find(RelatieMdl.class, relId);

        // hmm. de rest geloven we wel dat deze ook goed wordt weggeschreven.
    }

}
