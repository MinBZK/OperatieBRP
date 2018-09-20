/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.domain.kern;

import java.util.Date;

import nl.bzk.brp.testdatageneratie.utils.RandomUtil;




public class His implements java.io.Serializable {

    public final static int CORRECTIE_FRACTIE = 20;

    private long id;
    private Long actieByActieinh = RandomUtil.getActie();
    private Long actieByActieverval;
    private Date tsreg = RandomUtil.getPastTimestamp();
    private Date tsverval;

    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Long getActieByActieinh() {
        return this.actieByActieinh;
    }

    public void setActieByActieinh(final Long actieByActieinh) {
        this.actieByActieinh = actieByActieinh;
    }

    public Long getActieByActieverval() {
        return this.actieByActieverval;
    }

    public void setActieByActieverval(final Long actieByActieverval) {
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

}
