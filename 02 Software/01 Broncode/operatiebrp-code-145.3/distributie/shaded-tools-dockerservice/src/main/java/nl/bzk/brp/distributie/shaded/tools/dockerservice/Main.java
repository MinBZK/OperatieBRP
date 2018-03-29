package nl.bzk.brp.distributie.shaded.tools.dockerservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import nl.bzk.brp.docker.dockerservice.mojo.docker.DockerExecutor;
import nl.bzk.brp.docker.dockerservice.mojo.docker.DockerExecutor.DockerExecutionException;
import nl.bzk.brp.docker.dockerservice.mojo.parameters.Action;
import nl.bzk.brp.docker.dockerservice.mojo.parameters.Mode;
import nl.bzk.brp.docker.dockerservice.mojo.service.ServiceUtil;
import nl.bzk.brp.docker.dockerservice.mojo.service.ServiceUtil.ServiceConfigurationException;

/**
 * Main class voor dockerservice.
 */
public final class Main {

    private Main() {
        // Not instantiated
    }

    private static void info(final String message) {
        System.out.println(message);
    }

    private static void warn(final String message, final Exception e) {
        System.err.println(message);
        if (e != null) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Docker service uitvoeren .
     * @param arguments argumenten
     */
    public static void main(final String[] arguments) {
        final CommandLineParser commandLineParser = new DefaultParser();
        try {
            // Parse command line
            final CommandLine commandLine = commandLineParser.parse(getCliOptions(), arguments);

            final String dockerArguments = getArguments(commandLine, "docker-argument");
            final Mode mode = Mode.valueOf(getArgument(commandLine, "mode", "run").toUpperCase());
            final Action action = Action.valueOf(getArgument(commandLine, "action", "run").toUpperCase());
            final String service = getArgument(commandLine, "service", null);
            final String registry = getArgument(commandLine, "registry", null);
            final String version = getArgument(commandLine, "version", null);
            final String serviceArguments = getArguments(commandLine, "service-argument");
            final boolean skipPortMapping = "true".equalsIgnoreCase(getArgument(commandLine, "skip-port-mapping", "false"));
            final String commandArguments = getArguments(commandLine, "command-argument");

            // Execute docker command
            final DockerExecutor executor = new DockerExecutor(Main::info, Main::warn, dockerArguments);

            final List<String> argumentenLijst = new ArrayList<>();
            argumentenLijst.addAll(mode.createCommand(action, ServiceUtil.bepaalContainerNaam(service)));
            argumentenLijst
                    .addAll(ServiceUtil.bepaalActieArgumenten(service, action, mode, registry, version, serviceArguments, skipPortMapping, commandArguments));

            executor.execute(argumentenLijst);
        } catch (final ParseException e) {
            warn(e.getMessage(), null);
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar dockerservice.jar", "Voer een docker service commando uit", getCliOptions(), "", true);
        } catch (final ServiceConfigurationException e) {
            warn("Kan service configuratie niet vinden", e);
            System.exit(1);
        } catch (final DockerExecutionException e) {
            warn("Kan docker commando niet uitvoeren", e);
            System.exit(1);
        }
    }

    private static String getArgument(final CommandLine commandLine, final String option, final String defaultValue) {
        if (commandLine.hasOption(option)) {
            return commandLine.getOptionValue(option);
        } else {
            return defaultValue;
        }
    }

    private static String getArguments(final CommandLine commandLine, final String option) {
        if (commandLine.hasOption(option)) {
            return Arrays.stream(commandLine.getOptionValues(option)).collect(Collectors.joining(" "));
        } else {
            return null;
        }
    }

    private static Options getCliOptions() {
        final Options options = new Options();

        options.addOption(Option.builder().longOpt("service").desc("Service naam").hasArg().required().build());
        options.addOption(Option.builder().longOpt("action").desc("Service actie [CREATE|REMOVE|EXECUTE] (default=CREATE)").hasArg().build());
        options.addOption(Option.builder().longOpt("mode").desc("Service mode [RUN|SERVICE] (default=RUN)").hasArg().build());
        options.addOption(Option.builder().longOpt("registry").desc("Image registry").hasArg().build());
        options.addOption(Option.builder().longOpt("version").desc("Image version").hasArg().build());
        options.addOption(Option.builder().longOpt("docker-argument").desc("Extra docker argumenten").hasArgs().build());
        options.addOption(Option.builder().longOpt("service-argument").desc("Extra service argumenten").hasArgs().build());
        options.addOption(Option.builder().longOpt("skip-port-mapping").desc("Poort mapping overslaan [true|false] (default=false)").hasArgs().build());
        options.addOption(Option.builder().longOpt("command-argument").desc("Extra command argumenten").hasArgs().build());

        return options;
    }

}
