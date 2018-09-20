/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service.impl;

import nl.bzk.brp.bevraging.domein.repository.PartijRepository;
import nl.bzk.brp.bijhouding.business.bijhoudingsautorisatie.BijhoudingsAutorisatieCheck;
import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.autaut.BeperkingPopulatie;
import nl.bzk.brp.domein.autaut.Bijhoudingsautorisatie;
import nl.bzk.brp.domein.autaut.Uitgeslotene;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.SoortPartij;
import nl.bzk.brp.domein.kern.Verantwoordelijke;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BijhoudingsAutorisatieCheckTest {

    private final DomeinObjectFactory domeinObjectFactory = new PersistentDomeinObjectFactory();

    private Partij              partijGemeenteA;
    private Partij              partijGemeenteB;
    private Partij              partijMinister;
    private Partij              partijWetgever;

    @Before
    public void init() {
        partijWetgever = initPartij(SoortPartij.WETGEVER, PartijRepository.ID_PARTIJ_REGERING_EN_STATEN_GENERAAL);
        partijMinister = initPartij(SoortPartij.VERTEGENWOORDIGER_REGERING, PartijRepository.ID_PARTIJ_MINISTER);
        partijGemeenteA = initPartij(SoortPartij.GEMEENTE, 3);
        partijGemeenteB = initPartij(SoortPartij.GEMEENTE, 4);
    }

    @Test
    public void testGeenVerantwoordelijkeEnPopulatieBeperking() {
        BijhoudingsAutorisatieCheck check = new BijhoudingsAutorisatieCheck(partijGemeenteA, partijGemeenteB);

        Bijhoudingsautorisatie bijhoudingsAutorisatie = initBijhoudingsAutorisatie(null, null);

        Assert.assertTrue(check.apply(bijhoudingsAutorisatie));
    }

    @Test
    public void testVerantwoordelijkeBeperkingCollege() {
        // Populatie eigenaar voor bijhouding MOET een gemeente zijn.
        Bijhoudingsautorisatie bijhoudingsAutorisatie =
            initBijhoudingsAutorisatie(nl.bzk.brp.domein.kern.Verantwoordelijke.COLLEGE_VAN_BURGEMEESTER_EN_WETHOUDERS,
                    null);
        BijhoudingsAutorisatieCheck check = new BijhoudingsAutorisatieCheck(partijGemeenteA, partijGemeenteB);

        Assert.assertTrue(check.apply(bijhoudingsAutorisatie));

        check = new BijhoudingsAutorisatieCheck(partijMinister, partijGemeenteB);

        Assert.assertFalse(check.apply(bijhoudingsAutorisatie));

        check = new BijhoudingsAutorisatieCheck(partijWetgever, partijGemeenteB);

        Assert.assertFalse(check.apply(bijhoudingsAutorisatie));
    }

    @Test
    public void testVerantwoordelijkeBeperkingMinister() {
        // Populatie eigenaar voor bijhouding MOET de minister zijn.
        Bijhoudingsautorisatie bijhoudingsAutorisatie = initBijhoudingsAutorisatie(Verantwoordelijke.MINISTER, null);
        BijhoudingsAutorisatieCheck check = new BijhoudingsAutorisatieCheck(partijMinister, partijGemeenteB);

        Assert.assertTrue(check.apply(bijhoudingsAutorisatie));

        check = new BijhoudingsAutorisatieCheck(partijGemeenteA, partijGemeenteB);

        Assert.assertFalse(check.apply(bijhoudingsAutorisatie));

        check = new BijhoudingsAutorisatieCheck(partijWetgever, partijGemeenteB);

        Assert.assertFalse(check.apply(bijhoudingsAutorisatie));
    }

    @Test
    public void testPopulatieBeperkingOntvanger() {
        Bijhoudingsautorisatie bijhoudingsAutorisatie = initBijhoudingsAutorisatie(null, BeperkingPopulatie.ONTVANGER);
        BijhoudingsAutorisatieCheck check = new BijhoudingsAutorisatieCheck(partijGemeenteA, partijGemeenteA);

        Assert.assertTrue(check.apply(bijhoudingsAutorisatie));

        check = new BijhoudingsAutorisatieCheck(partijGemeenteA, partijGemeenteB);

        Assert.assertFalse(check.apply(bijhoudingsAutorisatie));

        check = new BijhoudingsAutorisatieCheck(partijGemeenteA, partijMinister);

        Assert.assertFalse(check.apply(bijhoudingsAutorisatie));
    }

    @Test
    public void testPopulatieBeperkingVerstrekker() {
        // Populatie beperking op Verstrekker zal altijd false moeten opleveren omdat deze waarde in de praktijk
        // niet gebruikt wordt.
        Bijhoudingsautorisatie bijhoudingsAutorisatie =
            initBijhoudingsAutorisatie(null, BeperkingPopulatie.VERSTREKKER);
        BijhoudingsAutorisatieCheck check = new BijhoudingsAutorisatieCheck(partijGemeenteA, partijGemeenteA);

        Assert.assertFalse(check.apply(bijhoudingsAutorisatie));

        check = new BijhoudingsAutorisatieCheck(partijGemeenteA, partijGemeenteB);

        Assert.assertFalse(check.apply(bijhoudingsAutorisatie));

        check = new BijhoudingsAutorisatieCheck(partijGemeenteA, partijMinister);

        Assert.assertFalse(check.apply(bijhoudingsAutorisatie));
    }

    @Test
    public void testUitgeslotene() {
        Bijhoudingsautorisatie bijhoudingsAutorisatie =
            initBijhoudingsAutorisatie(null, null, partijGemeenteA, partijGemeenteB);

        BijhoudingsAutorisatieCheck check = new BijhoudingsAutorisatieCheck(partijGemeenteB, partijGemeenteA);

        Assert.assertFalse(check.apply(bijhoudingsAutorisatie));

        check = new BijhoudingsAutorisatieCheck(partijGemeenteB, partijGemeenteB);

        Assert.assertFalse(check.apply(bijhoudingsAutorisatie));

        check = new BijhoudingsAutorisatieCheck(partijGemeenteB, partijMinister);

        Assert.assertTrue(check.apply(bijhoudingsAutorisatie));
    }

    private Bijhoudingsautorisatie initBijhoudingsAutorisatie(final Verantwoordelijke verantwoordelijke,
            final BeperkingPopulatie beperkingPopulatie, final Partij... uitgeslotenPartijen)
    {
        final Bijhoudingsautorisatie bijhoudingsAutorisatie = domeinObjectFactory.createBijhoudingsautorisatie();
        for (Partij partij : uitgeslotenPartijen) {
            Uitgeslotene uitgeslotene = domeinObjectFactory.createUitgeslotene();

            uitgeslotene.setBijhoudingsautorisatie(bijhoudingsAutorisatie);
            uitgeslotene.setUitgeslotenPartij(partij);
            bijhoudingsAutorisatie.addUitgeslotene(uitgeslotene);
        }
        bijhoudingsAutorisatie.setVerantwoordelijke(verantwoordelijke);
        bijhoudingsAutorisatie.setBeperkingPopulatie(beperkingPopulatie);
        return bijhoudingsAutorisatie;
    }

    private Partij initPartij(final SoortPartij soort, final Integer id) {
        final Partij partij = domeinObjectFactory.createPartij();
        partij.setSoort(soort);
        partij.setID(id);
        return partij;
    }
}
