/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link CorrectieRegistratieGeboorteRegistratieGegevensGerelateerde}.
 */
@RunWith(MockitoJUnitRunner.class)
public class CorrectieRegistratieGeboorteRegistratieGegevensGerelateerdeTest {

    private static final String PARTNER_OBJ_SLEUTEL = "partnerObjSleutel";
    private static final String IK_OBJ_SLEUTEL = "ikObjSleutel";
    private static final LandOfGebied LAND_OF_GEBIED = new LandOfGebied("6030", "Nederland");
    private ElementBuilder builder;
    private BijhoudingVerzoekBericht bericht;

    @Before
    public void setUp() {
        builder = new ElementBuilder();
        bericht = mock(BijhoudingVerzoekBericht.class);
    }

    @Test
    public void testGetSoortActie() {
        final PersoonRelatieElement persoonRelatieElement = builder.maakPersoonRelatieElement("persoon", null, "1234", Collections.emptyList());
        final CorrectieRegistratieGeboorteRegistratieGegevensGerelateerde actieElement =
                builder.maakCorrectieRegistratieGeboorteGerelateerdeActieElement("commId", 2016_01_01, null, persoonRelatieElement);
        assertEquals(SoortActie.CORRECTIEREGISTRATIE_GEBOORTE_GERELATEERDE, actieElement.getSoortActie());
    }


    @Test
    public void testBepaalSetVoorNieuwVoorkomen() {
        final CorrectieRegistratieGeboorteRegistratieGegevensGerelateerde actie = maakBasisActie(new ElementBuilder.PersoonParameters());

        final Persoon partner = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonGeboorteHistorie historie = new PersoonGeboorteHistorie(partner, 2016_01_01, LAND_OF_GEBIED);
        partner.addPersoonGeboorteHistorie(historie);
        final BijhoudingPersoon bijhoudingPartner = new BijhoudingPersoon(partner);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PARTNER_OBJ_SLEUTEL)).thenReturn(bijhoudingPartner);

        final Set historieSet = actie.bepaalSetVoorNieuwVoorkomen();
        assertTrue(historieSet instanceof BijhoudingPersoonGroepSet);
        assertEquals(1, historieSet.size());
        assertTrue(historieSet.contains(historie));
    }

    @Test
    public void testMaakNieuwVoorkomen() {
        final ElementBuilder.PersoonParameters partnerParams = new ElementBuilder.PersoonParameters();
        final ElementBuilder.GeboorteParameters params = new ElementBuilder.GeboorteParameters();
        params.datum(2016_01_01);
        GeboorteElement geboorteElement = builder.maakGeboorteElement("geboorteElement", params);
        partnerParams.geboorte(geboorteElement);
        final CorrectieRegistratieGeboorteRegistratieGegevensGerelateerde actieElement = maakBasisActie(partnerParams);

        final BijhoudingPersoon bijhoudingPartner = mock(BijhoudingPersoon.class);
        final PersoonGeboorteHistorie historie = new PersoonGeboorteHistorie(bijhoudingPartner, 2016_01_01, LAND_OF_GEBIED);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PARTNER_OBJ_SLEUTEL)).thenReturn(bijhoudingPartner);
        when(bijhoudingPartner.maakNieuweGeboorteHistorieVoorCorrectie(geboorteElement)).thenReturn(historie);
        FormeleHistorie nieuwVoorkomen = actieElement.maakNieuwVoorkomen();
        assertNotNull(nieuwVoorkomen);
    }

    @Test
    public void testValideerSpecifiekeInhoud() {
        final CorrectieRegistratieGeboorteRegistratieGegevensGerelateerde actie = maakBasisActie(new ElementBuilder.PersoonParameters());
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PARTNER_OBJ_SLEUTEL)).thenReturn(persoon);
        when(persoon.maakNieuweGeboorteHistorieVoorCorrectie(any(GeboorteElement.class)))
                .thenReturn(new PersoonGeboorteHistorie(persoon, 2016_01_01, new LandOfGebied("0001", "NL")));
        assertEquals(0, actie.valideerSpecifiekeInhoud().size());
    }

    private CorrectieRegistratieGeboorteRegistratieGegevensGerelateerde maakBasisActie(final ElementBuilder.PersoonParameters partnerParams) {
        final PersoonGegevensElement partner = builder.maakPersoonGegevensElement("partner", PARTNER_OBJ_SLEUTEL, null, partnerParams);
        final BetrokkenheidElement partnerBetrokkenheid = builder.maakBetrokkenheidElement("partnerBetr", "partnerBetrObjSleutel",
                BetrokkenheidElementSoort.PARTNER, partner, null);
        final GeregistreerdPartnerschapElement gpElement =
                builder.maakGeregistreerdPartnerschapElement("gpElement", null, Collections.singletonList(partnerBetrokkenheid));
        final BetrokkenheidElement ikBetrokkenheid =
                builder.maakBetrokkenheidElement("ikBetr", "betrObjSleutel", null, BetrokkenheidElementSoort.OUDER, gpElement);
        final PersoonRelatieElement ikPersoon = builder.maakPersoonRelatieElement("persoon", null, IK_OBJ_SLEUTEL, Collections.singletonList(ikBetrokkenheid));
        final CorrectieRegistratieGeboorteRegistratieGegevensGerelateerde actie =
                builder.maakCorrectieRegistratieGeboorteGerelateerdeActieElement("commId", 2016_01_01, null, ikPersoon);
        partner.setVerzoekBericht(bericht);
        ikPersoon.setVerzoekBericht(bericht);
        actie.setVerzoekBericht(bericht);
        return actie;
    }
}