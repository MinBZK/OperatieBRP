/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle;

import java.util.Objects;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisPersoon;
import org.springframework.stereotype.Component;

/**
 * Samengestelde naam vergelijker.
 */
@Component
public class SamengesteldeNaamVergelijker {

    /**
     * vergelijk samengestelden naam.
     * @param persoon te controleren persoon
     * @param samengesteldeNaam waartegen gecontrolleerd kan worden
     * @return true indien gelijk
     */
    public final boolean vergelijk(final BrpToevalligeGebeurtenisPersoon persoon, final BrpSamengesteldeNaamInhoud samengesteldeNaam) {
        boolean result = Objects.equals(samengesteldeNaam.getPredicaatCode(), persoon.getPredicaatCode());
        result &= Objects.equals(samengesteldeNaam.getVoornamen(), persoon.getVoornamen());
        result &= Objects.equals(samengesteldeNaam.getAdellijkeTitelCode(), persoon.getAdellijkeTitelCode());
        result &= Objects.equals(samengesteldeNaam.getVoorvoegsel(), persoon.getVoorvoegsel());
        result &= Objects.equals(samengesteldeNaam.getGeslachtsnaamstam(), persoon.getGeslachtsnaamstam());
        return result;
    }
}
