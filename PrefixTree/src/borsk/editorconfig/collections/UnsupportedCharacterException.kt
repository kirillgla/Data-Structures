package borsk.editorconfig.collections

class UnsupportedCharacterException(message: String?, cause: Throwable?) : Exception(message, cause) {
  constructor() : this(null, null)
  constructor(message: String?) : this(message, null)
  constructor(cause: Throwable?) : this(null, cause)
}
