/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.context;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.model.HisVolledigRootObject;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Assert;
import org.junit.Test;

public class BerichtContextTest {

    @Test
    public void testConstructorEnGetters() {
        BerichtContext context =
                new BijhoudingBerichtContext(new BerichtenIds(2L, 3L),
                        StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(), "ref", null);
        Assert.assertNotNull(context.getPartij());
        assertEquals(2, context.getIngaandBerichtId());
        assertEquals(3, context.getUitgaandBerichtId());
        Assert.assertNotNull(context.getTijdstipVerwerking());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorMetNullVoorBerichtenIds() {
        new BijhoudingBerichtContext(null, StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(), "ref", null);
    }

    @Test
    public void testBijgehoudenPersonen() {
        PersoonHisVolledigImpl persoon1 = new PersoonHisVolledigImpl(
            new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        PersoonHisVolledigImpl persoon2 = new PersoonHisVolledigImpl(
            new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        PersoonHisVolledigImpl persoon3 = new PersoonHisVolledigImpl(
            new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        PersoonHisVolledigImpl persoon4 = new PersoonHisVolledigImpl(
            new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        PersoonHisVolledigImpl persoon5 = new PersoonHisVolledigImpl(
            new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));

        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);
        context.voegAangemaaktBijhoudingsRootObjectToe("persoon1", persoon1);
        context.voegBestaandBijhoudingsRootObjectToe("persoon2", persoon2);
        context.voegBestaandBijhoudingsRootObjectToe("persoon3", persoon3);

        // Exclude persoon 3.
        context.voegWelInBerichtMaarNietBijgehoudenPersoonToe(persoon3);
        // Include persoon 4.
        context.voegNietInBerichtMaarWelBijgehoudenPersoonToe(persoon4);
        // Aangemaakte niet ingeschrevene
        context.voegNietInBerichtMaarWelAangemaakteNietIngeschreveneToe(persoon5);

        assertEquals(3, context.getBijgehoudenPersonen().size());
        Assert.assertTrue(context.getBijgehoudenPersonen().contains(persoon1));
        Assert.assertTrue(context.getBijgehoudenPersonen().contains(persoon2));
        Assert.assertFalse(context.getBijgehoudenPersonen().contains(persoon3));
        Assert.assertTrue(context.getBijgehoudenPersonen().contains(persoon4));
    }

    @Test
    public void testBijgehoudenPersonenMetNietIngeschreven() {
        PersoonHisVolledigImpl persoon1 =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        PersoonHisVolledigImpl persoon2 =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));
        PersoonHisVolledigImpl persoon3 =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        PersoonHisVolledigImpl persoon4 =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));
        PersoonHisVolledigImpl persoon5 =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        PersoonHisVolledigImpl persoon6 =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        PersoonHisVolledigImpl persoon7 =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));

        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);

        context.voegAangemaaktBijhoudingsRootObjectToe("persoon1", persoon1);
        context.voegAangemaaktBijhoudingsRootObjectToe("persoon2", persoon2);
        context.voegBestaandBijhoudingsRootObjectToe("persoon3", persoon3);
        context.voegBestaandBijhoudingsRootObjectToe("persoon4", persoon4);
        context.voegBestaandBijhoudingsRootObjectToe("persoon6", persoon6);
        context.voegNietInBerichtMaarWelBijgehoudenPersoonToe(persoon5);
        context.voegWelInBerichtMaarNietBijgehoudenPersoonToe(persoon6);
        context.voegNietInBerichtMaarWelAangemaakteNietIngeschreveneToe(persoon7);

        assertEquals(3, context.getBijgehoudenPersonen().size());
        Assert.assertTrue(context.getBijgehoudenPersonen().contains(persoon1));
        Assert.assertFalse(context.getBijgehoudenPersonen().contains(persoon2));
        Assert.assertTrue(context.getBijgehoudenPersonen().contains(persoon3));
        Assert.assertFalse(context.getBijgehoudenPersonen().contains(persoon4));
        Assert.assertTrue(context.getBijgehoudenPersonen().contains(persoon5));
        Assert.assertFalse(context.getBijgehoudenPersonen().contains(persoon6));

        assertEquals(6, context.getBijgehoudenPersonen(true).size());
        Assert.assertTrue(context.getBijgehoudenPersonen(true).contains(persoon1));
        Assert.assertTrue(context.getBijgehoudenPersonen(true).contains(persoon2));
        Assert.assertTrue(context.getBijgehoudenPersonen(true).contains(persoon3));
        Assert.assertTrue(context.getBijgehoudenPersonen(true).contains(persoon4));
        Assert.assertTrue(context.getBijgehoudenPersonen(true).contains(persoon5));
        Assert.assertFalse(context.getBijgehoudenPersonen(true).contains(persoon6));
    }

    @Test
    public void testZoekHisVolledigRootObjectVoorPersoonBerichtMetIdentificerendeSleutel() {
        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setIdentificerendeSleutel("identificerendeSleutel1");

        PersoonHisVolledig persoonHisVolledig = mock(PersoonHisVolledig.class);
        context.voegBestaandBijhoudingsRootObjectToe(persoonBericht.getIdentificerendeSleutel(), persoonHisVolledig);

        HisVolledigRootObject resultaat = context.zoekHisVolledigRootObject(persoonBericht);

        assertEquals(persoonHisVolledig, resultaat);
    }

    @Test
    public void testZoekHisVolledigRootObjectVoorPersoonBerichtMetIdentificerendeSleutelAangemaakteRootObjecten() {
        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setIdentificerendeSleutel("identificerendeSleutel1");

        PersoonHisVolledig persoonHisVolledig = mock(PersoonHisVolledig.class);
        context.voegAangemaaktBijhoudingsRootObjectToe(persoonBericht.getIdentificerendeSleutel(), persoonHisVolledig);

        HisVolledigRootObject resultaat = context.zoekHisVolledigRootObject(persoonBericht);

        assertEquals(persoonHisVolledig, resultaat);
    }

    @Test
    public void testZoekHisVolledigRootObjectVoorRelatieBerichtMetTechnischeSleutel() {
        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);
        RelatieBericht relatieBericht = new HuwelijkBericht();
        relatieBericht.setObjectSleutel("technischeSleutel1");

        RelatieHisVolledig relatieHisVolledig = mock(RelatieHisVolledig.class);
        context.voegBestaandBijhoudingsRootObjectToe(relatieBericht.getObjectSleutel(), relatieHisVolledig);

        HisVolledigRootObject resultaat = context.zoekHisVolledigRootObject(relatieBericht);

        assertEquals(relatieHisVolledig, resultaat);
    }

    @Test
    public void testZoekHisVolledigRootObjectVoorPersoonBerichtMetReferentieId() {
        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setReferentieID("referentieId1");

        PersoonHisVolledig persoonHisVolledig = mock(PersoonHisVolledig.class);
        context.voegAangemaaktBijhoudingsRootObjectToe(persoonBericht.getReferentieID(), persoonHisVolledig);

        HisVolledigRootObject resultaat = context.zoekHisVolledigRootObject(persoonBericht);

        assertEquals(persoonHisVolledig, resultaat);
    }

    @Test
    public void testZoekHisVolledigRootObjectVoorRelatieBerichtMetReferentieId() {
        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);
        RelatieBericht relatieBericht = new HuwelijkBericht();
        relatieBericht.setReferentieID("referentieId1");

        RelatieHisVolledig relatieHisVolledig = mock(RelatieHisVolledig.class);
        context.voegAangemaaktBijhoudingsRootObjectToe(relatieBericht.getReferentieID(), relatieHisVolledig);

        HisVolledigRootObject resultaat = context.zoekHisVolledigRootObject(relatieBericht);

        assertEquals(relatieHisVolledig, resultaat);
    }

    @Test
    public void testZoekHisVolledigRootObjectVoorPersoonBerichtMetCommunicatieId() {
        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setCommunicatieID("communicatieId1");

        PersoonHisVolledig persoonHisVolledig = mock(PersoonHisVolledig.class);
        context.voegAangemaaktBijhoudingsRootObjectToe(persoonBericht.getCommunicatieID(), persoonHisVolledig);

        HisVolledigRootObject resultaat = context.zoekHisVolledigRootObject(persoonBericht);

        assertEquals(persoonHisVolledig, resultaat);
    }

    @Test
    public void testZoekHisVolledigRootObjectVoorRelatieBerichtMetCommunicatieId() {
        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);
        RelatieBericht relatieBericht = new HuwelijkBericht();
        relatieBericht.setCommunicatieID("communicatieId1");

        RelatieHisVolledig relatieHisVolledig = mock(RelatieHisVolledig.class);
        context.voegAangemaaktBijhoudingsRootObjectToe(relatieBericht.getCommunicatieID(), relatieHisVolledig);

        HisVolledigRootObject resultaat = context.zoekHisVolledigRootObject(relatieBericht);

        assertEquals(relatieHisVolledig, resultaat);
    }

    @Test
    public void testNieuwAangemaakteNietIngeschrevenen() {
        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);

        Assert.assertNotNull(context.getNietInBerichtMaarWelAangemaakteNietIngeschrevenen());
        Assert.assertTrue(context.getNietInBerichtMaarWelAangemaakteNietIngeschrevenen().isEmpty());

        context.voegNietInBerichtMaarWelAangemaakteNietIngeschreveneToe(new PersoonHisVolledigImpl(
            new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE)));
        context.voegNietInBerichtMaarWelAangemaakteNietIngeschreveneToe(new PersoonHisVolledigImpl(
            new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE)));

        Assert.assertEquals(2, context.getNietInBerichtMaarWelAangemaakteNietIngeschrevenen().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToevoegenAangemaakteIngeschreveneAlsNietIngeschrevene() {
        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);

        context.voegNietInBerichtMaarWelAangemaakteNietIngeschreveneToe(new PersoonHisVolledigImpl(
            new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE)));
    }

}
