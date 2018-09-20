/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.basis.FormeleHistorie;
import nl.bzk.brp.model.basis.FormeleHistorieEntiteit;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat instantie.
 */
public class FormeleHistoriePredikaat implements Predicate {
    private final DatumTijd peilDatumTijd;

    /**
     * Constructor.
     *
     * @param moment de datumTijd waarop geldigheid wordt gecontroleerd
     */
    public FormeleHistoriePredikaat(final DatumTijd moment) {
        peilDatumTijd = moment;
    }

    /**
     * Geef een {@link Predicate} die voor een {@link FormeleHistorie} instantie geldigheid op een datum checkt.
     *
     * @param datumTijd de datumTijd waarop geldigheid wordt gecheckt
     * @return een predicaat
     */
    public static FormeleHistoriePredikaat bekendOp(final DatumTijd datumTijd) {
        return new FormeleHistoriePredikaat(datumTijd);
    }

    @Override
    public boolean evaluate(final Object object) {
        FormeleHistorieEntiteit entiteit = (FormeleHistorieEntiteit) object;
        FormeleHistorie formeleHistorie = entiteit.getFormeleHistorie();

        DatumTijd registratieDatumTijd = formeleHistorie.getDatumTijdRegistratie();
        DatumTijd vervalDatumTijd = formeleHistorie.getDatumTijdVerval();

        return (vervalDatumTijd == null || vervalDatumTijd.na(peilDatumTijd))
                && (registratieDatumTijd.voor(peilDatumTijd)
                || registratieDatumTijd.getWaarde().equals(peilDatumTijd.getWaarde()));
    }
}
