package com.example.resumate.utilities

const val delimiters = "\"\" \",\".\",\";\",\",\",\":\""
val splitBy:Regex = Regex("[, ]|[\\s+]|[. ]|[; ]|[;]|[,]|[.]|[&]|[(]|[)]|[\"]|[@]")
val commonWords = listOf("and", "end", "the", "is", "are", "candidate", "program", "looking", "you", "as", "expertise", "experience", "in", "at", "from", "etc", "of", "that", "to", "with", "will", "would", "can", "could", "should", "by", "a", "an", "like", "all", "very", "extremely", "just", "few", "little", "extra", "small", "bit", "while", "if", "then", "next", "since", "job")
/**
 * Takes a string value as input and tokenizes a string based on the punctuation delimiters.
 *  Duplicates are removed and the remaining set is output as a List of Strings.
 */
fun createTokenSetFromString(strIn:String) : List<String>{
    var strSetOut : List<String> = strIn.split(delimiters)
    strSetOut = strSetOut.distinct()
    return strSetOut
}

fun createTokenSetFromWebpageLink(strIn:String) : MutableList<String>{
    var strSetOut : List<String> = strIn.split(splitBy)
    strSetOut = strSetOut.distinct()
    val result: MutableList<String> = strSetOut.toMutableList()
    result.removeAll(commonWords)

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