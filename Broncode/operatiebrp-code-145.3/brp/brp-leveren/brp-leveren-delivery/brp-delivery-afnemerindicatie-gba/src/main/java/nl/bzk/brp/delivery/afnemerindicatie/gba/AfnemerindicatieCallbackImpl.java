/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemerindicatie.gba;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.berichtmodel.OnderhoudAfnemerindicatieAntwoordBericht;
import nl.bzk.brp.service.afnemerindicatie.RegistreerAfnemerindicatieCallback;

/**
 * AfnemerindicatieCallbackImpl.
 */
final class AfnemerindicatieCallbackImpl implements RegistreerAfnemerindicatieCallback<String> {

    private OnderhoudAfnemerindicatieAntwoordBericht antwoord;

    @Override
    public void verwerkResultaat(final SoortDienst soortDienst, final OnderhoudAfnemerindicatieAntwoordBericht bericht) {
        this.antwoord = bericht;
    }

    @Override
    public String getResultaat() {
        return null;
    }

    List<Melding> getMeldingen() {
        return antwoord.getBasisBerichtGegevens().getMeldingen();
    }
}
