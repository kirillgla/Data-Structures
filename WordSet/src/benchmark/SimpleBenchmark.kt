package borsk.editorconfig.collections.benchmark

import kotlin.reflect.KFunction

private const val OutputPrefix = "[TimeTest]"
private const val OutputHeader = "$OutputPrefix %-40s %-12s %-12s %-12s"
private const val OutputFormat = "$OutputPrefix %-40s %-12f %-12f %-12s"

fun benchmark(
  actionsPerIteration: Int = 1000,
  warmUpIterations: Int = 2,
  testIterations: Int = 10,
  verbose: Boolean = false,
  vararg actions: KFunction<Unit>
) {
  if (actions.isEmpty()) {
    println("$OutputPrefix Please, provide functions to test")
  }
  for (iteration in 1..warmUpIterations) {
    println("$OutputPrefix Warm up iteration $iteration...")
    for (action in actions) {
      runIteration(actionsPerIteration, verbose, action)
    }
    if (verbose) {
      println(OutputPrefix)
    }
  }

  println(OutputPrefix)
  val results: Array<ArrayList<Long>> = Array(actions.size) { ArrayList<Long>() }

  for (iteration in 1..testIterations) {
    println("$OutputPrefix Test iteration $iteration...")
    actions.forEachIndexed { index, action ->
      results[index].add(runIteration(actionsPerIteration, verbose, action))
    }
    if (verbose) {
      println(OutputPrefix)
    }
  }

  println(OutputPrefix)
  println(OutputHeader.format("Name:", "Minimum:", "Average", "Maximum"))
  actions.forEachIndexed { index, action ->
    printStats(action.name, results[index])
  }
}

private fun runIteration(numberOfActions: Int, verbose: Boolean, action: KFunction<Unit>): Long {
  System.gc()
  if (verbose) {
    print("$OutputPrefix   ${action.name}: ")
  }
  val start = System.currentTimeMillis()
  for (i in 1..numberOfActions) {
    action.call()
  }
  val end = System.currentTimeMillis()
  val result = end - start

  if (verbose) {
    println("${result}ms")
  }

  return result
}

private fun printStats(name: String, data: ArrayList<Long>) {
  println(OutputFormat.format(name, data.min()?.toDouble(), data.average(), data.max()?.toDouble()))
}
