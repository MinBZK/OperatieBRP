/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3NationaliteitFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;

/**
 * Wijzigingen voor categorie 08: verblijfplaats.
 */
public final class Lo3WijzigingenCategorie04 extends Lo3Wijzigingen<Lo3NationaliteitInhoud> {

    private static final Lo3NationaliteitFormatter FORMATTER = new Lo3NationaliteitFormatter();

    /**
     * Default constructor.
     */
    public Lo3WijzigingenCategorie04() {
        super(Lo3CategorieEnum.CATEGORIE_04, FORMATTER);
    }

    @Override
    protected Lo3NationaliteitInhoud bepaalHistorischeInhoudObvLaatsteActie(
            final Actie laatsteActie,
            final Lo3Categorie<Lo3NationaliteitInhoud> laatsteInhoud,
            final LaatsteActieType laatsteActieType) {
        if (LaatsteActieType.AANPASSING_GELDIGHEID == laatsteActieType) {
            final Lo3NationaliteitInhoud.Builder builder = new Lo3NationaliteitInhoud.Builder();
            builder.redenVerliesNederlandschapCode(laatsteInhoud.getInhoud().getRedenVerliesNederlandschapCode());
            return builder.build();
        } else {
            return null;
        }
    }
}
