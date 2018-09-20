package nl.bzk.brp.funqmachine.jbehave.context

import org.jbehave.core.context.ContextView
import org.springframework.stereotype.Component

/**
 * Implementatie van een ContextView waaraan we de waardes
 * kunnen opvragen.
 */
@Component
class BevraagbaarContextView implements ContextView {
    String story
    String scenario
    private String step

    @Override
    void show(String sty, String scn, String stp) {
        story = sty
        scenario = scn
        step = stp
    }

    @Override
    void close() {
        // doe niks
    }

    @Override
    public String toString() {
        return "BevraagbaarContextView{story='$story', scenario='$scenario'}";
    }
}
