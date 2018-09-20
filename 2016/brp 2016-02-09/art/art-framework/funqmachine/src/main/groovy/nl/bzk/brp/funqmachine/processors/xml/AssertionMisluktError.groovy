package nl.bzk.brp.funqmachine.processors.xml

class AssertionMisluktError extends AssertionError {

    AssertionMisluktError(final String message) {
        super(message as Object)
    }

    AssertionMisluktError(final String message, final Throwable throwable) {
        super(message, throwable)
    }
}
