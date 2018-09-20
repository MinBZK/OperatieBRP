/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.huwelijkofgp;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.ToevalligeGebeurtenisControle;

import org.springframework.stereotype.Component;

/**
 * Helper klasse voor inhoudelijke controle bij het sluiten van een huwelijk/geregistreerd partnerschap.
 */
@Component("omzettingHuwelijkOfGeregistreerdPartnerschapControle")
public final class OmzettingHuwelijkOfGeregistreerdPartnerschapControle implements ToevalligeGebeurtenisControle {

    @Inject
    @Named("persoonControle")
    private ToevalligeGebeurtenisControle persoonControle;

    @Inject
    @Named("huwelijkControle")
    private ToevalligeGebeurtenisControle huwelijkControle;

    @Inject
    @Named("soortVerbintenisOngelijkControle")
    private ToevalligeGebeurtenisControle soortVerbintenisOngelijkControle;

    @Override
    public boolean controleer(final Persoon rootPersoon, final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek) {
        return persoonControle.controleer(rootPersoon, verzoek)
                && huwelijkControle.controleer(rootPersoon, verzoek)
                && soortVerbintenisOngelijkControle.controleer(rootPersoon, verzoek);
    }

}
