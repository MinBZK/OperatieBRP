/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view.blob;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;

/**
 * View van een groep.
 */
public final class BlobViewGroep {

    private final Element element;
    private final List<BlobViewRecord> records = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param element
     *            element
     */
    BlobViewGroep(final Element element) {
        this.element = element;
    }

    /**
     * Geeft Element terug.
     * @return element
     */
    public Element getElement() {
        return element;
    }

    /**
     * Geeft records terug.
     * @return collectie van BlobViewRecords
     */
    public Collection<BlobViewRecord> getRecords() {
        return records;
    }

    /**
     * Voeg een record toe.
     * 
     * @param blobViewRecord
     *            record
     */
    public void addRecord(final BlobViewRecord blobViewRecord) {
        records.add(blobViewRecord);
    }

    @Override
    public String toString() {
        return "BlobViewGroep [element=" + element + ", records=" + records + "]";
    }
}
