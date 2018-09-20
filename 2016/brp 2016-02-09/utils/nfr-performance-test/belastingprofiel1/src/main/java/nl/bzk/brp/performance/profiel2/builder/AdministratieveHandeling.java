/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.performance.profiel2.builder;

import java.io.IOException;

/**
 */
public class AdministratieveHandeling implements SqlAppender {

    final long id                       = Globals.HANDELING_ID.incrementAndGet();
    final long afgeleidAdministratiefId = Globals.AFGELEID_ADMINISTRATIEF_ID.incrementAndGet();

    public int                      index;
    public AdministratieveHandeling vorigeHandeling;
    public AdministratieveHandeling volgendeHandeling;
    public Persoon                  persoon;
    public Actie                    actie;

    public AdministratieveHandeling(Persoon persoon) {
        this.persoon = persoon;
        actie = new Actie(this);
    }

    public String getTsReg() {
        return "date '2001-10-01'" + " + " + index;
    }

    public long getActieInhoud() {
        return actie.id;
    }

    public Long getActieVerval() {
        return volgendeHandeling != null ? volgendeHandeling.actie.id : null;
    }

    public String getTsVerval() {
        return volgendeHandeling != null ? volgendeHandeling.getTsReg() : null;
    }

    public AdministratieveHandeling maakNieuweHandeling() {
        final AdministratieveHandeling administratieveHandeling = new AdministratieveHandeling(persoon);
        administratieveHandeling.index = index + 1;
        administratieveHandeling.vorigeHandeling = this;
        this.volgendeHandeling = administratieveHandeling;
        Globals.HANDELINGEN.set(administratieveHandeling);
        return administratieveHandeling;
    }

    public void schrijf() throws IOException {
        Globals.WRITERS.get().admhnd.write(String.format("insert into kern.admhnd (id, srt, partij, tsreg) values (%d, 1, 1, date '2001-10-01');%n", id));
        actie.schrijf();
    }

    public void schrijfAfgeleidAdministratief() throws IOException {
//
//        Long actieVerval = null;
//        String tijdstipVerval = null;
//        if (volgendeHandeling != null) {
//            actieVerval = volgendeHandeling.actie.id;
//            tijdstipVerval = "date '2001-10-01'";
//        }

        Globals.WRITERS.get().his_persafgeleidadministrati.write(String.format("insert into kern.his_persafgeleidadministrati "
                + "(id, pers, tsreg, tsverval, actieinh, actieverval, admhnd, tslaatstewijz, sorteervolgorde, indonverwbijhvoorstelnieting) values (%d, %d, %s, %s, %d, %d, %d, date '2001-10-01', 1, 'f');%n",
            afgeleidAdministratiefId, persoon.id, getTsReg(), getTsVerval(), actie.id, getActieVerval(), id));

    }

}
