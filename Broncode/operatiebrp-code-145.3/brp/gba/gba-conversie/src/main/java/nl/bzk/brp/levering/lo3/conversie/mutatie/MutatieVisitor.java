/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import static nl.bzk.brp.levering.lo3.mapper.BuitenlandsPersoonsnummerMapper.AUTORITEITVANAFGIFTECODE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AanduidingOuder;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.conversie.excepties.OnduidelijkeOudersException;
import nl.bzk.brp.levering.lo3.conversie.mutatie.OuderBepaler.OuderIdentificatie;
import nl.bzk.brp.levering.lo3.decorator.Persoon;
import nl.bzk.brp.levering.lo3.mapper.AdresMapper;
import nl.bzk.brp.levering.lo3.mapper.AdressenMapper;
import nl.bzk.brp.levering.lo3.mapper.BuitenlandsPersoonsnummerMapper;
import nl.bzk.brp.levering.lo3.mapper.BuitenlandsPersoonsnummersMapper;
import nl.bzk.brp.levering.lo3.mapper.NationaliteitMapper;
import nl.bzk.brp.levering.lo3.mapper.NationaliteitenMapper;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapper;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapperImpl;
import nl.bzk.brp.levering.lo3.mapper.PersoonAfgeleidAdministratiefMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonBijhoudingMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonDeelnameEuVerkiezingenMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonGeboorteMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonGeslachtsaanduidingMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIdentificatienummersMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieBehandeldAlsNederlanderMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieDerdeHeeftGezagMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieOnderCurateleMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieOnverwerktDocumentAanwezigMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieStaatloosMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieVastgesteldNietNederlanderMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieVerstrekkingsbeperkingMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatiesBehandeldAlsNederlanderMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatiesDerdeHeeftGezagMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatiesOnderCurateleMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatiesOnverwerktDocumentAanwezigMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatiesSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatiesStaatloosMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatiesVastgesteldNietNederlanderMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatiesVerstrekkingsbeperkingMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonInschrijvingMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonMigratieMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonNaamgebruikMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonNummerverwijzingMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonOverlijdenMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonPersoonskaartMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonSamengesteldeNaamMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonUitsluitingKiesrechtMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonVerblijfsrechtMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonslijstMapper;
import nl.bzk.brp.levering.lo3.mapper.ReisdocumentMapper;
import nl.bzk.brp.levering.lo3.mapper.ReisdocumentenMapper;
import nl.bzk.brp.levering.lo3.mapper.VerificatieMapper;
import nl.bzk.brp.levering.lo3.mapper.VerificatiesMapper;
import nl.bzk.brp.levering.lo3.util.MetaModelUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mutatie visitor (bepaalt mutaties obv een hispersoonvolledig).
 */
@Component
public class MutatieVisitor {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Autowired
    private PersoonAfgeleidAdministratiefMutatieVerwerker afgeleidAdministratieMutatieVerwerker;
    @Autowired
    private PersoonBijhoudingInschrijvingMutatieVerwerker bijhoudingInschrijvingMutatieVerwerker;
    @Autowired
    private PersoonBijhoudingVerblijfplaatsMutatieVerwerker bijhoudingVerblijfplaatsMutatieVerwerker;
    @Autowired
    private PersoonDeelnameEuVerkiezingenMutatieVerwerker deelnameEuVerkiezingenMutatieVerwerker;
    @Autowired
    private PersoonGeboorteMutatieVerwerker geboorteMutatieVerwerker;
    @Autowired
    private PersoonGeslachtsaanduidingMutatieVerwerker geslachtsaanduidingMutatieVerwerker;
    @Autowired
    private PersoonIdentificatienummersMutatieVerwerker identificatienummersMutatieVerwerker;
    @Autowired
    private PersoonInschrijvingMutatieVerwerker inschrijvingMutatieVerwerker;
    @Autowired
    private PersoonMigratieMutatieVerwerker migratieMutatieVerwerker;
    @Autowired
    private PersoonNaamgebruikMutatieVerwerker naamgebruikMutatieVerwerker;
    @Autowired
    private PersoonNummerverwijzingMutatieVerwerker nummerverwijzingMutatieVerwerker;
    @Autowired
    private PersoonOverlijdenMutatieVerwerker overlijdenMutatieVerwerker;
    @Autowired
    private PersoonPersoonskaartMutatieVerwerker persoonskaartMutatieVerwerker;
    @Autowired
    private PersoonSamengesteldeNaamMutatieVerwerker samengesteldeNaamMutatieVerwerker;
    @Autowired
    private PersoonUitsluitingKiesrechtMutatieVerwerker uitsluitingKiesrechtMutatieVerwerker;
    @Autowired
    private PersoonVerblijfsrechtMutatieVerwerker verblijfsrechtMutatieVerwerker;

    @Autowired
    private PersoonIndicatieBehandeldAlsNederlanderMutatieVerwerker indicatieBehandeldAlsNederlanderMutatieVerwerker;
    @Autowired
    private PersoonIndicatieDerdeHeeftGezagMutatieVerwerker indicatieDerdeHeeftGezagMutatieVerwerker;
    @Autowired
    private PersoonIndicatieOnderCurateleMutatieVerwerker indicatieOnderCurateleMutatieVerwerker;
    @Autowired
    private PersoonIndicatieOnverwerktDocumentAanwezigMutatieVerwerker indicatieOnverwerktDocumentAanwezigMutatieVerwerker;
    @Autowired
    private PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentMutatieVerwerker
            indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentMutatieVerwerker;
    @Autowired
    private PersoonIndicatieStaatloosMutatieVerwerker indicatieStaatloosMutatieVerwerker;
    @Autowired
    private PersoonIndicatieVastgesteldNietNederlanderMutatieVerwerker indicatieVastgesteldNietNederlanderMutatieVerwerker;
    @Autowired
    private PersoonIndicatieVolledigeVerstrekkingsbeperkingMutatieVerwerker indicatieVolledigeVerstrekkingsbeperkingMutatieVerwerker;

    @Autowired
    private VerificatieIdentiteitOnderzoekVerwerker verificatieIdentiteitOnderzoekVerwerker;
    @Autowired
    private VerificatieStandaardMutatieVerwerker verificatieStandaardMutatieVerwerker;

    @Autowired
    private AdresIdentiteitOnderzoekVerwerker adresIdentiteitOnderzoekVerwerker;
    @Autowired
    private AdresStandaardMutatieVerwerker adresStandaardMutatieVerwerker;

    @Autowired
    private NationaliteitIdentiteitOnderzoekVerwerker nationaliteitIdentiteitOnderzoekVerwerker;
    @Autowired
    private NationaliteitStandaardMutatieVerwerker nationaliteitStandaardMutatieVerwerker;

    @Autowired
    private ReisdocumentIdentiteitOnderzoekVerwerker reisdocumentIdentiteitOnderzoekVerwerker;
    @Autowired
    private ReisdocumentStandaardMutatieVerwerker reisdocumentStandaardMutatieVerwerker;

    @Autowired
    private BuitenlandsPersoonsnummerIdentiteitOnderzoekVerwerker buitenlandsPersoonsnummerIdentiteitOnderzoekVerwerker;
    @Autowired
    private BuitenlandsPersoonsnummerStandaardMutatieVerwerker buitenlandsPersoonsnummerStandaardMutatieVerwerker;

    @Autowired
    private KindVisitor kindVisitor;
    @Autowired
    private OuderVisitor ouderVisitor;
    @Autowired
    private HuwelijkVisitor huwelijkVisitor;
    @Autowired
    private GeregistreerdPartnerschapVisitor partnerschapMutatieVerwerker;

    @Autowired
    private IstVisitor istVisitor;

    @Autowired
    private OuderBepaler ouderBepaler;
    @Autowired
    private RelatieBepaler relatieBepaler;

    @Autowired
    private IndicatieOnverwerktDocumentAanwezigNabewerking indicatieOnverwerktDocumentAanwezigNabewerking;

    @Autowired
    private PersoonGeslachtAdellijkeTitelPredikaatNabewerking persoonGeslachtAdellijkeTitelPredikaatNabewerking;

    /**
     * Bepaal de mutaties voor een persoon voor een administratieve handeling.
     * @param persoonslijst persoonsgegevens
     * @param istStapels IST-stapels
     * @param administratieveHandeling administratieve handeling
     * @return mutaties
     */
    public final List<Lo3CategorieWaarde> visit(final Persoonslijst persoonslijst, final List<Stapel> istStapels,
                                                final AdministratieveHandeling administratieveHandeling) {
        LOGGER.debug("visit(persoon={}, administratieveHandeling={})", persoonslijst, administratieveHandeling);

        final List<Long> acties = new ArrayList<>();
        for (final Actie actieModel : administratieveHandeling.getActies()) {
            acties.add(actieModel.getId());
        }
        LOGGER.debug("Acties: {}", acties);

        // Persoonsgegevens
        final MetaObject persoonObject = persoonslijst.getMetaObject();

        // OnderzoekMapper
        final Persoon persoon = new Persoon(persoonObject);
        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(persoon);

        // Bij mutaties moeten eerste de formele groepen worden verwerkt en daarna pas de materiele
        // groepen, dit komt door de opbouw van de historie (die wordt elke keer overschreven).
        final Lo3Mutaties resultaat = new Lo3Mutaties();
        verwerkPersoonsgegevens(persoonObject, acties, onderzoekMapper, resultaat);

        verwerkAdres(persoonObject, acties, onderzoekMapper, resultaat);
        // Indicaties moeten na adres, omdat de standaard adres converteerder alle adres velden leeg
        // maakt, waaronder dus ook de indicatie onverwerkt document
        verwerkIndicaties(persoonObject, acties, onderzoekMapper, resultaat);

        verwerkBuitenlandsPersoonnummers(persoonObject, acties, onderzoekMapper, resultaat);
        // Nationaliteiten moeten na buitenlandspersoonsnummer omdat nationaliteit materiele
        // historie heeft en buitenlands persoonsnummer enkel formeel
        verwerkNationaliteiten(persoonObject, acties, onderzoekMapper, resultaat);

        verwerkReisdocumenten(persoonObject, acties, onderzoekMapper, resultaat);

        verwerkRelaties(persoonObject, acties, onderzoekMapper, resultaat);
        istVisitor.visit(istStapels, resultaat);

        // Bijhouding verblijfplaats als laatste verwerken omdat de converteerders die als laatste
        // verwachten (adres en indicatie onverwerkt document maken deze weer null)
        verwerkBijhoudingVerblijfplaats(persoonObject, acties, onderzoekMapper, resultaat);

        return resultaat.geefCategorieen();
    }

    private void verwerkPersoonsgegevens(final MetaObject persoon, final List<Long> acties, final OnderzoekMapper onderzoekMapper,
                                         final Lo3Mutaties resultaat) {
        final List<Long> persoonObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(persoon);
        final MetaRecord persoonIdentiteitRecord = MetaModelUtil.getIdentiteitRecord(persoon, PersoonslijstMapper.IDENTITEIT_GROEP_ELEMENT);

        // Formeel
        afgeleidAdministratieMutatieVerwerker.verwerk(resultaat.geefInschrijvingWijziging(), persoonIdentiteitRecord,
                MetaModelUtil.getRecords(persoon, PersoonAfgeleidAdministratiefMapper.GROEP_ELEMENT), acties, persoonObjectSleutels, onderzoekMapper);
        deelnameEuVerkiezingenMutatieVerwerker.verwerk(resultaat.geefKiesrechtWijziging(), persoonIdentiteitRecord,
                MetaModelUtil.getRecords(persoon, PersoonDeelnameEuVerkiezingenMapper.GROEP_ELEMENT), acties, persoonObjectSleutels, onderzoekMapper);
        geboorteMutatieVerwerker.verwerk(resultaat.geefPersoonWijziging(), persoonIdentiteitRecord,
                MetaModelUtil.getRecords(persoon, PersoonGeboorteMapper.GROEP_ELEMENT), acties, persoonObjectSleutels, onderzoekMapper);
        inschrijvingMutatieVerwerker.verwerk(resultaat.geefInschrijvingWijziging(), persoonIdentiteitRecord,
                MetaModelUtil.getRecords(persoon, PersoonInschrijvingMapper.GROEP_ELEMENT), acties, persoonObjectSleutels, onderzoekMapper);
        naamgebruikMutatieVerwerker.verwerk(resultaat.geefPersoonWijziging(), persoonIdentiteitRecord,
                MetaModelUtil.getRecords(persoon, PersoonNaamgebruikMapper.GROEP_ELEMENT), acties, persoonObjectSleutels, onderzoekMapper);
        overlijdenMutatieVerwerker.verwerk(resultaat.geefOverlijdenWijziging(), persoonIdentiteitRecord,
                MetaModelUtil.getRecords(persoon, PersoonOverlijdenMapper.GROEP_ELEMENT), acties, persoonObjectSleutels, onderzoekMapper);
        persoonskaartMutatieVerwerker.verwerk(resultaat.geefInschrijvingWijziging(), persoonIdentiteitRecord,
                MetaModelUtil.getRecords(persoon, PersoonPersoonskaartMapper.GROEP_ELEMENT), acties, persoonObjectSleutels, onderzoekMapper);
        uitsluitingKiesrechtMutatieVerwerker.verwerk(resultaat.geefKiesrechtWijziging(), persoonIdentiteitRecord,
                MetaModelUtil.getRecords(persoon, PersoonUitsluitingKiesrechtMapper.GROEP_ELEMENT), acties, persoonObjectSleutels, onderzoekMapper);

        for (final MetaObject verificatie : MetaModelUtil.getObjecten(persoon, VerificatiesMapper.OBJECT_ELEMENT)) {
            final List<Long> verificatieObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(verificatie);
            final MetaRecord verificatieIdentiteitRecord = MetaModelUtil.getIdentiteitRecord(verificatie, VerificatieMapper.IDENTITEIT_GROEP_ELEMENT);

            verificatieIdentiteitOnderzoekVerwerker.verwerk(resultaat.geefInschrijvingWijziging(), acties, verificatieObjectSleutels, onderzoekMapper);

            resultaat.geefInschrijvingWijziging().leegVerificatie();

            verificatieStandaardMutatieVerwerker.verwerk(resultaat.geefInschrijvingWijziging(), verificatieIdentiteitRecord,
                    MetaModelUtil.getRecords(verificatie, VerificatieMapper.GROEP_ELEMENT), acties, verificatieObjectSleutels, onderzoekMapper);
        }

        // Materieel
        bijhoudingVerblijfplaatsMutatieVerwerker.verwerk(resultaat.geefVerblijfsplaatsWijziging(), persoonIdentiteitRecord,
                MetaModelUtil.getRecords(persoon, PersoonBijhoudingMapper.GROEP_ELEMENT), acties, persoonObjectSleutels, onderzoekMapper);
        bijhoudingInschrijvingMutatieVerwerker.verwerk(resultaat.geefInschrijvingWijziging(), persoonIdentiteitRecord,
                MetaModelUtil.getRecords(persoon, PersoonBijhoudingMapper.GROEP_ELEMENT), acties, persoonObjectSleutels, onderzoekMapper);
        geslachtsaanduidingMutatieVerwerker.verwerk(resultaat.geefPersoonWijziging(), persoonIdentiteitRecord,
                MetaModelUtil.getRecords(persoon, PersoonGeslachtsaanduidingMapper.GROEP_ELEMENT), acties, persoonObjectSleutels, onderzoekMapper);
        identificatienummersMutatieVerwerker.verwerk(resultaat.geefPersoonWijziging(), persoonIdentiteitRecord,
                MetaModelUtil.getRecords(persoon, PersoonIdentificatienummersMapper.GROEP_ELEMENT), acties, persoonObjectSleutels, onderzoekMapper);
        nummerverwijzingMutatieVerwerker.verwerk(resultaat.geefPersoonWijziging(), persoonIdentiteitRecord,
                MetaModelUtil.getRecords(persoon, PersoonNummerverwijzingMapper.GROEP_ELEMENT), acties, persoonObjectSleutels, onderzoekMapper);
        samengesteldeNaamMutatieVerwerker.verwerk(resultaat.geefPersoonWijziging(), persoonIdentiteitRecord,
                MetaModelUtil.getRecords(persoon, PersoonSamengesteldeNaamMapper.GROEP_ELEMENT), acties, persoonObjectSleutels, onderzoekMapper);
        verblijfsrechtMutatieVerwerker.verwerk(resultaat.geefVerblijfstitelWijziging(), persoonIdentiteitRecord,
                MetaModelUtil.getRecords(persoon, PersoonVerblijfsrechtMapper.GROEP_ELEMENT), acties, persoonObjectSleutels, onderzoekMapper);

        persoonGeslachtAdellijkeTitelPredikaatNabewerking.voerNabewerkingUit(resultaat.geefPersoonWijziging());
    }

    private void verwerkBijhoudingVerblijfplaats(final MetaObject persoon, final List<Long> acties, final OnderzoekMapper onderzoekMapper,
                                                 final Lo3Mutaties resultaat) {
        final List<Long> persoonObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(persoon);
        final MetaRecord persoonIdentiteitRecord = MetaModelUtil.getIdentiteitRecord(persoon, PersoonslijstMapper.IDENTITEIT_GROEP_ELEMENT);

        bijhoudingVerblijfplaatsMutatieVerwerker.verwerk(resultaat.geefVerblijfsplaatsWijziging(), persoonIdentiteitRecord,
                MetaModelUtil.getRecords(persoon, PersoonBijhoudingMapper.GROEP_ELEMENT), acties, persoonObjectSleutels, onderzoekMapper);
    }

    private void verwerkIndicaties(final MetaObject persoon, final List<Long> acties, final OnderzoekMapper onderzoekMapper, final Lo3Mutaties resultaat) {
        // Formeel

        for (final MetaObject indicatie : MetaModelUtil.getObjecten(persoon,
                PersoonIndicatiesSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper.OBJECT_ELEMENT)) {
            indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentMutatieVerwerker.verwerk(resultaat.geefNieuweReisdocumentWijziging(),
                    MetaModelUtil.getIdentiteitRecord(indicatie,
                            PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper.IDENTITEIT_GROEP_ELEMENT),
                    MetaModelUtil.getRecords(indicatie, PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper.GROEP_ELEMENT), acties,
                    ObjectSleutelsHelper.bepaalObjectSleutels(indicatie), onderzoekMapper);
        }
        for (final MetaObject indicatie : MetaModelUtil.getObjecten(persoon, PersoonIndicatiesVerstrekkingsbeperkingMapper.OBJECT_ELEMENT)) {
            indicatieVolledigeVerstrekkingsbeperkingMutatieVerwerker.verwerk(resultaat.geefInschrijvingWijziging(),
                    MetaModelUtil.getIdentiteitRecord(indicatie, PersoonIndicatieVerstrekkingsbeperkingMapper.IDENTITEIT_GROEP_ELEMENT),
                    MetaModelUtil.getRecords(indicatie, PersoonIndicatieVerstrekkingsbeperkingMapper.GROEP_ELEMENT), acties,
                    ObjectSleutelsHelper.bepaalObjectSleutels(indicatie), onderzoekMapper);
        }
        for (final MetaObject indicatie : MetaModelUtil.getObjecten(persoon, PersoonIndicatiesOnverwerktDocumentAanwezigMapper.OBJECT_ELEMENT)) {
            final Lo3Datum ingangsdatumGeldigheidVoorNabewerking = resultaat.geefVerblijfsplaatsWijziging().getActueleInhoud() == null ? null
                    : resultaat.geefVerblijfsplaatsWijziging().getActueleInhoud().getHistorie().getIngangsdatumGeldigheid();
            final IndicatieOnverwerktDocumentAanwezigNabewerking.Data dataVoorNabewerking =
                    indicatieOnverwerktDocumentAanwezigNabewerking.bepaalData(resultaat.geefVerblijfsplaatsWijziging());
            LOGGER.info("Indicatie onverwerkt document aanwezig verwerken met ingangsdatumgeldigheid {}.", ingangsdatumGeldigheidVoorNabewerking);
            final Lo3Wijzigingen<Lo3VerblijfplaatsInhoud> adresWijzigingen = resultaat.geefVerblijfsplaatsWijziging();
            indicatieOnverwerktDocumentAanwezigMutatieVerwerker.verwerk(adresWijzigingen,
                    MetaModelUtil.getIdentiteitRecord(indicatie, PersoonIndicatieOnverwerktDocumentAanwezigMapper.IDENTITEIT_GROEP_ELEMENT),
                    MetaModelUtil.getRecords(indicatie, PersoonIndicatieOnverwerktDocumentAanwezigMapper.GROEP_ELEMENT), acties,
                    ObjectSleutelsHelper.bepaalObjectSleutels(indicatie), onderzoekMapper);
            indicatieOnverwerktDocumentAanwezigNabewerking.voerNabewerkingUit(resultaat.geefVerblijfsplaatsWijziging(), dataVoorNabewerking);
        }

        // Materieel
        for (final MetaObject indicatie : MetaModelUtil.getObjecten(persoon, PersoonIndicatiesBehandeldAlsNederlanderMapper.OBJECT_ELEMENT)) {
            indicatieBehandeldAlsNederlanderMutatieVerwerker.verwerk(resultaat.geefNieuweOfBestaandeNationaliteitWijziging("$$_ind_behandeld_als_nederlander"),
                    MetaModelUtil.getIdentiteitRecord(indicatie, PersoonIndicatieBehandeldAlsNederlanderMapper.IDENTITEIT_GROEP_ELEMENT),
                    MetaModelUtil.getRecords(indicatie, PersoonIndicatieBehandeldAlsNederlanderMapper.GROEP_ELEMENT), acties,
                    ObjectSleutelsHelper.bepaalObjectSleutels(indicatie), onderzoekMapper);
        }
        for (final MetaObject indicatie : MetaModelUtil.getObjecten(persoon, PersoonIndicatiesDerdeHeeftGezagMapper.OBJECT_ELEMENT)) {
            indicatieDerdeHeeftGezagMutatieVerwerker.verwerk(resultaat.geefGezagsverhoudingWijziging(),
                    MetaModelUtil.getIdentiteitRecord(indicatie, PersoonIndicatieDerdeHeeftGezagMapper.IDENTITEIT_GROEP_ELEMENT),
                    MetaModelUtil.getRecords(indicatie, PersoonIndicatieDerdeHeeftGezagMapper.GROEP_ELEMENT), acties,
                    ObjectSleutelsHelper.bepaalObjectSleutels(indicatie), onderzoekMapper);
        }
        for (final MetaObject indicatie : MetaModelUtil.getObjecten(persoon, PersoonIndicatiesOnderCurateleMapper.OBJECT_ELEMENT)) {
            indicatieOnderCurateleMutatieVerwerker.verwerk(resultaat.geefGezagsverhoudingWijziging(),
                    MetaModelUtil.getIdentiteitRecord(indicatie, PersoonIndicatieOnderCurateleMapper.IDENTITEIT_GROEP_ELEMENT),
                    MetaModelUtil.getRecords(indicatie, PersoonIndicatieOnderCurateleMapper.GROEP_ELEMENT), acties,
                    ObjectSleutelsHelper.bepaalObjectSleutels(indicatie), onderzoekMapper);
        }
        for (final MetaObject indicatie : MetaModelUtil.getObjecten(persoon, PersoonIndicatiesStaatloosMapper.OBJECT_ELEMENT)) {
            indicatieStaatloosMutatieVerwerker.verwerk(resultaat.geefNieuweOfBestaandeNationaliteitWijziging("0499"),
                    MetaModelUtil.getIdentiteitRecord(indicatie, PersoonIndicatieStaatloosMapper.IDENTITEIT_GROEP_ELEMENT),
                    MetaModelUtil.getRecords(indicatie, PersoonIndicatieStaatloosMapper.GROEP_ELEMENT), acties,
                    ObjectSleutelsHelper.bepaalObjectSleutels(indicatie), onderzoekMapper);
        }
        for (final MetaObject indicatie : MetaModelUtil.getObjecten(persoon, PersoonIndicatiesVastgesteldNietNederlanderMapper.OBJECT_ELEMENT)) {
            indicatieVastgesteldNietNederlanderMutatieVerwerker.verwerk(
                    resultaat.geefNieuweOfBestaandeNationaliteitWijziging("$$_ind_vastgesteld_niet_nederlander"),
                    MetaModelUtil.getIdentiteitRecord(indicatie, PersoonIndicatieVastgesteldNietNederlanderMapper.IDENTITEIT_GROEP_ELEMENT),
                    MetaModelUtil.getRecords(indicatie, PersoonIndicatieVastgesteldNietNederlanderMapper.GROEP_ELEMENT), acties,
                    ObjectSleutelsHelper.bepaalObjectSleutels(indicatie), onderzoekMapper);
        }

    }

    private void verwerkAdres(final MetaObject persoon, final List<Long> acties, final OnderzoekMapper onderzoekMapper, final Lo3Mutaties resultaat) {
        final MetaRecord persoonIdentiteit = MetaModelUtil.getIdentiteitRecord(persoon, PersoonslijstMapper.IDENTITEIT_GROEP_ELEMENT);

        // Adressen
        for (final MetaObject adres : MetaModelUtil.getObjecten(persoon, AdressenMapper.OBJECT_ELEMENT)) {
            final List<Long> adresObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(adres);
            final MetaRecord adresIdentiteit = MetaModelUtil.getIdentiteitRecord(adres, AdresMapper.IDENTITEIT_GROEP_ELEMENT);

            adresIdentiteitOnderzoekVerwerker.verwerk(resultaat.geefVerblijfsplaatsWijziging(), acties, adresObjectSleutels, onderzoekMapper);
            adresStandaardMutatieVerwerker.verwerk(resultaat.geefVerblijfsplaatsWijziging(), adresIdentiteit,
                    MetaModelUtil.getRecords(adres, AdresMapper.GROEP_ELEMENT), acties, adresObjectSleutels, onderzoekMapper);

            final List<Long> persoonObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(persoon);
            migratieMutatieVerwerker.verwerk(resultaat.geefVerblijfsplaatsWijziging(), persoonIdentiteit,
                    MetaModelUtil.getRecords(persoon, PersoonMigratieMapper.GROEP_ELEMENT), acties, persoonObjectSleutels, onderzoekMapper);
        }

    }

    private void verwerkNationaliteiten(final MetaObject persoon, final List<Long> acties, final OnderzoekMapper onderzoekMapper, final Lo3Mutaties resultaat) {
        // Nationaliteiten
        for (final MetaObject nationaliteit : MetaModelUtil.getObjecten(persoon, NationaliteitenMapper.OBJECT_ELEMENT)) {
            final List<Long> nationaliteitObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(nationaliteit);
            final MetaRecord nationaliteitIdentiteit = MetaModelUtil.getIdentiteitRecord(nationaliteit, NationaliteitMapper.IDENTITEIT_GROEP_ELEMENT);

            final Lo3Wijzigingen<Lo3NationaliteitInhoud> wijzigingen = resultaat.geefNieuweOfBestaandeNationaliteitWijziging(
                    nationaliteitIdentiteit.getAttribuut(NationaliteitMapper.NATIONALITEITCODE_ELEMENT).getWaarde().toString());

            nationaliteitIdentiteitOnderzoekVerwerker.verwerk(wijzigingen, acties, nationaliteitObjectSleutels, onderzoekMapper);
            nationaliteitStandaardMutatieVerwerker.verwerk(wijzigingen, nationaliteitIdentiteit,
                    MetaModelUtil.getRecords(nationaliteit, NationaliteitMapper.GROEP_ELEMENT), acties, nationaliteitObjectSleutels, onderzoekMapper);
        }
    }

    private void verwerkBuitenlandsPersoonnummers(final MetaObject persoon, final List<Long> acties, final OnderzoekMapper onderzoekMapper,
                                                  final Lo3Mutaties resultaat) {
        for (final MetaObject persoonsnummer : MetaModelUtil.getObjecten(persoon, BuitenlandsPersoonsnummersMapper.OBJECT_ELEMENT)) {
            final List<Long> persoonsnummerObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(persoonsnummer);
            final MetaRecord persoonsnummerIdentiteit =
                    MetaModelUtil.getIdentiteitRecord(persoonsnummer, BuitenlandsPersoonsnummerMapper.IDENTITEIT_GROEP_ELEMENT);

            final Lo3Wijzigingen<Lo3NationaliteitInhoud> wijzigingen = resultaat
                    .geefNieuweOfBestaandeNationaliteitWijziging(persoonsnummerIdentiteit.getAttribuut(AUTORITEITVANAFGIFTECODE).getWaarde().toString());

            buitenlandsPersoonsnummerIdentiteitOnderzoekVerwerker.verwerk(wijzigingen, acties, persoonsnummerObjectSleutels, onderzoekMapper);
            buitenlandsPersoonsnummerStandaardMutatieVerwerker.verwerk(wijzigingen, persoonsnummerIdentiteit,
                    MetaModelUtil.getRecords(persoonsnummer, BuitenlandsPersoonsnummerMapper.GROEP_ELEMENT), acties, persoonsnummerObjectSleutels,
                    onderzoekMapper);
        }
    }

    private void verwerkReisdocumenten(final MetaObject persoon, final List<Long> acties, final OnderzoekMapper onderzoekMapper, final Lo3Mutaties resultaat) {
        // Reisdocumenten
        for (final MetaObject reisdocument : MetaModelUtil.getObjecten(persoon, ReisdocumentenMapper.OBJECT_ELEMENT)) {
            final List<Long> reisdocumentObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(reisdocument);
            final MetaRecord reisdocumentIdentiteit = MetaModelUtil.getIdentiteitRecord(reisdocument, ReisdocumentMapper.IDENTITEIT_GROEP_ELEMENT);

            final Lo3Wijzigingen<Lo3ReisdocumentInhoud> wijzigingen = resultaat.geefNieuweReisdocumentWijziging();

            reisdocumentIdentiteitOnderzoekVerwerker.verwerk(wijzigingen, acties, reisdocumentObjectSleutels, onderzoekMapper);
            reisdocumentStandaardMutatieVerwerker.verwerk(wijzigingen, reisdocumentIdentiteit,
                    MetaModelUtil.getRecords(reisdocument, ReisdocumentMapper.GROEP_ELEMENT), acties, reisdocumentObjectSleutels, onderzoekMapper);
        }
    }

    private void verwerkRelaties(final MetaObject persoon, final List<Long> acties, final OnderzoekMapper onderzoekMapper, final Lo3Mutaties resultaat) {
        verwerkOuders(persoon, acties, onderzoekMapper, resultaat);
        verwerkKinderen(persoon, acties, onderzoekMapper, resultaat);
        verwerkHuwlijkenGerelateerdePartnerschappen(persoon, acties, onderzoekMapper, resultaat);
    }

    private void verwerkOuders(final MetaObject persoon, final List<Long> acties, final OnderzoekMapper onderzoekMapper, final Lo3Mutaties resultaat) {
        // Verwerk ouders
        final MetaObject mijnKindBetrokkenheid = MetaModelUtil.getObject(persoon, PersoonslijstMapper.KIND_ELEMENT);
        if (mijnKindBetrokkenheid != null) {
            final MetaObject mijnKindRelatie = MetaModelUtil.getObject(mijnKindBetrokkenheid, PersoonslijstMapper.FAMILIERECHTELIJKE_BETREKKEING_ELEMENT);

            try {
                final List<OuderIdentificatie> ouders =
                        ouderBepaler.bepaalOuders(MetaModelUtil.getObjecten(mijnKindRelatie, PersoonslijstMapper.GERELATEERDE_OUDER_ELEMENT));

                verwerkOudersObvOuderIdentificatie(acties, onderzoekMapper, resultaat, mijnKindBetrokkenheid, ouders);
            } catch (final OnduidelijkeOudersException ooe) {
                throw new IllegalArgumentException("Kan ouders niet correct bepalen", ooe);
            }
        }
    }

    private void verwerkOudersObvOuderIdentificatie(final List<Long> acties, final OnderzoekMapper onderzoekMapper, final Lo3Mutaties resultaat,
                                                    final MetaObject mijnKindBetrokkenheid, final List<OuderIdentificatie> ouders) {
        for (final OuderIdentificatie ouder : ouders) {
            if (ouder.getOuderNummer() == AanduidingOuder.OUDER_1) {
                ouderVisitor.verwerk(resultaat.geefOuder1Wijziging(), AanduidingOuder.OUDER_1, resultaat.geefGezagsverhoudingWijziging(), acties,
                        onderzoekMapper, mijnKindBetrokkenheid, ouder.getMetaObject());
            } else if (ouder.getOuderNummer() == AanduidingOuder.OUDER_2) {
                ouderVisitor.verwerk(resultaat.geefOuder2Wijziging(), AanduidingOuder.OUDER_2, resultaat.geefGezagsverhoudingWijziging(), acties,
                        onderzoekMapper, mijnKindBetrokkenheid, ouder.getMetaObject());
            } else {
                throw new IllegalArgumentException("Meer dan 2 ouders bepaald.");
            }
        }
    }

    private void verwerkKinderen(final MetaObject persoon, final List<Long> acties, final OnderzoekMapper onderzoekMapper, final Lo3Mutaties resultaat) {
        // Verwerk kinderen
        for (final MetaObject mijnOuderBetrokkenheid : MetaModelUtil.getObjecten(persoon, PersoonslijstMapper.OUDER_ELEMENT)) {
            final MetaObject mijnOuderRelatie = MetaModelUtil.getObject(mijnOuderBetrokkenheid, PersoonslijstMapper.FAMILIERECHTELIJKE_BETREKKEING_ELEMENT);
            final MetaObject gerelateerdeKindBetrokkenheid = MetaModelUtil.getObject(mijnOuderRelatie, PersoonslijstMapper.GERELATEERDE_KIND_ELEMENT);

            kindVisitor.verwerk(resultaat.geefNieuweKindWijziging(), acties, onderzoekMapper, mijnOuderBetrokkenheid, mijnOuderRelatie,
                    gerelateerdeKindBetrokkenheid);
        }
    }

    private void verwerkHuwlijkenGerelateerdePartnerschappen(final MetaObject persoon, final List<Long> acties, final OnderzoekMapper onderzoekMapper,
                                                             final Lo3Mutaties resultaat) {
        // Verwerken huwelijken/gerelateerd partnerschappen
        final Map<Long, String> relatieMapping = bepaalRelatieMapping(persoon);

        for (final MetaObject mijnPartnerBetrokkenheid : MetaModelUtil.getObjecten(persoon, PersoonslijstMapper.PARTNER_ELEMENT)) {
            final Collection<MetaObject> huwelijken = MetaModelUtil.getObjecten(mijnPartnerBetrokkenheid, PersoonslijstMapper.HUWELIJK_ELEMENT);
            final Collection<MetaObject> partnerschappen =
                    MetaModelUtil.getObjecten(mijnPartnerBetrokkenheid, PersoonslijstMapper.GEREGISTREERD_PARTNERSCHAP_ELEMENT);

            for (final MetaObject huwelijk : huwelijken) {
                final MetaObject gerelateerdeBetrokkenheid = MetaModelUtil.getObject(huwelijk, PersoonslijstMapper.GERELATEERDE_HUWELIJKSPARTNER_ELEMENT);
                final MetaObject gerelateerdePersoon =
                        MetaModelUtil.getObject(gerelateerdeBetrokkenheid, PersoonslijstMapper.GERELATEERDE_HUWELIJKSPARTNER_PERSOON_ELEMENT);

                huwelijkVisitor.verwerk(resultaat.geefNieuweOfBestaandeHuwelijkWijziging(relatieMapping.get(huwelijk.getObjectsleutel())), acties,
                        onderzoekMapper, huwelijk, gerelateerdePersoon);
            }

            for (final MetaObject partnerschap : partnerschappen) {
                final MetaObject gerelateerdeBetrokkenheid =
                        MetaModelUtil.getObject(partnerschap, PersoonslijstMapper.GERELATEERDE_GEREGISTREERDEPARTNER_ELEMENT);
                final MetaObject gerelateerdePersoon =
                        MetaModelUtil.getObject(gerelateerdeBetrokkenheid, PersoonslijstMapper.GERELATEERDE_GEREGISTREERDEPARTNER_PERSOON_ELEMENT);

                partnerschapMutatieVerwerker.verwerk(resultaat.geefNieuweOfBestaandeHuwelijkWijziging(relatieMapping.get(partnerschap.getObjectsleutel())),
                        acties, onderzoekMapper, partnerschap, gerelateerdePersoon);
            }
        }
    }

    private Map<Long, String> bepaalRelatieMapping(final MetaObject persoon) {
        final Collection<MetaObject> huwelijken = new ArrayList<>();
        final Collection<MetaObject> partnerschappen = new ArrayList<>();

        // Eerst alle huwelijken en partnerschappen verzamelen (verschillende ik-betrokkenheden)
        for (final MetaObject mijnPartnerBetrokkenheid : MetaModelUtil.getObjecten(persoon, PersoonslijstMapper.PARTNER_ELEMENT)) {
            huwelijken.addAll(MetaModelUtil.getObjecten(mijnPartnerBetrokkenheid, PersoonslijstMapper.HUWELIJK_ELEMENT));
            partnerschappen.addAll(MetaModelUtil.getObjecten(mijnPartnerBetrokkenheid, PersoonslijstMapper.GEREGISTREERD_PARTNERSCHAP_ELEMENT));
        }

        return relatieBepaler.bepaalRelatieMapping(huwelijken, partnerschappen);
    }

}
