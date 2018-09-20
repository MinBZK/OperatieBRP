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
 * Autorisatie register implementatie.
 */
public final class AutorisatieRegisterImpl implements AutorisatieRegister, Serializable {
    private static final long serialVersionUID = 1L;

    private final List<Autorisatie> autorisaties;
    private transient Map<String, Autorisatie> autorisatieByPartijCode;

    /**
     * Constructor.
     *
     * @param autorisaties
     *            afnemers
     */
    public AutorisatieRegisterImpl(final List<Autorisatie> autorisaties) {
        this.autorisaties = Collections.unmodifiableList(autorisaties);
    }

    /* ************************************************************************************************************* */

    @Override
    public List<Autorisatie> geefAlleAutorisaties() {
        return autorisaties;
    }

    @Override
    public Autorisatie zoekAutorisatieOpPartijCode(final String partijCode) {
        // Locking/threading: niet erg als dit twee keer gebeurt.
        if (autorisatieByPartijCode == null) {
            final Map<String, Autorisatie> map = new HashMap<>();
            for (final Autorisatie autorisatie : autorisaties) {
                map.put(autorisatie.getPartijCode(), autorisatie);
            }
            autorisatieByPartijCode = map;
        }
        return autorisatieByPartijCode.get(partijCode);
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AutorisatieRegisterImpl)) {
            return false;
        }
        final AutorisatieRegisterImpl castOther = (AutorisatieRegisterImpl) other;
        return new EqualsBuilder().append(autorisaties, castOther.autorisaties).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(autorisaties).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("autorisaties", autorisaties).toString();
    }

}
