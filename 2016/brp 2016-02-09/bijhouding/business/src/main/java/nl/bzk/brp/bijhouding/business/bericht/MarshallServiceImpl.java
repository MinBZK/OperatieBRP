/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.bericht;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import javax.inject.Inject;
import nl.bzk.brp.model.bijhouding.NotificeerBijhoudingsplanBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonBericht;
import org.jibx.runtime.ICharacterEscaper;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;
import org.jibx.runtime.impl.UTF8Escaper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Implementatie van {@link MarshallService}.
 */
@Service("bijhMarshallService")
public class MarshallServiceImpl implements MarshallService {
    @Inject
    @Qualifier("bijhMarshallingContextFactoryImpl")
    private MarshallingContextFactory marshallingContextFactory;

    @Override
    public final String maakBericht(final NotificeerBijhoudingsplanBericht notificatieBericht) throws JiBXException {
        final IMarshallingContext mctx =
            marshallingContextFactory.nieuweMarshallingContext(NotificeerBijhoudingsplanBericht.class);
        final StringWriter stringWriter = new StringWriter();
        // TODO POC Default character escaper gebruiken.
        // Deze character escaper zorgt ervoor dat de '<' in brp:bijhoudingsvoorstelBericht en brp:bijhoudingsvoorstelBerichtResultaat niet veranderd in
        // &lt
        mctx.setOutput(stringWriter, new ICharacterEscaper() {
            private ICharacterEscaper escaper = UTF8Escaper.getInstance();

            @Override
            public void writeAttribute(final String s, final Writer writer) throws IOException {
                escaper.writeAttribute(s, writer);
            }

            @Override
            public void writeContent(final String s, final Writer writer) throws IOException {
                writer.write(s);
            }

            @Override
            public void writeCData(final String s, final Writer writer) throws IOException {
                escaper.writeCData(s, writer);
            }
        });
        mctx.setIndent(2);
        mctx.marshalDocument(notificatieBericht);

        return stringWriter.toString();
    }

    // TODO POC-Bijhouding: deze kan waarschijnlijk weg
    @Override
    public String maakBericht(final GeefSynchronisatiePersoonBericht geefSynchronisatiePersoonBericht) throws JiBXException {
        final IMarshallingContext mctx =
            marshallingContextFactory.nieuweMarshallingContext(GeefSynchronisatiePersoonBericht.class);
        final StringWriter stringWriter = new StringWriter();
        mctx.setOutput(stringWriter);
        mctx.setIndent(2);
        mctx.marshalDocument(geefSynchronisatiePersoonBericht);

        return stringWriter.toString();
    }

}
