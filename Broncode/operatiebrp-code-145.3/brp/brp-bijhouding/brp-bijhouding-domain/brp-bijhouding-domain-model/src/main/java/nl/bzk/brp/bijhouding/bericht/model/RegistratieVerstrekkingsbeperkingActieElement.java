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
 * De actie voor het registreren van een verstrekkingsbeperking.
 */
//
@XmlElement("registratieVerstrekkingsbeperking")
public final class RegistratieVerstrekkingsbeperkingActieElement extends AbstractPersoonWijzigingActieElement {

    /**
     * Maakt een RegistratieVerstrekkingsbeperkingActieElement object.
     * @param basisAttribuutGroep    de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid   datum einde geldigheid
     * @param bronnen                bron referenties
     * @param persoon                de persoon
     */
    public RegistratieVerstrekkingsbeperkingActieElement(
            final Map<String, String> basisAttribuutGroep,
            final DatumElement datumAanvangGeldigheid,
            final DatumElement datumEindeGeldigheid,
            final List<BronReferentieElement> bronnen,
            final PersoonGegevensElement persoon) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronnen, persoon);
    }

    @Override
    protected List<MeldingElement> valideerSpecifiekeInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        controleerVerstrekkingsBeperkingEnVolledigeVerstrekkingsBeperking(meldingen);
        controleerVervalVolledigeVerstrekkingsBeperking(meldingen);
        controleerVervalSpecifiekeVerstrekkingsBeperking(meldingen);
        return meldingen;
    }

    @Bedrijfsregel(Regel.R2518)
    private void controleerVerstrekkingsBeperkingEnVolledigeVerstrekkingsBeperking(List<MeldingElement> meldingen) {
        if (getPersoon().getIndicaties().isEmpty() && getPersoon().getVerstrekkingsbeperkingen().isEmpty()) {
            meldingen.add(MeldingElement.getInstance(Regel.R2518, this));
        }
    }

    @Bedrijfsregel(Regel.R2769)
    private void controleerVervalVolledigeVerstrekkingsBeperking(List<MeldingElement> meldeingen) {
        if (getPersoon().heeftPersoonEntiteit()) {
            final BijhoudingPersoon persoonEntiteit = getPersoon().getPersoonEntiteit();
            final PersoonIndicatie persoonIndicatieVolledig = persoonEntiteit.getPersoonIndicatie(SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING);
            if (persoonIndicatieVolledig != null && FormeleHistorieZonderVerantwoording
                    .heeftActueelVoorkomen(persoonIndicatieVolledig.getPersoonIndicatieHistorieSet()) && !getPersoon().getVerstrekkingsbeperkingen()
                    .isEmpty()) {
                meldeingen.add(MeldingElement.getInstance(Regel.R2769, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R2770)
    private void controleerVervalSpecifiekeVerstrekkingsBeperking(List<MeldingElement> meldeingen) {
        if (getPersoon().heeftPersoonEntiteit() && !getPersoon().getPersoonEntiteit().getPersoonVerstrekkingsbeperkingSet().isEmpty() &&
                !getPersoon().getIndicaties().isEmpty()) {
            meldeingen.add(MeldingElement.getInstance(Regel.R2770, this));
        }

    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.REGISTRATIE_VERSTREKKINGSBEPERKING;
    }

    @Override
    public BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
        final BijhoudingPersoon persoonEntiteit = getPersoon().getPersoonEntiteit();
        final BRPActie actie;
        if (persoonEntiteit.isVerwerkbaar()) {
            actie = maakActieEntiteit(administratieveHandeling);
            verwerkVerstrekkingsBeperking(persoonEntiteit, actie);
            verwerkIndicatieVolledigeVerstrekkingsBeperking(persoonEntiteit, actie);
        } else {
            actie = null;
        }
        return actie;
    }


    private void verwerkIndicatieVolledigeVerstrekkingsBeperking(final BijhoudingPersoon persoonEntiteit, final BRPActie actie) {
        if (!getPersoon().getIndicaties().isEmpty()) {
            persoonEntiteit.wijzigPersoonIndicatieEntiteit(getPersoon().getIndicaties().get(0), actie, SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING,
                    getPeilDatum().getWaarde());
            persoonEntiteit.beeindigAllePersoonVerstrekkingsbeperkingEntiteiten(actie);
        }
    }

    private void verwerkVerstrekkingsBeperking(final BijhoudingPersoon persoonEntiteit, final BRPActie actie) {
        getPersoon().getVerstrekkingsbeperkingen().forEach(
                verstrekkingsbeperkingElement -> persoonEntiteit.wijzigPersoonVerstrekkingsbeperkingEntiteit(verstrekkingsbeperkingElement, actie));
        if (!getPersoon().getVerstrekkingsbeperkingen().isEmpty()
                && persoonEntiteit.getPersoonIndicatie(SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING) != null) {
            persoonEntiteit.laatPersoonIndicatieVervallen(SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING, actie);
        }
    }


    @Override
    public DatumElement getPeilDatum() {
        return getVerzoekBericht().getAdministratieveHandeling().getHoofdActie().getDatumAanvangGeldigheid();
    }
}
