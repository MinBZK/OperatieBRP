/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.tostring;

import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ToStringStyle (based on SHORT_PREFIX_STYLE) but using a given class name.
 */
public final class OverridenClassToStringStyle extends ToStringStyle {

    private static final long serialVersionUID = 1L;

    private final String classNameToUse;

    /**
     * Constructor.
     * 
     * @param classNameToUse
     *            class name to use
     */
    public OverridenClassToStringStyle(final String classNameToUse) {
        super();
        this.classNameToUse = classNameToUse;
        setUseClassName(false);
        setUseShortClassName(false);
        setUseIdentityHashCode(false);
    }

    /**
     * <p>
     * Append to the <code>toString</code> the start of data indicator.
     * </p>
     * 
     * @param buffer
     *            the <code>StringBuffer</code> to populate
     * @param object
     *            the <code>Object</code> to build a <code>toString</code> for
     */
    @Override
    public void appendStart(final StringBuffer buffer, final Object object) {
        appendOverridenClassName(buffer);
        super.appendStart(buffer, object);
    }

    private void appendOverridenClassName(final StringBuffer buffer) {
        buffer.append(classNameToUse);

    }

}
