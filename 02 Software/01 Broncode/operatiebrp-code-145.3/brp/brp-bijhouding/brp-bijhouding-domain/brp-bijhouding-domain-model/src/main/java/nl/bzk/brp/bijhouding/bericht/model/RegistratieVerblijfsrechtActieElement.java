/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * De actie voor het registreren van een verblijfsrecht.
 */
//
@XmlElement("registratieVerblijfsrecht")
public class RegistratieVerblijfsrechtActieElement extends AbstractPersoonWijzigingActieElement {
    /**
     * Maakt een AbstractPersoonWijzigingActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param persoon de persoon
     */
    public RegistratieVerblijfsrechtActieElement(final Map<String, String> basisAttribuutGroep,
                                                 final DatumElement datumAanvangGeldigheid,
                                                 final DatumElement datumEindeGeldigheid,
                                                 final List<BronReferentieElement> bronReferenties, final PersoonGegevensElement persoon) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties, persoon);
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.REGISTRATIE_VERBLIJFSRECHT;
    }

    @Override
    public BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
        BRPActie actie = null;
        BijhoudingPersoon bijhoudingPersoon = getPersoon().getPersoonEntiteit();
        if (bijhoudingPersoon.isVerwerkbaar()) {
            actie = maakActieEntiteit(administratieveHandeling);
            bijhoudingPersoon.voegPersoonVerblijfsrechtHistorieToe(getPersoon().getVerblijfsrecht(), actie);
        }
        return actie;
    }

    @Override
    public DatumElement getPeilDatum() {
        return getPersoon().getVerblijfsrecht().getDatumAanvang();
    }

    @Override
    protected List<MeldingElement> valideerSpecifiekeInhoud() {
        List<MeldingElement> meldingen = new LinkedList<>();
        controleerPersoon(meldingen);
        return meldingen;
    }


    private void controleerPersoon(final List<MeldingElement> meldingen) {
        controleerPersoonIndicatie(meldingen);
        controleerPersoonNationaliteit(meldingen);

    }

    @Bedrijfsregel(Regel.R1901)
    private void controleerPersoonNationaliteit(final List<MeldingElement> meldingen) {
        for (final BijhoudingPersoon persoon : getHoofdPersonen()) {
            if (persoon.heeftNederlandseNationaliteit(getPeilDatum().getWaarde())) {
                meldingen.add(MeldingElement.getInstance(Regel.R1901, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R2326)
    private void controleerPersoonIndicatie(final List<MeldingElement> meldingen) {
        for (final BijhoudingPersoon persoon : getHoofdPersonen()) {
            final PersoonIndicatie indicatie = persoon.getPersoonIndicatie(SoortIndicatie.BEHANDELD_ALS_NEDERLANDER);
            if (indicatie != null) {
                final Set<PersoonIndicatieHistorie>
                        nietVervallenVoorkomens =
                        FormeleHistorieZonderVerantwoording.getNietVervallenVoorkomens(indicatie.getPersoonIndicatieHistorieSet());
                final Optional<PersoonIndicatieHistorie>
                        indicatieHistorie =
                        nietVervallenVoorkomens.stream().filter(historie -> DatumUtil
                                .valtDatumBinnenPeriode(getPeilDatum().getWaarde(), historie.getDatumAanvangGeldigheid(), historie.getDatumEindeGeldigheid()))
                                .findAny();
                indicatieHistorie.ifPresent(persoonIndicatieHistorie -> meldingen.add(MeldingElement.getInstance(Regel.R2326, this)));
            }
        }
    }
}
