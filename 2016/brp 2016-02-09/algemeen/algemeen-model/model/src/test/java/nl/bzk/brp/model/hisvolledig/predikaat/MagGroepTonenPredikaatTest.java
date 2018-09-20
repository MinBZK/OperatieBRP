/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import java.util.Collections;
import java.util.HashSet;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

public class MagGroepTonenPredikaatTest {

    /**
     * Het predikaat geeft false indien een gegeven groep niet in de set van geautoriseerde groepen zit.
     */
    @Test
    public void testGeenGroepAutorisatie() {
        final MagGroepTonenPredikaat predikaat = new MagGroepTonenPredikaat(Collections.<ElementEnum>emptySet());
        final PersoonHisVolledigImpl persoonHisVolledig = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
        .nieuwGeboorteRecord(19780309).datumGeboorte(19780309).eindeRecord().build();
        Assert.assertFalse(predikaat.evaluate(persoonHisVolledig.getPersoonGeboorteHistorie().getActueleRecord()));
    }

    /**
     * Het predikaat geeft true indien een gegeven groep in de set van geautoriseerde groepen zit.
     */
    @Test
    public void testWelGroepAutorisatie() {
        final HashSet<ElementEnum> groepEnumSet = new HashSet<>();
        groepEnumSet.add(ElementEnum.PERSOON_GEBOORTE);
        final MagGroepTonenPredikaat predikaat = new MagGroepTonenPredikaat(groepEnumSet);
        final PersoonHisVolledigImpl persoonHisVolledig = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwGeboorteRecord(19780309).datumGeboorte(19780309).eindeRecord().build();
        Assert.assertTrue(predikaat.evaluate(persoonHisVolledig.getPersoonGeboorteHistorie().getActueleRecord()));
    }
}
