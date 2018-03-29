/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.api;

import com.google.common.collect.Sets;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.ExpressieTaalConstanten;
import nl.bzk.brp.domain.expressie.SelectieLijst;
import nl.bzk.brp.domain.expressie.VariabeleExpressie;
import nl.bzk.brp.domain.expressie.functie.FunctieExpressie;
import nl.bzk.brp.domain.expressie.BRPExpressies;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import nl.bzk.brp.domain.expressie.util.ExpressietaalTestPersoon;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 */
public class BRPExpressiesTest {

    @Test
    public void parse() throws ExpressieException {
        Assert.assertTrue(ExpressieParser.parse("persoon") instanceof VariabeleExpressie);
        Assert.assertTrue(ExpressieParser.parse("WAAR").alsBoolean());
        Assert.assertTrue(ExpressieParser.parse("GEWIJZIGD(oud, nieuw)") instanceof FunctieExpressie);
    }

    @Test
    public void evalueerAttenderingsCriterium() throws ExpressieException {

        final Expressie expressie = Mockito.mock(Expressie.class);

        ArgumentCaptor<Context> argumentCaptor = ArgumentCaptor.forClass(Context.class);
        Mockito.when(expressie.evalueer(argumentCaptor.capture())).thenReturn(expressie);

        BRPExpressies.evalueerAttenderingsCriterium(expressie,
                ExpressietaalTestPersoon.PERSOONSLIJST, ExpressietaalTestPersoon.PERSOONSLIJST);

        final Context value = argumentCaptor.getValue();
        Assert.assertNotNull(value.getProperty(ExpressieTaalConstanten.CONTEXT_PERSOON_OUD));
        Assert.assertNotNull(value.getProperty(ExpressieTaalConstanten.CONTEXT_PERSOON_NIEUW));
    }

    @Test
    public void evalueer() throws ExpressieException {

        final Expressie expressie = Mockito.mock(Expressie.class);

        ArgumentCaptor<Context> argumentCaptor = ArgumentCaptor.forClass(Context.class);
        Mockito.when(expressie.evalueer(argumentCaptor.capture())).thenReturn(expressie);

        BRPExpressies.evalueer(expressie,
                ExpressietaalTestPersoon.PERSOONSLIJST);

        final Context value = argumentCaptor.getValue();
        Assert.assertNull(value.getProperty(ExpressieTaalConstanten.CONTEXT_PERSOON_OUD));
        Assert.assertNull(value.getProperty(ExpressieTaalConstanten.CONTEXT_PERSOON_NIEUW));
    }

    @Test
    public void evalueerMetSelectieDatumEnSelectielijst() throws ExpressieException {
        final Expressie expressie = Mockito.mock(Expressie.class);

        ArgumentCaptor<Context> argumentCaptor = ArgumentCaptor.forClass(Context.class);
        Mockito.when(expressie.evalueer(argumentCaptor.capture())).thenReturn(expressie);

        final SelectieLijst selectieLijst = new SelectieLijst(1,
                ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER),
                Sets.newHashSet("123456789"));
        BRPExpressies.evalueerMetSelectieDatumEnSelectielijst(expressie,
                ExpressietaalTestPersoon.PERSOONSLIJST, 20120101, selectieLijst);

        final Context value = argumentCaptor.getValue();
        Assert.assertNotNull(value.getProperty(ExpressieTaalConstanten.PROPERTY_DATUM_START_SELECTIE));
        Assert.assertNotNull(value.getProperty(ExpressieTaalConstanten.CONTEXT_PROPERTY_SELECTIE_LIJST));
    }

    @Test
    public void evalueerMetSelectieDatum() throws ExpressieException {
        final Expressie expressie = Mockito.mock(Expressie.class);

        ArgumentCaptor<Context> argumentCaptor = ArgumentCaptor.forClass(Context.class);
        Mockito.when(expressie.evalueer(argumentCaptor.capture())).thenReturn(expressie);

        final SelectieLijst selectieLijst = new SelectieLijst(1,
                ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER),
                Sets.newHashSet("123456789"));
        BRPExpressies.evalueerMetSelectieDatum(expressie,
                ExpressietaalTestPersoon.PERSOONSLIJST, 20120101);

        final Context value = argumentCaptor.getValue();
        Assert.assertNotNull(value.getProperty(ExpressieTaalConstanten.PROPERTY_DATUM_START_SELECTIE));
    }

}
