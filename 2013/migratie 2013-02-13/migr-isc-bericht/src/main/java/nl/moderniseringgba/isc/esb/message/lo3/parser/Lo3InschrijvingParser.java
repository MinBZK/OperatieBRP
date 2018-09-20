/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Deze parser verwerkt de LO3 categorie inschrijving in de excel input.
 * 
 */
public final class Lo3InschrijvingParser extends Lo3CategorieParser<Lo3InschrijvingInhoud> {

    @Override
    public Lo3Stapel<Lo3InschrijvingInhoud> parse(final List<Lo3CategorieWaarde> categorieen) {
        Lo3Categorie<Lo3InschrijvingInhoud> inschrijvingCategorie = null;

        for (final Lo3CategorieWaarde categorie : categorieen) {

            if (inschrijvingCategorie != null) {
                throw new ParseException(String.format("Onverwachte historie voor categorie %s",
                        categorie.getCategorie()));
            }
            final Map<Lo3ElementEnum, String> elementen =
                    new HashMap<Lo3ElementEnum, String>(categorie.getElementen());

            // 66 Blokkering
            final Lo3Datum datumIngangBlokkering =
                    Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_6620));

            // 67 Opschorting
            final Lo3Datum datumOpschortingBijhouding =
                    Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_6710));
            final Lo3RedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode =
                    Parser.parseLo3RedenOpschortingBijhoudingCode(elementen.remove(Lo3ElementEnum.ELEMENT_6720));

            // 68 Opname
            final Lo3Datum datumEersteInschrijving =
                    Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_6810));

            // 69 Gemeente PK
            final Lo3GemeenteCode gemeentePKCode =
                    Parser.parseLo3GemeenteCode(elementen.remove(Lo3ElementEnum.ELEMENT_6910));

            // 70 Geheim
            final Lo3IndicatieGeheimCode indicatieGeheimCode =
                    Parser.parseLo3IndicatieGeheimCode(elementen.remove(Lo3ElementEnum.ELEMENT_7010));

            // 80 Synchroniciteit
            final Integer versienummer = Parser.parseInteger(elementen.remove(Lo3ElementEnum.ELEMENT_8010));
            final Lo3Datumtijdstempel datumtijdstempel =
                    Parser.parseLo3Datumtijdstempel(elementen.remove(Lo3ElementEnum.ELEMENT_8020));

            // 87 PK-conversie
            final Lo3IndicatiePKVolledigGeconverteerdCode indicatiePKVolledigGeconverteerdCode =
                    Parser.parseLo3IndicatiePKVolledigGeconverteerdCode(elementen.remove(Lo3ElementEnum.ELEMENT_8710));

            if (!elementen.isEmpty()) {
                throw new OnverwachteElementenExceptie(categorie.getCategorie(), elementen);
            }

            inschrijvingCategorie =
                    new Lo3Categorie<Lo3InschrijvingInhoud>(new Lo3InschrijvingInhoud(datumIngangBlokkering,
                            datumOpschortingBijhouding, redenOpschortingBijhoudingCode, datumEersteInschrijving,
                            gemeentePKCode, indicatieGeheimCode, versienummer, datumtijdstempel,
                            indicatiePKVolledigGeconverteerdCode), null, Lo3Historie.NULL_HISTORIE,
                            categorie.getLo3Herkomst());
        }

        if (inschrijvingCategorie == null) {
            return null;
        } else {
            final List<Lo3Categorie<Lo3InschrijvingInhoud>> inschrijvingen =
                    new ArrayList<Lo3Categorie<Lo3InschrijvingInhoud>>();
            inschrijvingen.add(inschrijvingCategorie);
            return new Lo3Stapel<Lo3InschrijvingInhoud>(inschrijvingen);
        }
    }
}
