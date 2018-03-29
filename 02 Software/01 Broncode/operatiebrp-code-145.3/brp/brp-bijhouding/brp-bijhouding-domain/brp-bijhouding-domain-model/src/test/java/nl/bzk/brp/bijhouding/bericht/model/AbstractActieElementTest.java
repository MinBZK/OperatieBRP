/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractActieElementTest {

    List<SoortActieHandeling> actieHandelingen;
    ElementBuilder builder;

    @Before
    public void setup(){
        builder = new ElementBuilder();
    }

    @Test
    public void testVoerRegelUit(){
        actieHandelingen = Arrays.asList(new SoortActieHandeling(SoortActie.REGISTRATIE_ADRES),
                new SoortActieHandeling(SoortActie.REGISTRATIE_AANGETROFFEN_OP_ADRES,AdministratieveHandelingElementSoort.ADOPTIE));

        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement("persoon","1234");
        RegistratieAdresActieElement actie = builder.maakRegistratieAdresActieElement("adres",20010101,persoonElement);

        assertTrue(actie.voerRegelUit(actieHandelingen,AdministratieveHandelingElementSoort.ADOPTIE,SoortActie.REGISTRATIE_ADRES));
        assertTrue(actie.voerRegelUit(actieHandelingen,AdministratieveHandelingElementSoort.AANVANG_ONDERZOEK,SoortActie.REGISTRATIE_ADRES));
        assertTrue(actie.voerRegelUit(actieHandelingen,AdministratieveHandelingElementSoort.ADOPTIE,SoortActie.REGISTRATIE_AANGETROFFEN_OP_ADRES));
        assertFalse(actie.voerRegelUit(actieHandelingen,AdministratieveHandelingElementSoort.AANVANG_ONDERZOEK,SoortActie.REGISTRATIE_AANGETROFFEN_OP_ADRES));
    }


}