/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.statisch;

import nl.bzk.brp.model.attribuuttype.AangeverAdreshoudingCode;
import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.attribuuttype.Omschrijving;
import nl.bzk.brp.model.attribuuttype.TechnischIdKlein;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;

/**
 * Aangever adreshouding.
 */
public class AangeverAdreshouding extends AbstractStatischObjectType {

    private TechnischIdKlein aangeverAdreshoudingID;
    private Naam naam;
    private AangeverAdreshoudingCode aangeverAdreshoudingCode;
    private Omschrijving omschrijving;

    public TechnischIdKlein getAangeverAdreshoudingID() {
        return aangeverAdreshoudingID;
    }

    public Naam getNaam() {
        return naam;
    }

    public AangeverAdreshoudingCode getAangeverAdreshoudingCode() {
        return aangeverAdreshoudingCode;
    }

    public Omschrijving getOmschrijving() {
        return omschrijving;
    }
}
