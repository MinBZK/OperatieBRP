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
 * De BijhoudingsvoorstelVerzoekBerichtElement uit het notificatiebericht.
 */
@XmlElement("bijhoudingsvoorstelVerzoekbericht")
public final class BijhoudingsvoorstelVerzoekBerichtElement extends AbstractBmrGroep {

    private final BijhoudingVerzoekBerichtImpl bijhoudingVerzoekBericht;

    /**
     * Maakt een {@link BijhoudingsvoorstelVerzoekBerichtElement} object.
     *
     * @param basisAttribuutGroep      de basis attribuutgroep
     * @param bijhoudingVerzoekBericht bijhoudingVerzoekBericht
     */
    public BijhoudingsvoorstelVerzoekBerichtElement(
            final Map<String, String> basisAttribuutGroep,
            final BijhoudingVerzoekBerichtImpl bijhoudingVerzoekBericht) {
        super(basisAttribuutGroep);
        ValidatieHelper.controleerOpNullWaarde(bijhoudingVerzoekBericht, "bijhoudingVerzoekBericht");
        this.bijhoudingVerzoekBericht = bijhoudingVerzoekBericht;
    }

    /**
     * Geef de waarde van bijhoudingVerzoekBericht.
     *
     * @return bijhoudingVerzoekBericht
     */
    public BijhoudingVerzoekBericht getBijhoudingVerzoekBericht() {
        return bijhoudingVerzoekBericht;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        return VALIDATIE_OK;
    }
}
