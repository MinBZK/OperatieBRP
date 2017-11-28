/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * De actie voor het registreren van een staatloos indicatie.
 */
//
@XmlElement("registratieStaatloos")
public class RegistratieStaatloosActieElement extends AbstractPersoonWijzigingActieElement {
    /**
     * Maakt een AbstractPersoonWijzigingActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param persoon de persoon
     */
    public RegistratieStaatloosActieElement(final Map<String, String> basisAttribuutGroep,
                                            final DatumElement datumAanvangGeldigheid,
                                            final DatumElement datumEindeGeldigheid,
                                            final List<BronReferentieElement> bronReferenties, final PersoonGegevensElement persoon) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties, persoon);
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.REGISTRATIE_STAATLOOS;
    }

    @Override
    public BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
        final BRPActie actie;
        final PersoonElement persoonElement = getPersoon();
        final BijhoudingPersoon persoon = persoonElement.getPersoonEntiteit();
        if (persoon.isVerwerkbaar()) {
            actie = maakActieEntiteit(administratieveHandeling);
            persoon.wijzigPersoonIndicatieEntiteit(getPersoon().getIndicaties().get(0), actie, SoortIndicatie.STAATLOOS,
                    getDatumAanvangGeldigheid().getWaarde());
        } else {
            actie = null;
        }

        return actie;
    }

    @Override
    public DatumElement getPeilDatum() {
        return getDatumAanvangGeldigheid();
    }

    @Bedrijfsregel(Regel.R1816)
    @Override
    protected List<MeldingElement> valideerSpecifiekeInhoud() {
        final List<MeldingElement> meldingen = new LinkedList<>();
        if (getPersoon().heeftPersoonEntiteit()
                && !getPersoon().getPersoonEntiteit().isEersteInschrijving() && heeftNationaliteitOpPeildatum()) {
            meldingen.add(MeldingElement.getInstance(Regel.R1816, this));
        }
        return meldingen;
    }

    private boolean heeftNationaliteitOpPeildatum() {
        for (final PersoonNationaliteit persoonNationaliteit : getPersoon().getPersoonEntiteit().getPersoonNationaliteitSet()) {
            if (MaterieleHistorie.getGeldigVoorkomenOpPeildatum(persoonNationaliteit.getPersoonNationaliteitHistorieSet(),
                    getDatumAanvangGeldigheid().getWaarde()) != null) {
                return true;
            }
        }
        return false;
    }

}
