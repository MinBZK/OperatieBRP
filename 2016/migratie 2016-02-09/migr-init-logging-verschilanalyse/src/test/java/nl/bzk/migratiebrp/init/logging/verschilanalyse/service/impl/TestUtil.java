/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.verschilanalyse.service.impl;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

public class TestUtil {

    private static final Lo3Herkomst HERKOMST = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0);

    private TestUtil() {
    }

    /**
     * Geef de waarde van herkomst.
     *
     * @return herkomst
     */
    public static Lo3Herkomst getHerkomst() {
        return HERKOMST;
    }

    public static Lo3Stapel<Lo3PersoonInhoud> maakStapel() {
        return maakStapel(false);
    }

    public static Lo3Stapel<Lo3PersoonInhoud> maakStapel(final boolean anderPersoon) {
        return maakStapel(anderPersoon, HERKOMST);
    }

    public static Lo3Stapel<Lo3PersoonInhoud> maakStapel(final boolean anderPersoon, final Lo3Herkomst herkomst) {
        final List<Lo3Categorie<Lo3PersoonInhoud>> voorkomens = new ArrayList<>();
        voorkomens.add(maakVoorkomen(anderPersoon, herkomst));
        return new Lo3Stapel<>(voorkomens);
    }

    public static Lo3Categorie<Lo3PersoonInhoud> maakVoorkomen() {
        return maakVoorkomen(false);
    }

    public static Lo3Categorie<Lo3PersoonInhoud> maakVoorkomen(final boolean anderPersoon) {
        return maakVoorkomen(anderPersoon, HERKOMST);
    }

    public static Lo3Categorie<Lo3PersoonInhoud> maakVoorkomen(final boolean anderPersoon, final Lo3Herkomst herkomst) {
        final Lo3PersoonInhoud.Builder inhoud = new Lo3PersoonInhoud.Builder();
        if (anderPersoon) {
            inhoud.setaNummer(Lo3Long.wrap(9987654321L));
        } else {
            inhoud.setaNummer(Lo3Long.wrap(1234567890L));
        }
        return new Lo3Categorie<>(inhoud.build(), null, Lo3Historie.NULL_HISTORIE, herkomst);
    }

}
