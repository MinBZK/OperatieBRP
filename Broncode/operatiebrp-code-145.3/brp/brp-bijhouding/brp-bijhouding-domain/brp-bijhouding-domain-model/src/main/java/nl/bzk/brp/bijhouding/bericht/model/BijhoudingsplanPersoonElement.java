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
 * De BijhoudingsplanPersoonElement uit het bijhoudingsbericht.
 */
@XmlElement("bijhoudingsplanPersoon")
public final class BijhoudingsplanPersoonElement extends AbstractBmrObjecttype {

    private static final String OBJECT_TYPE = "BijhoudingsplanPersoon";

    private final PersoonGegevensElement persoon;
    private final StringElement situatieNaam;

    /**
     * Maakt een {@link BijhoudingsplanPersoonElement} object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param persoon persoon, mag niet null zijn
     * @param situatieNaam situatieNaam, mag niet null zijn
     */
    public BijhoudingsplanPersoonElement(final Map<String, String> basisAttribuutGroep, final PersoonGegevensElement persoon,
                                         final StringElement situatieNaam) {
        super(basisAttribuutGroep);
        ValidatieHelper.controleerOpNullWaarde(persoon, "persoon");
        ValidatieHelper.controleerOpNullWaarde(situatieNaam, "situatieNaam");
        this.persoon = persoon;
        this.situatieNaam = situatieNaam;
    }

    /**
     * Geeft het persoon terug.
     * @return persoon
     */
    public PersoonElement getPersoon() {
        return persoon;
    }

    /**
     * Geeft de situatie naam terug.
     * @return situatie naam
     */
    public StringElement getSituatieNaam() {
        return situatieNaam;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        return VALIDATIE_OK;
    }

    /**
     * Maakt een nieuw BijhoudingsplanPersoonElement object.
     * @param persoon persoon
     * @param situatieNaam situatieNaam
     * @return BijhoudingsplanPersoonElement
     */
    public static BijhoudingsplanPersoonElement getInstance(final PersoonGegevensElement persoon, final String situatieNaam) {
        return new BijhoudingsplanPersoonElement(
                new AttributenBuilder().objecttype(OBJECT_TYPE).build(),
                persoon,
                situatieNaam == null ? null : new StringElement(situatieNaam));
    }
}
