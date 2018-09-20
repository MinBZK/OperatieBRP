/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bevraging;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonMdl;


/** Het antwoord bericht voor bevragingen. */
public class VraagDetailsPersoonAntwoord extends BevragingAntwoordBericht {

    /**
     * Standaard constructor die op basis van het opgegeven berichtresultaat uit de business laag de juiste waardes
     * bepaalt/zet in dit antwoord bericht.
     *
     * @param berichtResultaat het bericht resultaat uit de business laag op basis waarvan het antwoord bericht wordt
     * gebouwd.
     */
    public VraagDetailsPersoonAntwoord(final OpvragenPersoonResultaat berichtResultaat) {
        super(berichtResultaat);
    }

    /**
     * Vraagt de gevonden persoon op.
     * @return Gevonden persoon indien er een persoon gevonden is.
     */
    public PersoonMdl getPersoon() {
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
    public void voegPersoonToe(final PersoonMdl persoon) {
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
}
