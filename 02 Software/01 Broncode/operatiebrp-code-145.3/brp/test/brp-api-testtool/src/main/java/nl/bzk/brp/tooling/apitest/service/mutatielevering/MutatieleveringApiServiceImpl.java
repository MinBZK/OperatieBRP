/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.mutatielevering;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.internbericht.admhndpublicatie.HandelingVoorPublicatie;
import nl.bzk.brp.service.mutatielevering.VerwerkHandelingException;
import nl.bzk.brp.service.mutatielevering.VerwerkHandelingService;
import nl.bzk.brp.tooling.apitest.service.dataaccess.PersoonDataStubService;
import nl.bzk.brp.tooling.apitest.service.leveringalgemeen.LeverberichtStubService;

/**
 * MutatieleveringApiService implementatie.
 */
final class MutatieleveringApiServiceImpl implements MutatieleveringApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private LeverberichtStubService leverberichtStubService;
    @Inject
    private PersoonDataStubService persoonDataStubService;
    @Inject
    private VerwerkHandelingService verwerkHandelingService;

    @Override
    public void leverLaatsteHandelingVoorPersoon(final String bsn) {
        leverberichtStubService.reset();
        final Long handelingId = persoonDataStubService.getLaatsteHandelingVanPersoon(bsn);
        final HandelingVoorPublicatie handelingVoorPublicatie = new HandelingVoorPublicatie();
        handelingVoorPublicatie.setAdmhndId(handelingId);
        final List<Long> ids = persoonDataStubService.geefPersonenMetHandeling(handelingId);
        //filter pseudo personen, alleen hoofdpersonen hebben een afgeleid administratief en zullen input zijn voor
        //verwerking van administratievehandeling. De admhnd poller zal geen pseudo personen als bijgehouden personen
        //opleveren
        final Set<Long> hoofdPersonen = new HashSet<>();
        for (Long id : ids) {
            if (!persoonDataStubService.isPseudoPersoon(id)) {
                hoofdPersonen.add(id);
            }
        }

        handelingVoorPublicatie.setBijgehoudenPersonen(hoofdPersonen);

        verwerk(handelingVoorPublicatie);
    }

    @Override
    public void leverInitieleVullingVoorPersoon(final String bsn) {
        leverberichtStubService.reset();
        final Long handelingId = persoonDataStubService.getInitieleVullingHandelingVanPersoon(bsn);
        final HandelingVoorPublicatie handelingVoorPublicatie = new HandelingVoorPublicatie();
        handelingVoorPublicatie.setAdmhndId(handelingId);
        final List<Long> ids = persoonDataStubService.geefPersonenMetHandeling(handelingId);
        //filter pseudo personen, alleen hoofdpersonen hebben een afgeleid administratief en zullen input zijn voor
        //verwerking van administratievehandeling. De admhnd poller zal geen pseudo personen als bijgehouden personen
        //opleveren
        final Set<Long> hoofdPersonen = new HashSet<>();
        for (Long id : ids) {
            if (!persoonDataStubService.isPseudoPersoon(id)) {
                hoofdPersonen.add(id);
            }
        }

        handelingVoorPublicatie.setBijgehoudenPersonen(hoofdPersonen);

        verwerk(handelingVoorPublicatie);
    }

    private void verwerk(HandelingVoorPublicatie handelingVoorPublicatie) {
        try {
            verwerkHandelingService.verwerkAdministratieveHandeling(handelingVoorPublicatie);
        } catch (VerwerkHandelingException e) {
            LOGGER.error("fout bij verwerken handeling", e);
        }
    }
}
