/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * De actie voor het registreren van een nieuw indetificatienummer voorkomen (als onderdeel van een correctie) in de bijhouding.
 */
@XmlElement("correctieregistratieIdentificatienummersGerelateerde")
public class CorrectieRegistratieIdentificatienummersRegistratieGegevensGerelateerde extends AbstractCorrectieRegistratieGegevensGerelateerdeActieElement {


    /**
     * Maakt een CorrectieVervalIdentificatienummersGerelateerde object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param persoon relatie objecttype
     */
    public CorrectieRegistratieIdentificatienummersRegistratieGegevensGerelateerde(
            final Map<String, String> basisAttribuutGroep,
            final DatumElement datumAanvangGeldigheid,
            final DatumElement datumEindeGeldigheid,
            final List<BronReferentieElement> bronReferenties,
            final PersoonRelatieElement persoon) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties,persoon);
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.CORRECTIEREGISTRATIE_IDENTIFICATIENUMMERS_GERELATEERDE;
    }

    @Override
    protected Set bepaalSetVoorNieuwVoorkomen() {
        return getPartner().getPersoonEntiteit().getPersoonIDHistorieSet();
    }

    @Override
    protected FormeleHistorie maakNieuwVoorkomen() {
        return getPartner().getPersoonEntiteit().maakNieuweIDHistorieVoorCorrectie(getPartner().getIdentificatienummers());
    }
}
