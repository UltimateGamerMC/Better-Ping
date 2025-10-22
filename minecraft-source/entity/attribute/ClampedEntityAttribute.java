package net.minecraft.entity.attribute;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.math.MathHelper;

public class ClampedEntityAttribute
extends EntityAttribute {
    private final double minValue;
    private final double maxValue;

    public ClampedEntityAttribute(String translationKey, double fallback, double min, double max) {
        super(translationKey, fallback);
        this.minValue = min;
        this.maxValue = max;
        if (min > max) {
            throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
        }
        if (fallback < min) {
            throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
        }
        if (fallback > max) {
            throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
        }
    }

    public double getMinValue() {
        return this.minValue;
    }

    public double getMaxValue() {
        return this.maxValue;
    }

    @Override
    public double clamp(double value) {
        if (Double.isNaN(value)) {
            return this.minValue;
        }
        return MathHelper.clamp(value, this.minValue, this.maxValue);
    }
}

