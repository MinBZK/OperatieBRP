/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * Actie element voor het registreren(wijzigen) van de geboorte van de gerelateerde.
 */
@XmlElement("registratieGeboorteGerelateerde")
public class RegistratieGeboorteGerelateerdeActieElement extends AbstractWijzigenGerelateerdeGegevensActieElement {

    /**
     * Maakt een {@link RegistratieGeboorteGerelateerdeActieElement} object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param persoon de persoon
     */
    public RegistratieGeboorteGerelateerdeActieElement(final Map<String, String> basisAttribuutGroep,
                                                       final DatumElement datumAanvangGeldigheid,
                                                       final DatumElement datumEindeGeldigheid,
                                                       final List<BronReferentieElement> bronReferenties,
                                                       final PersoonRelatieElement persoon) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties, persoon);
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.REGISTRATIE_GEBOORTE_GERELATEERDE;
    }

    @Override
    public BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
        BRPActie actie = null;
        final BijhoudingPersoon persoon = getPersoonRelatieElement().getPersoonEntiteit();
        if (persoon.isVerwerkbaar()) {
            actie = maakActieEntiteit(administratieveHandeling);
            final RelatieElement huwelijkElement = getPersoonRelatieElement().getBetrokkenheden().get(0).getRelatieElement();
            final PersoonGegevensElement partnerPersoonElement = huwelijkElement.getBetrokkenheden().get(0).getPersoon();
            final BijhoudingPersoon partnerPersoon = bepaalPartner(persoon.getId(), huwelijkElement.getRelatieEntiteit());
            partnerPersoon.voegPersoonGeboorteHistorieToe(partnerPersoonElement.getGeboorte(), actie);
        }
        return actie;
    }
}
