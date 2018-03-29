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
 * De PersoonBijhoudingElement.
 */
@XmlElement("bijhouding")
public final class BijhoudingElement extends AbstractBmrGroep {

    private final StringElement partijCode;

    /**
     * Maakt een {@link BijhoudingElement} object.
     *
     * @param attributen attributen, mag niet null zijn
     * @param partijCode partijCode, mag niet null zijn
     */
    public BijhoudingElement(final Map<String, String> attributen, final StringElement partijCode) {
        super(attributen);
        ValidatieHelper.controleerOpNullWaarde(partijCode, "partijCode");
        this.partijCode = partijCode;
    }

    /**
     * Geeft de partij code terug.
     * 
     * @return partij code
     */
    public StringElement getPartijCode() {
        return partijCode;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        return VALIDATIE_OK;
    }

    /**
     * Maakt een nieuw BijhoudingElement object.
     *
     * @param partijCode partijCode
     * @return BijhoudingElement
     */
    public static BijhoudingElement getInstance(final String partijCode) {
        return new BijhoudingElement(new AttributenBuilder().build(), partijCode == null ? null : new StringElement(partijCode));
    }
}
