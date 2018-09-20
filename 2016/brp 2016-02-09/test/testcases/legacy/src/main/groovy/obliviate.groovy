import nl.bzk.brp.artconversie.Funqificator

/**
 *
 */
final def projectRoot = '/Users/sander/Projects/mGBA/git'
final def input = "$projectRoot/art/art-levering"

def arts = [
/* DONE! */
    "${input}/synchronisatie/IntakeMutatielevering/ART",
    "${input}/synchronisatie/OnderhoudAfnemerindicaties/ART",
    "${input}/synchronisatie/VerzoekSynchronisatie/ART",
    "${input}/synchronisatie/Attendering/ART",

    "${input}/synchronisatie/MutatieleveringOpDoelbinding/ART",
    "${input}/synchronisatie/MutatieleveringOpAfnemerindicatie/ART",
    "${input}/synchronisatie/SynchronisatieStamgegeven/ART",

    "${input}/bevraging/GeefDetailsPersoon/ART",

    "${input}/technisch/TechnischALP/ART",
    "${input}/technisch/TechnischBezemwagen/ART",
    "${input}/technisch/TechnischToegang/ART"
]

final def output = new File("${projectRoot}/test/testcases/legacy/src/main/resources")
final def funqificator = new Funqificator(output.canonicalPath)

arts.each { art ->
    funqificator.funqify(art)
}

funqificator.freemarkify()
