/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.Collection;
import java.util.Collections;

/**
 * MessageBody according to the sPd-protocol.
 */
public final class PutMessageBody implements OperationRecord {

    private final String body;

    /**
     * Creates MessageBody with empty body.
     */
    public PutMessageBody() {
        this.body = "";
        invariant();
    }

    /**
     * Creates MessageBody with the specified body.
     * @param body message body
     */
    public PutMessageBody(final String body) {
        this.body = body;
        invariant();
    }

    /**
     * Factory method for creating a PutMessageBody from the concatenation of operation items.
     * @param operationItems concatenated string of operation items
     * @return PutMessageBody object
     */
    public static PutMessageBody fromOperationItems(final String operationItems) {
        return new PutMessageBody(operationItems);
    }

    @Override
    public Collection<Field<?>> fields() {
        return Collections.singletonList(StringField.builder().name("BodyString").length(body.length()).optional().build());
    }

    @Override
    public int operationCode() {
        return SpdConstants.OPC_PUT_MESSAGE_BODY;
    }

    @Override
    public String toSpdString() {
        return body;
    }
}
