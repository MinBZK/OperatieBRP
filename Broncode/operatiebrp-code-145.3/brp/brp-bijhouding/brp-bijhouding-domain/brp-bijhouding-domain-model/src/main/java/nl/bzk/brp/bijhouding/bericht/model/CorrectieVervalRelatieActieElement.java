/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * De actie voor het laten vervallen van een relatie voorkomen in de bijhouding.
 */
@XmlElement("correctievervalRelatie")
public final class CorrectieVervalRelatieActieElement extends AbstractCorrectieVervalActieElement<RelatieHistorie> implements CorrectieRelatieActieElement {

    private final RelatieElement relatie;

    /**
     * Maakt een CorrectieVervalRelatieActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param nadereAanduidingVerval nadere aanduiding verval
     * @param relatie relatie objecttype
     */
    public CorrectieVervalRelatieActieElement(
            final Map<String, String> basisAttribuutGroep,
            final DatumElement datumAanvangGeldigheid,
            final DatumElement datumEindeGeldigheid,
            final List<BronReferentieElement> bronReferenties,
            final CharacterElement nadereAanduidingVerval,
            final RelatieElement relatie) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties, nadereAanduidingVerval);
        ValidatieHelper.controleerOpNullWaarde(relatie, "relatie");
        this.relatie = relatie;
    }

    /**
     * Geef de waarde van relatie.
     * @return relatie
     */
    public RelatieElement getRelatie() {
        return relatie;
    }

    @Override
    BmrEntiteit getIstIngang() {
        return relatie;
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.CORRECTIEVERVAL_RELATIE;
    }

    @Override
    public List<BijhoudingPersoon> getHoofdPersonen() {
        return relatie.getRelatieEntiteit().getHoofdPersonen(getVerzoekBericht());
    }

    @Override
    public List<PersoonElement> getPersoonElementen() {
        return relatie.getPersoonElementen();
    }

    @Override
    public DatumElement getPeilDatum() {
        return getVerzoekBericht().getDatumOntvangst();
    }

    @Override
    public BijhoudingRelatie getRelatieEntiteit() {
        return relatie.getRelatieEntiteit();
    }

    @Override
    protected List<MeldingElement> valideerCorrectieInhoud() {
        return VALIDATIE_OK;
    }

    @Override
    protected RelatieHistorie bepaalTeVervallenVoorkomen() {
        RelatieHistorie result = null;
        final BijhoudingRelatie relatieEntiteit = relatie.getRelatieEntiteit();
        if (relatieEntiteit != null && relatie.getRelatieGroep().getVoorkomenSleutel() != null) {
            result = relatieEntiteit.zoekRelatieHistorieVoorVoorkomenSleutel(relatie.getRelatieGroep().getVoorkomenSleutel());
        }
        return result;
    }

    @Override
    protected BmrGroep getOngeldigAangewezenObjectOfVoorkomen() {
        BmrGroep result = null;
        final BijhoudingRelatie relatieEntiteit = relatie.getRelatieEntiteit();
        if (relatieEntiteit != null && relatie.getRelatieGroep().getVoorkomenSleutel() != null) {
            final RelatieHistorie
                    historieVoorSleutel = relatieEntiteit.zoekRelatieHistorieVoorVoorkomenSleutel(relatie.getRelatieGroep().getVoorkomenSleutel());
            if (historieVoorSleutel == null) {
                result = relatie.getRelatieGroep();
            }
        }
        return result;
    }

    @Override
    protected void verwijderIstGegevens() {
        for (final Stapel stapel : relatie.getRelatieEntiteit().getStapels()) {
            stapel.getPersoon().removeStapel(stapel);
            relatie.getRelatieEntiteit().removeStapel(stapel);
        }
    }

    @Override
    protected boolean moetenIstGegevensVerwijderdWorden() {
        return !relatie.getRelatieEntiteit().getStapels().isEmpty();
    }
}
