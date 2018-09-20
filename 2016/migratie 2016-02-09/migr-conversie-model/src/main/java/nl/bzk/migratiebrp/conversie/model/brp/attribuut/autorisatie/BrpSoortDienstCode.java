/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAttribuut;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Text;

/**
 * Deze enum representeert een BRP soort dienst.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpSoortDienstCode implements BrpAttribuut, Comparable<BrpSoortDienstCode> {

    /** Mutatielevernig obv doelbinding. */
    public static final BrpSoortDienstCode MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING = new BrpSoortDienstCode((short) 1);
    /** Mutatielevering obv afnemersindicatie. */
    public static final BrpSoortDienstCode MUTATIELEVERING_OP_BASIS_VAN_AFNEMERSINDICATIE = new BrpSoortDienstCode((short) 2);
    /** Onderhouden afnemersindicatie. */
    public static final BrpSoortDienstCode PLAATSEN_AFNEMERINDICATIE = new BrpSoortDienstCode((short) 3);
    /** Attendering. */
    public static final BrpSoortDienstCode ATTENDERING = new BrpSoortDienstCode((short) 4);
    /** Zoek persoon. */
    public static final BrpSoortDienstCode ZOEK_PERSOON = new BrpSoortDienstCode((short) 5);
    /** Zoek persoon met adres. */
    public static final BrpSoortDienstCode ZOEK_PERSOON_OP_ADRESGEGEVENS = new BrpSoortDienstCode((short) 6);
    /** Zoek medebewoners van persoon. */
    public static final BrpSoortDienstCode GEEF_MEDEBEWONERS_VAN_PERSOON = new BrpSoortDienstCode((short) 7);
    /** Detail persoon. */
    public static final BrpSoortDienstCode GEEF_DETAILS_PERSOON = new BrpSoortDienstCode((short) 8);
    /** Synchronisatie persoon. */
    public static final BrpSoortDienstCode SYNCHRONISATIE_PERSOON = new BrpSoortDienstCode((short) 9);
    /** Synchronisatie stamgegeven. */
    public static final BrpSoortDienstCode SYNCHRONISATIE_STAMGEGEVEN = new BrpSoortDienstCode((short) 10);
    /** Mutatielevering stamgegeven. */
    public static final BrpSoortDienstCode MUTATIELEVERING_STAMGEGEVEN = new BrpSoortDienstCode((short) 11);
    /** Selectie. */
    public static final BrpSoortDienstCode SELECTIE = new BrpSoortDienstCode((short) 12);
    /** Geef details persoon. */
    public static final BrpSoortDienstCode GEEF_DETAILS_PERSOON_BULK = new BrpSoortDienstCode((short) 13);
    /** Geef synchroniciteitsgegevens persoon. */
    public static final BrpSoortDienstCode GEEF_SYNCHRONICITEITSGEGEVENS_PERSOON = new BrpSoortDienstCode((short) 14);
    /** Geef identificerende gegevens persoon bulk. */
    public static final BrpSoortDienstCode GEEF_INDENTIFICERENDE_GEGEVENS_PERSOON_BULK = new BrpSoortDienstCode((short) 15);
    /** Geef details terugmelding. */
    public static final BrpSoortDienstCode GEEF_DETAILS_TERUGMELDING = new BrpSoortDienstCode((short) 16);
    /** Opvragen aantal personen op adres. */
    public static final BrpSoortDienstCode OPVRAGEN_AANTAL_PERSONEN_OP_ADRES = new BrpSoortDienstCode((short) 17);
    /** Aanmelding gerede twijfel. */
    public static final BrpSoortDienstCode AANMELDING_GEREDE_TWIJFEL = new BrpSoortDienstCode((short) 18);
    /** Intrekking terugmelding. */
    public static final BrpSoortDienstCode INTREKKING_TERUGMELDING = new BrpSoortDienstCode((short) 19);
    /** Verwijderen afnemerindicatie. */
    public static final BrpSoortDienstCode VERWIJDEREN_AFNEMERINDICATIE = new BrpSoortDienstCode((short) 20);

    @Text
    private final short code;

    /**
     * Maakt een BrpSoortDienstCode.
     *
     * @param brpCode
     *            BRP code
     */
    public BrpSoortDienstCode(@Text final short brpCode) {
        code = brpCode;
    }

    /**
     * Geef de waarde van code.
     *
     * @return the code
     */
    public short getCode() {
        return code;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpSoortDienstCode)) {
            return false;
        }
        final BrpSoortDienstCode castOther = (BrpSoortDienstCode) other;
        return new EqualsBuilder().append(code, castOther.code).isEquals();
    }

    @Override
    public int compareTo(final BrpSoortDienstCode o) {
        return new CompareToBuilder().append(code, o.code).build();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(code).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("code", code).toString();
    }

}
