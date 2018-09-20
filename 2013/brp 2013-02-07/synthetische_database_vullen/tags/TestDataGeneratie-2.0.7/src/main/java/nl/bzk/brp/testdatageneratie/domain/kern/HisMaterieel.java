/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.domain.kern;

import nl.bzk.brp.testdatageneratie.RandomService;

public class HisMaterieel extends His {

    private Long actieByActieaanpgel;
    private Integer dataanvgel = RandomService.randomDate();
    private Integer dateindegel;

    public Long getActieByActieaanpgel() {
        return actieByActieaanpgel;
    }

    public void setActieByActieaanpgel(final Long actieByActieaanpgel) {
        this.actieByActieaanpgel = actieByActieaanpgel;
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
        actieByActieaanpgel = RandomService.getActie();
    }

}
