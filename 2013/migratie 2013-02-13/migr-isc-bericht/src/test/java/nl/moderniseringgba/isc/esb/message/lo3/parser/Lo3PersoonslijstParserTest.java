/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.parser;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBezitBuitenlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Huisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LengteHouder;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Signalering;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Test;

public class Lo3PersoonslijstParserTest {

    private static final String WAARDE_ELEMENT_0110 = "123456790";
    private static final String WAARDE_ELEMENT_0120 = "85412165";
    private static final String WAARDE_ELEMENT_0210 = "Gerard";
    private static final String WAARDE_ELEMENT_0220 = "H";
    private static final String WAARDE_ELEMENT_0230 = "de";
    private static final String WAARDE_ELEMENT_0240 = "Vries";
    private static final String WAARDE_ELEMENT_0310 = "20120301";
    private static final String WAARDE_ELEMENT_0320 = "1940";
    private static final String WAARDE_ELEMENT_0330 = "3010";
    private static final String WAARDE_ELEMENT_0410 = "M";
    private static final String WAARDE_ELEMENT_0510 = "3010";
    private static final String WAARDE_ELEMENT_0610 = "20120427";
    private static final String WAARDE_ELEMENT_0620 = "1903";
    private static final String WAARDE_ELEMENT_0630 = "3010";
    private static final String WAARDE_ELEMENT_6310 = "007";
    private static final String WAARDE_ELEMENT_0710 = "20121001";
    private static final String WAARDE_ELEMENT_0720 = "1904";
    private static final String WAARDE_ELEMENT_0730 = "3010";
    private static final String WAARDE_ELEMENT_0740 = "Scheiding";
    private static final String WAARDE_ELEMENT_0810 = "20121213";
    private static final String WAARDE_ELEMENT_0820 = "1904";
    private static final String WAARDE_ELEMENT_0830 = "3010";
    private static final String WAARDE_ELEMENT_0910 = "1904";
    private static final String WAARDE_ELEMENT_0920 = "19500615";
    private static final String WAARDE_ELEMENT_1010 = "W";
    private static final String WAARDE_ELEMENT_1020 = "1";
    private static final String WAARDE_ELEMENT_1030 = "20120501";
    private static final String WAARDE_ELEMENT_1110 = "Beekstraat";
    private static final String WAARDE_ELEMENT_1115 = "De flat";
    private static final String WAARDE_ELEMENT_1120 = "13";
    private static final String WAARDE_ELEMENT_1130 = "F";
    private static final String WAARDE_ELEMENT_1140 = "bis";
    private static final String WAARDE_ELEMENT_1150 = "4E ETAGE";
    private static final String WAARDE_ELEMENT_1160 = "1342 BX";
    private static final String WAARDE_ELEMENT_1170 = "Den Haag";
    private static final String WAARDE_ELEMENT_1180 = "H";
    private static final String WAARDE_ELEMENT_1190 = "L";
    private static final String WAARDE_ELEMENT_1210 = "De flat tegenover het grasveld van het malieveld";
    private static final String WAARDE_ELEMENT_1310 = "5012";
    private static final String WAARDE_ELEMENT_1320 = "20120701";
    private static final String WAARDE_ELEMENT_1330 = "365 First Avenue, New York";
    private static final String WAARDE_ELEMENT_1340 = "3650 First Avenue, New York";
    private static final String WAARDE_ELEMENT_1350 = "36500 First Avenue, New York";
    private static final String WAARDE_ELEMENT_1410 = "1010";
    private static final String WAARDE_ELEMENT_1420 = "20121201";
    private static final String WAARDE_ELEMENT_1510 = "Huwelijk";
    private static final String WAARDE_ELEMENT_2010 = "541231651";
    private static final String WAARDE_ELEMENT_2020 = "845145112";
    private static final String WAARDE_ELEMENT_3110 = "1";
    private static final String WAARDE_ELEMENT_3120 = "20120105";
    private static final String WAARDE_ELEMENT_3130 = "20121231";
    private static final String WAARDE_ELEMENT_3210 = "J";
    private static final String WAARDE_ELEMENT_3310 = "007";
    private static final String WAARDE_ELEMENT_3510 = "Paspoort";
    private static final String WAARDE_ELEMENT_3520 = "NUP8DFRT3";
    private static final String WAARDE_ELEMENT_3530 = "20121001";
    private static final String WAARDE_ELEMENT_3540 = "GEM";
    private static final String WAARDE_ELEMENT_3550 = "20131231";
    private static final String WAARDE_ELEMENT_3560 = "20121101";
    private static final String WAARDE_ELEMENT_3570 = "N";
    private static final String WAARDE_ELEMENT_3580 = "12";
    private static final String WAARDE_ELEMENT_3610 = "5";
    private static final String WAARDE_ELEMENT_3710 = "0";
    private static final String WAARDE_ELEMENT_3810 = "N";
    private static final String WAARDE_ELEMENT_3820 = "20121201";
    private static final String WAARDE_ELEMENT_3910 = "20121213";
    private static final String WAARDE_ELEMENT_3920 = "1904";
    private static final String WAARDE_ELEMENT_3930 = "3010";
    private static final String WAARDE_ELEMENT_6110 = "E";
    private static final String WAARDE_ELEMENT_6210 = "20120427";
    private static final String WAARDE_ELEMENT_6410 = "110";
    private static final String WAARDE_ELEMENT_6510 = "A";
    private static final String WAARDE_ELEMENT_6620 = "20121212";
    private static final String WAARDE_ELEMENT_6710 = "20121212";
    private static final String WAARDE_ELEMENT_6720 = "M";
    private static final String WAARDE_ELEMENT_6810 = "19500615";
    private static final String WAARDE_ELEMENT_6910 = "1905";
    private static final String WAARDE_ELEMENT_7010 = "0";
    private static final String WAARDE_ELEMENT_7210 = "BZM-aanmelding";
    private static final String WAARDE_ELEMENT_7510 = "0";
    private static final String WAARDE_ELEMENT_8010 = "1";
    private static final String WAARDE_ELEMENT_8020 = "20121219000000";
    private static final String WAARDE_ELEMENT_8110 = "1904";
    private static final String WAARDE_ELEMENT_8120 = "A3542352";
    private static final String WAARDE_ELEMENT_8210 = "1920";
    private static final String WAARDE_ELEMENT_8220 = "20121219";
    private static final String WAARDE_ELEMENT_8230 = "123456";
    private static final String WAARDE_ELEMENT_8310 = "0320";
    private static final String WAARDE_ELEMENT_8320 = "20121101";
    private static final String WAARDE_ELEMENT_8330 = "20121201";
    private static final String WAARDE_ELEMENT_8410 = "0";
    private static final String WAARDE_ELEMENT_8510 = "20121214";
    private static final String WAARDE_ELEMENT_8610 = "20121219";
    private static final String WAARDE_ELEMENT_8710 = "N";

    // Ouder 2
    private static final String WAARDE_ELEMENT_0110_2 = "8745316841";
    private static final String WAARDE_ELEMENT_0120_2 = "987153135";
    private static final String WAARDE_ELEMENT_0210_2 = "Gerarda";
    private static final String WAARDE_ELEMENT_0220_2 = "H";
    private static final String WAARDE_ELEMENT_0230_2 = "op het";
    private static final String WAARDE_ELEMENT_0240_2 = "Veld";
    private static final String WAARDE_ELEMENT_0310_2 = "19590312";
    private static final String WAARDE_ELEMENT_0410_2 = "V";

    @Test
    public void testPersoonslijstParser() {

        final Lo3PersoonslijstParser parser = new Lo3PersoonslijstParser();

        final Lo3Historie historie =
                new Lo3Historie(new Lo3IndicatieOnjuist(WAARDE_ELEMENT_8410), new Lo3Datum(
                        Integer.valueOf(WAARDE_ELEMENT_8510)), new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_8610)));

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3Stapel<Lo3PersoonInhoud> persoonStapel = vulCategoriePersoon(categorieen, historie);
        final Lo3Stapel<Lo3OuderInhoud> ouder1Stapel = vulCategorieOuder1(categorieen, historie);
        final Lo3Stapel<Lo3OuderInhoud> ouder2Stapel = vulCategorieOuder2(categorieen, historie);
        final Lo3Stapel<Lo3NationaliteitInhoud> nationaliteitStapel =
                vulCategorieNationaliteit(categorieen, historie);
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkOfGpStapel = vulCategorieHuwelijkOfGp(categorieen, historie);
        final Lo3Stapel<Lo3OverlijdenInhoud> overlijdenStapel = vulCategorieOverlijden(categorieen, historie);
        final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel = vulCategorieInschrijving(categorieen, historie);
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel =
                vulCategorieVerblijfplaats(categorieen, historie);
        final Lo3Stapel<Lo3KindInhoud> kindStapel = vulCategorieKind(categorieen, historie);
        final Lo3Stapel<Lo3VerblijfstitelInhoud> verblijfstitelStapel =
                vulCategorieVerblijfstitel(categorieen, historie);

        final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagVerhoudingStapel =
                vulCategorieGezagsverhouding(categorieen, historie);

        final Lo3Stapel<Lo3ReisdocumentInhoud> reisdocumentStapel = vulCategorieReisdocument(categorieen, historie);

        final Lo3Stapel<Lo3KiesrechtInhoud> kiesrechtStapel = vulCategorieKiesrecht(categorieen, historie);

        final Lo3Persoonslijst persoonslijst = parser.parse(categorieen);

        Assert.assertEquals(persoonStapel.getMeestRecenteElement().getInhoud(), persoonslijst.getPersoonStapel()
                .getMeestRecenteElement().getInhoud());

        Assert.assertEquals(ouder1Stapel.getMeestRecenteElement().getInhoud(), persoonslijst.getOuder1Stapels()
                .get(0).getMeestRecenteElement().getInhoud());

        Assert.assertEquals(ouder2Stapel.getMeestRecenteElement().getInhoud(), persoonslijst.getOuder2Stapels()
                .get(0).getMeestRecenteElement().getInhoud());

        Assert.assertEquals(nationaliteitStapel.getMeestRecenteElement().getInhoud(), persoonslijst
                .getNationaliteitStapels().get(0).getMeestRecenteElement().getInhoud());

        Assert.assertEquals(huwelijkOfGpStapel.getMeestRecenteElement().getInhoud(), persoonslijst
                .getHuwelijkOfGpStapels().get(0).getMeestRecenteElement().getInhoud());

        Assert.assertEquals(overlijdenStapel.getMeestRecenteElement().getInhoud(), persoonslijst
                .getOverlijdenStapel().getMeestRecenteElement().getInhoud());

        Assert.assertEquals(inschrijvingStapel.getMeestRecenteElement().getInhoud(), persoonslijst
                .getInschrijvingStapel().getMeestRecenteElement().getInhoud());

        Assert.assertEquals(verblijfplaatsStapel.getMeestRecenteElement().getInhoud(), persoonslijst
                .getVerblijfplaatsStapel().getMeestRecenteElement().getInhoud());

        Assert.assertEquals(kindStapel.getMeestRecenteElement().getInhoud(), persoonslijst.getKindStapels().get(0)
                .getMeestRecenteElement().getInhoud());

        Assert.assertEquals(verblijfstitelStapel.getMeestRecenteElement().getInhoud(), persoonslijst
                .getVerblijfstitelStapel().getMeestRecenteElement().getInhoud());

        Assert.assertEquals(gezagVerhoudingStapel.getMeestRecenteElement().getInhoud(), persoonslijst
                .getGezagsverhoudingStapel().getMeestRecenteElement().getInhoud());

        Assert.assertEquals(reisdocumentStapel.getMeestRecenteElement().getInhoud(), persoonslijst
                .getReisdocumentStapels().get(0).getMeestRecenteElement().getInhoud());

        Assert.assertEquals(kiesrechtStapel.getMeestRecenteElement().getInhoud(), persoonslijst.getKiesrechtStapel()
                .getMeestRecenteElement().getInhoud());
    }

    private Lo3Stapel<Lo3PersoonInhoud> vulCategoriePersoon(
            final List<Lo3CategorieWaarde> categorieen,
            final Lo3Historie historie) {

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 1, 1);

        categorie.addElement(Lo3ElementEnum.ELEMENT_0110, WAARDE_ELEMENT_0110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0120, WAARDE_ELEMENT_0120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0210, WAARDE_ELEMENT_0210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_ELEMENT_0220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_ELEMENT_0230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_ELEMENT_0240);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0310, WAARDE_ELEMENT_0310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0320, WAARDE_ELEMENT_0320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0330, WAARDE_ELEMENT_0330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_2010, WAARDE_ELEMENT_2010);
        categorie.addElement(Lo3ElementEnum.ELEMENT_2020, WAARDE_ELEMENT_2020);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6110, WAARDE_ELEMENT_6110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDE_ELEMENT_8310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorie);

        final List<Lo3Categorie<Lo3PersoonInhoud>> categorieInhoudLijst =
                new ArrayList<Lo3Categorie<Lo3PersoonInhoud>>();

        final Lo3PersoonInhoud persoonInhoud =
                new Lo3PersoonInhoud(Long.valueOf(WAARDE_ELEMENT_0110), Long.valueOf(WAARDE_ELEMENT_0120),
                        WAARDE_ELEMENT_0210, new Lo3AdellijkeTitelPredikaatCode(WAARDE_ELEMENT_0220),
                        WAARDE_ELEMENT_0230, WAARDE_ELEMENT_0240, new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_0310)),
                        new Lo3GemeenteCode(WAARDE_ELEMENT_0320), new Lo3LandCode(WAARDE_ELEMENT_0330),
                        new Lo3Geslachtsaanduiding(WAARDE_ELEMENT_0410), new Lo3AanduidingNaamgebruikCode(
                                WAARDE_ELEMENT_6110), Long.valueOf(WAARDE_ELEMENT_2010),
                        Long.valueOf(WAARDE_ELEMENT_2020));

        final Lo3Categorie<Lo3PersoonInhoud> categorieInhoudCategorie =
                new Lo3Categorie<Lo3PersoonInhoud>(persoonInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_01, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        return new Lo3Stapel<Lo3PersoonInhoud>(categorieInhoudLijst);

    }

    private Lo3Stapel<Lo3OuderInhoud> vulCategorieOuder1(
            final List<Lo3CategorieWaarde> categorieen,
            final Lo3Historie historie) {

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_02, 1, 1);

        categorie.addElement(Lo3ElementEnum.ELEMENT_0110, WAARDE_ELEMENT_0110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0120, WAARDE_ELEMENT_0120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0210, WAARDE_ELEMENT_0210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_ELEMENT_0220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_ELEMENT_0230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_ELEMENT_0240);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0310, WAARDE_ELEMENT_0310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0320, WAARDE_ELEMENT_0320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0330, WAARDE_ELEMENT_0330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6210, WAARDE_ELEMENT_6210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDE_ELEMENT_8310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorie);

        final List<Lo3Categorie<Lo3OuderInhoud>> categorieInhoudLijst = new ArrayList<Lo3Categorie<Lo3OuderInhoud>>();

        final Lo3OuderInhoud ouderInhoud =
                new Lo3OuderInhoud(Long.valueOf(WAARDE_ELEMENT_0110), Long.valueOf(WAARDE_ELEMENT_0120),
                        WAARDE_ELEMENT_0210, new Lo3AdellijkeTitelPredikaatCode(WAARDE_ELEMENT_0220),
                        WAARDE_ELEMENT_0230, WAARDE_ELEMENT_0240, new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_0310)),
                        new Lo3GemeenteCode(WAARDE_ELEMENT_0320), new Lo3LandCode(WAARDE_ELEMENT_0330),
                        new Lo3Geslachtsaanduiding(WAARDE_ELEMENT_0410), new Lo3Datum(
                                Integer.valueOf(WAARDE_ELEMENT_6210)));

        final Lo3Categorie<Lo3OuderInhoud> categorieInhoudCategorie =
                new Lo3Categorie<Lo3OuderInhoud>(ouderInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_02, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        return new Lo3Stapel<Lo3OuderInhoud>(categorieInhoudLijst);

    }

    private Lo3Stapel<Lo3OuderInhoud> vulCategorieOuder2(
            final List<Lo3CategorieWaarde> categorieen,
            final Lo3Historie historie) {

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_03, 1, 1);

        categorie.addElement(Lo3ElementEnum.ELEMENT_0110, WAARDE_ELEMENT_0110_2);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0120, WAARDE_ELEMENT_0120_2);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0210, WAARDE_ELEMENT_0210_2);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_ELEMENT_0220_2);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_ELEMENT_0230_2);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_ELEMENT_0240_2);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0310, WAARDE_ELEMENT_0310_2);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0320, WAARDE_ELEMENT_0320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0330, WAARDE_ELEMENT_0330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410_2);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6210, WAARDE_ELEMENT_6210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDE_ELEMENT_8310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorie);

        final List<Lo3Categorie<Lo3OuderInhoud>> categorieInhoudLijst = new ArrayList<Lo3Categorie<Lo3OuderInhoud>>();

        final Lo3OuderInhoud ouderInhoud =
                new Lo3OuderInhoud(Long.valueOf(WAARDE_ELEMENT_0110_2), Long.valueOf(WAARDE_ELEMENT_0120_2),
                        WAARDE_ELEMENT_0210_2, new Lo3AdellijkeTitelPredikaatCode(WAARDE_ELEMENT_0220_2),
                        WAARDE_ELEMENT_0230_2, WAARDE_ELEMENT_0240_2, new Lo3Datum(
                                Integer.valueOf(WAARDE_ELEMENT_0310_2)), new Lo3GemeenteCode(WAARDE_ELEMENT_0320),
                        new Lo3LandCode(WAARDE_ELEMENT_0330), new Lo3Geslachtsaanduiding(WAARDE_ELEMENT_0410_2),
                        new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_6210)));

        final Lo3Categorie<Lo3OuderInhoud> categorieInhoudCategorie =
                new Lo3Categorie<Lo3OuderInhoud>(ouderInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_02, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        return new Lo3Stapel<Lo3OuderInhoud>(categorieInhoudLijst);

    }

    private Lo3Stapel<Lo3NationaliteitInhoud> vulCategorieNationaliteit(
            final List<Lo3CategorieWaarde> categorieen,
            final Lo3Historie historie) {
        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0510, WAARDE_ELEMENT_0510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6310, WAARDE_ELEMENT_6310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6410, WAARDE_ELEMENT_6410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6510, WAARDE_ELEMENT_6510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorie);

        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieInhoudLijst =
                new ArrayList<Lo3Categorie<Lo3NationaliteitInhoud>>();

        final Lo3NationaliteitInhoud categorieInhoud =
                new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(WAARDE_ELEMENT_0510),
                        new Lo3RedenNederlandschapCode(WAARDE_ELEMENT_6310), new Lo3RedenNederlandschapCode(
                                WAARDE_ELEMENT_6410), new Lo3AanduidingBijzonderNederlandschap(WAARDE_ELEMENT_6510));

        final Lo3Categorie<Lo3NationaliteitInhoud> categorieInhoudCategorie =
                new Lo3Categorie<Lo3NationaliteitInhoud>(categorieInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_02, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        return new Lo3Stapel<Lo3NationaliteitInhoud>(categorieInhoudLijst);

    }

    private Lo3Stapel<Lo3HuwelijkOfGpInhoud> vulCategorieHuwelijkOfGp(
            final List<Lo3CategorieWaarde> categorieen,
            final Lo3Historie historie) {
        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_05, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0110, WAARDE_ELEMENT_0110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0120, WAARDE_ELEMENT_0120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0210, WAARDE_ELEMENT_0210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_ELEMENT_0220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_ELEMENT_0230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_ELEMENT_0240);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0310, WAARDE_ELEMENT_0310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0320, WAARDE_ELEMENT_0320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0330, WAARDE_ELEMENT_0330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0610, WAARDE_ELEMENT_0610);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0620, WAARDE_ELEMENT_0620);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0630, WAARDE_ELEMENT_0630);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0710, WAARDE_ELEMENT_0710);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0720, WAARDE_ELEMENT_0720);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0730, WAARDE_ELEMENT_0730);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0740, WAARDE_ELEMENT_0740);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1510, WAARDE_ELEMENT_1510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDE_ELEMENT_8310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorie);

        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpInhoudLijst =
                new ArrayList<Lo3Categorie<Lo3HuwelijkOfGpInhoud>>();

        final Lo3HuwelijkOfGpInhoud huwelijkOfGpInhoud =
                new Lo3HuwelijkOfGpInhoud(Long.valueOf(WAARDE_ELEMENT_0110), Long.valueOf(WAARDE_ELEMENT_0120),
                        WAARDE_ELEMENT_0210, new Lo3AdellijkeTitelPredikaatCode(WAARDE_ELEMENT_0220),
                        WAARDE_ELEMENT_0230, WAARDE_ELEMENT_0240, new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_0310)),
                        new Lo3GemeenteCode(WAARDE_ELEMENT_0320), new Lo3LandCode(WAARDE_ELEMENT_0330),
                        new Lo3Geslachtsaanduiding(WAARDE_ELEMENT_0410), new Lo3Datum(
                                Integer.valueOf(WAARDE_ELEMENT_0610)), new Lo3GemeenteCode(WAARDE_ELEMENT_0620),
                        new Lo3LandCode(WAARDE_ELEMENT_0630), new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_0710)),
                        new Lo3GemeenteCode(WAARDE_ELEMENT_0720), new Lo3LandCode(WAARDE_ELEMENT_0730),
                        new Lo3RedenOntbindingHuwelijkOfGpCode(WAARDE_ELEMENT_0740), new Lo3SoortVerbintenis(
                                WAARDE_ELEMENT_1510));

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGpInhoudCategorie =
                new Lo3Categorie<Lo3HuwelijkOfGpInhoud>(huwelijkOfGpInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_05, 0, 0));

        huwelijkOfGpInhoudLijst.add(huwelijkOfGpInhoudCategorie);

        return new Lo3Stapel<Lo3HuwelijkOfGpInhoud>(huwelijkOfGpInhoudLijst);
    }

    private Lo3Stapel<Lo3OverlijdenInhoud> vulCategorieOverlijden(
            final List<Lo3CategorieWaarde> categorieen,
            final Lo3Historie historie) {

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_06, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0810, WAARDE_ELEMENT_0810);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0820, WAARDE_ELEMENT_0820);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0830, WAARDE_ELEMENT_0830);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDE_ELEMENT_8310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorie);

        final List<Lo3Categorie<Lo3OverlijdenInhoud>> categorieInhoudLijst =
                new ArrayList<Lo3Categorie<Lo3OverlijdenInhoud>>();

        final Lo3OverlijdenInhoud overlijdenInhoud =
                new Lo3OverlijdenInhoud(new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_0810)), new Lo3GemeenteCode(
                        WAARDE_ELEMENT_0820), new Lo3LandCode(WAARDE_ELEMENT_0830));

        final Lo3Categorie<Lo3OverlijdenInhoud> categorieInhoudCategorie =
                new Lo3Categorie<Lo3OverlijdenInhoud>(overlijdenInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_06, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        return new Lo3Stapel<Lo3OverlijdenInhoud>(categorieInhoudLijst);
    }

    private Lo3Stapel<Lo3InschrijvingInhoud> vulCategorieInschrijving(
            final List<Lo3CategorieWaarde> categorieen,
            final Lo3Historie historie) {

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_07, 1, 1);

        categorie.addElement(Lo3ElementEnum.ELEMENT_6620, WAARDE_ELEMENT_6620);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6710, WAARDE_ELEMENT_6710);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6720, WAARDE_ELEMENT_6720);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6810, WAARDE_ELEMENT_6810);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6910, WAARDE_ELEMENT_6910);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7010, WAARDE_ELEMENT_7010);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8010, WAARDE_ELEMENT_8010);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8020, WAARDE_ELEMENT_8020);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8710, WAARDE_ELEMENT_8710);

        categorieen.add(categorie);
        final Lo3Historie nullHistorie = Lo3Historie.NULL_HISTORIE;
        final List<Lo3Categorie<Lo3InschrijvingInhoud>> categorieInhoudLijst =
                new ArrayList<Lo3Categorie<Lo3InschrijvingInhoud>>();

        final Lo3InschrijvingInhoud inschrijvingInhoud =
                new Lo3InschrijvingInhoud(new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_6620)), new Lo3Datum(
                        Integer.valueOf(WAARDE_ELEMENT_6710)), new Lo3RedenOpschortingBijhoudingCode(
                        WAARDE_ELEMENT_6720), new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_6810)),
                        new Lo3GemeenteCode(WAARDE_ELEMENT_6910), new Lo3IndicatieGeheimCode(
                                Integer.valueOf(WAARDE_ELEMENT_7010)), Integer.valueOf(WAARDE_ELEMENT_8010),
                        new Lo3Datumtijdstempel(Long.valueOf(WAARDE_ELEMENT_8020)),
                        new Lo3IndicatiePKVolledigGeconverteerdCode(WAARDE_ELEMENT_8710));

        final Lo3Categorie<Lo3InschrijvingInhoud> categorieInhoudCategorie =
                new Lo3Categorie<Lo3InschrijvingInhoud>(inschrijvingInhoud, null, nullHistorie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_07, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        return new Lo3Stapel<Lo3InschrijvingInhoud>(categorieInhoudLijst);

    }

    private Lo3Stapel<Lo3VerblijfplaatsInhoud> vulCategorieVerblijfplaats(
            final List<Lo3CategorieWaarde> categorieen,
            final Lo3Historie historie) {

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_08, 1, 1);

        categorie.addElement(Lo3ElementEnum.ELEMENT_0910, WAARDE_ELEMENT_0910);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0920, WAARDE_ELEMENT_0920);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1010, WAARDE_ELEMENT_1010);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1020, WAARDE_ELEMENT_1020);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1030, WAARDE_ELEMENT_1030);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1110, WAARDE_ELEMENT_1110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1115, WAARDE_ELEMENT_1115);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1120, WAARDE_ELEMENT_1120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1130, WAARDE_ELEMENT_1130);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1140, WAARDE_ELEMENT_1140);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1150, WAARDE_ELEMENT_1150);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1160, WAARDE_ELEMENT_1160);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1170, WAARDE_ELEMENT_1170);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1180, WAARDE_ELEMENT_1180);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1190, WAARDE_ELEMENT_1190);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1210, WAARDE_ELEMENT_1210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1310, WAARDE_ELEMENT_1310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1320, WAARDE_ELEMENT_1320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1330, WAARDE_ELEMENT_1330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1340, WAARDE_ELEMENT_1340);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1350, WAARDE_ELEMENT_1350);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1410, WAARDE_ELEMENT_1410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1420, WAARDE_ELEMENT_1420);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7210, WAARDE_ELEMENT_7210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7510, WAARDE_ELEMENT_7510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDE_ELEMENT_8310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorie);

        final List<Lo3Categorie<Lo3VerblijfplaatsInhoud>> categorieInhoudLijst =
                new ArrayList<Lo3Categorie<Lo3VerblijfplaatsInhoud>>();

        final Lo3VerblijfplaatsInhoud verblijfplaatsInhoud =
                new Lo3VerblijfplaatsInhoud(new Lo3GemeenteCode(WAARDE_ELEMENT_0910), new Lo3Datum(
                        Integer.valueOf(WAARDE_ELEMENT_0920)), new Lo3FunctieAdres(WAARDE_ELEMENT_1010),
                        WAARDE_ELEMENT_1020, new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_1030)), WAARDE_ELEMENT_1110,
                        WAARDE_ELEMENT_1115, new Lo3Huisnummer(Integer.valueOf(WAARDE_ELEMENT_1120)),
                        WAARDE_ELEMENT_1130.charAt(0), WAARDE_ELEMENT_1140, new Lo3AanduidingHuisnummer(
                                WAARDE_ELEMENT_1150), WAARDE_ELEMENT_1160, WAARDE_ELEMENT_1170, WAARDE_ELEMENT_1180,
                        WAARDE_ELEMENT_1190, WAARDE_ELEMENT_1210, new Lo3LandCode(WAARDE_ELEMENT_1310), new Lo3Datum(
                                Integer.valueOf(WAARDE_ELEMENT_1320)), WAARDE_ELEMENT_1330, WAARDE_ELEMENT_1340,
                        WAARDE_ELEMENT_1350, new Lo3LandCode(WAARDE_ELEMENT_1410), new Lo3Datum(
                                Integer.valueOf(WAARDE_ELEMENT_1420)), new Lo3AangifteAdreshouding(
                                WAARDE_ELEMENT_7210), new Lo3IndicatieDocument(Integer.valueOf(WAARDE_ELEMENT_7510)));

        final Lo3Categorie<Lo3VerblijfplaatsInhoud> categorieInhoudCategorie =
                new Lo3Categorie<Lo3VerblijfplaatsInhoud>(verblijfplaatsInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_08, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        return new Lo3Stapel<Lo3VerblijfplaatsInhoud>(categorieInhoudLijst);

    }

    private Lo3Stapel<Lo3KindInhoud> vulCategorieKind(
            final List<Lo3CategorieWaarde> categorieen,
            final Lo3Historie historie) {
        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_09, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0110, WAARDE_ELEMENT_0110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0120, WAARDE_ELEMENT_0120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0210, WAARDE_ELEMENT_0210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_ELEMENT_0220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_ELEMENT_0230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_ELEMENT_0240);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0310, WAARDE_ELEMENT_0310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0320, WAARDE_ELEMENT_0320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0330, WAARDE_ELEMENT_0330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDE_ELEMENT_8310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorie);

        final List<Lo3Categorie<Lo3KindInhoud>> categorieInhoudLijst = new ArrayList<Lo3Categorie<Lo3KindInhoud>>();

        final Lo3KindInhoud kindInhoud =
                new Lo3KindInhoud(Long.valueOf(WAARDE_ELEMENT_0110), Long.valueOf(WAARDE_ELEMENT_0120),
                        WAARDE_ELEMENT_0210, new Lo3AdellijkeTitelPredikaatCode(WAARDE_ELEMENT_0220),
                        WAARDE_ELEMENT_0230, WAARDE_ELEMENT_0240, new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_0310)),
                        new Lo3GemeenteCode(WAARDE_ELEMENT_0320), new Lo3LandCode(WAARDE_ELEMENT_0330), true);

        final Lo3Categorie<Lo3KindInhoud> categorieInhoudCategorie =
                new Lo3Categorie<Lo3KindInhoud>(kindInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_09, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        return new Lo3Stapel<Lo3KindInhoud>(categorieInhoudLijst);
    }

    private Lo3Stapel<Lo3VerblijfstitelInhoud> vulCategorieVerblijfstitel(
            final List<Lo3CategorieWaarde> categorieen,
            final Lo3Historie historie) {
        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_10, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3910, WAARDE_ELEMENT_3910);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3920, WAARDE_ELEMENT_3920);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3930, WAARDE_ELEMENT_3930);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDE_ELEMENT_8310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorie);

        final List<Lo3Categorie<Lo3VerblijfstitelInhoud>> categorieInhoudLijst =
                new ArrayList<Lo3Categorie<Lo3VerblijfstitelInhoud>>();

        final Lo3VerblijfstitelInhoud verblijfstitelInhoud =
                new Lo3VerblijfstitelInhoud(new Lo3AanduidingVerblijfstitelCode(WAARDE_ELEMENT_3910), new Lo3Datum(
                        Integer.valueOf(WAARDE_ELEMENT_3920)), new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_3930)));

        final Lo3Categorie<Lo3VerblijfstitelInhoud> categorieInhoudCategorie =
                new Lo3Categorie<Lo3VerblijfstitelInhoud>(verblijfstitelInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_10, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        return new Lo3Stapel<Lo3VerblijfstitelInhoud>(categorieInhoudLijst);

    }

    private Lo3Stapel<Lo3GezagsverhoudingInhoud> vulCategorieGezagsverhouding(
            final List<Lo3CategorieWaarde> categorieen,
            final Lo3Historie historie) {

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_11, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3210, WAARDE_ELEMENT_3210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3310, WAARDE_ELEMENT_3310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDE_ELEMENT_8310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorie);

        final List<Lo3Categorie<Lo3GezagsverhoudingInhoud>> categorieInhoudLijst =
                new ArrayList<Lo3Categorie<Lo3GezagsverhoudingInhoud>>();

        final Lo3GezagsverhoudingInhoud gezagsverhoudingInhoud =
                new Lo3GezagsverhoudingInhoud(new Lo3IndicatieGezagMinderjarige(WAARDE_ELEMENT_3210),
                        new Lo3IndicatieCurateleregister(Integer.valueOf(WAARDE_ELEMENT_3310)));

        final Lo3Categorie<Lo3GezagsverhoudingInhoud> categorieInhoudCategorie =
                new Lo3Categorie<Lo3GezagsverhoudingInhoud>(gezagsverhoudingInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_11, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        return new Lo3Stapel<Lo3GezagsverhoudingInhoud>(categorieInhoudLijst);

    }

    private Lo3Stapel<Lo3ReisdocumentInhoud> vulCategorieReisdocument(
            final List<Lo3CategorieWaarde> categorieen,
            final Lo3Historie historie) {
        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_12, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3510, WAARDE_ELEMENT_3510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3520, WAARDE_ELEMENT_3520);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3530, WAARDE_ELEMENT_3530);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3540, WAARDE_ELEMENT_3540);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3550, WAARDE_ELEMENT_3550);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3560, WAARDE_ELEMENT_3560);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3570, WAARDE_ELEMENT_3570);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3580, WAARDE_ELEMENT_3580);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3610, WAARDE_ELEMENT_3610);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3710, WAARDE_ELEMENT_3710);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDE_ELEMENT_8310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorie);

        final List<Lo3Categorie<Lo3ReisdocumentInhoud>> categorieInhoudLijst =
                new ArrayList<Lo3Categorie<Lo3ReisdocumentInhoud>>();

        final Lo3ReisdocumentInhoud reisdocumentInhoud =
                new Lo3ReisdocumentInhoud(new Lo3SoortNederlandsReisdocument(WAARDE_ELEMENT_3510),
                        WAARDE_ELEMENT_3520, new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_3530)),
                        new Lo3AutoriteitVanAfgifteNederlandsReisdocument(WAARDE_ELEMENT_3540), new Lo3Datum(
                                Integer.valueOf(WAARDE_ELEMENT_3550)), new Lo3Datum(
                                Integer.valueOf(WAARDE_ELEMENT_3560)),
                        new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(WAARDE_ELEMENT_3570),
                        new Lo3LengteHouder(Integer.valueOf(WAARDE_ELEMENT_3580)), new Lo3Signalering(
                                Integer.valueOf(WAARDE_ELEMENT_3610)), new Lo3AanduidingBezitBuitenlandsReisdocument(
                                Integer.valueOf(WAARDE_ELEMENT_3710)));

        final Lo3Categorie<Lo3ReisdocumentInhoud> categorieInhoudCategorie =
                new Lo3Categorie<Lo3ReisdocumentInhoud>(reisdocumentInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_12, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        return new Lo3Stapel<Lo3ReisdocumentInhoud>(categorieInhoudLijst);

    }

    private Lo3Stapel<Lo3KiesrechtInhoud> vulCategorieKiesrecht(
            final List<Lo3CategorieWaarde> categorieen,
            final Lo3Historie historie) {
        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_13, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3110, WAARDE_ELEMENT_3110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3120, WAARDE_ELEMENT_3120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3130, WAARDE_ELEMENT_3130);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3810, WAARDE_ELEMENT_3810);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3820, WAARDE_ELEMENT_3820);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);

        categorieen.add(categorie);

        final Lo3Historie nullHistorie = Lo3Historie.NULL_HISTORIE;
        final List<Lo3Categorie<Lo3KiesrechtInhoud>> categorieInhoudLijst =
                new ArrayList<Lo3Categorie<Lo3KiesrechtInhoud>>();

        final Lo3KiesrechtInhoud gezagsverhoudingInhoud =
                new Lo3KiesrechtInhoud(new Lo3AanduidingEuropeesKiesrecht(Integer.valueOf(WAARDE_ELEMENT_3110)),
                        new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_3120)), new Lo3Datum(
                                Integer.valueOf(WAARDE_ELEMENT_3130)), new Lo3AanduidingUitgeslotenKiesrecht(
                                WAARDE_ELEMENT_3810), new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_3820)));

        final Lo3Categorie<Lo3KiesrechtInhoud> categorieInhoudCategorie =
                new Lo3Categorie<Lo3KiesrechtInhoud>(gezagsverhoudingInhoud, null, nullHistorie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_13, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        return new Lo3Stapel<Lo3KiesrechtInhoud>(categorieInhoudLijst);

    }

}
