/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.generiekmodel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NadereAanduidingVervalAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.poc.generiekmodel.bmr.ElementAttribuut;

/**
 * Een enkele historie rij
 */
public final class BrpGroepVoorkomen implements Visitable {

    private int voorkomensleutel;
    private BrpGroep parent;

    /**
     * dienst verantwoording
     */
    private Dienst dienstInhoud;
    private Dienst dienstVerval;

    /**
     * actie verantwoording
     */
    private ActieModel actieInhoud;
    private ActieModel actieVerval;

    /**
     *
     */
    private ActieModel actieVervalTbvLeveringMutaties;

    /**
     * De attributen van dit voorkomen
     */
    private final Map<ElementAttribuut, BrpAttribuut> attributen = new HashMap<>();

    /**
     * Constructor voor voorkomen
     */
    public BrpGroepVoorkomen() {
    }

    public int getVoorkomensleutel() {
        return voorkomensleutel;
    }

    public void setVoorkomensleutel(final int voorkomensleutel) {
        this.voorkomensleutel = voorkomensleutel;
    }

    public void setParent(final BrpGroep parent) {
        this.parent = parent;
    }

    public Map<ElementAttribuut, BrpAttribuut> getAttributen() {
        return Collections.unmodifiableMap(attributen);
    }

    /**
     *
     * @param attribuut
     */
    public void setAttribuut(final BrpAttribuut attribuut) {
        attributen.put(attribuut.getElement(), attribuut);
    }

    /**
     *
     * @return
     */
    public BrpGroep getParent() {
        return parent;
    }

    /**
     *
     *
     * @return
     */
    public DatumTijdAttribuut getVervalDatumTijd() {
        return (DatumTijdAttribuut) attributen.get(ElementAttribuut.FORMEEL_TS_VERVAL).getWaarde();
    }

    /**
     *
     * @param vervalDatumTijd
     */
    public void setVervalDatumTijd(final DatumTijdAttribuut vervalDatumTijd) {
        setAttribuut(new BrpAttribuut(this, ElementAttribuut.FORMEEL_TS_VERVAL, vervalDatumTijd));
    }

    /**
     *
     * @return
     */
    public DatumTijdAttribuut getRegistratieDatumTijd() {
        return (DatumTijdAttribuut) attributen.get(ElementAttribuut.FORMEEL_TS_REG).getWaarde();
    }

    /**
     *
     * @param registratieDatumTijd
     */
    public void setRegistratieDatumTijd(final DatumTijdAttribuut registratieDatumTijd) {
        setAttribuut(new BrpAttribuut(this, ElementAttribuut.FORMEEL_TS_REG, registratieDatumTijd));
    }

    /**
     *
     * @return
     */
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return (DatumEvtDeelsOnbekendAttribuut) attributen.get(ElementAttribuut.MATERIEEL_DAG).getWaarde();
    }

    /**
     *
     * @param datumAanvangGeldigheid
     */
    public void setDatumAanvangGeldigheid(final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid) {
        setAttribuut(new BrpAttribuut(this, ElementAttribuut.MATERIEEL_DAG, datumAanvangGeldigheid));
    }

    /**
     *
     * @return
     */
    public DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return (DatumEvtDeelsOnbekendAttribuut) attributen.get(ElementAttribuut.MATERIEEL_DEG).getWaarde();
    }

    /**
     *
     * @param datumEindeGeldigheid
     */
    public void setDatumEindeGeldigheid(final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid) {
        setAttribuut(new BrpAttribuut(this, ElementAttribuut.MATERIEEL_DEG, datumEindeGeldigheid));
    }

    /**
     *
     * @return
     */
    public NadereAanduidingVervalAttribuut getNadereAanduidingVerval() {
        return (NadereAanduidingVervalAttribuut) attributen.get(ElementAttribuut.NADERE_AANDUIDING_VERVAL).getWaarde();
    }

    /**
     *
     * @param nadereAanduidingVerval
     */
    public void setNadereAanduidingVerval(final NadereAanduidingVervalAttribuut nadereAanduidingVerval) {
        setAttribuut(new BrpAttribuut(this, ElementAttribuut.NADERE_AANDUIDING_VERVAL, nadereAanduidingVerval));
    }

    /**
     *
     * @return
     */
    public JaAttribuut getIndicatieVoorkomenTbvLeveringMutaties() {
        return (JaAttribuut) attributen.get(ElementAttribuut.INDICATIE_VOORKOMEN_TBV_LEVERMUTATIES).getWaarde();
    }

    /**
     *
     * @param attr
     */
    public void setIndicatieVoorkomenTbvLeveringMutaties(final JaAttribuut attr) {
        setAttribuut(new BrpAttribuut(this, ElementAttribuut.INDICATIE_VOORKOMEN_TBV_LEVERMUTATIES, attr));
    }

    /**
     *
     * @return
     */
    public Dienst getDienstInhoud() {
        return dienstInhoud;
    }

    /**
     *
     * @param dienstInhoud
     */
    public void setDienstInhoud(final Dienst dienstInhoud) {
        this.dienstInhoud = dienstInhoud;
    }

    /**
     *
     * @return
     */
    public Dienst getDienstVerval() {
        return dienstVerval;
    }

    /**
     *
     * @param dienstVerval
     */
    public void setDienstVerval(Dienst dienstVerval) {
        this.dienstVerval = dienstVerval;
    }

    /**
     *
     * @return
     */
    public ActieModel getActieInhoud() {
        return actieInhoud;
    }

    /**
     *
     * @param actieInhoud
     */
    public void setActieInhoud(final ActieModel actieInhoud) {
        this.actieInhoud = actieInhoud;
    }

    /**
     *
     * @return
     */
    public ActieModel getActieVerval() {
        return actieVerval;
    }

    /**
     *
     * @param actieVerval
     */
    public void setActieVerval(final ActieModel actieVerval) {
        this.actieVerval = actieVerval;
    }

    /**
     *
     * @return
     */
    public ActieModel getActieVervalTbvLeveringMutaties() {
        return actieVervalTbvLeveringMutaties;
    }

    /**
     *
     * @param actieVervalTbvLeveringMutaties
     */
    public void setActieVervalTbvLeveringMutaties(final ActieModel actieVervalTbvLeveringMutaties) {
        this.actieVervalTbvLeveringMutaties = actieVervalTbvLeveringMutaties;
    }

    /**
     *
     * @param visitor
     */
    @Override
    public void visit(final ModelVisitor visitor) {
        visitor.visit(this);
    }

//    public T getVerantwoording() {
//        return verantwoording;
//    }
//
//    public void setVerantwoording(T verantwoording) {
//        this.verantwoording = verantwoording;
//    }
}
