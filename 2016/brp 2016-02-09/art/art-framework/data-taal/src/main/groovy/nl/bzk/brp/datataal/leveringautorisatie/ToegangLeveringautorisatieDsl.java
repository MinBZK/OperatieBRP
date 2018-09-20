/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.datataal.leveringautorisatie;

import java.io.IOException;
import java.io.Writer;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang3.StringUtils;

/**
 */
public final class ToegangLeveringautorisatieDsl extends AbstractToegangautorisatieDsl implements SQLWriter {

    public static final         String        SECTIE = "Toegang Levering autorisatie";
    private static final AtomicInteger ID_GEN = new AtomicInteger();

    private final                int           id     = ID_GEN.incrementAndGet();
    private LeveringautorisatieDsl leveringautorisatie;
    private final String naderepopulatiebeperking;
    private final String afleverpunt;
    private final Integer rol;

    /**
     * @param sectie de sectie
     */
    public ToegangLeveringautorisatieDsl(final DslSectie sectie) {
        super(sectie);
        naderepopulatiebeperking = sectie.geefStringValue("naderepopulatiebeperking");
        afleverpunt = StringUtils.defaultString(sectie.geefStringValue("afleverpunt"), "<afleverpunt>");
        rol = sectie.geefIntegerOfDefault("rol", 1);
    }

    @Override
    public void toSQL(final Writer writer) throws IOException {
        writer.write(
                String.format("insert into autaut.toeganglevsautorisatie (id, geautoriseerde, levsautorisatie, " +
                                "ondertekenaar, transporteur, datingang, dateinde, naderepopulatiebeperking, " +
                                "afleverpunt, indblok) "
                                + "values (%d, (select id from kern.partijrol where partij = (select id from kern.partij where naam = %s) and rol=%d), %d, "
                                + "(select id from kern.partij where oin = %s), "
                                + "(select id from kern.partij where oin = %s), %d, %d, %s, %s, %s);%n",
                        id, DslSectie.quote(getGeautoriseerde()), getRol(), leveringautorisatie.getId(), DslSectie.quote(getOndertekenaar()), DslSectie.quote(getTransporteur()),
                        getDatingang(), getDateinde(), DslSectie.quote(naderepopulatiebeperking), DslSectie.quote(afleverpunt), isIndblok()));
    }

    /**
     * @return het id van de toegangleveringsautorisatie
     */
    @Override
    public int getId() {
        return id;
    }

    public LeveringautorisatieDsl getLeveringautorisatie() {
        return leveringautorisatie;
    }

    /**
     * Parsed de toegangleveringautorisatie
     * @param sectieIterator de sectie iterator
     * @return
     */
    public static ToegangLeveringautorisatieDsl parse(final ListIterator<DslSectie> sectieIterator) {
        final DslSectie sectie = sectieIterator.next();
        sectie.assertMetNaam(SECTIE);
        return new ToegangLeveringautorisatieDsl(sectie);
    }

    public String getNaderepopulatiebeperking() {
        return naderepopulatiebeperking;
    }

    public String getAfleverpunt() {
        return afleverpunt;
    }

    public void setLeveringautorisatie(final LeveringautorisatieDsl leveringautorisatie) {
        this.leveringautorisatie = leveringautorisatie;
    }

    public Integer getRol() {
        return rol;
    }
}
