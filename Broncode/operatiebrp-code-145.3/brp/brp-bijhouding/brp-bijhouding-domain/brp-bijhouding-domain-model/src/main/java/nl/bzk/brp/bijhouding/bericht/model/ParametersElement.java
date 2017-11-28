/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * De parameters voor een Bijhoudingsbericht.
 */
@XmlElement("parameters")
public final class ParametersElement extends AbstractBmrGroep {

    private final StringElement verwerkingswijze;

    /**
     * Maakt een ParametersElement object.
     *  @param attributen de attributen
     * @param verwerkingswijze verwerkingswijze
     */
    public ParametersElement(final Map<String, String> attributen, final StringElement verwerkingswijze) {
        super(attributen);
        ValidatieHelper.controleerOpNullWaarde(verwerkingswijze, "verwerkingswijze");
        this.verwerkingswijze = verwerkingswijze;
    }

    /**
     * Geef de waarde van verwerkingswijze.
     *
     * @return verwerkingswijze
     */
    public StringElement getVerwerkingswijze() {
        return verwerkingswijze;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        return VALIDATIE_OK;
    }
}
