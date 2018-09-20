package nl.bzk.brp.artconversie.model.jbehave

/**
 *
 */
class Story implements Iterable<Scenario> {
    String name
    List<String> meta = []

    @Delegate
    List<Scenario> scenarios = []
}
