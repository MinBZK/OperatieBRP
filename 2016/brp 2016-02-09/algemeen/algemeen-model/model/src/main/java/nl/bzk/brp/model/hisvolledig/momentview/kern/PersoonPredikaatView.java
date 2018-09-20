/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonHisMoment;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteit;
import org.apache.commons.collections.Predicate;

/**
 * View klasse voor Persoon.
 */
public final class PersoonPredikaatView extends AbstractPersoonPredikaatView implements ModelMoment, ElementIdentificeerbaar, PersoonHisMoment {

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoon   hisVolledig instantie voor deze view.
     * @param predikaat het predikaat.
     */
    public PersoonPredikaatView(final PersoonHisVolledig persoon, final Predicate predikaat) {
        super(persoon, predikaat);
    }


    @Override
    public boolean heeftNederlandseNationaliteit() {
        boolean heeftNederlandseNationaliteit = false;
        for (final PersoonNationaliteit nationaliteit : this.getNationaliteiten()) {
            if (nationaliteit.getNationaliteit().getWaarde().getCode().equals(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE)) {
                heeftNederlandseNationaliteit = true;
            }
        }
        return heeftNederlandseNationaliteit;
    }

    @Override
    public boolean isIngezetene() {
        return this.getBijhouding() != null
            && this.getBijhouding().getBijhoudingsaard().getWaarde() == Bijhoudingsaard.INGEZETENE;
    }

    @Override
    public boolean isPersoonGelijkAan(final Persoon persoon) {
        final boolean resultaat;
        if (!(persoon instanceof PersoonView)) {
            resultaat = false;
        } else {
            final PersoonView persoonView = (PersoonView) persoon;
            resultaat = persoonView.getID().equals(this.getID());
        }

        return resultaat;
    }

}
