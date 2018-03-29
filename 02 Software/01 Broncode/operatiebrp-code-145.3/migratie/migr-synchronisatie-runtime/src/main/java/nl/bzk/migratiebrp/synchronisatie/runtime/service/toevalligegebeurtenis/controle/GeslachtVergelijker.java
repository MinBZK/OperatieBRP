/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle;

import java.util.Objects;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisPersoon;
import org.springframework.stereotype.Component;

/**
 * Geslacht vergelijker.
 */
@Component
public class GeslachtVergelijker {

    /**
     * vergelijkt geslacht.
     * @param persoon te controleren persoon
     * @param geslacht waartegen gecontroleerd wordt.
     * @return true indien gelijk
     */
    public final boolean vergelijk(final BrpToevalligeGebeurtenisPersoon persoon, final BrpGeslachtsaanduidingInhoud geslacht) {
        return Objects.equals(geslacht.getGeslachtsaanduidingCode(), persoon.getGeslachtsaanduidingCode());
    }
}
