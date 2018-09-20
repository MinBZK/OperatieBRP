/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * LO3 Inhoud categorie.
 * 
 */
public final class Lo3InhoudCategorie {

    private static final int CATEGORIE_LENGTE_LENGTE = 3;

    private static final int CATEGORIENUMMER_LENGTE = 2;

    private static final Pattern CATEGORIE_PATTERN = Pattern.compile("^[0-9]{2}$");

    private final List<Lo3InhoudElement> elementen = new ArrayList<Lo3InhoudElement>();
    private byte[] bytes;

    private final String categorie;

    /**
     * Maak een nieuwe categorie aan.
     * 
     * @param categorie
     *            categorienummer (moet 2 cijfers zijn)
     * @throws IllegalArgumentException
     *             als het categorienummer niet uit twee cijfers bestaat
     * 
     */
    Lo3InhoudCategorie(final String categorie) {
        if (!CATEGORIE_PATTERN.matcher(categorie).matches()) {
            throw new IllegalArgumentException("Categorie(nummer) moet bestaan uit twee cijfers");
        }
        bytes = null;
        this.categorie = categorie;

    }

    /**
     * Voeg een element toe aan deze categorie.
     * 
     * @param element
     *            element
     */
    public void addElement(final Lo3InhoudElement element) {
        bytes = null;
        elementen.add(element);
    }

    /**
     * @return de categorie naam.
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @return de elementen waaruit deze categorie bestaat.
     */
    public List<Lo3InhoudElement> getElementen() {
        return Collections.unmodifiableList(elementen);
    }

    /**
     * Haal het element op met de meegegeven elementnaam.
     * 
     * @param elementNaam
     *            de naam van het element
     * @return het corresponderende Lg01Element of null, als deze niet in de categorie voorkomt
     */
    public Lo3InhoudElement getElement(final String elementNaam) {
        Lo3InhoudElement gevondenElement = null;
        for (final Lo3InhoudElement lg01element : elementen) {
            if (lg01element != null && lg01element.getElement().equals(elementNaam)) {
                gevondenElement = lg01element;
                break;
            }
        }
        return gevondenElement;
    }

    /**
     * Geef de byte weergave van deze categorie.
     * 
     * @return teletex encoded byte weergave van deze categorie
     */
    public byte[] getBytes() {
        if (bytes == null) {
            int size = 0;
            for (final Lo3InhoudElement element : elementen) {
                if (element != null) {
                    size = size + element.getBytes().length;
                }
            }

            bytes = new byte[CATEGORIENUMMER_LENGTE + CATEGORIE_LENGTE_LENGTE + size];
            System.arraycopy(categorie.getBytes(), 0, bytes, 0, CATEGORIENUMMER_LENGTE);
            System.arraycopy(String.format("%1$03d", size).getBytes(), 0, bytes, CATEGORIENUMMER_LENGTE,
                    CATEGORIE_LENGTE_LENGTE);

            int index = CATEGORIENUMMER_LENGTE + CATEGORIE_LENGTE_LENGTE;
            for (final Lo3InhoudElement element : elementen) {
                if (element != null) {
                    System.arraycopy(element.getBytes(), 0, bytes, index, element.getBytes().length);
                    index = index + element.getBytes().length;
                }
            }
        }
        return bytes;
    }
}
