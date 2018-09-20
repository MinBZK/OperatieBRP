/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.AbstractComponentTest;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpWijzeGebruikGeslachtsnaamCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAanschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Builder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.testutils.ReflectionUtil;

import org.junit.Assert;
import org.junit.Test;

public class PersoonConverteerderTest extends AbstractComponentTest {

    @Inject
    private PersoonConverteerder persoonConverteerder;

    private static List<Lo3Categorie<Lo3PersoonInhoud>> buildPersoonStapel() {
        final List<Lo3Categorie<Lo3PersoonInhoud>> persoonCategorieen =
                new ArrayList<Lo3Categorie<Lo3PersoonInhoud>>();

        persoonCategorieen.add(Lo3Builder.createLo3Persoon(1234567890L, 987654321L, "Piet Jan", "JH", "van",
                "HorenZeggen", 19800101, "00000", "6030", "M", "E", null, null, "O", 19800101, 19800102));
        persoonCategorieen.add(Lo3Builder.createLo3Persoon(1234567890L, 987654321L, "Piet Jan", "PS", "van",
                "HorenZeggen", 19800101, "00000", "6030", "M", "E", null, null, null, 19800101, 19800202));
        persoonCategorieen.add(Lo3Builder.createLo3Persoon(1234567890L, 987654321L, "Piet Jan", "PS", "van",
                "HorenZeggen", 19800101, "00000", "6030", "V", "E", null, null, null, 20010301, 20010401));
        return persoonCategorieen;
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testConverteer() throws InputValidationException {
        final List<Lo3Categorie<Lo3PersoonInhoud>> persoonCategorieen = buildPersoonStapel();

        final Lo3Stapel<Lo3PersoonInhoud> persoonStapel = new Lo3Stapel<Lo3PersoonInhoud>(persoonCategorieen);
        final MigratiePersoonslijstBuilder builder = new MigratiePersoonslijstBuilder();

        persoonConverteerder.converteer(persoonStapel, builder);

        checkGeboorteStapel((MigratieStapel<BrpGeboorteInhoud>) ReflectionUtil.getField(builder, "geboorteStapel"));
        checkAanschrijvingStapel((MigratieStapel<BrpAanschrijvingInhoud>) ReflectionUtil.getField(builder,
                "aanschrijvingStapel"));
        checkSamengesteldeNaamStapel((MigratieStapel<BrpSamengesteldeNaamInhoud>) ReflectionUtil.getField(builder,
                "samengesteldeNaamStapel"));
        checkIdentificatienummersStapel((MigratieStapel<BrpIdentificatienummersInhoud>) ReflectionUtil.getField(
                builder, "identificatienummerStapel"));
        checkGeslachtsaanduidingStapel((MigratieStapel<BrpGeslachtsaanduidingInhoud>) ReflectionUtil.getField(
                builder, "geslachtsaanduidingStapel"));
    }

    private static void checkGeboorteStapel(final MigratieStapel<BrpGeboorteInhoud> migratieStapel) {
        Assert.assertNotNull(migratieStapel);
        Assert.assertEquals(3, migratieStapel.size());
        Assert.assertEquals(null, migratieStapel.get(0).getInhoud().getGemeenteCode());
    }

    public static void checkSamengesteldeNaamStapel(final MigratieStapel<BrpSamengesteldeNaamInhoud> migratieStapel) {
        Assert.assertNotNull(migratieStapel);
        assertEquals(3, migratieStapel.size());
        assertNull(migratieStapel.get(0).getInhoud().getAdellijkeTitelCode());
        assertNotNull(migratieStapel.get(0).getInhoud().getPredikaatCode());

        assertNotNull(migratieStapel.get(1).getInhoud().getAdellijkeTitelCode());
        assertNull(migratieStapel.get(1).getInhoud().getPredikaatCode());
    }

    private void checkAanschrijvingStapel(final MigratieStapel<BrpAanschrijvingInhoud> migratieStapel) {
        Assert.assertNotNull(migratieStapel);
        Assert.assertEquals(3, migratieStapel.size());
        Assert.assertEquals(BrpWijzeGebruikGeslachtsnaamCode.E, migratieStapel.get(0).getInhoud()
                .getWijzeGebruikGeslachtsnaamCode());
    }

    private void checkIdentificatienummersStapel(final MigratieStapel<BrpIdentificatienummersInhoud> migratieStapel) {
        Assert.assertNotNull(migratieStapel);
        Assert.assertEquals(3, migratieStapel.size());
        Assert.assertEquals(new Long(1234567890L), migratieStapel.get(0).getInhoud().getAdministratienummer());
    }

    private void checkGeslachtsaanduidingStapel(final MigratieStapel<BrpGeslachtsaanduidingInhoud> migratieStapel) {
        Assert.assertNotNull(migratieStapel);
        Assert.assertEquals(3, migratieStapel.size());
        Assert.assertEquals(BrpGeslachtsaanduidingCode.MAN, migratieStapel.get(0).getInhoud()
                .getGeslachtsaanduiding());
    }
}
