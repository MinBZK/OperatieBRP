/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.domain.kern;

import java.util.Date;

public interface His {

    public final static int CORRECTIE_FRACTIE = 20;

    public Long getActieinh();
    public void setActieinh(final Long actieinh);
    public Long getActieverval();
    public void setActieverval(final Long actieverval);
    public Date getTsreg();
    public void setTsreg(final Date tsreg);
    public Date getTsverval();
    public void setTsverval(final Date tsverval);

//    public Long getActieinh() {
//        return actieinh;
//    }
//
//    public void setActieinh(final Long actieinh) {
//        this.actieinh = actieinh;
//    }
//
//    public Long getActieverval() {
//        return actieverval;
//    }
//
//    public void setActieverval(final Long actieverval) {
//        this.actieverval = actieverval;
//    }
//
//    public Date getTsreg() {
//        return tsreg;
//    }
//
//    public void setTsreg(final Date tsreg) {
//        this.tsreg = tsreg;
//    }
//
//    public Date getTsverval() {
//        return tsverval;
//    }
//
//    public void setTsverval(final Date tsverval) {
//        this.tsverval = tsverval;
//    }

}
