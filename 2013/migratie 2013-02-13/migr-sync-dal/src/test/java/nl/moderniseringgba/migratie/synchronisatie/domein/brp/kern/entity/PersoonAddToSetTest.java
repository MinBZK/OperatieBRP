/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PersoonAddToSetTest {

    @Test
    public void testAddPersoonReisdocument() {
        final Persoon persoon = new Persoon();
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
        final Persoon persoon = new Persoon();
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
        final Persoon persoon = new Persoon();
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
        final Persoon persoon = new Persoon();
        final PersoonSamengesteldeNaamHistorie persoonSamengesteldeNaamHistorie1 =
                new PersoonSamengesteldeNaamHistorie();
        final PersoonSamengesteldeNaamHistorie persoonSamengesteldeNaamHistorie2 =
                new PersoonSamengesteldeNaamHistorie();
        persoon.addPersoonSamengesteldeNaamHistorie(persoonSamengesteldeNaamHistorie1);
        persoon.addPersoonSamengesteldeNaamHistorie(persoonSamengesteldeNaamHistorie2);
        assertTrue(persoon.getPersoonSamengesteldeNaamHistorieSet().contains(persoonSamengesteldeNaamHistorie1));
        assertTrue(persoon.getPersoonSamengesteldeNaamHistorieSet().contains(persoonSamengesteldeNaamHistorie2));
        assertEquals(2, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonSamengesteldeNaamHistorieSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonAanschrijvingHistorie() {
        final Persoon persoon = new Persoon();
        final PersoonAanschrijvingHistorie persoonAanschrijvingHistorie1 = new PersoonAanschrijvingHistorie();
        final PersoonAanschrijvingHistorie persoonAanschrijvingHistorie2 = new PersoonAanschrijvingHistorie();
        persoon.addPersoonAanschrijvingHistorie(persoonAanschrijvingHistorie1);
        persoon.addPersoonAanschrijvingHistorie(persoonAanschrijvingHistorie2);
        assertTrue(persoon.getPersoonAanschrijvingHistorieSet().contains(persoonAanschrijvingHistorie1));
        assertTrue(persoon.getPersoonAanschrijvingHistorieSet().contains(persoonAanschrijvingHistorie2));
        assertEquals(2, persoon.getPersoonAanschrijvingHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonAanschrijvingHistorieSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonNationaliteit() {
        final Persoon persoon = new Persoon();
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
        final Persoon persoon = new Persoon();
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
        final Persoon persoon = new Persoon();
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
        final Persoon persoon = new Persoon();
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
    public void testAddMultiRealiteitRegelGeldigVoorPersoon() {
        final Persoon persoon = new Persoon();
        final MultiRealiteitRegel multiRealiteitRegel1 = new MultiRealiteitRegel();
        final MultiRealiteitRegel multiRealiteitRegel2 = new MultiRealiteitRegel();
        persoon.addMultiRealiteitRegelGeldigVoorPersoon(multiRealiteitRegel1);
        persoon.addMultiRealiteitRegelGeldigVoorPersoon(multiRealiteitRegel2);
        assertTrue(persoon.getMultiRealiteitRegelGeldigVoorPersoonSet().contains(multiRealiteitRegel1));
        assertTrue(persoon.getMultiRealiteitRegelGeldigVoorPersoonSet().contains(multiRealiteitRegel2));
        assertEquals(2, persoon.getMultiRealiteitRegelGeldigVoorPersoonSet().size());
        assertEquals(persoon, persoon.getMultiRealiteitRegelGeldigVoorPersoonSet().iterator().next()
                .getGeldigVoorPersoon());
    }

    @Test
    public void testAddMultiRealiteitRegel() {
        final Persoon persoon = new Persoon();
        final MultiRealiteitRegel multiRealiteitRegel1 = new MultiRealiteitRegel();
        final MultiRealiteitRegel multiRealiteitRegel2 = new MultiRealiteitRegel();
        persoon.addMultiRealiteitRegel(multiRealiteitRegel1);
        persoon.addMultiRealiteitRegel(multiRealiteitRegel2);
        assertTrue(persoon.getMultiRealiteitRegelSet().contains(multiRealiteitRegel1));
        assertTrue(persoon.getMultiRealiteitRegelSet().contains(multiRealiteitRegel2));
        assertEquals(2, persoon.getMultiRealiteitRegelSet().size());
        assertEquals(persoon, persoon.getMultiRealiteitRegelSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddMultiRealiteitRegelMultiRealiteitPersoon() {
        final Persoon persoon = new Persoon();
        final MultiRealiteitRegel multiRealiteitRegel1 = new MultiRealiteitRegel();
        final MultiRealiteitRegel multiRealiteitRegel2 = new MultiRealiteitRegel();
        persoon.addMultiRealiteitRegelMultiRealiteitPersoon(multiRealiteitRegel1);
        persoon.addMultiRealiteitRegelMultiRealiteitPersoon(multiRealiteitRegel2);
        assertTrue(persoon.getMultiRealiteitRegelMultiRealiteitPersoonSet().contains(multiRealiteitRegel1));
        assertTrue(persoon.getMultiRealiteitRegelMultiRealiteitPersoonSet().contains(multiRealiteitRegel2));
        assertEquals(2, persoon.getMultiRealiteitRegelMultiRealiteitPersoonSet().size());
        assertEquals(persoon, persoon.getMultiRealiteitRegelMultiRealiteitPersoonSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddBetrokkenheid() {
        final Persoon persoon = new Persoon();
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
    public void testAddPersoonEUVerkiezingenHistorie() {
        final Persoon persoon = new Persoon();
        final PersoonEUVerkiezingenHistorie persoonEUVerkiezingenHistorie1 = new PersoonEUVerkiezingenHistorie();
        final PersoonEUVerkiezingenHistorie persoonEUVerkiezingenHistorie2 = new PersoonEUVerkiezingenHistorie();
        persoon.addPersoonEUVerkiezingenHistorie(persoonEUVerkiezingenHistorie1);
        persoon.addPersoonEUVerkiezingenHistorie(persoonEUVerkiezingenHistorie2);
        assertTrue(persoon.getPersoonEUVerkiezingenHistorieSet().contains(persoonEUVerkiezingenHistorie1));
        assertTrue(persoon.getPersoonEUVerkiezingenHistorieSet().contains(persoonEUVerkiezingenHistorie2));
        assertEquals(2, persoon.getPersoonEUVerkiezingenHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonEUVerkiezingenHistorieSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonGeboorteHistorie() {
        final Persoon persoon = new Persoon();
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
        final Persoon persoon = new Persoon();
        final PersoonGeslachtsaanduidingHistorie persoonGeslachtsaanduidingHistorie1 =
                new PersoonGeslachtsaanduidingHistorie();
        final PersoonGeslachtsaanduidingHistorie persoonGeslachtsaanduidingHistorie2 =
                new PersoonGeslachtsaanduidingHistorie();
        persoon.addPersoonGeslachtsaanduidingHistorie(persoonGeslachtsaanduidingHistorie1);
        persoon.addPersoonGeslachtsaanduidingHistorie(persoonGeslachtsaanduidingHistorie2);
        assertTrue(persoon.getPersoonGeslachtsaanduidingHistorieSet().contains(persoonGeslachtsaanduidingHistorie1));
        assertTrue(persoon.getPersoonGeslachtsaanduidingHistorieSet().contains(persoonGeslachtsaanduidingHistorie2));
        assertEquals(2, persoon.getPersoonGeslachtsaanduidingHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonGeslachtsaanduidingHistorieSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonIDHistorie() {
        final Persoon persoon = new Persoon();
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
    public void testAddPersoonBijhoudingsgemeenteHistorie() {
        final Persoon persoon = new Persoon();
        final PersoonBijhoudingsgemeenteHistorie persoonBijhoudingsgemeenteHistorie1 =
                new PersoonBijhoudingsgemeenteHistorie();
        final PersoonBijhoudingsgemeenteHistorie persoonBijhoudingsgemeenteHistorie2 =
                new PersoonBijhoudingsgemeenteHistorie();
        persoon.addPersoonBijhoudingsgemeenteHistorie(persoonBijhoudingsgemeenteHistorie1);
        persoon.addPersoonBijhoudingsgemeenteHistorie(persoonBijhoudingsgemeenteHistorie2);
        assertTrue(persoon.getPersoonBijhoudingsgemeenteHistorieSet().contains(persoonBijhoudingsgemeenteHistorie1));
        assertTrue(persoon.getPersoonBijhoudingsgemeenteHistorieSet().contains(persoonBijhoudingsgemeenteHistorie2));
        assertEquals(2, persoon.getPersoonBijhoudingsgemeenteHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonBijhoudingsgemeenteHistorieSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonImmigratieHistorie() {
        final Persoon persoon = new Persoon();
        final PersoonImmigratieHistorie persoonImmigratieHistorie1 = new PersoonImmigratieHistorie();
        final PersoonImmigratieHistorie persoonImmigratieHistorie2 = new PersoonImmigratieHistorie();
        persoon.addPersoonImmigratieHistorie(persoonImmigratieHistorie1);
        persoon.addPersoonImmigratieHistorie(persoonImmigratieHistorie2);
        assertTrue(persoon.getPersoonImmigratieHistorieSet().contains(persoonImmigratieHistorie1));
        assertTrue(persoon.getPersoonImmigratieHistorieSet().contains(persoonImmigratieHistorie2));
        assertEquals(2, persoon.getPersoonImmigratieHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonImmigratieHistorieSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonInschrijvingHistorie() {
        final Persoon persoon = new Persoon();
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
    public void testAddPersoonOpschortingHistorie() {
        final Persoon persoon = new Persoon();
        final PersoonOpschortingHistorie persoonOpschortingHistorie1 = new PersoonOpschortingHistorie();
        final PersoonOpschortingHistorie persoonOpschortingHistorie2 = new PersoonOpschortingHistorie();
        persoon.addPersoonOpschortingHistorie(persoonOpschortingHistorie1);
        persoon.addPersoonOpschortingHistorie(persoonOpschortingHistorie2);
        assertTrue(persoon.getPersoonOpschortingHistorieSet().contains(persoonOpschortingHistorie1));
        assertTrue(persoon.getPersoonOpschortingHistorieSet().contains(persoonOpschortingHistorie2));
        assertEquals(2, persoon.getPersoonOpschortingHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonOpschortingHistorieSet().iterator().next().getPersoon());
    }

    @Test
    public void testAddPersoonOverlijdenHistorie() {
        final Persoon persoon = new Persoon();
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
        final Persoon persoon = new Persoon();
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
    public void testAddPersoonBijhoudingsverantwoordelijkheidHistorie() {
        final Persoon persoon = new Persoon();
        final PersoonBijhoudingsverantwoordelijkheidHistorie persoonBijhoudingsverantwoordelijkheidHistorie1 =
                new PersoonBijhoudingsverantwoordelijkheidHistorie();
        final PersoonBijhoudingsverantwoordelijkheidHistorie persoonBijhoudingsverantwoordelijkheidHistorie2 =
                new PersoonBijhoudingsverantwoordelijkheidHistorie();
        persoon.addPersoonBijhoudingsverantwoordelijkheidHistorie(persoonBijhoudingsverantwoordelijkheidHistorie1);
        persoon.addPersoonBijhoudingsverantwoordelijkheidHistorie(persoonBijhoudingsverantwoordelijkheidHistorie2);
        assertTrue(persoon.getPersoonBijhoudingsverantwoordelijkheidHistorieSet().contains(
                persoonBijhoudingsverantwoordelijkheidHistorie1));
        assertTrue(persoon.getPersoonBijhoudingsverantwoordelijkheidHistorieSet().contains(
                persoonBijhoudingsverantwoordelijkheidHistorie2));
        assertEquals(2, persoon.getPersoonBijhoudingsverantwoordelijkheidHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonBijhoudingsverantwoordelijkheidHistorieSet().iterator().next()
                .getPersoon());
    }

    @Test
    public void testAddPersoonVerblijfsrechtHistorie() {
        final Persoon persoon = new Persoon();
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
    public void testAddPersoonUitsluitingNLKiesrechtHistorie() {
        final Persoon persoon = new Persoon();
        final PersoonUitsluitingNLKiesrechtHistorie persoonUitsluitingNLKiesrechtHistorie1 =
                new PersoonUitsluitingNLKiesrechtHistorie();
        final PersoonUitsluitingNLKiesrechtHistorie persoonUitsluitingNLKiesrechtHistorie2 =
                new PersoonUitsluitingNLKiesrechtHistorie();
        persoon.addPersoonUitsluitingNLKiesrechtHistorie(persoonUitsluitingNLKiesrechtHistorie1);
        persoon.addPersoonUitsluitingNLKiesrechtHistorie(persoonUitsluitingNLKiesrechtHistorie2);
        assertTrue(persoon.getPersoonUitsluitingNLKiesrechtHistorieSet().contains(
                persoonUitsluitingNLKiesrechtHistorie1));
        assertTrue(persoon.getPersoonUitsluitingNLKiesrechtHistorieSet().contains(
                persoonUitsluitingNLKiesrechtHistorie2));
        assertEquals(2, persoon.getPersoonUitsluitingNLKiesrechtHistorieSet().size());
        assertEquals(persoon, persoon.getPersoonUitsluitingNLKiesrechtHistorieSet().iterator().next().getPersoon());
    }
}
