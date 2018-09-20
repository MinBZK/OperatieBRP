/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.tabel;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AangeverAdreshoudingRedenWijzigingAdresPaar;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AbstractConversietabelFactory;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.ConversietabelFactory;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.RedenVerkrijgingVerliesPaar;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifte;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentRedenOntbreken;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentSoort;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;

/**
 * Test implementatie die (de meeste) coderingen een op een direct doorgeeft zodat geen dependency op een database nodig
 * is.
 */
public final class ConversietabelFactoryImpl extends AbstractConversietabelFactory implements ConversietabelFactory {

    @Override
    public Conversietabel<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar>
            createAdellijkeTitelPredikaatConversietabel() {
        return new AdellijkeTitelPredikaatConversietabel();
    }

    @Override
    public Conversietabel<Lo3GemeenteCode, BrpPartijCode> createPartijConversietabel() {
        return new PartijConversietabel();
    }

    @Override
    public Conversietabel<Lo3GemeenteCode, BrpGemeenteCode> createGemeenteConversietabel() {
        return new GemeenteConversietabel();
    }

    @Override
    public Conversietabel<Lo3LandCode, BrpLandCode> createLandConversietabel() {
        return new LandConversietabel();
    }

    @Override
    public Conversietabel<Lo3RedenOntbindingHuwelijkOfGpCode, BrpRedenEindeRelatieCode>
            createRedenEindeRelatieConversietabel() {
        return new RedenEindeRelatieConversietabel();
    }

    @Override
    public Conversietabel<Lo3AanduidingVerblijfstitelCode, BrpVerblijfsrechtCode>
            createVerblijfsrechtConversietabel() {
        return new VerblijfsrechtConversietabel();
    }

    @Override
    public Conversietabel<Lo3NationaliteitCode, BrpNationaliteitCode> createNationaliteitConversietabel() {
        return new NationaliteitConversietabel();
    }

    @Override
    public Conversietabel<Lo3RedenNederlandschapCode, RedenVerkrijgingVerliesPaar>
            createRedenVerkrijgingNlNationaliteitConversietabel() {
        return new RedenVerkrijgingVerliesNlNationaliteitConversietabel();
    }

    @Override
    public Conversietabel<Lo3RedenNederlandschapCode, RedenVerkrijgingVerliesPaar>
            createRedenVerliesNlNationaliteitConversietabel() {
        return new RedenVerkrijgingVerliesNlNationaliteitConversietabel();
    }

    @Override
    public Conversietabel<Lo3AangifteAdreshouding, AangeverAdreshoudingRedenWijzigingAdresPaar>
            createAangeverAdreshoudingRedenWijzigingAdresConversietabel() {
        return new AangeverAdreshoudingRedenWijzigingAdresConversietabel();
    }

    @Override
    public Conversietabel<Lo3SoortNederlandsReisdocument, BrpReisdocumentSoort>
            createSoortReisdocumentConversietabel() {
        return new SoortReisdocumentConversietabel();
    }

    @Override
    public Conversietabel<Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpReisdocumentRedenOntbreken>
            createReisdocumentRedenOntbrekenConversietabel() {
        return new ReisdocumentRedenOntbrekenConversietabel();
    }

    @Override
    public Conversietabel<Lo3AutoriteitVanAfgifteNederlandsReisdocument, BrpReisdocumentAutoriteitVanAfgifte>
            createReisdocumentAutoriteitVanAfgifteConversietabel() {
        return new ReisdocumentAutoriteitVanAfgifteConversietabel();
    }

    @Override
    public Conversietabel<Lo3RedenOpschortingBijhoudingCode, BrpRedenOpschortingBijhoudingCode>
            createRedenOpschortingBijhoudingConversietabel() {
        return new RedenOpschortingBijhoudingConversietabel();
    }

    @Override
    public Conversietabel<String, VoorvoegselScheidingstekenPaar> createVoorvoegselScheidingstekenConversietabel() {
        return new VoorvoegselScheidingstekenConversietabel();
    }

    @Override
    public Conversietabel<String, BrpPlaatsCode> createPlaatsConversietabel() {
        return new PlaatsConversietabel();
    }

    @Override
    public Conversietabel<String, String> createNaamOpenbareRuimteConversietabel() {
        return new NaamOpenbareRuimteConversietabel();
    }

}
