/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

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
abstract class AbstractBrpIstMapper<T extends AbstractBrpIstGroepInhoud> {

    /**
     * Zoekt de door gegeven set met stapels naar stapels van de meegegeven categorie en levert deze set op.
     * @param stapels de set van stapels die moet worden doorzocht, mag niet null zijn
     * @param categorie de categorie waarop dient te worden gefiltert, mag niet null zijn
     * @return de set van stapels voor deze categorie
     */
    protected Set<Stapel> filterStapels(final Set<Stapel> stapels, final Lo3CategorieEnum categorie) {
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
    protected List<BrpStapel<T>> mapStapels(final Set<Stapel> stapels) {
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

    private List<BrpGroep<T>> mapIstGroepen(final Stapel istStapel) {
        final List<BrpGroep<T>> result = new ArrayList<>();
        for (final StapelVoorkomen istStapelVoorkomen : istStapel.getStapelvoorkomens()) {
            result.add(mapIstStapel(istStapelVoorkomen));
        }
        return result;
    }

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
     * Geef de waarde van actuele categorie.
     * @return de actuele categorie van de stapel die gemapped moet worden.
     */
    abstract Lo3CategorieEnum getActueleCategorie();

    /**
     * Mapped een StapelVoorkomen op een {@link BrpIstStandaardGroepInhoud}.
     * @param voorkomen het voorkomen dat gemapped moet worden
     * @return gevulde {@link BrpIstStandaardGroepInhoud}
     */
    BrpIstStandaardGroepInhoud mapBrpStandaardInhoud(final StapelVoorkomen voorkomen) {
        Lo3CategorieEnum categorie;
        try {
            categorie = Lo3CategorieEnum.getLO3Categorie(voorkomen.getStapel().getCategorie());
        } catch (final Lo3SyntaxException lse) {
            throw new IllegalArgumentException("Ongeldig categorie in stapelvoorkomen: " + voorkomen.getStapel().getCategorie(), lse);
        }

        final int stapel = voorkomen.getStapel().getVolgnummer();
        final int voorkomenNr = voorkomen.getVolgnummer();
        if (voorkomenNr > 0) {
            categorie = Lo3CategorieEnum.bepaalHistorischeCategorie(categorie);
        }

        final BrpSoortDocumentCode soortDocument =
                voorkomen.getSoortDocument() != null ? new BrpSoortDocumentCode(voorkomen.getSoortDocument().getNaam()) : null;
        final BrpInteger rubriek8220DatumDocument = BrpInteger.wrap(voorkomen.getRubriek8220DatumDocument(), null);
        final BrpString documentOmschrijving = BrpString.wrap(voorkomen.getDocumentOmschrijving(), null);
        final BrpInteger rubriek8310AanduidingGegevensInOnderzoek = BrpInteger.wrap(voorkomen.getRubriek8310AanduidingGegevensInOnderzoek(), null);
        final BrpInteger rubriek8320DatumIngangOnderzoek = BrpInteger.wrap(voorkomen.getRubriek8320DatumIngangOnderzoek(), null);
        final BrpInteger rubriek8330DatumEindeOnderzoek = BrpInteger.wrap(voorkomen.getRubriek8330DatumEindeOnderzoek(), null);
        final BrpCharacter rubriek8410OnjuistOfStrijdigOpenbareOrde = BrpCharacter.wrap(voorkomen.getRubriek8410OnjuistOfStrijdigOpenbareOrde(), null);
        final BrpInteger rubriek8510IngangsdatumGeldigheid = BrpInteger.wrap(voorkomen.getRubriek8510IngangsdatumGeldigheid(), null);
        final BrpInteger rubriek8610DatumVanOpneming = BrpInteger.wrap(voorkomen.getRubriek8610DatumVanOpneming(), null);
        final BrpString aktenummer = BrpString.wrap(voorkomen.getAktenummer(), null);

        final BrpPartijCode partij = voorkomen.getPartij() != null ? BrpPartijCode.wrap(voorkomen.getPartij().getCode(), null) : null;

        final BrpIstStandaardGroepInhoud.Builder builder = new BrpIstStandaardGroepInhoud.Builder(categorie, stapel, voorkomenNr);
        builder.aktenummer(aktenummer);
        builder.soortDocument(soortDocument);
        builder.partij(partij);
        builder.rubriek8220DatumDocument(rubriek8220DatumDocument);
        builder.documentOmschrijving(documentOmschrijving);
        builder.rubriek8310AanduidingGegevensInOnderzoek(rubriek8310AanduidingGegevensInOnderzoek);
        builder.rubriek8320DatumIngangOnderzoek(rubriek8320DatumIngangOnderzoek);
        builder.rubriek8330DatumEindeOnderzoek(rubriek8330DatumEindeOnderzoek);
        builder.rubriek8410OnjuistOfStrijdigOpenbareOrde(rubriek8410OnjuistOfStrijdigOpenbareOrde);
        builder.rubriek8510IngangsdatumGeldigheid(rubriek8510IngangsdatumGeldigheid);
        builder.rubriek8610DatumVanOpneming(rubriek8610DatumVanOpneming);
        return builder.build();
    }

    /**
     * Mapped een StapelVoorkomen op een BrpIstRelatieGroepInhoud.
     * @param voorkomen het voorkomen dat gemapped moet worden
     * @return het resultaat van de mapping
     */
    BrpIstRelatieGroepInhoud mapBrpRelatieGroepInhoud(final StapelVoorkomen voorkomen) {
        final BrpInteger rubriek6210DatumIngangFamilierechtelijkeBetrekking =
                BrpInteger.wrap(voorkomen.getRubriek6210DatumIngangFamilierechtelijkeBetrekking(), null);
        final BrpString anummer = BrpString.wrap(voorkomen.getAnummer(), null);
        final BrpString bsn = BrpString.wrap(voorkomen.getBsn(), null);
        final BrpString voornamen = BrpString.wrap(voorkomen.getVoornamen(), null);
        final BrpGeslachtsaanduidingCode geslachtsaanduidingCode;
        geslachtsaanduidingCode =
                voorkomen.getGeslachtsaanduiding() != null ? BrpGeslachtsaanduidingCode.wrap(voorkomen.getGeslachtsaanduiding().getCode(), null) : null;
        final BrpGeslachtsaanduidingCode geslachtsaanduidingCodeBijAdellijkeTitelOfPredikaat;
        if (voorkomen.getGeslachtsaanduidingBijAdellijkeTitelOfPredikaat() != null) {
            geslachtsaanduidingCodeBijAdellijkeTitelOfPredikaat =
                    BrpGeslachtsaanduidingCode.wrap(voorkomen.getGeslachtsaanduidingBijAdellijkeTitelOfPredikaat().getCode(), null);
        } else {
            geslachtsaanduidingCodeBijAdellijkeTitelOfPredikaat = null;
        }
        final BrpPredicaatCode predicaatCode;
        predicaatCode = voorkomen.getPredicaat() != null ? BrpPredicaatCode.wrap(voorkomen.getPredicaat().getCode(), null) : null;

        if (predicaatCode != null) {
            predicaatCode.setGeslachtsaanduiding(geslachtsaanduidingCodeBijAdellijkeTitelOfPredikaat);
        }

        final BrpAdellijkeTitelCode adellijkeTitel;
        adellijkeTitel = voorkomen.getAdellijkeTitel() != null ? BrpAdellijkeTitelCode.wrap(voorkomen.getAdellijkeTitel().getCode(), null) : null;

        if (adellijkeTitel != null) {
            adellijkeTitel.setGeslachtsaanduiding(geslachtsaanduidingCodeBijAdellijkeTitelOfPredikaat);
        }

        final BrpString voorvoegsel = BrpString.wrap(voorkomen.getVoorvoegsel(), null);
        final BrpCharacter scheidingsteken = BrpCharacter.wrap(voorkomen.getScheidingsteken(), null);
        final BrpString geslachtsnaamstam = BrpString.wrap(voorkomen.getGeslachtsnaamstam(), null);

        final BrpInteger datumGeboorte = BrpInteger.wrap(voorkomen.getDatumGeboorte(), null);
        final BrpGemeenteCode gemeenteCodeGeboorte;
        gemeenteCodeGeboorte = voorkomen.getGemeenteGeboorte() != null ? BrpGemeenteCode.wrap(voorkomen.getGemeenteGeboorte().getCode(), null) : null;
        final BrpString buitenlandsePlaatsGeboorte = BrpString.wrap(voorkomen.getBuitenlandsePlaatsGeboorte(), null);
        final BrpString omschrijvingLocatieGeboorte = BrpString.wrap(voorkomen.getOmschrijvingLocatieGeboorte(), null);
        final BrpLandOfGebiedCode landOfGebiedCode;
        landOfGebiedCode =
                voorkomen.getLandOfGebiedGeboorte() != null ? BrpLandOfGebiedCode.wrap(voorkomen.getLandOfGebiedGeboorte().getCode(), null) : null;

        final BrpIstRelatieGroepInhoud.Builder builder = new BrpIstRelatieGroepInhoud.Builder(mapBrpStandaardInhoud(voorkomen));

        builder.rubriek6210DatumIngangFamilierechtelijkeBetrekking(rubriek6210DatumIngangFamilierechtelijkeBetrekking);
        builder.anummer(anummer);
        builder.bsn(bsn);
        builder.voornamen(voornamen);
        builder.predicaat(predicaatCode);
        builder.adellijkeTitel(adellijkeTitel);
        builder.voorvoegsel(voorvoegsel);
        builder.scheidingsteken(scheidingsteken);
        builder.geslachtsnaamstam(geslachtsnaamstam);
        builder.datumGeboorte(datumGeboorte);
        builder.gemeenteCodeGeboorte(gemeenteCodeGeboorte);
        builder.buitenlandsePlaatsGeboorte(buitenlandsePlaatsGeboorte);
        builder.omschrijvingLocatieGeboorte(omschrijvingLocatieGeboorte);
        builder.landOfGebiedGeboorte(landOfGebiedCode);
        builder.geslachtsaanduidingCode(geslachtsaanduidingCode);

        return builder.build();
    }
}
