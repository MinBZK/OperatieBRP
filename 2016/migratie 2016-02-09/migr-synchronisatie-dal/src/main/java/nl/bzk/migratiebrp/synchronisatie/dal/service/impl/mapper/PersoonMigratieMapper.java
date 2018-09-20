/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonMigratieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortMigratie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapper waarmee een {@link nl.bzk.migratiebrp.conversie.model.brp.BrpStapel <BrpMigratieInhoud>} gemapt kan worden op
 * een verzameling van {@link nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonMigratieHistorie} en
 * vice versa.
 */
public final class PersoonMigratieMapper extends AbstractPersoonHistorieMapperStrategie<BrpMigratieInhoud, PersoonMigratieHistorie> {

    /**
     * Maakt een PersoonMigratieMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper
     *            de mapper voor onderzoeken
     */
    public PersoonMigratieMapper(
        final DynamischeStamtabelRepository dynamischeStamtabelRepository,
        final BRPActieFactory brpActieFactory,
        final OnderzoekMapper onderzoekMapper)
    {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonMigratieHistorie historie, final Persoon persoon) {
        persoon.addPersoonMigratieHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(final PersoonMigratieHistorie historie, final Persoon persoon) {
        persoon.setSoortMigratie(historie.getSoortMigratie());
        persoon.setRedenWijzigingMigratie(historie.getRedenWijzigingMigratie());
        persoon.setAangeverMigratie(historie.getAangeverMigratie());
        persoon.setLandOfGebiedMigratie(historie.getLandOfGebied());
        persoon.setBuitenlandsAdresRegel1Migratie(historie.getBuitenlandsAdresRegel1());
        persoon.setBuitenlandsAdresRegel2Migratie(historie.getBuitenlandsAdresRegel2());
        persoon.setBuitenlandsAdresRegel3Migratie(historie.getBuitenlandsAdresRegel3());
        persoon.setBuitenlandsAdresRegel4Migratie(historie.getBuitenlandsAdresRegel4());
        persoon.setBuitenlandsAdresRegel5Migratie(historie.getBuitenlandsAdresRegel5());
        persoon.setBuitenlandsAdresRegel6Migratie(historie.getBuitenlandsAdresRegel6());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonMigratieHistorie mapHistorischeGroep(final BrpMigratieInhoud groepInhoud, final Persoon persoon) {
        final PersoonMigratieHistorie result =
                new PersoonMigratieHistorie(persoon, SoortMigratie.parseCode(groepInhoud.getSoortMigratieCode().getWaarde()));
        result.setRedenWijzigingMigratie(getStamtabelMapping().findRedenWijzigingVerblijfByCode(groepInhoud.getRedenWijzigingMigratieCode()));
        result.setAangeverMigratie(getStamtabelMapping().findAangeverByCode(groepInhoud.getAangeverMigratieCode()));
        result.setLandOfGebied(getStamtabelMapping().findLandOfGebiedByCode(groepInhoud.getLandOfGebiedCode()));
        result.setBuitenlandsAdresRegel1(BrpString.unwrap(groepInhoud.getBuitenlandsAdresRegel1()));
        result.setBuitenlandsAdresRegel2(BrpString.unwrap(groepInhoud.getBuitenlandsAdresRegel2()));
        result.setBuitenlandsAdresRegel3(BrpString.unwrap(groepInhoud.getBuitenlandsAdresRegel3()));
        result.setBuitenlandsAdresRegel4(BrpString.unwrap(groepInhoud.getBuitenlandsAdresRegel4()));
        result.setBuitenlandsAdresRegel5(BrpString.unwrap(groepInhoud.getBuitenlandsAdresRegel5()));
        result.setBuitenlandsAdresRegel6(BrpString.unwrap(groepInhoud.getBuitenlandsAdresRegel6()));

        // onderzoek
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getSoortMigratieCode(), Element.PERSOON_MIGRATIE_SOORTCODE);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getRedenWijzigingMigratieCode(), Element.PERSOON_MIGRATIE_REDENWIJZIGINGCODE);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getAangeverMigratieCode(), Element.PERSOON_MIGRATIE_AANGEVERCODE);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getLandOfGebiedCode(), Element.PERSOON_MIGRATIE_LANDGEBIEDCODE);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getBuitenlandsAdresRegel1(), Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL1);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getBuitenlandsAdresRegel2(), Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL2);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getBuitenlandsAdresRegel3(), Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL3);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getBuitenlandsAdresRegel4(), Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL4);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getBuitenlandsAdresRegel5(), Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL5);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getBuitenlandsAdresRegel6(), Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL6);

        return result;
    }

}
