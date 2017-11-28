/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.List;
import java.util.Map;

/**
 * Een identificeerbaar element binnen een bijhoudingsbericht dat wordt aangeduid als groep in het BMR.
 */
public interface BmrGroep extends Element {

    /**
     * Geef de waarde van communicatieId.
     *
     * @return de communicatieId
     */
    String getCommunicatieId();

    /**
     * Geef de waarde van voorkomen sleutel.
     *
     * @return de voorkomensleutel
     */
    String getVoorkomenSleutel();

    /**
     * Geef de waarde van attributen.
     *
     * @return attributen
     */
    Map<String, String> getAttributen();

    /**
     * Valideert de inhoud van de groep en de inhoud van de groepen in deze groep en geeft een lege lijst als resultaat
     * wanneer er geen fouten zijn en anders een lijst met daarin de gevonden fouten.
     *
     * @return de lijst met meldingen betreffende de gevonden fouten
     */
    List<MeldingElement> valideer();
}
