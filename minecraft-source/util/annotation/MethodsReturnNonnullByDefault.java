package net.minecraft.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.meta.TypeQualifierDefault;
import org.jetbrains.annotations.NotNull;

@NotNull
@TypeQualifierDefault(value={ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface MethodsReturnNonnullByDefault {
}

