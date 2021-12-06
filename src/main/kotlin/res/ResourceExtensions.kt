package res

fun Resource.asStrings(): List<String> = items

fun <T> Resource.asType(transform: (String) -> T) = items.map(transform)

fun Resource.asInts(): List<Int> = asType { it.toInt() }