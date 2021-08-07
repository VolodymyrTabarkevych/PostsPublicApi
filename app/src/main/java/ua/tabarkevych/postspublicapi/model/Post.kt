package ua.tabarkevych.postspublicapi.model

class Post(
    val id: Int,
    val title: String,
    val body: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}