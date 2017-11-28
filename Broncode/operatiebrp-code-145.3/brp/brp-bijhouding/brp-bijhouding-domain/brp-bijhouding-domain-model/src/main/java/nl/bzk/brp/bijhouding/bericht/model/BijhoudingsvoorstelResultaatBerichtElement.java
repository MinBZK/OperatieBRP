/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

import java.util.List;
import java.util.Map;

/**
 * De BijhoudingsvoorstelResultaatBerichtElement uit het notificatiebericht.
 */
@XmlElement("bijhoudingsvoorstelResultaatbericht")
public final class BijhoudingsvoorstelResultaatBerichtElement extends AbstractBmrGroep {

    private final BijhoudingAntwoordBericht bijhoudingAntwoordBericht;

    /**
     * Maakt een {@link BijhoudingsvoorstelResultaatBerichtElement} object.
     *
     * @param basisAttribuutGroep       de basis attribuutgroep
     * @param bijhoudingAntwoordBericht bijhoudingAntwoordBericht
     */
    public BijhoudingsvoorstelResultaatBerichtElement(
            final Map<String, String> basisAttribuutGroep,
            final BijhoudingAntwoordBericht bijhoudingAntwoordBericht) {
        super(basisAttribuutGroep);
        ValidatieHelper.controleerOpNullWaarde(bijhoudingAntwoordBericht, "bijhoudingAntwoordBericht");
        this.bijhoudingAntwoordBericht = bijhoudingAntwoordBericht;
    }

    /**
     * Geef de waarde van bijhoudingVerzoekBericht.
     *
     * @return bijhoudingVerzoekBericht
     */
    public BijhoudingAntwoordBericht getBijhoudingAntwoordBericht() {
        return bijhoudingAntwoordBericht;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        return VALIDATIE_OK;
    }
}
