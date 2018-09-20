/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.converter;

import java.util.Set;

import nl.bzk.brp.beheer.web.service.stamgegevens.StamgegevensService;
import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.autaut.Functie;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.Rol;
import nl.bzk.brp.domein.kern.Sector;
import nl.bzk.brp.domein.kern.persistent.PersistentSector;
import org.hibernate.ObjectNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter.ConvertiblePair;


@RunWith(MockitoJUnitRunner.class)
public class StringToEntityConverterTest {

    @Mock
    private StamgegevensService       stamgegevensService;

    private final DomeinObjectFactory factory = new PersistentDomeinObjectFactory();

    private StringToEntityConverter   stringToEntityConverter;

    @Before
    public void setUp() {
        stringToEntityConverter = new StringToEntityConverter();
        stringToEntityConverter.setStamgegevensService(stamgegevensService);

        Sector sector1 = factory.createSector();
        sector1.setID(1L);
        sector1.setNaam("Sector1");

        Mockito.when(stamgegevensService.find(Sector.class, 1L)).thenReturn(sector1);
        Mockito.when(stamgegevensService.find(Sector.class, 2L)).thenThrow(
                new ObjectNotFoundException(new Integer(2), Rol.class.toString()));
    }

    /**
     * Test of de opgegegeven types gemapped worden.
     */
    @Test
    public void testGetConvertibleTypes() {
        String[] entitiesToConvert = { "nl.bzk.brp.domein.kern.Partij", "nl.bzk.brp.domein.kern.Sector" };

        stringToEntityConverter.setEntitiesToConvert(entitiesToConvert);

        Set<ConvertiblePair> pairs = stringToEntityConverter.getConvertibleTypes();

        Assert.assertEquals(4, pairs.size());

        int gemapped = 0;
        for (ConvertiblePair pair : pairs) {
            if (pair.getSourceType() == String.class) {
                if (pair.getTargetType() == Partij.class) {
                    gemapped++;
                } else if (pair.getTargetType() == Sector.class) {
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
        String[] entitiesToConvert = { "nl.bzk.brp.domein.kern.Partij", "nl.bzk.brp.beheer.model.Ongeldig" };

        stringToEntityConverter.setEntitiesToConvert(entitiesToConvert);

        stringToEntityConverter.getConvertibleTypes();
    }

    @Test
    public void testConvertSourceTypeString() {
        String[] entitiesToConvert = { "nl.bzk.brp.domein.kern.persistent.PersistentSector" };
        stringToEntityConverter.setEntitiesToConvert(entitiesToConvert);

        PersistentSector sector =
            (PersistentSector) stringToEntityConverter.convert("1", TypeDescriptor.valueOf(String.class),
                    TypeDescriptor.valueOf(Sector.class));

        Mockito.verify(stamgegevensService, Mockito.times(1)).find(Sector.class, 1L);

        Assert.assertEquals("Sector1", sector.getNaam());
    }

    /**
     * Test ongeldige id.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConvertSourceTypeStringKanEntityNietVinden() {
        String[] entitiesToConvert = { "nl.bzk.brp.domein.kern.persistent.PersistentSector" };
        stringToEntityConverter.setEntitiesToConvert(entitiesToConvert);

        stringToEntityConverter
                .convert("2", TypeDescriptor.valueOf(String.class), TypeDescriptor.valueOf(Sector.class));
    }

    /**
     * Test het converteren van object naar id.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testConvertTargetTypeString() {
        String[] entitiesToConvert = { "nl.bzk.brp.beheer.model.Rol" };
        stringToEntityConverter.setEntitiesToConvert(entitiesToConvert);
        Sector sector1 = factory.createSector();
        sector1.setID(1L);
        sector1.setNaam("Sector1");

        Long id =
            (Long) stringToEntityConverter.convert(sector1, TypeDescriptor.valueOf(Sector.class),
                    TypeDescriptor.valueOf(String.class));

        Mockito.verify(stamgegevensService, Mockito.times(0)).find((Class<Sector>) Matchers.any(), Matchers.anyLong());

        Assert.assertEquals(1, id.intValue());

        String empty =
            (String) stringToEntityConverter.convert(null, TypeDescriptor.valueOf(Sector.class),
                    TypeDescriptor.valueOf(String.class));
        Assert.assertEquals("", empty);
    }

    /**
     * Test geen getter in de source object.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConvertSourceTypeStringGeenGetter() {
        String[] entitiesToConvert = { "nl.bzk.brp.beheer.model.Functie" };
        stringToEntityConverter.setEntitiesToConvert(entitiesToConvert);

        Sector sector1 = factory.createSector();
        sector1.setID(1L);
        sector1.setNaam("Sector1");

        stringToEntityConverter.convert(sector1, TypeDescriptor.valueOf(Functie.class),
                TypeDescriptor.valueOf(String.class));
    }

}
