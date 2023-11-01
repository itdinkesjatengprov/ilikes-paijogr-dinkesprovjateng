package go.id.dinkesjatengprov.ilikes.data.local.room

import go.id.dinkesjatengprov.ilikes.data.remote.model.FailureModel

interface RoomListener {

    fun <T> onSuccess(result: T)
    fun onFailed(failureModel: FailureModel)

}