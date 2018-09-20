package nl.bzk.brp.funqmachine.jbehave.validatie

import com.google.common.base.Optional
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Valideert de inhoud van een .story file.
 */
class StoryFileValidator implements Validator<File> {
    private final Logger logger = LoggerFactory.getLogger(StoryFileValidator)
    private MetaVoorwaarden metaRegels = MetaVoorwaarden.instance()

    @Override
    Optional<Boolean> valideer(File input) {
        // TODO remove work-around voor legacy stories
        if (input.canonicalPath.toLowerCase().contains('legacy')) {
            return Optional.absent()
        }

        def valide = true
        List<String> regels = input.readLines().collect {it.trim()}

        List<Meta> metaTags = regels.findAll {it.startsWith('@') }.collect { new Meta(it) }

        try {
            assert regels.size() <= 1000: "De story file heeft meer dan 1000 regels"
            assert regels.any { it.trim().startsWith('Narrative:') }: "Er is geen Narrative in de story"
            assert metaTags.any { it.name.startsWith('@status') }: "Er is geen @status Meta informatie"

            heeftAlleenOndersteundeMeta(metaTags)
            waardesValide(metaTags)
        } catch (Throwable e) {
            valide = false
            logger.warn '{} faalt vanwege: \n{}', input.canonicalPath, e.message
        }
        return Optional.of(valide)
    }

    /*
     * Worden er alleen ondersteunde meta tags gebruikt?
     */
    private void heeftAlleenOndersteundeMeta(List<Meta> metas) {
        def tags = metas.collect { it.name }
        assert metaRegels.ondersteundeMetaTags().containsAll(tags): "Er zijn niet ondersteunde Meta tags gebruikt: ${tags - metaRegels.ondersteundeMetaTags()}"
    }

    /*
     * Zijn de metatags waar vaste waardes worden gebruikt ok?
     */
    private void waardesValide(List<Meta> metas) {
        metas.each { meta ->
            def definitie = metaRegels.getRegel(meta.name)

            if (definitie.regex) {
                meta.values.each { waarde ->
                    assert waarde ==~ definitie.regex: "Niet ondersteunde opmaak voor $meta.name gebruikt: ${waarde} (moet vodoen aan $definitie.regex)"
                }
            } else if (definitie.values) {
                assert definitie.values.containsAll(meta.values): "Niet ondersteunde waarde(s) voor $meta.name gebruikt: ${meta.values - definitie.values()}"
            }
        }
    }
}
