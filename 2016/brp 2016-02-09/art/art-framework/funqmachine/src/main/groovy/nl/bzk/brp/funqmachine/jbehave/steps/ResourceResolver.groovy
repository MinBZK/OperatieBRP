package nl.bzk.brp.funqmachine.jbehave.steps

import nl.bzk.brp.funqmachine.jbehave.context.BevraagbaarContextView
import org.jbehave.core.steps.ParameterConverters
import org.springframework.core.io.support.PathMatchingResourcePatternResolver

/**
 *
 */
class ResourceResolver {

    private BevraagbaarContextView context

    ResourceResolver(BevraagbaarContextView contextView) {
        this.context = contextView
    }

    File resolve(String fileNaam) {
        def resolver = new PathMatchingResourcePatternResolver()
        def base = ''

        if (context.story?.contains('/')) {
            base = '/' + context.story.substring(0, context.story.lastIndexOf('/'))
        }

        def resource = resolver.findPathMatchingResources("classpath*:$base/**/$fileNaam").find { it.exists() }

        if (!resource) {
            throw new ParameterConverters.ParameterConvertionFailed("Verwacht bestand ${fileNaam} te vinden in (een subdirectory van) ${base}")
        }
        return resource.file

    }
}
