/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel;


public class HisOuderOuderschapBuilder extends AbstractMaterieleHistorieEntiteitBuilder<HisOuderOuderschapModel> {

    private JaAttribuut indicatieOuder;

    public static HisOuderOuderschapBuilder defaultValues() {
        HisOuderOuderschapBuilder b = new HisOuderOuderschapBuilder();
        b.indicatieOuder = null;

        return b;
    }

    public HisOuderOuderschapBuilder indicatieOuder(final JaAttribuut indicatie) {
        this.indicatieOuder = indicatie;
        return this;
    }

    @Override
    public HisOuderOuderschapModel build() {
        final HisOuderOuderschapModel hisOuderOuderschapModel = mock(HisOuderOuderschapModel.class);

        when(hisOuderOuderschapModel.getMaterieleHistorie()).thenReturn(getHistorie());
        when(hisOuderOuderschapModel.getIndicatieOuder()).thenReturn(indicatieOuder);

        return hisOuderOuderschapModel;
    }
}
