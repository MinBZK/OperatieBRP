/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Test;

public class PersoonAddToSetTest {

    @Test
    public void testAddPersoonReisdocument() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonReisdocument persoonReisdocument1 = new PersoonReisdocument();
        final PersoonReisdocument persoonReisdocument2 = new PersoonReisdocument();
        persoon.addPersoonReisdocument(persoonReisdocument1);
        persoon.addPersoonReisdocument(persoonReisdocument2);
        assertTrue(persoon.getPersoonReisdocumentSet().contains(persoonReisdocument1));
        assertTrue(persoon.getPersoonReisdocumentSet().contains(persoonReisdocument2));
        assertEquals(2, persoon.getPersoonReisdocumentSet().size());
        assertEquals(persoon, persoon.getPersoonReisdocumentSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonVerificatie() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonVerificatie persoonVerificatie1 = new PersoonVerificatie();
        final PersoonVerificatie persoonVerificatie2 = new PersoonVerificatie();
        persoon.addPersoonVerificatie(persoonVerificatie1);
        persoon.addPersoonVerificatie(persoonVerificatie2);
        assertTrue(persoon.getPersoonVerificatieSet().contains(persoonVerificatie1));
        assertTrue(persoon.getPersoonVerificatieSet().contains(persoonVerificatie2));
        assertEquals(2, persoon.getPersoonVerificatieSet().size());
        assertEquals(persoon, persoon.getPersoonVerificatieSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonVoornaam() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonVoornaam persoonVoornaam1 = new PersoonVoornaam();
        final PersoonVoornaam persoonVoornaam2 = new PersoonVoornaam();
        persoon.addPersoonVoornaam(persoonVoornaam1);
        persoon.addPersoonVoornaam(persoonVoornaam2);
        assertTrue(persoon.getPersoonVoornaamSet().contains(persoonVoornaam1));
        assertTrue(persoon.getPersoonVoornaamSet().contains(persoonVoornaam2));
        assertEquals(2, persoon.getPersoonVoornaamSet().size());
        assertEquals(persoon, persoon.getPersoonVoornaamSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonSamengesteldeNaamHistorie() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonSamengesteldeNaamHistorie persoonSamengesteldeNaamHistorie1 = new PersoonSamengesteldeNaamHistorie();
        final PersoonSamengesteldeNaamHistorie persoonSamengesteldeNaamHistorie2 = new PersoonSamengesteldeNaamHistorie();
        persoon.addPersoonSamengesteldeNaamHistorie(persoonSamengesteldeNaamHistorie1);
        persoon.addPersoonSamengesteldeNaamHistorie(persoonSamengesteldeNaamHistorie2);
        assertTrue(persoon.getPersoonSamengesteldeNaamHistorieSet().contains(persoonSamengesteldeNaamHistorie1));
        assertTrue(persoon.getPersoonSamengesteldeNaamHistorieSet().contains(persoonSamengesteldeNaamHistorie2));
        assertEquals(2, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonSamengesteldeNaamHistorieSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonNaamgebruikHistorie() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonNaamgebruikHistorie persoonNaamgebruikHistorie1 = new PersoonNaamgebruikHistorie();
        final PersoonNaamgebruikHistorie persoonNaamgebruikHistorie2 = new PersoonNaamgebruikHistorie();
        persoon.addPersoonNaamgebruikHistorie(persoonNaamgebruikHistorie1);
        persoon.addPersoonNaamgebruikHistorie(persoonNaamgebruikHistorie2);
        assertTrue(persoon.getPersoonNaamgebruikHistorieSet().contains(persoonNaamgebruikHistorie1));
        assertTrue(persoon.getPersoonNaamgebruikHistorieSet().contains(persoonNaamgebruikHistorie2));
        assertEquals(2, persoon.getPersoonNaamgebruikHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonNaamgebruikHistorieSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonNationaliteit() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonNationaliteit persoonNationaliteit1 = new PersoonNationaliteit();
        final PersoonNationaliteit persoonNationaliteit2 = new PersoonNationaliteit();
        persoon.addPersoonNationaliteit(persoonNationaliteit1);
        persoon.addPersoonNationaliteit(persoonNationaliteit2);
        assertTrue(persoon.getPersoonNationaliteitSet().contains(persoonNationaliteit1));
        assertTrue(persoon.getPersoonNationaliteitSet().contains(persoonNationaliteit2));
        assertEquals(2, persoon.getPersoonNationaliteitSet().size());
        assertEquals(persoon, persoon.getPersoonNationaliteitSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonIndicatie() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonIndicatie persoonIndicatie1 = new PersoonIndicatie();
        final PersoonIndicatie persoonIndicatie2 = new PersoonIndicatie();
        persoon.addPersoonIndicatie(persoonIndicatie1);
        persoon.addPersoonIndicatie(persoonIndicatie2);
        assertTrue(persoon.getPersoonIndicatieSet().contains(persoonIndicatie1));
        assertTrue(persoon.getPersoonIndicatieSet().contains(persoonIndicatie2));
        assertEquals(2, persoon.getPersoonIndicatieSet().size());
        assertEquals(persoon, persoon.getPersoonIndicatieSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonGeslachtsnaamcomponent() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent1 = new PersoonGeslachtsnaamcomponent();
        final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent2 = new PersoonGeslachtsnaamcomponent();
        persoon.addPersoonGeslachtsnaamcomponent(persoonGeslachtsnaamcomponent1);
        persoon.addPersoonGeslachtsnaamcomponent(persoonGeslachtsnaamcomponent2);
        assertTrue(persoon.getPersoonGeslachtsnaamcomponentSet().contains(persoonGeslachtsnaamcomponent1));
        assertTrue(persoon.getPersoonGeslachtsnaamcomponentSet().contains(persoonGeslachtsnaamcomponent2));
        assertEquals(2, persoon.getPersoonGeslachtsnaamcomponentSet().size());
        assertEquals(persoon, persoon.getPersoonGeslachtsnaamcomponentSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonAdres() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonAdres persoonAdres1 = new PersoonAdres();
        final PersoonAdres persoonAdres2 = new PersoonAdres();
        persoon.addPersoonAdres(persoonAdres1);
        persoon.addPersoonAdres(persoonAdres2);
        assertTrue(persoon.getPersoonAdresSet().contains(persoonAdres1));
        assertTrue(persoon.getPersoonAdresSet().contains(persoonAdres2));
        assertEquals(2, persoon.getPersoonAdresSet().size());
        assertEquals(persoon, persoon.getPersoonAdresSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddBetrokkenheid() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final Betrokkenheid betrokkenheid1 = new Betrokkenheid();
        final Betrokkenheid betrokkenheid2 = new Betrokkenheid();
        persoon.addBetrokkenheid(betrokkenheid1);
        persoon.addBetrokkenheid(betrokkenheid2);
        assertTrue(persoon.getBetrokkenheidSet().contains(betrokkenheid1));
        assertTrue(persoon.getBetrokkenheidSet().contains(betrokkenheid2));
        assertEquals(2, persoon.getBetrokkenheidSet().size());
        assertEquals(persoon, persoon.getBetrokkenheidSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonEuVerkiezingenHistorie() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonDeelnameEuVerkiezingenHistorie persoonDeelnameEuVerkiezingenHistorie1 = new PersoonDeelnameEuVerkiezingenHistorie();
        final PersoonDeelnameEuVerkiezingenHistorie persoonDeelnameEuVerkiezingenHistorie2 = new PersoonDeelnameEuVerkiezingenHistorie();
        persoon.addPersoonDeelnameEuVerkiezingenHistorie(persoonDeelnameEuVerkiezingenHistorie1);
        persoon.addPersoonDeelnameEuVerkiezingenHistorie(persoonDeelnameEuVerkiezingenHistorie2);
        assertTrue(persoon.getPersoonDeelnameEuVerkiezingenHistorieSet().contains(persoonDeelnameEuVerkiezingenHistorie1));
        assertTrue(persoon.getPersoonDeelnameEuVerkiezingenHistorieSet().contains(persoonDeelnameEuVerkiezingenHistorie2));
        assertEquals(2, persoon.getPersoonDeelnameEuVerkiezingenHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonDeelnameEuVerkiezingenHistorieSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonGeboorteHistorie() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonGeboorteHistorie persoonGeboorteHistorie1 = new PersoonGeboorteHistorie();
        final PersoonGeboorteHistorie persoonGeboorteHistorie2 = new PersoonGeboorteHistorie();
        persoon.addPersoonGeboorteHistorie(persoonGeboorteHistorie1);
        persoon.addPersoonGeboorteHistorie(persoonGeboorteHistorie2);
        assertTrue(persoon.getPersoonGeboorteHistorieSet().contains(persoonGeboorteHistorie1));
        assertTrue(persoon.getPersoonGeboorteHistorieSet().contains(persoonGeboorteHistorie2));
        assertEquals(2, persoon.getPersoonGeboorteHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonGeboorteHistorieSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonGeslachtsaanduidingHistorie() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonGeslachtsaanduidingHistorie persoonGeslachtsaanduidingHistorie1 = new PersoonGeslachtsaanduidingHistorie();
        final PersoonGeslachtsaanduidingHistorie persoonGeslachtsaanduidingHistorie2 = new PersoonGeslachtsaanduidingHistorie();
        persoon.addPersoonGeslachtsaanduidingHistorie(persoonGeslachtsaanduidingHistorie1);
        persoon.addPersoonGeslachtsaanduidingHistorie(persoonGeslachtsaanduidingHistorie2);
        assertTrue(persoon.getPersoonGeslachtsaanduidingHistorieSet().contains(persoonGeslachtsaanduidingHistorie1));
        assertTrue(persoon.getPersoonGeslachtsaanduidingHistorieSet().contains(persoonGeslachtsaanduidingHistorie2));
        assertEquals(2, persoon.getPersoonGeslachtsaanduidingHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonGeslachtsaanduidingHistorieSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonIDHistorie() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonIDHistorie persoonIDHistorie1 = new PersoonIDHistorie();
        final PersoonIDHistorie persoonIDHistorie2 = new PersoonIDHistorie();
        persoon.addPersoonIDHistorie(persoonIDHistorie1);
        persoon.addPersoonIDHistorie(persoonIDHistorie2);
        assertTrue(persoon.getPersoonIDHistorieSet().contains(persoonIDHistorie1));
        assertTrue(persoon.getPersoonIDHistorieSet().contains(persoonIDHistorie2));
        assertEquals(2, persoon.getPersoonIDHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonIDHistorieSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonBijhoudingHistorie() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonBijhoudingHistorie persoonBijhoudingHistorie1 = new PersoonBijhoudingHistorie();
        final PersoonBijhoudingHistorie persoonBijhoudingHistorie2 = new PersoonBijhoudingHistorie();
        persoon.addPersoonBijhoudingHistorie(persoonBijhoudingHistorie1);
        persoon.addPersoonBijhoudingHistorie(persoonBijhoudingHistorie2);
        assertTrue(persoon.getPersoonBijhoudingHistorieSet().contains(persoonBijhoudingHistorie1));
        assertTrue(persoon.getPersoonBijhoudingHistorieSet().contains(persoonBijhoudingHistorie2));
        assertEquals(2, persoon.getPersoonBijhoudingHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonBijhoudingHistorieSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonImmigratieHistorie() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonMigratieHistorie persoonMigratieHistorie1 = new PersoonMigratieHistorie();
        final PersoonMigratieHistorie persoonMigratieHistorie2 = new PersoonMigratieHistorie();
        persoon.addPersoonMigratieHistorie(persoonMigratieHistorie1);
        persoon.addPersoonMigratieHistorie(persoonMigratieHistorie2);
        assertTrue(persoon.getPersoonMigratieHistorieSet().contains(persoonMigratieHistorie1));
        assertTrue(persoon.getPersoonMigratieHistorieSet().contains(persoonMigratieHistorie2));
        assertEquals(2, persoon.getPersoonMigratieHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonMigratieHistorieSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonInschrijvingHistorie() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonInschrijvingHistorie persoonInschrijvingHistorie1 = new PersoonInschrijvingHistorie();
        final PersoonInschrijvingHistorie persoonInschrijvingHistorie2 = new PersoonInschrijvingHistorie();
        persoon.addPersoonInschrijvingHistorie(persoonInschrijvingHistorie1);
        persoon.addPersoonInschrijvingHistorie(persoonInschrijvingHistorie2);
        assertTrue(persoon.getPersoonInschrijvingHistorieSet().contains(persoonInschrijvingHistorie1));
        assertTrue(persoon.getPersoonInschrijvingHistorieSet().contains(persoonInschrijvingHistorie2));
        assertEquals(2, persoon.getPersoonInschrijvingHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonInschrijvingHistorieSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonOverlijdenHistorie() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonOverlijdenHistorie persoonOverlijdenHistorie1 = new PersoonOverlijdenHistorie();
        final PersoonOverlijdenHistorie persoonOverlijdenHistorie2 = new PersoonOverlijdenHistorie();
        persoon.addPersoonOverlijdenHistorie(persoonOverlijdenHistorie1);
        persoon.addPersoonOverlijdenHistorie(persoonOverlijdenHistorie2);
        assertTrue(persoon.getPersoonOverlijdenHistorieSet().contains(persoonOverlijdenHistorie1));
        assertTrue(persoon.getPersoonOverlijdenHistorieSet().contains(persoonOverlijdenHistorie2));
        assertEquals(2, persoon.getPersoonOverlijdenHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonOverlijdenHistorieSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonPersoonskaartHistorie() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonPersoonskaartHistorie persoonPersoonskaartHistorie1 = new PersoonPersoonskaartHistorie();
        final PersoonPersoonskaartHistorie persoonPersoonskaartHistorie2 = new PersoonPersoonskaartHistorie();
        persoon.addPersoonPersoonskaartHistorie(persoonPersoonskaartHistorie1);
        persoon.addPersoonPersoonskaartHistorie(persoonPersoonskaartHistorie2);
        assertTrue(persoon.getPersoonPersoonskaartHistorieSet().contains(persoonPersoonskaartHistorie1));
        assertTrue(persoon.getPersoonPersoonskaartHistorieSet().contains(persoonPersoonskaartHistorie2));
        assertEquals(2, persoon.getPersoonPersoonskaartHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonPersoonskaartHistorieSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonVerblijfstitelHistorie() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonVerblijfsrechtHistorie persoonVerblijfsrechtHistorie1 = new PersoonVerblijfsrechtHistorie();
        final PersoonVerblijfsrechtHistorie persoonVerblijfsrechtHistorie2 = new PersoonVerblijfsrechtHistorie();
        persoon.addPersoonVerblijfsrechtHistorie(persoonVerblijfsrechtHistorie1);
        persoon.addPersoonVerblijfsrechtHistorie(persoonVerblijfsrechtHistorie2);
        assertTrue(persoon.getPersoonVerblijfsrechtHistorieSet().contains(persoonVerblijfsrechtHistorie1));
        assertTrue(persoon.getPersoonVerblijfsrechtHistorieSet().contains(persoonVerblijfsrechtHistorie2));
        assertEquals(2, persoon.getPersoonVerblijfsrechtHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonVerblijfsrechtHistorieSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonUitsluitingKiesrechtHistorie() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonUitsluitingKiesrechtHistorie persoonUitsluitingKiesrechtHistorie1 = new PersoonUitsluitingKiesrechtHistorie();
        final PersoonUitsluitingKiesrechtHistorie persoonUitsluitingKiesrechtHistorie2 = new PersoonUitsluitingKiesrechtHistorie();
        persoon.addPersoonUitsluitingKiesrechtHistorie(persoonUitsluitingKiesrechtHistorie1);
        persoon.addPersoonUitsluitingKiesrechtHistorie(persoonUitsluitingKiesrechtHistorie2);
        assertTrue(persoon.getPersoonUitsluitingKiesrechtHistorieSet().contains(persoonUitsluitingKiesrechtHistorie1));
        assertTrue(persoon.getPersoonUitsluitingKiesrechtHistorieSet().contains(persoonUitsluitingKiesrechtHistorie2));
        assertEquals(2, persoon.getPersoonUitsluitingKiesrechtHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonUitsluitingKiesrechtHistorieSet().iterator().next().getPersoon());
    }
}
