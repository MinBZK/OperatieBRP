/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.sql.Timestamp;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Before;
import org.junit.Test;

/**
 * Testen voor {@link Onderzoek}.
 */
public class OnderzoekTest {

    private Persoon persoon;
    private BRPActie actie;
    private PersoonGeboorteHistorie persoonGeboorteHistorie1;
    private PersoonGeboorteHistorie persoonGeboorteHistorie2;
    private Onderzoek onderzoek;

    @Before
    public void setup() {
        final AdministratieveHandeling
                administratieveHandeling =
                new AdministratieveHandeling(new Partij("partij", "123456"), SoortAdministratieveHandeling.AANVANG_ONDERZOEK,
                        new Timestamp(System.currentTimeMillis()));
        actie =
                new BRPActie(SoortActie.REGISTRATIE_AANVANG_ONDERZOEK, administratieveHandeling, administratieveHandeling.getPartij(),
                        administratieveHandeling.getDatumTijdRegistratie());
        persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoonGeboorteHistorie1 = new PersoonGeboorteHistorie(persoon, 20000101, new LandOfGebied("1234", "land/gebied"));
        persoonGeboorteHistorie1.setId(1L);
        persoonGeboorteHistorie2 = new PersoonGeboorteHistorie(persoon, 20000102, persoonGeboorteHistorie1.getLandOfGebied());
        onderzoek = new Onderzoek(actie.getPartij(), persoon);
    }

    @Test
    public void kopieerGegevenInOnderzoekVoorNieuwVoorkomen() {
        //setup
        final GegevenInOnderzoek persoonGeboorteHistorie1InOnderzoek = new GegevenInOnderzoek(onderzoek, Element.PERSOON_GEBOORTE_DATUM);
        persoonGeboorteHistorie1InOnderzoek.setEntiteitOfVoorkomen(persoonGeboorteHistorie1);
        onderzoek.addGegevenInOnderzoek(persoonGeboorteHistorie1InOnderzoek);

        //valideer uitgangssituatie
        assertEquals(1, onderzoek.getGegevenInOnderzoekSet().size());
        assertEquals(persoonGeboorteHistorie1.getId(), onderzoek.getGegevenInOnderzoekSet().iterator().next().getEntiteitOfVoorkomen().getId());
        //execute
        onderzoek.kopieerGegevenInOnderzoekVoorNieuwGegeven(persoonGeboorteHistorie1, persoonGeboorteHistorie2, actie);
        //valideer uitkomst
        assertEquals(2, onderzoek.getGegevenInOnderzoekSet().size());
        final GegevenInOnderzoek
                nieuwGegevenInOnderzoek =
                onderzoek.getGegevenInOnderzoekSet().stream().filter(gio -> !persoonGeboorteHistorie1.getId().equals(gio.getEntiteitOfVoorkomen().getId()))
                        .findFirst().get();
        assertNotNull(nieuwGegevenInOnderzoek);
        assertEquals(1, nieuwGegevenInOnderzoek.getGegevenInOnderzoekHistorieSet().size());
        final GegevenInOnderzoekHistorie nieuweHistorie = nieuwGegevenInOnderzoek.getGegevenInOnderzoekHistorieSet().iterator().next();
        assertSame(actie, nieuweHistorie.getActieInhoud());
        assertEquals(actie.getDatumTijdRegistratie(), nieuweHistorie.getDatumTijdRegistratie());
        assertNull(nieuweHistorie.getDatumTijdVerval());
        assertSame(persoonGeboorteHistorie2, nieuwGegevenInOnderzoek.getEntiteitOfVoorkomen());
    }

    @Test(expected = IllegalArgumentException.class)
    public void kopieerGegevenInOnderzoekVoorNieuwVoorkomenFout() {
        persoonGeboorteHistorie1.setId(null);
        onderzoek.kopieerGegevenInOnderzoekVoorNieuwGegeven(persoonGeboorteHistorie1, persoonGeboorteHistorie2, actie);
    }

    @Test
    public void kopieerGegevenInOnderzoekVoorNieuwVoorkomenGeenKopie() {
        //execute
        onderzoek.kopieerGegevenInOnderzoekVoorNieuwGegeven(persoonGeboorteHistorie1, persoonGeboorteHistorie2, actie);
        //valideer dat er geen gegeven zijn gekopieerd
        assertTrue(onderzoek.getGegevenInOnderzoekSet().isEmpty());
    }

    @Test
    public void kopieerGegevenInOnderzoekVoorNieuwVoorkomenGeenKopieGeenMatch() {
        //setup
        final GegevenInOnderzoek persoonGeboorteHistorie1InOnderzoek = new GegevenInOnderzoek(onderzoek, Element.PERSOON_GEBOORTE_DATUM);
        persoonGeboorteHistorie1InOnderzoek.setEntiteitOfVoorkomen(persoonGeboorteHistorie1);
        onderzoek.addGegevenInOnderzoek(persoonGeboorteHistorie1InOnderzoek);

        final PersoonOverlijdenHistorie
                persoonOverlijdenHistorie =
                new PersoonOverlijdenHistorie(persoon, persoonGeboorteHistorie1.getDatumGeboorte(), persoonGeboorteHistorie1.getLandOfGebied());
        persoonOverlijdenHistorie.setId(11L);

        //valideer uitgangssituatie
        assertEquals(1, onderzoek.getGegevenInOnderzoekSet().size());
        //execute
        onderzoek.kopieerGegevenInOnderzoekVoorNieuwGegeven(persoonOverlijdenHistorie, persoonGeboorteHistorie2, actie);
        //valideer dat er geen gegeven zijn gekopieerd
        assertEquals(1, onderzoek.getGegevenInOnderzoekSet().size());
    }
}
