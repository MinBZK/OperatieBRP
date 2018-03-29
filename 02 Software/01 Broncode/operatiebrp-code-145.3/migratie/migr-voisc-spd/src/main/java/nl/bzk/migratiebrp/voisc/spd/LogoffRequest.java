/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

/**
 * LogoffRequest according to the sPd-protocol.
 *
 * <pre>
 * <code>
 * <b>LogoffRequest:</b>
 * Length           [5]
 * Operationcode    [3]
 * </code>
 * </pre>
 */
public final class LogoffRequest implements OperationRecord, Request {

    @Override
    public int operationCode() {
        return SpdConstants.OPC_LOGOFF_REQUEST;
    }
}
