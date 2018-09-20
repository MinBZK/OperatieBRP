/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Builder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.testutils.ReflectionUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PersoonConverteerderTest extends AbstractComponentTest {

    public static final long A_NUMMER = 1234567890L;
    public static final int BSN_NUMMER = 987654321;
    public static final String PREDICAAT = "JH";
    public static final String ADELLIJKE_TITEL = "PS";
    public static final String VOORNAMEN = "Piet Jan";
    public static final String VOORVOEGSEL = "van";
    public static final String GESLACHTSNAAM = "HorenZeggen";
    public static final int GEBOORTE_DATUM = 19800101;
    public static final String GEMEENTE_CODE = "00000";
    public static final String LAND_CODE = "6030";
    public static final String NAAMGEBRUIK = "E";
    @Inject
    private PersoonConverteerder persoonConverteerder;

    private static List<Lo3Categorie<Lo3PersoonInhoud>> buildPersoonStapel() {
        final List<Lo3Categorie<Lo3PersoonInhoud>> persoonCategorieen = new ArrayList<>();

        persoonCategorieen.add(Lo3Builder.createLo3Persoon(
            A_NUMMER,
            BSN_NUMMER,
            VOORNAMEN,
            PREDICAAT,
            VOORVOEGSEL,
            GESLACHTSNAAM,
            GEBOORTE_DATUM,
            GEMEENTE_CODE,
            LAND_CODE,
            "M",
            null,
            null,
            NAAMGEBRUIK,
            "O",
            GEBOORTE_DATUM,
            19800102,
            2));
        persoonCategorieen.add(Lo3Builder.createLo3Persoon(
            A_NUMMER,
            BSN_NUMMER,
            VOORNAMEN,
            ADELLIJKE_TITEL,
            VOORVOEGSEL,
            GESLACHTSNAAM,
            GEBOORTE_DATUM,
            GEMEENTE_CODE,
            LAND_CODE,
            "M",
            null,
            null,
            NAAMGEBRUIK,
            null,
            GEBOORTE_DATUM,
            19800202,
            1));
        persoonCategorieen.add(Lo3Builder.createLo3Persoon(
            A_NUMMER,
            BSN_NUMMER,
            VOORNAMEN,
            ADELLIJKE_TITEL,
            VOORVOEGSEL,
            GESLACHTSNAAM,
            GEBOORTE_DATUM,
            GEMEENTE_CODE,
            LAND_CODE,
            "V",
            null,
            null,
            NAAMGEBRUIK,
            null,
            20010301,
            20010401,
            0));
        return persoonCategorieen;
    }

    @Before
    public void setUp() {
        Logging.initContext();
    }

    @After
    public void tearDown() {
        Logging.destroyContext();
    }

    @Test
    
    public void testConverteer() {
        final List<Lo3Categorie<Lo3PersoonInhoud>> persoonCategorieen = buildPersoonStapel();

        final Lo3Stapel<Lo3PersoonInhoud> persoonStapel = new Lo3Stapel<>(persoonCategorieen);
        final TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();

        persoonConverteerder.converteer(persoonStapel, false, builder);

        checkGeboorteStapel((TussenStapel<BrpGeboorteInhoud>) ReflectionUtil.getField(builder, "geboorteStapel"));
        checkNaamgebruikStapel((TussenStapel<BrpNaamgebruikInhoud>) ReflectionUtil.getField(builder, "naamgebruikStapel"));
        checkSamengesteldeNaamStapel((TussenStapel<BrpSamengesteldeNaamInhoud>) ReflectionUtil.getField(builder, "samengesteldeNaamStapel"));
        checkIdentificatienummersStapel((TussenStapel<BrpIdentificatienummersInhoud>) ReflectionUtil.getField(
            builder,
            "identificatienummerStapel"));
        checkGeslachtsaanduidingStapel((TussenStapel<BrpGeslachtsaanduidingInhoud>) ReflectionUtil.getField(
            builder,
            "geslachtsaanduidingStapel"));
    }

    private static void checkGeboorteStapel(final TussenStapel<BrpGeboorteInhoud> tussenStapel) {
        Assert.assertNotNull(tussenStapel);
        Assert.assertEquals(3, tussenStapel.size());
        Assert.assertEquals(null, tussenStapel.get(0).getInhoud().getGemeenteCode());
    }

    public static void checkSamengesteldeNaamStapel(final TussenStapel<BrpSamengesteldeNaamInhoud> tussenStapel) {
        Assert.assertNotNull(tussenStapel);
        assertEquals(3, tussenStapel.size());
        assertNull(tussenStapel.get(0).getInhoud().getAdellijkeTitelCode());
        assertNotNull(tussenStapel.get(0).getInhoud().getPredicaatCode());

        assertNotNull(tussenStapel.get(1).getInhoud().getAdellijkeTitelCode());
        assertNull(tussenStapel.get(1).getInhoud().getPredicaatCode());
    }

    private void checkNaamgebruikStapel(final TussenStapel<BrpNaamgebruikInhoud> tussenStapel) {
        Assert.assertNotNull(tussenStapel);
        Assert.assertEquals(3, tussenStapel.size());
        Assert.assertEquals(BrpNaamgebruikCode.E, tussenStapel.get(0).getInhoud().getNaamgebruikCode());
    }

    private void checkIdentificatienummersStapel(final TussenStapel<BrpIdentificatienummersInhoud> tussenStapel) {
        Assert.assertNotNull(tussenStapel);
        Assert.assertEquals(3, tussenStapel.size());
        Assert.assertEquals(new Long(A_NUMMER), BrpLong.unwrap(tussenStapel.get(0).getInhoud().getAdministratienummer()));
    }

    private void checkGeslachtsaanduidingStapel(final TussenStapel<BrpGeslachtsaanduidingInhoud> tussenStapel) {
        Assert.assertNotNull(tussenStapel);
        Assert.assertEquals(3, tussenStapel.size());
        Assert.assertEquals(BrpGeslachtsaanduidingCode.MAN, tussenStapel.get(0).getInhoud().getGeslachtsaanduidingCode());
    }
}
