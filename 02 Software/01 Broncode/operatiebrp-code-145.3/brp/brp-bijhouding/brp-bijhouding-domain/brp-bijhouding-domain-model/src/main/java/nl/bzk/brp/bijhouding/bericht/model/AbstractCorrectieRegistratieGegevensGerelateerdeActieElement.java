/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * Dit is de basis class voor alle correctie registratie acties van GereleateerdeGegevens.
 */
public abstract class AbstractCorrectieRegistratieGegevensGerelateerdeActieElement extends AbstractCorrectieRegistratieActieElement {

    private final PersoonRelatieElement persoon;

    /**
     * Maakt een AbstractCorrectieRegistratieGegevensGerelateerdeActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param persoon relatie objecttype
     */
    public AbstractCorrectieRegistratieGegevensGerelateerdeActieElement(final Map<String, String> basisAttribuutGroep,
                                                                        final DatumElement datumAanvangGeldigheid,
                                                                        final DatumElement datumEindeGeldigheid,
                                                                        final List<BronReferentieElement> bronReferenties,
                                                                        final PersoonRelatieElement persoon) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties);
        ValidatieHelper.controleerOpNullWaarde(persoon, "persoon");
        this.persoon = persoon;
    }

    @Override
    public List<BijhoudingPersoon> getHoofdPersonen() {
        return Collections.singletonList(getPersoon().getPersoonEntiteit());
    }

    @Override
    public List<PersoonElement> getPersoonElementen() {
        return Collections.singletonList(getPersoon());
    }

    @Override
    public DatumElement getPeilDatum() {
        return getVerzoekBericht().getDatumOntvangst();
    }

    /**
     * geeft persoon relatie element terug.
     * @return persoon
     */
    public PersoonRelatieElement getPersoon() {
        return persoon;
    }

    /**
     * Geeft de partner terug binnen de opgegeven relatie.
     * @return de {@link PersoonGegevensElement} van de partner
     */
    PersoonGegevensElement getPartner(){
        return getPersoon().getBetrokkenheden().get(0).getRelatieElement().getBetrokkenheden().get(0).getPersoon();
    }

    @Override
    public final boolean heeftInvloedOpGerelateerden() {
        return true;
    }

    @Bedrijfsregel(Regel.R2528)
    @Bedrijfsregel(Regel.R2535)
    @Override
    protected final List<MeldingElement> valideerSpecifiekeInhoud() {
        final List<MeldingElement>meldingen = new ArrayList<>();
        final FormeleHistorie nieuweHistorie = maakNieuwVoorkomen();
        if (nieuweHistorie instanceof MaterieleHistorie) {
            final MaterieleHistorie materieleHistorie = (MaterieleHistorie) nieuweHistorie;
            final Integer datumAanvangGeldigheid = getDatumAanvangGeldigheid().getWaarde();
            final Integer datumEindeGeldigheid = BmrAttribuut.getWaardeOfNull(getDatumEindeGeldigheid());

            if (!materieleHistorie.isDegGelijkAanDagToegestaan() ) {
                if ((datumEindeGeldigheid != null && datumEindeGeldigheid <= datumAanvangGeldigheid)) {
                    meldingen.add(MeldingElement.getInstance(Regel.R2528, this));
                }
            } else {
                if (datumEindeGeldigheid != null && datumEindeGeldigheid < datumAanvangGeldigheid) {
                    meldingen.add(MeldingElement.getInstance(Regel.R2535, this));
                }
            }
        }
        return meldingen;
    }
}
