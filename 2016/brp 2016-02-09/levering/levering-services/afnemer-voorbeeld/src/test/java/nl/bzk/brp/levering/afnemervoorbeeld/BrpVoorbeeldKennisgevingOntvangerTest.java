/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemervoorbeeld;

import javax.xml.bind.JAXBElement;
import nl.bzk.brp.brp0200.GroepBerichtStuurgegevens;
import nl.bzk.brp.brp0200.ObjectFactory;
import nl.bzk.brp.brp0200.PartijCode;
import nl.bzk.brp.brp0200.Referentienummer;
import nl.bzk.brp.brp0200.SynchronisatieVerwerkPersoon;
import nl.bzk.brp.brp0200.SysteemNaam;
import nl.bzk.brp.levering.berichtverwerking.service.LvgSynchronisatieVerwerking;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BrpVoorbeeldKennisgevingOntvangerTest {

    @InjectMocks
    private final LvgSynchronisatieVerwerking brpVoorbeeldKennisgevingOntvanger = new BrpVoorbeeldKennisgevingOntvanger();

    @Mock
    private KennisgevingVerwerker kennisgevingVerwerker;

    private final SynchronisatieVerwerkPersoon synVerwerkMutatiePersoon = new SynchronisatieVerwerkPersoon();

    private final ObjectFactory objectFactory = new ObjectFactory();

    @Before
    public void setUp() throws Exception {
        final GroepBerichtStuurgegevens groepBerichtStuurgegevens = new GroepBerichtStuurgegevens();
        final PartijCode ontvangendePartij = new PartijCode();
        ontvangendePartij.setValue("12345");
        groepBerichtStuurgegevens.setOntvangendePartij(objectFactory.createObjecttypePartijCode(ontvangendePartij));

        final PartijCode zendendePartij = new PartijCode();
        zendendePartij.setValue("67890");
        groepBerichtStuurgegevens.setZendendePartij(objectFactory.createObjecttypePartijCode(zendendePartij));

        final SysteemNaam zendendeSysteem = new SysteemNaam();
        zendendeSysteem.setValue("TESTSYSTEEM");
        groepBerichtStuurgegevens.setZendendeSysteem(objectFactory.createGroepBerichtStuurgegevensZendendeSysteem(zendendeSysteem));

        final Referentienummer referentieNummer = new Referentienummer();
        referentieNummer.setValue("d09b63f0-1a78-4ff9-8ad1-2b57529052cd");
        groepBerichtStuurgegevens.setReferentienummer(objectFactory.createGroepBerichtStuurgegevensReferentienummer(referentieNummer));

        final JAXBElement<GroepBerichtStuurgegevens> stuurgegevens = objectFactory.createObjecttypeBerichtStuurgegevens(groepBerichtStuurgegevens);
        synVerwerkMutatiePersoon.setStuurgegevens(stuurgegevens);
    }

    @Test
    public void testVerwerkPersoon() {
        brpVoorbeeldKennisgevingOntvanger.verwerkPersoon(synVerwerkMutatiePersoon);
    }

    @Test
    public void testVerwerkPersoonNullObject() {
        brpVoorbeeldKennisgevingOntvanger.verwerkPersoon(null);
    }

    @Test
    public void testVerwerkPersoonException() {
        Mockito.doThrow(Exception.class).when(kennisgevingVerwerker).verwerkKennisgeving(synVerwerkMutatiePersoon);
        brpVoorbeeldKennisgevingOntvanger.verwerkPersoon(synVerwerkMutatiePersoon);
    }

    @Test
    public void testVerwerkPersoonZonderZendendeEnOntvangendePartij() {
        final GroepBerichtStuurgegevens groepBerichtStuurgegevens = synVerwerkMutatiePersoon.getStuurgegevens().getValue();
        groepBerichtStuurgegevens.setOntvangendePartij(objectFactory.createObjecttypePartijCode(null));
        groepBerichtStuurgegevens.setZendendePartij(objectFactory.createObjecttypePartijCode(null));
        synVerwerkMutatiePersoon.getStuurgegevens().setValue(groepBerichtStuurgegevens);
        brpVoorbeeldKennisgevingOntvanger.verwerkPersoon(synVerwerkMutatiePersoon);
    }
}
