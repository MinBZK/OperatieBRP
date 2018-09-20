/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service.impl;

import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;

import nl.bzk.brp.bevraging.domein.repository.PartijRepository;
import nl.bzk.brp.bijhouding.business.bijhoudingsautorisatie.BijhoudingsAutorisatieCheck;
import nl.bzk.brp.bijhouding.business.bijhoudingsautorisatie.BijhoudingsAutorisatieResultaat;
import nl.bzk.brp.bijhouding.business.bijhoudingsautorisatie.BijhoudingsAutorisatieSituatieCheck;
import nl.bzk.brp.bijhouding.business.service.BijhoudingsAutorisatieService;
import nl.bzk.brp.bijhouding.domein.repository.BijhoudingsAutorisatieRepository;
import nl.bzk.brp.domein.autaut.Bijhoudingsautorisatie;
import nl.bzk.brp.domein.autaut.SoortBijhouding;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.SoortActie;
import nl.bzk.brp.domein.kern.SoortDocument;
import nl.bzk.brp.domein.kern.SoortPartij;
import nl.bzk.brp.util.DatumUtil;

import org.springframework.stereotype.Service;


/**
 * Implementatie van de {@link BijhoudingsAutorisatieService} voor het autoriseren van bijhoudingen.
 */
@Service
public class BijhoudingsAutorisatieServiceImpl implements BijhoudingsAutorisatieService {

    @Inject
    private BijhoudingsAutorisatieRepository bijhoudingsAutorisatieRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public BijhoudingsAutorisatieResultaat bepaalBijhoudingsAutorisatie(final Partij bijhoudendePartij,
            final Partij bijhoudingsVerantwoordelijkeVooraf, final Partij bijhoudingsVerantwoordelijkeAchteraf,
            final SoortActie soortActie, final SoortDocument soortDocument)
    {

        BijhoudingsAutorisatieResultaat autorisatieResultaat;

        // Bepaal partij die de populatie eigenaar is binnen de context van de bijhouding.
        final Partij populatieEigenaarVoorBijhouding =
            bepaalPopulatieEigenaarVoorBijhouding(bijhoudingsVerantwoordelijkeVooraf,
                    bijhoudingsVerantwoordelijkeAchteraf);

        Predicate<Bijhoudingsautorisatie> autorisatieCheck =
            Predicates.and(new BijhoudingsAutorisatieCheck(populatieEigenaarVoorBijhouding, bijhoudendePartij),
                    new BijhoudingsAutorisatieSituatieCheck(soortActie, soortDocument));

        // Bepaal of er een bijhoudingsAutorisatie van toepassing is van het soort "Bijhouden"
        autorisatieResultaat = bepaalAutorisatieVoorBijhouding(bijhoudendePartij, autorisatieCheck);

        if (autorisatieResultaat == null) {
            // Bepaal of er een bijhoudingsAutorisatie van toepassing is van het soort "Automatisch fiatteren"
            autorisatieResultaat = bepaalAutorisatieVoorAutomatischFiattering(bijhoudendePartij, autorisatieCheck);
        }

        if (autorisatieResultaat == null) {
            // Bepaal of er een bijhoudingsvoorstel gedaan mag worden.
            autorisatieResultaat = bepaalAutorisatieVoorBijhoudingsVoorstellen(bijhoudendePartij, autorisatieCheck);
        }

        if (autorisatieResultaat == null) {
            return BijhoudingsAutorisatieResultaat.BIJHOUDING_NIET_TOEGESTAAN;
        } else {
            return autorisatieResultaat;
        }

    }

    /**
     * Bepaal of er een autorisatie bestaat van het soort "Bijhouden", die is afgegeven voor de bijhoudendePartij.
     *
     * @param bijhoudendePartij De partij die de bijhouding doet.
     * @param autorisatieCheck Check die controleert of een autorisatie voldoet. Deze check wordt gebruikt om
     *            autorisaties die niet voldoen eruit te filteren.
     * @return Autorisatie resultaat waarin staat of de bijhouding geautoriseerd is of niet, en op basis waarvan.
     */
    private BijhoudingsAutorisatieResultaat bepaalAutorisatieVoorBijhouding(final Partij bijhoudendePartij,
            final Predicate<Bijhoudingsautorisatie> autorisatieCheck)
    {
        BijhoudingsAutorisatieResultaat bijhoudingsAutorisatieResultaat = null;

        // Zoek van toepassing zijnde bijhouding autorisaties voor de partij die de bijhouding doet.
        final List<Bijhoudingsautorisatie> autorisaties =
            bijhoudingsAutorisatieRepository.zoekBijhoudingsAutorisaties(SoortBijhouding.BIJHOUDEN, bijhoudendePartij,
                    bijhoudendePartij.getSoort(), DatumUtil.getDatumVandaagInteger());

        if (!autorisaties.isEmpty()) {
            // Controleer en filter de autorisaties die niet voldoen eruit.
            Iterable<Bijhoudingsautorisatie> geldigeAutorisaties = Iterables.filter(autorisaties, autorisatieCheck);

            if (geldigeAutorisaties.iterator().hasNext()) {
                if (geldigeAutorisaties.iterator().next().getBijhoudingsautorisatiebesluit().getAutoriseerder().getID()
                        .equals(PartijRepository.ID_PARTIJ_REGERING_EN_STATEN_GENERAAL))
                {
                    bijhoudingsAutorisatieResultaat =
                        BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_WET_BRP;
                } else {
                    bijhoudingsAutorisatieResultaat =
                        BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_VERLEENDE_AUTORISATIE;
                }
            }
        }
        return bijhoudingsAutorisatieResultaat;
    }

    /**
     * Bepaal of er een autorisatie bestaat van het soort "Automatisch fiatteren", die is afgegeven
     * voor de bijhoudendePartij.
     *
     * @param bijhoudendePartij De partij die de bijhouding doet.
     * @param autorisatieCheck Check die controleert of een autorisatie voldoet. Deze check wordt gebruikt om
     *            autorisaties die niet voldoen eruit te filteren.
     * @return Autorisatie resultaat waarin staat of automtisch fiatteren geautoriseerd is of niet, en op basis waarvan.
     */
    private BijhoudingsAutorisatieResultaat bepaalAutorisatieVoorAutomatischFiattering(final Partij bijhoudendePartij,
            final Predicate<Bijhoudingsautorisatie> autorisatieCheck)
    {

        // Zoek van toepassing zijnde autorisaties van het soort Automatisch fiatteren voor de partij die de
        // bijhouding doet.
        final List<Bijhoudingsautorisatie> autorisaties =
            bijhoudingsAutorisatieRepository.zoekBijhoudingsAutorisaties(SoortBijhouding.AUTOMATISCH_FIATTEREN,
                    bijhoudendePartij, bijhoudendePartij.getSoort(), DatumUtil.getDatumVandaagInteger());

        if (!autorisaties.isEmpty()) {
            // Controleer en filter de autorisaties die niet voldoen eruit.
            Iterable<Bijhoudingsautorisatie> geldigeAutorisaties = Iterables.filter(autorisaties, autorisatieCheck);

            if (geldigeAutorisaties.iterator().hasNext()) {
                return BijhoudingsAutorisatieResultaat.BIJHOUDING_TOEGESTAAN_OP_BASIS_AUT_FIATTERING_MANDAAT;
            }
        }
        return null;
    }

    /**
     * Bepaal of er een autorisatie bestaat van het soort "Voorstellen doen", die is afgegeven
     * voor de bijhoudendePartij.
     *
     * @param bijhoudendePartij De partij die de bijhouding doet.
     * @param autorisatieCheck Check die controleert of een autorisatie voldoet. Deze check wordt gebruikt om
     *            autorisaties die niet voldoen eruit te filteren.
     * @return Autorisatie resultaat waarin staat of het doen van voorstellen geautoriseerd is of niet.
     */
    private BijhoudingsAutorisatieResultaat bepaalAutorisatieVoorBijhoudingsVoorstellen(final Partij bijhoudendePartij,
            final Predicate<Bijhoudingsautorisatie> autorisatieCheck)
    {
        // Zoek van toepassing zijnde autorisaties van het soort Automatisch fiatteren voor de partij die de
        // bijhouding doet.
        final List<Bijhoudingsautorisatie> autorisaties =
            bijhoudingsAutorisatieRepository.zoekBijhoudingsAutorisaties(SoortBijhouding.BIJHOUDINGSVOORSTEL_DOEN,
                    bijhoudendePartij, bijhoudendePartij.getSoort(), DatumUtil.getDatumVandaagInteger());

        if (!autorisaties.isEmpty()) {
            // Controleer en filter de autorisaties die niet voldoen eruit.
            Iterable<Bijhoudingsautorisatie> geldigeAutorisaties = Iterables.filter(autorisaties, autorisatieCheck);

            if (geldigeAutorisaties.iterator().hasNext()) {
                return BijhoudingsAutorisatieResultaat.BIJHOUDING_VOORSTEL_TOEGESTAAN;
            }
        }
        return null;
    }

    /**
     * Bepaal de populatie eigenaar binnen de context van de uit te voeren bijhouding. Dit is de partij die het
     * voor het zeggen heeft wat de autorisatie betreft.
     *
     * @param bijhoudingsVerantwoordelijkeVooraf Partij die bijhoudings verantwoordelijke is van de persoon die
     *            bijgehouden wordt, voordat de bijhouding is verwerkt.
     * @param bijhoudingsVerantwoordelijkeAchteraf Partij die bijhoudingsverantwoordelijke is van de persoon die
     *            bijgehouden wordt, nadat de bijhouding is verwerkt.
     * @return Partij die de populatie eigenaar is binnen deze bijhouding.
     */
    private Partij bepaalPopulatieEigenaarVoorBijhouding(final Partij bijhoudingsVerantwoordelijkeVooraf,
            final Partij bijhoudingsVerantwoordelijkeAchteraf)
    {
        Partij populatieEigenaarVoorBijhouding;
        if (SoortPartij.GEMEENTE == bijhoudingsVerantwoordelijkeAchteraf.getSoort()) {
            populatieEigenaarVoorBijhouding = bijhoudingsVerantwoordelijkeAchteraf;
        } else {
            if (SoortPartij.GEMEENTE == bijhoudingsVerantwoordelijkeVooraf.getSoort()) {
                populatieEigenaarVoorBijhouding = bijhoudingsVerantwoordelijkeVooraf;
            } else {
                // De minister dus.
                populatieEigenaarVoorBijhouding = bijhoudingsVerantwoordelijkeAchteraf;
            }
        }
        return populatieEigenaarVoorBijhouding;
    }
}
