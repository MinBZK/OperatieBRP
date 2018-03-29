/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNummerverwijzingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3PlaatsLandConversieHelper.BrpPlaatsLand;
import org.springframework.stereotype.Component;

/**
 * Converteerder utilities.
 */
@Component
public final class ConverteerderUtils {
    private final Lo3AttribuutConverteerder converteerder;

    /**
     * Constructor.
     * @param converteerder Lo3AttribuutConverteerder
     */
    @Inject
    public ConverteerderUtils(final Lo3AttribuutConverteerder converteerder) {
        this.converteerder = converteerder;
    }

    /**
     * Bepaal of deze lijst alleen inhoudelijk lege Migratiegroepen bevat.
     * @param <T> TussenGroep type
     * @param lijst van Migratiegroepen
     * @return true als er tenminste 1 element in de lijst niet inhoudelijk leeg is.
     */
    <T extends BrpGroepInhoud> boolean isLijstMetAlleenLegeInhoud(final List<TussenGroep<T>> lijst) {
        assert lijst != null;

        for (final TussenGroep<T> groep : lijst) {
            if (!groep.isInhoudelijkLeeg()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Maakt een {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep} gevuld met een
     * {@link BrpSamengesteldeNaamInhoud}.
     * @param adellijkeTitelPredikaatCode lo3 adellijketitel of predikaat code
     * @param voornamen lo3 voornamen
     * @param voorvoegselGeslachtsnaam lo3 voorvoegsel geslachtsnaam
     * @param geslachtsnaam lo3 geslachtsnaam
     * @param lo3Historie lo3 historie
     * @param lo3Documentatie lo3 documentatie
     * @param lo3Herkomst lo3 herkomst
     * @return een gevulde {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep} met een {@link BrpSamengesteldeNaamInhoud}
     */
    TussenGroep<BrpSamengesteldeNaamInhoud> maakSamengesteldeNaamGroep(
            final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode,
            final Lo3String voornamen,
            final Lo3String voorvoegselGeslachtsnaam,
            final Lo3String geslachtsnaam,
            final Lo3Historie lo3Historie,
            final Lo3Documentatie lo3Documentatie,
            final Lo3Herkomst lo3Herkomst) {
        return maakSamengesteldeNaamGroep(
                adellijkeTitelPredikaatCode,
                voornamen,
                voorvoegselGeslachtsnaam,
                geslachtsnaam,
                lo3Historie,
                lo3Documentatie,
                lo3Herkomst,
                false);
    }

    /**
     * Maakt een {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep} gevuld met een
     * {@link BrpSamengesteldeNaamInhoud}.
     * @param adellijkeTitelPredikaatCode lo3 adellijketitel of predikaat code
     * @param voornamen lo3 voornamen
     * @param voorvoegselGeslachtsnaam lo3 voorvoegsel geslachtsnaam
     * @param geslachtsnaam lo3 geslachtsnaam
     * @param lo3Historie lo3 historie
     * @param lo3Documentatie lo3 documentatie
     * @param lo3Herkomst lo3 herkomst
     * @param indicatieAfgeleid true als de gegevens afgeleid gaan worden
     * @return een gevulde {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep} met een {@link BrpSamengesteldeNaamInhoud}
     */
    TussenGroep<BrpSamengesteldeNaamInhoud> maakSamengesteldeNaamGroep(
            final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode,
            final Lo3String voornamen,
            final Lo3String voorvoegselGeslachtsnaam,
            final Lo3String geslachtsnaam,
            final Lo3Historie lo3Historie,
            final Lo3Documentatie lo3Documentatie,
            final Lo3Herkomst lo3Herkomst,
            final boolean indicatieAfgeleid) {
        final BrpPredicaatCode predicaatCode = converteerPredicaatCode(adellijkeTitelPredikaatCode);
        final BrpAdellijkeTitelCode adellijkeTitelCode = converteerAdellijkeTitelCode(adellijkeTitelPredikaatCode);

        final VoorvoegselScheidingstekenPaar voorvoegselPaar =
                converteerder.converteerLo3VoorvoegselNaarVoorvoegselScheidingstekenPaar(voorvoegselGeslachtsnaam);
        final BrpString voornamenBrp = converteerder.converteerString(voornamen);
        final BrpString geslachtsnaamBrp = converteerder.converteerString(geslachtsnaam);
        final BrpBoolean indAfgeleid = new BrpBoolean(indicatieAfgeleid);

        return new TussenGroep<>(
                new BrpSamengesteldeNaamInhoud(
                        predicaatCode,
                        voornamenBrp,
                        voorvoegselPaar.getVoorvoegsel(),
                        voorvoegselPaar.getScheidingsteken(),
                        adellijkeTitelCode,
                        geslachtsnaamBrp,
                        new BrpBoolean(false),
                        indAfgeleid),
                lo3Historie,
                lo3Documentatie,
                lo3Herkomst);
    }

    /**
     * Maakt een {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep} gevuld met een
     * {@link BrpIdentificatienummersInhoud}.
     * @param anummer lo3 anummer
     * @param bsn lo3 bsn
     * @param lo3Historie lo3 historie
     * @param lo3Documentatie lo3 documentatie
     * @param lo3Herkomst lo3 historie
     * @return een {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep} gevuld met een {@link BrpIdentificatienummersInhoud}
     */
    TussenGroep<BrpIdentificatienummersInhoud> maakIdentificatieGroep(
            final Lo3String anummer,
            final Lo3String bsn,
            final Lo3Historie lo3Historie,
            final Lo3Documentatie lo3Documentatie,
            final Lo3Herkomst lo3Herkomst) {
        final BrpString administratienummer = converteerder.converteerString(anummer);
        final BrpString burgerservicenummer = converteerder.converteerString(bsn);
        return new TussenGroep<>(new BrpIdentificatienummersInhoud(administratienummer, burgerservicenummer), lo3Historie, lo3Documentatie, lo3Herkomst);
    }

    /**
     * Maakt een {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep} gevuld met een
     * {@link BrpNummerverwijzingInhoud}.
     * @param vorigAnummer lo3 vorigAnummer
     * @param volgendAnummer lo3 volgendAnummer
     * @param lo3Historie lo3 historie
     * @param lo3Documentatie lo3 documentatie
     * @param lo3Herkomst lo3 historie
     * @return een {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep} gevuld met een {@link BrpNummerverwijzingInhoud}
     */
    TussenGroep<BrpNummerverwijzingInhoud> maakNummerverwijzingGroep(
            final Lo3String vorigAnummer,
            final Lo3String volgendAnummer,
            final Lo3Historie lo3Historie,
            final Lo3Documentatie lo3Documentatie,
            final Lo3Herkomst lo3Herkomst) {
        final BrpString vorigeAdministratienummer = converteerder.converteerString(vorigAnummer);
        final BrpString volgendAdministratienummer = converteerder.converteerString(volgendAnummer);
        return new TussenGroep<>(
                new BrpNummerverwijzingInhoud(vorigeAdministratienummer, volgendAdministratienummer, null, null),
                lo3Historie,
                lo3Documentatie,
                lo3Herkomst);
    }

    /**
     * Maakt een {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep} gevuld met een {@link BrpGeboorteInhoud}.
     * @param geboorteGemeenteCode lo3 gemeentecode van geboorte
     * @param geboorteLandCode lo3 landcode van geboorte
     * @param geboortedatum lo3 geboortedatum
     * @param lo3Historie lo3 historie
     * @param lo3Documentatie lo3 documentatie
     * @param lo3Herkomst lo3 herkomst
     * @return een {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep} gevuld met een {@link BrpGeboorteInhoud}.
     */
    TussenGroep<BrpGeboorteInhoud> maakGeboorteGroep(
            final Lo3GemeenteCode geboorteGemeenteCode,
            final Lo3LandCode geboorteLandCode,
            final Lo3Datum geboortedatum,
            final Lo3Historie lo3Historie,
            final Lo3Documentatie lo3Documentatie,
            final Lo3Herkomst lo3Herkomst) {
        final BrpPlaatsLand brpPlaatsLand = new Lo3PlaatsLandConversieHelper(geboorteGemeenteCode, geboorteLandCode, converteerder).converteerNaarBrp();
        final BrpDatum brpGeboortedatum = converteerder.converteerDatum(geboortedatum);

        return new TussenGroep<>(
                new BrpGeboorteInhoud(
                        brpGeboortedatum,
                        brpPlaatsLand.getBrpGemeenteCode(),
                        brpPlaatsLand.getBrpWoonplaatsnaam(),
                        brpPlaatsLand.getBrpBuitenlandsePlaats(),
                        brpPlaatsLand.getBrpBuitenlandseRegio(),
                        brpPlaatsLand.getBrpLandOfGebiedCode(),
                        brpPlaatsLand.getBrpOmschrijvingLocatie()),
                lo3Historie,
                lo3Documentatie,
                lo3Herkomst);
    }

    /**
     * Maakt een {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep} gevuld met een
     * {@link BrpGeslachtsaanduidingInhoud}.
     * @param geslachtsaanduiding lo3 geslachtsaanduiding
     * @param historie lo3 herkomst
     * @param documentatie lo3 documentatie
     * @param lo3Herkomst lo3 herkomst
     * @return een {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep} gevuld met een {@link BrpGeslachtsaanduidingInhoud}
     */
    TussenGroep<BrpGeslachtsaanduidingInhoud> maakGeslachtsaanduidingInhoud(
            final Lo3Geslachtsaanduiding geslachtsaanduiding,
            final Lo3Historie historie,
            final Lo3Documentatie documentatie,
            final Lo3Herkomst lo3Herkomst) {
        return new TussenGroep<>(
                new BrpGeslachtsaanduidingInhoud(converteerder.converteerLo3Geslachtsaanduiding(geslachtsaanduiding)),
                historie,
                documentatie,
                lo3Herkomst);

    }

    /**
     * Maakt een {@link BrpDocumentInhoud}.
     * @param documentatie lo3 documentatie
     * @return gevuld {@link BrpDocumentInhoud} vanuit of een document of een akte.
     */
    public BrpDocumentInhoud maakDocumentInhoud(final Lo3Documentatie documentatie) {
        BrpDocumentInhoud documentInhoud = null;
        BrpPartijCode documentPartij;
        BrpSoortDocumentCode documentSoort;

        if (documentatie.isDocument()) {
            // DEF042 Er is een brondocument
            final Lo3GemeenteCode gemeenteDocument = documentatie.getGemeenteDocument();
            documentPartij = converteerder.converteerLo3GemeenteCodeNaarBrpPartijCode(gemeenteDocument);
            documentSoort = BrpSoortDocumentCode.HISTORIE_CONVERSIE;
            documentInhoud = new BrpDocumentInhoud(documentSoort, null, converteerder.converteerString(documentatie.getBeschrijvingDocument()), documentPartij);
        }

        if (documentatie.isAkte()) {
            // DEF043 Er is een akte
            // Of het eerste karakter een 1,2,3 of 5 is, wordt gevalideerd in de precondities. Hier is het eerste
            // karakter dus altijd de hiervoor genoemde waarde.
            final BrpString aktenummer = converteerder.converteerString(documentatie.getNummerAkte());
            final char akteIdentificatie = aktenummer.getWaarde().charAt(0);
            documentSoort = converteerder.converteerLo3Aktenummer(akteIdentificatie);
            final Lo3GemeenteCode gemeenteAkte = documentatie.getGemeenteAkte();
            documentPartij = converteerder.converteerLo3GemeenteCodeNaarBrpPartijCode(gemeenteAkte);
            documentInhoud = new BrpDocumentInhoud(documentSoort, aktenummer, null, documentPartij);
        }

        return documentInhoud;
    }

    /**
     * Maakt een {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep} gevuld met een {@link BrpRelatieInhoud}.
     * @param lo3Inhoud inhoud van het huwelijk of geregistreerd partnerschap lo3 categorie
     * @param eerdereLo3Inhoud (optioneel) De eerdere inhoud van huwelijk of geregistreerd partnerschap waaarvan gegevens worden overgenomen. Doorgaans zijn dit
     * gegevens over de sluiting die worden overgenomen bij de ontbinding. In een zeldzaam geval (registratie ontbinding < registratie sluiting) is het echter
     * andersom en worden de gegevens van de ontbinding overgenomen bij de sluiting (ORANJE-438).
     * @param lo3Documentatie lo3 documentatie
     * @param lo3Historie lo3 historie
     * @param lo3Herkomst lo3 herkomst
     * @return een {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep} gevuld met een {@link BrpRelatieInhoud}
     */
    TussenGroep<BrpRelatieInhoud> migreerRelatie(
            final Lo3HuwelijkOfGpInhoud lo3Inhoud,
            final Lo3HuwelijkOfGpInhoud eerdereLo3Inhoud,
            final Lo3Documentatie lo3Documentatie,
            final Lo3Historie lo3Historie,
            final Lo3Herkomst lo3Herkomst) {
        // Bepaal aanvang
        final BrpDatum datumAanvang;
        final BrpPlaatsLand brpAanvangRelatiePlaatsLand;
        if (eerdereLo3Inhoud != null && eerdereLo3Inhoud.isSluiting()) {
            brpAanvangRelatiePlaatsLand =
                    new Lo3PlaatsLandConversieHelper(
                            eerdereLo3Inhoud.getGemeenteCodeSluitingHuwelijkOfAangaanGp(),
                            eerdereLo3Inhoud.getLandCodeSluitingHuwelijkOfAangaanGp(),
                            converteerder).converteerNaarBrp();
            datumAanvang = converteerder.converteerDatum(eerdereLo3Inhoud.getDatumSluitingHuwelijkOfAangaanGp());
        } else {
            brpAanvangRelatiePlaatsLand =
                    new Lo3PlaatsLandConversieHelper(
                            lo3Inhoud.getGemeenteCodeSluitingHuwelijkOfAangaanGp(),
                            lo3Inhoud.getLandCodeSluitingHuwelijkOfAangaanGp(),
                            converteerder).converteerNaarBrp();
            datumAanvang = converteerder.converteerDatum(lo3Inhoud.getDatumSluitingHuwelijkOfAangaanGp());
        }

        final BrpGemeenteCode gemeenteCodeAanvang = brpAanvangRelatiePlaatsLand.getBrpGemeenteCode();
        final BrpString woonplaatsnaamAanvang = brpAanvangRelatiePlaatsLand.getBrpWoonplaatsnaam();
        final BrpString buitenlandsePlaatsAanvang = brpAanvangRelatiePlaatsLand.getBrpBuitenlandsePlaats();
        final BrpString buitenlandseRegioAanvang = brpAanvangRelatiePlaatsLand.getBrpBuitenlandseRegio();
        final BrpLandOfGebiedCode landCodeAanvang = brpAanvangRelatiePlaatsLand.getBrpLandOfGebiedCode();
        final BrpString omschrijvingLocatieAanvang = brpAanvangRelatiePlaatsLand.getBrpOmschrijvingLocatie();

        // Bepaal einde
        final BrpPlaatsLand brpEindeRelatiePlaatsLand;
        final BrpRedenEindeRelatieCode redenEinde;
        final BrpDatum datumEinde;

        if (eerdereLo3Inhoud != null && eerdereLo3Inhoud.isOntbinding()) {
            brpEindeRelatiePlaatsLand =
                    new Lo3PlaatsLandConversieHelper(
                            eerdereLo3Inhoud.getGemeenteCodeOntbindingHuwelijkOfGp(),
                            eerdereLo3Inhoud.getLandCodeOntbindingHuwelijkOfGp(),
                            converteerder).converteerNaarBrp();
            redenEinde = converteerRedenEinde(eerdereLo3Inhoud.getRedenOntbindingHuwelijkOfGpCode());
            datumEinde = converteerder.converteerDatum(eerdereLo3Inhoud.getDatumOntbindingHuwelijkOfGp());
        } else {
            brpEindeRelatiePlaatsLand =
                    new Lo3PlaatsLandConversieHelper(
                            lo3Inhoud.getGemeenteCodeOntbindingHuwelijkOfGp(),
                            lo3Inhoud.getLandCodeOntbindingHuwelijkOfGp(),
                            converteerder).converteerNaarBrp();
            redenEinde = converteerRedenEinde(lo3Inhoud.getRedenOntbindingHuwelijkOfGpCode());
            datumEinde = converteerder.converteerDatum(lo3Inhoud.getDatumOntbindingHuwelijkOfGp());
        }

        final BrpGemeenteCode gemeenteCodeEinde = brpEindeRelatiePlaatsLand.getBrpGemeenteCode();
        final BrpString woonplaatsnaamEinde = brpEindeRelatiePlaatsLand.getBrpWoonplaatsnaam();
        final BrpString buitenlandsePlaatsEinde = brpEindeRelatiePlaatsLand.getBrpBuitenlandsePlaats();
        final BrpString buitenlandseRegioEinde = brpEindeRelatiePlaatsLand.getBrpBuitenlandseRegio();
        final BrpLandOfGebiedCode landCodeEinde = brpEindeRelatiePlaatsLand.getBrpLandOfGebiedCode();
        final BrpString omschrijvingLocatieEinde = brpEindeRelatiePlaatsLand.getBrpOmschrijvingLocatie();

        return new TussenGroep<>(
                new BrpRelatieInhoud(
                        datumAanvang,
                        gemeenteCodeAanvang,
                        woonplaatsnaamAanvang,
                        buitenlandsePlaatsAanvang,
                        buitenlandseRegioAanvang,
                        landCodeAanvang,
                        omschrijvingLocatieAanvang,
                        redenEinde,
                        datumEinde,
                        gemeenteCodeEinde,
                        woonplaatsnaamEinde,
                        buitenlandsePlaatsEinde,
                        buitenlandseRegioEinde,
                        landCodeEinde,
                        omschrijvingLocatieEinde),
                lo3Historie,
                lo3Documentatie,
                lo3Herkomst);
    }

    private BrpRedenEindeRelatieCode converteerRedenEinde(final Lo3RedenOntbindingHuwelijkOfGpCode redenOntbinding) {
        if (Lo3RedenOntbindingHuwelijkOfGpCode.OMZETTING_VERBINTENIS.equals(redenOntbinding)) {
            return BrpRedenEindeRelatieCode.OMZETTING;
        } else {
            return converteerder.converteerLo3RedenOntbindingHuwelijkOfGpCode(redenOntbinding);
        }
    }

    /**
     * Converteer een predicaatcode.
     * @param adellijkeTitelPredikaatCode lo3 adellijketitel of predicaat code
     * @return brp predicaat of null als input een adellijke titel is
     */
    private BrpPredicaatCode converteerPredicaatCode(final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode) {
        return converteerder.converteerLo3AdellijkeTitelPredikaatCodeNaarBrpPredicaatCode(adellijkeTitelPredikaatCode);
    }

    /**
     * Converteer een adellijke titel.
     * @param adellijkeTitelPredikaatCode lo3 adellijketitel of predicaat code
     * @return brp adellijke titel of null als input een predicaat is
     */
    private BrpAdellijkeTitelCode converteerAdellijkeTitelCode(final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode) {
        return converteerder.converteerLo3AdellijkeTitelPredikaatCodeNaarBrpAdellijkeTitelCode(adellijkeTitelPredikaatCode);
    }
}
