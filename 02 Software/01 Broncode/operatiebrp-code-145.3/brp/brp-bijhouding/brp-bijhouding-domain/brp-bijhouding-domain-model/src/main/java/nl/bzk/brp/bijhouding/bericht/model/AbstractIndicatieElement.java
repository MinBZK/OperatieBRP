/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlChild;

/**
 * De basisgroep voor alle Persoon / Indicatie elementen.
 */
public abstract class AbstractIndicatieElement extends AbstractBmrObjecttype implements IndicatieElement {

    @XmlChild(naam = "waarde")
    private final BooleanElement heeftIndicatie;

    /**
     * Maakt een nieuw AbstractIndicatieElement object.
     * @param attributen de attributen voor dit element
     * @param heeftIndicatie heeftIndicatie
     */
    protected AbstractIndicatieElement(final Map<String, String> attributen, final BooleanElement heeftIndicatie) {
        super(attributen);
        this.heeftIndicatie = heeftIndicatie;
    }

    @Override
    public final BooleanElement getHeeftIndicatie() {
        return heeftIndicatie;
    }

    @Override
    protected final List<MeldingElement> valideerInhoud() {
        return VALIDATIE_OK;
    }
}
