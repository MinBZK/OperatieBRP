/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.versie;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class VersieTest {


    @Test
    public void test() {
        final Versie version = Versie.leesVersie("nl.bzk.brp.algemeen.util", "brp-algemeen-util-logging");
        Assert.assertNotNull(version.getApplicatieVersie());
        Assert.assertNotNull(version.getComponentVersies());

        System.out.println("Applicatie: " + version.toString());
        System.out.println("Componenten:\n" + version.toDetailsString());
    }

    @Test
    public void testUnknown() {
        final Versie version = Versie.leesVersie("nl.bzk.brp.algemeen.util", "brp-algemeen-util-BLABLABLA");
        Assert.assertNotNull(version.getApplicatieVersie().getVersie());
    }

    @Test
    public void testVersieRegel() {
    	Versie.VersieRegel regel = new Versie.VersieRegel("group1", "artifact1", "name1", "versie1", "build1", "revision1" );
    	Assert.assertEquals("group1", regel.getGroup());
    	Assert.assertEquals("artifact1", regel.getArtifact());
    	Assert.assertEquals("name1", regel.getName());
    	Assert.assertEquals("versie1", regel.getVersie());
    	Assert.assertEquals("revision1", regel.getRevision());
    	Assert.assertEquals("build1", regel.getBuild());

    	Versie.VersieRegel regel1 = new Versie.VersieRegel("group1", "artifact1", "name1", "versie1", "build1", "revision1" );
    	Versie.VersieRegel regel2 = new Versie.VersieRegel("group2", "artifact1", "name1", "versie1", "build1", "revision1" );
    	Versie.VersieRegel regel3 = new Versie.VersieRegel("group1", "artifact2", "name1", "versie1", "build1", "revision1" );
    	Versie.VersieRegel regel4 = new Versie.VersieRegel("group1", "artifact1", "name2", "versie1", "build1", "revision1" );
    	Versie.VersieRegel regel5= new Versie.VersieRegel("group1", "artifact1", "name1", "versie2", "build1", "revision1" );
    	Versie.VersieRegel regel6 = new Versie.VersieRegel("group1", "artifact1", "name1", "versie1", "build1", "revision2" );
    	Versie.VersieRegel regel7 = new Versie.VersieRegel("group1", "artifact1", "name1", "versie1", "build2", "revision1" );

    	List<Versie.VersieRegel> lijst = new ArrayList<>();
    	lijst.add(regel);
    	lijst.add(regel1);
    	lijst.add(regel2);
    	lijst.add(regel3);
    	lijst.add(regel4);
    	lijst.add(regel5);
    	lijst.add(regel6);
    	lijst.add(regel7);
        Collections.sort(lijst, new Versie.VersieRegelComparator());


    }
}
