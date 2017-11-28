/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.bericht;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;

/**
 * Interface voor het wegschrijven van een xml fragment.
 */
public final class BerichtConstants {

    /**
     * XML naam voor objecttype.
     */
    public static final String OBJECTTYPE = "objecttype";

    /**
     * XML naam voor objectsleutel.
     */
    public static final String OBJECT_SLEUTEL = "objectSleutel";

    /**
     * XML naam voor verwerkingssoort.
     */
    public static final String VERWERKINGSSOORT = "verwerkingssoort";

    /**
     * Padding posities voor partij code.
     */
    public static final int PARTIJ_CODE_PADDING_POSITIES = ElementHelper.getAttribuutElement(Element.PARTIJ_CODE.getId()).getMininumLengte();

    /**
     * Padding posities voor rechtsgrondcode.
     */
    public static final int RECHTSGROND_CODE_PADDING_POSITIES = ElementHelper.getAttribuutElement(Element.RECHTSGROND_CODE.getId()).getMininumLengte();

    /**
     * Padding opvulwaarde 0.
     */
    public static final String PADDING_WAARDE_0 = "0";

    private BerichtConstants() {

    }

}
