/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import org.junit.Before;
import org.junit.Test;


public class AdministratieveHandelingElementMockBijhoudingsPersoonTest extends AbstractElementTest {
    private ElementBuilder builder ;
    private AdministratieveHandelingElement aHandeling;
    private ActieElement actieElement;


    @Before
    public void setup(){
        builder = new ElementBuilder();
        actieElement = mock(ActieElement.class);
        final ElementBuilder.AdministratieveHandelingParameters parameters = new ElementBuilder.AdministratieveHandelingParameters();
        parameters.acties(Collections.singletonList(actieElement));
        parameters.partijCode(Z_PARTIJ.getCode());
        parameters.soort(AdministratieveHandelingElementSoort.ERKENNING);
        aHandeling = builder.maakAdministratieveHandelingElement("ah",parameters);
    }

    @Test
    @Bedrijfsregel(Regel.R1790)
    public void test1790() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BijhoudingPersoon bPersoon = mock(BijhoudingPersoon.class);

        PersoonElement persoonElement = builder.maakPersoonGegevensElement("comm","1234");
        persoonElement.setVerzoekBericht(getBericht());
        when(bPersoon.getPersoonElementen()).thenReturn(Collections.singletonList(persoonElement));
        when(actieElement.getHoofdPersonen()).thenReturn(Collections.singletonList(bPersoon));
        when(actieElement.getPeilDatum()).thenReturn(new DatumElement(20170101));

        Method method = AdministratieveHandelingElement.class.getDeclaredMethod("controleerOfReisdocumentOngeldigWordt", List.class);
        method.setAccessible(true);
        final List<MeldingElement> meldingen = new LinkedList<>();
        when(bPersoon.isErEenAanpassingWaardoorReisdocumentVervalt(anyInt())).thenReturn(false);
        method.invoke(aHandeling, meldingen);
        controleerRegels(meldingen);
    }

    @Test
    @Bedrijfsregel(Regel.R1790)
    public void test1790Wijziging() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BijhoudingPersoon bPersoon = mock(BijhoudingPersoon.class);
        PersoonElement persoonElement = builder.maakPersoonGegevensElement("comm","1234");
        persoonElement.setVerzoekBericht(getBericht());
        when(bPersoon.getPersoonElementen()).thenReturn(Collections.singletonList(persoonElement));
        when(actieElement.getHoofdPersonen()).thenReturn(Collections.singletonList(bPersoon));
        when(actieElement.getPeilDatum()).thenReturn(new DatumElement(20170101));

        Method method = AdministratieveHandelingElement.class.getDeclaredMethod("controleerOfReisdocumentOngeldigWordt", List.class);
        method.setAccessible(true);
        final List<MeldingElement> meldingen = new LinkedList<>();
        when(bPersoon.isErEenAanpassingWaardoorReisdocumentVervalt(anyInt())).thenReturn(true);
        method.invoke(aHandeling, meldingen);
        controleerRegels(meldingen,Regel.R1790);
    }
}