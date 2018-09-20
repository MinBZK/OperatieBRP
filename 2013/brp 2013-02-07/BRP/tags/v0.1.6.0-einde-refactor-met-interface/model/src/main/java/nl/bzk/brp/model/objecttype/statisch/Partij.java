/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.statisch;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.GemeenteCode;
import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.attribuuttype.TechnischIdMiddel;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;

/**
 * Partij.
 */
public class Partij extends AbstractStatischObjectType {

    private TechnischIdMiddel partijID;
    private Naam naam;
    private Datum datumAanvang;
    private Datum datumEinde;
    private GemeenteCode gemeenteCode;

    public TechnischIdMiddel getPartijID() {
        return partijID;
    }

    public Naam getNaam() {
        return naam;
    }

    public Datum getDatumAanvang() {
        return datumAanvang;
    }

    public Datum getDatumEinde() {
        return datumEinde;
    }

    public GemeenteCode getGemeenteCode() {
        return gemeenteCode;
    }
}
