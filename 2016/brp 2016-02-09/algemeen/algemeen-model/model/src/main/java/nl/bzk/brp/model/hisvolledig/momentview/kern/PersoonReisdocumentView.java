/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonReisdocumentHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonReisdocumentHisMoment;


/**
 * View klasse voor Persoon \ Reisdocument.
 */
public final class PersoonReisdocumentView extends AbstractPersoonReisdocumentView implements PersoonReisdocumentHisMoment {

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoonReisdocument hisVolledig instantie voor deze view.
     * @param formeelPeilmoment   formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public PersoonReisdocumentView(final PersoonReisdocumentHisVolledig persoonReisdocument,
        final DatumTijdAttribuut formeelPeilmoment, final DatumAttribuut materieelPeilmoment)
    {
        super(persoonReisdocument, formeelPeilmoment, materieelPeilmoment);
    }

}
