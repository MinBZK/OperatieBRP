/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import java.util.Calendar;
import java.util.Date;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeleHistorieModel;
import nl.bzk.brp.model.hisvolledig.GlazenbolException;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.time.DateUtils;


/**
 * Predikaat instantie.
 */
public class FormeleHistoriePredikaat implements Predicate {

    private final DatumTijdAttribuut peilDatumTijd;

    /**
     * Constructor.
     *
     * @param moment de datumTijd waarop geldigheid wordt gecontroleerd
     */
    protected FormeleHistoriePredikaat(final DatumTijdAttribuut moment) {
        final DatumTijdAttribuut morgen =
                new DatumTijdAttribuut(DateUtils.truncate(DateUtils.addDays(new Date(), 1), Calendar.DATE));
        if (!moment.voor(morgen)) {
            throw new GlazenbolException("Kan niet naar een moment in de toekomst kijken");
        }

        peilDatumTijd = moment;
    }

    /**
     * Geef een {@link org.apache.commons.collections.Predicate} die voor een
     * {@link nl.bzk.brp.model.basis.FormeelHistorisch} instantie geldigheid op een datum checkt.
     *
     * @param datumTijd de datumTijd waarop geldigheid wordt gecheckt
     * @return een predicaat
     */
    public static FormeleHistoriePredikaat bekendOp(final DatumTijdAttribuut datumTijd) {
        return new FormeleHistoriePredikaat(datumTijd);
    }

    @Override
    public final boolean evaluate(final Object object) {
        final FormeelHistorisch entiteit = (FormeelHistorisch) object;
        final FormeleHistorieModel formeleHistorie = entiteit.getFormeleHistorie();

        final DatumTijdAttribuut registratieDatumTijd = formeleHistorie.getTijdstipRegistratie();
        final DatumTijdAttribuut vervalDatumTijd = formeleHistorie.getDatumTijdVerval();

        return (vervalDatumTijd == null || vervalDatumTijd.na(peilDatumTijd))
                && (registratieDatumTijd.voor(peilDatumTijd) || registratieDatumTijd.getWaarde().equals(
                peilDatumTijd.getWaarde()));
    }
}
