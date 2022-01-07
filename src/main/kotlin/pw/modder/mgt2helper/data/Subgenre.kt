package pw.modder.mgt2helper.data

sealed interface Subgenre {
    val name: String

    object None: Subgenre {
        override val name = "none"
    }
    class Genre(val genre: pw.modder.mgt2helper.data.Genre): Subgenre {
        override val name: String
            get() = genre.name
    }
}