class TaskItem(
    var id: String? = null,
    val name: String,
    val description: String,
    val creationDate: String? = null,
    val deletionDate: String? = null,
    val completed: Boolean = false
)