/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

/**
 * Child-first iteratie strategie attribuut &gt; record &gt; groep &gt; object &gt; object[parent].
 */
public class ChildFirstModelVisitor implements ModelVisitor {

    /**
     * Constructor met default parent first strategie.
     */
    protected ChildFirstModelVisitor() {
    }

    @Override
    public final void visit(final MetaObject ot) {
        for (final MetaObject metaObject : ot.getObjecten()) {
            visit(metaObject);
        }
        for (final MetaGroep metaGroep : ot.getGroepen()) {
            visit(metaGroep);
        }
        doVisit(ot);
    }

    @Override
    public final void visit(final MetaAttribuut a) {
        doVisit(a);
    }

    @Override
    public final void visit(final MetaGroep brpGroep) {
        for (final MetaRecord metaRecord : brpGroep.getRecords()) {
            visit(metaRecord);
        }
        doVisit(brpGroep);
    }

    @Override
    public final void visit(final MetaRecord record) {
        for (final MetaAttribuut metaAttribuut : record.getAttributen().values()) {
            visit(metaAttribuut);
        }
        doVisit(record);
    }

    /**
     * Callback voor metaobject.
     *
     * @param object een metaobject
     */
    protected void doVisit(final MetaObject object) {
        //optionele hook
    }

    /**
     * Callback voor MetaGroep.
     *
     * @param groep een groep
     */
    protected void doVisit(final MetaGroep groep) {
        //optionele hook
    }

    /**
     * Callback voor MetaRecord.
     *
     * @param record een record
     */
    protected void doVisit(final MetaRecord record) {
        //optionele hook
    }

    /**
     * Callback voor MetaAttribuut.
     *
     * @param attribuut een attribuut.
     */
    protected void doVisit(final MetaAttribuut attribuut) {
        //optionele hook
    }

}
