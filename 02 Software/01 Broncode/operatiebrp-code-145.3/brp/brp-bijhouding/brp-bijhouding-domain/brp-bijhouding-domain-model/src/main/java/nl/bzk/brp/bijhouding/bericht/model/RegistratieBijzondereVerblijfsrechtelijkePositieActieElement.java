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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * De actie voor het registreren van een bijzondere verblijfsrechtelijke positie.
 */
//
@XmlElement("registratieBijzondereVerblijfsrechtelijkePositie")
public final class RegistratieBijzondereVerblijfsrechtelijkePositieActieElement extends AbstractPersoonWijzigingActieElement {

    /**
     * Maakt een {@link RegistratieBijzondereVerblijfsrechtelijkePositieActieElement} object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param persoon de persoon, mag niet null zijn
     */
    public RegistratieBijzondereVerblijfsrechtelijkePositieActieElement(final Map<String, String> basisAttribuutGroep,
                                                                        final DatumElement datumAanvangGeldigheid,
                                                                        final DatumElement datumEindeGeldigheid,
                                                                        final List<BronReferentieElement> bronReferenties,
                                                                        final PersoonGegevensElement persoon) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties, persoon);
    }

    @Override
    public BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
        final BijhoudingPersoon persoonEntiteit = getPersoon().getPersoonEntiteit();
        final BRPActie actie;
        if (persoonEntiteit.isVerwerkbaar()) {
            actie = maakActieEntiteit(administratieveHandeling);
            persoonEntiteit
                    .wijzigPersoonIndicatieEntiteit(getPersoon().getIndicaties().get(0), actie, SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE,
                            getPeilDatum().getWaarde());
        } else {
            actie = null;
        }
        return actie;
    }

    @Override
    public DatumElement getPeilDatum() {
        return getVerzoekBericht().getDatumOntvangst();
    }

    @Override
    protected List<MeldingElement> valideerSpecifiekeInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        controleerPersoonGeenNederlandseNationaliteit(meldingen);

        return meldingen;
    }

    @Bedrijfsregel(Regel.R1431)
    private void controleerPersoonGeenNederlandseNationaliteit(final List<MeldingElement> meldingen) {
        for (BijhoudingPersoon persoon : getHoofdPersonen()) {
            if (persoon.heeftNederlandseNationaliteit(getPeilDatum().getWaarde())) {
                meldingen.add(MeldingElement.getInstance(Regel.R1431, this));
            }
        }
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.REGISTRATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE;
    }
}
