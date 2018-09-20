/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de groep BRP Bijhoudingsgemeente.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpBijhoudingsgemeenteInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "bijhoudingsgemeenteCode", required = false)
    private final BrpGemeenteCode bijhoudingsgemeenteCode;
    @Element(name = "datumInschrijvingInGemeente", required = false)
    private final BrpDatum datumInschrijvingInGemeente;
    @Element(name = "onverwerktDocumentAanwezig", required = false)
    private final Boolean onverwerktDocumentAanwezig;

    /**
     * Maakt een BrpBijhoudingsgemeenteInhoud object.
     * 
     * @param bijhoudingsgemeenteCode
     *            bijhoudingsgemeente
     * @param datumInschrijvingInGemeente
     *            datum inschrijving in gemeente
     * @param onverwerktDocumentAanwezig
     *            indicatie onverwerkt document aanwezig
     */
    public BrpBijhoudingsgemeenteInhoud(
            @Element(name = "bijhoudingsgemeenteCode", required = false) final BrpGemeenteCode bijhoudingsgemeenteCode,
            @Element(name = "datumInschrijvingInGemeente", required = false) final BrpDatum datumInschrijvingInGemeente,
            @Element(name = "onverwerktDocumentAanwezig", required = false) final Boolean onverwerktDocumentAanwezig) {
        this.bijhoudingsgemeenteCode = bijhoudingsgemeenteCode;
        this.datumInschrijvingInGemeente = datumInschrijvingInGemeente;
        this.onverwerktDocumentAanwezig = onverwerktDocumentAanwezig;
    }

    @Override
    public boolean isLeeg() {
        return bijhoudingsgemeenteCode == null && datumInschrijvingInGemeente == null
                && onverwerktDocumentAanwezig == null;
    }

    public BrpGemeenteCode getBijhoudingsgemeenteCode() {
        return bijhoudingsgemeenteCode;
    }

    public BrpDatum getDatumInschrijvingInGemeente() {
        return datumInschrijvingInGemeente;
    }

    public Boolean getOnverwerktDocumentAanwezig() {
        return onverwerktDocumentAanwezig;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpBijhoudingsgemeenteInhoud)) {
            return false;
        }
        final BrpBijhoudingsgemeenteInhoud castOther = (BrpBijhoudingsgemeenteInhoud) other;
        return new EqualsBuilder().append(bijhoudingsgemeenteCode, castOther.bijhoudingsgemeenteCode)
                .append(datumInschrijvingInGemeente, castOther.datumInschrijvingInGemeente)
                .append(onverwerktDocumentAanwezig, castOther.onverwerktDocumentAanwezig).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(bijhoudingsgemeenteCode).append(datumInschrijvingInGemeente)
                .append(onverwerktDocumentAanwezig).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("bijhoudingsgemeenteCode", bijhoudingsgemeenteCode)
                .append("datumInschrijvingInGemeente", datumInschrijvingInGemeente)
                .append("onverwerktDocumentAanwezig", onverwerktDocumentAanwezig).toString();
    }
}
