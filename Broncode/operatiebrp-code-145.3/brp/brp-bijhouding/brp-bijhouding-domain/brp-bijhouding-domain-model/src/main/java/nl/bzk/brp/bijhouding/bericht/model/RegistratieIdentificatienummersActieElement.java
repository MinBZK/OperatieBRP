/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * De actie voor het registreren van identificatienummers.
 */
//
@XmlElement("registratieIdentificatienummers")
public final class RegistratieIdentificatienummersActieElement extends AbstractPersoonWijzigingActieElement {

    /**
     * Maakt een AbstractActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param persoon persoon
     */
    public RegistratieIdentificatienummersActieElement(final Map<String, String> basisAttribuutGroep,
                                                       final DatumElement datumAanvangGeldigheid,
                                                       final DatumElement datumEindeGeldigheid,
                                                       final List<BronReferentieElement> bronReferenties,
                                                       final PersoonGegevensElement persoon) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties, persoon);
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS;
    }

    @Override
    public BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
        final BRPActie actie;
        final BijhoudingPersoon persoon = getPersoon().getPersoonEntiteit();
        if (persoon.isVerwerkbaar()) {
            actie = maakActieEntiteit(administratieveHandeling);
            persoon.voegPersoonIDHistorieToe(getPersoon().getIdentificatienummers(), actie, getDatumAanvangGeldigheid().getWaarde());
        } else {
            actie = null;
        }
        return actie;
    }

    @Override
    public DatumElement getPeilDatum() {
        return getDatumAanvangGeldigheid();
    }

    @Override
    protected List<MeldingElement> valideerSpecifiekeInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        controleerBurgerServiceNummerBestaatNogNiet(meldingen);
        controleerAdministratienummerBestaatNogNiet(meldingen);
        return meldingen;
    }

    @Bedrijfsregel(Regel.R1586)
    private void controleerBurgerServiceNummerBestaatNogNiet(final List<MeldingElement> meldingen) {
        if (getPersoon().heeftPersoonEntiteit()
                && getPersoon().getPersoonEntiteit().isIngeschreveneEnNietOpgeschortMetRedenFoutOfGewist()
                && getPersoonRepository().komtBsnReedsVoor(getPersoon().getIdentificatienummers().getBurgerservicenummer().getWaarde())) {
            meldingen.add(MeldingElement.getInstance(Regel.R1586, this));
        }
    }

    @Bedrijfsregel(Regel.R1588)
    private void controleerAdministratienummerBestaatNogNiet(final List<MeldingElement> meldingen) {
        if (getPersoon().heeftPersoonEntiteit() && getPersoon().getPersoonEntiteit().isIngeschreveneEnNietOpgeschortMetRedenFoutOfGewist()
                && getPersoonRepository()
                .komtAdministratienummerReedsVoor(getPersoon().getIdentificatienummers().getAdministratienummer().getWaarde())) {
            meldingen.add(MeldingElement.getInstance(Regel.R1588, this));
        }
    }
}
