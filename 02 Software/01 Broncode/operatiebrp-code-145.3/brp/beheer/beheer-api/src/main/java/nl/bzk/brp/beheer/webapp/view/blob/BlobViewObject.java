/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view.blob;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;

/**
 * View van een object.
 */
public final class BlobViewObject {

    private final Element element;
    private final Long objectsleutel;
    private final Element parentElement;
    private final Long parentObjectsleutel;
    private final SortedMap<Element, BlobViewGroep> groepen = new TreeMap<>(BlobMapper::compareElementOnVolgnummer);

    private BlobViewObject parent;
    private final SortedMap<Element, Collection<BlobViewObject>> objecten = new TreeMap<>(BlobMapper::compareElementOnVolgnummer);

    /**
     * Constructor.
     *
     * @param element
     *            element
     * @param objectsleutel
     *            object sleutel
     * @param parentElement
     *            parent element
     * @param parentObjectsleutel
     *            parent object sleutel
     */
    public BlobViewObject(final Element element, final Long objectsleutel, final Element parentElement, final Long parentObjectsleutel) {
        this.element = element;
        this.objectsleutel = objectsleutel;
        this.parentElement = parentElement;
        this.parentObjectsleutel = parentObjectsleutel;
    }

    /**
     * Geef element terug.
     * @return element
     */
    public Element getElement() {
        return element;
    }

    /**
     * Geef objectsleutel terug.
     * @return objectsleutel
     */
    public Long getObjectsleutel() {
        return objectsleutel;
    }

    /**
     * Geef parent element terug.
     * @return parent element
     */
    public Element getParentElement() {
        return parentElement;
    }

    /**
     * Geef parent object sleutel terug.
     * @return parent object sleutel
     */
    public Long getParentObjectsleutel() {
        return parentObjectsleutel;
    }

    /**
     * Bepaal of dit object een gekoppelde parent heeft.
     *
     * @return true, als getParent() niet null zal retourneren
     */
    public boolean hasParent() {
        return parent != null;
    }

    /**
     * Bepaal of dit object een parent referentie bevat.
     *
     * @return true, als de parent element was gevuld bij de constructor
     */
    public boolean hasParentReference() {
        return parentElement != null;
    }

    /**
     * Geef een specifieke groep.
     *
     * @param groep
     *            groep element
     * @return groep (wordt toegevoegd indien niet bestaand)
     */
    public BlobViewGroep getGroep(final Element groep) {
        if (!groepen.containsKey(groep)) {
            groepen.put(groep, new BlobViewGroep(groep));
        }
        return groepen.get(groep);
    }

    /**
     * Geef alle groepen (gesorteerd op volgnummer van groep element).
     *
     * @return groepen
     */
    public SortedMap<Element, BlobViewGroep> getGroepen() {
        return groepen;
    }

    public void setParent(final BlobViewObject parent) {
        this.parent = parent;
    }

    public BlobViewObject getParent() {
        return parent;
    }

    /**
     * Voeg een child object toe.
     *
     * @param object
     *            child object
     */
    public void addObject(final BlobViewObject object) {
        if (!objecten.containsKey(object.getElement())) {
            objecten.put(object.getElement(), new ArrayList<>());
        }

        objecten.get(object.getElement()).add(object);
        object.setParent(this);
    }

    /**
     * Geef alle child objecten (gesorteerd op volgnummer van object element).
     *
     * @return child objecten
     */
    public SortedMap<Element, Collection<BlobViewObject>> getObjecten() {
        return objecten;
    }

    @Override
    public String toString() {
        return "BlobViewObject [element="
               + element
               + ", objectsleutel="
               + objectsleutel
               + ", parentElement="
               + parentElement
               + ", parentObjectsleutel="
               + parentObjectsleutel
               + ", groepen="
               + groepen
               + ", objecten="
               + objecten
               + "]";
    }

}
