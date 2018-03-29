/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze interface typeert BrpGroepInhoud waarvoor geldt dat het indicaties zijn. Indicatie worden binnen het BRP
 * opertionele gegevens model op de PersoonIndicatie gemapped.
 */
public abstract class AbstractBrpIndicatieGroepInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "indicatie", required = true)
    private final BrpBoolean indicatie;
    @Element(name = "migrRdnOpnameNation", required = false)
    private final BrpString migratieRedenOpnameNationaliteit;
    @Element(name = "migrRdnBeeindigingNation", required = false)
    private final BrpString migratieRedenBeeindigingNationaliteit;

    /**
     * Constructor voor alleen subclasses.
     * @param indicatie BrpBoolean waarde
     * @param migratieRedenOpnameNationaliteit de reden opname nationaliteit tbv migratie/conversie
     * @param migratieRedenBeeindigingNationaliteit de reden beeindiging nationaliteit tbv migratie/conversie
     */
    protected AbstractBrpIndicatieGroepInhoud(
            final BrpBoolean indicatie,
            final BrpString migratieRedenOpnameNationaliteit,
            final BrpString migratieRedenBeeindigingNationaliteit) {
        this.indicatie = indicatie;
        this.migratieRedenOpnameNationaliteit = migratieRedenOpnameNationaliteit;
        this.migratieRedenBeeindigingNationaliteit = migratieRedenBeeindigingNationaliteit;
    }

    /**
     * Geef de waarde van indicatie van AbstractBrpIndicatieGroepInhoud.
     * @return de waarde van indicatie van AbstractBrpIndicatieGroepInhoud
     */
    public final BrpBoolean getIndicatie() {
        return indicatie;
    }

    /**
     * @return true als de indicatie gevuld en true is.
     */
    public final boolean heeftIndicatie() {
        final Boolean indicatieWaarde = BrpBoolean.unwrap(indicatie);
        return indicatieWaarde != null && indicatieWaarde;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpGroepInhoud#isLeeg()
     */
    @Override
    public final boolean isLeeg() {
        return !heeftIndicatie();
    }

    /**
     * Geef de waarde van migratie reden beeindiging nationaliteit van AbstractBrpIndicatieGroepInhoud.
     * @return de waarde van migratie reden beeindiging nationaliteit van AbstractBrpIndicatieGroepInhoud
     */
    public final BrpString getMigratieRedenBeeindigingNationaliteit() {
        return migratieRedenBeeindigingNationaliteit;
    }

    /**
     * Geef de waarde van migratie reden opname nationaliteit van AbstractBrpIndicatieGroepInhoud.
     * @return de waarde van migratie reden opname nationaliteit van AbstractBrpIndicatieGroepInhoud
     */
    public final BrpString getMigratieRedenOpnameNationaliteit() {
        return migratieRedenOpnameNationaliteit;
    }

    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || !(other.getClass().equals(getClass()))) {
            return false;
        }

        return new EqualsBuilder().append(indicatie, ((AbstractBrpIndicatieGroepInhoud) other).indicatie)
                .append(migratieRedenOpnameNationaliteit, ((AbstractBrpIndicatieGroepInhoud) other).migratieRedenOpnameNationaliteit)
                .append(
                        migratieRedenBeeindigingNationaliteit,
                        ((AbstractBrpIndicatieGroepInhoud) other).migratieRedenBeeindigingNationaliteit)
                .isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(indicatie).append(migratieRedenOpnameNationaliteit).append(migratieRedenBeeindigingNationaliteit).toHashCode();
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("indicatie", indicatie)
                .append("migratie reden opname nationaliteit", migratieRedenOpnameNationaliteit)
                .append(
                        "migratie reden beeindiging nationaliteit",
                        migratieRedenBeeindigingNationaliteit)
                .toString();
    }
}
