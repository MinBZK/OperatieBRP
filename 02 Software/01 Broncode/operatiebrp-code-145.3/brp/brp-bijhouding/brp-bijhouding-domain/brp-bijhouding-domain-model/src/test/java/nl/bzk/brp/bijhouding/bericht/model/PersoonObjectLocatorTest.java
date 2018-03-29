/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AutoriteitAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBuitenlandsPersoonsnummer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerificatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerstrekkingsbeperking;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import org.junit.Test;

/**
 * Testen voor {@link PersoonObjectLocator}.
 */
public class PersoonObjectLocatorTest {

    @Test
    public void testZoekObject() {
        //setup
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setId(1234567890L);
        //id historie (om te testen dat voorkomens niet gevonden mogen worden
        final PersoonIDHistorie idVoorkomen1 = new PersoonIDHistorie(persoon);
        idVoorkomen1.setId(1L);
        persoon.addPersoonIDHistorie(idVoorkomen1);
        //adres
        final PersoonAdres adres1 = new PersoonAdres(persoon);
        adres1.setId(1L);
        final PersoonAdresHistorie adresVoorkomen1 = new PersoonAdresHistorie(adres1, SoortAdres.WOONADRES, new LandOfGebied("1234", "naam"),
                new RedenWijzigingVerblijf(RedenWijzigingVerblijf.AMBTSHALVE, "naam"));
        adresVoorkomen1.setId(1L);
        adres1.addPersoonAdresHistorie(adresVoorkomen1);
        persoon.addPersoonAdres(adres1);
        //huwelijkspartner
        final Persoon huwelijkspartnerPersoon = new Persoon(SoortPersoon.PSEUDO_PERSOON);
        huwelijkspartnerPersoon.setId(1234567891L);
        final Relatie huwelijk = new Relatie(SoortRelatie.HUWELIJK);
        huwelijk.setId(1L);
        final Betrokkenheid huwelijkspartnerAnder = maakBetrokkenheid(1L, SoortBetrokkenheid.PARTNER, huwelijk);
        huwelijkspartnerPersoon.addBetrokkenheid(huwelijkspartnerAnder);
        final Betrokkenheid huwelijkspartnerIk = maakBetrokkenheid(2L, SoortBetrokkenheid.PARTNER, huwelijk);
        persoon.addBetrokkenheid(huwelijkspartnerIk);
        //geregistreerdepartner
        final Persoon geregistreerdepartnerPersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        geregistreerdepartnerPersoon.setId(1234567892L);
        final Relatie geregistreerdpartnerschap = new Relatie(SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        geregistreerdpartnerschap.setId(2L);
        final Betrokkenheid geregistreerdepartnerAnder = maakBetrokkenheid(3L, SoortBetrokkenheid.PARTNER, geregistreerdpartnerschap);
        geregistreerdepartnerPersoon.addBetrokkenheid(geregistreerdepartnerAnder);
        final Betrokkenheid geregistreerdepartnerIk = maakBetrokkenheid(4L, SoortBetrokkenheid.PARTNER, geregistreerdpartnerschap);
        persoon.addBetrokkenheid(geregistreerdepartnerIk);
        //kind ouder familierechtelijke betrekking
        final Persoon ouderPersoon = new Persoon(SoortPersoon.PSEUDO_PERSOON);
        ouderPersoon.setId(1234567893L);
        final Relatie kindOuderRelatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        kindOuderRelatie.setId(3L);
        final Betrokkenheid ouderAnder = maakBetrokkenheid(5L, SoortBetrokkenheid.OUDER, kindOuderRelatie);
        ouderPersoon.addBetrokkenheid(ouderAnder);
        final Betrokkenheid kindIk = maakBetrokkenheid(6L, SoortBetrokkenheid.KIND, kindOuderRelatie);
        persoon.addBetrokkenheid(kindIk);
        //ouder kind familierechtelijke betrekking
        final Persoon kindPersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        kindPersoon.setId(1234567894L);
        final Relatie ouderKindRelatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        ouderKindRelatie.setId(4L);
        final Betrokkenheid kindAnder = maakBetrokkenheid(7L, SoortBetrokkenheid.KIND, ouderKindRelatie);
        kindPersoon.addBetrokkenheid(kindAnder);
        final Betrokkenheid ouderIk = maakBetrokkenheid(8L, SoortBetrokkenheid.OUDER, ouderKindRelatie);
        persoon.addBetrokkenheid(ouderIk);
        //geslachtsnaamcomponent
        final PersoonGeslachtsnaamcomponent geslachtsnaamcomponent1 = new PersoonGeslachtsnaamcomponent(persoon, 1);
        geslachtsnaamcomponent1.setId(5L);
        persoon.addPersoonGeslachtsnaamcomponent(geslachtsnaamcomponent1);
        //indicatie
        final PersoonIndicatie indicatie1 = new PersoonIndicatie(persoon, SoortIndicatie.DERDE_HEEFT_GEZAG);
        indicatie1.setId(6L);
        persoon.addPersoonIndicatie(indicatie1);
        final PersoonIndicatie indicatie2 = new PersoonIndicatie(persoon, SoortIndicatie.ONDER_CURATELE);
        indicatie2.setId(66L);
        persoon.addPersoonIndicatie(indicatie2);
        //nationaliteit
        final PersoonNationaliteit nationaliteit1 = new PersoonNationaliteit(persoon, new Nationaliteit("naam", "1234"));
        nationaliteit1.setId(7L);
        persoon.addPersoonNationaliteit(nationaliteit1);
        //reisdocument
        final PersoonReisdocument reisdocument1 = new PersoonReisdocument(persoon, new SoortNederlandsReisdocument("1234", "omschrijving"));
        reisdocument1.setId(8L);
        persoon.addPersoonReisdocument(reisdocument1);
        //verificatie
        final PersoonVerificatie verificatie1 = new PersoonVerificatie(persoon, new Partij("naam", "123456"), "soort");
        verificatie1.setId(9L);
        persoon.addPersoonVerificatie(verificatie1);
        //verstrekkingsbeperking
        final PersoonVerstrekkingsbeperking verstrekkingsbeperking1 = new PersoonVerstrekkingsbeperking(persoon);
        verstrekkingsbeperking1.setId(10L);
        persoon.addPersoonVerstrekkingsbeperking(verstrekkingsbeperking1);
        //voornaam
        final PersoonVoornaam voornaam1 = new PersoonVoornaam(persoon, 1);
        voornaam1.setId(11L);
        persoon.addPersoonVoornaam(voornaam1);
        //buitenlands persoonsnummer
        final PersoonBuitenlandsPersoonsnummer
                buitenlandsPersoonsnummer1 =
                new PersoonBuitenlandsPersoonsnummer(persoon, new AutoriteitAfgifteBuitenlandsPersoonsnummer("1234"), "1");
        buitenlandsPersoonsnummer1.setId(12L);
        persoon.addPersoonBuitenlandsPersoonsnummer(buitenlandsPersoonsnummer1);
        //onbekende ouder !! bijzonder omdat gerelateerde persoon ontbreekt
        final Relatie kindOnbekendeOuderRelatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        kindOnbekendeOuderRelatie.setId(13L);
        final Betrokkenheid onbekendeOuder = maakBetrokkenheid(9L, SoortBetrokkenheid.OUDER, kindOnbekendeOuderRelatie);
        final Betrokkenheid kindVanOnbekendeOuder = maakBetrokkenheid(10L, SoortBetrokkenheid.KIND, kindOnbekendeOuderRelatie);
        final Persoon kindVanOnbekendeOuderPersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        kindVanOnbekendeOuderPersoon.addBetrokkenheid(kindVanOnbekendeOuder);

        //validate
        assertNull("Er mag geen voorkomen gevonden worden door de object locator", PersoonObjectLocator.zoek(Element.PERSOON_IDENTIFICATIENUMMERS, idVoorkomen1.getId(), persoon));
        assertSame(persoon, PersoonObjectLocator.zoek(Element.PERSOON, persoon.getId(), persoon));
        assertSame(adres1, PersoonObjectLocator.zoek(Element.PERSOON_ADRES, adres1.getId(), persoon));
        assertSame(huwelijkspartnerPersoon, PersoonObjectLocator.zoek(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON, huwelijkspartnerPersoon.getId(), persoon));
        assertSame(huwelijkspartnerPersoon, PersoonObjectLocator.zoek(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_ACTIEINHOUD, huwelijkspartnerPersoon.getId(), persoon));
        assertSame(huwelijkspartnerPersoon, PersoonObjectLocator.zoek(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE, huwelijkspartnerPersoon.getId(), persoon));
        assertNull(PersoonObjectLocator.zoek(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON, huwelijkspartnerPersoon.getId(), persoon));
        assertSame(geregistreerdepartnerPersoon, PersoonObjectLocator.zoek(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON, geregistreerdepartnerPersoon.getId(), persoon));
        assertNull(PersoonObjectLocator.zoek(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON, geregistreerdepartnerPersoon.getId(), persoon));
        assertSame(ouderPersoon, PersoonObjectLocator.zoek(Element.GERELATEERDEOUDER_PERSOON, ouderPersoon.getId(), persoon));
        assertNull(PersoonObjectLocator.zoek(Element.GERELATEERDEKIND_PERSOON, ouderPersoon.getId(), persoon));
        assertSame(kindPersoon, PersoonObjectLocator.zoek(Element.GERELATEERDEKIND_PERSOON, kindPersoon.getId(), persoon));
        assertNull(PersoonObjectLocator.zoek(Element.GERELATEERDEOUDER_PERSOON, kindPersoon.getId(), persoon));
        assertSame(huwelijk, PersoonObjectLocator.zoek(Element.HUWELIJK, huwelijk.getId(), persoon));
        assertNull(PersoonObjectLocator.zoek(Element.GEREGISTREERDPARTNERSCHAP, huwelijk.getId(), persoon));
        assertSame(geregistreerdpartnerschap, PersoonObjectLocator.zoek(Element.GEREGISTREERDPARTNERSCHAP, geregistreerdpartnerschap.getId(), persoon));
        assertNull(PersoonObjectLocator.zoek(Element.HUWELIJK, geregistreerdpartnerschap.getId(), persoon));
        assertSame(huwelijk, PersoonObjectLocator.zoek(Element.RELATIE, huwelijk.getId(), persoon));
        assertSame(geregistreerdpartnerschap, PersoonObjectLocator.zoek(Element.RELATIE, geregistreerdpartnerschap.getId(), persoon));
        assertSame(kindOuderRelatie, PersoonObjectLocator.zoek(Element.FAMILIERECHTELIJKEBETREKKING, kindOuderRelatie.getId(), persoon));
        assertNull(PersoonObjectLocator.zoek(Element.HUWELIJK, kindOuderRelatie.getId(), persoon));
        assertSame(kindOuderRelatie, PersoonObjectLocator.zoek(Element.RELATIE, kindOuderRelatie.getId(), persoon));
        assertSame(ouderKindRelatie, PersoonObjectLocator.zoek(Element.FAMILIERECHTELIJKEBETREKKING, ouderKindRelatie.getId(), persoon));
        assertNull(PersoonObjectLocator.zoek(Element.GEREGISTREERDPARTNERSCHAP, ouderKindRelatie.getId(), persoon));
        assertSame(ouderKindRelatie, PersoonObjectLocator.zoek(Element.RELATIE, ouderKindRelatie.getId(), persoon));
        assertSame(huwelijkspartnerAnder, PersoonObjectLocator.zoek(Element.GERELATEERDEHUWELIJKSPARTNER, huwelijkspartnerAnder.getId(), persoon));
        assertNull(PersoonObjectLocator.zoek(Element.GERELATEERDEGEREGISTREERDEPARTNER, huwelijkspartnerAnder.getId(), persoon));
        assertSame(huwelijkspartnerIk, PersoonObjectLocator.zoek(Element.PARTNER, huwelijkspartnerIk.getId(), persoon));
        assertSame(geregistreerdepartnerAnder, PersoonObjectLocator.zoek(Element.GERELATEERDEGEREGISTREERDEPARTNER, geregistreerdepartnerAnder.getId(), persoon));
        assertNull(PersoonObjectLocator.zoek(Element.GERELATEERDEHUWELIJKSPARTNER, geregistreerdepartnerAnder.getId(), persoon));
        assertSame(geregistreerdepartnerIk, PersoonObjectLocator.zoek(Element.PARTNER, geregistreerdepartnerIk.getId(), persoon));
        assertSame(ouderAnder, PersoonObjectLocator.zoek(Element.GERELATEERDEOUDER, ouderAnder.getId(), persoon));
        assertSame(ouderIk, PersoonObjectLocator.zoek(Element.OUDER, ouderIk.getId(), persoon));
        assertSame(kindAnder, PersoonObjectLocator.zoek(Element.GERELATEERDEKIND, kindAnder.getId(), persoon));
        assertSame(kindIk, PersoonObjectLocator.zoek(Element.KIND, kindIk.getId(), persoon));
        assertSame(geslachtsnaamcomponent1, PersoonObjectLocator.zoek(Element.PERSOON_GESLACHTSNAAMCOMPONENT, geslachtsnaamcomponent1.getId(), persoon));
        assertSame(indicatie1, PersoonObjectLocator.zoek(Element.PERSOON_INDICATIE, indicatie1.getId(), persoon));
        assertSame(indicatie1, PersoonObjectLocator.zoek(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG, indicatie1.getId(), persoon));
        assertSame(indicatie2, PersoonObjectLocator.zoek(Element.PERSOON_INDICATIE, indicatie2.getId(), persoon));
        assertSame(indicatie2, PersoonObjectLocator.zoek(Element.PERSOON_INDICATIE_ONDERCURATELE, indicatie2.getId(), persoon));
        assertNull(PersoonObjectLocator.zoek(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE, indicatie1.getId(), persoon));
        assertNull(PersoonObjectLocator.zoek(Element.PERSOON_INDICATIE_ONDERCURATELE, indicatie1.getId(), persoon));
        assertNull(PersoonObjectLocator.zoek(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING, indicatie1.getId(), persoon));
        assertNull(PersoonObjectLocator.zoek(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER, indicatie1.getId(), persoon));
        assertNull(PersoonObjectLocator.zoek(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER, indicatie1.getId(), persoon));
        assertNull(PersoonObjectLocator.zoek(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT, indicatie1.getId(), persoon));
        assertNull(PersoonObjectLocator.zoek(Element.PERSOON_INDICATIE_STAATLOOS, indicatie1.getId(), persoon));
        assertSame(nationaliteit1, PersoonObjectLocator.zoek(Element.PERSOON_NATIONALITEIT, nationaliteit1.getId(), persoon));
        assertSame(reisdocument1, PersoonObjectLocator.zoek(Element.PERSOON_REISDOCUMENT, reisdocument1.getId(), persoon));
        assertSame(verificatie1, PersoonObjectLocator.zoek(Element.PERSOON_VERIFICATIE, verificatie1.getId(), persoon));
        assertSame(verstrekkingsbeperking1, PersoonObjectLocator.zoek(Element.PERSOON_VERSTREKKINGSBEPERKING, verstrekkingsbeperking1.getId(), persoon));
        assertSame(verstrekkingsbeperking1, PersoonObjectLocator.zoek(Element.PERSOON_VERSTREKKINGSBEPERKING_GEMEENTEVERORDENINGPARTIJCODE, verstrekkingsbeperking1.getId(), persoon));
        assertSame(voornaam1, PersoonObjectLocator.zoek(Element.PERSOON_VOORNAAM, voornaam1.getId(), persoon));
        assertSame(voornaam1, PersoonObjectLocator.zoek(Element.PERSOON_VOORNAAM_ACTIEINHOUD, voornaam1.getId(), persoon));
        assertSame(buitenlandsPersoonsnummer1, PersoonObjectLocator.zoek(Element.PERSOON_BUITENLANDSPERSOONSNUMMER, buitenlandsPersoonsnummer1.getId(), persoon));
        //bijzonder geval waarbij gerelateerde persoon ontbreekt en dus niet gezocht kan worden via gerelateerde persoon
        assertSame(onbekendeOuder, PersoonObjectLocator.zoek(Element.GERELATEERDEOUDER, onbekendeOuder.getId(), kindVanOnbekendeOuderPersoon));
    }

    static Betrokkenheid maakBetrokkenheid(final long id, final SoortBetrokkenheid soortBetrokkenheid, final Relatie relatie) {
        final Betrokkenheid result = new Betrokkenheid(soortBetrokkenheid, relatie);
        result.setId(id);
        final BetrokkenheidHistorie betrokkenheidHistorie = new BetrokkenheidHistorie(result);
        result.addBetrokkenheidHistorie(betrokkenheidHistorie);
        relatie.addBetrokkenheid(result);
        return result;
    }
}
