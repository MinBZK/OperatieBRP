/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.interceptor;

import nl.bzk.brp.bevraging.business.service.ArchiveringService;
import nl.bzk.brp.bevraging.domein.ber.Richting;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;


/**
 * CXF Interceptor die inkomende berichten archiveert via de archivering service.
 *
 * @see ArchiveringService
 */
public class ArchiveringInInterceptor extends AbstractSoapInterceptor {

    @Inject
    private ArchiveringService archiveringService;

    /**
     * Constructor die de fase zet waarin de interceptor dient te worden aangeroepen en tevens configureert
     * welke eventueel andere interceptoren reeds vooraf uitgevoerd dienen te worden.
     */
    public ArchiveringInInterceptor() {
        super(Phase.USER_STREAM);
        getAfter().add(BerichtIdGeneratorInterceptor.class.getName());
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        InputStream is = message.getContent(InputStream.class);
        if (is != null) {
            try {
                byte[] b = IOUtils.readBytesFromStream(is);
                is.close();

                long id = archiveringService.archiveer(new String(b), Richting.INGAAND);

                // TODO: tim - Overwrite van de bericht id verwijderen als bericht id correct wordt gebruikt
                message.put(BerichtIdGeneratorInterceptor.BRP_BERICHT_ID, id);

                message.setContent(InputStream.class, new ByteArrayInputStream(b));
            } catch (Exception e) {
                throw new Fault(e);
            }
        }
    }
}
