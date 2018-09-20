/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.datataal.leveringautorisatie;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 */
public final class DienstDsl implements SQLWriter {

    public static final String SECTIE = "Dienst";
    private static final AtomicInteger ID_GEN = new AtomicInteger();
    private final int id = ID_GEN.incrementAndGet();
    private final int srt;
    private final Integer effectafnemerindicaties;
    private final Integer datingang;
    private final Integer dateinde;
    private final Boolean indblok;
    private final String attenderingscriterium;

    /**
     * de parent dienstbundel
     */
    private DienstbundelDsl dienstbundel;

    /**
     * Maakt een nieuwe dienst parser
     *
     * @param sectie de sectie
     */
    DienstDsl(final DslSectie sectie) {
        srt = geefSoortDienst(sectie.geefStringValue("srt"));
        effectafnemerindicaties = geefEffectPlaatsenAfnemerindicatie(sectie.geefStringValue("effectafnemerindicaties"));
        datingang = sectie.geefDatumIntOfDefault("datingang", DatumAttribuut.gisteren());
        dateinde = sectie.geefDatumInt("dateinde");
        indblok = sectie.geefBooleanValue("indblok");
        attenderingscriterium = sectie.geefStringValue("attenderingscriterium");
    }

    public int getId() {
        return id;
    }

    public String getSrt() {
        return SoortDienst.values()[srt].getNaam();
    }

    @Override
    public void toSQL(final Writer writer) throws IOException {
        writer.write(String.format("insert into autaut.dienst (id, dienstbundel, srt, effectafnemerindicaties, " +
                        "datingang, dateinde, indblok, attenderingscriterium) " +
                        "values (%d, %d, %d, %d, %d, %d, %s, %s);%n",
                id, dienstbundel.getId(), srt, effectafnemerindicaties, datingang, dateinde, indblok, DslSectie.quote(attenderingscriterium)));
    }

    /**
     * Geeft soortDienst ID obv soortDienst naam
     * @param value dienst naam
     * @return dienst id
     */
    public static int geefSoortDienst(final String value) {
        final SoortDienst[] values = SoortDienst.values();
        for (int i = 0; i < values.length; i++) {
            final SoortDienst soortDienst = values[i];
            if (value.equals(soortDienst.getNaam())) {
                return i;
            }
        }
        throw new IllegalStateException(value);
    }

    private static Integer geefEffectPlaatsenAfnemerindicatie(final String value) {
        if (value == null) {
            return null;
        }

        final EffectAfnemerindicaties[] values = EffectAfnemerindicaties.values();
        for (int i = 0; i < values.length; i++) {
            final EffectAfnemerindicaties effectAfnemerindicaties = values[i];
            if (effectAfnemerindicaties.getNaam().equals(value)) {
                return i;
            }
        }
        throw new IllegalStateException(String.format("Verkeerde waarde voor effectafnemerindicaties, mogelijke opties %s",
                Arrays.asList(EffectAfnemerindicaties.PLAATSING.getNaam(), EffectAfnemerindicaties.VERWIJDERING.getNaam())));
    }

    /**
     * Parsed de dienst
     * @param dienstbundel de parent dienstbundel
     * @param regelIterator de totale lijst van te parsen regels
     * @return een geparsde dienst
     */
    static DienstDsl parse(final DienstbundelDsl dienstbundel, final ListIterator<DslSectie> regelIterator) {
        final DslSectie dienstSectie = regelIterator.next();
        dienstSectie.assertMetNaam(SECTIE);
        final DienstDsl dienst = new DienstDsl(dienstSectie);
        dienst.dienstbundel = dienstbundel;
        return dienst;
    }
}
