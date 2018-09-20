/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.datataal.leveringautorisatie;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;

import static nl.bzk.brp.datataal.leveringautorisatie.DslSectie.quote;

/**
 */
public final class LeveringautorisatieDsl implements SQLWriter {

    private static final AtomicInteger ID_GEN = new AtomicInteger();
    static final String SECTIE = "Levering autorisatie";

    private final int id = ID_GEN.incrementAndGet();
    private final int stelsel;
    private final boolean indmodelautorisatie;
    private final String naam;
    private final Integer protocolleringsniveau;
    private final Boolean indaliassrtadmhndleveren;
    private final Integer datingang;
    private final Integer dateinde;
    private final String populatiebeperking;
    private final Boolean indpopbeperkingvolconv;
    private final String toelichting;
    private final Boolean indblok;

    private List<DienstbundelDsl> dienstBundels = new LinkedList<>();

    /**
     * Maakt een leveringautorisatie obv de gegeven sectie
     * @param sectie een sectie
     */
    public LeveringautorisatieDsl(final DslSectie sectie) {


        if (sectie.heeftRegel("Stelsel")) {
            final Stelsel stelselEnum = Stelsel.valueOf(sectie.geefStringValue("Stelsel"));
            switch (stelselEnum) {
                case BRP:
                    stelsel = 1;
                    break;
                case GBA:
                    stelsel = 2;
                    break;
                default:
                    throw new IllegalArgumentException("Ongeldig stelsel");
            }
        } else {
            stelsel = 1; //default BRP
        }
        indmodelautorisatie = true;
        naam = sectie.geefStringValue("naam");
        protocolleringsniveau = sectie.geefInteger("protocolleringsniveau");
        indaliassrtadmhndleveren = sectie.geefBooleanValue("indaliassrtadmhndleveren");
        datingang = sectie.geefDatumIntOfDefault("datingang", DatumAttribuut.gisteren());
        dateinde = sectie.geefDatumInt("dateinde");
        populatiebeperking = sectie.geefStringValue("populatiebeperking");
        indpopbeperkingvolconv = sectie.geefBooleanValue("indpopbeperkingvolconv");
        toelichting = sectie.geefStringValue("toelichting");
        indblok = sectie.geefBooleanValue("indblok");

    }

    /**
     * Schrijft de leveringautorisatie SQL
     *
     * @param writer de writer om naartoe te schrijven
     * @throws IOException als het schrijven mislukt
     */
    public void toSQL(final Writer writer) throws IOException {
        writer.write(String.format("insert into autaut.levsautorisatie " +
                        "(id, stelsel, indmodelautorisatie, naam, protocolleringsniveau, indaliassrtadmhndleveren, " +
                        "datingang, dateinde, populatiebeperking, indpopbeperkingvolconv, toelichting, indblok) " +
                        "values (%d, %d, %s, %s, %d, %s, %d, %d, %s, %s, %s, %s);%n",
                id, stelsel, indmodelautorisatie, quote(naam), protocolleringsniveau, indaliassrtadmhndleveren, datingang,
                dateinde, quote(populatiebeperking), indpopbeperkingvolconv, quote(toelichting), indblok));
        for (final DienstbundelDsl dienstBundel : dienstBundels) {
            dienstBundel.toSQL(writer);
        }
    }

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public List<DienstbundelDsl> getDienstBundels() {
        return Collections.unmodifiableList(dienstBundels);
    }

    /**
     * Parsed de leveringautorisatie
     * @param regelIterator de totale lijst van te parsen regels
     * @return een geparsde dienst
     */
    static LeveringautorisatieDsl parse(final ListIterator<DslSectie> regelIterator) {
        final DslSectie sectie = regelIterator.next();
        sectie.assertMetNaam(SECTIE);
        final LeveringautorisatieDsl leveringautorisatie = new LeveringautorisatieDsl(sectie);
        while (regelIterator.hasNext()) {
            final DslSectie next = regelIterator.next();
            next.assertMetNaam(DienstbundelDsl.SECTIE);
            regelIterator.previous();
            final DienstbundelDsl dienstbundel = DienstbundelDsl.parse(leveringautorisatie, regelIterator);
            leveringautorisatie.dienstBundels.add(dienstbundel);
        }
        return leveringautorisatie;
    }
}
