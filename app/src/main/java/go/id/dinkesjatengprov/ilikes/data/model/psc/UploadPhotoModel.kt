package go.id.dinkesjatengprov.ilikes.data.model.psc

import com.google.gson.annotations.SerializedName

data class UploadPhotoModel(
    @SerializedName("data")
    val `data`: UploadPhotoDataModel,
    @SerializedName("message")
    val message: String
)

data class UploadPhotoDataModel(
    @SerializedName("message")
    val message: String,
    @SerializedName("url")
    val url: String
)