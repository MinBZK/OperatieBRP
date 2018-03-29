/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapper waarmee een {@link nl.bzk.migratiebrp.conversie.model.brp.BrpStapel<BrpGeslachtsnaamcomponentInhoud>} gemapt
 * kan worden op een verzameling van {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent}
 * inclusief historie.
 */
public final class PersoonGeslachtsnaamcomponentMapper
        extends AbstractHistorieMapperStrategie<BrpGeslachtsnaamcomponentInhoud, PersoonGeslachtsnaamcomponentHistorie, PersoonGeslachtsnaamcomponent> {

    /**
     * Maakt een PersoonGeslachtsnaamcomponentMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public PersoonGeslachtsnaamcomponentMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonGeslachtsnaamcomponentHistorie historie, final PersoonGeslachtsnaamcomponent entiteit) {
        entiteit.addPersoonGeslachtsnaamcomponentHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonGeslachtsnaamcomponentHistorie mapHistorischeGroep(
            final BrpGeslachtsnaamcomponentInhoud groepInhoud,
            final PersoonGeslachtsnaamcomponent entiteit) {
        final PersoonGeslachtsnaamcomponentHistorie result = new PersoonGeslachtsnaamcomponentHistorie(entiteit, BrpString.unwrap(groepInhoud.getStam()));
        if (groepInhoud.getAdellijkeTitelCode() != null) {
            result.setAdellijkeTitel(AdellijkeTitel.parseCode(groepInhoud.getAdellijkeTitelCode().getWaarde()));
        }
        if (groepInhoud.getPredicaatCode() != null) {
            result.setPredicaat(Predicaat.parseCode(groepInhoud.getPredicaatCode().getWaarde()));
        }
        result.setScheidingsteken(BrpCharacter.unwrap(groepInhoud.getScheidingsteken()));
        result.setVoorvoegsel(BrpString.unwrap(groepInhoud.getVoorvoegsel()));

        // onderzoek
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getStam(), Element.PERSOON_GESLACHTSNAAMCOMPONENT_STAM);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getAdellijkeTitelCode(), Element.PERSOON_GESLACHTSNAAMCOMPONENT_ADELLIJKETITELCODE);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getPredicaatCode(), Element.PERSOON_GESLACHTSNAAMCOMPONENT_PREDICAATCODE);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getScheidingsteken(), Element.PERSOON_GESLACHTSNAAMCOMPONENT_SCHEIDINGSTEKEN);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getVoorvoegsel(), Element.PERSOON_GESLACHTSNAAMCOMPONENT_VOORVOEGSEL);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getVolgnummer(), Element.PERSOON_GESLACHTSNAAMCOMPONENT_VOLGNUMMER);

        return result;
    }
}
