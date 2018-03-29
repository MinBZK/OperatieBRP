/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.algemeen;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingssoort;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;

/**
 * Dit object bevat alle persoon state die nodig is om een leverbericht op te stellen. Dit object dient na gebruik weggegooid te worden.
 */
public final class Berichtgegevens {

    private final MaakBerichtPersoonInformatie maakBerichtPersoon;
    private final Autorisatiebundel autorisatiebundel;

    private final StatischePersoongegevens statischePersoongegevens;
    private Persoonslijst persoonslijst;
    private MaakBerichtHistorieFilterInformatie historieFilterInformatie;

    /**
     * De geautoriseerde metamodel objecten.
     */
    private Multimap<MetaModel, MetaModel> berichtmodel = HashMultimap.create();
    /**
     * Set van acties welke geautoriseerd zijn om te leveren.
     */
    private Set<Actie> geautoriseerdeActies = Sets.newHashSet();
    private Map<MetaObject, String> persoonVersleuteldeObjectSleutelMap = Maps.newHashMap();
    private Set<AdministratieveHandeling> geautoriseerdeHandelingen;
    private boolean leegBericht;
    private MaakBerichtParameters parameters;
    private Set<MetaRecord> kandidaatRecords;
    private boolean geautoriseerdVoorGedeblokkeerdeMeldingen;
    private boolean mutatieberichtMetMeldingVerstrekkingsbeperking;


    /**
     * Constructor.
     * @param maakBerichtParameters de parameters
     * @param persoonslijst persoonslijst
     * @param maakBerichtPersoon maakBerichtPersoon
     * @param autorisatiebundel autorisatiebundel
     * @param statischePersoongegevens statischePersoonGegevens
     */
    public Berichtgegevens(final MaakBerichtParameters maakBerichtParameters, final Persoonslijst persoonslijst,
                           final MaakBerichtPersoonInformatie maakBerichtPersoon, final Autorisatiebundel autorisatiebundel,
                           final StatischePersoongegevens statischePersoongegevens) {
        this.parameters = maakBerichtParameters;
        this.persoonslijst = persoonslijst;
        this.maakBerichtPersoon = maakBerichtPersoon;
        this.autorisatiebundel = autorisatiebundel;
        this.statischePersoongegevens = statischePersoongegevens;
    }

    public MaakBerichtParameters getParameters() {
        return parameters;
    }

    public boolean isMutatiebericht() {
        return maakBerichtPersoon.getSoortSynchronisatie() == SoortSynchronisatie.MUTATIE_BERICHT;
    }

    public boolean isVolledigBericht() {
        return !isMutatiebericht();
    }

    public boolean isGeautoriseerdVoorGedeblokkeerdeMeldingen() {
        return geautoriseerdVoorGedeblokkeerdeMeldingen;
    }

    public void setGeautoriseerdVoorGedeblokkeerdeMeldingen(final boolean geautoriseerdVoorGedeblokkeerdeMeldingen) {
        this.geautoriseerdVoorGedeblokkeerdeMeldingen = geautoriseerdVoorGedeblokkeerdeMeldingen;
    }

    /**
     * @return persoongegevens.
     */
    public Persoonslijst getPersoonslijst() {
        return persoonslijst;
    }

    /**
     * Autoriseer actie.
     * @param actie actie
     */
    public void autoriseerActie(final Actie actie) {
        geautoriseerdeActies.add(actie);
    }

    public Set<Actie> getGeautoriseerdeActies() {
        return geautoriseerdeActies;
    }

    /**
     * Zet de geautoriseerde acties.
     * @param geautoriseerdeActies de geautoriseerde acties
     */
    public void setGeautoriseerdeActies(final Set<Actie> geautoriseerdeActies) {
        this.geautoriseerdeActies = geautoriseerdeActies;
    }

    /**
     * @param metaRecord metaRecord
     */
    public void addDeltaRecord(final MetaRecord metaRecord) {
        this.statischePersoongegevens.getDeltaRecords().add(metaRecord);
    }

    public Set<MetaRecord> getDeltaRecords() {
        return this.statischePersoongegevens.getDeltaRecords();
    }

    /**
     * Geeft een {@link Verwerkingssoort} terug voor een gegeven {@link MetaObject}.
     * @param metaObject het {@link MetaObject}
     * @return de gevonden {@link Verwerkingssoort} of null indien niet gevonden
     */
    public Verwerkingssoort getObjectVerwerkingssoort(final MetaObject metaObject) {
        final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = this.statischePersoongegevens.getVerwerkingssoortMap();
        if (verwerkingssoortMap != null) {
            return verwerkingssoortMap.get(metaObject);
        }
        return null;
    }

    /**
     * Geeft de verwerkingssoort van een record.
     * @param metaRecord een record
     * @return de verwerkingsoort, of null
     */
    public Verwerkingssoort getVerwerkingssoort(final MetaRecord metaRecord) {
        final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = this.statischePersoongegevens.getVerwerkingssoortMap();
        if (verwerkingssoortMap != null) {
            return verwerkingssoortMap.get(metaRecord);
        }
        return null;
    }

    /**
     * Haal de geautoriseerde handelingen op.
     * @return de geautoriseerde handelingen
     */
    public Set<AdministratieveHandeling> getGeautoriseerdeHandelingen() {
        return geautoriseerdeHandelingen;
    }

    /**
     * Zet de geautoriseerde handelingen.
     * @param geautoriseerdeHandelingen de geautoriseerde handelingen
     */
    public void setGeautoriseerdeHandelingen(final Set<AdministratieveHandeling> geautoriseerdeHandelingen) {
        this.geautoriseerdeHandelingen = geautoriseerdeHandelingen;
    }

    /**
     * Autoriseert het model object.
     * @param model het model object om te autoriseren
     */
    public void autoriseer(final MetaModel model) {
        berichtmodel.put(model, null);
        if (model.getParent() != null) {
            autoriseerParent(model.getParent(), model);
        }
    }

    private void autoriseerParent(final MetaModel model, final MetaModel ref) {
        if (model == null) {
            return;
        }
        berichtmodel.put(model, ref);
        autoriseerParent(model.getParent(), model);
    }

    /**
     * Verwijdert de autorisatie van een gegeven object.
     * @param model een model object.
     */
    public void verwijderAutorisatie(final MetaModel model) {

        if (model == null) {
            return;
        }

        final Collection<MetaModel> metaModels = Lists.newArrayList(berichtmodel.get(model));
        for (MetaModel metaModel : metaModels) {
            verwijderAutorisatie(metaModel);
        }
        //voeg null entry toe voor de parent om deze te bouden
        //als parent namelijk 1 kind bevat en deze verwijderd wordt,
        //dan vervalt het hele key/value paar
        if (model.getParent() != null && berichtmodel.containsKey(model.getParent())) {
            berichtmodel.put(model.getParent(), null);
        }
        berichtmodel.remove(model.getParent(), model);
        berichtmodel.removeAll(model);
    }

    /**
     * @param model het model object
     * @return indicatie of het model object geautoriseerd is
     */
    public boolean isGeautoriseerd(final MetaModel model) {
        return berichtmodel.containsKey(model);
    }

    /**
     * Geeft de onderliggend geautoriseerde MetaModel objecten waarvan bepaald is dat ze in het bericht komen.
     * @param metaModel een metamodel.
     * @return een lijst met onderliggende MetaModel instanties.
     */
    public Collection<MetaModel> getOnderliggendeAutorisaties(final MetaModel metaModel) {
        if (!berichtmodel.containsKey(metaModel)) {
            return Collections.emptySet();
        }
        final Collection<MetaModel> metaModels = Sets.newHashSet(berichtmodel.get(metaModel));
        metaModels.remove(null);
        return metaModels;
    }

    /**
     * Geeft de MetaObjecten waarvoor bepaald is dat ze in het bericht komen.
     * @param elementObject een element object
     * @return een set met MetaObjecten
     */
    public Set<MetaObject> getGeautoriseerdeObjecten(final ObjectElement elementObject) {
        return Sets.newHashSet(Iterators.filter(getGeautoriseerdeObjecten().iterator(),
                metaObject -> metaObject.getObjectElement() == elementObject));
    }

    /**
     * @return set met MetaAttributen waarvoor bepaald is dat ze in het bericht komen
     */
    public Set<MetaAttribuut> getGeautoriseerdeAttributen() {
        return Sets.newHashSet(Iterators.filter(berichtmodel.keySet().iterator(), MetaAttribuut.class));
    }

    /**
     * @return set met MetaRecords waarvoor bepaald is dat ze in het bericht komen
     */
    public Set<MetaRecord> getGeautoriseerdeRecords() {
        return Sets.newHashSet(Iterators.filter(berichtmodel.keySet().iterator(), MetaRecord.class));
    }

    /**
     * @return set met MetaObjecten waarvoor bepaald is dat ze in het bericht komen
     */
    public Set<MetaObject> getGeautoriseerdeObjecten() {
        return Sets.newHashSet(Iterators.filter(berichtmodel.keySet().iterator(), MetaObject.class));
    }

    /**
     * @return set met MetaGroepen waarvoor bepaald is dat ze in het bericht komen
     */
    public Set<MetaGroep> getGeautoriseerdeGroepen() {
        return Sets.newHashSet(Iterators.filter(berichtmodel.keySet().iterator(), MetaGroep.class));
    }

    /**
     * @param metaObject het object waarvoor de sleutel wordt opgehaal
     * @return de versleutelde sleutel, of null indien niet versleuteld
     */
    public String getVersleuteldeObjectSleutel(final MetaObject metaObject) {
        return persoonVersleuteldeObjectSleutelMap.get(metaObject);
    }


    /**
     * Voegt een versleutelde objectsleutel toe voor een gegeven metaobject.
     * @param metaObject het metaobject (vooralsnog enkel Persoon objecten)
     * @param sleutel de versleutelde objectsleutel
     */
    public void addVersleuteldeObjectSleutel(final MetaObject metaObject, final String sleutel) {
        this.persoonVersleuteldeObjectSleutelMap.put(metaObject, sleutel);
    }

    /**
     * @return verwerkingssoortMap
     */
    public Map<MetaModel, Verwerkingssoort> getVerwerkingssoortMap() {
        return this.statischePersoongegevens.getVerwerkingssoortMap();
    }

    /**
     * @return indicatie of het mutatiebericht leeg is.
     */
    public boolean isLeegBericht() {
        return leegBericht;
    }

    /**
     * Zet de indicatie of het een leeg bericht betreft.
     * @param leegBericht leeg
     */
    public void setLeegBericht(final boolean leegBericht) {
        this.leegBericht = leegBericht;
    }

    public Set<MetaRecord> getKandidaatRecords() {
        return kandidaatRecords;
    }

    /**
     * @param kandidaatRecords kandidaatRecords
     */
    public void setKandidaatRecords(final Set<MetaRecord> kandidaatRecords) {
        this.kandidaatRecords = kandidaatRecords;
    }

    public Multimap<MetaModel, MetaModel> getBerichtmodel() {
        return ImmutableMultimap.copyOf(berichtmodel);
    }

    /**
     * @return datum aanvang matieriele periode.
     */
    public Integer getDatumAanvangMaterielePeriode() {
        return maakBerichtPersoon.getDatumAanvangmaterielePeriode();
    }

    /**
     * @return soort synchronisatie.
     */
    public SoortSynchronisatie getSoortSynchronisatie() {
        return maakBerichtPersoon.getSoortSynchronisatie();
    }

    public Autorisatiebundel getAutorisatiebundel() {
        return autorisatiebundel;
    }

    public StatischePersoongegevens getStatischePersoongegevens() {
        return statischePersoongegevens;
    }

    public MaakBerichtPersoonInformatie getMaakBerichtPersoon() {
        return maakBerichtPersoon;
    }

    public MaakBerichtHistorieFilterInformatie getHistorieFilterInformatie() {
        return historieFilterInformatie;
    }

    public boolean isMutatieberichtMetMeldingVerstrekkingsbeperking() {
        return mutatieberichtMetMeldingVerstrekkingsbeperking;
    }

    public void setMutatieberichtMetMeldingVerstrekkingsbeperking(boolean mutatieberichtMetMeldingVerstrekkingsbeperking) {
        this.mutatieberichtMetMeldingVerstrekkingsbeperking = mutatieberichtMetMeldingVerstrekkingsbeperking;
    }
}
