/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Definitie;
import nl.moderniseringgba.migratie.Definities;
import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpWijzeGebruikGeslachtsnaamCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAanschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.Lo3PlaatsLandConversieHelper.BrpPlaatsLand;

import org.springframework.stereotype.Component;

/**
 * Deze class bevat de functionaliteit om de LO3 persoonsgegevens te converteren naar de BRP groepen.
 * 
 */
// CHECKSTYLE:OFF - Fan-out complexity - geaccepteerd
@Component
public class PersoonConverteerder {
    // CHECKSTYLE:ON

    @Inject
    private Lo3AttribuutConverteerder converteerder;

    /**
     * Converteert de LO3 elementen naar het BRP model.
     * 
     * @param persoonStapel
     *            de persoon stapel die moet worden geconverteerd
     * @param migratiePersoonslijstBuilder
     *            de builder waar het resultaat van de conversie aan wordt toegevoegd
     */
    public final void converteer(
            final Lo3Stapel<Lo3PersoonInhoud> persoonStapel,
            final MigratiePersoonslijstBuilder migratiePersoonslijstBuilder) {
        final List<MigratieGroep<BrpGeboorteInhoud>> geboorten = new ArrayList<MigratieGroep<BrpGeboorteInhoud>>();
        final List<MigratieGroep<BrpAanschrijvingInhoud>> aanschrijvingen =
                new ArrayList<MigratieGroep<BrpAanschrijvingInhoud>>();
        final List<MigratieGroep<BrpSamengesteldeNaamInhoud>> samengesteldeNamen =
                new ArrayList<MigratieGroep<BrpSamengesteldeNaamInhoud>>();
        final List<MigratieGroep<BrpIdentificatienummersInhoud>> identificatienummers =
                new ArrayList<MigratieGroep<BrpIdentificatienummersInhoud>>();
        final List<MigratieGroep<BrpGeslachtsaanduidingInhoud>> geslachten =
                new ArrayList<MigratieGroep<BrpGeslachtsaanduidingInhoud>>();

        for (final Lo3Categorie<Lo3PersoonInhoud> categorie : persoonStapel) {
            final Lo3PersoonInhoud inhoud = categorie.getInhoud();
            final Lo3Historie historie = categorie.getHistorie();
            final Lo3Documentatie documentatie = categorie.getDocumentatie();
            final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

            geboorten.add(new MigratieGroep<BrpGeboorteInhoud>(migreerGeboorte(inhoud), historie, documentatie,
                    herkomst));
            aanschrijvingen.add(new MigratieGroep<BrpAanschrijvingInhoud>(migreerAanschrijving(inhoud), historie,
                    documentatie, herkomst));
            samengesteldeNamen.add(new MigratieGroep<BrpSamengesteldeNaamInhoud>(migreerSamengesteldeNaam(inhoud),
                    historie, documentatie, herkomst));
            identificatienummers.add(new MigratieGroep<BrpIdentificatienummersInhoud>(
                    migreerIdentificatienummers(inhoud), historie, documentatie, herkomst));
            geslachten.add(new MigratieGroep<BrpGeslachtsaanduidingInhoud>(migreerGeslachtsaanduiding(inhoud),
                    historie, documentatie, herkomst));

        }

        migratiePersoonslijstBuilder.geboorteStapel(new MigratieStapel<BrpGeboorteInhoud>(geboorten));
        migratiePersoonslijstBuilder.aanschrijvingStapel(new MigratieStapel<BrpAanschrijvingInhoud>(aanschrijvingen));
        migratiePersoonslijstBuilder.samengesteldeNaamStapel(new MigratieStapel<BrpSamengesteldeNaamInhoud>(
                samengesteldeNamen));
        migratiePersoonslijstBuilder.identificatienummerStapel(new MigratieStapel<BrpIdentificatienummersInhoud>(
                identificatienummers));
        migratiePersoonslijstBuilder.geslachtsaanduidingStapel(new MigratieStapel<BrpGeslachtsaanduidingInhoud>(
                geslachten));
    }

    @Requirement(Requirements.CEL0410)
    private BrpGeslachtsaanduidingInhoud migreerGeslachtsaanduiding(final Lo3PersoonInhoud inhoud) {
        final BrpGeslachtsaanduidingCode geslachtsaanduiding =
                converteerder.converteerLo3Geslachtsaanduiding(inhoud.getGeslachtsaanduiding());

        return new BrpGeslachtsaanduidingInhoud(geslachtsaanduiding);
    }

    @Requirement(Requirements.CGR01_LB01)
    private static BrpIdentificatienummersInhoud migreerIdentificatienummers(final Lo3PersoonInhoud inhoud) {
        final Long anummer = inhoud.getaNummer();
        final Long burgerservicenummer = inhoud.getBurgerservicenummer();

        return new BrpIdentificatienummersInhoud(anummer, burgerservicenummer);
    }

    @Requirement({ Requirements.CEL0220_LB01, Requirements.CEL0230_LB01, Requirements.CEL0240_LB01 })
    private BrpSamengesteldeNaamInhoud migreerSamengesteldeNaam(final Lo3PersoonInhoud inhoud) {
        final BrpPredikaatCode predikaatCode =
                converteerder.converteerLo3AdellijkeTitelPredikaatCodeNaarBrpPredikaatCode(inhoud
                        .getAdellijkeTitelPredikaatCode());
        final String voornamen = inhoud.getVoornamen();
        final VoorvoegselScheidingstekenPaar voorvoegselPaar =
                converteerder.converteerLo3VoorvoegselNaarVoorvoegselScheidingstekenPaar(inhoud
                        .getVoorvoegselGeslachtsnaam());
        final BrpAdellijkeTitelCode adellijkeTitelCode =
                converteerder.converteerLo3AdellijkeTitelPredikaatCodeNaarBrpAdellijkeTitelCode(inhoud
                        .getAdellijkeTitelPredikaatCode());
        final String geslachtsnaam = inhoud.getGeslachtsnaam();

        return new BrpSamengesteldeNaamInhoud(predikaatCode, voornamen, voorvoegselPaar.getVoorvoegsel(),
                voorvoegselPaar.getScheidingsteken(), adellijkeTitelCode, geslachtsnaam, false, true);
    }

    @Requirement({ Requirements.CEL6110, Requirements.CEL0220_LB01, Requirements.CEL0230_LB01 })
    @Definitie(Definities.DEF016)
    private BrpAanschrijvingInhoud migreerAanschrijving(final Lo3PersoonInhoud inhoud) {
        final BrpWijzeGebruikGeslachtsnaamCode aanduidingNaamgebruik =
                converteerder.converteerLo3AanduidingNaamgebruikCode(inhoud.getAanduidingNaamgebruikCode());

        final String voornamen = inhoud.getVoornamen();
        final String geslachtsnaam = inhoud.getGeslachtsnaam();

        final VoorvoegselScheidingstekenPaar voorvoegselPaar =
                converteerder.converteerLo3VoorvoegselNaarVoorvoegselScheidingstekenPaar(inhoud
                        .getVoorvoegselGeslachtsnaam());

        final BrpAanschrijvingInhoud resultaat;
        final BrpAdellijkeTitelCode adellijkeTitel =
                converteerder.converteerLo3AdellijkeTitelPredikaatCodeNaarBrpAdellijkeTitelCode(inhoud
                        .getAdellijkeTitelPredikaatCode());
        if (isAdellijkeTitel(adellijkeTitel)) {
            resultaat =
                    new BrpAanschrijvingInhoud(aanduidingNaamgebruik, true, true, null, adellijkeTitel, voornamen,
                            voorvoegselPaar.getVoorvoegsel(), voorvoegselPaar.getScheidingsteken(), geslachtsnaam);
        } else {
            final BrpPredikaatCode predikaatCode =
                    converteerder.converteerLo3AdellijkeTitelPredikaatCodeNaarBrpPredikaatCode(inhoud
                            .getAdellijkeTitelPredikaatCode());
            if (isPredikaat(predikaatCode)) {
                resultaat =
                        new BrpAanschrijvingInhoud(aanduidingNaamgebruik, true, true, predikaatCode, null, voornamen,
                                voorvoegselPaar.getVoorvoegsel(), voorvoegselPaar.getScheidingsteken(), geslachtsnaam);

            } else {
                // DEF016 geen predikaat en geen adellijke titel
                resultaat =
                        new BrpAanschrijvingInhoud(aanduidingNaamgebruik, null, true, null, null, voornamen,
                                voorvoegselPaar.getVoorvoegsel(), voorvoegselPaar.getScheidingsteken(), geslachtsnaam);
            }
        }
        return resultaat;
    }

    @Definitie(Definities.DEF014)
    private static boolean isAdellijkeTitel(final BrpAdellijkeTitelCode code) {
        return code != null;
    }

    @Definitie(Definities.DEF015)
    private static boolean isPredikaat(final BrpPredikaatCode code) {
        return code != null;
    }

    @Requirement({ Requirements.CGR03_LB01, Requirements.CGR03_LB02, Requirements.CGR03_LB03 })
    private BrpGeboorteInhoud migreerGeboorte(final Lo3PersoonInhoud inhoud) {
        final BrpPlaatsLand brpPlaatsLand =
                new Lo3PlaatsLandConversieHelper(inhoud.getGeboorteGemeenteCode(), inhoud.getGeboorteLandCode(),
                        converteerder).converteerNaarBrp();

        final BrpDatum brpGeboortedatum = converteerder.converteerDatum(inhoud.getGeboortedatum());

        return new BrpGeboorteInhoud(brpGeboortedatum, brpPlaatsLand.getBrpGemeenteCode(),
                brpPlaatsLand.getBrpPlaatsCode(), brpPlaatsLand.getBrpBuitenlandsePlaats(),
                brpPlaatsLand.getBrpBuitenlandseRegio(), brpPlaatsLand.getBrpLandCode(),
                brpPlaatsLand.getBrpOmschrijvingLocatie());
    }
}
