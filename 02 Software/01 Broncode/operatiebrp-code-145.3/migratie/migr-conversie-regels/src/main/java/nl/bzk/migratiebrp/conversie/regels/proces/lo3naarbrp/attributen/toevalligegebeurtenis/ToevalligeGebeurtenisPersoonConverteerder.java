/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.toevalligegebeurtenis;

import java.util.Iterator;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisPersoon;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3PlaatsLandConversieHelper;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3PlaatsLandConversieHelper.BrpPlaatsLand;
import org.springframework.stereotype.Component;

/**
 * Persoon conversie.
 */
@Component
public final class ToevalligeGebeurtenisPersoonConverteerder {

    private final Lo3AttribuutConverteerder lo3AttribuutConverteerder;

    /**
     * Constructor.
     * @param lo3AttribuutConverteerder lo3 attribuut converteerder
     */
    @Inject
    public ToevalligeGebeurtenisPersoonConverteerder(
            final Lo3AttribuutConverteerder lo3AttribuutConverteerder) {
        this.lo3AttribuutConverteerder = lo3AttribuutConverteerder;
    }

    /**
     * Converteer een Lo3Stapel&lt;Lo3PersoonInhoud&gt; naar een BrpToevalligeGebeurtenisPersoon.
     * @param persoon lo3 representatie
     * @return brp representatie
     */
    public BrpToevalligeGebeurtenisPersoon converteer(final Lo3Stapel<Lo3PersoonInhoud> persoon) {
        BrpToevalligeGebeurtenisPersoon result = null;

        if (persoon != null) {
            final Iterator<Lo3Categorie<Lo3PersoonInhoud>> iterator = persoon.getCategorieen().iterator();
            final Lo3Categorie<Lo3PersoonInhoud> categoriePersoonActueel = iterator.next();
            final Lo3Categorie<Lo3PersoonInhoud> categoriePersoonHistorisch;
            if (iterator.hasNext()) {
                categoriePersoonHistorisch = iterator.next();
            } else {
                categoriePersoonHistorisch = categoriePersoonActueel;
            }
            result = maakPersoon(categoriePersoonActueel, categoriePersoonHistorisch);
        }
        return result;
    }

    private BrpToevalligeGebeurtenisPersoon maakPersoon(
            final Lo3Categorie<Lo3PersoonInhoud> categoriePersoonActueel,
            final Lo3Categorie<Lo3PersoonInhoud> categoriePersoonHistorisch) {
        final Lo3PersoonInhoud inhoudActueel = categoriePersoonActueel.getInhoud();
        final Lo3PersoonInhoud inhoudHistorisch = categoriePersoonHistorisch.getInhoud();

        return maakPersoon(
                inhoudActueel.getANummer(),
                inhoudActueel.getBurgerservicenummer(),
                inhoudHistorisch.getAdellijkeTitelPredikaatCode(),
                inhoudHistorisch.getVoornamen(),
                inhoudHistorisch.getVoorvoegselGeslachtsnaam(),
                inhoudHistorisch.getGeslachtsnaam(),
                inhoudHistorisch.getGeboortedatum(),
                inhoudHistorisch.getGeboorteGemeenteCode(),
                inhoudHistorisch.getGeboorteLandCode(),
                inhoudHistorisch.getGeslachtsaanduiding());
    }

    /**
     * Converteer een Lo3HuwelijkOfGpInhoud naar een BrpToevalligeGebeurtenisPersoon.
     * @param inhoud lo3 representatie
     * @return brp representatie
     */
    public BrpToevalligeGebeurtenisPersoon converteer(final Lo3HuwelijkOfGpInhoud inhoud) {
        BrpToevalligeGebeurtenisPersoon result = null;
        if (inhoud != null) {
            result =
                    maakPersoon(
                            inhoud.getaNummer(),
                            inhoud.getBurgerservicenummer(),
                            inhoud.getAdellijkeTitelPredikaatCode(),
                            inhoud.getVoornamen(),
                            inhoud.getVoorvoegselGeslachtsnaam(),
                            inhoud.getGeslachtsnaam(),
                            inhoud.getGeboortedatum(),
                            inhoud.getGeboorteGemeenteCode(),
                            inhoud.getGeboorteLandCode(),
                            inhoud.getGeslachtsaanduiding());
        }
        return result;
    }

    /**
     * Converteer een Lo3OuderInhoud naar een BrpToevalligeGebeurtenisPersoon.
     * @param inhoud lo3 representatie
     * @return brp representatie
     */
    public BrpToevalligeGebeurtenisPersoon converteer(final Lo3OuderInhoud inhoud) {
        BrpToevalligeGebeurtenisPersoon result = null;
        if (inhoud != null) {
            result =
                    maakPersoon(
                            inhoud.getaNummer(),
                            inhoud.getBurgerservicenummer(),
                            inhoud.getAdellijkeTitelPredikaatCode(),
                            inhoud.getVoornamen(),
                            inhoud.getVoorvoegselGeslachtsnaam(),
                            inhoud.getGeslachtsnaam(),
                            inhoud.getGeboortedatum(),
                            inhoud.getGeboorteGemeenteCode(),
                            inhoud.getGeboorteLandCode(),
                            inhoud.getGeslachtsaanduiding());
        }
        return result;
    }

    private BrpToevalligeGebeurtenisPersoon maakPersoon(
            final Lo3String anr,
            final Lo3String bsn,
            final Lo3AdellijkeTitelPredikaatCode predicaatAdellijkeTitel,
            final Lo3String voornaam,
            final Lo3String voorvoegsel,
            final Lo3String geslachtsnaam,
            final Lo3Datum geboorteDatum,
            final Lo3GemeenteCode geboorteGemeente,
            final Lo3LandCode geboorteland,
            final Lo3Geslachtsaanduiding geslachtsaanduiding) {
        final BrpString administratienummer = lo3AttribuutConverteerder.converteerString(anr);
        final BrpString burgerservicenummer = lo3AttribuutConverteerder.converteerString(bsn);

        final BrpPredicaatCode predicaatCode =
                lo3AttribuutConverteerder.converteerLo3AdellijkeTitelPredikaatCodeNaarBrpPredicaatCode(predicaatAdellijkeTitel);
        final BrpString voornamen = lo3AttribuutConverteerder.converteerString(voornaam);
        final VoorvoegselScheidingstekenPaar voorvoegselScheidingstekenPaar =
                lo3AttribuutConverteerder.converteerLo3VoorvoegselNaarVoorvoegselScheidingstekenPaar(voorvoegsel);
        final BrpAdellijkeTitelCode adellijkeTitelCode =
                lo3AttribuutConverteerder.converteerLo3AdellijkeTitelPredikaatCodeNaarBrpAdellijkeTitelCode(predicaatAdellijkeTitel);
        final BrpString geslachtsnaamstam = lo3AttribuutConverteerder.converteerString(geslachtsnaam);

        final BrpDatum geboortedatum = lo3AttribuutConverteerder.converteerDatum(geboorteDatum);
        final BrpPlaatsLand geboortePlaatsLand =
                new Lo3PlaatsLandConversieHelper(geboorteGemeente, geboorteland, lo3AttribuutConverteerder).converteerNaarBrp();

        final BrpGeslachtsaanduidingCode geslachtsaanduidingCode = lo3AttribuutConverteerder.converteerLo3Geslachtsaanduiding(geslachtsaanduiding);

        return new BrpToevalligeGebeurtenisPersoon(
                administratienummer,
                burgerservicenummer,
                predicaatCode,
                voornamen,
                voorvoegselScheidingstekenPaar.getVoorvoegsel(),
                voorvoegselScheidingstekenPaar.getScheidingsteken(),
                adellijkeTitelCode,
                geslachtsnaamstam,
                geboortedatum,
                geboortePlaatsLand.getBrpGemeenteCode(),
                geboortePlaatsLand.getBrpBuitenlandsePlaats(),
                geboortePlaatsLand.getBrpLandOfGebiedCode(),
                geboortePlaatsLand.getBrpOmschrijvingLocatie(),
                geslachtsaanduidingCode);
    }
}
