/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de BRP groep Inschrijving.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpInschrijvingInhoud extends AbstractBrpGroepInhoud {

    private static final int MAX_VERSIENUMMER = 9999;
    private static final int MIN_VERSIENUMMER = 0;

    @Element(name = "vorigAdministratienummer", required = false)
    private final Long vorigAdministratienummer;
    @Element(name = "volgendAdministratienummer", required = false)
    private final Long volgendAdministratienummer;
    @Element(name = "datumInschrijving", required = false)
    private final BrpDatum datumInschrijving;
    @Element(name = "versienummer", required = false)
    private final Integer versienummer;

    /**
     * Maakt een BrpInschrijvingInhoud object.
     * 
     * @param vorigAdministratienummer
     *            het vorige administratienummer, of null
     * @param volgendAdministratienummer
     *            het volgende administratienummer, of null
     * @param datumInschrijving
     *            de datum inschrijving, mag niet null zijn
     * @param versienummer
     *            het versienummer, moet een getal tussen 0 - 9999 zijn
     * @throws IllegalArgumentException
     *             als versienummer geen getal tussen de 0 en 9999 is
     * @throws NullPointerException
     *             als datumInschrijving null is
     */
    public BrpInschrijvingInhoud(
            @Element(name = "vorigAdministratienummer", required = false) final Long vorigAdministratienummer,
            @Element(name = "volgendAdministratienummer", required = false) final Long volgendAdministratienummer,
            @Element(name = "datumInschrijving", required = false) final BrpDatum datumInschrijving,
            @Element(name = "versienummer", required = false) final Integer versienummer) {
        if (datumInschrijving == null) {
            throw new NullPointerException("Datum inschrijving mag niet null zijn.");
        }
        if (versienummer < MIN_VERSIENUMMER || versienummer > MAX_VERSIENUMMER) {
            throw new IllegalArgumentException(String.format(
                    "versienummer moet een getal tussen %d en %d zijn maar is %d", MIN_VERSIENUMMER,
                    MAX_VERSIENUMMER, versienummer));
        }
        this.vorigAdministratienummer = vorigAdministratienummer;
        this.volgendAdministratienummer = volgendAdministratienummer;
        this.datumInschrijving = datumInschrijving;
        this.versienummer = versienummer;
    }

    /**
     * @return the vorigAdministratienummer, of null
     */
    public Long getVorigAdministratienummer() {
        return vorigAdministratienummer;
    }

    /**
     * @return the volgendAdministratienummer, of null
     */
    public Long getVolgendAdministratienummer() {
        return volgendAdministratienummer;
    }

    /**
     * @return the datumInschrijving
     */
    public BrpDatum getDatumInschrijving() {
        return datumInschrijving;
    }

    /**
     * @return the versienummer
     */
    public Integer getVersienummer() {
        return versienummer;
    }

    /**
     * @return false, omdat datumInschrijving en versienummer verplicht zijn is dit object nooit leeg
     */
    @Override
    public boolean isLeeg() {
        return false;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpInschrijvingInhoud)) {
            return false;
        }
        final BrpInschrijvingInhoud castOther = (BrpInschrijvingInhoud) other;
        return new EqualsBuilder().append(vorigAdministratienummer, castOther.vorigAdministratienummer)
                .append(volgendAdministratienummer, castOther.volgendAdministratienummer)
                .append(datumInschrijving, castOther.datumInschrijving).append(versienummer, castOther.versienummer)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(vorigAdministratienummer).append(volgendAdministratienummer)
                .append(datumInschrijving).append(versienummer).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("vorigAdministratienummer", vorigAdministratienummer)
                .append("volgendAdministratienummer", volgendAdministratienummer)
                .append("datumInschrijving", datumInschrijving).append("versienummer", versienummer).toString();
    }
}
