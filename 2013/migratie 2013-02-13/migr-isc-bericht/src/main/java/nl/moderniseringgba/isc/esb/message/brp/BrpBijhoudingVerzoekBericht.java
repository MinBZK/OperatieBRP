/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp;

import java.io.Serializable;
import java.math.BigInteger;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Bericht voor erkenning na geboorte.
 */
public abstract class BrpBijhoudingVerzoekBericht extends AbstractBrpBericht implements BrpVerzoekBericht,
        Serializable {
    private static final long serialVersionUID = 1L;
    private static final String BERICHT_TYPE_ELEMENT = "berichtType";
    private static final String PERSOONSLIJST_ELEMENT = "brpPersoonslijst";

    /* ************************************************************************************************************* */

    /**
     * @return Iscgemeente.brpGemeente
     */
    public abstract BrpGemeenteCode getBrpGemeente();

    /**
     * Zet IscGemeente.brpGemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    public abstract void setBrpGemeente(final BrpGemeenteCode gemeente);

    /**
     * @return Iscgemeente.lo3Gemeente
     */
    public abstract BrpGemeenteCode getLo3Gemeente();

    /**
     * Zet IscGemeente.lo3Gemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    public abstract void setLo3Gemeente(final BrpGemeenteCode gemeente);

    /**
     * Geeft de registratiegemeente die op het bericht staat terug.
     * 
     * @return De registratiegemeente of null in het geval dat er geen erkenningverzoek type beschikbaar is.
     */
    public abstract String getRegistratiegemeente();

    /**
     * Geeft het aktenummer dat op het bericht staat terug.
     * 
     * @return Het aktenummer of null in het geval dat er geen erkenningverzoek type beschikbaar is.
     */
    public abstract String getAktenummer();

    /**
     * Geeft de ingangsdatum die op het bericht staat terug.
     * 
     * @return De ingangsdatum of null in het geval dat er geen erkenningverzoek type beschikbaar is.
     */
    public abstract BigInteger getIngangsdatumGeldigheid();

    /* ************************************************************************************************************* */

    /**
     * Geeft de Brp Persoonslijst terug.
     * 
     * @return De brp persoonslijst.
     */
    public abstract BrpPersoonslijst getBrpPersoonslijst();

    /**
     * Stelt op basis van het beschikbare VerzoekType een BrpPersoonslijst op.
     * 
     * @throws BerichtInhoudException
     *             Indien de inhoud niet correct is.
     */
    protected abstract void converteerNaarBrpPersoonslijst() throws BerichtInhoudException;

    /* ************************************************************************************************************* */

    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpBijhoudingVerzoekBericht)) {
            return false;
        }
        final BrpBijhoudingVerzoekBericht castOther = (BrpBijhoudingVerzoekBericht) other;
        return new EqualsBuilder().append(getBerichtType(), castOther.getBerichtType())
                .append(getBrpPersoonslijst(), castOther.getBrpPersoonslijst()).isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(getBerichtType()).append(getBrpPersoonslijst()).toHashCode();
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(BERICHT_TYPE_ELEMENT, getBerichtType()).append(PERSOONSLIJST_ELEMENT, getBrpPersoonslijst())
                .toString();
    }

    /* ************************************************************************************************************* */

}
