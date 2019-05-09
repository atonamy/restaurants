package transferwise.test.technical.restaurants.ui.helpers

class PagesHelper (val totalItems: Long, val itemsPerPage: Int) {

    val totalPages: Long by lazy {
        (totalItems + itemsPerPage - 1) / itemsPerPage
    }

    fun offset(page: Long): Long {
        if(page > totalPages)
            return -1
        return if (page < totalPages)
            page * itemsPerPage
        else
            totalItems - ((totalPages * itemsPerPage) - totalItems)
    }

}