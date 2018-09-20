/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bevraging;

import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBericht;


/** Het antwoord bericht voor bevragingen. */
public class VraagDetailsPersoonAntwoord extends AbstractBevragingAntwoordBericht {

    /**
     * Vraagt de gevonden persoon op.
     * @return Gevonden persoon indien er een persoon gevonden is.
     */
    public PersoonModel getPersoon() {
        if (getPersonen() != null && !getPersonen().isEmpty()) {
            return getPersonen().iterator().next();
        }
        return null;
    }

    /**
     * Voegt een persoon toe aan de gevonden personen.
     * Deze functie wordt vereist door Jibx.
     * @param persoon De toe te voegen persoon.
     */
    public void voegPersoonToe(final PersoonModel persoon) {
        getPersonen().add(persoon);
    }

    /**
     * Controlleert of de persoon gevonden is.
     *
     * @return true als de persoon gevonden is
     */
    public boolean isPersoonGevonden() {
        return getPersonen() != null && !getPersonen().isEmpty();
    }

    @Override
    public SoortBericht getSoortBericht() {
        return SoortBericht.VRAAG_DETAILSPERSOON_BEVRAGEN_ANTWOORD;
    }
}
