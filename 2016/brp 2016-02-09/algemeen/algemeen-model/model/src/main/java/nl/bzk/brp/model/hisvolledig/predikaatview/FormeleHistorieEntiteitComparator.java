/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview;

import java.io.Serializable;
import java.util.Comparator;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeleHistorie;


/**
 * Deze klasse wordt gebruikt om formele historie instanties te sorteren.
 * <p/>
 * Dit volgens de volgende specs:
 * <p/>
 * <p/>
 * Als sorteervolgorde van actuele/historische verschijningen van een gegevensgroep: a) verwerkingssoort - Volgens logica sorteren verwerkingssoort b)
 * tijdstipVervallen – Aflopend c) tijdstipRegistratie – Aflopend
 * <p/>
 * Dit met volgende sorteer-regels: - tijdstipVervallen als eerste sorteerelement om zo de geldige en niet geldige historie te scheiden - ‘aflopend’ om
 * meest actuele situatie vooraan te hebben - indien tijdstipVervallen niet is gevuld, dan wordt hiervoor ten behoeve van de sortering de
 * ‘eeuwigheidswaarde’ genomen (99991231)
 *
 * @param <T> Soort entiteit die deze comparator kan vergelijken
 */
public final class FormeleHistorieEntiteitComparator<T extends FormeelHistorisch & FormeleHistorie> extends
    AbstractHistorieEntiteitComparator implements Comparator<T>, Serializable
{

    private static final long serialVersionUID = 1L;

    @Override
    public int compare(final T entiteit1, final T entiteit2) {
        int vergelijking = 0;

        if (entiteit1.getVerwerkingssoort() != null && entiteit2.getVerwerkingssoort() != null) {
            vergelijking = entiteit1.getVerwerkingssoort().compareTo(entiteit2.getVerwerkingssoort());
        }

        if (vergelijking == 0) {
            vergelijking = vergelijk(entiteit1.getTijdstipRegistratie(), entiteit2.getTijdstipRegistratie());
        }

        if (vergelijking == 0) {
            vergelijking = vergelijk(entiteit1.getDatumTijdVerval(), entiteit2.getDatumTijdVerval());
        }

        return vergelijking;
    }

}
