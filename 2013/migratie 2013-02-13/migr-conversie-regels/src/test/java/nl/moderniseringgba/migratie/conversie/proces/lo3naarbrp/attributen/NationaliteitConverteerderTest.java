/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.AbstractComponentTest;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeprivilegieerdeIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpStatenloosIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.testutils.ReflectionUtil;
import nl.moderniseringgba.migratie.testutils.StapelUtils;

import org.junit.Assert;
import org.junit.Test;

public class NationaliteitConverteerderTest extends AbstractComponentTest {

    @Inject
    private NationaliteitConverteerder nationaliteitConverteerder;

    // CHECKSTYLE:OFF - Meer dan 7 paramets
    private Lo3Categorie<Lo3NationaliteitInhoud> buildNationaliteit(
    // CHECKSTYLE:ON
            final String nationaliteit,
            final String redenVerkrijging,
            final String redenVerlies,
            final String aanduidingBijzonder,
            final Lo3IndicatieOnjuist indicatieOnjuist,
            final Integer ingangsdatumGeldigheid,
            final Integer datumVanOpneming,
            final Integer documentId,
            final String gemeenteCodeAkte,
            final String nummerAkte) {

        final Lo3NationaliteitInhoud inhoud =
                new Lo3NationaliteitInhoud(nationaliteit == null ? null : new Lo3NationaliteitCode(nationaliteit),
                        redenVerkrijging == null ? null : new Lo3RedenNederlandschapCode(redenVerkrijging),
                        redenVerlies == null ? null : new Lo3RedenNederlandschapCode(redenVerlies),
                        aanduidingBijzonder == null ? null : new Lo3AanduidingBijzonderNederlandschap(
                                aanduidingBijzonder));

        // inhoud.valideer();

        final Lo3Historie historie =
                new Lo3Historie(indicatieOnjuist, new Lo3Datum(ingangsdatumGeldigheid),
                        new Lo3Datum(datumVanOpneming));
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(documentId, new Lo3GemeenteCode(gemeenteCodeAkte), nummerAkte, null, null, null,
                        null, null);
        return new Lo3Categorie<Lo3NationaliteitInhoud>(inhoud, documentatie, historie, null);
    }

    private Lo3Stapel<Lo3NationaliteitInhoud> buildDuitseStapel() {
        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3NationaliteitInhoud>>();

        // @formatter:off
        categorieen.add(buildNationaliteit("0055", null, null, null, null, 19900101, 19900102, 11, "Rotterdam", "Duits-1"));
        categorieen.add(buildNationaliteit(null,   null, null, null, null, 19920101, 19920102, 12, "Rotterdam", "Duits-2"));
        // @formatter:on

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = StapelUtils.createStapel(categorieen);
        // stapel.valideer();

        return stapel;
    }

    private Lo3Stapel<Lo3NationaliteitInhoud> buildNederlandseStapel() {
        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3NationaliteitInhoud>>();

        // @formatter:off
        categorieen.add(buildNationaliteit("0001", "179", null,  null, null, 19920101, 19920102, 21, "Rotterdam", "Nederlander-1"));
        categorieen.add(buildNationaliteit(null,   null,  "187", null, null, 19940101, 19940102, 22, "Rotterdam", "Nederlander-2"));
        // @formatter:on

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = StapelUtils.createStapel(categorieen);
        // stapel.valideer();

        return stapel;

    }

    private Lo3Stapel<Lo3NationaliteitInhoud> buildBijzonderStapel() {
        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3NationaliteitInhoud>>();

        // @formatter:off
        categorieen.add(buildNationaliteit(null, null, null, "B",  null, 19940101, 19940102, 31, "Rotterdam", "Bijzonder-1"));
        categorieen.add(buildNationaliteit(null, null, null, null, null, 19960101, 19960102, 32, "Rotterdam", "Bijzonder-2"));
        categorieen.add(buildNationaliteit(null, null, null, "V",  null, 19980101, 19980102, 33, "Rotterdam", "Bijzonder-3"));
        categorieen.add(buildNationaliteit(null, null, null, null, null, 20000101, 20000102, 34, "Rotterdam", "Bijzonder-4"));
        // @formatter:on

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = StapelUtils.createStapel(categorieen);
        // stapel.valideer();

        return stapel;

    }

    private Lo3Stapel<Lo3NationaliteitInhoud> buildStatenloosStapel() {
        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3NationaliteitInhoud>>();

        // @formatter:off
        categorieen.add(buildNationaliteit("0499", null, null, null, null, 19960101, 19960102, 41, "Rotterdam", "Statenloos-1"));
        categorieen.add(buildNationaliteit(null,   null, null, null, null, 19980101, 19980102, 42, "Rotterdam", "Statenloos-2"));
        categorieen.add(buildNationaliteit("0499", null, null, null, null, 20000101, 20000102, 43, "Rotterdam", "Statenloos-3"));
        // @formatter:on

        final Lo3Stapel<Lo3NationaliteitInhoud> stapel = StapelUtils.createStapel(categorieen);
        // stapel.valideer();

        return stapel;
    }

    private List<Lo3Stapel<Lo3NationaliteitInhoud>> buildNationaliteitStapels() {
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels = new ArrayList<Lo3Stapel<Lo3NationaliteitInhoud>>();
        stapels.add(buildNederlandseStapel());
        stapels.add(buildDuitseStapel());
        stapels.add(buildBijzonderStapel());
        stapels.add(buildStatenloosStapel());

        return stapels;
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testConverteer() throws InputValidationException {
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> lo3NationaliteitStapels = buildNationaliteitStapels();
        final MigratiePersoonslijstBuilder migratiePersoonslijstBuilder = new MigratiePersoonslijstBuilder();
        nationaliteitConverteerder.converteer(lo3NationaliteitStapels, migratiePersoonslijstBuilder);

        controleerNationaliteitStapels((List<MigratieStapel<BrpNationaliteitInhoud>>) ReflectionUtil.getField(
                migratiePersoonslijstBuilder, "nationaliteitStapels"));
        controleerStatenloosStapel((MigratieStapel<BrpStatenloosIndicatieInhoud>) ReflectionUtil.getField(
                migratiePersoonslijstBuilder, "statenloosIndicatieStapel"));
        controleerBehandeldStapel((MigratieStapel<BrpBehandeldAlsNederlanderIndicatieInhoud>) ReflectionUtil
                .getField(migratiePersoonslijstBuilder, "behandeldAlsNederlanderIndicatieStapel"));
        controleerVastgesteldStapel((MigratieStapel<BrpVastgesteldNietNederlanderIndicatieInhoud>) ReflectionUtil
                .getField(migratiePersoonslijstBuilder, "vastgesteldNietNederlanderIndicatieStapel"));
        controleerGeprivilegieerdeStapel((MigratieStapel<BrpGeprivilegieerdeIndicatieInhoud>) ReflectionUtil
                .getField(migratiePersoonslijstBuilder, "geprivilegieerdeIndicatieStapel"));
    }

    private void controleerNationaliteitStapels(final List<MigratieStapel<BrpNationaliteitInhoud>> stapels) {
        Assert.assertEquals(2, stapels.size());

        for (final MigratieStapel<BrpNationaliteitInhoud> stapel : stapels) {
            if ("0001".equals(stapel.get(0).getInhoud().getNationaliteitCode().getCode())) {
                controleerNederlandseStapel(stapel);
            }
            if ("0055".equals(stapel.get(0).getInhoud().getNationaliteitCode().getCode())) {
                controleerDuitseStapel(stapel);
            }
        }
    }

    private void controleerNederlandseStapel(final MigratieStapel<BrpNationaliteitInhoud> stapel) {
        Assert.assertEquals(2, stapel.size());

        final MigratieGroep<BrpNationaliteitInhoud> groep1 = stapel.get(0);
        final MigratieGroep<BrpNationaliteitInhoud> groep2 = stapel.get(1);

        Assert.assertEquals("0001", groep1.getInhoud().getNationaliteitCode().getCode());
        Assert.assertNull("0001", groep2.getInhoud().getNationaliteitCode());
    }

    private void controleerDuitseStapel(final MigratieStapel<BrpNationaliteitInhoud> stapel) {
        Assert.assertEquals(2, stapel.size());

        final MigratieGroep<BrpNationaliteitInhoud> groep1 = stapel.get(0);
        final MigratieGroep<BrpNationaliteitInhoud> groep2 = stapel.get(1);

        Assert.assertEquals("0055", groep1.getInhoud().getNationaliteitCode().getCode());
        Assert.assertEquals(null, groep2.getInhoud().getNationaliteitCode());

    }

    private void controleerStatenloosStapel(final MigratieStapel<BrpStatenloosIndicatieInhoud> stapel) {
        Assert.assertEquals(3, stapel.size());

        final MigratieGroep<BrpStatenloosIndicatieInhoud> groep1 = stapel.get(0);
        final MigratieGroep<BrpStatenloosIndicatieInhoud> groep2 = stapel.get(1);
        final MigratieGroep<BrpStatenloosIndicatieInhoud> groep3 = stapel.get(2);

        Assert.assertEquals(Boolean.TRUE, groep1.getInhoud().getHeeftIndicatie());
        Assert.assertEquals(null, groep2.getInhoud().getHeeftIndicatie());
        Assert.assertEquals(Boolean.TRUE, groep3.getInhoud().getHeeftIndicatie());

        Assert.assertEquals(new Lo3Datum(19960101), groep1.getHistorie().getIngangsdatumGeldigheid());
        Assert.assertEquals(new Lo3Datum(19980101), groep2.getHistorie().getIngangsdatumGeldigheid());
        Assert.assertEquals(new Lo3Datum(20000101), groep3.getHistorie().getIngangsdatumGeldigheid());
    }

    private void controleerBehandeldStapel(final MigratieStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> stapel) {
        Assert.assertEquals(4, stapel.size());

        final MigratieGroep<BrpBehandeldAlsNederlanderIndicatieInhoud> groep1 = stapel.get(0);
        final MigratieGroep<BrpBehandeldAlsNederlanderIndicatieInhoud> groep2 = stapel.get(1);
        final MigratieGroep<BrpBehandeldAlsNederlanderIndicatieInhoud> groep3 = stapel.get(2);
        final MigratieGroep<BrpBehandeldAlsNederlanderIndicatieInhoud> groep4 = stapel.get(3);

        Assert.assertEquals(Boolean.TRUE, groep1.getInhoud().getHeeftIndicatie());
        Assert.assertEquals(null, groep2.getInhoud().getHeeftIndicatie());
        Assert.assertEquals(null, groep3.getInhoud().getHeeftIndicatie());
        Assert.assertEquals(null, groep4.getInhoud().getHeeftIndicatie());

        Assert.assertEquals(new Lo3Datum(19940101), groep1.getHistorie().getIngangsdatumGeldigheid());
        Assert.assertEquals(new Lo3Datum(19960101), groep2.getHistorie().getIngangsdatumGeldigheid());
        Assert.assertEquals(new Lo3Datum(19980101), groep3.getHistorie().getIngangsdatumGeldigheid());
        Assert.assertEquals(new Lo3Datum(20000101), groep4.getHistorie().getIngangsdatumGeldigheid());
    }

    private void
            controleerVastgesteldStapel(final MigratieStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> stapel) {
        Assert.assertEquals(4, stapel.size());
        Assert.assertEquals(4, stapel.size());

        final MigratieGroep<BrpVastgesteldNietNederlanderIndicatieInhoud> groep1 = stapel.get(0);
        final MigratieGroep<BrpVastgesteldNietNederlanderIndicatieInhoud> groep2 = stapel.get(1);
        final MigratieGroep<BrpVastgesteldNietNederlanderIndicatieInhoud> groep3 = stapel.get(2);
        final MigratieGroep<BrpVastgesteldNietNederlanderIndicatieInhoud> groep4 = stapel.get(3);

        Assert.assertEquals(null, groep1.getInhoud().getHeeftIndicatie());
        Assert.assertEquals(null, groep2.getInhoud().getHeeftIndicatie());
        Assert.assertEquals(Boolean.TRUE, groep3.getInhoud().getHeeftIndicatie());
        Assert.assertEquals(null, groep4.getInhoud().getHeeftIndicatie());

        Assert.assertEquals(new Lo3Datum(19940101), groep1.getHistorie().getIngangsdatumGeldigheid());
        Assert.assertEquals(new Lo3Datum(19960101), groep2.getHistorie().getIngangsdatumGeldigheid());
        Assert.assertEquals(new Lo3Datum(19980101), groep3.getHistorie().getIngangsdatumGeldigheid());
        Assert.assertEquals(new Lo3Datum(20000101), groep4.getHistorie().getIngangsdatumGeldigheid());
    }

    private void controleerGeprivilegieerdeStapel(final MigratieStapel<BrpGeprivilegieerdeIndicatieInhoud> stapel) {
        Assert.assertNull(stapel);
    }

}
