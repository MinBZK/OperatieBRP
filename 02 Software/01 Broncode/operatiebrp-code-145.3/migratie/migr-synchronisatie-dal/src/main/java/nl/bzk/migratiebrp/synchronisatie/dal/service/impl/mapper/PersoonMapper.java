/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.sql.Timestamp;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ActieBron;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Document;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBuitenlandsPersoonsnummer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerificatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpIndicatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBuitenlandsPersoonsnummerInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.synchronisatie.dal.service.SyncParameters;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapper verantwoordelijk voor het mappen van een {@link BrpPersoonslijst} op een {@link Persoon}.
 */
public final class PersoonMapper {

    private final DynamischeStamtabelRepository dynamischeStamtabelRepository;
    private final SyncParameters syncParameters;
    private final OnderzoekMapper onderzoekMapper;
    private BRPActieFactory brpActieFactory;

    /**
     * Maakt een nieuw PersoonMapper object.
     * @param repositoryDynamischeStamtabel de repository voor het bevragen van stamtabellen
     * @param synchronisatieParameters de synchronisatie parameters
     * @param mapperOnderzoek de mapper voor onderzoeken
     */
    public PersoonMapper(
            final DynamischeStamtabelRepository repositoryDynamischeStamtabel,
            final SyncParameters synchronisatieParameters,
            final OnderzoekMapper mapperOnderzoek) {
        dynamischeStamtabelRepository = repositoryDynamischeStamtabel;
        syncParameters = synchronisatieParameters;
        onderzoekMapper = mapperOnderzoek;
    }

    /**
     * Mapped de BRP persoonslijst op de persoon entiteit.
     * @param brpPersoonslijst De persoonslijst uit het migratiemodel.
     * @param persoon de persoon entiteit waarop de gegevens uit de BRP persoonslijst moeten worden gemapped.
     * @param lo3Bericht het bericht waaruit de persoonslijst is gekomen
     */
    public void mapVanMigratie(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon, final Lo3Bericht lo3Bericht) {
        brpActieFactory = new PersoonMapper.BRPActieFactoryImpl(maakAdministratieveHandeling(), lo3Bericht, dynamischeStamtabelRepository, onderzoekMapper);

        persoon.setSoortPersoon(SoortPersoon.INGESCHREVENE);

        mapPersoonGroepen(brpPersoonslijst, persoon);
        mapNationaliteitGroepen(brpPersoonslijst, persoon);
        mapBuitenlandsPersoonsnummerGroepen(brpPersoonslijst, persoon);
        mapGeslachtsnaamcomponentGroepen(brpPersoonslijst, persoon);
        mapIndicatieGroepen(brpPersoonslijst, persoon);
        mapReisdocumentGroepen(brpPersoonslijst, persoon);
        mapAdresGroepen(brpPersoonslijst, persoon);
        mapVoornaamGroepen(brpPersoonslijst, persoon);
        mapVerificatieGroepen(brpPersoonslijst, persoon);

        // Map Relaties en Ist groepen
        final Map<Lo3CategorieEnum, Map<Integer, Stapel>> stapelsPerCategorie = mapIstGroepen(brpPersoonslijst, persoon);
        mapRelatieGroepen(brpPersoonslijst, persoon, stapelsPerCategorie);

        // voeg lo3bericht obv conversie(tracing) toe aan persoon
        persoon.addLo3Bericht(lo3Bericht);
        // voeg lo3bericht obv logging toe aan persoon
        new LoggingMapper().mapLogging(Logging.getLogging(), lo3Bericht);
    }

    private void mapRelatieGroepen(
            final BrpPersoonslijst brpPersoonslijst,
            final Persoon persoon,
            final Map<Lo3CategorieEnum, Map<Integer, Stapel>> stapelsPerCategorie) {
        final List<BrpRelatie> brpRelaties = brpPersoonslijst.getRelaties();
        for (final BrpRelatie brpRelatie : brpRelaties) {
            final Relatie relatie = new Relatie(SoortRelatie.parseCode(brpRelatie.getSoortRelatieCode().getWaarde()));
            final RelatieMapper relatieMapper = new RelatieMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
            relatieMapper.mapStapelEnOverigeRelatieGegevens(brpRelatie, relatie, persoon, stapelsPerCategorie);
        }
    }

    private void mapVoornaamGroepen(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        final List<BrpStapel<BrpVoornaamInhoud>> voornaamStapels = brpPersoonslijst.getVoornaamStapels();
        for (final BrpStapel<BrpVoornaamInhoud> brpStapel : voornaamStapels) {
            final PersoonVoornaam persoonVoornaam = new PersoonVoornaam(persoon, BrpInteger.unwrap(brpStapel.getLaatsteElement().getInhoud().getVolgnummer()));
            new PersoonVoornaamMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper).mapVanMigratie(brpStapel, persoonVoornaam, null);
            persoon.addPersoonVoornaam(persoonVoornaam);
        }
    }

    private void mapVerificatieGroepen(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        final List<BrpStapel<BrpVerificatieInhoud>> verificatieStapels = brpPersoonslijst.getVerificatieStapels();
        for (final BrpStapel<BrpVerificatieInhoud> brpStapel : verificatieStapels) {
            final Partij partij = dynamischeStamtabelRepository.getPartijByCode(brpStapel.getLaatsteElement().getInhoud().getPartij().getWaarde());
            final PersoonVerificatie persoonVerificatie =
                    new PersoonVerificatie(persoon, partij, brpStapel.getLaatsteElement().getInhoud().getSoort().getWaarde());
            new PersoonVerificatieMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper).mapVanMigratie(brpStapel, persoonVerificatie, null);
            persoon.addPersoonVerificatie(persoonVerificatie);
        }
    }

    private void mapAdresGroepen(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        final BrpStapel<BrpAdresInhoud> adresStapel = brpPersoonslijst.getAdresStapel();
        if (adresStapel != null) {
            final PersoonAdres persoonAdres = new PersoonAdres(persoon);
            new AdresMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper).mapVanMigratie(adresStapel, persoonAdres, null);
            persoon.addPersoonAdres(persoonAdres);
        }
    }

    private void mapReisdocumentGroepen(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        final List<BrpStapel<BrpReisdocumentInhoud>> reisdocumentStapels = brpPersoonslijst.getReisdocumentStapels();
        for (final BrpStapel<BrpReisdocumentInhoud> stapel : reisdocumentStapels) {
            final PersoonReisdocumentMapper mapper = new PersoonReisdocumentMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
            final PersoonReisdocument persoonReisdocument =
                    new PersoonReisdocument(persoon, mapper.getStamtabelMapping().findSoortNederlandsReisdocumentByCode(
                            stapel.getLaatsteElement().getInhoud().getSoort()));
            mapper.mapVanMigratie(stapel, persoonReisdocument, null);
            persoon.addPersoonReisdocument(persoonReisdocument);
        }
    }

    private void mapIndicatieGroepen(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        mapIndicatieGroep(persoon, brpPersoonslijst.getBehandeldAlsNederlanderIndicatieStapel());
        mapIndicatieGroep(persoon, brpPersoonslijst.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel());
        mapIndicatieGroep(persoon, brpPersoonslijst.getDerdeHeeftGezagIndicatieStapel());
        mapIndicatieGroep(persoon, brpPersoonslijst.getOnderCurateleIndicatieStapel());
        mapIndicatieGroep(persoon, brpPersoonslijst.getStaatloosIndicatieStapel());
        mapIndicatieGroep(persoon, brpPersoonslijst.getVerstrekkingsbeperkingIndicatieStapel());
        mapIndicatieGroep(persoon, brpPersoonslijst.getVastgesteldNietNederlanderIndicatieStapel());
        mapIndicatieGroep(persoon, brpPersoonslijst.getBijzondereVerblijfsrechtelijkePositieIndicatieStapel());
        mapIndicatieGroep(persoon, brpPersoonslijst.getOnverwerktDocumentAanwezigIndicatieStapel());
    }

    private <T extends AbstractBrpIndicatieGroepInhoud> void mapIndicatieGroep(final Persoon persoon, final BrpStapel<T> stapel) {
        if (stapel != null) {
            final PersoonIndicatie persoonIndicatie =
                    new PersoonIndicatie(persoon, PersoonIndicatieMapper.mapBrpClassOpIndicatie(stapel.getLaatsteElement().getInhoud().getClass()));
            new PersoonIndicatieMapper<T>(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper).mapVanMigratie(stapel, persoonIndicatie, null);
            persoon.addPersoonIndicatie(persoonIndicatie);
        }
    }

    private void mapGeslachtsnaamcomponentGroepen(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> stapels = brpPersoonslijst.getGeslachtsnaamcomponentStapels();
        int volgnummer = 1;
        for (final BrpStapel<BrpGeslachtsnaamcomponentInhoud> stapel : stapels) {
            final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent = new PersoonGeslachtsnaamcomponent(persoon, volgnummer);
            volgnummer++;
            final PersoonGeslachtsnaamcomponentMapper persoonGeslachtsnaamcomponentMapper;
            persoonGeslachtsnaamcomponentMapper = new PersoonGeslachtsnaamcomponentMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
            persoonGeslachtsnaamcomponentMapper.mapVanMigratie(stapel, persoonGeslachtsnaamcomponent, null);
            persoon.addPersoonGeslachtsnaamcomponent(persoonGeslachtsnaamcomponent);
        }
    }

    private Map<Lo3CategorieEnum, Map<Integer, Stapel>> mapIstGroepen(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        final AdministratieveHandeling administratieveHandeling = brpActieFactory.getAdministratieveHandeling();
        final IstRelatieMapper istRelatieMapper = new IstRelatieMapper(dynamischeStamtabelRepository, administratieveHandeling);
        final IstHuwelijkOfGpMapper istHuwelijkOfGpMapper = new IstHuwelijkOfGpMapper(dynamischeStamtabelRepository, administratieveHandeling);
        final IstGezagsverhoudingMapper istGezagsverhoudingMapper = new IstGezagsverhoudingMapper(dynamischeStamtabelRepository, administratieveHandeling);
        final EnumMap<Lo3CategorieEnum, Map<Integer, Stapel>> stapelsPerCategorie = new EnumMap<>(Lo3CategorieEnum.class);

        // Ouder 1
        final BrpStapel<BrpIstRelatieGroepInhoud> ouder1Stapel = brpPersoonslijst.getIstOuder1Stapel();
        if (ouder1Stapel != null) {
            stapelsPerCategorie.putAll(istRelatieMapper.mapIstStapelOpPersoon(ouder1Stapel, persoon));
        }

        // Ouder 2
        final BrpStapel<BrpIstRelatieGroepInhoud> ouder2Stapel = brpPersoonslijst.getIstOuder2Stapel();
        if (ouder2Stapel != null) {
            stapelsPerCategorie.putAll(istRelatieMapper.mapIstStapelOpPersoon(ouder2Stapel, persoon));
        }

        // Huwelijk of geregistreerd partnerschap
        final List<BrpStapel<BrpIstHuwelijkOfGpGroepInhoud>> huwelijkOfGpStapels = brpPersoonslijst.getIstHuwelijkOfGpStapels();
        if (huwelijkOfGpStapels != null) {
            stapelsPerCategorie.putAll(istHuwelijkOfGpMapper.mapIstStapelsOpPersoon(huwelijkOfGpStapels, persoon));
        }

        // kind
        final List<BrpStapel<BrpIstRelatieGroepInhoud>> kindStapels = brpPersoonslijst.getIstKindStapels();
        if (kindStapels != null) {
            stapelsPerCategorie.putAll(istRelatieMapper.mapIstStapelsOpPersoon(kindStapels, persoon));
        }

        // Gezagsverhouding
        final BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> gezagsverhoudingStapel = brpPersoonslijst.getIstGezagsverhoudingsStapel();
        if (gezagsverhoudingStapel != null) {
            stapelsPerCategorie.putAll(istGezagsverhoudingMapper.mapIstStapelOpPersoon(gezagsverhoudingStapel, persoon));
        }

        return stapelsPerCategorie;
    }

    private void mapNationaliteitGroepen(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        final List<BrpStapel<BrpNationaliteitInhoud>> stapels = brpPersoonslijst.getNationaliteitStapels();
        for (final BrpStapel<BrpNationaliteitInhoud> stapel : stapels) {
            final NationaliteitMapper mapper = new NationaliteitMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
            final PersoonNationaliteit persoonNationaliteit =
                    new PersoonNationaliteit(persoon, mapper.getStamtabelMapping().findNationaliteitByCode(
                            stapel.getLaatsteElement().getInhoud().getNationaliteitCode()));

            mapper.mapVanMigratie(stapel, persoonNationaliteit, null);
            persoon.addPersoonNationaliteit(persoonNationaliteit);
        }
    }

    private void mapBuitenlandsPersoonsnummerGroepen(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        final List<BrpStapel<BrpBuitenlandsPersoonsnummerInhoud>> stapels = brpPersoonslijst.getBuitenlandsPersoonsnummerStapels();
        for (final BrpStapel<BrpBuitenlandsPersoonsnummerInhoud> stapel : stapels) {
            final BuitenlandsPersoonsnummerMapper mapper = new BuitenlandsPersoonsnummerMapper(dynamischeStamtabelRepository, brpActieFactory,
                    onderzoekMapper);
            final BrpBuitenlandsPersoonsnummerInhoud inhoud = stapel.getLaatsteElement().getInhoud();
            final PersoonBuitenlandsPersoonsnummer persoonBuitenlandsPersoonsnummer =
                    new PersoonBuitenlandsPersoonsnummer(persoon, mapper.getStamtabelMapping().findAutoriteitVanAfgifteBuitenlandsPersoonsnummer(
                            inhoud.getAutoriteitVanAfgifte()), inhoud.getNummer().getWaarde());

            mapper.mapVanMigratie(stapel, persoonBuitenlandsPersoonsnummer, null);
            persoon.addPersoonBuitenlandsPersoonsnummer(persoonBuitenlandsPersoonsnummer);
        }
    }

    private void mapPersoonGroepen(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        final PersoonAfgeleidAdministratiefMapper persoonAfgeleidAdministratiefMapper =
                new PersoonAfgeleidAdministratiefMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        persoonAfgeleidAdministratiefMapper.mapVanMigratie(brpPersoonslijst.getPersoonAfgeleidAdministratiefStapel(), persoon, null);
        final PersoonInschrijvingMapper persoonInschrijvingMapper =
                new PersoonInschrijvingMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        persoonInschrijvingMapper.mapVanMigratie(brpPersoonslijst.getInschrijvingStapel(), persoon, null);

        final PersoonNummerverwijzingMapper persoonNummerverwijzingMapper =
                new PersoonNummerverwijzingMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        persoonNummerverwijzingMapper.mapVanMigratie(brpPersoonslijst.getNummerverwijzingStapel(), persoon, null);
        final PersoonSamengesteldeNaamMapper persoonSamengesteldeNaamMapper =
                new PersoonSamengesteldeNaamMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        persoonSamengesteldeNaamMapper.mapVanMigratie(brpPersoonslijst.getSamengesteldeNaamStapel(), persoon, null);
        final PersoonGeslachtsaanduidingMapper persoonGeslachtsaanduidingMapper =
                new PersoonGeslachtsaanduidingMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        persoonGeslachtsaanduidingMapper.mapVanMigratie(brpPersoonslijst.getGeslachtsaanduidingStapel(), persoon, null);
        final PersoonNaamgebruikMapper persoonNaamgebruikMapper = new PersoonNaamgebruikMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        persoonNaamgebruikMapper.mapVanMigratie(brpPersoonslijst.getNaamgebruikStapel(), persoon, null);
        final PersoonGeboorteMapper persoonGeboorteMapper = new PersoonGeboorteMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        persoonGeboorteMapper.mapVanMigratie(brpPersoonslijst.getGeboorteStapel(), persoon, null);
        final PersoonOverlijdenMapper persoonOverlijdenMapper = new PersoonOverlijdenMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        persoonOverlijdenMapper.mapVanMigratie(brpPersoonslijst.getOverlijdenStapel(), persoon, null);
        final PersoonIDMapper persoonIDMapper = new PersoonIDMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        persoonIDMapper.mapVanMigratie(brpPersoonslijst.getIdentificatienummerStapel(), persoon, null);
        final PersoonBijhoudingMapper persoonBijhoudingMapper = new PersoonBijhoudingMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        persoonBijhoudingMapper.mapVanMigratie(brpPersoonslijst.getBijhoudingStapel(), persoon, null);
        final PersoonUitsluitingKiesrechtMapper persoonUitsluitingKiesrechtMapper =
                new PersoonUitsluitingKiesrechtMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        persoonUitsluitingKiesrechtMapper.mapVanMigratie(brpPersoonslijst.getUitsluitingKiesrechtStapel(), persoon, null);
        final PersoonDeelnameEuVerkiezingenMapper persoonDeelnameEuVerkiezingenMapper =
                new PersoonDeelnameEuVerkiezingenMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        persoonDeelnameEuVerkiezingenMapper.mapVanMigratie(brpPersoonslijst.getDeelnameEuVerkiezingenStapel(), persoon, null);
        final PersoonMigratieMapper persoonMigratieMapper = new PersoonMigratieMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        persoonMigratieMapper.mapVanMigratie(brpPersoonslijst.getMigratieStapel(), persoon, null);
        final PersoonVerblijfsrechtHistorieMapper persoonVerblijfsrechtHistorieMapper =
                new PersoonVerblijfsrechtHistorieMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        persoonVerblijfsrechtHistorieMapper.mapVanMigratie(brpPersoonslijst.getVerblijfsrechtStapel(), persoon, null);
        final PersoonPersoonskaartMapper persoonPersoonskaartMapper =
                new PersoonPersoonskaartMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        persoonPersoonskaartMapper.mapVanMigratie(brpPersoonslijst.getPersoonskaartStapel(), persoon, null);
    }

    private AdministratieveHandeling maakAdministratieveHandeling() {
        final SoortAdministratieveHandeling soortAdministratieveHandeling;
        if (syncParameters.isInitieleVulling()) {
            soortAdministratieveHandeling = SoortAdministratieveHandeling.GBA_INITIELE_VULLING;
        } else {
            soortAdministratieveHandeling = SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL;
        }

        final Partij partij = dynamischeStamtabelRepository.getPartijByCode(BrpPartijCode.MIGRATIEVOORZIENING.getWaarde());
        return new AdministratieveHandeling(partij, soortAdministratieveHandeling, new Timestamp(System.currentTimeMillis()));
    }

    /**
     * Niet threadsafe implementatie van BRPActieFactory.
     */
    public static class BRPActieFactoryImpl implements BRPActieFactory {

        private final Map<Long, BRPActie> brpActieMap = new HashMap<>();
        private final AdministratieveHandeling administratieveHandeling;
        private final Lo3Bericht lo3Bericht;
        private final DynamischeStamtabelRepository dynamischeStamtabelRepository;
        private final OnderzoekMapper onderzoekMapper;

        /**
         * Constructor implementatie van BrpActieFactory.
         * @param administratievehandeling De administatieve handeling.
         * @param berichtLo3 het lo3bericht waaraan de lo3voorkomens worden toegevoegd
         * @param repositoryDynamischeStamtabel De dynamische stamtabel repository.
         * @param mapperOnderzoek de mapper voor onderzoeken
         */
        public BRPActieFactoryImpl(
                final AdministratieveHandeling administratievehandeling,
                final Lo3Bericht berichtLo3,
                final DynamischeStamtabelRepository repositoryDynamischeStamtabel,
                final OnderzoekMapper mapperOnderzoek) {
            administratieveHandeling = administratievehandeling;
            lo3Bericht = berichtLo3;
            dynamischeStamtabelRepository = repositoryDynamischeStamtabel;
            onderzoekMapper = mapperOnderzoek;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public final BRPActie getBRPActie(final BrpActie migratieActie) {
            checkPreconditie(migratieActie);
            BRPActie result = null;
            if (migratieActie != null) {
                final Long key = migratieActie.getId();
                if (brpActieMap.containsKey(key)) {
                    result = brpActieMap.get(key);
                } else {
                    result = mapBrpActieToBRPActie(migratieActie);
                    brpActieMap.put(key, result);
                    mapDocumentenVoorActie(migratieActie, result);
                }
            }
            return result;
        }

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory#
         * getAdministratieveHandeling ()
         */
        @Override
        public final AdministratieveHandeling getAdministratieveHandeling() {
            return administratieveHandeling;
        }

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory#getLo3Bericht()
         */
        @Override
        public final Lo3Bericht getLo3Bericht() {
            return lo3Bericht;
        }

        private void checkPreconditie(final BrpActie migratieActie) {
            if (migratieActie != null && migratieActie.getId() == null) {
                throw new NullPointerException("De id voor de te mappen BrpActie mag niet null zijn: " + migratieActie);
            }
        }

        /**
         * Mapped een BrpActie naar een BRPActie (entity).
         * @param brpActie de brpActie die gemapped moet worden, mag null zijn
         * @return de BrpActie als BRPActie of null als BrpActie null is
         */
        private BRPActie mapBrpActieToBRPActie(final BrpActie brpActie) {
            if (brpActie == null) {
                return null;
            }
            Partij partij = null;
            if (brpActie.getPartijCode() != null) {
                partij = dynamischeStamtabelRepository.getPartijByCode(brpActie.getPartijCode().getWaarde());
            }

            final BRPActie result =
                    new BRPActie(
                            mapSoortActie(brpActie.getSoortActieCode()),
                            administratieveHandeling,
                            partij,
                            MapperUtil.mapBrpDatumTijdToTimestamp(brpActie.getDatumTijdRegistratie()));

            result.setDatumOntlening(MapperUtil.mapBrpDatumToInteger(brpActie.getDatumOntlening()));

            if (onderzoekMapper != null) {
                onderzoekMapper.mapOnderzoek(result, brpActie.getSoortActieCode(), Element.ACTIE_SOORTNAAM);
                onderzoekMapper.mapOnderzoek(result, brpActie.getPartijCode(), Element.ACTIE_PARTIJCODE);
                onderzoekMapper.mapOnderzoek(result, brpActie.getDatumTijdRegistratie(), Element.ACTIE_TIJDSTIPREGISTRATIE);
                onderzoekMapper.mapOnderzoek(result, brpActie.getDatumOntlening(), Element.ACTIE_DATUMONTLENING);
            }

            final Lo3Voorkomen lo3Voorkomen = mapLo3Herkomst(lo3Bericht, brpActie.getLo3Herkomst());
            if (lo3Voorkomen != null) {
                lo3Voorkomen.setActie(result);
            }

            // documenten worden later gemapped vanwege de circulaire referenties actie->document->actie
            return result;
        }

        private SoortActie mapSoortActie(final BrpSoortActieCode soortActieCode) {
            final SoortActie result;
            if (BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE.equals(soortActieCode)) {
                result = SoortActie.CONVERSIE_GBA_MATERIELE_HISTORIE;
            } else if (BrpSoortActieCode.CONVERSIE_GBA_LEEG_CATEGORIE_ONJUIST.equals(soortActieCode)) {
                result = SoortActie.CONVERSIE_GBA_LEGE_ONJUISTE_CATEGORIE;
            } else {
                result = SoortActie.CONVERSIE_GBA;
            }

            return result;
        }

        private Lo3Voorkomen mapLo3Herkomst(final Lo3Bericht bericht, final Lo3Herkomst herkomst) {
            if (herkomst == null || bericht == null) {
                return null;
            }
            final Lo3Voorkomen lo3Voorkomen = bericht.addVoorkomen(herkomst.getCategorie().toString(), herkomst.getStapel(), herkomst.getVoorkomen(), null);
            lo3Voorkomen.setConversieSortering(herkomst.getConversieSortering());
            return lo3Voorkomen;
        }

        private void mapDocumentenVoorActie(final BrpActie migratieActie, final BRPActie result) {
            if (migratieActie.getActieBronnen() == null) {
                return;
            }
            for (final BrpActieBron brpActieBron : migratieActie.getActieBronnen()) {
                final ActieBron actieBron = new ActieBron(result);

                if (brpActieBron.getDocumentStapel() != null) {
                    final DocumentMapper mapper = new DocumentMapper(dynamischeStamtabelRepository, onderzoekMapper);
                    final BrpDocumentInhoud inhoud = brpActieBron.getDocumentStapel().getLaatsteElement().getInhoud();
                    final Partij partij = mapper.getStamtabelMapping().findPartijByCode(inhoud.getPartijCode());
                    final SoortDocument soortDocument = mapper.getStamtabelMapping().findSoortDocumentByCode(inhoud.getSoortDocumentCode());
                    final Document document = new Document(soortDocument, partij);
                    mapper.mapVanMigratie(brpActieBron.getDocumentStapel(), document, null);
                    actieBron.setDocument(document);
                }

                actieBron.setRechtsgrondOmschrijving(BrpString.unwrap(brpActieBron.getRechtsgrondOmschrijving()));
                onderzoekMapper.mapOnderzoek(actieBron, brpActieBron.getRechtsgrondOmschrijving(), Element.ACTIEBRON_RECHTSGRONDOMSCHRIJVING);
                result.getActieBronSet().add(actieBron);
            }
        }
    }
}
