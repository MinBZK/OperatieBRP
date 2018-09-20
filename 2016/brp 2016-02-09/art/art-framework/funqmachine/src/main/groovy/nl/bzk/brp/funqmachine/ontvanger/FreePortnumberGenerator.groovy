package nl.bzk.brp.funqmachine.ontvanger

import org.springframework.stereotype.Component

/**
 * Generator die een portnumber genereert en valideert of deze vrij is
 * voordat deze wordt teruggegeven. Waardes liggen tussen 12000 en 12050.
 */
@Component
class FreePortnumberGenerator implements PortnumberGenerator {
    private static final def PORT_RANGE = 12_000..12_049
    private static final Map<Integer, Integer> taken_numbers = new HashMap<>()

    @Override
    int generate() {
        def gevondenPort = (PORT_RANGE).find { int port ->
            def vrij = isAvailable(port)
            def bezet = taken_numbers.put(port, taken_numbers.get(port, 0) - 1)

            return vrij && bezet <= 0
        }

        taken_numbers.put(gevondenPort, 3)

        gevondenPort
    }

    /*
     * Best mogelijke poging om te valideren of een
     * port bezet is.
     */
    private boolean isAvailable(int port) {
        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    /* should not be thrown */
                }
            }
        }

        return false;
    }
}
