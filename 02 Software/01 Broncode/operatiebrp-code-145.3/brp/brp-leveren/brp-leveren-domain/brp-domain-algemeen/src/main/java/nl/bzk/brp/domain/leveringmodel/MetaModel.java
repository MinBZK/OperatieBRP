/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

/**
 * abstract class voor meta model klassen.
 */
public class MetaModel {

    private final int hashcode;

    /**
     * Protected Constructor.
     */
    protected MetaModel() {
        this.hashcode = super.hashCode();
    }

    /**
     * Methode om de visitor te accepteren.
     *
     * @param visitor een model visitor.
     */
    public void accept(final ModelVisitor visitor) {
        //optional op
    }

    public MetaModel getParent() {
        return null;
    }

    /**
     * Elk metaobject is uniek. Vergelijkingen met andere metaobjecten (die eventueel functioneel hetzelfde kunenn zijn) moeten andere equals vergelijking
     * gebruiken
     */
    @Override
    public final boolean equals(final Object o) {
        return super.equals(o);
    }

    /**
     * Default hash code, zie equals.
     */
    @Override
    public final int hashCode() {
        return hashcode;
    }


}
