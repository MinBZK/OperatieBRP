package nl.bzk.brp.artconversie.model.jbehave

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap

/**
 *
 */
class Scenario implements Iterable<Step> {
    String name
    Multimap<String, Object> meta = HashMultimap.create()

    @Delegate
    List<Step> steps = []
}
