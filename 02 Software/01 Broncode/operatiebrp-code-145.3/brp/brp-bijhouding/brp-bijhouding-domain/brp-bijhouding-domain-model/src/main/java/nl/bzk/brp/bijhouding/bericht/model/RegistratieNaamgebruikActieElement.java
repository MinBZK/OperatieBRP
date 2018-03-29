/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

import java.util.List;
import java.util.Map;

/**
 * De actie voor het registreren van naamgebruik wijziging.
 */
//
@XmlElement("registratieNaamgebruik")
public final class RegistratieNaamgebruikActieElement extends AbstractPersoonWijzigingActieElement {

    /**
     * Maakt een RegistratieNaamgebruikActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronnen bron referenties
     * @param persoon de persoon
     */
    public RegistratieNaamgebruikActieElement(
            final Map<String, String> basisAttribuutGroep,
            final DatumElement datumAanvangGeldigheid,
            final DatumElement datumEindeGeldigheid,
            final List<BronReferentieElement> bronnen,
            final PersoonGegevensElement persoon) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronnen, persoon);
        ValidatieHelper.controleerOpNullWaarde(persoon.getNaamgebruik(), "naamgebruik");
    }

    /**
     * Geef de waarde van Persoon / Naamgebruik.
     * @return naamgebruik
     */
    public NaamgebruikElement getNaamgebruik() {
        return getPersoon().getNaamgebruik();
    }

    @Override
    protected List<MeldingElement> valideerSpecifiekeInhoud() {
        return VALIDATIE_OK;
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.REGISTRATIE_NAAMGEBRUIK;
    }

    @Override
    public BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
        final BijhoudingPersoon persoonEntiteit = getPersoon().getPersoonEntiteit();
        final BRPActie actie;
        if (persoonEntiteit.isVerwerkbaar()) {
            actie = maakActieEntiteit(administratieveHandeling);
            persoonEntiteit.wijzigPersoonNaamgebruikHistorie(actie, getPersoon().getNaamgebruik(), true);
        } else {
            actie = null;
        }
        return actie;
    }

    @Override
    public DatumElement getPeilDatum() {
        return getVerzoekBericht().getAdministratieveHandeling().getHoofdActie().getPeilDatum();
    }
}
