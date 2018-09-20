package nl.bzk.brp.funqmachine.ontvanger

/**
 * Generator die een willekeurig portnumber genereert, tussen
 * 12000 en 12050
 */
class RandomPortnumberGenerator implements PortnumberGenerator {
    private final int startPort = 12_000

    @Override
    int generate() {
        return startPort + new Random().nextInt(49)
    }
}
