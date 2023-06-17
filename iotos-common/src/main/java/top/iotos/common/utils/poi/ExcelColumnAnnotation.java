package top.iotos.common.utils.poi;

import java.lang.annotation.*;

/**
 * 描述：自定义实体类所需要的bean 属性
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumnAnnotation {
    /**
     * Excel标题
     * @return
     */
    String value() default "";

    /**
     * Excel从左往右排列位置
     * @return
     */
    int col() default 0;
}
