/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.statisch;

import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.attribuuttype.TechnischIdKlein;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;

/**
 * Reden wijziging adres.
 */
public class RedenWijzigingAdres extends AbstractStatischObjectType {

    private TechnischIdKlein redenWijzigingAdresID;
    private RedenWijzigingAdresCode redenWijzigingAdresCode;
    private Naam naam;

    public TechnischIdKlein getRedenWijzigingAdresID() {
        return redenWijzigingAdresID;
    }

    public RedenWijzigingAdresCode getRedenWijzigingAdresCode() {
        return redenWijzigingAdresCode;
    }

    public Naam getNaam() {
        return naam;
    }
}
