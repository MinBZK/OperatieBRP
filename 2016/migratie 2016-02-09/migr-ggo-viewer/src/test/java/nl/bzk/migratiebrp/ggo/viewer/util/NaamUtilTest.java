/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingNaamgebruikCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.ggo.viewer.Lo3PersoonslijstTestHelper;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import org.junit.Test;

public class NaamUtilTest {

    @Test
    public void createVolledigeNaam() throws Exception {
        final List<Lo3Persoonslijst> lo3Persoonslijsten = Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten("2vk_persoon.xls", new FoutMelder());

        assertNotNull("Er zouden lo3 persoonslijsten moeten zijn.", lo3Persoonslijsten);
        assertTrue("Er zou minstens 1 persoonslijst moeten zijn", lo3Persoonslijsten.size() > 0);
        for (final Lo3Persoonslijst lo3Persoonslijst : lo3Persoonslijsten) {
            final String volledigeNaam = NaamUtil.createVolledigeNaam(lo3Persoonslijst);
            assertTrue("Naam zou Betty Boop moeten zijn.", "Betty Boop".equals(volledigeNaam));
        }
    }

    @Test
    public void createVolledigeNaamEmptyVoornamen() {
        final String volledigeNaam = NaamUtil.createVolledigeNaam(createLo3Persoonslijst("", "van", "Veen"));
        assertEquals("Naam zou van Veen moeten zijn.", "van Veen", volledigeNaam);
    }

    @Test
    public void createVolledigeNaamEmptyVoorvoegsel() {
        final String volledigeNaam = NaamUtil.createVolledigeNaam(createLo3Persoonslijst("Joepie", null, "Veen"));
        assertEquals("Naam zou van Veen moeten zijn.", "Joepie Veen", volledigeNaam);
    }

    @Test
    public void createVolledigeNaamEmptyGeslachtsnaam() {
        final String volledigeNaam = NaamUtil.createVolledigeNaam(createLo3Persoonslijst("Joepie", "de", null));
        assertEquals("Naam zou van Veen moeten zijn.", "Joepie de", volledigeNaam);
    }

    private Lo3Persoonslijst createLo3Persoonslijst(final String voornamen, final String voorvoegsel, final String geslachtsnaam) {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        final List<Lo3Categorie<Lo3PersoonInhoud>> persoonInhoud = new ArrayList<>();

        final Lo3PersoonInhoud inhoud =
                new Lo3PersoonInhoud(
                    null,
                    null,
                    Lo3String.wrap(voornamen),
                    null,
                    Lo3String.wrap(voorvoegsel),
                    Lo3String.wrap(geslachtsnaam),
                    null,
                    null,
                    Lo3LandCode.NEDERLAND,
                    Lo3GeslachtsaanduidingEnum.MAN.asElement(),
                    null,
                    null,
                    Lo3AanduidingNaamgebruikCodeEnum.EIGEN_GESLACHTSNAAM.asElement());
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(20121212), new Lo3Datum(20121212));
        final Lo3Documentatie documentatie = new Lo3Documentatie(0, null, null, null, null, null, null, null);

        persoonInhoud.add(new Lo3Categorie<>(inhoud, documentatie, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0)));
        builder.persoonStapel(new Lo3Stapel<>(persoonInhoud));

        return builder.build();
    }
}
