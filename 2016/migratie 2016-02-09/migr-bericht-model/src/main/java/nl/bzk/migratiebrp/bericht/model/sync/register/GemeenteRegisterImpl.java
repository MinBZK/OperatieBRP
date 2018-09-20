/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.register;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Gemeente register implementatie.
 */
public final class GemeenteRegisterImpl implements GemeenteRegister, Serializable {
    private static final long serialVersionUID = 1L;

    private final List<Gemeente> gemeenten;
    private transient Map<String, Gemeente> gemeenteByGemeenteCode;
    private transient Map<String, Gemeente> gemeenteByPartijCode;

    /**
     * Constructor.
     * 
     * @param gemeenten
     *            gemeenten
     */
    public GemeenteRegisterImpl(final List<Gemeente> gemeenten) {
        this.gemeenten = Collections.unmodifiableList(gemeenten);
    }

    /* ************************************************************************************************************* */

    @Override
    public List<Gemeente> geefAlleGemeenten() {
        return gemeenten;
    }

    @Override
    public Gemeente zoekGemeenteOpGemeenteCode(final String gemeenteCode) {
        // Locking/threading: niet erg als dit twee keer gebeurt.
        if (gemeenteByGemeenteCode == null) {
            final Map<String, Gemeente> map = new HashMap<>();
            for (final Gemeente gemeente : gemeenten) {
                map.put(gemeente.getGemeenteCode(), gemeente);
            }
            gemeenteByGemeenteCode = map;
        }
        return gemeenteByGemeenteCode.get(gemeenteCode);
    }

    @Override
    public Gemeente zoekGemeenteOpPartijCode(final String partijCode) {
        // Locking/threading: niet erg als dit twee keer gebeurt.
        if (gemeenteByPartijCode == null) {
            final Map<String, Gemeente> map = new HashMap<>();
            for (final Gemeente gemeente : gemeenten) {
                map.put(gemeente.getPartijCode(), gemeente);
            }
            gemeenteByPartijCode = map;
        }
        return gemeenteByPartijCode.get(partijCode);
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GemeenteRegisterImpl)) {
            return false;
        }
        final GemeenteRegisterImpl castOther = (GemeenteRegisterImpl) other;
        return new EqualsBuilder().append(gemeenten, castOther.gemeenten).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(gemeenten).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("gemeenten", gemeenten)
                                                                          .toString();
    }

}
