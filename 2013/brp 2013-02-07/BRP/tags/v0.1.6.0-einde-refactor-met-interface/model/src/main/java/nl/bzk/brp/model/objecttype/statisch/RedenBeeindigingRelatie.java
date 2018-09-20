/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.statisch;

import nl.bzk.brp.model.attribuuttype.Omschrijving;
import nl.bzk.brp.model.attribuuttype.RedenBeeindigingRelatieCode;
import nl.bzk.brp.model.attribuuttype.TechnischIdKlein;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;


/**
 * Gegevens model object voor Reden beeindiging relatie.
 */
public class RedenBeeindigingRelatie extends AbstractStatischObjectType {

    private TechnischIdKlein            id;
    private RedenBeeindigingRelatieCode code;
    private Omschrijving                omschrijving;

    public TechnischIdKlein getId() {
        return id;
    }

    public RedenBeeindigingRelatieCode getCode() {
        return code;
    }

    public Omschrijving getOmschrijving() {
        return omschrijving;
    }
}
