/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.StapelVoorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpIstGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.StamtabelMapping;

/**
 * Deze mapper mapped de BrpIstAbstractInhoud op Stapel en StapelVoorkomen uit het BRP operationele model.
 * @param <T> het specifieke type IstMapper
 */
public abstract class AbstractIstMapper<T extends AbstractBrpIstGroepInhoud> extends AbstractMapperStrategie<T, Persoon> {
    private final AdministratieveHandeling administratieveHandeling;

    /**
     * Maakt een IstMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param administratieveHandeling de administratieve handeling voor de IST stapel
     */
    public AbstractIstMapper(final DynamischeStamtabelRepository dynamischeStamtabelRepository, final AdministratieveHandeling administratieveHandeling) {
        super(dynamischeStamtabelRepository, null);
        this.administratieveHandeling = administratieveHandeling;
    }

    /**
     * Mapt de IST stapelop de Persoon.
     * @param brpStapel stapel met IST gegevens
     * @param persoon persoon die opgeslagen wordt in de database
     * @return Map met daarin per categorie een map van stapels met als sleutel het stapelnummer.
     */
    public final Map<Lo3CategorieEnum, Map<Integer, Stapel>> mapIstStapelOpPersoon(final BrpStapel<T> brpStapel, final Persoon persoon) {
        return mapIstStapelsOpPersoon(Collections.singletonList(brpStapel), persoon);
    }

    /**
     * Mapt de IST stapels op de Persoon.
     * @param brpStapels stapels met IST gegevens
     * @param persoon persoon die opgeslagen wordt in de database
     * @return Map met daarin per categorie een map van stapels met als sleutel het stapelnummer.
     */
    public final Map<Lo3CategorieEnum, Map<Integer, Stapel>> mapIstStapelsOpPersoon(final List<BrpStapel<T>> brpStapels, final Persoon persoon) {
        final EnumMap<Lo3CategorieEnum, Map<Integer, Stapel>> stapelsPerCategorie = new EnumMap<>(Lo3CategorieEnum.class);
        final Map<Integer, Stapel> stapels = new HashMap<>();

        for (final BrpStapel<T> brpStapel : brpStapels) {
            Stapel stapel = null;
            final List<BrpGroep<T>> groepen = brpStapel.getGroepen();
            for (final BrpGroep<T> groep : groepen) {
                final T inhoud = groep.getInhoud();

                // Initieel aanmaken van de stapel
                if (stapel == null) {
                    final Lo3CategorieEnum categorie = Lo3CategorieEnum.bepaalActueleCategorie(inhoud.getCategorie());
                    final Integer stapelnr = inhoud.getStapel();
                    stapel = new Stapel(persoon, categorie.getCategorie(), stapelnr);
                    persoon.addStapel(stapel);
                    stapels.put(stapelnr, stapel);
                    toevoegenAlsLeeg(stapelsPerCategorie, stapels, categorie);
                }

                // Aanmaken van de stapelvoorkomen(s)
                stapel.addStapelVoorkomen(mapStapelVoorkomen(stapel, inhoud));
            }
        }
        return stapelsPerCategorie;
    }

    private void toevoegenAlsLeeg(final Map<Lo3CategorieEnum, Map<Integer, Stapel>> stapelsPerCategorie, final Map<Integer, Stapel> stapels,
                                  final Lo3CategorieEnum categorie) {
        if (stapelsPerCategorie.isEmpty()) {
            stapelsPerCategorie.put(categorie, stapels);
        }
    }

    /**
     * Maakt een {@link StapelVoorkomen} aan met de opgegeven inhoud en koppelt deze aan de opgegeven stapel.
     * @param stapel stapel waaraan de nieuwe stapelvoorkomen moet komen
     * @param inhoud de inhoud waarmee het stapelvoorkomen gevuld moet worden;
     * @return een gevuld {@link StapelVoorkomen}
     */
    protected abstract StapelVoorkomen mapStapelVoorkomen(Stapel stapel, T inhoud);

    /**
     * Mapt de gegevens van de standaard velden naar het voorkomen.
     * @param voorkomen stapel voorkomen dat gevuld moet worden
     * @param inhoud inhoud waaruit het voorkomen gevuld gaat worden
     */
    final void vulStandaard(final StapelVoorkomen voorkomen, final BrpIstStandaardGroepInhoud inhoud) {
        final StamtabelMapping stamtabelMapping = getStamtabelMapping();

        voorkomen.setSoortDocument(stamtabelMapping.findSoortDocumentByCode(inhoud.getSoortDocument()));
        voorkomen.setPartij(stamtabelMapping.findPartijByCode(inhoud.getPartij()));
        voorkomen.setAktenummer(BrpString.unwrap(inhoud.getAktenummer()));
        voorkomen.setRubriek8220DatumDocument(BrpInteger.unwrap(inhoud.getRubriek8220DatumDocument()));
        voorkomen.setDocumentOmschrijving(BrpString.unwrap(inhoud.getDocumentOmschrijving()));
        voorkomen.setRubriek8310AanduidingGegevensInOnderzoek(BrpInteger.unwrap(inhoud.getRubriek8310AanduidingGegevensInOnderzoek()));
        voorkomen.setRubriek8320DatumIngangOnderzoek(BrpInteger.unwrap(inhoud.getRubriek8320DatumIngangOnderzoek()));
        voorkomen.setRubriek8330DatumEindeOnderzoek(BrpInteger.unwrap(inhoud.getRubriek8330DatumEindeOnderzoek()));
        voorkomen.setRubriek8410OnjuistOfStrijdigOpenbareOrde(BrpCharacter.unwrap(inhoud.getRubriek8410OnjuistOfStrijdigOpenbareOrde()));
        voorkomen.setRubriek8510IngangsdatumGeldigheid(BrpInteger.unwrap(inhoud.getRubriek8510IngangsdatumGeldigheid()));
        voorkomen.setRubriek8610DatumVanOpneming(BrpInteger.unwrap(inhoud.getRubriek8610DatumVanOpneming()));
        // default fam. recht. betrekking
        voorkomen.setSoortRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
    }

    /**
     * Mapt de gegevens van de gerelateerden naar het voorkomen.
     * @param voorkomen stapel voorkomen dat gevuld moet worden
     * @param inhoud inhoud waaruit het voorkomen gevuld gaat worden
     */
    final void vulGerelateerden(final StapelVoorkomen voorkomen, final BrpIstRelatieGroepInhoud inhoud) {
        final StamtabelMapping stamtabelMapping = getStamtabelMapping();

        voorkomen.setAnummer(BrpString.unwrap(inhoud.getAnummer()));
        voorkomen.setBsn(BrpString.unwrap(inhoud.getBsn()));
        voorkomen.setVoornamen(BrpString.unwrap(inhoud.getVoornamen()));
        vulPredikaat(voorkomen, inhoud.getPredicaatCode());
        vulAdellijkeTitel(voorkomen, inhoud.getAdellijkeTitelCode());
        voorkomen.setVoorvoegsel(BrpString.unwrap(inhoud.getVoorvoegsel()));
        voorkomen.setScheidingsteken(BrpCharacter.unwrap(inhoud.getScheidingsteken()));
        voorkomen.setGeslachtsnaamstam(BrpString.unwrap(inhoud.getGeslachtsnaamstam()));
        voorkomen.setDatumGeboorte(BrpInteger.unwrap(inhoud.getDatumGeboorte()));
        voorkomen.setGemeenteGeboorte(stamtabelMapping.findGemeenteByCode(inhoud.getGemeenteCodeGeboorte()));
        voorkomen.setBuitenlandsePlaatsGeboorte(BrpString.unwrap(inhoud.getBuitenlandsePlaatsGeboorte()));
        voorkomen.setOmschrijvingLocatieGeboorte(BrpString.unwrap(inhoud.getOmschrijvingLocatieGeboorte()));
        voorkomen.setLandOfGebiedGeboorte(stamtabelMapping.findLandOfGebiedByCode(inhoud.getLandOfGebiedCodeGeboorte()));
        voorkomen.setGeslachtsaanduiding(MapperUtil.mapBrpGeslachtsaanduidingCode(inhoud.getGeslachtsaanduidingCode()));
        voorkomen.setRubriek6210DatumIngangFamilierechtelijkeBetrekking(BrpInteger.unwrap(inhoud.getRubriek6210DatumIngangFamilierechtelijkeBetrekking()));
    }

    private void vulAdellijkeTitel(final StapelVoorkomen voorkomen, final BrpAdellijkeTitelCode adellijkeTitel) {
        if (BrpValidatie.isAttribuutGevuld(adellijkeTitel)) {
            voorkomen.setAdellijkeTitel(AdellijkeTitel.valueOf(adellijkeTitel.getWaarde()));
            voorkomen.setGeslachtsaanduidingBijAdellijkeTitelOfPredikaat(MapperUtil.mapBrpGeslachtsaanduidingCode(adellijkeTitel.getGeslachtsaanduiding()));
        }
    }

    private void vulPredikaat(final StapelVoorkomen voorkomen, final BrpPredicaatCode predikaat) {
        if (BrpValidatie.isAttribuutGevuld(predikaat)) {
            voorkomen.setPredicaat(Predicaat.valueOf(predikaat.getWaarde()));
            voorkomen.setGeslachtsaanduidingBijAdellijkeTitelOfPredikaat(MapperUtil.mapBrpGeslachtsaanduidingCode(predikaat.getGeslachtsaanduiding()));
        }
    }

    @Override
    protected void mapHistorischeGegevens(final BrpStapel<T> brpStapel, final Persoon persoon, final Element objecttype) {
    }

    /**
     * Geef de waarde van administratieve handeling.
     * @return administratieve handeling
     */
    protected final AdministratieveHandeling getAdministratieveHandeling() {
        return administratieveHandeling;
    }
}
