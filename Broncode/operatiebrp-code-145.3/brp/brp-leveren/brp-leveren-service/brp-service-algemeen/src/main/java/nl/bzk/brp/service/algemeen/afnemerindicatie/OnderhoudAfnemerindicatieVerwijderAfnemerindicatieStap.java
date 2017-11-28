/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.afnemerindicatie;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.afnemerindicatie.GeneriekeOnderhoudAfnemerindicatieStappen.VerwijderAfnemerindicatie;
import nl.bzk.brp.service.algemeen.blob.AfnemerindicatieParameters;
import nl.bzk.brp.service.algemeen.blob.PersoonAfnemerindicatieService;
import org.springframework.stereotype.Component;

/**
 * Verwijdert de afnemerindicatie.
 */
@Component
final class OnderhoudAfnemerindicatieVerwijderAfnemerindicatieStap implements VerwijderAfnemerindicatie {

    @Inject
    private PersoonAfnemerindicatieService afnemerindicatiesService;
    @Inject
    private GeneriekeOnderhoudAfnemerindicatieStappen.ValideerRegelsVerwijderen valideerRegelsVerwijderen;


    private OnderhoudAfnemerindicatieVerwijderAfnemerindicatieStap() {

    }

    @Override
    public void voerStapUit(final VerwijderAfnemerindicatieParams params) throws StapException {
        final ToegangLeveringsAutorisatie tla = params.getToegangLeveringsautorisatie();
        final Dienst dienst = AutAutUtil.zoekDienst(tla.getLeveringsautorisatie(), params.getVerantwoordingDienstId());
        if (dienst == null) {
            throw new StapMeldingException(Regel.R2130);
        }
        valideerRegelsVerwijderen.valideer(tla, params.getPersoonslijst());
        try {
            final Partij partij = tla.getGeautoriseerde().getPartij();
            final Persoonslijst persoonslijst = params.getPersoonslijst();
            final AfnemerindicatieParameters
                    afnemerindicatieParameters =
                    new AfnemerindicatieParameters(persoonslijst.getId(), persoonslijst.getPersoonLockVersie(),
                            persoonslijst.getAfnemerindicatieLockVersie());
            afnemerindicatiesService
                    .verwijderAfnemerindicatie(
                            afnemerindicatieParameters,
                            partij, dienst.getId(), tla.getLeveringsautorisatie().getId());
            afnemerindicatiesService.updateAfnemerindicatieBlob(afnemerindicatieParameters);
        } catch (BlobException e) {
            throw new StapException(e);
        }
    }
}
