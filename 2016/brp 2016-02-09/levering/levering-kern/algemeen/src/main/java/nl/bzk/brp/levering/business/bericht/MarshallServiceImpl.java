/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bericht;

import java.io.StringWriter;
import javax.inject.Inject;
import nl.bzk.brp.model.levering.AbstractSynchronisatieBericht;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * Implementatie van {@link MarshallService}.
 */
@Service("levMarshallService")
public class MarshallServiceImpl implements MarshallService {

    @Inject
    @Qualifier("levMarshallingContextFactoryImpl")
    private MarshallingContextFactory marshallingContextFactory;

    @Override
    public final String maakBericht(final SynchronisatieBericht leveringBericht) throws JiBXException {
        final IMarshallingContext mctx =
            marshallingContextFactory.nieuweMarshallingContext(AbstractSynchronisatieBericht.class);
        final StringWriter stringWriter = new StringWriter();
        mctx.setOutput(stringWriter);
        mctx.setIndent(2);
        mctx.marshalDocument(leveringBericht);

        return stringWriter.toString();
    }

}
