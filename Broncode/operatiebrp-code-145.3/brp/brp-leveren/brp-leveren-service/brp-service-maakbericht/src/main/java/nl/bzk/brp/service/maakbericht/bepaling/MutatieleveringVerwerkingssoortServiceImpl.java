/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.bepaling;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistoriePatroon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingssoort;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.ChildFirstModelVisitor;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.springframework.stereotype.Component;


/**
 * Service voor het bepalen van verwerkinggsoorten in het levermodel. Bepaalt de {@link Verwerkingssoort} voor alle berichtgegevens. Alleen relevant voor
 * mutatieberichten.
 *
 * * <li>Correct leveren van verwerkingsoort in mutatieberichten. <br>Voor mutatieberichten moet de verwerkingssoort van {@link
 * MetaRecord}s en {@link MetaObject}en bepaald worden. <br>Zie: {@link
 * MutatieleveringVerwerkingssoortServiceImpl} </li>
 */
@Component
@Bedrijfsregel(Regel.R1317)
@Bedrijfsregel(Regel.R1320)
final class MutatieleveringVerwerkingssoortServiceImpl implements MutatieleveringVerwerkingssoortService {

    @Override
    public Map<MetaModel, Verwerkingssoort> execute(final Persoonslijst persoonslijst) {

        final VerwerkingssoortVisitor visitor = new VerwerkingssoortVisitor(persoonslijst.getAdministratieveHandeling());
        visitor.visit(persoonslijst.getMetaObject());

        return visitor.verwerkingssoortMap;
    }

    /**
     * VerwerkingssoortVisitor.
     */
    private static final class VerwerkingssoortVisitor extends ChildFirstModelVisitor {

        private Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = new HashMap<>();

        private AdministratieveHandeling administratieveHandeling;

        private VerwerkingssoortVisitor(final AdministratieveHandeling administratieveHandeling) {
            this.administratieveHandeling = administratieveHandeling;
        }

        @Override
        @Bedrijfsregel(Regel.R1317)
        protected void doVisit(final MetaRecord record) {
            if (!verwerkingssoortMoetBepaaldWorden(record)) {
                return;
            }
            final Actie actieInhoud = record.getActieInhoud();
            final Actie actieAanpassingGeldigheid = record.getActieAanpassingGeldigheid();
            final Actie actieVerval = record.getActieVervalTbvLeveringMutaties() != null ? record.getActieVervalTbvLeveringMutaties()
                    : record.getActieVerval();

            final Set<Actie> matchendeActies = administratieveHandeling.getActies().stream().filter(actie ->
                    actie.equals(actieInhoud) || actie.equals(actieAanpassingGeldigheid) || actie.equals(actieVerval)).collect(Collectors.toSet());
            bepaalRecordVerwerkingssoort(matchendeActies, record, actieInhoud, actieAanpassingGeldigheid, actieVerval);
        }

        private void bepaalRecordVerwerkingssoort(final Collection<Actie> administratieveHandelingActies, final MetaRecord record, final Actie actieInhoud,
                                                  final Actie actieAanpassingGeldigheid, final Actie actieVerval) {
            Verwerkingssoort verwerkingssoort = null;

            if (administratieveHandelingActies.contains(actieInhoud)) {
                verwerkingssoort = Verwerkingssoort.TOEVOEGING;
            } else if (administratieveHandelingActies.contains(actieAanpassingGeldigheid)) {
                verwerkingssoort = Verwerkingssoort.WIJZIGING;
            } else if (administratieveHandelingActies.contains(actieVerval)) {
                verwerkingssoort = Verwerkingssoort.VERVAL;
            }

            if (verwerkingssoort == null && record.getParentGroep().getGroepElement().isIdentificerend() && geenDatumEindeGeldigheid(record)) {
                verwerkingssoort = Verwerkingssoort.IDENTIFICATIE;
            }

            if (verwerkingssoort == null) {
                verwerkingssoort = Verwerkingssoort.REFERENTIE;
            }
            verwerkingssoortMap.put(record, verwerkingssoort);
        }

        private boolean verwerkingssoortMoetBepaaldWorden(final MetaRecord record) {
            //geen verwerkingssoort op groepen zonder historie
            return record.getParentGroep().getGroepElement().getHistoriePatroon() != HistoriePatroon.G;
        }

        private boolean geenDatumEindeGeldigheid(final MetaRecord record) {
            return !record.getParentGroep().getGroepElement().isMaterieel() || record.getDatumEindeGeldigheid() == null;
        }

        @Override
        @Bedrijfsregel(Regel.R1320)
        protected void doVisit(final MetaObject object) {
            final Set<Verwerkingssoort> verwerkingssoorten = EnumSet.noneOf(Verwerkingssoort.class);
            final Set<MetaRecord> alleRecords = Sets.newHashSet();
            for (final MetaGroep groep : object.getGroepen()) {
                if (groep.getGroepElement().getHistoriePatroon() == HistoriePatroon.G) {
                    continue;
                }
                for (final MetaRecord record : groep.getRecords()) {
                    verwerkingssoorten.add(verwerkingssoortMap.get(record));
                    alleRecords.add(record);
                }
            }
            if (isVervallenObject(alleRecords, verwerkingssoorten)) {
                verwerkingssoortMap.put(object, Verwerkingssoort.VERVAL);
            } else {
                zetVerwerkingssoortOnvervallenObject(object, verwerkingssoorten);
            }
        }

        private void zetVerwerkingssoortOnvervallenObject(final MetaObject object, final Set<Verwerkingssoort> verwerkingssoorten) {
            if (verwerkingssoorten.size() == 1) {
                final Verwerkingssoort verwerkingsoort = Iterables.getOnlyElement(verwerkingssoorten);
                if (verwerkingsoort == Verwerkingssoort.REFERENTIE || verwerkingsoort == Verwerkingssoort.TOEVOEGING
                        || verwerkingsoort == Verwerkingssoort.IDENTIFICATIE) {
                    verwerkingssoortMap.put(object, verwerkingsoort);
                }
            } else {
                if (verwerkingssoorten.contains(Verwerkingssoort.IDENTIFICATIE) && bevatteAlleenIdentificatieOfReferentie(verwerkingssoorten)) {
                    verwerkingssoortMap.put(object, Verwerkingssoort.IDENTIFICATIE);
                }
            }
            verwerkingssoortMap.putIfAbsent(object, Verwerkingssoort.WIJZIGING);
        }

        private boolean bevatteAlleenIdentificatieOfReferentie(final Set<Verwerkingssoort> verwerkingssoorten) {
            boolean bevatAlleenIdentificatieOfReferentie = true;
            for (final Verwerkingssoort verwerkingssoort : verwerkingssoorten) {
                bevatAlleenIdentificatieOfReferentie &= verwerkingssoort == Verwerkingssoort.IDENTIFICATIE
                        || verwerkingssoort == Verwerkingssoort.REFERENTIE;
            }
            return bevatAlleenIdentificatieOfReferentie;
        }

        private boolean isVervallenObject(final Collection<MetaRecord> records, final Set<Verwerkingssoort> verwerkingssoorten) {
            if (verwerkingssoorten.contains(Verwerkingssoort.VERVAL)) {
                boolean isObjectVervallen = true;
                for (final MetaRecord record : records) {
                    if (!Verwerkingssoort.VERVAL.equals(verwerkingssoortMap.get(record))) {
                        isObjectVervallen &= isVervallenRecord(record);
                    }
                }
                if (isObjectVervallen) {
                    return true;
                }
            }
            return false;
        }

        private boolean isVervallenRecord(final MetaRecord record) {
            return record.getDatumTijdVerval() != null && record.getActieVerval() != null;
        }
    }
}
