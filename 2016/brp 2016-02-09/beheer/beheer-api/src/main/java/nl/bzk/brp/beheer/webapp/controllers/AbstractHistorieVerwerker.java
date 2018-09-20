/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import java.util.Date;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.FormeelHistorisch;

/**
 * Basis historie verwerker.
 *
 * @param <T> entiteit type
 * @param <H> historie type
 */
public abstract class AbstractHistorieVerwerker<T, H extends FormeelHistorisch> implements HistorieVerwerker<T, H> {

    @Override
    public final void verwerkHistorie(final T inputItem, final T managedItem) {
        final H nieuweHistorie = maakHistorie(inputItem);
        final Set<H> managedHistorie = geefHistorie(managedItem);
        final H actueleHistorie = zoekActueleRecord(managedHistorie);

        if (nieuweHistorie == null) {
            // Enkel actueel laten vervallen
            if (actueleHistorie != null) {
                actueleHistorie.getFormeleHistorie().setDatumTijdVerval(new DatumTijdAttribuut(new Date()));
            }
            kopieerHistorie(inputItem, managedItem);
        } else {
            // Actueel laten vervallen (op registratie tijdstip van nieuwe) en nieuwe toevoegen
            if (actueleHistorie == null || !isHistorieInhoudelijkGelijk(nieuweHistorie, actueleHistorie)) {
                if (actueleHistorie != null) {
                    actueleHistorie.getFormeleHistorie().setDatumTijdVerval(nieuweHistorie.getFormeleHistorie().getTijdstipRegistratie());
                }
                managedHistorie.add(nieuweHistorie);
                kopieerHistorie(inputItem, managedItem);
            }
        }
    }

    /**
     * Geef de historie van het item.
     *
     * @param item item
     * @return historie set
     */
    public abstract Set<H> geefHistorie(T item);

    /**
     * Vergelijk historie records inhoudelijk.
     *
     * @param nieuweHistorie nieuwe historie
     * @param actueleRecord actuele historie
     * @return true, als de records inhoudelijk gelijk zijn
     */
    public abstract boolean isHistorieInhoudelijkGelijk(final H nieuweHistorie, final H actueleRecord);

    /**
     * Maak een historie record (met inhoud).
     *
     * @param item item (entiteit)
     * @return historie record, null als de historie inhoudelijk leeg zou zijn
     */
    public abstract H maakHistorie(final T item);

    /**
     * Kopieer de waarden voor de a-laag.
     *
     * @param item (input)
     * @param managedItem managed item (output)
     */
    public abstract void kopieerHistorie(final T item, final T managedItem);

    /**
     * Bepaal het actuele record.
     *
     * @param histories historie set
     * @return actuele record, null als er geen actueel record is
     */
    protected final H zoekActueleRecord(final Set<H> histories) {
        for (final H historie : histories) {
            if (historie.getFormeleHistorie().getDatumTijdVerval() == null) {
                return historie;
            }
        }
        return null;
    }

}
