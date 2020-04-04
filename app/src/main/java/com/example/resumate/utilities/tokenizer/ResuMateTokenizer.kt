package com.example.resumate.utilities.tokenizer

import java.util.*

val splitBy:Regex = Regex("[, ]|[\\s+]|[. ]|[; ]|[;]|[,]|[.]|[&]|[(]|[)]|[\"]|[@]|[:][-]")

/**
 * Takes a string value as input and tokenizes a string based on the punctuation delimiters.
 *  Duplicates and common words are removed and the remaining set is output as a List of Strings.
 */
fun createTokenSetFromString(strIn:String) : MutableList<String>{
    var strSetOut : List<String> = strIn.toLowerCase(Locale.ROOT).split(splitBy)
    strSetOut = strSetOut.distinct()
    val result: MutableList<String> = strSetOut.toMutableList()
    result.removeAll(commonWordList)
    result.removeAll(statesList)
    result.removeAll(alphabet)
    result.removeAll(number)
    result.removeAll(symbols)

    return result
}
