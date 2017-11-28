/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

import java.util.List;
import java.util.Map;

/**
 * De AdministratieveHandelingPlanElement uit het notificatiebericht.
 */
@XmlElement("administratieveHandelingPlan")
public final class AdministratieveHandelingPlanElement extends AbstractBmrObjecttype {

    private static final String OBJECT_TYPE = "AdministratieveHandeling";

    private final StringElement soortNaam;
    private final StringElement categorieNaam;
    private final StringElement partijCode;
    private final NotificatieBijhoudingsplanElement bijhoudingsplan;

    /**
     * Maakt een AdministratieveHandelingAntwoordElement object.
     *
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param soortNaam           soortNaam, mag niet null zijn
     * @param categorieNaam       categorieNaam, mag niet null zijn
     * @param partijCode          partijCode, mag niet null zijn
     * @param bijhoudingsplan     bijhoudingsplan
     */
    public AdministratieveHandelingPlanElement(
            final Map<String, String> basisAttribuutGroep,
            final StringElement soortNaam,
            final StringElement categorieNaam,
            final StringElement partijCode,
            final NotificatieBijhoudingsplanElement bijhoudingsplan) {
        super(basisAttribuutGroep);
        ValidatieHelper.controleerOpNullWaarde(soortNaam, "soortNaam");
        ValidatieHelper.controleerOpNullWaarde(categorieNaam, "categorieNaam");
        ValidatieHelper.controleerOpNullWaarde(partijCode, "partijCode");
        ValidatieHelper.controleerOpNullWaarde(bijhoudingsplan, "bijhoudingsplan");
        this.soortNaam = soortNaam;
        this.categorieNaam = categorieNaam;
        this.partijCode = partijCode;
        this.bijhoudingsplan = bijhoudingsplan;
    }

    /**
     * Geef de waarde van soortNaam.
     *
     * @return soortNaam
     */
    public StringElement getSoortNaam() {
        return soortNaam;
    }

    /**
     * Geef de waarde van categorieNaam.
     *
     * @return categorieNaam
     */
    public StringElement getCategorieNaam() {
        return categorieNaam;
    }

    /**
     * Geef de waarde van partijCode.
     *
     * @return partijCode
     */
    public StringElement getPartijCode() {
        return partijCode;
    }

    /**
     * Geef de waarde van het bijhoudingsplan terug.
     *
     * @return bijhoudingsplan
     */
    public NotificatieBijhoudingsplanElement getBijhoudingsplan() {
        return bijhoudingsplan;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        return VALIDATIE_OK;
    }

    /**
     * Maakt een AdministratieveHandelingPlanElement object.
     *
     * @param soortAdministratieveHandeling de soort administratieve handeling
     * @param partijCode                    de partij code
     * @param bijhoudingsplan               het bijhoudingsplan
     * @return AdministratieveHandelingPlanElement
     */
    public static AdministratieveHandelingPlanElement getInstance(
            final SoortAdministratieveHandeling soortAdministratieveHandeling,
            final String partijCode,
            final NotificatieBijhoudingsplanElement bijhoudingsplan) {
        return new AdministratieveHandelingPlanElement(
                new AttributenBuilder().objecttype(OBJECT_TYPE).build(),
                new StringElement(soortAdministratieveHandeling.getNaam()),
                new StringElement(soortAdministratieveHandeling.getCategorie().getNaam()),
                new StringElement(partijCode),
                bijhoudingsplan);
    }
}
