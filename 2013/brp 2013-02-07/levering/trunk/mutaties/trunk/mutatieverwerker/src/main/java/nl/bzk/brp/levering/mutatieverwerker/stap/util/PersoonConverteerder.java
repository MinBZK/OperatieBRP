/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.stap.util;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.hisvolledig.PersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.Persoon;


/**
 * De Class PersoonConverteerder,
 * tijdelijke klasse waarin een PersoonVolledig wordt geconverteerd naar een PersoonModel.
 */
public class PersoonConverteerder {

    /**
     * Bepaal betrokken persoon op peilmoment.
     *
     * @param persoonVolledig de persoon volledig
     * @param peilmoment de peilmoment
     * @return de persoon
     */
    public Persoon bepaalBetrokkenPersoonOpPeilmoment(final PersoonHisVolledig persoonVolledig,
            final DatumTijd peilmoment)
    {
        final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(persoonVolledig, peilmoment);
        return persoonHisVolledigView;
    }
}
