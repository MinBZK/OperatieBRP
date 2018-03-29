/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelLo3Rubriek;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienstbundelLo3Rubriek;
import org.springframework.stereotype.Component;

/**
 * Map dienstbundel lo3 rubriek van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpDienstbundelLo3RubriekMapper {

    /**
     * Map een database entiteit dienstbundel lo3 rubriek naar een BRP conversie model object.
     * @param dienstbundelLo3Rubriek database entiteit
     * @return conversie model object
     */
    public BrpDienstbundelLo3Rubriek mapDienstbundelLo3Rubriek(final DienstbundelLo3Rubriek dienstbundelLo3Rubriek) {
        return new BrpDienstbundelLo3Rubriek(dienstbundelLo3Rubriek.getLo3Rubriek().getNaam());
    }

}
