/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.generiekmodel;


import nl.bzk.brp.poc.generiekmodel.bmr.ElementAttribuut;

/**
 *
 */
public final class BrpAttribuut implements Visitable {

    /**
     * Het voorkomen waartoe dit attribuut behoort
     */
    private BrpGroepVoorkomen parent;

    /**
     * Het type attribuut
     */
    private ElementAttribuut element;

    /**
     * De waarde van het attribuut.
     * Typering is abstract, gebruiker moet casten adhv type element
     */
    private Object waarde;

    /**
     * Constructor voor attribuut
     */
    public BrpAttribuut() {
    }

    /**
     *
     * @param parent
     * @param element
     * @param waarde
     */
    public BrpAttribuut(final BrpGroepVoorkomen parent, final ElementAttribuut element, final Object waarde) {
        this.parent = parent;
        this.element = element;
        this.waarde = waarde;
    }

    public void setParent(final BrpGroepVoorkomen parent) {
        this.parent = parent;
    }

    public void setElement(final ElementAttribuut element) {
        this.element = element;
    }

    public void setWaarde(final Object waarde) {
        this.waarde = waarde;
    }

    public BrpGroepVoorkomen getParent() {
        return parent;
    }

    public ElementAttribuut getElement() {
        return element;
    }

    public Object getWaarde() {
        return waarde;
    }

    /**
     *
     * @param visitor
     */
    @Override
    public void visit(final ModelVisitor visitor) {
        visitor.visit(this);
    }

}
