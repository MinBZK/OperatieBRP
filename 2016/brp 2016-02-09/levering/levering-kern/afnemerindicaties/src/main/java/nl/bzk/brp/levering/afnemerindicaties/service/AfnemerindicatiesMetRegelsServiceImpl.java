/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.brp.business.regels.Bedrijfsregel;
import nl.bzk.brp.business.regels.context.AfnemerindicatieRegelContext;
import nl.bzk.brp.business.regels.context.AutorisatieRegelContext;
import nl.bzk.brp.business.regels.context.HuidigeSituatieRegelContext;
import nl.bzk.brp.business.regels.context.RegelContext;
import nl.bzk.brp.business.regels.context.VerstrekkingsbeperkingRegelContext;
import nl.bzk.brp.business.regels.logging.RegelLoggingUtil;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.exceptie.PersoonNietGevondenExceptie;
import nl.bzk.brp.levering.afnemerindicaties.model.BewerkAfnemerindicatieResultaat;
import nl.bzk.brp.levering.afnemerindicaties.regels.AfnemerindicatiesBedrijfsregelManager;
import nl.bzk.brp.levering.algemeen.service.PartijService;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.ToegangLeveringsautorisatieService;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.internbericht.RegelMelding;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;


/**
 * Implementatie van de {@link nl.bzk.brp.levering.afnemerindicaties.service.AfnemerindicatiesMetRegelsService}.
 *
 * @brp.bedrijfsregel R1408
 */
@Service
@Regels(Regel.R1408)
public class AfnemerindicatiesMetRegelsServiceImpl extends AbstractAfnemerindicatiesService implements AfnemerindicatiesMetRegelsService {

    private static final Logger LOGGER                                                                                     = LoggerFactory.getLogger();
    private static final String AFNEMERINDICATIE_NIET_GEPLAATST_MET_UITVOER_VAN_REGELS_DOOR_ONBEKENDE_REFERENTIE_VAN_VELD  =
        "Afnemerindicatie niet geplaatst met uitvoer van regels door onbekende referentie van veld: {}";
    private static final String AFNEMERINDICATIE_NIET_VERWIJDERD_MET_UITVOER_VAN_REGELS_DOOR_ONBEKENDE_REFERENTIE_VAN_VELD =
        "Afnemerindicatie niet verwijderd met uitvoer van regels door onbekende referentie van veld: {}";

    @Inject
    private ToegangLeveringsautorisatieService toegangLeveringsautorisatieService;

    @Inject
    private PartijService partijService;

    @Inject
    @Named("afnemerindicatiesBedrijfsregelManager")
    private AfnemerindicatiesBedrijfsregelManager regelManager;

    @Override
    public final BewerkAfnemerindicatieResultaat plaatsAfnemerindicatie(
        final int toegangleveringautorisatieId, final int persoonId, final int verantwoordingDienstId,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode, final DatumAttribuut datumEindeVolgen)
    {
        final BewerkAfnemerindicatieResultaat resultaat = new BewerkAfnemerindicatieResultaat();
        try {
            final ToegangLeveringsautorisatie tla = toegangLeveringsautorisatieService
                .geefToegangleveringautorisatieZonderControle(toegangleveringautorisatieId);
            final BewerkAfnemerindicatieResultaat resultaatVanPlaatsing
                = plaatsAfnemerindicatie(tla, persoonId, verantwoordingDienstId, datumAanvangMaterielePeriode, datumEindeVolgen);
            resultaat.setMeldingen(resultaatVanPlaatsing.getMeldingen());

        } catch (final EmptyResultDataAccessException e) {
            resultaat.getMeldingen().add(maakFoutMelding(Regel.BRLV0007));
        }

        return resultaat;
    }

    @Override
    public final BewerkAfnemerindicatieResultaat gbaPlaatsAfnemerindicatie(final ToegangLeveringsautorisatie toegangLeveringsautorisatie,
        final int persoonId,
        final int verantwoordingDienstId,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode,
        final DatumAttribuut datumEindeVolgen)
    {
        try {
            controleerToegangLeveringsautorisatie(toegangLeveringsautorisatie);
        } catch (AutorisatieException e) {
            final BewerkAfnemerindicatieResultaat resultaat = new BewerkAfnemerindicatieResultaat();
            resultaat.getMeldingen().add(maakFoutMelding(e.getRegel()));
            return resultaat;
        }
        return plaatsAfnemerindicatie(toegangLeveringsautorisatie, persoonId, verantwoordingDienstId, datumAanvangMaterielePeriode, datumEindeVolgen);
    }

    private void controleerToegangLeveringsautorisatie(final ToegangLeveringsautorisatie toegangLeveringsautorisatie) throws AutorisatieException {
        if (toegangLeveringsautorisatie == null) {
            LOGGER.info(Regel.R2120.getOmschrijving(), RegelLoggingUtil.PREFIX_LOGMELDING_FOUT, Regel.R2120);
            throw new AutorisatieException(Regel.R2120);
        }
        final DatumAttribuut systeemDatum = new DatumAttribuut(new Date());
        if (!toegangLeveringsautorisatie.isGeldigOp(systeemDatum)) {
            LOGGER.info(Regel.R1258.getOmschrijving(), RegelLoggingUtil.PREFIX_LOGMELDING_FOUT, Regel.R1258);
            throw new AutorisatieException(Regel.R1258);
        }
        if (toegangLeveringsautorisatie.isGeblokkeerd()) {
            LOGGER.info(Regel.R2052.getOmschrijving(), RegelLoggingUtil.PREFIX_LOGMELDING_FOUT, Regel.R2052);
            throw new AutorisatieException(Regel.R2052);
        }
        final Leveringsautorisatie leveringsautorisatie = toegangLeveringsautorisatie.getLeveringsautorisatie();
        if (leveringsautorisatie == null) {
            LOGGER.info(Regel.R2053.getOmschrijving(), RegelLoggingUtil.PREFIX_LOGMELDING_FOUT, Regel.R2053);
            throw new AutorisatieException(Regel.R2053);
        }
        if (!leveringsautorisatie.isGeldigOp(systeemDatum)) {
            LOGGER.info(Regel.R1261.getOmschrijving(), RegelLoggingUtil.PREFIX_LOGMELDING_FOUT, Regel.R1261);
            throw new AutorisatieException(Regel.R1261);
        }
        if (leveringsautorisatie.isGeblokkeerd()) {
            LOGGER.info(Regel.R1263.getOmschrijving(), RegelLoggingUtil.PREFIX_LOGMELDING_FOUT, Regel.R1263);
            throw new AutorisatieException(Regel.R1263);
        }
    }

    @Override
    public final BewerkAfnemerindicatieResultaat plaatsAfnemerindicatie(final ToegangLeveringsautorisatie toegangLeveringsautorisatie,
        final int persoonId,
        final int verantwoordingDienstId,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode,
        final DatumAttribuut datumEindeVolgen)
    {
        final BewerkAfnemerindicatieResultaat resultaat = new BewerkAfnemerindicatieResultaat();
        try {
            final PersoonHisVolledigImpl persoonhisVolledig = geefPersoon(persoonId);

            /**
             * Voor het uitvoeren van regels wordt nu bovenstaande dienst meegeven.
             */
            final List<? extends Bedrijfsregel> regelsVoorVerwerking = regelManager.getUitTeVoerenRegelsVoorVerwerking(EffectAfnemerindicaties.PLAATSING);

            //LET op, afnemerIndicatieDienst kan null zijn; regels checken hier verder op.
            final Leveringsautorisatie leveringsautorisatie = toegangLeveringsautorisatie.getLeveringsautorisatie();
            final Dienst afnemerIndicatieDienst = leveringsautorisatie.geefDienst(SoortDienst.PLAATSEN_AFNEMERINDICATIE);
            final List<RegelMelding> meldingen =
                voerBedrijfsregelsUit(datumAanvangMaterielePeriode, datumEindeVolgen, toegangLeveringsautorisatie, afnemerIndicatieDienst,
                    persoonhisVolledig,
                    SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE, regelsVoorVerwerking);
            resultaat.setMeldingen(meldingen);

            if (meldingen.size() == 0) {

                /**
                 * Deze dienst bepaalt de verantwoording van de te plaasen afnemerindicatie.
                 * Dit is bijv. mutatielevering obv. doelbinding
                 */
                final Dienst verantwoordingDienst = haalDienstUitLijst(leveringsautorisatie.geefDiensten(), verantwoordingDienstId);
                // De controle van een ongeldige dienstinhoud doen we na het bepalen van de meldingen door bedrijfsregels, er zijn namelijk
                // bedrijfsregels die betrekking hebben op de dienst.
                // TODO is het logisch dit pas hier te doen?
                if (verantwoordingDienst == null) {
                    throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.DIENST_ID, verantwoordingDienstId);
                }

                plaatsAfnemerindicatie(toegangLeveringsautorisatie, persoonId, verantwoordingDienst, datumAanvangMaterielePeriode, datumEindeVolgen, null);

                LOGGER.info(
                    FunctioneleMelding.LEVERING_AFNEMERINDICATIE_GEPLAATST_MET_REGELS,
                    "Afnemerindicatie geplaatst met uitvoer van regels. Leveringsautorisatie {}, persoonId {}, dienstId {}, partijCode {}",
                    toegangLeveringsautorisatie.getID(), persoonId, verantwoordingDienstId,
                    toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode());
            }
        } catch (final OnbekendeReferentieExceptie e) {
            if (OnbekendeReferentieExceptie.ReferentieVeld.PERSOON_ID.getNaam().equals(e.getReferentieVeldNaam())) {
                resultaat.getMeldingen().add(maakFoutMelding(Regel.BRLV0006));
            } else if (OnbekendeReferentieExceptie.ReferentieVeld.DIENST_ID.getNaam().equals(e.getReferentieVeldNaam())) {
                resultaat.getMeldingen().add(maakFoutMelding(Regel.R2055));
            } else {
                resultaat.getMeldingen().add(maakFoutMelding(Regel.REF0001));
                LOGGER.error(FunctioneleMelding.ALGEMEEN_ONBEKENDE_REFERENTIE,
                    AFNEMERINDICATIE_NIET_GEPLAATST_MET_UITVOER_VAN_REGELS_DOOR_ONBEKENDE_REFERENTIE_VAN_VELD, e.getReferentieVeldNaam());
            }
        } catch (final PersoonNietGevondenExceptie e) {
            resultaat.getMeldingen().add(maakFoutMelding(Regel.BRBV0006));
        }

        return resultaat;
    }

    @Override
    public final BewerkAfnemerindicatieResultaat verwijderAfnemerindicatie(final int toegangLeveringautorisatieId,
        final int persoonId,
        final int verantwoordingDienstId)
    {
        final BewerkAfnemerindicatieResultaat resultaat = new BewerkAfnemerindicatieResultaat();

        try {
            final ToegangLeveringsautorisatie toegangLeveringsautorisatie = toegangLeveringsautorisatieService
                .geefToegangleveringautorisatieZonderControle(toegangLeveringautorisatieId);
            final BewerkAfnemerindicatieResultaat resultaatVanVerwijdering = verwijderAfnemerindicatie(toegangLeveringsautorisatie, persoonId,
                verantwoordingDienstId);
            resultaat.setMeldingen(resultaatVanVerwijdering.getMeldingen());
        } catch (final EmptyResultDataAccessException e) {
            resultaat.getMeldingen().add(maakFoutMelding(Regel.BRLV0007));
        }
        return resultaat;
    }

    @Override
    public final BewerkAfnemerindicatieResultaat gbaVerwijderAfnemerindicatie(final ToegangLeveringsautorisatie toegangLeveringsautorisatie,
        final int persoonId, final int verantwoordingDienstId)
    {
        try {
            controleerToegangLeveringsautorisatie(toegangLeveringsautorisatie);
        } catch (AutorisatieException e) {
            final BewerkAfnemerindicatieResultaat resultaat = new BewerkAfnemerindicatieResultaat();
            resultaat.getMeldingen().add(maakFoutMelding(e.getRegel()));
            return resultaat;
        }

        return verwijderAfnemerindicatie(toegangLeveringsautorisatie, persoonId, verantwoordingDienstId);
    }


    @Override
    public final BewerkAfnemerindicatieResultaat verwijderAfnemerindicatie(final ToegangLeveringsautorisatie toegangLeveringsautorisatie,
        final int persoonId, final int verantwoordingDienstId)
    {
        final BewerkAfnemerindicatieResultaat resultaat = new BewerkAfnemerindicatieResultaat();
        final Leveringsautorisatie leveringsautorisatie = toegangLeveringsautorisatie.getLeveringsautorisatie();
        final Partij partij = toegangLeveringsautorisatie.getGeautoriseerde().getPartij();
        try {
            final PersoonHisVolledigImpl persoonhisVolledig = geefPersoon(persoonId);
            final List<? extends Bedrijfsregel> regelsVoorVerwerking =
                regelManager.getUitTeVoerenRegelsVoorVerwerking(EffectAfnemerindicaties.VERWIJDERING);

            //LET op, afnemerIndicatieDienst kan null zijn; regels checken hier verder op.
            final Dienst afnemerIndicatieDienst = leveringsautorisatie.geefDienst(SoortDienst.VERWIJDEREN_AFNEMERINDICATIE);
            final List<RegelMelding> meldingen =
                voerBedrijfsregelsUit(null, null, toegangLeveringsautorisatie, afnemerIndicatieDienst, persoonhisVolledig,
                    SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE,
                    regelsVoorVerwerking);
            resultaat.setMeldingen(meldingen);

            if (meldingen.size() == 0) {
                final Dienst verantwoordingDienst = haalDienstUitLijst(leveringsautorisatie.geefDiensten(), verantwoordingDienstId);
                verwijderAfnemerindicatie(partij, persoonhisVolledig, verantwoordingDienst, leveringsautorisatie);

                LOGGER.info(
                    FunctioneleMelding.LEVERING_AFNEMERINDICATIE_VERWIJDERD_MET_REGELS,
                    "Afnemerindicatie verwijderd met uitvoer van regels. Leveringsautorisatie {}, persoonId {}, dienstId {}, partijCode {}",
                    leveringsautorisatie.getID(), persoonId, verantwoordingDienstId, partij.getCode());
            }
        } catch (final OnbekendeReferentieExceptie e) {
            resultaat.getMeldingen().add(maakFoutMelding(Regel.REF0001));
            LOGGER.error(
                FunctioneleMelding.ALGEMEEN_ONBEKENDE_REFERENTIE,
                AFNEMERINDICATIE_NIET_VERWIJDERD_MET_UITVOER_VAN_REGELS_DOOR_ONBEKENDE_REFERENTIE_VAN_VELD,
                e.getReferentieVeldNaam());
        }
        return resultaat;
    }

    /**
     * Voert de regels uit voor het plaatsen of verwijderen van afnemerindicaties.
     *
     * @param datumAanvangMaterielePeriode  De datum aanvang materiele periode.
     * @param datumEindeVolgen              De datum einde volgen.
     * @param toegangLeveringsautorisatie   De leveringsautorisatie.
     * @param dienst                        De dienst plaatsen/verwijderen afnemerindicatie, of null
     * @param persoonhisVolledig            De persoon his volledig.
     * @param soortAdministratieveHandeling Het soort administratieve handeling.
     * @param regelsVoorVerwerking          De regels die uitgevoerd moeten worden.
     * @return Een lijst van meldingen bij fouten, anders een lege lijst.
     */
    private List<RegelMelding> voerBedrijfsregelsUit(final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode,
        final DatumAttribuut datumEindeVolgen, final ToegangLeveringsautorisatie toegangLeveringsautorisatie, final Dienst dienst,
        final PersoonHisVolledigImpl persoonhisVolledig,
        final SoortAdministratieveHandeling soortAdministratieveHandeling,
        final List<? extends Bedrijfsregel> regelsVoorVerwerking)
    {
        final List<RegelMelding> meldingen = new ArrayList<>();
        final PersoonView persoonView;
        if (persoonhisVolledig != null) {
            persoonView = new PersoonView(persoonhisVolledig);
        } else {
            persoonView = null;
        }

        final Leveringsautorisatie leveringsautorisatie = toegangLeveringsautorisatie.getLeveringsautorisatie();
        final Partij partij = toegangLeveringsautorisatie.getGeautoriseerde().getPartij();

        for (final Bedrijfsregel regelVoorVerwerking : regelsVoorVerwerking) {

            LOGGER.debug("Voert regel uit: {}", regelVoorVerwerking.getRegel().getCode());
            final RegelContext regelContext;
            if (regelVoorVerwerking.getContextType() == AutorisatieRegelContext.class) {
                regelContext = new AutorisatieRegelContext(toegangLeveringsautorisatie, dienst, persoonView, soortAdministratieveHandeling);

            } else if (regelVoorVerwerking.getContextType() == AfnemerindicatieRegelContext.class) {
                regelContext = new AfnemerindicatieRegelContext(partij, persoonView, soortAdministratieveHandeling, leveringsautorisatie,
                    datumAanvangMaterielePeriode, datumEindeVolgen);
            } else if (regelVoorVerwerking.getContextType() == VerstrekkingsbeperkingRegelContext.class) {
                regelContext = new VerstrekkingsbeperkingRegelContext(persoonView, partij);
            } else if (regelVoorVerwerking.getContextType() == HuidigeSituatieRegelContext.class) {
                regelContext = new HuidigeSituatieRegelContext(persoonView);
            } else {
                regelContext = null;
                LOGGER.warn(FunctioneleMelding.LEVERING_AFNEMERINDICATIE_ONGELDIGE_REGELCONTEXT,
                    "Er is een regel geconfigureerd in de afnemerindicatie-service met een niet-ondersteunde regelcontext: {}",
                    regelVoorVerwerking.getContextType().getCanonicalName());
            }

            if (regelContext != null && !regelVoorVerwerking.valideer(regelContext)) {
                final RegelMelding melding = maakMeldingModel(regelVoorVerwerking);
                meldingen.add(melding);
            }
        }

        return meldingen;
    }

    /**
     * @param bedrijfsRegel De regel.
     * @return De melding voor de gegeven regel.
     */
    private RegelMelding maakMeldingModel(final Bedrijfsregel bedrijfsRegel) {
        final RegelParameters regelParametersVoorRegel = regelManager.getRegelParametersVoorRegel(bedrijfsRegel);
        final MeldingtekstAttribuut meldingTekst = regelParametersVoorRegel.getMeldingTekst();
        return new RegelMelding(new RegelAttribuut(bedrijfsRegel.getRegel()), new SoortMeldingAttribuut(regelParametersVoorRegel.getSoortMelding()),
            meldingTekst);
    }

    private RegelMelding maakFoutMelding(final Regel regel) {
        return new RegelMelding(new RegelAttribuut(regel), new SoortMeldingAttribuut(SoortMelding.FOUT),
            new MeldingtekstAttribuut(regel.getOmschrijving()));
    }

}
