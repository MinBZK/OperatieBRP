/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test voor {@link CorrectieVervalGeslachtsaanduidingGerelateerde}.
 */
public class CorrectieVervalGeslachtsaanduidingGerelateerdeTest {
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
    public void testSoortActie() {
        final CorrectieVervalGeslachtsaanduidingGerelateerde actie = maakBasisActie(new ElementBuilder.PersoonParameters());
        assertEquals(SoortActie.CORRECTIEVERVAL_GESLACHTSAANDUIDING_GERELATEERDE, actie.getSoortActie());
    }

    @Test
    public void testBepaalTeVervallenVoorkomen_GeenHistorieGevonden() {
        final BijhoudingPersoon partner = mock(BijhoudingPersoon.class);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PARTNER_OBJ_SLEUTEL)).thenReturn(partner);

        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        final String voorkomenSleutel = "1234";
        final GeslachtsaanduidingElement element = builder.maakGeslachtsaanduidingElementVoorVerval("ident", voorkomenSleutel);
        persoonParams.geslachtsaanduiding(element);
        final CorrectieVervalGeslachtsaanduidingGerelateerde actie = maakBasisActie(persoonParams);

        when(partner.zoekRelatieHistorieVoorVoorkomenSleutel(voorkomenSleutel, PersoonIDHistorie.class)).thenReturn(null);
        assertNull(actie.bepaalTeVervallenVoorkomen());
    }

    @Test
    public void testBepaalTeVervallenVoorkomen() {
        final BijhoudingPersoon partner = mock(BijhoudingPersoon.class);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PARTNER_OBJ_SLEUTEL)).thenReturn(partner);

        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        final String voorkomenSleutel = "1234";
        final GeslachtsaanduidingElement element = builder.maakGeslachtsaanduidingElementVoorVerval("ident", voorkomenSleutel);
        persoonParams.geslachtsaanduiding(element);
        final CorrectieVervalGeslachtsaanduidingGerelateerde actie = maakBasisActie(persoonParams);

        final PersoonGeslachtsaanduidingHistorie historie = new PersoonGeslachtsaanduidingHistorie(partner, Geslachtsaanduiding.MAN);
        when(partner.zoekRelatieHistorieVoorVoorkomenSleutel(voorkomenSleutel, PersoonGeslachtsaanduidingHistorie.class)).thenReturn(historie);
        assertEquals(historie, actie.bepaalTeVervallenVoorkomen());
    }

    @Test
    public void testBepaalTeVervallenVoorkomen_GeenVoorkomenSleutel() {
        final BijhoudingPersoon partner = mock(BijhoudingPersoon.class);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PARTNER_OBJ_SLEUTEL)).thenReturn(partner);

        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        final GeslachtsaanduidingElement element = builder.maakGeslachtsaanduidingElementVoorVerval("ident", null);
        persoonParams.geslachtsaanduiding(element);
        final CorrectieVervalGeslachtsaanduidingGerelateerde actie = maakBasisActie(persoonParams);

        assertNull(actie.bepaalTeVervallenVoorkomen());
    }

    @Test
    public void testBepaalTeVervallenVoorkomen_GeenPartnerEntiteit() {
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PARTNER_OBJ_SLEUTEL)).thenReturn(null);

        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        final GeslachtsaanduidingElement element = builder.maakGeslachtsaanduidingElementVoorVerval("ident", "1234");
        persoonParams.geslachtsaanduiding(element);
        final CorrectieVervalGeslachtsaanduidingGerelateerde actie = maakBasisActie(persoonParams);

        assertNull(actie.bepaalTeVervallenVoorkomen());
    }

    @Test
    public void testOngeldigeAangewezenObjectOfVoorkomen() {
        final BijhoudingPersoon partner = mock(BijhoudingPersoon.class);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PARTNER_OBJ_SLEUTEL)).thenReturn(partner);

        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        final String voorkomenSleutel = "1234";
        final GeslachtsaanduidingElement element = builder.maakGeslachtsaanduidingElementVoorVerval("ident", voorkomenSleutel);
        persoonParams.geslachtsaanduiding(element);
        final CorrectieVervalGeslachtsaanduidingGerelateerde actie = maakBasisActie(persoonParams);

        final PersoonGeslachtsaanduidingHistorie historie = new PersoonGeslachtsaanduidingHistorie(partner, Geslachtsaanduiding.MAN);
        when(partner.zoekRelatieHistorieVoorVoorkomenSleutel(voorkomenSleutel, PersoonGeslachtsaanduidingHistorie.class)).thenReturn(historie);
        assertNull(actie.getOngeldigAangewezenObjectOfVoorkomen());
    }

    @Test
    public void testOngeldigeAangewezenObjectOfVoorkomen_GeenHistorieGevonden() {
        final BijhoudingPersoon partner = mock(BijhoudingPersoon.class);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PARTNER_OBJ_SLEUTEL)).thenReturn(partner);

        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        final String voorkomenSleutel = "1234";
        final GeslachtsaanduidingElement element = builder.maakGeslachtsaanduidingElementVoorVerval("ident", voorkomenSleutel);
        persoonParams.geslachtsaanduiding(element);
        final CorrectieVervalGeslachtsaanduidingGerelateerde actie = maakBasisActie(persoonParams);

        when(partner.zoekRelatieHistorieVoorVoorkomenSleutel(voorkomenSleutel, PersoonGeslachtsaanduidingHistorie.class)).thenReturn(null);
        assertNotNull(actie.getOngeldigAangewezenObjectOfVoorkomen());
    }

    @Test
    public void testOngeldigeAangewezenObjectOfVoorkomen_GeenVoorkomenSleutel() {
        final BijhoudingPersoon partner = mock(BijhoudingPersoon.class);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PARTNER_OBJ_SLEUTEL)).thenReturn(partner);

        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        final GeslachtsaanduidingElement element = builder.maakGeslachtsaanduidingElementVoorVerval("ident", null);
        persoonParams.geslachtsaanduiding(element);
        final CorrectieVervalGeslachtsaanduidingGerelateerde actie = maakBasisActie(persoonParams);

        assertNull(actie.getOngeldigAangewezenObjectOfVoorkomen());
    }

    @Test
    public void testOngeldigeAangewezenObjectOfVoorkomen_GeenPartnerEntiteit() {
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PARTNER_OBJ_SLEUTEL)).thenReturn(null);

        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        final GeslachtsaanduidingElement element = builder.maakGeslachtsaanduidingElementVoorVerval("ident", "1234");
        persoonParams.geslachtsaanduiding(element);
        final CorrectieVervalGeslachtsaanduidingGerelateerde actie = maakBasisActie(persoonParams);

        assertNull(actie.getOngeldigAangewezenObjectOfVoorkomen());
    }

    @Test
    public void testMoetenIstGegevenVerwijderdWorden() {
        final CorrectieVervalGeslachtsaanduidingGerelateerde actie = maakBasisActie(new ElementBuilder.PersoonParameters());

        final BijhoudingPersoon ikPersoon = mock(BijhoudingPersoon.class);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, IK_OBJ_SLEUTEL)).thenReturn(ikPersoon);

        assertFalse(actie.moetenIstGegevensVerwijderdWorden());

        final Stapel stapel = new Stapel(ikPersoon, "05", 0);
        final Set<Stapel> stapels = Collections.singleton(stapel);
        when(ikPersoon.getStapels()).thenReturn(stapels);
        assertTrue(actie.moetenIstGegevensVerwijderdWorden());
    }

    @Test
    public void testVerwijderIstGegevens() {
        final CorrectieVervalGeslachtsaanduidingGerelateerde actie = maakBasisActie(new ElementBuilder.PersoonParameters());
        final BijhoudingPersoon ikPersoon = mock(BijhoudingPersoon.class);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, IK_OBJ_SLEUTEL)).thenReturn(ikPersoon);
        final Stapel stapel = new Stapel(ikPersoon, "05", 0);
        final Set<Stapel> stapels = new HashSet<>();
        stapels.add(stapel);
        when(ikPersoon.getStapels()).thenReturn(stapels);

        assertEquals(1, stapels.size());
        actie.verwijderIstGegevens();
        assertEquals(0, stapels.size());
    }

    @Test
    public void testValideerCorrectieInhoud() {
        final CorrectieVervalGeslachtsaanduidingGerelateerde actie = maakBasisActie(new ElementBuilder.PersoonParameters());
        assertEquals(0, actie.valideerCorrectieInhoud().size());
    }

    @Test
    public void testGetIstIngang() {
        final CorrectieVervalGeslachtsaanduidingGerelateerde actie = maakBasisActie(new ElementBuilder.PersoonParameters());
        assertEquals(actie.getPersoon(), actie.getIstIngang());
    }

    @Test
    public void testPeilDatum() {
        final Integer vandaag = DatumUtil.vandaag();
        when(bericht.getDatumOntvangst()).thenReturn(new DatumElement(vandaag));
        final CorrectieVervalGeslachtsaanduidingGerelateerde actie = maakBasisActie(new ElementBuilder.PersoonParameters());
        assertEquals(vandaag, actie.getPeilDatum().getWaarde());
    }

    private CorrectieVervalGeslachtsaanduidingGerelateerde maakBasisActie(final ElementBuilder.PersoonParameters partnerParams) {
        final PersoonGegevensElement partner = builder.maakPersoonGegevensElement("partner", PARTNER_OBJ_SLEUTEL, null, partnerParams);
        final BetrokkenheidElement partnerBetrokkenheid = builder.maakBetrokkenheidElement("partnerBetr", "partnerBetrObjSleutel",
                BetrokkenheidElementSoort.PARTNER, partner, null);
        final GeregistreerdPartnerschapElement gpElement =
                builder.maakGeregistreerdPartnerschapElement("gpElement", null, Collections.singletonList(partnerBetrokkenheid));
        final BetrokkenheidElement ikBetrokkenheid =
                builder.maakBetrokkenheidElement("ikBetr", "betrObjSleutel", null, BetrokkenheidElementSoort.OUDER, gpElement);
        final PersoonRelatieElement ikPersoon = builder.maakPersoonRelatieElement("persoon", null, IK_OBJ_SLEUTEL, Collections.singletonList(ikBetrokkenheid));
        final CorrectieVervalGeslachtsaanduidingGerelateerde actie =
                builder.maakCorrectieVervalGeslachtsaanduidingGerelateerdeActieElement("commId", ikPersoon, 'S');
        partner.setVerzoekBericht(bericht);
        ikPersoon.setVerzoekBericht(bericht);
        actie.setVerzoekBericht(bericht);
        return actie;
    }
}