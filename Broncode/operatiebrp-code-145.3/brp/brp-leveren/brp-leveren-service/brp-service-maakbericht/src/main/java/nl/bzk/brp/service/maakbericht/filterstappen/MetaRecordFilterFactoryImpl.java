/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import java.util.function.Predicate;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.persoon.HistorieVanafPredikaat;
import nl.bzk.brp.domain.leveringmodel.persoon.PeilmomentHistorievormPredicate;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.springframework.stereotype.Component;

/**
 * Factory class voor het maken van de specifieke predikaten om {@link MetaRecord}s weg te filteren. De predikaten worden gecombineerd in een AND predikaat
 * zodat aan alle predikaten voldaan moet worden wil het record geldig zijn.
 */
@Component
@Bedrijfsregel(Regel.R2224)
@Bedrijfsregel(Regel.R2226)
@Bedrijfsregel(Regel.R2586)
final class MetaRecordFilterFactoryImpl implements MetaRecordFilterFactory {

    private static final ConversieHistoriecorrectiePredicate VOORKOMEN_LEVERING_MUTATIE_PREDIKAAT = new ConversieHistoriecorrectiePredicate();

    private MetaRecordFilterFactoryImpl() {
    }

    @Override
    public Predicate<MetaRecord> maakRecordfilters(final Berichtgegevens berichtgegevens) {
        Predicate<MetaRecord> predikaat = new MetaRecordAutorisatiePredicate(berichtgegevens);

        if (berichtgegevens.isMutatieberichtMetMeldingVerstrekkingsbeperking()) {
            predikaat = predikaat.and(new ActueelEnIdentificerendMetaRecordPredicate(berichtgegevens.getPersoonslijst().getNuNuBeeld()));
        }

        if (berichtgegevens.getSoortSynchronisatie() == SoortSynchronisatie.VOLLEDIG_BERICHT) {
            predikaat = predikaat.and(VOORKOMEN_LEVERING_MUTATIE_PREDIKAAT);
        }

        if (berichtgegevens.getDatumAanvangMaterielePeriode() != null) {
            predikaat = predikaat.and(HistorieVanafPredikaat.geldigOpEnNa(berichtgegevens.getDatumAanvangMaterielePeriode()));
        }

        if (berichtgegevens.getMaakBerichtPersoon().getHistorieFilterInformatie() != null
                && berichtgegevens.getMaakBerichtPersoon().getHistorieFilterInformatie().getHistorieVorm() != null) {
            predikaat = predikaat.and(new PeilmomentHistorievormPredicate(
                    berichtgegevens.getMaakBerichtPersoon().getHistorieFilterInformatie().getPeilmomentMaterieel(),
                    berichtgegevens.getMaakBerichtPersoon().getHistorieFilterInformatie().getPeilmomentFormeel(),
                    berichtgegevens.getMaakBerichtPersoon().getHistorieFilterInformatie().getHistorieVorm()));
        }
        return predikaat;
    }
}
