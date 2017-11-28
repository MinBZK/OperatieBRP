/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistoriePatroon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;

/**
 * Bepaalt adhv van de indicaties (formeel / materieel) op de groep, of records geautoriseerd worden voor levering.
 */
@Bedrijfsregel(Regel.R1974)
@Bedrijfsregel(Regel.R1349)
@Bedrijfsregel(Regel.R1546)
final class MetaRecordAutorisatiePredicate implements Predicate<MetaRecord> {

    private final Map<Integer, DienstbundelGroep> dienstbundelGroepen = Maps.newHashMap();
    private final boolean isMutatieLevering;

    /**
     * Constructor.
     * @param berichtgegevens de persoon
     */
    MetaRecordAutorisatiePredicate(final Berichtgegevens berichtgegevens) {
        for (DienstbundelGroep dienstbundelGroep : berichtgegevens.getAutorisatiebundel().getDienst().getDienstbundel().getDienstbundelGroepSet()) {
            dienstbundelGroepen.put(dienstbundelGroep.getGroep().getId(), dienstbundelGroep);
        }
        this.isMutatieLevering = berichtgegevens.isMutatiebericht();
    }

    @Override
    public boolean test(@Nullable final MetaRecord metaRecord) {
        if (metaRecord == null) {
            throw new IllegalStateException("metarecord kan niet null zijn");
        }
        return isRecordGeautoriseerd(metaRecord);
    }

    /**
     * Controleert of een record getoond mag worden. Wanneer er geen voorkomen in de abonnementGroep aanwezig is, dan zijn er twee uitkomsten: voor
     * mutatielevering wordt de groep wel getoond, in andere gevallen niet.
     * @param record het ElementIdentificeerbaar object
     * @return true als de groep mag voorkomen, anders false
     */
    private boolean isRecordGeautoriseerd(final MetaRecord record) {
        final GroepElement groepElement = record.getParentGroep().getGroepElement();
        if (groepElement.getHistoriePatroon() == HistoriePatroon.G) {
            //sommige identiteitgroepen hebben geen historie maar wel aanwijsbare attributen
            //deze groepen bevatten 1 record en deze willen we hier niet wegfilteren.
            return true;
        }

        final boolean recordTonen;
        final DienstbundelGroep dienstbundelGroep = dienstbundelGroepen.get(groepElement.getId());
        if (dienstbundelGroep != null) {
            final boolean heeftIndicatieMaterieleHistorie = dienstbundelGroep.getIndicatieMaterieleHistorie();
            final boolean heeftIndicatieFormeleHistorie = dienstbundelGroep.getIndicatieFormeleHistorie();
            final boolean moetFormeleHistorieTonen = isMutatieLevering || heeftIndicatieFormeleHistorie;
            recordTonen = magRecordTonen(record, heeftIndicatieMaterieleHistorie, moetFormeleHistorieTonen);
        } else {
            // Er is geen voorkomen in de abonnementGroep autorisatie
            recordTonen = magRecordTonen(record, false, isMutatieLevering);
        }

        return recordTonen;
    }

    /**
     * Bepaalt of het voorkomen van een groep mag worden getoond.
     * @param record een formeel of materieel historisch voorkomen van een groep
     * @param heeftIndicatieMaterieleHistorie de indicatie mag materiele historie zien
     * @param heeftIndicatieFormeleHistorie de indicatie mag formele historie zien
     * @return {@code true} indien het voorkomen getoond mag worden
     */
    private boolean magRecordTonen(final MetaRecord record,
                                   final boolean heeftIndicatieMaterieleHistorie,
                                   final boolean heeftIndicatieFormeleHistorie) {
        boolean resultaat = false;
        if (record.getParentGroep().getGroepElement().isFormeel()) {
            resultaat = heeftIndicatieFormeleHistorie || record.getActieVerval() == null;
        } else if (record.getParentGroep().getGroepElement().isMaterieel()) {
            resultaat = magMaterieelZien(record, heeftIndicatieMaterieleHistorie, heeftIndicatieFormeleHistorie);
        }
        return resultaat;
    }

    /**
     * Bepaalt of het record met materiele historie getoond mag worden.
     * @param record Het record.
     * @param heeftIndicatieMaterieleHistorie De indicatie heeft materiele historie.
     * @param heeftIndicatieFormeleHistorie De indicatie heeft formele historie.
     * @return {@code true} als het record getoond mag worden, anders false.
     */
    private boolean magMaterieelZien(final MetaRecord record, final boolean heeftIndicatieMaterieleHistorie,
                                     final boolean heeftIndicatieFormeleHistorie) {
        if (heeftIndicatieMaterieleHistorie && heeftIndicatieFormeleHistorie) {
            // beide vlaggen op aan
            return true;
        }

        final boolean resultaat;
        final boolean entiteitIsNietVervallen = record.getActieVerval() == null;
        final boolean entiteitIsNietBeeindigd = record.getDatumEindeGeldigheid() == null;

        if (!(heeftIndicatieMaterieleHistorie || heeftIndicatieFormeleHistorie)) {
            // beide vlaggen op uit, alleen entiteit "huidig"
            resultaat = entiteitIsNietVervallen && entiteitIsNietBeeindigd;
        } else if (heeftIndicatieMaterieleHistorie) {
            // alleen materiele vlag staat aan, dus alleen entiteiten zonder datumtijd verval
            resultaat = entiteitIsNietVervallen;
        } else {
            // alleen formele vlag staat aan, dus alleen entiteiten zonder datum einde geldigheid
            resultaat = entiteitIsNietBeeindigd;
        }
        return resultaat;
    }
}
