package xyz.meowing.knit.api.command.functions

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles

/**
 * # FunctionInvoker
 *
 * Interface to simplify invoking functions, using Java reflection.
 *
 * @see LambdaInvoker
 * @author Stivais
 */
sealed interface FunctionInvoker<T> {

    /**
     * List of parameters from the function.
     */
    val parameters: List<Parameter<*>>

    /**
     * Invokes the function, with a list of arguments.
     *
     * This can error, due to incorrect input.
     */
    fun invoke(arguments: List<Any?>): T

    /**
     * [FunctionInvoker] implementation, that is used for anonymous [functions][Function], like lambdas.
     *
     * This class uses [MethodHandle] to invoke the function.
     *
     * It is recommended to use [FunctionInvoker.from] instead of directly initializing this class.
     */
    class LambdaInvoker<T> internal constructor(
        lambda: Function<T>,
        parameterTypes: Array<out Class<*>>
    ) : FunctionInvoker<T> {
        override val parameters: List<Parameter<*>>

        /**
         * Used for invoking the function.
         */
        private val mHandle: MethodHandle

        init {
            try {
                val lambdaClass = lambda.javaClass

                val invokeMethod = lambdaClass.declaredMethods.firstOrNull { method ->
                    !method.isSynthetic &&
                            !method.isBridge &&
                            method.name != "equals" &&
                            method.name != "hashCode" &&
                            method.name != "toString"
                } ?: throw IllegalStateException("No invoke method found in lambda")

                if (!invokeMethod.isAccessible) invokeMethod.isAccessible = true
                mHandle = MethodHandles.lookup().unreflect(invokeMethod).bindTo(lambda)

                if (parameterTypes.isEmpty()) {
                    parameters = invokeMethod.parameterTypes.mapIndexed { index, type ->
                        Parameter("param$index", type, false)
                    }
                } else {
                    parameters = parameterTypes.mapIndexed { index, type ->
                        Parameter("param$index", type, false)
                    }
                }
            } catch (e: Exception) {
                throw Exception("[Knit-Commodore] Error creating Function Invoker.", e)
            }
        }

        @Suppress("UNCHECKED_CAST")
        override fun invoke(arguments: List<Any?>): T {
            return mHandle.invokeWithArguments(arguments) as T
        }
    }

    companion object {
        /**
         * Creates a function invoker with explicit parameter types.
         * If no parameter types are provided, will attempt to extract them from the lambda's method signature.
         */
        fun <T> from(function: Function<T>, vararg parameterTypes: Class<*>): FunctionInvoker<T> {
            return LambdaInvoker(function, parameterTypes)
        }
    }
}