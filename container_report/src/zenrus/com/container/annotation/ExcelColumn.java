package zenrus.com.container.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumn {
	public String name();
	public int numberColumn() default -1;
	public boolean isDate() default false;
	public int width() default 15;
}
