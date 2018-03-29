/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.persoon.OnderzoekIndex;
import nl.bzk.brp.domain.leveringmodel.persoon.Onderzoekbundel;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * De stap draagt zorg voor het correct leveren van onderzoeksgegevens.
 * Onderzoeken worden geleverd voor alle diensten, voor volledig en
 * mutatieberichten gelden echter specifieke uitzonderingen.
 * <p>
 * Voor onderzoeken spelen de volgende objecten een rol:
 * <ul>
 * <li>ElementInOnderzoek
 * <p>Een element of set elementen dat het onderzoek aanwijst
 * {@link OnderzoekIndex#gegevensInOnderzoek})
 * Indien er geen autorisatie bestaat voor dit element kan het onderzoek ook niet getoond worden.</li>
 * <li>GegevenInOnderzoek
 * <p>Een {@link MetaObject} dat het GegevenInOnderzoek modelleert, geindexeerd
 * door {@link OnderzoekIndex OnderzoekIndex}
 * en te benaderen is via {@link Persoonslijst}.
 * </li>
 * <li>Onderzoek
 * <p>Een MetaObject dat het onderzoek modelleert, dit onderzoek kan meerdere
 * GegevenInOnderzoek objecten bevatten. Het onderzoek heeft een einddatum
 * (LET OP: géén materieel attribuut), wat zn effect heeft op de levering
 * van onderzoeken in volledigberichten.
 * </li>
 * <li>PersoonOnderzoek
 * <p>MetaObject voor PersoonOnderzoek, dit fungeert nu enkel als kapstok object en
 * komt mogelijk te vervallen. Dit object is niet zichtbaar in berichten.
 * </li>
 * </ul>
 * <p>
 * De algemene flow voor het correct leveren van onderzoeken is:
 * <ul>
 * <li>Bepaal de set geautoriseerde GegevenInOnderzoek objecten.</li>
 * <li>Filter GegevenInOnderzoek objecten die naar een gegeven wijzen dat niet geautoriseerd is. </li>
 * <li>Filter GegevenInOnderzoek objecten die wijzen naar een ontbrekend gegeven (muv van bijhouders)</li>
 * <li>Filter GegevenInOnderzoek objecten die deel uit maken van een onderzoek dat beeindigd is</li>
 * <li>Filter GegevenInOnderzoek objecten die deel uit maken van een onderzoek dat geen geautoriseerde records meer bevat (weggefilterd door
 * recordfiltering)
 * </li>
 * <li>Verwijder alle GegevenInOnderzoek objecten welke niet geleverd worden</li>
 * <li>Verwijder alle Onderzoek objecten welke niet geleverd worden</li>
 * <li>Verwijder alle PersoonOnderzoek objecten welke niet geleverd worden</li>
 * </ul>
 * </p>
 * <p> Voor mutatieberichten moet er mogelijk nog gecorrigeerd worden voor geautoriseerde gegevens die in onderzoek
 * staan, maar niet in de delta zitten. Autorisatie van gegevens niet in de delta zitten zullen uiteindelijk allemaal
 * ingetrokken worden (muv identificerende gegevens, zie {@link MutatieleveringBehoudIdentificerendeGegevensServiceImpl}.
 * De delta moet om deze reden gecorrigeerd worden, door het aan te vullen met records.
 */
@Component
@Bedrijfsregel(Regel.R2412)
@Bedrijfsregel(Regel.R2065)
@Bedrijfsregel(Regel.R2063)
@Bedrijfsregel(Regel.R1561)
@Bedrijfsregel(Regel.R1562)
@Bedrijfsregel(Regel.R1544)
class AutoriseerOnderzoekServiceImpl implements MaakBerichtStap {

    private static final AttribuutElement ONDERZOEK_DATUMEINDE = ElementHelper.getAttribuutElement(Element.ONDERZOEK_DATUMEINDE);
    private static final ObjectElement ONDERZOEK = ElementHelper.getObjectElement(Element.ONDERZOEK);

    @Override
    public void execute(final Berichtgegevens berichtgegevens) {
        final OnderzoekData data = new OnderzoekData(berichtgegevens);

        //Bepaal de te leveren gegeven in onderzoeken
        final List<Onderzoekbundel> teLeverenOnderzoekbundel = bepaalTeLeverenGegevenInOnderzoekObjecten(data);

        //Mogelijk zijn er teveel records weggefilterd waarop wel autorisatie zit
        correctLeverenGegevenInOnderzoek(teLeverenOnderzoekbundel, data);
    }

    private List<Onderzoekbundel> bepaalTeLeverenGegevenInOnderzoekObjecten(final OnderzoekData data) {
        //selecteer de geautoriseerde GegevenInOnderzoek objecten
        final List<MetaObject> gegevenInOnderzoekLijst = Lists.newArrayList(
                data.berichtgegevens.getGeautoriseerdeObjecten(ElementHelper.getObjectElement(Element.GEGEVENINONDERZOEK)));

        final List<Onderzoekbundel> alleGegevensInOnderzoek = data.persoonslijst.getOnderzoekIndex().getAlleGegevensInOnderzoek();
        final Map<MetaObject, Onderzoekbundel> gegevenInOnderzoekMap = Maps.newHashMap();
        for (Onderzoekbundel inOnderzoek : alleGegevensInOnderzoek) {
            gegevenInOnderzoekMap.put(inOnderzoek.getGegevenInOnderzoek(), inOnderzoek);
        }
        final List<Onderzoekbundel> returnlist = Lists.newLinkedList();
        for (MetaObject gegevenInOnderzoekMetaObject : gegevenInOnderzoekLijst) {
            final Onderzoekbundel onderzoekbundel = gegevenInOnderzoekMap.get(gegevenInOnderzoekMetaObject);
            if (moetGegevenInOnderzoekGeleverdWorden(onderzoekbundel, data)) {
                returnlist.add(onderzoekbundel);
            }
        }
        return returnlist;
    }

    /**
     * Kijk of gegeven in onderzoek niet wijst naar een rij die is weggefilterd door predikaten.
     */
    private boolean verwijstGegevenInOnderzoekNaarGeautoriseerdGegegeven(final Collection<MetaModel> gegevens, final OnderzoekData onderzoekData) {
        for (final MetaModel gegeven : gegevens) {
            if (onderzoekData.berichtgegevens.isGeautoriseerd(gegeven)) {
                return true;
            }
        }
        return false;
    }

    private boolean moetGegevenInOnderzoekGeleverdWorden(final Onderzoekbundel onderzoekbundel, final OnderzoekData data) {
        final boolean leveren;
        final boolean isNietGeautoriseerdBeeindigdOnderzoek = data.nietGeautoriseerdeBeeindigdeOnderzoeken.contains(onderzoekbundel.getOnderzoek());
        if (!onderzoekHeeftOnderliggendeAutorisaties(onderzoekbundel, data) || isNietGeautoriseerdBeeindigdOnderzoek) {
            leveren = false;
        } else if (onderzoekbundel.isOntbrekend()) {
            leveren = leverenOntbrekendGegevenInOnderzoek(data);
        } else {
            leveren = verwijstGegevenInOnderzoekNaarGeautoriseerdGegegeven(data.gegevensInOnderzoek.get(onderzoekbundel), data);
        }
        return leveren;
    }

    private boolean onderzoekHeeftOnderliggendeAutorisaties(final Onderzoekbundel onderzoekbundel, final OnderzoekData data) {
        //controleer bovenliggend Onderzoek object. Het bovenliggende onderzoek moet niet weggefilterd zijn (dwz geen records meer in standaard groep)
        boolean onderzoekHeeftOnderliggendeAutorisaties = true;
        final Collection<MetaModel> geautoriseerdeOnderzoekRecords =
                data.berichtgegevens.getOnderliggendeAutorisaties(onderzoekbundel.getOnderzoek().getGroep(Element.ONDERZOEK_STANDAARD));
        if (geautoriseerdeOnderzoekRecords.isEmpty()) {
            onderzoekHeeftOnderliggendeAutorisaties = false;
        }
        return onderzoekHeeftOnderliggendeAutorisaties;
    }

    private boolean gegevenInOnderzoekZitInDelta(final Onderzoekbundel onderzoekbundel, final OnderzoekData data, final Set<MetaRecord> origineleDelta) {
        //bepaal v mutatieberichten of onderzoek in delta zit
        if (!data.berichtgegevens.isVolledigBericht()) {
            final Collection<MetaModel> records
                    = data.berichtgegevens.getOnderliggendeAutorisaties(onderzoekbundel.getOnderzoek()
                    .getGroep(Element.ONDERZOEK_STANDAARD));
            for (MetaModel recordVanStandaard : records) {
                if (recordVanStandaard instanceof MetaRecord && origineleDelta.contains(recordVanStandaard)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Onderzoeken naar ontbrekende gegevens worden niet aan afnemers geleverd, wel aan bijhouders indien dienst NIET zoek persoon/zoek persoon op adres/
     * geef medebewoners (oftewel diensten die geen complete PL leveren, R2412).
     */
    @Bedrijfsregel(Regel.R2412)
    private boolean leverenOntbrekendGegevenInOnderzoek(final OnderzoekData data) {
        final boolean dienstVereistLeverenOntbrekendGegeven = !Sets.newHashSet(
                SoortDienst.GEEF_MEDEBEWONERS_VAN_PERSOON,
                SoortDienst.ZOEK_PERSOON,
                SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS).contains(data.berichtgegevens.getParameters().getSoortDienst());
        return data.bijhouder && dienstVereistLeverenOntbrekendGegeven;
    }

    private void correctLeverenGegevenInOnderzoek(final List<Onderzoekbundel> teLeverenOnderzoekbundel, final OnderzoekData data) {
        //maak set met te verwijderen gegevens in onderzoek: staan wel op PL, maar zijn niet gemarkeerd voor levering
        final Set<Onderzoekbundel> teVerwijderenGegevensInOnderzoek = Sets.newHashSet(data.persoonslijst.getOnderzoekIndex().getAlleGegevensInOnderzoek());
        teVerwijderenGegevensInOnderzoek.removeAll(teLeverenOnderzoekbundel);

        for (final Onderzoekbundel onderzoekbundel : teVerwijderenGegevensInOnderzoek) {
            //verwijder autorisatie gegeven in onderzoek
            data.berichtgegevens.verwijderAutorisatie(onderzoekbundel.getGegevenInOnderzoek());

            //indien het onderzoek geen onderliggende GegevenInOnderzoek objecten bevat, verwijder dan ook het onderzoek
            final Collection<MetaModel> onderliggendeAutorisaties =
                    data.berichtgegevens.getOnderliggendeAutorisaties(onderzoekbundel.getOnderzoek());
            boolean heeftKindObject = false;
            for (final MetaModel kindModel : onderliggendeAutorisaties) {
                if (kindModel instanceof MetaObject) {
                    heeftKindObject = true;
                    break;
                }
            }
            if (!heeftKindObject) {
                data.berichtgegevens.verwijderAutorisatie(onderzoekbundel.getOnderzoek());
            }
        }

        if (data.berichtgegevens.isMutatiebericht()) {
            corrigeerVoorMutatielevering(teLeverenOnderzoekbundel, data);
        }
    }

    private void corrigeerVoorMutatielevering(final List<Onderzoekbundel> teLeverenOnderzoekbundels, final OnderzoekData data) {
        final Set<MetaRecord> origineleDelta = Sets.newHashSet(data.berichtgegevens.getDeltaRecords());
        final Collection<MetaObject> gewijzigdeBetrokkenheden = bepaalBetrokkenhedenInDelta(data.berichtgegevens);

        for (Onderzoekbundel onderzoekbundel : teLeverenOnderzoekbundels) {
            //als de mutatie enkel het onderzoek geraakt heeft, maar niet het eigenlijke gegeven dat aangewezen wordt
            //dan moet dat gegeven alsnog toegevoegd worden aan de delta. Dit voorkomt dat het onderzoek wel getoond
            //wordt maar het gegeven niet.
            final Collection<MetaModel> metaModels = data.gegevensInOnderzoek.get(onderzoekbundel);
            final Set<MetaRecord> records = geefRecordsVanOnderzoekgegeven(metaModels);
            records.retainAll(data.berichtgegevens.getGeautoriseerdeRecords());
            for (final MetaRecord record : records) {
                //toon onderzoeken op identificerende gegevens (die niet in de delta zitten) van een betrokkenheid
                //alleen als *iets* van de betrokkenheid gewijzigd is.
                if (gegevenInOnderzoekZitInDelta(onderzoekbundel, data, origineleDelta) ||
                        origineleDelta.contains(record) || bepaalTonenIdentificerendGegeven(gewijzigdeBetrokkenheden, record)) {
                    voegOnderzoekToeAanDelta(onderzoekbundel, data);
                    //behoud de geautoriseerde records die in delta zitten of in identificerende groep zitten, door ze aan de set deltarecords toe te voegen
                    data.berichtgegevens.addDeltaRecord(record);
                }
            }
        }
    }

    private boolean bepaalTonenIdentificerendGegeven(Collection<MetaObject> betrokkenhedenInDelta, MetaRecord recordVanOnderzoek) {
        if (recordVanOnderzoek.getParentGroep().getGroepElement().isIdentificerend()) {
            final ObjectElement betrokkenheidElement = ElementHelper.getObjectElement(Element.BETROKKENHEID);
            MetaModel parent = recordVanOnderzoek.getParent();
            boolean isRecordVanBetrokkenheid = false;
            while ((parent = parent.getParent()) != null) {
                if (parent instanceof MetaObject && ((MetaObject) parent).getObjectElement().isVanType(betrokkenheidElement)) {
                    isRecordVanBetrokkenheid = true;
                    break;
                }
            }
            return !isRecordVanBetrokkenheid || betrokkenhedenInDelta.contains(parent);
        }
        return false;
    }

    private Collection<MetaObject> bepaalBetrokkenhedenInDelta(Berichtgegevens berichtgegevens) {

        final Set<MetaRecord> deltaRecords = berichtgegevens.getDeltaRecords();
        final Set<MetaObject> deltaBetrokkenheden = Sets.newHashSet();
        final ObjectElement betrokkenheidElement = ElementHelper.getObjectElement(Element.BETROKKENHEID);
        for (MetaRecord deltaRecord : deltaRecords) {
            MetaModel parent = deltaRecord.getParent();
            while ((parent = parent.getParent()) != null) {
                if (parent instanceof MetaObject && ((MetaObject) parent).getObjectElement().isVanType(betrokkenheidElement)) {
                    deltaBetrokkenheden.add((MetaObject) parent);
                    break;
                }
            }
        }
        return deltaBetrokkenheden;
    }


    private void voegOnderzoekToeAanDelta(final Onderzoekbundel onderzoekbundel, final OnderzoekData data) {
        //voor te leveren onderzoeksgegevens : voeg g.i.o. records & onderzoek standaard records toe aan delta
        for (MetaGroep groep : onderzoekbundel.getGegevenInOnderzoek().getGroepen()) {
            for (MetaRecord record : groep.getRecords()) {
                data.berichtgegevens.addDeltaRecord(record);
            }
        }
        final MetaObject onderzoek = onderzoekbundel.getOnderzoek();
        final MetaGroep groep = onderzoek.getGroep(Element.ONDERZOEK_STANDAARD);
        for (MetaRecord record : groep.getRecords()) {
            if (data.bijhouder || data.persoonslijst.isActueel(record)) {
                data.berichtgegevens.addDeltaRecord(record);
            }
        }
    }

    private Set<MetaRecord> geefRecordsVanOnderzoekgegeven(final Collection<MetaModel> metaModels) {
        final Set<MetaRecord> records = Sets.newHashSet();
        for (MetaModel metaModel : metaModels) {
            if (metaModel instanceof MetaAttribuut) {
                records.add(((MetaAttribuut) metaModel).getParentRecord());
            } else if (metaModel instanceof MetaRecord) {
                records.add((MetaRecord) metaModel);
            } else if (metaModel instanceof MetaGroep) {
                records.addAll(((MetaGroep) metaModel).getRecords());
            } else if (metaModel instanceof MetaObject) {
                for (MetaGroep model : ((MetaObject) metaModel).getGroepen()) {
                    records.addAll(model.getRecords());
                }
            }
        }
        return records;
    }

    /**
     * Statefull hulpklasse voor het autoriseren van onderzoeken.
     */
    private static final class OnderzoekData {

        private final Berichtgegevens berichtgegevens;
        private final Persoonslijst persoonslijst;
        private final boolean bijhouder;
        private final Multimap<Onderzoekbundel, MetaModel> gegevensInOnderzoek;
        private final Set<MetaObject> alleOnderzoeken;
        private final Set<MetaObject> nietGeautoriseerdeBeeindigdeOnderzoeken;

        /**
         * Constructor.
         * @param berichtgegevens de berichtgegevens
         */
        OnderzoekData(final Berichtgegevens berichtgegevens) {
            this.berichtgegevens = berichtgegevens;
            persoonslijst = berichtgegevens.getPersoonslijst();
            gegevensInOnderzoek = persoonslijst.getOnderzoekIndex().getGegevensInOnderzoek();
            alleOnderzoeken = persoonslijst.getModelIndex().geefObjecten(ONDERZOEK);
            nietGeautoriseerdeBeeindigdeOnderzoeken = bepaalNietGeautoriseerdeBeeindigdeOnderzoeken();
            final Autorisatiebundel autorisatiebundel = berichtgegevens.getAutorisatiebundel();
            final ToegangLeveringsAutorisatie toegangLeveringsautorisatie = autorisatiebundel.getToegangLeveringsautorisatie();
            this.bijhouder = toegangLeveringsautorisatie.getGeautoriseerde().getRol() != Rol.AFNEMER;
        }

        /**
         * Bepaalt de beeindigde onderzoeken die niet-geautoriseerd zijn v levering tav Datum Aanvang Materieel uit de afnemerindicatie.
         * (R1544) : Voor volledige berichten en mutatieberichten wordt een beeindigd onderzoek enkel getoond als de datum aanvang materiele periode vóór
         * DatumEinde van het onderzoek ligt.
         */
        private Set<MetaObject> bepaalNietGeautoriseerdeBeeindigdeOnderzoeken() {
            final Set<MetaObject> tempBeeindigdeOnderzoeken = Sets.newHashSet();
            for (final MetaObject onderzoek : alleOnderzoeken) {
                final MetaGroep groep = onderzoek.getGroep(Element.ONDERZOEK_STANDAARD);
                Assert.notNull(groep, "Onderzoek.Standaard groep mag niet null zijn.");
                MetaRecord actueleRecord = null;
                for (final MetaRecord metaRecord : groep.getRecords()) {
                    if (persoonslijst.isActueel(metaRecord)) {
                        actueleRecord = metaRecord;
                    }
                }

                if (actueleRecord != null
                        && actueleRecord.getAttribuut(Element.ONDERZOEK_DATUMEINDE) != null
                        && berichtgegevens.getDatumAanvangMaterielePeriode() != null) {
                    // Als er geleverd wordt voor een materiele datum vanaf,
                    // dan moeten we enkel de onderzoeken leveren welke niet beeindigd zijn voor of op deze datum
                    // LET OP: ONDERZOEK_DATUMEINDE is geen materieel of formeel attribuut, de recordfiltering doet
                    // hier niets mee en filtert dit mogelijk niet weg. Er moet hier dus expliciet gecontroleerd
                    // worden of het onderzoek nog geldig is.
                    final Integer datumEinde = actueleRecord.getAttribuut(ONDERZOEK_DATUMEINDE).<Integer>getWaarde();
                    final boolean onderzoekGeldigOpPeilmoment = DatumUtil
                            .valtDatumBinnenPeriode(berichtgegevens.getDatumAanvangMaterielePeriode(), null, datumEinde);
                    if (!onderzoekGeldigOpPeilmoment) {
                        tempBeeindigdeOnderzoeken.add(onderzoek);
                    }
                }
            }
            return Collections.unmodifiableSet(tempBeeindigdeOnderzoeken);
        }

    }
}
