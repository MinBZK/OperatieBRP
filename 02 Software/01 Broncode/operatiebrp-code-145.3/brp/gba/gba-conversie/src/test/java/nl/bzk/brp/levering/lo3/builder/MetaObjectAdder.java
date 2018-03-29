/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;

/**
 * Hulpmiddel om mutaties toe te voegen aan een bestaand MetaObject weergave van een persoon
 */
public class MetaObjectAdder {

    private static final Logger LOG = LoggerFactory.getLogger();

    private MetaObject basisMetaObject;

    private int teller = 100000;

    private MetaObjectAdder(final MetaObject basisMetaObject) {
        this.basisMetaObject = basisMetaObject;
    }

    /**
     * Maak een nieuwe MetaObjectAdder.
     * @param basisMetaObject MetaObject waarop mutaties moeten worden gedaan
     * @return Een object om mutaties toe te voegen aan een MetaObject
     */
    public static MetaObjectAdder nieuw(final MetaObject basisMetaObject) {
        return new MetaObjectAdder(basisMetaObject);
    }

    /**
     * Voeg mutatie toe aan het Persoon object
     * @param mutatieRecord Record om toe te voegen
     * @return MetaObjectAdder met nieuw aangemaakte Persoon
     */
    public MetaObjectAdder voegPersoonMutatieToe(final MetaRecord mutatieRecord) {
        return wijzigHistorie(mutatieRecord, (parentGroep, teMuterenRecord) -> {
            final Set<MetaRecord> nieuweMetaRecords = new HashSet<>();
            switch (parentGroep.getGroepElement().getHistoriePatroon()) {
                case F:
                case F1:
                    nieuweMetaRecords.add(kopieerMetaRecord(teMuterenRecord).metActieVerval(mutatieRecord.getActieInhoud()).build(parentGroep));
                    break;
                case F_M:
                case F_M1:
                case M1:
                    nieuweMetaRecords.add(kopieerMetaRecord(teMuterenRecord).metActieVerval(mutatieRecord.getActieInhoud()).build(parentGroep));
                    nieuweMetaRecords.add(
                            kopieerMetaRecord(teMuterenRecord).metActieAanpassingGeldigheid(mutatieRecord.getActieInhoud())
                                    .metDatumEindeGeldigheid(mutatieRecord.getDatumAanvangGeldigheid())
                                    .build(parentGroep));
                    break;
                case G:
                    // alleen record vervangen door een nieuw record?
                    break;
            }
            return nieuweMetaRecords;
        });
    }

    /**
     * Voeg correctie toe aan het Persoon object
     * @param correctieRecord Record om toe te voegen
     * @param nadereAanduidingVerval Reden van de correctie
     * @return MetaObjectAdder met nieuw aangemaakte Persoon
     */
    public MetaObjectAdder voegPersoonCorrectieToe(final MetaRecord correctieRecord, final String nadereAanduidingVerval) {
        return wijzigHistorie(correctieRecord, (parentGroep, teMuterenRecord) -> {
            final Set<MetaRecord> nieuweMetaRecords = new HashSet<>();
            switch (parentGroep.getGroepElement().getHistoriePatroon()) {
                case F:
                case F1:
                case F_M:
                case F_M1:
                case M1:
                    nieuweMetaRecords.add(
                            kopieerMetaRecord(teMuterenRecord).metNadereAanduidingVerval(nadereAanduidingVerval)
                                    .metActieVerval(correctieRecord.getActieInhoud())
                                    .build(parentGroep));
                    break;
                case G:
                    // alleen record vervangen door een nieuw record?
                    break;
            }
            return nieuweMetaRecords;
        });
    }

    private MetaObjectAdder wijzigHistorie(final MetaRecord nieuwRecord, final BiFunction<MetaGroep, MetaRecord, Set<MetaRecord>> historieLogica) {
        final MetaGroep parentGroep = nieuwRecord.getParentGroep();
        final MetaRecord teMuterenRecord = vindTeMuterenRecord(parentGroep);
        final Set<MetaRecord> nieuweMetaRecords = new HashSet<>();
        nieuweMetaRecords.add(nieuwRecord);

        final boolean mutatie = teMuterenRecord != null;
        if (mutatie) {
            nieuweMetaRecords.addAll(historieLogica.apply(parentGroep, teMuterenRecord));
        }

        basisMetaObject = bouwObject(basisMetaObject, parentGroep, nieuweMetaRecords, mutatie).build();
        if (!nieuweMetaRecords.isEmpty()) {
            basisMetaObject = voegRestantNieuweMetaRecordsAanBestaandeObjectenToe(basisMetaObject, nieuweMetaRecords).build();
        }
        if (!nieuweMetaRecords.isEmpty()) {
            basisMetaObject = voegRestantNieuweMetaRecordsToe(basisMetaObject, nieuweMetaRecords).build();
        }
        return this;
    }

    public MetaObject build() {
        return basisMetaObject;
    }

    private MetaRecord vindTeMuterenRecord(final MetaGroep parent) {
        Optional<MetaRecord> resultaat = Optional.empty();
        MetaObject doorTeZoekenMetaObject = vindTeMuterenObject(parent.getParentObject(), basisMetaObject);
        if (doorTeZoekenMetaObject != null) {
            MetaGroep groep = doorTeZoekenMetaObject.getGroep(parent.getGroepElement());
            if (groep != null) {
                resultaat = groep.getRecords().stream().filter(r -> r.getActieVerval() == null && r.getDatumEindeGeldigheid() == null).findFirst();
            }
        }
        return resultaat.orElse(null);
    }

    private MetaObject vindTeMuterenObject(final MetaObject teVindenObject, final MetaObject doorTeZoekenObject) {
        MetaObject resultaat = null;
        if (teVindenObject.getObjectElement().equals(doorTeZoekenObject.getObjectElement())
                && (teVindenObject.getObjectsleutel() == 0 || teVindenObject.getObjectsleutel() == doorTeZoekenObject.getObjectsleutel())) {
            resultaat = doorTeZoekenObject;
        } else {
            for (final MetaObject metaObject : doorTeZoekenObject.getObjecten()) {
                resultaat = vindTeMuterenObject(teVindenObject, metaObject);
                if (resultaat != null) {
                    break;
                }
            }
        }
        return resultaat;
    }

    private MetaRecord.Builder kopieerMetaRecord(final MetaRecord record) {
        MetaObject.Builder objectBuilder = MetaObject.maakBuilder();
        MetaRecord.Builder builder =
                objectBuilder.metObjectElement(record.getParentGroep().getParentObject().getObjectElement())
                        .metId(record.getParentGroep().getParentObject().getObjectsleutel())
                        .metGroep()
                        .metGroepElement(record.getParentGroep().getGroepElement())
                        .metRecord();
        builder.metId(record.getVoorkomensleutel() + teller++);
        if (record.getActieInhoud() != null) {
            builder.metActieInhoud(record.getActieInhoud());
        }
        if (record.getDatumAanvangGeldigheid() != null) {
            builder.metDatumAanvangGeldigheid(record.getDatumAanvangGeldigheid());
        }
        if (record.isIndicatieTbvLeveringMutaties() != null) {
            builder.metIndicatieTbvLeveringMutaties(record.isIndicatieTbvLeveringMutaties());
        }
        record.getAttributen().values().forEach((metaAttribuut) -> builder.metAttribuut(metaAttribuut.getAttribuutElement(), metaAttribuut.getWaarde()));
        return builder;
    }

    private MetaObject.Builder bouwObject(
            final MetaObject metaObject,
            final MetaGroep teWijzigenGroep,
            final Set<MetaRecord> nieuweRecords,
            final boolean mutatie) {
        final MetaObject.Builder objectBuilder = new MetaObject.Builder();
        objectBuilder.metObjectElement(metaObject.getObjectElement());
        objectBuilder.metId(metaObject.getObjectsleutel());
        metaObject.getObjecten()
                .forEach((object) -> objectBuilder.metObjecten(Collections.singletonList(bouwObject(object, teWijzigenGroep, nieuweRecords, mutatie))));
        final List<MetaGroep.Builder> groepen = new ArrayList<>();
        if (mutatie) {
            metaObject.getGroepen().stream().map((metaGroep) -> {
                MetaGroep.Builder groepBuilder;
                if (metaGroep.getParentObject().getObjectElement().equals(teWijzigenGroep.getParentObject().getObjectElement())
                        && (teWijzigenGroep.getParentObject().getObjectsleutel() == 0
                        || teWijzigenGroep.getParentObject().getObjectsleutel() == metaGroep.getParentObject().getObjectsleutel())
                        && metaGroep.getGroepElement().equals(teWijzigenGroep.getGroepElement())) {
                    groepBuilder = bouwTeWijzigenGroep(metaGroep, nieuweRecords);
                } else {
                    groepBuilder = bouwGroep(metaGroep);
                }
                return groepBuilder;
            }).forEach(groepen::add);
        } else {
            metaObject.getGroepen().stream().map((metaGroep) -> {
                MetaGroep.Builder groepBuilder;
                groepBuilder = bouwGroep(metaGroep);
                nieuweRecords.removeIf((MetaRecord r) -> {
                    if (metaGroep.getParentObject().getObjectElement().equals(r.getParentGroep().getParentObject().getObjectElement())
                            && (r.getParentGroep().getParentObject().getObjectsleutel() == 0
                            || r.getParentGroep().getParentObject().getObjectsleutel() == metaGroep.getParentObject().getObjectsleutel())
                            && metaGroep.getGroepElement().equals(r.getParentGroep().getGroepElement())) {
                        bouwRecord(r, groepBuilder);
                        return true;
                    } else {
                        return false;
                    }
                });
                return groepBuilder;
            }).forEach(groepen::add);
        }
        objectBuilder.metGroepen(groepen);
        return objectBuilder;
    }

    private MetaObject.Builder voegRestantNieuweMetaRecordsAanBestaandeObjectenToe(final MetaObject metaObject, final Set<MetaRecord> nieuweRecords) {
        final MetaObject.Builder objectBuilder = new MetaObject.Builder();
        objectBuilder.metObjectElement(metaObject.getObjectElement());
        objectBuilder.metId(metaObject.getObjectsleutel());
        metaObject.getObjecten().forEach(
                (object) -> objectBuilder.metObjecten(Collections.singletonList(voegRestantNieuweMetaRecordsAanBestaandeObjectenToe(object, nieuweRecords))));
        final List<MetaGroep.Builder> groepen = new ArrayList<>();
        nieuweRecords.removeIf((MetaRecord r) -> {
            if (r.getParentGroep().getParentObject().getObjectElement().equals(metaObject.getObjectElement())
                    && (r.getParentGroep().getParentObject().getObjectsleutel() == 0
                    || r.getParentGroep().getParentObject().getObjectsleutel() == metaObject.getObjectsleutel())) {
                final MetaGroep.Builder groepBuilder = new MetaGroep.Builder();
                groepBuilder.metGroepElement(r.getParentGroep().getGroepElement());
                bouwRecord(r, groepBuilder);
                groepen.add(groepBuilder);
                return true;
            } else {
                return false;
            }
        });
        metaObject.getGroepen().stream().map(this::bouwGroep).forEach(groepen::add);
        objectBuilder.metGroepen(groepen);
        return objectBuilder;
    }

    private MetaObject.Builder voegRestantNieuweMetaRecordsToe(final MetaObject metaObject, final Set<MetaRecord> nieuweRecords) {
        final MetaObject.Builder objectBuilder = new MetaObject.Builder();
        objectBuilder.metObjectElement(metaObject.getObjectElement());
        objectBuilder.metId(metaObject.getObjectsleutel());
        List<MetaObject.Builder> objectBuilders = new ArrayList<>();
        metaObject.getObjecten().forEach((object) -> objectBuilders.add(voegObjectenToe(object, nieuweRecords)));
        nieuweRecords.forEach(r -> {
            final MetaObject.Builder nieuwObjectBuilder = new MetaObject.Builder();
            MetaGroep.Builder nieuwGroepElement =
                    nieuwObjectBuilder.metObjectElement(r.getParentGroep().getParentObject().getObjectElement())
                            .metId(r.getParentGroep().getParentObject().getObjectsleutel())
                            .metGroep()
                            .metGroepElement(r.getParentGroep().getGroepElement());
            bouwRecord(r, nieuwGroepElement);
            objectBuilders.add(nieuwObjectBuilder);

        });
        objectBuilder.metObjecten(objectBuilders);
        final List<MetaGroep.Builder> groepen = new ArrayList<>();
        metaObject.getGroepen().stream().map(this::bouwGroep).forEach(groepen::add);
        objectBuilder.metGroepen(groepen);
        return objectBuilder;
    }

    private MetaObject.Builder voegObjectenToe(final MetaObject metaObject, final Set<MetaRecord> nieuweRecords) {
        final MetaObject.Builder objectBuilder = new MetaObject.Builder();
        objectBuilder.metObjectElement(metaObject.getObjectElement());
        objectBuilder.metId(metaObject.getObjectsleutel());
        List<MetaObject.Builder> objectBuilders = new ArrayList<>();
        metaObject.getObjecten().forEach((object) -> objectBuilders.add(voegObjectenToe(object, nieuweRecords)));
        nieuweRecords.removeIf((MetaRecord r) -> {
            LOG.info("metaObject type: " + metaObject.getObjectElement().getElementNaam());
            LOG.info("metaObject sleutel: " + metaObject.getObjectsleutel());
            boolean verwerk = r.getParentGroep() != null;
            verwerk = verwerk && r.getParentGroep().getParentObject() != null;
            verwerk = verwerk && r.getParentGroep().getParentObject().getParentObject() != null;
            verwerk = verwerk && r.getParentGroep().getParentObject().getParentObject().getObjectElement().equals(metaObject.getObjectElement());
            verwerk =
                    verwerk
                            && (r.getParentGroep().getParentObject().getParentObject().getObjectsleutel() == 0
                            || r.getParentGroep().getParentObject().getParentObject().getObjectsleutel() == metaObject.getObjectsleutel());

            if (verwerk) {
                final MetaObject.Builder nieuwObjectBuilder = new MetaObject.Builder();
                MetaGroep.Builder nieuwGroepElement =
                        nieuwObjectBuilder.metObjectElement(r.getParentGroep().getParentObject().getObjectElement())
                                .metId(r.getParentGroep().getParentObject().getObjectsleutel())
                                .metGroep()
                                .metGroepElement(r.getParentGroep().getGroepElement());
                bouwRecord(r, nieuwGroepElement);
                objectBuilders.add(nieuwObjectBuilder);
                return true;
            } else {
                return false;
            }
        });
        objectBuilder.metObjecten(objectBuilders);
        final List<MetaGroep.Builder> groepen = new ArrayList<>();
        metaObject.getGroepen().stream().map(this::bouwGroep).forEach(groepen::add);
        objectBuilder.metGroepen(groepen);
        return objectBuilder;
    }

    private MetaGroep.Builder bouwTeWijzigenGroep(final MetaGroep metaGroep, final Set<MetaRecord> nieuweRecords) {
        final MetaGroep.Builder groepBuilder = new MetaGroep.Builder();
        groepBuilder.metGroepElement(metaGroep.getGroepElement());
        metaGroep.getRecords().forEach((record) -> {
            if (record.getActieVerval() == null && record.getDatumEindeGeldigheid() == null) {
                nieuweRecords.removeIf((MetaRecord nieuwMetaRecord) -> {
                    bouwRecord(nieuwMetaRecord, groepBuilder);
                    return true;
                });
            } else {
                bouwRecord(record, groepBuilder);
            }
        });
        return groepBuilder;
    }

    private MetaGroep.Builder bouwGroep(final MetaGroep metaGroep) {
        final MetaGroep.Builder groepBuilder = new MetaGroep.Builder();
        groepBuilder.metGroepElement(metaGroep.getGroepElement());
        metaGroep.getRecords().forEach((record) -> bouwRecord(record, groepBuilder));
        return groepBuilder;
    }

    private MetaRecord.Builder bouwRecord(final MetaRecord record, final MetaGroep.Builder groepBuilder) {
        final MetaRecord.Builder metaRecordBuilder = groepBuilder.metRecord();
        metaRecordBuilder.metId(record.getVoorkomensleutel());
        if (record.getActieVerval() != null) {
            metaRecordBuilder.metActieVerval(record.getActieVerval());
        }
        if (record.getActieInhoud() != null) {
            metaRecordBuilder.metActieInhoud(record.getActieInhoud());
        }
        if (record.getActieAanpassingGeldigheid() != null) {
            metaRecordBuilder.metActieAanpassingGeldigheid(record.getActieAanpassingGeldigheid());
        }
        if (record.getActieVervalTbvLeveringMutaties() != null) {
            metaRecordBuilder.metActieVervalTbvLeveringMutaties(record.getActieVervalTbvLeveringMutaties());
        }
        if (record.getDatumAanvangGeldigheid() != null) {
            metaRecordBuilder.metDatumAanvangGeldigheid(record.getDatumAanvangGeldigheid());
        }
        if (record.getDatumEindeGeldigheid() != null) {
            metaRecordBuilder.metDatumEindeGeldigheid(record.getDatumEindeGeldigheid());
        }
        if (record.getNadereAanduidingVerval() != null) {
            metaRecordBuilder.metNadereAanduidingVerval(record.getNadereAanduidingVerval());
        }
        if (record.isIndicatieTbvLeveringMutaties() != null) {
            metaRecordBuilder.metIndicatieTbvLeveringMutaties(record.isIndicatieTbvLeveringMutaties());
        }
        record.getAttributen()
                .values()
                .forEach((metaAttribuut) -> metaRecordBuilder.metAttribuut(metaAttribuut.getAttribuutElement(), metaAttribuut.getWaarde()));
        return metaRecordBuilder;
    }

}
