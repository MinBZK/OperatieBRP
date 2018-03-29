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
 * De actie voor het registreren van een einde van een geregistreerd partnerschap.
 */
//
@XmlElement("registratieEindeGeregistreerdPartnerschap")
public final class RegistratieEindeGeregistreerdPartnerschapActieElement extends AbstractRegistratieEindeHuwelijkOfGpActieElement {

    private final GeregistreerdPartnerschapElement geregistreerdPartnerschap;

    /**
     * Maakt een RegistratieAanvangHuwelijkGPActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param geregistreerdPartnerschap geregistreerdPartnerschap
     */
    public RegistratieEindeGeregistreerdPartnerschapActieElement(
            final Map<String, String> basisAttribuutGroep,
            final DatumElement datumAanvangGeldigheid,
            final DatumElement datumEindeGeldigheid,
            final List<BronReferentieElement> bronReferenties,
            final GeregistreerdPartnerschapElement geregistreerdPartnerschap) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties, geregistreerdPartnerschap);
        this.geregistreerdPartnerschap = geregistreerdPartnerschap;
    }

    /**
     * Geef de waarde van geregistreerdPartnerschap.
     * @return geregistreerdPartnerschap
     */
    public GeregistreerdPartnerschapElement getGeregistreerdPartnerschap() {
        return geregistreerdPartnerschap;
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.REGISTRATIE_EINDE_GEREGISTREERD_PARTNERSCHAP;
    }
}
