/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.serialize;

import java.io.Writer;
import nl.bzk.algemeenbrp.util.xml.context.Context;
import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
import nl.bzk.algemeenbrp.util.xml.model.CompositeObject;
import nl.bzk.algemeenbrp.util.xml.model.XmlObject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import org.w3c.dom.Element;

/**
 * BrpActie kan binnen zichzelf worden gerefereerd. Als dat zo is dan kan het XML framework geen gerefereerd object
 * vinden en zal null geven. Echter wij lossen dit op in de constructor van BrpActie door 'lege' BrpActies met hetzelfde
 * id dan te vervangen door een correcte referentie.
 */
public final class BrpActieObject implements XmlObject<BrpActie> {

    private final CompositeObject<BrpActie> base;

    /**
     * Constructor.
     * @param base basis (voor CompositeObjectWithRefs constructor)
     */
    BrpActieObject(final CompositeObject<BrpActie> base) {
        this.base = base;
    }

    @Override
    public void encode(final Context context, final Class<?> clazzFromParent, final String nameFromParent, final BrpActie value, final Writer writer)
            throws XmlException {
        if (value == null) {
            return;
        }

        final String[] elementStack = context.getElementStack().toArray();
        final int documentStapelIndex = 2;
        if (!"documentStapel".equals(elementStack[documentStapelIndex])) {
            base.encode(context, clazzFromParent, nameFromParent, value, writer);
        }
    }

    @Override
    public BrpActie decode(final Context context, final Element element) throws XmlException {
        return base.decode(context, element);
    }

    @Override
    public String toString() {
        return "BrpActieObject [base=" + base + "]";
    }

}
