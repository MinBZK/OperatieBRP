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
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBezitBuitenlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LengteHouder;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Signalering;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Deze parser verwerkt de LO3 reisdocument velden in de excel input.
 * 
 */
// CHECKSTYLE:OFF - Fan-out complexity
public final class Lo3ReisdocumentParser extends Lo3CategorieParser<Lo3ReisdocumentInhoud> {
    // CHECKSTYLE:ON

    @Override
    public Lo3Stapel<Lo3ReisdocumentInhoud> parse(final List<Lo3CategorieWaarde> categorieen) {
        final List<Lo3Categorie<Lo3ReisdocumentInhoud>> reisdocumentStapel =
                new ArrayList<Lo3Categorie<Lo3ReisdocumentInhoud>>();

        for (final Lo3CategorieWaarde categorie : categorieen) {

            final Map<Lo3ElementEnum, String> elementen =
                    new HashMap<Lo3ElementEnum, String>(categorie.getElementen());

            final Lo3SoortNederlandsReisdocument soort =
                    Parser.parseLo3SoortNederlandsReisdocument(elementen.remove(Lo3ElementEnum.ELEMENT_3510));
            final String nummer = elementen.remove(Lo3ElementEnum.ELEMENT_3520);
            final Lo3Datum uitgifte = Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_3530));
            final Lo3AutoriteitVanAfgifteNederlandsReisdocument autoriteit =
                    Parser.parseLo3AutoriteitVanAfgifteNederlandsReisdocument(elementen
                            .remove(Lo3ElementEnum.ELEMENT_3540));
            final Lo3Datum einde = Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_3550));
            final Lo3Datum datumInhouding = Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_3560));
            final Lo3AanduidingInhoudingVermissingNederlandsReisdocument aanduidingInhouding =
                    Parser.parseLo3AanduidingInhoudingVermissingNederlandsReisdocument(elementen
                            .remove(Lo3ElementEnum.ELEMENT_3570));
            final Lo3LengteHouder lengte = Parser.parseLo3LengteHouder(elementen.remove(Lo3ElementEnum.ELEMENT_3580));
            final Lo3Signalering signalering =
                    Parser.parseLo3Signalering(elementen.remove(Lo3ElementEnum.ELEMENT_3610));
            final Lo3AanduidingBezitBuitenlandsReisdocument aanduidingBuitenlands =
                    Parser.parseLo3AanduidingBezitBuitenlandsReisdocument(elementen
                            .remove(Lo3ElementEnum.ELEMENT_3710));

            final Lo3Documentatie documentatie =
                    Lo3Documentatie.build(null, null,
                            Parser.parseLo3GemeenteCode(elementen.remove(Lo3ElementEnum.ELEMENT_8210)),
                            Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_8220)),
                            elementen.remove(Lo3ElementEnum.ELEMENT_8230));

            // TODO: procedure wordt nog niet verwerkt

            // final Integer aanduidingGegevensInOnderzoek =
            Parser.parseInteger(elementen.remove(Lo3ElementEnum.ELEMENT_8310));
            // final Lo3Datum datumIngangOnderzoek =
            Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_8320));
            // final Lo3Datum datumEindeOnderzoek =
            Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_8330));

            final Lo3Historie historie = parseLo3Historie(elementen);

            if (!elementen.isEmpty()) {
                throw new OnverwachteElementenExceptie(categorie.getCategorie(), elementen);
            }

            Lo3Categorie<Lo3ReisdocumentInhoud> reisdocument = null;
            try {
                reisdocument =
                        new Lo3Categorie<Lo3ReisdocumentInhoud>(new Lo3ReisdocumentInhoud(soort, nummer, uitgifte,
                                autoriteit, einde, datumInhouding, aanduidingInhouding, lengte, signalering,
                                aanduidingBuitenlands), documentatie, historie, categorie.getLo3Herkomst());
            } catch (final NullPointerException npe) {
                throw new ParseException(npe.getMessage(), npe);
            }

            reisdocumentStapel.add(reisdocument);
        }

        return reisdocumentStapel.isEmpty() ? null : new Lo3Stapel<Lo3ReisdocumentInhoud>(reisdocumentStapel);
    }
}
