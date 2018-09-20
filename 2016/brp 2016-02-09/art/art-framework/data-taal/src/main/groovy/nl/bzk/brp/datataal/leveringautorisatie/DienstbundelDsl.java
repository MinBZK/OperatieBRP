/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.datataal.leveringautorisatie;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 */
public final class DienstbundelDsl implements SQLWriter {

    private static final AtomicInteger ID_GEN = new AtomicInteger();
    static final String SECTIE = "DienstenBundel";

    private final DslSectie sectie;

    private final int id = ID_GEN.incrementAndGet();
    private final String naam;
    private final Integer datingang;
    private final Integer dateinde;
    private final String naderepopulatiebeperking;
    private final Boolean indnaderepopbeperkingvolconv;
    private final String toelichting;
    private final Boolean indblok;

    private final List<DienstbundelGroepDsl> groepen;
    private final List<DienstbundelGroepAttrDsl> attributen;
    private final String lo3Rubrieken;

    private List<DienstDsl> diensten = new LinkedList<>();
    private LeveringautorisatieDsl leveringautorisatie;

    DienstbundelDsl(final DslSectie sectie) {
        this.sectie = sectie;
        naam = sectie.geefStringValue("naam");
        datingang = sectie.geefDatumIntOfDefault("datingang", DatumAttribuut.gisteren());
        dateinde = sectie.geefDatumInt("dateinde");
        naderepopulatiebeperking = sectie.geefStringValue("naderepopulatiebeperking");
        indnaderepopbeperkingvolconv = sectie.geefBooleanValue("indnaderepopbeperkingvolconv");
        toelichting = sectie.geefStringValue("toelichting");
        indblok = sectie.geefBooleanValue("indblok");

        groepen = DienstbundelGroepDsl.parse(this, sectie.geefStringValue("Geautoriseerde Groepen"));
        attributen = DienstbundelGroepAttrDsl.parse(this, sectie.geefStringValue("Geautoriseerde attributen"));
        lo3Rubrieken = sectie.geefStringValue("LO3Rubrieken");

    }

    @Override
    public void toSQL(final Writer writer) throws IOException {

        writer.write(String.format("insert into autaut.dienstbundel (id, levsautorisatie, naam, datingang, dateinde, " +
                        "naderepopulatiebeperking, indnaderepopbeperkingvolconv, toelichting, indblok) " +
                        "values (%d, %d, %s, %d, %d, %s, %s, %s, %s);%n",
                id, leveringautorisatie.getId(), DslSectie.quote(naam), datingang, dateinde, DslSectie.quote(naderepopulatiebeperking),
                indnaderepopbeperkingvolconv, DslSectie.quote(toelichting), indblok));

        for (DienstDsl dienstDsl : diensten) {
            dienstDsl.toSQL(writer);
        }
        for (DienstbundelGroepDsl dienstbundelGroepDsl : groepen) {
            dienstbundelGroepDsl.toSQL(writer);
        }
        for (DienstbundelGroepAttrDsl attr : attributen) {
            attr.toSQL(writer);
        }

        //voor nu supported we enkel het toevoegen van alle lo3rubrieken
        if ("*".equals(lo3Rubrieken)) {
            writer.write(String.format("insert into autaut.dienstbundello3rubriek (dienstbundel, rubr) select %d, id from conv.convlo3rubriek;%n",
                id));
        }
    }

    public int getId() {
        return id;
    }

    public List<DienstbundelGroepDsl> getGroepen() {
        return groepen;
    }

    public List<DienstbundelGroepAttrDsl> getAttributen() {
        return attributen;
    }

    public String getNaam() {
        return naam;
    }

    public List<DienstDsl> getDiensten() {
        return diensten;
    }

    /**
     * Parsed de dienstbundel
     * @param leveringautorisatie de parent leveringautorisatie
     * @param regelIterator de totale lijst van te parsen regels
     * @return een geparsde dienstbundel
     */
    static DienstbundelDsl parse(final LeveringautorisatieDsl leveringautorisatie,
                                        final ListIterator<DslSectie> regelIterator) {
        final DslSectie dienstbundelSectie = regelIterator.next();
        dienstbundelSectie.assertMetNaam(SECTIE);
        final DienstbundelDsl dienstbundel = new DienstbundelDsl(dienstbundelSectie);
        dienstbundel.leveringautorisatie = leveringautorisatie;
        while (regelIterator.hasNext()) {
            final DslSectie dienstSectie = regelIterator.next();
            if (!DienstDsl.SECTIE.equals(dienstSectie.getSectieNaam())) {
                regelIterator.previous();
                break;
            }
            dienstSectie.assertMetNaam(DienstDsl.SECTIE);
            regelIterator.previous();
            final DienstDsl dienst = DienstDsl.parse(dienstbundel, regelIterator);
            dienstbundel.diensten.add(dienst);
        }
        return dienstbundel;
    }
}
