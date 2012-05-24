/*
 * ##############################################################################
 * #
 * # Copyright (c) 2012 RiTH-Tech (http://rith-tech.com). All Right Reserved
 * #
 * # Author : Huy Doan (huy.doan@rith-tech.com)
 * #
 * ##############################################################################
 */
package vn.paracel.pos.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Huy Doan
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DatabaseField {
    String name();
    Fields type() default Fields.CHAR;
    boolean readonly() default false;
    boolean required() default false;
    
    int defInt() default 0;
    boolean defBoolean() default false;
    String defString() default "";
    double defFloat() default 0.0;
}
