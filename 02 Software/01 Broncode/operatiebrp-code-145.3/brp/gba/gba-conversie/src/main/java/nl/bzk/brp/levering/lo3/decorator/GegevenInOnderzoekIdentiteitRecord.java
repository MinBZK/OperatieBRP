/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.decorator;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ElementObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;

/**
 * Gegeven in onderzoek: identiteit.
 */
public final class GegevenInOnderzoekIdentiteitRecord extends RecordDecorator {

    private final AttribuutElement elementElement = ElementHelper.getAttribuutElement(Element.GEGEVENINONDERZOEK_ELEMENTNAAM.getId());
    private final AttribuutElement objectSleutelGegevenElement =
            ElementHelper.getAttribuutElement(Element.GEGEVENINONDERZOEK_OBJECTSLEUTELGEGEVEN.getId());
    private final AttribuutElement voorkomenSleutelGegevenElement =
            ElementHelper.getAttribuutElement(Element.GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN.getId());

    /**
     * Constructor.
     * @param metaRecord record
     */
    GegevenInOnderzoekIdentiteitRecord(final MetaRecord metaRecord) {
        super(metaRecord);
    }

    /**
     * Geef de naam van het element van het gegeven dat in onderzoek is.
     * @return de naam van het element van het gegeven dat in onderzoek is
     */
    public String getElement() {
        return getAttribuut(elementElement);
    }

    /**
     * Geef het element van het gegeven dat in onderzoek is.
     * @return element van het gegeven dat in onderzoek is
     */
    public ElementObject getElementAsAlement() {
        return ElementHelper.getElement(getElement());
    }

    /**
     * Geef de objectsleutel van het gegevens dat in onderzoek is.
     * @return objectsleutel van het gegevens dat in onderzoek is
     */
    public Long getObjectSleutelGegeven() {
        final Number number = this.getAttribuut(objectSleutelGegevenElement);
        return number == null ? null : number.longValue();
    }

    /**
     * Geef het voorkomensleutel van het gegevens dat in onderzoek is.
     * @return voorkomensleutel van het gegevens dat in onderzoek is
     */
    public Long getVoorkomenSleutelGegeven() {
        final Number number = this.getAttribuut(voorkomenSleutelGegevenElement);
        return number == null ? null : number.longValue();
    }
}
