/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.decorator;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;

/**
 * Onderzoek: standaard.
 */
public final class OnderzoekStandaardRecord extends RecordDecorator {

    private final AttribuutElement datumAanvangElement = ElementHelper.getAttribuutElement(Element.ONDERZOEK_DATUMAANVANG.getId());
    private final AttribuutElement datumEindeElement = ElementHelper.getAttribuutElement(Element.ONDERZOEK_DATUMEINDE.getId());
    private final AttribuutElement omschrijvingElement = ElementHelper.getAttribuutElement(Element.ONDERZOEK_OMSCHRIJVING.getId());
    private final AttribuutElement statusElement = ElementHelper.getAttribuutElement(Element.ONDERZOEK_STATUSNAAM.getId());

    /**
     * Constructor.
     * @param metaRecord record
     */
    OnderzoekStandaardRecord(final MetaRecord metaRecord) {
        super(metaRecord);
    }

    /**
     * Geef de datum aanvang.
     * @return datum aanvang
     */
    public Integer getDatumAanvang() {
        return getAttribuut(datumAanvangElement);
    }

    /**
     * Geef de datum einde.
     * @return datum einde
     */
    public Integer getDatumEinde() {
        return getAttribuut(datumEindeElement);
    }

    /**
     * Geef de omschrijving.
     * @return omschrijving
     */
    public String getOmschrijving() {
        return getAttribuut(omschrijvingElement);
    }

    /**
     * Geef de status.
     * @return status
     */
    public String getStatus() {
        return getAttribuut(statusElement);
    }

}
