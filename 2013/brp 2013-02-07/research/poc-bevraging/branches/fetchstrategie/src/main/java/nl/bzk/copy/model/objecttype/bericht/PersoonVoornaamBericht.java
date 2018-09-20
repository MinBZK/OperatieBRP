/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.bericht;

import nl.bzk.copy.model.objecttype.bericht.basis.AbstractPersoonVoornaamBericht;
import nl.bzk.copy.model.objecttype.logisch.PersoonVoornaam;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *
 */
@SuppressWarnings("serial")
public class PersoonVoornaamBericht extends AbstractPersoonVoornaamBericht implements PersoonVoornaam {

    private static final int HASHCODE_NIET_NUL_ONEVEN_GETAL = 11;
    private static final int HASHCODE_MULTIPLIER_NIET_NUL_ONEVEN_GETAL = 21;

    @Override
    public boolean equals(final Object obj) {
        final boolean resultaat;
        if (!(obj instanceof PersoonVoornaamBericht)) {
            resultaat = false;
        } else {
            if (this == obj) {
                resultaat = true;
            } else {
                PersoonVoornaamBericht pv = (PersoonVoornaamBericht) obj;
                resultaat = new EqualsBuilder()
                        .append(getPersoon(), pv.getPersoon())
                        .append(getVolgnummer(), pv.getVolgnummer())
                        .isEquals();
            }
        }
        return resultaat;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASHCODE_NIET_NUL_ONEVEN_GETAL, HASHCODE_MULTIPLIER_NIET_NUL_ONEVEN_GETAL)
                .append(getPersoon()).append(getVolgnummer()).toHashCode();
    }
}
