/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * De actie voor het laten vervallen van een huwelijk in de bijhouding.
 */
@XmlElement("vervalGeregistreerdPartnerschap")
public class VervalGeregistreerdPartnerschapActieElement extends AbstractVervalHuwelijkOfGpActieElement {

    private final GeregistreerdPartnerschapElement geregistreerdPartnerschap;

    /**
     * Maakt een VervalGeregistreerdPartnerschapActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param nadereAanduidingVerval nadere aanduiding verval
     * @param geregistreerdPartnerschap geregistreerdPartnerschap
     */
    public VervalGeregistreerdPartnerschapActieElement(final Map<String, String> basisAttribuutGroep,
                                                       final DatumElement datumAanvangGeldigheid,
                                                       final DatumElement datumEindeGeldigheid,
                                                       final List<BronReferentieElement> bronReferenties,
                                                       final CharacterElement nadereAanduidingVerval,
                                                       final GeregistreerdPartnerschapElement geregistreerdPartnerschap) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties, nadereAanduidingVerval);
        ValidatieHelper.controleerOpNullWaarde(geregistreerdPartnerschap, "geregistreerdPartnerschap");
        this.geregistreerdPartnerschap = geregistreerdPartnerschap;
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.VERVAL_GEREGISTREERD_PARTNERSCHAP;
    }

    @Override
    protected RelatieElement getRelatieElement() {
        return geregistreerdPartnerschap;
    }

    /**
     * Geef de waarde van geregistreerdPartnerschap.
     * @return geregistreerdPartnerschap
     */
    public GeregistreerdPartnerschapElement getGeregistreerdPartnerschap() {
        return geregistreerdPartnerschap;
    }
}
