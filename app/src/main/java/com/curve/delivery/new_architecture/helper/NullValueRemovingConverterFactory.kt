import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Converter
import retrofit2.Retrofit
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class NullValueRemovingConverterFactory(private val gson: Gson) : Converter.Factory() {

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        val adapter: TypeAdapter<Any> = gson.getAdapter(TypeToken.get(type)) as TypeAdapter<Any>
        return NullValueRemovingRequestBodyConverter(gson, adapter)
    }

    private class NullValueRemovingRequestBodyConverter<T>(
        private val gson: Gson,
        private val adapter: TypeAdapter<T>
    ) : Converter<T, RequestBody> {

        override fun convert(value: T): RequestBody {
            val jsonElement = gson.toJsonTree(value)

            // Remove keys with null or blank values
            if (jsonElement.isJsonObject) {
                val jsonObject = jsonElement.asJsonObject
                val keysToRemove = mutableListOf<String>()
                for ((key, jsonValue) in jsonObject.entrySet()) {
                    if (jsonValue.isJsonNull || (jsonValue.isJsonPrimitive && jsonValue.asString.isBlank())) {
                        keysToRemove.add(key)
                    }
                }
                keysToRemove.forEach { jsonObject.remove(it) }
            }

            val buffer = Buffer()
            val writer = buffer.outputStream().writer(Charsets.UTF_8)
            gson.toJson(jsonElement, writer)
            writer.close()

            return RequestBody.create("application/json; charset=UTF-8".toMediaTypeOrNull(), buffer.readByteString())
        }
    }
}
