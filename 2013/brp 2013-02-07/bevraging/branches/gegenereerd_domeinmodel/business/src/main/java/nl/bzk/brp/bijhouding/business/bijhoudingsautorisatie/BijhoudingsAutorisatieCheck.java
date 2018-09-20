/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.bijhoudingsautorisatie;

import com.google.common.base.Predicate;

import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.SoortPartij;
import nl.bzk.brp.bevraging.domein.Verantwoordelijke;
import nl.bzk.brp.bevraging.domein.repository.PartijRepository;
import nl.bzk.brp.bijhouding.domein.aut.BeperkingPopulatie;
import nl.bzk.brp.bijhouding.domein.aut.BijhoudingsAutorisatie;
import nl.bzk.brp.bijhouding.domein.aut.Uitgeslotene;

/**
 * Een check die bepaalt of een partij die een bijhouding wil doen geautoriseerd is volgens een
 * bijhoudingsautorisatie.
 * Een aantal velden in {@link BijhoudingsAutorisatie} bepalen of een bijhoudende partij geautoriseerd is of niet.
 * Dit zijn {@link BijhoudingsAutorisatie#verantwoordelijke} en {@link BijhoudingsAutorisatie#beperkingPopulatie}.
 * Daarnaast kan een partij nog uitgesloten zijn van een BijhoudingsAutorisatie.
 * De {@link #apply(BijhoudingsAutorisatie)} functie bepaalt aan de hand van een BijhoudingsAutorisatie of de
 * bijhoudende partij geautoriseerd is of niet.
 */
public final class BijhoudingsAutorisatieCheck implements Predicate<BijhoudingsAutorisatie> {

    private final Partij populatieEigenaarVoorBijhouding;
    private final Partij bijhoudendePartij;

    /**
     * Constructor.
     *
     * @param populatieEigenaarVoorBijhouding Populatie eigenaar binnen de context van de bijhouding.
     * @param bijhoudendePartij De partij die de bijhouding doet.
     */
    public BijhoudingsAutorisatieCheck(final Partij populatieEigenaarVoorBijhouding,
                                       final Partij bijhoudendePartij)
    {
        this.populatieEigenaarVoorBijhouding = populatieEigenaarVoorBijhouding;
        this.bijhoudendePartij = bijhoudendePartij;
    }

    /**
     * Bepaal of {@link #bijhoudendePartij} op basis van bijhoudingsAutorisatie geautoriseerd is om bij te houden
     * of niet.
     *
     * @param bijhoudingsAutorisatie De bijhoudingsautorisatie.
     * @return true indien {@link #bijhoudendePartij} geautoriseerd is, anders false.
     */
    @Override
    public boolean apply(final BijhoudingsAutorisatie bijhoudingsAutorisatie) {
        // Controleer autorisatie beperking op verantwoordelijke.
        boolean verantwoordelijkeKlopt = true;
        if (bijhoudingsAutorisatie.getVerantwoordelijke() != null) {
            if (Verantwoordelijke.COLLEGE == bijhoudingsAutorisatie.getVerantwoordelijke()) {
                verantwoordelijkeKlopt = SoortPartij.GEMEENTE == populatieEigenaarVoorBijhouding.getSoort();
            } else if (Verantwoordelijke.MINISTER == bijhoudingsAutorisatie.getVerantwoordelijke()) {
                verantwoordelijkeKlopt =
                    PartijRepository.ID_PARTIJ_MINISTER.equals(populatieEigenaarVoorBijhouding.getId());
            } else {
                verantwoordelijkeKlopt = false;
            }
        }

        // Controleer autorisatie beperking op populatie.
        boolean beperkingPopulatieKlopt = true;
        if (bijhoudingsAutorisatie.getBeperkingPopulatie() != null) {
            if (BeperkingPopulatie.ONTVANGER == bijhoudingsAutorisatie.getBeperkingPopulatie()) {
                beperkingPopulatieKlopt = bijhoudendePartij.getId().equals(populatieEigenaarVoorBijhouding.getId());
            } else {
                beperkingPopulatieKlopt = false;
            }
        }

        // Controleer of de bijhoudende partij uitgesloten is van de autorisatie.
        boolean bijhoudendePartijIsUitgesloten = false;
        if (bijhoudingsAutorisatie.getUitgeslotenen() != null && !bijhoudingsAutorisatie.getUitgeslotenen().isEmpty()) {
            for (Uitgeslotene uitgeslotene : bijhoudingsAutorisatie.getUitgeslotenen()) {
                if (uitgeslotene.getUitgeslotenPartij().getId().equals(bijhoudendePartij.getId())) {
                    bijhoudendePartijIsUitgesloten = true;
                }
            }
        }
        return verantwoordelijkeKlopt && beperkingPopulatieKlopt && !bijhoudendePartijIsUitgesloten;
    }
}
