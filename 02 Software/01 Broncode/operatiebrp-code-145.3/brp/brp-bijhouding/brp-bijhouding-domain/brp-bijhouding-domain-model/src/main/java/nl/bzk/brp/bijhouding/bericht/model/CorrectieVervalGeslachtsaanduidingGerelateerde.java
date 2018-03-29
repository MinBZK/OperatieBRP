/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * De actie voor het laten vervallen van een geslachtsaanduiding voorkomen in de bijhouding.
 */
@XmlElement("correctievervalGeslachtsaanduidingGerelateerde")
public class CorrectieVervalGeslachtsaanduidingGerelateerde extends AbstractCorrectieVervalGegevensGerelateerdeActieElement {

    /**
     * Maakt een CorrectieVervalIdentificatienummersGerelateerde object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param nadereAanduidingVerval nadere aanduiding verval
     * @param persoon relatie objecttype
     */
    public CorrectieVervalGeslachtsaanduidingGerelateerde(
            final Map<String, String> basisAttribuutGroep,
            final DatumElement datumAanvangGeldigheid,
            final DatumElement datumEindeGeldigheid,
            final List<BronReferentieElement> bronReferenties,
            final CharacterElement nadereAanduidingVerval,
            final PersoonRelatieElement persoon) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties, nadereAanduidingVerval, persoon);
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.CORRECTIEVERVAL_GESLACHTSAANDUIDING_GERELATEERDE;
    }

    @Override
    protected List<MeldingElement> valideerCorrectieInhoud() {
        return VALIDATIE_OK;
    }

    @Override
    protected PersoonGeslachtsaanduidingHistorie bepaalTeVervallenVoorkomen() {
        PersoonGeslachtsaanduidingHistorie result = null;
        final BijhoudingPersoon persoon = getPartner().getPersoonEntiteit();
        final String voorkomenSleutel = getPartner().getGeslachtsaanduiding().getVoorkomenSleutel();
        if (persoon != null && voorkomenSleutel != null) {
            result = persoon.zoekRelatieHistorieVoorVoorkomenSleutel(voorkomenSleutel, PersoonGeslachtsaanduidingHistorie.class);
        }
        return result;
    }

    @Override
    protected BmrGroep getOngeldigAangewezenObjectOfVoorkomen() {
        BmrGroep result = null;
        final BijhoudingPersoon persoon = getPartner().getPersoonEntiteit();
        final String voorkomenSleutel = getPartner().getGeslachtsaanduiding().getVoorkomenSleutel();
        if (persoon != null && voorkomenSleutel != null) {
            final PersoonGeslachtsaanduidingHistorie historie =
                    persoon.zoekRelatieHistorieVoorVoorkomenSleutel(voorkomenSleutel, PersoonGeslachtsaanduidingHistorie.class);
            if (historie == null) {
                result = getPartner().getGeslachtsaanduiding();
            }
        }
        return result;
    }
}
