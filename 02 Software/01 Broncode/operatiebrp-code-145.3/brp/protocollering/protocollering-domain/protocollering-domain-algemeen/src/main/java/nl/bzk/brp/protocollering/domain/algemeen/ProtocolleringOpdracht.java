/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.domain.algemeen;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;


/**
 * ProtocolleringOpdracht.
 */
@JsonAutoDetect
public final class ProtocolleringOpdracht {

    private Integer toegangLeveringsautorisatieId;
    private Integer dienstId;
    private ZonedDateTime datumTijdKlaarzettenLevering;
    private Integer datumAanvangMaterielePeriodeResultaat;
    private Integer datumEindeMaterielePeriodeResultaat;
    private ZonedDateTime datumTijdAanvangFormelePeriodeResultaat;
    private ZonedDateTime datumTijdEindeFormelePeriodeResultaat;
    private Long administratieveHandelingId;
    private SoortSynchronisatie soortSynchronisatie;
    private SoortDienst soortDienst;
    private HistorieVorm historievorm;
    private Set<Integer> scopingAttributen;
    private List<LeveringPersoon> geleverdePersonen;

    /**
     * Default constructor nodig voor JSON de-serialisatie.
     */
    public ProtocolleringOpdracht() {
        //public constructor v json deserialisatie
    }

    /**
     * @return toegangLeveringsautorisatieId
     */
    public Integer getToegangLeveringsautorisatieId() {
        return toegangLeveringsautorisatieId;
    }

    /**
     * @param toegangLeveringsautorisatieId toegangLeveringsautorisatieId
     */
    public void setToegangLeveringsautorisatieId(Integer toegangLeveringsautorisatieId) {
        this.toegangLeveringsautorisatieId = toegangLeveringsautorisatieId;
    }

    /**
     * @return dienstId
     */
    public Integer getDienstId() {
        return dienstId;
    }

    /**
     * @param dienstId dienstId
     */
    public void setDienstId(Integer dienstId) {
        this.dienstId = dienstId;
    }

    /**
     * @return datumTijdKlaarzettenLevering
     */
    public ZonedDateTime getDatumTijdKlaarzettenLevering() {
        return datumTijdKlaarzettenLevering;
    }

    /**
     * @param datumTijdKlaarzettenLevering datumTijdKlaarzettenLevering
     */
    public void setDatumTijdKlaarzettenLevering(ZonedDateTime datumTijdKlaarzettenLevering) {
        this.datumTijdKlaarzettenLevering = datumTijdKlaarzettenLevering;
    }

    /**
     * @return datumAanvangMaterielePeriodeResultaat
     */
    public Integer getDatumAanvangMaterielePeriodeResultaat() {
        return datumAanvangMaterielePeriodeResultaat;
    }

    /**
     * @param datumAanvangMaterielePeriodeResultaat datumAanvangMaterielePeriodeResultaat
     */
    public void setDatumAanvangMaterielePeriodeResultaat(Integer datumAanvangMaterielePeriodeResultaat) {
        this.datumAanvangMaterielePeriodeResultaat = datumAanvangMaterielePeriodeResultaat;
    }

    /**
     * @return datumEindeMaterielePeriodeResultaat
     */
    public Integer getDatumEindeMaterielePeriodeResultaat() {
        return datumEindeMaterielePeriodeResultaat;
    }

    /**
     * @param datumEindeMaterielePeriodeResultaat datumEindeMaterielePeriodeResultaat
     */
    public void setDatumEindeMaterielePeriodeResultaat(Integer datumEindeMaterielePeriodeResultaat) {
        this.datumEindeMaterielePeriodeResultaat = datumEindeMaterielePeriodeResultaat;
    }

    /**
     * @return datumTijdAanvangFormelePeriodeResultaat
     */
    public ZonedDateTime getDatumTijdAanvangFormelePeriodeResultaat() {
        return datumTijdAanvangFormelePeriodeResultaat;
    }

    /**
     * @param datumTijdAanvangFormelePeriodeResultaat datumTijdAanvangFormelePeriodeResultaat
     */
    public void setDatumTijdAanvangFormelePeriodeResultaat(ZonedDateTime datumTijdAanvangFormelePeriodeResultaat) {
        this.datumTijdAanvangFormelePeriodeResultaat = datumTijdAanvangFormelePeriodeResultaat;
    }

    /**
     * @return datumTijdEindeFormelePeriodeResultaat
     */
    public ZonedDateTime getDatumTijdEindeFormelePeriodeResultaat() {
        return datumTijdEindeFormelePeriodeResultaat;
    }

    /**
     * @param datumTijdEindeFormelePeriodeResultaat datumTijdEindeFormelePeriodeResultaat
     */
    public void setDatumTijdEindeFormelePeriodeResultaat(ZonedDateTime datumTijdEindeFormelePeriodeResultaat) {
        this.datumTijdEindeFormelePeriodeResultaat = datumTijdEindeFormelePeriodeResultaat;
    }

    /**
     * @return administratieveHandelingId
     */
    public Long getAdministratieveHandelingId() {
        return administratieveHandelingId;
    }

    /**
     * @param administratieveHandelingId administratieveHandelingId
     */
    public void setAdministratieveHandelingId(Long administratieveHandelingId) {
        this.administratieveHandelingId = administratieveHandelingId;
    }

    /**
     * @return soortSynchronisatie
     */
    public SoortSynchronisatie getSoortSynchronisatie() {
        return soortSynchronisatie;
    }

    /**
     * @param soortSynchronisatie soortSynchronisatie
     */
    public void setSoortSynchronisatie(SoortSynchronisatie soortSynchronisatie) {
        this.soortSynchronisatie = soortSynchronisatie;
    }

    /**
     * @return soortDienst
     */
    public SoortDienst getSoortDienst() {
        return soortDienst;
    }

    /**
     * @param soortDienst soortDienst
     */
    public void setSoortDienst(SoortDienst soortDienst) {
        this.soortDienst = soortDienst;
    }

    /**
     * @return historievorm
     */
    public HistorieVorm getHistorievorm() {
        return historievorm;
    }

    /**
     * @param historievorm historievorm
     */
    public void setHistorievorm(HistorieVorm historievorm) {
        this.historievorm = historievorm;
    }

    /**
     * @return scopingAttributen
     */
    public Set<Integer> getScopingAttributen() {
        return scopingAttributen;
    }

    /**
     * @param scopingAttributen scopingAttributen
     */
    public void setScopingAttributen(Set<Integer> scopingAttributen) {
        this.scopingAttributen = scopingAttributen;
    }

    /**
     * @return geleverdePersonen
     */
    public List<LeveringPersoon> getGeleverdePersonen() {
        return geleverdePersonen;
    }

    /**
     * @param geleverdePersonen geleverdePersonen
     */
    public void setGeleverdePersonen(List<LeveringPersoon> geleverdePersonen) {
        this.geleverdePersonen = geleverdePersonen;
    }
}
