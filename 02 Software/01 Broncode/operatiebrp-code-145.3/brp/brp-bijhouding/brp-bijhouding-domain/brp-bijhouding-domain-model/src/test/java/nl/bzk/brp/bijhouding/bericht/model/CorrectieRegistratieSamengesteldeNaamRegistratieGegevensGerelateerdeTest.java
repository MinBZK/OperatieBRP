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
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link CorrectieRegistratieSamengesteldeNaamRegistratieGegevensGerelateerdeTest}.
 */
@RunWith(MockitoJUnitRunner.class)
public class CorrectieRegistratieSamengesteldeNaamRegistratieGegevensGerelateerdeTest {

    private static final String PARTNER_OBJ_SLEUTEL = "partnerObjSleutel";
    private static final String IK_OBJ_SLEUTEL = "ikObjSleutel";
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
        final CorrectieRegistratieSamengesteldeNaamRegistratieGegevensGerelateerde actieElement =
                builder.maakCorrectieRegistratieSamengesteldeNaamGerelateerdeActieElement("commId", 2016_01_01, null, persoonRelatieElement);
        assertEquals(SoortActie.CORRECTIEREGISTRATIE_SAMENGESTELDE_NAAM_GERELATEERDE, actieElement.getSoortActie());
    }


    @Test
    public void testBepaalSetVoorNieuwVoorkomen() {
        final CorrectieRegistratieSamengesteldeNaamRegistratieGegevensGerelateerde actie = maakBasisActie(new ElementBuilder.PersoonParameters());

        final Persoon partner = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonSamengesteldeNaamHistorie historie = new PersoonSamengesteldeNaamHistorie(partner, "stam", false, false);
        partner.addPersoonSamengesteldeNaamHistorie(historie);
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
        final ElementBuilder.NaamParameters params = new ElementBuilder.NaamParameters();

        SamengesteldeNaamElement samengesteldeNaamElement = builder.maakSamengesteldeNaamElement("geboorteElement", params);
        partnerParams.samengesteldeNaam(samengesteldeNaamElement);
        final CorrectieRegistratieSamengesteldeNaamRegistratieGegevensGerelateerde actieElement = maakBasisActie(partnerParams);

        final BijhoudingPersoon bijhoudingPartner = mock(BijhoudingPersoon.class);
        final PersoonSamengesteldeNaamHistorie historie = new PersoonSamengesteldeNaamHistorie(bijhoudingPartner, "stam", false, false);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PARTNER_OBJ_SLEUTEL)).thenReturn(bijhoudingPartner);
        when(bijhoudingPartner.maakNieuweSamengesteldeNaamHistorieVoorCorrectie(samengesteldeNaamElement)).thenReturn(historie);
        FormeleHistorie nieuwVoorkomen = actieElement.maakNieuwVoorkomen();
        assertNotNull(nieuwVoorkomen);
    }

    @Bedrijfsregel(Regel.R2535)
    @Test
    public void testR2535() {
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PARTNER_OBJ_SLEUTEL)).thenReturn(persoon);
        final PersoonSamengesteldeNaamHistorie historie = new PersoonSamengesteldeNaamHistorie(persoon, "Stam", true, false);
        when(persoon.maakNieuweSamengesteldeNaamHistorieVoorCorrectie(any(SamengesteldeNaamElement.class))).thenReturn(historie);

        assertEquals(0, maakBasisActie(new ElementBuilder.PersoonParameters()).valideerSpecifiekeInhoud().size());

        builder = new ElementBuilder();
        assertEquals(0, maakActie(new ElementBuilder.PersoonParameters(), 2017_01_01).valideerSpecifiekeInhoud().size());

        builder = new ElementBuilder();
        final List<MeldingElement> meldingen = maakActie(new ElementBuilder.PersoonParameters(), 2016_01_01).valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());

        builder = new ElementBuilder();
        final List<MeldingElement> meldingen2 = maakActie(new ElementBuilder.PersoonParameters(), 2015_01_01).valideerSpecifiekeInhoud();
        assertEquals(1, meldingen2.size());
        assertEquals(Regel.R2535, meldingen2.get(0).getRegel());
    }


    private CorrectieRegistratieSamengesteldeNaamRegistratieGegevensGerelateerde maakBasisActie(final ElementBuilder.PersoonParameters partnerParams) {
        return maakActie(partnerParams, null);
    }
    private CorrectieRegistratieSamengesteldeNaamRegistratieGegevensGerelateerde maakActie(final ElementBuilder.PersoonParameters partnerParams,
                                                                                           final Integer datumEinde) {
        final PersoonGegevensElement partner = builder.maakPersoonGegevensElement("partner", PARTNER_OBJ_SLEUTEL, null, partnerParams);
        final BetrokkenheidElement partnerBetrokkenheid = builder.maakBetrokkenheidElement("partnerBetr", "partnerBetrObjSleutel",
                BetrokkenheidElementSoort.PARTNER, partner, null);
        final GeregistreerdPartnerschapElement gpElement =
                builder.maakGeregistreerdPartnerschapElement("gpElement", null, Collections.singletonList(partnerBetrokkenheid));
        final BetrokkenheidElement ikBetrokkenheid =
                builder.maakBetrokkenheidElement("ikBetr", "betrObjSleutel", null, BetrokkenheidElementSoort.OUDER, gpElement);
        final PersoonRelatieElement ikPersoon = builder.maakPersoonRelatieElement("persoon", null, IK_OBJ_SLEUTEL, Collections.singletonList(ikBetrokkenheid));
        final CorrectieRegistratieSamengesteldeNaamRegistratieGegevensGerelateerde actie =
                builder.maakCorrectieRegistratieSamengesteldeNaamGerelateerdeActieElement("commId", 2016_01_01, datumEinde, ikPersoon);
        partner.setVerzoekBericht(bericht);
        ikPersoon.setVerzoekBericht(bericht);
        actie.setVerzoekBericht(bericht);
        return actie;
    }
}
