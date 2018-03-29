/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.algemeen;

import java.util.Collections;
import java.util.List;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ObjectElement;

/**
 * StamgegevenTabel.
 */
public final class StamgegevenTabel {

    private final ObjectElement objectElement;
    private final List<AttribuutElement> stamgegevenAttributenInBericht;
    private final List<AttribuutElement> stamgegevenAttributen;

    /**
     * Instantiates a new Stamgegeven tabel.
     * @param objectElement objectElement
     * @param stamgegevenAttributenInBericht stamgegevenAttributenInBericht
     * @param stamgegevenAttributen stamgegevenAttributenInBericht
     */
    public StamgegevenTabel(final ObjectElement objectElement,
                            final List<AttribuutElement> stamgegevenAttributenInBericht, final List<AttribuutElement> stamgegevenAttributen) {
        this.objectElement = objectElement;
        this.stamgegevenAttributenInBericht = Collections.unmodifiableList(stamgegevenAttributenInBericht);
        this.stamgegevenAttributen = Collections.unmodifiableList(stamgegevenAttributen);
    }

    /**
     * Gets object element.
     * @return the object element
     */
    public ObjectElement getObjectElement() {
        return objectElement;
    }

    /**
     * Gets naam.
     * @return the naam
     */
    public String getNaam() {
        return objectElement.getXmlNaam();
    }

    /**
     * Gets stamgegeven attributen in bericht.
     * @return the stamgegeven attributen in bericht
     */
    public List<AttribuutElement> getStamgegevenAttributenInBericht() {
        return stamgegevenAttributenInBericht;
    }

    /**
     * Gets stamgegeven attributen.
     * @return the stamgegeven attributen
     */
    public List<AttribuutElement> getStamgegevenAttributen() {
        return stamgegevenAttributen;
    }
}
