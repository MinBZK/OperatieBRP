/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.internbericht.admhndpublicatie.HandelingVoorPublicatie;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiehandeling;
import org.springframework.stereotype.Component;


/**
 * Deze service maakt een Mutatiehandeling object obv het administatievehandeling id.
 *
 * Het kan voorkomen dat de handeling die geleverd wordt reeds is ingehaald door de bijhouding. De blob bevat in dat geval toekomstige handelingen. {@link
 * Regel#R1556} wordt toegepast om de eerdere handeling te reconstrueren. Een 'latere handeling"' zal altijd een BRP handeling zijn, omdat de
 * migratievoorziening geen handelingen doorvoert voordat alle eerdere handelingen geleverd zijn.
 */
@Component
@Bedrijfsregel(Regel.R1556)
final class MaakMutatiehandelingServiceImpl implements VerwerkHandelingService.MaakMutatiehandelingService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private PersoonslijstService persoonslijstService;

    private MaakMutatiehandelingServiceImpl() {

    }

    @Override
    public Mutatiehandeling maakHandeling(final HandelingVoorPublicatie handelingVoorPublicatie) {
        final long admhdnId = handelingVoorPublicatie.getAdmhndId();
        LOGGER.debug("Administratieve handeling [{}] opgehaald met persoonIds [{}]", admhdnId,
                handelingVoorPublicatie.getBijgehoudenPersonen().size());
        final Map<Long, Persoonslijst> persoonsLijstMap = haalPersoonslijstOp(handelingVoorPublicatie.getBijgehoudenPersonen(), admhdnId);
        return new Mutatiehandeling(admhdnId, persoonsLijstMap);
    }

    private Map<Long, Persoonslijst> haalPersoonslijstOp(final Set<Long> persoonIds, final Long handelingId) {
        final Map<Long, Persoonslijst> resultaat = Maps.newHashMap();
        for (Long persoonId : persoonIds) {
            final Persoonslijst persoonslijst = persoonslijstService.getById(persoonId);
            resultaat.put(persoonId, persoonslijst.beeldVan().administratievehandeling(handelingId));
        }
        return resultaat;
    }
}
