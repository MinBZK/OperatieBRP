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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * De actie voor het beeindigen van een bijzondere verblijfsrechtelijke positie.
 */
//
@XmlElement("beeindigingBijzondereVerblijfsrechtelijkePositie")
public class BeeindigingBijzondereVerblijfsrechtelijkePositieActieElement extends AbstractPersoonWijzigingActieElement {

    /**
     * Maakt een {@link BeeindigingBijzondereVerblijfsrechtelijkePositieActieElement} object.
     * @param basisAttribuutGroep de basis attribuutgroepnN
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param persoon de persoon
     */
    public BeeindigingBijzondereVerblijfsrechtelijkePositieActieElement(final Map<String, String> basisAttribuutGroep,
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
            persoonEntiteit.laatPersoonIndicatieVervallen(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE, actie);
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
        controleerGroepNietVervallen(meldingen);
        return meldingen;
    }

    @Bedrijfsregel(Regel.R2501)
    private void controleerGroepNietVervallen(final List<MeldingElement> meldingen) {
        final BijhoudingPersoon persoonEntiteit = getPersoon().getPersoonEntiteit();
        final PersoonIndicatie persoonIndicatie = persoonEntiteit.getPersoonIndicatie(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE);
        if (persoonIndicatie != null) {
            if (FormeleHistorieZonderVerantwoording.getNietVervallenVoorkomens(persoonIndicatie.getPersoonIndicatieHistorieSet()).isEmpty()) {
                meldingen.add(MeldingElement.getInstance(Regel.R2501, this));
            }
        } else {
            meldingen.add(MeldingElement.getInstance(Regel.R2501, this));
        }
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.BEEINDIGING_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE;
    }
}
