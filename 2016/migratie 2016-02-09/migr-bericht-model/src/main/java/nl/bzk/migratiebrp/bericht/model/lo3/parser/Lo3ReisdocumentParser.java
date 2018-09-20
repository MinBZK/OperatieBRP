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
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Signalering;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Deze parser verwerkt de LO3 reisdocument velden in de excel input.
 *
 */

public final class Lo3ReisdocumentParser extends AbstractLo3CategorieParser<Lo3ReisdocumentInhoud> {

    /**
     * Deze methode kan worden gebruikt om te detecteren dat deze stapel moet worden genegeerd conform LO3.9 omdat
     * aanduiding bezit buitenlands reisdocument is gevuld.
     *
     * @param categorieen
     *            de lijst met categoriewaarden die samen een LO3 stapel moeten gaan vormen
     * @return true als de lijst met categoriewaarden {@link Lo3ElementEnum#ELEMENT_3710} bevant, anders false
     */
    public boolean bevatAanduidingBezitNederlandsReisdocument(final List<Lo3CategorieWaarde> categorieen) {
        boolean result = false;
        for (final Lo3CategorieWaarde categorie : categorieen) {
            final Map<Lo3ElementEnum, String> elementen = new HashMap<>(categorie.getElementen());
            if (Parser.bevatLo3AanduidingBezitBuitenlandsReisdocument(elementen, Lo3ElementEnum.ELEMENT_3710)) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public Lo3Stapel<Lo3ReisdocumentInhoud> parse(final List<Lo3CategorieWaarde> categorieen) {
        final List<Lo3Categorie<Lo3ReisdocumentInhoud>> reisdocumentStapel = new ArrayList<>();

        for (final Lo3CategorieWaarde categorie : categorieen) {

            final Map<Lo3ElementEnum, String> elementen = new HashMap<>(categorie.getElementen());
            final Lo3Herkomst lo3Herkomst = categorie.getLo3Herkomst();
            final Lo3Onderzoek lo3Onderzoek = parseLo3Onderzoek(elementen, lo3Herkomst);
            final Lo3CategorieEnum herkomstCategorie = lo3Herkomst.getCategorie();

            final Lo3SoortNederlandsReisdocument soort =
                    Parser.parseLo3SoortNederlandsReisdocument(elementen, Lo3ElementEnum.ELEMENT_3510, herkomstCategorie, lo3Onderzoek);
            final Lo3String nummer = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_3520, herkomstCategorie, lo3Onderzoek);
            final Lo3Datum uitgifte = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_3530, herkomstCategorie, lo3Onderzoek);
            final Lo3AutoriteitVanAfgifteNederlandsReisdocument autoriteit =
                    Parser.parseLo3AutoriteitVanAfgifteNederlandsReisdocument(elementen, Lo3ElementEnum.ELEMENT_3540, herkomstCategorie, lo3Onderzoek);
            final Lo3Datum einde = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_3550, herkomstCategorie, lo3Onderzoek);
            final Lo3Datum datumInhouding = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_3560, herkomstCategorie, lo3Onderzoek);
            final Lo3AanduidingInhoudingVermissingNederlandsReisdocument aanduidingInhouding;
            aanduidingInhouding =
                    Parser.parseLo3AanduidingInhoudingVermissingNederlandsReisdocument(
                        elementen,
                        Lo3ElementEnum.ELEMENT_3570,
                        herkomstCategorie,
                        lo3Onderzoek);
            /*
             * Element 3580 wordt niet meer gebruikt maar mag niet tot fouten leiden omdat oude PL-en nog deze gegevens
             * kunnen bevatten ihgv IV.
             */
            elementen.remove(Lo3ElementEnum.ELEMENT_3580);
            final Lo3Signalering signalering = Parser.parseLo3Signalering(elementen, Lo3ElementEnum.ELEMENT_3610, herkomstCategorie, lo3Onderzoek);

            final Lo3Documentatie lo3Documentatie = parseLo3Documentatie(elementen, herkomstCategorie, lo3Onderzoek);
            final Lo3Historie lo3Historie = parseLo3Historie(elementen, herkomstCategorie, lo3Onderzoek);

            if (!elementen.isEmpty()) {
                throw new OnverwachteElementenExceptie(categorie.getCategorie(), elementen);
            }

            final Lo3Categorie<Lo3ReisdocumentInhoud> reisdocument =
                    new Lo3Categorie<>(new Lo3ReisdocumentInhoud(
                        soort,
                        nummer,
                        uitgifte,
                        autoriteit,
                        einde,
                        datumInhouding,
                        aanduidingInhouding,
                        signalering), lo3Documentatie, lo3Onderzoek, lo3Historie, categorie.getLo3Herkomst());

            reisdocumentStapel.add(reisdocument);
        }

        return reisdocumentStapel.isEmpty() ? null : new Lo3Stapel<>(reisdocumentStapel);
    }
}
