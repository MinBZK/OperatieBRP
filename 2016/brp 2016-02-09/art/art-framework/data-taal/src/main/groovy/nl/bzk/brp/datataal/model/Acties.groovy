package nl.bzk.brp.datataal.model

import nl.bzk.brp.model.operationeel.kern.ActieModel

/**
 * Acties is een collectie van {@link ActieModel}.
 *
 */
class Acties implements Iterable<ActieModel> {
    private Collection<ActieModel> modellen

    /**
     * Constructor.
     *
     * @param acties de collectie van acties
     */
    Acties(final Collection<? extends ActieModel> acties) {
        this.modellen = acties
    }

    @Override
    Iterator<ActieModel> iterator() {
        return modellen.iterator()
    }
}
