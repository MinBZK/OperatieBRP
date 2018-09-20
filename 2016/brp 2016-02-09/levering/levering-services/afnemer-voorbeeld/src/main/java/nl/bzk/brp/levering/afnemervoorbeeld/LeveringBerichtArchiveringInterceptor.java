/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemervoorbeeld;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.springframework.stereotype.Component;

/**
 * Deze interceptor zet naast zijn standaard taak (logging van berichtinhoud) ook de berichtinhoud op de context van de interceptorchain.
 */
@Component
public class LeveringBerichtArchiveringInterceptor extends LoggingInInterceptor {

    @Override
    protected final void writePayload(final StringBuilder builder, final CachedOutputStream cos, final String encoding, final String contentType)
        throws Exception
    {
        super.writePayload(builder, cos, encoding, contentType);

        final StringBuilder stringBuilder = new StringBuilder();
        cos.writeCacheTo(stringBuilder);
        PhaseInterceptorChain.getCurrentMessage().put("berichtinhoud", stringBuilder.toString());
    }
}
