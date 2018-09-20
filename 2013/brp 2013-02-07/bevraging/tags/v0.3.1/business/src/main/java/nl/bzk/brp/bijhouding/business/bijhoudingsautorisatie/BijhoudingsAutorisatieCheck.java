/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.bijhoudingsautorisatie;

import com.google.common.base.Predicate;

import nl.bzk.brp.bevraging.domein.repository.PartijRepository;
import nl.bzk.brp.domein.autaut.BeperkingPopulatie;
import nl.bzk.brp.domein.autaut.Bijhoudingsautorisatie;
import nl.bzk.brp.domein.autaut.Uitgeslotene;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.SoortPartij;
import nl.bzk.brp.domein.kern.Verantwoordelijke;


/**
 * Een check die bepaalt of een partij die een bijhouding wil doen geautoriseerd is volgens een
 * bijhoudingsautorisatie.
 * Een aantal velden in {@link Bijhoudingsautorisatie} bepalen of een bijhoudende partij geautoriseerd is of niet.
 * Dit zijn {@link Bijhoudingsautorisatie#verantwoordelijke} en {@link Bijhoudingsautorisatie#beperkingPopulatie}.
 * Daarnaast kan een partij nog uitgesloten zijn van een Bijhoudingsautorisatie.
 * De {@link #apply(Bijhoudingsautorisatie)} functie bepaalt aan de hand van een Bijhoudingsautorisatie of de
 * bijhoudende partij geautoriseerd is of niet.
 */
public final class BijhoudingsAutorisatieCheck implements Predicate<Bijhoudingsautorisatie> {

    private final Partij populatieEigenaarVoorBijhouding;
    private final Partij bijhoudendePartij;

    /**
     * Constructor.
     *
     * @param populatieEigenaarVoorBijhouding Populatie eigenaar binnen de context van de bijhouding.
     * @param bijhoudendePartij De partij die de bijhouding doet.
     */
    public BijhoudingsAutorisatieCheck(final Partij populatieEigenaarVoorBijhouding, final Partij bijhoudendePartij) {
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
    public boolean apply(final Bijhoudingsautorisatie bijhoudingsAutorisatie) {
        // Controleer autorisatie beperking op verantwoordelijke.
        boolean verantwoordelijkeKlopt = true;
        if (bijhoudingsAutorisatie.getVerantwoordelijke() != null) {
            if (Verantwoordelijke.COLLEGE_VAN_BURGEMEESTER_EN_WETHOUDERS == bijhoudingsAutorisatie
                    .getVerantwoordelijke())
            {
                verantwoordelijkeKlopt = SoortPartij.GEMEENTE == populatieEigenaarVoorBijhouding.getSoort();
            } else if (Verantwoordelijke.MINISTER == bijhoudingsAutorisatie.getVerantwoordelijke()) {
                verantwoordelijkeKlopt =
                    PartijRepository.ID_PARTIJ_MINISTER.equals(populatieEigenaarVoorBijhouding.getID());
            } else {
                verantwoordelijkeKlopt = false;
            }
        }

        // Controleer autorisatie beperking op populatie.
        boolean beperkingPopulatieKlopt = true;
        if (bijhoudingsAutorisatie.getBeperkingPopulatie() != null) {
            if (BeperkingPopulatie.ONTVANGER == bijhoudingsAutorisatie.getBeperkingPopulatie()) {
                beperkingPopulatieKlopt = bijhoudendePartij.getID().equals(populatieEigenaarVoorBijhouding.getID());
            } else {
                beperkingPopulatieKlopt = false;
            }
        }

        // Controleer of de bijhoudende partij uitgesloten is van de autorisatie.
        boolean bijhoudendePartijIsUitgesloten = false;
        if (bijhoudingsAutorisatie.getUitgesloteneen() != null && !bijhoudingsAutorisatie.getUitgesloteneen().isEmpty())
        {
            for (Uitgeslotene uitgeslotene : bijhoudingsAutorisatie.getUitgesloteneen()) {
                if (uitgeslotene.getUitgeslotenPartij().getID().equals(bijhoudendePartij.getID())) {
                    bijhoudendePartijIsUitgesloten = true;
                }
            }
        }
        return verantwoordelijkeKlopt && beperkingPopulatieKlopt && !bijhoudendePartijIsUitgesloten;
    }
}
