/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle;

import java.util.Objects;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisPersoon;
import org.springframework.stereotype.Component;

/**
 * Geboorte vergelijker.
 */
@Component
public class GeboorteVergelijker {

    /**
     * vergelijkt een geboorte.
     * @param persoon te controleren persoon
     * @param geboorte waartegen gecontroleerd wordt
     * @return true indien gelijk
     */
    public final boolean vergelijk(final BrpToevalligeGebeurtenisPersoon persoon, final BrpGeboorteInhoud geboorte) {
        boolean result = Objects.equals(geboorte.getGeboortedatum(), persoon.getGeboorteDatum());
        result &= Objects.equals(geboorte.getGemeenteCode(), persoon.getGeboorteGemeenteCode());
        result &= Objects.equals(geboorte.getLandOfGebiedCode(), persoon.getGeboorteLandOfGebiedCode());
        result &= Objects.equals(geboorte.getBuitenlandsePlaatsGeboorte(), persoon.getGeboorteBuitenlandsePlaats());
        result &= Objects.equals(geboorte.getOmschrijvingGeboortelocatie(), persoon.getGeboorteOmschrijvingLocatie());
        return result;
    }
}
