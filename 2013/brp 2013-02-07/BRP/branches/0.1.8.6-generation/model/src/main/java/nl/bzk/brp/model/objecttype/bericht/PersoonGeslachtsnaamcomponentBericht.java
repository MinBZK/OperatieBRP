/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.bericht;

import nl.bzk.brp.model.objecttype.bericht.basis.AbstractPersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.objecttype.logisch.PersoonGeslachtsnaamcomponent;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 *
 */
@SuppressWarnings("serial")
public class PersoonGeslachtsnaamcomponentBericht extends AbstractPersoonGeslachtsnaamcomponentBericht implements
        PersoonGeslachtsnaamcomponent
{

    private static final int HASHCODE_NIET_NUL_ONEVEN_GETAL            = 7;
    private static final int HASHCODE_MULTIPLIER_NIET_NUL_ONEVEN_GETAL = 21;

    @Override
    public boolean equals(final Object obj) {
        final boolean resultaat;
        if (!(obj instanceof PersoonGeslachtsnaamcomponentBericht)) {
            resultaat = false;
        } else {
            if (this == obj) {
                resultaat = true;
            } else {
                PersoonGeslachtsnaamcomponentBericht pg = (PersoonGeslachtsnaamcomponentBericht) obj;
                resultaat =
                    new EqualsBuilder().append(getPersoon(), pg.getPersoon()).append(getVolgnummer(),
                            pg.getVolgnummer()).isEquals();
            }
        }
        return resultaat;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASHCODE_NIET_NUL_ONEVEN_GETAL, HASHCODE_MULTIPLIER_NIET_NUL_ONEVEN_GETAL).append(
                getPersoon()).append(getVolgnummer()).toHashCode();
    }
}
