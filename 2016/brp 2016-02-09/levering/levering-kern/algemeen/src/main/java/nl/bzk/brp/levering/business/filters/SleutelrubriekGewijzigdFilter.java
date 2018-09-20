/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.filters;

import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.levering.business.bepalers.SleutelrubriekGewijzigdBepaler;
import nl.bzk.brp.levering.business.expressietaal.ExpressieService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.autaut.HisPersoonAfnemerindicatieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * Filter dat bepaalt of de levering door mag gaan voor diensten van de categorie {@link nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst#ATTENDERING}
 * en waar de attenderingsCriterium expressie aangeeeft dat attributen van een persoon zijn gewijzigd.
 */
@Component
@Order(20)
@Regels(Regel.VR00108)
public class SleutelrubriekGewijzigdFilter implements LeverenPersoonFilter {

    private static final Logger LOGGER                                                                                                            = LoggerFactory
        .getLogger();
    private static final String PERSOON_ZAL_NIET_GELEVERD_WORDEN_VOOR_DIENST_ATTENDERING_MET_PLAATSING_AFNEMERINDICATIE_DAAR_LEVERINGSAUTORISATIE =
        "Persoon {} zal niet geleverd worden voor dienst attendering met plaatsing afnemerindicatie daar leveringsautorisatie {} ";

    @Inject
    private SleutelrubriekGewijzigdBepaler bepaler;

    @Inject
    private ExpressieService expressieService;

    @Override
    public final boolean magLeverenDoorgaan(final PersoonHisVolledig persoon, final Populatie populatie, final Leveringinformatie leveringinformatie,
        final AdministratieveHandelingModel administratieveHandeling) throws ExpressieExceptie
    {
        boolean magLeveren = true;
        final Expressie attenderingsCriterium = expressieService.geefAttenderingsCriterium(leveringinformatie);
        final Leveringsautorisatie la = leveringinformatie.getToegangLeveringsautorisatie().getLeveringsautorisatie();

        if (SoortDienst.ATTENDERING == leveringinformatie.getDienst().getSoort() && attenderingsCriterium == null) {
            magLeveren = false;
            LOGGER.debug(PERSOON_ZAL_NIET_GELEVERD_WORDEN_VOOR_DIENST_ATTENDERING_MET_PLAATSING_AFNEMERINDICATIE_DAAR_LEVERINGSAUTORISATIE
                + "({}) geen attenderingscriterium heeft.", persoon.getID(), la.getID(), la.getNaam());
        } else if (SoortDienst.ATTENDERING == leveringinformatie.getDienst().getSoort()) {
            if (leveringinformatie.getDienst().getEffectAfnemerindicaties() == EffectAfnemerindicaties.PLAATSING) {
                final Set<? extends PersoonAfnemerindicatieHisVolledig> afnemerindicaties = persoon.getAfnemerindicaties();
                for (final PersoonAfnemerindicatieHisVolledig persoonAfnemerindicatieHisVolledig : afnemerindicaties) {
                    final HisPersoonAfnemerindicatieModel actueleRecord =
                        persoonAfnemerindicatieHisVolledig.getPersoonAfnemerindicatieHistorie().getActueleRecord();
                    final Integer laId = leveringinformatie.getToegangLeveringsautorisatie().getLeveringsautorisatie().getID();
                    final boolean idGelijk = actueleRecord != null && laId.equals(actueleRecord.getPersoonAfnemerindicatie().getLeveringsautorisatie()
                        .getWaarde().getID());
                    final boolean partijGelijk = actueleRecord != null && actueleRecord.getPersoonAfnemerindicatie().getAfnemer().getWaarde().getCode()
                        .getWaarde().
                            equals(leveringinformatie.getToegangLeveringsautorisatie().getGeautoriseerde().getPartij().getCode().getWaarde());
                    if (idGelijk && partijGelijk) {
                        LOGGER.debug(PERSOON_ZAL_NIET_GELEVERD_WORDEN_VOOR_DIENST_ATTENDERING_MET_PLAATSING_AFNEMERINDICATIE_DAAR_LEVERINGSAUTORISATIE
                            + "({}) reeds een afnemerindicatie heeft.", persoon.getID(), la.getID(), la.getNaam());
                        return false;
                    }
                }
            }
            magLeveren = bepaler.bepaalAttributenGewijzigd(persoon, administratieveHandeling, attenderingsCriterium, la);
            if (!magLeveren) {
                LOGGER.debug("Persoon {} zal niet geleverd worden voor dienst attendering daar er geen attributen zijn gewijzigd.", persoon.getID());
            }
        }

        return magLeveren;
    }
}
