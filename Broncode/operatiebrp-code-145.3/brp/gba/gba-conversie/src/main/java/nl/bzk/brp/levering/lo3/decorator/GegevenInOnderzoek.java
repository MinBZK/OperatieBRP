/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.decorator;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ElementObject;
import nl.bzk.brp.domain.leveringmodel.MetaObject;

/**
 * Gegeven in onderzoek.
 */
public final class GegevenInOnderzoek extends ObjectDecorator {

    private final Onderzoek onderzoek;
    private final ElementObject element;
    private final Long objectSleutelGegeven;
    private final Long voorkomenSleutelGegeven;

    /**
     * Constructor.
     * @param onderzoek onderzoek waartoe dit gegeven in onderzoek hoort
     * @param metaObject meta object
     */
    GegevenInOnderzoek(final Onderzoek onderzoek, final MetaObject metaObject) {
        super(metaObject);
        this.onderzoek = onderzoek;
        final GegevenInOnderzoekIdentiteitRecord identiteit =
                getRecords(ElementHelper.getGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT.getId()), GegevenInOnderzoekIdentiteitRecord::new)
                        .iterator()
                        .next();
        element = identiteit.getElementAsAlement();
        objectSleutelGegeven = identiteit.getObjectSleutelGegeven();
        voorkomenSleutelGegeven = identiteit.getVoorkomenSleutelGegeven();

    }

    /**
     * Geef het onderzoek.
     * @return onderzoek
     */
    public Onderzoek getOnderzoek() {
        return onderzoek;
    }

    /**
     * Geef het element van het gegeven dat in onderzoek is.
     * @return element van het gegeven dat in onderzoek is
     */
    public ElementObject getElement() {
        return element;
    }

    /**
     * Geef de objectsleutel van het gegevens dat in onderzoek is.
     * @return objectsleutel van het gegevens dat in onderzoek is
     */
    public Long getObjectSleutelGegeven() {
        return objectSleutelGegeven;
    }

    /**
     * Geef het voorkomensleutel van het gegevens dat in onderzoek is.
     * @return voorkomensleutel van het gegevens dat in onderzoek is
     */
    public Long getVoorkomenSleutelGegeven() {
        return voorkomenSleutelGegeven;
    }

    @Override
    public String toString() {
        return "GegevenInOnderzoek [element="
                + element
                + ", objectSleutelGegeven="
                + objectSleutelGegeven
                + ", voorkomenSleutelGegeven="
                + voorkomenSleutelGegeven
                + "]";
    }

}
