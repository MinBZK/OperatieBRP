/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import javax.inject.Inject;
import nl.bzk.brp.gba.dataaccess.ConversieTabelRepository;
import nl.bzk.brp.gba.dataaccess.StamTabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpProtocolleringsniveauCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AangeverRedenWijzigingVerblijfPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.AbstractConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch.VerstrekkingsbeperkingConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;

/**
 * LO3 Conversie tabel factory voor gebruik in de conversie van BRP naar LO3.
 */
public final class Lo3ConversieTabelFactory extends AbstractConversietabelFactory {

    @Inject
    private ConversieTabelRepository conversieTabelRepository;
    @Inject
    private StamTabelRepository stamTabelRepository;

    @Override
    public Conversietabel<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar> createAdellijkeTitelPredikaatConversietabel() {
        return new AdellijkeTitelPredikaatConversietabel(conversieTabelRepository.findAllAdellijkeTitelPredikaat());
    }

    @Override
    public Conversietabel<Lo3RedenOntbindingHuwelijkOfGpCode, BrpRedenEindeRelatieCode> createRedenEindeRelatieConversietabel() {
        return new RedenOntbindingHuwelijkPartnerschapConversietabel(conversieTabelRepository.findAllRedenOntbindingHuwelijkPartnerschap());
    }

    @Override
    public Conversietabel<Lo3AanduidingVerblijfstitelCode, BrpVerblijfsrechtCode> createVerblijfsrechtConversietabel() {
        return new VerblijfsrechtConversietabel(stamTabelRepository.findAllAanduidingVerblijfsrecht());
    }

    @Override
    public Conversietabel<Lo3RedenNederlandschapCode, BrpRedenVerkrijgingNederlandschapCode> createRedenOpnameNationaliteitConversietabel() {
        return new RedenVerkrijgingNederlanderschapConversietabel(stamTabelRepository.findAllVerkrijgingNLNationaliteit());
    }

    @Override
    public Conversietabel<Lo3RedenNederlandschapCode, BrpRedenVerliesNederlandschapCode> createRedenBeeindigingNationaliteitConversietabel() {
        return new RedenVerliesNederlanderschapConversietabel(stamTabelRepository.findAllVerliesNLNationaliteit());
    }

    @Override
    public Conversietabel<Lo3AangifteAdreshouding, AangeverRedenWijzigingVerblijfPaar> createAangeverRedenWijzigingVerblijfConversietabel() {
        return new AangeverRedenWijzigingVerblijfConversietabel(conversieTabelRepository.findAllAangifteAdreshouding());
    }

    @Override
    public Conversietabel<Lo3SoortNederlandsReisdocument, BrpSoortNederlandsReisdocumentCode> createSoortReisdocumentConversietabel() {
        return new SoortNlReisdocumentConversietabel(conversieTabelRepository.findAllSoortNlReisdocument());
    }

    @Override
    public Conversietabel<Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpAanduidingInhoudingOfVermissingReisdocumentCode> createAanduidingInhoudingVermissingReisdocumentConversietabel() {
        return new AanduidingInhoudingVermissingReisdocumentConversietabel(conversieTabelRepository.findAllAanduidingInhoudingVermissingReisdocument());
    }

    @Override
    public Conversietabel<Lo3RedenOpschortingBijhoudingCode, BrpNadereBijhoudingsaardCode> createRedenOpschortingBijhoudingConversietabel() {
        return new RedenOpschortingConversietabel(conversieTabelRepository.findAllRedenOpschorting());
    }

    @Override
    public Conversietabel<Lo3RNIDeelnemerCode, BrpPartijCode> createRNIDeelnemerConversietabel() {
        return new RNIDeelnemerConversietabel(conversieTabelRepository.findAllRNIDeelnemer());
    }

    @Override
    public Conversietabel<Lo3String, VoorvoegselScheidingstekenPaar> createVoorvoegselScheidingstekenConversietabel() {
        return new VoorvoegselConversietabel(conversieTabelRepository.findAllVoorvoegsel());
    }

    @Override
    public Conversietabel<String, String> createWoonplaatsnaamConversietabel() {
        return new WoonplaatsConversietabel(stamTabelRepository.findAllPlaats());
    }

    @Override
    public Conversietabel<Lo3GemeenteCode, BrpPartijCode> createPartijConversietabel() {
        return new PartijConversietabel(stamTabelRepository.findAllGemeente());
    }

    @Override
    public Conversietabel<Lo3GemeenteCode, BrpGemeenteCode> createGemeenteConversietabel() {
        return new GemeenteConversietabel(stamTabelRepository.findAllGemeenteCodes());
    }

    @Override
    public Conversietabel<Lo3LandCode, BrpLandOfGebiedCode> createLandConversietabel() {
        return new LandConversietabel(stamTabelRepository.findAllLandCodes());
    }

    @Override
    public Conversietabel<Lo3NationaliteitCode, BrpNationaliteitCode> createNationaliteitConversietabel() {
        return new NationaliteitConversietabel(stamTabelRepository.findAllNationaliteitCodes());
    }

    @Override
    public Conversietabel<Character, BrpSoortDocumentCode> createSoortRegisterSoortDocumentConversietabel() {
        return new SoortRegisterSoortDocumentConversietabel();
    }

    @Override
    public Conversietabel<String, String> createLo3RubriekConversietabel() {
        // LO3 rubriek conversie tabel is niet nodig bij converteren persoonlijst.
        throw new UnsupportedOperationException("Lo3 rubriek conversietabel niet geimplementeerd");
    }

    @Override
    public Conversietabel<Integer, BrpProtocolleringsniveauCode> createVerstrekkingsbeperkingConversietabel() {
        return new VerstrekkingsbeperkingConversietabel();
    }

}
