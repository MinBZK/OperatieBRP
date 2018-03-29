/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpIstGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Deze mapper mapped de BrpRelatieInhoud op Relatie en RelatieHistorie uit het BRP operationele model.
 */
public final class RelatieMapper extends AbstractHistorieMapperStrategie<BrpRelatieInhoud, RelatieHistorie, Relatie> {

    private static final String ONBEKENDE_OF_ONJUISTE_SOORT_RELATIE = "Onbekende of onjuiste soort relatie";
    private final DynamischeStamtabelRepository dynamischeStamtabelRepository;
    private final BRPActieFactory brpActieFactory;

    /**
     * Maakt een RelatieMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public RelatieMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        this.dynamischeStamtabelRepository = dynamischeStamtabelRepository;
        this.brpActieFactory = brpActieFactory;
    }

    /**
     * Mapped gegevens die niet in een stapel voorkomen en specifiek zijn voor een BrpRelatie.
     * @param migratieRelatie de BrpRelatie uit het migratie model
     * @param relatie de Relatie entiteit uit het operationele BRP gegevensmodel
     * @param persoon de ik-persoon waarmee de ik-betrokkenheid wordt aangemaakt
     * @param stapelsPerCategorie map van ist stapels per categorie
     */
    public void mapStapelEnOverigeRelatieGegevens(
            final BrpRelatie migratieRelatie,
            final Relatie relatie,
            final Persoon persoon,
            final Map<Lo3CategorieEnum, Map<Integer, Stapel>> stapelsPerCategorie) {
        // ik-betrokkenheid is impliciet verwerkt in BrpRelatie
        final Betrokkenheid ikBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.parseCode(migratieRelatie.getRolCode().getWaarde()), relatie);
        final BetrokkenheidIdentiteitMapper betrokkenheidIdentiteitMapper =
                new BetrokkenheidIdentiteitMapper(dynamischeStamtabelRepository, brpActieFactory, getOnderzoekMapper());
        betrokkenheidIdentiteitMapper.mapVanMigratie(migratieRelatie.getIkBetrokkenheid().getIdentiteitStapel(), ikBetrokkenheid, null);

        if (SoortBetrokkenheid.OUDER == ikBetrokkenheid.getSoortBetrokkenheid() && !migratieRelatie.getBetrokkenheden().isEmpty()) {
            final BetrokkenheidOuderMapper betrokkenheidOuderMapper =
                    new BetrokkenheidOuderMapper(dynamischeStamtabelRepository, brpActieFactory, getOnderzoekMapper());
            betrokkenheidOuderMapper.mapVanMigratie(migratieRelatie.getBetrokkenheden().get(0).getOuderStapel(), ikBetrokkenheid, null);
        }

        persoon.addBetrokkenheid(ikBetrokkenheid);

        relatie.addBetrokkenheid(ikBetrokkenheid);

        for (final BrpBetrokkenheid migratieBetrokkenheid : migratieRelatie.getBetrokkenheden()) {
            verwerkMigratieBetrokkenheid(relatie, migratieBetrokkenheid);
        }

        // valideer gemeentecode
        valideerGemeenteCodeVanGroepen(migratieRelatie.getSoortRelatieCode(), migratieRelatie.getRelatieStapel());
        valideerLandCodeVanGroepen(migratieRelatie.getSoortRelatieCode(), migratieRelatie.getRelatieStapel());

        // map historie
        if (migratieRelatie.getRelatieStapel() != null) {
            mapVanMigratie(migratieRelatie.getRelatieStapel(), relatie, null);
        }

        // Map IST
        mapIstGegevens(relatie, migratieRelatie, stapelsPerCategorie);

        // Map onderzoek op soort verbintenis
        mapOnderzoekOpSoortVerbintenis(relatie, migratieRelatie);
    }

    private void verwerkMigratieBetrokkenheid(final Relatie relatie, final BrpBetrokkenheid migratieBetrokkenheid) {
        final Element objecttype;
        final BrpSoortBetrokkenheidCode rol = migratieBetrokkenheid.getRol();
        if (BrpSoortBetrokkenheidCode.KIND.equals(rol)) {
            objecttype = Element.GERELATEERDEKIND_PERSOON;
        } else if (BrpSoortBetrokkenheidCode.OUDER.equals(rol)) {
            objecttype = Element.GERELATEERDEOUDER_PERSOON;
        } else if (BrpSoortBetrokkenheidCode.PARTNER.equals(rol)) {
            switch (relatie.getSoortRelatie()) {
                case GEREGISTREERD_PARTNERSCHAP:
                    objecttype = Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON;
                    break;
                case HUWELIJK:
                    objecttype = Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON;
                    break;
                default:
                    throw new IllegalStateException(String.format("Soort relatie is onbekend (%s)", relatie.getSoortRelatie()));
            }
        } else {
            throw new IllegalStateException(String.format("Onbekende rol van betrokkenheid (%s)", rol));
        }
        new BetrokkenheidMapper(dynamischeStamtabelRepository, brpActieFactory, getOnderzoekMapper()).mapVanMigratie(
                migratieBetrokkenheid,
                relatie,
                objecttype);
    }

    private void mapIstGegevens(
            final Relatie relatie,
            final BrpRelatie migratieRelatie,
            final Map<Lo3CategorieEnum, Map<Integer, Stapel>> stapelsPerCategorie) {
        final BrpStapel<BrpIstRelatieGroepInhoud> ouder1Stapel = migratieRelatie.getIstOuder1Stapel();
        final BrpStapel<BrpIstRelatieGroepInhoud> ouder2Stapel = migratieRelatie.getIstOuder2Stapel();
        final BrpStapel<BrpIstHuwelijkOfGpGroepInhoud> huwelijkOfGpStapel = migratieRelatie.getIstHuwelijkOfGpStapel();
        final BrpStapel<BrpIstRelatieGroepInhoud> kindStapel = migratieRelatie.getIstKindStapel();
        final BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> gezagsverhoudingStapel = migratieRelatie.getIstGezagsverhoudingStapel();

        if (ouder1Stapel != null) {
            mapIstStapel(ouder1Stapel, relatie, stapelsPerCategorie.get(Lo3CategorieEnum.CATEGORIE_02));
        }
        if (ouder2Stapel != null) {
            mapIstStapel(ouder2Stapel, relatie, stapelsPerCategorie.get(Lo3CategorieEnum.CATEGORIE_03));
        }
        if (huwelijkOfGpStapel != null) {
            mapIstStapel(huwelijkOfGpStapel, relatie, stapelsPerCategorie.get(Lo3CategorieEnum.CATEGORIE_05));
        }
        if (kindStapel != null) {
            mapIstStapel(kindStapel, relatie, stapelsPerCategorie.get(Lo3CategorieEnum.CATEGORIE_09));
        }
        if (gezagsverhoudingStapel != null) {
            mapIstStapel(gezagsverhoudingStapel, relatie, stapelsPerCategorie.get(Lo3CategorieEnum.CATEGORIE_11));
        }
    }

    private <T extends AbstractBrpIstGroepInhoud> void mapIstStapel(
            final BrpStapel<T> migratieStapel,
            final Relatie relatie,
            final Map<Integer, Stapel> istStapels) {
        final Integer migratieStapelNr = migratieStapel.get(0).getInhoud().getStapel();
        if (istStapels.containsKey(migratieStapelNr)) {
            relatie.addStapel(istStapels.get(migratieStapelNr));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final RelatieHistorie historie, final Relatie entiteit) {
        entiteit.addRelatieHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected RelatieHistorie mapHistorischeGroep(final BrpRelatieInhoud groepInhoud, final Relatie entiteit) {
        final RelatieHistorie historie;
        historie = new RelatieHistorie(entiteit);
        historie.setDatumAanvang(MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumAanvang()));
        historie.setLandOfGebiedAanvang(getStamtabelMapping().findLandOfGebiedByCode(groepInhoud.getLandOfGebiedCodeAanvang()));
        historie.setBuitenlandsePlaatsAanvang(BrpString.unwrap(groepInhoud.getBuitenlandsePlaatsAanvang()));
        historie.setBuitenlandsePlaatsEinde(BrpString.unwrap(groepInhoud.getBuitenlandsePlaatsEinde()));
        historie.setBuitenlandseRegioAanvang(BrpString.unwrap(groepInhoud.getBuitenlandseRegioAanvang()));
        historie.setBuitenlandseRegioEinde(BrpString.unwrap(groepInhoud.getBuitenlandseRegioEinde()));
        historie.setDatumEinde(MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumEinde()));

        historie.setGemeenteAanvang(getStamtabelMapping().findGemeenteByCode(groepInhoud.getGemeenteCodeAanvang()));
        historie.setGemeenteEinde(getStamtabelMapping().findGemeenteByCode(groepInhoud.getGemeenteCodeEinde()));
        historie.setLandOfGebiedEinde(getStamtabelMapping().findLandOfGebiedByCode(groepInhoud.getLandOfGebiedCodeEinde()));
        historie.setOmschrijvingLocatieAanvang(BrpString.unwrap(groepInhoud.getOmschrijvingLocatieAanvang()));
        historie.setOmschrijvingLocatieEinde(BrpString.unwrap(groepInhoud.getOmschrijvingLocatieEinde()));
        historie.setRedenBeeindigingRelatie(getStamtabelMapping().findRedenBeeindigingRelatieByCode(groepInhoud.getRedenEindeRelatieCode()));
        historie.setWoonplaatsnaamAanvang(BrpString.unwrap(groepInhoud.getWoonplaatsnaamAanvang()));
        historie.setWoonplaatsnaamEinde(BrpString.unwrap(groepInhoud.getWoonplaatsnaamEinde()));

        mapOnderzoek(groepInhoud, historie, entiteit);

        return historie;
    }

    private void mapOnderzoek(final BrpRelatieInhoud groepInhoud, final RelatieHistorie historie, final Relatie relatie) {
        final SoortRelatie soortRelatie = relatie.getSoortRelatie();
        switch (soortRelatie) {
            case GEREGISTREERD_PARTNERSCHAP:
                mapOnderzoekGeregistreerdPartnerschap(groepInhoud, historie);
                break;
            case HUWELIJK:
                mapOnderzoekHuwelijk(groepInhoud, historie);
                break;
            case FAMILIERECHTELIJKE_BETREKKING:
                mapOnderzoekFamilierechtelijkeBetrekking(groepInhoud, historie);
                break;
            default:
                throw new IllegalStateException(ONBEKENDE_OF_ONJUISTE_SOORT_RELATIE);
        }
    }

    private void mapOnderzoekGeregistreerdPartnerschap(final BrpRelatieInhoud groepInhoud, final RelatieHistorie historie) {
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getDatumAanvang(), Element.GEREGISTREERDPARTNERSCHAP_DATUMAANVANG);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getDatumEinde(), Element.GEREGISTREERDPARTNERSCHAP_DATUMEINDE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getBuitenlandsePlaatsAanvang(), Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSAANVANG);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getBuitenlandsePlaatsEinde(), Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSEINDE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getBuitenlandseRegioAanvang(), Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOAANVANG);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getBuitenlandseRegioEinde(), Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOEINDE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getGemeenteCodeAanvang(), Element.GEREGISTREERDPARTNERSCHAP_GEMEENTEAANVANGCODE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getGemeenteCodeEinde(), Element.GEREGISTREERDPARTNERSCHAP_GEMEENTEEINDECODE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getLandOfGebiedCodeAanvang(), Element.GEREGISTREERDPARTNERSCHAP_LANDGEBIEDAANVANGCODE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getLandOfGebiedCodeEinde(), Element.GEREGISTREERDPARTNERSCHAP_LANDGEBIEDEINDECODE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getOmschrijvingLocatieAanvang(), Element.GEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEAANVANG);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getOmschrijvingLocatieEinde(), Element.GEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEEINDE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getWoonplaatsnaamAanvang(), Element.GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMAANVANG);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getWoonplaatsnaamEinde(), Element.GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMEINDE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getRedenEindeRelatieCode(), Element.GEREGISTREERDPARTNERSCHAP_REDENEINDECODE);
    }

    private void mapOnderzoekHuwelijk(final BrpRelatieInhoud groepInhoud, final RelatieHistorie historie) {
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getDatumAanvang(), Element.HUWELIJK_DATUMAANVANG);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getDatumEinde(), Element.HUWELIJK_DATUMEINDE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getBuitenlandsePlaatsAanvang(), Element.HUWELIJK_BUITENLANDSEPLAATSAANVANG);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getBuitenlandsePlaatsEinde(), Element.HUWELIJK_BUITENLANDSEPLAATSEINDE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getBuitenlandseRegioAanvang(), Element.HUWELIJK_BUITENLANDSEREGIOAANVANG);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getBuitenlandseRegioEinde(), Element.HUWELIJK_BUITENLANDSEREGIOEINDE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getGemeenteCodeAanvang(), Element.HUWELIJK_GEMEENTEAANVANGCODE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getGemeenteCodeEinde(), Element.HUWELIJK_GEMEENTEEINDECODE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getLandOfGebiedCodeAanvang(), Element.HUWELIJK_LANDGEBIEDAANVANGCODE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getLandOfGebiedCodeEinde(), Element.HUWELIJK_LANDGEBIEDEINDECODE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getOmschrijvingLocatieAanvang(), Element.HUWELIJK_OMSCHRIJVINGLOCATIEAANVANG);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getOmschrijvingLocatieEinde(), Element.HUWELIJK_OMSCHRIJVINGLOCATIEEINDE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getWoonplaatsnaamAanvang(), Element.HUWELIJK_WOONPLAATSNAAMAANVANG);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getWoonplaatsnaamEinde(), Element.HUWELIJK_WOONPLAATSNAAMEINDE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getRedenEindeRelatieCode(), Element.HUWELIJK_REDENEINDECODE);
    }

    private void mapOnderzoekFamilierechtelijkeBetrekking(final BrpRelatieInhoud groepInhoud, final RelatieHistorie historie) {
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getDatumAanvang(), Element.FAMILIERECHTELIJKEBETREKKING_DATUMAANVANG);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getDatumEinde(), Element.FAMILIERECHTELIJKEBETREKKING_DATUMEINDE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getBuitenlandsePlaatsAanvang(), Element.FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEPLAATSAANVANG);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getBuitenlandsePlaatsEinde(), Element.FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEPLAATSEINDE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getBuitenlandseRegioAanvang(), Element.FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEREGIOAANVANG);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getBuitenlandseRegioEinde(), Element.FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEREGIOEINDE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getGemeenteCodeAanvang(), Element.FAMILIERECHTELIJKEBETREKKING_GEMEENTEAANVANGCODE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getGemeenteCodeEinde(), Element.FAMILIERECHTELIJKEBETREKKING_GEMEENTEEINDECODE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getLandOfGebiedCodeAanvang(), Element.FAMILIERECHTELIJKEBETREKKING_LANDGEBIEDAANVANGCODE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getLandOfGebiedCodeEinde(), Element.FAMILIERECHTELIJKEBETREKKING_LANDGEBIEDEINDECODE);
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getOmschrijvingLocatieAanvang(),
                Element.FAMILIERECHTELIJKEBETREKKING_OMSCHRIJVINGLOCATIEAANVANG);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getOmschrijvingLocatieEinde(), Element.FAMILIERECHTELIJKEBETREKKING_OMSCHRIJVINGLOCATIEEINDE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getWoonplaatsnaamAanvang(), Element.FAMILIERECHTELIJKEBETREKKING_WOONPLAATSNAAMAANVANG);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getWoonplaatsnaamEinde(), Element.FAMILIERECHTELIJKEBETREKKING_WOONPLAATSNAAMEINDE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getRedenEindeRelatieCode(), Element.FAMILIERECHTELIJKEBETREKKING_REDENEINDECODE);
    }

    private void mapOnderzoekOpSoortVerbintenis(final Relatie relatie, final BrpRelatie migratieRelatie) {
        final Lo3Onderzoek onderzoek = migratieRelatie.getSoortRelatieCode().getOnderzoek();
        if (onderzoek != null) {
            final Lo3Herkomst lo3Herkomst = onderzoek.getLo3Herkomst();
            RelatieHistorie historieVoorOnderzoek = null;
            for (final RelatieHistorie relatieHistorie : relatie.getRelatieHistorieSet()) {
                final Lo3Voorkomen historieLo3Herkomst = relatieHistorie.getActieInhoud().getLo3Voorkomen();
                if (lo3Herkomst.getCategorie().getCategorie().equals(historieLo3Herkomst.getCategorie())
                        && lo3Herkomst.getStapel() == historieLo3Herkomst.getStapelvolgnummer()
                        && lo3Herkomst.getVoorkomen() == historieLo3Herkomst.getVoorkomenvolgnummer()) {
                    historieVoorOnderzoek = relatieHistorie;
                }
            }

            if (SoortRelatie.FAMILIERECHTELIJKE_BETREKKING.equals(relatie.getSoortRelatie())) {
                getOnderzoekMapper().mapOnderzoek(relatie, migratieRelatie.getSoortRelatieCode(), Element.FAMILIERECHTELIJKEBETREKKING_SOORTCODE);
            } else if (SoortRelatie.HUWELIJK.equals(relatie.getSoortRelatie())) {
                getOnderzoekMapper().mapOnderzoek(historieVoorOnderzoek, migratieRelatie.getSoortRelatieCode(), Element.HUWELIJK_STANDAARD);
                getOnderzoekMapper().mapOnderzoek(relatie, migratieRelatie.getSoortRelatieCode(), Element.HUWELIJK_SOORTCODE);
            } else if (SoortRelatie.GEREGISTREERD_PARTNERSCHAP.equals(relatie.getSoortRelatie())) {
                getOnderzoekMapper().mapOnderzoek(historieVoorOnderzoek, migratieRelatie.getSoortRelatieCode(), Element.GEREGISTREERDPARTNERSCHAP_STANDAARD);
                getOnderzoekMapper().mapOnderzoek(relatie, migratieRelatie.getSoortRelatieCode(), Element.GEREGISTREERDPARTNERSCHAP_SOORTCODE);
            } else {
                throw new IllegalStateException(ONBEKENDE_OF_ONJUISTE_SOORT_RELATIE);
            }
        }
    }

    private void valideerGemeenteCodeVanGroepen(final BrpSoortRelatieCode brpSoortRelatieCode, final BrpStapel<BrpRelatieInhoud> relatieStapel) {
        if (relatieStapel == null) {
            return;
        }
        for (final BrpGroep<BrpRelatieInhoud> groep : relatieStapel.getGroepen()) {
            if (BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP.equals(brpSoortRelatieCode) || BrpSoortRelatieCode.HUWELIJK.equals(brpSoortRelatieCode)) {
                getStamtabelMapping().findGemeenteByCode(groep.getInhoud().getGemeenteCodeAanvang(), SoortMeldingCode.PRE027);
                getStamtabelMapping().findGemeenteByCode(groep.getInhoud().getGemeenteCodeEinde(), SoortMeldingCode.PRE029);
            } else {
                getStamtabelMapping().findGemeenteByCode(groep.getInhoud().getGemeenteCodeAanvang(), SoortMeldingCode.PRE002);
                getStamtabelMapping().findGemeenteByCode(groep.getInhoud().getGemeenteCodeEinde(), SoortMeldingCode.PRE002);
            }

        }

    }

    private void valideerLandCodeVanGroepen(final BrpSoortRelatieCode brpSoortRelatieCode, final BrpStapel<BrpRelatieInhoud> relatieStapel) {
        if (relatieStapel == null) {
            return;
        }
        for (final BrpGroep<BrpRelatieInhoud> groep : relatieStapel.getGroepen()) {
            if (BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP.equals(brpSoortRelatieCode) || BrpSoortRelatieCode.HUWELIJK.equals(brpSoortRelatieCode)) {
                getStamtabelMapping().findLandOfGebiedByCode(groep.getInhoud().getLandOfGebiedCodeAanvang(), SoortMeldingCode.PRE024);
                getStamtabelMapping().findLandOfGebiedByCode(groep.getInhoud().getLandOfGebiedCodeEinde(), SoortMeldingCode.PRE028);
            } else {
                getStamtabelMapping().findLandOfGebiedByCode(groep.getInhoud().getLandOfGebiedCodeAanvang(), SoortMeldingCode.PRE001);
                getStamtabelMapping().findLandOfGebiedByCode(groep.getInhoud().getLandOfGebiedCodeEinde(), SoortMeldingCode.PRE001);
            }

        }

    }
}
