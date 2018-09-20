/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.domain.kern;

import nl.bzk.brp.testdatageneratie.utils.RandomUtil;

public class HisMaterieel extends His {

    private static final long serialVersionUID = 6000779666533351348L;

    private Long actieByActieaanpgel;
    private Integer dataanvgel = RandomUtil.randomDate();
    private Integer dateindegel;

    public final Long getActieByActieaanpgel() {
        return actieByActieaanpgel;
    }

    public final void setActieByActieaanpgel(final Long actieByActieaanpgel) {
        this.actieByActieaanpgel = actieByActieaanpgel;
    }

    public final Integer getDataanvgel() {
        return this.dataanvgel;
    }

    public final void setDataanvgel(final Integer dataanvgel) {
        this.dataanvgel = dataanvgel;
    }

    public final Integer getDateindegel() {
        return this.dateindegel;
    }

    public final void setDateindegel(final Integer dateindegel) {
        this.dateindegel = dateindegel;
        actieByActieaanpgel = RandomUtil.getActie();
    }

}
