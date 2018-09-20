/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpIndicatieGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAdresInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.BRPActie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Document;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.HistorieStatus;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Partij;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonAdres;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonGeslachtsnaamcomponent;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonIndicatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonNationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonVoornaam;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Relatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortActie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortPersoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Verdrag;
import nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.Lo3Herkomst;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.MapperUtil;

/**
 * Mapper verantwoordelijk voor het mappen van een {@link BrpPersoonslijst} op een {@link Persoon}.
 */
// CHECKSTYLE:OFF Class Fan-Out complexity is hoger dan 20 maar levert hier geen onnodig complexe class op
public final class PersoonMapper {
    // CHECKSTYLE:ON

    private final DynamischeStamtabelRepository dynamischeStamtabelRepository;
    private final BRPActieFactory brpActieFactory;

    /**
     * Maakt een nieuw PersoonMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository voor het bevragen van stamtabellen
     */
    public PersoonMapper(final DynamischeStamtabelRepository dynamischeStamtabelRepository) {
        this.dynamischeStamtabelRepository = dynamischeStamtabelRepository;
        brpActieFactory = new PersoonMapper.BRPActieFactoryImpl();
    }

    /**
     * Mapped de BRP persoonslijst op de persoon entiteit.
     * 
     * @param brpPersoonslijst
     *            De persoonslijst uit het migratiemodel.
     * @param persoon
     *            de persoon entiteit waarop de gegevens uit de BRP persoonslijst moeten worden gemapped.
     */
    public void mapVanMigratie(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        persoon.setSoortPersoon(SoortPersoon.INGESCHREVENE);
        persoon.setAdministratienummer(new BigDecimal(brpPersoonslijst.getActueelAdministratienummer()));

        if (brpPersoonslijst.getAfgeleidAdministratiefStapel() != null) {
            persoon.setTijdstipLaatsteWijziging(MapperUtil.mapBrpDatumTijdToTimestamp(brpPersoonslijst
                    .getAfgeleidAdministratiefStapel().get(0).getInhoud().getLaatsteWijziging()));
        }

        mapPersoonHistorieStatus(brpPersoonslijst, persoon);
        mapPersoonGroepen(brpPersoonslijst, persoon);
        mapNationaliteitGroepen(brpPersoonslijst, persoon);
        mapGeslachtsnaamcomponentGroepen(brpPersoonslijst, persoon);
        mapIndicatieGroepen(brpPersoonslijst, persoon);
        mapReisdocumentGroepen(brpPersoonslijst, persoon);
        mapAdresGroepen(brpPersoonslijst, persoon);
        mapVoornaamGroepen(brpPersoonslijst, persoon);
        mapRelatieGroepen(brpPersoonslijst, persoon);
    }

    private void mapRelatieGroepen(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        final List<BrpRelatie> brpRelaties = brpPersoonslijst.getRelaties();
        for (final BrpRelatie brpRelatie : brpRelaties) {
            final Relatie relatie = new Relatie();
            final RelatieMapper relatieMapper = new RelatieMapper(dynamischeStamtabelRepository, brpActieFactory);
            relatieMapper.mapStapelEnOverigeRelatieGegevens(brpRelatie, relatie, persoon);
        }
    }

    private void mapVoornaamGroepen(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        final List<BrpStapel<BrpVoornaamInhoud>> voornaamStapels = brpPersoonslijst.getVoornaamStapels();
        for (final BrpStapel<BrpVoornaamInhoud> brpStapel : voornaamStapels) {
            final PersoonVoornaam persoonVoornaam = new PersoonVoornaam();
            new PersoonVoornaamMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(brpStapel,
                    persoonVoornaam);
            persoon.addPersoonVoornaam(persoonVoornaam);
        }
    }

    private void mapAdresGroepen(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        final BrpStapel<BrpAdresInhoud> adresStapel = brpPersoonslijst.getAdresStapel();
        final PersoonAdres persoonAdres = new PersoonAdres();
        new AdresMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(adresStapel, persoonAdres);
        persoon.addPersoonAdres(persoonAdres);
    }

    private void mapReisdocumentGroepen(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        final List<BrpStapel<BrpReisdocumentInhoud>> reisdocumentStapels = brpPersoonslijst.getReisdocumentStapels();
        for (final BrpStapel<BrpReisdocumentInhoud> stapel : reisdocumentStapels) {
            final PersoonReisdocument persoonReisdocument = new PersoonReisdocument();
            new PersoonReisdocumentMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(stapel,
                    persoonReisdocument);
            persoon.addPersoonReisdocument(persoonReisdocument);
        }
    }

    private void mapIndicatieGroepen(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        mapIndicatieGroep(persoon, brpPersoonslijst.getBehandeldAlsNederlanderIndicatieStapel());
        mapIndicatieGroep(persoon, brpPersoonslijst.getBelemmeringVerstrekkingReisdocumentIndicatieStapel());
        mapIndicatieGroep(persoon, brpPersoonslijst.getBezitBuitenlandsReisdocumentIndicatieStapel());
        mapIndicatieGroep(persoon, brpPersoonslijst.getDerdeHeeftGezagIndicatieStapel());
        mapIndicatieGroep(persoon, brpPersoonslijst.getGeprivilegieerdeIndicatieStapel());
        mapIndicatieGroep(persoon, brpPersoonslijst.getOnderCurateleIndicatieStapel());
        mapIndicatieGroep(persoon, brpPersoonslijst.getStatenloosIndicatieStapel());
        mapIndicatieGroep(persoon, brpPersoonslijst.getVerstrekkingsbeperkingStapel());
        mapIndicatieGroep(persoon, brpPersoonslijst.getVastgesteldNietNederlanderIndicatieStapel());
    }

    private <T extends BrpIndicatieGroepInhoud> void mapIndicatieGroep(
            final Persoon persoon,
            final BrpStapel<T> stapel) {
        if (stapel != null) {
            final PersoonIndicatie persoonIndicatie = new PersoonIndicatie();
            new PersoonIndicatieMapper<T>(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(stapel,
                    persoonIndicatie);
            persoon.addPersoonIndicatie(persoonIndicatie);
        }
    }

    private void mapGeslachtsnaamcomponentGroepen(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> stapels =
                brpPersoonslijst.getGeslachtsnaamcomponentStapels();
        for (final BrpStapel<BrpGeslachtsnaamcomponentInhoud> stapel : stapels) {
            final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent = new PersoonGeslachtsnaamcomponent();
            new PersoonGeslachtsnaamcomponentMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                    stapel, persoonGeslachtsnaamcomponent);
            persoon.addPersoonGeslachtsnaamcomponent(persoonGeslachtsnaamcomponent);
        }
    }

    private void mapNationaliteitGroepen(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        final List<BrpStapel<BrpNationaliteitInhoud>> stapels = brpPersoonslijst.getNationaliteitStapels();
        for (final BrpStapel<BrpNationaliteitInhoud> stapel : stapels) {
            final PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit();
            new NationaliteitMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(stapel,
                    persoonNationaliteit);
            persoon.addPersoonNationaliteit(persoonNationaliteit);
        }
    }

    private void mapPersoonGroepen(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        new PersoonInschrijvingMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                brpPersoonslijst.getInschrijvingStapel(), persoon);
        new PersoonSamengesteldeNaamMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                brpPersoonslijst.getSamengesteldeNaamStapel(), persoon);
        new PersoonGeslachtsaanduidingMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                brpPersoonslijst.getGeslachtsaanduidingStapel(), persoon);
        new PersoonAanschrijvingMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                brpPersoonslijst.getAanschrijvingStapel(), persoon);
        new PersoonGeboorteMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                brpPersoonslijst.getGeboorteStapel(), persoon);
        new PersoonOverlijdenMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                brpPersoonslijst.getOverlijdenStapel(), persoon);
        new PersoonIDMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                brpPersoonslijst.getIdentificatienummerStapel(), persoon);
        new PersoonBijhoudingsgemeenteMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                brpPersoonslijst.getBijhoudingsgemeenteStapel(), persoon);
        new PersoonBijhoudingsverantwoordelijkheidMapper(dynamischeStamtabelRepository, brpActieFactory)
                .mapVanMigratie(brpPersoonslijst.getBijhoudingsverantwoordelijkheidStapel(), persoon);
        new PersoonUitsluitingNLKiesrechtMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                brpPersoonslijst.getUitsluitingNederlandsKiesrechtStapel(), persoon);
        new PersoonEUVerkiezingenMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                brpPersoonslijst.getEuropeseVerkiezingenStapel(), persoon);
        new PersoonImmigratieMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                brpPersoonslijst.getImmigratieStapel(), persoon);
        new PersoonVerblijfsrechtHistorieMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                brpPersoonslijst.getVerblijfsrechtStapel(), persoon);
        new PersoonOpschortingMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                brpPersoonslijst.getOpschortingStapel(), persoon);
        new PersoonPersoonskaartMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                brpPersoonslijst.getPersoonskaartStapel(), persoon);
    }

    private void mapPersoonHistorieStatus(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        persoon.setSamengesteldeNaamStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpPersoonslijst
                .getSamengesteldeNaamStapel()));
        persoon.setAanschrijvingStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpPersoonslijst
                .getAanschrijvingStapel()));
        persoon.setEuVerkiezingenStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpPersoonslijst
                .getEuropeseVerkiezingenStapel()));
        persoon.setGeboorteStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpPersoonslijst.getGeboorteStapel()));
        persoon.setGeslachtsaanduidingStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpPersoonslijst
                .getGeslachtsaanduidingStapel()));
        persoon.setIdentificatienummersStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpPersoonslijst
                .getIdentificatienummerStapel()));
        persoon.setBijhoudingsgemeenteStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpPersoonslijst
                .getBijhoudingsgemeenteStapel()));
        persoon.setImmigratieStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpPersoonslijst
                .getImmigratieStapel()));
        persoon.setInschrijvingStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpPersoonslijst
                .getInschrijvingStapel()));
        persoon.setOpschortingStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpPersoonslijst
                .getOpschortingStapel()));
        persoon.setOverlijdenStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpPersoonslijst
                .getOverlijdenStapel()));
        persoon.setPersoonskaartStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpPersoonslijst
                .getPersoonskaartStapel()));
        persoon.setBijhoudingsverantwoordelijkheidStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpPersoonslijst
                .getBijhoudingsverantwoordelijkheidStapel()));
        persoon.setVerblijfsrechtStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpPersoonslijst
                .getVerblijfsrechtStapel()));
        persoon.setUitsluitingNLKiesrecht(HistorieStatus.bepaalHistorieStatus(brpPersoonslijst
                .getUitsluitingNederlandsKiesrechtStapel()));
    }

    /**
     * Niet threadsafe implementatie van BRPActieFactory.
     */
    private class BRPActieFactoryImpl implements BRPActieFactory {

        private final Map<Long, BRPActie> brpActieMap = new HashMap<Long, BRPActie>();

        /**
         * {@inheritDoc}
         */
        @Override
        public BRPActie getBRPActie(final BrpActie migratieActie) {
            checkPreconditie(migratieActie);
            BRPActie result = null;
            if (migratieActie != null) {
                final Long key = migratieActie.getId();
                if (brpActieMap.containsKey(key)) {
                    result = brpActieMap.get(key);
                } else {
                    result = mapBrpActieToBRPActie(migratieActie);
                    brpActieMap.put(key, result);
                    mapDocumentenVoorActie(migratieActie.getDocumentStapels(), result);
                }
            }
            return result;
        }

        private void checkPreconditie(final BrpActie migratieActie) {
            if (migratieActie != null && migratieActie.getId() == null) {
                throw new NullPointerException("De id voor de te mappen BrpActie mag niet null zijn: "
                        + migratieActie);
            }
        }

        /**
         * Mapped een BrpActie naar een BRPActie (entity).
         * 
         * @param brpActie
         *            de brpActie die gemapped moet worden, mag null zijn
         * @return de BrpActie als BRPActie of null als BrpActie null is
         */
        private BRPActie mapBrpActieToBRPActie(final BrpActie brpActie) {
            if (brpActie == null) {
                return null;
            }
            final BRPActie result = new BRPActie();
            result.setSoortActie(SoortActie.CONVERSIE_GBA);
            result.setDatumTijdOntlening(MapperUtil.mapBrpDatumTijdToTimestamp(brpActie.getDatumTijdOntlening()));
            result.setDatumTijdRegistratie(MapperUtil.mapBrpDatumTijdToTimestamp(brpActie.getDatumTijdRegistratie()));

            Partij partij = null;
            if (brpActie.getPartijCode() != null) {
                if (brpActie.getPartijCode().getNaam() != null) {
                    partij = dynamischeStamtabelRepository.findPartijByNaam(brpActie.getPartijCode().getNaam());
                }
                if (brpActie.getPartijCode().getGemeenteCode() != null) {
                    partij =
                            dynamischeStamtabelRepository.findPartijByGemeentecode(new BigDecimal(brpActie
                                    .getPartijCode().getGemeenteCode()));
                }
            }
            result.setPartij(partij);
            Verdrag verdrag = null;
            if (brpActie.getVerdragCode() != null) {
                verdrag =
                        dynamischeStamtabelRepository.findVerdragByOmschrijving(brpActie.getVerdragCode()
                                .getOmschrijving());
            }
            result.setVerdrag(verdrag);
            result.setLo3Herkomst(mapLo3Herkomst(brpActie.getLo3Herkomst()));
            // documenten worden later gemapped vanwege de circulaire referenties actie->document->actie
            return result;
        }

        private Lo3Herkomst mapLo3Herkomst(
                final nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst lo3Herkomst) {
            if (lo3Herkomst == null) {
                return null;
            }
            final Lo3Herkomst result = new Lo3Herkomst();
            result.setCategorie(lo3Herkomst.getCategorie().getCategorieAsInt());
            result.setStapel(lo3Herkomst.getStapel());
            result.setVoorkomen(lo3Herkomst.getVoorkomen());
            return result;
        }

        private void mapDocumentenVoorActie(
                final List<BrpStapel<BrpDocumentInhoud>> documentStapels,
                final BRPActie result) {
            if (documentStapels == null) {
                return;
            }
            for (final BrpStapel<BrpDocumentInhoud> documentStapel : documentStapels) {
                final Document document = new Document();
                new DocumentMapper(dynamischeStamtabelRepository, this).mapVanMigratie(documentStapel, document);
                result.addDocument(document);
            }
        }
    }
}
