/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.interceptor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ResourceBundle;
import javax.inject.Inject;
import javax.xml.transform.TransformerException;

import nl.bzk.brp.business.service.ArchiveringService;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CacheAndWriteOutputStream;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.CachedOutputStreamCallback;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CXF OUT Interceptor die aan de interceptor chain van CXF kan worden toegevoegd en dan alle uitgaande berichten
 * archiveert.
 * <p/>
 * Merk op dat deze class grotendeels is overgenomen van de {@link
 * org.apache.cxf.interceptor.LoggingOutInterceptor}, maar net anders is, daar de genoemde CXF class gericht is op
 * het loggen naar een {@link java.io.PrintWriter} of een {@link java.util.logging.Logger}, zonder callback
 * mogelijkheden of toegang tot het bericht op moment van loggen, terwijl dit wel noodzakelijk is. Op moment van loggen
 * moeten immers de ids van de berichten waaronder ze gelogd zijn aan de bericht context worden toegevoegd en dit
 * lukt niet binnen de genoemde standaard interceptor van CXF.
 */
public class BerichtArchiveringUitInterceptor extends AbstractPhaseInterceptor<Message> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BerichtArchiveringUitInterceptor.class);

    @Inject
    private ArchiveringService archiveringService;

    /**
     * Standaard constructor die direct de CXF fase waarin deze interceptor moet draaien zet op
     * {@link Phase.PRE_STREAM}.
     */
    public BerichtArchiveringUitInterceptor() {
        this(Phase.PRE_STREAM);
    }

    /**
     * Standaard constructor die de CXF fase waarin deze interceptor moet draaien zet naar de opgegeven fase.
     *
     * @param fase de fase waarin deze interceptor moet draaien.
     */
    public BerichtArchiveringUitInterceptor(final String fase) {
        super(fase);
    }

    /**
     * Handelt de archivering af van het opgegeven bericht. Daar de ouput via een {@link java.io.OutputStream}
     * beschikbaar is, wordt er een nieuwe stream gebouwd, die de output naar buiten tevens cached en tevens callback
     * mogelijkheden biedt. Door tevens een callback class te registeren op deze outputstream, wordt via die
     * callback class het bericht gearchiveerd.
     *
     * @param bericht het (SOAP) bericht dat moet worden afgehandeld.
     */
    @Override
    public void handleMessage(final Message bericht) {
        final OutputStream berichtOutputStream = bericht.getContent(OutputStream.class);
        if (berichtOutputStream == null) {
            return;
        }

        // Schrijf de output, maar cache deze ook om het later middels de callback te kunnen loggen
        final CacheAndWriteOutputStream cachedBerichtStream = new CacheAndWriteOutputStream(berichtOutputStream);
        bericht.setContent(OutputStream.class, cachedBerichtStream);
        cachedBerichtStream.registerCallback(new LoggingCallback(bericht, berichtOutputStream));
    }


    /**
     * Een {@link CachedOutputStreamCallback} class die bij het sluiten can de {@link CachedOutputStream} de content
     * archiveert.
     */
    class LoggingCallback implements CachedOutputStreamCallback {

        private final Message      bericht;
        private final OutputStream initieleBerichtOutputStream;

        /**
         * Standaard constructor die het bericht zet en de de initiele outputstream (voor het gebruik van de {@link
         * CachedOutputStream}.
         *
         * @param bericht het bericht dat middels de interceptor wordt afgehandeld en waarvoor deze callback werkt.
         * @param initieleBerichtOutputStream de initiele outputstream waarop het uitgaande bericht wordt gestreamd.
         */
        public LoggingCallback(final Message bericht, final OutputStream initieleBerichtOutputStream) {
            this.bericht = bericht;
            this.initieleBerichtOutputStream = initieleBerichtOutputStream;
        }

        /**
         * Methode die wordt aangeroepen als de onderliggende stream wordt geflushed. In deze class wordt er niets
         * gedaan tijdens de flush, daar we het bericht pas archiveren als het gehele bericht is gestreamed.
         *
         * @param berichtOutputStream de outputstream waarop het uitgaande bericht wordt gestreamd.
         */
        @Override
        public void onFlush(final CachedOutputStream berichtOutputStream) {

        }

        /**
         * Methode die wordt aangeroepen als de onderliggende stream wordt gesloten. Als de outputstream is gesloten,
         * is alle content van het bericht aanwezig en wordt deze daarom gearchiveerd.
         *
         * @param berichtOutputStream de outputstream waarop het uitgaande bericht wordt gestreamd.
         */
        @Override
        public void onClose(final CachedOutputStream berichtOutputStream) {
            ArchiveringBericht archiveringBericht = bouwArchiveringBericht(berichtOutputStream);
            Long berichtId = (Long) bericht.getExchange().get(ArchiveringBericht.BERICHT_ARCHIVERING_UIT_ID);

            if (berichtId != null) {
                archiveringService.werkDataBij(berichtId, archiveringBericht.toString());
                LOGGER.debug("Uitgaand bericht gearchiveerd onder id {}", berichtId);
            } else {
                LOGGER.error(
                    "Bericht Id onbekend en kan bericht archivering dus niet bijwerken. Bericht NIET gearchiveerd!");
            }

            try {
                // cache leeg maken
                berichtOutputStream.lockOutputStream();
                berichtOutputStream.resetOut(null, false);
            } catch (IOException ex) {
                LOGGER.warn("Probleem opgetreden bij het legen van een cache van een outputstream.");
            }
            bericht.setContent(OutputStream.class, initieleBerichtOutputStream);
        }

        /**
         * Bouwt en retourneert een {@link ArchiveringBericht} instantie op, op basis van het {@link Message
         * bericht} en de opgegeven outputstream die de content/payload bevat. Deze archiveringBericht instantie bevat
         * de te archiveren informatie uit het bericht.
         *
         * @param berichtOutputStream de stream die de content/payload van het bericht bevat.
         * @return de te archiveren bericht informatie.
         * @throws Fault indien er een fout optreedt bij het lezen van de bericht content/payload.
         */
        private ArchiveringBericht bouwArchiveringBericht(final CachedOutputStream berichtOutputStream) {
            final ArchiveringBericht archiveringBericht = new ArchiveringBericht(bericht, "Uitgaand Bericht");

            String adres = (String) bericht.get(Message.ENDPOINT_ADDRESS);
            if (adres != null) {
                archiveringBericht.setAdres(adres);
            }

            try {
                archiveringBericht.vulPayload(berichtOutputStream);
            } catch (IOException e) {
                LOGGER.error(
                    "Fout opgetreden bij archivering van uitgaand bericht: fout bij ophalen van de payload", e);
                throw new Fault(new org.apache.cxf.common.i18n.Message(
                    "Fout opgetreden bij berichtarchivering; bericht is potentieel wel verwerkt",
                    (ResourceBundle) null, e));
            }

            return archiveringBericht;
        }
    }

}
