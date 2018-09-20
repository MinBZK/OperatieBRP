/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.regels.Bedrijfsregel;
import nl.bzk.brp.business.regels.BerichtBedrijfsregel;
import nl.bzk.brp.business.regels.BevragingBedrijfsregelManager;
import nl.bzk.brp.business.regels.RegelInterface;
import nl.bzk.brp.business.regels.context.AutorisatieRegelContext;
import nl.bzk.brp.business.regels.context.BerichtRegelContext;
import nl.bzk.brp.business.regels.context.RegelContext;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;


/**
 * Abstracte super class voor de stappen die bedrijfsregels uitvoeren.
 */
public abstract class AbstractVerwerkingRegelsStap extends
    AbstractBerichtVerwerkingStap<BevragingsBericht, BevragingBerichtContextBasis, BevragingResultaat>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Bepaalt de soort handeling die bij het bericht hoort.
     *
     * @param bericht het bericht
     * @return de soort handeling voor dit bericht
     */
    protected SoortAdministratieveHandeling bepaalSoortAdministratieveHandelingVoorBericht(
        final BevragingsBericht bericht)
    {
        if (SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON == bericht.getSoort().getWaarde()
            || SoortBericht.BHG_BVG_GEEF_DETAILS_PERSOON == bericht.getSoort().getWaarde())
        {
            return SoortAdministratieveHandeling.GEEF_DETAILS_PERSOON;
        } else {
            throw new IllegalArgumentException("Soort bericht wordt niet ondersteund door de bevraging service: "
                + bericht.getSoort().getWaarde());
        }
    }

    /**
     * Verwerk de bedrijfregel.
     *
     * @param bericht                    bericht
     * @param context                    context
     * @param regel                      regel
     * @param falendeRegelsMetEntiteiten falende regels met entiteiten
     */
    protected void
    verwerkRegel(
        final BevragingsBericht bericht,
        final BevragingBerichtContextBasis context,
        final RegelInterface regel,
        final Map<BerichtIdentificeerbaar, Map<RegelInterface, List<BerichtIdentificeerbaar>>> falendeRegelsMetEntiteiten)
    {

        final PersoonView rootObject;
        if (context.getPersoonHisVolledigImpl() != null) {
            rootObject = new PersoonView(context.getPersoonHisVolledigImpl());
        } else {
            rootObject = null;
        }

        final Class contextType = bepaalContextType(regel);

        final RegelContext regelContext;
        if (contextType == AutorisatieRegelContext.class) {
            final Leveringinformatie leveringinformatie = context.getLeveringinformatie();
            regelContext =
                new AutorisatieRegelContext(leveringinformatie.getToegangLeveringsautorisatie(),
                    leveringinformatie.getDienst(), rootObject,
                    bepaalSoortAdministratieveHandelingVoorBericht(bericht));
        } else if (contextType == BerichtRegelContext.class) {
            regelContext = new BerichtRegelContext(null, bepaalSoortAdministratieveHandelingVoorBericht(bericht), bericht);

        } else {
            regelContext = null;
            LOGGER.warn("Er is een regel geconfigureerd in de bevraging-service met een niet-"
                + "ondersteunde regelcontext: {}, {}", regel.getRegel().getCode(), contextType.getCanonicalName());
        }

        final BerichtParametersGroepBericht parameters = bericht.getParameters();
        if (falendeRegelsMetEntiteiten.get(parameters) == null) {
            falendeRegelsMetEntiteiten.put(parameters, new HashMap<RegelInterface, List<BerichtIdentificeerbaar>>());
        }


        LOGGER.debug("Voert regel uit: {}", regel.getRegel().getCode());
        if (regel instanceof Bedrijfsregel) {
            if (!((Bedrijfsregel) regel).valideer(regelContext)
                && falendeRegelsMetEntiteiten.get(parameters).get(regel) == null)
            {
                falendeRegelsMetEntiteiten.get(parameters).put(regel, null);
            }
        } else if (regel instanceof BerichtBedrijfsregel) {
            final List<BerichtIdentificeerbaar> objectenDieDeRegelOvertreden = new ArrayList<>();
            objectenDieDeRegelOvertreden.addAll(((BerichtBedrijfsregel) regel).valideer(regelContext));

            if (!objectenDieDeRegelOvertreden.isEmpty()) {
                if (falendeRegelsMetEntiteiten.get(parameters).get(regel) == null) {
                    falendeRegelsMetEntiteiten.get(parameters).put(regel, new ArrayList<BerichtIdentificeerbaar>());
                }
                falendeRegelsMetEntiteiten.get(parameters).get(regel).addAll(objectenDieDeRegelOvertreden);
            }
        }
    }

    /**
     * Bepaalt het context type van de bedrijfsregel.
     *
     * @param regel de bedrijfsregel
     * @return Klasse die het contexttype aanduidt.
     */
    private Class bepaalContextType(final RegelInterface regel) {
        final Class contextType;
        if (regel instanceof Bedrijfsregel) {
            contextType = ((Bedrijfsregel) regel).getContextType();
        } else if (regel instanceof BerichtBedrijfsregel) {
            contextType = ((BerichtBedrijfsregel) regel).getContextType();
        } else {
            contextType = null;
            LOGGER.error("Het type bedrijfsregel wordt niet ondersteund.");
        }
        return contextType;
    }

    /**
     * Geeft de implementatie van de bedrijfsregel manager, zodat alle methoden van de manager voor dit project beschikbaar zijn.
     *
     * @return de bedrijfsregel manager implementatie
     */
    protected BevragingBedrijfsregelManager getBedrijfsregelManagerImpl() {
        return (BevragingBedrijfsregelManager) this.getBedrijfsregelManager();
    }

}
