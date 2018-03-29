/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingHuisnummerEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AangifteAdreshoudingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3FunctieAdresEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieDocumentEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Character;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Huisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;

/**
 * Abstract class voor de tests voor verblijfplaats zodat alle tests dezelfde waardes kunnen gebruiken
 */
public abstract class AbstractVerblijfplaatsTest extends AbstractComponentTest {

    // Groep 09
    protected static final Lo3GemeenteCode LO3_GEMEENTE_CODE = new Lo3GemeenteCode("1904");
    protected static final BrpGemeenteCode BRP_GEMEENTE_CODE = new BrpGemeenteCode("1904");
    protected static final Lo3Datum LO3_DATUM_INSCHRIJVING = new Lo3Datum(2000_01_01);
    // Groep 10
    protected static final Lo3FunctieAdres LO3_FUNCTIE_ADRES_B = Lo3FunctieAdresEnum.BRIEFADRES.asElement();
    protected static final BrpSoortAdresCode BRP_SOORT_ADRES_CODE_B = BrpSoortAdresCode.B;
    protected static final Lo3String GEMEENTE_DEEL = Lo3String.wrap("linkerkant");
    protected static final Lo3Datum LO3_DATUM_AANVANG_ADRESHOUDING = new Lo3Datum(2000_01_02);
    protected static final BrpDatum BRP_DATUM_AANVANG_ADRESHOUDING = new BrpDatum(2000_01_02, null);
    // Groep 11
    protected static final Lo3String STRAATNAAM_PUNT = Lo3String.wrap(".");
    protected static final Lo3String STRAATNAAM = Lo3String.wrap("straat");
    protected static final Lo3String NAAM_OPENBARE_RUIMTE = Lo3String.wrap("ergens maar niet overal");
    protected static final Lo3Integer HUISNUMMER = Lo3Integer.wrap(12);
    protected static final Lo3Huisnummer LO3_HUISNUMMER = new Lo3Huisnummer(Lo3Integer.unwrap(HUISNUMMER));
    protected static final Lo3Character HUISLETTER = Lo3Character.wrap('a');
    protected static final Lo3String HUISNUMMER_TOEVOEGING = Lo3String.wrap("-z");
    protected static final Lo3AanduidingHuisnummer LO3_AANDUIDING_HUISNUMMER = Lo3AanduidingHuisnummerEnum.BY.asElement();
    protected static final BrpAanduidingBijHuisnummerCode BRP_AANDUIDING_HUISNUMMER = new BrpAanduidingBijHuisnummerCode(
            BrpAanduidingBijHuisnummerCode.CODE_BY);
    protected static final Lo3String POSTCODE = Lo3String.wrap("1233WE");
    protected static final Lo3String WOONPLAATSNAAM = Lo3String.wrap("Appingedam");
    protected static final Lo3String IDENTCODE_ADRESSEERBAAR_OBJECT = Lo3String.wrap("2349_82_349832WER");
    protected static final Lo3String IDENTCODE_NUMMERAANDUIDING = Lo3String.wrap("0023_02_34939342w");
    // Groep 12
    protected static final Lo3String LOCATIE_OMSCHRIJVING = Lo3String.wrap("locatie omschrijving");
    // Groep 13
    protected static final Lo3LandCode LO3_LAND_CODE_EMIGRATIE = new Lo3LandCode("1234");
    protected static final BrpLandOfGebiedCode BRP_LAND_OF_GEBIED_CODE_EMIGRATIE = new BrpLandOfGebiedCode("1234");
    protected static final Lo3Datum LO3_DATUM_VERTREK_UIT_NL = new Lo3Datum(2000_01_03);
    protected static final Lo3String ADRES_BUITENLAND_1 = Lo3String.wrap("adres buitenland 1");
    protected static final Lo3String ADRES_BUITENLAND_2 = Lo3String.wrap("adres buitenland 2");
    protected static final Lo3String ADRES_BUITENLAND_3 = Lo3String.wrap("adres buitenland 3");
    // Groep 14
    protected static final Lo3LandCode LO3_LAND_CODE_IMMIGRATIE = new Lo3LandCode("4321");
    protected static final BrpLandOfGebiedCode BRP_LAND_OF_GEBIED_CODE_IMMIGRATIE = new BrpLandOfGebiedCode("4321");
    protected static final Lo3Datum LO3_DATUM_VESTIGING_IN_NL = new Lo3Datum(2000_01_04);
    // Groep 72
    protected static final Lo3AangifteAdreshouding LO3_AANGIFTE_ADRESHOUDING_INGESCHREVENE =
            Lo3AangifteAdreshoudingEnum.INGESCHREVENE.asElement();
    protected static final BrpAangeverCode BRP_AANGEVER_ADRESHOUDING_CODE_INGESCHREVENE = new BrpAangeverCode('I');
    protected static final BrpRedenWijzigingVerblijfCode BRP_REDEN_WIJZIGING_ADRES_PERSOON = new BrpRedenWijzigingVerblijfCode('P');

    // Groep 75
    protected static final Lo3IndicatieDocument LO3_INDICATIE_DOCUMENT = Lo3IndicatieDocumentEnum.INDICATIE.asElement();

    protected static final BrpLandOfGebiedCode BRP_LAND_OF_GEBIED_CODE_NL = new BrpLandOfGebiedCode("6030");
}
