/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.partijen.converter;

import java.util.Set;

import nl.bzk.brp.beheer.model.Functie;
import nl.bzk.brp.beheer.model.Partij;
import nl.bzk.brp.beheer.model.Rol;
import nl.bzk.brp.beheer.web.beheer.stamgegevens.service.StamgegevensService;
import org.hibernate.ObjectNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter.ConvertiblePair;

@RunWith(MockitoJUnitRunner.class)
public class StringToEntityConverterTest {

    @Mock
    private StamgegevensService stamgegevensService;

    private StringToEntityConverter stringToEntityConverter;

    @Before
    public void setUp() {
        stringToEntityConverter = new StringToEntityConverter();
        stringToEntityConverter.setStamgegevensService(stamgegevensService);

        Mockito.when(stamgegevensService.find(Rol.class, 1)).thenReturn(new Rol(1, "Rol1"));
        Mockito.when(stamgegevensService.find(Rol.class, 2)).thenThrow(new ObjectNotFoundException(new Integer(2), Rol.class.toString()));
    }

    /**
     * Test of de opgegegeven types gemapped worden.
     */
    @Test
    public void testGetConvertibleTypes() {
        String[] entitiesToConvert = { "nl.bzk.brp.beheer.model.Partij", "nl.bzk.brp.beheer.model.Rol" };

        stringToEntityConverter.setEntitiesToConvert(entitiesToConvert);

        Set<ConvertiblePair> pairs = stringToEntityConverter.getConvertibleTypes();

        Assert.assertEquals(4, pairs.size());

        int gemapped = 0;
        for (ConvertiblePair pair : pairs) {
            if (pair.getSourceType() == String.class) {
                if (pair.getTargetType() == Partij.class) {
                    gemapped++;
                } else if (pair.getTargetType() == Rol.class) {
                    gemapped++;
                }
            }
        }

        Assert.assertEquals("Er moeten twee types gemapped zijn.", 2, gemapped);
    }

    /**
     * Test voor een niet bestaande class.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetConvertibleTypesOngeldigeClass() {
        String[] entitiesToConvert = { "nl.bzk.brp.beheer.model.Partij", "nl.bzk.brp.beheer.model.Ongeldig" };

        stringToEntityConverter.setEntitiesToConvert(entitiesToConvert);

        stringToEntityConverter.getConvertibleTypes();
    }

    @Test
    public void testConvertSourceTypeString() {
        String[] entitiesToConvert = { "nl.bzk.brp.beheer.model.Rol" };
        stringToEntityConverter.setEntitiesToConvert(entitiesToConvert);

        Rol rol = (Rol) stringToEntityConverter.convert("1", TypeDescriptor.valueOf(String.class) , TypeDescriptor.valueOf(Rol.class));

        Mockito.verify(stamgegevensService, Mockito.times(1)).find(Rol.class, 1);

        Assert.assertEquals("Rol1", rol.getNaam());
    }

    /**
     * Test ongeldige id.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConvertSourceTypeStringKanEntityNietVinden() {
        String[] entitiesToConvert = { "nl.bzk.brp.beheer.model.Rol" };
        stringToEntityConverter.setEntitiesToConvert(entitiesToConvert);

        stringToEntityConverter.convert("2", TypeDescriptor.valueOf(String.class) , TypeDescriptor.valueOf(Rol.class));
    }


    /**
     * Test het converteren van object naar id.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testConvertTargetTypeString() {
        String[] entitiesToConvert = { "nl.bzk.brp.beheer.model.Rol" };
        stringToEntityConverter.setEntitiesToConvert(entitiesToConvert);

        Integer id = (Integer) stringToEntityConverter.convert(new Rol(1, "Rol1"), TypeDescriptor.valueOf(Rol.class), TypeDescriptor.valueOf(String.class));

        Mockito.verify(stamgegevensService, Mockito.times(0)).find((Class<Rol>) Mockito.any(), Mockito.anyInt());

        Assert.assertEquals(1, id.intValue());

        String empty = (String) stringToEntityConverter.convert(null, TypeDescriptor.valueOf(Rol.class), TypeDescriptor.valueOf(String.class));
        Assert.assertEquals("", empty);
    }

    /**
     * Test geen getter in de source object.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConvertSourceTypeStringGeenGetter() {
        String[] entitiesToConvert = { "nl.bzk.brp.beheer.model.Functie" };
        stringToEntityConverter.setEntitiesToConvert(entitiesToConvert);

        stringToEntityConverter.convert(new Rol(1, "Rol1"), TypeDescriptor.valueOf(Functie.class), TypeDescriptor.valueOf(String.class));
    }


}
