/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.domain.kern;

import java.util.Date;

import nl.bzk.brp.testdatageneratie.utils.RandomUtil;




public class His implements java.io.Serializable {

    private static final long serialVersionUID = 7595383775497530904L;

    public final static int CORRECTIE_FRACTIE = 20;

    private Integer id;
    private Long actieinh = RandomUtil.getActie();
    private Long actieverval;
    private Date tsreg = RandomUtil.getPastTimestamp();
    private Date tsverval;

    public final Integer getId() {
        return this.id;
    }

    public final void setId(final Integer id) {
        this.id = id;
    }

    public final Long getActieinh() {
        return this.actieinh;
    }

    public final void setActieinh(final Long actieinh) {
        this.actieinh = actieinh;
    }

    public final Long getActieverval() {
        return this.actieverval;
    }

    public final void setActieverval(final Long actieverval) {
        this.actieverval = actieverval;
    }

    public final Date getTsreg() {
        return this.tsreg;
    }

    public final void setTsreg(final Date tsreg) {
        this.tsreg = tsreg;
    }

    public final Date getTsverval() {
        return this.tsverval;
    }

    public final void setTsverval(final Date tsverval) {
        this.tsverval = tsverval;
    }

}
