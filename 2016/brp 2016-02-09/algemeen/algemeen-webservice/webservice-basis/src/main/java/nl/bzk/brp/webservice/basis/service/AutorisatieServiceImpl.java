/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.basis.service;

import javax.servlet.http.HttpServletRequest;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OINAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.util.AutorisatieOffloadGegevens;
import nl.bzk.brp.webservice.business.service.AutorisatieException;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class AutorisatieServiceImpl implements AutorisatieService {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Autowired
    private ReferentieDataRepository referentieDataRepository;

    @Override
    public final AutorisatieOffloadGegevens geefAutorisatieOffloadGegevens() throws AutorisatieException {
        final Message message = PhaseInterceptorChain.getCurrentMessage();
        final HttpServletRequest httpRequest = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        return new AutorisatieOffloadGegevens(
            geefPartij(httpRequest, AutorisatieOffloadGegevens.OIN_ONDERTEKENAAR),
            geefPartij(httpRequest, AutorisatieOffloadGegevens.OIN_TRANSPORTEUR)
        );
    }

    private Partij geefPartij(final HttpServletRequest httpRequest, final String headerKey) throws AutorisatieException {
        final String oinWaarde = httpRequest.getHeader(headerKey);
        if (StringUtils.isEmpty(oinWaarde)) {
            throw new AutorisatieException(Regel.R2120);
        }
        final OINAttribuut attribuut = new OINAttribuut(oinWaarde);
        final Partij partij = referentieDataRepository.vindPartijOpOIN(attribuut);
        if (partij == null) {
            throw new AutorisatieException(Regel.R2120);
        }
        return partij;
    }
}
