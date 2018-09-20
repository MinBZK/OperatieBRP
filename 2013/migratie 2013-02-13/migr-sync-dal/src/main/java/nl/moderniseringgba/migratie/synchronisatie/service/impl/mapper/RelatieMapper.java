/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Betrokkenheid;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.HistorieStatus;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Relatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RelatieHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortBetrokkenheid;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortRelatie;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.MapperUtil;

/**
 * Deze mapper mapped de BrpRelatieInhoud op Relatie en RelatieHistorie uit het BRP operationele model.
 * 
 */
public final class RelatieMapper extends AbstractHistorieMapperStrategie<BrpRelatieInhoud, RelatieHistorie, Relatie> {

    private final DynamischeStamtabelRepository dynamischeStamtabelRepository;
    private final BRPActieFactory brpActieFactory;

    /**
     * Maakt een RelatieMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public RelatieMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
        this.dynamischeStamtabelRepository = dynamischeStamtabelRepository;
        this.brpActieFactory = brpActieFactory;
    }

    /**
     * Mapped gegevens die niet in een stapel voorkomen en specifiek zijn voor een BrpRelatie.
     * 
     * @param migratieRelatie
     *            de BrpRelatie uit het migratie model
     * @param relatie
     *            de Relatie entiteit uit het operationele BRP gegevensmodel
     * @param persoon
     *            de ik-persoon waarmee de ik-betrokkenheid wordt aangemaakt
     */
    public void mapStapelEnOverigeRelatieGegevens(
            final BrpRelatie migratieRelatie,
            final Relatie relatie,
            final Persoon persoon) {
        relatie.setSoortRelatie(SoortRelatie.parseCode(migratieRelatie.getSoortRelatieCode().getCode()));

        // ik-betrokkenheid is impliciet verwerkt in BrpRelatie
        final Betrokkenheid ikBetrokkenheid = new Betrokkenheid();
        ikBetrokkenheid.setOuderlijkGezagStatusHistorie(HistorieStatus.bepaalHistorieStatus(null));
        ikBetrokkenheid.setSoortBetrokkenheid(SoortBetrokkenheid.parseCode(migratieRelatie.getRolCode().getCode()));

        if (SoortBetrokkenheid.OUDER == ikBetrokkenheid.getSoortBetrokkenheid()) {
            new BetrokkenheidOuderMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                    migratieRelatie.getBetrokkenheden().get(0).getOuderStapel(), ikBetrokkenheid);
        } else {
            ikBetrokkenheid.setOuderStatusHistorie(HistorieStatus.bepaalHistorieStatus(null));
        }

        persoon.addBetrokkenheid(ikBetrokkenheid);

        relatie.addBetrokkenheid(ikBetrokkenheid);

        for (final BrpBetrokkenheid migratieBetrokkenheid : migratieRelatie.getBetrokkenheden()) {
            new BetrokkenheidMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                    migratieBetrokkenheid, relatie);
        }

        // valideer gemeentecode
        valideerGemeenteCodeVanGroepen(migratieRelatie.getSoortRelatieCode(), migratieRelatie.getRelatieStapel());
        valideerLandCodeVanGroepen(migratieRelatie.getSoortRelatieCode(), migratieRelatie.getRelatieStapel());

        // map historie
        if (migratieRelatie.getRelatieStapel() != null) {
            mapVanMigratie(migratieRelatie.getRelatieStapel(), relatie);
        } else {
            relatie.setRelatieStatusHistorie(HistorieStatus.bepaalHistorieStatus(null));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mapActueleGegevens(final BrpStapel<BrpRelatieInhoud> brpStapel, final Relatie entiteit) {
        entiteit.setRelatieStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpStapel));
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
    protected void kopieerActueleGroepNaarEntiteit(final RelatieHistorie historie, final Relatie entiteit) {
        entiteit.setBuitenlandsePlaatsAanvang(historie.getBuitenlandsePlaatsAanvang());
        entiteit.setBuitenlandsePlaatsEinde(historie.getBuitenlandsePlaatsEinde());
        entiteit.setBuitenlandseRegioAanvang(historie.getBuitenlandseRegioAanvang());
        entiteit.setBuitenlandseRegioEinde(historie.getBuitenlandseRegioEinde());
        entiteit.setDatumAanvang(historie.getDatumAanvang());
        entiteit.setDatumEinde(historie.getDatumEinde());
        entiteit.setGemeenteAanvang(historie.getGemeenteAanvang());
        entiteit.setGemeenteEinde(historie.getGemeenteEinde());
        entiteit.setLandAanvang(historie.getLandAanvang());
        entiteit.setLandEinde(historie.getLandEinde());
        entiteit.setOmschrijvingLocatieAanvang(historie.getOmschrijvingLocatieAanvang());
        entiteit.setOmschrijvingLocatieEinde(historie.getOmschrijvingLocatieEinde());
        entiteit.setRedenBeeindigingRelatie(historie.getRedenBeeindigingRelatie());
        entiteit.setWoonplaatsAanvang(historie.getWoonplaatsAanvang());
        entiteit.setWoonplaatsEinde(historie.getWoonplaatsEinde());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected RelatieHistorie mapHistorischeGroep(final BrpRelatieInhoud groepInhoud) {
        final RelatieHistorie result = new RelatieHistorie();
        result.setBuitenlandsePlaatsAanvang(groepInhoud.getBuitenlandsePlaatsAanvang());
        result.setBuitenlandsePlaatsEinde(groepInhoud.getBuitenlandsePlaatsEinde());
        result.setBuitenlandseRegioAanvang(groepInhoud.getBuitenlandseRegioAanvang());
        result.setBuitenlandseRegioEinde(groepInhoud.getBuitenlandseRegioEinde());
        result.setDatumAanvang(MapperUtil.mapBrpDatumToBigDecimal(groepInhoud.getDatumAanvang()));
        result.setDatumEinde(MapperUtil.mapBrpDatumToBigDecimal(groepInhoud.getDatumEinde()));

        result.setGemeenteAanvang(getStamtabelMapping()
                .findPartijByGemeentecode(groepInhoud.getGemeenteCodeAanvang()));
        result.setGemeenteEinde(getStamtabelMapping().findPartijByGemeentecode(groepInhoud.getGemeenteCodeEinde()));
        result.setLandAanvang(getStamtabelMapping().findLandByLandcode(groepInhoud.getLandCodeAanvang()));
        result.setLandEinde(getStamtabelMapping().findLandByLandcode(groepInhoud.getLandCodeEinde()));
        result.setOmschrijvingLocatieAanvang(groepInhoud.getOmschrijvingLocatieAanvang());
        result.setOmschrijvingLocatieEinde(groepInhoud.getOmschrijvingLocatieEinde());
        result.setRedenBeeindigingRelatie(getStamtabelMapping().findRedenBeeindigingRelatieByCode(
                groepInhoud.getRedenEinde()));
        result.setWoonplaatsAanvang(getStamtabelMapping().findPlaatsByCode(groepInhoud.getPlaatsCodeAanvang()));
        result.setWoonplaatsEinde(getStamtabelMapping().findPlaatsByCode(groepInhoud.getPlaatsCodeEinde()));
        return result;
    }

    private void valideerGemeenteCodeVanGroepen(
            final BrpSoortRelatieCode brpSoortRelatieCode,
            final BrpStapel<BrpRelatieInhoud> relatieStapel) {
        if (relatieStapel == null) {
            return;
        }
        for (final BrpGroep<BrpRelatieInhoud> groep : relatieStapel.getGroepen()) {
            switch (brpSoortRelatieCode) {
                case HUWELIJK:
                case GEREGISTREERD_PARTNERSCHAP:
                    getStamtabelMapping().findPartijByGemeentecode(groep.getInhoud().getGemeenteCodeAanvang(),
                            Precondities.PRE027);
                    getStamtabelMapping().findPartijByGemeentecode(groep.getInhoud().getGemeenteCodeEinde(),
                            Precondities.PRE029);
                    break;
                default:
                    getStamtabelMapping().findPartijByGemeentecode(groep.getInhoud().getGemeenteCodeAanvang(),
                            Precondities.PRE002);
                    getStamtabelMapping().findPartijByGemeentecode(groep.getInhoud().getGemeenteCodeEinde(),
                            Precondities.PRE002);
            }

        }

    }

    private void valideerLandCodeVanGroepen(
            final BrpSoortRelatieCode brpSoortRelatieCode,
            final BrpStapel<BrpRelatieInhoud> relatieStapel) {
        if (relatieStapel == null) {
            return;
        }
        for (final BrpGroep<BrpRelatieInhoud> groep : relatieStapel.getGroepen()) {
            switch (brpSoortRelatieCode) {
                case HUWELIJK:
                case GEREGISTREERD_PARTNERSCHAP:
                    getStamtabelMapping().findLandByLandcode(groep.getInhoud().getLandCodeAanvang(),
                            Precondities.PRE024);
                    getStamtabelMapping().findLandByLandcode(groep.getInhoud().getLandCodeEinde(),
                            Precondities.PRE028);
                    break;
                default:
                    getStamtabelMapping().findLandByLandcode(groep.getInhoud().getLandCodeAanvang(),
                            Precondities.PRE001);
                    getStamtabelMapping().findLandByLandcode(groep.getInhoud().getLandCodeEinde(),
                            Precondities.PRE001);
            }

        }

    }
}
