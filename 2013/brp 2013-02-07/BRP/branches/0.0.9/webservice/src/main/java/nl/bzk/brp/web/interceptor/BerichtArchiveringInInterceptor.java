/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.interceptor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.xml.transform.TransformerException;

import nl.bzk.brp.business.dto.BerichtenIds;
import nl.bzk.brp.business.service.ArchiveringService;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CXF IN Interceptor die aan de interceptor chain van CXF kan worden toegevoegd en dan alle inkomende berichten
 * archiveert.
 * <p/>
 * Merk op dat deze class grotendeels is overgenomen van de {@link
 * org.apache.cxf.interceptor.LoggingInInterceptor}, maar net anders is, daar de genoemde CXF class gericht is op
 * het loggen naar een {@link java.io.PrintWriter} of een {@link java.util.logging.Logger}, zonder callback
 * mogelijkheden of toegang tot het bericht op moment van loggen, terwijl dit wel noodzakelijk is. Op moment van loggen
 * moeten immers de ids van de berichten waaronder ze gelogd zijn aan de bericht context worden toegevoegd en dit
 * lukt niet binnen de genoemde standaard interceptor van CXF.
 */
public class BerichtArchiveringInInterceptor extends AbstractPhaseInterceptor<Message> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BerichtArchiveringInInterceptor.class);

    @Inject
    private ArchiveringService archiveringService;

    /**
     * Standaard constructor die direct de CXF fase waarin deze interceptor moet draaien zet op
     * {@link Phase.RECEIVE}.
     */
    public BerichtArchiveringInInterceptor() {
        this(Phase.RECEIVE);
    }

    /**
     * Standaard constructor die de CXF fase waarin deze interceptor moet draaien zet naar de opgegeven fase.
     *
     * @param fase de fase waarin deze interceptor moet draaien.
     */
    public BerichtArchiveringInInterceptor(final String fase) {
        super(fase);
    }

    /**
     * Handelt de archivering af van het opgegeven bericht. Hiervoor wordt eerst een {@link ArchiveringBericht}
     * instantie gebouwd op basis van het opgegeven bericht en deze wordt dan gearchiveerd. De id van het bericht,
     * waaronder het wordt gearchiveerd, alsmede het id van het (toekomstige) antwoordbericht worden door de
     * archiveringsservice geretourneerd en worden voor verder gebruik aan de context van het bericht toegevoegd.
     *
     * @param bericht het (SOAP) bericht dat moet worden afgehandeld.
     */
    @Override
    public void handleMessage(final Message bericht) {
        ArchiveringBericht archiveringBericht = bouwArchiveringBericht(bericht);

        BerichtenIds berichtenIds = archiveerBericht(archiveringBericht);
        LOGGER.debug("Bericht gearchiveerd onder id {} en uitgaand bericht onder {}",
            berichtenIds.getIngaandBerichtId(), berichtenIds.getUitgaandBerichtId());

        voegBerichtenIdsAanContext(bericht, berichtenIds);
    }

    /**
     * Archiveert het bericht door de archiveringsservice aan te roepen.
     *
     * @param archiveringBericht het bericht dat gearchiveerd dient te worden.
     * @return het id waaronder het bericht is gearchiveerd en het id van het (toekomstige) antwoordbericht.
     */
    private BerichtenIds archiveerBericht(final ArchiveringBericht archiveringBericht) {
        return archiveringService.archiveer(archiveringBericht.toString());
    }

    /**
     * Voegt de opgegeven berichten ids, van zowel het inkomende en in deze interceptor afgehandelde bericht, als het
     * op dit bericht (toekomstige) antwoordbericht, toe aan de context van het bericht.
     *
     * @param bericht het bericht dat in deze interceptor wordt afgehandeld.
     * @param berichtenIds de ids waaronder dit bericht en het (toekomstige) antwoord bericht is gearchiveerd.
     */
    private void voegBerichtenIdsAanContext(final Message bericht, final BerichtenIds berichtenIds) {
        bericht.getExchange().put(ArchiveringBericht.BERICHT_ARCHIVERING_IN_ID, berichtenIds.getIngaandBerichtId());
        bericht.getExchange().put(ArchiveringBericht.BERICHT_ARCHIVERING_UIT_ID, berichtenIds.getUitgaandBerichtId());
    }

    /**
     * Bouwt en retourneert een {@link ArchiveringBericht} instantie op, op basis van het opgegeven {@link Message
     * bericht}. Deze archiveringBericht instantie bevat de te archiveren informatie uit het bericht.
     *
     * @param bericht het bericht dat gearchiveerd dient te worden.
     * @return de te archiveren bericht informatie.
     */
    private ArchiveringBericht bouwArchiveringBericht(final Message bericht) {
        final ArchiveringBericht archiveringBericht = new ArchiveringBericht(bericht, "Inkomend Bericht");

        voegAdresToeAanArchiveringBericht(bericht, archiveringBericht);
        voegBerichtPayloadToeAanArchiveringBericht(bericht, archiveringBericht);

        return archiveringBericht;
    }

    /**
     * Voegt de URL/het adres waarnaar het bericht is verstuurd toe aan de archiveringsrepresentatie van het bericht.
     *
     * @param bericht het bericht dat gearchiveerd dient te worden.
     * @param archiveringBericht de archiveringsrepresentatie van het bericht.
     */
    private void voegAdresToeAanArchiveringBericht(final Message bericht, final ArchiveringBericht archiveringBericht) {
        String uri = (String) bericht.get(Message.REQUEST_URL);
        if (uri != null) {
            StringBuilder adres = new StringBuilder(uri);
            String query = (String) bericht.get(Message.QUERY_STRING);
            if (query != null) {
                adres.append("?").append(query);
            }
            archiveringBericht.setAdres(adres.toString());
        } else {
            archiveringBericht.setAdres(null);
        }
    }

    /**
     * Voegt de content/payload van het bericht aan de archiveringsrepresentatie van het bericht.
     *
     * @param bericht het bericht dat gearchiveerd dient te worden.
     * @param archiveringBericht de archiveringsrepresentatie van het bericht.
     */
    private void voegBerichtPayloadToeAanArchiveringBericht(final Message bericht,
        final ArchiveringBericht archiveringBericht)
    {
        InputStream binnenkomendBerichtStream = bericht.getContent(InputStream.class);
        if (binnenkomendBerichtStream != null) {
            CachedOutputStream berichtUitleesStream = new CachedOutputStream();
            try {
                IOUtils.copy(binnenkomendBerichtStream, berichtUitleesStream);

                berichtUitleesStream.flush();
                binnenkomendBerichtStream.close();

                bericht.setContent(InputStream.class, berichtUitleesStream.getInputStream());
                archiveringBericht.vulPayload(berichtUitleesStream);
                berichtUitleesStream.close();
            } catch (IOException e) {
                LOGGER.error(
                    "Fout opgetreden bij archivering van inkomend bericht: fout bij ophalen van de payload", e);
                throw new Fault(new org.apache.cxf.common.i18n.Message("Fout opgetreden bij berichtarchivering",
                    (ResourceBundle) null, e));
            }
        }
    }

}
