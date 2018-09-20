/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van een BRP identiteit. Identiteit objecten hebben geen inhoudelijke gegevens en
 * dienen te worden gebruikt in identiteit stapels waarbij alleen historie gegevens voorkomen. Gebruik altijd de @
 * #IDENTITEIT} constante ipv de constructor.
 *
 * Deze class is immutable en threadsafe.
 *
 */
public final class BrpIdentiteitInhoud extends AbstractBrpGroepInhoud {

    /**
     * Identiteit heeft geen inhoud. Daarom dient altijd hetzelfde object gebruikt te worden in identiteit stapels.
     */
    public static final BrpIdentiteitInhoud IDENTITEIT = new BrpIdentiteitInhoud();

    /**
     * Maakt een BrpOuderInhoud object.
     *
     */
    private BrpIdentiteitInhoud() {
    }

    /**
     * Identiteit is altijd gevuld.
     *
     * @return false
     */
    @Override
    public boolean isLeeg() {
        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).toString();
    }


}
