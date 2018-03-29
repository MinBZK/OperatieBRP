/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.decorator;

import java.util.Collection;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.MetaObject;

/**
 * Decorator om het generieke gegevens model heen.
 * <p>
 * Dit wordt voornamelijk gebruikt om de onderzoeken te mappen. Voor de LO3 mutatie bepaling zijn meer gegevens nodig over de onderzoeken dan nu
 * beschikbaar zijn in het specifieke gedeelte van het generieke model.
 */
public final class Persoon extends ObjectDecorator {

    /**
     * Constructor.
     * @param metaObject metaObject van de hoofdpersoon
     */
    public Persoon(final MetaObject metaObject) {
        super(metaObject);
    }

    /**
     * Geef de onderzoeken van een persoon.
     * @return onderzoeken
     */
    public Collection<Onderzoek> getOnderzoeken() {
        return getObjecten(ElementHelper.getObjectElement(Element.ONDERZOEK.getId()), metaObject -> new Onderzoek(this, metaObject));
    }
}
