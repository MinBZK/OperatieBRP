/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import nl.bzk.brp.testrunner.omgeving.Component;
import nl.bzk.brp.testrunner.omgeving.OmgevingIncompleetException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Component test voor het relateren component.
 */
@RunWith(JUnit4.class)
public class RelaterenComponentTest { //extends BrpEmbedder {

    @Test
    public void testMaakRelaterenOmgeving() throws InterruptedException {
        final BrpOmgeving omgeving = new OmgevingBouwer().metLegeDatabase().metRelateren().maak();
        omgeving.start();
        omgeving.wachtTotFunctioneelBeschikbaar();
        omgeving.cache().update();
        omgeving.stop();
    }

    @Test(expected = OmgevingIncompleetException.class)
    public void testIncompleteOmgeving() {
        final BrpOmgeving omgeving = new OmgevingBouwer().metRelateren().maak();
        omgeving.start();
    }

    @Test
    public void testSchrijvenEnLezenPersoon() {
        final BrpOmgeving omgeving = new OmgevingBouwer().metLegeDatabase().metRelateren().maak();
        omgeving.start();
        omgeving.persoonDsl().uitBestand("dsl/huwelijk.groovy");
        final Component component = omgeving.geefComponent(ComponentNamen.BRP_DB);
        if (component instanceof BrpDatabase) {
            final BrpDatabase brpDatabase = (BrpDatabase) component;
            final Integer localPort = brpDatabase.getPoortMap().entrySet().iterator().next().getValue();
            System.out.println(localPort);
            // final DatabaseVerzoek dbVerzoek = new DatabaseVerzoek() {
            // @Override public void voerUitMet(final Object o) {
            //
            // }
            // }
            // brpDatabase.voerUit(dbVerzoek);
        }
        omgeving.stop();
    }

//    @Override
//    protected List<String> storyPaths() {
//        final String codeLocation = CodeLocations.codeLocationFromClass(this.getClass()).getFile();
//        return new StoryFinder()
//            .findPaths(codeLocation, Collections.singletonList("**/*.story"), Collections.<String>emptyList(), "file:" +
//                codeLocation);
//    }
}
