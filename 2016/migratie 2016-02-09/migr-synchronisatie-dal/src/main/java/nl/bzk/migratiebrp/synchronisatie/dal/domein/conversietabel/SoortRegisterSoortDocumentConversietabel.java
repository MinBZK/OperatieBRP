/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortDocument;

/**
 * Convesietabel voor soortdocument.
 * 
 */
public final class SoortRegisterSoortDocumentConversietabel implements Conversietabel<Character, BrpSoortDocumentCode> {

    /**
     * Constante waarde voor het gebruik van soort document voor conversie.
     */
    public static final String CONVERSIE_SOORT_DOCUMENT = BrpSoortDocumentCode.HISTORIE_CONVERSIE.getWaarde();
    private final BrpSoortDocumentCode soortDocumentCode;

    /**
     * Maakt een SoortRegisterSoortDocumentConversietabel object.
     *
     * @param soortDocument
     *            het soort document dat gebruikt moet worden voor conversie
     */
    public SoortRegisterSoortDocumentConversietabel(final SoortDocument soortDocument) {
        this.soortDocumentCode = new BrpSoortDocumentCode(soortDocument.getNaam());
    }

    @Override
    public BrpSoortDocumentCode converteerNaarBrp(final Character input) {
        return soortDocumentCode;
    }

    @Override
    public boolean valideerLo3(final Character input) {
        return true;
    }

    @Override
    public Character converteerNaarLo3(final BrpSoortDocumentCode input) {
        return null;
    }

    @Override
    public boolean valideerBrp(final BrpSoortDocumentCode input) {
        return true;
    }
}
