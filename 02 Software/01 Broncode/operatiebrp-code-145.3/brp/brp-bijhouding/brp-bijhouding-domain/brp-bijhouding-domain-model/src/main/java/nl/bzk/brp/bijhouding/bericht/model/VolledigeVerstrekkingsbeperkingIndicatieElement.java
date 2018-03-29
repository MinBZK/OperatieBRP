/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.Map;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * Het Volledige Verstrekkingsbeperking Indicatie element uit een bijhoudingsbericht.
 */
@XmlElement("volledigeVerstrekkingsbeperking")
public class VolledigeVerstrekkingsbeperkingIndicatieElement extends AbstractIndicatieElement {

    /**
     * Maakt een nieuw VolledigeVerstrekkingsbeperkingIndicatieElement object.
     *
     * @param attributen de attributen voor dit element
     * @param heeftIndicatie heeftIndicatie
     */
    public VolledigeVerstrekkingsbeperkingIndicatieElement(Map<String, String> attributen, BooleanElement heeftIndicatie) {
        super(attributen, heeftIndicatie);
    }
}
