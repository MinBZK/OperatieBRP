package nl.bzk.brp.funqmachine.jbehave.validatie

import com.google.common.base.Optional
import com.google.common.base.Predicate
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Predicate dat een string aanneemt die wijst naar (path) een .story bestand.
 * Deze wordt als {@link File} aan een validator gegeven die de inhoud van het story bestand controleert.
 */
class ValideStoryPredicate implements Predicate<String> {
    private Logger logger = LoggerFactory.getLogger(ValideStoryPredicate)
    private StoryFileValidator fileValidator = new StoryFileValidator()

    @Override
    boolean apply(String input) {
        Optional<Boolean> result = fileValidator.valideer(new File(getClass().getResource("/${input}").toURI()))

        if (result.orNull() == null) {
            // TODO remove work-around voor legacy stories
            logger.warn('Story [{}] is LEGACY en voldoet NIET aan de kwaliteitseisen.', input)
            result = Optional.of(true)
        } else if (!result.get()) {
            logger.error('Story [{}] voldoet NIET aan de kwaliteitseisen.', input)
        }

        return result.get()
    }
}
