/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import java.util.Calendar;
import java.util.Date;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieleHistorieModel;
import nl.bzk.brp.model.hisvolledig.GlazenbolException;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.time.DateUtils;


/**
 * Predikaat instantie.
 */
public class MaterieleHistoriePredikaat implements Predicate {

    private final DatumTijdAttribuut formeelPeilmoment;
    private final DatumAttribuut     materieelPeilmoment;

    /**
     * Constructor.
     *
     * @param formeelPeilmoment   de datumTijd waarop geldigheid wordt gecontroleerd tegen de formele historie
     * @param materieelPeilmoment de datum waarop geldigheid wordt gecontroleerd tegen de materiele historie
     */
    protected MaterieleHistoriePredikaat(final DatumTijdAttribuut formeelPeilmoment,
            final DatumAttribuut materieelPeilmoment)
    {
        final DatumTijdAttribuut morgen =
                new DatumTijdAttribuut(DateUtils.truncate(DateUtils.addDays(new Date(), 1), Calendar.DATE));
        if (!formeelPeilmoment.voor(morgen)) {
            throw new GlazenbolException("Kan niet naar een moment in de toekomst kijken");
        }

        this.formeelPeilmoment = formeelPeilmoment;
        this.materieelPeilmoment = materieelPeilmoment;
    }

    /**
     * Geef een {@link org.apache.commons.collections.Predicate} die voor een
     * {@link nl.bzk.brp.model.basis.MaterieelHistorisch} instantie geldigheid op een datum checkt.
     *
     * @param formeelPeilmoment   de datumTijd waarop geldigheid wordt gecheckt van de formele historie
     * @param materieelPeilmoment de datum waarop geldigheid wordt gecheckt van de materiele historie
     * @return een predicaat
     */
    public static MaterieleHistoriePredikaat geldigOp(final DatumTijdAttribuut formeelPeilmoment,
            final DatumAttribuut materieelPeilmoment)
    {
        return new MaterieleHistoriePredikaat(formeelPeilmoment, materieelPeilmoment);
    }

    /**
     * Geef een {@link org.apache.commons.collections.Predicate} die voor een
     * {@link nl.bzk.brp.model.basis.MaterieelHistorisch} instantie geldigheid op een datum checkt.
     *
     * @param peilmoment de datumTijd die wordt gebruikt als formeel <strong>en</strong> materieel peilmoment
     * @return een predicaat
     * @see #geldigOp(DatumTijdAttribuut, DatumAttribuut)
     */
    public static MaterieleHistoriePredikaat geldigOp(final DatumTijdAttribuut peilmoment) {
        return new MaterieleHistoriePredikaat(peilmoment, new DatumAttribuut(peilmoment.getWaarde()));
    }

    @Override
    public boolean evaluate(final Object object) {
        final MaterieelHistorisch entiteit = (MaterieelHistorisch) object;
        final MaterieleHistorieModel materieleHistorie = entiteit.getMaterieleHistorie();

        final DatumTijdAttribuut registratieDatumTijd = materieleHistorie.getTijdstipRegistratie();
        final DatumTijdAttribuut vervalDatumTijd = materieleHistorie.getDatumTijdVerval();

        final boolean formeleHistorieGeldig =
                (vervalDatumTijd == null || vervalDatumTijd.na(formeelPeilmoment))
                        && (registratieDatumTijd.voor(formeelPeilmoment) || registratieDatumTijd.getWaarde().equals(
                        formeelPeilmoment.getWaarde()));

        final DatumEvtDeelsOnbekendAttribuut aanvangDatum = materieleHistorie.getDatumAanvangGeldigheid();
        final DatumEvtDeelsOnbekendAttribuut eindeDatum = materieleHistorie.getDatumEindeGeldigheid();

        final boolean materieleHistorieGeldig =
                (eindeDatum == null || eindeDatum.na(materieelPeilmoment))
                        && (aanvangDatum.voor(materieelPeilmoment) || aanvangDatum.op(materieelPeilmoment));

        return formeleHistorieGeldig && materieleHistorieGeldig;
    }
}
