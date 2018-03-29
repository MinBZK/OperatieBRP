/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.element.ElementConstants;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.ChildFirstModelVisitor;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ModelIndex;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.springframework.stereotype.Component;

/**
 * In mutatieleveringen moet enkel de set gegevens geleverd worden die geraakt zijn in de administratievehandeling plus een set identificerende gegevens.
 * De stap zorgt ervoor dat identificerende gegevens niet wegvallen (in mutatieberichten) omdat ze niet deel uitmaken van de delta.
 * <p>
 * Er zijn meerdere de identificeren gegevens te benoemen.
 * <p>
 * Op de hoofdpersoon zijn de identificerende groepen: {@link nl.bzk.algemeenbrp.dal.domein.brp.enums.Element#PERSOON_IDENTIFICATIENUMMERS},{@link
 * nl.bzk.algemeenbrp.dal.domein.brp.enums.Element#PERSOON_GEBOORTE}, {@link nl.bzk.algemeenbrp.dal.domein.brp.enums.Element#PERSOON_GESLACHTSAANDUIDING},
 * {@link nl.bzk.algemeenbrp.dal.domein.brp.enums.Element#PERSOON_SAMENGESTELDENAAM},
 * <p>
 * Voor betrokken personen geldt dat de gehele betrokkenheidstructuur getoond wordt indien de delta iets in de betrokkenheidstructuur geraakt heeft.
 * <p>
 * Deze stap bepaalt eerst de records die geautoriseerd zijn: Dit is bijvoorbeeld:
 * <p>
 * <pre><code>
 * &nbsp;&nbsp;[r] Persoon.Identiteit
 * &nbsp;&nbsp;[r] Persoon.Identificatienummers
 * &nbsp;&nbsp;[r] Persoon.Geboorte
 * &nbsp;&nbsp;[r] Persoon.Geslachtsaanduiding
 * &nbsp;&nbsp;[r] Persoon.SamengesteldeNaam
 * &nbsp;&nbsp;[r] Persoon.Persoonskaart
 * </code></pre>
 * <p>
 * Daarna wordt bepaald welke records in de delta zitten, dit is  bijvoorbeeld:
 * <pre><code>
 * &nbsp;&nbsp;[r] Persoon.Persoonskaart
 * &nbsp;&nbsp;[r] Persoon.Voornaam.Standaard
 * </code></pre>
 * <p>
 * Sec kijkend naar de delta moet nu het symmetrisch verschil (onderstaande records) verwijderd worden:
 * <pre><code>
 * &nbsp;&nbsp;[r] Persoon.Identiteit (niet in delta)
 * &nbsp;&nbsp;[r] Persoon.Identificatienummers (niet in delta)
 * &nbsp;&nbsp;[r] Persoon.Geboorte (niet in delta)
 * &nbsp;&nbsp;[r] Persoon.Geslachtsaanduiding (niet in delta)
 * &nbsp;&nbsp;[r] Persoon.SamengesteldeNaam (niet in delta)
 * &nbsp;&nbsp;[r] Persoon.Voornaam.Standaard (niet geautoriseerd)
 * </code></pre>
 * <p>
 * Dit wordt vervolgens weer gecorrigeerd door de actuele identificerende groepen terug te zetten (mits geautoriseerd natuurlijk). Het lijstje met te
 * verwijderen records wordt uiteindelijk:
 * <p>
 * <pre><code>
 * &nbsp;&nbsp;[r] Persoon.Voornaam.Standaard
 * </code></pre>
 */
@Component
@Bedrijfsregel(Regel.R1542)
@Bedrijfsregel(Regel.R1975)
@Bedrijfsregel(Regel.R1973)
final class MutatieleveringBehoudIdentificerendeGegevensServiceImpl implements MaakBerichtStap {

    /**
     * Waarde voor: Persoon - geslachtsaanduiding.
     */
    private static final GroepElement PERSOON_GESLACHTSAANDUIDING = ElementHelper.getGroepElement(Element.PERSOON_GESLACHTSAANDUIDING);

    private static final Set<GroepElement> IDGROEPEN = Sets
            .newHashSet(ElementConstants.PERSOON_GEBOORTE, PERSOON_GESLACHTSAANDUIDING, ElementConstants.PERSOON_IDENTIFICATIENUMMERS,
                    ElementConstants.PERSOON_SAMENGESTELDENAAM);

    @Override
    public void execute(final Berichtgegevens berichtgegevens) {
        if (berichtgegevens.isMutatiebericht()) {
            //Bepaal de te verwijderen records:
            //Dit zijn records die in de delta zitten EN NIET geautoriseerd zijn.
            //OF dit zijn records die NIET in de delta zitten maar WEL geautoriseerd zijn
            final Set<MetaRecord> teVerwijderenRecords = Sets.newHashSet(Sets
                    .symmetricDifference(berichtgegevens.getGeautoriseerdeRecords(), berichtgegevens.getDeltaRecords()));
            //mogelijk wordt nu te veel verwijderd, van de identificerende groepen moet eea behouden blijven.
            teVerwijderenRecords.removeAll(bepaalTeBehoudenIdentificerendeRecordsVanHoofdpersoon(berichtgegevens));
            //van de betrokken persoon moet ook eea behouden blijven als de betrokkenheid geraakt is in de delta
            teVerwijderenRecords.removeAll(bepaalTeBehoudenIdentificerendeRecordsVanBetrokkenPersoon(berichtgegevens));

            //verwijder autorisatie voor alle records hierboven bepaald, behalve
            //de records van identiteitgroepen.
            final Set<MetaRecord> idRecordsZonderHistorie = Sets.newHashSet();
            for (final MetaRecord teVerwijderenRecord : teVerwijderenRecords) {
                final GroepElement groepElement = teVerwijderenRecord.getParentGroep().getGroepElement();
                if (groepElement.isIdentiteitGroep()) {
                    idRecordsZonderHistorie.add(teVerwijderenRecord);
                    continue;
                }
                berichtgegevens.verwijderAutorisatie(teVerwijderenRecord);
            }
            //verwijder nu records van de solitaire identiteitsgroepen zonder historie
            behoudRecordAutorisatie(berichtgegevens, idRecordsZonderHistorie);

            //verwijder objecten zonder onderliggende geautoriseerde records
            berichtgegevens.getPersoonslijst().getMetaObject().accept(new ObjectOpruimVisitor(berichtgegevens));
        }
    }

    /**
     * Behoud enkel de identiteitgroepen, indien geautoriseerd EN er andere groepen MET voorkomens bestaan onder hetzelfde object OF objecten worden
     * geleverd onder bovenliggend object.
     */
    private void behoudRecordAutorisatie(final Berichtgegevens berichtgegevens, final Set<MetaRecord> identiteitRecords) {
        for (MetaRecord idRecord : identiteitRecords) {
            boolean verwijderAutorisatie = true;
            final Collection<MetaModel> onderliggendeAutorisaties = berichtgegevens.getOnderliggendeAutorisaties(idRecord.getParentGroep().getParent());
            for (MetaModel onderliggendeAutorisatie : onderliggendeAutorisaties) {
                if (behoudRecordAutorisatie(berichtgegevens, idRecord, onderliggendeAutorisatie)) {
                    verwijderAutorisatie = false;
                    break;
                }
            }
            if (verwijderAutorisatie) {
                berichtgegevens.verwijderAutorisatie(idRecord);
            }
        }
    }

    private boolean behoudRecordAutorisatie(final Berichtgegevens berichtgegevens,
                                            final MetaRecord idRecord, final MetaModel onderliggendeAutorisatie) {
        if (onderliggendeAutorisatie instanceof MetaGroep) {
            final Collection<MetaModel> onderliggendeRecords = berichtgegevens.getOnderliggendeAutorisaties(onderliggendeAutorisatie);
            for (MetaModel onderliggendRecord : onderliggendeRecords) {
                if (!onderliggendRecord.equals(idRecord)) {
                    return true;
                }
            }
        } else if (onderliggendeAutorisatie instanceof MetaObject) {
            //als onderliggende autorisatie een object is kijken we verder naar beneden of er in onderliggend model niet identiteits rijen
            // bevinden met autorisatie en die in de delta zitten. Dit komt voor bij betrokkenheid. Dit object heeft enkel een
            // identiteitsgroep (met) historie. Deze moet mee in bericht als er onderliggende objecten in bericht komen.
            final BevatNietIdentiteitDelta leverIdentiteit = new BevatNietIdentiteitDelta(berichtgegevens, idRecord);
            onderliggendeAutorisatie.accept(leverIdentiteit);
            if (leverIdentiteit.bevat) {
                return true;
            }
        }
        return false;
    }


    //uitzondering
    //de delta bevat mogelijk geen identificerende gegevens, omdat deze niet geraakt zijn in de mutatie.
    //dit moet hersteld worden, zodat het actuele record zichtbaar blijft.
    private Collection<MetaRecord> bepaalTeBehoudenIdentificerendeRecordsVanHoofdpersoon(final Berichtgegevens berichtgegevens) {
        final Multimap<GroepElement, MetaRecord> groepDeltaMap = HashMultimap.create();
        for (final MetaRecord deltaRecord : berichtgegevens.getDeltaRecords()) {
            if (berichtgegevens.isGeautoriseerd(deltaRecord)) {
                groepDeltaMap.put(deltaRecord.getParentGroep().getGroepElement(), deltaRecord);
            }
        }
        final Set<MetaRecord> teBehoudenRecords = Sets.newHashSet();
        final ModelIndex modelIndex = berichtgegevens.getPersoonslijst().getModelIndex();
        for (final GroepElement groepElement : IDGROEPEN) {
            bepaalTeBehoudenIdentificerendeRecordsVanHoofdpersoon(berichtgegevens, groepDeltaMap, teBehoudenRecords, modelIndex, groepElement);
        }
        return teBehoudenRecords;
    }

    private void bepaalTeBehoudenIdentificerendeRecordsVanHoofdpersoon(final Berichtgegevens berichtgegevens,
                                                                       final Multimap<GroepElement, MetaRecord> groepDeltaMap,
                                                                       final Set<MetaRecord> teBehoudenRecords, final ModelIndex modelIndex,
                                                                       final GroepElement groepElement) {
        if (!groepDeltaMap.containsKey(groepElement)) {
            //geen delta records gevonden voor identificerende groep
            //het actuele record moet nu behouden blijven
            final Set<MetaGroep> groepSet = modelIndex.geefGroepenVanElement(groepElement);
            if (!groepSet.isEmpty()) {
                final MetaGroep groep = Iterables.getOnlyElement(groepSet);
                berichtgegevens.getPersoonslijst().getActueleRecord(groep)
                        .filter(berichtgegevens::isGeautoriseerd).ifPresent(teBehoudenRecords::add);
            }
        }
    }

    /**
     * Indien er (recursief) binnen een betrokkenheid een record geraakt is door de delta, dan wordt de betrokken persoon in zijn geheel getoond)
     * @param berichtgegevens de berichtgegevens
     */
    private Collection<MetaRecord> bepaalTeBehoudenIdentificerendeRecordsVanBetrokkenPersoon(final Berichtgegevens berichtgegevens) {
        final Set<MetaRecord> teBehoudenRecords = Sets.newHashSet();
        final ObjectInBetrokkenheid objectInBetrokkenheid = new ObjectInBetrokkenheid(berichtgegevens);
        final GerelateerdePersoonOphaler gerelateerdePersoonOphaler = new GerelateerdePersoonOphaler(objectInBetrokkenheid.teTonenObjecten);
        for (final MetaObject gerelateerdePersoon : gerelateerdePersoonOphaler.personen) {
            gerelateerdePersoon.accept(new ParentFirstModelVisitor() {
                @Override
                protected void doVisit(final MetaGroep groep) {
                    //actueel van gerelateerde persoon tonen. De blob bevat alleen identificerende gegevens van de betrokken personen
                    berichtgegevens.getPersoonslijst().getActueleRecord(groep)
                            .filter(berichtgegevens::isGeautoriseerd)
                            .filter(metaRecord -> !metaRecord.getParentGroep().getGroepElement().isIdentiteitGroep())
                            .ifPresent(teBehoudenRecords::add);
                }
            });
        }
        return teBehoudenRecords;
    }

    /**
     * Bepaalt of er onderliggend een niet identiteit gegeven geautoriseerd delta.
     */
    private static final class BevatNietIdentiteitDelta extends ParentFirstModelVisitor {

        private final Berichtgegevens berichtgegevens;
        private final MetaRecord idRecord;
        private boolean bevat;

        private BevatNietIdentiteitDelta(final Berichtgegevens berichtgegevens, final MetaRecord idRecord) {
            this.berichtgegevens = berichtgegevens;
            this.idRecord = idRecord;
        }

        @Override
        protected void doVisit(final MetaRecord record) {
            final boolean delta = berichtgegevens.getDeltaRecords().contains(record);
            if (!record.getParentGroep().getGroepElement().isIdentiteitGroep() && !record.equals(idRecord) && delta && berichtgegevens
                    .isGeautoriseerd(record)) {
                bevat = true;
            }
        }
    }

    /**
     * Bepaalt de delta objecten in de betrokkenheden van de hoofdpersoon
     */
    private static final class ObjectInBetrokkenheid extends ParentFirstModelVisitor {

        private final Berichtgegevens berichtgegevens;
        private Set<MetaObject> teTonenObjecten = Sets.newHashSet();

        private ObjectInBetrokkenheid(final Berichtgegevens berichtgegevens) {
            this.berichtgegevens = berichtgegevens;

            final Set<MetaObject> betrokkenheden = berichtgegevens.getPersoonslijst()
                    .getMetaObject().getObjecten().stream()
                    .filter(metaObject -> metaObject.getObjectElement().isVanType(ElementConstants
                            .BETROKKENHEID)).collect(Collectors.toSet());
            betrokkenheden.forEach(this::visit);
        }

        @Override
        protected void doVisit(final MetaRecord record) {
            if (berichtgegevens.getDeltaRecords().contains(record) && berichtgegevens.isGeautoriseerd(record)) {
                teTonenObjecten.add(record.getParentGroep().getParentObject());
            }
        }
    }

    /**
     * Hulpklasse voor het verwijderen van de objectstructuren.
     */
    private static final class ObjectOpruimVisitor extends ChildFirstModelVisitor {

        private final Berichtgegevens berichtgegevens;

        ObjectOpruimVisitor(final Berichtgegevens berichtgegevens) {
            this.berichtgegevens = berichtgegevens;
        }

        @Override
        protected void doVisit(final MetaObject object) {
            //behoud object indien het onderliggende geautoriseerde objecten bevat
            for (MetaObject metaObject : object.getObjecten()) {
                if (berichtgegevens.isGeautoriseerd(metaObject)) {
                    return;
                }
            }
            //behoud object indien het onderliggende geautoriseerde records bevat
            for (final MetaGroep groep : object.getGroepen()) {
                for (final MetaRecord record : groep.getRecords()) {
                    if (berichtgegevens.isGeautoriseerd(record)) {
                        return;
                    }
                }
            }
            //verwijder objecten en onderliggende structuur uit bericht.
            berichtgegevens.verwijderAutorisatie(object);
        }
    }

    /**
     * Haalt de (onderliggende) gerelateerde persoon objecten op van de objecten geraakt door de delta.
     */
    private static final class GerelateerdePersoonOphaler extends ParentFirstModelVisitor {

        private Set<MetaObject> personen = Sets.newHashSet();

        GerelateerdePersoonOphaler(final Set<MetaObject> objectSet) {
            objectSet.forEach(this::visit);
        }

        @Override
        protected void doVisit(final MetaObject object) {
            if (object.getObjectElement().isAliasVan(ElementConstants.PERSOON)) {
                personen.add(object);
            }
        }
    }
}
