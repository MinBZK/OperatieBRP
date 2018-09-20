/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpEuropeseVerkiezingenInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpUitsluitingNederlandsKiesrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingEuropeesKiesrechtEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingNaamgebruikCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingUitgeslotenKiesrechtEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieOnjuistEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.testutils.StapelUtils;
import nl.moderniseringgba.migratie.testutils.VerplichteStapel;

import org.junit.Test;

public class ConversieLo3NaarBrpNaarLo3Test extends AbstractConversieServiceTest {

    private static final Comparator<BrpGroep<?>> BRP_GROEPEN_COMPARATOR = new BrpGroepenComparator();
    private static final Lo3IndicatieOnjuist ONJUIST = Lo3IndicatieOnjuistEnum.ONJUIST.asElement();

    // CHECKSTYLE:OFF - Parameters
    private static Lo3Categorie<Lo3PersoonInhoud> buildPersoon(
    // CHECKSTYLE:ON
            final Long anummer,
            final String voornamen,
            final String geslachtsnaam,
            final Integer geboortedatum,
            final String gemeenteCodeGeboorte,
            final Lo3IndicatieOnjuist indicatieOnjuist,
            final Integer ingangsdatumGeldigheid,
            final Integer datumVanOpneming,
            final Integer documentId,
            final String gemeenteCodeAkte,
            final String nummerAkte) {

        final Lo3PersoonInhoud inhoud =
                new Lo3PersoonInhoud(anummer, null, voornamen, null, null, geslachtsnaam,
                        new Lo3Datum(geboortedatum), new Lo3GemeenteCode(gemeenteCodeGeboorte),
                        Lo3LandCode.NEDERLAND, Lo3GeslachtsaanduidingEnum.MAN.asElement(),
                        Lo3AanduidingNaamgebruikCodeEnum.EIGEN_GESLACHTSNAAM.asElement(), null, null);
        // inhoud.valideer();

        final Lo3Historie historie =
                new Lo3Historie(indicatieOnjuist, new Lo3Datum(ingangsdatumGeldigheid),
                        new Lo3Datum(datumVanOpneming));
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(documentId, new Lo3GemeenteCode(gemeenteCodeAkte), nummerAkte, null, null, null,
                        null, null);

        return new Lo3Categorie<Lo3PersoonInhoud>(inhoud, documentatie, historie, new Lo3Herkomst(
                Lo3CategorieEnum.CATEGORIE_01, 0, 0));
    }

    private Lo3Stapel<Lo3PersoonInhoud> buildPersoonStapel() {
        final List<Lo3Categorie<Lo3PersoonInhoud>> categorieen = new ArrayList<Lo3Categorie<Lo3PersoonInhoud>>();

        // Casus 1
        // @formatter:off
        categorieen.add(buildPersoon(1000000000L, "Klaas", "Jansen", 19900101, "0363", null, 20000101, 20000602, 4,
                "0518", "Naamsw-1"));
        categorieen.add(buildPersoon(1000000000L, "Klaas", "Jansen", 19900101, "0599", ONJUIST, 20000101, 20000102,
                2, "0518", "Naamsw-1"));
        categorieen.add(buildPersoon(1000000000L, "Henk", "Jansen", 19900101, "0363", null, 19950101, 20010103, 6,
                "0518", "Naamsw-2b"));
        categorieen.add(buildPersoon(1000000000L, "Hank", "Jansen", 19900101, "0363", ONJUIST, 19950101, 20010102, 5,
                "0518", "Naamsw-2a"));
        categorieen.add(buildPersoon(1000000000L, "Jan", "Jansen", 19900101, "0363", null, 19900101, 20000602, 3,
                "0518", "Geb.akte-1b"));
        categorieen.add(buildPersoon(1000000000L, "Jan", "Jansen", 19900101, "0599", ONJUIST, 19900101, 19900102, 1,
                "0518", "Geb.akte-1a"));
        // @formatter:on

        final Lo3Stapel<Lo3PersoonInhoud> stapel = StapelUtils.createStapel(categorieen);
        // stapel.valideer();

        return stapel;
    }

    public static Lo3Stapel<Lo3InschrijvingInhoud> buildInschrijvingStapel() {
        final Lo3InschrijvingInhoud inhoud =
                new Lo3InschrijvingInhoud(null, null, null, new Lo3Datum(19900101), null, null, 1,
                        new Lo3Datumtijdstempel(19900101120000000L), null);

        final Lo3Historie historie = Lo3Historie.NULL_HISTORIE;
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(-1000, new Lo3GemeenteCode("0518"), "Inschr-Akte", null, null, null, null, null);

        final List<Lo3Categorie<Lo3InschrijvingInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3InschrijvingInhoud>>();
        categorieen.add(new Lo3Categorie<Lo3InschrijvingInhoud>(inhoud, documentatie, historie, new Lo3Herkomst(
                Lo3CategorieEnum.CATEGORIE_07, 0, 0)));

        return new Lo3Stapel<Lo3InschrijvingInhoud>(categorieen);
    }

    private Lo3Categorie<Lo3NationaliteitInhoud> buildNationaliteit(
            final String nationaliteit,
            final Lo3IndicatieOnjuist indicatieOnjuist,
            final Integer ingangsdatumGeldigheid,
            final Integer datumVanOpneming,
            final Integer documentId,
            final String gemeenteCodeAkte,
            final String nummerAkte) {

        final Lo3NationaliteitInhoud inhoud =
                new Lo3NationaliteitInhoud(nationaliteit == null ? null : new Lo3NationaliteitCode(nationaliteit),
                        null, null, null);

        // inhoud.valideer();

        final Lo3Historie historie =
                new Lo3Historie(indicatieOnjuist, new Lo3Datum(ingangsdatumGeldigheid),
                        new Lo3Datum(datumVanOpneming));
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(documentId, new Lo3GemeenteCode(gemeenteCodeAkte), nummerAkte, null, null, null,
                        null, null);

        return new Lo3Categorie<Lo3NationaliteitInhoud>(inhoud, documentatie, historie, new Lo3Herkomst(
                Lo3CategorieEnum.CATEGORIE_04, 0, 0));
    }

    private Lo3Stapel<Lo3NationaliteitInhoud> buildNationaliteitStapel() {
        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3NationaliteitInhoud>>();

        // @formatter:off
        categorieen.add(buildNationaliteit("0055", null, 19900101, 19900103, 11, "0518", "Verkrijging-1"));
        // @formatter:on

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = StapelUtils.createStapel(categorieen);
        // stapel.valideer();

        return stapel;
    }

    private Lo3Stapel<Lo3NationaliteitInhoud> buildNationaliteitStapelCasus7() {
        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3NationaliteitInhoud>>();

        // @formatter:off
        categorieen.add(buildNationaliteit(null, null, 19941231, 19950103, 23, "0518", "Document-A3"));
        categorieen.add(buildNationaliteit(null, ONJUIST, 19950101, 19950102, 22, "0518", "Document-A2"));
        categorieen.add(buildNationaliteit("0007", null, 19900101, 19900102, 21, "0518", "Document-A1"));
        // @formatter:on

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = StapelUtils.createStapel(categorieen);
        // stapel.valideer();

        return stapel;
    }

    // CHECKSTYLE:OFF - Parameters
    private Lo3Categorie<Lo3KiesrechtInhoud> buildKiesrecht(
    // CHECKSTYLE:ON
            final Boolean aanduidingEuropeesKiesrecht,
            final Integer datumEuropeesKiesrecht,
            final Integer einddatumUitsluitingEuropeesKiesrecht,
            final Boolean aanduidingUitgeslotenKiesrecht,
            final Integer einddatumUitsluitingKiesrecht,
            final Integer documentId,
            final String gemeenteCodeAkte,
            final String nummerAkte) {
        final Lo3AanduidingEuropeesKiesrecht euroKiesrecht =
                aanduidingEuropeesKiesrecht == null ? null
                        : Boolean.TRUE.equals(aanduidingEuropeesKiesrecht) ? Lo3AanduidingEuropeesKiesrechtEnum.ONTVANGT_OPROEP
                                .asElement() : Lo3AanduidingEuropeesKiesrechtEnum.UITGESLOTEN.asElement();
        final Lo3AanduidingUitgeslotenKiesrecht nlKiesrecht =
                Boolean.TRUE.equals(aanduidingUitgeslotenKiesrecht) ? Lo3AanduidingUitgeslotenKiesrechtEnum.UITGESLOTEN_KIESRECHT
                        .asElement() : null;

        final Lo3KiesrechtInhoud inhoud =
                new Lo3KiesrechtInhoud(euroKiesrecht, datumEuropeesKiesrecht == null ? null : new Lo3Datum(
                        datumEuropeesKiesrecht), einddatumUitsluitingEuropeesKiesrecht == null ? null : new Lo3Datum(
                        einddatumUitsluitingEuropeesKiesrecht), nlKiesrecht,
                        einddatumUitsluitingKiesrecht == null ? null : new Lo3Datum(einddatumUitsluitingKiesrecht));

        // inhoud.valideer();

        final Lo3Documentatie documentatie =
                new Lo3Documentatie(documentId, new Lo3GemeenteCode(gemeenteCodeAkte), nummerAkte, null, null, null,
                        null, null);

        return new Lo3Categorie<Lo3KiesrechtInhoud>(inhoud, documentatie, Lo3Historie.NULL_HISTORIE, new Lo3Herkomst(
                Lo3CategorieEnum.CATEGORIE_13, 0, 0));
    }

    private Lo3Stapel<Lo3KiesrechtInhoud> buildKiesrechtStapel() {
        final List<Lo3Categorie<Lo3KiesrechtInhoud>> categorieen = new ArrayList<Lo3Categorie<Lo3KiesrechtInhoud>>();

        // @formatter:off
        categorieen.add(buildKiesrecht(null, null, null, true, 19900101, 1000, "0518", "Uitsluiting-1"));
        // @formatter:on

        final Lo3Stapel<Lo3KiesrechtInhoud> stapel = StapelUtils.createStapel(categorieen);
        // stapel.valideer();

        return stapel;
    }

    // CHECKSTYLE:OFF - NCSS
    @Test
    public void test() throws InputValidationException {
        // CHECKSTYLE:ON
        // Logging
        Logging.initContext();
        System.out.println("test()");

        System.out.println("build");
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(buildPersoonStapel());
        builder.nationaliteitStapel(buildNationaliteitStapelCasus7());
        builder.nationaliteitStapel(buildNationaliteitStapel());
        builder.kiesrechtStapel(buildKiesrechtStapel());
        builder.inschrijvingStapel(buildInschrijvingStapel());

        builder.verblijfplaatsStapel(VerplichteStapel.createVerblijfplaatsStapel());
        builder.ouder1Stapel(VerplichteStapel.createOuderStapel());
        builder.ouder2Stapel(VerplichteStapel.createOuderStapel());

        final Lo3Persoonslijst lo3Persoonslijst = builder.build();
        System.out.println("\n\nPersoon");
        printLo3(lo3Persoonslijst.getPersoonStapel());
        System.out.println("\n\nNationaliteit");
        printLo3(lo3Persoonslijst.getNationaliteitStapels());
        System.out.println("\n\nKiesrecht");
        printLo3(lo3Persoonslijst.getKiesrechtStapel());

        System.out.println("Converteer LO3 -> BRP");

        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(lo3Persoonslijst);
        System.out.println("\n\nSamengestelde naam");
        printBrp(brpPersoonslijst.getSamengesteldeNaamStapel());
        System.out.println("\n\nVoornamen");
        printBrp(brpPersoonslijst.getVoornaamStapels());
        System.out.println("\n\nGeslachtsnaam");
        printBrp(brpPersoonslijst.getGeslachtsnaamcomponentStapels());
        System.out.println("\n\nGeboorte");
        printBrp(brpPersoonslijst.getGeboorteStapel());
        System.out.println("\n\nNationaliteit");
        printBrp(brpPersoonslijst.getNationaliteitStapels());
        System.out.println("\n\nUitsluiting Nederlands kiesrecht");
        printBrp(brpPersoonslijst.getUitsluitingNederlandsKiesrechtStapel());
        System.out.println("\n\nEuropese verkiezingen");
        printBrp(brpPersoonslijst.getEuropeseVerkiezingenStapel());

        System.out.println("Converteer BRP -> LO3");

        final Lo3Persoonslijst lo3Persoonslijst2 = conversieService.converteerBrpPersoonslijst(brpPersoonslijst);
        System.out.println("\n\nPersoon");
        printLo3(lo3Persoonslijst2.getPersoonStapel());
        System.out.println("\n\nNationaliteit");
        printLo3(lo3Persoonslijst2.getNationaliteitStapels());
        System.out.println("\n\nKiesrecht");
        printLo3(lo3Persoonslijst2.getKiesrechtStapel());

        System.out.println("BZM module");
        final BrpPersoonslijstBuilder bzmBuilder = new BrpPersoonslijstBuilder(brpPersoonslijst);

        final List<BrpStapel<BrpDocumentInhoud>> bzmGeboorteDocumenten =
                new ArrayList<BrpStapel<BrpDocumentInhoud>>();
        final BrpGroep<BrpDocumentInhoud> brpDocumentInhoud =
                new BrpGroep<BrpDocumentInhoud>(new BrpDocumentInhoud(new BrpSoortDocumentCode("Akte"), null,
                        "BZM-00001", "BZM", new BrpPartijCode(null, Integer.valueOf("0518"))), new BrpHistorie(null,
                        null, BrpDatumTijd.fromDatumTijd(20100104120000L), null), null, null, null);
        bzmGeboorteDocumenten.add(new BrpStapel<BrpDocumentInhoud>(Collections.singletonList(brpDocumentInhoud)));
        // hier wordt bewust een actie met een NIET conversie code aangemaakt om een actie van de BZM te simuleren
        final BrpActie bzmGeboorteActie =
                new BrpActie(5003L, BrpSoortActieCode.INSCHR_GEBOORTE, BrpPartijCode.MIGRATIEVOORZIENING, null, null,
                        BrpDatumTijd.fromDatumTijd(20100104120000L), bzmGeboorteDocumenten, 0, null);
        final BrpGeboorteInhoud bzmGeboorteInhoud =
                new BrpGeboorteInhoud(new BrpDatum(19900101), new BrpGemeenteCode(new BigDecimal("1234")), null,
                        null, null, new BrpLandCode(Integer.valueOf("6030")), null);
        final BrpHistorie bzmGeboorteHistorie =
                new BrpHistorie(null, null, BrpDatumTijd.fromDatumTijd(20100104120000L), null);

        final BrpGroep<BrpGeboorteInhoud> bzmGeboorte =
                new BrpGroep<BrpGeboorteInhoud>(bzmGeboorteInhoud, bzmGeboorteHistorie, bzmGeboorteActie, null, null);
        final List<BrpGroep<BrpGeboorteInhoud>> bzmGeboorten = new ArrayList<BrpGroep<BrpGeboorteInhoud>>();
        for (final BrpGroep<BrpGeboorteInhoud> brpGeboorte : brpPersoonslijst.getGeboorteStapel()) {
            // if (brpGeboorte.getHistorie().getDatumTijdRegistratie().getDatumTijd() != 20010103120000L) {
            if (brpGeboorte.getActieVerval() != null) {
                bzmGeboorten.add(brpGeboorte);
            } else {
                final BrpHistorie his = brpGeboorte.getHistorie();
                final BrpHistorie his2 =
                        new BrpHistorie(null, null, his.getDatumTijdRegistratie(),
                                bzmGeboorteActie.getDatumTijdRegistratie());
                bzmGeboorten.add(new BrpGroep<BrpGeboorteInhoud>(brpGeboorte.getInhoud(), his2, brpGeboorte
                        .getActieInhoud(), bzmGeboorteActie, null));
            }
        }
        bzmGeboorten.add(bzmGeboorte);
        bzmBuilder.geboorteStapel(new BrpStapel<BrpGeboorteInhoud>(bzmGeboorten));

        final List<BrpStapel<BrpDocumentInhoud>> bzmSamengesteldeNaamDocumenten =
                new ArrayList<BrpStapel<BrpDocumentInhoud>>();
        final BrpGroep<BrpDocumentInhoud> brpDocumentInhoud2 =
                new BrpGroep<BrpDocumentInhoud>(new BrpDocumentInhoud(new BrpSoortDocumentCode("Akte"), null,
                        "BZM-00002", "BZM", new BrpPartijCode(null, Integer.valueOf("0518"))), new BrpHistorie(null,
                        null, BrpDatumTijd.fromDatumTijd(20100104120000L), null), null, null, null);
        bzmSamengesteldeNaamDocumenten.add(new BrpStapel<BrpDocumentInhoud>(Collections
                .singletonList(brpDocumentInhoud2)));

        final BrpActie bzmSamengesteldeNaamActie =
                new BrpActie(5004L, BrpSoortActieCode.INSCHR_GEBOORTE, BrpPartijCode.MIGRATIEVOORZIENING, null, null,
                        BrpDatumTijd.fromDatumTijd(20100223120000L), bzmSamengesteldeNaamDocumenten, 0, null);
        final BrpSamengesteldeNaamInhoud bzmSamengesteldeNaamInhoud =
                new BrpSamengesteldeNaamInhoud(null, "Jantje", null, null, null, "Jansen", false, true);
        final BrpHistorie bzmSamengesteldeNaamHistorie =
                new BrpHistorie(new BrpDatum(19920101), new BrpDatum(19950101),
                        BrpDatumTijd.fromDatumTijd(20100223120000L), null);
        final BrpGroep<BrpSamengesteldeNaamInhoud> bzmSamengesteldeNaam =
                new BrpGroep<BrpSamengesteldeNaamInhoud>(bzmSamengesteldeNaamInhoud, bzmSamengesteldeNaamHistorie,
                        bzmSamengesteldeNaamActie, null, null);
        final List<BrpGroep<BrpSamengesteldeNaamInhoud>> bzmSamengesteldeNamen =
                new ArrayList<BrpGroep<BrpSamengesteldeNaamInhoud>>();
        for (final BrpGroep<BrpSamengesteldeNaamInhoud> brpSamengesteldeNaam : brpPersoonslijst
                .getSamengesteldeNaamStapel()) {
            if (brpSamengesteldeNaam.getActieInhoud().getId().longValue() != 3L) {
                bzmSamengesteldeNamen.add(brpSamengesteldeNaam);
            } else {
                // Deze rij laten vervallen
                final BrpHistorie his = brpSamengesteldeNaam.getHistorie();
                final BrpHistorie his2 =
                        new BrpHistorie(his.getDatumAanvangGeldigheid(), his.getDatumEindeGeldigheid(),
                                his.getDatumTijdRegistratie(), bzmSamengesteldeNaamActie.getDatumTijdRegistratie());
                bzmSamengesteldeNamen.add(new BrpGroep<BrpSamengesteldeNaamInhoud>(brpSamengesteldeNaam.getInhoud(),
                        his2, brpSamengesteldeNaam.getActieInhoud(), bzmSamengesteldeNaamActie, null));

                // Nieuwe rij toevoegen
                final BrpHistorie his3 =
                        new BrpHistorie(his.getDatumAanvangGeldigheid(),
                                bzmSamengesteldeNaamHistorie.getDatumAanvangGeldigheid(),
                                his.getDatumTijdRegistratie(), bzmSamengesteldeNaamActie.getDatumTijdRegistratie());
                bzmSamengesteldeNamen.add(new BrpGroep<BrpSamengesteldeNaamInhoud>(brpSamengesteldeNaam.getInhoud(),
                        his3, brpSamengesteldeNaam.getActieInhoud(), null, bzmSamengesteldeNaamActie));

            }

        }

        bzmSamengesteldeNamen.add(bzmSamengesteldeNaam);
        bzmBuilder.samengesteldeNaamStapel(new BrpStapel<BrpSamengesteldeNaamInhoud>(bzmSamengesteldeNamen));

        final BrpPersoonslijst bzmPersoonslijst = bzmBuilder.build();

        System.out.println("\n\nGeboorte");
        printBrp(bzmPersoonslijst.getGeboorteStapel());
        System.out.println("\n\nSamengestelde naam");
        printBrp(bzmPersoonslijst.getSamengesteldeNaamStapel());

        System.out.println("Converteer BZM -> LO3");

        final Lo3Persoonslijst lo3Persoonslijst3 = conversieService.converteerBrpPersoonslijst(bzmPersoonslijst);
        System.out.println("\n\nPersoon");
        printLo3(lo3Persoonslijst3.getPersoonStapel());
        // System.out.println("\n\nNationaliteit");
        // printLo3(lo3Persoonslijst.getNationaliteitStapels());
        //
        // final BrpPersoonslijst brpPersoonslijst2 = ConversieService.converteerLo3Persoonslijst(lo3Persoonslijst3);
        // final Lo3Persoonslijst lo3Persoonslijst4 = ConversieService.converteerBrpPersoonslijst(brpPersoonslijst2);
        // System.out.println("\n\nPersoon");
        // printLo3(lo3Persoonslijst4.getPersoonStapel());

        Logging.destroyContext();
    }

    private void printBrp(final List<? extends BrpStapel<?>> stapels) {
        System.out.println("Stapels (" + stapels.size() + "):");
        for (final BrpStapel<?> stapel : stapels) {
            printBrp(stapel);
        }
    }

    private void printLo3(final List<? extends Lo3Stapel<?>> stapels) {
        System.out.println("Stapels (" + stapels.size() + "):");
        for (final Lo3Stapel<?> stapel : stapels) {
            printLo3(stapel);
        }
    }

    private void printBrp(final BrpStapel<?> stapel) {
        System.out.println("\nStapel (" + (stapel == null ? 0 : stapel.size()) + "):");
        System.out.println(String.format("%50s%30s%30s%40s%40s%10s%10s%10s", "", "Aanvang", "Einde", "Registratie",
                "Verval", "Inhoud", "Geldig", "Verval"));

        final List<BrpGroep<?>> groepen = new ArrayList<BrpGroep<?>>();
        if (stapel != null) {
            groepen.addAll(stapel.getGroepen());
        }
        Collections.sort(groepen, BRP_GROEPEN_COMPARATOR);

        for (final BrpGroep<?> groep : groepen) {
            print(groep.getInhoud());
            print(groep.getHistorie());
            print(groep.getActieInhoud());
            print(groep.getActieGeldigheid());
            print(groep.getActieVerval());
            System.out.println();
        }
    }

    private <T extends Lo3CategorieInhoud> void printLo3(final Lo3Stapel<T> stapel) {
        System.out.println("\nStapel (" + (stapel == null ? 0 : stapel.size()) + "):");
        System.out.println(String.format("%80s%10s%30s%30s%10s", "", "Onjuist", "Ingang", "Opneming", "Document"));

        final List<Lo3Categorie<T>> categorieen = new ArrayList<Lo3Categorie<T>>();
        if (stapel != null) {
            categorieen.addAll(stapel.getCategorieen());
        }
        Collections.sort(categorieen, new Lo3CategorieenComparator());

        for (final Lo3Categorie<T> categorie : categorieen) {
            print(categorie.getInhoud());
            print(categorie.getHistorie());
            print(categorie.getDocumentatie());
            System.out.println();
        }

    }

    /* Moet 50 lang zijn. */
    private void print(final BrpGroepInhoud inhoud) {
        if (inhoud instanceof BrpVoornaamInhoud) {
            final BrpVoornaamInhoud voornaam = (BrpVoornaamInhoud) inhoud;
            System.out.print(String.format("%10s%20s%20s", voornaam.getVolgnummer(), voornaam.getVoornaam(), ""));
        } else if (inhoud instanceof BrpGeslachtsnaamcomponentInhoud) {
            final BrpGeslachtsnaamcomponentInhoud geslachtsnaam = (BrpGeslachtsnaamcomponentInhoud) inhoud;
            System.out
                    .print(String.format("%10s%20s%20s", geslachtsnaam.getVolgnummer(), geslachtsnaam.getNaam(), ""));
        } else if (inhoud instanceof BrpSamengesteldeNaamInhoud) {
            final BrpSamengesteldeNaamInhoud samengesteldeNaam = (BrpSamengesteldeNaamInhoud) inhoud;
            System.out.print(String.format("%20s%20s%10s", samengesteldeNaam.getVoornamen(),
                    samengesteldeNaam.getGeslachtsnaam(), ""));

        } else if (inhoud instanceof BrpGeboorteInhoud) {
            final BrpGeboorteInhoud geboorte = (BrpGeboorteInhoud) inhoud;
            System.out.print(String.format("%10s%20s%20s", geboorte.getGeboortedatum().getDatum(), geboorte
                    .getGemeenteCode().getCode(), ""));
        } else if (inhoud instanceof BrpNationaliteitInhoud) {
            final BrpNationaliteitInhoud nationaliteit = (BrpNationaliteitInhoud) inhoud;
            System.out.print(String.format("%30s%20s", nationaliteit.getNationaliteitCode(), ""));
        } else if (inhoud instanceof BrpUitsluitingNederlandsKiesrechtInhoud) {
            final BrpUitsluitingNederlandsKiesrechtInhoud kiesrecht =
                    (BrpUitsluitingNederlandsKiesrechtInhoud) inhoud;
            System.out.print(String.format("%10s%40s", kiesrecht.getIndicatieUitsluitingNederlandsKiesrecht(),
                    kiesrecht.getDatumEindeUitsluitingNederlandsKiesrecht()));

        } else if (inhoud instanceof BrpEuropeseVerkiezingenInhoud) {
            final BrpEuropeseVerkiezingenInhoud euro = (BrpEuropeseVerkiezingenInhoud) inhoud;
            System.out.print(String.format("%10s%20s%20s", euro.getDeelnameEuropeseVerkiezingen(),
                    euro.getDatumAanleidingAanpassingDeelnameEuropeseVerkiezingen(),
                    euro.getDatumEindeUitsluitingEuropeesKiesrecht()));
        }
    }

    /* Moet 80 lang zijn. */
    private void print(final Lo3CategorieInhoud inhoud) {
        if (inhoud instanceof Lo3PersoonInhoud) {
            final Lo3PersoonInhoud persoon = (Lo3PersoonInhoud) inhoud;
            System.out.print(String.format("%12s%12s%12s%22s%22s", persoon.getaNummer(), persoon.getVoornamen(),
                    persoon.getGeslachtsnaam(), get(persoon.getGeboortedatum()), persoon.getGeboorteGemeenteCode()));
        }
        if (inhoud instanceof Lo3NationaliteitInhoud) {
            final Lo3NationaliteitInhoud nationaliteit = (Lo3NationaliteitInhoud) inhoud;
            System.out.print(String.format("%20s%20s%20s%20s", nationaliteit.getNationaliteitCode() == null ? null
                    : nationaliteit.getNationaliteitCode().getCode(), nationaliteit
                    .getRedenVerkrijgingNederlandschapCode() == null ? null : nationaliteit
                    .getRedenVerkrijgingNederlandschapCode().getCode(), nationaliteit
                    .getRedenVerliesNederlandschapCode() == null ? null : nationaliteit
                    .getRedenVerliesNederlandschapCode().getCode(), nationaliteit
                    .getAanduidingBijzonderNederlandschap()));
        }
        if (inhoud instanceof Lo3KiesrechtInhoud) {
            final Lo3KiesrechtInhoud kiesrecht = (Lo3KiesrechtInhoud) inhoud;
            System.out.print(String.format("%7s%12s%7s%12s%12s", kiesrecht.getAanduidingUitgeslotenKiesrecht(),
                    get(kiesrecht.getEinddatumUitsluitingKiesrecht()), kiesrecht.getAanduidingEuropeesKiesrecht(),
                    get(kiesrecht.getDatumEuropeesKiesrecht()),
                    get(kiesrecht.getEinddatumUitsluitingEuropeesKiesrecht())));
        }
    }

    private static Integer get(final Lo3Datum datum) {
        return datum == null ? null : datum.getDatum();
    }

    private void print(final BrpHistorie historie) {
        System.out
                .print(String.format("%30s%30s%40s%40s", historie.getDatumAanvangGeldigheid(),
                        historie.getDatumEindeGeldigheid(), historie.getDatumTijdRegistratie(),
                        historie.getDatumTijdVerval()));
    }

    private void print(final Lo3Historie historie) {
        System.out.print(String.format("%10s%30s%30s", historie.getIndicatieOnjuist(),
                historie.getIngangsdatumGeldigheid(), historie.getDatumVanOpneming()));
    }

    private void print(final Lo3Documentatie documentatie) {
        System.out.print(String.format("%10s", documentatie == null ? null : documentatie.getId()));
    }

    private void print(final BrpActie actie) {
        System.out.print(String.format("%10s", actie == null ? null : actie.getId()));
    }

    private static final class BrpGroepenComparator implements Comparator<BrpGroep<?>> {
        @Override
        public int compare(final BrpGroep<?> o1, final BrpGroep<?> o2) {
            return o2.getActieInhoud().getId().compareTo(o1.getActieInhoud().getId());
        }

    }

    private static final class Lo3CategorieenComparator implements Comparator<Lo3Categorie<?>> {

        @Override
        public int compare(final Lo3Categorie<?> arg0, final Lo3Categorie<?> arg1) {
            int result =
                    -arg0.getHistorie().getIngangsdatumGeldigheid()
                            .compareTo(arg1.getHistorie().getIngangsdatumGeldigheid());

            if (result == 0) {
                result =
                        -arg0.getHistorie().getDatumVanOpneming().compareTo(arg1.getHistorie().getDatumVanOpneming());
            }

            return result;
        }

    }

}
