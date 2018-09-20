/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.toegangsbewaking;

import nl.bzk.brp.bevraging.business.dto.antwoord.PersoonZoekCriteriaAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.bevraging.business.dto.verzoek.PersoonZoekCriteria;
import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.SoortPartij;
import nl.bzk.brp.bevraging.domein.aut.AutorisatieBesluit;
import nl.bzk.brp.bevraging.domein.aut.SoortAutorisatieBesluit;
import nl.bzk.brp.bevraging.domein.ber.SoortBericht;
import nl.bzk.brp.bevraging.domein.lev.Abonnement;
import nl.bzk.brp.bevraging.domein.lev.SoortAbonnement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class StandaardToegangsBewakingServiceFunctioneleAutorisatieTest {

    private ToegangsBewakingService service;

    @Test
    public void testCorrectFunctioneelGeautoriseerd() {
        Abonnement abonnement = getAbonnementOpSoortBerichten(SoortBericht.OPVRAGEN_PERSOON_VRAAG);

        Assert.assertTrue(service.isFunctioneelGeautoriseerd(abonnement, getOpvragenPersoonVerzoek()));
    }

    @Test
    public void testNietFunctioneelGeautoriseerd() {
        Abonnement abonnement = getAbonnementOpSoortBerichten(SoortBericht.DUMMY);

        Assert.assertFalse(service.isFunctioneelGeautoriseerd(abonnement, getOpvragenPersoonVerzoek()));
    }

    @Test
    public void testNietFunctioneelGeautoriseerdIndienGeenAbonnementSoorten() {
        Abonnement abonnement = getAbonnementOpSoortBerichten();

        Assert.assertFalse(service.isFunctioneelGeautoriseerd(abonnement, getOpvragenPersoonVerzoek()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalideCallMissendAbonnement() {
        service.isFunctioneelGeautoriseerd(null, getOpvragenPersoonVerzoek());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalideCallMissendCommand() {
        Abonnement abonnement = getAbonnementOpSoortBerichten(SoortBericht.OPVRAGEN_PERSOON_VRAAG);
        service.isFunctioneelGeautoriseerd(abonnement, null);
    }

    @Before
    public void init() {
        service = new StandaardToegangsBewakingService();
    }

    /**
     * Retourneert een abonnement, inclusief doelbinding, waarbij voor het abonnement de opgegeven {@link SoortBericht}
     * als de toegestane berichtsoorten worden gedefinieerd.
     *
     * @param soortBerichten de berichtsoorten die via het abonnement als toegestaan moeten worden gedefinieerd.
     * @return een abonnement.
     */
    private Abonnement getAbonnementOpSoortBerichten(final SoortBericht... soortBerichten) {
        AutorisatieBesluit besluit =
            new AutorisatieBesluit(SoortAutorisatieBesluit.LEVERINGSAUTORISATIE, "Het mag niet", new Partij(
                    SoortPartij.VERTEGENWOORDIGER_REGERING));
        Partij partij = new Partij(SoortPartij.GEMEENTE);
        Abonnement abonnement = partij.createDoelbinding(besluit).createAbonnement(SoortAbonnement.LEVERING);
        for (SoortBericht soort : soortBerichten) {
            abonnement.voegSoortBerichtToe(soort);
        }
        return abonnement;
    }

    /**
     * Retourneert een standaard (leeg) {@link BerichtVerzoek}.
     *
     * @return een leeg {@link BerichtVerzoek}.
     */
    private BerichtVerzoek<PersoonZoekCriteriaAntwoord> getOpvragenPersoonVerzoek() {
        return new PersoonZoekCriteria();
    }

}
