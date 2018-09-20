/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.AbstractPersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.logisch.ist.Stapel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;


public class IstTabelRepositoryTest extends AbstractIntegratieTest {

    @Autowired
    private IstTabelRepository istTabelRepository;

    @Test
    public final void testOphalenIstStapels() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoon = new PersoonHisVolledigView(builder.build(), null);
        final Field persoonField = ReflectionUtils.findField(AbstractPersoonHisVolledigView.class, "persoon");
        persoonField.setAccessible(true);
        final PersoonHisVolledig persoonHisVolledig = (PersoonHisVolledig) ReflectionUtils.getField(persoonField,
                                                                                                    persoon);
        final Field idField = ReflectionUtils.findField(PersoonHisVolledigImpl.class, "iD");
        idField.setAccessible(true);
        ReflectionUtils.setField(idField, persoonHisVolledig, 1);
        ReflectionUtils.setField(persoonField, persoon, persoonHisVolledig);
        final List<Stapel> resultaat = istTabelRepository.leesIstStapels(persoon);

        assertThat(resultaat.size(), CoreMatchers.is(2));
    }
}
