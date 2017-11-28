/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.brp.bijhouding.bericht.annotation.XmlChildList;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

import java.util.List;
import java.util.Map;

/**
 * Het BijhoudingsplanElement uit het bijhoudingsbericht.
 */
@XmlElement("bijhoudingsplan")
public final class BijhoudingsplanElement extends AbstractBmrObjecttype {

    private static final String OBJECT_TYPE = "Bijhoudingsplan";

    private final StringElement bijhoudingsvoorstelPartijCode;
    private final StringElement geboorteGBAPartijCode;
    @XmlChildList(listElementType = BijhoudingsplanPersoonElement.class)
    private final List<BijhoudingsplanPersoonElement> bijhoudingsplanPersonen;

    /**
     * Maakt een {@link BijhoudingsplanElement} object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param geboorteGBAPartijCode geboorte GBA Partij code
     * @param bijhoudingsvoorstelPartijCode bijhoudingsvoorstel partijcode
     * @param bijhoudingsplanPersonen bijhoudingsplan personen
     */
    public BijhoudingsplanElement(
            final Map<String, String> basisAttribuutGroep,
            final StringElement bijhoudingsvoorstelPartijCode,
            final StringElement geboorteGBAPartijCode,
            final List<BijhoudingsplanPersoonElement> bijhoudingsplanPersonen) {
        super(basisAttribuutGroep);
        ValidatieHelper.controleerOpNullWaarde(bijhoudingsvoorstelPartijCode, "bijhoudingsvoorstelPartijCode");
        ValidatieHelper.controleerOpNullWaarde(bijhoudingsplanPersonen, "bijhoudingsplanPersonen");
        this.bijhoudingsvoorstelPartijCode = bijhoudingsvoorstelPartijCode;
        this.geboorteGBAPartijCode = geboorteGBAPartijCode;
        this.bijhoudingsplanPersonen = initArrayList(bijhoudingsplanPersonen);
    }

    /**
     * Geeft het de bijhoudingsvoorstel partijcode terug.
     * @return bijhoudingsvoorstel partijcode
     */
    public StringElement getBijhoudingsvoorstelPartijCode() {
        return bijhoudingsvoorstelPartijCode;
    }

    /**
     * Geeft de bijhoudingsplan personen terug.
     * @return bijhoudingsplan personen
     */
    public List<BijhoudingsplanPersoonElement> getBijhoudingsplanPersonen() {
        return bijhoudingsplanPersonen;
    }

    /**
     * Geeft de code van de GBA geboorte partij terug.
     * @return de code van de GBA geboorte partij
     */
    public StringElement getGeboorteGBAPartijCode() {
        return geboorteGBAPartijCode;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        return VALIDATIE_OK;
    }

    /**
     * Maakt een nieuw BijhoudingsplanElement object.
     * @param bijhoudingsvoorstelPartijCode bijhoudingsvoorstel partijcode
     * @param geboorteGBAPartijCode geboorte GBA partij code
     * @param bijhoudingsplanPersonen bijhoudingsplan personen
     * @return een nieuw BijhoudingsplanElement object
     */
    public static BijhoudingsplanElement getInstance(
            final StringElement bijhoudingsvoorstelPartijCode,
            final StringElement geboorteGBAPartijCode,
            final List<BijhoudingsplanPersoonElement> bijhoudingsplanPersonen) {
        return new BijhoudingsplanElement(
                new AbstractBmrGroep.AttributenBuilder().objecttype(OBJECT_TYPE).build(),
                bijhoudingsvoorstelPartijCode,
                geboorteGBAPartijCode,
                bijhoudingsplanPersonen);
    }

}
