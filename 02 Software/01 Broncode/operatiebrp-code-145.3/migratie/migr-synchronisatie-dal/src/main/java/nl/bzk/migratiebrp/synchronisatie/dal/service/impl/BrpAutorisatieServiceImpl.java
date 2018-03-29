/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelLo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LeveringsautorisatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRolHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsautorisatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EffectAfnemerindicaties;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienst;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienstbundel;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienstbundelLo3Rubriek;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpLeveringsautorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.LeveringsautorisatieRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.PartijRolRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpAutorisatieService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PartijNietGevondenException;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.PersoonMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie.BrpAutorisatieMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie.DienstAttenderingMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie.DienstMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie.DienstSelectieMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie.DienstbundelMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie.LeveringsAutorisatieMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatie class voor de {@link BrpAutorisatieServiceImpl}.
 */
@Service
public final class BrpAutorisatieServiceImpl implements BrpAutorisatieService {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String SYNC_DAL_TRANSACTION_MANAGER = "syncDalTransactionManager";
    private static final String OPVRAGEN_PL = "Opvragen PL";
    private static final String SPACE = " ";
    private static final String POPULATIEBEPERKING_WAAR = "WAAR";
    private static final int PARTIJ_AFNEMERINDICATIE_MAXIMUM = 200_000;

    private final DynamischeStamtabelRepository dynamischeStamtabelRepository;
    private final LeveringsautorisatieRepository leveringsautorisatieRepository;
    private final PartijRolRepository partijRolRepository;

    /**
     * Constructor voor de AutorisatieServiceImpl met alle repositories die nodig zijn.
     * @param dynamischeStamtabelRepository repository voor dynamische stamtabellen
     * @param leveringsautorisatieRepository {@link LeveringsautorisatieRepository}
     * @param partijRolRepository {@link PartijRolRepository }
     */
    @Inject
    public BrpAutorisatieServiceImpl(final DynamischeStamtabelRepository dynamischeStamtabelRepository, final LeveringsautorisatieRepository
            leveringsautorisatieRepository, final PartijRolRepository partijRolRepository) {
        this.dynamischeStamtabelRepository = dynamischeStamtabelRepository;
        this.leveringsautorisatieRepository = leveringsautorisatieRepository;
        this.partijRolRepository = partijRolRepository;
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    @Override
    public List<ToegangLeveringsAutorisatie> persisteerAutorisatie(final BrpAutorisatie brpAutorisatie) throws PartijNietGevondenException {
        // Controleer parameters
        if (brpAutorisatie == null || brpAutorisatie.getLeveringsAutorisatieLijst() == null || brpAutorisatie.getPartij() == null) {
            throw new NullPointerException("brpLeveringAutorisatie en brpPartij mag niet null zijn");
        }

        // Setup administratieve handeling voor acties
        final Partij administratieveHandelingPartij = dynamischeStamtabelRepository.getPartijByCode(BrpPartijCode.MIGRATIEVOORZIENING.getWaarde());
        final Timestamp nuMoment = Timestamp.from(Instant.now());
        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(administratieveHandelingPartij, SoortAdministratieveHandeling.GBA_INITIELE_VULLING, nuMoment);

        final BRPActieFactory brpActieFactory = new PersoonMapper.BRPActieFactoryImpl(administratieveHandeling, null, dynamischeStamtabelRepository, null);

        // Bepaal partij
        Partij partij = dynamischeStamtabelRepository.getPartijByCode(brpAutorisatie.getPartij().getWaarde());
        if (partij == null) {
            Logging.log(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_35, -1, -1), LogSeverity.ERROR, SoortMeldingCode.AUT014, Lo3ElementEnum.ELEMENT_9510);
            LOG.error("Partij kan niet worden gevonden obv afnemersindicatie {}", brpAutorisatie.getPartij().getWaarde());
            throw new PartijNietGevondenException("Partij kan niet worden gevonden obv afnemersindicatie " + brpAutorisatie.getPartij().getWaarde());
        }
        // Bijwerken indicatie verstrekkingsbeperking mogelijk
        final Boolean indicatieVerstrekkingsbeperkingMogelijk = brpAutorisatie.getIndicatieVerstrekkingsbeperkingMogelijk().getWaarde();
        if (!Objects.equals(indicatieVerstrekkingsbeperkingMogelijk, partij.isIndicatieVerstrekkingsbeperkingMogelijk())) {
            LOG.info(
                    "Indicatie verstrekkingsbeperking mogelijk is gewijzigd van {} naar {}",
                    partij.isIndicatieVerstrekkingsbeperkingMogelijk(),
                    indicatieVerstrekkingsbeperkingMogelijk);
            final PartijHistorie actuelePartijHistorie = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(partij.getHisPartijen());
            final PartijHistorie partijHistorie =
                    new PartijHistorie(
                            partij,
                            nuMoment,
                            actuelePartijHistorie.getDatumIngang(),
                            indicatieVerstrekkingsbeperkingMogelijk,
                            actuelePartijHistorie.getNaam());
            partijHistorie.setDatumEinde(actuelePartijHistorie.getDatumEinde());
            partijHistorie.setOin(actuelePartijHistorie.getOin());
            actuelePartijHistorie.setDatumTijdVerval(nuMoment);

            partij.addHisPartij(partijHistorie);
            partij = dynamischeStamtabelRepository.savePartij(partij);
        }

        // Verwerken rol
        List<PartijRol> partijRollen = partijRolRepository.getPartijRollenByPartij(partij);
        PartijRol afnemerRol = geefAfnemerRol(partijRollen);
        PartijRol bijhouderRol = geefBijhouderRol(partijRollen);
        if (afnemerRol == null) {
            // Partij heeft nog geen rol 'AFNEMER', voeg de standaard rol 'AFNEMER' toe voor deze partij. De
            // Ingangsdatum van de rol is gelijk aan de oudste ingangsdatum van de leveringsautorisaties.
            LOG.info("Rol AFNEMER wordt toegevoegd voor partij {} - {}", partij.getCode(), partij.getNaam());
            afnemerRol =
                    savePartijRol(
                            partij,
                            BrpDatum.unwrap(
                                    brpAutorisatie.getLeveringsAutorisatieLijst()
                                            .iterator()
                                            .next()
                                            .getLeveringsautorisatieStapel()
                                            .getLaatsteElement()
                                            .getInhoud()
                                            .getDatumIngang()));
        }

        final List<ToegangLeveringsAutorisatie> toegangLeveringsAutorisaties = new ArrayList<>();
        for (final BrpLeveringsautorisatie brpLeveringsAutorisatie : brpAutorisatie.getLeveringsAutorisatieLijst()) {
            toegangLeveringsAutorisaties.add(maakToegangLeveringsAutorisatie(nuMoment, brpActieFactory, afnemerRol, brpLeveringsAutorisatie, ""));

            // Toevoegen opvragen PL autorisatie voor bijhouders (gemeenten en RNI)
            if (Integer.valueOf(brpAutorisatie.getPartij().getWaarde()) < PARTIJ_AFNEMERINDICATIE_MAXIMUM && bijhouderRol != null) {
                toegangLeveringsAutorisaties
                        .add(maakToegangLeveringsAutorisatie(nuMoment, brpActieFactory, bijhouderRol, brpLeveringsAutorisatie, SPACE + OPVRAGEN_PL + SPACE));
            }
        }

        return toegangLeveringsAutorisaties;
    }

    private ToegangLeveringsAutorisatie maakToegangLeveringsAutorisatie(Timestamp nuMoment, BRPActieFactory brpActieFactory, PartijRol partijRol,
                                                                        BrpLeveringsautorisatie brpLeveringsAutorisatie, final String autorisatieNaam) {
        // LeveringAutorisatie Mapper
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.GBA, false);
        new LeveringsAutorisatieMapper(dynamischeStamtabelRepository, brpActieFactory, autorisatieNaam)
                .mapVanMigratie(
                        brpLeveringsAutorisatie.getLeveringsautorisatieStapel(),
                        leveringsautorisatie,
                        null);

        final LeveringsautorisatieHistorie leveringsautorisatieHistorie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(leveringsautorisatie.getLeveringsautorisatieHistorieSet());
        final Set<Dienstbundel> dienstbundelSet;

        if (partijRol.getRol() == Rol.BIJHOUDINGSORGAAN_MINISTER || partijRol.getRol() == Rol.BIJHOUDINGSORGAAN_COLLEGE) {
            dienstbundelSet = Collections.singleton(maakOpvragenPLDienstbundel(brpLeveringsAutorisatie, leveringsautorisatie));
        } else {
            dienstbundelSet = maakDienstbundels(brpActieFactory, brpLeveringsAutorisatie, leveringsautorisatie);
        }
        leveringsautorisatie.setDienstbundelSet(dienstbundelSet);

        // Maak de toegang leveringsautorisatie aan en vul op basis van de leveringsautorisatie en de partij.
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final ToegangLeveringsautorisatieHistorie toegangLeveringsAutorisatieHistorie =
                new ToegangLeveringsautorisatieHistorie(toegangLeveringsAutorisatie, nuMoment, leveringsautorisatieHistorie.getDatumIngang());
        toegangLeveringsAutorisatieHistorie.setDatumEinde(leveringsautorisatieHistorie.getDatumEinde());
        toegangLeveringsAutorisatie.addToegangLeveringsautorisatieHistorieSet(toegangLeveringsAutorisatieHistorie);

        // Save de leveringautorisatie.
        leveringsautorisatie.addToegangLeveringsautorisatieSet(toegangLeveringsAutorisatie);
        leveringsautorisatieRepository.saveLeveringsautorisatie(leveringsautorisatie);

        return toegangLeveringsAutorisatie;
    }

    private PartijRol geefAfnemerRol(final List<PartijRol> partijRolSet) {
        Optional<PartijRol> partijRolOptional = partijRolSet.stream().filter(rol -> rol.getRol() == Rol.AFNEMER).findAny();
        return partijRolOptional.isPresent() ? partijRolOptional.get() : null;
    }

    private PartijRol geefBijhouderRol(final List<PartijRol> partijRolSet) {
        Optional<PartijRol>
                partijRolOptional =
                partijRolSet.stream().filter(rol -> rol.getRol() == Rol.BIJHOUDINGSORGAAN_COLLEGE || rol.getRol() == Rol.BIJHOUDINGSORGAAN_MINISTER).findAny();
        return partijRolOptional.isPresent() ? partijRolOptional.get() : null;
    }


    private Dienstbundel maakOpvragenPLDienstbundel(final BrpLeveringsautorisatie brpLeveringsAutorisatie,
                                                    final Leveringsautorisatie leveringsautorisatie) {
        final Integer
                datumIngang =
                MapperUtil.mapBrpDatumToInteger(brpLeveringsAutorisatie.getLeveringsautorisatieStapel().getActueel().getInhoud().getDatumIngang());
        final Integer
                datumEinde =
                MapperUtil.mapBrpDatumToInteger(brpLeveringsAutorisatie.getLeveringsautorisatieStapel().getActueel().getInhoud().getDatumEinde());
        final Timestamp tijdstipRegistratie =
                MapperUtil.mapBrpDatumToTimestamp(brpLeveringsAutorisatie.getLeveringsautorisatieStapel().getActueel().getInhoud().getDatumIngang());
        final Dienstbundel opvragenPLDienstbundel = new Dienstbundel(leveringsautorisatie);
        opvragenPLDienstbundel.setDatumEinde(datumEinde);
        opvragenPLDienstbundel.setDatumIngang(datumIngang);
        opvragenPLDienstbundel.setNaam(OPVRAGEN_PL);
        opvragenPLDienstbundel.setNaderePopulatiebeperking(POPULATIEBEPERKING_WAAR);
        opvragenPLDienstbundel.addDienstSet(maakOpvragenPLDienst(tijdstipRegistratie, datumIngang, datumEinde, opvragenPLDienstbundel));
        final DienstbundelHistorie
                opvragenPLDienstbundelHistorie =
                new DienstbundelHistorie(opvragenPLDienstbundel, tijdstipRegistratie, OPVRAGEN_PL, datumIngang);
        opvragenPLDienstbundelHistorie.setDatumEinde(datumEinde);
        opvragenPLDienstbundelHistorie.setNaderePopulatiebeperking(POPULATIEBEPERKING_WAAR);
        opvragenPLDienstbundel.addDienstbundelHistorieSet(opvragenPLDienstbundelHistorie);
        return opvragenPLDienstbundel;
    }

    private Dienst maakOpvragenPLDienst(final Timestamp tijdstipRegistratie, final Integer datumIngang, final Integer datumEinde,
                                        final Dienstbundel opvragenPLDienstbundel) {
        final Dienst opvragenPLDienst = new Dienst(opvragenPLDienstbundel, SoortDienst.ZOEK_PERSOON);
        opvragenPLDienst.setDatumEinde(datumEinde);
        opvragenPLDienst.setDatumIngang(datumIngang);
        final DienstHistorie
                dienstHistorie =
                new DienstHistorie(opvragenPLDienst, tijdstipRegistratie, datumIngang);
        dienstHistorie.setDatumEinde(datumEinde);
        opvragenPLDienst.addDienstHistorieSet(dienstHistorie);
        return opvragenPLDienst;
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public BrpAutorisatie bevraagAutorisatie(final String partijCode, final String naam, final Integer ingangsDatumRegel) {
        final Partij partij = dynamischeStamtabelRepository.getPartijByCode(partijCode);
        if (partij == null) {
            throw new IllegalStateException(String.format("Geen partij gevonden voor partijcode '%s' (bij bevragen autorisatie).", partijCode));
        }

        List<ToegangLeveringsAutorisatie> toegangen = new ArrayList<>();
        final PartijRol afnemerRol = partijRolRepository.getPartijRolByPartij(partij, Rol.AFNEMER);
        if (afnemerRol != null) {
            toegangen.addAll(leveringsautorisatieRepository.getToegangLeveringsautorisatieByPartijEnDatumIngang(afnemerRol, ingangsDatumRegel));
        }
        final PartijRol collegeRol = partijRolRepository.getPartijRolByPartij(partij, Rol.BIJHOUDINGSORGAAN_COLLEGE);
        if (collegeRol != null) {
            toegangen.addAll(leveringsautorisatieRepository.getToegangLeveringsautorisatieByPartijEnDatumIngang(collegeRol, ingangsDatumRegel));
        }
        final PartijRol ministerRol = partijRolRepository.getPartijRolByPartij(partij, Rol.BIJHOUDINGSORGAAN_MINISTER);
        if (ministerRol != null) {
            toegangen.addAll(leveringsautorisatieRepository.getToegangLeveringsautorisatieByPartijEnDatumIngang(ministerRol, ingangsDatumRegel));
        }

        if (toegangen.isEmpty()) {
            return null;
        }

        return new BrpAutorisatieMapper().mapNaarMigratie(toegangen);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Collection<Leveringsautorisatie> geefAlleGbaAutorisaties() {
        return leveringsautorisatieRepository.geefAlleGbaLeveringsautorisaties();
    }

    private PartijRol savePartijRol(final Partij partij, final Integer datumIngang) {
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        partijRol.getPartijBijhoudingHistorieSet().add(new PartijRolHistorie(partijRol, new Timestamp(System.currentTimeMillis()), datumIngang));

        return partijRolRepository.savePartijRol(partijRol);
    }

    private Set<Dienstbundel> maakDienstbundels(
            final BRPActieFactory brpActieFactory,
            final BrpLeveringsautorisatie brpLeveringsAutorisatie,
            final Leveringsautorisatie leveringsautorisatie) {
        final Set<Dienstbundel> dienstbundelSet = new HashSet<>();
        for (final BrpDienstbundel brpDienstbundel : brpLeveringsAutorisatie.getDienstbundels()) {

            // Map diensten.
            final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
            new DienstbundelMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                    brpDienstbundel.getDienstbundelStapel(),
                    dienstbundel,
                    null);

            final Set<Dienst> dienstSet = maakDiensten(brpActieFactory, brpDienstbundel, dienstbundel);
            dienstbundel.setDienstSet(dienstSet);

            // LO3 Rubriek
            final Set<DienstbundelLo3Rubriek> dienstbundelLo3RubriekSet = new HashSet<>();
            for (final BrpDienstbundelLo3Rubriek brpDienstbundelLo3Rubriek : brpDienstbundel.getLo3Rubrieken()) {
                final Lo3Rubriek lo3Rubriek = dynamischeStamtabelRepository.getLo3RubriekByNaam(brpDienstbundelLo3Rubriek.getConversieRubriek());
                final DienstbundelLo3Rubriek dienstbundelLo3Rubriek = new DienstbundelLo3Rubriek(dienstbundel, lo3Rubriek);
                dienstbundelLo3RubriekSet.add(dienstbundelLo3Rubriek);
            }

            dienstbundel.setDienstbundelLo3RubriekSet(dienstbundelLo3RubriekSet);
            dienstbundelSet.add(dienstbundel);
        }
        return dienstbundelSet;
    }

    private Set<Dienst> maakDiensten(final BRPActieFactory brpActieFactory, final BrpDienstbundel brpDienstbundel, final Dienstbundel dienstbundel) {
        final Set<Dienst> dienstSet = new HashSet<>();
        for (final BrpDienst brpDienst : brpDienstbundel.getDiensten()) {

            final Dienst dienst = new Dienst(dienstbundel, SoortDienst.parseId((int) brpDienst.getSoortDienstCode().getCode()));
            if (brpDienst.getEffectAfnemersindicatie() != null) {
                // Zet het effectafnemersindicaties indien aanwezig.
                dienst.setEffectAfnemerindicaties(EffectAfnemerindicaties.parseId((int) brpDienst.getEffectAfnemersindicatie().getCode()));
            }
            // Map de dienst historie.
            new DienstMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(brpDienst.getDienstStapel(), dienst, null);
            // Map de dienst attendering historie.
            new DienstAttenderingMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                    brpDienst.getDienstAttenderingStapel(),
                    dienst,
                    null);
            // Map de dienst selectie historie.
            new DienstSelectieMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(brpDienst.getDienstSelectieStapel(), dienst, null);
            dienstSet.add(dienst);
        }
        return dienstSet;
    }
}
