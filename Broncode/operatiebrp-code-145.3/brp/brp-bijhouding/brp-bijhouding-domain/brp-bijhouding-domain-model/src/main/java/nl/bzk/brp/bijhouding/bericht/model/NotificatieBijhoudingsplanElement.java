/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlChildList;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * Het BijhoudingsplanElement uit het bijhoudingsbericht.
 */
@XmlElement("bijhoudingsplan")
public final class NotificatieBijhoudingsplanElement extends AbstractBmrObjecttype {

    private static final String OBJECT_TYPE = "Bijhoudingsplan";

    private final StringElement bijhoudingsvoorstelPartijCode;
    private final StringElement gbaGeboortePartijCode;
    private final BijhoudingsvoorstelVerzoekBerichtElement bijhoudingsvoorstelVerzoekBericht;
    private final BijhoudingsvoorstelResultaatBerichtElement bijhoudingsvoorstelResultaatBericht;
    @XmlChildList(listElementType = BijhoudingsplanPersoonElement.class)
    private final List<BijhoudingsplanPersoonElement> bijhoudingsplanPersonen;

    /**
     * Maakt een {@link NotificatieBijhoudingsplanElement} object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param bijhoudingsvoorstelPartijCode bijhoudingsvoorstel partijcode
     * @param gbaGeboortePartijCode gba geboorte partijcode
     * @param bijhoudingsvoorstelVerzoekBericht het bijhoudingsverzoekbericht
     * @param bijhoudingsvoorstelResultaatBericht het bijhoudingsantwoordbericht
     * @param bijhoudingsplanPersonen bijhoudingsplan personen
     */
    public NotificatieBijhoudingsplanElement(
            final Map<String, String> basisAttribuutGroep,
            final StringElement bijhoudingsvoorstelPartijCode,
            final StringElement gbaGeboortePartijCode,
            final BijhoudingsvoorstelVerzoekBerichtElement bijhoudingsvoorstelVerzoekBericht,
            final BijhoudingsvoorstelResultaatBerichtElement bijhoudingsvoorstelResultaatBericht,
            final List<BijhoudingsplanPersoonElement> bijhoudingsplanPersonen) {
        super(basisAttribuutGroep);
        ValidatieHelper.controleerOpNullWaarde(bijhoudingsvoorstelPartijCode, "bijhoudingsvoorstelPartijCode");
        ValidatieHelper.controleerOpNullWaarde(bijhoudingsplanPersonen, "bijhoudingsplanPersonen");
        this.bijhoudingsvoorstelPartijCode = bijhoudingsvoorstelPartijCode;
        this.gbaGeboortePartijCode = gbaGeboortePartijCode;
        this.bijhoudingsvoorstelVerzoekBericht = bijhoudingsvoorstelVerzoekBericht;
        this.bijhoudingsvoorstelResultaatBericht = bijhoudingsvoorstelResultaatBericht;
        this.bijhoudingsplanPersonen = initArrayList(bijhoudingsplanPersonen);
    }

    /**
     * Geeft de bijhoudingsvoorstel partijcode terug.
     * @return bijhoudingsvoorstel partijcode
     */
    public StringElement getBijhoudingsvoorstelPartijCode() {
        return bijhoudingsvoorstelPartijCode;
    }

    /**
     * Geeft de GBA geboorte partijcode terug.
     * @return GBA geboorte partijcode
     */
    public StringElement getGbaGeboortePartijCode() {
        return gbaGeboortePartijCode;
    }

    /**
     * Geeft de bijhoudingsplan personen terug.
     * @return bijhoudingsplan personen
     */
    public List<BijhoudingsplanPersoonElement> getBijhoudingsplanPersonen() {
        return bijhoudingsplanPersonen;
    }

    /**
     * Geeft het bijhouding verzoek bericht terug.
     * @return bijhouding verzoek bericht
     */
    public BijhoudingsvoorstelVerzoekBerichtElement getBijhoudingsvoorstelVerzoekBericht() {
        return bijhoudingsvoorstelVerzoekBericht;
    }

    /**
     * Geeft het bijhouding antwoord bericht terug.
     * @return bijhouding antwoord bericht
     */
    public BijhoudingsvoorstelResultaatBerichtElement getBijhoudingsvoorstelResultaatBericht() {
        return bijhoudingsvoorstelResultaatBericht;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        return VALIDATIE_OK;
    }

    /**
     * Maak een NotificatieBijhoudingsplanElement object.
     * @param bijhoudingsvoorstelPartijCode bijhoudingsvoorstelPartijCode
     * @param bijhoudingVerzoekBericht bijhoudingVerzoekBericht
     * @param gbaGeboortePartijElement gba geboorte partij {@link StringElement}
     * @param bijhoudingAntwoordBericht bijhoudingAntwoordBericht
     * @param bijhoudingsplanPersonen bijhoudingsplanPersonen
     * @return NotificatieBijhoudingsplanElement
     */
    public static NotificatieBijhoudingsplanElement getInstance(
            final String bijhoudingsvoorstelPartijCode,
            final StringElement gbaGeboortePartijElement,
            final BijhoudingVerzoekBericht bijhoudingVerzoekBericht,
            final BijhoudingAntwoordBericht bijhoudingAntwoordBericht,
            final List<BijhoudingsplanPersoonElement> bijhoudingsplanPersonen) {
        final StringElement bijhoudingsvoorstelPartijCodeElement =
                bijhoudingsvoorstelPartijCode == null ? null : new StringElement(bijhoudingsvoorstelPartijCode);

        final BijhoudingsvoorstelVerzoekBerichtElement bijhoudingsvoorstelVerzoekBerichtElement =
                bijhoudingVerzoekBericht == null ? null : new BijhoudingsvoorstelVerzoekBerichtElement(
                        new AttributenBuilder().build(),
                        (BijhoudingVerzoekBerichtImpl) bijhoudingVerzoekBericht);

        final BijhoudingsvoorstelResultaatBerichtElement bijhoudingsvoorstelResultaatBerichtElement =
                bijhoudingAntwoordBericht == null ? null
                        : new BijhoudingsvoorstelResultaatBerichtElement(new AttributenBuilder().build(), bijhoudingAntwoordBericht);

        return new NotificatieBijhoudingsplanElement(new AttributenBuilder().objecttype(OBJECT_TYPE).build(), bijhoudingsvoorstelPartijCodeElement,
                gbaGeboortePartijElement, bijhoudingsvoorstelVerzoekBerichtElement, bijhoudingsvoorstelResultaatBerichtElement, bijhoudingsplanPersonen);
    }
}
