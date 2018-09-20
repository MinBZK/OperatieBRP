/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Huisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.conversie.tabel.GemeenteConversietabel;
import nl.moderniseringgba.migratie.conversie.tabel.LandConversietabel;
import nl.moderniseringgba.migratie.conversie.tabel.PlaatsConversietabel;

import org.junit.Test;

/**
 * Preconditie tests voor categorie 08: verblijfplaats.
 */
@SuppressWarnings("unchecked")
public class Lo3VerblijfsplaatPreconditiesTest extends AbstractPreconditieTest {

    private static final Lo3Herkomst LO3_HERKOMST_VERBLIJFSPLAATS = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0,
            0);
    @Inject
    private Lo3VerblijfplaatsPrecondities precondities;

    private Lo3VerblijfplaatsInhoud.Builder bagBuilder() {
        final Lo3VerblijfplaatsInhoud.Builder builder = new Lo3VerblijfplaatsInhoud.Builder();
        builder.setGemeenteInschrijving(new Lo3GemeenteCode("0518"));
        builder.setDatumInschrijving(new Lo3Datum(20040101));

        builder.setFunctieAdres(new Lo3FunctieAdres("W"));
        builder.setGemeenteDeel("Den Haag");
        builder.setAanvangAdreshouding(new Lo3Datum(20031230));

        builder.setStraatnaam("Straat");
        builder.setNaamOpenbareRuimte("Voortuin");
        builder.setHuisnummer(new Lo3Huisnummer(4));
        builder.setHuisletter('a');
        builder.setHuisnummertoevoeging("li");
        builder.setAanduidingHuisnummer(null);
        builder.setPostcode("9334AA");
        builder.setWoonplaatsnaam("Voorschoten");
        builder.setIdentificatiecodeVerblijfplaats("ABCDEF0123456789");
        builder.setIdentificatiecodeNummeraanduiding("1234567890ABCDEF");

        builder.setLocatieBeschrijving(null);

        builder.setLandWaarnaarVertrokken(null);
        builder.setVertrekUitNederland(null);
        builder.setAdresBuitenland1(null);
        builder.setAdresBuitenland2(null);
        builder.setAdresBuitenland3(null);

        builder.setLandVanwaarIngeschreven(new Lo3LandCode("5004"));
        builder.setVestigingInNederland(new Lo3Datum(19770405));

        builder.setAangifteAdreshouding(new Lo3AangifteAdreshouding("K"));

        builder.setIndicatieDocument(new Lo3IndicatieDocument(1));

        return builder;
    }

    private Lo3VerblijfplaatsInhoud.Builder oudBuilder() {
        final Lo3VerblijfplaatsInhoud.Builder builder = new Lo3VerblijfplaatsInhoud.Builder();
        builder.setGemeenteInschrijving(new Lo3GemeenteCode("0518"));
        builder.setDatumInschrijving(new Lo3Datum(20040101));

        builder.setFunctieAdres(new Lo3FunctieAdres("W"));
        builder.setGemeenteDeel("Den Haag");
        builder.setAanvangAdreshouding(new Lo3Datum(20031230));

        builder.setStraatnaam("Straat");
        builder.setNaamOpenbareRuimte(null);
        builder.setHuisnummer(new Lo3Huisnummer(4));
        builder.setHuisletter('a');
        builder.setHuisnummertoevoeging(null);
        builder.setAanduidingHuisnummer(new Lo3AanduidingHuisnummer("TO"));
        builder.setPostcode("9334AA");
        builder.setWoonplaatsnaam(null);
        builder.setIdentificatiecodeVerblijfplaats(null);
        builder.setIdentificatiecodeNummeraanduiding(null);

        builder.setLocatieBeschrijving(null);

        builder.setLandWaarnaarVertrokken(null);
        builder.setVertrekUitNederland(null);
        builder.setAdresBuitenland1(null);
        builder.setAdresBuitenland2(null);
        builder.setAdresBuitenland3(null);

        builder.setLandVanwaarIngeschreven(new Lo3LandCode("5004"));
        builder.setVestigingInNederland(new Lo3Datum(19770405));

        builder.setAangifteAdreshouding(new Lo3AangifteAdreshouding("K"));

        builder.setIndicatieDocument(new Lo3IndicatieDocument(1));

        return builder;
    }

    private Lo3VerblijfplaatsInhoud.Builder locatieBuilder() {
        final Lo3VerblijfplaatsInhoud.Builder builder = new Lo3VerblijfplaatsInhoud.Builder();
        builder.setGemeenteInschrijving(new Lo3GemeenteCode("0518"));
        builder.setDatumInschrijving(new Lo3Datum(20040101));

        builder.setFunctieAdres(new Lo3FunctieAdres("W"));
        builder.setGemeenteDeel("Den Haag");
        builder.setAanvangAdreshouding(new Lo3Datum(20031230));

        builder.setStraatnaam(null);
        builder.setNaamOpenbareRuimte(null);
        builder.setHuisnummer(null);
        builder.setHuisletter(null);
        builder.setHuisnummertoevoeging(null);
        builder.setAanduidingHuisnummer(null);
        builder.setPostcode(null);
        builder.setWoonplaatsnaam(null);
        builder.setIdentificatiecodeVerblijfplaats(null);
        builder.setIdentificatiecodeNummeraanduiding(null);

        builder.setLocatieBeschrijving("Ergens op de hei");

        builder.setLandWaarnaarVertrokken(null);
        builder.setVertrekUitNederland(null);
        builder.setAdresBuitenland1(null);
        builder.setAdresBuitenland2(null);
        builder.setAdresBuitenland3(null);

        builder.setLandVanwaarIngeschreven(new Lo3LandCode("5004"));
        builder.setVestigingInNederland(new Lo3Datum(19770405));

        builder.setAangifteAdreshouding(new Lo3AangifteAdreshouding("K"));

        builder.setIndicatieDocument(new Lo3IndicatieDocument(1));

        return builder;
    }

    private Lo3VerblijfplaatsInhoud.Builder emigratieBuilder() {
        final Lo3VerblijfplaatsInhoud.Builder builder = new Lo3VerblijfplaatsInhoud.Builder();
        builder.setGemeenteInschrijving(new Lo3GemeenteCode("0518"));
        builder.setDatumInschrijving(new Lo3Datum(20040101));

        builder.setFunctieAdres(null);
        builder.setGemeenteDeel(null);
        builder.setAanvangAdreshouding(null);

        builder.setStraatnaam(null);
        builder.setNaamOpenbareRuimte(null);
        builder.setHuisnummer(null);
        builder.setHuisletter(null);
        builder.setHuisnummertoevoeging(null);
        builder.setAanduidingHuisnummer(null);
        builder.setPostcode(null);
        builder.setWoonplaatsnaam(null);
        builder.setIdentificatiecodeVerblijfplaats(null);
        builder.setIdentificatiecodeNummeraanduiding(null);

        builder.setLocatieBeschrijving(null);

        builder.setLandWaarnaarVertrokken(new Lo3LandCode("5001"));
        builder.setVertrekUitNederland(new Lo3Datum(20040101));
        builder.setAdresBuitenland1("Regel 1");
        builder.setAdresBuitenland2("Regel 2");
        builder.setAdresBuitenland3("Regel 3");

        builder.setLandVanwaarIngeschreven(null);
        builder.setVestigingInNederland(null);

        builder.setAangifteAdreshouding(new Lo3AangifteAdreshouding("K"));

        builder.setIndicatieDocument(new Lo3IndicatieDocument(1));

        return builder;
    }

    @Test
    public void testHappyBag() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(bagBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testHappyOud() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(oudBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testHappyLocatie() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(locatieBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testHappyImmigratie() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(emigratieBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr104() {
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> onjuist1 =
                Lo3StapelHelper.lo3Cat(emigratieBuilder().build(), Lo3StapelHelper.lo3His("O", 19990101, 19990101),
                        null, LO3_HERKOMST_VERBLIJFSPLAATS);
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(oudBuilder().build(), Lo3StapelHelper.lo3His("O", 19990101, 20000101), null,
                        LO3_HERKOMST_VERBLIJFSPLAATS);
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(bagBuilder().build(), Lo3StapelHelper.lo3His(null, 19990101, 20000101), null,
                        LO3_HERKOMST_VERBLIJFSPLAATS);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapelOk = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2, juist3);

        precondities.controleerStapel(stapelOk);
        assertAantalErrors(0);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapelNok = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2);

        precondities.controleerStapel(stapelNok);
        assertAantalErrors(2);
        assertPreconditie(Precondities.PRE055);
    }

    @Test
    public void testContr106() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.setWoonplaatsnaam(PlaatsConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE057);
    }

    @Test
    public void testContr110() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(bagBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 20000101, 20000100), null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE031);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr244() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.setGemeenteInschrijving(null);
        builder.setDatumInschrijving(null);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE036);
    }

    @Test
    public void testContr246() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(bagBuilder().build(),
                        Lo3StapelHelper.lo3His(null, null, 20000101), null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE030);
    }

    @Test
    public void testContr247() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(bagBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 20000101, null), null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE031);
    }

    @Test
    public void testContr251() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(bagBuilder().build(),
                        Lo3StapelHelper.lo3His("O", 20000101, 20000101), null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        // assertAantalErrors(1);
        assertPreconditie(Precondities.PRE055);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr317() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.setGemeenteInschrijving(null);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE036);
    }

    @Test
    public void testContr318() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.setDatumInschrijving(null);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE036);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr4015() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.setDatumInschrijving(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4016() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.setAanvangAdreshouding(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4017() {
        final Lo3VerblijfplaatsInhoud.Builder builder = emigratieBuilder();
        builder.setVertrekUitNederland(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4018() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.setVestigingInNederland(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40124() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(bagBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 20040141, 20010101), null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40125() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(bagBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 20010101, 20040141), null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4035() {
        final Lo3VerblijfplaatsInhoud.Builder builder = emigratieBuilder();
        builder.setLandWaarnaarVertrokken(LandConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr4036() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.setLandVanwaarIngeschreven(LandConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr4061() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.setGemeenteInschrijving(GemeenteConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr414() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.setFunctieAdres(new Lo3FunctieAdres("Q"));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr422() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.setHuisletter('2');

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testContr424() {
        final Lo3VerblijfplaatsInhoud.Builder builder = oudBuilder();
        builder.setAanduidingHuisnummer(new Lo3AanduidingHuisnummer("QQ"));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr452() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.setAangifteAdreshouding(new Lo3AangifteAdreshouding("Q"));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr453() {
        final Lo3VerblijfplaatsInhoud.Builder builder = bagBuilder();
        builder.setIndicatieDocument(new Lo3IndicatieDocument(9));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        null, LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr459() {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(bagBuilder().build(),
                        Lo3StapelHelper.lo3His("X", 20000101, 20000101), null, LO3_HERKOMST_VERBLIJFSPLAATS),
                        Lo3StapelHelper.lo3Cat(bagBuilder().build(), Lo3StapelHelper.lo3His(20010101), null,
                                LO3_HERKOMST_VERBLIJFSPLAATS));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testLegeOfNullStapel() {
        precondities.controleerStapel(null);
        assertAantalErrors(0);
    }
}
