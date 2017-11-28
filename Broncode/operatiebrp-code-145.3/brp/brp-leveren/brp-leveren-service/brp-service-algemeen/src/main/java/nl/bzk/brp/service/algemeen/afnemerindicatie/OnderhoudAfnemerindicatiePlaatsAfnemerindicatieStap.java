/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.afnemerindicatie;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.blob.AfnemerindicatieParameters;
import nl.bzk.brp.service.algemeen.blob.PersoonAfnemerindicatieService;
import org.springframework.stereotype.Component;

/**
 * Plaatst de afnemerindicatie.
 */
@Component
final class OnderhoudAfnemerindicatiePlaatsAfnemerindicatieStap implements GeneriekeOnderhoudAfnemerindicatieStappen.PlaatsAfnemerindicatie {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private PersoonAfnemerindicatieService afnemerindicatiesService;
    @Inject
    private GeneriekeOnderhoudAfnemerindicatieStappen.ValideerRegelsPlaatsing valideerRegelsPlaatsing;


    private OnderhoudAfnemerindicatiePlaatsAfnemerindicatieStap() {

    }

    @Override
    public void voerStapUit(final PlaatsAfnemerindicatieParams params) throws StapException {
        final ToegangLeveringsAutorisatie tla = params.getToegangLeveringsautorisatie();
        /*
          Deze dienst bepaalt de verantwoording van de te plaatsen afnemerindicatie.
          Dit is bijv. mutatielevering obv. doelbinding.
         */
        controleerDienstInLijst(AutAutUtil.zoekDiensten(tla.getLeveringsautorisatie()), params.getVerantwoordingDienstId());
        valideerRegelsPlaatsing.valideer(new GeneriekeOnderhoudAfnemerindicatieStappen.ValideerPlaatsAfnemerindicatieParams(
                tla, params.getPersoonslijst(), params.getDatumAanvangMaterielePeriode(), params.getDatumEindeVolgen()
        ));
        try {
            final AfnemerindicatieParameters
                    afnemerindicatieParameters =
                    new AfnemerindicatieParameters(params.getPersoonslijst().getId(), params.getPersoonslijst().getPersoonLockVersie(),
                            params.getPersoonslijst().getAfnemerindicatieLockVersie());
            afnemerindicatiesService
                    .plaatsAfnemerindicatie(
                            afnemerindicatieParameters, tla.getGeautoriseerde().getPartij(),
                            tla.getLeveringsautorisatie().getId(),
                            params.getVerantwoordingDienstId(),
                            params.getDatumAanvangMaterielePeriode(),
                            params.getDatumEindeVolgen(), params.getTijdstipRegistratie());
            afnemerindicatiesService.updateAfnemerindicatieBlob(afnemerindicatieParameters);
        } catch (BlobException e) {
            throw new StapException(e);
        }

        LOGGER.info("Afnemerindicatie geplaatst met uitvoer van regels. Leveringsautorisatie {}, persoonId {}, dienstId {}, partijCode {}",
                tla.getId(), params.getPersoonslijst().getId(), params.getVerantwoordingDienstId(),
                tla.getGeautoriseerde().getPartij().getCode());
    }

    private Dienst controleerDienstInLijst(final Iterable<Dienst> diensten, final int dienstId) throws StapException {
        for (final Dienst dienst : diensten) {
            if (dienst.getId() == dienstId) {
                return dienst;
            }
        }
        throw new StapMeldingException(Regel.R2130);
    }
}
