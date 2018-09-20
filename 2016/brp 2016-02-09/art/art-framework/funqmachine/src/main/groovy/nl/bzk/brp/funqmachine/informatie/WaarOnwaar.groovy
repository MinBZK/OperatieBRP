package nl.bzk.brp.funqmachine.informatie

/**
 *
 */
final class WaarOnwaar {
    private static final def JA_likes = ['ja', 'wel', 'j', 'y', 'waar', 'true', 'ok', 'aan']
    private static final def NEE_likes = ['nee', 'niet', 'n', 'onwaar', 'false', 'nok', 'uit']

    private WaarOnwaar() {
        // alleen static methods op deze util klasse
    }

    static boolean isWaar(String input) {
        if (JA_likes.contains(input.toLowerCase().trim())) { return true }
        if (NEE_likes.contains(input.toLowerCase().trim())) { return false }

        throw new IllegalArgumentException("De waarde '${input}' wordt niet herkend als ja/nee waarde.")
    }

    static boolean isOnwaar(String input) {
        !isWaar(input)
    }
}
