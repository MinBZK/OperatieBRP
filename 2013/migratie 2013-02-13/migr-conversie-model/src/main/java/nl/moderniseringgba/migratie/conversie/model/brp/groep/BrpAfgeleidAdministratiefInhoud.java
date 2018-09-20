/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Deze class representeert de inhoud van de BRP groep Afgeleid Administratief.
 * 
 * Deze class is immutable en threadsafe.
 */
@Root(strict = false)
public final class BrpAfgeleidAdministratiefInhoud extends AbstractBrpGroepInhoud {
    @Element(name = "laatsteWijziging", required = false)
    private final BrpDatumTijd laatsteWijziging;
    @Element(name = "inOnderzoek", required = false)
    private final Boolean inOnderzoek;

    /**
     * Maak een BrpAfgeleidAdministratiefInhoud object.
     * 
     * @param laatsteWijziging
     *            datumtijd laatste wijziging
     * @param inOnderzoek
     *            indicatie in onderzoek
     */
    public BrpAfgeleidAdministratiefInhoud(
            @Element(name = "laatsteWijziging", required = false) final BrpDatumTijd laatsteWijziging,
            @Element(name = "inOnderzoek", required = false) final Boolean inOnderzoek) {
        this.laatsteWijziging = laatsteWijziging;
        this.inOnderzoek = inOnderzoek;
    }

    @Override
    public boolean isLeeg() {
        return !ValidationUtils.isEenParameterGevuld(laatsteWijziging, inOnderzoek);
    }

    /**
     * @return the laatsteWijziging
     */
    public BrpDatumTijd getLaatsteWijziging() {
        return laatsteWijziging;
    }

    /**
     * @return the inOnderzoek
     */

    public Boolean getInOnderzoek() {
        return inOnderzoek;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpAfgeleidAdministratiefInhoud)) {
            return false;
        }
        final BrpAfgeleidAdministratiefInhoud castOther = (BrpAfgeleidAdministratiefInhoud) other;
        return new EqualsBuilder().append(laatsteWijziging, castOther.laatsteWijziging)
                .append(inOnderzoek, castOther.inOnderzoek).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(laatsteWijziging).append(inOnderzoek).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("laatsteWijziging", laatsteWijziging).append("inOnderzoek", inOnderzoek).toString();
    }

}
