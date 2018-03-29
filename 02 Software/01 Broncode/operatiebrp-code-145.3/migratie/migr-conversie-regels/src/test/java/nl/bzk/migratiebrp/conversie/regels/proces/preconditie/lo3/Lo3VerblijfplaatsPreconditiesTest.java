/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
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
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import nl.bzk.migratiebrp.conversie.regels.tabel.GemeenteConversietabel;
import nl.bzk.migratiebrp.conversie.regels.tabel.LandConversietabel;
import nl.bzk.migratiebrp.conversie.regels.tabel.WoonplaatsnaamConversietabel;
import org.junit.Test;

/**
 * Preconditie tests voor categorie 08: verblijfplaats.
 */
public class Lo3VerblijfplaatsPreconditiesTest extends AbstractPreconditieTest {

    private static final Lo3Herkomst LO3_HERKOMST_VERBLIJFPLAATS = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 0);
    private static final Lo3Herkomst LO3_HERKOMST_VERBLIJFPLAATS_1 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 1);
    private static final Lo3Herkomst LO3_HERKOMST_VERBLIJFPLAATS_2 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 2);
    private static final String GEMEENTE_INSCHRIJVING = "0518";

    private Lo3VerblijfplaatsPrecondities precondities = new Lo3VerblijfplaatsPrecondities(new ConversietabelFactoryImpl());

    private Lo3VerblijfplaatsInhoud.Builder bagBuilder() {
        final Lo3VerblijfplaatsInhoud.Builder builder = new Lo3VerblijfplaatsInhoud.Builder();
        builder.gemeenteInschrijving(new Lo3GemeenteCode(GEMEENTE_INSCHRIJVING));
        builder.datumInschrijving(new Lo3Datum(20040101));

        builder.functieAdres(new Lo3FunctieAdres("W"));
        builder.gemeenteDeel(Lo3String.wrap("Den Haag"));
        builder.aanvangAdreshouding(new Lo3Datum(20031230));

        builder.straatnaam(Lo3String.wrap("Straat"));
        builder.naamOpenbareRuimte(Lo3String.wrap("Voortuin"));
        builder.huisnummer(new Lo3Huisnummer(4));
        builder.huisletter(Lo3Character.wrap('a'));
        builder.huisnummertoevoeging(Lo3String.wrap("li"));
        builder.aanduidingHuisnummer(null);
        builder.postcode(Lo3String.wrap("9334AA"));
        builder.woonplaatsnaam(Lo3String.wrap("Voorschoten"));
        builder.identificatiecodeVerblijfplaats(Lo3String.wrap("ABCDEF0123456789"));
        builder.identificatiecodeNummeraanduiding(Lo3String.wrap("1234567890ABCDEF"));

        builder.locatieBeschrijving(null);

        builder.landAdresBuitenland(null);
        builder.vertrekUitNederland(null);
        builder.adresBuitenland1(null);
        builder.adresBuitenland2(null);
        builder.adresBuitenland3(null);

        builder.landVanwaarIngeschreven(new Lo3LandCode("5004"));
        builder.vestigingInNederland(new Lo3Datum(19770405));

        builder.aangifteAdreshouding(new Lo3AangifteAdreshouding("K"));

        builder.indicatieDocument(new Lo3IndicatieDocument(1));

        return builder;
    }

    private Lo3VerblijfplaatsInhoud.Builder oudBuilder() {
        final Lo3VerblijfplaatsInhoud.Builder builder = new Lo3VerblijfplaatsInhoud.Builder();
        builder.gemeenteInschrijving(new Lo3GemeenteCode(GEMEENTE_INSCHRIJVING));
        builder.datumInschrijving(new Lo3Datum(20040101));

        builder.functieAdres(new Lo3FunctieAdres("W"));
        builder.gemeenteDeel(Lo3String.wrap("Den Haag"));
        builder.aanvangAdreshouding(new Lo3Datum(20031230));

        builder.straatnaam(Lo3String.wrap("Straat"));
        builder.naamOpenbareRuimte(null);
        builder.huisnummer(new Lo3Huisnummer(4));
        builder.huisletter(Lo3Character.wrap('a'));
        builder.huisnummertoevoeging(null);
        builder.aanduidingHuisnummer(new Lo3AanduidingHuisnummer("to"));
        builder.postcode(Lo3String.wrap("9334AA"));
        builder.woonplaatsnaam(null);
        builder.identificatiecodeVerblijfplaats(null);
        builder.identificatiecodeNummeraanduiding(null);

        builder.locatieBeschrijving(null);

        builder.landAdresBuitenland(null);
        builder.vertrekUitNederland(null);
        builder.adresBuitenland1(null);
        builder.adresBuitenland2(null);
        builder.adresBuitenland3(null);

        builder.landVanwaarIngeschreven(new Lo3LandCode("5004"));
        builder.vestigingInNederland(new Lo3Datum(19770405));

        builder.aangifteAdreshouding(new Lo3AangifteAdreshouding("K"));

        builder.indicatieDocument(new Lo3IndicatieDocument(1));

        return builder;
    }

    private Lo3VerblijfplaatsInhoud.Builder locatieBuilder() {
        final Lo3VerblijfplaatsInhoud.Builder builder = new Lo3VerblijfplaatsInhoud.Builder();
        builder.gemeenteInschrijving(new Lo3GemeenteCode(GEMEENTE_INSCHRIJVING));
        builder.datumInschrijving(new Lo3Datum(20040101));

        builder.functieAdres(new Lo3FunctieAdres("W"));
        builder.gemeenteDeel(Lo3String.wrap("Den Haag"));
        builder.aanvangAdreshouding(new Lo3Datum(20031230));

        builder.straatnaam(null);
        builder.naamOpenbareRuimte(null);
        builder.huisnummer(null);
        builder.huisletter(null);
        builder.huisnummertoevoeging(null);
        builder.aanduidingHuisnummer(null);
        builder.postcode(null);
        builder.woonplaatsnaam(null);
        builder.identificatiecodeVerblijfplaats(null);
        builder.identificatiecodeNummeraanduiding(null);

        builder.locatieBeschrijving(Lo3String.wrap("Ergens op de hei"));

        builder.landAdresBuitenland(null);
        builder.vertrekUitNederland(null);
        builder.adresBuitenland1(null);
        builder.adresBuitenland2(null);
        builder.adresBuitenland3(null);

        builder.landVanwaarIngeschreven(new Lo3LandCode("5004"));
        builder.vestigingInNederland(new Lo3Datum(19770405));

        builder.aangifteAdreshouding(new Lo3AangifteAdreshouding("K"));

        builder.indicatieDocument(new Lo3IndicatieDocument(1));

        return builder;
    }

    private Lo3VerblijfplaatsInhoud.Builder emigratieBuilder() {
        final Lo3VerblijfplaatsInhoud.Builder builder = new Lo3VerblijfplaatsInhoud.Builder();
        builder.gemeenteInschrijving(new Lo3GemeenteCode(GEMEENTE_INSCHRIJVING));
        builder.datumInschrijving(new Lo3Datum(20040101));

        builder.functieAdres(null);
        builder.gemeenteDeel(null);
        builder.aanvangAdreshouding(null);

        builder.straatnaam(null);
        builder.naamOpenbareRuimte(null);
        builder.huisnummer(null);
        builder.huisletter(null);
        builder.huisnummertoevoeging(null);
        builder.aanduidingHuisnummer(null);
        builder.postcode(null);
        builder.woonplaatsnaam(null);
        builder.identificatiecodeVerblijfplaats(null);
        builder.identificatiecodeNummeraanduiding(null);

        builder.locatieBeschrijving(null);

        builder.landAdresBuitenland(new Lo3LandCode("5001"));
        builder.vertrekUitNederland(new Lo3Datum(20040101));
        builder.adresBuitenland1(Lo3String.wrap("Regel 1"));
        builder.adresBuitenland2(Lo3String.wrap("Regel 2"));
        builder.adresBuitenland3(Lo3String.wrap("Regel 3"));

        builder.landVanwaarIngeschreven(null);
        builder.vestigingInNederland(null);

        builder.aangifteAdreshouding(new Lo3AangifteAdreshouding("K"));

        builder.indicatieDocument(new Lo3IndicatieDocument(1));

        return builder;
    }

    @Test
    public void testHappyBag() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(bagBuilder().build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testHappyOud() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(oudBuilder().build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testHappyLocatie() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(locatieBuilder().build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testHappyImmigratie() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(bagBuilder().build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testImmigratieGeenDatumVestiging() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.vestigingInNederland(null);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE079, 1);
    }

    @Test
    public void testImmigratieGeenLandVanwaarIngeschreven() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.landVanwaarIngeschreven(null);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE079, 1);
    }

    @Test
    public void testContr104() {
        Lo3Categorie<Lo3VerblijfplaatsInhoud> onjuist1 =
                Lo3StapelHelper.lo3Cat(emigratieBuilder().build(), null, Lo3StapelHelper.lo3His("O", 19990101, 19990101), LO3_HERKOMST_VERBLIJFPLAATS_2);
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(oudBuilder().build(), null, Lo3StapelHelper.lo3His("O", 19990101, 20000101), LO3_HERKOMST_VERBLIJFPLAATS_1);
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(bagBuilder().build(), null, Lo3StapelHelper.lo3His(null, 19990101, 20000101), LO3_HERKOMST_VERBLIJFPLAATS);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapelOk = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2, juist3);

        precondities.controleerStapel(stapelOk);
        assertAantalErrors(0);

        onjuist1 = Lo3StapelHelper.lo3Cat(emigratieBuilder().build(), null, Lo3StapelHelper.lo3His("O", 19990101, 19990101), LO3_HERKOMST_VERBLIJFPLAATS);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapelNok = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2);

        precondities.controleerStapel(stapelNok);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB033, 0);
        assertSoortMeldingCode(SoortMeldingCode.PRE055, 1);
    }

    @Test
    public void testContr106() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.woonplaatsnaam(Lo3String.wrap(WoonplaatsnaamConversietabel.LO3_NIET_VALIDE_UITZONDERING));
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE057, 1);
    }

    @Test
    public void testWoonadresLeeg() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.woonplaatsnaam(Lo3String.wrap(""));
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testWoonadresNull() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.woonplaatsnaam(null);
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testWoonadresPuntadres() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.woonplaatsnaam(Lo3String.wrap("."));
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testPre102Cat11Aanwezig() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.woonplaatsnaam(Lo3String.wrap(WoonplaatsnaamConversietabel.LO3_NIET_VALIDE_UITZONDERING));
        builder.gemeenteInschrijving(new Lo3GemeenteCode(GEMEENTE_INSCHRIJVING));

        builder.landAdresBuitenland(new Lo3LandCode("1234"));
        builder.vertrekUitNederland(new Lo3Datum(20000101));

        builder.functieAdres(null);
        builder.gemeenteDeel(null);
        builder.aanvangAdreshouding(null);
        builder.landVanwaarIngeschreven(null);
        builder.vestigingInNederland(null);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE102, 1);
    }

    @Test
    public void testPre102Cat12Aanwezig() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.locatieBeschrijving(new Lo3String("Locatie"));
        builder.landAdresBuitenland(new Lo3LandCode("1234"));
        builder.vertrekUitNederland(new Lo3Datum(20000101));

        builder.functieAdres(null);
        builder.gemeenteDeel(null);
        builder.aanvangAdreshouding(null);
        builder.landVanwaarIngeschreven(null);
        builder.vestigingInNederland(null);
        builder.woonplaatsnaam(null);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE102, 1);
    }

    @Test
    public void testContr110() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(bagBuilder().build(), null, Lo3StapelHelper.lo3His(null, 20000101, 20000100), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE031, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr244() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.gemeenteInschrijving(null);
        builder.datumInschrijving(null);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE036, 1);
    }

    @Test
    public void testContr246() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(bagBuilder().build(), null, Lo3StapelHelper.lo3His(null, null, 20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE030, 1);
    }

    @Test
    public void testContr247() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(bagBuilder().build(), null, Lo3StapelHelper.lo3His(null, 20000101, null), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE031, 1);
    }

    @Test
    public void testPreconditie94Groep14Only() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(bagBuilder().build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE094, 0);
    }

    @Test
    public void testPreconditie94Groep13EnGroep14() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.landAdresBuitenland(new Lo3LandCode("5001"));
        builder.vertrekUitNederland(new Lo3Datum(20040101));
        builder.adresBuitenland1(Lo3String.wrap("Regel 1"));
        builder.adresBuitenland2(Lo3String.wrap("Regel 2"));
        builder.adresBuitenland3(Lo3String.wrap("Regel 3"));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE094, 1);
    }

    @Test
    public void testPreconditie080HappyGroep13() {
        final Lo3VerblijfplaatsInhoud.Builder builder = emigratieBuilder();
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE080, 0);
        assertSoortMeldingCode(SoortMeldingCode.PRE094, 0);
    }

    @Test
    public void testPreconditie080HappyGroep10() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE080, 0);
    }

    @Test
    public void testPreconditie080Groep10en13() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.landAdresBuitenland(new Lo3LandCode("5001"));
        builder.vertrekUitNederland(new Lo3Datum(20040101));
        builder.adresBuitenland1(Lo3String.wrap("Regel 1"));
        builder.adresBuitenland2(Lo3String.wrap("Regel 2"));
        builder.adresBuitenland3(Lo3String.wrap("Regel 3"));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE080, 1);
    }

    @Test
    public void testPreconditie080geenGroep10en13() {
        final Lo3VerblijfplaatsInhoud.Builder builder = emigratieBuilder();
        builder.landAdresBuitenland(null);
        builder.vertrekUitNederland(null);
        builder.adresBuitenland1(null);
        builder.adresBuitenland2(null);
        builder.adresBuitenland3(null);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE080, 1);
    }

    @Test
    public void testContr251() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(bagBuilder().build(), null, Lo3StapelHelper.lo3His("O", 20000101, 20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        // assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE055, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    @Preconditie(SoortMeldingCode.PRE036)
    public void testContr317() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.gemeenteInschrijving(null);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE036, 1);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE036)
    public void testContr318() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.datumInschrijving(null);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE036, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr4015() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.datumInschrijving(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4016() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.aanvangAdreshouding(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4017() {
        final Lo3VerblijfplaatsInhoud.Builder builder = emigratieBuilder();
        builder.vertrekUitNederland(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4018() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.vestigingInNederland(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40124() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(bagBuilder().build(), null, Lo3StapelHelper.lo3His(null, 20040141, 20010101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40125() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(bagBuilder().build(), null, Lo3StapelHelper.lo3His(null, 20010101, 20040141), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4035() {
        final Lo3VerblijfplaatsInhoud.Builder builder = emigratieBuilder();
        builder.landAdresBuitenland(LandConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr4036() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.landVanwaarIngeschreven(LandConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr4061() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.gemeenteInschrijving(GemeenteConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr414() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.functieAdres(new Lo3FunctieAdres("Q"));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr422() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.huisletter(Lo3Character.wrap('2'));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testContr424() {
        final Lo3VerblijfplaatsInhoud.Builder builder = oudBuilder();
        builder.aanduidingHuisnummer(new Lo3AanduidingHuisnummer("QQ"));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3CategorieEnum.CATEGORIE_08));

        precondities.controleerStapel(stapel);

        // Er hoeft geen preconditie te worden gedaan op aanduiding huisnummer (zie ORANJE-270).
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 0);
    }

    @Test
    public void testContr452() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.aangifteAdreshouding(new Lo3AangifteAdreshouding("Q"));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3CategorieEnum.CATEGORIE_08));

        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr453() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.indicatieDocument(new Lo3IndicatieDocument(9));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3CategorieEnum.CATEGORIE_08));

        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testLegeOfNullStapel() {
        precondities.controleerStapel(null);
        assertAantalErrors(0);
    }

    @Test
    public void testPre081LandBuitenlandNull() {
        final Lo3VerblijfplaatsInhoud.Builder builder = emigratieBuilder();
        builder.landAdresBuitenland(null);
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3CategorieEnum.CATEGORIE_08));
        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.PRE081, 1);
        assertAantalErrors(1);
    }

    @Test
    public void testPre081VertrekBuitenlandNull() {
        final Lo3VerblijfplaatsInhoud.Builder builder = emigratieBuilder();
        builder.vertrekUitNederland(null);
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3CategorieEnum.CATEGORIE_08));
        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.PRE081, 1);
        assertAantalErrors(1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB028)
    public void testBijzondereSituatieLB028() {
        final Lo3VerblijfplaatsInhoud.Builder builder = emigratieBuilder();
        builder.vertrekUitNederland(new Lo3Datum(19800101));
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3CategorieEnum.CATEGORIE_08));
        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB028, 1);
        assertAantalInfos(1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB028)
    public void testBijzondereSituatieLB028TreedtNietOp() {
        final Lo3VerblijfplaatsInhoud.Builder builder = emigratieBuilder();
        builder.vertrekUitNederland(new Lo3Datum(20120101));
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3CategorieEnum.CATEGORIE_08));
        precondities.controleerStapel(stapel);
        assertAantalInfos(0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB028)
    public void testBijzondereSituatieLB028DatumInschrijvingGemeenteRecenter() {
        final Lo3VerblijfplaatsInhoud.Builder builder = emigratieBuilder();
        builder.vertrekUitNederland(new Lo3Datum(20100101));
        builder.datumInschrijving(new Lo3Datum(20120101));
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3CategorieEnum.CATEGORIE_08));
        precondities.controleerStapel(stapel);
        assertAantalInfos(0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB028)
    public void testBijzondereSituatieLB028DatumInschrijvingGemeenteRecenterOngelijkAanIngangsdatum() {
        final Lo3VerblijfplaatsInhoud.Builder builder = emigratieBuilder();
        builder.vertrekUitNederland(new Lo3Datum(20100101));
        builder.datumInschrijving(new Lo3Datum(20140101));
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3CategorieEnum.CATEGORIE_08));
        precondities.controleerStapel(stapel);
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB028, 1);
        assertAantalInfos(1);
    }

    @Test
    public void testPre084Groep11en12NietAanwezig() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        // Straatnaam is een verplicht veld als groep 11 voorkomt. Daarom straatnaam leeg ==> groep 11 leeg
        builder.straatnaam(null);
        builder.locatieBeschrijving(null);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3CategorieEnum.CATEGORIE_08));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE084, 1);
    }

    @Test
    public void testPre084Groep11NietAanwezig() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        // Straatnaam is een verplicht veld als groep 11 voorkomt. Daarom straatnaam leeg ==> groep 11 leeg
        builder.straatnaam(null);
        builder.locatieBeschrijving(Lo3String.wrap("Middle of Nowhere"));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3CategorieEnum.CATEGORIE_08));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE084, 0);
    }

    @Test
    public void testPre084Groep10AanwezigBeideGevuld() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.straatnaam(Lo3String.wrap("Straat"));
        builder.locatieBeschrijving(Lo3String.wrap("Middle of Nowhere"));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3CategorieEnum.CATEGORIE_08));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE084, 1);
    }

    @Test
    public void testPre084Groep10AanwezigBeideLeeg() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.straatnaam(null);
        builder.locatieBeschrijving(null);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3CategorieEnum.CATEGORIE_08));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE084, 1);
    }

    @Test
    public void testPre084Groep10AanwezigStraatnaamLeegMetOnderzoek() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        final Lo3Onderzoek onderzoek = new Lo3Onderzoek(new Lo3Integer("080000", null), new Lo3Datum(0), null);
        builder.straatnaam(new Lo3String(null, onderzoek));
        builder.locatieBeschrijving(null);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3CategorieEnum.CATEGORIE_08));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE084, 1);
    }

    @Test
    public void testPre084Groep10AanwezigLocatieBeschrijvingLeegMetOnderzoek() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        final Lo3Onderzoek onderzoek = new Lo3Onderzoek(new Lo3Integer("080000", null), new Lo3Datum(0), null);
        builder.straatnaam(null);
        builder.locatieBeschrijving(new Lo3String(null, onderzoek));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3CategorieEnum.CATEGORIE_08));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE084, 1);
    }

    @Test
    public void testPre084Groep10AanwezigStraatEnLocatieBeschrijvingLeegMetOnderzoek() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        final Lo3Onderzoek onderzoek = new Lo3Onderzoek(new Lo3Integer("080000", null), new Lo3Datum(0), null);
        builder.straatnaam(new Lo3String(null, onderzoek));
        builder.locatieBeschrijving(new Lo3String(null, onderzoek));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3CategorieEnum.CATEGORIE_08));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE084, 1);
    }

    @Test
    public void testPre08412NietAanwezig() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.straatnaam(Lo3String.wrap("Lange Vijverberg"));
        builder.locatieBeschrijving(null);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3CategorieEnum.CATEGORIE_08));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE084, 0);
    }

    @Test
    public void testPreconditie091GeenAanduiding() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.aanduidingHuisnummer(null);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE091, 0);
    }

    @Test
    public void testPreconditie091AanduidingOk() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.aanduidingHuisnummer(new Lo3AanduidingHuisnummer("to"));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE091, 0);
    }

    @Test
    public void testPreconditie091AanduidingFout() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.aanduidingHuisnummer(new Lo3AanduidingHuisnummer("na"));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));
        precondities.controleerStapel(stapel);

        assertSoortMeldingCode(SoortMeldingCode.PRE091, 1);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE100)
    public void testPreconditie100FunctieAdresNull() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.functieAdres(null);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));
        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE100, 1);
    }

    @Test
    public void testPreconditie098GroepNietAanwezig() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                bagBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"),
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_VERBLIJFPLAATS));
        precondities.controleerStapel(stapel);
        assertAantalErrors(0);
        assertSoortMeldingCode(SoortMeldingCode.PRE098, 0);
    }

    @Test
    public void testPreconditie098GroepAanwezig8810NietGevuld() {
        final Lo3Documentatie lo3Doc =
                Lo3StapelHelper.lo3Documentatie(
                        1L,
                        GEMEENTE_INSCHRIJVING,
                        "1-X" + String.format("%04d", 1),
                        null,
                        null,
                        null,
                        null,
                        "omschrijvingVerdrag");
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(bagBuilder().build(), lo3Doc, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));
        precondities.controleerStapel(stapel);
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE098, 1);
    }

    @Test
    public void testPreconditie098GroepAanwezig8810Gevuld() {
        final Lo3Documentatie lo3Doc =
                Lo3StapelHelper.lo3Documentatie(1L, GEMEENTE_INSCHRIJVING, "1-X" + String.format("%04d", 1), null, null, null, "0000", null);
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(bagBuilder().build(), lo3Doc, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_VERBLIJFPLAATS));
        precondities.controleerStapel(stapel);
        assertAantalErrors(0);
        assertSoortMeldingCode(SoortMeldingCode.PRE098, 0);
    }

    @Test
    public void testControleerGroep83Procedure8320NietGevuld() {
        final Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(Lo3Integer.wrap(10110), null, null);
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                bagBuilder().build(),
                                Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"),
                                lo3Onderzoek,
                                Lo3StapelHelper.lo3His(20000101),
                                LO3_HERKOMST_VERBLIJFPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE099, 1);
    }

    @Test
    public void testPre054GemeenteConversieRNINederlandsAdres() {
        final Lo3VerblijfplaatsInhoud.Builder inhoud = emigratieBuilder();
        inhoud.gemeenteInschrijving(GemeenteConversietabel.LO3_RNI_NIET_VALIDE_UITZONDERING);
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel = maakLo3Stapel(inhoud);

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testPre054GemeenteConversieRNINederlandsAdresOnbekendeRNI() {
        final Lo3VerblijfplaatsInhoud.Builder inhoud = bagBuilder();
        inhoud.gemeenteInschrijving(GemeenteConversietabel.LO3_RNI_NIET_VALIDE_UITZONDERING);
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel = maakLo3Stapel(inhoud);

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testBijzondereSituatieLb033() {
        final Lo3VerblijfplaatsInhoud.Builder inhoud = emigratieBuilder();
        inhoud.adresBuitenland2(null);
        inhoud.adresBuitenland3(null);

        precondities.controleerStapel(maakLo3Stapel(inhoud));
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB033, 1);
    }

    private Lo3Stapel<Lo3VerblijfplaatsInhoud> maakLo3Stapel(final Lo3VerblijfplaatsInhoud.Builder inhoud) {
        return Lo3StapelHelper.lo3Stapel(
                Lo3StapelHelper.lo3Cat(
                        inhoud.build(),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"),
                        null,
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_VERBLIJFPLAATS));
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB036)
    public void testWoonplaatsStandaardGeenBijzondereSituatieLB036() {
        final Lo3VerblijfplaatsInhoud.Builder inhoud = bagBuilder();
        inhoud.woonplaatsnaam(new Lo3String(".", null));

        precondities.controleerStapel(maakLo3Stapel(inhoud));
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB036, 0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB036)
    public void testBijzondereSituatieLB036() {
        final Lo3VerblijfplaatsInhoud.Builder inhoud = bagBuilder();
        inhoud.woonplaatsnaam(new Lo3String(".", null));
        inhoud.identificatiecodeNummeraanduiding(null);
        inhoud.identificatiecodeVerblijfplaats(null);
        inhoud.naamOpenbareRuimte(null);

        precondities.controleerStapel(maakLo3Stapel(inhoud));
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB036, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB037)
    public void testWoonplaatsStandaardGeenBijzondereSituatieLB037() {
        final Lo3VerblijfplaatsInhoud.Builder inhoud = bagBuilder();
        inhoud.woonplaatsnaam(new Lo3String("Plaatsnaam", null));

        precondities.controleerStapel(maakLo3Stapel(inhoud));
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB037, 0);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB037)
    public void testBijzondereSituatieLB037() {
        final Lo3VerblijfplaatsInhoud.Builder inhoud = bagBuilder();
        inhoud.woonplaatsnaam(null);

        precondities.controleerStapel(maakLo3Stapel(inhoud));
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB037, 1);
    }

    @Test
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB037)
    public void testBijzondereSituatieLB037MeerdereBagGegevensNietAanwezig() {
        final Lo3VerblijfplaatsInhoud.Builder inhoud = bagBuilder();
        inhoud.woonplaatsnaam(null);
        inhoud.naamOpenbareRuimte(null);

        precondities.controleerStapel(maakLo3Stapel(inhoud));
        assertSoortMeldingCode(SoortMeldingCode.BIJZ_CONV_LB037, 0);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE111)
    public void testPreconditie111() {
        final Lo3VerblijfplaatsInhoud.Builder inhoud = bagBuilder();
        inhoud.aangifteAdreshouding(null);

        precondities.controleerStapel(maakLo3Stapel(inhoud));
        assertSoortMeldingCode(SoortMeldingCode.PRE111, 1);
    }
}
