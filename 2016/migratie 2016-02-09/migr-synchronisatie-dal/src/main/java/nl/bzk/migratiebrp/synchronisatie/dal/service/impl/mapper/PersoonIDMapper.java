/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIDHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractNietIngeschrevenPersoonHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapper waarmee een {@link BrpStapel<BrpIdentificatienummersInhoud>} gemapt kan worden op een verzameling van
 * {@link PersoonIDHistorie} en vice versa.
 */
public final class PersoonIDMapper extends AbstractNietIngeschrevenPersoonHistorieMapperStrategie<BrpIdentificatienummersInhoud, PersoonIDHistorie> {

    /**
     * Maakt een PersoonIDMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper
     *            de mapper voor onderzoeken
     */
    public PersoonIDMapper(
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
    protected void voegHistorieToeAanEntiteit(final PersoonIDHistorie historie, final Persoon persoon) {
        persoon.addPersoonIDHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(final PersoonIDHistorie historie, final Persoon persoon) {
        persoon.setAdministratienummer(historie.getAdministratienummer());
        persoon.setBurgerservicenummer(historie.getBurgerservicenummer());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonIDHistorie mapHistorischeGroep(final BrpIdentificatienummersInhoud groepInhoud, final Persoon persoon) {
        final PersoonIDHistorie result = new PersoonIDHistorie(persoon);
        final Long aNummer = BrpLong.unwrap(groepInhoud.getAdministratienummer());
        if (aNummer != null) {
            result.setAdministratienummer(aNummer);
        }
        final Integer bsn = BrpInteger.unwrap(groepInhoud.getBurgerservicenummer());
        if (bsn != null) {
            result.setBurgerservicenummer(bsn);
        }

        // onderzoek
        mapOnderzoek(persoon, groepInhoud, result);

        return result;
    }

    @Override
    protected void mapOnderzoekPersoon(final BrpIdentificatienummersInhoud groepInhoud, final PersoonIDHistorie historie) {
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getAdministratienummer(), Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getBurgerservicenummer(), Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER);
    }

    @Override
    protected void mapOnderzoekKind(final BrpIdentificatienummersInhoud groepInhoud, final PersoonIDHistorie historie) {
        getOnderzoekMapper().mapOnderzoek(
            historie,
            groepInhoud.getAdministratienummer(),
            Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER);
        getOnderzoekMapper().mapOnderzoek(
            historie,
            groepInhoud.getBurgerservicenummer(),
            Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER);
    }

    @Override
    protected void mapOnderzoekOuder(final BrpIdentificatienummersInhoud groepInhoud, final PersoonIDHistorie historie) {
        getOnderzoekMapper().mapOnderzoek(
            historie,
            groepInhoud.getAdministratienummer(),
            Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER);
        getOnderzoekMapper().mapOnderzoek(
            historie,
            groepInhoud.getBurgerservicenummer(),
            Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER);
    }

    @Override
    protected void mapOnderzoekHuwelijkspartner(final BrpIdentificatienummersInhoud groepInhoud, final PersoonIDHistorie historie) {
        getOnderzoekMapper().mapOnderzoek(
            historie,
            groepInhoud.getAdministratienummer(),
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER);
        getOnderzoekMapper().mapOnderzoek(
            historie,
            groepInhoud.getBurgerservicenummer(),
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER);
    }

    @Override
    protected void mapOnderzoekGeregistreerdPartner(final BrpIdentificatienummersInhoud groepInhoud, final PersoonIDHistorie historie) {
        getOnderzoekMapper().mapOnderzoek(
            historie,
            groepInhoud.getAdministratienummer(),
            Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER);
        getOnderzoekMapper().mapOnderzoek(
            historie,
            groepInhoud.getBurgerservicenummer(),
            Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER);
    }
}
