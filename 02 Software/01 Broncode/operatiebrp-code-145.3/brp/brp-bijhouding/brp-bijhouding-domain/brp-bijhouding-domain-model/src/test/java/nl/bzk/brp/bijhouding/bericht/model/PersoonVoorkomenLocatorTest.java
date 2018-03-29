/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.sql.Timestamp;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AutoriteitAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderlijkGezagHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBuitenlandsPersoonsnummer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBuitenlandsPersoonsnummerHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonDeelnameEuVerkiezingenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonInschrijvingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonMigratieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNaamgebruikHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNummerverwijzingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonOverlijdenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonPersoonskaartHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocumentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonUitsluitingKiesrechtHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerblijfsrechtHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerificatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerificatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerstrekkingsbeperking;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerstrekkingsbeperkingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import org.junit.Test;

/**
 * Testen van {@link PersoonVoorkomenLocator}.
 */
public class PersoonVoorkomenLocatorTest {

    @Test
    public void testZoekVoorkomen() {
        //setup
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonIDHistorie idVoorkomen1 = new PersoonIDHistorie(persoon);
        idVoorkomen1.setId(1L);
        final PersoonIDHistorie idVoorkomen2 = new PersoonIDHistorie(persoon);
        idVoorkomen2.setId(2L);
        persoon.addPersoonIDHistorie(idVoorkomen1);
        persoon.addPersoonIDHistorie(idVoorkomen2);
        final PersoonIndicatie indicatie1 = new PersoonIndicatie(persoon, SoortIndicatie.DERDE_HEEFT_GEZAG);
        final PersoonIndicatie indicatie2 = new PersoonIndicatie(persoon, SoortIndicatie.BEHANDELD_ALS_NEDERLANDER);
        final PersoonIndicatieHistorie indicatieVoorkomen1 = new PersoonIndicatieHistorie(indicatie1, true);
        indicatieVoorkomen1.setId(1L);
        final PersoonIndicatieHistorie indicatieVoorkomen2 = new PersoonIndicatieHistorie(indicatie2, true);
        indicatieVoorkomen2.setId(2L);
        indicatie1.addPersoonIndicatieHistorie(indicatieVoorkomen1);
        indicatie2.addPersoonIndicatieHistorie(indicatieVoorkomen2);
        persoon.addPersoonIndicatie(indicatie1);
        persoon.addPersoonIndicatie(indicatie2);
        final PersoonGeslachtsnaamcomponent geslachtsnaamcomponent1 = new PersoonGeslachtsnaamcomponent(persoon, 1);
        final PersoonGeslachtsnaamcomponentHistorie
                geslachtsnaamcomponentVoorkomen1 =
                new PersoonGeslachtsnaamcomponentHistorie(geslachtsnaamcomponent1, "stam");
        geslachtsnaamcomponentVoorkomen1.setId(1L);
        geslachtsnaamcomponent1.addPersoonGeslachtsnaamcomponentHistorie(geslachtsnaamcomponentVoorkomen1);
        persoon.addPersoonGeslachtsnaamcomponent(geslachtsnaamcomponent1);
        final PersoonAdres adres1 = new PersoonAdres(persoon);
        final PersoonAdresHistorie adresVoorkomen1 = new PersoonAdresHistorie(adres1, SoortAdres.WOONADRES, new LandOfGebied("1234", "naam"),
                new RedenWijzigingVerblijf(RedenWijzigingVerblijf.AMBTSHALVE, "naam"));
        adresVoorkomen1.setId(1L);
        adres1.addPersoonAdresHistorie(adresVoorkomen1);
        persoon.addPersoonAdres(adres1);
        final PersoonNationaliteit nationaliteit1 = new PersoonNationaliteit(persoon, new Nationaliteit("naam", "1234"));
        final PersoonNationaliteitHistorie nationaliteitVoorkomen1 = new PersoonNationaliteitHistorie(nationaliteit1);
        nationaliteitVoorkomen1.setId(1L);
        nationaliteit1.addPersoonNationaliteitHistorie(nationaliteitVoorkomen1);
        persoon.addPersoonNationaliteit(nationaliteit1);
        final PersoonReisdocument reisdocument1 = new PersoonReisdocument(persoon, new SoortNederlandsReisdocument("1234", "omschrijving"));
        final PersoonReisdocumentHistorie reisdocumentVoorkomen1 = new PersoonReisdocumentHistorie(0, 0, 0, "1", "1", reisdocument1);
        reisdocumentVoorkomen1.setId(1L);
        reisdocument1.addPersoonReisdocumentHistorieSet(reisdocumentVoorkomen1);
        persoon.addPersoonReisdocument(reisdocument1);
        final PersoonVerificatie verificatie1 = new PersoonVerificatie(persoon, new Partij("naam", "123456"), "soort");
        final PersoonVerificatieHistorie verificatieVoorkomen1 = new PersoonVerificatieHistorie(verificatie1, 0);
        verificatieVoorkomen1.setId(1L);
        verificatie1.addPersoonVerificatieHistorie(verificatieVoorkomen1);
        persoon.addPersoonVerificatie(verificatie1);
        final PersoonVerstrekkingsbeperking verstrekkingsbeperking1 = new PersoonVerstrekkingsbeperking(persoon);
        final PersoonVerstrekkingsbeperkingHistorie verstrekkingsbeperkingVoorkomen1 = new PersoonVerstrekkingsbeperkingHistorie(verstrekkingsbeperking1);
        verstrekkingsbeperkingVoorkomen1.setId(1L);
        verstrekkingsbeperking1.addPersoonVerstrekkingsbeperkingHistorie(verstrekkingsbeperkingVoorkomen1);
        persoon.addPersoonVerstrekkingsbeperking(verstrekkingsbeperking1);
        final PersoonVoornaam voornaam1 = new PersoonVoornaam(persoon, 1);
        final PersoonVoornaamHistorie voornaamVoorkomen1 = new PersoonVoornaamHistorie(voornaam1, "voornaam");
        voornaamVoorkomen1.setId(3L);
        voornaam1.addPersoonVoornaamHistorie(voornaamVoorkomen1);
        persoon.addPersoonVoornaam(voornaam1);
        final PersoonBuitenlandsPersoonsnummer
                buitenlandsPersoonsnummer1 =
                new PersoonBuitenlandsPersoonsnummer(persoon, new AutoriteitAfgifteBuitenlandsPersoonsnummer("1234"), "1");
        final PersoonBuitenlandsPersoonsnummerHistorie
                buitenlandsPersoonsnummerVoorkomen1 =
                new PersoonBuitenlandsPersoonsnummerHistorie(buitenlandsPersoonsnummer1);
        buitenlandsPersoonsnummerVoorkomen1.setId(22L);
        buitenlandsPersoonsnummer1.addPersoonBuitenlandsPersoonsnummerHistorie(buitenlandsPersoonsnummerVoorkomen1);
        persoon.addPersoonBuitenlandsPersoonsnummer(buitenlandsPersoonsnummer1);
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamVoorkomen1 = new PersoonSamengesteldeNaamHistorie(persoon, "stam", false, false);
        samengesteldeNaamVoorkomen1.setId(12L);
        persoon.addPersoonSamengesteldeNaamHistorie(samengesteldeNaamVoorkomen1);
        final PersoonNaamgebruikHistorie naamgebruikVoorkomen1 = new PersoonNaamgebruikHistorie(persoon, "test", false, Naamgebruik.EIGEN);
        naamgebruikVoorkomen1.setId(1L);
        persoon.addPersoonNaamgebruikHistorie(naamgebruikVoorkomen1);
        final PersoonDeelnameEuVerkiezingenHistorie deelnameEuVerkiezingenVoorkomen1 = new PersoonDeelnameEuVerkiezingenHistorie(persoon, false);
        deelnameEuVerkiezingenVoorkomen1.setId(1L);
        persoon.addPersoonDeelnameEuVerkiezingenHistorie(deelnameEuVerkiezingenVoorkomen1);
        final PersoonGeboorteHistorie geboorteVoorkomen1 = new PersoonGeboorteHistorie(persoon, 0, new LandOfGebied("1234", "naam"));
        geboorteVoorkomen1.setId(1L);
        persoon.addPersoonGeboorteHistorie(geboorteVoorkomen1);
        final PersoonGeslachtsaanduidingHistorie geslachtsaanduidingVoorkomen1 = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.MAN);
        geslachtsaanduidingVoorkomen1.setId(1L);
        persoon.addPersoonGeslachtsaanduidingHistorie(geslachtsaanduidingVoorkomen1);
        final PersoonBijhoudingHistorie
                bijhoudingVoorkomen1 =
                new PersoonBijhoudingHistorie(persoon, new Partij("test", "123456"), Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.BIJZONDERE_STATUS);
        bijhoudingVoorkomen1.setId(1L);
        persoon.addPersoonBijhoudingHistorie(bijhoudingVoorkomen1);
        final PersoonMigratieHistorie migratieVoorkomen1 = new PersoonMigratieHistorie(persoon, SoortMigratie.EMIGRATIE);
        migratieVoorkomen1.setId(1L);
        persoon.addPersoonMigratieHistorie(migratieVoorkomen1);
        final PersoonInschrijvingHistorie inschrijvingVoorkomen1 = new PersoonInschrijvingHistorie(persoon, 0, 1L, new Timestamp(System.currentTimeMillis()));
        inschrijvingVoorkomen1.setId(1L);
        persoon.addPersoonInschrijvingHistorie(inschrijvingVoorkomen1);
        final PersoonNummerverwijzingHistorie nummerverwijzingVoorkomen1 = new PersoonNummerverwijzingHistorie(persoon);
        nummerverwijzingVoorkomen1.setId(1L);
        persoon.addPersoonNummerverwijzingHistorie(nummerverwijzingVoorkomen1);
        final PersoonOverlijdenHistorie overlijdenVoorkomen1 = new PersoonOverlijdenHistorie(persoon, 0, new LandOfGebied("1234", "naam"));
        overlijdenVoorkomen1.setId(1L);
        persoon.addPersoonOverlijdenHistorie(overlijdenVoorkomen1);
        final PersoonPersoonskaartHistorie persoonskaartVoorkomen1 = new PersoonPersoonskaartHistorie(persoon, false);
        persoonskaartVoorkomen1.setId(1L);
        persoon.addPersoonPersoonskaartHistorie(persoonskaartVoorkomen1);
        final PersoonVerblijfsrechtHistorie
                verblijfsrechtVoorkomen1 =
                new PersoonVerblijfsrechtHistorie(persoon, new Verblijfsrecht("12", "omschrijving"), 0, 0);
        verblijfsrechtVoorkomen1.setId(1L);
        persoon.addPersoonVerblijfsrechtHistorie(verblijfsrechtVoorkomen1);
        final PersoonUitsluitingKiesrechtHistorie uitsluitingKiesrechtVoorkomen1 = new PersoonUitsluitingKiesrechtHistorie(persoon, false);
        uitsluitingKiesrechtVoorkomen1.setId(1L);
        persoon.addPersoonUitsluitingKiesrechtHistorie(uitsluitingKiesrechtVoorkomen1);
        final PersoonAfgeleidAdministratiefHistorie
                afgeleidAdministratiefVoorkomen1 =
                new PersoonAfgeleidAdministratiefHistorie((short) 1, persoon, new AdministratieveHandeling(new Partij("naam", "123456"),
                        SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND, new Timestamp(System.currentTimeMillis())),
                        new Timestamp(System.currentTimeMillis()));
        afgeleidAdministratiefVoorkomen1.setId(1L);
        persoon.addPersoonAfgeleidAdministratiefHistorie(afgeleidAdministratiefVoorkomen1);
        //familierechtelijke betrekkingen
        final Relatie ouderKindRelatie1 = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final RelatieHistorie relatieVoorkomen1 = new RelatieHistorie(ouderKindRelatie1);
        relatieVoorkomen1.setId(1L);
        ouderKindRelatie1.addRelatieHistorie(relatieVoorkomen1);
        final Relatie kindOuderRelatie1 = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final RelatieHistorie relatieVoorkomen2 = new RelatieHistorie(kindOuderRelatie1);
        relatieVoorkomen2.setId(2L);
        kindOuderRelatie1.addRelatieHistorie(relatieVoorkomen2);
        final Betrokkenheid ouderBetrokkenheid1 = new Betrokkenheid(SoortBetrokkenheid.OUDER, ouderKindRelatie1);
        final BetrokkenheidHistorie betrokkenheidVoorkomen1 = new BetrokkenheidHistorie(ouderBetrokkenheid1);
        betrokkenheidVoorkomen1.setId(1L);
        final BetrokkenheidOuderHistorie ouderVoorkomen1 = new BetrokkenheidOuderHistorie(ouderBetrokkenheid1);
        ouderVoorkomen1.setId(2L);
        ouderBetrokkenheid1.addBetrokkenheidHistorie(betrokkenheidVoorkomen1);
        ouderBetrokkenheid1.addBetrokkenheidOuderHistorie(ouderVoorkomen1);
        final Betrokkenheid ouderBetrokkenheid2 = new Betrokkenheid(SoortBetrokkenheid.OUDER, ouderKindRelatie1);
        final BetrokkenheidHistorie betrokkenheidVoorkomen2 = new BetrokkenheidHistorie(ouderBetrokkenheid2);
        betrokkenheidVoorkomen2.setId(3L);
        final BetrokkenheidOuderHistorie ouderVoorkomen2 = new BetrokkenheidOuderHistorie(ouderBetrokkenheid2);
        ouderVoorkomen2.setId(4L);
        ouderBetrokkenheid2.addBetrokkenheidHistorie(betrokkenheidVoorkomen2);
        ouderBetrokkenheid2.addBetrokkenheidOuderHistorie(ouderVoorkomen2);
        final BetrokkenheidOuderlijkGezagHistorie ouderlijkGezagVoorkomen1 = new BetrokkenheidOuderlijkGezagHistorie(ouderBetrokkenheid2, true);
        ouderlijkGezagVoorkomen1.setId(5L);
        ouderBetrokkenheid2.addBetrokkenheidOuderlijkGezagHistorie(ouderlijkGezagVoorkomen1);
        final Betrokkenheid kindBetrokkenheid1 = new Betrokkenheid(SoortBetrokkenheid.KIND, ouderKindRelatie1);
        final BetrokkenheidHistorie betrokkenheidVoorkomen3 = new BetrokkenheidHistorie(kindBetrokkenheid1);
        kindBetrokkenheid1.addBetrokkenheidHistorie(betrokkenheidVoorkomen3);
        final Betrokkenheid kindBetrokkenheid2 = new Betrokkenheid(SoortBetrokkenheid.KIND, kindOuderRelatie1);
        final BetrokkenheidHistorie betrokkenheidVoorkomen4 = new BetrokkenheidHistorie(kindBetrokkenheid2);
        kindBetrokkenheid2.addBetrokkenheidHistorie(betrokkenheidVoorkomen4);
        ouderKindRelatie1.addBetrokkenheid(ouderBetrokkenheid1);
        ouderKindRelatie1.addBetrokkenheid(kindBetrokkenheid1);
        kindOuderRelatie1.addBetrokkenheid(kindBetrokkenheid2);
        kindOuderRelatie1.addBetrokkenheid(ouderBetrokkenheid2);
        persoon.addBetrokkenheid(ouderBetrokkenheid1);
        persoon.addBetrokkenheid(kindBetrokkenheid2);
        final Persoon gerelateerdeKind = new Persoon(SoortPersoon.INGESCHREVENE);
        gerelateerdeKind.addBetrokkenheid(kindBetrokkenheid1);
        final Persoon gerelateerdeOuder = new Persoon(SoortPersoon.INGESCHREVENE);
        gerelateerdeOuder.addBetrokkenheid(ouderBetrokkenheid2);
        //huwelijk
        final Relatie huwelijkRelatie1 = new Relatie(SoortRelatie.HUWELIJK);
        final Betrokkenheid ikPartnerBetrokkenheid1 = new Betrokkenheid(SoortBetrokkenheid.PARTNER, huwelijkRelatie1);
        final BetrokkenheidHistorie ikPartnerBetrokkenheid1Voorkomen1 = new BetrokkenheidHistorie(ikPartnerBetrokkenheid1);
        ikPartnerBetrokkenheid1.addBetrokkenheidHistorie(ikPartnerBetrokkenheid1Voorkomen1);
        final Betrokkenheid anderPartnerBetrokkenheid1 = new Betrokkenheid(SoortBetrokkenheid.PARTNER, huwelijkRelatie1);
        final BetrokkenheidHistorie anderPartnerBetrokkenheid1Voorkomen1 = new BetrokkenheidHistorie(anderPartnerBetrokkenheid1);
        anderPartnerBetrokkenheid1.addBetrokkenheidHistorie(anderPartnerBetrokkenheid1Voorkomen1);
        huwelijkRelatie1.addBetrokkenheid(ikPartnerBetrokkenheid1);
        huwelijkRelatie1.addBetrokkenheid(anderPartnerBetrokkenheid1);
        final Persoon gerelateerdeHuwelijksPartner = new Persoon(SoortPersoon.INGESCHREVENE);
        gerelateerdeHuwelijksPartner.addBetrokkenheid(anderPartnerBetrokkenheid1);
        persoon.addBetrokkenheid(ikPartnerBetrokkenheid1);
        final PersoonIDHistorie idVoorkomen3 = new PersoonIDHistorie(gerelateerdeHuwelijksPartner);
        idVoorkomen3.setId(3L);
        gerelateerdeHuwelijksPartner.addPersoonIDHistorie(idVoorkomen3);

        //geregistreerd partnerschap
        final Relatie geregistreerdPartnerschapRelatie1 = new Relatie(SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        final Betrokkenheid ikPartnerBetrokkenheid2 = new Betrokkenheid(SoortBetrokkenheid.PARTNER, geregistreerdPartnerschapRelatie1);
        final BetrokkenheidHistorie ikPartnerBetrokkenheid2Voorkomen2 = new BetrokkenheidHistorie(ikPartnerBetrokkenheid2);
        ikPartnerBetrokkenheid2.addBetrokkenheidHistorie(ikPartnerBetrokkenheid2Voorkomen2);
        final Betrokkenheid anderPartnerBetrokkenheid2 = new Betrokkenheid(SoortBetrokkenheid.PARTNER, geregistreerdPartnerschapRelatie1);
        final BetrokkenheidHistorie anderPartnerBetrokkenheid2Voorkomen2 = new BetrokkenheidHistorie(anderPartnerBetrokkenheid2);
        anderPartnerBetrokkenheid2.addBetrokkenheidHistorie(anderPartnerBetrokkenheid2Voorkomen2);
        geregistreerdPartnerschapRelatie1.addBetrokkenheid(ikPartnerBetrokkenheid2);
        geregistreerdPartnerschapRelatie1.addBetrokkenheid(anderPartnerBetrokkenheid2);
        final Persoon gerelateerdeGeregistreerdPartner = new Persoon(SoortPersoon.INGESCHREVENE);
        gerelateerdeGeregistreerdPartner.addBetrokkenheid(anderPartnerBetrokkenheid2);
        persoon.addBetrokkenheid(ikPartnerBetrokkenheid2);
        final PersoonIDHistorie idVoorkomen4 = new PersoonIDHistorie(gerelateerdeGeregistreerdPartner);
        idVoorkomen4.setId(4L);
        gerelateerdeGeregistreerdPartner.addPersoonIDHistorie(idVoorkomen4);
        final PersoonIDHistorie idVoorkomen5 = new PersoonIDHistorie(gerelateerdeKind);
        idVoorkomen5.setId(5L);
        gerelateerdeKind.addPersoonIDHistorie(idVoorkomen5);

        //onbekende ouder !! bijzonder omdat gerelateerde persoon ontbreekt
        final Relatie kindOnbekendeOuderRelatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        kindOnbekendeOuderRelatie.setId(13L);
        final Betrokkenheid onbekendeOuder = PersoonObjectLocatorTest.maakBetrokkenheid(9L, SoortBetrokkenheid.OUDER, kindOnbekendeOuderRelatie);
        final BetrokkenheidOuderHistorie onbekendeOuderOuderschap1 = new BetrokkenheidOuderHistorie(onbekendeOuder);
        onbekendeOuderOuderschap1.setId(14L);
        onbekendeOuder.addBetrokkenheidOuderHistorie(onbekendeOuderOuderschap1);
        final BetrokkenheidOuderlijkGezagHistorie onbekendeOuderOuderlijkGezag1 = new BetrokkenheidOuderlijkGezagHistorie(onbekendeOuder, true);
        onbekendeOuderOuderlijkGezag1.setId(15L);
        onbekendeOuder.addBetrokkenheidOuderlijkGezagHistorie(onbekendeOuderOuderlijkGezag1);
        final Betrokkenheid kindVanOnbekendeOuder = PersoonObjectLocatorTest.maakBetrokkenheid(10L, SoortBetrokkenheid.KIND, kindOnbekendeOuderRelatie);
        final Persoon kindVanOnbekendeOuderPersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        kindVanOnbekendeOuderPersoon.addBetrokkenheid(kindVanOnbekendeOuder);

        //validate
        assertNull(PersoonVoorkomenLocator.zoek(Element.STELSEL_NAAM, 1L, persoon));
        assertSame(idVoorkomen1, PersoonVoorkomenLocator.zoek(Element.PERSOON_IDENTIFICATIENUMMERS, idVoorkomen1.getId(), persoon));
        assertSame(idVoorkomen2, PersoonVoorkomenLocator.zoek(Element.PERSOON_IDENTIFICATIENUMMERS, idVoorkomen2.getId(), persoon));
        assertSame(idVoorkomen1, PersoonVoorkomenLocator.zoek(Element.PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD, idVoorkomen1.getId(), persoon));
        assertNull(PersoonVoorkomenLocator.zoek(Element.PERSOON, idVoorkomen1.getId(), persoon));
        assertNull(PersoonVoorkomenLocator.zoek(Element.PERSOON_IDENTIFICATIENUMMERS, 3L, persoon));
        assertSame(indicatieVoorkomen1,
                PersoonVoorkomenLocator.zoek(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_STANDAARD, indicatieVoorkomen1.getId(), persoon));
        assertNull(PersoonVoorkomenLocator.zoek(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_STANDAARD, 99L, persoon));
        assertSame(indicatieVoorkomen2,
                PersoonVoorkomenLocator.zoek(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_STANDAARD, indicatieVoorkomen2.getId(), persoon));
        assertSame(indicatieVoorkomen1, PersoonVoorkomenLocator.zoek(Element.PERSOON_INDICATIE_STANDAARD, indicatieVoorkomen1.getId(), persoon));
        //soort indicatie moet overeenkomen
        assertNull(
                PersoonVoorkomenLocator.zoek(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_STANDAARD, indicatieVoorkomen1.getId(), persoon));
        assertNull(
                PersoonVoorkomenLocator.zoek(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_STANDAARD, indicatieVoorkomen2.getId(), persoon));
        assertNull(PersoonVoorkomenLocator
                .zoek(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_ACTIEINHOUD, indicatieVoorkomen1.getId(), persoon));
        assertNull(PersoonVoorkomenLocator
                .zoek(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_MIGRATIEREDENBEEINDIGENNATIONALITEIT, indicatieVoorkomen2.getId(),
                        persoon));
        assertSame(geslachtsnaamcomponentVoorkomen1,
                PersoonVoorkomenLocator.zoek(Element.PERSOON_GESLACHTSNAAMCOMPONENT_ADELLIJKETITELCODE, geslachtsnaamcomponentVoorkomen1.getId(), persoon));
        assertNull(PersoonVoorkomenLocator.zoek(Element.PERSOON_GESLACHTSNAAMCOMPONENT_ADELLIJKETITELCODE, 99L, persoon));
        assertSame(adresVoorkomen1, PersoonVoorkomenLocator.zoek(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1, adresVoorkomen1.getId(), persoon));
        assertNull(PersoonVoorkomenLocator.zoek(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1, 99L, persoon));
        assertSame(nationaliteitVoorkomen1, PersoonVoorkomenLocator.zoek(Element.PERSOON_NATIONALITEIT_STANDAARD, nationaliteitVoorkomen1.getId(), persoon));
        assertNull(PersoonVoorkomenLocator.zoek(Element.PERSOON_NATIONALITEIT_STANDAARD, 99L, persoon));
        assertSame(reisdocumentVoorkomen1,
                PersoonVoorkomenLocator.zoek(Element.PERSOON_REISDOCUMENT_DATUMEINDEDOCUMENT, reisdocumentVoorkomen1.getId(), persoon));
        assertNull(PersoonVoorkomenLocator.zoek(Element.PERSOON_REISDOCUMENT_DATUMEINDEDOCUMENT, 99L, persoon));
        assertSame(verificatieVoorkomen1, PersoonVoorkomenLocator.zoek(Element.PERSOON_VERIFICATIE_DATUM, verificatieVoorkomen1.getId(), persoon));
        assertNull(PersoonVoorkomenLocator.zoek(Element.PERSOON_VERIFICATIE_DATUM, 99L, persoon));
        assertSame(verstrekkingsbeperkingVoorkomen1,
                PersoonVoorkomenLocator.zoek(Element.PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT, verstrekkingsbeperkingVoorkomen1.getId(), persoon));
        assertNull(PersoonVoorkomenLocator.zoek(Element.PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT, 99L, persoon));
        assertSame(verstrekkingsbeperkingVoorkomen1, PersoonVoorkomenLocator
                .zoek(Element.PERSOON_VERSTREKKINGSBEPERKING_GEMEENTEVERORDENINGPARTIJCODE, verstrekkingsbeperkingVoorkomen1.getId(), persoon));
        assertSame(voornaamVoorkomen1, PersoonVoorkomenLocator.zoek(Element.PERSOON_VOORNAAM_DATUMAANVANGGELDIGHEID, voornaamVoorkomen1.getId(), persoon));
        assertNull(PersoonVoorkomenLocator.zoek(Element.PERSOON_VOORNAAM_DATUMAANVANGGELDIGHEID, 99L, persoon));
        assertSame(buitenlandsPersoonsnummerVoorkomen1, PersoonVoorkomenLocator
                .zoek(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_NADEREAANDUIDINGVERVAL, buitenlandsPersoonsnummerVoorkomen1.getId(), persoon));
        assertNull(PersoonVoorkomenLocator.zoek(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_NADEREAANDUIDINGVERVAL, 99L, persoon));
        assertSame(samengesteldeNaamVoorkomen1, PersoonVoorkomenLocator.zoek(Element.PERSOON_SAMENGESTELDENAAM, samengesteldeNaamVoorkomen1.getId(), persoon));
        assertSame(samengesteldeNaamVoorkomen1,
                PersoonVoorkomenLocator.zoek(Element.PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE, samengesteldeNaamVoorkomen1.getId(), persoon));
        assertSame(naamgebruikVoorkomen1, PersoonVoorkomenLocator.zoek(Element.PERSOON_NAAMGEBRUIK_CODE, naamgebruikVoorkomen1.getId(), persoon));
        assertSame(deelnameEuVerkiezingenVoorkomen1,
                PersoonVoorkomenLocator.zoek(Element.PERSOON_DEELNAMEEUVERKIEZINGEN_INDICATIEDEELNAME, deelnameEuVerkiezingenVoorkomen1.getId(), persoon));
        assertSame(geboorteVoorkomen1, PersoonVoorkomenLocator.zoek(Element.PERSOON_GEBOORTE_DATUM, geboorteVoorkomen1.getId(), persoon));
        assertSame(geslachtsaanduidingVoorkomen1,
                PersoonVoorkomenLocator.zoek(Element.PERSOON_GESLACHTSAANDUIDING, geslachtsaanduidingVoorkomen1.getId(), persoon));
        assertSame(bijhoudingVoorkomen1, PersoonVoorkomenLocator.zoek(Element.PERSOON_BIJHOUDING_BIJHOUDINGSAARDCODE, bijhoudingVoorkomen1.getId(), persoon));
        assertSame(migratieVoorkomen1, PersoonVoorkomenLocator.zoek(Element.PERSOON_MIGRATIE_AANGEVERCODE, migratieVoorkomen1.getId(), persoon));
        assertSame(inschrijvingVoorkomen1, PersoonVoorkomenLocator.zoek(Element.PERSOON_INSCHRIJVING_DATUM, inschrijvingVoorkomen1.getId(), persoon));
        assertSame(nummerverwijzingVoorkomen1,
                PersoonVoorkomenLocator.zoek(Element.PERSOON_NUMMERVERWIJZING_DATUMEINDEGELDIGHEID, nummerverwijzingVoorkomen1.getId(), persoon));
        assertSame(overlijdenVoorkomen1, PersoonVoorkomenLocator.zoek(Element.PERSOON_OVERLIJDEN_BUITENLANDSEPLAATS, overlijdenVoorkomen1.getId(), persoon));
        assertSame(persoonskaartVoorkomen1, PersoonVoorkomenLocator.zoek(Element.PERSOON_PERSOONSKAART_PARTIJCODE, persoonskaartVoorkomen1.getId(), persoon));
        assertSame(verblijfsrechtVoorkomen1, PersoonVoorkomenLocator.zoek(Element.PERSOON_VERBLIJFSRECHT, verblijfsrechtVoorkomen1.getId(), persoon));
        assertSame(uitsluitingKiesrechtVoorkomen1,
                PersoonVoorkomenLocator.zoek(Element.PERSOON_UITSLUITINGKIESRECHT_DATUMVOORZIENEINDE, uitsluitingKiesrechtVoorkomen1.getId(), persoon));
        assertSame(afgeleidAdministratiefVoorkomen1,
                PersoonVoorkomenLocator.zoek(Element.PERSOON_AFGELEIDADMINISTRATIEF_NADEREAANDUIDINGVERVAL, afgeleidAdministratiefVoorkomen1.getId(), persoon));
        assertSame(ouderVoorkomen1, PersoonVoorkomenLocator.zoek(Element.OUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN, ouderVoorkomen1.getId(), persoon));
        assertNull(PersoonVoorkomenLocator.zoek(Element.OUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN, 99L, persoon));
        assertSame(ouderVoorkomen1,
                PersoonVoorkomenLocator.zoek(Element.PERSOON_OUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN, ouderVoorkomen1.getId(), persoon));
        assertNull(PersoonVoorkomenLocator.zoek(Element.GERELATEERDEOUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN, ouderVoorkomen1.getId(), persoon));
        assertNull(PersoonVoorkomenLocator.zoek(Element.OUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN, ouderVoorkomen2.getId(), persoon));
        assertSame(ouderVoorkomen2,
                PersoonVoorkomenLocator.zoek(Element.GERELATEERDEOUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN, ouderVoorkomen2.getId(), persoon));
        assertNull(PersoonVoorkomenLocator.zoek(Element.OUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG, ouderlijkGezagVoorkomen1.getId(), persoon));
        assertSame(ouderlijkGezagVoorkomen1,
                PersoonVoorkomenLocator.zoek(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG, ouderlijkGezagVoorkomen1.getId(), persoon));
        assertNull(PersoonVoorkomenLocator.zoek(Element.OUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG, 99L, persoon));
        assertSame(relatieVoorkomen1, PersoonVoorkomenLocator.zoek(Element.RELATIE_DATUMAANVANG, relatieVoorkomen1.getId(), persoon));
        assertNull(PersoonVoorkomenLocator.zoek(Element.RELATIE_DATUMAANVANG, 99L, persoon));
        assertSame(idVoorkomen3,
                PersoonVoorkomenLocator.zoek(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS, idVoorkomen3.getId(), persoon));
        assertNull(PersoonVoorkomenLocator.zoek(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS, idVoorkomen3.getId(), persoon));
        assertSame(idVoorkomen4,
                PersoonVoorkomenLocator.zoek(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS, idVoorkomen4.getId(), persoon));
        assertNull(PersoonVoorkomenLocator.zoek(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS, idVoorkomen4.getId(), persoon));
        assertNull(PersoonVoorkomenLocator.zoek(Element.PERSOON_IDENTIFICATIENUMMERS, idVoorkomen4.getId(), persoon));
        assertNull(PersoonVoorkomenLocator.zoek(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS, idVoorkomen1.getId(), persoon));
        assertSame(idVoorkomen5, PersoonVoorkomenLocator.zoek(Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS, idVoorkomen5.getId(), persoon));

        assertSame(onbekendeOuderOuderschap1, PersoonVoorkomenLocator
                .zoek(Element.GERELATEERDEOUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN, onbekendeOuderOuderschap1.getId(), kindVanOnbekendeOuderPersoon));
        assertSame(onbekendeOuderOuderlijkGezag1, PersoonVoorkomenLocator
                .zoek(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG_DATUMAANVANGGELDIGHEID, onbekendeOuderOuderlijkGezag1.getId(), kindVanOnbekendeOuderPersoon));
    }
}
