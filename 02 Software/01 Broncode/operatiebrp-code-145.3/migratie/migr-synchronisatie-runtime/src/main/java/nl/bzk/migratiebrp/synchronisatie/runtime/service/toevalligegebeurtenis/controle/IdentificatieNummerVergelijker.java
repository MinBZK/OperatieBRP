/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle;

import java.util.Objects;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisPersoon;
import org.springframework.stereotype.Component;

/**
 * Vergelijker voor identificatienummers.
 */
@Component
public class IdentificatieNummerVergelijker {

    /**
     * Vergelijk identificatienummers.
     * @param persoon persoon waarvan id wordt gecontroleerd
     * @param inhoud object waartegen wordt gecontroleerd
     * @return true indien gelijk
     */
    public final boolean vergelijk(final BrpToevalligeGebeurtenisPersoon persoon, final BrpIdentificatienummersInhoud inhoud) {
        boolean result = Objects.equals(persoon.getAdministratienummer(), inhoud.getAdministratienummer());
        result &= Objects.equals(persoon.getBurgerservicenummer(), inhoud.getBurgerservicenummer());
        return result;
    }
}
