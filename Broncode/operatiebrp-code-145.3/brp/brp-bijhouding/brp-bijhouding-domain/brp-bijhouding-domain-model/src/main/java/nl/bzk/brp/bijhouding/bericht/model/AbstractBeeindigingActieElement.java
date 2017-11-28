/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;

/**
 * Abstract class voor acties die een beeindiging doen.
 * @param <T> extends MaterieleHistorie
 */
public abstract class AbstractBeeindigingActieElement<T extends MaterieleHistorie> extends AbstractPersoonWijzigingActieElement {

    /**
     * Maakt een AbstractPersoonWijzigingActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param persoon de persoon
     */
    public AbstractBeeindigingActieElement(final Map<String, String> basisAttribuutGroep,
                                           final DatumElement datumAanvangGeldigheid,
                                           final DatumElement datumEindeGeldigheid,
                                           final List<BronReferentieElement> bronReferenties,
                                           final PersoonGegevensElement persoon) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties, persoon);
    }

    @Override
    protected List<MeldingElement> valideerSpecifiekeInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        controleerDatumEindeGeldigheidOpOfNaDatumAanvangGeldigheid(meldingen);
        controleerOfActueelAlBeeindigdIs(meldingen);
        meldingen.addAll(valideerActieInhoud());
        return meldingen;
    }

    protected abstract List<MeldingElement> valideerActieInhoud();

    @Bedrijfsregel(Regel.R1658)
    private void controleerDatumEindeGeldigheidOpOfNaDatumAanvangGeldigheid(final List<MeldingElement> meldingen) {
        final Integer datumAanvangGeldigheiVanTeBeeindingenRij = getDatumAanvangGeldigheiVanTeBeeindingenRij();
        if (datumAanvangGeldigheiVanTeBeeindingenRij != null && datumAanvangGeldigheiVanTeBeeindingenRij - getDatumEindeGeldigheid().getWaarde() > 0) {
            meldingen.add(MeldingElement.getInstance(Regel.R1658, this));
        }
    }

    @Bedrijfsregel(Regel.R2640)
    private void controleerOfActueelAlBeeindigdIs(List<MeldingElement> meldingen) {
        Set<T> teBeeindigenHistorieSet = getTeBeeindigenHistorie();
        if (!teBeeindigenHistorieSet.isEmpty()) {
            final MaterieleHistorie geldigVoorkomenOpPeildatum =
                    MaterieleHistorie.getGeldigVoorkomenOpPeildatum(teBeeindigenHistorieSet, getPeilDatum().getWaarde());
            if (geldigVoorkomenOpPeildatum == null || geldigVoorkomenOpPeildatum.getDatumEindeGeldigheid() != null) {
                meldingen.add(MeldingElement.getInstance(Regel.R2640, this));
            }
        }
    }

    /**
     * Geeft de datum aanvang geldigheid terug van de rij die deze actie moet beeindigen.
     * @return de datum aanvang geldigheid
     */
    protected abstract Integer getDatumAanvangGeldigheiVanTeBeeindingenRij();

    /**
     * Geeft de te beeindigen historie set terug.
     */
    abstract Set<T> getTeBeeindigenHistorie();
}
