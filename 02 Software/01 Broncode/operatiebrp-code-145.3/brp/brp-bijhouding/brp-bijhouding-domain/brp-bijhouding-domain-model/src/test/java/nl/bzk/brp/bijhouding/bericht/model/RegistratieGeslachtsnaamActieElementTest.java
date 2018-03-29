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
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import org.junit.Test;

/**
 * Testen voor {@link RegistratieGeslachtsnaamActieElement}.
 */
public class RegistratieGeslachtsnaamActieElementTest extends AbstractHuwelijkInNederlandTestBericht {


    @Test
    public void testVerwerk() {
        // setup
        final BijhoudingVerzoekBericht bericht = getSuccesHuwelijkInNederlandBericht();
        final ActieElement actieElement = bericht.getAdministratieveHandeling().getActies().get(1);
        assertTrue(actieElement instanceof RegistratieGeslachtsnaamActieElement);
        final RegistratieGeslachtsnaamActieElement registratieGeslachtsnaamElement = (RegistratieGeslachtsnaamActieElement) actieElement;
        registratieGeslachtsnaamElement.getHoofdPersonen().get(0).setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        // execute
        registratieGeslachtsnaamElement.verwerk(bericht, getActie().getAdministratieveHandeling());

        final Persoon persoonEntiteit = getIngeschrevenPersoon();
        assertNotNull(persoonEntiteit);
        assertEquals(1, persoonEntiteit.getPersoonSamengesteldeNaamHistorieSet().size());
        final PersoonSamengesteldeNaamHistorie samengesteldeNaam = persoonEntiteit.getPersoonSamengesteldeNaamHistorieSet().iterator().next();
        assertEquals(AdellijkeTitel.B, samengesteldeNaam.getAdellijkeTitel());
        assertEquals("van", samengesteldeNaam.getVoorvoegsel());
        assertEquals(Character.valueOf(' '), samengesteldeNaam.getScheidingsteken());
        assertEquals("Bakkersma", samengesteldeNaam.getGeslachtsnaamstam());
        assertNull(samengesteldeNaam.getVoornamen());
        assertTrue(samengesteldeNaam.getIndicatieAfgeleid());
        assertFalse(samengesteldeNaam.getIndicatieNamenreeks());

        assertEquals(1, persoonEntiteit.getPersoonGeslachtsnaamcomponentSet().size());
        final PersoonGeslachtsnaamcomponent geslachtsnaamcomponent = persoonEntiteit.getPersoonGeslachtsnaamcomponentSet().iterator().next();
        assertEquals(1, geslachtsnaamcomponent.getPersoonGeslachtsnaamcomponentHistorieSet().size());
        final PersoonGeslachtsnaamcomponentHistorie geslachtsnaamcomponentHistorie =
                geslachtsnaamcomponent.getPersoonGeslachtsnaamcomponentHistorieSet().iterator().next();
        assertEquals(AdellijkeTitel.B, geslachtsnaamcomponentHistorie.getAdellijkeTitel());
        assertEquals("van", geslachtsnaamcomponentHistorie.getVoorvoegsel());
        assertEquals(Character.valueOf(' '), geslachtsnaamcomponentHistorie.getScheidingsteken());
        assertEquals("Bakkersma", geslachtsnaamcomponentHistorie.getStam());
        assertEquals(0, persoonEntiteit.getPersoonAfgeleidAdministratiefHistorieSet().size());
    }

    @Test
    public void testVerwerkOntrelateerdePersoon() {
        // setup
        final String objectsleutel = "212121";
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, objectsleutel)).thenReturn(getIngeschrevenPersoon());
        final ElementBuilder builder = new ElementBuilder();
        final String nieuweNaam = "NieuweNaam";
        final GeslachtsnaamcomponentElement
                nieuweGeslachtsnaam =
                builder.maakGeslachtsnaamcomponentElement("CI_persoon_3_gc_1", "K", "B", "van", ' ', nieuweNaam);
        final PersoonGegevensElement
                persoonGeslachtsnaamWijziging =
                builder.maakPersoonGegevensElement("CI_persoon_3", objectsleutel,
                        null, new ElementBuilder.PersoonParameters().geslachtsnaamcomponenten(Collections.singletonList(nieuweGeslachtsnaam)));
        persoonGeslachtsnaamWijziging.setVerzoekBericht(bericht);
        final RegistratieGeslachtsnaamActieElement
                actie =
                builder.maakRegistratieGeslachtsnaamActieElement("CI_actie_reg_geslachtsnaam", 20170101,
                        Collections.emptyList(), persoonGeslachtsnaamWijziging);
        // trigger bijwerken van groep samengestelde naam
        getIngeschrevenPersoon().setIsResultaatVanOntrelateren();
        // verify uitgangspositie
        assertTrue(getIngeschrevenPersoon().getPersoonSamengesteldeNaamHistorieSet().isEmpty());
        final PersoonSamengesteldeNaamHistorie
                samengesteldeNaamHistorie =
                new PersoonSamengesteldeNaamHistorie(getIngeschrevenPersoon().getDelegates().iterator().next(), "OudeNaam", false, false);
        samengesteldeNaamHistorie.setVoornamen("Jan Piet");
        samengesteldeNaamHistorie.setDatumTijdRegistratie(new Timestamp(System.currentTimeMillis() - 10_000));
        getIngeschrevenPersoon().addPersoonSamengesteldeNaamHistorie(samengesteldeNaamHistorie);
        // execute
        actie.verwerk(bericht, getAdministratieveHandeling());
        //verify
        assertEquals(3, getIngeschrevenPersoon().getPersoonSamengesteldeNaamHistorieSet().size());
        final PersoonSamengesteldeNaamHistorie
                nieuweSamengesteldeNaam =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getIngeschrevenPersoon().getPersoonSamengesteldeNaamHistorieSet());
        assertEquals(nieuweNaam, nieuweSamengesteldeNaam.getGeslachtsnaamstam());
        assertEquals(samengesteldeNaamHistorie.getVoornamen(), nieuweSamengesteldeNaam.getVoornamen());
        assertEquals(Predicaat.K, nieuweSamengesteldeNaam.getPredicaat());
        assertEquals(AdellijkeTitel.B, nieuweSamengesteldeNaam.getAdellijkeTitel());
        assertEquals("van", nieuweSamengesteldeNaam.getVoorvoegsel());
        assertEquals(Character.valueOf(' '), nieuweSamengesteldeNaam.getScheidingsteken());
    }

    @Test
    public void testVerwerkNiet() {
        // setup
        final BijhoudingVerzoekBericht bericht = getSuccesHuwelijkInNederlandBericht();
        final ActieElement actieElement = bericht.getAdministratieveHandeling().getActies().get(1);
        assertTrue(actieElement instanceof RegistratieGeslachtsnaamActieElement);
        final RegistratieGeslachtsnaamActieElement registratieGeslachtsnaamElement = (RegistratieGeslachtsnaamActieElement) actieElement;
        registratieGeslachtsnaamElement.getHoofdPersonen().get(0).setBijhoudingSituatie(BijhoudingSituatie.OPGESCHORT);
        // execute
        registratieGeslachtsnaamElement.verwerk(bericht, getActie().getAdministratieveHandeling());

        final Persoon persoonEntiteit = getIngeschrevenPersoon();
        assertNotNull(persoonEntiteit);
        assertEquals(0, persoonEntiteit.getPersoonSamengesteldeNaamHistorieSet().size());
    }
}
