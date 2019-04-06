
package models

class ViewTestData {
    def getTopics() = Array(
        "スポーツ",
        "政治",
        "経済",
        "文化"
    )
    def getTopic(index: Int): String = {
        val topics = getTopics()
        if (index < 0 || index >= topics.size) {
            return topics(0)
        }
        return topics(index )
    }
}