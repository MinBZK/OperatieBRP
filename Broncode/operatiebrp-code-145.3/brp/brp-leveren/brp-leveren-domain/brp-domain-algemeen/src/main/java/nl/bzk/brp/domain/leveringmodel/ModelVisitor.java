/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

/**
 * Visitor om het generieke model te visiteren.
 */
public interface ModelVisitor {

    /**
     * bezoek een MetaObject.
     *
     * @param ot het object
     */
    void visit(MetaObject ot);

    /**
     * bezoek een MetaAttribuut.
     *
     * @param a een attribuut
     */
    void visit(MetaAttribuut a);

    /**
     * bezoek een MetaGroep.
     *
     * @param brpGroep een groep
     */
    void visit(MetaGroep brpGroep);

    /**
     * bezoek een MetaRecord.
     *
     * @param record een record
     */
    void visit(MetaRecord record);
}
