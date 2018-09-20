package nl.bzk.brp.soapui

/**
 * Wrapper voor assertion-excepties.
 */
class AssertionMisluktExceptie extends Exception {

    /**
     * Default constructor.
     * @param e De oorzaak.
     */
    public AssertionMisluktExceptie(final Throwable e) {
        super((Throwable) e)
    }

}
