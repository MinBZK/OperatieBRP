/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import com.google.common.collect.Sets;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistoriePatroon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.leveringmodel.ChildFirstModelVisitor;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.springframework.stereotype.Component;

/**
 * Service voor het opschonen van records, groepen en objecten tav indicatie voorkomen mutatielevering. In een volledig bericht worden records zonder indicatie
 * voorkomen tbv mutatielevering weggefilterd (zie {@link KandidaatRecordBepaling}). Indien er binnen een object enkel records overblijven in een groep zonder
 * formele historie (en dus zonder ind.mut.lev), dienen deze records weggefilterd te worden. De lege objecten en/of lege groepen die vervolgens overblijven
 * dienen ook weggefilterd te worden uit het volledig bericht.
 */
@Component
@Bedrijfsregel(Regel.R2507)
final class OpschonenTavIndMutLevServiceImpl implements MaakBerichtStap {

    @Override
    public void execute(final Berichtgegevens berichtgegevens) {
        if (berichtgegevens.isVolledigBericht()) {
            berichtgegevens.getPersoonslijst().getMetaObject().accept(new CorrigeerAutorisatieVisitor(berichtgegevens));
        }
    }

    private static final class CorrigeerAutorisatieVisitor extends ChildFirstModelVisitor {

        private final Berichtgegevens berichtgegevens;

        private CorrigeerAutorisatieVisitor(final Berichtgegevens berichtgegevens) {
            this.berichtgegevens = berichtgegevens;
        }

        @Override
        protected void doVisit(final MetaObject metaObject) {
            //check of object geautoriseerde groepen en/of objecten bevat
            final Set<MetaGroep> geautoriseerdeGroepen = berichtgegevens.getGeautoriseerdeGroepen();
            final Set<MetaObject> geautoriseerdeObjecten = berichtgegevens.getGeautoriseerdeObjecten();
            if (Sets.intersection(geautoriseerdeGroepen, metaObject.getGroepen()).isEmpty()
                    && Sets.intersection(geautoriseerdeObjecten, metaObject.getObjecten()).isEmpty()) {
                berichtgegevens.verwijderAutorisatie(metaObject);
            }
        }

        @Override
        protected void doVisit(final MetaGroep groep) {
            //verwijder autorisatie van geautoriseerde groepen zonder geautoriseerde records
            if (Sets.intersection(berichtgegevens.getGeautoriseerdeRecords(), groep.getRecords()).isEmpty()) {
                berichtgegevens.verwijderAutorisatie(groep);
            }
        }

        @Override
        protected void doVisit(final MetaRecord record) {
            //Indien record deel uitmaakt van groep zonder formele historie (en dus zonder ind.mut.lev.):
            //check of parent-object nog andere groepen heeft met records zonder ind.mut.lev. Zo niet, verwijder autorisatie.
            final boolean isRecordMetIndMutLev = Boolean.TRUE.equals(record.isIndicatieTbvLeveringMutaties());
            final boolean isRecordUitGroepZonderHistorie = record.getParentGroep().getGroepElement().getHistoriePatroon() == HistoriePatroon.G;
            if (!isRecordMetIndMutLev && isRecordUitGroepZonderHistorie && !bevatParentobjectGroepenMetRecordsZonderIndMutLev(record)) {
                berichtgegevens.verwijderAutorisatie(record);
            }
        }

        private boolean bevatParentobjectGroepenMetRecordsZonderIndMutLev(final MetaRecord metaRecord) {
            final MetaObject parentObject = metaRecord.getParentGroep().getParentObject();
            final Set<MetaGroep> overigeGroepen = Sets.symmetricDifference(parentObject.getGroepen(), Sets.newHashSet(metaRecord.getParentGroep()));
            for (final MetaGroep metaGroep : overigeGroepen) {
                for (final MetaRecord record : metaGroep.getRecords()) {
                    if (record.isIndicatieTbvLeveringMutaties() == null || !record.isIndicatieTbvLeveringMutaties()) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
