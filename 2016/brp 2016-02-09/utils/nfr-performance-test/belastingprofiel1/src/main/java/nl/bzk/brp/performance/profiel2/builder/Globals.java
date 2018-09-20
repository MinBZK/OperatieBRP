/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.performance.profiel2.builder;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

/**
 */
public class Globals {

    public static AtomicLong AFGELEID_ADMINISTRATIEF_ID = new AtomicLong();
    public static AtomicLong PERSOON_ID                 = new AtomicLong();
    public static AtomicLong HANDELING_ID               = new AtomicLong();
    public static AtomicLong ACTIE_ID                   = new AtomicLong();
    public static AtomicLong ADRES_ID                   = new AtomicLong();
    public static AtomicLong HIS_ADRES_ID               = new AtomicLong();
    public static AtomicLong BSN                        = new AtomicLong();
    public static AtomicLong RELATIE_ID                 = new AtomicLong();
    public static AtomicLong HIS_RELATIE_ID             = new AtomicLong();
    public static AtomicLong BETROKKENHEID_ID           = new AtomicLong();
    public static AtomicLong HIS_BETROKKENHEID_ID       = new AtomicLong();
    public static AtomicLong AFNEMERINDICATIE_ID        = new AtomicLong();
    public static AtomicLong HIS_AFNEMERINDICATIE_ID    = new AtomicLong();

    public static ThreadLocal<Writers>                  WRITERS     = new ThreadLocal<>();
    public static ThreadLocal<AdministratieveHandeling> HANDELINGEN = new ThreadLocal<>();
    public static ThreadLocal<Queue<Persoon>>           PERSONEN    = new ThreadLocal<Queue<Persoon>>() {
        @Override
        protected Queue<Persoon> initialValue() {
            return new LinkedList<>();
        }
    };


}
