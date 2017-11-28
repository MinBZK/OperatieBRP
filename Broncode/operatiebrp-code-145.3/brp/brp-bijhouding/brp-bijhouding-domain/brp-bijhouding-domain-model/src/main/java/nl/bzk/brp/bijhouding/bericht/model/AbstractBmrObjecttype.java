/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.Map;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECTTYPE_ATTRIBUUT;

/**
 * Bevat de attributen die op elk objecttype zitten.
 */
public abstract class AbstractBmrObjecttype extends AbstractBmrGroep implements BmrObjecttype {

    /**
     * Maakt een AbstractBmrObjecttype object.
     *
     * @param attributen de lijst met attributen waarin objecttype moet voorkomen
     */
    protected AbstractBmrObjecttype(final Map<String, String> attributen) {
        super(attributen);
        ValidatieHelper.controleerOpNullWaarde(attributen.get(OBJECTTYPE_ATTRIBUUT.toString()), "objecttype");
    }

    @Override
    public final String getObjecttype() {
        return getAttributen().get(OBJECTTYPE_ATTRIBUUT.toString());
    }
}
