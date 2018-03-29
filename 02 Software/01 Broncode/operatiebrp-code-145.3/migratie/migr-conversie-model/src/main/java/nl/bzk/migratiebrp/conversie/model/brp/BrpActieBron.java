/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Een Brp ActieBron.
 */
public class BrpActieBron {
    /**
     * Een actieBron heeft 1 document, maar document heeft historie, dus wordt het toch een stapel.
     */
    private final BrpStapel<BrpDocumentInhoud> documentStapel;
    private final BrpString rechtsgrondOmschrijving;

    /**
     * Maakt een nieuwe BrpActieBron.
     * @param documentInhoudStapel een {@link BrpStapel} met daarin de documentatie.
     * @param rechtsgrondOmschrijvingText de rechtsgrond omschrijving
     */
    public BrpActieBron(
            @Element(name = "documentStapel") final BrpStapel<BrpDocumentInhoud> documentInhoudStapel,
            @Element(name = "rechtsgrondOmschrijving") final BrpString rechtsgrondOmschrijvingText) {
        documentStapel = documentInhoudStapel;
        rechtsgrondOmschrijving = rechtsgrondOmschrijvingText;
    }

    /**
     * Gets the een actieBron heeft 1 document, maar document heeft historie, dus wordt het toch een stapel.
     * @return the een actieBron heeft 1 document, maar document heeft historie, dus wordt het toch een stapel
     */
    @Element(name = "documentStapel", required = false)
    public final BrpStapel<BrpDocumentInhoud> getDocumentStapel() {
        return documentStapel;
    }

    /**
     * Geef de waarde van rechtsgrond omschrijving.
     * @return rechtsgrond omschrijving
     */
    @Element(name = "rechtsgrondOmschrijving", required = false)
    public final BrpString getRechtsgrondOmschrijving() {
        return rechtsgrondOmschrijving;
    }

    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpActieBron)) {
            return false;
        }
        final BrpActieBron castOther = (BrpActieBron) other;
        return new EqualsBuilder().append(documentStapel, castOther.documentStapel)
                .append(rechtsgrondOmschrijving, castOther.rechtsgrondOmschrijving)
                .isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(documentStapel).append(rechtsgrondOmschrijving).toHashCode();
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("documentStapel", documentStapel)
                .append("rechtsgrondOmschrijving", rechtsgrondOmschrijving)
                .toString();
    }
}
