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
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.logisch.kern.Partner;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.HuwelijkGeregistreerdPartnerschapModel;
import nl.bzk.brp.model.operationeel.kern.HuwelijkModel;
import nl.bzk.brp.model.operationeel.kern.PartnerModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor het testen van de ORM mapping op {@link nl.bzk.brp.model.operationeel.kern.RelatieModel}. In deze
 * test wordt via JPA direct een relatie opgehaald en weggeschreven en de resultaten worden gecontroleerd.
 */
public class RelatieMappingTest extends AbstractRepositoryTestCase {

    public static final String ALMERE = "Almere";
    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    @Test
    public void testOphalenRelatieMiddelsJPA() {
        final HuwelijkGeregistreerdPartnerschapModel rel = em.find(HuwelijkModel.class, 1001);
        Assert.assertNotNull(rel);

        Assert.assertEquals(SoortRelatie.HUWELIJK, rel.getSoort().getWaarde());
        Assert.assertNotNull(rel.getStandaard());
        Assert.assertEquals("blPlaatsAanv", rel.getStandaard().getBuitenlandsePlaatsAanvang().getWaarde());
        Assert.assertEquals("blPlaatsEinde", rel.getStandaard().getBuitenlandsePlaatsEinde().getWaarde());
        Assert.assertEquals("blRegioAanv", rel.getStandaard().getBuitenlandseRegioAanvang().getWaarde());
        Assert.assertEquals("blRegioEinde", rel.getStandaard().getBuitenlandseRegioEinde().getWaarde());
        Assert.assertEquals(Integer.valueOf(19681116), rel.getStandaard().getDatumAanvang().getWaarde());
        Assert.assertEquals(Integer.valueOf(20140519), rel.getStandaard().getDatumEinde().getWaarde());
        Assert.assertEquals(ALMERE, rel.getStandaard().getGemeenteAanvang().getWaarde().getNaam().getWaarde());
        Assert.assertEquals(null, rel.getStandaard().getGemeenteEinde());
        Assert.assertEquals("Nederland", rel.getStandaard().getLandGebiedAanvang().getWaarde().getNaam().getWaarde());
        Assert.assertEquals("5010", rel.getStandaard().getLandGebiedEinde().getWaarde().getCode().toString());
        Assert.assertEquals("omsLocAanv", rel.getStandaard().getOmschrijvingLocatieAanvang().getWaarde());
        Assert.assertEquals("omsLocEinde", rel.getStandaard().getOmschrijvingLocatieEinde().getWaarde());
        Assert.assertNull(rel.getStandaard().getRedenEinde());
        Assert.assertEquals(ALMERE, rel.getStandaard().getWoonplaatsnaamAanvang().getWaarde());
        Assert.assertEquals("Scheveningen", rel.getStandaard().getWoonplaatsnaamEinde().getWaarde());

        Assert.assertEquals(2, rel.getBetrokkenheden().size());

        for (final BetrokkenheidModel betr : rel.getBetrokkenheden()) {
            if (betr.getID().longValue() == 1L) {
                Assert.assertNotNull(betr);
                Assert.assertNotNull(betr.getPersoon());
                Assert.assertNotNull(betr.getRelatie());
                Assert.assertEquals(SoortBetrokkenheid.PARTNER, betr.getRol().getWaarde());
                break;
            }
        }

    }

    @Test
    public void testOpslaanRelatieMiddelsJPA() {
        final PersoonModel persoon = em.find(PersoonModel.class, 1);
        final HuwelijkBericht rel = new HuwelijkBericht();
        final RelatieStandaardGroepBericht relGrp = new RelatieStandaardGroepBericht();

        final PartnerBericht betr = new PartnerBericht();

        final List<BetrokkenheidBericht> betrokkenheden = new ArrayList<>();
        betrokkenheden.add(betr);
        rel.setStandaard(relGrp);
        rel.setBetrokkenheden(betrokkenheden);
        betr.setRelatie(rel);

        final HuwelijkModel relatie = new HuwelijkModel(rel);

        ReflectionTestUtils.setField(relatie, "betrokkenheden", new TreeSet<BetrokkenheidModel>());
        for (final BetrokkenheidBericht betrokkenheidWeb : rel.getBetrokkenheden()) {
            final PartnerModel betrModel = new PartnerModel((Partner) betrokkenheidWeb, relatie, persoon);
            relatie.getBetrokkenheden().add(betrModel);
        }
        em.persist(relatie);
        Assert.assertNotNull(relatie.getID());

        Assert.assertNotNull(relatie.getBetrokkenheden().iterator().next().getID());
    }

}
