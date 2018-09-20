/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de BRP Groep Nationaliteit.
 * 
 * Deze class is immutable en threadsafe.
 * 
 * 
 * 
 */
public final class BrpNationaliteitInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "nationaliteitCode", required = false)
    private final BrpNationaliteitCode nationaliteitCode;
    @Element(name = "redenVerkrijgingNederlandschapCode", required = false)
    private final BrpRedenVerkrijgingNederlandschapCode redenVerkrijgingNederlandschapCode;
    @Element(name = "redenVerliesNederlandschapCode", required = false)
    private final BrpRedenVerliesNederlandschapCode redenVerliesNederlandschapCode;

    /**
     * Maakt een BrpNationaliteitInhoud object.
     * 
     * @param nationaliteitCode
     *            code die een BRP nationaliteit identificeert, mag null zijn
     * @param redenVerkrijgingNederlandschapCode
     *            code die een BRP reden verkrijging nederlandschap identificeert, mag null zijn
     * @param redenVerliesNederlandschapCode
     *            code de een reden verlies nederlanschap identificeert, mag null zijn
     */
    public BrpNationaliteitInhoud(
            @Element(name = "nationaliteitCode", required = false) final BrpNationaliteitCode nationaliteitCode,
            @Element(name = "redenVerkrijgingNederlandschapCode", required = false) final BrpRedenVerkrijgingNederlandschapCode redenVerkrijgingNederlandschapCode,
            @Element(name = "redenVerliesNederlandschapCode", required = false) final BrpRedenVerliesNederlandschapCode redenVerliesNederlandschapCode) {
        this.nationaliteitCode = nationaliteitCode;
        this.redenVerkrijgingNederlandschapCode = redenVerkrijgingNederlandschapCode;
        this.redenVerliesNederlandschapCode = redenVerliesNederlandschapCode;
    }

    @Override
    public boolean isLeeg() {
        // groep is leeg als nationaliteitCode en redenVerkrijging leeg zijn.
        // redenVerlies is dan wel gevuld
        return nationaliteitCode == null && redenVerkrijgingNederlandschapCode == null;
    }

    public BrpNationaliteitCode getNationaliteitCode() {
        return nationaliteitCode;
    }

    public BrpRedenVerkrijgingNederlandschapCode getRedenVerkrijgingNederlandschapCode() {
        return redenVerkrijgingNederlandschapCode;
    }

    public BrpRedenVerliesNederlandschapCode getRedenVerliesNederlandschapCode() {
        return redenVerliesNederlandschapCode;
    }

    /**
     * Vergelijkt de inhoud van deze BrpNationaliteitInhoud met het meegegeven object.
     * 
     * Deze logica wijkt in het geval van reden verlies NL nationaliteit af van het standaard equals gedrag. Wanneer
     * BrpNationaliteitInhoud X en Y met elkaar worden vergeleken en X.redenVerliesNederlandschapCode of
     * Y.redenVerliesNederlandschapCode is null, dan wordt deze verder niet meegenomen in de equals vergelijking.
     * 
     * @param other
     *            het object waarme vergeleken moet worden
     * @return true als het andere object gelijk is aan deze BRP Nationaliteit inhoud, anders false
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpNationaliteitInhoud)) {
            return false;
        }
        final BrpNationaliteitInhoud castOther = (BrpNationaliteitInhoud) other;
        final boolean result =
                new EqualsBuilder().append(nationaliteitCode, castOther.nationaliteitCode)
                        .append(redenVerkrijgingNederlandschapCode, castOther.redenVerkrijgingNederlandschapCode)
                        .isEquals();
        /* Uitzondering op standaard gedrag! */
        if (redenVerliesNederlandschapCode != null && castOther.redenVerliesNederlandschapCode != null) {
            if (!redenVerliesNederlandschapCode.equals(castOther.redenVerliesNederlandschapCode)) {
                return false;
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(nationaliteitCode).append(redenVerkrijgingNederlandschapCode)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("nationaliteitCode", nationaliteitCode)
                .append("redenVerkrijgingNederlandschapCode", redenVerkrijgingNederlandschapCode)
                .append("redenVerliesNederlandschapCode", redenVerliesNederlandschapCode).toString();
    }
}
