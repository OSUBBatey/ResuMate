package com.example.resumate.utilities

const val delimiters = "\"\" \",\".\",\";\",\",\",\":\""
val splitBy:Regex = Regex("[, ]|[\\s+]|[. ]|[; ]|[;]|[,]|[.]|[&]|[(]|[)]|[\"]|[@]|[:]")
/**
 * Takes a string value as input and tokenizes a string based on the punctuation delimiters.
 *  Duplicates are removed and the remaining set is output as a List of Strings.
 */
fun createTokenSetFromString(strIn:String) : List<String>{
    var strSetOut : List<String> = strIn.split(delimiters)
    strSetOut = strSetOut.distinct()
    return strSetOut
}
/**
 * Takes a string value as input and tokenizes a string based on the punctuation delimiters.
 *  Duplicates and common words are removed and the remaining set is output as a List of Strings.
 */
fun createTokenSetFromWebpageLink(strIn:String) : MutableList<String>{
    var strSetOut : List<String> = strIn.split(splitBy)
    strSetOut = strSetOut.distinct()
    val result: MutableList<String> = strSetOut.toMutableList()
    result.removeAll(commonWordList)

    return result
}

/**
 * Takes a List of <String> as input and removes all items in the List of <String> blackListSet.
 * Returns the result as a List<String>
 */
fun removeWordSet(wordSet: List<String>, blackListSet: List<String>) : List<String> {
    val tempList: MutableList<String> = ArrayList()
    for(e in wordSet){
        if(e !in blackListSet){
            tempList.add(e)
        }
    }
    return tempList.toList()
}