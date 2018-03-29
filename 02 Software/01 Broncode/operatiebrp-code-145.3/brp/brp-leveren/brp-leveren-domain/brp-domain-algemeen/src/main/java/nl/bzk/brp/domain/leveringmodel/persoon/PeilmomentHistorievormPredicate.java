/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import com.google.common.base.Predicate;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistoriePatroon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerantwoordingCategorie;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;


/**
 * Predikaat specifiek ten behoeve van de historie bepaling. Hierbij spelen 3 parameters een rol: formeel peilmoment, materieel peilmoment en historievorm:
 * - Het formele peilmoment bepaalt het moment in de tijd vanuit waar de situatie bekeken wordt, - Het materiele peilmoment geeft de materiele historie
 * terug vanaf 'oneindig vroeg' (de geboorte) tot en met het meegegeven materiele peilmoment. - De historievorm bepaalt of alleen de actuele situatie
 * teruggegeven wordt (Geen), alle materiele, niet vervallen historie (Materieel) of alle beschikbare historie (MaterieelFormeel).
 */
@Bedrijfsregel(Regel.R2224)
@Bedrijfsregel(Regel.R2225)
@Bedrijfsregel(Regel.R2226)
public final class PeilmomentHistorievormPredicate implements Predicate<MetaRecord> {

    private final Integer materieelTotEnMetMoment;
    private final ZonedDateTime formeelPeilmoment;
    private final HistorieVorm historievorm;

    /**
     * Constructor met de benodigde parameters voor dit predikaat.
     *
     * @param materieelTotEnMetMoment maximale tijdstip waarnaar wordt gekeken
     * @param formeelPeilmoment       moment van waaruit wordt gekeken
     * @param historievorm            welke historie wordt getoond
     */
    public PeilmomentHistorievormPredicate(final Integer materieelTotEnMetMoment,
                                           final ZonedDateTime formeelPeilmoment, final HistorieVorm historievorm) {
        this.materieelTotEnMetMoment = materieelTotEnMetMoment;
        this.formeelPeilmoment = formeelPeilmoment;
        this.historievorm = historievorm;
    }

    @Override
    public boolean apply(final MetaRecord record) {
        return record != null && doApply(record);
    }

    private boolean doApply(final MetaRecord record) {

        final boolean materieelTotEnMetAkkoord;
        if (record.getParentGroep().getGroepElement().getHistoriePatroon() == HistoriePatroon.G
                || record.getParentGroep().getGroepElement().getVerantwoordingCategorie() == VerantwoordingCategorie.G) {
            return true;
        } else if (record.getParentGroep().getGroepElement().isFormeel()) {
            // Materiele historie is niet relevant hier, dus standaard akkoord.
            materieelTotEnMetAkkoord = true;
        } else {
            final Integer aanvangDatum = record.getDatumAanvangGeldigheid();
            Integer eindeDatum = null;
            if (record.getDatumEindeGeldigheid() != null) {
                eindeDatum = record.getDatumEindeGeldigheid();
            }
            materieelTotEnMetAkkoord = GeldigheidUtil.materieelGeldig(aanvangDatum, eindeDatum, historievorm, materieelTotEnMetMoment);
        }
        boolean formeelAkkoord = false;
        if (materieelTotEnMetAkkoord) {
            final ZonedDateTime registratieDatumTijd;
            ZonedDateTime vervalDatumTijd = null;
            if (record.getParentGroep().getGroepElement().getVerantwoordingCategorie() == VerantwoordingCategorie.D) {
                final MetaAttribuut tijdstipRegistratie = record.getAttribuut(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE);
                final MetaAttribuut tijdstipVerval = record.getAttribuut(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPVERVAL);
                registratieDatumTijd = tijdstipRegistratie.getWaarde();
                if (tijdstipVerval != null) {
                    vervalDatumTijd = tijdstipVerval.getWaarde();
                }
            } else {
                registratieDatumTijd = record.getTijdstipRegistratieAttribuut();
                vervalDatumTijd = record.getDatumTijdVervalAttribuut();
            }
            // We nemen dit record mee als de materiele en formele historie akkoord zijn.
            formeelAkkoord = GeldigheidUtil.formeelGeldig(registratieDatumTijd, vervalDatumTijd, historievorm, formeelPeilmoment);
        }

        return formeelAkkoord;
    }
}
