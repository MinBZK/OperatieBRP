/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

import java.util.List;
import java.util.Map;

/**
 * De actie voor het registreren van een ontbinding van een Huwelijk.
 */
//
@XmlElement("registratieEindeHuwelijk")
public final class RegistratieEindeHuwelijkActieElement extends AbstractRegistratieEindeHuwelijkOfGpActieElement {

    private final HuwelijkElement huwelijk;

    /**
     * Maakt een RegistratieAanvangHuwelijkGPActieElement object.
     *
     * @param basisAttribuutGroep    de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties        bron referenties
     * @param huwelijk               geregistreerdPartnerschap
     */
    public RegistratieEindeHuwelijkActieElement(
            final Map<String, String> basisAttribuutGroep,
            final DatumElement datumAanvangGeldigheid,
            final DatumElement datumEindeGeldigheid,
            final List<BronReferentieElement> bronReferenties,
            final HuwelijkElement huwelijk) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties, huwelijk);
        this.huwelijk = huwelijk;
    }

    /**
     * Geef de waarde van geregistreerdPartnerschap.
     *
     * @return geregistreerdPartnerschap
     */
    public HuwelijkElement getHuwelijk() {
        return huwelijk;
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.REGISTRATIE_EINDE_HUWELIJK;
    }
}
