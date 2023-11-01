package go.id.dinkesjatengprov.ilikes.data.remote.model

interface RetrofitListener {
    fun <T> success(data: T?)
    fun failure(failureModel: FailureModel)
}

interface RetrofitListener2 {
    fun <T> success(data: T?)
    fun <T> next(data: T)
    fun failure(failureModel: FailureModel)
}