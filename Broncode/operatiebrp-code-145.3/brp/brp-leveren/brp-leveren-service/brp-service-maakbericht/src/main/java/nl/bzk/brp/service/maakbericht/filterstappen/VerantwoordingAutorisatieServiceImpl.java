/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.springframework.stereotype.Component;

/**
 * Autoriseer de verantwoordingen. <br> In een bericht mogen geen acties getoond worden die niet verantwoord worden. Dit betekent dat van de acties in de
 * inhoudelijke groepen geen acties mogen blijven staan die niet verantwoord worden door het handelingen gedeelte en omgekeerd mogen geen acties of bronnen
 * blijven staan in verantwoording die niet getoond worden in inhoudelijke groepen. Voor mutatielevering wordt alleen de handeling onderhanden meegenomen
 * in vergelijking. <br> <ul> <li>Alleen Acties die verantwoording vormen voor inhoudelijke-of onderzoeksgroepen meeleveren <li>Alleen bronnen waarnaar
 * daadwerkelijk wordt verwezen meeleveren <li>Alleen de Administratieve handelingen waarnaar daadwerkelijk wordt verwezen als verantwoording leveren.
 * <li>Alleen bronnen waarnaar daadwerkelijk wordt verwezen meeleveren </ul> <br> Het bericht is consistent: Alle verwijzingen (van inhoudelijke groepen
 * naar acties en van onderzoek naar hetgeen in onderzoek staat) hebben betrekking op iets dat in het bericht terug te vinden is. Omgekeerd geldt dat
 * verantwoordingsgegevens alleen maar in het bericht aanwezig zijn als er naar verwezen wordt vanuit het inhoudelijke deel van het bericht.
 */
@Component
@Bedrijfsregel(Regel.R1318)
@Bedrijfsregel(Regel.R1551)
@Bedrijfsregel(Regel.R1552)
@Bedrijfsregel(Regel.R2015)
@Bedrijfsregel(Regel.R2051)
final class VerantwoordingAutorisatieServiceImpl implements MaakBerichtStap {

    @Override
    public void execute(final Berichtgegevens berichtgegevens) {
        // geautoriseerde acties weten we
        final Set<Actie> geautoriseerdeActies = bepaalActiesVanGeautoriseerdeAttributen(berichtgegevens);
        final Set<Actie> geautoriseerdeActiesMetHandelingen = Sets.newHashSet();
        // acties uit de administratieve handelingen
        final Set<AdministratieveHandeling> geautoriseerdeHandelingen = Sets.newHashSet();
        for (final AdministratieveHandeling handeling : berichtgegevens.getPersoonslijst().getAdministratieveHandelingen()) {
            if (berichtgegevens.isMutatiebericht() && !handeling.getId().equals(berichtgegevens.getParameters().getAdministratieveHandeling())) {
                // voor mutatielevering alleen de huidige handeling in acht nemen
                continue;

            }
            final Collection<Actie> acties = handeling.getActies();
            final Sets.SetView<Actie> gedeeldeActies = Sets.intersection(geautoriseerdeActies, Sets.newHashSet(acties));
            if (!gedeeldeActies.isEmpty()) {
                geautoriseerdeHandelingen.add(handeling);
                geautoriseerdeActiesMetHandelingen.addAll(gedeeldeActies);
            }
        }
        // update LeverPersoon met geautoriseerde acties die ook handelingen hebben
        berichtgegevens.setGeautoriseerdeActies(geautoriseerdeActiesMetHandelingen);
        // update LeverPersoon met geautoriseerde handelingen
        berichtgegevens.setGeautoriseerdeHandelingen(geautoriseerdeHandelingen);

        // verantwoordingattributen alleen leveren als de Actie geautoriseerd is
        for (final MetaAttribuut attribuut : berichtgegevens.getGeautoriseerdeAttributen()) {
            if (attribuut.getAttribuutElement().isVerantwoording() && !geautoriseerdeActiesMetHandelingen.contains(attribuut.<Actie>getWaarde())) {
                berichtgegevens.verwijderAutorisatie(attribuut);
            }
        }
    }

    private Set<Actie> bepaalActiesVanGeautoriseerdeAttributen(final Berichtgegevens berichtgegevens) {
        final Set<Actie> actieSet = Sets.newHashSet();
        for (final MetaAttribuut attribuut : berichtgegevens.getGeautoriseerdeAttributen()) {
            if (attribuut.getAttribuutElement().isActieInhoud()
                    || attribuut.getAttribuutElement().isActieVerval()
                    || attribuut.getAttribuutElement().isActieAanpassingGeldigheid()) {
                actieSet.add(attribuut.getWaarde());
            }
        }
        return actieSet;
    }
}
