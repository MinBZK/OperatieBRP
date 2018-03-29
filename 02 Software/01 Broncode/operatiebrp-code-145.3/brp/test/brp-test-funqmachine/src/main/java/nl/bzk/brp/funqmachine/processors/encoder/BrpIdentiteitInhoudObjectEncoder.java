/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.processors.encoder;

import java.io.Writer;
import nl.bzk.algemeenbrp.util.xml.context.Context;
import nl.bzk.algemeenbrp.util.xml.exception.ConfigurationException;
import nl.bzk.algemeenbrp.util.xml.exception.EncodeException;
import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
import nl.bzk.algemeenbrp.util.xml.model.XmlObject;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentiteitInhoud;
import org.w3c.dom.Element;

/**
 */
public class BrpIdentiteitInhoudObjectEncoder extends AbstractBrpObjectEncoder implements XmlObject<BrpIdentiteitInhoud> {
    @Override
    public void encode(
            final Context context,
            final Class<?> clazzFromParent,
            final String nameFromParent,
            final BrpIdentiteitInhoud value,
            final Writer writer) throws ConfigurationException, EncodeException {

    }

    @Override
    public BrpIdentiteitInhoud decode(final Context context, final Element element) throws XmlException {
        throw new UnsupportedOperationException("BrpActieBronObject.decode niet ondersteund");
    }
}
