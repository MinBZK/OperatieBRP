/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.HisVolledigRootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentIdentificatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import nl.bzk.brp.model.operationeel.kern.DocumentStandaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * TODO: Add documentation
 */
public class BijhoudingBerichtContextTest {

    @Test
    public void testBijgehoudenDocumenten() {
        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);

        DocumentModel document1 = new DocumentModel(StatischeObjecttypeBuilder.bouwSoortDocument("Geboorteakte"));
        ReflectionTestUtils.setField(document1, "iD", new Long(10));
        DocumentStandaardGroepModel standaard1 = new DocumentStandaardGroepModel(
            new DocumentIdentificatieAttribuut("1234567"),
            null, null, StatischeObjecttypeBuilder.bouwPartij(1234567, "partij"));
        document1.setStandaard(standaard1);
        context.voegBijgehoudenDocumentToe("document1", document1);

        DocumentModel document2 = new DocumentModel(StatischeObjecttypeBuilder.bouwSoortDocument("Koninklijk besluit"));
        ReflectionTestUtils.setField(document2, "iD", new Long(20));
        DocumentStandaardGroepModel standaard2 = new DocumentStandaardGroepModel(
            new DocumentIdentificatieAttribuut("3456789"),
            null, null, StatischeObjecttypeBuilder.bouwPartij(1234567, "partij"));
        document2.setStandaard(standaard2);
        context.voegBijgehoudenDocumentToe("document2", document2);

        assertEquals(2, context.getBijgehoudenDocumenten().size());
        assertTrue(context.getBijgehoudenDocumenten().containsValue(document1));
        assertTrue(context.getBijgehoudenDocumenten().containsValue(document2));
        assertNotNull(context.getBijgehoudenDocument("document1"));
        assertNotNull(context.getBijgehoudenDocument("document2"));
    }

    @Test
    public void testBijgehoudenPersonen() {
        PersoonHisVolledigImpl persoon1 =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        PersoonHisVolledigImpl persoon2 =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        PersoonHisVolledigImpl persoon3 =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        PersoonHisVolledigImpl persoon4 =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        PersoonHisVolledigImpl persoon5 =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));

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
        assertTrue(context.getBijgehoudenPersonen().contains(persoon1));
        assertTrue(context.getBijgehoudenPersonen().contains(persoon2));
        assertFalse(context.getBijgehoudenPersonen().contains(persoon3));
        assertTrue(context.getBijgehoudenPersonen().contains(persoon4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToevoegenTweeBijgehoudenPersonenMetHetZelfdeCommunicatieID() {
        PersoonHisVolledigImpl persoon1 =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);
        context.voegAangemaaktBijhoudingsRootObjectToe("persoon1", persoon1);
        context.voegAangemaaktBijhoudingsRootObjectToe("persoon1", persoon1);
    }

    @Test
    public void testToevoegenHuwelijkAlsBijgehoudenPersoon() {
        RelatieHisVolledigImpl huwelijk = new HuwelijkHisVolledigImpl();

        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);
        context.voegAangemaaktBijhoudingsRootObjectToe("huwelijk1", huwelijk);
        context.voegBestaandBijhoudingsRootObjectToe("huwelijk2", huwelijk);

        assertTrue(context.getBijgehoudenPersonen().isEmpty());
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
        assertTrue(context.getBijgehoudenPersonen().contains(persoon1));
        assertFalse(context.getBijgehoudenPersonen().contains(persoon2));
        assertTrue(context.getBijgehoudenPersonen().contains(persoon3));
        assertFalse(context.getBijgehoudenPersonen().contains(persoon4));
        assertTrue(context.getBijgehoudenPersonen().contains(persoon5));
        assertFalse(context.getBijgehoudenPersonen().contains(persoon6));

        assertEquals(6, context.getBijgehoudenPersonen(true).size());
        assertTrue(context.getBijgehoudenPersonen(true).contains(persoon1));
        assertTrue(context.getBijgehoudenPersonen(true).contains(persoon2));
        assertTrue(context.getBijgehoudenPersonen(true).contains(persoon3));
        assertTrue(context.getBijgehoudenPersonen(true).contains(persoon4));
        assertTrue(context.getBijgehoudenPersonen(true).contains(persoon5));
        assertFalse(context.getBijgehoudenPersonen(true).contains(persoon6));
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
        relatieBericht.setObjectSleutel("objectSleutel1");

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
    public void testZoekHisVolledigRootObjectVoorRelatieBerichtZonderCommunicatieId() {
        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);
        RelatieBericht relatieBericht = new HuwelijkBericht();

        RelatieHisVolledig relatieHisVolledig = mock(RelatieHisVolledig.class);
        context.voegAangemaaktBijhoudingsRootObjectToe(relatieBericht.getCommunicatieID(), relatieHisVolledig);

        HisVolledigRootObject resultaat = context.zoekHisVolledigRootObject(relatieBericht);
        assertNull(resultaat);
    }

    @Test
    public void testNieuwAangemaakteNietIngeschrevenen() {
        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);

        assertNotNull(context.getNietInBerichtMaarWelAangemaakteNietIngeschrevenen());
        assertTrue(context.getNietInBerichtMaarWelAangemaakteNietIngeschrevenen().isEmpty());

        context.voegNietInBerichtMaarWelAangemaakteNietIngeschreveneToe(new PersoonHisVolledigImpl(
            new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE)));
        context.voegNietInBerichtMaarWelAangemaakteNietIngeschreveneToe(new PersoonHisVolledigImpl(
            new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE)));

        assertEquals(2, context.getNietInBerichtMaarWelAangemaakteNietIngeschrevenen().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToevoegenAangemaakteIngeschreveneAlsNietIngeschrevene() {
        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);

        context.voegNietInBerichtMaarWelAangemaakteNietIngeschreveneToe(new PersoonHisVolledigImpl(
            new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE)));
    }

    @Test
    public void testVoegOnderzoekenToeVoorPersoon() {
        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);
        HisOnderzoekModel onderzoek = mock(HisOnderzoekModel.class);
        List<HisOnderzoekModel> onderzoeken = new ArrayList<>();
        onderzoeken.add(onderzoek);
        context.voegOnderzoekenToeVoorPersoon(12345, onderzoeken);

        assertNotNull(context.getPersoonOnderzoeken(12345));
        assertEquals(onderzoeken.size(), context.getPersoonOnderzoeken(12345).size());
        assertEquals(onderzoek, context.getPersoonOnderzoeken(12345).get(0));
    }

    @Test
    public void testVoegBestaandBijhoudingsRootObjectToe() {
        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setCommunicatieID("communicatieId1");

        PersoonHisVolledig persoonHisVolledig = mock(PersoonHisVolledig.class);
        context.voegBestaandBijhoudingsRootObjectToe(persoonBericht.getCommunicatieID(), persoonHisVolledig);

        assertNotNull(context.getBestaandeBijhoudingsRootObjecten());
        assertEquals(1, context.getBestaandeBijhoudingsRootObjecten().size());
        assertTrue(context.getBestaandeBijhoudingsRootObjecten().containsKey(persoonBericht.getCommunicatieID()));
        assertEquals(persoonHisVolledig,
            context.getBestaandeBijhoudingsRootObjecten().get(persoonBericht.getCommunicatieID()));
    }

    @Test
    public void testVoegAangemaaktBijhoudingsRootObjectToe() {
        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setCommunicatieID("communicatieId1");

        PersoonHisVolledig persoonHisVolledig = mock(PersoonHisVolledig.class);
        context.voegAangemaaktBijhoudingsRootObjectToe(persoonBericht.getCommunicatieID(), persoonHisVolledig);

        assertNotNull(context.getAangemaakteBijhoudingsRootObjecten());
        assertEquals(1, context.getAangemaakteBijhoudingsRootObjecten().size());
        assertTrue(context.getAangemaakteBijhoudingsRootObjecten().containsKey(persoonBericht.getCommunicatieID()));
        assertEquals(persoonHisVolledig,
            context.getAangemaakteBijhoudingsRootObjecten().get(persoonBericht.getCommunicatieID()));
    }

    @Test
    public void testSetAdministratieveHandeling() {
        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);
        assertNull(context.getAdministratieveHandeling());
        assertNull(context.getAdministratieveHandelingId());

        AdministratieveHandelingModel administratieveHandeling = mock(AdministratieveHandelingModel.class);
        when(administratieveHandeling.getID()).thenReturn(12345L);
        context.setAdministratieveHandeling(administratieveHandeling);

        assertNotNull(context.getAdministratieveHandeling());
        assertNotNull(context.getAdministratieveHandelingId());
        assertNotNull(context.getResultaatId());
        assertEquals(administratieveHandeling, context.getAdministratieveHandeling());
        assertEquals(administratieveHandeling.getID(), context.getAdministratieveHandelingId());
        assertEquals(administratieveHandeling.getID(), context.getResultaatId());
    }

    @Test
    public void testSetIdentificeerbareObjecten() {
        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);
        assertNull(context.getIdentificeerbareObjecten());

        final CommunicatieIdMap identificeerbareObjecten = new CommunicatieIdMap();
        context.setIdentificeerbareObjecten(identificeerbareObjecten);

        assertNotNull(context.getIdentificeerbareObjecten());
        assertEquals(identificeerbareObjecten, context.getIdentificeerbareObjecten());
    }

    @Test
    public void testVoegActieMappingToe() {
        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);
        assertNotNull(context.getAangemaakteBijhoudingsRootObjecten());
        assertEquals(0, context.getAangemaakteBijhoudingsRootObjecten().size());

        ActieBericht actieBericht = mock(ActieBericht.class);
        ActieModel actieModel = mock(ActieModel.class);
        context.voegActieMappingToe(actieBericht, actieModel);

        assertNotNull(context.getActieMapping());
        assertEquals(actieModel, context.getActieMapping().get(actieBericht));
    }
}
