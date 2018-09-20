/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import org.springframework.test.util.ReflectionTestUtils;

/**
 */
public class TestDienstbundelGroepBuilder {

    private Dienstbundel   dienstbundel;
    private Element        groep;
    private JaNeeAttribuut indicatieFormeleHistorie;
    private JaNeeAttribuut indicatieMaterieleHistorie;
    private JaNeeAttribuut indicatieVerantwoording;

    private Set<DienstbundelGroepAttribuutImpl> attributen;

    private TestDienstbundelGroepBuilder() {

    }

    public static TestDienstbundelGroepBuilder maker() {
        return new TestDienstbundelGroepBuilder();
    }

    public TestDienstbundelGroepBuilder metGroep(Element groep) {
        this.groep = groep;
        return this;
    }

    public TestDienstbundelGroepBuilder metIndicatieFormeleHistorie(boolean indicatieFormeleHistorie) {
        this.indicatieFormeleHistorie = new JaNeeAttribuut(indicatieFormeleHistorie);
        return this;
    }

    public TestDienstbundelGroepBuilder metIndicatieMaterieleHistorie(boolean indicatieMaterieleHistorie) {
        this.indicatieMaterieleHistorie = new JaNeeAttribuut(indicatieMaterieleHistorie);
        return this;
    }

    public TestDienstbundelGroepBuilder metIndicatieVerantwoording(boolean indicatieVerantwoording) {
        this.indicatieVerantwoording = new JaNeeAttribuut(indicatieVerantwoording);
        return this;
    }

    public TestDienstbundelGroepBuilder metAttributen(DienstbundelGroepAttribuutImpl ... attributen) {
        this.attributen = new HashSet<>(Arrays.asList(attributen));
        return this;
    }

    public DienstbundelGroep maak() {
        final DienstbundelGroep dienstbundelGroep = new DienstbundelGroep(dienstbundel, groep, indicatieFormeleHistorie, indicatieMaterieleHistorie,
            indicatieVerantwoording);

        ReflectionTestUtils.setField(dienstbundelGroep, "attributen", attributen);
        if (attributen != null && !attributen.isEmpty()) {
            for (DienstbundelGroepAttribuutImpl attribuut : attributen) {
                ReflectionTestUtils.setField(attribuut, "dienstbundelGroep", dienstbundelGroep);
            }
        }

        if (indicatieFormeleHistorie != null) {
            ReflectionTestUtils.setField(dienstbundelGroep, "indicatieFormeleHistorie", indicatieFormeleHistorie);
        }
        if (indicatieMaterieleHistorie != null) {
            ReflectionTestUtils.setField(dienstbundelGroep, "indicatieMaterieleHistorie", indicatieMaterieleHistorie);
        }
        if (indicatieVerantwoording != null) {
            ReflectionTestUtils.setField(dienstbundelGroep, "indicatieVerantwoording", indicatieVerantwoording);
        }
        return dienstbundelGroep;
    }
}
