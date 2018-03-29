package nl.bzk.brp.docker.dockerservice.mojo.docker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Docker commando executor.
 */
public final class DockerExecutor {

    private final Consumer<String> infoLogger;
    private final BiConsumer<String, Exception> warnLogger;
    private final List<String> baseCommand;

    /**
     * Constructor.
     *
     * @param infoLogger logger for informational messages
     * @param warnLogger logger for warning messages
     * @param dockerArguments extra arguments for the docker command
     */
    public DockerExecutor(final Consumer<String> infoLogger, final BiConsumer<String, Exception> warnLogger, final String dockerArguments) {
        this.infoLogger = infoLogger;
        this.warnLogger = warnLogger;

        baseCommand = new ArrayList<>();
        baseCommand.add("docker");
        if ((dockerArguments != null) && !"".equals(dockerArguments)) {
            baseCommand.addAll(Arrays.<String>asList(dockerArguments.split(" ")));
        }
    }

    /**
     * Execute a docker command.
     *
     * @param arguments arguments (should not contain docker only the arguments)
     * @throws DockerExecutionException on failures
     */
    public void execute(final List<String> arguments) throws DockerExecutionException {
        execute(arguments, false);
    }

    /**
     * Execute a docker command.
     *
     * @param arguments arguments (should not contain docker only the arguments)
     * @param returnOutput true if output should be returned
     * @return the output
     * @throws DockerExecutionException on failures
     */
    public List<String> execute(final List<String> arguments, final boolean returnOutput) throws DockerExecutionException {
        final List<String> command = new ArrayList<>(baseCommand);
        command.addAll(arguments);
        infoLogger.accept(String.format("Docker commando: %s", command));

        // Bouw proces
        final ProcessBuilder pb = new ProcessBuilder(command);
        final Process process;
        try {
            process = pb.start();
        } catch (final IOException e) {
            throw new DockerExecutionException("Kan het Docker commando niet uitvoeren", e);
        }

        try {
            return executeProcess(process, returnOutput);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        }
    }

    private List<String> executeProcess(final Process process, final boolean returnOutput) throws DockerExecutionException, InterruptedException {
        try (final BufferedReader procesStdOut = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                final BufferedReader procesStdErr = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"))) {

            final List<String> output = new ArrayList<>();
            final Thread stdOutReader = new Thread(new StdOutReader(procesStdOut, returnOutput ? output : null));
            stdOutReader.start();

            final Thread stdErrReader = new Thread(new StdErrReader(procesStdErr));
            stdErrReader.start();

            final int resultCode = process.waitFor();
            infoLogger.accept(String.format("Resultaat van process is: %s", resultCode));
            if (resultCode != 0) {
                throw new DockerExecutionException("Docker commando gefaald");
            }

            stdOutReader.join();
            stdErrReader.join();

            return output;
        } catch (final IOException e) {
            throw new DockerExecutionException("Error tijdens openen StdOut of StdErr van Docker commando", e);
        }
    }

    /**
     * StdOut reader.
     */
    private final class StdOutReader implements Runnable {

        private final BufferedReader procesStdOut;
        private final List<String> output;

        /**
         * Constructor.
         *
         * @param procesStdOut StdOut
         * @param output output, can be null if output shouldn't be appended
         */
        public StdOutReader(final BufferedReader procesStdOut, final List<String> output) {
            this.procesStdOut = procesStdOut;
            this.output = output;
        }

        @Override
        public void run() {
            String line;
            try {
                while ((line = procesStdOut.readLine()) != null) {
                    infoLogger.accept(line);
                    if (output != null) {
                        output.add(line);
                    }
                }
            } catch (final IOException e) {
                warnLogger.accept("Error tijdens lezen StdOut van Docker commando", e);
            }
        }
    }

    /**
     * StdErr reader.
     */
    private final class StdErrReader implements Runnable {
        private final BufferedReader procesStdErr;

        /**
         * Constructor.
         *
         * @param procesStdErr StdErr
         */
        public StdErrReader(final BufferedReader procesStdErr) {
            this.procesStdErr = procesStdErr;
        }

        @Override
        public void run() {
            String line;
            try {
                while ((line = procesStdErr.readLine()) != null) {
                    warnLogger.accept(line, null);
                }
            } catch (final IOException e) {
                warnLogger.accept("Error tijdens lezen StdErr van Docker commando", e);
            }
        }
    }

    /**
     * Docker execution exception.
     */
    public static final class DockerExecutionException extends Exception {
        private static final long serialVersionUID = 1L;

        /**
         * Constructor.
         *
         * @param message message
         */
        public DockerExecutionException(final String message) {
            super(message);
        }

        /**
         * Constructor.
         *
         * @param message message
         * @param cause cause
         */
        public DockerExecutionException(final String message, final IOException cause) {
            super(message, cause);
        }
    }
}
