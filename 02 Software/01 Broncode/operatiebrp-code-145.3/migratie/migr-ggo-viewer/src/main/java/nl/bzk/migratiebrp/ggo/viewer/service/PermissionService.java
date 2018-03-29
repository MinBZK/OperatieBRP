/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;

/**
 * Geeft aan of de huidige gebruiker leesrechten heeft op de gevraagde pl.
 */
public interface PermissionService {
    /**
     * Controleer de permissions van de gebruiker. PL-en van de eigen gemeente en die van gerelateerden mogen bekeken
     * worden. Gebruikers in de rol 'ROLE_ADMIN' mogen alle PL-en inzien.
     * @param lo3Persoonslijst Lo3Persoonslijst waar de rechten voor gecontroleerd moeten worden.
     * @return boolean True als de gebruiker rechten heeft de PL te bekijken.
     */
    boolean hasPermissionOnPl(final Lo3Persoonslijst lo3Persoonslijst);
}
