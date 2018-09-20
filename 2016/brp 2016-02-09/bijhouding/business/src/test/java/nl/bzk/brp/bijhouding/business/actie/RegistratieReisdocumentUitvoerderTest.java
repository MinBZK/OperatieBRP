/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Verwerkingsregel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonReisdocumentHisVolledigImpl;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class RegistratieReisdocumentUitvoerderTest {

    @Test
    public void testReisdocumentenNull() {
        PersoonBericht persoon = maakBericht(null);
        persoon.setReisdocumenten(null);
        RegistratieReisdocumentUitvoerder uitvoerder = maakUitvoerder(persoon, null);
        uitvoerder.verzamelVerwerkingsregels();

        List<Verwerkingsregel> verwerkingsregels = (List<Verwerkingsregel>) ReflectionTestUtils.getField(uitvoerder, "verwerkingsregels");
        Assert.assertEquals(0, verwerkingsregels.size());
    }

    @Test
    public void testNieuwReisdocument() {
        RegistratieReisdocumentUitvoerder uitvoerder = maakUitvoerder(maakBericht(null), maakModel());
        uitvoerder.verzamelVerwerkingsregels();

        List<Verwerkingsregel> verwerkingsregels = (List<Verwerkingsregel>) ReflectionTestUtils.getField(uitvoerder, "verwerkingsregels");
        Assert.assertEquals(1, verwerkingsregels.size());
        PersoonReisdocumentHisVolledigImpl persoonReisdocumentHisVolledig =
                (PersoonReisdocumentHisVolledigImpl) ReflectionTestUtils.getField(verwerkingsregels.get(0), "model");
        Assert.assertNull(persoonReisdocumentHisVolledig.getID());
    }

    @Test
    public void testBestaandReisdocument() {
        // Grapje hier: de his volledigs komen niet in een vaste volgorde terug, maar we willen wel coverage
        // op de id equals, dus voegen we maar wat extra id's toe om de kans op als eerste de juiste te verkleinen. :)
        RegistratieReisdocumentUitvoerder uitvoerder = maakUitvoerder(maakBericht(5), maakModel(1, 2, 3, 4, 5, 6, 7, 8, 9));
        uitvoerder.verzamelVerwerkingsregels();

        List<Verwerkingsregel> verwerkingsregels = (List<Verwerkingsregel>) ReflectionTestUtils.getField(uitvoerder, "verwerkingsregels");
        Assert.assertEquals(1, verwerkingsregels.size());
        PersoonReisdocumentHisVolledigImpl persoonReisdocumentHisVolledig =
                (PersoonReisdocumentHisVolledigImpl) ReflectionTestUtils.getField(verwerkingsregels.get(0), "model");
        Assert.assertEquals(5, persoonReisdocumentHisVolledig.getID().intValue());
    }

    @Test(expected = IllegalStateException.class)
    public void testNietGevondenReisdocument() {
        RegistratieReisdocumentUitvoerder uitvoerder = maakUitvoerder(maakBericht(1), maakModel());
        uitvoerder.verzamelVerwerkingsregels();
    }

    private PersoonBericht maakBericht(final Integer reisdocumentId) {
        PersoonBericht persoon = new PersoonBericht();

        persoon.setReisdocumenten(new ArrayList<PersoonReisdocumentBericht>());
        PersoonReisdocumentBericht persoonReisdocumentBericht = new PersoonReisdocumentBericht();
        if (reisdocumentId != null) {
            persoonReisdocumentBericht.setObjectSleutel("" + reisdocumentId);
        }
        persoon.getReisdocumenten().add(persoonReisdocumentBericht);

        return persoon;
    }

    private PersoonHisVolledigImpl maakModel(final Integer ... reisdocumentIds) {
        PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();

        for (Integer reisdocumentId : reisdocumentIds) {
            if (reisdocumentId != null) {
                PersoonReisdocumentHisVolledigImpl reisdocument = new PersoonReisdocumentHisVolledigImpl(persoon, null);
                ReflectionTestUtils.setField(reisdocument, "iD", reisdocumentId);
                persoon.getReisdocumenten().add(reisdocument);
            }
        }

        return persoon;
    }

    private RegistratieReisdocumentUitvoerder maakUitvoerder(final PersoonBericht persoonBericht,
            final PersoonHisVolledigImpl persoonHisVolledig)
    {
        RegistratieReisdocumentUitvoerder uitvoerder = new RegistratieReisdocumentUitvoerder();
        ReflectionTestUtils.setField(uitvoerder, "berichtRootObject", persoonBericht);
        ReflectionTestUtils.setField(uitvoerder, "modelRootObject", persoonHisVolledig);
        return uitvoerder;
    }

}
