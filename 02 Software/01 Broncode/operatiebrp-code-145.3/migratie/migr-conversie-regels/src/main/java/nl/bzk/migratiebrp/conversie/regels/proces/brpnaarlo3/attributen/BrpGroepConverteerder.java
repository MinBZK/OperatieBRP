/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;

/**
 * Interface voor het converteren van BRP groepen.
 * @param <B> extends {@link BrpGroepInhoud}
 * @param <L> extends {@link Lo3CategorieInhoud}
 */
public interface BrpGroepConverteerder<B extends BrpGroepInhoud, L extends Lo3CategorieInhoud> {
    /**
     * Converteert de groep naar LO3.
     * @param actie actie
     * @param groepen de BRP groepen die geconverteerd moeten worden
     * @param stapel de {@link BrpStapel} die geconverteerd wordt.
     * @param categorieen de reeds terug geconverteerde LO3 categorieen
     *
     */
    void converteer(
            BrpActie actie,
            List<BrpGroep<B>> groepen,
            BrpStapel<B> stapel,
            List<Lo3CategorieWrapper<L>> categorieen);

    /**
     * Maak een nieuwe (lege) lo3 rij.
     * @return nieuwe (lege) lo3 rij
     */
    L maakNieuweInhoud();

    /**
     * Vul de gegevens lo3 inhoud met de brp inhoud. Maak de lo3 inhoud leeg als brp inhoud null is.
     * @param lo3Inhoud lo3 inhoud
     * @param brpInhoud brp inhoud
     * @param brpVorige brp inhoud van vorige record
     * @return de gevulde lo3 inhoud
     */
    L vulInhoud(L lo3Inhoud, B brpInhoud, B brpVorige);
}
