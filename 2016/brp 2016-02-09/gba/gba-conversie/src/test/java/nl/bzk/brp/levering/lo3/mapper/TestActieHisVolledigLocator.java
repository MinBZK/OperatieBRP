/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.hisvolledig.kern.ActieHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.ActieHisVolledigImplBuilder;

public class TestActieHisVolledigLocator implements ActieHisVolledigLocator {

    @Override
    public ActieHisVolledig locate(final ActieModel actieModel) {
        final ActieHisVolledigImplBuilder builder =
                new ActieHisVolledigImplBuilder(
                    actieModel.getSoort().getWaarde(),
                    actieModel.getPartij().getWaarde(),
                    actieModel.getDatumAanvangGeldigheid(),
                    actieModel.getDatumEindeGeldigheid(),
                    actieModel.getTijdstipRegistratie(),
                    actieModel.getDatumOntlening());

        return builder.build();
    }
}
