package nl.bzk.brp.funqmachine.jbehave.steps

import nl.bzk.brp.funqmachine.jbehave.context.DefaultScenarioRunContext
import nl.bzk.brp.funqmachine.jbehave.context.StepResult
import nl.bzk.brp.funqmachine.ontvanger.HttpLeveringOntvanger
import org.codehaus.groovy.runtime.powerassert.PowerAssertionError
import org.junit.Before
import org.junit.Test
import org.springframework.test.util.ReflectionTestUtils

class AsynchroonBerichtenStepsTest {

    private AsynchroonBerichtenSteps asynchroonBerichtenSteps

    @Before
    void setUp() {
        asynchroonBerichtenSteps = new AsynchroonBerichtenSteps()
        ReflectionTestUtils.setField(asynchroonBerichtenSteps, 'runContext', new DefaultScenarioRunContext())
    }

    @Test
    void testGroepGesorteerd() {
        final String levering = new File(getClass().getResource("/xml/3-actual.xml").toURI()).text

        StepResult result = new StepResult(StepResult.Soort.LEVERING)
        result.response = levering
        asynchroonBerichtenSteps.runContext.add(result)

        final String attribuut = "naam"
        final String groep = "voornamen"
        final List<String> verwachteWaardes = new ArrayList<>()
        verwachteWaardes.add('Cees')
        verwachteWaardes.add('Peter')

        asynchroonBerichtenSteps.heeftGroepenGesorteerd(attribuut, groep, verwachteWaardes)
    }

    @Test
    void testVoorkomenAttribuutWaarde() {
        final String levering = new File(getClass().getResource("/xml/3-actual.xml").toURI()).text

        StepResult result = new StepResult(StepResult.Soort.LEVERING)
        result.response = levering
        asynchroonBerichtenSteps.runContext.add(result)

        asynchroonBerichtenSteps.heeftAttribuutInVoorkomenWaarde('geslachtsnaamstam', 'samengesteldeNaam', 1, 'Vries')
        asynchroonBerichtenSteps.heeftAttribuutInVoorkomenWaarde('scheidingsteken', 'samengesteldeNaam', 1, null)
        asynchroonBerichtenSteps.heeftAttribuutInVoorkomenWaarde('geslachtsnaamstam', 'samengesteldeNaam', 2, 'Modernodam')
        asynchroonBerichtenSteps.heeftAttribuutInVoorkomenWaarde('code', 'geslachtsaanduiding', 2, 'M')
    }

    @Test
    void testVoorkomenAttribuutAanwezig() {
        final String levering = new File(getClass().getResource("/xml/3-actual.xml").toURI()).text

        StepResult result = new StepResult(StepResult.Soort.LEVERING)
        result.response = levering
        asynchroonBerichtenSteps.runContext.add(result)

        asynchroonBerichtenSteps.isAttribuutInVookomenAanwezig('geslachtsnaamstam', 'samengesteldeNaam', 1, 'true')
        asynchroonBerichtenSteps.isAttribuutInVookomenAanwezig('scheidingsteken', 'samengesteldeNaam', 1, 'false')
        asynchroonBerichtenSteps.isAttribuutInVookomenAanwezig('foobar', 'samengesteldeNaam', 1, 'onwaar')
        asynchroonBerichtenSteps.isAttribuutInVookomenAanwezig('geslachtsnaamstam', 'samengesteldeNaam', 2, 'j')
        asynchroonBerichtenSteps.isAttribuutInVookomenAanwezig('code', 'geslachtsaanduiding', 2, 'y')
    }

    @Test
    void testLeveringVoldoetAanXsd() {
        final String levering = new File(getClass().getResource("/xml/xsd_validatie_test.xml").toURI()).text

        StepResult result = new StepResult(StepResult.Soort.LEVERING)
        result.response = levering
        asynchroonBerichtenSteps.runContext.add(result)


        asynchroonBerichtenSteps.leveringVoldoetAanXsd()
    }

    @Test
    void erIsGeenLeverBericht() {
        StepResult result = new StepResult(StepResult.Soort.LEVERING)
        result.response = null
        asynchroonBerichtenSteps.runContext.add(result)

        asynchroonBerichtenSteps.erIsGeenLeverBericht()
    }

    @Test(expected = PowerAssertionError.class)
    void erIsGeenLeverBerichtNietWaar() {
        StepResult result = new StepResult(StepResult.Soort.LEVERING)
        result.response = "een levering"
        asynchroonBerichtenSteps.runContext.add(result)


        asynchroonBerichtenSteps.erIsGeenLeverBericht()
    }

    @Test
    void erIsGeenLeverberichtNaTweeResultaten() {
        StepResult result = new StepResult(StepResult.Soort.LEVERING)
        result.response = 'een levering'
        asynchroonBerichtenSteps.runContext.add(result)

        StepResult result2 = new StepResult(StepResult.Soort.LEVERING)
        result.response = ''
        asynchroonBerichtenSteps.runContext.add(result2)

        asynchroonBerichtenSteps.erIsGeenLeverBericht()
    }

    @Test
    void erIsGeenLeverBerichtMetEchtZoeken() {
        asynchroonBerichtenSteps.ontvanger = new StubHttpLeveringOntvanger()

        asynchroonBerichtenSteps.haalVolledigberichtOp('Geen pop.bep. levering op basis van afnemerindicatie')
        asynchroonBerichtenSteps.haalMutatieberichtOp('foo')
        asynchroonBerichtenSteps.erIsGeenLeverBericht()
    }

    private class StubHttpLeveringOntvanger extends HttpLeveringOntvanger {

        StubHttpLeveringOntvanger() {
            super(11999)
        }

        @Override int getReceivedMessages() { 1 }

        @Override List<String> getMessages() {
            [new File(getClass().getResource("/xml/xsd_validatie_test.xml").toURI()).text]
        }
    }
}
