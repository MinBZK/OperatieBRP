/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AangeverAdreshoudingRedenWijzigingAdresPaar;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AbstractConversietabelFactory;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
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
import nl.moderniseringgba.migratie.synchronisatie.repository.ConversietabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.repository.StamtabelRepository;

import org.springframework.stereotype.Component;

/**
 * De implementatie van de ConversietabelFactory die gebruik maakt van JPA repositories om conversietabellen uit de
 * database in te lezen.
 */
@Component
public final class DalConversietabelFactory extends AbstractConversietabelFactory {

    @Inject
    private ConversietabelRepository conversietabelRepository;

    @Inject
    private StamtabelRepository stamtabelRepository;

    @Override
    public Conversietabel<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar>
            createAdellijkeTitelPredikaatConversietabel() {
        return new AdellijkeTitelPredikaatConversietabel(conversietabelRepository.findAllAdellijkeTitelPredikaat());
    }

    @Override
    public Conversietabel<Lo3RedenOntbindingHuwelijkOfGpCode, BrpRedenEindeRelatieCode>
            createRedenEindeRelatieConversietabel() {
        return new RedenOntbindingHuwelijkPartnerschapConversietabel(
                conversietabelRepository.findAllRedenOntbindingHuwelijkPartnerschap());
    }

    @Override
    public Conversietabel<Lo3AanduidingVerblijfstitelCode, BrpVerblijfsrechtCode>
            createVerblijfsrechtConversietabel() {
        return new VerblijfstitelConversietabel(conversietabelRepository.findAllVerblijfstitel());
    }

    @Override
    public Conversietabel<Lo3RedenNederlandschapCode, RedenVerkrijgingVerliesPaar>
            createRedenVerkrijgingNlNationaliteitConversietabel() {
        return new RedenVerkrijgingVerliesNederlanderschapConversietabel(
                conversietabelRepository.findAllRedenVerkrijgingNlSchap());
    }

    @Override
    public Conversietabel<Lo3RedenNederlandschapCode, RedenVerkrijgingVerliesPaar>
            createRedenVerliesNlNationaliteitConversietabel() {
        return new RedenVerkrijgingVerliesNederlanderschapConversietabel(
                conversietabelRepository.findAllRedenVerliesNlSchap());
    }

    @Override
    public Conversietabel<Lo3AangifteAdreshouding, AangeverAdreshoudingRedenWijzigingAdresPaar>
            createAangeverAdreshoudingRedenWijzigingAdresConversietabel() {
        return new AangeverAdreshoudingRedenWijzigingAdresConversietabel(
                conversietabelRepository.findAllAangifteAdreshouding());
    }

    @Override
    public Conversietabel<Lo3SoortNederlandsReisdocument, BrpReisdocumentSoort>
            createSoortReisdocumentConversietabel() {
        return new SoortNlReisdocumentConversietabel(conversietabelRepository.findAllSoortNlReisdocument());
    }

    @Override
    public Conversietabel<Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpReisdocumentRedenOntbreken>
            createReisdocumentRedenOntbrekenConversietabel() {
        return new RedenVervallenReisdocumentConversietabel(
                conversietabelRepository.findAllRedenInhoudingVermissingReisdocument());
    }

    @Override
    public Conversietabel<Lo3AutoriteitVanAfgifteNederlandsReisdocument, BrpReisdocumentAutoriteitVanAfgifte>
            createReisdocumentAutoriteitVanAfgifteConversietabel() {
        return new AutoriteitVanAfgifteReisdocumentConversietabel(
                conversietabelRepository.findAllAutoriteitVanAfgifte());
    }

    @Override
    public Conversietabel<Lo3RedenOpschortingBijhoudingCode, BrpRedenOpschortingBijhoudingCode>
            createRedenOpschortingBijhoudingConversietabel() {
        return new RedenOpschortingConversietabel(conversietabelRepository.findAllRedenOpschorting());
    }

    @Override
    public Conversietabel<String, VoorvoegselScheidingstekenPaar> createVoorvoegselScheidingstekenConversietabel() {
        return new VoorvoegselConversietabel(conversietabelRepository.findAllVoorvoegsels());
    }

    @Override
    public Conversietabel<Lo3GemeenteCode, BrpPartijCode> createPartijConversietabel() {
        final Set<Integer> partijCodes = new LinkedHashSet<Integer>();
        for (final BigDecimal partijCode : stamtabelRepository.findAllPartijCodes()) {
            partijCodes.add(partijCode.intValue());
        }
        return new PartijConversietabel(partijCodes);
    }

    @Override
    public Conversietabel<Lo3GemeenteCode, BrpGemeenteCode> createGemeenteConversietabel() {
        return new GemeenteConversietabel(stamtabelRepository.findAllGemeenteCodes());
    }

    @Override
    public Conversietabel<Lo3LandCode, BrpLandCode> createLandConversietabel() {
        final Set<Integer> landCodes = new LinkedHashSet<Integer>();
        for (final BigDecimal landCode : stamtabelRepository.findAllLandCodes()) {
            landCodes.add(landCode.intValue());
        }
        return new LandConversietabel(landCodes);
    }

    @Override
    public Conversietabel<Lo3NationaliteitCode, BrpNationaliteitCode> createNationaliteitConversietabel() {
        final Set<Integer> nationaliteitCodes = new LinkedHashSet<Integer>();
        for (final BigDecimal nationaliteitCode : stamtabelRepository.findAllNationaliteitCodes()) {
            nationaliteitCodes.add(nationaliteitCode.intValue());
        }
        return new NationaliteitConversietabel(nationaliteitCodes);
    }

    @Override
    public Conversietabel<String, BrpPlaatsCode> createPlaatsConversietabel() {
        return new PlaatsConversietabel(stamtabelRepository.findAllPlaatsnamen());
    }

    @Override
    public Conversietabel<String, String> createNaamOpenbareRuimteConversietabel() {
        return new NaamOpenbareRuimteConversietabel();
    }
}
