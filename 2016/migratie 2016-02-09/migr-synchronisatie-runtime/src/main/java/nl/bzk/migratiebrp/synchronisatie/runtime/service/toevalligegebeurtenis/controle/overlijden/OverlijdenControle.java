/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.overlijden;

import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.ToevalligeGebeurtenisControle;

import org.springframework.stereotype.Component;

/**
 * Helper klasse voor het inhoudelijk controleren van het overlijden van een persoon tegen de persoon uit de BRP.
 */
@Component("overlijdenControle")
public final class OverlijdenControle implements ToevalligeGebeurtenisControle {

    @Override
    public boolean controleer(final Persoon rootPersoon, final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek) {

        final PersoonType persoon = verzoek.getPersoon();

        if (persoon == null) {
            return false;
        }

        return rootPersoon.getPersoonOverlijdenHistorieSet().isEmpty();
    }

}
