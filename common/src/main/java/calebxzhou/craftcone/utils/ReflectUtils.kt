package calebxzhou.craftcone.utils

/**
 * Created  on 2023-06-29,9:47.
 */
object ReflectUtils {
    fun Any.fieldsNameValueMap(): Map<String,Any>{
        val map = hashMapOf<String,Any>()
        this.javaClass.declaredFields.forEach { field ->
            field.isAccessible = true
            var fieldValue = field.get(this)
            if(fieldValue is List<*>){
                val newFieldValue = hashMapOf<String,Any>()
                fieldValue.forEach {
                    if (it != null) {
                        newFieldValue += it.fieldsNameValueMap()
                    }
                }
                fieldValue = newFieldValue
            }
            map += Pair(field.name, fieldValue)
        }

        return map
    }
}