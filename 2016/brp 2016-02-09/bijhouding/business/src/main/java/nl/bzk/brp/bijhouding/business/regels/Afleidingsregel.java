/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels;

import java.util.Set;

import nl.bzk.brp.business.regels.RegelInterface;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;


/**
 * Generieke interface voor afleidingsregels. Een afleidingsregel is een regel die informatie verwerkt
 * volgens een bepaalde business logica. Het gaat hier expliciet niet om directe data uit het bericht, maar
 * om data die daar uit afgeleid kan worden. Deze regels zijn in de specs opgenomen als VR.....a/b/c
 * en implementerende klasses dienen dan ook met één zo'n dergelijke spec overeen te komen.
 * Implementerende klasses wordt geadviseerd gebruik te maken van AbstractAfleidingsregel of een
 * van zijn subklasses.
 *
 * NB: Het uitgangspunt is dat elke afleidingsregel zelf al zijn precondities checkt! Je kan er als afleidingsregel
 * dus niet vanuit gaan dat je onder geldige condities wordt aangeroepen. Het advies is zelfs dat groepsverwerkers
 * gewoon altijd alle mogelijke afleidingsregels toevoegen, zonder precondities te checken. Zo blijft de logica
 * voor de preconditie op 1 plek: in de afleidingsregel zelf.
 */
public interface Afleidingsregel extends RegelInterface {

    /**
     * Functie die de afleiding doorvoert.
     *
     * @return een lijst met cascaded afleidingsregels
     */
    AfleidingResultaat leidAf();

    /**
     * Geef de lijst met extra bijgehouden personen terug. Dit kunnen personen zijn
     * die door de afleiding ook gaan gelden als bijgehouden personen.
     * Let op: roep deze methode aan na leidAf().
     *
     * @return de lijst met extra bijgehouden personen
     */
    Set<PersoonHisVolledigImpl> getExtraBijgehoudenPersonen();

    /**
     * Geef de lijst met extra aangemaakte niet ingeschrevenen terug. Dit zijn niet ingeschrevenen die in een
     * afleiding zijn aangemaakt.
     * Let op: roep deze methode aan na leidAf().
     *
     * @return de lijst met extra aangemaakte niet ingeschrevenen.
     */
    Set<PersoonHisVolledigImpl> getExtraAangemaakteNietIngeschrevenen();
}
