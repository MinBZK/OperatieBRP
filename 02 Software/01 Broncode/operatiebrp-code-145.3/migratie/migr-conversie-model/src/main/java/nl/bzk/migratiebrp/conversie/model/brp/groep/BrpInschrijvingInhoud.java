/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de BRP groep Inschrijving.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpInschrijvingInhoud extends AbstractBrpGroepInhoud {

    private static final long MAX_VERSIENUMMER = 9999L;
    private static final long MIN_VERSIENUMMER = 0L;

    @Element(name = "datumInschrijving", required = true)
    private final BrpDatum datumInschrijving;
    @Element(name = "versienummer", required = true)
    private final BrpLong versienummer;
    @Element(name = "datumtijdstempel", required = true)
    private final BrpDatumTijd datumtijdstempel;

    /**
     * Maakt een BrpInschrijvingInhoud object.
     * @param datumInschrijving de datum inschrijving, mag niet null zijn
     * @param versienummer het versienummer, moet een getal tussen 0 - 9999 zijn
     * @param datumtijdstempel de datum/tijdstempel
     * @throws IllegalArgumentException als versienummer geen getal tussen de 0 en 9999 is
     * @throws NullPointerException als datumInschrijving null is
     */
    public BrpInschrijvingInhoud(
            @Element(name = "datumInschrijving", required = true) final BrpDatum datumInschrijving,
            @Element(name = "versienummer", required = true) final BrpLong versienummer,
            @Element(name = "datumtijdstempel", required = true) final BrpDatumTijd datumtijdstempel) {
        if (datumInschrijving == null) {
            throw new NullPointerException("Datum inschrijving mag niet null zijn.");
        }
        final Long unwrappedVersienummer = BrpLong.unwrap(versienummer);
        if (unwrappedVersienummer < MIN_VERSIENUMMER || unwrappedVersienummer > MAX_VERSIENUMMER) {
            throw new IllegalArgumentException(
                    String.format("versienummer moet een getal tussen %d en %d zijn maar is %d", MIN_VERSIENUMMER, MAX_VERSIENUMMER, unwrappedVersienummer));
        }
        if (datumtijdstempel == null) {
            throw new NullPointerException("Datumtijdstempel mag niet null zijn.");
        }
        this.datumInschrijving = datumInschrijving;
        this.versienummer = versienummer;
        this.datumtijdstempel = datumtijdstempel;
    }

    /**
     * Geef de waarde van datum inschrijving van BrpInschrijvingInhoud.
     * @return de waarde van datum inschrijving van BrpInschrijvingInhoud
     */
    public BrpDatum getDatumInschrijving() {
        return datumInschrijving;
    }

    /**
     * Geef de waarde van versienummer van BrpInschrijvingInhoud.
     * @return de waarde van versienummer van BrpInschrijvingInhoud
     */
    public BrpLong getVersienummer() {
        return versienummer;
    }

    /**
     * Geef de waarde van datumtijdstempel van BrpInschrijvingInhoud.
     * @return de waarde van datumtijdstempel van BrpInschrijvingInhoud
     */
    public BrpDatumTijd getDatumtijdstempel() {
        return datumtijdstempel;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud#isLeeg()
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
        return new EqualsBuilder().append(datumInschrijving, castOther.datumInschrijving)
                .append(versienummer, castOther.versienummer)
                .append(datumtijdstempel, castOther.datumtijdstempel)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datumInschrijving).append(versienummer).append(datumtijdstempel).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("datumInschrijving", datumInschrijving)
                .append("versienummer", versienummer)
                .append("datumtijdstempel", datumtijdstempel)
                .toString();
    }
}
