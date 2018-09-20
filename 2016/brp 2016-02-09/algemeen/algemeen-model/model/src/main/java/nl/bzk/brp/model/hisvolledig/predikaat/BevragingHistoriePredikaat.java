/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Historievorm;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeleHistorieModel;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieleHistorieModel;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat specifiek ten behoeve van de historie die gebruikt wordt in het antwoord van een bevraging.
 * Hierbij spelen 3 parameters een rol: formeel peilmoment, materieel peilmoment en historievorm:
 * - Het formele peilmoment bepaalt het moment in de tijd vanuit waar de situatie bekeken wordt,
 * - Het materiele peilmoment geeft de materiele historie terug vanaf 'oneindig vroeg' (de geboorte) tot en met
 * het meegegeven materiele peilmoment.
 * - De historievorm bepaalt of alleen de actuele situatie teruggegeven wordt (Geen),
 * alle materiele, niet vervallen historie (Materieel) of alle beschikbare historie (MaterieelFormeel).
 */
public class BevragingHistoriePredikaat implements Predicate {

    private final DatumAttribuut     materieelTotEnMetMoment;
    private final DatumTijdAttribuut formeelPeilmoment;
    private final Historievorm       historievorm;

    /**
     * Constructor met de benodigede parameters voor dit predikaat.
     *
     * @param materieelTotEnMetMoment maximale tijdstip waarnaar wordt gekeken
     * @param formeelPeilmoment moment van waaruit wordt gekeken
     * @param historievorm welke historie wordt getoond
     */
    public BevragingHistoriePredikaat(final DatumAttribuut materieelTotEnMetMoment,
            final DatumTijdAttribuut formeelPeilmoment, final Historievorm historievorm)
    {
        this.materieelTotEnMetMoment = materieelTotEnMetMoment;
        this.formeelPeilmoment = formeelPeilmoment;
        this.historievorm = historievorm;
    }

    @Override
    public final boolean evaluate(final Object object) {
        FormeleHistorieModel formeleHistorie;

        boolean formeleHistorieAkkoord;
        boolean materieelTotEnMetAkkoord;

        if (object instanceof FormeelHistorisch) {
            final FormeelHistorisch entiteit = (FormeelHistorisch) object;
            formeleHistorie = entiteit.getFormeleHistorie();

            // Materiele historie is niet relevant hier, dus standaard akkoord.
            materieelTotEnMetAkkoord = true;

        } else if (object instanceof MaterieelHistorisch) {
            final MaterieelHistorisch entiteit = (MaterieelHistorisch) object;
            final MaterieleHistorieModel materieleHistorie = entiteit.getMaterieleHistorie();
            formeleHistorie = materieleHistorie;

            final DatumEvtDeelsOnbekendAttribuut aanvangDatum = materieleHistorie.getDatumAanvangGeldigheid();
            final DatumEvtDeelsOnbekendAttribuut eindeDatum = materieleHistorie.getDatumEindeGeldigheid();

            // Het record is materieel akkoord als de datum aanvang voor of op het peilmoment ligt.
            materieelTotEnMetAkkoord = aanvangDatum.voorOfOp(materieelTotEnMetMoment);
            // Als er een datum einde geldigheid bestaat en tov het peilmoment in het verleden ligt,
            // dan is dit een niet actueel geldig record, dus moet de historievorm inclusief materiele historie zijn.
            if (eindeDatum != null && eindeDatum.voorOfOp(materieelTotEnMetMoment)) {
                materieelTotEnMetAkkoord &= inclusiefMaterieleHistorie();
            }
        } else {
            throw new IllegalArgumentException("Ongeldig type object voor predikaat: " + object.getClass());
        }

        final DatumTijdAttribuut registratieDatumTijd = formeleHistorie.getTijdstipRegistratie();
        final DatumTijdAttribuut vervalDatumTijd = formeleHistorie.getDatumTijdVerval();

        // Het record is formeel akkoord als de registratiedatum op of voor het peilmoment liggen
        // (oftewel het peilmoment na de registratiedatum).
        formeleHistorieAkkoord = formeelPeilmoment.na(registratieDatumTijd);
        // Als we te maken hebben met niet actuele formele historie (record is vervallen)
        // en tov het peilmoment in het verleden ligt, dan moet de historievorm inclusief formele historie zijn.
        if (vervalDatumTijd != null && formeelPeilmoment.na(vervalDatumTijd)) {
            formeleHistorieAkkoord &= inclusiefFormeleHistorie();
        }

        // We nemen dit record mee als de materiele en formele historie akkoord zijn.
        return materieelTotEnMetAkkoord && formeleHistorieAkkoord;
    }

    private boolean inclusiefMaterieleHistorie() {
        return historievorm == Historievorm.MATERIEEL || historievorm == Historievorm.MATERIEEL_FORMEEL;
    }

    private boolean inclusiefFormeleHistorie() {
        return historievorm == Historievorm.MATERIEEL_FORMEEL;
    }

    public final DatumAttribuut getMaterieelTotEnMetMoment() {
        return materieelTotEnMetMoment;
    }

    public final DatumTijdAttribuut getFormeelPeilmoment() {
        return formeelPeilmoment;
    }

    public final Historievorm getHistorievorm() {
        return historievorm;
    }
}
