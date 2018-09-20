/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel;


public class HisOuderOuderlijkGezagBuilder extends
        AbstractMaterieleHistorieEntiteitBuilder<HisOuderOuderlijkGezagModel>
{

    private JaNeeAttribuut heeftGezag;

    public static HisOuderOuderlijkGezagBuilder defaultValues() {
        final HisOuderOuderlijkGezagBuilder b = new HisOuderOuderlijkGezagBuilder();
        b.heeftGezag = JaNeeAttribuut.NEE;

        return b;
    }

    public HisOuderOuderlijkGezagBuilder heeftGezag(final JaNeeAttribuut gezag) {
        this.heeftGezag = gezag;
        return this;
    }

    @Override
    public HisOuderOuderlijkGezagModel build() {
        final HisOuderOuderlijkGezagModel hisOuderOuderlijkGezagModel = mock(HisOuderOuderlijkGezagModel.class);

        when(hisOuderOuderlijkGezagModel.getMaterieleHistorie()).thenReturn(getHistorie());
        when(hisOuderOuderlijkGezagModel.getIndicatieOuderHeeftGezag()).thenReturn(heeftGezag);

        return hisOuderOuderlijkGezagModel;
    }
}
