/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.autorisatie.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienst;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienstbundel;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienstbundelLo3Rubriek;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpLeveringsautorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Dienst;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Dienstbundel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.DienstbundelLo3Rubriek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.EffectAfnemerindicaties;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Leveringsautorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.SoortDienst;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.ToegangLeveringsAutorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PartijRol;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Rol;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stelsel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.Lo3Rubriek;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.autorisatie.AutorisatieService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.PersoonMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie.BrpAutorisatieMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie.DienstAttenderingMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie.DienstMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie.DienstSelectieMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie.DienstbundelLo3RubriekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie.DienstbundelMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie.LeveringsAutorisatieMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie.PartijMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;

/**
 * Implementatie class voor de {@link AutorisatieService}.
 */
public final class AutorisatieServiceImpl implements AutorisatieService {

    private final DynamischeStamtabelRepository dynamischeStamtabelRepository;

    /**
     * Constructor voor de AutorisatieServiceImpl met alle repositories die nodig zijn.
     *
     * @param dynamischeStamtabelRepository
     *            repository voor dynamische stamtabellen
     */
    public AutorisatieServiceImpl(final DynamischeStamtabelRepository dynamischeStamtabelRepository) {
        this.dynamischeStamtabelRepository = dynamischeStamtabelRepository;
    }

    @Override
    public List<ToegangLeveringsAutorisatie> persisteerAutorisatie(final BrpAutorisatie brpAutorisatie) {
        // Controleer parameters
        if (brpAutorisatie == null || brpAutorisatie.getLeveringsAutorisatieLijst() == null || brpAutorisatie.getPartij() == null) {
            throw new NullPointerException("brpLeveringAutorisatie en brpPartij mag niet null zijn");
        }

        // Setup administratieve handeling voor acties
        final Partij administratieveHandelingPartij = dynamischeStamtabelRepository.getPartijByCode(BrpPartijCode.MIGRATIEVOORZIENING.getWaarde());
        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(administratieveHandelingPartij, SoortAdministratieveHandeling.GBA_INITIELE_VULLING);
        administratieveHandeling.setDatumTijdRegistratie(new Timestamp(System.currentTimeMillis()));

        final BRPActieFactory brpActieFactory = new PersoonMapper.BRPActieFactoryImpl(administratieveHandeling, null, dynamischeStamtabelRepository, null);

        // Save partij
        final Partij partij = savePartij(brpAutorisatie, brpActieFactory);
        PartijRol partijRol = dynamischeStamtabelRepository.getPartijRolByPartij(partij, Rol.AFNEMER);

        // Partij heeft nog geen rol 'AFNEMER', voeg de standaard rol 'AFNEMER' toe voor deze partij. De Ingangsdatum
        // van de rol is gelijk aan de ingangsdatum van de partij.
        if (partijRol == null) {
            final BrpDatum datumIngang = new BrpDatum(partij.getDatumIngang(), null);
            partijRol = savePartijRol(partij, datumIngang);
        }

        final List<ToegangLeveringsAutorisatie> toegangLeveringsAutorisaties = new ArrayList<>();
        for (final BrpLeveringsautorisatie brpLeveringsAutorisatie : brpAutorisatie.getLeveringsAutorisatieLijst()) {

            // LeveringAutorisatie Mapper
            final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.GBA, false);
            new LeveringsAutorisatieMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                brpLeveringsAutorisatie.getLeveringsautorisatieStapel(),
                leveringsautorisatie);

            final Set<Dienstbundel> dienstbundelSet = maakDienstbundels(brpActieFactory, brpLeveringsAutorisatie, leveringsautorisatie);

            leveringsautorisatie.setDienstbundelSet(dienstbundelSet);

            // Save leveringautorisatie.

            // Maak de toegang leveringsautorisatie aan en vul op basis van de leveringsautorisatie en de partij.
            ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
            toegangLeveringsAutorisatie.setDatumIngang(leveringsautorisatie.getDatumIngang());
            toegangLeveringsAutorisatie.setDatumEinde(leveringsautorisatie.getDatumEinde());

            // Save de toegangleveringautorisatie.
            toegangLeveringsAutorisatie = dynamischeStamtabelRepository.saveToegangLeveringsAutorisatie(toegangLeveringsAutorisatie);
            toegangLeveringsAutorisaties.add(toegangLeveringsAutorisatie);
        }

        return toegangLeveringsAutorisaties;
    }

    private Set<Dienstbundel> maakDienstbundels(
        final BRPActieFactory brpActieFactory,
        final BrpLeveringsautorisatie brpLeveringsAutorisatie,
        final Leveringsautorisatie leveringsautorisatie)
    {
        final Set<Dienstbundel> dienstbundelSet = new HashSet<>();
        for (final BrpDienstbundel brpDienstbundel : brpLeveringsAutorisatie.getDienstbundels()) {

            // Map diensten.
            final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
            new DienstbundelMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(brpDienstbundel.getDienstbundelStapel(), dienstbundel);

            final Set<Dienst> dienstSet = maakDiensten(brpActieFactory, brpDienstbundel, dienstbundel);
            dienstbundel.setDienstSet(dienstSet);

            // LO3 Rubriek
            final Set<DienstbundelLo3Rubriek> dienstbundelLo3RubriekSet = new HashSet<>();
            for (final BrpDienstbundelLo3Rubriek brpDienstbundelLo3Rubriek : brpDienstbundel.getLo3Rubrieken()) {
                final Lo3Rubriek lo3Rubriek = dynamischeStamtabelRepository.getLo3RubriekByNaam(brpDienstbundelLo3Rubriek.getConversieRubriek());
                final DienstbundelLo3Rubriek dienstbundelLo3Rubriek = new DienstbundelLo3Rubriek(dienstbundel, lo3Rubriek);
                new DienstbundelLo3RubriekMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                    brpDienstbundelLo3Rubriek.getDienstbundelLo3RubriekStapel(),
                    dienstbundelLo3Rubriek);
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

            final Dienst dienst = new Dienst(dienstbundel, SoortDienst.parseId(brpDienst.getSoortDienstCode().getCode()));
            if (brpDienst.getEffectAfnemersindicatie() != null) {
                // Zet het effectafnemersindicaties indien aanwezig.
                dienst.setEffectAfnemerindicaties(EffectAfnemerindicaties.parseId(brpDienst.getEffectAfnemersindicatie().getCode()));
            }
            // Map de dienst historie.
            new DienstMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(brpDienst.getDienstStapel(), dienst);
            // Map de dienst attendering historie.
            new DienstAttenderingMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(brpDienst.getDienstAttenderingStapel(), dienst);
            // Map de dienst selectie historie.
            new DienstSelectieMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(brpDienst.getDienstSelectieStapel(), dienst);
            dienstSet.add(dienst);
        }
        return dienstSet;
    }

    @Override
    public BrpAutorisatie bevraagAutorisatie(final Integer partijCode, final String naam, final Integer ingangsDatumRegel) {
        final Partij partij = dynamischeStamtabelRepository.getPartijByCode(partijCode);
        if (partij == null) {
            throw new IllegalStateException(String.format("Geen partij gevonden voor partijcode '%s' (bij bevragen autorisatie).", partijCode));
        }

        final PartijRol partijRol = dynamischeStamtabelRepository.getPartijRolByPartij(partij, Rol.AFNEMER);

        final List<ToegangLeveringsAutorisatie> toegangen =
                dynamischeStamtabelRepository.getToegangLeveringsautorisatieByPartijEnDatumIngang(partijRol, ingangsDatumRegel);

        if (toegangen == null) {
            return null;
        }

        return new BrpAutorisatieMapper().mapNaarMigratie(toegangen);
    }

    private Partij savePartij(final BrpAutorisatie brpAutorisatie, final BRPActieFactory brpActieFactory) {
        final int partijCode = brpAutorisatie.getPartij().getPartijCode().getWaarde();
        Partij partij = dynamischeStamtabelRepository.findPartijByCode(partijCode);
        if (partij == null) {
            // Partij bestaat nog niet
            partij = new PartijMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(brpAutorisatie.getPartij());
            updatePartijNaam(partij, brpAutorisatie.getPartij().getNaam());
            // Partij opslaan
            partij = dynamischeStamtabelRepository.savePartij(partij);
        }

        return partij;
    }

    private PartijRol savePartijRol(final Partij partij, final BrpDatum datumIngang) {
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        partijRol.setDatumIngang(MapperUtil.mapBrpDatumToInteger(datumIngang));
        return dynamischeStamtabelRepository.savePartijRol(partijRol);
    }

    /**
     * Kijkt of de partijNaam als bestaat, zoja aanvullen met partijCode. Zonee, alleen naam gebruiken.
     *
     * @param partij
     *            partij die ge-updatet moet worden
     * @param naam
     *            nieuwe naam voor de partij
     */
    private void updatePartijNaam(final Partij partij, final String naam) {
        // Zoek partij met naam, als die al bestaat moet de nieuwe partij naam: <naam afnemerindicatie> worden.
        final Partij gevondenPartij = dynamischeStamtabelRepository.findPartijByNaam(naam);
        if (gevondenPartij != null && !gevondenPartij.getId().equals(partij.getId())) {
            partij.setNaam(naam + " (" + partij.getCode() + ")");
        } else {
            partij.setNaam(naam);
        }
    }

    @Override
    public Collection<Leveringsautorisatie> geefAlleGbaAutorisaties() {
        return dynamischeStamtabelRepository.geefAlleGbaLeveringsautorisaties();
    }

}
