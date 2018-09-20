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
import javax.persistence.PersistenceUnit;

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


/**
 * Unit test voor het testen van de ORM mapping op {@link RelatieModel}. In deze test wordt via JPA direct een relatie
 * opgehaald en weggeschreven en de resultaten worden gecontroleerd.
 */
public class RelatieMappingTest extends AbstractRepositoryTestCase {

    @PersistenceContext(unitName="nl.bzk.brp")
    private EntityManager em;

    @Test
    public void testOphalenRelatieMiddelsJPA() {
        RelatieModel rel = em.find(RelatieModel.class, 1);
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
        Assert.assertEquals(Short.valueOf((short) 5010), rel.getGegevens().getLandEinde().getCode().getWaarde());
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
                Assert.assertEquals(StatusHistorie.A, betr.getOuderschapStatusHistorie());
                Assert.assertEquals(SoortBetrokkenheid.PARTNER, betr.getRol());
                break;
            }
        }
    }

    @Test
    public void testOpslaanRelatieMiddelsJPA() {
        PersoonModel persoon = em.find(PersoonModel.class, 1);
        RelatieBericht rel = new RelatieBericht();
        RelatieStandaardGroepBericht relGrp = new RelatieStandaardGroepBericht();

        BetrokkenheidBericht betr = new BetrokkenheidBericht();
        BetrokkenheidOuderlijkGezagGroepBericht betrOuderlijkGezag = new BetrokkenheidOuderlijkGezagGroepBericht();
        BetrokkenheidOuderschapGroepBericht betrOuderschap = new BetrokkenheidOuderschapGroepBericht();
        List<BetrokkenheidBericht> betrokkenheden = new ArrayList<BetrokkenheidBericht>();
        betrokkenheden.add(betr);
        rel.setGegevens(relGrp);
        rel.setSoort(SoortRelatie.HUWELIJK);
        rel.setBetrokkenheden(betrokkenheden);
        betr.setRelatie(rel);
        betr.setBetrokkenheidOuderlijkGezag(betrOuderlijkGezag);
        betr.setBetrokkenheidOuderschap(betrOuderschap);

        RelatieModel relatie = new RelatieModel(rel);
        ReflectionTestUtils.setField(relatie, "statusHistorie", StatusHistorie.A);

        ReflectionTestUtils.setField(relatie, "betrokkenheden", new HashSet<BetrokkenheidModel>());
        for (BetrokkenheidBericht betrokkenheidWeb : rel.getBetrokkenheden()) {
            BetrokkenheidModel betrModel = new BetrokkenheidModel(betrokkenheidWeb, persoon, relatie);
            ReflectionTestUtils.setField(betrModel, "ouderlijkGezagStatusHistorie", StatusHistorie.A);
            ReflectionTestUtils.setField(betrModel, "ouderschapStatusHistorie", StatusHistorie.A);
            relatie.getBetrokkenheden().add(betrModel);
        }
        em.persist(relatie);
        Assert.assertNotNull(relatie.getId());
        Assert.assertNotNull(relatie.getBetrokkenheden().iterator().next().getId());
    }

}
