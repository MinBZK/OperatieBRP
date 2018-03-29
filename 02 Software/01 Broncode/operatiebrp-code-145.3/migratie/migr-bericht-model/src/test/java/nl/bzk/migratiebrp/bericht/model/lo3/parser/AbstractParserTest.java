/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Character;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;

public abstract class AbstractParserTest {

    private static final String LAND_CODE = "3010";
    private static final String PLAATS_CODE = "1904";

    static final String WAARDE_ELEMENT_0110 = "1234567890";
    static final String WAARDE_ELEMENT_0120 = "213454121";
    static final String WAARDE_ELEMENT_0210 = "Johannes";
    static final String WAARDE_ELEMENT_0220 = "B";
    static final String WAARDE_ELEMENT_0230 = "van";
    static final String WAARDE_ELEMENT_0240 = "Bijsterveld";
    static final String WAARDE_ELEMENT_0310 = "19500615";
    static final String WAARDE_ELEMENT_0320 = PLAATS_CODE;
    static final String WAARDE_ELEMENT_0330 = LAND_CODE;
    static final String WAARDE_ELEMENT_0410 = "M";
    static final String WAARDE_ELEMENT_0510 = LAND_CODE;
    static final String WAARDE_ELEMENT_0610 = "20120427";
    static final String WAARDE_ELEMENT_0620 = "1903";
    static final String WAARDE_ELEMENT_0630 = LAND_CODE;
    static final String WAARDE_ELEMENT_0710 = "20121001";
    static final String WAARDE_ELEMENT_0720 = PLAATS_CODE;
    static final String WAARDE_ELEMENT_0730 = LAND_CODE;
    static final String WAARDE_ELEMENT_0740 = "Scheiding";
    static final String WAARDE_ELEMENT_0810 = "20121213";
    static final String WAARDE_ELEMENT_0820 = PLAATS_CODE;
    static final String WAARDE_ELEMENT_0830 = LAND_CODE;
    static final String WAARDE_ELEMENT_0910 = PLAATS_CODE;
    static final String WAARDE_ELEMENT_0920 = "19500615";
    static final String WAARDE_ELEMENT_1010 = "W";
    static final String WAARDE_ELEMENT_1020 = "1";
    static final String WAARDE_ELEMENT_1030 = "20120501";
    static final String WAARDE_ELEMENT_1110 = "Beekstraat";
    static final String WAARDE_ELEMENT_1115 = "De flat";
    static final String WAARDE_ELEMENT_1120 = "13";
    static final String WAARDE_ELEMENT_1130 = "F";
    static final String WAARDE_ELEMENT_1140 = "bis";
    static final String WAARDE_ELEMENT_1150 = "4E ETAGE";
    static final String WAARDE_ELEMENT_1160 = "1342 BX";
    static final String WAARDE_ELEMENT_1170 = "Den Haag";
    static final String WAARDE_ELEMENT_1180 = "H";
    static final String WAARDE_ELEMENT_1190 = "L";
    static final String WAARDE_ELEMENT_1210 = "De flat tegenover het grasveld van het malieveld";
    static final String WAARDE_ELEMENT_1310 = "5012";
    static final String WAARDE_ELEMENT_1320 = "20120701";
    static final String WAARDE_ELEMENT_1330 = "365 First Avenue, New York";
    static final String WAARDE_ELEMENT_1340 = "3650 First Avenue, New York";
    static final String WAARDE_ELEMENT_1350 = "36500 First Avenue, New York";
    static final String WAARDE_ELEMENT_1410 = "1010";
    static final String WAARDE_ELEMENT_1420 = "20121201";
    static final String WAARDE_ELEMENT_1510 = "Huwelijk";
    static final String WAARDE_ELEMENT_2010 = "541231651";
    static final String WAARDE_ELEMENT_2020 = "845145112";
    static final String WAARDE_ELEMENT_3110 = "1";
    static final String WAARDE_ELEMENT_3120 = "20120105";
    static final String WAARDE_ELEMENT_3130 = "20121231";
    static final String WAARDE_ELEMENT_3210 = "12";
    static final String WAARDE_ELEMENT_3310 = "1";
    static final String WAARDE_ELEMENT_3510 = "ID";
    static final String WAARDE_ELEMENT_3520 = "NUP8DFRT3";
    static final String WAARDE_ELEMENT_3530 = "20121001";
    static final String WAARDE_ELEMENT_3540 = "BW";
    static final String WAARDE_ELEMENT_3550 = "20131231";
    static final String WAARDE_ELEMENT_3560 = "20121101";
    static final String WAARDE_ELEMENT_3570 = "V";
    static final String WAARDE_ELEMENT_3580 = "180";
    static final String WAARDE_ELEMENT_3610 = "1";
    static final String WAARDE_ELEMENT_3710 = "1";
    static final String WAARDE_ELEMENT_3810 = "N";
    static final String WAARDE_ELEMENT_3820 = "20121201";
    static final String WAARDE_ELEMENT_3910 = "20121213";
    static final String WAARDE_ELEMENT_3920 = PLAATS_CODE;
    static final String WAARDE_ELEMENT_3930 = LAND_CODE;
    static final String WAARDE_ELEMENT_6110 = "E";
    static final String WAARDE_ELEMENT_6210 = "20120427";
    static final String WAARDE_ELEMENT_6310 = "007";
    static final String WAARDE_ELEMENT_6410 = "110";
    static final String WAARDE_ELEMENT_6510 = "A";
    static final String WAARDE_ELEMENT_6620 = "20121212";
    static final String WAARDE_ELEMENT_6710 = "20121212";
    static final String WAARDE_ELEMENT_6720 = "M";
    static final String WAARDE_ELEMENT_6810 = "19500615";
    static final String WAARDE_ELEMENT_6910 = "1905";
    static final String WAARDE_ELEMENT_7010 = "0";
    static final String WAARDE_ELEMENT_7110 = "20111111";
    static final String WAARDE_ELEMENT_7120 = "OmschrijvingVerificatie";
    static final String WAARDE_ELEMENT_7210 = "BZM-aanmelding";
    static final String WAARDE_ELEMENT_7310 = "1 2+a-f/Z.D";
    static final String WAARDE_ELEMENT_7510 = "0";
    static final String WAARDE_ELEMENT_8010 = "1";
    static final String WAARDE_ELEMENT_8020 = "20121219000000";
    static final String WAARDE_ELEMENT_8110 = PLAATS_CODE;
    static final String WAARDE_ELEMENT_8120 = "A3542352";
    static final String WAARDE_ELEMENT_8210 = "1920";
    static final String WAARDE_ELEMENT_8220 = "20121219";
    static final String WAARDE_ELEMENT_8230 = "123456";
    static final String WAARDE_ELEMENT_8320 = "20121101";
    static final String WAARDE_ELEMENT_8330 = "20121201";
    static final String WAARDE_ELEMENT_8410 = "O";
    static final String WAARDE_ELEMENT_8510 = "20121214";
    static final String WAARDE_ELEMENT_8610 = "20121219";
    static final String WAARDE_ELEMENT_8710 = "N";
    static final String WAARDE_ELEMENT_8810 = "1999";
    static final String WAARDE_ELEMENT_8820 = "Omschrijving verdrag";

    Lo3Datum maakDatum(final String waarde, final Lo3Onderzoek onderzoek) {
        return new Lo3Datum(waarde, onderzoek);
    }

    Lo3Onderzoek maakOnderzoek() {
        return new Lo3Onderzoek(maakLo3Integer(getGegevensInOnderzoek(), null), maakDatum(WAARDE_ELEMENT_8320, null), maakDatum(WAARDE_ELEMENT_8330, null));
    }

    Lo3Historie maakHistorie() {
        final Lo3CategorieEnum categorie = getCategorie();
        final Lo3IndicatieOnjuist indicatieOnjuist = Lo3CategorieEnum.CATEGORIE_12.equals(categorie) ? null : new Lo3IndicatieOnjuist(WAARDE_ELEMENT_8410);
        final Lo3Datum datumVanOpneming = maakDatum(WAARDE_ELEMENT_8610, null);

        return new Lo3Historie(indicatieOnjuist, maakDatum(WAARDE_ELEMENT_8510, maakOnderzoek()), datumVanOpneming);

    }

    Lo3Documentatie maakDocumentatie() {
        final Lo3CategorieEnum categorie = getCategorie();
        boolean bevatGroep81 = true;
        boolean bevatGroep82 = true;
        boolean bevatGroep88 = true;

        if (Lo3CategorieEnum.CATEGORIE_04.equals(categorie)
                || Lo3CategorieEnum.CATEGORIE_07.equals(categorie)
                || Lo3CategorieEnum.CATEGORIE_08.equals(categorie)
                || Lo3CategorieEnum.CATEGORIE_10.equals(categorie)
                || Lo3CategorieEnum.CATEGORIE_11.equals(categorie)
                || Lo3CategorieEnum.CATEGORIE_12.equals(categorie)
                || Lo3CategorieEnum.CATEGORIE_13.equals(categorie)
                || Lo3CategorieEnum.CATEGORIE_21.equals(categorie)) {
            bevatGroep81 = false;
        }
        if (Lo3CategorieEnum.CATEGORIE_07.equals(categorie)
                || Lo3CategorieEnum.CATEGORIE_08.equals(categorie)
                || Lo3CategorieEnum.CATEGORIE_10.equals(categorie)
                || Lo3CategorieEnum.CATEGORIE_21.equals(categorie)) {
            bevatGroep82 = false;
        }
        if (Lo3CategorieEnum.CATEGORIE_02.equals(categorie)
                || Lo3CategorieEnum.CATEGORIE_03.equals(categorie)
                || Lo3CategorieEnum.CATEGORIE_05.equals(categorie)
                || Lo3CategorieEnum.CATEGORIE_09.equals(categorie)
                || Lo3CategorieEnum.CATEGORIE_10.equals(categorie)
                || Lo3CategorieEnum.CATEGORIE_11.equals(categorie)
                || Lo3CategorieEnum.CATEGORIE_12.equals(categorie)
                || Lo3CategorieEnum.CATEGORIE_13.equals(categorie)
                || Lo3CategorieEnum.CATEGORIE_21.equals(categorie)) {
            bevatGroep88 = false;
        }

        Lo3GemeenteCode gemeenteAkte = null;
        Lo3String nummerAkte = null;
        Lo3GemeenteCode gemeenteDocument = null;
        Lo3Datum datumDocument = null;
        Lo3String beschrijvingDocument = null;
        Lo3RNIDeelnemerCode rniCode = null;
        Lo3String rniOmschrijving = null;
        if (bevatGroep81) {
            gemeenteAkte = new Lo3GemeenteCode(WAARDE_ELEMENT_8110);
            nummerAkte = maakLo3String(WAARDE_ELEMENT_8120, null);
        }
        if (bevatGroep82) {
            Lo3Onderzoek lo3Onderzoek = null;
            gemeenteDocument = new Lo3GemeenteCode(WAARDE_ELEMENT_8210, lo3Onderzoek);
            datumDocument = maakDatum(WAARDE_ELEMENT_8220, lo3Onderzoek);
            beschrijvingDocument = maakLo3String(WAARDE_ELEMENT_8230, lo3Onderzoek);
        }
        if (bevatGroep88) {
            rniCode = new Lo3RNIDeelnemerCode(WAARDE_ELEMENT_8810);
            rniOmschrijving = maakLo3String(WAARDE_ELEMENT_8820, null);
        }
        return Lo3Documentatie.build(gemeenteAkte, nummerAkte, gemeenteDocument, datumDocument, beschrijvingDocument, rniCode, rniOmschrijving);
    }

    Lo3Character maakLo3Character(final String waarde, final Lo3Onderzoek onderzoek) {
        return new Lo3Character(waarde, onderzoek);
    }

    Lo3Integer maakLo3Integer(final String waarde, final Lo3Onderzoek onderzoek) {
        return new Lo3Integer(waarde, onderzoek);
    }

    Lo3Long maakLo3Long(final String waarde, final Lo3Onderzoek onderzoek) {
        return new Lo3Long(waarde, onderzoek);
    }

    Lo3String maakLo3String(final String waarde, final Lo3Onderzoek onderzoek) {
        return new Lo3String(waarde, onderzoek);
    }

    /**
     * Geef de waarde van gegevens in onderzoek.
     * @return gegevens in onderzoek
     */
    abstract String getGegevensInOnderzoek();

    /**
     * Geef de waarde van categorie.
     * @return categorie
     */
    abstract Lo3CategorieEnum getCategorie();
}
