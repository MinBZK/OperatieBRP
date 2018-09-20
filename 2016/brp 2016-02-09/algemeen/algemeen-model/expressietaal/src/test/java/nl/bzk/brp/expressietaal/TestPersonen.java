/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonOnderzoek;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusOnderzoek;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestElementBuilder;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.ErkenningOngeborenVruchtHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.GegevenInOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.GeregistreerdPartnerschapHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.NaamskeuzeOngeborenVruchtHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.ErkennerHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.ErkenningOngeborenVruchtHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.GegevenInOnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.GeregistreerdPartnerschapHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.InstemmerHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.KindHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.NaamgeverHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.NaamskeuzeOngeborenVruchtHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PartnerHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieOnderCurateleHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonOnderzoekHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVoornaamHisVolledigImplBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.springframework.test.util.ReflectionTestUtils;

public final class TestPersonen {

    public static final int OPA_BSN           = 111111111;
    public static final int ZOON_BSN          = 333333333;
    public static final int OMA_BSN           = 222222222;
    public static final int DOCHTER_BSN       = 444444444;
    public static final int SCHOONDOCHTER_BSN = 55555555;
    public static final int KLEINZOON_BSN     = 66666666;
    public static final int SUPEROPA_BSN      = 100000000;
    public static final int SUPEROMA_BSN      = 100000001;

    public static final long STANDAARD_ANUMMER = 1234567890L;
    public static final long ZOON_ANUMMER      = 3333333333L;
    public static final long DOCHTER_ANUMMER   = 4444444444L;

    private static final int                SUKKEL_ID                   = 1;
    private static final int                OPA_ID                      = 4;
    private static final int                ZOON_ID                     = 5;
    private static final int                OMA_ID                      = 6;
    private static final int                DOCHTER_ID                  = 7;
    private static final int                SCHOONDOCHTER_ID            = 8;
    private static final int                KLEINZOON_ID                = 9;
    private static final int                SUPEROPA_ID                 = 10;
    private static final int                SUPEROMA_ID                 = 11;
    public static final  DatumTijdAttribuut DATUM_TIJD_KIND_REGISTRATIE = DatumTijdAttribuut.bouwDatumTijd(2013, 3, 5, 23, 1, 59);

    private static int betrokkenhedenIdIndex;

    private static PersoonHisVolledigImpl zoon;

    private TestPersonen() {
    }

    public static List<PersoonHisVolledig> geefAlleTestPersonen() {
        return Arrays.asList(maakSukkel(), TestPersoonJohnnyJordaan.maak(), maakTestPersoon());
    }

    public static PersoonHisVolledig maakSukkel() {
        final PersoonHisVolledigImplBuilder bld = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledig sukkel = bld.build();
        ReflectionTestUtils.setField(sukkel, "iD", SUKKEL_ID);
        return sukkel;
    }

    private static PersoonHisVolledigImplBuilder maakPersoonBuilder(final int bsn, final long anummer, final String voornaam,
        final String achternaam,
        final Geslachtsaanduiding geslacht,
        final int geboorteDatum,
        final GemeenteAttribuut geboorteGemeente,
        final NaamEnumeratiewaardeAttribuut geboortePlaats)
    {
        final PersoonHisVolledigImplBuilder bld = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwGeslachtsaanduidingRecord(geboorteDatum, null, geboorteDatum)
            .geslachtsaanduiding(geslacht)
            .eindeRecord()
            .nieuwGeboorteRecord(geboorteDatum)
            .datumGeboorte(geboorteDatum)
            .gemeenteGeboorte(geboorteGemeente.getWaarde())
            .woonplaatsnaamGeboorte(geboortePlaats.getWaarde())
            .landGebiedGeboorte(StatischeObjecttypeBuilder.LAND_NEDERLAND.getWaarde())
            .eindeRecord();

        if (bsn > 0) {
            bld.nieuwIdentificatienummersRecord(geboorteDatum, null, geboorteDatum)
                .burgerservicenummer(bsn)
                .administratienummer(anummer)
                .eindeRecord();
        }

        if (voornaam != null) {
            // voornaam
            bld.voegPersoonVoornaamToe(
                new PersoonVoornaamHisVolledigImplBuilder(new VolgnummerAttribuut(1))
                    .nieuwStandaardRecord(geboorteDatum, null, geboorteDatum)
                    .naam(voornaam)
                    .eindeRecord()
                    .build());
        }

        // geslachtsnaam
        bld.voegPersoonGeslachtsnaamcomponentToe(
            new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(new VolgnummerAttribuut(1))
                .nieuwStandaardRecord(geboorteDatum, null, geboorteDatum)
                .stam(achternaam)
                .eindeRecord()
                .build())
            .nieuwSamengesteldeNaamRecord(geboorteDatum, null, geboorteDatum)
            .geslachtsnaamstam(achternaam)
            .voornamen(voornaam)
            .eindeRecord();
        // standaard nationaliteit
        bld.voegPersoonNationaliteitToe(
            new PersoonNationaliteitHisVolledigImplBuilder(
                StatischeObjecttypeBuilder.NATIONALITEIT_NEDERLANDS.getWaarde())
                .nieuwStandaardRecord(geboorteDatum, null, geboorteDatum)
                .eindeRecord()
                .build());

        return bld;
    }

    private static PersoonHisVolledigImplBuilder maakOverlijden(final PersoonHisVolledigImplBuilder bld,
                                                                final int datumOverlijden,
                                                                final GemeenteAttribuut gemeenteOverlijden,
                                                                final NaamEnumeratiewaardeAttribuut plaatsOverlijden)
    {
        bld.nieuwOverlijdenRecord(datumOverlijden)
            .gemeenteOverlijden(gemeenteOverlijden.getWaarde())
            .woonplaatsnaamOverlijden(plaatsOverlijden.getWaarde())
            .landGebiedOverlijden(StatischeObjecttypeBuilder.LAND_NEDERLAND.getWaarde())
            .datumOverlijden(datumOverlijden);
        return bld;
    }

    public static PersoonHisVolledig maakTestPersoon() {
        final NaamEnumeratiewaardeAttribuut ovplaats = StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM;
        final GemeenteAttribuut ovgemeente = StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM;
        // superopa
        final PersoonHisVolledigImpl superOpa =
            maakPersoonBuilder(
                SUPEROPA_BSN, STANDAARD_ANUMMER, "Wilbert", "McSnor", Geslachtsaanduiding.MAN, 19100310,
                StatischeObjecttypeBuilder.GEMEENTE_UTRECHT, StatischeObjecttypeBuilder.WOONPLAATS_UTRECHT)
                .nieuwOverlijdenRecord(19830310)
                .gemeenteOverlijden(StatischeObjecttypeBuilder.GEMEENTE_UTRECHT.getWaarde())
                .woonplaatsnaamOverlijden(StatischeObjecttypeBuilder.WOONPLAATS_UTRECHT.getWaarde())
                .landGebiedOverlijden(StatischeObjecttypeBuilder.LAND_NEDERLAND.getWaarde())
                .datumOverlijden(19830310)
                .eindeRecord()
                .build();
        ReflectionTestUtils.setField(superOpa, "iD", SUPEROPA_ID);
        // superoma
        final PersoonHisVolledigImpl superOma =
            maakPersoonBuilder(
                SUPEROMA_BSN, STANDAARD_ANUMMER, "Opoe", "Bakblik", Geslachtsaanduiding.VROUW, 19101020,
                StatischeObjecttypeBuilder.GEMEENTE_DEN_HAAG, StatischeObjecttypeBuilder.WOONPLAATS_DEN_HAAG)
                .voegPersoonNationaliteitToe(
                    new PersoonNationaliteitHisVolledigImplBuilder(
                        StatischeObjecttypeBuilder.NATIONALITEIT_TURKS.getWaarde())
                        .nieuwStandaardRecord(
                            19101020, null,
                            19101020).eindeRecord().build())
                .nieuwOverlijdenRecord(19750920)
                .gemeenteOverlijden(StatischeObjecttypeBuilder.GEMEENTE_UTRECHT.getWaarde())
                .woonplaatsnaamOverlijden(StatischeObjecttypeBuilder.WOONPLAATS_UTRECHT.getWaarde())
                .landGebiedOverlijden(StatischeObjecttypeBuilder.LAND_NEDERLAND.getWaarde())
                .datumOverlijden(19750920)
                .eindeRecord()
                .build();

        final ActieModel actieModel = new ActieModel(null, null, null, null, null, DatumTijdAttribuut.nu(), null);

        // opa
        final PersoonHisVolledigImplBuilder opaBuilder =
            maakPersoonBuilder(
                -1, STANDAARD_ANUMMER, null, "McSnor", Geslachtsaanduiding.MAN, 19431121,
                StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM, StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM);
        final PersoonHisVolledigImpl opa = maakOverlijden(opaBuilder, 19950326, ovgemeente, ovplaats)
            .nieuwAfgeleidAdministratiefRecord(actieModel).eindeRecord()
            .voegPersoonVoornaamToe(
                new PersoonVoornaamHisVolledigImplBuilder(new VolgnummerAttribuut(1))
                    .nieuwStandaardRecord(19431121, 19550101, 19431121)
                    .naam("Jacco")
                    .eindeRecord()
                    .nieuwStandaardRecord(19550101, null, 19550101)
                    .naam("Jack")
                    .eindeRecord()
                    .build())
            .voegPersoonVoornaamToe(
                new PersoonVoornaamHisVolledigImplBuilder(new VolgnummerAttribuut(2))
                    .nieuwStandaardRecord(19431121, null, 19431121)
                    .naam("Johnson")
                    .eindeRecord()
                    .build())
            .nieuwSamengesteldeNaamRecord(19431121, 19550101, 19431121)
            .geslachtsnaamstam("McSnor")
            .voornamen("Jacco Johnson")
            .eindeRecord()
            .nieuwSamengesteldeNaamRecord(19550101, null, 19550101)
            .geslachtsnaamstam("McSnor")
            .voornamen("Jack Johnson")
            .eindeRecord()
            .voegPersoonNationaliteitToe(
                new PersoonNationaliteitHisVolledigImplBuilder(
                    StatischeObjecttypeBuilder.NATIONALITEIT_TURKS.getWaarde()).nieuwStandaardRecord(
                    19431121, null, 19431121)
                    .eindeRecord()
                    .build())
            .voegPersoonNationaliteitToe(
                new PersoonNationaliteitHisVolledigImplBuilder(
                    StatischeObjecttypeBuilder.NATIONALITEIT_SLOWAAKS.getWaarde()).nieuwStandaardRecord(
                    19431121, 19700310,
                    19700310)
                    .eindeRecord()
                    .build())
            .nieuwOverlijdenRecord(20020319)
            .gemeenteOverlijden(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde())
            .woonplaatsnaamOverlijden(StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM.getWaarde())
            .landGebiedOverlijden(StatischeObjecttypeBuilder.LAND_NEDERLAND.getWaarde())
            .datumOverlijden(20020319)
            .eindeRecord()
            .nieuwIdentificatienummersRecord(19500101, 19600101, 19500101)
            .administratienummer(19501950L)
            .burgerservicenummer(OPA_BSN)
            .eindeRecord()
            .nieuwIdentificatienummersRecord(19600101, 19700101, 19600101)
            .administratienummer(19601960L)
            .burgerservicenummer(OPA_BSN)
            .eindeRecord()
            .nieuwIdentificatienummersRecord(19700101, null, 19700101)
            .administratienummer(19701970L)
            .burgerservicenummer(OPA_BSN)
            .eindeRecord()
            .voegPersoonAdresToe(
                new PersoonAdresHisVolledigImplBuilder()
                    .nieuwStandaardRecord(19450210, 19500210, 19450210)
                    .woonplaatsnaam(StatischeObjecttypeBuilder.WOONPLAATS_UTRECHT.getWaarde())
                    .postcode("7654PP")
                    .soort(FunctieAdres.WOONADRES)
                    .huisnummer(1)
                    .naamOpenbareRuimte("Steenweg")
                    .eindeRecord()
                    .build())
            .voegPersoonAdresToe(
                new PersoonAdresHisVolledigImplBuilder()
                    .nieuwStandaardRecord(19500210, null, 19500210)
                    .woonplaatsnaam(StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM.getWaarde())
                    .postcode("1234AB")
                    .soort(FunctieAdres.WOONADRES)
                    .huisnummer(1)
                    .naamOpenbareRuimte("Takkenstraat")
                    .eindeRecord()
                    .build())
            .voegPersoonIndicatieOnderCurateleToe(
                new PersoonIndicatieOnderCurateleHisVolledigImplBuilder()
                    .nieuwStandaardRecord(19700101, null, 19700101)
                    .waarde(Ja.J)
                    .eindeRecord()
                    .build())
                //                .voegPersoonIndicatieToe(
                //                        new PersoonIndicatieHisVolledigImplBuilder(SoortIndicatie
                // .INDICATIE_VERSTREKKINGSBEPERKING)
                //                                .nieuwStandaardRecord(19500210, null, 19500210)
                //                                .waarde(Ja.J)
                //                                .eindeRecord()
                //                                .build())
                //                .voegPersoonIndicatieToe(
                //        new PersoonIndicatieHisVolledigImplBuilder(
                //                                SoortIndicatie
                // .INDICATIE_BELEMMERING_VERSTREKKING_REISDOCUMENT)
                //                                .nieuwStandaardRecord(19601010, null, 19601010)
                //                                .waarde(Ja.J)
                //                                .eindeRecord()
                //                                .build())
            .nieuwUitsluitingKiesrechtRecord(19800201)
            .indicatieUitsluitingKiesrecht(Ja.J)
            .eindeRecord()
            .build();
        ReflectionTestUtils.setField(superOma, "iD", SUPEROMA_ID);
        // oma
        final PersoonHisVolledigImpl oma = maakPersoonBuilder(
            OMA_BSN, STANDAARD_ANUMMER, "Ria", "Hola", Geslachtsaanduiding.VROUW, 19431224,
            StatischeObjecttypeBuilder.GEMEENTE_BREDA, StatischeObjecttypeBuilder.WOONPLAATS_BREDA).build();
        ReflectionTestUtils.setField(oma, "iD", OMA_ID);

        // dochter
        final PersoonHisVolledigImpl dochter =
            maakPersoonBuilder(
                DOCHTER_BSN, DOCHTER_ANUMMER, "Tut", "Hola", Geslachtsaanduiding.VROUW, 19720326,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA, StatischeObjecttypeBuilder.WOONPLAATS_BREDA).build();
        ReflectionTestUtils.setField(dochter, "iD", DOCHTER_ID);
        // zoon
        zoon = maakPersoonBuilder(
            ZOON_BSN, ZOON_ANUMMER, "Piet", "Hola", Geslachtsaanduiding.MAN, 19700102,
            StatischeObjecttypeBuilder.GEMEENTE_BREDA, StatischeObjecttypeBuilder.WOONPLAATS_BREDA).build();
        ReflectionTestUtils.setField(zoon, "iD", ZOON_ID);
        // schoondochter
        final PersoonHisVolledigImpl schoondochter =
            maakPersoonBuilder(
                SCHOONDOCHTER_BSN, STANDAARD_ANUMMER, "Mien", "Bakgraag", Geslachtsaanduiding.VROUW, 19740520,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA, StatischeObjecttypeBuilder.WOONPLAATS_BREDA).build();
        ReflectionTestUtils.setField(schoondochter, "iD", SCHOONDOCHTER_ID);
        // kleinzoon
        final PersoonHisVolledigImpl kleinzoon =
            maakPersoonBuilder(
                KLEINZOON_BSN, STANDAARD_ANUMMER, "Kleine", "Hola", Geslachtsaanduiding.MAN, 20011209,
                StatischeObjecttypeBuilder.GEMEENTE_DEN_HAAG,
                StatischeObjecttypeBuilder.WOONPLAATS_DEN_HAAG).build();
        ReflectionTestUtils.setField(kleinzoon, "iD", KLEINZOON_ID);
        // relaties
        voegHuwelijkToe(superOpa, superOma, 19301005);
        voegKindToe(superOpa, superOma, opa, 19431121);
        voegHuwelijkToe(opa, oma, 19650101);
        voegKindToe(opa, oma, dochter, 19720326);
        voegKindToe(opa, oma, zoon, 19700102);
        voegGeregistreerdPartnerschapToe(zoon, schoondochter, 19920220);
        voegKindToe(zoon, schoondochter, kleinzoon, 20011209);
        ReflectionTestUtils.setField(opa, "iD", OPA_ID);
        voegErkennerToe(opa, schoondochter);
        voegInstemmerToe(opa, kleinzoon);
        voegNaamgeverToe(opa, superOma);
        voegNaamskeuzepartnerToe(opa, superOma);
        voegGeregistreerdPartnerschapPartnerToe(opa, superOma);
        voegOnderzoekToe(opa);

        return opa;
    }

    public static PersoonHisVolledigImpl bouwZoon() {
        return zoon;
    }

    private static void voegErkennerToe(PersoonHisVolledigImpl ik,
                                        PersoonHisVolledigImpl ander){

        final ActieModel actieModel = new ActieModel(null, null, null, null, null, DatumTijdAttribuut.nu(), null);
        final ErkenningOngeborenVruchtHisVolledigImpl relatie = new ErkenningOngeborenVruchtHisVolledigImplBuilder().nieuwStandaardRecord(19541231).eindeRecord().build();

        //instemmer
        final BetrokkenheidHisVolledigImpl mijnBetrokkenheid =
                new InstemmerHisVolledigImplBuilder(relatie, ik).metVerantwoording(actieModel).build();
        ReflectionTestUtils.setField(mijnBetrokkenheid, "iD", getUniekBetrokkenheidId());
        //erkenner
        final BetrokkenheidHisVolledigImpl anderBetrokkenheid =
                new ErkennerHisVolledigImplBuilder(relatie, ander).metVerantwoording(actieModel).build();
        ReflectionTestUtils.setField(anderBetrokkenheid, "iD", getUniekBetrokkenheidId());

        relatie.getBetrokkenheden().add(mijnBetrokkenheid);
        relatie.getBetrokkenheden().add(anderBetrokkenheid);
    }

    private static void voegInstemmerToe(PersoonHisVolledigImpl ik,
                                         PersoonHisVolledigImpl ander) {

        final ActieModel actieModel = new ActieModel(null, null, null, null, null, DatumTijdAttribuut.nu(), null);
        final ErkenningOngeborenVruchtHisVolledigImpl relatie = new ErkenningOngeborenVruchtHisVolledigImplBuilder().nieuwStandaardRecord(19541231).eindeRecord().build();

        final BetrokkenheidHisVolledigImpl mijnBetrokkenheid =
                new ErkennerHisVolledigImplBuilder(relatie, ik).metVerantwoording(actieModel).build();
        ReflectionTestUtils.setField(mijnBetrokkenheid, "iD", getUniekBetrokkenheidId());

        final BetrokkenheidHisVolledigImpl anderBetrokkenheid =
                new InstemmerHisVolledigImplBuilder(relatie, ander).metVerantwoording(actieModel).build();
        ReflectionTestUtils.setField(anderBetrokkenheid, "iD", getUniekBetrokkenheidId());

        relatie.getBetrokkenheden().add(anderBetrokkenheid);
        relatie.getBetrokkenheden().add(mijnBetrokkenheid);
    }

    private static void voegNaamgeverToe(PersoonHisVolledigImpl ik, PersoonHisVolledigImpl ander) {

        final NaamskeuzeOngeborenVruchtHisVolledigImpl relatie = new NaamskeuzeOngeborenVruchtHisVolledigImplBuilder().nieuwStandaardRecord(19541231).eindeRecord().build();
        final ActieModel actieModel = new ActieModel(null, null, null, null, null, DatumTijdAttribuut.nu(), null);
        final BetrokkenheidHisVolledigImpl mijnBetrokkenheid =
            new PartnerHisVolledigImplBuilder(relatie, ik).metVerantwoording(actieModel).build();
        ReflectionTestUtils.setField(mijnBetrokkenheid, "iD", getUniekBetrokkenheidId());

        final BetrokkenheidHisVolledigImpl anderBetrokkenheid =
            new NaamgeverHisVolledigImplBuilder(relatie, ander).metVerantwoording(actieModel).build();
        ReflectionTestUtils.setField(anderBetrokkenheid, "iD", getUniekBetrokkenheidId());

        relatie.getBetrokkenheden().add(anderBetrokkenheid);
        relatie.getBetrokkenheden().add(mijnBetrokkenheid);
    }

    private static void voegNaamskeuzepartnerToe(PersoonHisVolledigImpl ik, PersoonHisVolledigImpl ander) {

        final NaamskeuzeOngeborenVruchtHisVolledigImpl relatie = new NaamskeuzeOngeborenVruchtHisVolledigImplBuilder().nieuwStandaardRecord(19541231).eindeRecord().build();
        final ActieModel actieModel = new ActieModel(null, null, null, null, null, DatumTijdAttribuut.nu(), null);
        final BetrokkenheidHisVolledigImpl mijnBetrokkenheid =
            new NaamgeverHisVolledigImplBuilder(relatie, ik).metVerantwoording(actieModel).build();
        ReflectionTestUtils.setField(mijnBetrokkenheid, "iD", getUniekBetrokkenheidId());

        final BetrokkenheidHisVolledigImpl anderBetrokkenheid =
                new PartnerHisVolledigImplBuilder(relatie, ander).metVerantwoording(actieModel).build();
        ReflectionTestUtils.setField(anderBetrokkenheid, "iD", getUniekBetrokkenheidId());

        relatie.getBetrokkenheden().add(anderBetrokkenheid);
        relatie.getBetrokkenheden().add(mijnBetrokkenheid);
    }

    private static void voegGeregistreerdPartnerschapPartnerToe(PersoonHisVolledigImpl ik, PersoonHisVolledigImpl ander) {
        final GeregistreerdPartnerschapHisVolledigImpl relatie = new GeregistreerdPartnerschapHisVolledigImplBuilder().nieuwStandaardRecord(19541231).eindeRecord().build();
        final ActieModel actieModel = new ActieModel(null, null, null, null, null, DatumTijdAttribuut.nu(), null);
        final BetrokkenheidHisVolledigImpl mijnBetrokkenheid =
                new PartnerHisVolledigImplBuilder(relatie, ik).metVerantwoording(actieModel).build();
        ReflectionTestUtils.setField(mijnBetrokkenheid, "iD", getUniekBetrokkenheidId());

        //instemmer
        final BetrokkenheidHisVolledigImpl andereBetrokkenheid =
                new PartnerHisVolledigImplBuilder(relatie, ander).metVerantwoording(actieModel).build();
        ReflectionTestUtils.setField(andereBetrokkenheid, "iD", getUniekBetrokkenheidId());

        relatie.getBetrokkenheden().add(andereBetrokkenheid);
        relatie.getBetrokkenheden().add(mijnBetrokkenheid);

    }

    private static void voegKindToe(final PersoonHisVolledigImpl vader, final PersoonHisVolledigImpl moeder,
                                    final PersoonHisVolledigImpl kind, final int geboortedatum)
    {
        final ActieModel actieModel = new ActieModel(null, null, null, null, null, DATUM_TIJD_KIND_REGISTRATIE, null);
        final FamilierechtelijkeBetrekkingHisVolledigImpl fb =
            new FamilierechtelijkeBetrekkingHisVolledigImplBuilder()
                .build();
        final BetrokkenheidHisVolledigImpl ouder1B =
            new OuderHisVolledigImplBuilder(fb, vader).nieuwOuderlijkGezagRecord(geboortedatum, null, geboortedatum)
                .indicatieOuderHeeftGezag(true)
                .eindeRecord()
                .metVerantwoording(actieModel)
                .build();
        ReflectionTestUtils.setField(ouder1B, "iD", getUniekBetrokkenheidId());
        final BetrokkenheidHisVolledigImpl ouder2B =
            new OuderHisVolledigImplBuilder(fb, moeder).nieuwOuderlijkGezagRecord(geboortedatum, null,
                geboortedatum)
                .indicatieOuderHeeftGezag(true)
                .eindeRecord()
                .metVerantwoording(actieModel)
                .build();
        ReflectionTestUtils.setField(ouder2B, "iD", getUniekBetrokkenheidId());

        final BetrokkenheidHisVolledigImpl kindB = new KindHisVolledigImplBuilder(fb, kind).metVerantwoording(actieModel).build();
        ReflectionTestUtils.setField(kindB, "iD", getUniekBetrokkenheidId());
        fb.getBetrokkenheden().add(ouder1B);
        fb.getBetrokkenheden().add(ouder2B);
        fb.getBetrokkenheden().add(kindB);
    }

    private static void voegHuwelijkToe(final PersoonHisVolledigImpl persoon1, final PersoonHisVolledigImpl persoon2,
                                        final int huwelijksdatum)
    {
        final ActieModel actieModel = new ActieModel(null, null, null, null, null, DatumTijdAttribuut.nu(), null);
        final HuwelijkHisVolledigImpl huwelijk = new HuwelijkHisVolledigImplBuilder()
            .nieuwStandaardRecord(huwelijksdatum)
            .datumAanvang(huwelijksdatum)
            .gemeenteAanvang(StatischeObjecttypeBuilder.GEMEENTE_UTRECHT.getWaarde())
            .eindeRecord()
            .build();
        final BetrokkenheidHisVolledigImpl huwelijk1partner1 = new PartnerHisVolledigImplBuilder(huwelijk, persoon1).metVerantwoording(actieModel).build();
        ReflectionTestUtils.setField(huwelijk1partner1, "iD", getUniekBetrokkenheidId());
        final BetrokkenheidHisVolledigImpl huwelijk1partner2 = new PartnerHisVolledigImplBuilder(huwelijk, persoon2).metVerantwoording(actieModel).build();
        ReflectionTestUtils.setField(huwelijk1partner2, "iD", getUniekBetrokkenheidId());
        huwelijk.getBetrokkenheden().add(huwelijk1partner1);
        huwelijk.getBetrokkenheden().add(huwelijk1partner2);
    }

    private static void voegGeregistreerdPartnerschapToe(final PersoonHisVolledigImpl persoon1,
                                                         final PersoonHisVolledigImpl persoon2,
                                                         final int huwelijksdatum)
    {
        final ActieModel actieModel = new ActieModel(null, null, null, null, null, DatumTijdAttribuut.nu(), null);
        final GeregistreerdPartnerschapHisVolledigImpl partnerschap = new GeregistreerdPartnerschapHisVolledigImplBuilder()
            .nieuwStandaardRecord(huwelijksdatum)
            .datumAanvang(huwelijksdatum)
            .gemeenteAanvang(StatischeObjecttypeBuilder.GEMEENTE_UTRECHT.getWaarde())
            .eindeRecord()
            .build();
        final BetrokkenheidHisVolledigImpl huwelijk1partner1 = new PartnerHisVolledigImplBuilder(partnerschap, persoon1).metVerantwoording(actieModel)
            .build();
        ReflectionTestUtils.setField(huwelijk1partner1, "iD", getUniekBetrokkenheidId());
        final BetrokkenheidHisVolledigImpl huwelijk1partner2 = new PartnerHisVolledigImplBuilder(partnerschap, persoon2).metVerantwoording(actieModel).build();
        ReflectionTestUtils.setField(huwelijk1partner2, "iD", getUniekBetrokkenheidId());
        partnerschap.getBetrokkenheden().add(huwelijk1partner1);
        partnerschap.getBetrokkenheden().add(huwelijk1partner2);
    }

    private static void voegOnderzoekToe(final PersoonHisVolledigImpl persoon) {
        final Element element = TestElementBuilder.maker().metId(1).metNaam(ElementEnum.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER)
            .metElementNaam(
                "Persoon.Identificatie.Bsn").maak();
        final GegevenInOnderzoekHisVolledigImpl gegevenInOnderzoek = new GegevenInOnderzoekHisVolledigImplBuilder(element,
            new SleutelwaardeAttribuut(2L),
            new SleutelwaardeAttribuut(3L), true).build();

        final OnderzoekHisVolledigImpl onderzoek = new OnderzoekHisVolledigImplBuilder(true).nieuwStandaardRecord(20150909).datumAanvang(20150909)
            .omschrijving("is niet pluis").status(StatusOnderzoek.IN_UITVOERING).verwachteAfhandeldatum(20160101).eindeRecord()
            .voegGegevenInOnderzoekToe(gegevenInOnderzoek).build();
        final HisOnderzoekModel actueleRecord = onderzoek.getOnderzoekHistorie().getActueleRecord();
        ReflectionTestUtils.setField(actueleRecord, "iD", 4);
        ReflectionTestUtils.setField(gegevenInOnderzoek, "iD", 1);
        ReflectionTestUtils.setField(onderzoek, "iD", 2);

        final Set<PersoonOnderzoekHisVolledigImpl> persoonOnderzoeken = persoon.getOnderzoeken();
        final PersoonOnderzoekHisVolledigImpl persoonOnderzoek = new PersoonOnderzoekHisVolledigImplBuilder(persoon, onderzoek, true)
            .nieuwStandaardRecord(20150909)
            .rol(SoortPersoonOnderzoek.DIRECT).eindeRecord().build();
        ReflectionTestUtils.setField(persoonOnderzoek, "iD", 3);
        persoonOnderzoeken.add(persoonOnderzoek);
    }

    private static int getUniekBetrokkenheidId() {
        return betrokkenhedenIdIndex++;
    }

}
