/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlChildList;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * Het gegeven in onderzoek element uit het bijhoudingsbericht.
 */
@XmlElement("onderzoek")
public final class OnderzoekElement extends AbstractBmrObjecttype implements BmrEntiteit<BijhoudingOnderzoek> {

    private static final String OBJECTTYPE = "Onderzoek";

    private final OnderzoekGroepElement onderzoekGroep;
    @XmlChildList(listElementType = GegevenInOnderzoekElement.class)
    private final List<GegevenInOnderzoekElement> gegevensInOnderzoek;

    /**
     * Maak een OnderzoekElement object.
     * @param attributen de attributen
     * @param onderzoekGroep onderzoekGroep
     * @param gegevensInOnderzoek gegevensInOnderzoek
     */
    public OnderzoekElement(final Map<String, String> attributen, final OnderzoekGroepElement onderzoekGroep,
                            final List<GegevenInOnderzoekElement> gegevensInOnderzoek) {
        super(attributen);
        this.onderzoekGroep = onderzoekGroep;
        this.gegevensInOnderzoek = initArrayList(gegevensInOnderzoek);
    }

    @Override
    public boolean inObjectSleutelIndex() {
        return true;
    }

    /**
     * Geef de waarde van onderzoekGroep.
     * @return onderzoekGroep
     */
    public OnderzoekGroepElement getOnderzoekGroep() {
        return onderzoekGroep;
    }

    /**
     * Geef de waarde van gegevensInOnderzoek.
     * @return gegevensInOnderzoek
     */
    public List<GegevenInOnderzoekElement> getGegevensInOnderzoek() {
        return Collections.unmodifiableList(gegevensInOnderzoek);
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        controleerGeldigheidElement(meldingen);
        return meldingen;
    }

    private void controleerGeldigheidElement(final List<MeldingElement> meldingen) {
        if (onderzoekGroep.getDatumAanvang() != null) {
            for (final GegevenInOnderzoekElement gegevenInOnderzoekElement : gegevensInOnderzoek) {
                gegevenInOnderzoekElement.controleerGeldigheidElement(meldingen, onderzoekGroep.getDatumAanvang().getWaarde());
            }
        }
    }

    /**
     * Helper methode voor het maken van een OnderzoekElement.
     * @param communicatieId communicatie id
     * @param onderzoekGroep onderzoek groep
     * @param gegevensInOnderzoek gegevens in onderzoek
     * @return een nieuw OnderzoekElement
     */
    public static OnderzoekElement getInstance(final String communicatieId, final OnderzoekGroepElement onderzoekGroep,
                                               final List<GegevenInOnderzoekElement> gegevensInOnderzoek) {
        return OnderzoekElement.getInstance(communicatieId, null, onderzoekGroep, gegevensInOnderzoek);
    }

    /**
     * Helper methode voor het maken van een OnderzoekElement.
     * @param communicatieId communicatie id
     * @param objectSleutel objectsleutel
     * @param onderzoekGroep onderzoek groep
     * @param gegevensInOnderzoek gegevens in onderzoek
     * @return een nieuw OnderzoekElement
     */
    public static OnderzoekElement getInstance(final String communicatieId, final String objectSleutel, final OnderzoekGroepElement onderzoekGroep,
                                               final List<GegevenInOnderzoekElement> gegevensInOnderzoek) {
        return new OnderzoekElement(new AttributenBuilder().communicatieId(communicatieId).objecttype(OBJECTTYPE).objectSleutel(objectSleutel).build(),
                onderzoekGroep, gegevensInOnderzoek);
    }

    @Override
    public Class<BijhoudingOnderzoek> getEntiteitType() {
        return BijhoudingOnderzoek.class;
    }
}
