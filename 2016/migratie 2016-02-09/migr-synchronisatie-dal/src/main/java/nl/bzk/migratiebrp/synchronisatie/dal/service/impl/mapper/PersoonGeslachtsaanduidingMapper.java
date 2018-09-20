/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractNietIngeschrevenPersoonHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapper waarmee een {@link nl.bzk.migratiebrp.conversie.model.brp.BrpStapel <BrpGeslachtsaanduidingInhoud>} gemapt kan
 * worden op een verzameling van {@link PersoonGeslachtsaanduidingHistorie} en vice versa.
 */
public final class PersoonGeslachtsaanduidingMapper extends
        AbstractNietIngeschrevenPersoonHistorieMapperStrategie<BrpGeslachtsaanduidingInhoud, PersoonGeslachtsaanduidingHistorie>
{

    /**
     * Maakt een PersoonGeslachtsaanduidingMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper
     *            de mapper voor onderzoeken
     */
    public PersoonGeslachtsaanduidingMapper(
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
    protected void voegHistorieToeAanEntiteit(final PersoonGeslachtsaanduidingHistorie historie, final Persoon persoon) {
        persoon.addPersoonGeslachtsaanduidingHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(final PersoonGeslachtsaanduidingHistorie historie, final Persoon persoon) {
        persoon.setGeslachtsaanduiding(historie.getGeslachtsaanduiding());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonGeslachtsaanduidingHistorie mapHistorischeGroep(final BrpGeslachtsaanduidingInhoud groepInhoud, final Persoon persoon) {
        final PersoonGeslachtsaanduidingHistorie result;
        result = new PersoonGeslachtsaanduidingHistorie(persoon, MapperUtil.mapBrpGeslachtsaanduidingCode(groepInhoud.getGeslachtsaanduidingCode()));

        // onderzoek
        mapOnderzoek(persoon, groepInhoud, result);

        return result;
    }

    @Override
    protected void mapOnderzoekPersoon(final BrpGeslachtsaanduidingInhoud groepInhoud, final PersoonGeslachtsaanduidingHistorie historie) {
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getGeslachtsaanduidingCode(), Element.PERSOON_GESLACHTSAANDUIDING_CODE);
    }

    @Override
    protected void mapOnderzoekKind(final BrpGeslachtsaanduidingInhoud groepInhoud, final PersoonGeslachtsaanduidingHistorie historie) {
        // Bij een niet-ingeschreven kind komst geslachtsaanduiding niet voor als groep
    }

    @Override
    protected void mapOnderzoekOuder(final BrpGeslachtsaanduidingInhoud groepInhoud, final PersoonGeslachtsaanduidingHistorie historie) {
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getGeslachtsaanduidingCode(), Element.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_CODE);
    }

    @Override
    protected void mapOnderzoekHuwelijkspartner(final BrpGeslachtsaanduidingInhoud groepInhoud, final PersoonGeslachtsaanduidingHistorie historie) {
        getOnderzoekMapper().mapOnderzoek(
            historie,
            groepInhoud.getGeslachtsaanduidingCode(),
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_CODE);
    }

    @Override
    protected void mapOnderzoekGeregistreerdPartner(final BrpGeslachtsaanduidingInhoud groepInhoud, final PersoonGeslachtsaanduidingHistorie historie) {
        getOnderzoekMapper().mapOnderzoek(
            historie,
            groepInhoud.getGeslachtsaanduidingCode(),
            Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_CODE);
    }
}
