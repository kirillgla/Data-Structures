package borsk.editorconfig.collections

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <TObj, TData> cache(fetcher: () -> TData) = CacheDelegate<TObj, TData>(fetcher)

/**
 * Stores result of fetcher function until manually flushed.
 * Flush is preformed via assigning null.
 */
class CacheDelegate<in TObj, TData>(private val fetcher: () -> TData?) : ReadWriteProperty<TObj, TData?> {
  private var data: TData? = null

  override operator fun getValue(thisRef: TObj, property: KProperty<*>): TData? =
    // Note: Kotlin allows to write like 'fetcher().also {...}',
    // although fetcher can return null. That might be a bug
    data ?: fetcher()?.also { data = it }

  override operator fun setValue(thisRef: TObj, property: KProperty<*>, value: TData?) {
    data = value?.also { error("can only flush cache, not write ot it") }
  }
}
