/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.statisch;

import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.attribuuttype.RedenVervallenReisDocumentCode;
import nl.bzk.brp.model.attribuuttype.TechnischIdKlein;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;

/**
 * Object type reden vervallen reisdocument.
 */
public class RedenVervallenReisdocument extends AbstractStatischObjectType {

    private TechnischIdKlein id;
    private RedenVervallenReisDocumentCode code;
    private Naam naam;

    public TechnischIdKlein getId() {
        return id;
    }

    public RedenVervallenReisDocumentCode getCode() {
        return code;
    }

    public Naam getNaam() {
        return naam;
    }
}
