/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledig;
import nl.bzk.brp.model.logisch.kern.FamilierechtelijkeBetrekkingBasis;
import org.apache.commons.collections.Predicate;

/**
 * View klasse voor Familierechtelijke Betrekking.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentPredikaatViewModelGenerator")
public abstract class AbstractFamilierechtelijkeBetrekkingPredikaatView extends RelatiePredikaatView implements ModelMoment,
        FamilierechtelijkeBetrekkingBasis, ElementIdentificeerbaar
{

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param relatie hisVolledig instantie voor deze view.
     * @param predikaat het predikaat.
     */
    public AbstractFamilierechtelijkeBetrekkingPredikaatView(final FamilierechtelijkeBetrekkingHisVolledig relatie, final Predicate predikaat) {
        super(relatie, predikaat);

    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.FAMILIERECHTELIJKEBETREKKING;
    }

}
