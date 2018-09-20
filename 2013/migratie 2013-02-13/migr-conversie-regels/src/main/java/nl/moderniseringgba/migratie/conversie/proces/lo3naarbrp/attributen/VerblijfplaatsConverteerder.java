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
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAangeverAdreshoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpFunctieAdresCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenWijzigingAdresCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAdresInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBijhoudingsgemeenteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpImmigratieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

import org.springframework.stereotype.Component;

/**
 * Deze service levert de functionaliteit om de LO3 categorie inhoud Verblijfplaats naar BRP inhoud te converteren.
 * 
 */
@Component
@Requirement({ Requirements.CCA08, Requirements.CCA08_LB01, Requirements.CCA08_LB02, Requirements.CCA08_LB03,
        Requirements.CCA08_LB04, Requirements.CCA08_LB05 })
public class VerblijfplaatsConverteerder {

    @Inject
    private Lo3AttribuutConverteerder converteerder;

    /**
     * Converteer de verblijfplaats.
     * 
     * @param verblijfplaatsStapel
     *            de LO3 verblijfplaats stapel
     * @param migratiePersoonslijstBuilder
     *            migratie persoonlijst builder
     * @throws NullPointerException
     *             als verblijfplaatsStapel of migratiePersoonslijstBuilder null is
     */
    public final void converteer(
            final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel,
            final MigratiePersoonslijstBuilder migratiePersoonslijstBuilder) {
        if (migratiePersoonslijstBuilder == null) {
            throw new NullPointerException(
                    "migratiePersoonslijstBuilder mag niet null zijn voor VerblijfplaatsConverteerder.converteer");
        }

        // Deze LO3 stapel converteert naar 3 stapels in het BRP model
        final List<MigratieGroep<BrpAdresInhoud>> migratieAdresList = new ArrayList<MigratieGroep<BrpAdresInhoud>>();
        final List<MigratieGroep<BrpBijhoudingsgemeenteInhoud>> migratieBijhoudingsgemeenteList =
                new ArrayList<MigratieGroep<BrpBijhoudingsgemeenteInhoud>>();
        final List<MigratieGroep<BrpImmigratieInhoud>> migratieImmigratieList =
                new ArrayList<MigratieGroep<BrpImmigratieInhoud>>();

        for (final Lo3Categorie<Lo3VerblijfplaatsInhoud> lo3VerblijfplaatsCategorie : verblijfplaatsStapel) {

            migratieAdresList.add(converteerAdres(lo3VerblijfplaatsCategorie));

            // CCA08-LB04: Immigratie
            migratieImmigratieList.add(converteerImmigratie(lo3VerblijfplaatsCategorie));

            // CCA08-LB05: Bijhoudingsgemeente
            migratieBijhoudingsgemeenteList.add(converteerBijhoudingsgemeente(lo3VerblijfplaatsCategorie));
        }

        migratiePersoonslijstBuilder.adresStapel(new MigratieStapel<BrpAdresInhoud>(migratieAdresList));
        if (!ConverteerderUtils.isLijstMetAlleenLegeInhoud(migratieImmigratieList)) {
            migratiePersoonslijstBuilder.immigratieStapel(new MigratieStapel<BrpImmigratieInhoud>(
                    migratieImmigratieList));
        }

        if (!ConverteerderUtils.isLijstMetAlleenLegeInhoud(migratieBijhoudingsgemeenteList)) {
            migratiePersoonslijstBuilder.bijhoudingsgemeenteStapel(new MigratieStapel<BrpBijhoudingsgemeenteInhoud>(
                    migratieBijhoudingsgemeenteList));
        }
    }

    @Definitie({ Definities.DEF033, Definities.DEF034 })
    private MigratieGroep<BrpAdresInhoud> converteerAdres(
            final Lo3Categorie<Lo3VerblijfplaatsInhoud> lo3VerblijfplaatsCategorie) {
        final Lo3VerblijfplaatsInhoud lo3Verblijfplaats = lo3VerblijfplaatsCategorie.getInhoud();

        final BrpFunctieAdresCode functieAdresCode =
                converteerder.converteerLo3FunctieAdres(lo3Verblijfplaats.getFunctieAdres());
        final BrpRedenWijzigingAdresCode redenWijzigingAdresCode =
                converteerder.converteerLo3AangifteAdreshoudingNaarBrpRedenWijziging(lo3Verblijfplaats
                        .getAangifteAdreshouding());
        final BrpAangeverAdreshoudingCode aangeverAdreshoudingCode =
                converteerder.converteerLo3AangifteAdreshoudingNaarBrpAangeverAdreshouding(lo3Verblijfplaats
                        .getAangifteAdreshouding());
        final BrpDatum datumAanvangAdreshouding =
                converteerder.converteerDatum(lo3Verblijfplaats.getAanvangAdreshouding());
        final BrpGemeenteCode gemeenteCode =
                converteerder.converteerLo3GemeenteCode(lo3Verblijfplaats.getGemeenteInschrijving());
        final String afgekorteNaamOpenbareRuimte =
                lo3Verblijfplaats.isPuntAdres() ? null : lo3Verblijfplaats.getStraatnaam();
        final Integer huisnummer = converteerder.converteerHuisnummer(lo3Verblijfplaats.getHuisnummer());
        final BrpPlaatsCode plaatsCode =
                converteerder.converteerLo3Woonplaatsnaam(lo3Verblijfplaats.getWoonplaatsnaam());
        final BrpAanduidingBijHuisnummerCode locatieTovAdres =
                converteerder.converteerAanduidingBijHuisnummer(lo3Verblijfplaats.getAanduidingHuisnummer());

        final String adresBuitenland1;
        final String adresBuitenland2;
        final String adresBuitenland3;

        if (ValidationUtils.isEmpty(lo3Verblijfplaats.getAdresBuitenland1())
                && !ValidationUtils.isEmpty(lo3Verblijfplaats.getAdresBuitenland2())
                && ValidationUtils.isEmpty(lo3Verblijfplaats.getAdresBuitenland3())) {
            // DEF033
            adresBuitenland1 = lo3Verblijfplaats.getAdresBuitenland2();
            adresBuitenland2 = null;
            adresBuitenland3 = null;

        } else {
            // DEF034
            adresBuitenland1 = lo3Verblijfplaats.getAdresBuitenland1();
            adresBuitenland2 = lo3Verblijfplaats.getAdresBuitenland2();
            adresBuitenland3 = lo3Verblijfplaats.getAdresBuitenland3();
        }

        final BrpLandCode landCode =
                converteerder.converteerLo3LandCode(lo3Verblijfplaats.isNederlandsAdres() ? Lo3LandCode.NEDERLAND
                        : lo3Verblijfplaats.getLandWaarnaarVertrokken());
        final boolean buitenland = !BrpLandCode.NEDERLAND.equals(landCode);

        final BrpDatum datumVertrekuitNederland =
                converteerder.converteerDatum(lo3Verblijfplaats.getDatumVertrekUitNederland());

        final BrpAdresInhoud inhoud =
                new BrpAdresInhoud(functieAdresCode, redenWijzigingAdresCode, aangeverAdreshoudingCode,
                        datumAanvangAdreshouding, lo3Verblijfplaats.getIdentificatiecodeVerblijfplaats(),
                        lo3Verblijfplaats.getIdentificatiecodeNummeraanduiding(), buitenland ? null : gemeenteCode,
                        lo3Verblijfplaats.getNaamOpenbareRuimte(), afgekorteNaamOpenbareRuimte,
                        lo3Verblijfplaats.getGemeenteDeel(), huisnummer, lo3Verblijfplaats.getHuisletter(),
                        lo3Verblijfplaats.getHuisnummertoevoeging(), lo3Verblijfplaats.getPostcode(), plaatsCode,
                        locatieTovAdres, lo3Verblijfplaats.getLocatieBeschrijving(), adresBuitenland1,
                        adresBuitenland2, adresBuitenland3, null, null, null, landCode, datumVertrekuitNederland);

        return new MigratieGroep<BrpAdresInhoud>(inhoud, lo3VerblijfplaatsCategorie.getHistorie(),
                lo3VerblijfplaatsCategorie.getDocumentatie(), lo3VerblijfplaatsCategorie.getLo3Herkomst());
    }

    private MigratieGroep<BrpImmigratieInhoud> converteerImmigratie(
            final Lo3Categorie<Lo3VerblijfplaatsInhoud> lo3VerblijfplaatsCategorie) {
        final Lo3VerblijfplaatsInhoud lo3Verblijfplaats = lo3VerblijfplaatsCategorie.getInhoud();

        final BrpLandCode landVanwaarIngeschreven =
                converteerder.converteerLo3LandCode(lo3Verblijfplaats.getLandVanwaarIngeschreven());
        final BrpDatum datumVestigingInNederland =
                converteerder.converteerDatum(lo3Verblijfplaats.getVestigingInNederland());

        final BrpImmigratieInhoud inhoud =
                new BrpImmigratieInhoud(landVanwaarIngeschreven, datumVestigingInNederland);

        return new MigratieGroep<BrpImmigratieInhoud>(inhoud, lo3VerblijfplaatsCategorie.getHistorie(),
                lo3VerblijfplaatsCategorie.getDocumentatie(), lo3VerblijfplaatsCategorie.getLo3Herkomst());
    }

    private MigratieGroep<BrpBijhoudingsgemeenteInhoud> converteerBijhoudingsgemeente(
            final Lo3Categorie<Lo3VerblijfplaatsInhoud> lo3VerblijfplaatsCategorie) {
        final Lo3VerblijfplaatsInhoud lo3Verblijfplaats = lo3VerblijfplaatsCategorie.getInhoud();

        final BrpGemeenteCode bijhoudingsgemeenteCode =
                converteerder.converteerLo3GemeenteCode(lo3Verblijfplaats.getGemeenteInschrijving());
        final BrpDatum datumInschrijvingInGemeente =
                converteerder.converteerDatum(lo3Verblijfplaats.getDatumInschrijving());
        final Boolean onverwerktDocumentAanwezig =
                converteerder.converteerIndicatieDocument(lo3Verblijfplaats.getIndicatieDocument());

        final BrpBijhoudingsgemeenteInhoud inhoud =
                new BrpBijhoudingsgemeenteInhoud(bijhoudingsgemeenteCode, datumInschrijvingInGemeente,
                        onverwerktDocumentAanwezig);

        return new MigratieGroep<BrpBijhoudingsgemeenteInhoud>(inhoud, lo3VerblijfplaatsCategorie.getHistorie(),
                lo3VerblijfplaatsCategorie.getDocumentatie(), lo3VerblijfplaatsCategorie.getLo3Herkomst());
    }
}
