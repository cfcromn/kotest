package io.kotest.core.source

internal class StackWalkerStackFrameProvider : StackFrameProvider {

   private val stackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)

   override fun findFirst(
      excludeDataTest: Boolean,
      predicate: (JvmStackFrame) -> Boolean,
   ): JvmStackFrame? {
      return stackWalker.walk { frames ->
         frames
            .filter { !SourceRefUtils.isExcludedFrame(it.className, excludeDataTest) }
            .map { JvmStackFrame(it.declaringClass, it.lineNumber) }
            .filter(predicate)
            .findFirst()
      }.orElse(null)
   }
}
