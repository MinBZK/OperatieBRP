/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.populatie;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import nl.bzk.brp.levering.business.filters.LeverenPersoonFilter;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;


/**
 * Implementatie van de FilterNietTeLeverenPersonenService.
 */
@Component
public class FilterNietTeLeverenPersonenServiceImpl implements FilterNietTeLeverenPersonenService {

    private List<LeverenPersoonFilter> filters;

    @Override
    public final void filterNietTeLeverenPersonen(final List<PersoonHisVolledig> personen,
        final Map<Integer, Populatie> mogelijkTeLeverenPersonen,
        final Leveringinformatie leveringAutorisatie,
        final AdministratieveHandelingModel administratieveHandeling) throws ExpressieExceptie
    {
        for (final PersoonHisVolledig persoon : personen) {
            final Populatie populatie = mogelijkTeLeverenPersonen.get(persoon.getID());

            if (!moetGeleverdWorden(persoon, populatie, leveringAutorisatie, administratieveHandeling)) {
                mogelijkTeLeverenPersonen.remove(persoon.getID());
            }
        }
    }

    /**
     * Bepaalt op basis van de populatie, de leveringsautorisatie en de administratieve handeling of de persoon geleverd moet worden.
     *
     * @param persoon                  De persoon.
     * @param populatie                De populatie van de persoon.
     * @param leveringAutorisatie      De leveringAutorisatie
     * @param administratieveHandeling De administratieve handeling.
     * @return boolean true als geleverd moet worden, anders false.
     * @brp.bedrijfsregel VR00062
     */
    @Regels(Regel.VR00062)
    private boolean moetGeleverdWorden(final PersoonHisVolledig persoon, final Populatie populatie,
        final Leveringinformatie leveringAutorisatie,
        final AdministratieveHandelingModel administratieveHandeling) throws ExpressieExceptie
    {
        for (final LeverenPersoonFilter filter : filters) {
            final boolean doorgaan = filter.magLeverenDoorgaan(persoon, populatie, leveringAutorisatie, administratieveHandeling);

            if (!doorgaan) {
                return false;
            }
        }

        return true;
    }

    @Resource
    public final void setFilters(final List<LeverenPersoonFilter> filters) {
        this.filters = filters;
    }

    /**
     * Initialiseer deze klasse na instantiering.
     */
    @PostConstruct
    public final void init() {
        Collections.sort(filters, new AnnotationAwareOrderComparator());
    }
}
