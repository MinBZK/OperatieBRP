/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.ormmapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.groep.bericht.BetrokkenheidOuderlijkGezagGroepBericht;
import nl.bzk.brp.model.groep.bericht.BetrokkenheidOuderschapGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.StatusHistorie;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class RelatieMappingTest extends AbstractRepositoryTestCase {

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testOphalenRelatieMiddelsJPA() {
        RelatieModel rel = em.find(RelatieModel.class, 1L);
        Assert.assertNotNull(rel);

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
        Assert.assertEquals(StatusHistorie.A, rel.getStatusHistorie());
        Assert.assertEquals("Almeres", rel.getGegevens().getWoonPlaatsAanvang().getNaam().getWaarde());
        Assert.assertEquals("Scheveningen", rel.getGegevens().getWoonPlaatsEinde().getNaam().getWaarde());

        Assert.assertEquals(2, rel.getBetrokkenheden().size());
        for (BetrokkenheidModel betr : rel.getBetrokkenheden()) {
            if (betr.getId().longValue() == 1L) {
                Assert.assertNotNull(betr);
                Assert.assertNotNull(betr.getBetrokkene());
                Assert.assertNotNull(betr.getRelatie());
                Assert.assertNull(betr.getBetrokkenheidOuderlijkGezag());
                Assert.assertEquals(StatusHistorie.A, betr.getOuderlijkGezagStatusHistorie());
                Assert.assertNull(betr.getBetrokkenheidOuderschap());
                Assert.assertEquals(StatusHistorie.A, betr.getOuderStatusHistorie());
                Assert.assertEquals(SoortBetrokkenheid.PARTNER, betr.getRol());
                break;
            }
        }
    }

    @Test
    public void testOpslaanRelatieMiddelsJPA() {
        PersoonModel persoon = em.find(PersoonModel.class, 1L);
        RelatieBericht rel = new RelatieBericht();
        RelatieStandaardGroepBericht relGrp = new RelatieStandaardGroepBericht();

        BetrokkenheidBericht betr = new BetrokkenheidBericht();
        BetrokkenheidOuderlijkGezagGroepBericht betrOuderlijkGezag = new BetrokkenheidOuderlijkGezagGroepBericht();
        BetrokkenheidOuderschapGroepBericht betrOuderschap = new BetrokkenheidOuderschapGroepBericht();
        List<BetrokkenheidBericht> betrokkenheden = new ArrayList<BetrokkenheidBericht>();
        betrokkenheden.add(betr);
        ReflectionTestUtils.setField(rel, "gegevens", relGrp);
        ReflectionTestUtils.setField(rel, "soort", SoortRelatie.HUWELIJK);
        ReflectionTestUtils.setField(rel, "gegevens", relGrp);
        ReflectionTestUtils.setField(rel, "betrokkenheden", betrokkenheden);
        ReflectionTestUtils.setField(betr, "relatie", rel);
        ReflectionTestUtils.setField(betr, "betrokkenheidOuderlijkGezag", betrOuderlijkGezag);
        ReflectionTestUtils.setField(betr, "betrokkenheidOuderschap", betrOuderschap);

        RelatieModel relatie = new RelatieModel(rel);
        ReflectionTestUtils.setField(relatie, "statusHistorie", StatusHistorie.A);

        ReflectionTestUtils.setField(relatie, "betrokkenheden", new HashSet<BetrokkenheidModel>());
        for (BetrokkenheidBericht betrokkenheidWeb : rel.getBetrokkenheden()) {
            BetrokkenheidModel betrMdl = new BetrokkenheidModel(betrokkenheidWeb, persoon, relatie);
            ReflectionTestUtils.setField(betrMdl, "ouderlijkGezagStatusHistorie", StatusHistorie.A);
            ReflectionTestUtils.setField(betrMdl, "ouderStatusHistorie", StatusHistorie.A);
            relatie.getBetrokkenheden().add(betrMdl);
        }
        em.persist(relatie);
        Assert.assertNotNull(relatie.getId());
        Assert.assertNotNull(relatie.getBetrokkenheden().iterator().next().getId());
    }

}
