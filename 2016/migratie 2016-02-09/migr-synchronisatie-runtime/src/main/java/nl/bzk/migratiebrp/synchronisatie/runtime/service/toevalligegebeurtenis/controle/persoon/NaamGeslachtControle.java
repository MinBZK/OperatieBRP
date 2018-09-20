/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.persoon;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.ToevalligeGebeurtenisControle;

import org.springframework.stereotype.Component;

/**
 * Helper klasse voor het inhoudelijk controleren van een naamgeslacht van een persoon tegen de persoon uit de BRP.
 */
@Component("naamGeslachtControle")
public final class NaamGeslachtControle implements ToevalligeGebeurtenisControle {

    @Inject
    @Named("geslachtsaanduidingControle")
    private ToevalligeGebeurtenisControle geslachtsaanduidingControle;

    @Inject
    @Named("geslachtsnaamComponentenControle")
    private ToevalligeGebeurtenisControle geslachtsnaamComponentenControle;

    @Override
    public boolean controleer(final Persoon rootPersoon, final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek) {

        boolean resultaat = true;
        final PersoonType persoon = verzoek.getPersoon();

        if (persoon == null) {
            return false;
        }

        // Controleer geslacht
        if (persoon.getGeslacht() != null) {
            resultaat = geslachtsaanduidingControle.controleer(rootPersoon, verzoek);
        }

        // Controleer geslachtsnaam componenten
        if (resultaat && persoon.getNaam() != null) {
            resultaat = geslachtsnaamComponentenControle.controleer(rootPersoon, verzoek);
        }

        return resultaat;
    }

}
