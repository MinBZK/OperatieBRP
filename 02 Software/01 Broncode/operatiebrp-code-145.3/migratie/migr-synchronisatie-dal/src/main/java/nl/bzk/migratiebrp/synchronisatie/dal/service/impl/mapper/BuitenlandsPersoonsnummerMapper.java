/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBuitenlandsPersoonsnummer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBuitenlandsPersoonsnummerHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBuitenlandsPersoonsnummerInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapped {@link nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBuitenlandsPersoonsnummerInhoud} op de
 * {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBuitenlandsPersoonsnummer} entity inclusief historie.
 */
public final class BuitenlandsPersoonsnummerMapper extends
        AbstractHistorieMapperStrategie<BrpBuitenlandsPersoonsnummerInhoud, PersoonBuitenlandsPersoonsnummerHistorie, PersoonBuitenlandsPersoonsnummer> {

    /**
     * Maakt een {@link BuitenlandsPersoonsnummerMapper} object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    BuitenlandsPersoonsnummerMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonBuitenlandsPersoonsnummerHistorie historie, final PersoonBuitenlandsPersoonsnummer entiteit) {
        entiteit.addPersoonBuitenlandsPersoonsnummerHistorie(historie);
    }

    @Override
    protected PersoonBuitenlandsPersoonsnummerHistorie mapHistorischeGroep(
            final BrpBuitenlandsPersoonsnummerInhoud groepInhoud,
            final PersoonBuitenlandsPersoonsnummer entiteit) {
        final PersoonBuitenlandsPersoonsnummerHistorie result = new PersoonBuitenlandsPersoonsnummerHistorie(entiteit);
        final BrpString nummer = groepInhoud.getNummer();
        final BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer autoriteitVanAfgifte = groepInhoud.getAutoriteitVanAfgifte();

        if (nummer.getOnderzoek() != null) {
            getOnderzoekMapper().mapOnderzoek(result, nummer, Element.PERSOON_BUITENLANDSPERSOONSNUMMER_STANDAARD);
            getOnderzoekMapper().mapOnderzoek(entiteit, nummer, Element.PERSOON_BUITENLANDSPERSOONSNUMMER_NUMMER);
        }
        if (autoriteitVanAfgifte.getOnderzoek() != null) {
            getOnderzoekMapper().mapOnderzoek(result, autoriteitVanAfgifte, Element.PERSOON_BUITENLANDSPERSOONSNUMMER_STANDAARD);
            getOnderzoekMapper().mapOnderzoek(entiteit, autoriteitVanAfgifte, Element.PERSOON_BUITENLANDSPERSOONSNUMMER_AUTORITEITVANAFGIFTECODE);
        }

        return result;
    }
}
