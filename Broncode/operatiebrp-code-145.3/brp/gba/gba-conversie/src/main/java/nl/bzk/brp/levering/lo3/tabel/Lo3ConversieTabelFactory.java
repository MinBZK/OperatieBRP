/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AutoriteitAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.brp.gba.dataaccess.conversie.AanduidingInhoudingVermissingReisdocumentRepository;
import nl.bzk.brp.gba.dataaccess.conversie.AangifteAdreshoudingRepository;
import nl.bzk.brp.gba.dataaccess.conversie.AdellijkeTitelPredikaatRepository;
import nl.bzk.brp.gba.dataaccess.conversie.AutoriteitVanAfgifteBuitenlandsPersoonsnummerRepository;
import nl.bzk.brp.gba.dataaccess.conversie.RNIDeelnemerRepository;
import nl.bzk.brp.gba.dataaccess.conversie.RedenOntbindingHuwelijkPartnerschapRepository;
import nl.bzk.brp.gba.dataaccess.conversie.RedenOpschortingRepository;
import nl.bzk.brp.gba.dataaccess.conversie.SoortNlReisdocumentRepository;
import nl.bzk.brp.gba.dataaccess.conversie.VoorvoegselConversieRepository;
import nl.bzk.brp.gba.dataaccess.stam.GemeenteRepository;
import nl.bzk.brp.gba.dataaccess.stam.LandOfGebiedRepository;
import nl.bzk.brp.gba.dataaccess.stam.NationaliteitRepository;
import nl.bzk.brp.gba.dataaccess.stam.PlaatsRepository;
import nl.bzk.brp.gba.dataaccess.stam.RedenVerkrijgingNLNationaliteitRepository;
import nl.bzk.brp.gba.dataaccess.stam.RedenVerliesNLNationaliteitRepository;
import nl.bzk.brp.gba.dataaccess.stam.VerblijfsrechtRepository;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer;
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

    private AanduidingInhoudingVermissingReisdocumentRepository aanduidingInhoudingVermissingReisdocumentRepository;
    private AangifteAdreshoudingRepository aangifteAdreshoudingRepository;
    private AdellijkeTitelPredikaatRepository adellijkeTitelPredikaatRepository;
    private RedenOntbindingHuwelijkPartnerschapRepository redenOntbindingHuwelijkPartnerschapRepository;
    private RedenOpschortingRepository redenOpschortingRepository;
    private RNIDeelnemerRepository rniDeelnemerRepository;
    private SoortNlReisdocumentRepository soortNlReisdocumentRepository;
    private VoorvoegselConversieRepository voorvoegselConversieRepository;

    private GemeenteRepository gemeenteRepository;
    private LandOfGebiedRepository landOfGebiedRepository;
    private NationaliteitRepository nationaliteitRepository;
    private PlaatsRepository plaatsRepository;
    private RedenVerkrijgingNLNationaliteitRepository redenVerkrijgingNLNationaliteitRepository;
    private RedenVerliesNLNationaliteitRepository redenVerliesNLNationaliteitRepository;
    private VerblijfsrechtRepository verblijfsrechtRepository;
    private AutoriteitVanAfgifteBuitenlandsPersoonsnummerRepository autoriteitVanAfgifteBuitenlandsPersoonsnummerRepository;

    /**
     * Constructor.
     * @param gbaGemeenteRepository gemeente repo
     * @param gbaLandOfGebiedRepository land gebied repo
     * @param gbaNationaliteitRepository nationaliteit repo
     * @param gbaPlaatsRepository plaats repo
     * @param gbaRedenVerkrijgingNLNationaliteitRepository reden verkrijging nl nationaliteit repo
     * @param gbaRedenVerliesNLNationaliteitRepository reden verlies nl nationaliteit repo
     * @param gbaVerblijfsrechtRepository verblijfrecht repo
     * @param aanduidingInhoudingVermissingReisdocumentRepository aanduiding inhouding vermissing reisdocument repo
     * @param aangifteAdreshoudingRepository aangifte adreshouding repo
     * @param adellijkeTitelPredikaatRepository adellijke titel predikaat repo
     * @param redenOntbindingHuwelijkPartnerschapRepository ontbinding huwelijk partnerschap repo
     * @param redenOpschortingRepository reden opschorting repo
     * @param rniDeelnemerRepository rni deelnemer repo
     * @param soortNlReisdocumentRepository soort nl reisdocument repo
     * @param voorvoegselConversieRepository voorvoegsel conversie repo
     * @param autoriteitVanAfgifteBuitenlandsPersoonsnummerRepository autoriteit van afgifte buitenliands persoonnummer repo
     */
    @Inject
    public Lo3ConversieTabelFactory(final GemeenteRepository gbaGemeenteRepository,
                                    final LandOfGebiedRepository gbaLandOfGebiedRepository,
                                    final NationaliteitRepository gbaNationaliteitRepository,
                                    final PlaatsRepository gbaPlaatsRepository,
                                    final RedenVerkrijgingNLNationaliteitRepository gbaRedenVerkrijgingNLNationaliteitRepository,
                                    final RedenVerliesNLNationaliteitRepository gbaRedenVerliesNLNationaliteitRepository,
                                    final VerblijfsrechtRepository gbaVerblijfsrechtRepository,
                                    final AanduidingInhoudingVermissingReisdocumentRepository aanduidingInhoudingVermissingReisdocumentRepository,
                                    final AangifteAdreshoudingRepository aangifteAdreshoudingRepository,
                                    final AdellijkeTitelPredikaatRepository adellijkeTitelPredikaatRepository,
                                    final RedenOntbindingHuwelijkPartnerschapRepository redenOntbindingHuwelijkPartnerschapRepository,
                                    final RedenOpschortingRepository redenOpschortingRepository,
                                    final RNIDeelnemerRepository rniDeelnemerRepository,
                                    final SoortNlReisdocumentRepository soortNlReisdocumentRepository,
                                    final VoorvoegselConversieRepository voorvoegselConversieRepository,
                                    final AutoriteitVanAfgifteBuitenlandsPersoonsnummerRepository autoriteitVanAfgifteBuitenlandsPersoonsnummerRepository) {
        this.gemeenteRepository = gbaGemeenteRepository;
        this.landOfGebiedRepository = gbaLandOfGebiedRepository;
        this.nationaliteitRepository = gbaNationaliteitRepository;
        this.plaatsRepository = gbaPlaatsRepository;
        this.redenVerkrijgingNLNationaliteitRepository = gbaRedenVerkrijgingNLNationaliteitRepository;
        this.redenVerliesNLNationaliteitRepository = gbaRedenVerliesNLNationaliteitRepository;
        this.verblijfsrechtRepository = gbaVerblijfsrechtRepository;
        this.aanduidingInhoudingVermissingReisdocumentRepository = aanduidingInhoudingVermissingReisdocumentRepository;
        this.aangifteAdreshoudingRepository = aangifteAdreshoudingRepository;
        this.adellijkeTitelPredikaatRepository = adellijkeTitelPredikaatRepository;
        this.redenOntbindingHuwelijkPartnerschapRepository = redenOntbindingHuwelijkPartnerschapRepository;
        this.redenOpschortingRepository = redenOpschortingRepository;
        this.rniDeelnemerRepository = rniDeelnemerRepository;
        this.soortNlReisdocumentRepository = soortNlReisdocumentRepository;
        this.voorvoegselConversieRepository = voorvoegselConversieRepository;
        this.autoriteitVanAfgifteBuitenlandsPersoonsnummerRepository = autoriteitVanAfgifteBuitenlandsPersoonsnummerRepository;
    }

    @Override
    public Conversietabel<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar> createAdellijkeTitelPredikaatConversietabel() {
        return new AdellijkeTitelPredikaatConversietabel(adellijkeTitelPredikaatRepository.findAll());
    }

    @Override
    public Conversietabel<Lo3RedenOntbindingHuwelijkOfGpCode, BrpRedenEindeRelatieCode> createRedenEindeRelatieConversietabel() {
        return new RedenOntbindingHuwelijkPartnerschapConversietabel(redenOntbindingHuwelijkPartnerschapRepository.findAll());
    }

    @Override
    public Conversietabel<Lo3AanduidingVerblijfstitelCode, BrpVerblijfsrechtCode> createVerblijfsrechtConversietabel() {
        return new VerblijfsrechtConversietabel(verblijfsrechtRepository.findAll());
    }

    @Override
    public Conversietabel<Lo3RedenNederlandschapCode, BrpRedenVerkrijgingNederlandschapCode> createRedenOpnameNationaliteitConversietabel() {
        return new RedenVerkrijgingNederlanderschapConversietabel(redenVerkrijgingNLNationaliteitRepository.findAll());
    }

    @Override
    public Conversietabel<Lo3RedenNederlandschapCode, BrpRedenVerliesNederlandschapCode> createRedenBeeindigingNationaliteitConversietabel() {
        return new RedenVerliesNederlanderschapConversietabel(redenVerliesNLNationaliteitRepository.findAll());
    }

    @Override
    public Conversietabel<Lo3AangifteAdreshouding, AangeverRedenWijzigingVerblijfPaar> createAangeverRedenWijzigingVerblijfConversietabel() {
        return new AangeverRedenWijzigingVerblijfConversietabel(aangifteAdreshoudingRepository.findAll());
    }

    @Override
    public Conversietabel<Lo3SoortNederlandsReisdocument, BrpSoortNederlandsReisdocumentCode> createSoortReisdocumentConversietabel() {
        return new SoortNlReisdocumentConversietabel(soortNlReisdocumentRepository.findAll());
    }

    @Override
    public Conversietabel<Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpAanduidingInhoudingOfVermissingReisdocumentCode>
    createAanduidingInhoudingVermissingReisdocumentConversietabel() {
        return new AanduidingInhoudingVermissingReisdocumentConversietabel(aanduidingInhoudingVermissingReisdocumentRepository.findAll());
    }

    @Override
    public Conversietabel<Lo3RedenOpschortingBijhoudingCode, BrpNadereBijhoudingsaardCode> createRedenOpschortingBijhoudingConversietabel() {
        return new RedenOpschortingConversietabel(redenOpschortingRepository.findAll());
    }

    @Override
    public Conversietabel<Lo3RNIDeelnemerCode, BrpPartijCode> createRNIDeelnemerConversietabel() {
        return new RNIDeelnemerConversietabel(rniDeelnemerRepository.findAll());
    }

    @Override
    public Conversietabel<Lo3String, VoorvoegselScheidingstekenPaar> createVoorvoegselScheidingstekenConversietabel() {
        return new VoorvoegselConversietabel(voorvoegselConversieRepository.findAll());
    }

    @Override
    public Conversietabel<String, String> createWoonplaatsnaamConversietabel() {
        return new WoonplaatsConversietabel(plaatsRepository.findAll());
    }

    @Override
    public Conversietabel<Lo3GemeenteCode, BrpPartijCode> createPartijConversietabel() {
        return new PartijConversietabel(gemeenteRepository.findAll());
    }

    @Override
    public Conversietabel<Lo3GemeenteCode, BrpGemeenteCode> createGemeenteConversietabel() {
        return new GemeenteConversietabel(
                gemeenteRepository.findAll().stream().map(Gemeente::getCode).collect(Collectors.toList()));
    }

    @Override
    public Conversietabel<Lo3LandCode, BrpLandOfGebiedCode> createLandConversietabel() {
        return new LandConversietabel(landOfGebiedRepository.findAll().stream().map(LandOfGebied::getCode).collect(Collectors.toList()));
    }

    @Override
    public Conversietabel<Lo3NationaliteitCode, BrpNationaliteitCode> createNationaliteitConversietabel() {
        return new NationaliteitConversietabel(nationaliteitRepository.findAll().stream().map(Nationaliteit::getCode).collect(Collectors.toList()));
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
    public Conversietabel<Lo3NationaliteitCode, BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer> createAutoriteitVanAfgifteBuitenlandsPersoonsnummertabel() {
        return new AutoriteitVanAfgifteBuitenlandsPersoonsnummerConversietabel(autoriteitVanAfgifteBuitenlandsPersoonsnummerRepository.findAll().stream().map(
                AutoriteitAfgifteBuitenlandsPersoonsnummer::getCode).collect(Collectors.toList()));
    }

    @Override
    public Conversietabel<Integer, BrpProtocolleringsniveauCode> createVerstrekkingsbeperkingConversietabel() {
        return new VerstrekkingsbeperkingConversietabel();
    }
}
