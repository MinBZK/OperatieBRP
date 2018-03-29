/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.algemeenbrp.util.xml.annotation.ElementList;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class om de geslachtsnaamstam voor naamgebruik te kunnen opslaan. Dit omdat dit veld als enige meerdere onderzoeken
 * gekoppeld kan krijgen.
 */
public final class BrpNaamgebruikGeslachtsnaamstam {

    private String waarde;

    private Set<Lo3Onderzoek> onderzoeken;

    /**
     * Constructor voor XML in/output.
     * @param waarde waarde van de naam
     * @param onderzoeken de onderzoeken
     */
    public BrpNaamgebruikGeslachtsnaamstam(
            @Element(name = "waarde") final String waarde,
            @ElementList(name = "onderzoeken", entry = "onderzoek", type = Lo3Onderzoek.class) final Set<Lo3Onderzoek> onderzoeken) {
        this.waarde = waarde;
        if (onderzoeken != null && !onderzoeken.isEmpty()) {
            this.onderzoeken = new HashSet<>(onderzoeken);
        }
    }

    /**
     * Geeft de waarde terug.
     * @return de waarde
     */
    @Element(name = "waarde")
    public String getWaarde() {
        return waarde;
    }

    /**
     * Geeft de lijst van onderzoeken terug.
     * @return de lijst van onderzoeken
     */
    @ElementList(name = "onderzoeken", entry = "onderzoek", type = Lo3Onderzoek.class)
    public Set<Lo3Onderzoek> getOnderzoeken() {
        return onderzoeken;
    }

    /**
     * Geeft aan of dit object inhoudelijk gevuld is. Dit object kan namelijk alleen een onderzoek bevatten terwijl
     * waarde <code>null</code> of <code>""</code> is.
     * @return true als waarde niet <code>null</code> of <code>""</code> is.
     */
    public boolean isInhoudelijkGevuld() {
        return waarde != null && !waarde.isEmpty();
    }

    /**
     * Unwrap een {@link BrpNaamgebruikGeslachtsnaamstam} object om de {@link String} waarde terug te krijgen.
     * @param attribuut Een {@link BrpNaamgebruikGeslachtsnaamstam}, mag null zijn.
     * @return Een {@link String} object, of null als de input null is.
     */
    public static String unwrap(final BrpNaamgebruikGeslachtsnaamstam attribuut) {
        return attribuut == null ? null : attribuut.waarde;
    }

    /**
     * Wrap de waarde en de lijst van onderzoeken naar een {@link BrpNaamgebruikGeslachtsnaamstam}.
     * @param waarde de {@link String} waarde
     * @param onderzoeken een lijst van {@link Lo3Onderzoek}
     * @return BrpNaamgebruikGeslachtsnaamstam object met daarin waarde en de lijst van onderzoeken
     */
    public static BrpNaamgebruikGeslachtsnaamstam wrap(final String waarde, final Set<Lo3Onderzoek> onderzoeken) {
        if (waarde == null && (onderzoeken == null || onderzoeken.isEmpty())) {
            return null;
        }
        return new BrpNaamgebruikGeslachtsnaamstam(waarde, onderzoeken);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpNaamgebruikGeslachtsnaamstam)) {
            return false;
        }

        final BrpNaamgebruikGeslachtsnaamstam andere = (BrpNaamgebruikGeslachtsnaamstam) other;
        return new EqualsBuilder().append(waarde, andere.waarde).append(onderzoeken, andere.onderzoeken).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(waarde).append(onderzoeken).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("waarde", waarde).append("onderzoeken", onderzoeken).toString();
    }
}
