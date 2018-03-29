/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNaamgebruikHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikGeslachtsnaamstam;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapper waarmee een {@link nl.bzk.migratiebrp.conversie.model.brp.BrpStapel <BrpNaamgebruikInhoud>} gemapt kan worden
 * op een verzameling van {@link PersoonNaamgebruikHistorie} en vice versa.
 */
public final class PersoonNaamgebruikMapper extends AbstractPersoonHistorieMapperStrategie<BrpNaamgebruikInhoud, PersoonNaamgebruikHistorie> {

    /**
     * Maakt een PersoonNaamgebruikMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public PersoonNaamgebruikMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonNaamgebruikHistorie historie, final Persoon persoon) {
        persoon.addPersoonNaamgebruikHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonNaamgebruikHistorie mapHistorischeGroep(final BrpNaamgebruikInhoud groepInhoud, final Persoon persoon) {
        final Naamgebruik naamgebruik = Naamgebruik.parseCode(groepInhoud.getNaamgebruikCode().getWaarde());

        Boolean afgeleid = BrpBoolean.unwrap(groepInhoud.getIndicatieAfgeleid());
        if (afgeleid == null) {
            afgeleid = false;
        }

        final PersoonNaamgebruikHistorie result =
                new PersoonNaamgebruikHistorie(persoon, BrpNaamgebruikGeslachtsnaamstam.unwrap(groepInhoud.getGeslachtsnaamstam()), afgeleid, naamgebruik);
        result.setVoornamenNaamgebruik(BrpString.unwrap(groepInhoud.getVoornamen()));
        result.setScheidingstekenNaamgebruik(BrpCharacter.unwrap(groepInhoud.getScheidingsteken()));
        result.setVoorvoegselNaamgebruik(BrpString.unwrap(groepInhoud.getVoorvoegsel()));
        if (BrpValidatie.isAttribuutGevuld(groepInhoud.getPredicaatCode())) {
            result.setPredicaat(Predicaat.parseCode(groepInhoud.getPredicaatCode().getWaarde()));
        }
        if (BrpValidatie.isAttribuutGevuld(groepInhoud.getAdellijkeTitelCode())) {
            result.setAdellijkeTitel(AdellijkeTitel.valueOf(groepInhoud.getAdellijkeTitelCode().getWaarde()));
        }

        // onderzoek
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getAdellijkeTitelCode(), Element.PERSOON_NAAMGEBRUIK_ADELLIJKETITELCODE);
        getOnderzoekMapper().mapOnderzoeken(result, groepInhoud.getGeslachtsnaamstam(), Element.PERSOON_NAAMGEBRUIK_GESLACHTSNAAMSTAM);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getIndicatieAfgeleid(), Element.PERSOON_NAAMGEBRUIK_INDICATIEAFGELEID);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getNaamgebruikCode(), Element.PERSOON_NAAMGEBRUIK_CODE);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getPredicaatCode(), Element.PERSOON_NAAMGEBRUIK_PREDICAATCODE);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getVoornamen(), Element.PERSOON_NAAMGEBRUIK_VOORNAMEN);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getVoorvoegsel(), Element.PERSOON_NAAMGEBRUIK_VOORVOEGSEL);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getScheidingsteken(), Element.PERSOON_NAAMGEBRUIK_SCHEIDINGSTEKEN);

        return result;
    }
}
