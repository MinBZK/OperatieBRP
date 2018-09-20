/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.domain.kern;

import java.util.Date;

import nl.bzk.brp.testdatageneratie.RandomService;




public class His implements java.io.Serializable {

    private long id;
    private Actie actieByActieinh;
    private Actie actieByActieverval;
    private Date tsreg = RandomService.getPastTimestamp();
    private Date tsverval;
    private Integer dataanvgel = RandomService.randomDate();
    private Integer dateindegel;

    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Actie getActieByActieinh() {
        return this.actieByActieinh;
    }

    public void setActieByActieinh(final Actie actieByActieinh) {
        this.actieByActieinh = actieByActieinh;
    }

    public Actie getActieByActieverval() {
        return this.actieByActieverval;
    }

    public void setActieByActieverval(final Actie actieByActieverval) {
        this.actieByActieverval = actieByActieverval;
    }

    public Date getTsreg() {
        return this.tsreg;
    }

    public void setTsreg(final Date tsreg) {
        this.tsreg = tsreg;
    }

    public Date getTsverval() {
        return this.tsverval;
    }

    public void setTsverval(final Date tsverval) {
        this.tsverval = tsverval;
    }

    public Integer getDataanvgel() {
        return this.dataanvgel;
    }

    public void setDataanvgel(final Integer dataanvgel) {
        this.dataanvgel = dataanvgel;
    }

    public Integer getDateindegel() {
        return this.dateindegel;
    }

    public void setDateindegel(final Integer dateindegel) {
        this.dateindegel = dateindegel;
    }

}
