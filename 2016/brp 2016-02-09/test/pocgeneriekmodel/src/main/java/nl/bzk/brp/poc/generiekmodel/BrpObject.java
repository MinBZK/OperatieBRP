/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.generiekmodel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import nl.bzk.brp.poc.generiekmodel.bmr.ElementObjectType;

/**
 */
public final class BrpObject implements Visitable {

    /**
     * Het type object
     */
    private ElementObjectType element;

    /**
     * Het parent object
     */
    private BrpObject parent;

    /**
     * Geassocieerde objecten, gegroepeerd op type
     * Bijv Persoon heeft meerdere adressen
     */
    private final Map<ElementObjectType, Set<BrpObject>> objecten = new HashMap<>();

    /**
     * De groepen die het object bevat
     * Bijv. Identiteit, Standaard
     */
    private final Set<BrpGroep> groepen = new HashSet<>();

    /**
     * Technische sleutel
     */
    private int objectsleutel;

    /**
     *
     */
    public BrpObject() {
    }

    public ElementObjectType getElement() {
        return element;
    }

    public BrpObject getParent() {
        return parent;
    }

    public int getObjectsleutel() {
        return objectsleutel;
    }

    public void setElement(final ElementObjectType element) {
        this.element = element;
    }

    public void setParent(final BrpObject parent) {
        this.parent = parent;
    }

    public void setObjectsleutel(final int objectsleutel) {
        this.objectsleutel = objectsleutel;
    }

    public Map<ElementObjectType, Set<BrpObject>> getObjecten() {
        return objecten;
    }

    public Set<BrpGroep> getGroepen() {
        return groepen;
    }

    //visitor
    @Override
    public void visit(final ModelVisitor visitor) {
        visitor.visit(this);
    }


    /**
     *
     * @param object
     */
    void voegObjectToe(final BrpObject object) {

        Set<BrpObject> objectSet;
        if (objecten.containsKey(object.element)){
            objectSet = objecten.get(object.element);
        } else {
            objectSet = new HashSet<>();
            objecten.put(object.element, objectSet);

        }
        objectSet.add(object);

    }
}
