/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.generiekmodel;


import java.util.Set;
import nl.bzk.brp.poc.generiekmodel.bmr.ElementGroep;

/**
 */
public final class BrpGroep implements Visitable {

    /**
     * Het parent object dat de groep bevat
     */
    private BrpObject parent;

    /**
     * Type groep
     */
    private ElementGroep element;

    /**
     * Aantal historische voorkomens in de groep
     */
    private Set<BrpGroepVoorkomen> voorkomens;

    /**
     * Constructor voor BrpGroep
     */
    public BrpGroep() {
    }

    public BrpObject getParent() {
        return parent;
    }

    public ElementGroep getElement() {
        return element;
    }

    public void setParent(final BrpObject parent) {
        this.parent = parent;
    }

    public void setElement(final ElementGroep element) {
        this.element = element;
    }

    public void setVoorkomens(final Set<BrpGroepVoorkomen> voorkomens) {
        this.voorkomens = voorkomens;
    }

    /**
     *
     * @return
     */
    public Set<BrpGroepVoorkomen> getVoorkomens() {
        return voorkomens;
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
