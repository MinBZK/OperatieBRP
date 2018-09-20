/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.model.logisch.kern.PersoonAdres;


/** Repository voor de {@link nl.bzk.brp.model.operationeel.kern.PersoonAdresModel} class. */
public interface PersoonAdresRepository {

    /**
     * Controleert in de BRP database of er iemand momenteel ingeschreven staat op een adres. Hierbij wordt een
     * exacte match verwacht qua adres. Lege velden dienen dus niet als wildcard te worden gezien.
     *
     * @param persoonAdres Het adres dat gecontroleerd wordt.
     * @return true indien er iemand ingeschreven staat anders false.
     */
    boolean isIemandIngeschrevenOpAdres(final PersoonAdres persoonAdres);
}
