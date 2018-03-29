/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.Collection;
import java.util.Collections;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;

/**
 * MessageBody according to the sPd-protocol.
 */
public final class GetMessageBody implements OperationRecord {

    private final String body;

    /**
     * Creates MessageBody with empty body.
     */
    public GetMessageBody() {
        this.body = "";
        invariant();
    }

    /**
     * Creates MessageBody with the specified body.
     * @param body message body
     */
    public GetMessageBody(final String body) {
        this.body = body;
        invariant();
    }

    /**
     * Factory method for creating a GetMessageBody from the concatenation of operation items.
     * @param operationItems concatenated string of operation items
     * @return GetMessageBody object
     */
    public static GetMessageBody fromOperationItems(final String operationItems) {
        return new GetMessageBody(operationItems);
    }

    @Override
    public Collection<Field<?>> fields() {
        return Collections.singletonList(StringField.builder().name("BodyString").length(body.length()).optional().build());
    }

    public String getBody() {
        return body;
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_GET_MESSAGE_BODY;
    }

    @Override
    public String toSpdString() {
        return body;
    }

    @Override
    public void vulBericht(final Bericht bericht) throws SpdProtocolException {
        bericht.setBerichtInhoud(getBody());
    }
}
