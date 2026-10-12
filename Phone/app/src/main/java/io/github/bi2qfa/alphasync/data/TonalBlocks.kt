package io.github.bi2qfa.alphasync.data

/** Equal-width bands of display-referred JPEG brightness. */
internal object TonalBlocks {
    val supportedLevels = listOf(3, 4, 5, 6, 8)
    fun validLevels(value: Int): Int = if (value in supportedLevels) value else 4
    fun map(pixel: Int, levels: Int = 4): Int {
        require(levels in supportedLevels)
        val r = (pixel ushr 16) and 255
        val g = (pixel ushr 8) and 255
        val b = pixel and 255
        val luminance = (2126 * r + 7152 * g + 722 * b + 5000) / 10000
        val band = (luminance * levels / 256).coerceAtMost(levels - 1)
        val gray = (band * 255 + (levels - 1) / 2) / (levels - 1)
        return (pixel and 0xff000000.toInt()) or (gray shl 16) or (gray shl 8) or gray
    }
}
