package dk.easj.anbo.restrepositorypostsexample.models

data class Post(val userId: Int, val id: Int, val title: String, val body: String) {
    override fun toString(): String {
        return "$userId $id $title"
    }
}