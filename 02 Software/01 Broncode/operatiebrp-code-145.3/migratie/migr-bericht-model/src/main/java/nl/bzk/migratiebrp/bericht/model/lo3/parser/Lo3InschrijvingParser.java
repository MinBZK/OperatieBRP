/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Deze parser verwerkt de LO3 categorie inschrijving in de excel input.
 */
public final class Lo3InschrijvingParser extends AbstractLo3CategorieParser<Lo3InschrijvingInhoud> {

    @Override
    public Lo3Stapel<Lo3InschrijvingInhoud> parse(final List<Lo3CategorieWaarde> categorieen) {
        Lo3Categorie<Lo3InschrijvingInhoud> inschrijvingCategorie = null;
        for (final Lo3CategorieWaarde categorie : categorieen) {
            if (inschrijvingCategorie != null) {
                throw new ParseException(String.format("Onverwachte historie voor categorie %s", categorie.getCategorie()));
            }
            final EnumMap<Lo3ElementEnum, String> elementen = new EnumMap<>(categorie.getElementen());

            // 66 Blokkering
            final Lo3Datum datumIngangBlokkering = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_6620, null, null);

            // 67 Opschorting
            final Lo3Datum datumOpschortingBijhouding = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_6710, null, null);
            final Lo3RedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode =
                    Parser.parseLo3RedenOpschortingBijhoudingCode(elementen, Lo3ElementEnum.ELEMENT_6720, null, null);

            // 68 Opname
            final Lo3Datum datumEersteInschrijving = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_6810, null, null);

            // 69 Gemeente PK
            final Lo3GemeenteCode gemeentePKCode = Parser.parseLo3GemeenteCode(elementen, Lo3ElementEnum.ELEMENT_6910, null, null);

            // 70 Geheim
            final Lo3IndicatieGeheimCode indicatieGeheimCode = Parser.parseLo3IndicatieGeheimCode(elementen, Lo3ElementEnum.ELEMENT_7010, null, null);

            // 71 Verificatie
            final Lo3Datum datumVerificatie = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_7110, null, null);
            final Lo3String omschrijvingVerificatie = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_7120, null, null);

            // 80 Synchroniciteit
            final Lo3Integer versienummer = Parser.parseLo3Integer(elementen, Lo3ElementEnum.ELEMENT_8010, null, null);
            final Lo3Datumtijdstempel datumtijdstempel = Parser.parseLo3Datumtijdstempel(elementen, Lo3ElementEnum.ELEMENT_8020);

            // 87 PK-conversie
            final Lo3IndicatiePKVolledigGeconverteerdCode indicatiePKVolledigGeconverteerdCode =
                    Parser.parseLo3IndicatiePKVolledigGeconverteerdCode(elementen, Lo3ElementEnum.ELEMENT_8710, null, null);

            final Lo3Documentatie lo3Documentatie = parseLo3Documentatie(elementen, null, null);

            if (!elementen.isEmpty()) {
                throw new OnverwachteElementenExceptie(categorie.getCategorie(), elementen);
            }

            inschrijvingCategorie =
                    new Lo3Categorie<>(
                            new Lo3InschrijvingInhoud(
                                    datumIngangBlokkering,
                                    datumOpschortingBijhouding,
                                    redenOpschortingBijhoudingCode,
                                    datumEersteInschrijving,
                                    gemeentePKCode,
                                    indicatieGeheimCode,
                                    datumVerificatie,
                                    omschrijvingVerificatie,
                                    versienummer,
                                    datumtijdstempel,
                                    indicatiePKVolledigGeconverteerdCode),
                            lo3Documentatie,
                            null,
                            new Lo3Historie(null, null, null),
                            categorie.getLo3Herkomst());
        }

        if (inschrijvingCategorie == null) {
            return null;
        } else {
            final List<Lo3Categorie<Lo3InschrijvingInhoud>> inschrijvingen = new ArrayList<>();
            inschrijvingen.add(inschrijvingCategorie);
            return new Lo3Stapel<>(inschrijvingen);
        }
    }
}
