/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.Afnemerindicatie;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ModelAfdruk;
import nl.bzk.brp.domain.leveringmodel.ModelIndex;

/**
 * Container object voor de statische persoon gegevens.
 */
public final class Persoonslijst {

    private static final AttribuutElement PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_WAARDE = ElementHelper
            .getAttribuutElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_WAARDE);
    private static final AttribuutElement PERSOON_VERSTREKKINGSBEPERKING_PARTIJCODE
            = ElementHelper.getAttribuutElement(Element.PERSOON_VERSTREKKINGSBEPERKING_PARTIJCODE);
    private final Long afnemerindicatieLockVersie;


    /**
     * Het persoon meta object.
     */
    private MetaObject persoonRootObject;
    /**
     * De geldige afnemerindicaties van de persoon.
     */
    private Set<Afnemerindicatie> geldendeAfnemerindicaties;
    /**
     * Map met de gesorteerde administratieveHandelingen van de persoon.
     * De laatste administratieveHandeling representeert de laatste formele
     * vastlegging (tot nu) indien deze {@link Persoonslijst} gelijk is aan
     * het NuNu beeld.
     */
    private List<AdministratieveHandeling> administratievehandelingen;

    /**
     * De index voor snelle lookups in het persoon meta object.
     */
    private ModelIndex modelIndex;
    /**
     * De onderzoek index voor snelle lookups in het persoon meta object mbt onderzoekgegevens.
     */
    private OnderzoekIndex onderzoekIndex;
    /**
     * Het beeld op het moment van de laatste formele vastlegging (het NuNu beeld).
     */
    private Persoonslijst nuNuBeeld;
    /**
     * De bepaling voor actuele records.
     */
    private ActueelBepaling actueelBepaling;
    private PersoonslijstBeeldFactory persoonslijstBeeldFactory;

    /**
     * Constructor voor het maken van het NuNu beeld.
     * @param persoon het metaobject van de persoon
     * @param afnemerindicatieLockVersie afnemerindicatieLockVersie
     */
    public Persoonslijst(final MetaObject persoon, Long afnemerindicatieLockVersie) {
        PersoonMetaObjectValidator.valideer(persoon);
        this.nuNuBeeld = this;
        this.afnemerindicatieLockVersie = afnemerindicatieLockVersie;
        initPersoon(persoon);
    }

    /**
     * Interne constructor voor het maken van een {@link Persoonslijst}.
     * @param metaObject een persoon metaobject.
     * @param nuNuBeeld het NuNu beeld
     */
    Persoonslijst(final MetaObject metaObject, final Persoonslijst nuNuBeeld, Long afnemerindicatieLockVersie) {
        this.nuNuBeeld = nuNuBeeld;
        this.afnemerindicatieLockVersie = afnemerindicatieLockVersie;
        initPersoon(metaObject);
    }

    private void initPersoon(final MetaObject persoon) {
        this.persoonRootObject = persoon;
        modelIndex = new ModelIndex(persoon);
        onderzoekIndex = new OnderzoekIndex(modelIndex);

        final List<AdministratieveHandeling> tempHandelingList =
                Lists.newArrayList(AdministratievehandelingFilter.bepaalHandelingenOpHoofdPersoon(persoon));
        tempHandelingList.sort(AdministratieveHandeling.comparator());
        this.administratievehandelingen = ImmutableList.copyOf(tempHandelingList);
        leidAfnemerindicatiesAfVanModel();
        this.actueelBepaling = new ActueelBepaling(persoonRootObject);
        this.persoonslijstBeeldFactory = new PersoonslijstBeeldFactory(this);
    }

    /**
     * Het NuNu beeld geeft de situatie weer van de {@link Persoonslijst} na
     * de laatste {@link AdministratieveHandeling handeling}.
     * @return indicatie of deze {@link Persoonslijst} het NuNu beeld is.
     */
    boolean isNuNuBeeld() {
        return this == nuNuBeeld;
    }

    /**
     * @return het NuNu beeld.
     */
    public Persoonslijst getNuNuBeeld() {
        return nuNuBeeld;
    }

    /**
     * @return de lock versie van de afnemerindicatie lock versie
     */
    public Long getAfnemerindicatieLockVersie() {
        return afnemerindicatieLockVersie;
    }

    /**
     * @return lock versie van persoon
     */
    public Long getPersoonLockVersie() {
        final Set<MetaAttribuut> attributen = getModelIndex().geefAttributen(Element.PERSOON_VERSIE_LOCK);
        if (attributen == null || attributen.size() != 1) {
            throw new UnsupportedOperationException("Persoon lockversie in blob kon niet worden bepaald");
        }
        final MetaAttribuut lockVersieAttribuut = Iterables.getOnlyElement(attributen);
        return lockVersieAttribuut.getWaarde();
    }

    /**
     * Bepaalt het tijdstip van laatste wijziging.
     * @return tijdstip laatste wijziging
     */
    public ZonedDateTime bepaalTijdstipLaatsteWijziging() {
        return this.<ZonedDateTime>getActueleAttribuutWaarde(ElementHelper.getAttribuutElement(
                Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGING.getId())).orElse(null);
    }

    /**
     * Bepaalt het tijdstip van laatste wijziging GBA-systematiek.
     * @return tijdstip laatste wijziging
     */
    public ZonedDateTime bepaalTijdstipLaatsteWijzigingGBASystematiek() {
        return this.<ZonedDateTime>getActueleAttribuutWaarde(ElementHelper.getAttribuutElement(
                Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGINGGBASYSTEMATIEK.getId())).orElse(null);
    }

    /**
     * Geeft een factory voor maken van nieuwe beelden op basis van dit beeld.
     * @return een {@link PersoonslijstBeeldFactory}
     */
    public PersoonslijstBeeldFactory beeldVan() {
        return persoonslijstBeeldFactory;
    }

    /**
     * @return het root persoon object.
     */
    public MetaObject getMetaObject() {
        return persoonRootObject;
    }

    /**
     * Geeft een lijst met {@link AdministratieveHandeling}en. De lijst
     * is gesorteerd middels {@link AdministratieveHandeling#comparator()}.
     * De laatste handeling in de lijst is formeel de laatste.
     * @return map met administratieve handelingen van de persoon
     */
    public List<AdministratieveHandeling> getAdministratieveHandelingen() {
        return administratievehandelingen;
    }

    /**
     * Geeft de laatste administratievehandeling.
     * @return een {@link AdministratieveHandeling}
     */
    public AdministratieveHandeling getAdministratieveHandeling() {
        return administratievehandelingen.get(administratievehandelingen.size() - 1);
    }

    /**
     * Geeft de geldende (niet vervallen) afnemerindicaties op de persoon.
     * @return de geldige afnemerindicaties op de persoon
     */
    public Set<Afnemerindicatie> getGeldendeAfnemerindicaties() {
        return this.geldendeAfnemerindicaties;
    }

    /**
     * @return indicatie of voor de persoon een volledige verstrekkingsbeperking geldt
     */
    public boolean heeftIndicatieVolledigeVerstrekkingsbeperking() {
        return this.<Boolean>getActueleAttribuutWaarde(PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_WAARDE)
                .filter(waarde -> waarde == Boolean.TRUE)
                .orElse(Boolean.FALSE);
    }

    /**
     * @return een set met partijcodes waarvoor een verstrekkingsbeperking geldt.
     */
    public Set<String> getVerstrekkingsbeperkingen() {
        final Set<String> partijCodes = new HashSet<>();
        final Collection<MetaAttribuut> attributen = modelIndex
                .geefAttributen(PERSOON_VERSTREKKINGSBEPERKING_PARTIJCODE);
        for (final MetaAttribuut attribuut : attributen) {
            if (isActueel(attribuut.getParentRecord())) {
                partijCodes.add(attribuut.getWaarde());
            }
        }
        return partijCodes;
    }

    /**
     * Zoekt de geplaatste afnemerindicatie op een persoon, die is geplaatst als gevolg van de gegeven administratieve handeling.
     * @param leveringAutorisatieId de leveringsautorisatie waarvoor we de afnemerindicatie zoeken
     * @return de meegegeven datum aanvang materiele periode, of null
     */
    public Integer getDatumAanvangMaterielePeriodeVanAfnemerindicatie(final int leveringAutorisatieId) {
        for (final Afnemerindicatie afnemerindicatie : getGeldendeAfnemerindicaties()) {
            if (afnemerindicatie.getLeveringsAutorisatieId() == leveringAutorisatieId) {
                return afnemerindicatie.getDatumAanvangMaterielePeriode();
            }
        }
        return null;
    }

    /**
     * @return de modelindex
     */
    public ModelIndex getModelIndex() {
        return modelIndex;
    }

    /**
     * Geef de actuele attribuut waarde van een persoonslijst of null indien
     * er geen actuele waarde bestaat.
     * @param attribuutElement attribuut
     * @param <T> type attribuut waarde
     * @return de waarde
     */
    public <T> Optional<T> getActueleAttribuutWaarde(final AttribuutElement attribuutElement) {
        return getModelIndex().geefAttributen(attribuutElement).stream()
                .filter(attribuut -> isActueel(attribuut.getParentRecord()))
                .findFirst()
                .map(MetaAttribuut::getWaarde);
    }

    /**
     * @return de onderzoekindex
     */
    public OnderzoekIndex getOnderzoekIndex() {
        return onderzoekIndex;
    }

    /**
     * @param record het record
     * @return of het record actueel is
     */
    public boolean isActueel(final MetaRecord record) {
        return actueelBepaling.getActueleRecords().contains(record);
    }

    /**
     * @param metaGroep de metagroep
     * @return het actuele metarecord
     */
    public Optional<MetaRecord> getActueleRecord(final MetaGroep metaGroep) {
        return Optional.ofNullable(actueelBepaling.getActueleRecordsPerGroep().get(metaGroep));
    }

    /**
     * Geeft het laatste materiele historierecord van de gegeven groep.
     * @param metaGroep de metagroep
     * @return een record, of null indien er geen laatste historierecord bestaat
     */
    public Optional<MetaRecord> getMaterieelLaatsteHistorieRecord(final MetaGroep metaGroep) {
        return metaGroep.getRecords().stream()
                .filter(metaRecord -> metaRecord.getDatumEindeGeldigheid() != null)
                //sorteer de beeindigde records van nieuw naar oud
                .sorted((o1, o2) -> o2.getDatumEindeGeldigheid() - o1.getDatumEindeGeldigheid()).findFirst();
    }

    /**
     * @return technisch id van de persoon.
     */
    public Long getId() {
        return persoonRootObject.getObjectsleutel();
    }

    @Override
    public String toString() {
        return String.valueOf(persoonRootObject.getObjectsleutel());
    }

    /**
     * @return een String representatie van het volledige beeld van de persoon en zijn administratievehandelingen.
     */
    public String toStringVolledig() {
        final StringBuilder sb = new StringBuilder();
        sb.append(ModelAfdruk.maakAfdruk(this.persoonRootObject));
        for (AdministratieveHandeling administratieveHandeling : administratievehandelingen) {
            sb.append(System.lineSeparator());
            sb.append(ModelAfdruk.maakAfdruk(administratieveHandeling.getMetaObject()));
        }
        return sb.toString();
    }

    private void leidAfnemerindicatiesAfVanModel() {
        final Set<MetaObject> metaAfnemerindicaties = modelIndex.geefObjecten(Element.PERSOON_AFNEMERINDICATIE);
        final Set<Afnemerindicatie> allAfnemerindicaties = AfnemerindicatieConverter.converteerNaarAfnemerIndicaties(
                metaAfnemerindicaties);
        final Set<Afnemerindicatie> allGeldigeAfnemerindicaties = new HashSet<>();
        for (final Afnemerindicatie next : allAfnemerindicaties) {
            if (next.getTijdstipVerval() == null) {
                allGeldigeAfnemerindicaties.add(next);
            }
        }
        this.geldendeAfnemerindicaties = Collections.unmodifiableSet(allGeldigeAfnemerindicaties);
    }
}
