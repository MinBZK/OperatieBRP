/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ist;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3CategorieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.logisch.kern.Persoon;

/**
 * De categoriestapel van een LO3 persoon
 *
 * In LO3.x wordt niet over objecten gesproken, maar over categorieën. Van sommige categorieën (maar niet alle) kunnen
 * meerdere stapels aanwezig zijn.
 *
 * Overwegingen:
 *
 * 1) stapel in één structuur. Vier structuren: - categorie 5, een huwelijk met stapels vanuit beide partners. In
 * essentie nodig: verwijzing naar relatie(s), en verwijzing naar partners. Verder essentiële naamgegevens, en gegevens
 * over relatie inclusief document en opneming etc. Verder een volgnummer binnen de categorie, omdat er meer 'categorie
 * 5' stapels kunnen zijn. - categorie 2 en 2, informatie over de ouders. In essentie nodig: verwijzing naar de relatie.
 * Geen verwijzing naar de specifieke ouder(!), dezelfde stapel kan namelijk meer verschillende ouders bevatten. Relatie
 * naar het kind is mogelijk, edoch in essentie overbodig als er al een relatie naar zijn FRB is. Verder essentiële
 * naamgegevens, en gegevens over document en opneming etc. Volgnummer eigenlijk niet nodig, omdat er voor categorie 2
 * en 3 elk precies één stapel is. - categorie 9, een verwijzing naar een kind. In essentie nodig: verwijzing naar de
 * relatie (van het kind), en een verwijzing naar de ouder voor wie deze stapel van toepassing is. Volgnummer, omdat er
 * meerdere categorie 9 stapels kunnen zijn. - categorie 11, een uitspraak over gezag, een verwijzing naar OF het kind,
 * OF de FRB: heb je de één dan heb je de ander. Volgnummer overbodig, want je hebt maar één stapel voor categorie 11.
 * NB: categorie 11 hoeft niet synchroon te lopen met categorie 2 of 3 ==> eigen stapel!
 *
 * MB: als relatie=FRB, en persoon is het kind daarin, dan TOCH persoon vullen, en wel met kind. Dus: categorie 2, 3 en
 * 11: persoon = kind van FRB.
 *
 * --aandachtspunt: weggooien en opnieuw aanmaken & uit de sleutels lopen(!)
 *
 * Uitgangspunten: IST is géén boedelbak. Dus bijvoorbeeld: geen materiële historie op naamgebruik toevoegen aan de IST.
 *
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface StapelBasis extends BrpObject {

    /**
     * Retourneert Persoon van Stapel.
     *
     * @return Persoon.
     */
    Persoon getPersoon();

    /**
     * Retourneert Categorie van Stapel.
     *
     * @return Categorie.
     */
    LO3CategorieAttribuut getCategorie();

    /**
     * Retourneert Volgnummer van Stapel.
     *
     * @return Volgnummer.
     */
    VolgnummerAttribuut getVolgnummer();

}
