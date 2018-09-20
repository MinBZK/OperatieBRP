/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Character;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Huisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Deze parser verwerkt de LO3 verblijfsplaats velden in de excel input.
 *
 */

public final class Lo3VerblijfplaatsParser extends AbstractLo3CategorieParser<Lo3VerblijfplaatsInhoud> {

    @Override
    public Lo3Stapel<Lo3VerblijfplaatsInhoud> parse(final List<Lo3CategorieWaarde> categorieen) {
        final List<Lo3Categorie<Lo3VerblijfplaatsInhoud>> verblijfplaatsList = new ArrayList<>();

        for (final Lo3CategorieWaarde categorie : categorieen) {
            final Map<Lo3ElementEnum, String> elementen = new HashMap<>(categorie.getElementen());
            final Lo3Herkomst lo3Herkomst = categorie.getLo3Herkomst();
            final Lo3Onderzoek lo3Onderzoek = parseLo3Onderzoek(elementen, lo3Herkomst);
            final Lo3CategorieEnum herkomstCategorie = lo3Herkomst.getCategorie();

            final Lo3GemeenteCode gemeenteInschrijving =
                    Parser.parseLo3GemeenteCode(elementen, Lo3ElementEnum.ELEMENT_0910, herkomstCategorie, lo3Onderzoek);
            final Lo3Datum datumInschrijving = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_0920, herkomstCategorie, lo3Onderzoek);
            final Lo3FunctieAdres functieAdres = Parser.parseLo3FunctieAdres(elementen, Lo3ElementEnum.ELEMENT_1010, herkomstCategorie, lo3Onderzoek);
            final Lo3String gemeenteDeel = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_1020, herkomstCategorie, lo3Onderzoek);
            final Lo3Datum aanvangAdreshouding = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_1030, herkomstCategorie, lo3Onderzoek);
            final Lo3String straatnaam = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_1110, herkomstCategorie, lo3Onderzoek);
            final Lo3String naamOpenbareRuimte = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_1115, herkomstCategorie, lo3Onderzoek);
            final Lo3Huisnummer huisnummer = Parser.parseLo3Huisnummer(elementen, Lo3ElementEnum.ELEMENT_1120, herkomstCategorie, lo3Onderzoek);
            final Lo3Character huisletter = Parser.parseLo3Character(elementen, Lo3ElementEnum.ELEMENT_1130, herkomstCategorie, lo3Onderzoek);
            final Lo3String huisnummertoevoeging = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_1140, herkomstCategorie, lo3Onderzoek);
            final Lo3AanduidingHuisnummer aanduidingHuisnummer =
                    Parser.parseLo3AanduidingHuisnummer(elementen, Lo3ElementEnum.ELEMENT_1150, herkomstCategorie, lo3Onderzoek);
            final Lo3String postcode = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_1160, herkomstCategorie, lo3Onderzoek);
            final Lo3String woonplaatsnaam = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_1170, herkomstCategorie, lo3Onderzoek);
            final Lo3String identificatiecodeVerblijfplaats =
                    Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_1180, herkomstCategorie, lo3Onderzoek);
            final Lo3String identificatiecodeNummeraanduiding =
                    Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_1190, herkomstCategorie, lo3Onderzoek);
            final Lo3String locatieBeschrijving = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_1210, herkomstCategorie, lo3Onderzoek);
            final Lo3LandCode landAdresBuitenland = Parser.parseLo3LandCode(elementen, Lo3ElementEnum.ELEMENT_1310, herkomstCategorie, lo3Onderzoek);
            final Lo3Datum vertrekUitNederland = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_1320, herkomstCategorie, lo3Onderzoek);
            final Lo3String adresBuitenland1 = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_1330, herkomstCategorie, lo3Onderzoek);
            final Lo3String adresBuitenland2 = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_1340, herkomstCategorie, lo3Onderzoek);
            final Lo3String adresBuitenland3 = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_1350, herkomstCategorie, lo3Onderzoek);
            final Lo3LandCode landVanwaarIngeschreven = Parser.parseLo3LandCode(elementen, Lo3ElementEnum.ELEMENT_1410, herkomstCategorie, lo3Onderzoek);
            final Lo3Datum vestigingInNederland = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_1420, herkomstCategorie, lo3Onderzoek);
            final Lo3AangifteAdreshouding aangifteAdreshouding =
                    Parser.parseLo3AangifteAdreshouding(elementen, Lo3ElementEnum.ELEMENT_7210, herkomstCategorie, lo3Onderzoek);
            final Lo3IndicatieDocument indicatieDocument =
                    Parser.parseLo3IndicatieDocument(elementen, Lo3ElementEnum.ELEMENT_7510, herkomstCategorie, lo3Onderzoek);

            final Lo3Documentatie lo3Documentatie = parseLo3RNIDeelnemer(elementen, herkomstCategorie, lo3Onderzoek);
            final Lo3Historie lo3Historie = parseLo3Historie(elementen, herkomstCategorie, lo3Onderzoek);

            if (!elementen.isEmpty()) {
                throw new OnverwachteElementenExceptie(categorie.getCategorie(), elementen);
            }

            final Lo3Categorie<Lo3VerblijfplaatsInhoud> verblijfplaats =
                    new Lo3Categorie<>(new Lo3VerblijfplaatsInhoud(
                        gemeenteInschrijving,
                        datumInschrijving,
                        functieAdres,
                        gemeenteDeel,
                        aanvangAdreshouding,
                        straatnaam,
                        naamOpenbareRuimte,
                        huisnummer,
                        huisletter,
                        huisnummertoevoeging,
                        aanduidingHuisnummer,
                        postcode,
                        woonplaatsnaam,
                        identificatiecodeVerblijfplaats,
                        identificatiecodeNummeraanduiding,
                        locatieBeschrijving,
                        landAdresBuitenland,
                        vertrekUitNederland,
                        adresBuitenland1,
                        adresBuitenland2,
                        adresBuitenland3,
                        landVanwaarIngeschreven,
                        vestigingInNederland,
                        aangifteAdreshouding,
                        indicatieDocument), lo3Documentatie, lo3Onderzoek, lo3Historie, categorie.getLo3Herkomst());
            verblijfplaatsList.add(verblijfplaats);
        }
        return verblijfplaatsList.isEmpty() ? null : new Lo3Stapel<>(verblijfplaatsList);
    }
}
