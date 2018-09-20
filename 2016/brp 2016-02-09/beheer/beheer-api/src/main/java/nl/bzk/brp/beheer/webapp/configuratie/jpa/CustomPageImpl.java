/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.jpa;

import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * Aangepast page object met een waarschuwing om ook een waarschuwing (vanuit de query) te kunnen versturen.
 *
 * @param <T> item type
 */
public final class CustomPageImpl<T> extends PageImpl<T> implements PageWarning {

    private static final long serialVersionUID = 1L;
    private static final int HASH_3 = 3;
    private static final int HASH_67 = 67;
    private final String warning;

    /**
     * Constructor.
     *
     * @param content content
     * @param pageable pageable
     * @param total total
     * @param warning waarschuwing
     */
    public CustomPageImpl(final List<T> content, final Pageable pageable, final long total, final String warning) {
        super(content, pageable, total);
        this.warning = warning;
    }

    @Override
    public String getWarning() {
        return warning;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof CustomPageImpl) {
            return super.equals(obj) && warning.equals(((CustomPageImpl) obj).warning);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = HASH_3;
        hash = HASH_67 * hash + Objects.hashCode(this.warning);
        return hash;
    }
}
