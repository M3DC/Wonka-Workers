package miApp

data class OompaLoompa(
    val first_name: String,
    val last_name: String,
    val favorite: Favorite,
    val gender: Char,
    val image: String,
    val profession: String,
    val email: String,
    val age: Int,
    val country: String,
    val height: Int,
    val id: Int
) {
    val fullName: String
        get() = "$first_name $last_name"
}

data class Favorite(
    val color: String,
    val food: String,
    val random_string: String,
    val song: String
)
