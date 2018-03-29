/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBuitenlandsPersoonsnummerInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de buitenlandspersoonsnummer gegevens.
 */
@Component
public final class BuitenlandsPersoonsnummerMapper extends AbstractMapper<BrpBuitenlandsPersoonsnummerInhoud> {

    /**
     * Identiteit groep element.
     */
    public static final GroepElement IDENTITEIT_GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_IDENTITEIT.getId());
    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_STANDAARD.getId());

    private static final AttribuutElement NUMMER =
            ElementHelper.getAttribuutElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_NUMMER.getId());
    /**
     * Autoriteit van afgifte code element.
     */
    public static final AttribuutElement AUTORITEITVANAFGIFTECODE =
            ElementHelper.getAttribuutElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_AUTORITEITVANAFGIFTECODE.getId());

    /**
     * Constructor.
     */
    public BuitenlandsPersoonsnummerMapper() {
        super(IDENTITEIT_GROEP_ELEMENT,
                GROEP_ELEMENT,
                null,
                null,
                ElementHelper.getAttribuutElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_TIJDSTIPVERVAL.getId()));
    }

    /**
     * Map inhoud.
     * @param identiteitRecord identiteit record
     * @param record BRP record
     * @param onderzoekMapper onderzoek mapper
     */
    @Override
    public BrpBuitenlandsPersoonsnummerInhoud mapInhoud(
            final MetaRecord identiteitRecord,
            final MetaRecord record,
            final OnderzoekMapper onderzoekMapper) {

        return new BrpBuitenlandsPersoonsnummerInhoud(
                BrpMetaAttribuutMapper.mapBrpString(
                        identiteitRecord.getAttribuut(NUMMER),
                        onderzoekMapper.bepaalOnderzoek(identiteitRecord.getVoorkomensleutel(), NUMMER, true)),
                BrpMetaAttribuutMapper.mapBrpBuitenlandsPersoonsnummerAutoriteitVanAfgifteCode(
                        identiteitRecord.getAttribuut(AUTORITEITVANAFGIFTECODE),
                        onderzoekMapper.bepaalOnderzoek(identiteitRecord.getVoorkomensleutel(), AUTORITEITVANAFGIFTECODE, true)));
    }
}
