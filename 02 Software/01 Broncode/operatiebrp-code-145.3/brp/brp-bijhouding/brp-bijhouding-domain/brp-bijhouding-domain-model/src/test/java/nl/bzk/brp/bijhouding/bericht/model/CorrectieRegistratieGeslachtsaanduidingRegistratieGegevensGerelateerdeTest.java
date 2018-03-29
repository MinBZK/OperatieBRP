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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link CorrectieRegistratieGeslachtsaanduidingRegistratieGegevensGerelateerde}.
 */
@RunWith(MockitoJUnitRunner.class)
public class CorrectieRegistratieGeslachtsaanduidingRegistratieGegevensGerelateerdeTest {

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
        final CorrectieRegistratieGeslachtsaanduidingRegistratieGegevensGerelateerde actieElement =
                builder.maakCorrectieRegistratieGeslachtsaanduidingGerelateerdeActieElement("commId", 2016_01_01, null, persoonRelatieElement);
        assertEquals(SoortActie.CORRECTIEREGISTRATIE_GESLACHTSAANDUIDING_GERELATEERDE, actieElement.getSoortActie());
    }

    @Test
    public void testBepaalSetVoorNieuwVoorkomen() {
        final CorrectieRegistratieGeslachtsaanduidingRegistratieGegevensGerelateerde actie = maakBasisActie(new ElementBuilder.PersoonParameters());

        final Persoon partner = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonGeslachtsaanduidingHistorie historie = new PersoonGeslachtsaanduidingHistorie(partner, Geslachtsaanduiding.MAN);
        final BijhoudingPersoon bijhoudingPartner = new BijhoudingPersoon(partner);
        partner.addPersoonGeslachtsaanduidingHistorie(historie);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PARTNER_OBJ_SLEUTEL)).thenReturn(bijhoudingPartner);

        final Set historieSet = actie.bepaalSetVoorNieuwVoorkomen();
        assertTrue(historieSet instanceof BijhoudingPersoonGroepSet);
        assertEquals(1, historieSet.size());
        assertTrue(historieSet.contains(historie));
    }

    @Test
    public void testMaakNieuwVoorkomen() {
        final ElementBuilder.PersoonParameters partnerParams = new ElementBuilder.PersoonParameters();
        final GeslachtsaanduidingElement element = builder.maakGeslachtsaanduidingElement("idComm", "M");
        partnerParams.geslachtsaanduiding(element);
        final CorrectieRegistratieGeslachtsaanduidingRegistratieGegevensGerelateerde actieElement = maakBasisActie(partnerParams);

        final BijhoudingPersoon bijhoudingPartner = mock(BijhoudingPersoon.class);
        final PersoonGeslachtsaanduidingHistorie historie = new PersoonGeslachtsaanduidingHistorie(bijhoudingPartner, Geslachtsaanduiding.MAN);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PARTNER_OBJ_SLEUTEL)).thenReturn(bijhoudingPartner);
        when(bijhoudingPartner.maakNieuweGeslachtsaanduidingHistorieVoorCorrectie(element)).thenReturn(historie);
        FormeleHistorie nieuwVoorkomen = actieElement.maakNieuwVoorkomen();
        assertNotNull(nieuwVoorkomen);
    }

    @Bedrijfsregel(Regel.R2528)
    @Test
    public void testR2528() {
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PARTNER_OBJ_SLEUTEL)).thenReturn(persoon);
        final PersoonGeslachtsaanduidingHistorie historie = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.MAN);
        when(persoon.maakNieuweGeslachtsaanduidingHistorieVoorCorrectie(any(GeslachtsaanduidingElement.class))).thenReturn(historie);

        assertEquals(0, maakBasisActie(new ElementBuilder.PersoonParameters()).valideerSpecifiekeInhoud().size());

        builder = new ElementBuilder();
        assertEquals(0, maakActie(new ElementBuilder.PersoonParameters(), 2017_01_01).valideerSpecifiekeInhoud().size());

        builder = new ElementBuilder();
        final List<MeldingElement> meldingen = maakActie(new ElementBuilder.PersoonParameters(), 2016_01_01).valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2528, meldingen.get(0).getRegel());

        builder = new ElementBuilder();
        final List<MeldingElement> meldingen2 = maakActie(new ElementBuilder.PersoonParameters(), 2015_01_01).valideerSpecifiekeInhoud();
        assertEquals(1, meldingen2.size());
        assertEquals(Regel.R2528, meldingen2.get(0).getRegel());
    }

    private CorrectieRegistratieGeslachtsaanduidingRegistratieGegevensGerelateerde maakBasisActie(final ElementBuilder.PersoonParameters partnerParams) {
        return maakActie(partnerParams, null);
    }

    private CorrectieRegistratieGeslachtsaanduidingRegistratieGegevensGerelateerde maakActie(final ElementBuilder.PersoonParameters partnerParams,
                                                                                             final Integer datumEinde) {
        final PersoonGegevensElement partner = builder.maakPersoonGegevensElement("partner", PARTNER_OBJ_SLEUTEL, null, partnerParams);
        final BetrokkenheidElement partnerBetrokkenheid = builder.maakBetrokkenheidElement("partnerBetr", "partnerBetrObjSleutel",
                BetrokkenheidElementSoort.PARTNER, partner, null);
        final GeregistreerdPartnerschapElement gpElement =
                builder.maakGeregistreerdPartnerschapElement("gpElement", null, Collections.singletonList(partnerBetrokkenheid));
        final BetrokkenheidElement ikBetrokkenheid =
                builder.maakBetrokkenheidElement("ikBetr", "betrObjSleutel", null, BetrokkenheidElementSoort.OUDER, gpElement);
        final PersoonRelatieElement ikPersoon = builder.maakPersoonRelatieElement("persoon", null, IK_OBJ_SLEUTEL, Collections.singletonList(ikBetrokkenheid));
        final CorrectieRegistratieGeslachtsaanduidingRegistratieGegevensGerelateerde actie =
                builder.maakCorrectieRegistratieGeslachtsaanduidingGerelateerdeActieElement("commId", 2016_01_01, datumEinde, ikPersoon);
        partner.setVerzoekBericht(bericht);
        ikPersoon.setVerzoekBericht(bericht);
        actie.setVerzoekBericht(bericht);
        return actie;
    }
}