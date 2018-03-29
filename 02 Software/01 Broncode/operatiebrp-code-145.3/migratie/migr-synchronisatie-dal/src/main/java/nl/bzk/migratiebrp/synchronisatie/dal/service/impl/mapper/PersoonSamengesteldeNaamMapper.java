/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractNietIngeschrevenPersoonHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapper waarmee een {@link nl.bzk.migratiebrp.conversie.model.brp.BrpStapel <BrpSamengesteldeNaamInhoud>} gemapt kan
 * worden op een verzameling van {@link PersoonSamengesteldeNaamHistorie} en vice versa.
 */
public final class PersoonSamengesteldeNaamMapper extends
        AbstractNietIngeschrevenPersoonHistorieMapperStrategie<BrpSamengesteldeNaamInhoud, PersoonSamengesteldeNaamHistorie> {

    /**
     * Maakt een PersoonSamengesteldeNaamMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public PersoonSamengesteldeNaamMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonSamengesteldeNaamHistorie historie, final Persoon persoon) {
        persoon.addPersoonSamengesteldeNaamHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonSamengesteldeNaamHistorie mapHistorischeGroep(final BrpSamengesteldeNaamInhoud groepInhoud, final Persoon persoon) {
        final PersoonSamengesteldeNaamHistorie result =
                new PersoonSamengesteldeNaamHistorie(
                        persoon,
                        BrpString.unwrap(groepInhoud.getGeslachtsnaamstam()),
                        BrpBoolean.unwrap(groepInhoud.getIndicatieAfgeleid()),
                        BrpBoolean.unwrap(groepInhoud.getIndicatieNamenreeks()));

        final BrpAdellijkeTitelCode adellijkeTitelCode = groepInhoud.getAdellijkeTitelCode();
        if (BrpValidatie.isAttribuutGevuld(adellijkeTitelCode)) {
            result.setAdellijkeTitel(AdellijkeTitel.valueOf(adellijkeTitelCode.getWaarde()));
        }
        final BrpPredicaatCode predicaatCode = groepInhoud.getPredicaatCode();
        if (BrpValidatie.isAttribuutGevuld(predicaatCode)) {
            result.setPredicaat(Predicaat.valueOf(predicaatCode.getWaarde()));
        }
        result.setVoornamen(BrpString.unwrap(groepInhoud.getVoornamen()));
        result.setVoorvoegsel(BrpString.unwrap(groepInhoud.getVoorvoegsel()));
        result.setScheidingsteken(BrpCharacter.unwrap(groepInhoud.getScheidingsteken()));

        // onderzoek
        mapOnderzoek(persoon, groepInhoud, result);

        return result;
    }

    @Override
    protected void mapOnderzoekPersoon(final BrpSamengesteldeNaamInhoud groepInhoud, final PersoonSamengesteldeNaamHistorie historie) {
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getAdellijkeTitelCode(), Element.PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getGeslachtsnaamstam(), Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getIndicatieAfgeleid(), Element.PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getIndicatieNamenreeks(), Element.PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getPredicaatCode(), Element.PERSOON_SAMENGESTELDENAAM_PREDICAATCODE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getVoornamen(), Element.PERSOON_SAMENGESTELDENAAM_VOORNAMEN);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getVoorvoegsel(), Element.PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getScheidingsteken(), Element.PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN);
    }

    @Override
    protected void mapOnderzoekKind(final BrpSamengesteldeNaamInhoud groepInhoud, final PersoonSamengesteldeNaamHistorie historie) {
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getAdellijkeTitelCode(),
                Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE);
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getGeslachtsnaamstam(),
                Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM);
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getIndicatieAfgeleid(),
                Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID);
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getIndicatieNamenreeks(),
                Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getPredicaatCode(), Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getVoornamen(), Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_VOORNAMEN);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getVoorvoegsel(), Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getScheidingsteken(), Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN);
    }

    @Override
    protected void mapOnderzoekOuder(final BrpSamengesteldeNaamInhoud groepInhoud, final PersoonSamengesteldeNaamHistorie historie) {
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getAdellijkeTitelCode(),
                Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE);
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getGeslachtsnaamstam(),
                Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM);
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getIndicatieAfgeleid(),
                Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID);
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getIndicatieNamenreeks(),
                Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getPredicaatCode(), Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getVoornamen(), Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getVoorvoegsel(), Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getScheidingsteken(), Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN);
    }

    @Override
    protected void mapOnderzoekHuwelijkspartner(final BrpSamengesteldeNaamInhoud groepInhoud, final PersoonSamengesteldeNaamHistorie historie) {
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getAdellijkeTitelCode(),
                Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE);
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getGeslachtsnaamstam(),
                Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM);
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getIndicatieAfgeleid(),
                Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID);
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getIndicatieNamenreeks(),
                Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS);
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getPredicaatCode(),
                Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getVoornamen(), Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN);
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getVoorvoegsel(),
                Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL);
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getScheidingsteken(),
                Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN);
    }

    @Override
    protected void mapOnderzoekGeregistreerdPartner(final BrpSamengesteldeNaamInhoud groepInhoud, final PersoonSamengesteldeNaamHistorie historie) {
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getAdellijkeTitelCode(),
                Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE);
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getGeslachtsnaamstam(),
                Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM);
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getIndicatieAfgeleid(),
                Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID);
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getIndicatieNamenreeks(),
                Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS);
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getPredicaatCode(),
                Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE);
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getVoornamen(),
                Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN);
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getVoorvoegsel(),
                Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL);
        getOnderzoekMapper().mapOnderzoek(
                historie,
                groepInhoud.getScheidingsteken(),
                Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN);
    }
}
