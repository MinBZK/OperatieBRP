/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNummerverwijzingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import org.springframework.stereotype.Component;

/**
 * Deze class bevat de functionaliteit om de LO3 persoonsgegevens te converteren naar de BRP groepen.
 *
 */
@Component
public class PersoonConverteerder extends AbstractConverteerder {

    /**
     * Converteert de LO3 elementen naar het BRP model.
     *
     * @param persoonStapel
     *            de persoon stapel die moet worden geconverteerd
     * @param isDummyPL
     *            of de PL een dummy is of niet.
     * @param tussenPersoonslijstBuilder
     *            Hybride persoonslijst van BRP inhoud met Lo3 geschiedenis om het tussen resultaat in op te slaan.
     */
    public final void converteer(
        final Lo3Stapel<Lo3PersoonInhoud> persoonStapel,
        final boolean isDummyPL,
        final TussenPersoonslijstBuilder tussenPersoonslijstBuilder)
    {
        final List<TussenGroep<BrpGeboorteInhoud>> geboorten = new ArrayList<>();
        final List<TussenGroep<BrpNaamgebruikInhoud>> naamgebruiken = new ArrayList<>();
        final List<TussenGroep<BrpSamengesteldeNaamInhoud>> samengesteldeNamen = new ArrayList<>();
        final List<TussenGroep<BrpIdentificatienummersInhoud>> identificatienummers = new ArrayList<>();
        final List<TussenGroep<BrpGeslachtsaanduidingInhoud>> geslachten = new ArrayList<>();
        final List<TussenGroep<BrpNummerverwijzingInhoud>> nummerverwijzingen = new ArrayList<>();

        for (final Lo3Categorie<Lo3PersoonInhoud> categorie : persoonStapel) {
            final Lo3PersoonInhoud inhoud = categorie.getInhoud();
            final Lo3Historie historie = categorie.getHistorie();
            final Lo3Documentatie documentatie = categorie.getDocumentatie();
            final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

            final BrpNaamgebruikInhoud naamgebruikInhoud = migreerNaamgebruik(inhoud, isDummyPL);
            if (naamgebruikInhoud != null) {
                naamgebruiken.add(new TussenGroep<>(naamgebruikInhoud, historie, documentatie, herkomst));
            }

            geboorten.add(migreerGeboorte(categorie));
            samengesteldeNamen.add(migreerSamengesteldeNaam(categorie));
            identificatienummers.add(migreerIdentificatienummers(categorie));
            geslachten.add(migreerGeslachtsaanduiding(categorie));

            final TussenGroep<BrpNummerverwijzingInhoud> nummerverwijzingGroep = migreerNummerverwijzing(categorie);
            if (nummerverwijzingGroep != null) {
                nummerverwijzingen.add(nummerverwijzingGroep);
            }
        }

        if (naamgebruiken.size() > 0) {
            tussenPersoonslijstBuilder.naamgebruikStapel(new TussenStapel<>(naamgebruiken));
        }

        tussenPersoonslijstBuilder.geboorteStapel(new TussenStapel<>(geboorten));
        tussenPersoonslijstBuilder.samengesteldeNaamStapel(new TussenStapel<>(samengesteldeNamen));
        tussenPersoonslijstBuilder.identificatienummerStapel(new TussenStapel<>(identificatienummers));
        tussenPersoonslijstBuilder.geslachtsaanduidingStapel(new TussenStapel<>(geslachten));
        if (!nummerverwijzingen.isEmpty()) {
            tussenPersoonslijstBuilder.nummerverwijzingStapel(new TussenStapel<>(nummerverwijzingen));
        }
    }

    @Requirement(Requirements.CEL0410)
    private TussenGroep<BrpGeslachtsaanduidingInhoud> migreerGeslachtsaanduiding(final Lo3Categorie<Lo3PersoonInhoud> persoon) {
        final Lo3PersoonInhoud inhoud = persoon.getInhoud();
        return getUtils().maakGeslachtsaanduidingInhoud(
            inhoud.getGeslachtsaanduiding(),
            persoon.getHistorie(),
            persoon.getDocumentatie(),
            persoon.getLo3Herkomst());
    }

    @Requirement(Requirements.CGR01_LB01)
    private TussenGroep<BrpIdentificatienummersInhoud> migreerIdentificatienummers(final Lo3Categorie<Lo3PersoonInhoud> persoon) {
        final Lo3PersoonInhoud inhoud = persoon.getInhoud();
        return getUtils().maakIdentificatieGroep(
            inhoud.getANummer(),
            inhoud.getBurgerservicenummer(),
            persoon.getHistorie(),
            persoon.getDocumentatie(),
            persoon.getLo3Herkomst());
    }

    @Requirement({Requirements.CEL0220_LB01, Requirements.CEL0230_LB01, Requirements.CEL0240_LB01 })
    private TussenGroep<BrpSamengesteldeNaamInhoud> migreerSamengesteldeNaam(final Lo3Categorie<Lo3PersoonInhoud> persoon) {
        final Lo3PersoonInhoud inhoud = persoon.getInhoud();
        return getUtils().maakSamengesteldeNaamGroep(
            inhoud.getAdellijkeTitelPredikaatCode(),
            inhoud.getVoornamen(),
            inhoud.getVoorvoegselGeslachtsnaam(),
            inhoud.getGeslachtsnaam(),
            persoon.getHistorie(),
            persoon.getDocumentatie(),
            persoon.getLo3Herkomst(),
            true);
    }

    @Requirement({Requirements.CEL6110, Requirements.CEL0220_LB01, Requirements.CEL0230_LB01 })
    @Definitie(Definities.DEF016)
    private BrpNaamgebruikInhoud migreerNaamgebruik(final Lo3PersoonInhoud inhoud, final boolean isDummyPL) {
        final BrpNaamgebruikCode aanduidingNaamgebruik;
        if (!isDummyPL) {
            aanduidingNaamgebruik = getLo3AttribuutConverteerder().converteerLo3AanduidingNaamgebruikCode(inhoud.getAanduidingNaamgebruikCode());
        } else {
            aanduidingNaamgebruik = null;
        }
        final BrpString voornamen = getLo3AttribuutConverteerder().converteerString(inhoud.getVoornamen());
        final BrpString geslachtsnaam = getLo3AttribuutConverteerder().converteerString(inhoud.getGeslachtsnaam());
        final BrpBoolean indAfgeleid = new BrpBoolean(true, null);

        final VoorvoegselScheidingstekenPaar voorvoegselPaar =
                getLo3AttribuutConverteerder().converteerLo3VoorvoegselNaarVoorvoegselScheidingstekenPaar(inhoud.getVoorvoegselGeslachtsnaam());

        BrpNaamgebruikInhoud resultaat = null;
        final BrpAdellijkeTitelCode adellijkeTitel =
                getLo3AttribuutConverteerder().converteerLo3AdellijkeTitelPredikaatCodeNaarBrpAdellijkeTitelCode(inhoud.getAdellijkeTitelPredikaatCode());
        if (isAdellijkeTitel(adellijkeTitel)) {
            resultaat =
                    new BrpNaamgebruikInhoud(
                        aanduidingNaamgebruik,
                        indAfgeleid,
                        null,
                        adellijkeTitel,
                        voornamen,
                        voorvoegselPaar.getVoorvoegsel(),
                        voorvoegselPaar.getScheidingsteken(),
                        geslachtsnaam);
        } else {
            final BrpPredicaatCode predicaatCode =
                    getLo3AttribuutConverteerder().converteerLo3AdellijkeTitelPredikaatCodeNaarBrpPredicaatCode(inhoud.getAdellijkeTitelPredikaatCode());
            if (isPredicaat(predicaatCode)) {
                resultaat =
                        new BrpNaamgebruikInhoud(
                            aanduidingNaamgebruik,
                            indAfgeleid,
                            predicaatCode,
                            null,
                            voornamen,
                            voorvoegselPaar.getVoorvoegsel(),
                            voorvoegselPaar.getScheidingsteken(),
                            geslachtsnaam);

            } else {
                // DEF016 geen predikaat en geen adellijke titel
                if (Validatie.isEenParameterGevuld(
                    aanduidingNaamgebruik,
                    voornamen,
                    voorvoegselPaar.getVoorvoegsel(),
                    voorvoegselPaar.getScheidingsteken(),
                    geslachtsnaam))
                {
                    resultaat =
                            new BrpNaamgebruikInhoud(
                                aanduidingNaamgebruik,
                                indAfgeleid,
                                null,
                                null,
                                voornamen,
                                voorvoegselPaar.getVoorvoegsel(),
                                voorvoegselPaar.getScheidingsteken(),
                                geslachtsnaam);
                }
            }
        }
        return resultaat;
    }

    @Definitie(Definities.DEF014)
    private static boolean isAdellijkeTitel(final BrpAdellijkeTitelCode code) {
        return Validatie.isAttribuutGevuld(code);
    }

    @Definitie(Definities.DEF015)
    private static boolean isPredicaat(final BrpPredicaatCode code) {
        return Validatie.isAttribuutGevuld(code);
    }

    @Requirement({Requirements.CGR03_LB01, Requirements.CGR03_LB02, Requirements.CGR03_LB03 })
    private TussenGroep<BrpGeboorteInhoud> migreerGeboorte(final Lo3Categorie<Lo3PersoonInhoud> persoon) {
        final Lo3PersoonInhoud inhoud = persoon.getInhoud();
        return getUtils().maakGeboorteGroep(
            inhoud.getGeboorteGemeenteCode(),
            inhoud.getGeboorteLandCode(),
            inhoud.getGeboortedatum(),
            persoon.getHistorie(),
            persoon.getDocumentatie(),
            persoon.getLo3Herkomst());
    }

    private TussenGroep<BrpNummerverwijzingInhoud> migreerNummerverwijzing(final Lo3Categorie<Lo3PersoonInhoud> persoon) {
        final Lo3PersoonInhoud inhoud = persoon.getInhoud();
        return getUtils().maakNummerverwijzingGroep(
            inhoud.getVorigANummer(),
            inhoud.getVolgendANummer(),
            persoon.getHistorie(),
            persoon.getDocumentatie(),
            persoon.getLo3Herkomst());
    }
}
