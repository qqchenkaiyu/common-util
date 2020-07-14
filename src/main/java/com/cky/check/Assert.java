package com.cky.check;

import com.cky.strUtil.StringUtil;

import java.util.Objects;

/**
 * 断言<br>
 * 断言某些对象或值是否符合规定，否则抛出异常。经常用于做变量检查
 * 
 * @author Looly
 *
 */
public class Assert {

	/**
	 * 断言是否为真，如果为 {@code false} 抛出 {@code IllegalArgumentException} 异常<br>
	 * 
	 * <pre class="code">
	 * Assert.isTrue(i &gt; 0, "The value must be greater than zero");
	 * </pre>
	 * 
	 * @param expression 布尔值
	 * @param errorMsgTemplate 错误抛出异常附带的消息模板，变量用{}代替
	 * @throws IllegalArgumentException if expression is {@code false}
	 */
	public static void isTrue(boolean expression, String errorMsgTemplate) throws IllegalArgumentException {
		if (false == expression) {
			throw new IllegalArgumentException(errorMsgTemplate);
		}
	}

	/**
	 * 断言是否为真，如果为 {@code false} 抛出 {@code IllegalArgumentException} 异常<br>
	 * 
	 * <pre class="code">
	 * Assert.isTrue(i &gt; 0, "The value must be greater than zero");
	 * </pre>
	 * 
	 * @param expression 布尔值
	 * @throws IllegalArgumentException if expression is {@code false}
	 */
	public static void isTrue(boolean expression) throws IllegalArgumentException {
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}
	/**
	 * 断言是否为真，如果为 {@code false} 抛出 {@code IllegalArgumentException} 异常<br>
	 *
	 * <pre class="code">
	 * Assert.isTrue(i &gt; 0, "The value must be greater than zero");
	 * </pre>
	 *
	 * @throws IllegalArgumentException if expression is {@code false}
	 */
	public static void isEquals(Object a, Object b) throws IllegalArgumentException {
		isTrue(Objects.equals(a,b), "[Assertion failed] - object a not eqauls object b");
	}

	/**
	 * 断言是否为真，如果为 {@code false} 抛出 {@code IllegalArgumentException} 异常<br>
	 *
	 * <pre class="code">
	 * Assert.isTrue(i &gt; 0, "The value must be greater than zero");
	 * </pre>
	 *
	 * @throws IllegalArgumentException if expression is {@code false}
	 */
	public static void isEquals(Object a, Object b,String errormessage) throws IllegalArgumentException {
		isTrue(Objects.equals(a,b), errormessage);
	}
	/**
	 * 断言是否为假，如果为 {@code true} 抛出 {@code IllegalArgumentException} 异常<br>
	 * 
	 * <pre class="code">
	 * Assert.isFalse(i &lt; 0, "The value must be greater than zero");
	 * </pre>
	 * 
	 * @param expression 布尔值
	 * @param errorMsgTemplate 错误抛出异常附带的消息模板，变量用{}代替
	 * @throws IllegalArgumentException if expression is {@code false}
	 */
	public static void isFalse(boolean expression, String errorMsgTemplate) throws IllegalArgumentException {
		if (expression) {
			throw new IllegalArgumentException(errorMsgTemplate);
		}
	}

	/**
	 * 断言是否为假，如果为 {@code true} 抛出 {@code IllegalArgumentException} 异常<br>
	 * 
	 * <pre class="code">
	 * Assert.isFalse(i &lt; 0);
	 * </pre>
	 * 
	 * @param expression 布尔值
	 * @throws IllegalArgumentException if expression is {@code false}
	 */
	public static void isFalse(boolean expression) throws IllegalArgumentException {
		isFalse(expression, "[Assertion failed] - this expression must be false");
	}

	/**
	 * 断言对象是否为{@code null} ，如果不为{@code null} 抛出{@link IllegalArgumentException} 异常
	 * 
	 * <pre class="code">
	 * Assert.isNull(value, "The value must be null");
	 * </pre>
	 * 
	 * @param object 被检查的对象
	 * @param errorMsgTemplate 消息模板，变量使用{}表示
	 * @throws IllegalArgumentException if the object is not {@code null}
	 */
	public static void isNull(Object object, String errorMsgTemplate) throws IllegalArgumentException {
		if (object != null) {
			throw new IllegalArgumentException(errorMsgTemplate);
		}
	}

	/**
	 * 断言对象是否为{@code null} ，如果不为{@code null} 抛出{@link IllegalArgumentException} 异常
	 * 
	 * <pre class="code">
	 * Assert.isNull(value);
	 * </pre>
	 * 
	 * @param object 被检查对象
	 * @throws IllegalArgumentException if the object is not {@code null}
	 */
	public static void isNull(Object object) throws IllegalArgumentException {
		isNull(object, "[Assertion failed] - the object argument must be null");
	}
	/**
	 * 断言对象是否不为{@code null} ，如果不为{@code null} 抛出{@link IllegalArgumentException} 异常
	 *
	 * <pre class="code">
	 * Assert.isNull(value, "The value must be null");
	 * </pre>
	 *
	 * @param object 被检查的对象
	 * @param errorMsgTemplate 消息模板，变量使用{}表示
	 * @throws IllegalArgumentException if the object is not {@code null}
	 */
	public static void isNotNull(Object object, String errorMsgTemplate) throws IllegalArgumentException {
		if (object == null) {
			throw new IllegalArgumentException(errorMsgTemplate);
		}
	}
	/**
	 * 断言对象是否不为{@code null} ，如果不为{@code null} 抛出{@link IllegalArgumentException} 异常
	 *
	 * <pre class="code">
	 * Assert.isNull(value, "The value must be null");
	 * </pre>
	 *
	 * @param object 被检查的对象
	 * @throws IllegalArgumentException if the object is not {@code null}
	 */
	public static void isNotNull(Object object) throws IllegalArgumentException {
		isNotNull(object,"The value must not be null");
	}
	// ----------------------------------------------------------------------------------------------------------- Check not null
	/**
	 * 断言对象是否不为{@code null} ，如果为{@code null} 抛出{@link IllegalArgumentException} 异常 Assert that an object is not {@code null} .
	 * 
	 * <pre class="code">
	 * Assert.notNull(clazz, "The class must not be null");
	 * </pre>
	 * 
	 * @param <T> 被检查对象泛型类型
	 * @param object 被检查对象
	 * @param errorMsgTemplate 错误消息模板，变量使用{}表示
	 * @return 被检查后的对象
	 * @throws IllegalArgumentException if the object is {@code null}
	 */
	public static <T> T notNull(T object, String errorMsgTemplate) throws IllegalArgumentException {
		if (object == null) {
			throw new IllegalArgumentException(errorMsgTemplate);
		}
		return object;
	}

	/**
	 * 断言对象是否不为{@code null} ，如果为{@code null} 抛出{@link IllegalArgumentException} 异常
	 * 
	 * <pre class="code">
	 * Assert.notNull(clazz);
	 * </pre>
	 * 
	 * @param <T> 被检查对象类型
	 * @param object 被检查对象
	 * @return 非空对象
	 * @throws IllegalArgumentException if the object is {@code null}
	 */
	public static <T> T notNull(T object) throws IllegalArgumentException {
		return notNull(object, "[Assertion failed] - this argument is required; it must not be null");
	}

	/**
	 * 检查给定字符串是否为空白（null、空串或只包含空白符），为空抛出 {@link IllegalArgumentException}
	 *
	 * <pre class="code">
	 * Assert.notBlank(name, "Name must not be blank");
	 * </pre>
	 *
	 * @param text 被检查字符串
	 * @param errorMsgTemplate 错误消息模板，变量使用{}表示
	 * @return 非空字符串
	 * @throws IllegalArgumentException 被检查字符串为空白
	 */
	public static boolean notBlank(String text, String errorMsgTemplate) throws IllegalArgumentException {
		if (StringUtil.isBlank(text)) {
			throw new IllegalArgumentException(errorMsgTemplate);
		}
		return true;
	}

	/**
	 * 检查给定字符串是否为空白（null、空串或只包含空白符），为空抛出 {@link IllegalArgumentException}
	 *
	 * <pre class="code">
	 * Assert.notBlank(name, "Name must not be blank");
	 * </pre>
	 *
	 * @param text 被检查字符串
	 * @return 非空字符串
	 * @throws IllegalArgumentException 被检查字符串为空白
	 */
	public static boolean notBlank(String text) throws IllegalArgumentException {
		return notBlank(text, "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
	}

}
