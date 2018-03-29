/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpIstGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;

/**
 * Abstract klasse voor IST conversie.
 */
public abstract class AbstractRelatieConverteerder extends Converteerder {

    /**
     * constructor.
     * @param lo3AttribuutConverteerder
     */
    public AbstractRelatieConverteerder(final Lo3AttribuutConverteerder lo3AttribuutConverteerder) {
        super(lo3AttribuutConverteerder);
    }

    /**
     * Maakt een IstRelatie inhoud aan.
     * @param adellijkeTitelPredikaatCode adellijktitel of predikaat code
     * @param voornamen voornamen
     * @param voorvoegselGeslachtsnaam voorvoegsel geslachtsnaam
     * @param geslachtsnaam geslachtsnaam
     * @param geboorteGemeenteCode gemeentecode waar persoon geboren is
     * @param geboorteLandCode land waar persoon geboren is
     * @param geboortedatum datum wanneer persoon geboren is
     * @param geslachtsaanduiding geslachtsaanduiding
     * @param anummer anummer
     * @param burgerservicenummer BSN
     * @param familierechtelijkeBetrekking datum ingang familie rechtelijke betrekking
     * @param lo3Documentatie lo3 documentatie
     * @param lo3Onderzoek lo3 onderzoek
     * @param lo3Historie lo3 historie
     * @param lo3Herkomst lo3 herkomst
     * @return gevulde IST-relatie
     */
    final BrpIstRelatieGroepInhoud maakIstRelatieGroepInhoud(
            final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode,
            final Lo3String voornamen,
            final Lo3String voorvoegselGeslachtsnaam,
            final Lo3String geslachtsnaam,
            final Lo3GemeenteCode geboorteGemeenteCode,
            final Lo3LandCode geboorteLandCode,
            final Lo3Datum geboortedatum,
            final Lo3Geslachtsaanduiding geslachtsaanduiding,
            final Lo3String anummer,
            final Lo3String burgerservicenummer,
            final Lo3Datum familierechtelijkeBetrekking,
            final Lo3Documentatie lo3Documentatie,
            final Lo3Onderzoek lo3Onderzoek,
            final Lo3Historie lo3Historie,
            final Lo3Herkomst lo3Herkomst) {
        final BrpIdentificatienummersInhoud identInhoud =
                getUtils().maakIdentificatieGroep(anummer, burgerservicenummer, lo3Historie, lo3Documentatie, lo3Herkomst).getInhoud();

        final BrpSamengesteldeNaamInhoud naamInhoud =
                getUtils().maakSamengesteldeNaamGroep(
                        adellijkeTitelPredikaatCode,
                        voornamen,
                        voorvoegselGeslachtsnaam,
                        geslachtsnaam,
                        lo3Historie,
                        lo3Documentatie,
                        lo3Herkomst).getInhoud();

        final BrpGeboorteInhoud geboorteInhoud =
                getUtils().maakGeboorteGroep(geboorteGemeenteCode, geboorteLandCode, geboortedatum, lo3Historie, lo3Documentatie, lo3Herkomst).getInhoud();

        final BrpGeslachtsaanduidingInhoud geslachtsAanduidingInhoud =
                getUtils().maakGeslachtsaanduidingInhoud(geslachtsaanduiding, lo3Historie, lo3Documentatie, lo3Herkomst).getInhoud();

        final BrpIstStandaardGroepInhoud standaardGegevens = vulStandaardGegevens(lo3Herkomst, lo3Historie, lo3Documentatie, lo3Onderzoek);

        final BrpIstRelatieGroepInhoud.Builder relatieBuilder = new BrpIstRelatieGroepInhoud.Builder(standaardGegevens);

        vulGerelateerdeGegevens(relatieBuilder, identInhoud, naamInhoud, geboorteInhoud, geslachtsAanduidingInhoud);
        if (familierechtelijkeBetrekking != null) {
            relatieBuilder.rubriek6210DatumIngangFamilierechtelijkeBetrekking(
                    new BrpInteger(familierechtelijkeBetrekking.getIntegerWaarde(), familierechtelijkeBetrekking.getOnderzoek()));
        }
        return relatieBuilder.build();
    }

    /**
     * Maakt een IST Huwelijk of Geregistreerd partnerschap inhoud.
     * @param lo3Inhoud lo3 inhoud met Huwelijk of Geregistreerd partnerschap
     * @param lo3Documentatie lo3 documentatie
     * @param lo3Onderzoek lo3 onderzoek
     * @param lo3Historie lo3 historie
     * @param lo3Herkomst lo3 herkomst
     * @return een gevuld IST Huwelijk of Geregistreerd partnerschap inhoud
     */
    final BrpIstHuwelijkOfGpGroepInhoud maakIstHuwelijkOfGpGroepInhoud(
            final Lo3HuwelijkOfGpInhoud lo3Inhoud,
            final Lo3Documentatie lo3Documentatie,
            final Lo3Onderzoek lo3Onderzoek,
            final Lo3Historie lo3Historie,
            final Lo3Herkomst lo3Herkomst) {
        final BrpIdentificatienummersInhoud identInhoud =
                getUtils().maakIdentificatieGroep(lo3Inhoud.getaNummer(), lo3Inhoud.getBurgerservicenummer(), lo3Historie, lo3Documentatie, lo3Herkomst)
                        .getInhoud();

        final BrpSamengesteldeNaamInhoud naamInhoud =
                getUtils().maakSamengesteldeNaamGroep(
                        lo3Inhoud.getAdellijkeTitelPredikaatCode(),
                        lo3Inhoud.getVoornamen(),
                        lo3Inhoud.getVoorvoegselGeslachtsnaam(),
                        lo3Inhoud.getGeslachtsnaam(),
                        lo3Historie,
                        lo3Documentatie,
                        lo3Herkomst).getInhoud();

        final BrpGeboorteInhoud geboorteInhoud =
                getUtils().maakGeboorteGroep(
                        lo3Inhoud.getGeboorteGemeenteCode(),
                        lo3Inhoud.getGeboorteLandCode(),
                        lo3Inhoud.getGeboortedatum(),
                        lo3Historie,
                        lo3Documentatie,
                        lo3Herkomst).getInhoud();

        final BrpGeslachtsaanduidingInhoud geslachtsAanduidingInhoud =
                getUtils().maakGeslachtsaanduidingInhoud(lo3Inhoud.getGeslachtsaanduiding(), lo3Historie, lo3Documentatie, lo3Herkomst).getInhoud();

        final BrpRelatieInhoud relatieInhoud = getUtils().migreerRelatie(lo3Inhoud, null, lo3Documentatie, lo3Historie, lo3Herkomst).getInhoud();

        final BrpIstStandaardGroepInhoud standaardGegevens = vulStandaardGegevens(lo3Herkomst, lo3Historie, lo3Documentatie, lo3Onderzoek);

        final BrpIstRelatieGroepInhoud.Builder relatieBuilder = new BrpIstRelatieGroepInhoud.Builder(standaardGegevens);

        vulGerelateerdeGegevens(relatieBuilder, identInhoud, naamInhoud, geboorteInhoud, geslachtsAanduidingInhoud);

        final BrpIstHuwelijkOfGpGroepInhoud.Builder builder = new BrpIstHuwelijkOfGpGroepInhoud.Builder(standaardGegevens, relatieBuilder.build());

        if (BrpValidatie.isAttribuutGevuld(relatieInhoud.getDatumAanvang())) {
            builder.datumAanvang(new BrpInteger(relatieInhoud.getDatumAanvang().getWaarde(), relatieInhoud.getDatumAanvang().getOnderzoek()));
        }

        builder.gemeenteCodeAanvang(relatieInhoud.getGemeenteCodeAanvang());
        builder.buitenlandsePlaatsAanvang(relatieInhoud.getBuitenlandsePlaatsAanvang());
        builder.omschrijvingLocatieAanvang(relatieInhoud.getOmschrijvingLocatieAanvang());
        builder.landOfGebiedAanvang(relatieInhoud.getLandOfGebiedCodeAanvang());
        builder.redenBeeindigingRelatieCode(relatieInhoud.getRedenEindeRelatieCode());

        if (BrpValidatie.isAttribuutGevuld(relatieInhoud.getDatumEinde())) {
            builder.datumEinde(new BrpInteger(relatieInhoud.getDatumEinde().getWaarde(), relatieInhoud.getDatumEinde().getOnderzoek()));
        }

        builder.gemeenteCodeEinde(relatieInhoud.getGemeenteCodeEinde());
        builder.buitenlandsePlaatsEinde(relatieInhoud.getBuitenlandsePlaatsEinde());
        builder.omschrijvingLocatieEinde(relatieInhoud.getOmschrijvingLocatieEinde());
        builder.landOfGebiedEinde(relatieInhoud.getLandOfGebiedCodeEinde());
        builder.soortRelatieCode(getLo3AttribuutConverteerder().converteerLo3SoortVerbintenis(lo3Inhoud.getSoortVerbintenis()));

        return builder.build();
    }

    /**
     * Maak IST Gezagsverhouding groep inhoud.
     * @param lo3Inhoud inhoud
     * @param lo3Documentatie documentatie
     * @param lo3Onderzoek onderzoek
     * @param lo3Historie historie
     * @param lo3Herkomst herkomst
     * @return gezagsverhouding inhoud.
     */
    final BrpIstGezagsVerhoudingGroepInhoud maakIstGezagsVerhoudingGroepInhoud(
            final Lo3GezagsverhoudingInhoud lo3Inhoud,
            final Lo3Documentatie lo3Documentatie,
            final Lo3Onderzoek lo3Onderzoek,
            final Lo3Historie lo3Historie,
            final Lo3Herkomst lo3Herkomst) {
        final Lo3IndicatieGezagMinderjarige indicatieGezagMinderjarige = lo3Inhoud.getIndicatieGezagMinderjarige();

        final BrpIstStandaardGroepInhoud standaardGegevens = vulStandaardGegevens(lo3Herkomst, lo3Historie, lo3Documentatie, lo3Onderzoek);

        final BrpIstGezagsVerhoudingGroepInhoud.Builder istGezagsVerhoudingBuilder = new BrpIstGezagsVerhoudingGroepInhoud.Builder(standaardGegevens);
        final BrpBoolean ouder1Gezag = getLo3AttribuutConverteerder().converteerOuder1HeeftGezag(indicatieGezagMinderjarige);
        final BrpBoolean ouder2Gezag = getLo3AttribuutConverteerder().converteerOuder2HeeftGezag(indicatieGezagMinderjarige);

        if (BrpBoolean.isTrue(ouder1Gezag)) {
            istGezagsVerhoudingBuilder.indicatieOuder1HeeftGezag(ouder1Gezag);
        }
        if (BrpBoolean.isTrue(ouder2Gezag)) {
            istGezagsVerhoudingBuilder.indicatieOuder2HeeftGezag(ouder2Gezag);
        }

        istGezagsVerhoudingBuilder.indicatieDerdeHeeftGezag(getLo3AttribuutConverteerder().converteerDerdeHeeftGezag(indicatieGezagMinderjarige));
        istGezagsVerhoudingBuilder.indicatieOnderCuratele(
                getLo3AttribuutConverteerder().converteerOnderCuratele(lo3Inhoud.getIndicatieCurateleregister()));
        return istGezagsVerhoudingBuilder.build();
    }

    /**
     * Maak tussen groep.
     * @param inhoud inhoud
     * @param <T> extends AbstractBrpIstGroepInhoud
     * @return tussen groep.
     */
    final <T extends AbstractBrpIstGroepInhoud> TussenGroep<T> maakMigratieGroep(final T inhoud) {
        return new TussenGroep<>(inhoud, new Lo3Historie(null, null, null), null, null);
    }

    /* Private methods */
    private void vulGerelateerdeGegevens(
            final BrpIstRelatieGroepInhoud.Builder istInhoudBuilder,
            final BrpIdentificatienummersInhoud identInhoud,
            final BrpSamengesteldeNaamInhoud naamInhoud,
            final BrpGeboorteInhoud geboorteInhoud,
            final BrpGeslachtsaanduidingInhoud geslachtsAanduidingInhoud) {
        istInhoudBuilder.anummer(identInhoud.getAdministratienummer());
        istInhoudBuilder.bsn(identInhoud.getBurgerservicenummer());
        istInhoudBuilder.voornamen(naamInhoud.getVoornamen());
        istInhoudBuilder.predicaat(naamInhoud.getPredicaatCode());
        istInhoudBuilder.adellijkeTitel(naamInhoud.getAdellijkeTitelCode());
        istInhoudBuilder.voorvoegsel(naamInhoud.getVoorvoegsel());
        istInhoudBuilder.scheidingsteken(naamInhoud.getScheidingsteken());
        istInhoudBuilder.geslachtsnaamstam(naamInhoud.getGeslachtsnaamstam());
        if (BrpValidatie.isAttribuutGevuld(geboorteInhoud.getGeboortedatum())) {
            istInhoudBuilder.datumGeboorte(
                    new BrpInteger(geboorteInhoud.getGeboortedatum().getWaarde(), geboorteInhoud.getGeboortedatum().getOnderzoek()));
        }
        istInhoudBuilder.gemeenteCodeGeboorte(geboorteInhoud.getGemeenteCode());
        istInhoudBuilder.buitenlandsePlaatsGeboorte(geboorteInhoud.getBuitenlandsePlaatsGeboorte());
        istInhoudBuilder.omschrijvingLocatieGeboorte(geboorteInhoud.getOmschrijvingGeboortelocatie());
        istInhoudBuilder.landOfGebiedGeboorte(geboorteInhoud.getLandOfGebiedCode());
        istInhoudBuilder.geslachtsaanduidingCode(geslachtsAanduidingInhoud.getGeslachtsaanduidingCode());
    }

    private BrpIstStandaardGroepInhoud vulStandaardGegevens(
            final Lo3Herkomst lo3Herkomst,
            final Lo3Historie lo3Historie,
            final Lo3Documentatie lo3Documentatie,
            final Lo3Onderzoek lo3Onderzoek) {
        // Standaard
        final BrpIstStandaardGroepInhoud.Builder standaardGegevens =
                new BrpIstStandaardGroepInhoud.Builder(lo3Herkomst.getCategorie(), lo3Herkomst.getStapel(), lo3Herkomst.getVoorkomen());
        final BrpDocumentInhoud brpDocument = getUtils().maakDocumentInhoud(lo3Documentatie);
        final Lo3AttribuutConverteerder converteerder = getLo3AttribuutConverteerder();

        standaardGegevens.aktenummer(converteerder.converteerString(lo3Documentatie.getNummerAkte()));
        standaardGegevens.soortDocument(brpDocument != null ? brpDocument.getSoortDocumentCode() : null);
        standaardGegevens.partij(brpDocument != null ? brpDocument.getPartijCode() : bepaalLegePartij(lo3Documentatie));
        standaardGegevens.rubriek8220DatumDocument(converteerder.converteerDatumToInteger(lo3Documentatie.getDatumDocument()));
        standaardGegevens.documentOmschrijving(converteerder.converteerString(lo3Documentatie.getBeschrijvingDocument()));

        if (lo3Onderzoek != null) {
            standaardGegevens.rubriek8310AanduidingGegevensInOnderzoek(converteerder.converteerInteger(lo3Onderzoek.getAanduidingGegevensInOnderzoek()));
            if (lo3Onderzoek.getDatumIngangOnderzoek() != null) {
                standaardGegevens.rubriek8320DatumIngangOnderzoek(converteerder.converteerDatumToInteger(lo3Onderzoek.getDatumIngangOnderzoek()));
            }
            if (lo3Onderzoek.getDatumEindeOnderzoek() != null) {
                standaardGegevens.rubriek8330DatumEindeOnderzoek(converteerder.converteerDatumToInteger(lo3Onderzoek.getDatumEindeOnderzoek()));
            }
        }
        standaardGegevens.rubriek8410OnjuistOfStrijdigOpenbareOrde(converteerder.converteerOnjuistIndicatie(lo3Historie.getIndicatieOnjuist()));
        standaardGegevens.rubriek8510IngangsdatumGeldigheid(converteerder.converteerDatumToInteger(lo3Historie.getIngangsdatumGeldigheid()));
        standaardGegevens.rubriek8610DatumVanOpneming(converteerder.converteerDatumToInteger(lo3Historie.getDatumVanOpneming()));
        return standaardGegevens.build();
    }

    private BrpPartijCode bepaalLegePartij(final Lo3Documentatie lo3Documentatie) {
        final Lo3GemeenteCode bronPartijCode;
        if (lo3Documentatie.getGemeenteAkte() != null) {
            bronPartijCode = lo3Documentatie.getGemeenteAkte();
        } else {
            bronPartijCode = lo3Documentatie.getGemeenteDocument();
        }

        return getLo3AttribuutConverteerder().converteerLo3GemeenteCodeNaarBrpPartijCode(bronPartijCode);
    }

}
