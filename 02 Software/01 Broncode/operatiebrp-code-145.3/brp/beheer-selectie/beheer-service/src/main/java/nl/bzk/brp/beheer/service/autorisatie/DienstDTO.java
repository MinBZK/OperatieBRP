/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.autorisatie;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Optional;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.util.common.DatumUtil;

/**
 * DTO voor diensten.
 */
@JsonAutoDetect
public class DienstDTO {

    private Integer id;
    private String selectiecriterium;
    private Integer historievormSelectie;
    private LocalDate selectiePeilmomentMaterieelResultaat;
    private ZonedDateTime selectiePeilmomentFormeelResultaat;
    private Integer maxAantalPersonenPerSelectiebestand;
    private Integer maxGrootteSelectiebestand;
    private Integer leverwijzeSelectie;
    private Boolean verzendVolledigBerichtBijWijzigingAfnemerindicatie;
    private Boolean selectieresultaatControleren;

    DienstDTO(Dienst dienst) {
        this.id = dienst.getId();
        this.selectiecriterium = dienst.getNadereSelectieCriterium();
        this.historievormSelectie = dienst.getHistorievormSelectie();
        this.selectiePeilmomentMaterieelResultaat =
                Optional.ofNullable(dienst.getSelectiePeilmomentMaterieelResultaat()).map(DatumUtil::vanIntegerNaarLocalDate).orElse(null);
        this.selectiePeilmomentFormeelResultaat =
                Optional.ofNullable(dienst.getSelectiePeilmomentFormeelResultaat()).map(DatumUtil::vanTimestampNaarZonedDateTime).orElse(null);
        this.maxAantalPersonenPerSelectiebestand = dienst.getMaxAantalPersonenPerSelectiebestand();
        this.maxGrootteSelectiebestand = dienst.getMaxGrootteSelectiebestand();
        this.leverwijzeSelectie = dienst.getLeverwijzeSelectie();
        this.verzendVolledigBerichtBijWijzigingAfnemerindicatie = dienst.getIndVerzVolBerBijWijzAfniNaSelectie();
        this.selectieresultaatControleren = dienst.getIndicatieSelectieresultaatControleren();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHistorievormSelectie() {
        return historievormSelectie;
    }

    public void setHistorievormSelectie(Integer historievormSelectie) {
        this.historievormSelectie = historievormSelectie;
    }

    public LocalDate getSelectiePeilmomentMaterieelResultaat() {
        return selectiePeilmomentMaterieelResultaat;
    }

    public void setSelectiePeilmomentMaterieelResultaat(LocalDate selectiePeilmomentMaterieelResultaat) {
        this.selectiePeilmomentMaterieelResultaat = selectiePeilmomentMaterieelResultaat;
    }

    public ZonedDateTime getSelectiePeilmomentFormeelResultaat() {
        return selectiePeilmomentFormeelResultaat;
    }

    public void setSelectiePeilmomentFormeelResultaat(ZonedDateTime selectiePeilmomentFormeelResultaat) {
        this.selectiePeilmomentFormeelResultaat = selectiePeilmomentFormeelResultaat;
    }

    public String getSelectiecriterium() {
        return selectiecriterium;
    }

    public void setSelectiecriterium(String selectiecriterium) {
        this.selectiecriterium = selectiecriterium;
    }

    public Integer getMaxAantalPersonenPerSelectiebestand() {
        return maxAantalPersonenPerSelectiebestand;
    }

    public void setMaxAantalPersonenPerSelectiebestand(Integer maxAantalPersonenPerSelectiebestand) {
        this.maxAantalPersonenPerSelectiebestand = maxAantalPersonenPerSelectiebestand;
    }

    public Integer getLeverwijzeSelectie() {
        return leverwijzeSelectie;
    }

    public void setLeverwijzeSelectie(Integer leverwijzeSelectie) {
        this.leverwijzeSelectie = leverwijzeSelectie;
    }

    public Boolean getVerzendVolledigBerichtBijWijzigingAfnemerindicatie() {
        return verzendVolledigBerichtBijWijzigingAfnemerindicatie;
    }

    public void setVerzendVolledigBerichtBijWijzigingAfnemerindicatie(Boolean verzendVolledigBerichtBijWijzigingAfnemerindicatie) {
        this.verzendVolledigBerichtBijWijzigingAfnemerindicatie = verzendVolledigBerichtBijWijzigingAfnemerindicatie;
    }

    public Boolean getSelectieresultaatControleren() {
        return selectieresultaatControleren;
    }

    public void setSelectieresultaatControleren(Boolean selectieresultaatControleren) {
        this.selectieresultaatControleren = selectieresultaatControleren;
    }
}
