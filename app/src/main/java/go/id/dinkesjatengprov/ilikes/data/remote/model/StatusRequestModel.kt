package go.id.dinkesjatengprov.ilikes.data.remote.model

data class StatusRequestModel<out T>(

    val statusRequest: StatusRequest,

    val data: T? = null,

    val failureModel: FailureModel?

){
    companion object{
        fun <T> loading(): StatusRequestModel<T> {
            return StatusRequestModel(
                StatusRequest.LOADING,
                null,
                null
            )
        }

        fun <T> error(failureModel: FailureModel?): StatusRequestModel<T> {
            return StatusRequestModel(
                StatusRequest.ERROR,
                null,
                failureModel
            )
        }

        fun <T> success(data: T): StatusRequestModel<T> {
            return StatusRequestModel(
                StatusRequest.SUCCESS,
                data,
                null
            )
        }

        fun <T> complete(data: T): StatusRequestModel<T> {
            return StatusRequestModel(
                StatusRequest.COMPLETE,
                data,
                null
            )
        }

        fun <T> empty(): StatusRequestModel<T> {
            return StatusRequestModel(
                StatusRequest.EMPTY,
                null,
                null
            )
        }
    }
}