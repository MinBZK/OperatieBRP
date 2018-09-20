/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels.BRBY0009;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.afgeleidadministratief.AfgeleidAdministratiefGroepVerwerker;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository;
import nl.bzk.brp.dataaccess.repository.RelatieHisVolledigRepository;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.HisVolledigRootObject;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import org.springframework.stereotype.Component;

/**
 * Deze stap zorgt ervoor dat alle door de uitvoerders bijgewerkte en aangemaakte rootobjecten worden opgeslagen, zowel
 * de relaties als de personen.
 */
@Component
public class BijhoudingRootObjectenOpslagStap {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private PersoonHisVolledigRepository persoonRepository;

    @Inject
    private RelatieHisVolledigRepository relatieRepository;

    /**
     *
     * @param bericht           bericht
     * @param berichtContext    berichtContext
     * @return Resultaat
     */
    public Resultaat voerStapUit(final BijhoudingsBericht bericht, final BijhoudingBerichtContext berichtContext) {
        werkAfgeleidAdministratiefGroepBijVoorPersonen(bericht, berichtContext);

        //TODO: Moet hier niet ook met de complete bijgehouden personen gewerkt worden?
        // Dat kan namelijk meer bevatten dan alleen de aangemaakte en bestaande root objecten.
        slaNieuweRootobjectenOp(berichtContext.getAangemaakteBijhoudingsRootObjecten().values());
        slaNieuweRootobjectenOp(berichtContext.getNietInBerichtMaarWelAangemaakteNietIngeschrevenen());
        slaGewijzigdeRootobjectenOp(berichtContext.getBestaandeBijhoudingsRootObjecten().values());

        return Resultaat.LEEG;
    }

    /**
     * Werkt de afgeleid administratief groep bij voor alle personen.
     *
     * @param bericht        bijhoudings bericht
     * @param berichtContext bericht context met informatie voor de afgeleid administratief groep.
     */
    private void werkAfgeleidAdministratiefGroepBijVoorPersonen(final BijhoudingsBericht bericht, final BijhoudingBerichtContext berichtContext) {
        final ActieBericht hoofdActieBericht = bericht.getAdministratieveHandeling().getHoofdActie();
        final ActieModel hoofdActieModel = berichtContext.getActieMapping().get(hoofdActieBericht);
        final List<PersoonBericht> hoofdPersonenBericht = new BRBY0009().bepaalHoofdPersonen(bericht);
        final List<PersoonHisVolledig> hoofdPersonenContext = new ArrayList<>();

        for (final PersoonBericht persoonBericht : hoofdPersonenBericht) {
            hoofdPersonenContext.add((PersoonHisVolledig) berichtContext.zoekHisVolledigRootObject(persoonBericht));
        }

        for (final PersoonHisVolledig persoon : berichtContext.getBijgehoudenPersonen()) {
            if (persoon instanceof PersoonHisVolledigImpl) {
                // VR00027a:
                new AfgeleidAdministratiefGroepVerwerker(
                        persoon, hoofdActieModel, hoofdPersonenContext.contains(persoon)).leidAf();
            }
        }
    }

    /**
     * Slaat alle nieuwe rootobjecten op.
     *
     * @param rootobjecten de nieuwe rootobjecten.
     */
    private void slaNieuweRootobjectenOp(final Collection<? extends HisVolledigRootObject> rootobjecten) {
        for (final HisVolledigRootObject rootObject : rootobjecten) {
            if (rootObject instanceof RelatieHisVolledig) {
                LOGGER.info("Opslaan nieuwe relatie van type '{}' met {} betrokkenheden.",
                        ((RelatieHisVolledig) rootObject).getSoort(),
                        ((RelatieHisVolledig) rootObject).getBetrokkenheden().size());
                relatieRepository.schrijfGenormalizeerdModel((RelatieHisVolledig) rootObject);
            } else if (rootObject instanceof PersoonHisVolledig) {
                LOGGER.info("Opslaan nieuw persoon van soort '{}'.", ((PersoonHisVolledig) rootObject).getSoort());
                persoonRepository.opslaanNieuwPersoon((PersoonHisVolledigImpl) rootObject);
            }
        }
    }

    /**
     * Sla alle gewijzigde (reeds in database bestaande) rootobjecten op.
     *
     * @param rootobjecten collectie van gewijzigde rootobjecten.
     */
    private void slaGewijzigdeRootobjectenOp(final Collection<HisVolledigRootObject> rootobjecten) {
        for (final HisVolledigRootObject rootObject : rootobjecten) {
            if (rootObject instanceof RelatieHisVolledigImpl) {
                LOGGER.info("Opslaan gewijzigde relatie met id '{}' en met {} betrokkenheden.",
                        ((RelatieHisVolledigImpl) rootObject).getID(),
                        ((RelatieHisVolledig) rootObject).getBetrokkenheden().size());
                relatieRepository.schrijfGenormalizeerdModel((RelatieHisVolledigImpl) rootObject);
            } else if (rootObject instanceof PersoonHisVolledigImpl) {
                LOGGER.info("Opslaan gewijzigde persoon met id '{}'.", ((PersoonHisVolledigImpl) rootObject).getID());
                persoonRepository.schrijfGenormalizeerdModel((PersoonHisVolledigImpl) rootObject);
            }
        }
    }

}
