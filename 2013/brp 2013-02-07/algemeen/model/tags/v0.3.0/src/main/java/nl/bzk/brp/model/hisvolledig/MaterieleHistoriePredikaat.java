/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.MaterieleHistorieEntiteit;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat instantie.
 */
public class MaterieleHistoriePredikaat implements Predicate {
    private final DatumTijd formeelPeilmoment;
    private final Datum     materieelPeilmoment;

    /**
     * Constructor.
     *
     * @param formeelPeilmoment   de datumTijd waarop geldigheid wordt gecontroleerd tegen de formele historie
     * @param materieelPeilmoment de datum waarop geldigheid wordt gecontroleerd tegen de materiele historie
     */
    public MaterieleHistoriePredikaat(final DatumTijd formeelPeilmoment, final Datum materieelPeilmoment) {
        this.formeelPeilmoment = formeelPeilmoment;
        this.materieelPeilmoment = materieelPeilmoment;
    }

    /**
     * Geef een {@link Predicate} die voor een {@link MaterieleHistorie} instantie geldigheid op een datum checkt.
     *
     * @param formeelPeilmoment   de datumTijd waarop geldigheid wordt gecheckt van de formele historie
     * @param materieelPeilmoment de datum waarop geldigheid wordt gecheckt van de materiele historie
     * @return een predicaat
     */
    public static MaterieleHistoriePredikaat geldigOp(final DatumTijd formeelPeilmoment,
                                                    final Datum materieelPeilmoment)
    {
        return new MaterieleHistoriePredikaat(formeelPeilmoment, materieelPeilmoment);
    }

    @Override
    public boolean evaluate(final Object object) {
        MaterieleHistorieEntiteit entiteit = (MaterieleHistorieEntiteit) object;
        MaterieleHistorie materieleHistorie = entiteit.getMaterieleHistorie();

        DatumTijd registratieDatumTijd = materieleHistorie.getDatumTijdRegistratie();
        DatumTijd vervalDatumTijd = materieleHistorie.getDatumTijdVerval();

        boolean formeleHistorieGeldig = (vervalDatumTijd == null || vervalDatumTijd.na(formeelPeilmoment))
                && (registratieDatumTijd.voor(formeelPeilmoment)
                || registratieDatumTijd.getWaarde().equals(formeelPeilmoment.getWaarde()));

        Datum aanvangDatum = materieleHistorie.getDatumAanvangGeldigheid();
        Datum eindeDatum = materieleHistorie.getDatumEindeGeldigheid();

        boolean materieleHistorieGeldig = (eindeDatum == null || eindeDatum.na(materieelPeilmoment))
                && (aanvangDatum.voor(materieelPeilmoment)
                || aanvangDatum.getWaarde().equals(materieelPeilmoment.getWaarde()));

        return formeleHistorieGeldig && materieleHistorieGeldig;
    }
}
