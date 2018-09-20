/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers.impl;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.levering.algemeen.service.ToekomstigeActieService;
import nl.bzk.brp.levering.business.expressietaal.ExpressieService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonPredikaatView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonUitgeslotenActiesPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.FormeleHistorieEntiteitComparator;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandeling;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;


/**
 * Abstracte populatie bepaler, bevat gedeelde code tussen de populatiebepalers die gebruikt worden om de populatie te
 * bepalen van personen die eventueel
 * geleverd moeten worden.
 */
public abstract class AbstractBepaler {

    private static final Logger     LOGGER = LoggerFactory.getLogger();

    /**
     * De toekomstige actie service die bepaalt welke acties in de toekomst liggen tov een administratieve handeling.
     */
    @Inject
    private ToekomstigeActieService toekomstigeActieService;

    @Inject
    private ExpressieService        expressieService;

    /**
     * Bepaalt de vorige tijdstip registratie als deze bestaat, anders wordt null geretourneerd.
     *
     * @param persoon the persoon
     * @param administratieveHandeling administratieve handeling
     * @return datum tijd attribuut van vorige tijdstip registratie
     */
    protected final DatumTijdAttribuut bepaalVorigeTijdstipRegistratie(final PersoonHisVolledig persoon,
            final AdministratieveHandeling administratieveHandeling)
    {
        final NavigableSet<HisPersoonAfgeleidAdministratiefModel> afgeleidAdministratiefModellen =
                new TreeSet<>(new FormeleHistorieEntiteitComparator<HisPersoonAfgeleidAdministratiefModel>());
        afgeleidAdministratiefModellen.addAll(persoon.getPersoonAfgeleidAdministratiefHistorie().getHistorie());

        final Iterator<HisPersoonAfgeleidAdministratiefModel> iterator = afgeleidAdministratiefModellen.iterator();

        while (iterator.hasNext()) {
            final HisPersoonAfgeleidAdministratiefModel model = iterator.next();
            if (model.getTijdstipRegistratie().equals(administratieveHandeling.getTijdstipRegistratie())) {
                if (iterator.hasNext()) {
                    return iterator.next().getTijdstipRegistratie();
                }
            }
        }

        return null;
    }

    /**
     * Evalueert een expressie.
     *
     * @param expressie expressie
     * @param persoon persoon die geevalueerd wordt
     * @param toekomstigeOftewelBuitengeslotenActieIds De lijst van toekomstige acties die niet meegerekend moeten
     *            worden.
     * @return true als aan expressie voldaan wordt, anders false
     */
    protected final Boolean evalueerExpressie(final Expressie expressie, final PersoonHisVolledig persoon,
            final Set<Long> toekomstigeOftewelBuitengeslotenActieIds)
    {
        final PersoonUitgeslotenActiesPredikaat predikaat = new PersoonUitgeslotenActiesPredikaat(toekomstigeOftewelBuitengeslotenActieIds);
        final PersoonPredikaatView persoonPredikaatView = new PersoonPredikaatView(persoon, predikaat);
        final Boolean resultaat;

        try {
            final Expressie expressieResultaat = expressieService.evalueer(expressie, persoonPredikaatView);
            if (expressieResultaat.isNull()) {
                resultaat = null;
            } else {
                resultaat = expressieResultaat.alsBoolean();
            }
            return resultaat;
        } catch (final ExpressieExceptie exceptie) {
            LOGGER.error("Fout bij het uitvoeren van expressie: '{}' op persoon met id {}.", expressie.alsString(), persoon.getID(), exceptie);
            return false;
        }
    }

    /**
     * Geeft de toekomstige actie service.
     *
     * @return de toekomstige actie service
     */
    protected final ToekomstigeActieService getToekomstigeActieService() {
        return toekomstigeActieService;
    }

}
