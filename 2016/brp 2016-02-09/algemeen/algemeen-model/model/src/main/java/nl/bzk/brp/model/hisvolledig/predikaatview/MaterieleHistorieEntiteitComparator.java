/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview;

import java.io.Serializable;
import java.util.Comparator;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieleHistorie;


/**
 * Deze klasse wordt gebruikt om materiele historie instanties te sorteren.
 * <p/>
 * Dit volgens de volgende specs:
 * <p/>
 * <p/>
 * Als sorteervolgorde van actuele/historische verschijningen van een gegevensgroep: a) verwerkingssoort - Volgens logica sorteren verwerkingssoort b)
 * tijdstipVervallen – Aflopend c) datumAanvangGeldigheid – Aflopend d) datumEindeGeldigheid – Aflopend e) tijdstipRegistratie – Aflopend
 * <p/>
 * Dit met volgende sorteerregels:
 * <ul>
 * <li>tijdstipVervallen als eerste sorteerelement om zo de materiële en formele historie te scheiden</li>
 * <li>‘aflopend’ om meest actuele situatie vooraan te hebben</li>
 * <li>indien tijdstipVervallen en datumEindeGeldigheid niet zijn gevuld, dan wordt hiervoor ten behoeve van de sortering
 * de ‘eeuwigheidswaarde’ genomen (voor datumEindeGeldigheid dus 99991231)</li>
 * <li>tijdstipRegistratie speelt in de sortering alleen maar een rol bij groepen met alleen formele historie</li>
 * </ul>
 *
 * @param <T> Soort entiteit die deze comparator kan vergelijken
 */
public final class MaterieleHistorieEntiteitComparator<T extends MaterieelHistorisch & MaterieleHistorie> extends
    AbstractHistorieEntiteitComparator implements Comparator<T>, Serializable
{

    private static final long serialVersionUID = 1L;

    @Override
    public int compare(final T entiteit1, final T entiteit2) {
        int vergelijking = 0;

        // a) verwerkingssoort - Volgens logica sorteren verwerkingssoort
        // (vergelijking == 0 check alvast erbij, zodat het makkelijk schuiven is)
        if (entiteit1.getVerwerkingssoort() != null && entiteit2.getVerwerkingssoort() != null) {
            vergelijking = entiteit1.getVerwerkingssoort().compareTo(entiteit2.getVerwerkingssoort());
        }

        // b) tijdstipVervallen – Aflopend
        if (vergelijking == 0) {
            vergelijking =
                vergelijk(datumTijdOfEeuwigheid(entiteit1.getDatumTijdVerval()),
                    datumTijdOfEeuwigheid(entiteit2.getDatumTijdVerval()));
        }

        // c) datumAanvangGeldigheid – Aflopend
        if (vergelijking == 0) {
            // Datum aanvang geldigheid mag nooit leeg zijn, dus geen 'eeuwigheid' hier.
            vergelijking = vergelijk(entiteit1.getDatumAanvangGeldigheid(), entiteit2.getDatumAanvangGeldigheid());
        }

        // d) datumEindeGeldigheid – Aflopend
        if (vergelijking == 0) {
            vergelijking =
                vergelijk(datumOfEeuwigheid(entiteit1.getDatumEindeGeldigheid()),
                    datumOfEeuwigheid(entiteit2.getDatumEindeGeldigheid()));
        }

        // e) tijdstipRegistratie – Aflopend
        if (vergelijking == 0) {
            // Datum tijd registratie mag nooit leeg zijn, dus geen 'eeuwigheid' hier.
            vergelijking = vergelijk(entiteit1.getTijdstipRegistratie(), entiteit2.getTijdstipRegistratie());
        }

        return vergelijking;
    }

}
