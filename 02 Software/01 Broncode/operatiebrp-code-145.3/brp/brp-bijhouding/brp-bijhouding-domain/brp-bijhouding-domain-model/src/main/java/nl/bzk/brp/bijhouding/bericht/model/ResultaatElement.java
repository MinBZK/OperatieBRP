/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingResultaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

import java.util.List;
import java.util.Map;

/**
 * Het ResultaatElement voor een bijhoudingsbericht.
 */
@XmlElement("resultaat")
public final class ResultaatElement extends AbstractBmrGroep {

    private final StringElement verwerking;
    private final StringElement bijhouding;
    private final StringElement hoogsteMeldingsniveau;

    /**
     * Maakt een ResultaatElement object.
     *
     * @param attributen            de lijst met attributen
     * @param verwerking            verwerking
     * @param bijhouding            bijhouding
     * @param hoogsteMeldingsniveau hoogsteMeldingsniveau
     */
    public ResultaatElement(
            final Map<String, String> attributen,
            final StringElement verwerking,
            final StringElement bijhouding,
            final StringElement hoogsteMeldingsniveau) {
        super(attributen);
        ValidatieHelper.controleerOpNullWaarde(verwerking, "verwerking");
        ValidatieHelper.controleerOpNullWaarde(hoogsteMeldingsniveau, "hoogsteMeldingsniveau");
        this.verwerking = verwerking;
        this.bijhouding = bijhouding;
        this.hoogsteMeldingsniveau = hoogsteMeldingsniveau;
    }

    /**
     * Geef de waarde van verwerking.
     *
     * @return verwerking
     */
    public StringElement getVerwerking() {
        return verwerking;
    }

    /**
     * Geef de waarde van bijhouding.
     *
     * @return bijhouding
     */
    public StringElement getBijhouding() {
        return bijhouding;
    }

    /**
     * Geef de waarde van hoogsteMeldingsniveau.
     *
     * @return hoogsteMeldingsniveau
     */
    public StringElement getHoogsteMeldingsniveau() {
        return hoogsteMeldingsniveau;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        return VALIDATIE_OK;
    }

    /**
     * Maakt een {@link ResultaatElement} object.
     *
     * @param verwerkingsResultaat    verwerking resultaat
     * @param bijhoudingResultaat     bijhoudings resultaat
     * @param meldingMetHoogsteNiveau melding met de hoogste niveau
     * @return een nieuw {@link ResultaatElement}
     */
    public static ResultaatElement getInstance(
            final VerwerkingsResultaat verwerkingsResultaat,
            final BijhoudingResultaat bijhoudingResultaat,
            final SoortMelding meldingMetHoogsteNiveau) {
        return new ResultaatElement(
                new AttributenBuilder().build(),
                StringElement.getInstance(verwerkingsResultaat.getNaam()),
                bijhoudingResultaat == null ? null : StringElement.getInstance(bijhoudingResultaat.getNaam()),
                StringElement.getInstance(meldingMetHoogsteNiveau.getNaam()));
    }
}
