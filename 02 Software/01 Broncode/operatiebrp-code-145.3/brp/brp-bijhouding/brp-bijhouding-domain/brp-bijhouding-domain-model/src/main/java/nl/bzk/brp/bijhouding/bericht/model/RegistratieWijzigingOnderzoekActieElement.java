/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusOnderzoek;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * De actie voor het registreren van een aanvang van een huwelijk.
 */
//
@XmlElement("registratieWijzigingOnderzoek")
public final class RegistratieWijzigingOnderzoekActieElement extends AbstractPersoonWijzigingActieElement {

    private static final Set<List<StatusOnderzoek>> STATUS_OVERGANGEN = new HashSet<>();

    static {
        STATUS_OVERGANGEN.add(Arrays.asList(StatusOnderzoek.IN_UITVOERING, StatusOnderzoek.GESTAAKT));
        STATUS_OVERGANGEN.add(Arrays.asList(StatusOnderzoek.GESTAAKT, StatusOnderzoek.IN_UITVOERING));
    }

    /**
     * Maakt een RegistratieAanvangOnderzoekActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param persoon de persoon
     */
    public RegistratieWijzigingOnderzoekActieElement(
            final Map<String, String> basisAttribuutGroep,
            final DatumElement datumAanvangGeldigheid,
            final DatumElement datumEindeGeldigheid,
            final List<BronReferentieElement> bronReferenties,
            final PersoonGegevensElement persoon) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties, persoon);
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.REGISTRATIE_WIJZIGING_ONDERZOEK;
    }

    @Override
    public BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
        final BijhoudingPersoon persoonEntiteit = getHoofdPersonen().get(0);
        final BRPActie actie;
        if (persoonEntiteit.isVerwerkbaar()) {
            actie = maakActieEntiteit(administratieveHandeling);
            getOnderzoek().getEntiteit().wijzigStatusOnderzoek(actie, getOnderzoek().getOnderzoekGroep().getStatusOnderzoek());
        } else {
            actie = null;
        }
        return actie;
    }

    @Override
    public DatumElement getPeilDatum() {
        return new DatumElement(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getOnderzoek().getEntiteit().getOnderzoekHistorieSet())
                .getDatumAanvang());
    }

    @Override
    protected List<MeldingElement> valideerSpecifiekeInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        controleerStatusOvergang(meldingen);
        return meldingen;
    }

    @Bedrijfsregel(Regel.R2607)
    private void controleerStatusOvergang(final List<MeldingElement> meldingen) {
        final StatusOnderzoek
                huidigeStatus =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getOnderzoek().getEntiteit().getOnderzoekHistorieSet())
                        .getStatusOnderzoek();
        final StatusOnderzoek nieuweStatus = getOnderzoek().getOnderzoekGroep().getStatusOnderzoek();
        if (!STATUS_OVERGANGEN.contains(Arrays.asList(huidigeStatus, nieuweStatus))) {
            meldingen.add(MeldingElement.getInstance(Regel.R2607, this));
        }
    }

    private OnderzoekElement getOnderzoek() {
        return getPersoon().getOnderzoeken().iterator().next();
    }
}
