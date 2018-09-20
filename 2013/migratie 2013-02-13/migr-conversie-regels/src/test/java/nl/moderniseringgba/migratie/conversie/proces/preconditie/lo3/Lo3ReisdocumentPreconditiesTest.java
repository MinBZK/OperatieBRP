/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBezitBuitenlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LengteHouder;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Signalering;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.conversie.tabel.LandConversietabel;
import nl.moderniseringgba.migratie.conversie.tabel.ReisdocumentAutoriteitVanAfgifteConversietabel;

import org.junit.Test;

/**
 * Preconditie tests voor categorie 12: reisdocument.
 */
@SuppressWarnings("unchecked")
public class Lo3ReisdocumentPreconditiesTest extends AbstractPreconditieTest {

    private static final Lo3Herkomst LO3_HERKOMST_REISDOCUMENT = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_12, 0, 0);
    @Inject
    private Lo3ReisdocumentPrecondities precondities;

    private Lo3ReisdocumentInhoud.Builder reisdocumentBuilder() {
        final Lo3ReisdocumentInhoud.Builder builder = new Lo3ReisdocumentInhoud.Builder();
        builder.setSoortNederlandsReisdocument(new Lo3SoortNederlandsReisdocument("PN"));
        builder.setNummerNederlandsReisdocument("123456789");
        builder.setDatumUitgifteNederlandsReisdocument(new Lo3Datum(20050101));
        builder.setAutoriteitVanAfgifteNederlandsReisdocument(new Lo3AutoriteitVanAfgifteNederlandsReisdocument(
                "BI0518"));
        builder.setDatumEindeGeldigheidNederlandsReisdocument(new Lo3Datum(20150101));
        builder.setDatumInhoudingVermissingNederlandsReisdocument(new Lo3Datum(20080505));
        builder.setAanduidingInhoudingNederlandsReisdocument(new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(
                "V"));
        builder.setLengteHouder(new Lo3LengteHouder(180));

        builder.setSignalering(null);

        builder.setAanduidingBezitBuitenlandsReisdocument(null);

        return builder;
    }

    private Lo3ReisdocumentInhoud.Builder signaleringBuilder() {
        final Lo3ReisdocumentInhoud.Builder builder = new Lo3ReisdocumentInhoud.Builder();
        builder.setSoortNederlandsReisdocument(null);
        builder.setNummerNederlandsReisdocument(null);
        builder.setDatumUitgifteNederlandsReisdocument(null);
        builder.setAutoriteitVanAfgifteNederlandsReisdocument(null);
        builder.setDatumEindeGeldigheidNederlandsReisdocument(null);
        builder.setDatumInhoudingVermissingNederlandsReisdocument(null);
        builder.setAanduidingInhoudingNederlandsReisdocument(null);
        builder.setLengteHouder(null);

        builder.setSignalering(new Lo3Signalering(1));

        builder.setAanduidingBezitBuitenlandsReisdocument(null);

        return builder;
    }

    private Lo3ReisdocumentInhoud.Builder aanduidingBuilder() {
        final Lo3ReisdocumentInhoud.Builder builder = new Lo3ReisdocumentInhoud.Builder();
        builder.setSoortNederlandsReisdocument(null);
        builder.setNummerNederlandsReisdocument(null);
        builder.setDatumUitgifteNederlandsReisdocument(null);
        builder.setAutoriteitVanAfgifteNederlandsReisdocument(null);
        builder.setDatumEindeGeldigheidNederlandsReisdocument(null);
        builder.setDatumInhoudingVermissingNederlandsReisdocument(null);
        builder.setAanduidingInhoudingNederlandsReisdocument(null);
        builder.setLengteHouder(null);

        builder.setSignalering(null);

        builder.setAanduidingBezitBuitenlandsReisdocument(new Lo3AanduidingBezitBuitenlandsReisdocument(1));

        return builder;
    }

    @Test
    public void testHappyReisdocument() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(reisdocumentBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"),
                        LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testHappySignalering() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(signaleringBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"),
                        LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    @Test
    public void testHappyAanduiding() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(aanduidingBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"),
                        LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(0);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr110() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(reisdocumentBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 20000101, 20000100),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE031);
    }

    @Test
    public void testContr112() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel2 =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(reisdocumentBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 00000000, "Doc"),
                        LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel2);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(reisdocumentBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20000100, "Doc"),
                        LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE067);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr265() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.setSignalering(new Lo3Signalering(1));

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE035);
    }

    @Test
    public void testContr267() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(reisdocumentBuilder().build(),
                        Lo3StapelHelper.lo3His(null, null, 20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE030);
    }

    @Test
    public void testContr268() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(reisdocumentBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 20000101, null),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_REISDOCUMENT));

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
    public void testContr332() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.setSoortNederlandsReisdocument(null);

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE011);
    }

    @Test
    public void testContr333() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.setNummerNederlandsReisdocument(null);

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE011);
    }

    @Test
    public void testContr334() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.setDatumUitgifteNederlandsReisdocument(null);

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE011);
    }

    @Test
    public void testContr335() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.setAutoriteitVanAfgifteNederlandsReisdocument(null);

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE011);
    }

    @Test
    public void testContr336() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.setDatumEindeGeldigheidNederlandsReisdocument(null);

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE011);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr40111() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.setDatumUitgifteNederlandsReisdocument(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40112() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.setDatumEindeGeldigheidNederlandsReisdocument(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40113() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.setDatumInhoudingVermissingNederlandsReisdocument(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40121() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(reisdocumentBuilder().build(),
                        Lo3StapelHelper.lo3His(20000101), Lo3StapelHelper.lo3Doc(1L, "0514", 20050155, "Doc"),
                        LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40124() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(reisdocumentBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 20040141, 20010101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40125() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(reisdocumentBuilder().build(),
                        Lo3StapelHelper.lo3His(null, 20010101, 20040141),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4064() {
        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(reisdocumentBuilder().build(), Lo3StapelHelper
                        .lo3His(20000101), Lo3StapelHelper.lo3Documentatie(1L, null, null,
                        LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getCode(), 20010101, "Doc"),
                        LO3_HERKOMST_REISDOCUMENT));

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
    public void testContr437() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.setSoortNederlandsReisdocument(new Lo3SoortNederlandsReisdocument("QQ"));

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr439() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.setAutoriteitVanAfgifteNederlandsReisdocument(ReisdocumentAutoriteitVanAfgifteConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr440() {
        final Lo3ReisdocumentInhoud.Builder builder = reisdocumentBuilder();
        builder.setAanduidingInhoudingNederlandsReisdocument(new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(
                "Q"));

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr442() {
        final Lo3ReisdocumentInhoud.Builder builder = signaleringBuilder();
        builder.setSignalering(new Lo3Signalering(9));

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }

    @Test
    public void testContr443() {
        final Lo3ReisdocumentInhoud.Builder builder = aanduidingBuilder();
        builder.setAanduidingBezitBuitenlandsReisdocument(new Lo3AanduidingBezitBuitenlandsReisdocument(9));

        final Lo3Stapel<Lo3ReisdocumentInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Doc(1L, "0514", 20000101, "Doc"), LO3_HERKOMST_REISDOCUMENT));

        precondities.controleerStapel(stapel);

        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE054);
    }
}
