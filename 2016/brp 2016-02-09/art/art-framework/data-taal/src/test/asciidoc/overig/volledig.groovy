import nl.bzk.brp.datataal.model.Persoon

def willem = Persoon.uitDatabase(bsn: 302533928)
Persoon.nieuweGebeurtenissenVoor(willem) {
    beeindigAdres() {
        op 20010911
    }
}

Persoon.slaOp(willem)
