/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.element;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Element;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Text;

/**
 * Deze class representeert het LO3 element voornamen (02.10). Dit is een door spaties gescheiden lijst van voornamen.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class Lo3Voornamen implements Lo3Element, Serializable {

    private static final long serialVersionUID = 1L;

    private static final String SCHEIDINGSTEKEN = " ";

    @Text
    private final String voornamen;

    /**
     * Maakt een Lo3Voornamen object.
     * 
     * @param voornamen
     *            de voornaam of voornamen, mag niet null zijn
     * @throws NullPointerException
     *             als voornamen null is
     */
    public Lo3Voornamen(@Text final String voornamen) {
        if (voornamen == null) {
            throw new NullPointerException();
        }
        this.voornamen = voornamen;
    }

    /**
     * @return een string met de voornaam of voornamen
     */
    public String getVoornamen() {
        return voornamen;
    }

    /**
     * @return een array met individuele voornamen
     */
    public String[] getVoornamenAsArray() {
        final String[] splitResultaat = voornamen.split(SCHEIDINGSTEKEN);
        final List<String> result = new ArrayList<String>();
        for (final String splitDeel : splitResultaat) {
            if ("".equals(splitDeel)) {
                continue;
            }
            result.add(splitDeel);
        }
        return result.toArray(new String[] {});
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3Voornamen)) {
            return false;
        }
        final Lo3Voornamen castOther = (Lo3Voornamen) other;
        return new EqualsBuilder().append(voornamen, castOther.voornamen).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(voornamen).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("voornamen", voornamen).toString();
    }

}
