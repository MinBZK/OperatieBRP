/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import java.util.Calendar;
import java.util.Date;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeleHistorieModel;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieleHistorieModel;
import nl.bzk.brp.model.hisvolledig.GlazenbolException;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.time.DateUtils;


/**
 * Predikaat waarmee voor Identificerende groepen (ook wel altijd-tonen-groepen genoemd) de meest recente record op een
 * peilmoment wordt gezocht.
 *
 * VR00079 Een mutatielevering bevat slechts groepen die in de administratieve handeling geraakt zijn plus de
 * identificerende groepen.
 *
 * Bij een Mutatielevering worden in het resultaat alleen groepen opgenomen die voldoen aan minstens één van de volgende
 * voorwaarden:
 * - ActieInhoud hoort bij de Administratieve handelingen en (ActieAanpassingGeldigheid is leeg )
 * - ActieAanpassingGeldigheid hoort bij de Administratieve handeling
 * - ActieVerval hoort bij de Admninistratieve handeling
 * - Het betreft een Identificerende groep
 *
 * Dit predikaat zorgt voor het laatste punt dat hierboven wordt genoemd omtrent de identificerende groepen.
 *
 * @brp.bedrijfsregel BRLV0048
 */
@Regels(Regel.BRLV0048)
public class AltijdTonenGroepPredikaat implements Predicate {

    private final DatumTijdAttribuut peilDatumTijd;
    private final DatumAttribuut     peilDatum;

    /**
     * Constructor.
     *
     * @param moment de datumTijd waarop geldigheid wordt gecontroleerd
     */
    public AltijdTonenGroepPredikaat(final DatumTijdAttribuut moment) {
        final DatumTijdAttribuut morgen =
                new DatumTijdAttribuut(DateUtils.truncate(DateUtils.addDays(new Date(), 1), Calendar.DATE));
        if (!moment.voor(morgen)) {
            throw new GlazenbolException("Kan niet naar een moment in de toekomst kijken");
        }

        peilDatumTijd = moment;
        peilDatum = new DatumAttribuut(moment.getWaarde());
    }

    /**
     * Geef een {@link org.apache.commons.collections.Predicate} die voor een
     * {@link nl.bzk.brp.model.basis.FormeelHistorisch} instantie
     * geldigheid op een datum checkt.
     *
     * @param datumTijd de datumTijd waarop geldigheid wordt gecheckt
     * @return een predicaat
     */
    public static AltijdTonenGroepPredikaat bekendOp(final DatumTijdAttribuut datumTijd) {
        return new AltijdTonenGroepPredikaat(datumTijd);
    }

    @Override
    public boolean evaluate(final Object object) {
        if (object instanceof FormeelHistorisch) {
            final FormeelHistorisch entiteit = (FormeelHistorisch) object;
            final FormeleHistorieModel formeleHistorie = entiteit.getFormeleHistorie();

            return formeleHistorieGeldigOpPeilmoment(formeleHistorie);
        } else {
            final MaterieelHistorisch entiteit = (MaterieelHistorisch) object;
            final MaterieleHistorieModel materieleHistorie = entiteit.getMaterieleHistorie();

            final DatumEvtDeelsOnbekendAttribuut eindDatumTijd = materieleHistorie.getDatumEindeGeldigheid();
            final DatumEvtDeelsOnbekendAttribuut aanvangDatum = materieleHistorie.getDatumAanvangGeldigheid();

            final boolean formeleHistorieGeldig = formeleHistorieGeldigOpPeilmoment(materieleHistorie);

            return formeleHistorieGeldig && (eindDatumTijd == null || eindDatumTijd.na(peilDatum))
                    && (aanvangDatum.voor(peilDatum) || aanvangDatum.getWaarde().equals(peilDatum.getWaarde()));
        }
    }

    /**
     * Controleert voor een formeleHistorie entiteit of deze op de peilDatumTijd geldig is.
     *
     * @param formeleHistorie De formeleHistorie entiteit.
     * @return Boolean true als de entiteit geldig is op het peilmoment, anders false.
     */
    private boolean formeleHistorieGeldigOpPeilmoment(final FormeleHistorieModel formeleHistorie) {
        final DatumTijdAttribuut registratieDatumTijd = formeleHistorie.getTijdstipRegistratie();
        final DatumTijdAttribuut vervalDatumTijd = formeleHistorie.getDatumTijdVerval();

        return (vervalDatumTijd == null || vervalDatumTijd.na(peilDatumTijd))
                && (registratieDatumTijd.voor(peilDatumTijd) || registratieDatumTijd.getWaarde().equals(
                peilDatumTijd.getWaarde()));
    }
}
