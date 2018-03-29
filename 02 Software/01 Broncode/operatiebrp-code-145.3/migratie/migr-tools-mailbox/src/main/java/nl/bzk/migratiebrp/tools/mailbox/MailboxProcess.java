/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.tools.mailbox.impl.Mailbox;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxException;
import nl.bzk.migratiebrp.tools.mailbox.impl.MailboxFactory;
import nl.bzk.migratiebrp.voisc.spd.ChangePasswordRequest;
import nl.bzk.migratiebrp.voisc.spd.GetMessage;
import nl.bzk.migratiebrp.voisc.spd.ListMessages;
import nl.bzk.migratiebrp.voisc.spd.LogoffRequest;
import nl.bzk.migratiebrp.voisc.spd.LogonRequest;
import nl.bzk.migratiebrp.voisc.spd.NoOperationRequest;
import nl.bzk.migratiebrp.voisc.spd.Operation;
import nl.bzk.migratiebrp.voisc.spd.PutMessageOperation;
import nl.bzk.migratiebrp.voisc.spd.Request;
import nl.bzk.migratiebrp.voisc.spd.RequestHandler;
import nl.bzk.migratiebrp.voisc.spd.SpdElementToStringVisitor;
import nl.bzk.migratiebrp.voisc.spd.exception.ParseException;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;

/**
 * Models the mailbox client. The client has a connection with the MailboxServer over which messages are exchanged.
 */
final class MailboxProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    // The connection with the client
    private final SpdAdapter spdAdapter;

    // The mailbox factory
    private final MailboxFactory mbFactory;
    private final Map<Class<? extends Request>, RequestHandler> requestHandlers;
    // De geopende mailbox
    private Mailbox mailbox;

    /**
     * Constructor.
     * @param connection connectie
     * @param mbFactory mailbox factory
     * @throws IOException als de input- of outputstream (van de connectie) niet geopend kan worden.
     */
    MailboxProcess(final Socket connection, final MailboxFactory mbFactory) throws IOException {
        spdAdapter = new SpdAdapter(connection);
        this.mbFactory = mbFactory;
        this.requestHandlers = new HashMap<>();

        receive(LogonRequest.class, (req, res) -> mailbox = new LogonRequestHandler(mbFactory, mailbox).handleRequest((LogonRequest) req, res));
        receive(LogoffRequest.class, (req, res) -> mailbox = new LogoffRequestHandler(mailbox).handleRequest(res));
        receive(ChangePasswordRequest.class, (req, res) -> new ChangePasswordRequestHandler(mailbox).handleRequest((ChangePasswordRequest) req, res));
        receive(ListMessages.class, (req, res) -> new ListMessagesHandler(mailbox).handleRequest((ListMessages) req, res));
        receive(GetMessage.class, (req, res) -> new GetMessageHandler(mailbox).handleRequest((GetMessage) req, res));
        receive(NoOperationRequest.class, (req, res) -> new NoOperationRequestHandler().handleRequest(res));
    }

    /**
     * Handle the Mailbox State Machine.
     */
    void process() {

        boolean keepAlive = true;
        try {
            while (keepAlive) {
                keepAlive = handleMainOperation(spdAdapter.receiveRequest());
            }
        } catch (final IOException e) {
            LOGGER.error("Fout tijdens het gebruik van de socket", e);
        } catch (final VoaException | ParseException e) {
            LOGGER.error("Probleem bij ontvangen request.", e);
        } finally {
            // Als de mailbox niet netjes is afgesloten dan hier
            if (mailbox != null) {
                try {
                    mailbox.save();
                } catch (final MailboxException e) {
                    LOGGER.error("Probleem bij (nood)afsluiting mailbox.", e);
                } finally {
                    mailbox.close();
                }

            }
        }
    }

    private boolean handleMainOperation(final Operation operation) throws IOException {

        boolean keepAlive = true;
        final Operation.Builder builder = new Operation.Builder();

        if (operation instanceof PutMessageOperation) {
            new PutMessageHandler(mbFactory, mailbox).handleRequest((PutMessageOperation) operation, builder);
        } else {
            final Request request = operation.getRequest().orElse(new NoOperationRequest());
            this.requestHandlers.get(request.getClass()).accept(request, builder);
        }

        if (this.mailbox == null) {
            keepAlive = false;
        }

        final Operation response = builder.build();
        LOGGER.debug(
                "[Mailbox {}]: Sending response {}",
                mailbox == null ? "<null>" : mailbox.getMailboxnr(),
                new SpdElementToStringVisitor().visit(response).toString());
        spdAdapter.sendResponse(response);

        return keepAlive;
    }

    private void receive(final Class<? extends Request> clazz, final RequestHandler handler) {
        this.requestHandlers.put(clazz, handler);
    }
}
