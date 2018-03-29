package nl.bzk.brp.docker.dockerservice.mojo.parameters;

import java.util.Arrays;
import java.util.List;

/**
 * Docker kan in twee modes worden gedraaid, de niet swarm mode en de nieuwe swarm mode.
 */
public enum Mode {

    /**
     * traditioneel run commando voor starten van containers.
     */
    RUN("run") {
        @Override
        public List<String> createCommand(final Action action, final String name) {
            final List<String> result;
            switch (action) {
                case CREATE:
                    result = Arrays.asList("create", Constants.ARGUMENT_NAME, name);
                    break;
                case START:
                    result = Arrays.asList("start", name);
                    break;
                case RUN:
                    result = Arrays.asList("run", "-d", Constants.ARGUMENT_NAME, name);
                    break;
                case STOP:
                    result = Arrays.asList("stop", name);
                    break;
                case REMOVE:
                    result = Arrays.asList("rm", name);
                    break;
                case EXECUTE:
                    result = Arrays.asList("run", "-t");
                    break;
                default:
                    throw new IllegalArgumentException("Actie '" + action + "' niet ondersteund voor mode 'run'.");
            }
            return result;
        }
    },

    /**
     * nieuwe swarm methode van starten dmv services.
     */
    SERVICE(Constants.SERVICE) {
        @Override
        public List<String> createCommand(final Action action, final String name) {
            final List<String> result;
            switch (action) {
                case CREATE:
                    result = Arrays.asList(Constants.SERVICE, "create", Constants.ARGUMENT_NAME, name, "--label", "nl.bzk.brp.service=" + name);
                    break;
                case REMOVE:
                    result = Arrays.asList(Constants.SERVICE, "rm", name);
                    break;
                case EXECUTE:
                    result = Arrays.asList("run", "-t");
                    break;
                default:
                    throw new IllegalArgumentException("Actie '" + action + "' niet ondersteund voor mode 'service'.");
            }
            return result;
        }
    };

    private String propertySuffix;

    /**
     * Constructor.
     * @param propertySuffix suffix van commando
     */
    Mode(final String propertySuffix) {
        this.propertySuffix = propertySuffix;
    }

    /**
     * Geef suffix terug.
     * @return de suffix
     */
    public String getPropertySuffix() {
        return propertySuffix;
    }

    /**
     * Creeer het docker commando.
     * @param action actie
     * @param name naam
     * @return docker commando
     */
    public abstract List<String> createCommand(Action action, String name);

    /**
     * Constants.
     */
    private static final class Constants {

        /**
         * Service.
         */
        static final String SERVICE = "service";

        /**
         * --name.
         */
        static final String ARGUMENT_NAME = "--name";

        private Constants() {
        }
    }
}
