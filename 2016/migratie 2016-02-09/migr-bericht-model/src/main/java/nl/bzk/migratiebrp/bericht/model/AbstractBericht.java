/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Abstract bericht.
 */
public abstract class AbstractBericht implements Bericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Map<Class<?>, List<String>> EXCLUDED_FIELD_NAMES = new HashMap<>();
    private static final int HASHCODE_INITIAL_NON_ZERO_ODD_NUMBER = 17;
    private static final int HASHCODE_MULTIPLIER_NON_ZERO_ODD_NUMBER = 37;

    private String messageId;
    private String correlationId;

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.Bericht#getMessageId()
     */
    @Override
    public final String getMessageId() {
        return messageId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.Bericht#setMessageId(java.lang.String)
     */
    @Override
    public final void setMessageId(final String messageId) {
        this.messageId = messageId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.Bericht#getCorrelationId()
     */
    @Override
    public final String getCorrelationId() {
        return correlationId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.Bericht#setCorrelationId(java.lang.String)
     */
    @Override
    public final void setCorrelationId(final String correlationId) {
        this.correlationId = correlationId;
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de integer representatie van de string waarde terug.
     *
     * @param value
     *            De string waarde.
     * @return De integer representatie van de string waarde.
     */
    protected static final Integer asInteger(final String value) {
        if (value == null || "".equals(value)) {
            return null;
        } else {
            return Integer.parseInt(value);
        }
    }

    /**
     * Geeft de integer representatie van de biginteger waarde terug.
     *
     * @param value
     *            De biginteger waarde.
     * @return De long representatie van de biginteger waarde.
     */
    protected static final Integer asInteger(final BigInteger value) {
        if (value == null) {
            return null;
        } else {
            return value.intValue();
        }
    }

    /**
     * Geeft de long representatie van de string waarde terug.
     *
     * @param value
     *            De string waarde.
     * @return De long representatie van de string waarde.
     */
    protected static final Long asLong(final String value) {
        if (value == null || "".equals(value)) {
            return null;
        } else {
            return Long.parseLong(value);
        }
    }

    /**
     * Geeft de string representatie van een waarde terug.
     *
     * @param value
     *            De waarde.
     * @return De string representatie van de waarde.
     */
    protected static final String asString(final Object value) {
        if (value == null) {
            return null;
        } else {
            return String.valueOf(value);
        }
    }

    /* ************************************************************************************************************* */

    /**
     * Registereer velden om te negeren bij equals, hashCode en toString.
     *
     * @param clazz
     *            class
     * @param excludedFieldNames
     *            te negeren veld namen
     */
    protected static void registerExcludedFieldNames(final Class<?> clazz, final String[] excludedFieldNames) {
        EXCLUDED_FIELD_NAMES.put(clazz, Arrays.asList(excludedFieldNames));
    }

    private static String[] bepaalExcludedFieldNames(final Class<?> clazz) {
        final List<String> result = new ArrayList<>();
        Class<?> theClazz = clazz;
        while (theClazz != null) {
            if (EXCLUDED_FIELD_NAMES.containsKey(theClazz)) {
                result.addAll(EXCLUDED_FIELD_NAMES.get(theClazz));
            }
            theClazz = theClazz.getSuperclass();
        }

        return result.isEmpty() ? null : result.toArray(new String[result.size()]);
    }

    @Override
    public final String toString() {
        final ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE, null, null, true, false);
        builder.setExcludeFieldNames(bepaalExcludedFieldNames(getClass()));
        return builder.toString();
    }

    @Override
    public final boolean equals(final Object other) {
        return EqualsBuilder.reflectionEquals(this, other, true, null, bepaalExcludedFieldNames(getClass()));
    }

    @Override
    public final int hashCode() {
        return HashCodeBuilder.reflectionHashCode(
            HASHCODE_INITIAL_NON_ZERO_ODD_NUMBER,
            HASHCODE_MULTIPLIER_NON_ZERO_ODD_NUMBER,
            this,
            true,
            null,
            bepaalExcludedFieldNames(getClass()));
    }
}
