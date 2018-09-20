/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import java.util.Set;
import java.util.TreeSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.ActieBronHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.ActieHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.AdministratieveHandelingBronComparator;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Actie.
 */
public final class ActieHisVolledigView extends AbstractActieHisVolledigView implements ActieHisVolledig, ElementIdentificeerbaar {

    private Set<ActieBronHisVolledig> actieBronnenCache;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param actieHisVolledig actie
     * @param predikaat        predikaat
     */
    public ActieHisVolledigView(final ActieHisVolledig actieHisVolledig, final Predicate predikaat) {
        super(actieHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param actieHisVolledig                 actie
     * @param predikaat                        predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public ActieHisVolledigView(
        final ActieHisVolledig actieHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(actieHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

    @Override
    public void setMagGeleverdWorden(final boolean vlag) {
        actie.setMagGeleverdWorden(vlag);
    }

    @Override
    public boolean isMagGeleverdWorden() {
        return actie.isMagGeleverdWorden();
    }

    /**
     * Retourneert de objectsleutel tbv marshalling naar xml. (jibx)
     *
     * @return de object sleutel.
     */
    public String getObjectSleutel() {
        return getID().toString();
    }

    /**
     * Geeft een gesorteerde lijst van actiebronnen.
     *
     * @return De lijst van actiebronnen.
     */
    public Set<ActieBronHisVolledig> getActieBronnen() {
        if (actieBronnenCache == null) {
            actieBronnenCache = new TreeSet<>(new AdministratieveHandelingBronComparator());

            for (final ActieBronHisVolledig actieBronModel : this.getBronnen()) {
                actieBronnenCache.add(actieBronModel);
            }
        }
        return actieBronnenCache;
    }

    /**
     * test method voor bindings.
     *
     * @return heeft de actie bronnen die geleverd mogen worden.
     */
    public boolean heeftActieBronnen() {
        final Set<ActieBronHisVolledig> bronnen = getActieBronnen();
        for (final ActieBronHisVolledig actieBronHisVolledig : bronnen) {
            final ActieBronHisVolledigView actieBronHisVolledigView = (ActieBronHisVolledigView) actieBronHisVolledig;
            if (actieBronHisVolledigView.isZichtbaar()) {
                return true;
            }
        }
        return false;
    }
}
