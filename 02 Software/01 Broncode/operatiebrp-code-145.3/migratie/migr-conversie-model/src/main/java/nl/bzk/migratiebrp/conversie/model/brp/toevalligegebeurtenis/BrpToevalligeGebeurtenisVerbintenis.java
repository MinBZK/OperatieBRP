/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * Verbintenis.
 */
public final class BrpToevalligeGebeurtenisVerbintenis {

    private final BrpToevalligeGebeurtenisPersoon partner;
    private final BrpToevalligeGebeurtenisVerbintenisSluiting sluiting;

    // Choice: min 0, max 1
    private final BrpToevalligeGebeurtenisVerbintenisOntbinding ontbinding;
    private final BrpToevalligeGebeurtenisVerbintenisSluiting omzetting;

    /**
     * Constructor.
     * @param partner partner
     * @param sluiting sluiting
     * @param ontbinding ontbinding
     * @param omzetting omzetting
     */
    public BrpToevalligeGebeurtenisVerbintenis(
            final BrpToevalligeGebeurtenisPersoon partner,
            final BrpToevalligeGebeurtenisVerbintenisSluiting sluiting,
            final BrpToevalligeGebeurtenisVerbintenisOntbinding ontbinding,
            final BrpToevalligeGebeurtenisVerbintenisSluiting omzetting) {
        super();
        this.partner = partner;
        this.sluiting = sluiting;
        this.ontbinding = ontbinding;
        this.omzetting = omzetting;

        ValidationUtils.checkNietNull("Partner mag niet null zijn", partner);
        ValidationUtils.checkNietNull("Sluiting mag niet null zijn", sluiting);
        ValidationUtils.checkMaximaalEen("Ontbinding en omzetting mogen niet beide gevuld zijn", ontbinding, omzetting);
    }

    /**
     * Geef de waarde van partner van BrpToevalligeGebeurtenisVerbintenis.
     * @return de waarde van partner van BrpToevalligeGebeurtenisVerbintenis
     */
    public BrpToevalligeGebeurtenisPersoon getPartner() {
        return partner;
    }

    /**
     * Geef de waarde van sluiting van BrpToevalligeGebeurtenisVerbintenis.
     * @return de waarde van sluiting van BrpToevalligeGebeurtenisVerbintenis
     */
    public BrpToevalligeGebeurtenisVerbintenisSluiting getSluiting() {
        return sluiting;
    }

    /**
     * Geef de waarde van ontbinding van BrpToevalligeGebeurtenisVerbintenis.
     * @return de waarde van ontbinding van BrpToevalligeGebeurtenisVerbintenis
     */
    public BrpToevalligeGebeurtenisVerbintenisOntbinding getOntbinding() {
        return ontbinding;
    }

    /**
     * Geef de waarde van omzetting van BrpToevalligeGebeurtenisVerbintenis.
     * @return de waarde van omzetting van BrpToevalligeGebeurtenisVerbintenis
     */
    public BrpToevalligeGebeurtenisVerbintenisSluiting getOmzetting() {
        return omzetting;
    }

}
