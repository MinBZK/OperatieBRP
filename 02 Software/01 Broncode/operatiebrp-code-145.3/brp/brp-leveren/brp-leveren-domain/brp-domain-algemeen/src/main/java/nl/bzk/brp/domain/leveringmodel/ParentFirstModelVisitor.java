/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

/**
 * Parent-first iteratie strategie object[parent] &gt; object[child] &gt;groep &gt; record &gt; attribuut.
 */
public class ParentFirstModelVisitor implements ModelVisitor {

    /**
     * Constructor.
     */
    protected ParentFirstModelVisitor() {
    }

    @Override
    public final void visit(final MetaObject ot) {
        doVisit(ot);
        for (final MetaObject metaObject : ot.getObjecten()) {
            visit(metaObject);
        }
        for (final MetaGroep metaGroep : ot.getGroepen()) {
            visit(metaGroep);
        }
    }

    @Override
    public final void visit(final MetaGroep brpGroep) {
        doVisit(brpGroep);
        for (final MetaRecord metaRecord : brpGroep.getRecords()) {
            visit(metaRecord);
        }
    }

    @Override
    public final void visit(final MetaRecord record) {
        doVisit(record);
        for (final MetaAttribuut metaAttribuut : record.getAttributen().values()) {
            visit(metaAttribuut);
        }
    }

    @Override
    public final void visit(final MetaAttribuut a) {
        doVisit(a);
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
