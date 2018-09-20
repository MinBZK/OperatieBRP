/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.internbericht;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.operationeel.ber.BerichtStuurgegevensGroepModel;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Bericht object dat via JMS gecommuniceerd wordt tussen de levering componenten. JSON wordt gebruikt voor (De)serialisatie.
 */
public final class SynchronisatieBerichtGegevens implements BrpObject {

    /**
     * De administratieve handeling identifier.
     */
    @JsonProperty
    private Long administratieveHandelingId;

    /**
     * De toegang abonnement identifier.
     */
    @JsonProperty
    private Integer toegangLeveringsautorisatieId;

    /**
     * Het id van de zendende partij.
     */
    @JsonProperty
    private Short zendendePartijId;

    /**
     * Het id van de ontvangende partij.
     */
    @JsonProperty
    private Short ontvangendePartijId;

    /**
     * De tijdstip registratie van de administratieve handeling.
     */
    @JsonProperty
    private DatumTijdAttribuut administratieveHandelingTijdstipRegistratie;

    /**
     * De dienst identifier.
     */
    @JsonProperty
    private Integer dienstId;

    /**
     * Het stelse.
     */
    @JsonProperty
    private Stelsel stelsel;

    /**
     * De Stuurgegevens.
     */
    @JsonProperty
    private BerichtStuurgegevensGroepModel stuurgegevens;

    /**
     * De geleverde persoon ids.
     */
    @JsonProperty
    private List<Integer> geleverdePersoonsIds;

    /**
     * De doort synchronisatie.
     */
    @JsonProperty
    private SoortSynchronisatieAttribuut soortSynchronisatie;

    /**
     * De soortDienst optie waarvoor geleverd wordt.
     */
    @JsonProperty
    private SoortDienst soortDienst;

    /**
     * De datum aanvang materiele periode van het resultaat.
     */
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriodeResultaat;

    /**
     * De datum einde materiele periode van het rresultaat.
     */
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumEindeMaterielePeriodeResultaat;

    /**
     * De datum tijd aanvang formele periode van het rresultaat.
     */
    @JsonProperty
    private DatumTijdAttribuut datumTijdAanvangFormelePeriodeResultaat;

    /**
     * De datum tijd einde formele periode van het rresultaat.
     */
    @JsonProperty
    private DatumTijdAttribuut datumTijdEindeFormelePeriodeResultaat;

    /**
     * Default constructor nodig voor deserialisatie.
     */
    public SynchronisatieBerichtGegevens() {

    }

    /**
     * Constructor die een administratieve handeling id en een afnemer aanneemt.
     *
     * @param administratieveHandelingId De administratieve handeling.
     * @param toegangLeveringsautorisatieId        Het toegangLeveringsautorisatieId id.
     */
    public SynchronisatieBerichtGegevens(final Long administratieveHandelingId,
        final Integer toegangLeveringsautorisatieId)
    {
        this.toegangLeveringsautorisatieId = toegangLeveringsautorisatieId;
        this.administratieveHandelingId = administratieveHandelingId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("admhndID", administratieveHandelingId)
            .append("toegangLeveringsautorisatieId", toegangLeveringsautorisatieId)
            .append("dienstID", dienstId)
            .append("stelsel", stelsel.getNaam())
            .append("persoonIDs", geleverdePersoonsIds)
            .append("administratieveHandelingTijdstipRegistratie", administratieveHandelingTijdstipRegistratie)
            .append("soortSynchronisatie", soortSynchronisatie)
            .append("soortDienst", soortDienst)
            .append("datumAanvangMaterielePeriodeResultaat", datumAanvangMaterielePeriodeResultaat)
            .append("datumEindeMaterielePeriodeResultaat", datumEindeMaterielePeriodeResultaat)
            .append("datumTijdAanvangFormelePeriodeResultaat", datumTijdAanvangFormelePeriodeResultaat)
            .append("datumTijdEindeFormelePeriodeResultaat", datumTijdEindeFormelePeriodeResultaat)
            .append("zendendePartijId", zendendePartijId)
            .append("ontvangendePartijId", ontvangendePartijId)
            .toString();
    }

    public Long getAdministratieveHandelingId() {
        return administratieveHandelingId;
    }

    public void setAdministratieveHandelingId(final Long administratieveHandelingId) {
        this.administratieveHandelingId = administratieveHandelingId;
    }

    public BerichtStuurgegevensGroepModel getStuurgegevens() {
        return stuurgegevens;
    }

    public void setStuurgegevens(final BerichtStuurgegevensGroepModel stuurgegevens) {
        this.stuurgegevens = stuurgegevens;
    }

    public List<Integer> getGeleverdePersoonsIds() {
        return geleverdePersoonsIds;
    }

    public void setGeleverdePersoonsIds(final List<Integer> geleverdePersoonsIds) {
        this.geleverdePersoonsIds = geleverdePersoonsIds;
    }

    public Integer getToegangLeveringsautorisatieId() {
        return toegangLeveringsautorisatieId;
    }

    public void setToegangLeveringsautorisatieIdId(final Integer toegangLeveringsautorisatieId) {
        this.toegangLeveringsautorisatieId = toegangLeveringsautorisatieId;
    }

    public DatumTijdAttribuut getAdministratieveHandelingTijdstipRegistratie() {
        return administratieveHandelingTijdstipRegistratie;
    }

    public void setAdministratieveHandelingTijdstipRegistratie(
        final DatumTijdAttribuut administratieveHandelingTijdstipRegistratie)
    {
        this.administratieveHandelingTijdstipRegistratie = administratieveHandelingTijdstipRegistratie;
    }

    public SoortSynchronisatieAttribuut getSoortSynchronisatie() {
        return soortSynchronisatie;
    }

    public void setSoortSynchronisatie(final SoortSynchronisatieAttribuut soortSynchronisatie) {
        this.soortSynchronisatie = soortSynchronisatie;
    }

    public Integer getDienstId() {
        return dienstId;
    }

    public void setDienstId(final Integer dienstId) {
        this.dienstId = dienstId;
    }

    public Stelsel getStelsel() {
        return stelsel;
    }

    public void setStelsel(final Stelsel stelsel) {
        this.stelsel = stelsel;
    }

    public SoortDienst getSoortDienst() {
        return soortDienst;
    }

    public void setSoortDienst(final SoortDienst soortDienst) {
        this.soortDienst = soortDienst;
    }

    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangMaterielePeriodeResultaat() {
        return datumAanvangMaterielePeriodeResultaat;
    }

    public void setDatumAanvangMaterielePeriodeResultaat(final DatumEvtDeelsOnbekendAttribuut
        datumAanvangMaterielePeriodeResultaat)
    {
        this.datumAanvangMaterielePeriodeResultaat = datumAanvangMaterielePeriodeResultaat;
    }

    public DatumEvtDeelsOnbekendAttribuut getDatumEindeMaterielePeriodeResultaat() {
        return datumEindeMaterielePeriodeResultaat;
    }

    public void setDatumEindeMaterielePeriodeResultaat(final DatumEvtDeelsOnbekendAttribuut
        datumEindeMaterielePeriodeResultaat)
    {
        this.datumEindeMaterielePeriodeResultaat = datumEindeMaterielePeriodeResultaat;
    }

    public DatumTijdAttribuut getDatumTijdAanvangFormelePeriodeResultaat() {
        return datumTijdAanvangFormelePeriodeResultaat;
    }

    public void setDatumTijdAanvangFormelePeriodeResultaat(
        final DatumTijdAttribuut datumTijdAanvangFormelePeriodeResultaat)
    {
        this.datumTijdAanvangFormelePeriodeResultaat = datumTijdAanvangFormelePeriodeResultaat;
    }

    public DatumTijdAttribuut getDatumTijdEindeFormelePeriodeResultaat() {
        return datumTijdEindeFormelePeriodeResultaat;
    }

    public void setDatumTijdEindeFormelePeriodeResultaat(
        final DatumTijdAttribuut datumTijdEindeFormelePeriodeResultaat)
    {
        this.datumTijdEindeFormelePeriodeResultaat = datumTijdEindeFormelePeriodeResultaat;
    }

    public Short getZendendePartijId() {
        return zendendePartijId;
    }

    public void setZendendePartijId(final Short zendendePartijId) {
        this.zendendePartijId = zendendePartijId;
    }

    public Short getOntvangendePartijId() {
        return ontvangendePartijId;
    }

    public void setOntvangendePartijId(final Short ontvangendePartijId) {
        this.ontvangendePartijId = ontvangendePartijId;
    }
}
