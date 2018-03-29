/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortMigratieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractVerblijfplaatsTest;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests voor {@link nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.VerblijfplaatsConverteerder}.
 */
public class VerblijfplaatsConverteerderTest extends AbstractVerblijfplaatsTest {

    private VerblijfplaatsConverteerder converteerder = new VerblijfplaatsConverteerder(new Lo3AttribuutConverteerder(new ConversietabelFactoryImpl()));
    private TussenPersoonslijstBuilder tussenPersoonslijstBuilder;

    @Before
    public void setUp() {
        tussenPersoonslijstBuilder = new TussenPersoonslijstBuilder();
    }

    @Test(expected = NullPointerException.class)
    public void testNullInputs1() {
        converteerder.converteer(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullInputs2() {
        converteerder.converteer(maakStapel(groep10Builder()), null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullInputs3() {
        converteerder.converteer(null, tussenPersoonslijstBuilder);
    }

    @Test
    @Definitie(Definities.DEF021)
    @Requirement({Requirements.CCA08_LB01, Requirements.CCA08_LB02})
    public void testPuntAdres() {
        // Setup
        final Lo3VerblijfplaatsInhoud.Builder builder = puntAdresBuilder();

        // Test of het een puntadres is
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> verblijfplaats = maakCategorie(builder);
        Assert.assertTrue(verblijfplaats.getInhoud().isPuntAdres());

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel = maakStapel(builder);
        // Run
        converteerder.converteer(verblijfplaatsStapel, tussenPersoonslijstBuilder);
        final TussenPersoonslijst tussenPersoonslijst = tussenPersoonslijstBuilder.build();
        final TussenStapel<BrpAdresInhoud> adresStapel = tussenPersoonslijst.getAdresStapel();
        final TussenStapel<BrpMigratieInhoud> migratieStapel = tussenPersoonslijst.getMigratieStapel();

        // Check
        Assert.assertEquals(1, adresStapel.size());
        final TussenGroep<BrpAdresInhoud> adres = adresStapel.get(0);
        Assert.assertNotNull(adres);
        Assert.assertFalse(adres.isInhoudelijkLeeg());
        final BrpAdresInhoud adresInhoud = adres.getInhoud();
        // Adreshouding
        assertAdreshouding(adresInhoud);

        // DEF021
        assertPuntadres(adresInhoud);
        Assert.assertNull(migratieStapel);
    }

    @Test
    @Definitie(Definities.DEF022)
    @Requirement({Requirements.CCA08_LB01, Requirements.CCA08_LB02})
    public void testStraatnaamPuntGeenPuntAdres() {
        final Lo3VerblijfplaatsInhoud.Builder builder = puntAdresBuilder();
        builder.postcode(POSTCODE);
        // Test of het geen puntadres is
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> verblijfplaats = maakCategorie(builder);
        Assert.assertFalse(verblijfplaats.getInhoud().isPuntAdres());

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel = maakStapel(builder);
        // Run
        converteerder.converteer(verblijfplaatsStapel, tussenPersoonslijstBuilder);
        final TussenPersoonslijst tussenPersoonslijst = tussenPersoonslijstBuilder.build();
        final TussenStapel<BrpAdresInhoud> adresStapel = tussenPersoonslijst.getAdresStapel();

        // Check
        Assert.assertEquals(1, adresStapel.size());
        final TussenGroep<BrpAdresInhoud> adres = adresStapel.get(0);
        Assert.assertNotNull(adres);
        Assert.assertFalse(adres.isInhoudelijkLeeg());
        final BrpAdresInhoud adresInhoud = adres.getInhoud();
        assertAdreshouding(adresInhoud);
        assertLo3BrpEquals(STRAATNAAM_PUNT, adresInhoud.getAfgekorteNaamOpenbareRuimte());
        assertLo3BrpEquals(POSTCODE, adresInhoud.getPostcode());
    }

    @Test
    @Definitie(Definities.DEF022)
    @Requirement({Requirements.CCA08_LB01, Requirements.CCA08_LB02})
    public void testNederlandsAdres() {
        final Lo3VerblijfplaatsInhoud.Builder builder = nederlandsAdresBuilder();

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel = maakStapel(builder);
        // Run
        converteerder.converteer(verblijfplaatsStapel, tussenPersoonslijstBuilder);
        final TussenPersoonslijst tussenPersoonslijst = tussenPersoonslijstBuilder.build();
        final TussenStapel<BrpAdresInhoud> adresStapel = tussenPersoonslijst.getAdresStapel();

        // Check
        Assert.assertEquals(1, adresStapel.size());
        final TussenGroep<BrpAdresInhoud> adres = adresStapel.get(0);
        Assert.assertNotNull(adres);
        Assert.assertFalse(adres.isInhoudelijkLeeg());
        final BrpAdresInhoud adresInhoud = adres.getInhoud();
        assertNederlandsAdres(adresInhoud, false);
    }

    @Test
    @Definitie(Definities.DEF022)
    @Requirement({Requirements.CCA08_LB01, Requirements.CCA08_LB02})
    public void testNederlandsAdresWoonadresPunt() {
        final Lo3VerblijfplaatsInhoud.Builder builder = nederlandsAdresBuilder();
        builder.woonplaatsnaam(Lo3String.wrap("."));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel = maakStapel(builder);
        // Run
        converteerder.converteer(verblijfplaatsStapel, tussenPersoonslijstBuilder);
        final TussenPersoonslijst tussenPersoonslijst = tussenPersoonslijstBuilder.build();
        final TussenStapel<BrpAdresInhoud> adresStapel = tussenPersoonslijst.getAdresStapel();

        // Check
        Assert.assertEquals(1, adresStapel.size());
        final TussenGroep<BrpAdresInhoud> adres = adresStapel.get(0);
        Assert.assertNotNull(adres);
        Assert.assertFalse(adres.isInhoudelijkLeeg());
        final BrpAdresInhoud adresInhoud = adres.getInhoud();
        assertNederlandsAdres(adresInhoud, true);
    }

    @Test
    @Requirement({Requirements.CCA08_LB01, Requirements.CCA08_LB02, Requirements.CCA08_LB04})
    public void testImmigratie() {
        // Setup
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel = maakStapel(immigratieBuilder());

        // Run
        converteerder.converteer(verblijfplaatsStapel, tussenPersoonslijstBuilder);
        final TussenPersoonslijst tussenPersoonslijst = tussenPersoonslijstBuilder.build();
        final TussenStapel<BrpAdresInhoud> adresStapel = tussenPersoonslijst.getAdresStapel();
        final TussenStapel<BrpMigratieInhoud> migratieStapel = tussenPersoonslijst.getMigratieStapel();

        // Check
        Assert.assertEquals(1, adresStapel.size());
        final TussenGroep<BrpAdresInhoud> adres = adresStapel.get(0);
        Assert.assertFalse(adres.isInhoudelijkLeeg());
        assertNederlandsAdres(adres.getInhoud(), false);

        Assert.assertNotNull(migratieStapel);
        Assert.assertEquals(1, migratieStapel.size());
        final TussenGroep<BrpMigratieInhoud> migratie = migratieStapel.get(0);
        Assert.assertFalse(migratie.isInhoudelijkLeeg());

        final BrpMigratieInhoud migratieInhoud = migratie.getInhoud();
        Assert.assertEquals(BrpSoortMigratieCode.IMMIGRATIE, migratieInhoud.getSoortMigratieCode());
        Assert.assertEquals(BRP_LAND_OF_GEBIED_CODE_IMMIGRATIE, migratieInhoud.getLandOfGebiedCode());
        Assert.assertEquals(null, migratieInhoud.getAangeverMigratieCode());
        Assert.assertEquals(null, migratieInhoud.getRedenWijzigingMigratieCode());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel1());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel2());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel3());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel4());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel5());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel6());

        Assert.assertEquals(LO3_DATUM_VESTIGING_IN_NL, migratie.getHistorie().getIngangsdatumGeldigheid());
    }

    @Test
    @Requirement({Requirements.CCA08_LB01, Requirements.CCA08_LB02, Requirements.CCA08_LB04})
    public void testImmigratieEerstLegeRegel() {
        // Setup
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> nlVerblijfplaats = maakCategorie(nederlandsAdresBuilder());
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> immigratie = maakCategorie(immigratieBuilder());
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel = Lo3StapelHelper.lo3Stapel(nlVerblijfplaats, immigratie);

        // Run
        converteerder.converteer(verblijfplaatsStapel, tussenPersoonslijstBuilder);
        final TussenPersoonslijst tussenPersoonslijst = tussenPersoonslijstBuilder.build();
        final TussenStapel<BrpAdresInhoud> adresStapel = tussenPersoonslijst.getAdresStapel();
        final TussenStapel<BrpMigratieInhoud> migratieStapel = tussenPersoonslijst.getMigratieStapel();

        // Check
        Assert.assertEquals(2, adresStapel.size());
        final TussenGroep<BrpAdresInhoud> adres = adresStapel.get(0);
        Assert.assertFalse(adres.isInhoudelijkLeeg());
        assertNederlandsAdres(adres.getInhoud(), false);

        Assert.assertNotNull(migratieStapel);
        Assert.assertEquals(1, migratieStapel.size());
        final TussenGroep<BrpMigratieInhoud> migratie = migratieStapel.get(0);
        Assert.assertFalse(migratie.isInhoudelijkLeeg());

        final BrpMigratieInhoud migratieInhoud = migratie.getInhoud();
        Assert.assertEquals(BrpSoortMigratieCode.IMMIGRATIE, migratieInhoud.getSoortMigratieCode());
        Assert.assertEquals(BRP_LAND_OF_GEBIED_CODE_IMMIGRATIE, migratieInhoud.getLandOfGebiedCode());
        Assert.assertEquals(null, migratieInhoud.getAangeverMigratieCode());
        Assert.assertEquals(null, migratieInhoud.getRedenWijzigingMigratieCode());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel1());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel2());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel3());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel4());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel5());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel6());

        Assert.assertEquals(LO3_DATUM_VESTIGING_IN_NL, migratie.getHistorie().getIngangsdatumGeldigheid());
    }

    @Test
    @Requirement({Requirements.CCA08_LB01, Requirements.CCA08_LB02, Requirements.CCA08_LB04})
    public void testImmigratieEerstMigratieRegelGevuldTweedeLeeg() {
        // Setup
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> nlVerblijfplaats = maakCategorie(nederlandsAdresBuilder());
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> immigratie = maakCategorie(immigratieBuilder());
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel = Lo3StapelHelper.lo3Stapel(immigratie, nlVerblijfplaats);

        // Run
        converteerder.converteer(verblijfplaatsStapel, tussenPersoonslijstBuilder);
        final TussenPersoonslijst tussenPersoonslijst = tussenPersoonslijstBuilder.build();
        final TussenStapel<BrpAdresInhoud> adresStapel = tussenPersoonslijst.getAdresStapel();
        final TussenStapel<BrpMigratieInhoud> migratieStapel = tussenPersoonslijst.getMigratieStapel();

        // Check
        Assert.assertEquals(2, adresStapel.size());
        final TussenGroep<BrpAdresInhoud> adres = adresStapel.get(0);
        Assert.assertFalse(adres.isInhoudelijkLeeg());
        assertNederlandsAdres(adres.getInhoud(), false);

        Assert.assertNotNull(migratieStapel);
        Assert.assertEquals(2, migratieStapel.size());
        final TussenGroep<BrpMigratieInhoud> migratie = migratieStapel.get(0);
        Assert.assertFalse(migratie.isInhoudelijkLeeg());
        final TussenGroep<BrpMigratieInhoud> migratieLegeRij = migratieStapel.get(1);
        Assert.assertTrue(migratieLegeRij.isInhoudelijkLeeg());

        final BrpMigratieInhoud migratieInhoud = migratie.getInhoud();
        Assert.assertEquals(BrpSoortMigratieCode.IMMIGRATIE, migratieInhoud.getSoortMigratieCode());
        Assert.assertEquals(BRP_LAND_OF_GEBIED_CODE_IMMIGRATIE, migratieInhoud.getLandOfGebiedCode());
        Assert.assertEquals(null, migratieInhoud.getAangeverMigratieCode());
        Assert.assertEquals(null, migratieInhoud.getRedenWijzigingMigratieCode());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel1());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel2());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel3());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel4());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel5());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel6());

        Assert.assertEquals(LO3_DATUM_VESTIGING_IN_NL, migratie.getHistorie().getIngangsdatumGeldigheid());
    }

    @Test
    @Definitie(Definities.DEF034)
    @Requirement(Requirements.CCA08_LB03)
    public void testEmigratieGevolgdDoorLegeRegel() {
        // Setup
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> nlVerblijfplaats = maakCategorie(nederlandsAdresBuilder());
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> emigratie = maakCategorie(emigratieBuilder());
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel = Lo3StapelHelper.lo3Stapel(emigratie, nlVerblijfplaats);

        // Run
        converteerder.converteer(verblijfplaatsStapel, tussenPersoonslijstBuilder);
        final TussenPersoonslijst tussenPersoonslijst = tussenPersoonslijstBuilder.build();
        final TussenStapel<BrpAdresInhoud> adresStapel = tussenPersoonslijst.getAdresStapel();
        final TussenStapel<BrpMigratieInhoud> migratieStapel = tussenPersoonslijst.getMigratieStapel();

        // Check
        Assert.assertEquals(2, adresStapel.size());
        final TussenGroep<BrpAdresInhoud> adres = adresStapel.get(0);
        Assert.assertFalse(adres.isInhoudelijkLeeg());

        final BrpAdresInhoud adresInhoud = adres.getInhoud();
        Assert.assertEquals(BrpSoortAdresCode.W, adresInhoud.getSoortAdresCode());
        assertLo3BrpEquals(ADRES_BUITENLAND_1, adresInhoud.getBuitenlandsAdresRegel1());
        assertLo3BrpEquals(ADRES_BUITENLAND_2, adresInhoud.getBuitenlandsAdresRegel2());
        assertLo3BrpEquals(ADRES_BUITENLAND_3, adresInhoud.getBuitenlandsAdresRegel3());
        Assert.assertNull(adresInhoud.getBuitenlandsAdresRegel4());
        Assert.assertNull(adresInhoud.getBuitenlandsAdresRegel5());
        Assert.assertNull(adresInhoud.getBuitenlandsAdresRegel6());
        Assert.assertEquals(BRP_LAND_OF_GEBIED_CODE_EMIGRATIE, adresInhoud.getLandOfGebiedCode());

        Assert.assertNotNull(migratieStapel);
        Assert.assertEquals(2, migratieStapel.size());
        final TussenGroep<BrpMigratieInhoud> migratie = migratieStapel.get(0);
        Assert.assertFalse(migratie.isInhoudelijkLeeg());
        final TussenGroep<BrpMigratieInhoud> migratieLegeRegel = migratieStapel.get(1);
        Assert.assertTrue(migratieLegeRegel.isInhoudelijkLeeg());

        final BrpMigratieInhoud migratieInhoud = migratie.getInhoud();
        Assert.assertEquals(BrpSoortMigratieCode.EMIGRATIE, migratieInhoud.getSoortMigratieCode());
        Assert.assertEquals(BRP_LAND_OF_GEBIED_CODE_EMIGRATIE, migratieInhoud.getLandOfGebiedCode());
        Assert.assertEquals(BRP_AANGEVER_ADRESHOUDING_CODE_INGESCHREVENE, migratieInhoud.getAangeverMigratieCode());
        Assert.assertEquals(BRP_REDEN_WIJZIGING_ADRES_PERSOON, migratieInhoud.getRedenWijzigingMigratieCode());
        assertLo3BrpEquals(ADRES_BUITENLAND_1, migratieInhoud.getBuitenlandsAdresRegel1());
        assertLo3BrpEquals(ADRES_BUITENLAND_2, migratieInhoud.getBuitenlandsAdresRegel2());
        assertLo3BrpEquals(ADRES_BUITENLAND_3, migratieInhoud.getBuitenlandsAdresRegel3());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel4());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel5());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel6());

        Assert.assertEquals(LO3_DATUM_VERTREK_UIT_NL, migratie.getHistorie().getIngangsdatumGeldigheid());
    }

    @Test
    @Definitie(Definities.DEF034)
    @Requirement(Requirements.CCA08_LB03)
    public void testEmigratie() {
        // Setup
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel = maakStapel(emigratieBuilder());

        // Run
        converteerder.converteer(verblijfplaatsStapel, tussenPersoonslijstBuilder);
        final TussenPersoonslijst tussenPersoonslijst = tussenPersoonslijstBuilder.build();
        final TussenStapel<BrpAdresInhoud> adresStapel = tussenPersoonslijst.getAdresStapel();
        final TussenStapel<BrpMigratieInhoud> migratieStapel = tussenPersoonslijst.getMigratieStapel();

        // Check
        Assert.assertEquals(1, adresStapel.size());
        final TussenGroep<BrpAdresInhoud> adres = adresStapel.get(0);
        Assert.assertFalse(adres.isInhoudelijkLeeg());

        final BrpAdresInhoud adresInhoud = adres.getInhoud();
        Assert.assertEquals(BrpSoortAdresCode.W, adresInhoud.getSoortAdresCode());
        assertLo3BrpEquals(ADRES_BUITENLAND_1, adresInhoud.getBuitenlandsAdresRegel1());
        assertLo3BrpEquals(ADRES_BUITENLAND_2, adresInhoud.getBuitenlandsAdresRegel2());
        assertLo3BrpEquals(ADRES_BUITENLAND_3, adresInhoud.getBuitenlandsAdresRegel3());
        Assert.assertNull(adresInhoud.getBuitenlandsAdresRegel4());
        Assert.assertNull(adresInhoud.getBuitenlandsAdresRegel5());
        Assert.assertNull(adresInhoud.getBuitenlandsAdresRegel6());
        Assert.assertEquals(BRP_LAND_OF_GEBIED_CODE_EMIGRATIE, adresInhoud.getLandOfGebiedCode());

        Assert.assertNotNull(migratieStapel);
        Assert.assertEquals(1, migratieStapel.size());
        final TussenGroep<BrpMigratieInhoud> migratie = migratieStapel.get(0);
        Assert.assertFalse(migratie.isInhoudelijkLeeg());

        final BrpMigratieInhoud migratieInhoud = migratie.getInhoud();
        Assert.assertEquals(BrpSoortMigratieCode.EMIGRATIE, migratieInhoud.getSoortMigratieCode());
        Assert.assertEquals(BRP_LAND_OF_GEBIED_CODE_EMIGRATIE, migratieInhoud.getLandOfGebiedCode());
        Assert.assertEquals(BRP_AANGEVER_ADRESHOUDING_CODE_INGESCHREVENE, migratieInhoud.getAangeverMigratieCode());
        Assert.assertEquals(BRP_REDEN_WIJZIGING_ADRES_PERSOON, migratieInhoud.getRedenWijzigingMigratieCode());
        assertLo3BrpEquals(ADRES_BUITENLAND_1, migratieInhoud.getBuitenlandsAdresRegel1());
        assertLo3BrpEquals(ADRES_BUITENLAND_2, migratieInhoud.getBuitenlandsAdresRegel2());
        assertLo3BrpEquals(ADRES_BUITENLAND_3, migratieInhoud.getBuitenlandsAdresRegel3());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel4());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel5());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel6());

        Assert.assertEquals(LO3_DATUM_VERTREK_UIT_NL, migratie.getHistorie().getIngangsdatumGeldigheid());
    }

    @Test
    @Definitie(Definities.DEF033)
    @Requirement(Requirements.CCA08_LB03)
    public void testEmigratieAlleenAdresRegel2() {
        // Setup
        final Lo3VerblijfplaatsInhoud.Builder builder = emigratieBuilder();
        builder.adresBuitenland1(null);
        builder.adresBuitenland3(null);
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel = maakStapel(builder);

        // Run
        converteerder.converteer(verblijfplaatsStapel, tussenPersoonslijstBuilder);
        final TussenPersoonslijst tussenPersoonslijst = tussenPersoonslijstBuilder.build();
        final TussenStapel<BrpAdresInhoud> adresStapel = tussenPersoonslijst.getAdresStapel();
        final TussenStapel<BrpMigratieInhoud> migratieStapel = tussenPersoonslijst.getMigratieStapel();

        // Check
        Assert.assertEquals(1, adresStapel.size());
        final TussenGroep<BrpAdresInhoud> adres = adresStapel.get(0);
        Assert.assertFalse(adres.isInhoudelijkLeeg());

        final BrpAdresInhoud adresInhoud = adres.getInhoud();
        Assert.assertEquals(BrpSoortAdresCode.W, adresInhoud.getSoortAdresCode());
        assertLo3BrpEquals(ADRES_BUITENLAND_2, adresInhoud.getBuitenlandsAdresRegel1());
        Assert.assertNull(adresInhoud.getBuitenlandsAdresRegel2());
        Assert.assertNull(adresInhoud.getBuitenlandsAdresRegel3());
        Assert.assertNull(adresInhoud.getBuitenlandsAdresRegel4());
        Assert.assertNull(adresInhoud.getBuitenlandsAdresRegel5());
        Assert.assertNull(adresInhoud.getBuitenlandsAdresRegel6());
        Assert.assertEquals(BRP_LAND_OF_GEBIED_CODE_EMIGRATIE, adresInhoud.getLandOfGebiedCode());

        Assert.assertNotNull(migratieStapel);
        Assert.assertEquals(1, migratieStapel.size());
        final TussenGroep<BrpMigratieInhoud> migratie = migratieStapel.get(0);
        Assert.assertFalse(migratie.isInhoudelijkLeeg());

        final BrpMigratieInhoud migratieInhoud = migratie.getInhoud();
        Assert.assertEquals(BrpSoortMigratieCode.EMIGRATIE, migratieInhoud.getSoortMigratieCode());
        Assert.assertEquals(BRP_LAND_OF_GEBIED_CODE_EMIGRATIE, migratieInhoud.getLandOfGebiedCode());
        Assert.assertEquals(BRP_AANGEVER_ADRESHOUDING_CODE_INGESCHREVENE, migratieInhoud.getAangeverMigratieCode());
        Assert.assertEquals(BRP_REDEN_WIJZIGING_ADRES_PERSOON, migratieInhoud.getRedenWijzigingMigratieCode());
        assertLo3BrpEquals(ADRES_BUITENLAND_2, migratieInhoud.getBuitenlandsAdresRegel1());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel2());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel3());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel4());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel5());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel6());

        Assert.assertEquals(LO3_DATUM_VERTREK_UIT_NL, migratie.getHistorie().getIngangsdatumGeldigheid());
    }

    @Test
    @Definitie(Definities.DEF034)
    @Requirement(Requirements.CCA08_LB03)
    public void testEmigratieMetOnjuist() {
        // Setup
        final Lo3VerblijfplaatsInhoud.Builder builder = emigratieBuilder();
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> verblijfplaatsCategorie = maakCategorie(builder);

        builder.vertrekUitNederland(LO3_DATUM_VESTIGING_IN_NL);
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> verblijfplaatsCategorieHistorie =
                Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(2),
                        Lo3StapelHelper.lo3His("O", LO3_DATUM_INSCHRIJVING.getIntegerWaarde(), LO3_DATUM_INSCHRIJVING.getIntegerWaarde()),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 1));

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel =
                Lo3StapelHelper.lo3Stapel(verblijfplaatsCategorieHistorie, verblijfplaatsCategorie);
        // Run
        converteerder.converteer(verblijfplaatsStapel, tussenPersoonslijstBuilder);
        final TussenPersoonslijst tussenPersoonslijst = tussenPersoonslijstBuilder.build();
        final TussenStapel<BrpAdresInhoud> adresStapel = tussenPersoonslijst.getAdresStapel();
        final TussenStapel<BrpMigratieInhoud> migratieStapel = tussenPersoonslijst.getMigratieStapel();

        // Check
        Assert.assertEquals(2, adresStapel.size());
        final TussenGroep<BrpAdresInhoud> adres = adresStapel.get(0);
        Assert.assertFalse(adres.isInhoudelijkLeeg());

        final BrpAdresInhoud adresInhoud = adres.getInhoud();
        Assert.assertEquals(BrpSoortAdresCode.W, adresInhoud.getSoortAdresCode());
        assertLo3BrpEquals(ADRES_BUITENLAND_1, adresInhoud.getBuitenlandsAdresRegel1());
        assertLo3BrpEquals(ADRES_BUITENLAND_2, adresInhoud.getBuitenlandsAdresRegel2());
        assertLo3BrpEquals(ADRES_BUITENLAND_3, adresInhoud.getBuitenlandsAdresRegel3());
        Assert.assertNull(adresInhoud.getBuitenlandsAdresRegel4());
        Assert.assertNull(adresInhoud.getBuitenlandsAdresRegel5());
        Assert.assertNull(adresInhoud.getBuitenlandsAdresRegel6());
        Assert.assertEquals(BRP_LAND_OF_GEBIED_CODE_EMIGRATIE, adresInhoud.getLandOfGebiedCode());

        Assert.assertNotNull(migratieStapel);
        Assert.assertEquals(1, migratieStapel.size());
        final TussenGroep<BrpMigratieInhoud> migratie = migratieStapel.get(0);
        Assert.assertFalse(migratie.isInhoudelijkLeeg());

        final BrpMigratieInhoud migratieInhoud = migratie.getInhoud();
        Assert.assertEquals(BrpSoortMigratieCode.EMIGRATIE, migratieInhoud.getSoortMigratieCode());
        Assert.assertEquals(BRP_LAND_OF_GEBIED_CODE_EMIGRATIE, migratieInhoud.getLandOfGebiedCode());
        Assert.assertEquals(BRP_AANGEVER_ADRESHOUDING_CODE_INGESCHREVENE, migratieInhoud.getAangeverMigratieCode());
        Assert.assertEquals(BRP_REDEN_WIJZIGING_ADRES_PERSOON, migratieInhoud.getRedenWijzigingMigratieCode());
        assertLo3BrpEquals(ADRES_BUITENLAND_1, migratieInhoud.getBuitenlandsAdresRegel1());
        assertLo3BrpEquals(ADRES_BUITENLAND_2, migratieInhoud.getBuitenlandsAdresRegel2());
        assertLo3BrpEquals(ADRES_BUITENLAND_3, migratieInhoud.getBuitenlandsAdresRegel3());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel4());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel5());
        Assert.assertNull(migratieInhoud.getBuitenlandsAdresRegel6());

        Assert.assertEquals(LO3_DATUM_VERTREK_UIT_NL, migratie.getHistorie().getIngangsdatumGeldigheid());
    }

    private Lo3VerblijfplaatsInhoud.Builder verplichteVeldenBuilder() {
        final Lo3VerblijfplaatsInhoud.Builder builder = new Lo3VerblijfplaatsInhoud.Builder();
        // Groep 9
        builder.datumInschrijving(LO3_DATUM_INSCHRIJVING);
        builder.gemeenteInschrijving(LO3_GEMEENTE_CODE);
        // Groep 72
        builder.aangifteAdreshouding(LO3_AANGIFTE_ADRESHOUDING_INGESCHREVENE);
        return builder;
    }

    private Lo3VerblijfplaatsInhoud.Builder groep10Builder() {
        final Lo3VerblijfplaatsInhoud.Builder builder = verplichteVeldenBuilder();
        // Groep 10
        builder.functieAdres(LO3_FUNCTIE_ADRES_B);
        builder.gemeenteDeel(GEMEENTE_DEEL);
        builder.aanvangAdreshouding(LO3_DATUM_AANVANG_ADRESHOUDING);
        return builder;
    }

    private Lo3VerblijfplaatsInhoud.Builder puntAdresBuilder() {
        final Lo3VerblijfplaatsInhoud.Builder builder = groep10Builder();
        builder.gemeenteDeel(null);
        builder.straatnaam(STRAATNAAM_PUNT);

        return builder;
    }

    private Lo3VerblijfplaatsInhoud.Builder nederlandsAdresBuilder() {
        final Lo3VerblijfplaatsInhoud.Builder builder = groep10Builder();
        builder.straatnaam(STRAATNAAM);
        builder.naamOpenbareRuimte(NAAM_OPENBARE_RUIMTE);
        builder.huisnummer(LO3_HUISNUMMER);
        builder.huisletter(HUISLETTER);
        builder.huisnummertoevoeging(HUISNUMMER_TOEVOEGING);
        builder.aanduidingHuisnummer(LO3_AANDUIDING_HUISNUMMER);
        builder.postcode(POSTCODE);
        builder.woonplaatsnaam(WOONPLAATSNAAM);
        builder.identificatiecodeVerblijfplaats(IDENTCODE_ADRESSEERBAAR_OBJECT);
        builder.identificatiecodeNummeraanduiding(IDENTCODE_NUMMERAANDUIDING);
        builder.locatieBeschrijving(LOCATIE_OMSCHRIJVING);
        return builder;
    }

    private Lo3VerblijfplaatsInhoud.Builder immigratieBuilder() {
        final Lo3VerblijfplaatsInhoud.Builder builder = nederlandsAdresBuilder();
        builder.landVanwaarIngeschreven(LO3_LAND_CODE_IMMIGRATIE);
        builder.vestigingInNederland(LO3_DATUM_VESTIGING_IN_NL);
        return builder;
    }

    private Lo3VerblijfplaatsInhoud.Builder emigratieBuilder() {
        final Lo3VerblijfplaatsInhoud.Builder builder = verplichteVeldenBuilder();
        builder.indicatieDocument(LO3_INDICATIE_DOCUMENT);
        builder.landAdresBuitenland(LO3_LAND_CODE_EMIGRATIE);
        builder.vertrekUitNederland(LO3_DATUM_VERTREK_UIT_NL);
        builder.adresBuitenland1(ADRES_BUITENLAND_1);
        builder.adresBuitenland2(ADRES_BUITENLAND_2);
        builder.adresBuitenland3(ADRES_BUITENLAND_3);
        return builder;
    }

    private Lo3Categorie<Lo3VerblijfplaatsInhoud> maakCategorie(final Lo3VerblijfplaatsInhoud.Builder builder) {
        return Lo3StapelHelper.lo3Cat(
                builder.build(),
                Lo3StapelHelper.lo3Akt(1),
                Lo3StapelHelper.lo3His(LO3_DATUM_INSCHRIJVING.getIntegerWaarde()),
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 0));
    }

    private Lo3Stapel<Lo3VerblijfplaatsInhoud> maakStapel(final Lo3VerblijfplaatsInhoud.Builder builder) {
        return Lo3StapelHelper.lo3Stapel(maakCategorie(builder));
    }

    private void assertPuntadres(final BrpAdresInhoud adresInhoud) {
        // Controleer dat alle velden leeg zijn behalve `Gemeente` en `Land
        Assert.assertNull(adresInhoud.getIdentificatiecodeAdresseerbaarObject());
        Assert.assertNull(adresInhoud.getIdentificatiecodeNummeraanduiding());
        Assert.assertEquals(BRP_GEMEENTE_CODE, adresInhoud.getGemeenteCode());
        Assert.assertNull(adresInhoud.getNaamOpenbareRuimte());
        Assert.assertNull(adresInhoud.getAfgekorteNaamOpenbareRuimte());
        Assert.assertNull(adresInhoud.getGemeentedeel());
        Assert.assertNull(adresInhoud.getHuisnummer());
        Assert.assertNull(adresInhoud.getHuisletter());
        Assert.assertNull(adresInhoud.getHuisnummertoevoeging());
        Assert.assertNull(adresInhoud.getPostcode());
        Assert.assertNull(adresInhoud.getWoonplaatsnaam());
        Assert.assertNull(adresInhoud.getLocatieTovAdres());
        Assert.assertNull(adresInhoud.getLocatieOmschrijving());
        Assert.assertEquals(BRP_LAND_OF_GEBIED_CODE_NL, adresInhoud.getLandOfGebiedCode());
        Assert.assertNull(adresInhoud.getIndicatiePersoonAangetroffenOpAdres());
    }

    private void assertAdreshouding(final BrpAdresInhoud adresInhoud) {
        Assert.assertEquals(BRP_SOORT_ADRES_CODE_B, adresInhoud.getSoortAdresCode());
        Assert.assertEquals(BRP_REDEN_WIJZIGING_ADRES_PERSOON, adresInhoud.getRedenWijzigingAdresCode());
        Assert.assertEquals(BRP_AANGEVER_ADRESHOUDING_CODE_INGESCHREVENE, adresInhoud.getAangeverAdreshoudingCode());
        Assert.assertEquals(BRP_DATUM_AANVANG_ADRESHOUDING, adresInhoud.getDatumAanvangAdreshouding());
    }

    private void assertNederlandsAdres(final BrpAdresInhoud adresInhoud, final boolean isWoonplaatsnaamPunt) {
        assertAdreshouding(adresInhoud);
        assertLo3BrpEquals(IDENTCODE_ADRESSEERBAAR_OBJECT, adresInhoud.getIdentificatiecodeAdresseerbaarObject());
        assertLo3BrpEquals(IDENTCODE_NUMMERAANDUIDING, adresInhoud.getIdentificatiecodeNummeraanduiding());
        Assert.assertEquals(BRP_GEMEENTE_CODE, adresInhoud.getGemeenteCode());
        assertLo3BrpEquals(NAAM_OPENBARE_RUIMTE, adresInhoud.getNaamOpenbareRuimte());
        assertLo3BrpEquals(STRAATNAAM, adresInhoud.getAfgekorteNaamOpenbareRuimte());
        assertLo3BrpEquals(GEMEENTE_DEEL, adresInhoud.getGemeentedeel());
        Assert.assertEquals(Lo3Integer.unwrap(HUISNUMMER), BrpInteger.unwrap(adresInhoud.getHuisnummer()));
        assertLo3BrpEquals(HUISLETTER, adresInhoud.getHuisletter());
        assertLo3BrpEquals(HUISNUMMER_TOEVOEGING, adresInhoud.getHuisnummertoevoeging());
        assertLo3BrpEquals(POSTCODE, adresInhoud.getPostcode());
        if (isWoonplaatsnaamPunt) {
            Assert.assertNull(adresInhoud.getWoonplaatsnaam());
        } else {
            assertLo3BrpEquals(WOONPLAATSNAAM, adresInhoud.getWoonplaatsnaam());
        }
        Assert.assertEquals(BRP_AANDUIDING_HUISNUMMER, adresInhoud.getLocatieTovAdres());
        assertLo3BrpEquals(LOCATIE_OMSCHRIJVING, adresInhoud.getLocatieOmschrijving());
        // Controleer dat Nederland als default is opgenomen
        Assert.assertEquals(BRP_LAND_OF_GEBIED_CODE_NL, adresInhoud.getLandOfGebiedCode());
    }
}
