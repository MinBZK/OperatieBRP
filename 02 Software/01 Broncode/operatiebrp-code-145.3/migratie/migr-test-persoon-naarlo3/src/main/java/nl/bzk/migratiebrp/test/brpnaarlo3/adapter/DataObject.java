/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Generieke weergave van ingelezen informatie.
 */
public final class DataObject {
    private final List<String> headers = new ArrayList<>();
    private final List<List<String>> data = new ArrayList<>();

    /**
     * Registreer een header.
     * @param header header
     */
    public void header(final String header) {
        headers.add(header);
    }

    /**
     * Registreer data.
     * @param deData data
     */
    public void data(final List<String> deData) {
        this.data.add(deData);
    }

    /**
     * Geef alle headers.
     * @return headers
     */
    public List<String> getHeaders() {
        return headers;
    }

    /**
     * Geef alle data.
     * @return data
     */
    public List<List<String>> getData() {
        return data;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("headers", headers)
                .append("data", data)
                .toString();
    }
}
