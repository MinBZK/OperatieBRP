/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.ormmapping;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.logisch.kern.Partner;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.HuwelijkGeregistreerdPartnerschapModel;
import nl.bzk.brp.model.operationeel.kern.HuwelijkModel;
import nl.bzk.brp.model.operationeel.kern.PartnerModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor het testen van de ORM mapping op {@link RelatieModel}. In deze test wordt via JPA direct een relatie
 * opgehaald en weggeschreven en de resultaten worden gecontroleerd.
 */
public class RelatieMappingTest extends AbstractRepositoryTestCase {

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager em;

    @Test
    public void testOphalenRelatieMiddelsJPA() {
        HuwelijkGeregistreerdPartnerschapModel rel = em.find(HuwelijkModel.class, 1);
        Assert.assertNotNull(rel);

        Assert.assertEquals(SoortRelatie.HUWELIJK, rel.getSoort());
        Assert.assertNotNull(rel.getStandaard());
        Assert.assertEquals("blPlaatsAanv", rel.getStandaard().getBuitenlandsePlaatsAanvang().getWaarde());
        Assert.assertEquals("blPlaatsEinde", rel.getStandaard().getBuitenlandsePlaatsEinde().getWaarde());
        Assert.assertEquals("blRegioAanv", rel.getStandaard().getBuitenlandseRegioAanvang().getWaarde());
        Assert.assertEquals("blRegioEinde", rel.getStandaard().getBuitenlandseRegioEinde().getWaarde());
        Assert.assertEquals(new Integer(19681116), rel.getStandaard().getDatumAanvang().getWaarde());
        Assert.assertEquals(new Integer(20140519), rel.getStandaard().getDatumEinde().getWaarde());
        Assert.assertEquals("Almere", rel.getStandaard().getGemeenteAanvang().getNaam().getWaarde());
        Assert.assertEquals("Minister", rel.getStandaard().getGemeenteEinde().getNaam().getWaarde());
        Assert.assertEquals("Nederland", rel.getStandaard().getLandAanvang().getNaam().getWaarde());
        Assert.assertEquals("5010", rel.getStandaard().getLandEinde().getCode().toString());
        Assert.assertEquals("omsLocAanv", rel.getStandaard().getOmschrijvingLocatieAanvang().getWaarde());
        Assert.assertEquals("omsLocEinde", rel.getStandaard().getOmschrijvingLocatieEinde().getWaarde());
        Assert.assertNull(rel.getStandaard().getRedenEinde());
        Assert.assertEquals(StatusHistorie.A,
                            rel.getHuwelijkGeregistreerdPartnerschapStatusHis());
        Assert.assertEquals("Almere", rel.getStandaard().getWoonplaatsAanvang().getNaam().getWaarde());
        Assert.assertEquals("Scheveningen", rel.getStandaard().getWoonplaatsEinde().getNaam().getWaarde());

        Assert.assertEquals(2, rel.getBetrokkenheden().size());

        for (BetrokkenheidModel betr : rel.getBetrokkenheden()) {
            if (betr.getID().longValue() == 1L) {
                Assert.assertNotNull(betr);
                Assert.assertNotNull(betr.getPersoon());
                Assert.assertNotNull(betr.getRelatie());
                Assert.assertEquals(SoortBetrokkenheid.PARTNER, betr.getRol());
                break;
            }
        }

    }

    @Test
    public void testOpslaanRelatieMiddelsJPA() {
        PersoonModel persoon = em.find(PersoonModel.class, 1);
        HuwelijkBericht rel = new HuwelijkBericht();
        HuwelijkGeregistreerdPartnerschapStandaardGroepBericht relGrp = new HuwelijkGeregistreerdPartnerschapStandaardGroepBericht();

        PartnerBericht betr = new PartnerBericht();

        List<BetrokkenheidBericht> betrokkenheden = new ArrayList<BetrokkenheidBericht>();
        betrokkenheden.add(betr);
        rel.setStandaard(relGrp);
        rel.setBetrokkenheden(betrokkenheden);
        betr.setRelatie(rel);

        HuwelijkModel relatie = new HuwelijkModel(rel);
        relatie.setHuwelijkGeregistreerdPartnerschapStatusHis(StatusHistorie.A);

        ReflectionTestUtils.setField(relatie, "betrokkenheden", new TreeSet<BetrokkenheidModel>());
        for (BetrokkenheidBericht betrokkenheidWeb : rel.getBetrokkenheden()) {
            PartnerModel betrModel = new PartnerModel((Partner) betrokkenheidWeb, relatie, persoon);
            relatie.getBetrokkenheden().add(betrModel);
        }
        em.persist(relatie);
        Assert.assertNotNull(relatie.getID());

        Assert.assertNotNull(relatie.getBetrokkenheden().iterator().next().getID());
    }

}
