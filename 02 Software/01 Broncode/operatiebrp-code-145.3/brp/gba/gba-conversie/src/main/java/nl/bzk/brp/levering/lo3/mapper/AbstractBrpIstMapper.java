/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.StapelVoorkomen;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpIstGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;

/**
 * Mapped IST stapels van een Persoon op de IST stapels van de BrpPersoonslijst.
 * @param <T> het type IST groep inhoud
 */
public abstract class AbstractBrpIstMapper<T extends AbstractBrpIstGroepInhoud> {

    /**
     * Zoekt de door gegeven set met stapels naar stapels van de meegegeven categorie en levert deze set op.
     * @param stapels de set van stapels die moet worden doorzocht, mag niet null zijn
     * @param categorie de categorie waarop dient te worden gefiltert, mag niet null zijn
     * @return de set van stapels voor deze categorie
     */
    protected final Set<Stapel> filterStapels(final Set<Stapel> stapels, final Lo3CategorieEnum categorie) {
        if (stapels == null) {
            return Collections.emptySet();
        }

        final Set<Stapel> result = new LinkedHashSet<>();
        for (final Stapel stapel : stapels) {
            try {
                if (Lo3CategorieEnum.getLO3Categorie(stapel.getCategorie()).equals(categorie)) {
                    result.add(stapel);
                }
            } catch (final Lo3SyntaxException e) {
                throw new IllegalArgumentException("De IST stapel bevat een onbekende categorie: " + stapel.getCategorie(), e);
            }
        }
        return result;
    }

    /**
     * Mapped van de set met stapels de juiste stapels op een lijst van BrpStapels met <T>.
     * @param stapels de set met IST stapels
     * @return een lijst van stapels van <T>
     */
    protected final List<BrpStapel<T>> mapStapels(final Set<Stapel> stapels) {
        final Set<Stapel> istStapels = filterStapels(stapels, getActueleCategorie());
        if (istStapels.isEmpty()) {
            return Collections.emptyList();
        }
        final List<BrpStapel<T>> result = new ArrayList<>();
        for (final Stapel istRelatieStapel : istStapels) {
            result.add(new BrpStapel<>(mapIstGroepen(istRelatieStapel)));
        }
        return result;
    }

    /**
     * Map de IST groepen.
     * @param istStapel ist stapel
     * @return ist groepen
     */
    private List<BrpGroep<T>> mapIstGroepen(final Stapel istStapel) {
        final List<BrpGroep<T>> result = new ArrayList<>();
        for (final StapelVoorkomen istStapelVoorkomen : istStapel.getStapelvoorkomens()) {
            result.add(mapIstStapel(istStapelVoorkomen));
        }
        return result;
    }

    /**
     * Map een IST groep.
     * @param istStapelVoorkomen stapel voorkomen
     * @return ist groep
     */
    private BrpGroep<T> mapIstStapel(final StapelVoorkomen istStapelVoorkomen) {
        return new BrpGroep<>(mapBrpIstGroepInhoud(istStapelVoorkomen), BrpHistorie.NULL_HISTORIE, null, null, null);
    }

    /**
     * Mapped een StapelVoorkomen op een IST groep.
     * @param voorkomen het voorkomen dat gemapped moet worden
     * @return het resultaat van de mapping
     */
    abstract T mapBrpIstGroepInhoud(final StapelVoorkomen voorkomen);

    /**
     * @param voorkomen het voorkomen in de stapel data gemapped moet worden.
     * @return de categorie van het voorkomen in de stapel dat gemapped moet worden.
     */
    final Lo3CategorieEnum getCategorie(final StapelVoorkomen voorkomen) {
        final Lo3CategorieEnum actueleCategorie = getActueleCategorie();

        if (voorkomen.getVolgnummer() == 0) {
            return actueleCategorie;
        } else {
            return Lo3CategorieEnum.bepaalHistorischeCategorie(actueleCategorie);
        }
    }

    /**
     * @return de actuele categorie van de stapel die gemapped moet worden.
     */
    abstract Lo3CategorieEnum getActueleCategorie();

    /**
     * Mapped een StapelVoorkomen op een {@link BrpIstStandaardGroepInhoud}.
     * @param voorkomen het voorkomen dat gemapped moet worden
     * @return gevulde {@link BrpIstStandaardGroepInhoud}
     */
    protected final BrpIstStandaardGroepInhoud mapBrpStandaardInhoud(final StapelVoorkomen voorkomen) {
        Lo3CategorieEnum categorie;
        try {
            categorie = Lo3CategorieEnum.getLO3Categorie(voorkomen.getStapel().getCategorie());
        } catch (final Lo3SyntaxException lse) {
            throw new IllegalArgumentException("Ongeldig categorie in stapelvoorkomen: " + voorkomen.getStapel().getCategorie(), lse);
        }

        final Integer stapel = voorkomen.getStapel().getVolgnummer();
        final Integer voorkomenNr = voorkomen.getVolgnummer();
        if (voorkomenNr != null && voorkomenNr > 0) {
            categorie = Lo3CategorieEnum.bepaalHistorischeCategorie(categorie);
        }

        final BrpPartijCode partij = BrpMapperUtil.mapBrpPartijCode(voorkomen.getPartij(), null);
        final BrpString documentOmschrijving = BrpMapperUtil.mapBrpString(voorkomen.getDocumentOmschrijving(), null);
        final BrpSoortDocumentCode soortDocumentCode = BrpMapperUtil.mapBrpSoortDocumentCode(voorkomen.getSoortDocument(), null);

        final BrpInteger rubriek8220DatumDocument = BrpMapperUtil.mapBrpInteger(voorkomen.getRubriek8220DatumDocument(), null);
        final BrpInteger rubriek8310AanduidingGegevensInOnderzoek =
                BrpMapperUtil.mapBrpInteger(voorkomen.getRubriek8310AanduidingGegevensInOnderzoek(), null);
        final BrpInteger rubriek8320DatumIngangOnderzoek = BrpMapperUtil.mapBrpInteger(voorkomen.getRubriek8320DatumIngangOnderzoek(), null);
        final BrpInteger rubriek8330DatumEindeOnderzoek = BrpMapperUtil.mapBrpInteger(voorkomen.getRubriek8330DatumEindeOnderzoek(), null);
        final BrpCharacter rubriek8410IndicatieOnjuistStrijdigheidOpenbareOrde =
                BrpMapperUtil.mapBrpCharacter(voorkomen.getRubriek8410OnjuistOfStrijdigOpenbareOrde(), null);
        final BrpInteger rubriek8510IngangsdatumGeldigheid = BrpMapperUtil.mapBrpInteger(voorkomen.getRubriek8510IngangsdatumGeldigheid(), null);
        final BrpInteger rubriek8610DatumVanOpneming = BrpMapperUtil.mapBrpInteger(voorkomen.getRubriek8610DatumVanOpneming(), null);

        final BrpString aktenummer = BrpMapperUtil.mapBrpString(voorkomen.getAktenummer(), null);

        final BrpIstStandaardGroepInhoud.Builder builder =
                new BrpIstStandaardGroepInhoud.Builder(categorie, stapel, voorkomenNr == null ? -1 : voorkomenNr);
        builder.aktenummer(aktenummer);
        builder.soortDocument(soortDocumentCode);
        builder.partij(partij);
        builder.rubriek8220DatumDocument(rubriek8220DatumDocument);
        builder.documentOmschrijving(documentOmschrijving);
        builder.rubriek8310AanduidingGegevensInOnderzoek(rubriek8310AanduidingGegevensInOnderzoek);
        builder.rubriek8320DatumIngangOnderzoek(rubriek8320DatumIngangOnderzoek);
        builder.rubriek8330DatumEindeOnderzoek(rubriek8330DatumEindeOnderzoek);
        builder.rubriek8410OnjuistOfStrijdigOpenbareOrde(rubriek8410IndicatieOnjuistStrijdigheidOpenbareOrde);
        builder.rubriek8510IngangsdatumGeldigheid(rubriek8510IngangsdatumGeldigheid);
        builder.rubriek8610DatumVanOpneming(rubriek8610DatumVanOpneming);
        return builder.build();
    }

    /**
     * Mapped een StapelVoorkomen op een BrpIstRelatieGroepInhoud.
     * @param voorkomen het voorkomen dat gemapped moet worden
     * @return het resultaat van de mapping
     */
    protected final BrpIstRelatieGroepInhoud mapBrpRelatieGroepInhoud(final StapelVoorkomen voorkomen) {

        // Map ingangsdatum familierechtelijke betrekkingen.
        final BrpInteger rubriek6210DatumIngangFamilierechtelijkeBetrekking =
                BrpMapperUtil.mapBrpInteger(voorkomen.getRubriek6210DatumIngangFamilierechtelijkeBetrekking(), null);

        // Map gerelateerde informatie.

        final BrpString anummer = BrpMapperUtil.mapBrpString(voorkomen.getAnummer(), null);
        final BrpString bsn = BrpMapperUtil.mapBrpString(voorkomen.getBsn(), null);
        final BrpString voornamen = BrpMapperUtil.mapBrpString(voorkomen.getVoornamen(), null);
        final BrpGeslachtsaanduidingCode geslachtsaanduidingCode = BrpMapperUtil.mapBrpGeslachtsaanduidingCode(voorkomen.getGeslachtsaanduiding(), null);
        final BrpGeslachtsaanduidingCode geslachtsaanduidingCodeBijAdellijkeTitelOfPredikaat =
                BrpMapperUtil.mapBrpGeslachtsaanduidingCode(voorkomen.getGeslachtsaanduidingBijAdellijkeTitelOfPredikaat(), null);

        final BrpPredicaatCode predicaatCode = BrpMapperUtil.mapBrpPredicaatCode(voorkomen.getPredicaat(), null);
        if (predicaatCode != null) {
            predicaatCode.setGeslachtsaanduiding(geslachtsaanduidingCodeBijAdellijkeTitelOfPredikaat);
        }

        final BrpAdellijkeTitelCode adellijkeTitel = BrpMapperUtil.mapBrpAdellijkeTitelCode(voorkomen.getAdellijkeTitel(), null);
        if (adellijkeTitel != null) {
            adellijkeTitel.setGeslachtsaanduiding(geslachtsaanduidingCodeBijAdellijkeTitelOfPredikaat);
        }

        final BrpString voorvoegsel = BrpMapperUtil.mapBrpString(voorkomen.getVoorvoegsel(), null);
        final BrpCharacter scheidingsteken = BrpMapperUtil.mapBrpCharacter(voorkomen.getScheidingsteken(), null);
        final BrpString geslachtsnaamstam = BrpMapperUtil.mapBrpString(voorkomen.getGeslachtsnaamstam(), null);

        final BrpInteger datumGeboorte = BrpMapperUtil.mapBrpInteger(voorkomen.getDatumGeboorte(), null);
        final BrpGemeenteCode gemeenteCodeGeboorte = BrpMapperUtil.mapBrpGemeenteCode(voorkomen.getGemeenteGeboorte(), null);
        final BrpString buitenlandsePlaatsGeboorte = BrpMapperUtil.mapBrpString(voorkomen.getBuitenlandsePlaatsGeboorte(), null);
        final BrpString omschrijvingLocatieGeboorte = BrpMapperUtil.mapBrpString(voorkomen.getOmschrijvingLocatieGeboorte(), null);
        final BrpLandOfGebiedCode landOfGebiedCode = BrpMapperUtil.mapBrpLandOfGebiedCode(voorkomen.getLandOfGebiedGeboorte(), null);

        final BrpIstRelatieGroepInhoud.Builder builder = new BrpIstRelatieGroepInhoud.Builder(mapBrpStandaardInhoud(voorkomen));

        builder.adellijkeTitel(adellijkeTitel);
        builder.anummer(anummer);
        builder.bsn(bsn);
        builder.buitenlandsePlaatsGeboorte(buitenlandsePlaatsGeboorte);
        builder.datumGeboorte(datumGeboorte);
        builder.geslachtsaanduidingCode(geslachtsaanduidingCode);
        builder.geslachtsnaamstam(geslachtsnaamstam);
        builder.gemeenteCodeGeboorte(gemeenteCodeGeboorte);
        builder.landOfGebiedGeboorte(landOfGebiedCode);
        builder.omschrijvingLocatieGeboorte(omschrijvingLocatieGeboorte);
        builder.predicaat(predicaatCode);
        builder.rubriek6210DatumIngangFamilierechtelijkeBetrekking(rubriek6210DatumIngangFamilierechtelijkeBetrekking);
        builder.scheidingsteken(scheidingsteken);
        builder.voornamen(voornamen);
        builder.voorvoegsel(voorvoegsel);

        return builder.build();
    }
}
