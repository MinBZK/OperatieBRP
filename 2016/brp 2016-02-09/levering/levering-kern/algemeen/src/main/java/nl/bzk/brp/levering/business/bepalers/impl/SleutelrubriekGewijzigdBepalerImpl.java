/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers.impl;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.literals.BrpObjectExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.levering.business.bepalers.SleutelrubriekGewijzigdBepaler;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonPredikaatView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonUitgeslotenActiesPredikaat;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.springframework.stereotype.Component;

import java.util.Set;


/**
 * Implementatie van {@link nl.bzk.brp.levering.business.bepalers.SleutelrubriekGewijzigdBepaler}.
 *
 * @brp.bedrijfsregel VR00062
 */
@Regels(Regel.VR00062)
@Component
public class SleutelrubriekGewijzigdBepalerImpl extends AbstractBepaler implements SleutelrubriekGewijzigdBepaler {

    /**
     * De variabele voor de oude situatie, zoals gebruikt in de attenderingsCriterium expressie.
     */
    public static final String  ATTENDERING_EXPRESSIE_OUD                                 = "oud";

    /**
     * De variabele voor de nieuwe situatie, zoals gebruikt in de attenderingsCriterium expressie.
     */
    public static final String  ATTENDERING_EXPRESSIE_NIEUW                               = "nieuw";

    private static final Logger LOGGER                                                    = LoggerFactory.getLogger();

    private static final String ATTENDERINGSCRITERIUM_EVALUEERT_NAAR_WAARDE_NULL_ONBEKEND =
            "Attenderingscriterium evalueert naar waarde NULL (onbekend) voor leveringsautorisatie: '{}' en persoon met id: {}";

    @Override
    public final boolean bepaalAttributenGewijzigd(final PersoonHisVolledig persoon,
            final AdministratieveHandelingModel administratieveHandeling, final Expressie expressie,
            final Leveringsautorisatie leveringsautorisatie)
    {
        final Set<Long> toekomstActies =
            getToekomstigeActieService().geefToekomstigeActieIds(administratieveHandeling, persoon);

        final Persoon oudPersoonView = bepaalOudPersoonView(persoon, administratieveHandeling);
        final Persoon nieuwPersoonView =
            new PersoonPredikaatView(persoon, new PersoonUitgeslotenActiesPredikaat(toekomstActies));

        final Expressie resultaatExpressie =
            evalueerAttenderingsCriterium(expressie, oudPersoonView, nieuwPersoonView, leveringsautorisatie, persoon.getID());

        return resultaatExpressie.alsBoolean();
    }

    /**
     * Bepaalt de persoon view van de oude situatie.
     *
     * @param persoon de persoon
     * @param administratieveHandeling administratieve handeling
     * @return the persoon
     */
    private Persoon bepaalOudPersoonView(final PersoonHisVolledig persoon,
            final AdministratieveHandelingModel administratieveHandeling)
    {
        final boolean isNieuwPersoon = persoon.getPersoonAfgeleidAdministratiefHistorie().getAantal() == 1;

        final Persoon oudPersoonView;
        if (isNieuwPersoon) {
            oudPersoonView = new PersoonModel(persoon.getSoort());
        } else {
            final Set<Long> huidigeEnToekomstActies =
                getToekomstigeActieService().geefToekomstigeActieIdsPlusHuidigeHandeling(administratieveHandeling,
                        persoon);
            oudPersoonView =
                new PersoonPredikaatView(persoon, new PersoonUitgeslotenActiesPredikaat(huidigeEnToekomstActies));
        }

        return oudPersoonView;
    }

    /**
     * Evalueer het attenderingsCriterium.
     *
     * @param expressie de attenderingsCriterium expressie
     * @param oud de oude situatie van de persoon
     * @param nieuw de nieuwe situatie van de persoon
     * @param leveringsautorisatie het leveringsautorisatie waarvoor het attenderingscriterium wordt geevalueerd
     * @param persoonID het id van de betrokken persoon
     * @return de resultaat expressie
     */
    private Expressie evalueerAttenderingsCriterium(final Expressie expressie, final Persoon oud, final Persoon nieuw,
            final Leveringsautorisatie leveringsautorisatie, final Integer persoonID)
    {
        final Context context = new Context();
        context.definieer(ATTENDERING_EXPRESSIE_OUD, new BrpObjectExpressie(oud, ExpressieType.PERSOON));
        context.definieer(ATTENDERING_EXPRESSIE_NIEUW, new BrpObjectExpressie(nieuw, ExpressieType.PERSOON));

        Expressie result = expressie.evalueer(context);
        if (result == null) {
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_VR00062,
                    ATTENDERINGSCRITERIUM_EVALUEERT_NAAR_WAARDE_NULL_ONBEKEND, leveringsautorisatie.getID(),
                    persoonID);
            result = NullValue.getInstance();
        }
        return result;
    }
}
