/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BooleanSupplier;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistoriePatroon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerantwoordingCategorie;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;

/**
 * Hulpklasse voor de bepaling van de actuele records.
 */
final class ActueelBepaling {

    /**
     * De berekende actuele records
     */
    private Set<MetaRecord> actueleRecords = new HashSet<>();
    /**
     * De berekende actuele records
     */
    private Map<MetaGroep, MetaRecord> actueleRecordsPerGroep = new HashMap<>();

    ActueelBepaling(final MetaObject persoonObject) {
        //bepaal multimap van actuele records
        //records zonder historie (G), zijn altijd actueel, hierop volgt mogelijk nog een correctie
        //records met actieverantwoording zijn actueel indien niet vervallen en niet beeindigd
        //records met dienstverantwoording zijn actueel indien niet vervallen
        final Multimap<MetaObject, MetaRecord> records = bepaalActueleRecords(persoonObject);
        //Corrigeer actuele identiteitrecords met vervallen standaard (Persoon.Afnemerindicatie, Persoon.BuitenlandsPersoonsnummer etc..)
        corrigeerIdentiteitrecordsMetVervallenStandaard(records);

        actueleRecords = ImmutableSet.copyOf(records.values());
        final Map<MetaGroep, MetaRecord> tempActueelRecordPerGroep = Maps.newHashMap();
        for (MetaRecord actueleRecord : actueleRecords) {
            tempActueelRecordPerGroep.put(actueleRecord.getParentGroep(), actueleRecord);
        }
        actueleRecordsPerGroep = ImmutableMap.copyOf(tempActueelRecordPerGroep);
    }

    Set<MetaRecord> getActueleRecords() {
        return actueleRecords;
    }

    Map<MetaGroep, MetaRecord> getActueleRecordsPerGroep() {
        return actueleRecordsPerGroep;
    }

    private void corrigeerIdentiteitrecordsMetVervallenStandaard(final Multimap<MetaObject, MetaRecord> records) {
        final HashSet<MetaObject> copySet = Sets.newHashSet(records.keySet());
        for (MetaObject metaObject : copySet) {
            boolean heeftIdentiteitGroepZonderHistorie = metaObject.getObjectElement().getGroepen().stream().anyMatch(groepElement ->
                    groepElement.isIdentiteitGroep() && groepElement.getHistoriePatroon() == HistoriePatroon.G);
            boolean heeftStandaardGroepMetHistorie = metaObject.getObjectElement().getGroepen().stream().anyMatch(groepElement ->
                    groepElement.isStandaardGroep() && groepElement.getHistoriePatroon() != HistoriePatroon.G);
            if (heeftIdentiteitGroepZonderHistorie && heeftStandaardGroepMetHistorie) {
                //is er een actueel standaard record
                final Collection<MetaRecord> recordsVanObject = records.get(metaObject);
                boolean actueelStandaardRecord = recordsVanObject.stream().anyMatch(metaRecord -> metaRecord.getParentGroep().getGroepElement()
                        .isStandaardGroep());
                if (!actueelStandaardRecord) {
                    records.removeAll(metaObject);
                }
            }
        }
    }

    private Multimap<MetaObject, MetaRecord> bepaalActueleRecords(final MetaObject persoonObject) {
        final Multimap<MetaObject, MetaRecord> records = HashMultimap.create();
        persoonObject.accept(new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                final VerantwoordingCategorie verantwoordingCategorie = record.getParentGroep().getGroepElement().getVerantwoordingCategorie();
                final BooleanSupplier actueelActieRecord = () -> verantwoordingCategorie == VerantwoordingCategorie.A
                        && record.getActieVerval() == null && record.getDatumEindeGeldigheid() == null;
                final BooleanSupplier actueelDienstRecord = () -> verantwoordingCategorie == VerantwoordingCategorie.D
                        && record.getDatumTijdVerval() == null;
                if (verantwoordingCategorie == VerantwoordingCategorie.G || actueelActieRecord.getAsBoolean() || actueelDienstRecord.getAsBoolean()) {
                    records.put(record.getParentGroep().getParentObject(), record);
                }
            }
        });
        return records;
    }
}
