/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.statisch;

import nl.bzk.brp.model.attribuuttype.AutoriteitVanAfgifteReisdocumentCode;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Omschrijving;
import nl.bzk.brp.model.attribuuttype.TechnischIdKlein;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;

/**
 * Autoriteit van afgifte reisdocument.
 *
 */
public class AutoriteitVanAfgifteReisdocument extends AbstractStatischObjectType {

    private TechnischIdKlein autoriteitVanAfgifteDocumentID;
    private AutoriteitVanAfgifteReisdocumentCode code;
    private Omschrijving omschrijving;
    private Datum datumAanvangGeldigheid;
    private Datum datumEindeGeldigheid;

    /**
     * .
     * @return .
     */
    public TechnischIdKlein getautoriteitVanAfgifteDocumentID() {
        return autoriteitVanAfgifteDocumentID;
    }

    public AutoriteitVanAfgifteReisdocumentCode getCode() {
        return code;
    }

    public Omschrijving getOmschrijving() {
        return omschrijving;
    }

    public Datum getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    public Datum getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

}
