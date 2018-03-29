/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Corrigeert mutlev records, zodat een correct aantal actueel records overblijft.
 */
@Bedrijfsregel(Regel.R2551)
final class WisOverigeMutLevRecordPredicate implements Predicate<MetaRecord> {

    private final Set<AdministratieveHandeling> toekomstigeHandelingen;

    public WisOverigeMutLevRecordPredicate(final Set<AdministratieveHandeling> toekomstigeHandelingen) {
        this.toekomstigeHandelingen = toekomstigeHandelingen;
    }

    @Override
    public boolean test(final MetaRecord metaRecord) {
        //true is behouden
        if (metaRecord.isIndicatieTbvLeveringMutaties() == Boolean.TRUE) {
            //als er meerdere records bestaan met IndicatieMutatieLevering = 'WAAR', behoud dan degene met de laatste tsreg
            final List<MetaRecord> recordList = metaRecord.getParentGroep().getRecords().stream()
                    .filter(metaRecord1 -> metaRecord1.isIndicatieTbvLeveringMutaties() == Boolean.TRUE)
                    .sorted((o1, o2) -> o2.getTijdstipRegistratie().compareTo(o1.getTijdstipRegistratie()))
                    .collect(Collectors.toList());
            boolean behoudRecord =  recordList.get(0) == metaRecord;

            //verwijder voorkomen indien er een record met IndicatieMutatieLevering <> 'WAAR'
            //en datumAanvangGeldigheid op later moment bestaat
            if (behoudRecord && metaRecord.getDatumAanvangGeldigheid() != null) {
                behoudRecord = metaRecord.getParentGroep().getRecords().stream()
                        .filter(metaRecord1 -> !toekomstigeHandelingen.contains(metaRecord1.getActieInhoud().getAdministratieveHandeling()))
                        .filter(metaRecord1 -> metaRecord1.getDatumAanvangGeldigheid() != null)
                        .filter(metaRecord1 -> metaRecord1.isIndicatieTbvLeveringMutaties() != Boolean.TRUE)
                        .filter(metaRecord1 -> metaRecord1.getDatumAanvangGeldigheid() > metaRecord.getDatumAanvangGeldigheid())
                        .count() == 0;
            }
            return behoudRecord;
        } else {
            //verwijder voorkomens waarvoor geldt dat IndicatieMutatieLevering <> 'WAAR' en er een voorkomen IndicatieMutatieLevering = 'WAAR' bestaat
            //met gelijke datumAanvangGeldigheid.
            return metaRecord.getParentGroep().getRecords().stream()
                    .filter(metaRecord1 -> metaRecord1.isIndicatieTbvLeveringMutaties() == Boolean.TRUE)
                    .filter(metaRecord1 -> new EqualsBuilder().append(metaRecord1
                            .getDatumAanvangGeldigheid(), metaRecord.getDatumAanvangGeldigheid()).isEquals())
                    .count() == 0;
        }
    }
}
