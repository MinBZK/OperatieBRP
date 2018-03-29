/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.security;

import javax.servlet.http.HttpServletRequest;
import nl.bzk.brp.service.algemeen.request.OIN;
import nl.bzk.brp.service.algemeen.request.OinResolver;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.stereotype.Component;

/**
 * Resolver voor OIN gegevens in request header.
 */
@Component
public final class OinResolverImpl implements OinResolver {

    @Override
    public OIN get() {
        final Message message = PhaseInterceptorChain.getCurrentMessage();
        final HttpServletRequest httpRequest = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        final String oinWaardeOndertekenaar = httpRequest.getHeader(OIN.OIN_ONDERTEKENAAR);
        final String oinWaardeTransporteur = httpRequest.getHeader(OIN.OIN_TRANSPORTEUR);
        return new OIN(oinWaardeOndertekenaar, oinWaardeTransporteur);
    }
}
