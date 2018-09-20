/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.brp.levering.lo3.conversie.excepties.OnduidelijkeOudersException;
import nl.bzk.brp.levering.lo3.conversie.mutatie.OuderBepaler.OuderIdentificatie;
import nl.bzk.brp.levering.lo3.mapper.ActieHisVolledigLocator;
import nl.bzk.brp.levering.lo3.mapper.ActieHisVolledigLocatorImpl;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapper;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.verconv.LO3AanduidingOuder;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortAanduidingOuder;
import nl.bzk.brp.model.hisvolledig.kern.GeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkGeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.KindHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PartnerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonReisdocumentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
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
    private PersoonDeelnameEUVerkiezingenMutatieVerwerker deelnameEUVerkiezingenMutatieVerwerker;
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
    private PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentMutatieVerwerker indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentMutatieVerwerker;
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
    private KindVisitor kindVisitor;
    @Autowired
    private OuderVisitor ouderVisitor;
    @Autowired
    private HuwelijkVisitor huwelijkVisitor;
    @Autowired
    private GeregistreerdPartnerschapVisitor partnerschapMutatieVerwerker;

    @Autowired
    private OuderBepaler ouderBepaler;
    @Autowired
    private RelatieBepaler relatieBepaler;

    @Autowired
    private PersoonGeslachtAdellijkeTitelPredikaatNabewerking persoonGeslachtAdellijkeTitelPredikaatNabewerking;

    /**
     * Bepaal de mutaties voor een persoon voor een administratieve handeling.
     *
     * @param persoon
     *            persoon
     * @param administratieveHandeling
     *            administratieve handeling
     * @return mutaties
     */
    public final List<Lo3CategorieWaarde> visit(final PersoonHisVolledig persoon, final AdministratieveHandelingModel administratieveHandeling) {
        LOGGER.debug("visit(persoon={}, administratieveHandeling={})", persoon, administratieveHandeling);

        final List<Long> acties = new ArrayList<>();
        for (final ActieModel actieModel : administratieveHandeling.getActies()) {
            acties.add(actieModel.getID());
        }
        LOGGER.debug("Acties: {}", acties);

        final OnderzoekMapper onderzoekMapper = new OnderzoekMapper(persoon);
        final ActieHisVolledigLocator actieLocator = new ActieHisVolledigLocatorImpl(persoon);

        final Lo3Mutaties resultaat = new Lo3Mutaties();
        verwerkPersoonsgegevens(persoon, acties, onderzoekMapper, actieLocator, resultaat);
        verwerkIndicaties(persoon, acties, onderzoekMapper, actieLocator, resultaat);
        verwerkAdres(persoon, acties, onderzoekMapper, actieLocator, resultaat);
        verwerkNationaliteiten(persoon, acties, onderzoekMapper, actieLocator, resultaat);
        verwerkReisdocumenten(persoon, acties, onderzoekMapper, actieLocator, resultaat);
        verwerkRelaties(persoon, acties, onderzoekMapper, actieLocator, resultaat);

        return resultaat.geefCategorieen();
    }

    private void verwerkPersoonsgegevens(
        final PersoonHisVolledig persoon,
        final List<Long> acties,
        final OnderzoekMapper onderzoekMapper,
        final ActieHisVolledigLocator actieLocator,
        final Lo3Mutaties resultaat)
    {
        final List<Long> persoonObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(persoon);

        // Formeel
        afgeleidAdministratieMutatieVerwerker.verwerk(
            resultaat.geefInschrijvingWijziging(),
            persoon.getPersoonAfgeleidAdministratiefHistorie(),
            acties,
            persoonObjectSleutels,
            onderzoekMapper,
            actieLocator);

        deelnameEUVerkiezingenMutatieVerwerker.verwerk(
            resultaat.geefKiesrechtWijziging(),
            persoon.getPersoonDeelnameEUVerkiezingenHistorie(),
            acties,
            persoonObjectSleutels,
            onderzoekMapper,
            actieLocator);
        geboorteMutatieVerwerker.verwerk(
            resultaat.geefPersoonWijziging(),
            persoon.getPersoonGeboorteHistorie(),
            acties,
            persoonObjectSleutels,
            onderzoekMapper,
            actieLocator);
        inschrijvingMutatieVerwerker.verwerk(
            resultaat.geefInschrijvingWijziging(),
            persoon.getPersoonInschrijvingHistorie(),
            acties,
            persoonObjectSleutels,
            onderzoekMapper,
            actieLocator);
        naamgebruikMutatieVerwerker.verwerk(
            resultaat.geefPersoonWijziging(),
            persoon.getPersoonNaamgebruikHistorie(),
            acties,
            persoonObjectSleutels,
            onderzoekMapper,
            actieLocator);
        overlijdenMutatieVerwerker.verwerk(
            resultaat.geefOverlijdenWijziging(),
            persoon.getPersoonOverlijdenHistorie(),
            acties,
            persoonObjectSleutels,
            onderzoekMapper,
            actieLocator);
        persoonskaartMutatieVerwerker.verwerk(
            resultaat.geefInschrijvingWijziging(),
            persoon.getPersoonPersoonskaartHistorie(),
            acties,
            persoonObjectSleutels,
            onderzoekMapper,
            actieLocator);
        uitsluitingKiesrechtMutatieVerwerker.verwerk(
            resultaat.geefKiesrechtWijziging(),
            persoon.getPersoonUitsluitingKiesrechtHistorie(),
            acties,
            persoonObjectSleutels,
            onderzoekMapper,
            actieLocator);

        for (final PersoonVerificatieHisVolledig verificatie : persoon.getVerificaties()) {
            final List<Long> verificatieObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(verificatie);

            verificatieIdentiteitOnderzoekVerwerker.verwerk(resultaat.geefInschrijvingWijziging(), acties, verificatieObjectSleutels, onderzoekMapper);
            verificatieStandaardMutatieVerwerker.verwerk(
                resultaat.geefInschrijvingWijziging(),
                verificatie.getPersoonVerificatieHistorie(),
                acties,
                verificatieObjectSleutels,
                onderzoekMapper,
                actieLocator);
        }

        // Materieel
        bijhoudingInschrijvingMutatieVerwerker.verwerk(
            resultaat.geefInschrijvingWijziging(),
            persoon.getPersoonBijhoudingHistorie(),
            acties,
            persoonObjectSleutels,
            onderzoekMapper,
            actieLocator);
        bijhoudingVerblijfplaatsMutatieVerwerker.verwerk(
            resultaat.geefVerblijfsplaatsWijziging(),
            persoon.getPersoonBijhoudingHistorie(),
            acties,
            persoonObjectSleutels,
            onderzoekMapper,
            actieLocator);
        geslachtsaanduidingMutatieVerwerker.verwerk(
            resultaat.geefPersoonWijziging(),
            persoon.getPersoonGeslachtsaanduidingHistorie(),
            acties,
            persoonObjectSleutels,
            onderzoekMapper,
            actieLocator);
        identificatienummersMutatieVerwerker.verwerk(
            resultaat.geefPersoonWijziging(),
            persoon.getPersoonIdentificatienummersHistorie(),
            acties,
            persoonObjectSleutels,
            onderzoekMapper,
            actieLocator);
        nummerverwijzingMutatieVerwerker.verwerk(
            resultaat.geefPersoonWijziging(),
            persoon.getPersoonNummerverwijzingHistorie(),
            acties,
            persoonObjectSleutels,
            onderzoekMapper,
            actieLocator);
        samengesteldeNaamMutatieVerwerker.verwerk(
            resultaat.geefPersoonWijziging(),
            persoon.getPersoonSamengesteldeNaamHistorie(),
            acties,
            persoonObjectSleutels,
            onderzoekMapper,
            actieLocator);
        verblijfsrechtMutatieVerwerker.verwerk(
            resultaat.geefVerblijfstitelWijziging(),
            persoon.getPersoonVerblijfsrechtHistorie(),
            acties,
            persoonObjectSleutels,
            onderzoekMapper,
            actieLocator);

        persoonGeslachtAdellijkeTitelPredikaatNabewerking.voerNabewerkingUit(resultaat.geefPersoonWijziging());
    }

    private void verwerkIndicaties(
        final PersoonHisVolledig persoon,
        final List<Long> acties,
        final OnderzoekMapper onderzoekMapper,
        final ActieHisVolledigLocator actieLocator,
        final Lo3Mutaties resultaat)
    {
        // Formeel
        if (persoon.getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument() != null) {
            indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentMutatieVerwerker.verwerk(
                resultaat.geefNieuweReisdocumentWijziging(),
                persoon.getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument().getPersoonIndicatieHistorie(),
                acties,
                ObjectSleutelsHelper.bepaalObjectSleutels(persoon.getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument()),
                onderzoekMapper,
                actieLocator);
        }
        if (persoon.getIndicatieVolledigeVerstrekkingsbeperking() != null) {
            indicatieVolledigeVerstrekkingsbeperkingMutatieVerwerker.verwerk(
                resultaat.geefInschrijvingWijziging(),
                persoon.getIndicatieVolledigeVerstrekkingsbeperking().getPersoonIndicatieHistorie(),
                acties,
                ObjectSleutelsHelper.bepaalObjectSleutels(persoon.getIndicatieVolledigeVerstrekkingsbeperking()),
                onderzoekMapper,
                actieLocator);
        }

        // Materieel
        if (persoon.getIndicatieBehandeldAlsNederlander() != null) {
            indicatieBehandeldAlsNederlanderMutatieVerwerker.verwerk(
                resultaat.geefNieuweNationaliteitWijziging(),
                persoon.getIndicatieBehandeldAlsNederlander().getPersoonIndicatieHistorie(),
                acties,
                ObjectSleutelsHelper.bepaalObjectSleutels(persoon.getIndicatieBehandeldAlsNederlander()),
                onderzoekMapper,
                actieLocator);
        }
        // persoon.getIndicatieBijzondereVerblijfsrechtelijkePositie()
        if (persoon.getIndicatieDerdeHeeftGezag() != null) {
            indicatieDerdeHeeftGezagMutatieVerwerker.verwerk(
                resultaat.geefGezagsverhoudingWijziging(),
                persoon.getIndicatieDerdeHeeftGezag().getPersoonIndicatieHistorie(),
                acties,
                ObjectSleutelsHelper.bepaalObjectSleutels(persoon.getIndicatieDerdeHeeftGezag()),
                onderzoekMapper,
                actieLocator);
        }
        if (persoon.getIndicatieOnderCuratele() != null) {
            indicatieOnderCurateleMutatieVerwerker.verwerk(
                resultaat.geefGezagsverhoudingWijziging(),
                persoon.getIndicatieOnderCuratele().getPersoonIndicatieHistorie(),
                acties,
                ObjectSleutelsHelper.bepaalObjectSleutels(persoon.getIndicatieOnderCuratele()),
                onderzoekMapper,
                actieLocator);
        }

        if (persoon.getIndicatieStaatloos() != null) {
            indicatieStaatloosMutatieVerwerker.verwerk(
                resultaat.geefNieuweNationaliteitWijziging(),
                persoon.getIndicatieStaatloos().getPersoonIndicatieHistorie(),
                acties,
                ObjectSleutelsHelper.bepaalObjectSleutels(persoon.getIndicatieStaatloos()),
                onderzoekMapper,
                actieLocator);
        }
        if (persoon.getIndicatieVastgesteldNietNederlander() != null) {
            indicatieVastgesteldNietNederlanderMutatieVerwerker.verwerk(
                resultaat.geefNieuweNationaliteitWijziging(),
                persoon.getIndicatieVastgesteldNietNederlander().getPersoonIndicatieHistorie(),
                acties,
                ObjectSleutelsHelper.bepaalObjectSleutels(persoon.getIndicatieVastgesteldNietNederlander()),
                onderzoekMapper,
                actieLocator);
        }

    }

    private void verwerkAdres(
        final PersoonHisVolledig persoon,
        final List<Long> acties,
        final OnderzoekMapper onderzoekMapper,
        final ActieHisVolledigLocator actieLocator,
        final Lo3Mutaties resultaat)
    {
        // Adres
        final PersoonAdresHisVolledig adres = persoon.getAdressen().iterator().next();
        final List<Long> adresObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(adres);

        adresIdentiteitOnderzoekVerwerker.verwerk(resultaat.geefVerblijfsplaatsWijziging(), acties, adresObjectSleutels, onderzoekMapper);
        adresStandaardMutatieVerwerker.verwerk(
            resultaat.geefVerblijfsplaatsWijziging(),
            adres.getPersoonAdresHistorie(),
            acties,
            adresObjectSleutels,
            onderzoekMapper,
            actieLocator);

        final List<Long> persoonObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(persoon);
        migratieMutatieVerwerker.verwerk(
            resultaat.geefVerblijfsplaatsWijziging(),
            persoon.getPersoonMigratieHistorie(),
            acties,
            persoonObjectSleutels,
            onderzoekMapper,
            actieLocator);
    }

    private void verwerkNationaliteiten(
        final PersoonHisVolledig persoon,
        final List<Long> acties,
        final OnderzoekMapper onderzoekMapper,
        final ActieHisVolledigLocator actieLocator,
        final Lo3Mutaties resultaat)
    {
        // Nationaliteiten
        for (final PersoonNationaliteitHisVolledig nationaliteit : persoon.getNationaliteiten()) {
            final List<Long> nationaliteitObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(nationaliteit);

            final Lo3Wijzigingen<Lo3NationaliteitInhoud> wijzigingen = resultaat.geefNieuweNationaliteitWijziging();

            nationaliteitIdentiteitOnderzoekVerwerker.verwerk(wijzigingen, acties, nationaliteitObjectSleutels, onderzoekMapper);
            nationaliteitStandaardMutatieVerwerker.verwerk(
                wijzigingen,
                nationaliteit.getPersoonNationaliteitHistorie(),
                acties,
                nationaliteitObjectSleutels,
                onderzoekMapper,
                actieLocator);
        }
    }

    private void verwerkReisdocumenten(
        final PersoonHisVolledig persoon,
        final List<Long> acties,
        final OnderzoekMapper onderzoekMapper,
        final ActieHisVolledigLocator actieLocator,
        final Lo3Mutaties resultaat)
    {
        // Reisdocumenten
        for (final PersoonReisdocumentHisVolledig reisdocument : persoon.getReisdocumenten()) {
            final List<Long> reisdocumentObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(reisdocument);

            final Lo3Wijzigingen<Lo3ReisdocumentInhoud> wijzigingen = resultaat.geefNieuweReisdocumentWijziging();

            reisdocumentIdentiteitOnderzoekVerwerker.verwerk(wijzigingen, acties, reisdocumentObjectSleutels, onderzoekMapper);
            reisdocumentStandaardMutatieVerwerker.verwerk(
                wijzigingen,
                reisdocument.getPersoonReisdocumentHistorie(),
                acties,
                reisdocumentObjectSleutels,
                onderzoekMapper,
                actieLocator);
        }
    }

    private void verwerkRelaties(
        final PersoonHisVolledig persoon,
        final List<Long> acties,
        final OnderzoekMapper onderzoekMapper,
        final ActieHisVolledigLocator actieLocator,
        final Lo3Mutaties resultaat)
    {
        // Verwerk ouders
        final KindHisVolledig mijnKindBetrokkenheid = persoon.getKindBetrokkenheid();
        if (mijnKindBetrokkenheid != null) {
            final RelatieHisVolledig mijnKindRelatie = mijnKindBetrokkenheid.getRelatie();

            try {
                final List<OuderIdentificatie> ouders = ouderBepaler.bepaalOuders(mijnKindRelatie.getOuderBetrokkenheden());

                for (final OuderIdentificatie ouder : ouders) {
                    if (ouder.getOuderNummer() == LO3SoortAanduidingOuder.OUDER1) {
                        ouderVisitor.verwerk(
                            resultaat.geefOuder1Wijziging(),
                            LO3AanduidingOuder.OUDER1,
                            resultaat.geefGezagsverhoudingWijziging(),
                            acties,
                            onderzoekMapper,
                            actieLocator,
                            mijnKindBetrokkenheid,
                            mijnKindRelatie,
                            ouder.getOuderHisVolledig());
                    } else if (ouder.getOuderNummer() == LO3SoortAanduidingOuder.OUDER2) {
                        ouderVisitor.verwerk(
                            resultaat.geefOuder2Wijziging(),
                            LO3AanduidingOuder.OUDER2,
                            resultaat.geefGezagsverhoudingWijziging(),
                            acties,
                            onderzoekMapper,
                            actieLocator,
                            mijnKindBetrokkenheid,
                            mijnKindRelatie,
                            ouder.getOuderHisVolledig());
                    } else {
                        throw new IllegalArgumentException("Meer dan 2 ouders bepaald.");
                    }
                }
            } catch (final OnduidelijkeOudersException ooe) {
                throw new IllegalArgumentException("Kan ouders niet correct bepalen", ooe);
            }
        }

        // Verwerk kinderen
        for (final OuderHisVolledig mijnOuderBetrokkenheid : persoon.getOuderBetrokkenheden()) {
            final RelatieHisVolledig mijnOuderRelatie = mijnOuderBetrokkenheid.getRelatie();
            final KindHisVolledig gerelateerdeKindBetrokkenheid = mijnOuderRelatie.getKindBetrokkenheid();

            kindVisitor.verwerk(
                resultaat.geefNieuweKindWijziging(),
                acties,
                onderzoekMapper,
                actieLocator,
                mijnOuderBetrokkenheid,
                mijnOuderRelatie,
                gerelateerdeKindBetrokkenheid);
        }

        // Verwerken huwelijken/gerelateerd partnerschappen
        final Set<? extends HuwelijkGeregistreerdPartnerschapHisVolledig> relaties = persoon.getHuwelijkGeregistreerdPartnerschappen();
        final Map<Integer, String> relatieMapping = relatieBepaler.bepaalRelatieMapping(relaties);
        for (final HuwelijkGeregistreerdPartnerschapHisVolledig mijnPartnerRelatie : relaties) {
            final PartnerHisVolledig gerelateerdePersoon = mijnPartnerRelatie.geefPartnerVan(persoon);

            if (mijnPartnerRelatie instanceof HuwelijkHisVolledig) {
                huwelijkVisitor.verwerk(
                    resultaat.geefNieuweOfBestaandeHuwelijkWijziging(relatieMapping.get(mijnPartnerRelatie.getID())),
                    acties,
                    onderzoekMapper,
                    actieLocator,
                    (HuwelijkHisVolledig) mijnPartnerRelatie,
                    gerelateerdePersoon.getPersoon());
            } else if (mijnPartnerRelatie instanceof GeregistreerdPartnerschapHisVolledig) {
                partnerschapMutatieVerwerker.verwerk(
                    resultaat.geefNieuweOfBestaandeHuwelijkWijziging(relatieMapping.get(mijnPartnerRelatie.getID())),
                    acties,
                    onderzoekMapper,
                    actieLocator,
                    (GeregistreerdPartnerschapHisVolledig) mijnPartnerRelatie,
                    gerelateerdePersoon.getPersoon());
            } else {
                throw new IllegalArgumentException("Onbekend type partnerrelatie: " + mijnPartnerRelatie.getClass());
            }

        }
    }

}
