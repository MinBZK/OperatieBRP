/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * Dit is de basis class voor alle correctie verval acties met betrekking tot gerelateerde gegevens van de bijhouding.
 */
public abstract class AbstractCorrectieVervalGegevensGerelateerdeActieElement extends AbstractCorrectieVervalActieElement {

    private final PersoonRelatieElement persoon;

    /**
     * Maakt een CorrectieVervalGeboorteGerelateerde object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param nadereAanduidingVerval nadere aanduiding verval
     * @param persoon relatie objecttype
     */
    public AbstractCorrectieVervalGegevensGerelateerdeActieElement(
            final Map<String, String> basisAttribuutGroep,
            final DatumElement datumAanvangGeldigheid,
            final DatumElement datumEindeGeldigheid,
            final List<BronReferentieElement> bronReferenties,
            final CharacterElement nadereAanduidingVerval,
            final PersoonRelatieElement persoon) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties, nadereAanduidingVerval);
        ValidatieHelper.controleerOpNullWaarde(persoon, "persoon");
        this.persoon = persoon;
    }

    @Override
    public List<BijhoudingPersoon> getHoofdPersonen() {
        return Collections.singletonList(persoon.getPersoonEntiteit());
    }

    @Override
    public List<PersoonElement> getPersoonElementen() {
        return Collections.singletonList(persoon);
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

    @Override
    final BmrEntiteit getIstIngang() {
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
    protected final void verwijderIstGegevens() {
        final Iterator<Stapel> iterator = getPersoon().getPersoonEntiteit().getStapels().iterator();
        while(iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    @Override
    protected final boolean moetenIstGegevensVerwijderdWorden() {
        return !getPersoon().getPersoonEntiteit().getStapels().isEmpty();
    }

    @Override
    public boolean heeftInvloedOpGerelateerden() {
        return true;
    }
}
