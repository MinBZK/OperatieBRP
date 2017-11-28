/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.security;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import nl.bzk.brp.service.algemeen.PartijCodeResolver;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.springframework.stereotype.Component;

/**
 * Resolver voor de partij code in request header.
 */
@Component
public final class PartijCodeResolverImpl implements PartijCodeResolver {

    private static final int PARTIJ_CODE_LENGTE = 6;

    @Override
    public Optional<String> get() {
        final Message message = PhaseInterceptorChain.getCurrentMessage();
        final Map<String, List<String>> headers = CastUtils.cast((Map<?, ?>) message.get(Message.PROTOCOL_HEADERS));
        return headers
                .getOrDefault(StringUtils.leftPad(HEADER.PARTIJ_CODE.getNaam(), PARTIJ_CODE_LENGTE, '0'), Collections.emptyList())
                .stream()
                .findFirst();
    }
}
