/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.testpersoonbouwers;

import java.util.Date;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdres;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.OuderOuderlijkGezagGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonGeslachtsnaamcomponentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieDerdeHeeftGezagHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieOnderCurateleHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieStaatloosHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonReisdocumentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVerificatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVoornaamHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerstrekkingsbeperkingModel;
import nl.bzk.brp.util.RandomTechnischeSleutelService;
import nl.bzk.brp.util.RelatieTestUtil;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.VerantwoordingTestUtil;
import nl.bzk.brp.util.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.KindHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PartnerHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieBehandeldAlsNederlanderHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieOnderCurateleHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieStaatloosHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieVastgesteldNietNederlanderHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonReisdocumentHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVerificatieHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVoornaamHisVolledigImplBuilder;
import org.springframework.test.util.ReflectionTestUtils;


/**
 */
public final class TestPersoonAntwoordPersoon {

    private static final int REGISTRATIE_DATUM = 20131215;
    private static final int REGISTRATIE_JAAR  = 2013;
    private static final int REGISTRATIE_MAAND = 12;
    private static final int REGISTRATIE_DAG   = 15;

    private static final String WOONPLAATSNAAM = "woonplaats";

    @SuppressWarnings("deprecation")
    private static final DatumEvtDeelsOnbekendAttribuut ACTIE_DATUM          =
        new DatumEvtDeelsOnbekendAttribuut(new DatumAttribuut(new Date(REGISTRATIE_JAAR - 1900, REGISTRATIE_MAAND - 1, REGISTRATIE_DAG)));
    private static final DatumTijdAttribuut             TIJDSTIP_REGISTRATIE =
        DatumTijdAttribuut.bouwDatumTijd(REGISTRATIE_JAAR, REGISTRATIE_MAAND, REGISTRATIE_DAG, 12, 0, 0);

    private TestPersoonAntwoordPersoon() {
        // geen instantie nodig
    }

    public static PersoonHisVolledigImpl maakAntwoordPersoon() {
        return maakAntwoordPersoon(false);
    }

    public static PersoonHisVolledigImpl maakAntwoordPersoon(final boolean defaultMagGeleverdWordenVoorAttributen) {
        TIJDSTIP_REGISTRATIE.setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        ACTIE_DATUM.setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);

        final AdministratieveHandelingModel administratieveHandeling = VerantwoordingTestUtil
            .bouwAdministratieveHandeling(SoortAdministratieveHandeling.G_B_A_INITIELE_VULLING, null,
                                          new OntleningstoelichtingAttribuut("Ontleend via de rechter."), TIJDSTIP_REGISTRATIE);
        ReflectionTestUtils.setField(administratieveHandeling, "iD", 100L);

        final ActieModel eenActie = new ActieModel(new SoortActieAttribuut(SoortActie.CONVERSIE_G_B_A), administratieveHandeling, null, ACTIE_DATUM, null,
            TIJDSTIP_REGISTRATIE, null);
        final Dienst eenDienst = TestDienstBuilder.maker().metSoortDienst(SoortDienst.DUMMY).maak();
        eenActie.setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        ReflectionTestUtils.setField(eenActie, "iD", 100L);

        administratieveHandeling.getActies().add(eenActie);

        final PersoonHisVolledigImplBuilder persoonHisVolledigImplBuilder =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE, defaultMagGeleverdWordenVoorAttributen);

        final String blAdresRegel = "hiep";
        final PersoonHisVolledigImpl persoon =
                persoonHisVolledigImplBuilder
                        .nieuwIdentificatienummersRecord(eenActie).burgerservicenummer(123456789)
                        .administratienummer(9876543210L)
                        .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel())
                        .nieuwSamengesteldeNaamRecord(eenActie).indicatieAfgeleid(false).indicatieNamenreeks(false)
                        .predicaat("H").voornamen("Voornaam").adellijkeTitel("B").voorvoegsel("voor")
                        .scheidingsteken(",")
                        .geslachtsnaamstam("geslachtsnaam")
                        .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel())
                        .nieuwGeboorteRecord(eenActie).datumGeboorte(20120101).gemeenteGeboorte((short) 10)
                        .woonplaatsnaamGeboorte("geboorteplaats").buitenlandsePlaatsGeboorte("buitenland")
                        .buitenlandseRegioGeboorte("bregio").omschrijvingLocatieGeboorte("omschrijving")
                        .landGebiedGeboorte((short) 31).eindeRecord(RandomTechnischeSleutelService.randomTechSleutel())
                        .nieuwGeslachtsaanduidingRecord(eenActie).geslachtsaanduiding(Geslachtsaanduiding.MAN)
                        .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel())
                        .nieuwInschrijvingRecord(eenActie)
                        .datumInschrijving(20101122).versienummer(1L)
                        .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel()).nieuwBijhoudingRecord(eenActie)
                        .bijhoudingsaard(Bijhoudingsaard.NIET_INGEZETENE).bijhoudingspartij(10)
                        .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel()).nieuwOverlijdenRecord(eenActie)
                        .datumOverlijden(21000101).gemeenteOverlijden((short) 10).woonplaatsnaamOverlijden(WOONPLAATSNAAM)
                        .buitenlandsePlaatsOverlijden("buitenland").buitenlandseRegioOverlijden("bregio")
                        .omschrijvingLocatieOverlijden("omschrijving").landGebiedOverlijden((short) 31)
                        .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel())
                        .nieuwNaamgebruikRecord(eenActie)
                        .naamgebruik(Naamgebruik.EIGEN).indicatieNaamgebruikAfgeleid(false).predicaatNaamgebruik("H")
                        .voornamenNaamgebruik("voornaam").adellijkeTitelNaamgebruik("B").voorvoegselNaamgebruik("voor")
                        .scheidingstekenNaamgebruik(",").geslachtsnaamstamNaamgebruik("geslachtsnaam")
                        .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel())
                        .nieuwNummerverwijzingRecord(eenActie).vorigeBurgerservicenummer(123456789)
                        .volgendeBurgerservicenummer(123456789).vorigeAdministratienummer(123456789L)
                        .volgendeAdministratienummer(123456789L)
                        .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel()).nieuwBijhoudingRecord(eenActie)
                        .bijhoudingspartij(1234).bijhoudingsaard(Bijhoudingsaard.INGEZETENE)
                        .nadereBijhoudingsaard(NadereBijhoudingsaard.ACTUEEL).indicatieOnverwerktDocumentAanwezig(true)
                        .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel()).nieuwMigratieRecord(eenActie)
                        .soortMigratie(SoortMigratie.IMMIGRATIE).landGebiedMigratie((short) 20)
                        .buitenlandsAdresRegel1Migratie(blAdresRegel).buitenlandsAdresRegel2Migratie(blAdresRegel)
                        .buitenlandsAdresRegel3Migratie(blAdresRegel).buitenlandsAdresRegel4Migratie(blAdresRegel)
                        .buitenlandsAdresRegel5Migratie(blAdresRegel).buitenlandsAdresRegel6Migratie("hoeraaaaaaaaa!!!")
                        .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel())
                        .nieuwVerblijfsrechtRecord(eenActie).aanduidingVerblijfsrecht((short) 23)
                        .datumMededelingVerblijfsrecht(20120101).datumVoorzienEindeVerblijfsrecht(20120101)
                        .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel())
                        .nieuwUitsluitingKiesrechtRecord(eenActie).indicatieUitsluitingKiesrecht(Ja.J)
                        .datumVoorzienEindeUitsluitingKiesrecht(20131215)
                        .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel())
                        .nieuwDeelnameEUVerkiezingenRecord(eenActie).indicatieDeelnameEUVerkiezingen(true)
                        .datumAanleidingAanpassingDeelnameEUVerkiezingen(20131215)
                        .datumVoorzienEindeUitsluitingEUVerkiezingen(20131215)
                        .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel())
                        .nieuwPersoonskaartRecord(eenActie)
                        .gemeentePersoonskaart(321).indicatiePersoonskaartVolledigGeconverteerd(false)
                        .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel()).build();

        // Een afgeleid administratief record:
        final HisPersoonAfgeleidAdministratiefModel afgeleidAdministratief
            = new HisPersoonAfgeleidAdministratiefModel(persoon, administratieveHandeling, TIJDSTIP_REGISTRATIE, null,
                                                        new JaNeeAttribuut(true), TIJDSTIP_REGISTRATIE, eenActie);
        afgeleidAdministratief.getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig().setMagGeleverdWorden(true);
        ReflectionTestUtils.setField(afgeleidAdministratief, "iD", RandomTechnischeSleutelService.randomTechSleutel());
        persoon.getPersoonAfgeleidAdministratiefHistorie().voegToe(afgeleidAdministratief);

        ReflectionTestUtils.setField(persoon, "iD", RandomTechnischeSleutelService.randomTechSleutel());

        // Voeg de handeling toe aan de verantwoording.
        persoon.getAdministratieveHandelingen().add(VerantwoordingTestUtil.converteer(administratieveHandeling));

        // Voornamen:
        final PersoonVoornaamHisVolledigImpl voornaam1 =
                new PersoonVoornaamHisVolledigImplBuilder(persoon, new VolgnummerAttribuut(1),
                        defaultMagGeleverdWordenVoorAttributen).nieuwStandaardRecord(eenActie).naam("voornaam1")
                        .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel()).build();

        ReflectionTestUtils.setField(voornaam1, "iD", RandomTechnischeSleutelService.randomTechSleutel());

        final PersoonVoornaamHisVolledigImpl voornaam2 =
                new PersoonVoornaamHisVolledigImplBuilder(persoon, new VolgnummerAttribuut(2),
                        defaultMagGeleverdWordenVoorAttributen).nieuwStandaardRecord(eenActie).naam("voornaam2")
                        .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel()).build();
        ReflectionTestUtils.setField(voornaam2, "iD", RandomTechnischeSleutelService.randomTechSleutel());

        persoon.getVoornamen().add(voornaam1);
        persoon.getVoornamen().add(voornaam2);

        // geslachtsnaamcomponenten
        final PersoonGeslachtsnaamcomponentHisVolledigImpl geslComp =
                new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(persoon, new VolgnummerAttribuut(1),
                        defaultMagGeleverdWordenVoorAttributen).nieuwStandaardRecord(eenActie).stam("comp")
                        .predicaat("H")
                        .adellijkeTitel("H").voorvoegsel("voorv").scheidingsteken(";")
                        .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel()).build();
        ReflectionTestUtils.setField(geslComp, "iD", RandomTechnischeSleutelService.randomTechSleutel());
        persoon.getGeslachtsnaamcomponenten().add(geslComp);

        // adressen
        final PersoonAdresHisVolledigImpl persAdres =
                new PersoonAdresHisVolledigImplBuilder(persoon, defaultMagGeleverdWordenVoorAttributen)
                        .nieuwStandaardRecord(eenActie).soort(FunctieAdres.BRIEFADRES).redenWijziging("A")
                        .aangeverAdreshouding("G").datumAanvangAdreshouding(eenActie.getDatumAanvangGeldigheid())
                        .identificatiecodeAdresseerbaarObject("1234567890123456")
                        .identificatiecodeNummeraanduiding("1234567890123456").gemeente((short) 10)
                        .naamOpenbareRuimte("opNaam").afgekorteNaamOpenbareRuimte("afg").gemeentedeel("gemdeel")
                        .huisnummer(12).huisletter("A").huisnummertoevoeging("b").postcode("1234AB")
                        .woonplaatsnaam(WOONPLAATSNAAM).locatieTenOpzichteVanAdres(LocatieTenOpzichteVanAdres.BY)
                        .locatieomschrijving("omschr").buitenlandsAdresRegel1("buitregel1")
                        .buitenlandsAdresRegel2("buitregel2").buitenlandsAdresRegel3("buitregel3")
                        .buitenlandsAdresRegel4("buitregel4").buitenlandsAdresRegel5("buitregel5")
                        .buitenlandsAdresRegel6("buitregel6").landGebied((short) 31)
                        .indicatiePersoonAangetroffenOpAdres(Nee.N)
                        .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel()).build();
        ReflectionTestUtils.setField(persAdres, "iD", RandomTechnischeSleutelService.randomTechSleutel());
        persoon.getAdressen().add(persAdres);

        // Nationaliteiten
        final PersoonNationaliteitHisVolledigImpl persNationaliteit =
                new PersoonNationaliteitHisVolledigImplBuilder(persoon,
                        StatischeObjecttypeBuilder.NATIONALITEIT_NEDERLANDS.getWaarde(),
                        defaultMagGeleverdWordenVoorAttributen).nieuwStandaardRecord(eenActie)
                        .redenVerkrijging((short) 1)
                        .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel()).build();
        ReflectionTestUtils.setField(persNationaliteit, "iD", RandomTechnischeSleutelService.randomTechSleutel());
        persoon.getNationaliteiten().add(persNationaliteit);

        // Reisdocumenten
        final PersoonReisdocumentHisVolledigImpl reisdoc =
                new PersoonReisdocumentHisVolledigImplBuilder(persoon, StatischeObjecttypeBuilder
                        .bouwSoortNederlandsReisdocument("15").getWaarde(), defaultMagGeleverdWordenVoorAttributen)
                        .nieuwStandaardRecord(eenActie).autoriteitVanAfgifte("23")
                        .datumIngangDocument(eenActie.getDatumAanvangGeldigheid())
                        .datumInhoudingVermissing(eenActie.getDatumAanvangGeldigheid())
                        .datumUitgifte(eenActie.getDatumAanvangGeldigheid())
                        .datumEindeDocument(eenActie.getDatumAanvangGeldigheid())
                        .nummer("12345").aanduidingInhoudingVermissing("4")
                        .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel()).build();
        ReflectionTestUtils.setField(reisdoc, "iD", RandomTechnischeSleutelService.randomTechSleutel());
        persoon.getReisdocumenten().add(reisdoc);

        // indicaties
        final PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl indicatieBehAlsNed =
                new PersoonIndicatieBehandeldAlsNederlanderHisVolledigImplBuilder(persoon,
                        defaultMagGeleverdWordenVoorAttributen).nieuwStandaardRecord(eenActie).waarde(Ja.J)
                        .eindeRecord()
                        .build();
        ReflectionTestUtils.setField(indicatieBehAlsNed, "iD", RandomTechnischeSleutelService.randomTechSleutel());
        ReflectionTestUtils.setField(indicatieBehAlsNed.getPersoonIndicatieHistorie().getActueleRecord(), "iD",
                RandomTechnischeSleutelService.randomTechSleutel());
        persoon.setIndicatieBehandeldAlsNederlander(indicatieBehAlsNed);

        final PersoonIndicatieOnderCurateleHisVolledigImpl indicatieOnderCur =
                new PersoonIndicatieOnderCurateleHisVolledigImplBuilder(persoon, defaultMagGeleverdWordenVoorAttributen)
                        .nieuwStandaardRecord(eenActie).waarde(Ja.J).eindeRecord().build();
        ReflectionTestUtils.setField(indicatieOnderCur, "iD", RandomTechnischeSleutelService.randomTechSleutel());
        ReflectionTestUtils.setField(indicatieOnderCur.getPersoonIndicatieHistorie().getActueleRecord(), "iD",
                RandomTechnischeSleutelService.randomTechSleutel());
        persoon.setIndicatieOnderCuratele(indicatieOnderCur);

        final PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl
                indicatieBelVerstReisDoc =
                new PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImplBuilder(persoon,
                        defaultMagGeleverdWordenVoorAttributen).nieuwStandaardRecord(eenActie).waarde(Ja.J)
                        .eindeRecord()
                        .build();
        ReflectionTestUtils
                .setField(indicatieBelVerstReisDoc, "iD", RandomTechnischeSleutelService.randomTechSleutel());
        ReflectionTestUtils.setField(indicatieBelVerstReisDoc.getPersoonIndicatieHistorie().getActueleRecord(), "iD",
                RandomTechnischeSleutelService.randomTechSleutel());
        persoon.setIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument(indicatieBelVerstReisDoc);

        final PersoonIndicatieDerdeHeeftGezagHisVolledigImpl indicatieDerdeGezag =
                new PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder(persoon,
                        defaultMagGeleverdWordenVoorAttributen)
                        .nieuwStandaardRecord(eenActie).waarde(Ja.J).eindeRecord().build();
        ReflectionTestUtils.setField(indicatieDerdeGezag, "iD", RandomTechnischeSleutelService.randomTechSleutel());
        ReflectionTestUtils.setField(indicatieDerdeGezag.getPersoonIndicatieHistorie().getActueleRecord(), "iD",
                RandomTechnischeSleutelService.randomTechSleutel());
        persoon.setIndicatieDerdeHeeftGezag(indicatieDerdeGezag);

        final PersoonIndicatieStaatloosHisVolledigImpl indicatieStaatloos =
                new PersoonIndicatieStaatloosHisVolledigImplBuilder(persoon, defaultMagGeleverdWordenVoorAttributen)
                        .nieuwStandaardRecord(eenActie).waarde(Ja.J).eindeRecord().build();
        ReflectionTestUtils.setField(indicatieStaatloos, "iD", RandomTechnischeSleutelService.randomTechSleutel());
        ReflectionTestUtils.setField(indicatieStaatloos.getPersoonIndicatieHistorie().getActueleRecord(), "iD",
                RandomTechnischeSleutelService.randomTechSleutel());
        persoon.setIndicatieStaatloos(indicatieStaatloos);

        final PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl indicatieVastGestNietNl =
                new PersoonIndicatieVastgesteldNietNederlanderHisVolledigImplBuilder(persoon,
                        defaultMagGeleverdWordenVoorAttributen).nieuwStandaardRecord(eenActie).waarde(Ja.J)
                        .eindeRecord()
                        .build();
        ReflectionTestUtils.setField(indicatieVastGestNietNl, "iD", RandomTechnischeSleutelService.randomTechSleutel());
        ReflectionTestUtils.setField(indicatieVastGestNietNl.getPersoonIndicatieHistorie().getActueleRecord(), "iD",
                RandomTechnischeSleutelService.randomTechSleutel());
        persoon.setIndicatieVastgesteldNietNederlander(indicatieVastGestNietNl);

        final PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl indicatieVerstBep =
                new PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder(persoon,
                        defaultMagGeleverdWordenVoorAttributen).nieuwStandaardRecord(eenActie).waarde(Ja.J)
                        .eindeRecord()
                        .build();
        ReflectionTestUtils.setField(indicatieVerstBep, "iD", RandomTechnischeSleutelService.randomTechSleutel());
        ReflectionTestUtils.setField(indicatieVerstBep.getPersoonIndicatieHistorie().getActueleRecord(), "iD",
                RandomTechnischeSleutelService.randomTechSleutel());
        persoon.setIndicatieVolledigeVerstrekkingsbeperking(indicatieVerstBep);

        final PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImpl indicatieBijzVerblRechPos =
                new PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImplBuilder(persoon,
                        defaultMagGeleverdWordenVoorAttributen).nieuwStandaardRecord(eenActie).waarde(Ja.J)
                        .eindeRecord()
                        .build();
        ReflectionTestUtils.setField(indicatieBijzVerblRechPos, "iD",
                RandomTechnischeSleutelService.randomTechSleutel());
        ReflectionTestUtils.setField(indicatieBijzVerblRechPos.getPersoonIndicatieHistorie().getActueleRecord(), "iD",
                RandomTechnischeSleutelService.randomTechSleutel());
        persoon.setIndicatieBijzondereVerblijfsrechtelijkePositie(indicatieBijzVerblRechPos);

        // Verstrekkings beperkingen
        final PersoonVerstrekkingsbeperkingHisVolledigImpl verstrekkingsbeperkingHisVolledig =
                new PersoonVerstrekkingsbeperkingHisVolledigImplBuilder(persoon,
                        StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(),
                        new OmschrijvingEnumeratiewaardeAttribuut("omsderde"),
                        StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                        defaultMagGeleverdWordenVoorAttributen).build();
        ReflectionTestUtils.setField(verstrekkingsbeperkingHisVolledig, "iD",
                RandomTechnischeSleutelService.randomTechSleutel());
        final HisPersoonVerstrekkingsbeperkingModel hisPersoonVerstrekkingsbeperkingModel =
                new HisPersoonVerstrekkingsbeperkingModel(verstrekkingsbeperkingHisVolledig, eenActie);
        ReflectionTestUtils.setField(hisPersoonVerstrekkingsbeperkingModel, "iD",
                RandomTechnischeSleutelService.randomTechSleutel());
        verstrekkingsbeperkingHisVolledig.getPersoonVerstrekkingsbeperkingHistorie().voegToe(
                hisPersoonVerstrekkingsbeperkingModel);
        persoon.getVerstrekkingsbeperkingen().add(verstrekkingsbeperkingHisVolledig);

        // Afnemer indicaties
        final Leveringsautorisatie abonnement = TestLeveringsautorisatieBuilder.maker().metNaam("abo").maak();
        final PersoonAfnemerindicatieHisVolledigImpl afnemerindicatie =
                new PersoonAfnemerindicatieHisVolledigImplBuilder(persoon,
                        StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), abonnement,
                        defaultMagGeleverdWordenVoorAttributen).nieuwStandaardRecord(eenDienst)
                        .datumAanvangMaterielePeriode(20120101).datumEindeVolgen(DatumAttribuut.vandaag())
                        .eindeRecord(RandomTechnischeSleutelService.randomLongTechSleutel()).build();
        ReflectionTestUtils.setField(afnemerindicatie, "iD",
                ((long) RandomTechnischeSleutelService.randomTechSleutel()));
        persoon.getAfnemerindicaties().add(afnemerindicatie);

        // Persoon Verificatie
        final PersoonVerificatieHisVolledigImpl persoonVerificatie =
                new PersoonVerificatieHisVolledigImplBuilder(persoon,
                        StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(),
                        new NaamEnumeratiewaardeAttribuut("A"), defaultMagGeleverdWordenVoorAttributen)
                        .nieuwStandaardRecord(eenActie).datum(20120101)
                        .eindeRecord(RandomTechnischeSleutelService.randomLongTechSleutel()).build();
        ReflectionTestUtils.setField(persoonVerificatie, "iD",
            (long) RandomTechnischeSleutelService.randomTechSleutel());
        persoon.getVerificaties().add(persoonVerificatie);

        return persoon;
    }

    public static void voegKindBetrokkenheidToeAanPersoon(final PersoonHisVolledigImpl kind) {
        voegKindBetrokkenheidToeAanPersoon(kind, false);
    }

    public static void voegKindBetrokkenheidToeAanPersoon(final PersoonHisVolledigImpl kind,
            final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        final PersoonHisVolledigImpl moeder = maakAntwoordPersoon(defaultMagGeleverdWordenVoorAttributen);
        final PersoonHisVolledigImpl vader = maakAntwoordPersoon(defaultMagGeleverdWordenVoorAttributen);

        voegKindBetrokkenheidToeAanPersoon(kind, moeder, vader, defaultMagGeleverdWordenVoorAttributen);
    }

    public static void voegKindBetrokkenheidToeAanPersoon(final PersoonHisVolledigImpl kind,
            final PersoonHisVolledigImpl moeder, final PersoonHisVolledigImpl vader)
    {
        voegKindBetrokkenheidToeAanPersoon(kind, moeder, vader, false);
    }

    public static void voegKindBetrokkenheidToeAanPersoonOld(final PersoonHisVolledigImpl kind, final PersoonHisVolledigImpl moeder,
        final PersoonHisVolledigImpl vader, final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        final ActieModel eenActie = new ActieModel(null, null, null, ACTIE_DATUM, null, TIJDSTIP_REGISTRATIE, null);
        eenActie.setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        ReflectionTestUtils.setField(eenActie, "iD", 200L);

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(moeder, vader, kind, REGISTRATIE_DATUM, defaultMagGeleverdWordenVoorAttributen, eenActie);

        final FamilierechtelijkeBetrekkingHisVolledigImpl familierechtelijkeBetrekkingHisVolledig =
            RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind);

        // Ouderlijk gezag en ouderschap groepen:
        for (final BetrokkenheidHisVolledigImpl betrokkenheidHisVolledig : familierechtelijkeBetrekkingHisVolledig.getBetrokkenheden()) {
            if (SoortBetrokkenheid.OUDER == betrokkenheidHisVolledig.getRol().getWaarde()) {
                final OuderHisVolledigImpl ouderBetr = (OuderHisVolledigImpl) betrokkenheidHisVolledig;
                final OuderOuderlijkGezagGroepBericht ouderOuderlijkGezagGroepBericht = new OuderOuderlijkGezagGroepBericht();
                ouderOuderlijkGezagGroepBericht.setIndicatieOuderHeeftGezag(new JaNeeAttribuut(false));
                ouderOuderlijkGezagGroepBericht.getIndicatieOuderHeeftGezag().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
                ouderOuderlijkGezagGroepBericht.setDatumAanvangGeldigheid(eenActie.getDatumAanvangGeldigheid());
                ouderOuderlijkGezagGroepBericht.getDatumAanvangGeldigheid().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
                final HisOuderOuderlijkGezagModel hisOuderOuderlijkGezagModel =
                    new HisOuderOuderlijkGezagModel(ouderBetr, ouderOuderlijkGezagGroepBericht, ouderOuderlijkGezagGroepBericht, eenActie);
                ReflectionTestUtils.setField(hisOuderOuderlijkGezagModel, "iD", RandomTechnischeSleutelService.randomTechSleutel());
                ouderBetr.getOuderOuderlijkGezagHistorie().voegToe(hisOuderOuderlijkGezagModel);
            }
        }
    }



    private static void voegKindBetrokkenheidToeAanPersoon(final PersoonHisVolledigImpl kind, final PersoonHisVolledigImpl moeder,
                                                          final PersoonHisVolledigImpl vader, final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        final ActieModel eenActie = new ActieModel(null, null, null, ACTIE_DATUM, null, TIJDSTIP_REGISTRATIE, null);
        eenActie.setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        ReflectionTestUtils.setField(eenActie, "iD", 200L);

        final FamilierechtelijkeBetrekkingHisVolledigImpl familierechtelijkeBetrekkingHisVolledig =
            new FamilierechtelijkeBetrekkingHisVolledigImplBuilder(defaultMagGeleverdWordenVoorAttributen)
                .nieuwStandaardRecord(eenActie)
                .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel())
                .build();
        ReflectionTestUtils.setField(familierechtelijkeBetrekkingHisVolledig, "iD", RandomTechnischeSleutelService.randomTechSleutel());


        final KindHisVolledigImpl kindBetr = new KindHisVolledigImplBuilder(familierechtelijkeBetrekkingHisVolledig, kind)
            .metVerantwoording(eenActie).build();
        final OuderHisVolledigImpl moederBetr = new OuderHisVolledigImplBuilder(familierechtelijkeBetrekkingHisVolledig, moeder)
            .metVerantwoording(eenActie).build();
        final OuderHisVolledigImpl vaderBetr = new OuderHisVolledigImplBuilder(familierechtelijkeBetrekkingHisVolledig, vader)
            .metVerantwoording(eenActie).build();

        ReflectionTestUtils.setField(kindBetr, "iD", RandomTechnischeSleutelService.randomTechSleutel());
        ReflectionTestUtils.setField(moederBetr, "iD", RandomTechnischeSleutelService.randomTechSleutel());
        ReflectionTestUtils.setField(vaderBetr, "iD", RandomTechnischeSleutelService.randomTechSleutel());

        familierechtelijkeBetrekkingHisVolledig.getBetrokkenheden().add(kindBetr);
        familierechtelijkeBetrekkingHisVolledig.getBetrokkenheden().add(moederBetr);
        familierechtelijkeBetrekkingHisVolledig.getBetrokkenheden().add(vaderBetr);

        kind.getBetrokkenheden().add(kindBetr);
        moeder.getBetrokkenheden().add(moederBetr);
        vader.getBetrokkenheden().add(vaderBetr);
    }

    public static void voegOuderBetrokkenheidToeAanPersoon(final PersoonHisVolledigImpl ouder) {
        voegOuderBetrokkenheidToeAanPersoon(ouder, false);
    }

    public static void voegOuderBetrokkenheidToeAanPersoon(final PersoonHisVolledigImpl ouder, final boolean defaultMagGeleverdWordenVoorAttributen) {
        final PersoonHisVolledigImpl moeder = maakAntwoordPersoon(defaultMagGeleverdWordenVoorAttributen);
        final PersoonHisVolledigImpl kind = maakAntwoordPersoon(defaultMagGeleverdWordenVoorAttributen);

        voegOuderBetrokkenheidToeAanPersoon(ouder, moeder, kind, defaultMagGeleverdWordenVoorAttributen);
    }

    public static void voegOuderBetrokkenheidToeAanPersoon(final PersoonHisVolledigImpl ouder, final PersoonHisVolledigImpl moeder,
                                                           final PersoonHisVolledigImpl kind)
    {
        voegOuderBetrokkenheidToeAanPersoon(ouder, moeder, kind, false);
    }

    public static void voegOuderBetrokkenheidToeAanPersoon(final PersoonHisVolledigImpl ouder,
            final PersoonHisVolledigImpl moeder, final PersoonHisVolledigImpl kind,
            final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        final ActieModel eenActie = new ActieModel(null, null, null, ACTIE_DATUM, null, TIJDSTIP_REGISTRATIE, null);
        eenActie.setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        ReflectionTestUtils.setField(eenActie, "iD", 300L);

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder, moeder, kind, REGISTRATIE_DATUM, defaultMagGeleverdWordenVoorAttributen, eenActie);

        final FamilierechtelijkeBetrekkingHisVolledigImpl familierechtelijkeBetrekkingHisVolledig =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind);


    }

    public static void voegPartnerBetrokkenheidToeAanPersoon(final PersoonHisVolledigImpl partner) {
        voegPartnerBetrokkenheidToeAanPersoon(partner, false);
    }

    public static void voegPartnerBetrokkenheidToeAanPersoon(final PersoonHisVolledigImpl partner,
            final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        final PersoonHisVolledigImpl partner2 = maakAntwoordPersoon(defaultMagGeleverdWordenVoorAttributen);

        voegPartnerBetrokkenheidToeAanPersoon(partner, partner2, defaultMagGeleverdWordenVoorAttributen);
    }

    private static void voegPartnerBetrokkenheidToeAanPersoon(final PersoonHisVolledigImpl partner,
            final PersoonHisVolledigImpl partner2, final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        final ActieModel eenActie = new ActieModel(null, null, null, ACTIE_DATUM, null, TIJDSTIP_REGISTRATIE, null);
        eenActie.setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        ReflectionTestUtils.setField(eenActie, "iD", 400L);

        final HuwelijkHisVolledigImpl huwelijk =
                new HuwelijkHisVolledigImplBuilder(defaultMagGeleverdWordenVoorAttributen)
                        .nieuwStandaardRecord(eenActie)
                        .datumAanvang(20120101).landGebiedAanvang((short) 15).landGebiedEinde((short) 15)
                        .buitenlandsePlaatsAanvang("trouwplaats").buitenlandsePlaatsEinde("scheidingplaats")
                        .buitenlandseRegioAanvang("woestijn").buitenlandseRegioEinde("amazone").datumEinde(20120102)
                        .gemeenteAanvang((short) 25).gemeenteEinde((short) 25)
                        .omschrijvingLocatieAanvang("roos kleurig")
                        .omschrijvingLocatieEinde("grijs").redenEinde("K").woonplaatsnaamAanvang(WOONPLAATSNAAM)
                        .woonplaatsnaamEinde(WOONPLAATSNAAM)
                        .eindeRecord(RandomTechnischeSleutelService.randomTechSleutel())
                        .build();
        ReflectionTestUtils.setField(huwelijk, "iD", RandomTechnischeSleutelService.randomTechSleutel());

        final PartnerHisVolledigImpl partnerBetr = new PartnerHisVolledigImplBuilder(huwelijk, partner).metVerantwoording(eenActie).build();
        final PartnerHisVolledigImpl partnerBetr2 = new PartnerHisVolledigImplBuilder(huwelijk, partner2).metVerantwoording(eenActie).build();
        ReflectionTestUtils.setField(partnerBetr, "iD", RandomTechnischeSleutelService.randomTechSleutel());
        ReflectionTestUtils.setField(partnerBetr2, "iD", RandomTechnischeSleutelService.randomTechSleutel());

        huwelijk.getBetrokkenheden().add(partnerBetr);
        huwelijk.getBetrokkenheden().add(partnerBetr2);

        partner.getBetrokkenheden().add(partnerBetr);
        partner2.getBetrokkenheden().add(partnerBetr2);
    }
}
