package nl.bzk.brp.docker.dockerservice.mojo.parameters;

/**
 * Ondersteunde docker acties.
 */
public enum Action {

    /** create actie. */
    CREATE("create"),

    /** start actie. */
    START("start"),

    /** run actie. */
    RUN("create"),

    /** stop actie. */
    STOP("rm"),

    /** rm actie. */
    REMOVE("rm"),

    /** execute actie. */
    EXECUTE("exec"),

    /** pull actie. */
    PULL("pull");

    private String propertyKey;

    /**
     * Constructie voor Action.
     * @param propertyKey de docker actie.
     */
    Action(final String propertyKey) {
        this.propertyKey = propertyKey;
    }

    /**
     * Geef actie van docker terug.
     * @return actie van docker
     */
    public String getPropertyKey() {
        return propertyKey;
    }

}
