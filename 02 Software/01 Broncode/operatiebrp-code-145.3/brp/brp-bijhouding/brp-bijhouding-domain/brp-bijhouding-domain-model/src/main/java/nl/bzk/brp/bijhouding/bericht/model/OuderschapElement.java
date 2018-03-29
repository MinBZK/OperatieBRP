/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * het {@link OuderschapElement} voor een bijhoudingsbericht.
 */
@XmlElement("ouderschap")
public final class OuderschapElement extends AbstractBmrGroep {
    private final BooleanElement indicatieOuderUitWieKindIsGeboren;

    /**
     * Maakt een AbstractBmrGroep object.
     * @param attributen de lijst met
     * @param indicatieOuderUitWieKindIsGeboren de indicatie voor ouder uit wie kind is geboren
     */
    public OuderschapElement(final Map<String, String> attributen, final BooleanElement indicatieOuderUitWieKindIsGeboren) {
        super(attributen);
        this.indicatieOuderUitWieKindIsGeboren = indicatieOuderUitWieKindIsGeboren;
    }

    /**
     * Geeft de waarde voor indicatie ouder uit wie kind is geboren.
     * @return indicatie
     */
    public BooleanElement getIndicatieOuderUitWieKindIsGeboren() {
        return indicatieOuderUitWieKindIsGeboren;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        return VALIDATIE_OK;
    }

    /**
     * Maakt een {@link OuderschapElement} element.
     * @param isOuwkig true als het een OUWKIG betreft. Mag niet null zijn.
     * @return een nieuw ouderschap element
     */
    public static OuderschapElement getInstance(final Boolean isOuwkig) {
        return new OuderschapElement(new AttributenBuilder().objecttype("Ouderschap").build(), isOuwkig != null ? new BooleanElement(isOuwkig) : null);
    }
}
